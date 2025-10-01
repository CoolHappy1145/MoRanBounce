package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.Locale;
import javax.lang.model.element.ExecutableElement;
import javax.tools.Diagnostic;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandler;
import org.spongepowered.tools.obfuscation.Mappings;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandlerOverwrite.class */
class AnnotatedMixinElementHandlerOverwrite extends AnnotatedMixinElementHandler {

    /* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandlerOverwrite$AnnotatedElementOverwrite.class */
    static class AnnotatedElementOverwrite extends AnnotatedMixinElementHandler.AnnotatedElement {
        private final boolean shouldRemap;

        public AnnotatedElementOverwrite(ExecutableElement executableElement, AnnotationHandle annotationHandle, boolean z) {
            super(executableElement, annotationHandle);
            this.shouldRemap = z;
        }

        public boolean shouldRemap() {
            return this.shouldRemap;
        }
    }

    AnnotatedMixinElementHandlerOverwrite(IMixinAnnotationProcessor iMixinAnnotationProcessor, AnnotatedMixin annotatedMixin) {
        super(iMixinAnnotationProcessor, annotatedMixin);
    }

    public void registerMerge(ExecutableElement executableElement) {
        validateTargetMethod(executableElement, null, new AnnotatedMixinElementHandler.AliasedElementName(executableElement, AnnotationHandle.MISSING), "overwrite", true, true);
    }

    public void registerOverwrite(AnnotatedElementOverwrite annotatedElementOverwrite) {
        validateTargetMethod((ExecutableElement) annotatedElementOverwrite.getElement(), annotatedElementOverwrite.getAnnotation(), new AnnotatedMixinElementHandler.AliasedElementName(annotatedElementOverwrite.getElement(), annotatedElementOverwrite.getAnnotation()), "@Overwrite", true, false);
        checkConstraints((ExecutableElement) annotatedElementOverwrite.getElement(), annotatedElementOverwrite.getAnnotation());
        if (annotatedElementOverwrite.shouldRemap()) {
            Iterator it = this.mixin.getTargets().iterator();
            while (it.hasNext()) {
                if (!registerOverwriteForTarget(annotatedElementOverwrite, (TypeHandle) it.next())) {
                    return;
                }
            }
        }
        if (!"true".equalsIgnoreCase(this.f228ap.getOption(SupportedOptions.DISABLE_OVERWRITE_CHECKER))) {
            Diagnostic.Kind kind = "error".equalsIgnoreCase(this.f228ap.getOption(SupportedOptions.OVERWRITE_ERROR_LEVEL)) ? Diagnostic.Kind.ERROR : Diagnostic.Kind.WARNING;
            String javadoc = this.f228ap.getJavadocProvider().getJavadoc(annotatedElementOverwrite.getElement());
            if (javadoc == null) {
                this.f228ap.printMessage(kind, "@Overwrite is missing javadoc comment", annotatedElementOverwrite.getElement(), SuppressedBy.OVERWRITE);
                return;
            }
            if (!javadoc.toLowerCase(Locale.ROOT).contains("@author")) {
                this.f228ap.printMessage(kind, "@Overwrite is missing an @author tag", annotatedElementOverwrite.getElement(), SuppressedBy.OVERWRITE);
            }
            if (!javadoc.toLowerCase(Locale.ROOT).contains("@reason")) {
                this.f228ap.printMessage(kind, "@Overwrite is missing an @reason tag", annotatedElementOverwrite.getElement(), SuppressedBy.OVERWRITE);
            }
        }
    }

    private boolean registerOverwriteForTarget(AnnotatedElementOverwrite annotatedElementOverwrite, TypeHandle typeHandle) {
        ObfuscationData obfMethod = this.obf.getDataProvider().getObfMethod(typeHandle.getMappingMethod(annotatedElementOverwrite.getSimpleName(), annotatedElementOverwrite.getDesc()));
        if (obfMethod.isEmpty()) {
            Diagnostic.Kind kind = Diagnostic.Kind.ERROR;
            try {
                if (((Boolean) annotatedElementOverwrite.getElement().getClass().getMethod("isStatic", new Class[0]).invoke(annotatedElementOverwrite.getElement(), new Object[0])).booleanValue()) {
                    kind = Diagnostic.Kind.WARNING;
                }
            } catch (Exception unused) {
            }
            this.f228ap.printMessage(kind, "No obfuscation mapping for @Overwrite method", annotatedElementOverwrite.getElement());
            return false;
        }
        try {
            addMethodMappings(annotatedElementOverwrite.getSimpleName(), annotatedElementOverwrite.getDesc(), obfMethod);
            return true;
        } catch (Mappings.MappingConflictException e) {
            annotatedElementOverwrite.printMessage(this.f228ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Overwrite method: " + e.getNew().getSimpleName() + " for target " + typeHandle + " conflicts with existing mapping " + e.getOld().getSimpleName());
            return false;
        }
    }
}
