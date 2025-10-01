package org.spongepowered.tools.obfuscation.mirror;

import java.util.Iterator;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.util.SignaturePrinter;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mirror/TypeHandleSimulated.class */
public class TypeHandleSimulated extends TypeHandle {
    private final TypeElement simulatedType;

    public TypeHandleSimulated(String str, TypeMirror typeMirror) {
        this(TypeUtils.getPackage(typeMirror), str, typeMirror);
    }

    public TypeHandleSimulated(PackageElement packageElement, String str, TypeMirror typeMirror) {
        super(packageElement, str);
        this.simulatedType = ((DeclaredType) typeMirror).asElement();
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.TypeHandle
    protected TypeElement getTargetElement() {
        return this.simulatedType;
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.TypeHandle
    public String findDescriptor(ITargetSelectorByName iTargetSelectorByName) {
        if (iTargetSelectorByName != null) {
            return iTargetSelectorByName.getDesc();
        }
        return null;
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.TypeHandle
    public FieldHandle findField(String str, String str2, boolean z) {
        return new FieldHandle((String) null, str, str2);
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.TypeHandle
    public MethodHandle findMethod(String str, String str2, boolean z) {
        return new MethodHandle(null, str, str2);
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.TypeHandle
    public MappingMethod getMappingMethod(String str, String str2) {
        String descriptor = new SignaturePrinter(str, str2).setFullyQualified(true).toDescriptor();
        MethodHandle methodHandleFindMethodRecursive = findMethodRecursive((TypeHandle) this, str, descriptor, TypeUtils.stripGenerics(descriptor), true);
        return methodHandleFindMethodRecursive != null ? methodHandleFindMethodRecursive.asMapping(true) : super.getMappingMethod(str, str2);
    }

    private static MethodHandle findMethodRecursive(TypeHandle typeHandle, String str, String str2, String str3, boolean z) {
        TypeElement targetElement = typeHandle.getTargetElement();
        if (targetElement == null) {
            return null;
        }
        MethodHandle methodHandleFindMethod = TypeHandle.findMethod(typeHandle, str, str2, str3, z);
        if (methodHandleFindMethod != null) {
            return methodHandleFindMethod;
        }
        Iterator it = targetElement.getInterfaces().iterator();
        while (it.hasNext()) {
            MethodHandle methodHandleFindMethodRecursive = findMethodRecursive((TypeMirror) it.next(), str, str2, str3, z);
            if (methodHandleFindMethodRecursive != null) {
                return methodHandleFindMethodRecursive;
            }
        }
        TypeMirror superclass = targetElement.getSuperclass();
        if (superclass == null || superclass.getKind() == TypeKind.NONE) {
            return null;
        }
        return findMethodRecursive(superclass, str, str2, str3, z);
    }

    private static MethodHandle findMethodRecursive(TypeMirror typeMirror, String str, String str2, String str3, boolean z) {
        if (!(typeMirror instanceof DeclaredType)) {
            return null;
        }
        return findMethodRecursive(new TypeHandle(((DeclaredType) typeMirror).asElement()), str, str2, str3, z);
    }
}
