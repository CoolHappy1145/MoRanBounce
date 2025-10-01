package org.spongepowered.asm.obfuscation.mapping.mcp;

import org.spongepowered.asm.obfuscation.mapping.common.MappingField;

/* loaded from: L-out.jar:org/spongepowered/asm/obfuscation/mapping/mcp/MappingFieldSrg.class */
public class MappingFieldSrg extends MappingField {
    private final String srg;

    public MappingFieldSrg(String str) {
        super(getOwnerFromSrg(str), getNameFromSrg(str), null);
        this.srg = str;
    }

    public MappingFieldSrg(MappingField mappingField) {
        super(mappingField.getOwner(), mappingField.getName(), null);
        this.srg = mappingField.getOwner() + "/" + mappingField.getName();
    }

    @Override // org.spongepowered.asm.obfuscation.mapping.common.MappingField, org.spongepowered.asm.obfuscation.mapping.IMapping
    public String serialise() {
        return this.srg;
    }

    private static String getNameFromSrg(String str) {
        if (str == null) {
            return null;
        }
        int iLastIndexOf = str.lastIndexOf(47);
        return iLastIndexOf > -1 ? str.substring(iLastIndexOf + 1) : str;
    }

    private static String getOwnerFromSrg(String str) {
        int iLastIndexOf;
        if (str != null && (iLastIndexOf = str.lastIndexOf(47)) > -1) {
            return str.substring(0, iLastIndexOf);
        }
        return null;
    }
}
