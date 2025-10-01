package org.spongepowered.asm.mixin.injection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({})
@Retention(RetentionPolicy.RUNTIME)
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/Constant.class */
public @interface Constant {
    boolean nullValue() default false;

    int intValue() default 0;

    float floatValue() default 0.0f;

    long longValue() default 0;

    double doubleValue() default 0.0d;

    String stringValue() default "";

    Class classValue() default Object.class;

    int ordinal() default -1;

    String slice() default "";

    Condition[] expandZeroConditions() default {};

    boolean log() default false;

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/Constant$Condition.class */
    public enum Condition {
        LESS_THAN_ZERO(new int[]{155, 156}),
        LESS_THAN_OR_EQUAL_TO_ZERO(new int[]{158, 157}),
        GREATER_THAN_OR_EQUAL_TO_ZERO(LESS_THAN_ZERO),
        GREATER_THAN_ZERO(LESS_THAN_OR_EQUAL_TO_ZERO);

        private final int[] opcodes;
        private final Condition equivalence;

        Condition(int[] iArr) {
            this(null, iArr);
        }

        Condition(Condition condition) {
            this(condition, condition.opcodes);
        }

        Condition(Condition condition, int[] iArr) {
            this.equivalence = condition != null ? condition : this;
            this.opcodes = iArr;
        }

        public Condition getEquivalentCondition() {
            return this.equivalence;
        }

        public int[] getOpcodes() {
            return this.opcodes;
        }
    }
}
