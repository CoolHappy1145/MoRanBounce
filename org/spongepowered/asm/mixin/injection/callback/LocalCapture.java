package org.spongepowered.asm.mixin.injection.callback;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/callback/LocalCapture.class */
public enum LocalCapture {
    NO_CAPTURE(false, false),
    PRINT(false, true),
    CAPTURE_FAILSOFT,
    CAPTURE_FAILHARD,
    CAPTURE_FAILEXCEPTION;

    private final boolean captureLocals;
    private final boolean printLocals;

    LocalCapture() {
        this(true, false);
    }

    LocalCapture(boolean z, boolean z2) {
        this.captureLocals = z;
        this.printLocals = z2;
    }

    boolean isCaptureLocals() {
        return this.captureLocals;
    }

    boolean isPrintLocals() {
        return this.printLocals;
    }
}
