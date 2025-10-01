package jdk.internal.dynalink.linker;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.lang.invoke.WrongMethodTypeException;
import java.util.List;
import java.util.Objects;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.support.Guards;
import jdk.nashorn.internal.lookup.Lookup;

/* loaded from: L-out.jar:jdk/internal/dynalink/linker/GuardedInvocation.class */
public class GuardedInvocation {
    private final MethodHandle invocation;
    private final MethodHandle guard;
    private final Class exception;
    private final SwitchPoint[] switchPoints;

    public GuardedInvocation(MethodHandle methodHandle) {
        this(methodHandle, (MethodHandle) null, (SwitchPoint) null, (Class) null);
    }

    public GuardedInvocation(MethodHandle methodHandle, MethodHandle methodHandle2) {
        this(methodHandle, methodHandle2, (SwitchPoint) null, (Class) null);
    }

    public GuardedInvocation(MethodHandle methodHandle, SwitchPoint switchPoint) {
        this(methodHandle, (MethodHandle) null, switchPoint, (Class) null);
    }

    public GuardedInvocation(MethodHandle methodHandle, MethodHandle methodHandle2, SwitchPoint switchPoint) {
        this(methodHandle, methodHandle2, switchPoint, (Class) null);
    }

    public GuardedInvocation(MethodHandle methodHandle, MethodHandle methodHandle2, SwitchPoint switchPoint, Class cls) {
        this.invocation = (MethodHandle) Objects.requireNonNull(methodHandle);
        this.guard = methodHandle2;
        this.switchPoints = switchPoint == null ? null : new SwitchPoint[]{switchPoint};
        this.exception = cls;
    }

    public GuardedInvocation(MethodHandle methodHandle, MethodHandle methodHandle2, SwitchPoint[] switchPointArr, Class cls) {
        this.invocation = (MethodHandle) Objects.requireNonNull(methodHandle);
        this.guard = methodHandle2;
        this.switchPoints = switchPointArr == null ? null : (SwitchPoint[]) switchPointArr.clone();
        this.exception = cls;
    }

    public MethodHandle getInvocation() {
        return this.invocation;
    }

    public MethodHandle getGuard() {
        return this.guard;
    }

    public SwitchPoint[] getSwitchPoints() {
        if (this.switchPoints == null) {
            return null;
        }
        return (SwitchPoint[]) this.switchPoints.clone();
    }

    public Class getException() {
        return this.exception;
    }

    public boolean hasBeenInvalidated() {
        if (this.switchPoints == null) {
            return false;
        }
        for (SwitchPoint switchPoint : this.switchPoints) {
            if (switchPoint.hasBeenInvalidated()) {
                return true;
            }
        }
        return false;
    }

    public void assertType(MethodType methodType) {
        assertType(this.invocation, methodType);
        if (this.guard != null) {
            assertType(this.guard, methodType.changeReturnType(Boolean.TYPE));
        }
    }

    public GuardedInvocation replaceMethods(MethodHandle methodHandle, MethodHandle methodHandle2) {
        return new GuardedInvocation(methodHandle, methodHandle2, this.switchPoints, this.exception);
    }

    public GuardedInvocation addSwitchPoint(SwitchPoint switchPoint) {
        SwitchPoint[] switchPointArr;
        if (switchPoint == null) {
            return this;
        }
        if (this.switchPoints != null) {
            switchPointArr = new SwitchPoint[this.switchPoints.length + 1];
            System.arraycopy(this.switchPoints, 0, switchPointArr, 0, this.switchPoints.length);
            switchPointArr[this.switchPoints.length] = switchPoint;
        } else {
            switchPointArr = new SwitchPoint[]{switchPoint};
        }
        return new GuardedInvocation(this.invocation, this.guard, switchPointArr, this.exception);
    }

    private GuardedInvocation replaceMethodsOrThis(MethodHandle methodHandle, MethodHandle methodHandle2) {
        if (methodHandle == this.invocation && methodHandle2 == this.guard) {
            return this;
        }
        return replaceMethods(methodHandle, methodHandle2);
    }

    public GuardedInvocation asType(MethodType methodType) {
        return replaceMethodsOrThis(this.invocation.asType(methodType), this.guard == null ? null : Guards.asType(this.guard, methodType));
    }

    public GuardedInvocation asType(LinkerServices linkerServices, MethodType methodType) {
        return replaceMethodsOrThis(linkerServices.asType(this.invocation, methodType), this.guard == null ? null : Guards.asType(linkerServices, this.guard, methodType));
    }

    public GuardedInvocation asTypeSafeReturn(LinkerServices linkerServices, MethodType methodType) {
        return replaceMethodsOrThis(linkerServices.asTypeLosslessReturn(this.invocation, methodType), this.guard == null ? null : Guards.asType(linkerServices, this.guard, methodType));
    }

    public GuardedInvocation asType(CallSiteDescriptor callSiteDescriptor) {
        return asType(callSiteDescriptor.getMethodType());
    }

    public GuardedInvocation filterArguments(int i, MethodHandle[] methodHandleArr) {
        return replaceMethods(MethodHandles.filterArguments(this.invocation, i, methodHandleArr), this.guard == null ? null : MethodHandles.filterArguments(this.guard, i, methodHandleArr));
    }

    public GuardedInvocation dropArguments(int i, List list) {
        return replaceMethods(MethodHandles.dropArguments(this.invocation, i, (List<Class<?>>) list), this.guard == null ? null : MethodHandles.dropArguments(this.guard, i, (List<Class<?>>) list));
    }

    public GuardedInvocation dropArguments(int i, Class[] clsArr) {
        return replaceMethods(MethodHandles.dropArguments(this.invocation, i, (Class<?>[]) clsArr), this.guard == null ? null : MethodHandles.dropArguments(this.guard, i, (Class<?>[]) clsArr));
    }

    public MethodHandle compose(MethodHandle methodHandle) {
        return compose(methodHandle, methodHandle, methodHandle);
    }

    public MethodHandle compose(MethodHandle methodHandle, MethodHandle methodHandle2, MethodHandle methodHandle3) {
        MethodHandle methodHandleGuardWithTest = this.guard == null ? this.invocation : MethodHandles.guardWithTest(this.guard, this.invocation, methodHandle);
        MethodHandle methodHandleCatchException = this.exception == null ? methodHandleGuardWithTest : Lookup.f31MH.catchException(methodHandleGuardWithTest, this.exception, MethodHandles.dropArguments(methodHandle3, 0, (Class<?>[]) new Class[]{this.exception}));
        if (this.switchPoints == null) {
            return methodHandleCatchException;
        }
        MethodHandle methodHandleGuardWithTest2 = methodHandleCatchException;
        for (SwitchPoint switchPoint : this.switchPoints) {
            methodHandleGuardWithTest2 = switchPoint.guardWithTest(methodHandleGuardWithTest2, methodHandle2);
        }
        return methodHandleGuardWithTest2;
    }

    private static void assertType(MethodHandle methodHandle, MethodType methodType) {
        if (!methodHandle.type().equals(methodType)) {
            throw new WrongMethodTypeException("Expected type: " + methodType + " actual type: " + methodHandle.type());
        }
    }
}
