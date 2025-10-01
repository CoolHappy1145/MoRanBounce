package org.spongepowered.asm.mixin.transformer.ext;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.transformer.ClassInfo;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ext/ITargetClassContext.class */
public interface ITargetClassContext {
    ClassInfo getClassInfo();

    ClassNode getClassNode();
}
