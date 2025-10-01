package org.spongepowered.asm.mixin.injection.selectors;

import org.objectweb.asm.tree.AbstractInsnNode;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.util.asm.ElementNode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/selectors/ITargetSelector.class */
public interface ITargetSelector {
    ITargetSelector next();

    ITargetSelector configure(String[] strArr);

    ITargetSelector validate();

    ITargetSelector attach(IMixinContext iMixinContext);

    int getMatchCount();

    MatchResult match(ElementNode elementNode);

    MatchResult match(AbstractInsnNode abstractInsnNode);
}
