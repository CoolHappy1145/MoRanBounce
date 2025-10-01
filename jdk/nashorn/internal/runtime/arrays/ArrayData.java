package jdk.nashorn.internal.runtime.arrays;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;

/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/ArrayData.class */
public abstract class ArrayData {
    protected static final int CHUNK_SIZE = 32;
    public static final ArrayData EMPTY_ARRAY;
    private long length;
    protected static final CompilerConstants.Call THROW_UNWARRANTED;
    static final /* synthetic */ boolean $assertionsDisabled;

    public abstract ArrayData copy();

    public abstract Object[] asObjectArray();

    /*  JADX ERROR: Failed to decode insn: 0x0007: MOVE_MULTI
        java.lang.ArrayIndexOutOfBoundsException: arraycopy: source index -1 out of bounds for object array[6]
        	at java.base/java.lang.System.arraycopy(Native Method)
        	at jadx.plugins.input.java.data.code.StackState.insert(StackState.java:52)
        	at jadx.plugins.input.java.data.code.CodeDecodeState.insert(CodeDecodeState.java:137)
        	at jadx.plugins.input.java.data.code.JavaInsnsRegister.dup2x1(JavaInsnsRegister.java:313)
        	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
        	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
        	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
        	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
        	at jadx.core.ProcessClass.process(ProcessClass.java:69)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
        	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
        	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
        */
    protected final long increaseLength() {
        /*
            r6 = this;
            r0 = r6
            r1 = r0
            long r1 = r1.length
            r2 = 1
            long r1 = r1 + r2
            // decode failed: arraycopy: source index -1 out of bounds for object array[6]
            r0.length = r1
            return r-1
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.runtime.arrays.ArrayData.increaseLength():long");
    }

    /*  JADX ERROR: Failed to decode insn: 0x0007: MOVE_MULTI
        java.lang.ArrayIndexOutOfBoundsException: arraycopy: source index -1 out of bounds for object array[6]
        	at java.base/java.lang.System.arraycopy(Native Method)
        	at jadx.plugins.input.java.data.code.StackState.insert(StackState.java:52)
        	at jadx.plugins.input.java.data.code.CodeDecodeState.insert(CodeDecodeState.java:137)
        	at jadx.plugins.input.java.data.code.JavaInsnsRegister.dup2x1(JavaInsnsRegister.java:313)
        	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
        	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
        	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
        	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
        	at jadx.core.ProcessClass.process(ProcessClass.java:69)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
        	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
        	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
        */
    protected final long decreaseLength() {
        /*
            r6 = this;
            r0 = r6
            r1 = r0
            long r1 = r1.length
            r2 = 1
            long r1 = r1 - r2
            // decode failed: arraycopy: source index -1 out of bounds for object array[6]
            r0.length = r1
            return r-1
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.runtime.arrays.ArrayData.decreaseLength():long");
    }

    public abstract ArrayData shiftLeft(int i);

    public abstract ArrayData shiftRight(int i);

    public abstract ArrayData ensure(long j);

    public abstract ArrayData shrink(long j);

    public abstract ArrayData set(int i, Object obj, boolean z);

    public abstract ArrayData set(int i, int i2, boolean z);

    public abstract ArrayData set(int i, double d, boolean z);

    public abstract int getInt(int i);

    public abstract double getDouble(int i);

    public abstract Object getObject(int i);

    public abstract boolean has(int i);

    public abstract ArrayData delete(int i);

    public abstract ArrayData delete(long j, long j2);

    public abstract ArrayData convert(Class<?> cls);

    public abstract Object pop();

    public abstract ArrayData slice(long j, long j2);

    static {
        $assertionsDisabled = !ArrayData.class.desiredAssertionStatus();
        EMPTY_ARRAY = new UntouchedArrayData(null);
        THROW_UNWARRANTED = CompilerConstants.staticCall(MethodHandles.lookup(), ArrayData.class, "throwUnwarranted", Void.TYPE, new Class[]{ArrayData.class, Integer.TYPE, Integer.TYPE});
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/arrays/ArrayData$UntouchedArrayData.class */
    private static class UntouchedArrayData extends ContinuousArrayData {
        static final boolean $assertionsDisabled;

        @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData copy() {
            return copy();
        }

        UntouchedArrayData(C02451 c02451) {
            this();
        }

        static {
            $assertionsDisabled = !ArrayData.class.desiredAssertionStatus();
        }

        private UntouchedArrayData() {
            super(0L);
        }

        private ArrayData toRealArrayData() {
            return new IntArrayData(0);
        }

        private ArrayData toRealArrayData(int i) {
            return new DeletedRangeArrayFilter(new IntArrayData(i + 1), 0L, i);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData, jdk.nashorn.internal.runtime.arrays.ArrayData
        public ContinuousArrayData copy() {
            if ($assertionsDisabled || length() == 0) {
                return this;
            }
            throw new AssertionError();
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public Object asArrayOfType(Class cls) {
            return Array.newInstance((Class<?>) cls, 0);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public Object[] asObjectArray() {
            return ScriptRuntime.EMPTY_ARRAY;
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData ensure(long j) {
            if (!$assertionsDisabled && j < 0) {
                throw new AssertionError();
            }
            if (j >= 131072) {
                return new SparseArrayData(this, j + 1);
            }
            return toRealArrayData((int) j);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData convert(Class cls) {
            return toRealArrayData().convert(cls);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData delete(int i) {
            return new DeletedRangeArrayFilter(this, i, i);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData delete(long j, long j2) {
            return new DeletedRangeArrayFilter(this, j, j2);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData set(int i, Object obj, boolean z) {
            return toRealArrayData(i).set(i, obj, z);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData set(int i, int i2, boolean z) {
            return toRealArrayData(i).set(i, i2, z);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData set(int i, double d, boolean z) {
            return toRealArrayData(i).set(i, d, z);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public int getInt(int i) {
            throw new ArrayIndexOutOfBoundsException(i);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public double getDouble(int i) {
            throw new ArrayIndexOutOfBoundsException(i);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public Object getObject(int i) {
            throw new ArrayIndexOutOfBoundsException(i);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public Object pop() {
            return ScriptRuntime.UNDEFINED;
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData push(boolean z, Object obj) {
            return toRealArrayData().push(z, obj);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
        public ContinuousArrayData fastConcat(ContinuousArrayData continuousArrayData) {
            return continuousArrayData.copy();
        }

        public String toString() {
            return getClass().getSimpleName();
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
        public Class getElementType() {
            return Integer.TYPE;
        }
    }

    protected ArrayData(long length) {
        this.length = length;
    }

    public static ArrayData initialArray() {
        return new IntArrayData();
    }

    protected static void throwUnwarranted(ArrayData data, int programPoint, int index) {
        throw new UnwarrantedOptimismException(data.getObject(index), programPoint);
    }

    protected static int alignUp(int size) {
        return ((size + 32) - 1) & (-32);
    }

    public static ArrayData allocate(long length) {
        if (length == 0) {
            return new IntArrayData();
        }
        if (length >= 131072) {
            return new SparseArrayData(EMPTY_ARRAY, length);
        }
        return new DeletedRangeArrayFilter(new IntArrayData((int) length), 0L, length - 1);
    }

    public static ArrayData allocate(Object array) {
        Class<?> clazz = array.getClass();
        if (clazz == int[].class) {
            return new IntArrayData((int[]) array, ((int[]) array).length);
        }
        if (clazz == double[].class) {
            return new NumberArrayData((double[]) array, ((double[]) array).length);
        }
        return new ObjectArrayData((Object[]) array, ((Object[]) array).length);
    }

    public static ArrayData allocate(int[] array) {
        return new IntArrayData(array, array.length);
    }

    public static ArrayData allocate(double[] array) {
        return new NumberArrayData(array, array.length);
    }

    public static ArrayData allocate(Object[] array) {
        return new ObjectArrayData(array, array.length);
    }

    public static ArrayData allocate(ByteBuffer buf) {
        return new ByteBufferArrayData(buf);
    }

    public static ArrayData freeze(ArrayData underlying) {
        return new FrozenArrayFilter(underlying);
    }

    public static ArrayData seal(ArrayData underlying) {
        return new SealedArrayFilter(underlying);
    }

    public static ArrayData preventExtension(ArrayData underlying) {
        return new NonExtensibleArrayFilter(underlying);
    }

    public static ArrayData setIsLengthNotWritable(ArrayData underlying) {
        return new LengthNotWritableFilter(underlying);
    }

    public final long length() {
        return this.length;
    }

    public Object asArrayOfType(Class<?> componentType) {
        return JSType.convertArray(asObjectArray(), componentType);
    }

    public void setLength(long length) {
        this.length = length;
    }

    public ArrayData setEmpty(int index) {
        return this;
    }

    public ArrayData setEmpty(long lo, long hi) {
        return this;
    }

    public Type getOptimisticType() {
        return Type.OBJECT;
    }

    public int getIntOptimistic(int index, int programPoint) {
        throw new UnwarrantedOptimismException(getObject(index), programPoint, getOptimisticType());
    }

    public double getDoubleOptimistic(int index, int programPoint) {
        throw new UnwarrantedOptimismException(getObject(index), programPoint, getOptimisticType());
    }

    public boolean canDelete(int index, boolean strict) {
        return true;
    }

    public boolean canDelete(long longIndex, boolean strict) {
        return true;
    }

    public final ArrayData safeDelete(long fromIndex, long toIndex, boolean strict) {
        if (fromIndex <= toIndex && canDelete(fromIndex, strict)) {
            return delete(fromIndex, toIndex);
        }
        return this;
    }

    public PropertyDescriptor getDescriptor(Global global, int index) {
        return global.newDataDescriptor(getObject(index), true, true, true);
    }

    public ArrayData push(boolean strict, Object... items) {
        if (items.length == 0) {
            return this;
        }
        Class<?> widest = widestType(items);
        ArrayData newData = convert(widest);
        long pos = newData.length;
        for (Object item : items) {
            newData = newData.ensure(pos);
            long j = pos;
            pos = j + 1;
            newData.set((int) j, item, strict);
        }
        return newData;
    }

    public ArrayData push(boolean strict, Object item) {
        return push(strict, item);
    }

    public ArrayData fastSplice(int start, int removed, int added) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    static Class<?> widestType(Object... items) {
        if (!$assertionsDisabled && items.length <= 0) {
            throw new AssertionError();
        }
        Class<?> widest = Integer.class;
        for (Object item : items) {
            if (item == null) {
                return Object.class;
            }
            Class<?> itemClass = item.getClass();
            if (itemClass == Double.class || itemClass == Float.class || itemClass == Long.class) {
                if (widest == Integer.class) {
                    widest = Double.class;
                }
            } else if (itemClass != Integer.class && itemClass != Short.class && itemClass != Byte.class) {
                return Object.class;
            }
        }
        return widest;
    }

    protected List<Long> computeIteratorKeys() {
        List<Long> keys = new ArrayList<>();
        long len = length();
        long jNextIndex = 0;
        while (true) {
            long i = jNextIndex;
            if (i < len) {
                if (has((int) i)) {
                    keys.add(Long.valueOf(i));
                }
                jNextIndex = nextIndex(i);
            } else {
                return keys;
            }
        }
    }

    public Iterator<Long> indexIterator() {
        return computeIteratorKeys().iterator();
    }

    public static int nextSize(int size) {
        return alignUp(size + 1) * 2;
    }

    long nextIndex(long index) {
        return index + 1;
    }

    static Object invoke(MethodHandle mh, Object arg) {
        try {
            return (Object) mh.invoke(arg);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public GuardedInvocation findFastCallMethod(Class<? extends ArrayData> clazz, CallSiteDescriptor desc, LinkRequest request) {
        return null;
    }

    public GuardedInvocation findFastGetMethod(Class<? extends ArrayData> clazz, CallSiteDescriptor desc, LinkRequest request, String operator) {
        return null;
    }

    public GuardedInvocation findFastGetIndexMethod(Class<? extends ArrayData> clazz, CallSiteDescriptor desc, LinkRequest request) {
        return null;
    }

    public GuardedInvocation findFastSetIndexMethod(Class<? extends ArrayData> clazz, CallSiteDescriptor desc, LinkRequest request) {
        return null;
    }
}
