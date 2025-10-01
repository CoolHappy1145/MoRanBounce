package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/throwables/InvalidInterfaceMixinException.class */
public class InvalidInterfaceMixinException extends InvalidMixinException {
    private static final long serialVersionUID = 2;

    public InvalidInterfaceMixinException(IMixinInfo iMixinInfo, String str) {
        super(iMixinInfo, str);
    }

    public InvalidInterfaceMixinException(IMixinContext iMixinContext, String str) {
        super(iMixinContext, str);
    }

    public InvalidInterfaceMixinException(IMixinInfo iMixinInfo, Throwable th) {
        super(iMixinInfo, th);
    }

    public InvalidInterfaceMixinException(IMixinContext iMixinContext, Throwable th) {
        super(iMixinContext, th);
    }

    public InvalidInterfaceMixinException(IMixinInfo iMixinInfo, String str, Throwable th) {
        super(iMixinInfo, str, th);
    }

    public InvalidInterfaceMixinException(IMixinContext iMixinContext, String str, Throwable th) {
        super(iMixinContext, str, th);
    }
}
