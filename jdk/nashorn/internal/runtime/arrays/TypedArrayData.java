package jdk.nashorn.internal.runtime.arrays;

import java.lang.invoke.MethodHandle;
import java.nio.Buffer;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.lookup.Lookup;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/TypedArrayData.class */
public abstract class TypedArrayData extends ContinuousArrayData {

    /* renamed from: nb */
    protected final Buffer f56nb;

    protected abstract MethodHandle getGetElem();

    protected abstract MethodHandle getSetElem();

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ContinuousArrayData copy() {
        return copy();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData convert(Class cls) {
        return convert(cls);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData copy() {
        return copy();
    }

    protected TypedArrayData(Buffer buffer, int i) {
        super(i);
        this.f56nb = buffer;
    }

    public final int getElementLength() {
        return (int) length();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public TypedArrayData copy() {
        throw new UnsupportedOperationException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object[] asObjectArray() {
        throw new UnsupportedOperationException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftLeft(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shiftRight(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData shrink(long j) {
        throw new UnsupportedOperationException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public final boolean has(int i) {
        return 0 <= i && ((long) i) < length();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public TypedArrayData convert(Class cls) {
        throw new UnsupportedOperationException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public Object pop() {
        throw new UnsupportedOperationException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
    public ArrayData slice(long j, long j2) {
        throw new UnsupportedOperationException();
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public MethodHandle getElementGetter(Class cls, int i) {
        MethodHandle continuousElementGetter = getContinuousElementGetter(getClass(), getGetElem(), cls, i);
        if (continuousElementGetter != null) {
            return Lookup.filterReturnType(continuousElementGetter, cls);
        }
        return continuousElementGetter;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    public MethodHandle getElementSetter(Class cls) {
        return getContinuousElementSetter(getClass(), Lookup.filterArgumentType(getSetElem(), 2, cls), cls);
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
    protected MethodHandle getContinuousElementSetter(Class cls, MethodHandle methodHandle, Class cls2) {
        MethodHandle methodHandleFilterArgumentType = Lookup.filterArgumentType(methodHandle, 2, cls2);
        return Lookup.f31MH.asType(methodHandleFilterArgumentType, methodHandleFilterArgumentType.type().changeParameterType(0, (Class<?>) cls));
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public GuardedInvocation findFastGetIndexMethod(Class cls, CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        GuardedInvocation guardedInvocationFindFastGetIndexMethod = super.findFastGetIndexMethod(cls, callSiteDescriptor, linkRequest);
        if (guardedInvocationFindFastGetIndexMethod != null) {
            return guardedInvocationFindFastGetIndexMethod;
        }
        return null;
    }

    @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
    public GuardedInvocation findFastSetIndexMethod(Class cls, CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        GuardedInvocation guardedInvocationFindFastSetIndexMethod = super.findFastSetIndexMethod(cls, callSiteDescriptor, linkRequest);
        if (guardedInvocationFindFastSetIndexMethod != null) {
            return guardedInvocationFindFastSetIndexMethod;
        }
        return null;
    }
}
