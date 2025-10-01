package org.spongepowered.asm.mixin.throwables;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/throwables/CompanionPluginError.class */
public class CompanionPluginError extends LinkageError {
    private static final long serialVersionUID = 1;

    public CompanionPluginError() {
    }

    public CompanionPluginError(String str) {
        super(str);
    }

    public CompanionPluginError(String str, Throwable th) {
        super(str, th);
    }
}
