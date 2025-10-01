package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.code.ISliceContext;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/throwables/InvalidSliceException.class */
public class InvalidSliceException extends InvalidInjectionException {
    private static final long serialVersionUID = 1;

    public InvalidSliceException(IMixinContext iMixinContext, String str) {
        super(iMixinContext, str);
    }

    public InvalidSliceException(ISliceContext iSliceContext, String str) {
        super(iSliceContext.getContext(), str);
    }

    public InvalidSliceException(IMixinContext iMixinContext, Throwable th) {
        super(iMixinContext, th);
    }

    public InvalidSliceException(ISliceContext iSliceContext, Throwable th) {
        super(iSliceContext.getContext(), th);
    }

    public InvalidSliceException(IMixinContext iMixinContext, String str, Throwable th) {
        super(iMixinContext, str, th);
    }

    public InvalidSliceException(ISliceContext iSliceContext, String str, Throwable th) {
        super(iSliceContext.getContext(), str, th);
    }
}
