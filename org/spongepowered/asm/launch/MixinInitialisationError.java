package org.spongepowered.asm.launch;

import org.spongepowered.asm.mixin.throwables.MixinError;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/MixinInitialisationError.class */
public class MixinInitialisationError extends MixinError {
    private static final long serialVersionUID = 1;

    public MixinInitialisationError() {
    }

    public MixinInitialisationError(String str) {
        super(str);
    }

    public MixinInitialisationError(Throwable th) {
        super(th);
    }

    public MixinInitialisationError(String str, Throwable th) {
        super(str, th);
    }
}
