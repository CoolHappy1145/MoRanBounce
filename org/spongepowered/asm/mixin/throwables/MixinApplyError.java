package org.spongepowered.asm.mixin.throwables;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/throwables/MixinApplyError.class */
public class MixinApplyError extends MixinError {
    private static final long serialVersionUID = 1;

    public MixinApplyError(String str) {
        super(str);
    }

    public MixinApplyError(Throwable th) {
        super(th);
    }

    public MixinApplyError(String str, Throwable th) {
        super(str, th);
    }
}
