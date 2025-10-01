package org.spongepowered.tools.obfuscation.validation;

import java.util.Collection;
import java.util.Iterator;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.tools.obfuscation.MixinValidator;
import org.spongepowered.tools.obfuscation.SupportedOptions;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IMixinValidator;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/validation/TargetValidator.class */
public class TargetValidator extends MixinValidator {
    public TargetValidator(IMixinAnnotationProcessor iMixinAnnotationProcessor) {
        super(iMixinAnnotationProcessor, IMixinValidator.ValidationPass.LATE);
    }

    @Override // org.spongepowered.tools.obfuscation.MixinValidator
    public boolean validate(TypeElement typeElement, AnnotationHandle annotationHandle, Collection collection) {
        if ("true".equalsIgnoreCase(this.options.getOption(SupportedOptions.DISABLE_TARGET_VALIDATOR))) {
            return true;
        }
        if (typeElement.getKind() == ElementKind.INTERFACE) {
            validateInterfaceMixin(typeElement, collection);
            return true;
        }
        validateClassMixin(typeElement, collection);
        return true;
    }

    private void validateInterfaceMixin(TypeElement typeElement, Collection collection) {
        boolean z = false;
        for (Element element : typeElement.getEnclosedElements()) {
            if (element.getKind() == ElementKind.METHOD) {
                z |= (AnnotationHandle.m82of(element, Accessor.class).exists() || AnnotationHandle.m82of(element, Invoker.class).exists()) ? false : true;
            }
        }
        if (!z) {
            return;
        }
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            TypeHandle typeHandle = (TypeHandle) it.next();
            TypeElement element2 = typeHandle.getElement();
            if (element2 != null && element2.getKind() != ElementKind.INTERFACE) {
                error("Targetted type '" + typeHandle + " of " + typeElement + " is not an interface", typeElement);
            }
        }
    }

    private void validateClassMixin(TypeElement typeElement, Collection collection) {
        TypeMirror superclass = typeElement.getSuperclass();
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            TypeMirror type = ((TypeHandle) it.next()).getType();
            if (type != null && !validateSuperClass(type, superclass)) {
                error("Superclass " + superclass + " of " + typeElement + " was not found in the hierarchy of target class " + type, typeElement);
            }
        }
    }

    private boolean validateSuperClass(TypeMirror typeMirror, TypeMirror typeMirror2) {
        if (TypeUtils.isAssignable(this.processingEnv, typeMirror, typeMirror2)) {
            return true;
        }
        return validateSuperClassRecursive(typeMirror, typeMirror2);
    }

    private boolean validateSuperClassRecursive(TypeMirror typeMirror, TypeMirror typeMirror2) {
        if (!(typeMirror instanceof DeclaredType)) {
            return false;
        }
        if (TypeUtils.isAssignable(this.processingEnv, typeMirror, typeMirror2)) {
            return true;
        }
        TypeMirror superclass = ((DeclaredType) typeMirror).asElement().getSuperclass();
        if (superclass.getKind() == TypeKind.NONE) {
            return false;
        }
        if (checkMixinsFor(superclass, typeMirror2)) {
            return true;
        }
        return validateSuperClassRecursive(superclass, typeMirror2);
    }

    private boolean checkMixinsFor(TypeMirror typeMirror, TypeMirror typeMirror2) {
        Iterator it = getMixinsTargeting(typeMirror).iterator();
        while (it.hasNext()) {
            if (TypeUtils.isAssignable(this.processingEnv, (TypeMirror) it.next(), typeMirror2)) {
                return true;
            }
        }
        return false;
    }
}
