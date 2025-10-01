package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.collect.ImmutableList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.tools.obfuscation.mirror.mapping.MappingMethodResolvable;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mirror/TypeHandle.class */
public class TypeHandle {
    private final String name;
    private final PackageElement pkg;
    private final TypeElement element;
    private TypeReference reference;

    public TypeHandle(PackageElement packageElement, String str) {
        this.name = str.replace('.', '/');
        this.pkg = packageElement;
        this.element = null;
    }

    public TypeHandle(TypeElement typeElement) {
        this.pkg = TypeUtils.getPackage(typeElement);
        this.name = TypeUtils.getInternalName(typeElement);
        this.element = typeElement;
    }

    public TypeHandle(DeclaredType declaredType) {
        this(declaredType.asElement());
    }

    public final String toString() {
        return this.name.replace('/', '.');
    }

    public final String getName() {
        return this.name;
    }

    public final String getSimpleName() {
        return Bytecode.getSimpleName(this.name);
    }

    public final PackageElement getPackage() {
        return this.pkg;
    }

    public final TypeElement getElement() {
        return this.element;
    }

    protected TypeElement getTargetElement() {
        return this.element;
    }

    public AnnotationHandle getAnnotation(Class cls) {
        return AnnotationHandle.m82of(getTargetElement(), cls);
    }

    public final List getEnclosedElements() {
        return getEnclosedElements(getTargetElement());
    }

    public List getEnclosedElements(ElementKind[] elementKindArr) {
        return getEnclosedElements(getTargetElement(), elementKindArr);
    }

    public TypeMirror getType() {
        if (getTargetElement() != null) {
            return getTargetElement().asType();
        }
        return null;
    }

    public TypeHandle getSuperclass() {
        DeclaredType superclass;
        TypeElement targetElement = getTargetElement();
        if (targetElement == null || (superclass = targetElement.getSuperclass()) == null || superclass.getKind() == TypeKind.NONE) {
            return null;
        }
        return new TypeHandle(superclass);
    }

    public List getInterfaces() {
        if (getTargetElement() == null) {
            return Collections.emptyList();
        }
        ImmutableList.Builder builder = ImmutableList.builder();
        Iterator it = getTargetElement().getInterfaces().iterator();
        while (it.hasNext()) {
            builder.add(new TypeHandle((DeclaredType) it.next()));
        }
        return builder.build();
    }

    public boolean isPublic() {
        TypeElement targetElement = getTargetElement();
        if (targetElement == null || !targetElement.getModifiers().contains(Modifier.PUBLIC)) {
            return false;
        }
        Element enclosingElement = targetElement.getEnclosingElement();
        while (true) {
            Element element = enclosingElement;
            if (element != null && element.getKind() != ElementKind.PACKAGE) {
                if (element.getModifiers().contains(Modifier.PUBLIC)) {
                    enclosingElement = element.getEnclosingElement();
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    public boolean isImaginary() {
        return getTargetElement() == null;
    }

    public final TypeReference getReference() {
        if (this.reference == null) {
            this.reference = new TypeReference(this);
        }
        return this.reference;
    }

    public MappingMethod getMappingMethod(String str, String str2) {
        return new MappingMethodResolvable(this, str, str2);
    }

    public String findDescriptor(ITargetSelectorByName iTargetSelectorByName) {
        String desc = iTargetSelectorByName.getDesc();
        if (desc == null) {
            Iterator it = getEnclosedElements(new ElementKind[]{ElementKind.METHOD}).iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                ExecutableElement executableElement = (ExecutableElement) it.next();
                if (executableElement.getSimpleName().toString().equals(iTargetSelectorByName.getName())) {
                    desc = TypeUtils.getDescriptor(executableElement);
                    break;
                }
            }
        }
        return desc;
    }

    public final FieldHandle findField(VariableElement variableElement) {
        return findField(variableElement, true);
    }

    public final FieldHandle findField(VariableElement variableElement, boolean z) {
        return findField(variableElement.getSimpleName().toString(), TypeUtils.getTypeName(variableElement.asType()), z);
    }

    public final FieldHandle findField(String str, String str2) {
        return findField(str, str2, true);
    }

    public FieldHandle findField(String str, String str2, boolean z) {
        String strStripGenerics = TypeUtils.stripGenerics(str2);
        for (VariableElement variableElement : getEnclosedElements(new ElementKind[]{ElementKind.FIELD})) {
            if (compareElement(variableElement, str, str2, z)) {
                return new FieldHandle(getTargetElement(), variableElement);
            }
            if (compareElement(variableElement, str, strStripGenerics, z)) {
                return new FieldHandle(getTargetElement(), variableElement, true);
            }
        }
        return null;
    }

    public final MethodHandle findMethod(ExecutableElement executableElement) {
        return findMethod(executableElement, true);
    }

    public final MethodHandle findMethod(ExecutableElement executableElement, boolean z) {
        return findMethod(executableElement.getSimpleName().toString(), TypeUtils.getJavaSignature((Element) executableElement), z);
    }

    public final MethodHandle findMethod(String str, String str2) {
        return findMethod(str, str2, true);
    }

    public MethodHandle findMethod(String str, String str2, boolean z) {
        return findMethod(this, str, str2, TypeUtils.stripGenerics(str2), z);
    }

    protected static MethodHandle findMethod(TypeHandle typeHandle, String str, String str2, String str3, boolean z) {
        for (ExecutableElement executableElement : getEnclosedElements(typeHandle.getTargetElement(), new ElementKind[]{ElementKind.CONSTRUCTOR, ElementKind.METHOD})) {
            if (compareElement(executableElement, str, str2, z) || compareElement(executableElement, str, str3, z)) {
                return new MethodHandle(typeHandle, executableElement);
            }
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:13:0x0048, code lost:
    
        if (r5.equals(r0) != false) goto L14;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    protected static boolean compareElement(Element element, String str, String str2, boolean z) {
        try {
            String string = element.getSimpleName().toString();
            String javaSignature = TypeUtils.getJavaSignature(element);
            Object objStripGenerics = TypeUtils.stripGenerics(javaSignature);
            if (z ? str.equals(string) : str.equalsIgnoreCase(string)) {
                if (str2.length() != 0 && !str2.equals(javaSignature)) {
                }
                return true;
            }
            return false;
        } catch (NullPointerException unused) {
            return false;
        }
    }

    protected static List getEnclosedElements(TypeElement typeElement, ElementKind[] elementKindArr) {
        if (elementKindArr == null || elementKindArr.length < 1) {
            return getEnclosedElements(typeElement);
        }
        if (typeElement == null) {
            return Collections.emptyList();
        }
        ImmutableList.Builder builder = ImmutableList.builder();
        for (Element element : typeElement.getEnclosedElements()) {
            int length = elementKindArr.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    if (element.getKind() != elementKindArr[i]) {
                        i++;
                    } else {
                        builder.add(element);
                        break;
                    }
                }
            }
        }
        return builder.build();
    }

    protected static List getEnclosedElements(TypeElement typeElement) {
        return typeElement != null ? typeElement.getEnclosedElements() : Collections.emptyList();
    }
}
