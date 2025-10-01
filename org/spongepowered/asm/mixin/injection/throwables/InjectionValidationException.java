package org.spongepowered.asm.mixin.injection.throwables;

import org.spongepowered.asm.mixin.injection.struct.InjectorGroupInfo;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/throwables/InjectionValidationException.class */
public class InjectionValidationException extends Exception {
    private static final long serialVersionUID = 1;
    private final InjectorGroupInfo group;

    public InjectionValidationException(InjectorGroupInfo injectorGroupInfo, String str) {
        super(str);
        this.group = injectorGroupInfo;
    }

    public InjectorGroupInfo getGroup() {
        return this.group;
    }
}
