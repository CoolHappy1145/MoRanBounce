package org.spongepowered.asm.mixin.throwables;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/throwables/MixinPrepareError.class */
public class MixinPrepareError extends MixinError {
    private static final long serialVersionUID = 1;

    public MixinPrepareError(String str) {
        super(str);
    }

    public MixinPrepareError(Throwable th) {
        super(th);
    }

    public MixinPrepareError(String str, Throwable th) {
        super(str, th);
    }
}
