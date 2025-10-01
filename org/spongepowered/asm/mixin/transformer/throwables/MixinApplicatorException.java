package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.ActivityStack;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/throwables/MixinApplicatorException.class */
public class MixinApplicatorException extends InvalidMixinException {
    private static final long serialVersionUID = 1;

    public MixinApplicatorException(IMixinInfo iMixinInfo, String str) {
        super(iMixinInfo, str, (ActivityStack) null);
    }

    public MixinApplicatorException(IMixinInfo iMixinInfo, String str, ActivityStack activityStack) {
        super(iMixinInfo, str, activityStack);
    }

    public MixinApplicatorException(IMixinContext iMixinContext, String str) {
        super(iMixinContext, str, (ActivityStack) null);
    }

    public MixinApplicatorException(IMixinContext iMixinContext, String str, ActivityStack activityStack) {
        super(iMixinContext, str, activityStack);
    }

    public MixinApplicatorException(IMixinInfo iMixinInfo, String str, Throwable th) {
        super(iMixinInfo, str, th, (ActivityStack) null);
    }

    public MixinApplicatorException(IMixinInfo iMixinInfo, String str, Throwable th, ActivityStack activityStack) {
        super(iMixinInfo, str, th, activityStack);
    }

    public MixinApplicatorException(IMixinContext iMixinContext, String str, Throwable th) {
        super(iMixinContext, str, th, (ActivityStack) null);
    }

    public MixinApplicatorException(IMixinContext iMixinContext, String str, Throwable th, ActivityStack activityStack) {
        super(iMixinContext, str, th, activityStack);
    }

    public MixinApplicatorException(IMixinInfo iMixinInfo, Throwable th) {
        super(iMixinInfo, th, (ActivityStack) null);
    }

    public MixinApplicatorException(IMixinInfo iMixinInfo, Throwable th, ActivityStack activityStack) {
        super(iMixinInfo, th, activityStack);
    }

    public MixinApplicatorException(IMixinContext iMixinContext, Throwable th) {
        super(iMixinContext, th, (ActivityStack) null);
    }

    public MixinApplicatorException(IMixinContext iMixinContext, Throwable th, ActivityStack activityStack) {
        super(iMixinContext, th, activityStack);
    }
}
