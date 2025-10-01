package org.spongepowered.asm.obfuscation.mapping.common;

import com.google.common.base.Objects;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.util.Constants;

/* loaded from: L-out.jar:org/spongepowered/asm/obfuscation/mapping/common/MappingMethod.class */
public class MappingMethod implements IMapping {
    private final String owner;
    private final String name;
    private final String desc;

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public Object getSuper() {
        return null;
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public Object copy() {
        return copy();
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public Object transform(String str) {
        return transform(str);
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public Object remap(String str) {
        return remap(str);
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public Object move(String str) {
        return move(str);
    }

    public MappingMethod(String str, String str2) {
        this(getOwnerFromName(str), getBaseName(str), str2);
    }

    public MappingMethod(String str, String str2, String str3) {
        this.owner = str;
        this.name = str2;
        this.desc = str3;
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public IMapping.Type getType() {
        return IMapping.Type.METHOD;
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public String getName() {
        if (this.name == null) {
            return null;
        }
        return (this.owner != null ? this.owner + "/" : "") + this.name;
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public String getSimpleName() {
        return this.name;
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public String getOwner() {
        return this.owner;
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public String getDesc() {
        return this.desc;
    }

    public boolean isConstructor() {
        return Constants.CTOR.equals(this.name);
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public MappingMethod move(String str) {
        return new MappingMethod(str, getSimpleName(), getDesc());
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public MappingMethod remap(String str) {
        return new MappingMethod(getOwner(), str, getDesc());
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public MappingMethod transform(String str) {
        return new MappingMethod(getOwner(), getSimpleName(), str);
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public MappingMethod copy() {
        return new MappingMethod(getOwner(), getSimpleName(), getDesc());
    }

    public MappingMethod addPrefix(String str) {
        String simpleName = getSimpleName();
        if (simpleName == null || simpleName.startsWith(str)) {
            return this;
        }
        return new MappingMethod(getOwner(), str + simpleName, getDesc());
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{getName(), this.desc});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof MappingMethod) && Objects.equal(this.name, ((MappingMethod) obj).name) && Objects.equal(this.desc, ((MappingMethod) obj).desc);
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public String serialise() {
        return toString();
    }

    public String toString() {
        String str = this.desc;
        Object[] objArr = new Object[3];
        objArr[0] = getName();
        objArr[1] = str != null ? " " : "";
        objArr[2] = str != null ? str : "";
        return String.format("%s%s%s", objArr);
    }

    private static String getBaseName(String str) {
        if (str == null) {
            return null;
        }
        int iLastIndexOf = str.lastIndexOf(47);
        return iLastIndexOf > -1 ? str.substring(iLastIndexOf + 1) : str;
    }

    private static String getOwnerFromName(String str) {
        int iLastIndexOf;
        if (str != null && (iLastIndexOf = str.lastIndexOf(47)) > -1) {
            return str.substring(0, iLastIndexOf);
        }
        return null;
    }
}
