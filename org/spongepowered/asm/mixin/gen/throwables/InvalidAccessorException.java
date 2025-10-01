package org.spongepowered.asm.mixin.gen.throwables;

import org.spongepowered.asm.mixin.gen.AccessorInfo;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidMixinException;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/gen/throwables/InvalidAccessorException.class */
public class InvalidAccessorException extends InvalidMixinException {
    private static final long serialVersionUID = 2;
    private final AccessorInfo info;

    public InvalidAccessorException(IMixinContext iMixinContext, String str) {
        super(iMixinContext, str);
        this.info = null;
    }

    public InvalidAccessorException(AccessorInfo accessorInfo, String str) {
        super(accessorInfo.getContext(), str);
        this.info = accessorInfo;
    }

    public InvalidAccessorException(IMixinContext iMixinContext, Throwable th) {
        super(iMixinContext, th);
        this.info = null;
    }

    public InvalidAccessorException(AccessorInfo accessorInfo, Throwable th) {
        super(accessorInfo.getContext(), th);
        this.info = accessorInfo;
    }

    public InvalidAccessorException(IMixinContext iMixinContext, String str, Throwable th) {
        super(iMixinContext, str, th);
        this.info = null;
    }

    public InvalidAccessorException(AccessorInfo accessorInfo, String str, Throwable th) {
        super(accessorInfo.getContext(), str, th);
        this.info = accessorInfo;
    }

    public AccessorInfo getAccessorInfo() {
        return this.info;
    }
}
