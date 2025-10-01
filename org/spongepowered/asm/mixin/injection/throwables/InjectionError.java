package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.throwables.MixinError;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/throwables/InjectionError.class */
public class InjectionError extends MixinError {
    private static final long serialVersionUID = 1;

    public InjectionError() {
    }

    public InjectionError(String str) {
        super(str);
    }

    public InjectionError(Throwable th) {
        super(th);
    }

    public InjectionError(String str, Throwable th) {
        super(str, th);
    }
}
