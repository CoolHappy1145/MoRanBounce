package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.util.Collections;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.objects.ArrayBufferView;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.TypedArrayData;

/* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeFloat32Array.class */
public final class NativeFloat32Array extends ArrayBufferView {
    private static PropertyMap $nasgenmap$;
    private static final ArrayBufferView.Factory FACTORY = new ArrayBufferView.Factory(4) { // from class: jdk.nashorn.internal.objects.NativeFloat32Array.1
        @Override // jdk.nashorn.internal.objects.ArrayBufferView.Factory
        public TypedArrayData createArrayData(ByteBuffer byteBuffer, int i, int i2) {
            return createArrayData(byteBuffer, i, i2);
        }

        @Override // jdk.nashorn.internal.objects.ArrayBufferView.Factory
        public ArrayBufferView construct(NativeArrayBuffer nativeArrayBuffer, int i, int i2) {
            return new NativeFloat32Array(nativeArrayBuffer, i, i2);
        }

        @Override // jdk.nashorn.internal.objects.ArrayBufferView.Factory
        public Float32ArrayData createArrayData(ByteBuffer byteBuffer, int i, int i2) {
            return new Float32ArrayData(byteBuffer.asFloatBuffer(), i, i2, null);
        }
    };

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeFloat32Array$Constructor.class */
    final class Constructor extends ScriptFunction {
        private static final PropertyMap $nasgenmap$ = null;

        /*  JADX ERROR: Failed to decode insn: 0x0003: CONST
            jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
            	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
            	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
            	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
            	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
            	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:464)
            	at jadx.core.ProcessClass.process(ProcessClass.java:69)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            */
        Constructor() {
            /*
                r6 = this;
                r0 = r6
                java.lang.String r1 = "Float32Array"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r2 = r9
                r3 = 0
                r-1.<init>(r0, r1, r2, r3)
                r-1 = r6
                jdk.nashorn.internal.objects.NativeFloat32Array$Prototype r0 = new jdk.nashorn.internal.objects.NativeFloat32Array$Prototype
                r1 = r0
                r1.<init>()
                r1 = r0
                r2 = r6
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r1, r2)
                r-1.setPrototype(r0)
                r-1 = r6
                r0 = 1
                r-1.setArity(r0)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeFloat32Array.Constructor.<init>():void");
        }
    }

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeFloat32Array$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object set;
        private Object subarray;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$set() {
            return this.set;
        }

        public void S$set(Object obj) {
            this.set = obj;
        }

        public Object G$subarray() {
            return this.subarray;
        }

        public void S$subarray(Object obj) {
            this.subarray = obj;
        }

        /*  JADX ERROR: Failed to decode insn: 0x000A: CONST
            jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
            	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
            	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
            	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
            	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
            	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:464)
            	at jadx.core.ProcessClass.process(ProcessClass.java:69)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            */
        /*  JADX ERROR: Failed to decode insn: 0x0014: CONST
            jadx.plugins.input.java.utils.JavaClassParseException: Unsupported constant type: METHOD_HANDLE
            	at jadx.plugins.input.java.data.code.decoders.LoadConstDecoder.decode(LoadConstDecoder.java:65)
            	at jadx.plugins.input.java.data.code.JavaInsnData.decode(JavaInsnData.java:46)
            	at jadx.core.dex.instructions.InsnDecoder.lambda$process$0(InsnDecoder.java:50)
            	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:85)
            	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:464)
            	at jadx.core.ProcessClass.process(ProcessClass.java:69)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            */
        Prototype() {
            /*
                r4 = this;
                r0 = r4
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeFloat32Array.Prototype.$nasgenmap$
                r0.<init>(r1)
                r0 = r4
                java.lang.String r1 = "set"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r5 = r1
                r-1.set = r0
                r-1 = r4
                java.lang.String r0 = "subarray"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r5 = r0
                r-2.subarray = r-1
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeFloat32Array.Prototype.<init>():void");
        }
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    static {
        $clinit$();
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeFloat32Array$Float32ArrayData.class */
    private static final class Float32ArrayData extends TypedArrayData {
        private static final MethodHandle GET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), Float32ArrayData.class, "getElem", Double.TYPE, new Class[]{Integer.TYPE}).methodHandle();
        private static final MethodHandle SET_ELEM = CompilerConstants.specialCall(MethodHandles.lookup(), Float32ArrayData.class, "setElem", Void.TYPE, new Class[]{Integer.TYPE, Double.TYPE}).methodHandle();

        Float32ArrayData(FloatBuffer floatBuffer, int i, int i2, C01831 c01831) {
            this(floatBuffer, i, i2);
        }

        private Float32ArrayData(FloatBuffer floatBuffer, int i, int i2) {
            super(((FloatBuffer) floatBuffer.position(i).limit(i2)).slice(), i2 - i);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
        public Class getElementType() {
            return Double.TYPE;
        }

        @Override // jdk.nashorn.internal.runtime.arrays.TypedArrayData
        protected MethodHandle getGetElem() {
            return GET_ELEM;
        }

        @Override // jdk.nashorn.internal.runtime.arrays.TypedArrayData
        protected MethodHandle getSetElem() {
            return SET_ELEM;
        }

        private double getElem(int i) {
            try {
                return ((FloatBuffer) this.f56nb).get(i);
            } catch (IndexOutOfBoundsException unused) {
                throw new ClassCastException();
            }
        }

        private void setElem(int i, double d) {
            try {
                if (i < ((FloatBuffer) this.f56nb).limit()) {
                    ((FloatBuffer) this.f56nb).put(i, (float) d);
                }
            } catch (IndexOutOfBoundsException unused) {
                throw new ClassCastException();
            }
        }

        @Override // jdk.nashorn.internal.runtime.arrays.TypedArrayData, jdk.nashorn.internal.runtime.arrays.ContinuousArrayData
        public MethodHandle getElementGetter(Class cls, int i) {
            if (cls == Integer.TYPE) {
                return null;
            }
            return getContinuousElementGetter(getClass(), GET_ELEM, cls, i);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public int getInt(int i) {
            return (int) getDouble(i);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public double getDouble(int i) {
            return getElem(i);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public double getDoubleOptimistic(int i, int i2) {
            return getElem(i);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public Object getObject(int i) {
            return Double.valueOf(getDouble(i));
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData set(int i, Object obj, boolean z) {
            return set(i, JSType.toNumber(obj), z);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData set(int i, int i2, boolean z) {
            return set(i, i2, z);
        }

        @Override // jdk.nashorn.internal.runtime.arrays.ArrayData
        public ArrayData set(int i, double d, boolean z) {
            setElem(i, d);
            return this;
        }
    }

    public static NativeFloat32Array constructor(boolean z, Object obj, Object[] objArr) {
        return (NativeFloat32Array) constructorImpl(z, objArr, FACTORY);
    }

    NativeFloat32Array(NativeArrayBuffer nativeArrayBuffer, int i, int i2) {
        super(nativeArrayBuffer, i, i2);
    }

    @Override // jdk.nashorn.internal.objects.ArrayBufferView
    protected ArrayBufferView.Factory factory() {
        return FACTORY;
    }

    protected static Object set(Object obj, Object obj2, Object obj3) {
        return ArrayBufferView.setImpl(obj, obj2, obj3);
    }

    protected static NativeFloat32Array subarray(Object obj, Object obj2, Object obj3) {
        return (NativeFloat32Array) ArrayBufferView.subarrayImpl(obj, obj2, obj3);
    }

    @Override // jdk.nashorn.internal.objects.ArrayBufferView
    protected ScriptObject getPrototype(Global global) {
        return global.getFloat32ArrayPrototype();
    }
}
