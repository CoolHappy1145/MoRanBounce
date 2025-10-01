package org.spongepowered.asm.util.throwables;

import org.spongepowered.asm.util.ConstraintParser;

/* loaded from: L-out.jar:org/spongepowered/asm/util/throwables/ConstraintViolationException.class */
public class ConstraintViolationException extends Exception {
    private static final String MISSING_VALUE = "UNRESOLVED";
    private static final long serialVersionUID = 1;
    private final ConstraintParser.Constraint constraint;
    private final String badValue;

    public ConstraintViolationException(ConstraintParser.Constraint constraint) {
        this.constraint = constraint;
        this.badValue = MISSING_VALUE;
    }

    public ConstraintViolationException(ConstraintParser.Constraint constraint, int i) {
        this.constraint = constraint;
        this.badValue = String.valueOf(i);
    }

    public ConstraintViolationException(String str, ConstraintParser.Constraint constraint) {
        super(str);
        this.constraint = constraint;
        this.badValue = MISSING_VALUE;
    }

    public ConstraintViolationException(String str, ConstraintParser.Constraint constraint, int i) {
        super(str);
        this.constraint = constraint;
        this.badValue = String.valueOf(i);
    }

    public ConstraintViolationException(Throwable th, ConstraintParser.Constraint constraint) {
        super(th);
        this.constraint = constraint;
        this.badValue = MISSING_VALUE;
    }

    public ConstraintViolationException(Throwable th, ConstraintParser.Constraint constraint, int i) {
        super(th);
        this.constraint = constraint;
        this.badValue = String.valueOf(i);
    }

    public ConstraintViolationException(String str, Throwable th, ConstraintParser.Constraint constraint) {
        super(str, th);
        this.constraint = constraint;
        this.badValue = MISSING_VALUE;
    }

    public ConstraintViolationException(String str, Throwable th, ConstraintParser.Constraint constraint, int i) {
        super(str, th);
        this.constraint = constraint;
        this.badValue = String.valueOf(i);
    }

    public ConstraintParser.Constraint getConstraint() {
        return this.constraint;
    }

    public String getBadValue() {
        return this.badValue;
    }
}
