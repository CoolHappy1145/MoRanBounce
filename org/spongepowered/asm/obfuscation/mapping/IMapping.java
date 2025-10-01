package org.spongepowered.asm.obfuscation.mapping;

/* loaded from: L-out.jar:org/spongepowered/asm/obfuscation/mapping/IMapping.class */
public interface IMapping {

    /* loaded from: L-out.jar:org/spongepowered/asm/obfuscation/mapping/IMapping$Type.class */
    public enum Type {
        FIELD,
        METHOD,
        CLASS,
        PACKAGE
    }

    Type getType();

    Object move(String str);

    Object remap(String str);

    Object transform(String str);

    Object copy();

    String getName();

    String getSimpleName();

    String getOwner();

    String getDesc();

    Object getSuper();

    String serialise();
}
