package jdk.nashorn.internal.runtime.arrays;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(name = "arrays")
/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/ContinuousArrayData.class */
public abstract class ContinuousArrayData extends ArrayData {
    protected static final MethodHandle FAST_ACCESS_GUARD = Lookup.f31MH.dropArguments(CompilerConstants.staticCall(MethodHandles.lookup(), ContinuousArrayData.class, "guard", Boolean.TYPE, new Class[]{Class.class, ScriptObject.class}).methodHandle(), 2, new Class[]{Integer.TYPE});

    public abstract MethodHandle getElementGetter(Class cls, int i);

    public abstract MethodHandle getElementSetter(Class cls);

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public abstract ContinuousArrayData copy();

    public abstract Class getElementType();

    public abstract Class getBoxedElementType();

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        return copy();
    }

    protected ContinuousArrayData(long j) {
        super(j);
    }

    public final boolean hasRoomFor(int i) {
        return has(i) || (((long) i) == length() && ensure((long) i) == this);
    }

    public boolean isEmpty() {
        return length() == 0;
    }

    protected final int throwHas(int i) {
        if (!has(i)) {
            throw new ClassCastException();
        }
        return i;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Type getOptimisticType() {
        return Type.typeFor((Class<?>) getElementType());
    }

    public ContinuousArrayData widest(ContinuousArrayData continuousArrayData) {
        Class<?> elementType = getElementType();
        return Type.widest(elementType, (Class<?>) continuousArrayData.getElementType()) == elementType ? this : continuousArrayData;
    }

    protected final MethodHandle getContinuousElementGetter(MethodHandle methodHandle, Class cls, int i) {
        return getContinuousElementGetter(getClass(), methodHandle, cls, i);
    }

    protected final MethodHandle getContinuousElementSetter(MethodHandle methodHandle, Class cls) {
        return getContinuousElementSetter(getClass(), methodHandle, cls);
    }

    protected MethodHandle getContinuousElementGetter(Class cls, MethodHandle methodHandle, Class cls2, int i) {
        boolean zIsValid = UnwarrantedOptimismException.isValid(i);
        int accessorTypeIndex = JSType.getAccessorTypeIndex(methodHandle.type().returnType());
        int accessorTypeIndex2 = JSType.getAccessorTypeIndex(cls2);
        MethodHandle methodHandleInsertArguments = methodHandle;
        if (zIsValid && accessorTypeIndex2 < accessorTypeIndex) {
            methodHandleInsertArguments = Lookup.f31MH.insertArguments(ArrayData.THROW_UNWARRANTED.methodHandle(), 1, new Object[]{Integer.valueOf(i)});
        }
        MethodHandle methodHandleAsType = Lookup.f31MH.asType(methodHandleInsertArguments, methodHandleInsertArguments.type().changeReturnType((Class<?>) cls2).changeParameterType(0, (Class<?>) cls));
        if (!zIsValid) {
            return Lookup.filterReturnType(methodHandleAsType, cls2);
        }
        return methodHandleAsType;
    }

    protected MethodHandle getContinuousElementSetter(Class cls, MethodHandle methodHandle, Class cls2) {
        return Lookup.f31MH.asType(methodHandle, methodHandle.type().changeParameterType(2, (Class<?>) cls2).changeParameterType(0, (Class<?>) cls));
    }

    private static final boolean guard(Class cls, ScriptObject scriptObject) {
        if (scriptObject != null && scriptObject.getArray().getClass() == cls) {
            return true;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public GuardedInvocation findFastGetIndexMethod(Class cls, CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        MethodType methodType = callSiteDescriptor.getMethodType();
        Class<?> clsParameterType = methodType.parameterType(1);
        Class<?> clsReturnType = methodType.returnType();
        if (ContinuousArrayData.class.isAssignableFrom(cls) && clsParameterType == Integer.TYPE) {
            Object[] arguments = linkRequest.getArguments();
            if (has(((Integer) arguments[arguments.length - 1]).intValue())) {
                MethodHandle methodHandle = ScriptObject.GET_ARRAY.methodHandle();
                MethodHandle elementGetter = getElementGetter(clsReturnType, NashornCallSiteDescriptor.isOptimistic(callSiteDescriptor) ? NashornCallSiteDescriptor.getProgramPoint(callSiteDescriptor) : -1);
                if (elementGetter != null) {
                    return new GuardedInvocation(Lookup.f31MH.filterArguments(elementGetter, 0, new MethodHandle[]{Lookup.f31MH.asType(methodHandle, methodHandle.type().changeReturnType((Class<?>) cls))}), Lookup.f31MH.insertArguments(FAST_ACCESS_GUARD, 0, new Object[]{cls}), (SwitchPoint) null, ClassCastException.class);
                }
                return null;
            }
            return null;
        }
        return null;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public GuardedInvocation findFastSetIndexMethod(Class cls, CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        MethodHandle elementSetter;
        MethodType methodType = callSiteDescriptor.getMethodType();
        Class<?> clsParameterType = methodType.parameterType(1);
        Class<?> clsParameterType2 = methodType.parameterType(2);
        if (ContinuousArrayData.class.isAssignableFrom(cls) && clsParameterType == Integer.TYPE) {
            Object[] arguments = linkRequest.getArguments();
            if (hasRoomFor(((Integer) arguments[arguments.length - 2]).intValue()) && (elementSetter = getElementSetter(clsParameterType2)) != null) {
                MethodHandle methodHandle = ScriptObject.GET_ARRAY.methodHandle();
                return new GuardedInvocation(Lookup.f31MH.filterArguments(elementSetter, 0, new MethodHandle[]{Lookup.f31MH.asType(methodHandle, methodHandle.type().changeReturnType(getClass()))}), Lookup.f31MH.insertArguments(FAST_ACCESS_GUARD, 0, new Object[]{cls}), (SwitchPoint) null, ClassCastException.class);
            }
            return null;
        }
        return null;
    }

    public double fastPush(int i) {
        throw new ClassCastException(String.valueOf(getClass()));
    }

    public double fastPush(long j) {
        throw new ClassCastException(String.valueOf(getClass()));
    }

    public double fastPush(double d) {
        throw new ClassCastException(String.valueOf(getClass()));
    }

    public double fastPush(Object obj) {
        throw new ClassCastException(String.valueOf(getClass()));
    }

    public int fastPopInt() {
        throw new ClassCastException(String.valueOf(getClass()));
    }

    public double fastPopDouble() {
        throw new ClassCastException(String.valueOf(getClass()));
    }

    public Object fastPopObject() {
        throw new ClassCastException(String.valueOf(getClass()));
    }

    public ContinuousArrayData fastConcat(ContinuousArrayData continuousArrayData) {
        throw new ClassCastException(String.valueOf(getClass()) + " != " + String.valueOf(continuousArrayData.getClass()));
    }
}
