package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/throwables/MixinTargetAlreadyLoadedException.class */
public class MixinTargetAlreadyLoadedException extends InvalidMixinException {
    private static final long serialVersionUID = 1;
    private final String target;

    public MixinTargetAlreadyLoadedException(IMixinInfo iMixinInfo, String str, String str2) {
        super(iMixinInfo, str);
        this.target = str2;
    }

    public MixinTargetAlreadyLoadedException(IMixinInfo iMixinInfo, String str, String str2, Throwable th) {
        super(iMixinInfo, str, th);
        this.target = str2;
    }

    public String getTarget() {
        return this.target;
    }
}
