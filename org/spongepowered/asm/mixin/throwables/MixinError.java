package org.spongepowered.asm.mixin.throwables;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/throwables/MixinError.class */
public class MixinError extends Error {
    private static final long serialVersionUID = 1;

    public MixinError() {
    }

    public MixinError(String str) {
        super(str);
    }

    public MixinError(Throwable th) {
        super(th);
    }

    public MixinError(String str, Throwable th) {
        super(str, th);
    }
}
