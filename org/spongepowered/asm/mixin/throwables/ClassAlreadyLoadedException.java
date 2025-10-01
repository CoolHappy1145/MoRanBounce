package org.spongepowered.asm.mixin.throwables;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/throwables/ClassAlreadyLoadedException.class */
public class ClassAlreadyLoadedException extends MixinException {
    private static final long serialVersionUID = 1;

    public ClassAlreadyLoadedException(String str) {
        super(str);
    }

    public ClassAlreadyLoadedException(Throwable th) {
        super(th);
    }

    public ClassAlreadyLoadedException(String str, Throwable th) {
        super(str, th);
    }
}
