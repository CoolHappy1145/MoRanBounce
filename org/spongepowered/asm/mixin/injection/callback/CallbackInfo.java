package org.spongepowered.asm.mixin.injection.callback;

import org.objectweb.asm.Type;
import org.spongepowered.asm.util.Constants;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/callback/CallbackInfo.class */
public class CallbackInfo implements Cancellable {
    private final String name;
    private final boolean cancellable;
    private boolean cancelled;

    public CallbackInfo(String str, boolean z) {
        this.name = str;
        this.cancellable = z;
    }

    public String getId() {
        return this.name;
    }

    public String toString() {
        return String.format("CallbackInfo[TYPE=%s,NAME=%s,CANCELLABLE=%s]", getClass().getSimpleName(), this.name, Boolean.valueOf(this.cancellable));
    }

    @Override // org.spongepowered.asm.mixin.injection.callback.Cancellable
    public final boolean isCancellable() {
        return this.cancellable;
    }

    @Override // org.spongepowered.asm.mixin.injection.callback.Cancellable
    public final boolean isCancelled() {
        return this.cancelled;
    }

    @Override // org.spongepowered.asm.mixin.injection.callback.Cancellable
    public void cancel() {
        if (!this.cancellable) {
            throw new CancellationException(String.format("The call %s is not cancellable.", this.name));
        }
        this.cancelled = true;
    }

    static String getCallInfoClassName() {
        return CallbackInfo.class.getName();
    }

    public static String getCallInfoClassName(Type type) {
        return (type.equals(Type.VOID_TYPE) ? CallbackInfo.class.getName() : CallbackInfoReturnable.class.getName()).replace('.', '/');
    }

    static String getConstructorDescriptor(Type type) {
        if (type.equals(Type.VOID_TYPE)) {
            return getConstructorDescriptor();
        }
        if (type.getSort() == 10 || type.getSort() == 9) {
            return String.format("(%sZ%s)V", Constants.STRING_DESC, Constants.OBJECT_DESC);
        }
        return String.format("(%sZ%s)V", Constants.STRING_DESC, type.getDescriptor());
    }

    static String getConstructorDescriptor() {
        return String.format("(%sZ)V", Constants.STRING_DESC);
    }
}
