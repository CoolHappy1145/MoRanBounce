package org.spongepowered.tools.obfuscation;

import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

@SupportedAnnotationTypes({"org.spongepowered.asm.mixin.Mixin", "org.spongepowered.asm.mixin.Shadow", "org.spongepowered.asm.mixin.Overwrite", "org.spongepowered.asm.mixin.gen.Accessor", "org.spongepowered.asm.mixin.Implements"})
/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/MixinObfuscationProcessorTargets.class */
public class MixinObfuscationProcessorTargets extends MixinObfuscationProcessor {
    public boolean process(Set set, RoundEnvironment roundEnvironment) {
        if (roundEnvironment.processingOver()) {
            postProcess(roundEnvironment);
            return true;
        }
        processMixins(roundEnvironment);
        processShadows(roundEnvironment);
        processOverwrites(roundEnvironment);
        processAccessors(roundEnvironment);
        processInvokers(roundEnvironment);
        processImplements(roundEnvironment);
        postProcess(roundEnvironment);
        return true;
    }

    @Override // org.spongepowered.tools.obfuscation.MixinObfuscationProcessor
    protected void postProcess(RoundEnvironment roundEnvironment) {
        super.postProcess(roundEnvironment);
        try {
            this.mixins.writeReferences();
            this.mixins.writeMappings();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processShadows(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Shadow.class)) {
            TypeElement enclosingElement = element.getEnclosingElement();
            if (!(enclosingElement instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(enclosingElement), element);
            } else {
                AnnotationHandle annotationHandleM82of = AnnotationHandle.m82of(element, Shadow.class);
                if (element.getKind() == ElementKind.FIELD) {
                    this.mixins.registerShadow(enclosingElement, (VariableElement) element, annotationHandleM82of);
                } else if (element.getKind() == ElementKind.METHOD) {
                    this.mixins.registerShadow(enclosingElement, (ExecutableElement) element, annotationHandleM82of);
                } else {
                    this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method or field", element);
                }
            }
        }
    }

    private void processOverwrites(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Overwrite.class)) {
            TypeElement enclosingElement = element.getEnclosingElement();
            if (!(enclosingElement instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(enclosingElement), element);
            } else if (element.getKind() == ElementKind.METHOD) {
                this.mixins.registerOverwrite(enclosingElement, (ExecutableElement) element);
            } else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method", element);
            }
        }
    }

    private void processAccessors(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Accessor.class)) {
            TypeElement enclosingElement = element.getEnclosingElement();
            if (!(enclosingElement instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(enclosingElement), element);
            } else if (element.getKind() == ElementKind.METHOD) {
                this.mixins.registerAccessor(enclosingElement, (ExecutableElement) element);
            } else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method", element);
            }
        }
    }

    private void processInvokers(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Invoker.class)) {
            TypeElement enclosingElement = element.getEnclosingElement();
            if (!(enclosingElement instanceof TypeElement)) {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Unexpected parent with type " + TypeUtils.getElementType(enclosingElement), element);
            } else if (element.getKind() == ElementKind.METHOD) {
                this.mixins.registerInvoker(enclosingElement, (ExecutableElement) element);
            } else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Element is not a method", element);
            }
        }
    }

    private void processImplements(RoundEnvironment roundEnvironment) {
        for (Element element : roundEnvironment.getElementsAnnotatedWith(Implements.class)) {
            if (element.getKind() == ElementKind.CLASS || element.getKind() == ElementKind.INTERFACE) {
                this.mixins.registerSoftImplements((TypeElement) element, AnnotationHandle.m82of(element, Implements.class));
            } else {
                this.mixins.printMessage(Diagnostic.Kind.ERROR, "Found an @Implements annotation on an element which is not a class or interface", element);
            }
        }
    }
}
