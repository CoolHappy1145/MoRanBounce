package org.spongepowered.tools.obfuscation;

import java.io.File;
import java.util.Collection;
import java.util.List;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.ObfuscationUtil;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;
import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/ObfuscationEnvironment.class */
public abstract class ObfuscationEnvironment implements IObfuscationEnvironment {
    protected final ObfuscationType type;
    protected final IMappingProvider mappingProvider;
    protected final IMappingWriter mappingWriter;
    protected final RemapperProxy remapper = new RemapperProxy(this);

    /* renamed from: ap */
    protected final IMixinAnnotationProcessor f232ap;
    protected final String outFileName;
    protected final List<String> inFileNames;
    private boolean initDone;

    protected abstract IMappingProvider getMappingProvider(Messager messager, Filer filer);

    protected abstract IMappingWriter getMappingWriter(Messager messager, Filer filer);

    /* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/ObfuscationEnvironment$RemapperProxy.class */
    final class RemapperProxy implements ObfuscationUtil.IClassRemapper {
        final ObfuscationEnvironment this$0;

        RemapperProxy(ObfuscationEnvironment obfuscationEnvironment) {
            this.this$0 = obfuscationEnvironment;
        }

        @Override // org.spongepowered.asm.util.ObfuscationUtil.IClassRemapper
        public String map(String str) {
            if (this.this$0.mappingProvider == null) {
                return null;
            }
            return this.this$0.mappingProvider.getClassMapping(str);
        }

        @Override // org.spongepowered.asm.util.ObfuscationUtil.IClassRemapper
        public String unmap(String str) {
            if (this.this$0.mappingProvider == null) {
                return null;
            }
            return this.this$0.mappingProvider.getClassMapping(str);
        }
    }

    protected ObfuscationEnvironment(ObfuscationType type) {
        this.type = type;
        this.f232ap = type.getAnnotationProcessor();
        this.inFileNames = type.getInputFileNames();
        this.outFileName = type.getOutputFileName();
        this.mappingProvider = getMappingProvider(this.f232ap, this.f232ap.getProcessingEnvironment().getFiler());
        this.mappingWriter = getMappingWriter(this.f232ap, this.f232ap.getProcessingEnvironment().getFiler());
    }

    public String toString() {
        return this.type.toString();
    }

    private boolean initMappings() {
        if (!this.initDone) {
            this.initDone = true;
            if (this.inFileNames == null) {
                this.f232ap.printMessage(Diagnostic.Kind.ERROR, "The " + this.type.getConfig().getInputFileOption() + " argument was not supplied, obfuscation processing will not occur");
                return false;
            }
            int successCount = 0;
            for (String inputFileName : this.inFileNames) {
                File inputFile = new File(inputFileName);
                try {
                    if (inputFile.isFile()) {
                        this.f232ap.printMessage(Diagnostic.Kind.NOTE, "Loading " + this.type + " mappings from " + inputFile.getAbsolutePath());
                        this.mappingProvider.read(inputFile);
                        successCount++;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            if (successCount < 1) {
                this.f232ap.printMessage(Diagnostic.Kind.ERROR, "No valid input files for " + this.type + " could be read, processing may not be sucessful.");
                this.mappingProvider.clear();
            }
        }
        return !this.mappingProvider.isEmpty();
    }

    public ObfuscationType getType() {
        return this.type;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment
    public MappingMethod getObfMethod(ITargetSelectorRemappable method) {
        MappingMethod obfd = getObfMethod(method.asMethodMapping());
        if (obfd != null || !method.isFullyQualified()) {
            return obfd;
        }
        TypeHandle type = this.f232ap.getTypeProvider().getTypeHandle(method.getOwner());
        if (type == null || type.isImaginary()) {
            return null;
        }
        DeclaredType superclass = type.getElement().getSuperclass();
        if (superclass.getKind() != TypeKind.DECLARED) {
            return null;
        }
        String superClassName = superclass.asElement().getQualifiedName().toString();
        return getObfMethod(method.move(superClassName.replace('.', '/')));
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment
    public MappingMethod getObfMethod(MappingMethod method) {
        return getObfMethod(method, true);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment
    public MappingMethod getObfMethod(MappingMethod method, boolean lazyRemap) {
        if (initMappings()) {
            boolean remapped = true;
            boolean superRef = false;
            MappingMethod mapping = null;
            MappingMethod md = method;
            while (md != null && mapping == null) {
                mapping = this.mappingProvider.getMethodMapping(md);
                md = md.getSuper();
                superRef = true;
            }
            if (mapping == null) {
                if (lazyRemap) {
                    return null;
                }
                mapping = method.copy();
                remapped = false;
            } else if (superRef) {
                String obfOwner = getObfClass(method.getOwner());
                mapping = mapping.move(obfOwner != null ? obfOwner : method.getOwner());
            }
            String remappedOwner = getObfClass(mapping.getOwner());
            if (remappedOwner == null || remappedOwner.equals(method.getOwner()) || remappedOwner.equals(mapping.getOwner())) {
                if (remapped) {
                    return mapping;
                }
                return null;
            }
            if (remapped) {
                return mapping.move(remappedOwner);
            }
            String desc = ObfuscationUtil.mapDescriptor(mapping.getDesc(), this.remapper);
            return new MappingMethod(remappedOwner, mapping.getSimpleName(), desc);
        }
        return null;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment
    public ITargetSelectorRemappable remapDescriptor(ITargetSelectorRemappable method) {
        String newOwner;
        boolean transformed = false;
        String owner = method.getOwner();
        if (owner != null && (newOwner = this.remapper.map(owner)) != null) {
            owner = newOwner;
            transformed = true;
        }
        String desc = method.getDesc();
        if (desc != null) {
            String newDesc = ObfuscationUtil.mapDescriptor(method.getDesc(), this.remapper);
            if (!newDesc.equals(method.getDesc())) {
                desc = newDesc;
                transformed = true;
            }
        }
        if (transformed) {
            return new MemberInfo(method.getName(), owner, desc, method.getMatchCount() > 1);
        }
        return null;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment
    public String remapDescriptor(String desc) {
        return ObfuscationUtil.mapDescriptor(desc, this.remapper);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment
    public MappingField getObfField(ITargetSelectorRemappable field) {
        return getObfField(field.asFieldMapping(), true);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment
    public MappingField getObfField(MappingField field) {
        return getObfField(field, true);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment
    public MappingField getObfField(MappingField field, boolean lazyRemap) {
        if (!initMappings()) {
            return null;
        }
        MappingField mapping = this.mappingProvider.getFieldMapping(field);
        if (mapping == null) {
            if (lazyRemap) {
                return null;
            }
            mapping = field;
        }
        String remappedOwner = getObfClass(mapping.getOwner());
        if (remappedOwner == null || remappedOwner.equals(field.getOwner()) || remappedOwner.equals(mapping.getOwner())) {
            if (mapping != field) {
                return mapping;
            }
            return null;
        }
        return mapping.move(remappedOwner);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment
    public String getObfClass(String className) {
        if (!initMappings()) {
            return null;
        }
        return this.mappingProvider.getClassMapping(className);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IObfuscationEnvironment
    public void writeMappings(Collection<IMappingConsumer> consumers) {
        IMappingConsumer.MappingSet<MappingField> fields = new IMappingConsumer.MappingSet();
        IMappingConsumer.MappingSet<MappingMethod> methods = new IMappingConsumer.MappingSet();
        for (IMappingConsumer mappings : consumers) {
            fields.addAll(mappings.getFieldMappings(this.type));
            methods.addAll(mappings.getMethodMappings(this.type));
        }
        this.mappingWriter.write(this.outFileName, this.type, fields, methods);
    }
}
