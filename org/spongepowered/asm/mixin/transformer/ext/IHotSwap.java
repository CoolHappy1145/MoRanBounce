package org.spongepowered.asm.mixin.transformer.ext;

import org.objectweb.asm.tree.ClassNode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ext/IHotSwap.class */
public interface IHotSwap {
    void registerMixinClass(String str);

    void registerTargetClass(String str, ClassNode classNode);
}
