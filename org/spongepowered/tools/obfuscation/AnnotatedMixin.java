package org.spongepowered.tools.obfuscation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerAccessor;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerInjector;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerOverwrite;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerShadow;
import org.spongepowered.tools.obfuscation.interfaces.IMessagerSuppressible;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.interfaces.ITypeHandleProvider;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
import org.spongepowered.tools.obfuscation.struct.InjectorRemap;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/AnnotatedMixin.class */
class AnnotatedMixin {
    private final AnnotationHandle annotation;
    private final IMessagerSuppressible messager;
    private final ITypeHandleProvider typeProvider;
    private final IObfuscationManager obf;
    private final IMappingConsumer mappings;
    private final TypeElement mixin;
    private final List methods;
    private final TypeHandle handle;
    private final String classRef;
    private final boolean remap;
    private final boolean virtual;
    private final AnnotatedMixinElementHandlerOverwrite overwrites;
    private final AnnotatedMixinElementHandlerShadow shadows;
    private final AnnotatedMixinElementHandlerInjector injectors;
    private final AnnotatedMixinElementHandlerAccessor accessors;
    private final AnnotatedMixinElementHandlerSoftImplements softImplements;
    private final List targets = new ArrayList();
    private boolean validated = false;
    private final TypeHandle primaryTarget = initTargets();

    public AnnotatedMixin(IMixinAnnotationProcessor iMixinAnnotationProcessor, TypeElement typeElement) {
        this.typeProvider = iMixinAnnotationProcessor.getTypeProvider();
        this.obf = iMixinAnnotationProcessor.getObfuscationManager();
        this.mappings = this.obf.createMappingConsumer();
        this.messager = iMixinAnnotationProcessor;
        this.mixin = typeElement;
        this.handle = new TypeHandle(typeElement);
        this.methods = new ArrayList(this.handle.getEnclosedElements(new ElementKind[]{ElementKind.METHOD}));
        this.virtual = this.handle.getAnnotation(Pseudo.class).exists();
        this.annotation = this.handle.getAnnotation(Mixin.class);
        this.classRef = TypeUtils.getInternalName(typeElement);
        this.remap = this.annotation.getBoolean("remap", true) && this.targets.size() > 0;
        this.overwrites = new AnnotatedMixinElementHandlerOverwrite(iMixinAnnotationProcessor, this);
        this.shadows = new AnnotatedMixinElementHandlerShadow(iMixinAnnotationProcessor, this);
        this.injectors = new AnnotatedMixinElementHandlerInjector(iMixinAnnotationProcessor, this);
        this.accessors = new AnnotatedMixinElementHandlerAccessor(iMixinAnnotationProcessor, this);
        this.softImplements = new AnnotatedMixinElementHandlerSoftImplements(iMixinAnnotationProcessor, this);
    }

    AnnotatedMixin runValidators(IMixinValidator.ValidationPass validationPass, Collection collection) {
        Iterator it = collection.iterator();
        while (it.hasNext() && ((IMixinValidator) it.next()).validate(validationPass, this.mixin, this.annotation, this.targets)) {
        }
        if (validationPass == IMixinValidator.ValidationPass.FINAL && !this.validated) {
            this.validated = true;
            runFinalValidation();
        }
        return this;
    }

    private TypeHandle initTargets() {
        TypeHandle typeHandle = null;
        try {
            Iterator it = this.annotation.getList().iterator();
            while (it.hasNext()) {
                TypeHandle typeHandle2 = new TypeHandle((DeclaredType) it.next());
                if (!this.targets.contains(typeHandle2)) {
                    addTarget(typeHandle2);
                    if (typeHandle == null) {
                        typeHandle = typeHandle2;
                    }
                }
            }
        } catch (Exception e) {
            printMessage(Diagnostic.Kind.WARNING, "Error processing public targets: " + e.getClass().getName() + ": " + e.getMessage(), this);
        }
        try {
            for (String str : this.annotation.getList("targets")) {
                TypeHandle typeHandle3 = this.typeProvider.getTypeHandle(str);
                if (!this.targets.contains(typeHandle3)) {
                    if (this.virtual) {
                        typeHandle3 = this.typeProvider.getSimulatedHandle(str, this.mixin.asType());
                    } else {
                        if (typeHandle3 == null) {
                            printMessage(Diagnostic.Kind.ERROR, "Mixin target " + str + " could not be found", this);
                            return null;
                        }
                        if (typeHandle3.isPublic()) {
                            printMessage(Diagnostic.Kind.WARNING, "Mixin target " + str + " is public and must be specified in value", this, typeHandle3.getPackage().isUnnamed() ? SuppressedBy.DEFAULT_PACKAGE : SuppressedBy.PUBLIC_TARGET);
                            return null;
                        }
                    }
                    addSoftTarget(typeHandle3, str);
                    if (typeHandle == null) {
                        typeHandle = typeHandle3;
                    }
                }
            }
        } catch (Exception e2) {
            printMessage(Diagnostic.Kind.WARNING, "Error processing private targets: " + e2.getClass().getName() + ": " + e2.getMessage(), this);
        }
        if (typeHandle == null) {
            printMessage(Diagnostic.Kind.ERROR, "Mixin has no targets", this);
        }
        return typeHandle;
    }

    private void printMessage(Diagnostic.Kind kind, CharSequence charSequence, AnnotatedMixin annotatedMixin) {
        this.messager.printMessage(kind, charSequence, (Element) this.mixin, this.annotation.asMirror());
    }

    private void printMessage(Diagnostic.Kind kind, CharSequence charSequence, AnnotatedMixin annotatedMixin, SuppressedBy suppressedBy) {
        this.messager.printMessage(kind, charSequence, this.mixin, this.annotation.asMirror(), suppressedBy);
    }

    private void addSoftTarget(TypeHandle typeHandle, String str) {
        ObfuscationData obfClass = this.obf.getDataProvider().getObfClass(typeHandle);
        if (!obfClass.isEmpty()) {
            this.obf.getReferenceManager().addClassMapping(this.classRef, str, obfClass);
        }
        addTarget(typeHandle);
    }

    private void addTarget(TypeHandle typeHandle) {
        this.targets.add(typeHandle);
    }

    public String toString() {
        return this.mixin.getSimpleName().toString();
    }

    public AnnotationHandle getAnnotation() {
        return this.annotation;
    }

    public TypeElement getMixin() {
        return this.mixin;
    }

    public TypeHandle getHandle() {
        return this.handle;
    }

    public String getClassRef() {
        return this.classRef;
    }

    public boolean isInterface() {
        return this.mixin.getKind() == ElementKind.INTERFACE;
    }

    @Deprecated
    public TypeHandle getPrimaryTarget() {
        return this.primaryTarget;
    }

    public List getTargets() {
        return this.targets;
    }

    public boolean isMultiTarget() {
        return this.targets.size() > 1;
    }

    public boolean remap() {
        return this.remap;
    }

    public IMappingConsumer getMappings() {
        return this.mappings;
    }

    private void runFinalValidation() {
        Iterator it = this.methods.iterator();
        while (it.hasNext()) {
            this.overwrites.registerMerge((ExecutableElement) it.next());
        }
    }

    public void registerOverwrite(ExecutableElement executableElement, AnnotationHandle annotationHandle, boolean z) {
        this.methods.remove(executableElement);
        this.overwrites.registerOverwrite(new AnnotatedMixinElementHandlerOverwrite.AnnotatedElementOverwrite(executableElement, annotationHandle, z));
    }

    public void registerShadow(VariableElement variableElement, AnnotationHandle annotationHandle, boolean z) {
        AnnotatedMixinElementHandlerShadow annotatedMixinElementHandlerShadow = this.shadows;
        AnnotatedMixinElementHandlerShadow annotatedMixinElementHandlerShadow2 = this.shadows;
        annotatedMixinElementHandlerShadow2.getClass();
        annotatedMixinElementHandlerShadow.registerShadow(new AnnotatedMixinElementHandlerShadow.AnnotatedElementShadowField(annotatedMixinElementHandlerShadow2, variableElement, annotationHandle, z));
    }

    public void registerShadow(ExecutableElement executableElement, AnnotationHandle annotationHandle, boolean z) {
        this.methods.remove(executableElement);
        AnnotatedMixinElementHandlerShadow annotatedMixinElementHandlerShadow = this.shadows;
        AnnotatedMixinElementHandlerShadow annotatedMixinElementHandlerShadow2 = this.shadows;
        annotatedMixinElementHandlerShadow2.getClass();
        annotatedMixinElementHandlerShadow.registerShadow(new AnnotatedMixinElementHandlerShadow.AnnotatedElementShadowMethod(annotatedMixinElementHandlerShadow2, executableElement, annotationHandle, z));
    }

    public void registerInjector(ExecutableElement executableElement, AnnotationHandle annotationHandle, InjectorRemap injectorRemap) {
        this.methods.remove(executableElement);
        this.injectors.registerInjector(new AnnotatedMixinElementHandlerInjector.AnnotatedElementInjector(executableElement, annotationHandle, injectorRemap));
        Iterator it = annotationHandle.getAnnotationList("at").iterator();
        while (it.hasNext()) {
            registerInjectionPoint(executableElement, annotationHandle, (AnnotationHandle) it.next(), injectorRemap, "@At(%s)");
        }
        for (AnnotationHandle annotationHandle2 : annotationHandle.getAnnotationList("slice")) {
            String str = (String) annotationHandle2.getValue("id", "");
            AnnotationHandle annotation = annotationHandle2.getAnnotation("from");
            if (annotation != null) {
                registerInjectionPoint(executableElement, annotationHandle, annotation, injectorRemap, "@Slice[" + str + "](from=@At(%s))");
            }
            AnnotationHandle annotation2 = annotationHandle2.getAnnotation("to");
            if (annotation2 != null) {
                registerInjectionPoint(executableElement, annotationHandle, annotation2, injectorRemap, "@Slice[" + str + "](to=@At(%s))");
            }
        }
    }

    public void registerInjectionPoint(ExecutableElement executableElement, AnnotationHandle annotationHandle, AnnotationHandle annotationHandle2, InjectorRemap injectorRemap, String str) {
        this.injectors.registerInjectionPoint(new AnnotatedMixinElementHandlerInjector.AnnotatedElementInjectionPoint(executableElement, annotationHandle, annotationHandle2, injectorRemap), str);
    }

    public void registerAccessor(ExecutableElement executableElement, AnnotationHandle annotationHandle, boolean z) {
        this.methods.remove(executableElement);
        this.accessors.registerAccessor(new AnnotatedMixinElementHandlerAccessor.AnnotatedElementAccessor(executableElement, annotationHandle, z));
    }

    public void registerInvoker(ExecutableElement executableElement, AnnotationHandle annotationHandle, boolean z) {
        this.methods.remove(executableElement);
        this.accessors.registerAccessor(new AnnotatedMixinElementHandlerAccessor.AnnotatedElementInvoker(executableElement, annotationHandle, z));
    }

    public void registerSoftImplements(AnnotationHandle annotationHandle) {
        this.softImplements.process(annotationHandle);
    }
}
