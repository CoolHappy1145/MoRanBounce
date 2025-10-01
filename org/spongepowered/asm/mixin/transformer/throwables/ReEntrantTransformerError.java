package org.spongepowered.asm.mixin.transformer.throwables;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/throwables/ReEntrantTransformerError.class */
public class ReEntrantTransformerError extends MixinTransformerError {
    private static final long serialVersionUID = 7073583236491579255L;

    public ReEntrantTransformerError(String str) {
        super(str);
    }

    public ReEntrantTransformerError(Throwable th) {
        super(th);
    }

    public ReEntrantTransformerError(String str, Throwable th) {
        super(str, th);
    }
}
