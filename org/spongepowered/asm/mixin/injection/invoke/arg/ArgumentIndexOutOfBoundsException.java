package org.spongepowered.asm.mixin.injection.invoke.arg;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/arg/ArgumentIndexOutOfBoundsException.class */
public class ArgumentIndexOutOfBoundsException extends IndexOutOfBoundsException {
    private static final long serialVersionUID = 1;

    public ArgumentIndexOutOfBoundsException(int i) {
        super("Argument index is out of bounds: " + i);
    }
}
