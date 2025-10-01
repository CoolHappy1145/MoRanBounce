package org.spongepowered.asm.util.throwables;

/* loaded from: L-out.jar:org/spongepowered/asm/util/throwables/InvalidConstraintException.class */
public class InvalidConstraintException extends IllegalArgumentException {
    private static final long serialVersionUID = 1;

    public InvalidConstraintException() {
    }

    public InvalidConstraintException(String str) {
        super(str);
    }

    public InvalidConstraintException(Throwable th) {
        super(th);
    }

    public InvalidConstraintException(String str, Throwable th) {
        super(str, th);
    }
}
