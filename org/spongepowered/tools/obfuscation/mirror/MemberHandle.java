package org.spongepowered.tools.obfuscation.mirror;

import org.spongepowered.asm.obfuscation.mapping.IMapping;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mirror/MemberHandle.class */
public abstract class MemberHandle {
    private final String owner;
    private final String name;
    private final String desc;

    public abstract Visibility getVisibility();

    public abstract IMapping asMapping(boolean z);

    protected MemberHandle(String str, String str2, String str3) {
        this.owner = str;
        this.name = str2;
        this.desc = str3;
    }

    public final String getOwner() {
        return this.owner;
    }

    public final String getName() {
        return this.name;
    }

    public final String getDesc() {
        return this.desc;
    }
}
