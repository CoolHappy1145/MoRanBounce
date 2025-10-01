package org.spongepowered.asm.mixin.injection.struct;

import org.spongepowered.asm.mixin.injection.selectors.InvalidSelectorException;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/InvalidMemberDescriptorException.class */
public class InvalidMemberDescriptorException extends InvalidSelectorException {
    private static final long serialVersionUID = 1;

    public InvalidMemberDescriptorException(String str) {
        super(str);
    }

    public InvalidMemberDescriptorException(Throwable th) {
        super(th);
    }

    public InvalidMemberDescriptorException(String str, Throwable th) {
        super(str, th);
    }
}
