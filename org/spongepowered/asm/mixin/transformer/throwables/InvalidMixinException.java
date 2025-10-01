package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.ActivityStack;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/throwables/InvalidMixinException.class */
public class InvalidMixinException extends MixinException {
    private static final long serialVersionUID = 2;
    private final IMixinInfo mixin;

    public InvalidMixinException(IMixinInfo iMixinInfo, String str) {
        super(str);
        this.mixin = iMixinInfo;
    }

    public InvalidMixinException(IMixinInfo iMixinInfo, String str, ActivityStack activityStack) {
        super(str, activityStack);
        this.mixin = iMixinInfo;
    }

    public InvalidMixinException(IMixinContext iMixinContext, String str) {
        this(iMixinContext.getMixin(), str);
    }

    public InvalidMixinException(IMixinContext iMixinContext, String str, ActivityStack activityStack) {
        this(iMixinContext.getMixin(), str, activityStack);
    }

    public InvalidMixinException(IMixinInfo iMixinInfo, Throwable th) {
        super(th);
        this.mixin = iMixinInfo;
    }

    public InvalidMixinException(IMixinInfo iMixinInfo, Throwable th, ActivityStack activityStack) {
        super(th, activityStack);
        this.mixin = iMixinInfo;
    }

    public InvalidMixinException(IMixinContext iMixinContext, Throwable th) {
        this(iMixinContext.getMixin(), th);
    }

    public InvalidMixinException(IMixinContext iMixinContext, Throwable th, ActivityStack activityStack) {
        this(iMixinContext.getMixin(), th, activityStack);
    }

    public InvalidMixinException(IMixinInfo iMixinInfo, String str, Throwable th) {
        super(str, th);
        this.mixin = iMixinInfo;
    }

    public InvalidMixinException(IMixinInfo iMixinInfo, String str, Throwable th, ActivityStack activityStack) {
        super(str, th, activityStack);
        this.mixin = iMixinInfo;
    }

    public InvalidMixinException(IMixinContext iMixinContext, String str, Throwable th) {
        super(str, th);
        this.mixin = iMixinContext.getMixin();
    }

    public InvalidMixinException(IMixinContext iMixinContext, String str, Throwable th, ActivityStack activityStack) {
        super(str, th, activityStack);
        this.mixin = iMixinContext.getMixin();
    }

    public IMixinInfo getMixin() {
        return this.mixin;
    }
}
