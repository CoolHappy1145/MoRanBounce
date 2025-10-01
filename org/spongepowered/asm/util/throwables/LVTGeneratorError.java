package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.mixin.throwables.MixinError;

/* loaded from: L-out.jar:org/spongepowered/asm/util/throwables/LVTGeneratorError.class */
public class LVTGeneratorError extends MixinError {
    private static final long serialVersionUID = 1;

    public LVTGeneratorError(String str) {
        super(str);
    }

    public LVTGeneratorError(String str, Throwable th) {
        super(str, th);
    }
}
