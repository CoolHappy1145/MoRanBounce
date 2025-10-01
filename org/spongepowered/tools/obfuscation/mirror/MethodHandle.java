package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.base.Strings;
import javax.lang.model.element.ExecutableElement;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mirror.mapping.MappingMethodResolvable;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mirror/MethodHandle.class */
public class MethodHandle extends MemberHandle {
    private final ExecutableElement element;
    private final TypeHandle ownerHandle;

    @Override // org.spongepowered.tools.obfuscation.mirror.MemberHandle
    public IMapping asMapping(boolean z) {
        return asMapping(z);
    }

    public MethodHandle(TypeHandle typeHandle, ExecutableElement executableElement) {
        this(typeHandle, executableElement, TypeUtils.getName(executableElement), TypeUtils.getDescriptor(executableElement));
    }

    public MethodHandle(TypeHandle typeHandle, String str, String str2) {
        this(typeHandle, null, str, str2);
    }

    private MethodHandle(TypeHandle typeHandle, ExecutableElement executableElement, String str, String str2) {
        super(typeHandle != null ? typeHandle.getName() : null, str, str2);
        this.element = executableElement;
        this.ownerHandle = typeHandle;
    }

    public boolean isImaginary() {
        return this.element == null;
    }

    public ExecutableElement getElement() {
        return this.element;
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.MemberHandle
    public Visibility getVisibility() {
        return TypeUtils.getVisibility(this.element);
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.MemberHandle
    public MappingMethod asMapping(boolean z) {
        if (z) {
            if (this.ownerHandle != null) {
                return new MappingMethodResolvable(this.ownerHandle, getName(), getDesc());
            }
            return new MappingMethod(getOwner(), getName(), getDesc());
        }
        return new MappingMethod(null, getName(), getDesc());
    }

    public String toString() {
        return String.format("%s%s%s", getOwner() != null ? "L" + getOwner() + ";" : "", Strings.nullToEmpty(getName()), Strings.nullToEmpty(getDesc()));
    }
}
