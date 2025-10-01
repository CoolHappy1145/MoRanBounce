package org.spongepowered.asm.mixin.injection.callback;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/callback/Cancellable.class */
public interface Cancellable {
    boolean isCancellable();

    boolean isCancelled();

    void cancel();
}
