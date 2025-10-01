package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import jdk.internal.dynalink.linker.GuardedInvocation;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/GuardedInvocationComponent.class */
class GuardedInvocationComponent {
    private final GuardedInvocation guardedInvocation;
    private final Validator validator;

    /* loaded from: L-out.jar:jdk/internal/dynalink/beans/GuardedInvocationComponent$ValidationType.class */
    enum ValidationType {
        NONE,
        INSTANCE_OF,
        EXACT_CLASS,
        IS_ARRAY
    }

    GuardedInvocationComponent(MethodHandle methodHandle) {
        this(methodHandle, (MethodHandle) null, ValidationType.NONE);
    }

    GuardedInvocationComponent(MethodHandle methodHandle, MethodHandle methodHandle2, ValidationType validationType) {
        this(methodHandle, methodHandle2, null, validationType);
    }

    GuardedInvocationComponent(MethodHandle methodHandle, MethodHandle methodHandle2, Class cls, ValidationType validationType) {
        this(methodHandle, methodHandle2, new Validator(cls, validationType));
    }

    GuardedInvocationComponent(GuardedInvocation guardedInvocation, Class cls, ValidationType validationType) {
        this(guardedInvocation, new Validator(cls, validationType));
    }

    GuardedInvocationComponent replaceInvocation(MethodHandle methodHandle) {
        return replaceInvocation(methodHandle, this.guardedInvocation.getGuard());
    }

    GuardedInvocationComponent replaceInvocation(MethodHandle methodHandle, MethodHandle methodHandle2) {
        return new GuardedInvocationComponent(this.guardedInvocation.replaceMethods(methodHandle, methodHandle2), this.validator);
    }

    private GuardedInvocationComponent(MethodHandle methodHandle, MethodHandle methodHandle2, Validator validator) {
        this(new GuardedInvocation(methodHandle, methodHandle2), validator);
    }

    private GuardedInvocationComponent(GuardedInvocation guardedInvocation, Validator validator) {
        this.guardedInvocation = guardedInvocation;
        this.validator = validator;
    }

    GuardedInvocation getGuardedInvocation() {
        return this.guardedInvocation;
    }

    Class getValidatorClass() {
        return this.validator.validatorClass;
    }

    ValidationType getValidationType() {
        return this.validator.validationType;
    }

    GuardedInvocationComponent compose(MethodHandle methodHandle, MethodHandle methodHandle2, Class cls, ValidationType validationType) {
        Validator validatorCompose = this.validator.compose(new Validator(cls, validationType));
        return new GuardedInvocationComponent(methodHandle, validatorCompose == this.validator ? this.guardedInvocation.getGuard() : methodHandle2, validatorCompose);
    }

    /* loaded from: L-out.jar:jdk/internal/dynalink/beans/GuardedInvocationComponent$Validator.class */
    private static class Validator {
        final Class validatorClass;
        final ValidationType validationType;

        Validator(Class cls, ValidationType validationType) {
            this.validatorClass = cls;
            this.validationType = validationType;
        }

        Validator compose(Validator validator) {
            if (validator.validationType == ValidationType.NONE) {
                return this;
            }
            switch (C00121.f3x6625531f[this.validationType.ordinal()]) {
                case 1:
                    switch (C00121.f3x6625531f[validator.validationType.ordinal()]) {
                        case 1:
                            if (isAssignableFrom(validator)) {
                                return validator;
                            }
                            if (validator.isAssignableFrom(this)) {
                                return this;
                            }
                            break;
                        case 2:
                            if (isAssignableFrom(validator)) {
                                return validator;
                            }
                            break;
                        case 3:
                            if (this.validatorClass.isArray()) {
                                return this;
                            }
                            break;
                        default:
                            throw new AssertionError();
                    }
                case 2:
                    switch (C00121.f3x6625531f[validator.validationType.ordinal()]) {
                        case 1:
                            if (validator.isAssignableFrom(this)) {
                                return this;
                            }
                            break;
                        case 2:
                            if (this.validatorClass == validator.validatorClass) {
                                return this;
                            }
                            break;
                        case 3:
                            if (this.validatorClass.isArray()) {
                                return this;
                            }
                            break;
                        default:
                            throw new AssertionError();
                    }
                case 3:
                    switch (C00121.f3x6625531f[validator.validationType.ordinal()]) {
                        case 1:
                        case 2:
                            if (validator.validatorClass.isArray()) {
                                return validator;
                            }
                            break;
                        case 3:
                            return this;
                        default:
                            throw new AssertionError();
                    }
                case 4:
                    return validator;
                default:
                    throw new AssertionError();
            }
            throw new AssertionError("Incompatible composition " + this + " vs " + validator);
        }

        private boolean isAssignableFrom(Validator validator) {
            return this.validatorClass.isAssignableFrom(validator.validatorClass);
        }

        public String toString() {
            return "Validator[" + this.validationType + (this.validatorClass == null ? "" : " " + this.validatorClass.getName()) + "]";
        }
    }

    /* renamed from: jdk.internal.dynalink.beans.GuardedInvocationComponent$1 */
    /* loaded from: L-out.jar:jdk/internal/dynalink/beans/GuardedInvocationComponent$1.class */
    static /* synthetic */ class C00121 {

        /* renamed from: $SwitchMap$jdk$internal$dynalink$beans$GuardedInvocationComponent$ValidationType */
        static final int[] f3x6625531f = new int[ValidationType.values().length];

        static {
            try {
                f3x6625531f[ValidationType.INSTANCE_OF.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f3x6625531f[ValidationType.EXACT_CLASS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f3x6625531f[ValidationType.IS_ARRAY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f3x6625531f[ValidationType.NONE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }
}
