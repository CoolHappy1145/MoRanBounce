package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.ActivityStack;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/throwables/InvalidInjectionException.class */
public class InvalidInjectionException extends InvalidMixinException {
    private static final long serialVersionUID = 2;
    private final InjectionInfo info;

    public InvalidInjectionException(IMixinContext iMixinContext, String str) {
        super(iMixinContext, str);
        this.info = null;
    }

    public InvalidInjectionException(IMixinContext iMixinContext, String str, ActivityStack activityStack) {
        super(iMixinContext, str, activityStack);
        this.info = null;
    }

    public InvalidInjectionException(InjectionInfo injectionInfo, String str) {
        super(injectionInfo.getContext(), str);
        this.info = injectionInfo;
    }

    public InvalidInjectionException(InjectionInfo injectionInfo, String str, ActivityStack activityStack) {
        super(injectionInfo.getContext(), str, activityStack);
        this.info = injectionInfo;
    }

    public InvalidInjectionException(IMixinContext iMixinContext, Throwable th) {
        super(iMixinContext, th);
        this.info = null;
    }

    public InvalidInjectionException(IMixinContext iMixinContext, Throwable th, ActivityStack activityStack) {
        super(iMixinContext, th, activityStack);
        this.info = null;
    }

    public InvalidInjectionException(InjectionInfo injectionInfo, Throwable th) {
        super(injectionInfo.getContext(), th);
        this.info = injectionInfo;
    }

    public InvalidInjectionException(InjectionInfo injectionInfo, Throwable th, ActivityStack activityStack) {
        super(injectionInfo.getContext(), th, activityStack);
        this.info = injectionInfo;
    }

    public InvalidInjectionException(IMixinContext iMixinContext, String str, Throwable th) {
        super(iMixinContext, str, th);
        this.info = null;
    }

    public InvalidInjectionException(IMixinContext iMixinContext, String str, Throwable th, ActivityStack activityStack) {
        super(iMixinContext, str, th, activityStack);
        this.info = null;
    }

    public InvalidInjectionException(InjectionInfo injectionInfo, String str, Throwable th) {
        super(injectionInfo.getContext(), str, th);
        this.info = injectionInfo;
    }

    public InvalidInjectionException(InjectionInfo injectionInfo, String str, Throwable th, ActivityStack activityStack) {
        super(injectionInfo.getContext(), str, th, activityStack);
        this.info = injectionInfo;
    }

    public InjectionInfo getInjectionInfo() {
        return this.info;
    }
}
