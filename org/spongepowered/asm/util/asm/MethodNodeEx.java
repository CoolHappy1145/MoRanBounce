package org.spongepowered.asm.util.asm;

import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

/* loaded from: L-out.jar:org/spongepowered/asm/util/asm/MethodNodeEx.class */
public class MethodNodeEx extends MethodNode {
    private final IMixinInfo owner;
    private final String originalName;

    public MethodNodeEx(int i, String str, String str2, String str3, String[] strArr, IMixinInfo iMixinInfo) {
        super(ASM.API_VERSION, i, str, str2, str3, strArr);
        this.originalName = str;
        this.owner = iMixinInfo;
    }

    public String toString() {
        return String.format("%s%s", this.originalName, this.desc);
    }

    public String getQualifiedName() {
        return String.format("%s::%s", this.owner.getName(), this.originalName);
    }

    public String getOriginalName() {
        return this.originalName;
    }

    public IMixinInfo getOwner() {
        return this.owner;
    }

    public static String getName(MethodNode methodNode) {
        return methodNode instanceof MethodNodeEx ? ((MethodNodeEx) methodNode).getOriginalName() : methodNode.name;
    }
}
