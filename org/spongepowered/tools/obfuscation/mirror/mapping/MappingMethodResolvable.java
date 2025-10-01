package org.spongepowered.tools.obfuscation.mirror.mapping;

import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;
import org.spongepowered.tools.obfuscation.mirror.TypeUtils;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mirror/mapping/MappingMethodResolvable.class */
public final class MappingMethodResolvable extends MappingMethod {
    private final TypeHandle ownerHandle;

    @Override // org.spongepowered.asm.obfuscation.mapping.common.MappingMethod, org.spongepowered.asm.obfuscation.mapping.IMapping
    public Object getSuper() {
        return getSuper();
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.common.MappingMethod, org.spongepowered.asm.obfuscation.mapping.IMapping
    public Object copy() {
        return copy();
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.common.MappingMethod, org.spongepowered.asm.obfuscation.mapping.IMapping
    public Object transform(String str) {
        return transform(str);
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.common.MappingMethod, org.spongepowered.asm.obfuscation.mapping.IMapping
    public Object remap(String str) {
        return remap(str);
    }

    public MappingMethodResolvable(TypeHandle typeHandle, String str, String str2) {
        super(typeHandle.getName(), str, str2);
        this.ownerHandle = typeHandle;
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.common.MappingMethod, org.spongepowered.asm.obfuscation.mapping.IMapping
    public MappingMethod getSuper() {
        if (this.ownerHandle != null) {
            String simpleName = getSimpleName();
            String desc = getDesc();
            String javaSignature = TypeUtils.getJavaSignature(desc);
            TypeHandle superclass = this.ownerHandle.getSuperclass();
            if (superclass != null && superclass.findMethod(simpleName, javaSignature) != null) {
                return superclass.getMappingMethod(simpleName, desc);
            }
            for (TypeHandle typeHandle : this.ownerHandle.getInterfaces()) {
                if (typeHandle.findMethod(simpleName, javaSignature) != null) {
                    return typeHandle.getMappingMethod(simpleName, desc);
                }
            }
            if (superclass != null) {
                superclass.getMappingMethod(simpleName, desc);
                return null;
            }
            return null;
        }
        return null;
    }

    public MappingMethod move(TypeHandle typeHandle) {
        return new MappingMethodResolvable(typeHandle, getSimpleName(), getDesc());
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.common.MappingMethod, org.spongepowered.asm.obfuscation.mapping.IMapping
    public MappingMethod remap(String str) {
        return new MappingMethodResolvable(this.ownerHandle, str, getDesc());
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.common.MappingMethod, org.spongepowered.asm.obfuscation.mapping.IMapping
    public MappingMethod transform(String str) {
        return new MappingMethodResolvable(this.ownerHandle, getSimpleName(), str);
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.common.MappingMethod, org.spongepowered.asm.obfuscation.mapping.IMapping
    public MappingMethod copy() {
        return new MappingMethodResolvable(this.ownerHandle, getSimpleName(), getDesc());
    }
}
