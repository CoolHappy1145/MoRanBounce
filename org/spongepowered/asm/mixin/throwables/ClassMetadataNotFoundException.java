package org.spongepowered.asm.mixin.throwables;

import org.spongepowered.asm.mixin.transformer.ActivityStack;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/throwables/ClassMetadataNotFoundException.class */
public class ClassMetadataNotFoundException extends MixinException {
    private static final long serialVersionUID = 1;

    public ClassMetadataNotFoundException(String str) {
        super(str);
    }

    public ClassMetadataNotFoundException(String str, ActivityStack activityStack) {
        super(str, activityStack);
    }

    public ClassMetadataNotFoundException(Throwable th) {
        super(th);
    }

    public ClassMetadataNotFoundException(Throwable th, ActivityStack activityStack) {
        super(th, activityStack);
    }

    public ClassMetadataNotFoundException(String str, Throwable th) {
        super(str, th);
    }

    public ClassMetadataNotFoundException(String str, Throwable th, ActivityStack activityStack) {
        super(str, th, activityStack);
    }
}
