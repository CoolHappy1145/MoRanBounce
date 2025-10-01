package org.spongepowered.asm.mixin.injection.selectors;

import org.spongepowered.asm.mixin.throwables.MixinException;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/selectors/InvalidSelectorException.class */
public class InvalidSelectorException extends MixinException {
    private static final long serialVersionUID = 1;

    public InvalidSelectorException(String str) {
        super(str);
    }

    public InvalidSelectorException(Throwable th) {
        super(th);
    }

    public InvalidSelectorException(String str, Throwable th) {
        super(str, th);
    }
}
