package org.spongepowered.asm.mixin.throwables;

import org.spongepowered.asm.mixin.transformer.ActivityStack;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/throwables/MixinException.class */
public class MixinException extends RuntimeException {
    private static final long serialVersionUID = 1;
    private String activityDescriptor;

    public MixinException(String str) {
        super(str);
    }

    public MixinException(String str, ActivityStack activityStack) {
        super(str);
        this.activityDescriptor = activityStack != null ? activityStack.toString() : null;
    }

    public MixinException(Throwable th) {
        super(th);
    }

    public MixinException(Throwable th, ActivityStack activityStack) {
        super(th);
        this.activityDescriptor = activityStack != null ? activityStack.toString() : null;
    }

    public MixinException(String str, Throwable th) {
        super(str, th);
    }

    public MixinException(String str, Throwable th, ActivityStack activityStack) {
        super(str, th);
        this.activityDescriptor = activityStack != null ? activityStack.toString() : null;
    }

    public void prepend(ActivityStack activityStack) {
        String string = activityStack.toString();
        this.activityDescriptor = this.activityDescriptor != null ? string + ActivityStack.GLUE_STRING + this.activityDescriptor : ActivityStack.GLUE_STRING + string;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        String message = super.getMessage();
        return this.activityDescriptor != null ? message + " [" + this.activityDescriptor + "]" : message;
    }
}
