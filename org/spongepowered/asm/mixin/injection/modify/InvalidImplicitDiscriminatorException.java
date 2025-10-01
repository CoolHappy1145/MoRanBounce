package org.spongepowered.asm.mixin.injection.modify;

import org.spongepowered.asm.mixin.throwables.MixinException;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/modify/InvalidImplicitDiscriminatorException.class */
public class InvalidImplicitDiscriminatorException extends MixinException {
    private static final long serialVersionUID = 1;

    public InvalidImplicitDiscriminatorException(String str) {
        super(str);
    }

    public InvalidImplicitDiscriminatorException(String str, Throwable th) {
        super(str, th);
    }
}
