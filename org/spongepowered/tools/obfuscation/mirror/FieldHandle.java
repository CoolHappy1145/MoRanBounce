package org.spongepowered.tools.obfuscation.mirror;

import com.google.common.base.Strings;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mirror/FieldHandle.class */
public class FieldHandle extends MemberHandle {
    private final VariableElement element;
    private final boolean rawType;

    @Override // org.spongepowered.tools.obfuscation.mirror.MemberHandle
    public IMapping asMapping(boolean z) {
        return asMapping(z);
    }

    public FieldHandle(TypeElement typeElement, VariableElement variableElement) {
        this(TypeUtils.getInternalName(typeElement), variableElement);
    }

    public FieldHandle(String str, VariableElement variableElement) {
        this(str, variableElement, false);
    }

    public FieldHandle(TypeElement typeElement, VariableElement variableElement, boolean z) {
        this(TypeUtils.getInternalName(typeElement), variableElement, z);
    }

    public FieldHandle(String str, VariableElement variableElement, boolean z) {
        this(str, variableElement, z, TypeUtils.getName(variableElement), TypeUtils.getInternalName(variableElement));
    }

    public FieldHandle(String str, String str2, String str3) {
        this(str, null, false, str2, str3);
    }

    private FieldHandle(String str, VariableElement variableElement, boolean z, String str2, String str3) {
        super(str, str2, str3);
        this.element = variableElement;
        this.rawType = z;
    }

    public boolean isImaginary() {
        return this.element == null;
    }

    public VariableElement getElement() {
        return this.element;
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.MemberHandle
    public Visibility getVisibility() {
        return TypeUtils.getVisibility(this.element);
    }

    public boolean isRawType() {
        return this.rawType;
    }

    @Override // org.spongepowered.tools.obfuscation.mirror.MemberHandle
    public MappingField asMapping(boolean z) {
        return new MappingField(z ? getOwner() : null, getName(), getDesc());
    }

    public String toString() {
        return String.format("%s%s:%s", getOwner() != null ? "L" + getOwner() + ";" : "", Strings.nullToEmpty(getName()), Strings.nullToEmpty(getDesc()));
    }
}
