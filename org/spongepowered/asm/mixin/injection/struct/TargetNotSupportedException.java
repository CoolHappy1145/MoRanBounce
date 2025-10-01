package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.injection.selectors.InvalidSelectorException;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/TargetNotSupportedException.class */
public class TargetNotSupportedException extends InvalidSelectorException {
    private static final long serialVersionUID = 1;

    public TargetNotSupportedException(String str) {
        super(str);
    }

    public TargetNotSupportedException(Throwable th) {
        super(th);
    }

    public TargetNotSupportedException(String str, Throwable th) {
        super(str, th);
    }
}
