package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.Locale;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandler;
import org.spongepowered.tools.obfuscation.Mappings;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationDataProvider;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandlerShadow.class */
class AnnotatedMixinElementHandlerShadow extends AnnotatedMixinElementHandler {

    /* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandlerShadow$AnnotatedElementShadow.class */
    static abstract class AnnotatedElementShadow extends AnnotatedMixinElementHandler.AnnotatedElement {
        private final boolean shouldRemap;
        private final AnnotatedMixinElementHandler.ShadowElementName name;
        private final IMapping.Type type;

        public abstract IMapping getMapping(TypeHandle typeHandle, String str, String str2);

        public abstract void addMapping(ObfuscationType obfuscationType, IMapping iMapping);

        protected AnnotatedElementShadow(Element element, AnnotationHandle annotationHandle, boolean z, IMapping.Type type) {
            super(element, annotationHandle);
            this.shouldRemap = z;
            this.name = new AnnotatedMixinElementHandler.ShadowElementName(element, annotationHandle);
            this.type = type;
        }

        public boolean shouldRemap() {
            return this.shouldRemap;
        }

        public AnnotatedMixinElementHandler.ShadowElementName getName() {
            return this.name;
        }

        public IMapping.Type getElementType() {
            return this.type;
        }

        public String toString() {
            return getElementType().name().toLowerCase(Locale.ROOT);
        }

        public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(IMapping iMapping) {
            return setObfuscatedName(iMapping.getSimpleName());
        }

        public AnnotatedMixinElementHandler.ShadowElementName setObfuscatedName(String str) {
            return getName().setObfuscatedName(str);
        }

        public ObfuscationData getObfuscationData(IObfuscationDataProvider iObfuscationDataProvider, TypeHandle typeHandle) {
            return iObfuscationDataProvider.getObfEntry(getMapping(typeHandle, getName().toString(), getDesc()));
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandlerShadow$AnnotatedElementShadowField.class */
    class AnnotatedElementShadowField extends AnnotatedElementShadow {
        final AnnotatedMixinElementHandlerShadow this$0;

        @Override // org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow
        public IMapping getMapping(TypeHandle typeHandle, String str, String str2) {
            return getMapping(typeHandle, str, str2);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnnotatedElementShadowField(AnnotatedMixinElementHandlerShadow annotatedMixinElementHandlerShadow, VariableElement variableElement, AnnotationHandle annotationHandle, boolean z) {
            super(variableElement, annotationHandle, z, IMapping.Type.FIELD);
            this.this$0 = annotatedMixinElementHandlerShadow;
        }

        @Override // org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow
        public MappingField getMapping(TypeHandle typeHandle, String str, String str2) {
            return new MappingField(typeHandle.getName(), str, str2);
        }

        @Override // org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow
        public void addMapping(ObfuscationType obfuscationType, IMapping iMapping) {
            this.this$0.addFieldMapping(obfuscationType, setObfuscatedName(iMapping), getDesc(), iMapping.getDesc());
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandlerShadow$AnnotatedElementShadowMethod.class */
    class AnnotatedElementShadowMethod extends AnnotatedElementShadow {
        final AnnotatedMixinElementHandlerShadow this$0;

        @Override // org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow
        public IMapping getMapping(TypeHandle typeHandle, String str, String str2) {
            return getMapping(typeHandle, str, str2);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnnotatedElementShadowMethod(AnnotatedMixinElementHandlerShadow annotatedMixinElementHandlerShadow, ExecutableElement executableElement, AnnotationHandle annotationHandle, boolean z) {
            super(executableElement, annotationHandle, z, IMapping.Type.METHOD);
            this.this$0 = annotatedMixinElementHandlerShadow;
        }

        @Override // org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow
        public MappingMethod getMapping(TypeHandle typeHandle, String str, String str2) {
            return typeHandle.getMappingMethod(str, str2);
        }

        @Override // org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandlerShadow.AnnotatedElementShadow
        public void addMapping(ObfuscationType obfuscationType, IMapping iMapping) {
            this.this$0.addMethodMapping(obfuscationType, setObfuscatedName(iMapping), getDesc(), iMapping.getDesc());
        }
    }

    AnnotatedMixinElementHandlerShadow(IMixinAnnotationProcessor iMixinAnnotationProcessor, AnnotatedMixin annotatedMixin) {
        super(iMixinAnnotationProcessor, annotatedMixin);
    }

    public void registerShadow(AnnotatedElementShadow annotatedElementShadow) {
        validateTarget(annotatedElementShadow.getElement(), annotatedElementShadow.getAnnotation(), annotatedElementShadow.getName(), "@Shadow");
        if (!annotatedElementShadow.shouldRemap()) {
            return;
        }
        Iterator it = this.mixin.getTargets().iterator();
        while (it.hasNext()) {
            registerShadowForTarget(annotatedElementShadow, (TypeHandle) it.next());
        }
    }

    private void registerShadowForTarget(AnnotatedElementShadow annotatedElementShadow, TypeHandle typeHandle) {
        ObfuscationData obfuscationData = annotatedElementShadow.getObfuscationData(this.obf.getDataProvider(), typeHandle);
        if (obfuscationData.isEmpty()) {
            String str = this.mixin.isMultiTarget() ? " in target " + typeHandle : "";
            if (0 != 0) {
                annotatedElementShadow.printMessage(this.f228ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + str + " for @Shadow " + annotatedElementShadow);
                return;
            } else {
                annotatedElementShadow.printMessage(this.f228ap, Diagnostic.Kind.WARNING, "Unable to locate obfuscation mapping" + str + " for @Shadow " + annotatedElementShadow);
                return;
            }
        }
        Iterator it = obfuscationData.iterator();
        while (it.hasNext()) {
            ObfuscationType obfuscationType = (ObfuscationType) it.next();
            try {
                annotatedElementShadow.addMapping(obfuscationType, (IMapping) obfuscationData.get(obfuscationType));
            } catch (Mappings.MappingConflictException e) {
                annotatedElementShadow.printMessage(this.f228ap, Diagnostic.Kind.ERROR, "Mapping conflict for @Shadow " + annotatedElementShadow + ": " + e.getNew().getSimpleName() + " for target " + typeHandle + " conflicts with existing mapping " + e.getOld().getSimpleName());
            }
        }
    }
}
