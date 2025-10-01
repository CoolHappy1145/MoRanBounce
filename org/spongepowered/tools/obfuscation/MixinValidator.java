package org.spongepowered.tools.obfuscation;

import java.util.Collection;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.tools.Diagnostic;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.interfaces.IOptionProvider;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/MixinValidator.class */
public abstract class MixinValidator implements IMixinValidator {
    protected final ProcessingEnvironment processingEnv;
    protected final Messager messager;
    protected final IOptionProvider options;
    protected final IMixinValidator.ValidationPass pass;

    protected abstract boolean validate(TypeElement typeElement, AnnotationHandle annotationHandle, Collection collection);

    public MixinValidator(IMixinAnnotationProcessor iMixinAnnotationProcessor, IMixinValidator.ValidationPass validationPass) {
        this.processingEnv = iMixinAnnotationProcessor.getProcessingEnvironment();
        this.messager = iMixinAnnotationProcessor;
        this.options = iMixinAnnotationProcessor;
        this.pass = validationPass;
    }

    @Override // org.spongepowered.tools.obfuscation.interfaces.IMixinValidator
    public final boolean validate(IMixinValidator.ValidationPass validationPass, TypeElement typeElement, AnnotationHandle annotationHandle, Collection collection) {
        if (validationPass != this.pass) {
            return true;
        }
        return validate(typeElement, annotationHandle, collection);
    }

    protected final void note(String str, Element element) {
        this.messager.printMessage(Diagnostic.Kind.NOTE, str, element);
    }

    protected final void warning(String str, Element element, String str2) {
        this.messager.printMessage(Diagnostic.Kind.WARNING, str, element);
    }

    protected final void error(String str, Element element) {
        this.messager.printMessage(Diagnostic.Kind.ERROR, str, element);
    }

    protected final Collection getMixinsTargeting(TypeMirror typeMirror) {
        return AnnotatedMixins.getMixinsForEnvironment(this.processingEnv).getMixinsTargeting(typeMirror);
    }
}
