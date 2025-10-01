package org.spongepowered.asm.mixin.injection.invoke.arg;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentCountException.class */
public class ArgumentCountException extends IllegalArgumentException {
    private static final long serialVersionUID = 1;

    public ArgumentCountException(int i, int i2, String str) {
        super("Invalid number of arguments for setAll, received " + i + " but expected " + i2 + ": " + str);
    }
}
