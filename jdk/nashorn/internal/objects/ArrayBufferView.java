package jdk.nashorn.internal.objects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.TypedArrayData;

/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: L-out.jar:jdk/nashorn/internal/objects/ArrayBufferView.class */
public abstract class ArrayBufferView extends ScriptObject {
    private final NativeArrayBuffer buffer;
    private final int byteOffset;
    private static PropertyMap $nasgenmap$;
    static final boolean $assertionsDisabled;

    protected abstract Factory factory();

    protected abstract ScriptObject getPrototype(Global global);

    /*  JADX ERROR: Failed to decode insn: 0x000E: CONST
        jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
        	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
        	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
        	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
        	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
        	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
        	at jadx.core.ProcessClass.process(ProcessClass.java:69)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
        	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
        	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
        */
    /*  JADX ERROR: Failed to decode insn: 0x001F: CONST
        jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
        	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
        	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
        	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
        	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
        	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
        	at jadx.core.ProcessClass.process(ProcessClass.java:69)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
        	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
        	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
        */
    /*  JADX ERROR: Failed to decode insn: 0x0030: CONST
        jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
        	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
        	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
        	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
        	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
        	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
        	at jadx.core.ProcessClass.process(ProcessClass.java:69)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
        	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
        	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
        */
    /*  JADX ERROR: Failed to decode insn: 0x0041: CONST
        jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
        	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
        	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
        	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
        	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
        	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
        	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
        	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
        	at jadx.core.ProcessClass.process(ProcessClass.java:69)
        	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
        	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
        	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
        	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
        */
    public static void $clinit$() {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = r0
            r2 = 4
            r1.<init>(r2)
            r1 = r0
            java.lang.String r2 = "buffer"
            r3 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r4 = 0
            r5 = r10
            boolean r4 = r4.add(r5)
            r4 = r3
            java.lang.String r5 = "byteOffset"
            r6 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r7 = 0
            r8 = r10
            boolean r7 = r7.add(r8)
            r7 = r6
            java.lang.String r8 = "byteLength"
            r9 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r10 = 0
            r11 = r10
            boolean r10 = r10.add(r11)
            r10 = r9
            java.lang.String r11 = "length"
            r12 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r13 = 0
            r14 = r10
            boolean r13 = r13.add(r14)
            jdk.nashorn.internal.runtime.PropertyMap r12 = jdk.nashorn.internal.runtime.PropertyMap.newMap(r12)
            jdk.nashorn.internal.objects.ArrayBufferView.$nasgenmap$ = r12
            return
            r12 = 0
            r13 = 0
            r9 = r13
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.ArrayBufferView.$clinit$():void");
    }

    /* JADX WARN: Failed to check method for inline after forced processjdk.nashorn.internal.objects.ArrayBufferView.$clinit$():void */
    static {
        $assertionsDisabled = !ArrayBufferView.class.desiredAssertionStatus();
        $clinit$();
    }

    private ArrayBufferView(NativeArrayBuffer nativeArrayBuffer, int i, int i2, Global global) {
        super($nasgenmap$);
        int iBytesPerElement = bytesPerElement();
        checkConstructorArgs(nativeArrayBuffer.getByteLength(), iBytesPerElement, i, i2);
        setProto(getPrototype(global));
        this.buffer = nativeArrayBuffer;
        this.byteOffset = i;
        if (!$assertionsDisabled && i % iBytesPerElement != 0) {
            throw new AssertionError();
        }
        int i3 = i / iBytesPerElement;
        setArray(factory().createArrayData(nativeArrayBuffer.getNioBuffer().duplicate().order(ByteOrder.nativeOrder()), i3, i3 + i2));
    }

    protected ArrayBufferView(NativeArrayBuffer nativeArrayBuffer, int i, int i2) {
        this(nativeArrayBuffer, i, i2, Global.instance());
    }

    private static void checkConstructorArgs(int i, int i2, int i3, int i4) {
        if (i3 < 0 || i4 < 0) {
            throw new RuntimeException("byteOffset or length must not be negative, byteOffset=" + i3 + ", elementLength=" + i4 + ", bytesPerElement=" + i2);
        }
        if (i3 + (i4 * i2) > i) {
            throw new RuntimeException("byteOffset + byteLength out of range, byteOffset=" + i3 + ", elementLength=" + i4 + ", bytesPerElement=" + i2);
        }
        if (i3 % i2 != 0) {
            throw new RuntimeException("byteOffset must be a multiple of the element size, byteOffset=" + i3 + " bytesPerElement=" + i2);
        }
    }

    private int bytesPerElement() {
        return factory().bytesPerElement;
    }

    public static Object buffer(Object obj) {
        return ((ArrayBufferView) obj).buffer;
    }

    public static int byteOffset(Object obj) {
        return ((ArrayBufferView) obj).byteOffset;
    }

    public static int byteLength(Object obj) {
        ArrayBufferView arrayBufferView = (ArrayBufferView) obj;
        return ((TypedArrayData) arrayBufferView.getArray()).getElementLength() * arrayBufferView.bytesPerElement();
    }

    public static int length(Object obj) {
        return ((ArrayBufferView) obj).elementLength();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public final Object getLength() {
        return Integer.valueOf(elementLength());
    }

    private int elementLength() {
        return ((TypedArrayData) getArray()).getElementLength();
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/ArrayBufferView$Factory.class */
    protected static abstract class Factory {
        final int bytesPerElement;
        final int maxElementLength;

        public abstract ArrayBufferView construct(NativeArrayBuffer nativeArrayBuffer, int i, int i2);

        public abstract TypedArrayData createArrayData(ByteBuffer byteBuffer, int i, int i2);

        public abstract String getClassName();

        public Factory(int i) {
            this.bytesPerElement = i;
            this.maxElementLength = Integer.MAX_VALUE / i;
        }

        public final ArrayBufferView construct(int i) {
            if (i > this.maxElementLength) {
                throw ECMAErrors.rangeError("inappropriate.array.buffer.length", new String[]{JSType.toString(i)});
            }
            return construct(new NativeArrayBuffer(i * this.bytesPerElement), 0, i);
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public final String getClassName() {
        return factory().getClassName();
    }

    protected static ArrayBufferView constructorImpl(boolean z, Object[] objArr, Factory factory) {
        int iLengthToInt;
        ArrayBufferView arrayBufferViewConstruct;
        int byteLength;
        Object obj = objArr.length != 0 ? objArr[0] : 0;
        if (!z) {
            throw ECMAErrors.typeError("constructor.requires.new", new String[]{factory.getClassName()});
        }
        if (obj instanceof NativeArrayBuffer) {
            NativeArrayBuffer nativeArrayBuffer = (NativeArrayBuffer) obj;
            int int32 = objArr.length > 1 ? JSType.toInt32(objArr[1]) : 0;
            if (objArr.length > 2) {
                byteLength = JSType.toInt32(objArr[2]);
            } else {
                if ((nativeArrayBuffer.getByteLength() - int32) % factory.bytesPerElement != 0) {
                    throw new RuntimeException("buffer.byteLength - byteOffset must be a multiple of the element size");
                }
                byteLength = (nativeArrayBuffer.getByteLength() - int32) / factory.bytesPerElement;
            }
            return factory.construct(nativeArrayBuffer, int32, byteLength);
        }
        if (obj instanceof ArrayBufferView) {
            iLengthToInt = ((ArrayBufferView) obj).elementLength();
            arrayBufferViewConstruct = factory.construct(iLengthToInt);
        } else if (obj instanceof NativeArray) {
            iLengthToInt = lengthToInt(((NativeArray) obj).getArray().length());
            arrayBufferViewConstruct = factory.construct(iLengthToInt);
        } else {
            double number = JSType.toNumber(obj);
            return factory.construct(lengthToInt(Double.isInfinite(number) ? 0L : (long) number));
        }
        copyElements(arrayBufferViewConstruct, iLengthToInt, (ScriptObject) obj, 0);
        return arrayBufferViewConstruct;
    }

    protected static Object setImpl(Object obj, Object obj2, Object obj3) {
        int length;
        ArrayBufferView arrayBufferView = (ArrayBufferView) obj;
        if (obj2 instanceof ArrayBufferView) {
            length = ((ArrayBufferView) obj2).elementLength();
        } else if (obj2 instanceof NativeArray) {
            length = (int) (((NativeArray) obj2).getArray().length() & 2147483647L);
        } else {
            throw new RuntimeException("argument is not of array type");
        }
        ScriptObject scriptObject = (ScriptObject) obj2;
        int int32 = JSType.toInt32(obj3);
        if (arrayBufferView.elementLength() < length + int32 || int32 < 0) {
            throw new RuntimeException("offset or array length out of bounds");
        }
        copyElements(arrayBufferView, length, scriptObject, int32);
        return ScriptRuntime.UNDEFINED;
    }

    private static void copyElements(ArrayBufferView arrayBufferView, int i, ScriptObject scriptObject, int i2) {
        if (0 == 0) {
            int i3 = 0;
            int i4 = i2;
            while (i3 < i) {
                arrayBufferView.set(i4, scriptObject.getInt(i3, -1), 0);
                i3++;
                i4++;
            }
            return;
        }
        int i5 = 0;
        int i6 = i2;
        while (i5 < i) {
            arrayBufferView.set(i6, scriptObject.getDouble(i5, -1), 0);
            i5++;
            i6++;
        }
    }

    private static int lengthToInt(long j) {
        if (j > 2147483647L || j < 0) {
            throw ECMAErrors.rangeError("inappropriate.array.buffer.length", new String[]{JSType.toString(j)});
        }
        return (int) (j & 2147483647L);
    }

    protected static ScriptObject subarrayImpl(Object obj, Object obj2, Object obj3) {
        ArrayBufferView arrayBufferView = (ArrayBufferView) obj;
        int i = arrayBufferView.byteOffset;
        int iBytesPerElement = arrayBufferView.bytesPerElement();
        int iElementLength = arrayBufferView.elementLength();
        int iAdjustIndex = NativeArrayBuffer.adjustIndex(JSType.toInt32(obj2), iElementLength);
        int iMax = Math.max(NativeArrayBuffer.adjustIndex(obj3 != ScriptRuntime.UNDEFINED ? JSType.toInt32(obj3) : iElementLength, iElementLength) - iAdjustIndex, 0);
        if ($assertionsDisabled || i % iBytesPerElement == 0) {
            return arrayBufferView.factory().construct(arrayBufferView.buffer, (iAdjustIndex * iBytesPerElement) + i, iMax);
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected GuardedInvocation findGetIndexMethod(CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        getArray();
        getArray().getClass();
        if (0 == 0) {
            return super.findGetIndexMethod(callSiteDescriptor, linkRequest);
        }
        return null;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected GuardedInvocation findSetIndexMethod(CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        getArray();
        getArray().getClass();
        if (0 == 0) {
            return super.findSetIndexMethod(callSiteDescriptor, linkRequest);
        }
        return null;
    }
}
