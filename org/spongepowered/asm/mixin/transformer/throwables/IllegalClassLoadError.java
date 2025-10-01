package org.spongepowered.asm.mixin.transformer.throwables;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/throwables/IllegalClassLoadError.class */
public class IllegalClassLoadError extends MixinTransformerError {
    private static final long serialVersionUID = 1;

    public IllegalClassLoadError(String str) {
        super(str);
    }

    public IllegalClassLoadError(Throwable th) {
        super(th);
    }

    public IllegalClassLoadError(String str, Throwable th) {
        super(str, th);
    }
}
