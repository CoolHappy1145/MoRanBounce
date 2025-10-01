package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.throwables.MixinError;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/throwables/MixinTransformerError.class */
public class MixinTransformerError extends MixinError {
    private static final long serialVersionUID = 1;

    public MixinTransformerError(String str) {
        super(str);
    }

    public MixinTransformerError(Throwable th) {
        super(th);
    }

    public MixinTransformerError(String str, Throwable th) {
        super(str, th);
    }
}
