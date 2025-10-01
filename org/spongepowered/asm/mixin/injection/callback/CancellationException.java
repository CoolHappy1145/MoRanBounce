package org.spongepowered.asm.mixin.injection.callback;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/callback/CancellationException.class */
public class CancellationException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public CancellationException() {
    }

    public CancellationException(String str) {
        super(str);
    }

    public CancellationException(Throwable th) {
        super(th);
    }

    public CancellationException(String str, Throwable th) {
        super(str, th);
    }
}
