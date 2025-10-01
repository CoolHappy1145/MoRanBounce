package org.spongepowered.tools.obfuscation;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IReferenceManager;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/ReferenceManager.class */
public class ReferenceManager implements IReferenceManager {

    /* renamed from: ap */
    private final IMixinAnnotationProcessor f235ap;
    private final String outRefMapFileName;
    private final List environments;
    private final ReferenceMapper refMapper = new ReferenceMapper();
    private boolean allowConflicts;

    /* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/ReferenceManager$ReferenceConflictException.class */
    public static class ReferenceConflictException extends RuntimeException {
        private static final long serialVersionUID = 1;
        private final String oldReference;
        private final String newReference;

        public ReferenceConflictException(String str, String str2) {
            this.oldReference = str;
            this.newReference = str2;
        }

        public String getOld() {
            return this.oldReference;
        }

        public String getNew() {
            return this.newReference;
        }
    }

    public ReferenceManager(IMixinAnnotationProcessor iMixinAnnotationProcessor, List list) {
        this.f235ap = iMixinAnnotationProcessor;
        this.environments = list;
        this.outRefMapFileName = this.f235ap.getOption(SupportedOptions.OUT_REFMAP_FILE);
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IReferenceManager
    public boolean getAllowConflicts() {
        return this.allowConflicts;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IReferenceManager
    public void setAllowConflicts(boolean z) {
        this.allowConflicts = z;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IReferenceManager
    public void write() {
        if (this.outRefMapFileName == null) {
            return;
        }
        PrintWriter printWriterNewWriter = null;
        try {
            try {
                printWriterNewWriter = newWriter(this.outRefMapFileName, "refmap");
                this.refMapper.write(printWriterNewWriter);
                if (printWriterNewWriter != null) {
                    try {
                        printWriterNewWriter.close();
                    } catch (Exception unused) {
                    }
                }
            } catch (Throwable th) {
                if (printWriterNewWriter != null) {
                    try {
                        printWriterNewWriter.close();
                    } catch (Exception unused2) {
                    }
                }
                throw th;
            }
        } catch (IOException e) {
            e.printStackTrace();
            if (printWriterNewWriter != null) {
                try {
                    printWriterNewWriter.close();
                } catch (Exception unused3) {
                }
            }
        }
    }

    private PrintWriter newWriter(String str, String str2) {
        if (str.matches("^.*[\\\\/:].*$")) {
            File file = new File(str);
            file.getParentFile().mkdirs();
            this.f235ap.printMessage(Diagnostic.Kind.NOTE, "Writing " + str2 + " to " + file.getAbsolutePath());
            return new PrintWriter(file);
        }
        FileObject fileObjectCreateResource = this.f235ap.getProcessingEnvironment().getFiler().createResource(StandardLocation.CLASS_OUTPUT, "", str, new Element[0]);
        this.f235ap.printMessage(Diagnostic.Kind.NOTE, "Writing " + str2 + " to " + new File(fileObjectCreateResource.toUri()).getAbsolutePath());
        return new PrintWriter(fileObjectCreateResource.openWriter());
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IReferenceManager
    public ReferenceMapper getMapper() {
        return this.refMapper;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IReferenceManager
    public void addMethodMapping(String str, String str2, ObfuscationData obfuscationData) {
        for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            MappingMethod mappingMethod = (MappingMethod) obfuscationData.get(obfuscationEnvironment.getType());
            if (mappingMethod != null) {
                addMapping(obfuscationEnvironment.getType(), str, str2, new MemberInfo(mappingMethod).toString());
            }
        }
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IReferenceManager
    public void addMethodMapping(String str, String str2, ITargetSelectorRemappable iTargetSelectorRemappable, ObfuscationData obfuscationData) {
        for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            MappingMethod mappingMethod = (MappingMethod) obfuscationData.get(obfuscationEnvironment.getType());
            if (mappingMethod != null) {
                addMapping(obfuscationEnvironment.getType(), str, str2, iTargetSelectorRemappable.remapUsing(mappingMethod, true).toString());
            }
        }
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IReferenceManager
    public void addFieldMapping(String str, String str2, ITargetSelectorRemappable iTargetSelectorRemappable, ObfuscationData obfuscationData) {
        for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            MappingField mappingField = (MappingField) obfuscationData.get(obfuscationEnvironment.getType());
            if (mappingField != null) {
                addMapping(obfuscationEnvironment.getType(), str, str2, MemberInfo.fromMapping(mappingField.transform(obfuscationEnvironment.remapDescriptor(iTargetSelectorRemappable.getDesc()))).toString());
            }
        }
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IReferenceManager
    public void addClassMapping(String str, String str2, ObfuscationData obfuscationData) {
        for (ObfuscationEnvironment obfuscationEnvironment : this.environments) {
            String str3 = (String) obfuscationData.get(obfuscationEnvironment.getType());
            if (str3 != null) {
                addMapping(obfuscationEnvironment.getType(), str, str2, str3);
            }
        }
    }

    protected void addMapping(ObfuscationType obfuscationType, String str, String str2, String str3) {
        String strAddMapping = this.refMapper.addMapping(obfuscationType.getKey(), str, str2, str3);
        if (obfuscationType.isDefault()) {
            this.refMapper.addMapping(null, str, str2, str3);
        }
        if (!this.allowConflicts && strAddMapping != null && !strAddMapping.equals(str3)) {
            throw new ReferenceConflictException(strAddMapping, str3);
        }
    }
}
