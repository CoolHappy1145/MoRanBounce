package org.spongepowered.asm.mixin.transformer.throwables;

import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.ActivityStack;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/throwables/MixinPreProcessorException.class */
public class MixinPreProcessorException extends MixinException {
    private static final long serialVersionUID = 1;

    public MixinPreProcessorException(String str, ActivityStack activityStack) {
        super(str, activityStack);
    }

    public MixinPreProcessorException(String str, Throwable th, ActivityStack activityStack) {
        super(str, th, activityStack);
    }
}
