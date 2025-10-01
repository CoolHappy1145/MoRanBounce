package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/throwables/InvalidInjectionPointException.class */
public class InvalidInjectionPointException extends InvalidInjectionException {
    private static final long serialVersionUID = 2;

    public InvalidInjectionPointException(IMixinContext iMixinContext, String str, Object[] objArr) {
        super(iMixinContext, String.format(str, objArr));
    }

    public InvalidInjectionPointException(InjectionInfo injectionInfo, String str, Object[] objArr) {
        super(injectionInfo, String.format(str, objArr));
    }

    public InvalidInjectionPointException(IMixinContext iMixinContext, Throwable th, String str, Object[] objArr) {
        super(iMixinContext, String.format(str, objArr), th);
    }

    public InvalidInjectionPointException(InjectionInfo injectionInfo, Throwable th, String str, Object[] objArr) {
        super(injectionInfo, String.format(str, objArr), th);
    }
}
