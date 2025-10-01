package org.spongepowered.asm.mixin.transformer.ext;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ext/IExtension.class */
public interface IExtension {
    boolean checkActive(MixinEnvironment mixinEnvironment);

    void preApply(ITargetClassContext iTargetClassContext);

    void postApply(ITargetClassContext iTargetClassContext);

    void export(MixinEnvironment mixinEnvironment, String str, boolean z, ClassNode classNode);
}
