package org.spongepowered.tools.obfuscation;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/MixinObfuscationProcessorInjection.class */
public class MixinObfuscationProcessorInjection extends MixinObfuscationProcessor {
    public Set getSupportedAnnotationTypes() {
        HashSet hashSet = new HashSet();
        hashSet.add(InterfaceC0563At.class.getName());
        Iterator it = InjectionInfo.getRegisteredAnnotations().iterator();
        while (it.hasNext()) {
            hashSet.add(((Class) it.next()).getName());
        }
        return hashSet;
    }

    public boolean process(Set set, RoundEnvironment roundEnvironment) {
        if (roundEnvironment.processingOver()) {
            postProcess(roundEnvironment);
            return true;
        }
        processMixins(roundEnvironment);
        Iterator it = InjectionInfo.getRegisteredAnnotations().iterator();
        while (it.hasNext()) {
            processInjectors(roundEnvironment, (Class) it.next());
        }
        postProcess(roundEnvironment);
        return true;
    }

    @Override // org.spongepowered.tools.obfuscation.MixinObfuscationProcessor
    protected void postProcess(RoundEnvironment roundEnvironment) {
        super.postProcess(roundEnvironment);
        try {
            this.mixins.writeReferences();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void processInjectors(RoundEnvironment roundEnvironment, Class cls) {
        for (ExecutableElement executableElement : roundEnvironment.getElementsAnnotatedWith(cls)) {
            TypeElement enclosingElement = executableElement.getEnclosingElement();
            if (!(enclosingElement instanceof TypeElement)) {
                throw new IllegalStateException("@" + cls.getSimpleName() + " element has unexpected parent with type " + TypeUtils.getElementType(enclosingElement));
            }
            AnnotationHandle annotationHandleM82of = AnnotationHandle.m82of(executableElement, cls);
            if (executableElement.getKind() == ElementKind.METHOD) {
                this.mixins.registerInjector(enclosingElement, executableElement, annotationHandleM82of);
            } else {
                this.mixins.printMessage(Diagnostic.Kind.WARNING, "Found an @" + cls.getSimpleName() + " annotation on an element which is not a method: " + executableElement.toString());
            }
        }
    }
}
