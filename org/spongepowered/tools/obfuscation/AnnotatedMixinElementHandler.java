package org.spongepowered.tools.obfuscation;

import java.util.Iterator;
import java.util.List;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelector;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.ConstraintParser;
import org.spongepowered.asm.util.throwables.ConstraintViolationException;
import org.spongepowered.asm.util.throwables.InvalidConstraintException;
import org.spongepowered.tools.obfuscation.interfaces.IMessagerSuppressible;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;
import org.spongepowered.tools.obfuscation.interfaces.IObfuscationManager;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mirror.AnnotationHandle;
import org.spongepowered.tools.obfuscation.mirror.FieldHandle;
import org.spongepowered.tools.obfuscation.mirror.MethodHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;
import org.spongepowered.tools.obfuscation.mirror.Visibility;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandler.class */
abstract class AnnotatedMixinElementHandler {
    protected final AnnotatedMixin mixin;
    protected final String classRef;

    /* renamed from: ap */
    protected final IMixinAnnotationProcessor f228ap;
    protected final IObfuscationManager obf;
    private IMappingConsumer mappings;

    /* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandler$AnnotatedElement.class */
    static abstract class AnnotatedElement {
        protected final Element element;
        protected final AnnotationHandle annotation;
        private final String desc;

        public AnnotatedElement(Element element, AnnotationHandle annotationHandle) {
            this.element = element;
            this.annotation = annotationHandle;
            this.desc = TypeUtils.getDescriptor(element);
        }

        public Element getElement() {
            return this.element;
        }

        public AnnotationHandle getAnnotation() {
            return this.annotation;
        }

        public String getSimpleName() {
            return getElement().getSimpleName().toString();
        }

        public String getDesc() {
            return this.desc;
        }

        public final void printMessage(Messager messager, Diagnostic.Kind kind, CharSequence charSequence) {
            messager.printMessage(kind, charSequence, this.element, this.annotation.asMirror());
        }

        public final void printMessage(IMessagerSuppressible iMessagerSuppressible, Diagnostic.Kind kind, CharSequence charSequence, SuppressedBy suppressedBy) {
            iMessagerSuppressible.printMessage(kind, charSequence, this.element, this.annotation.asMirror(), suppressedBy);
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandler$AliasedElementName.class */
    static class AliasedElementName {
        protected final String originalName;
        private final List aliases;
        private boolean caseSensitive;

        public AliasedElementName(Element element, AnnotationHandle annotationHandle) {
            this.originalName = element.getSimpleName().toString();
            this.aliases = annotationHandle.getList("aliases");
        }

        public AliasedElementName setCaseSensitive(boolean z) {
            this.caseSensitive = z;
            return this;
        }

        public boolean isCaseSensitive() {
            return this.caseSensitive;
        }

        public boolean hasAliases() {
            return this.aliases.size() > 0;
        }

        public List getAliases() {
            return this.aliases;
        }

        public String elementName() {
            return this.originalName;
        }

        public String baseName() {
            return this.originalName;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/AnnotatedMixinElementHandler$ShadowElementName.class */
    static class ShadowElementName extends AliasedElementName {
        private final boolean hasPrefix;
        private final String prefix;
        private final String baseName;
        private String obfuscated;

        ShadowElementName(Element element, AnnotationHandle annotationHandle) {
            super(element, annotationHandle);
            this.prefix = (String) annotationHandle.getValue("prefix", "shadow$");
            boolean z = false;
            String strSubstring = this.originalName;
            if (strSubstring.startsWith(this.prefix)) {
                z = true;
                strSubstring = strSubstring.substring(this.prefix.length());
            }
            this.hasPrefix = z;
            String str = strSubstring;
            this.baseName = str;
            this.obfuscated = str;
        }

        public String toString() {
            return this.baseName;
        }

        @Override // org.spongepowered.tools.obfuscation.AnnotatedMixinElementHandler.AliasedElementName
        public String baseName() {
            return this.baseName;
        }

        public ShadowElementName setObfuscatedName(IMapping iMapping) {
            this.obfuscated = iMapping.getName();
            return this;
        }

        public ShadowElementName setObfuscatedName(String str) {
            this.obfuscated = str;
            return this;
        }

        public boolean hasPrefix() {
            return this.hasPrefix;
        }

        public String prefix() {
            return this.hasPrefix ? this.prefix : "";
        }

        public String name() {
            return prefix(this.baseName);
        }

        public String obfuscated() {
            return prefix(this.obfuscated);
        }

        public String prefix(String str) {
            return this.hasPrefix ? this.prefix + str : str;
        }
    }

    AnnotatedMixinElementHandler(IMixinAnnotationProcessor ap, AnnotatedMixin mixin) {
        this.f228ap = ap;
        this.mixin = mixin;
        this.classRef = mixin.getClassRef();
        this.obf = ap.getObfuscationManager();
    }

    private IMappingConsumer getMappings() {
        if (this.mappings == null) {
            IMappingConsumer mappingConsumer = this.mixin.getMappings();
            if (mappingConsumer instanceof Mappings) {
                this.mappings = ((Mappings) mappingConsumer).asUnique();
            } else {
                this.mappings = mappingConsumer;
            }
        }
        return this.mappings;
    }

    protected final void addFieldMappings(String mcpName, String mcpSignature, ObfuscationData<MappingField> obfData) {
        Iterator it = obfData.iterator();
        while (it.hasNext()) {
            ObfuscationType type = (ObfuscationType) it.next();
            MappingField obfField = (MappingField) obfData.get(type);
            addFieldMapping(type, mcpName, obfField.getSimpleName(), mcpSignature, obfField.getDesc());
        }
    }

    protected final void addFieldMapping(ObfuscationType type, ShadowElementName name, String mcpSignature, String obfSignature) {
        addFieldMapping(type, name.name(), name.obfuscated(), mcpSignature, obfSignature);
    }

    protected final void addFieldMapping(ObfuscationType type, String mcpName, String obfName, String mcpSignature, String obfSignature) {
        MappingField from = new MappingField(this.classRef, mcpName, mcpSignature);
        MappingField to = new MappingField(this.classRef, obfName, obfSignature);
        getMappings().addFieldMapping(type, from, to);
    }

    protected final void addMethodMappings(String mcpName, String mcpSignature, ObfuscationData<MappingMethod> obfData) {
        Iterator it = obfData.iterator();
        while (it.hasNext()) {
            ObfuscationType type = (ObfuscationType) it.next();
            MappingMethod obfMethod = (MappingMethod) obfData.get(type);
            addMethodMapping(type, mcpName, obfMethod.getSimpleName(), mcpSignature, obfMethod.getDesc());
        }
    }

    protected final void addMethodMapping(ObfuscationType type, ShadowElementName name, String mcpSignature, String obfSignature) {
        addMethodMapping(type, name.name(), name.obfuscated(), mcpSignature, obfSignature);
    }

    protected final void addMethodMapping(ObfuscationType type, String mcpName, String obfName, String mcpSignature, String obfSignature) {
        MappingMethod from = new MappingMethod(this.classRef, mcpName, mcpSignature);
        MappingMethod to = new MappingMethod(this.classRef, obfName, obfSignature);
        getMappings().addMethodMapping(type, from, to);
    }

    protected final void checkConstraints(ExecutableElement method, AnnotationHandle annotation) {
        try {
            ConstraintParser.Constraint constraint = ConstraintParser.parse((String) annotation.getValue("constraints"));
            try {
                constraint.check(this.f228ap.getTokenProvider());
            } catch (ConstraintViolationException ex) {
                this.f228ap.printMessage(Diagnostic.Kind.ERROR, (CharSequence) ex.getMessage(), (Element) method, annotation.asMirror());
            }
        } catch (InvalidConstraintException ex2) {
            this.f228ap.printMessage(Diagnostic.Kind.WARNING, ex2.getMessage(), method, annotation.asMirror(), SuppressedBy.CONSTRAINTS);
        }
    }

    protected final void validateTarget(Element element, AnnotationHandle annotation, AliasedElementName name, String type) {
        if (element instanceof ExecutableElement) {
            validateTargetMethod((ExecutableElement) element, annotation, name, type, false, false);
        } else if (element instanceof VariableElement) {
            validateTargetField((VariableElement) element, annotation, name, type);
        }
    }

    protected final void validateTargetMethod(ExecutableElement method, AnnotationHandle annotation, AliasedElementName name, String type, boolean overwrite, boolean merge) {
        String signature = TypeUtils.getJavaSignature((Element) method);
        for (TypeHandle target : this.mixin.getTargets()) {
            if (!target.isImaginary()) {
                MethodHandle targetMethod = target.findMethod(method);
                if (targetMethod == null && name.hasPrefix()) {
                    targetMethod = target.findMethod(name.baseName(), signature);
                }
                if (targetMethod == null && name.hasAliases()) {
                    for (String alias : name.getAliases()) {
                        MethodHandle methodHandleFindMethod = target.findMethod(alias, signature);
                        targetMethod = methodHandleFindMethod;
                        if (methodHandleFindMethod != null) {
                            break;
                        }
                    }
                }
                if (targetMethod != null) {
                    if (overwrite) {
                        validateMethodVisibility(method, annotation, type, target, targetMethod);
                    }
                } else if (!merge) {
                    printMessage(Diagnostic.Kind.WARNING, "Cannot find target for " + type + " method in " + target, method, annotation, SuppressedBy.TARGET);
                }
            }
        }
    }

    private void validateMethodVisibility(ExecutableElement method, AnnotationHandle annotation, String type, TypeHandle target, MethodHandle targetMethod) {
        Visibility visTarget = targetMethod.getVisibility();
        if (visTarget == null) {
            return;
        }
        Visibility visMethod = TypeUtils.getVisibility(method);
        String visibility = "visibility of " + visTarget + " method in " + target;
        if (visTarget.ordinal() > visMethod.ordinal()) {
            printMessage(Diagnostic.Kind.WARNING, visMethod + " " + type + " method cannot reduce " + visibility, method, annotation, SuppressedBy.VISIBILITY);
        } else if (visTarget == Visibility.PRIVATE && visMethod.ordinal() > visTarget.ordinal()) {
            printMessage(Diagnostic.Kind.WARNING, visMethod + " " + type + " method will upgrade " + visibility, method, annotation, SuppressedBy.VISIBILITY);
        }
    }

    protected final void validateTargetField(VariableElement field, AnnotationHandle annotation, AliasedElementName name, String type) {
        String fieldType = field.asType().toString();
        for (TypeHandle target : this.mixin.getTargets()) {
            if (!target.isImaginary()) {
                FieldHandle targetField = target.findField(field);
                if (targetField == null) {
                    List<String> aliases = name.getAliases();
                    for (String alias : aliases) {
                        FieldHandle fieldHandleFindField = target.findField(alias, fieldType);
                        targetField = fieldHandleFindField;
                        if (fieldHandleFindField != null) {
                            break;
                        }
                    }
                    if (targetField == null) {
                        this.f228ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find target for " + type + " field in " + target, field, annotation.asMirror(), SuppressedBy.TARGET);
                    }
                }
            }
        }
    }

    protected final void validateReferencedTarget(ExecutableElement method, AnnotationHandle inject, ITargetSelector reference, String type) {
        if (!(reference instanceof ITargetSelectorByName)) {
            return;
        }
        ITargetSelectorByName nameRef = (ITargetSelectorByName) reference;
        String signature = nameRef.toDescriptor();
        for (TypeHandle target : this.mixin.getTargets()) {
            if (!target.isImaginary()) {
                MethodHandle targetMethod = target.findMethod(nameRef.getName(), signature);
                if (targetMethod == null) {
                    this.f228ap.printMessage(Diagnostic.Kind.WARNING, "Cannot find target method for " + type + " in " + target, method, inject.asMirror(), SuppressedBy.TARGET);
                }
            }
        }
    }

    private void printMessage(Diagnostic.Kind kind, String msg, Element e, AnnotationHandle annotation, SuppressedBy suppressedBy) {
        if (annotation == null) {
            this.f228ap.printMessage(kind, msg, e, suppressedBy);
        } else {
            this.f228ap.printMessage(kind, msg, e, annotation.asMirror(), suppressedBy);
        }
    }

    protected static <T extends IMapping<T>> ObfuscationData<T> stripOwnerData(ObfuscationData<T> data) {
        ObfuscationData<T> stripped = new ObfuscationData();
        Iterator it = data.iterator();
        while (it.hasNext()) {
            ObfuscationType type = (ObfuscationType) it.next();
            stripped.put(type, ((IMapping) data.get(type)).move(null));
        }
        return stripped;
    }

    protected static <T extends IMapping<T>> ObfuscationData<T> stripDescriptors(ObfuscationData<T> data) {
        ObfuscationData<T> stripped = new ObfuscationData();
        Iterator it = data.iterator();
        while (it.hasNext()) {
            ObfuscationType type = (ObfuscationType) it.next();
            stripped.put(type, ((IMapping) data.get(type)).transform(null));
        }
        return stripped;
    }
}
