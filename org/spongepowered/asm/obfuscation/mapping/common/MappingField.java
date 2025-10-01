package org.spongepowered.asm.obfuscation.mapping.common;

import com.google.common.base.Objects;
import com.google.common.base.Strings;
import org.spongepowered.asm.obfuscation.mapping.IMapping;

/* loaded from: L-out.jar:org/spongepowered/asm/obfuscation/mapping/common/MappingField.class */
public class MappingField implements IMapping {
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

    public MappingField(String str, String str2) {
        this(str, str2, null);
    }

    public MappingField(String str, String str2, String str3) {
        this.owner = str;
        this.name = str2;
        this.desc = str3;
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public IMapping.Type getType() {
        return IMapping.Type.FIELD;
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public String getName() {
        return this.name;
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public final String getSimpleName() {
        return this.name;
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public final String getOwner() {
        return this.owner;
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public final String getDesc() {
        return this.desc;
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public MappingField move(String str) {
        return new MappingField(str, getName(), getDesc());
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public MappingField remap(String str) {
        return new MappingField(getOwner(), str, getDesc());
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public MappingField transform(String str) {
        return new MappingField(getOwner(), getName(), str);
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public MappingField copy() {
        return new MappingField(getOwner(), getName(), getDesc());
    }

    public int hashCode() {
        return Objects.hashCode(new Object[]{toString()});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MappingField) {
            return Objects.equal(toString(), ((MappingField) obj).toString());
        }
        return false;
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.IMapping
    public String serialise() {
        return toString();
    }

    public String toString() {
        return String.format("L%s;%s:%s", getOwner(), getName(), Strings.nullToEmpty(getDesc()));
    }
}
