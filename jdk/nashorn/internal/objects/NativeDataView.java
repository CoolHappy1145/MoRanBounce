package jdk.nashorn.internal.objects;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;

/*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
    java.lang.NullPointerException
    */
/* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeDataView.class */
public class NativeDataView extends ScriptObject {
    private static PropertyMap $nasgenmap$;
    public final Object buffer;
    public final int byteOffset;
    public final int byteLength;
    private final ByteBuffer buf;

    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeDataView$Constructor.class */
    final class Constructor extends ScriptFunction {
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
        /*  JADX ERROR: Failed to decode insn: 0x000E: CONST
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
        /*  JADX ERROR: Failed to decode insn: 0x001A: CONST
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
                r11 = this;
                r0 = r11
                java.lang.String r1 = "DataView"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                jdk.nashorn.internal.runtime.Specialization[] r1 = new jdk.nashorn.internal.runtime.Specialization[r1]
                r2 = r1
                r3 = 0
                jdk.nashorn.internal.runtime.Specialization r4 = new jdk.nashorn.internal.runtime.Specialization
                r5 = r4
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r3.<init>(r4, r5)
                r0[r1] = r2
                r0 = r-1
                r1 = 1
                jdk.nashorn.internal.runtime.Specialization r2 = new jdk.nashorn.internal.runtime.Specialization
                r3 = r2
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r1.<init>(r2, r3)
                r-2[r-1] = r0
                r-6.<init>(r-5, r-4, r-3)
                r-6 = r11
                jdk.nashorn.internal.objects.NativeDataView$Prototype r-5 = new jdk.nashorn.internal.objects.NativeDataView$Prototype
                r-4 = r-5
                r-4.<init>()
                r-4 = r-5
                r-3 = r11
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r-4, r-3)
                r-6.setPrototype(r-5)
                r-6 = r11
                r-5 = 1
                r-6.setArity(r-5)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeDataView.Constructor.<init>():void");
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeDataView$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object getInt8;
        private Object getUint8;
        private Object getInt16;
        private Object getUint16;
        private Object getInt32;
        private Object getUint32;
        private Object getFloat32;
        private Object getFloat64;
        private Object setInt8;
        private Object setUint8;
        private Object setInt16;
        private Object setUint16;
        private Object setInt32;
        private Object setUint32;
        private Object setFloat32;
        private Object setFloat64;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$getInt8() {
            return this.getInt8;
        }

        public void S$getInt8(Object obj) {
            this.getInt8 = obj;
        }

        public Object G$getUint8() {
            return this.getUint8;
        }

        public void S$getUint8(Object obj) {
            this.getUint8 = obj;
        }

        public Object G$getInt16() {
            return this.getInt16;
        }

        public void S$getInt16(Object obj) {
            this.getInt16 = obj;
        }

        public Object G$getUint16() {
            return this.getUint16;
        }

        public void S$getUint16(Object obj) {
            this.getUint16 = obj;
        }

        public Object G$getInt32() {
            return this.getInt32;
        }

        public void S$getInt32(Object obj) {
            this.getInt32 = obj;
        }

        public Object G$getUint32() {
            return this.getUint32;
        }

        public void S$getUint32(Object obj) {
            this.getUint32 = obj;
        }

        public Object G$getFloat32() {
            return this.getFloat32;
        }

        public void S$getFloat32(Object obj) {
            this.getFloat32 = obj;
        }

        public Object G$getFloat64() {
            return this.getFloat64;
        }

        public void S$getFloat64(Object obj) {
            this.getFloat64 = obj;
        }

        public Object G$setInt8() {
            return this.setInt8;
        }

        public void S$setInt8(Object obj) {
            this.setInt8 = obj;
        }

        public Object G$setUint8() {
            return this.setUint8;
        }

        public void S$setUint8(Object obj) {
            this.setUint8 = obj;
        }

        public Object G$setInt16() {
            return this.setInt16;
        }

        public void S$setInt16(Object obj) {
            this.setInt16 = obj;
        }

        public Object G$setUint16() {
            return this.setUint16;
        }

        public void S$setUint16(Object obj) {
            this.setUint16 = obj;
        }

        public Object G$setInt32() {
            return this.setInt32;
        }

        public void S$setInt32(Object obj) {
            this.setInt32 = obj;
        }

        public Object G$setUint32() {
            return this.setUint32;
        }

        public void S$setUint32(Object obj) {
            this.setUint32 = obj;
        }

        public Object G$setFloat32() {
            return this.setFloat32;
        }

        public void S$setFloat32(Object obj) {
            this.setFloat32 = obj;
        }

        public Object G$setFloat64() {
            return this.setFloat64;
        }

        public void S$setFloat64(Object obj) {
            this.setFloat64 = obj;
        }

        /*  JADX ERROR: Method load error
            jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeDataView.Prototype.<init>():void, file: L-out.jar:jdk/nashorn/internal/objects/NativeDataView$Prototype.class
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:168)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:464)
            	at jadx.core.ProcessClass.process(ProcessClass.java:69)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:117)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            Caused by: java.lang.ArrayIndexOutOfBoundsException
            */
        Prototype() {
            /*
            // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeDataView.Prototype.<init>():void, file: L-out.jar:jdk/nashorn/internal/objects/NativeDataView$Prototype.class
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeDataView.Prototype.<init>():void");
        }
    }

    /* JADX WARN: Failed to check method for inline after forced processjdk.nashorn.internal.objects.NativeDataView.$clinit$():void */
    static {
        $clinit$();
    }

    /*  JADX ERROR: Failed to decode insn: 0x000D: CONST
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
    /*  JADX ERROR: Failed to decode insn: 0x001E: CONST
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
    /*  JADX ERROR: Failed to decode insn: 0x002F: CONST
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
            r2 = 3
            r1.<init>(r2)
            r1 = r0
            java.lang.String r2 = "buffer"
            r3 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            jdk.nashorn.internal.runtime.AccessorProperty r0 = jdk.nashorn.internal.runtime.AccessorProperty.create(r0, r1, r2, r3)
            r-1.add(r0)
            r-1 = r-2
            java.lang.String r0 = "byteOffset"
            r1 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r2 = 0
            r3 = 0
            boolean r2 = r2.add(r3)
            r2 = r1
            java.lang.String r3 = "byteLength"
            r4 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            r5 = 0
            r6 = 0
            boolean r5 = r5.add(r6)
            jdk.nashorn.internal.runtime.PropertyMap r4 = jdk.nashorn.internal.runtime.PropertyMap.newMap(r4)
            jdk.nashorn.internal.objects.NativeDataView.$nasgenmap$ = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeDataView.$clinit$():void");
    }

    public Object G$buffer() {
        return this.buffer;
    }

    public int G$byteOffset() {
        return this.byteOffset;
    }

    public int G$byteLength() {
        return this.byteLength;
    }

    private NativeDataView(NativeArrayBuffer nativeArrayBuffer) {
        this(nativeArrayBuffer, nativeArrayBuffer.getBuffer(), 0);
    }

    private NativeDataView(NativeArrayBuffer nativeArrayBuffer, int i) {
        this(nativeArrayBuffer, bufferFrom(nativeArrayBuffer, i), i);
    }

    private NativeDataView(NativeArrayBuffer nativeArrayBuffer, int i, int i2) {
        this(nativeArrayBuffer, bufferFrom(nativeArrayBuffer, i, i2), i, i2);
    }

    private NativeDataView(NativeArrayBuffer nativeArrayBuffer, ByteBuffer byteBuffer, int i) {
        this(nativeArrayBuffer, byteBuffer, i, byteBuffer.capacity() - i);
    }

    private NativeDataView(NativeArrayBuffer nativeArrayBuffer, ByteBuffer byteBuffer, int i, int i2) {
        super(Global.instance().getDataViewPrototype(), $nasgenmap$);
        this.buffer = nativeArrayBuffer;
        this.byteOffset = i;
        this.byteLength = i2;
        this.buf = byteBuffer;
    }

    public static NativeDataView constructor(boolean z, Object obj, Object[] objArr) {
        if (objArr.length == 0 || !(objArr[0] instanceof NativeArrayBuffer)) {
            throw ECMAErrors.typeError("not.an.arraybuffer.in.dataview", new String[0]);
        }
        NativeArrayBuffer nativeArrayBuffer = (NativeArrayBuffer) objArr[0];
        switch (objArr.length) {
            case 1:
                return new NativeDataView(nativeArrayBuffer);
            case 2:
                return new NativeDataView(nativeArrayBuffer, JSType.toInt32(objArr[1]));
            default:
                return new NativeDataView(nativeArrayBuffer, JSType.toInt32(objArr[1]), JSType.toInt32(objArr[2]));
        }
    }

    public static NativeDataView constructor(boolean z, Object obj, Object obj2, int i) {
        if (!(obj2 instanceof NativeArrayBuffer)) {
            throw ECMAErrors.typeError("not.an.arraybuffer.in.dataview", new String[0]);
        }
        return new NativeDataView((NativeArrayBuffer) obj2, i);
    }

    public static NativeDataView constructor(boolean z, Object obj, Object obj2, int i, int i2) {
        if (!(obj2 instanceof NativeArrayBuffer)) {
            throw ECMAErrors.typeError("not.an.arraybuffer.in.dataview", new String[0]);
        }
        return new NativeDataView((NativeArrayBuffer) obj2, i, i2);
    }

    public static int getInt8(Object obj, Object obj2) {
        try {
            return getBuffer(obj).get(JSType.toInt32(obj2));
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static int getInt8(Object obj, int i) {
        try {
            return getBuffer(obj).get(i);
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static int getUint8(Object obj, Object obj2) {
        try {
            return 255 & getBuffer(obj).get(JSType.toInt32(obj2));
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static int getUint8(Object obj, int i) {
        try {
            return 255 & getBuffer(obj).get(i);
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static int getInt16(Object obj, Object obj2, Object obj3) {
        try {
            return getBuffer(obj, obj3).getShort(JSType.toInt32(obj2));
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static int getInt16(Object obj, int i) {
        try {
            return getBuffer(obj, false).getShort(i);
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static int getInt16(Object obj, int i, boolean z) {
        try {
            return getBuffer(obj, z).getShort(i);
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static int getUint16(Object obj, Object obj2, Object obj3) {
        try {
            return 65535 & getBuffer(obj, obj3).getShort(JSType.toInt32(obj2));
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static int getUint16(Object obj, int i) {
        try {
            return 65535 & getBuffer(obj, false).getShort(i);
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static int getUint16(Object obj, int i, boolean z) {
        try {
            return 65535 & getBuffer(obj, z).getShort(i);
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static int getInt32(Object obj, Object obj2, Object obj3) {
        try {
            return getBuffer(obj, obj3).getInt(JSType.toInt32(obj2));
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static int getInt32(Object obj, int i) {
        try {
            return getBuffer(obj, false).getInt(i);
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static int getInt32(Object obj, int i, boolean z) {
        try {
            return getBuffer(obj, z).getInt(i);
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static double getUint32(Object obj, Object obj2, Object obj3) {
        try {
            return JSType.MAX_UINT & getBuffer(obj, obj3).getInt(JSType.toInt32(obj2));
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static double getUint32(Object obj, int i) {
        try {
            long j = i;
            return getBuffer(obj, false).getInt((int) ((j < -9007199254740992L || j > 9007199254740992L) ? (long) (j % 4.294967296E9d) : j)) & JSType.MAX_UINT;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static double getUint32(Object obj, int i, boolean z) {
        try {
            long j = i;
            return getBuffer(obj, z).getInt((int) ((j < -9007199254740992L || j > 9007199254740992L) ? (long) (j % 4.294967296E9d) : j)) & JSType.MAX_UINT;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static double getFloat32(Object obj, Object obj2, Object obj3) {
        try {
            return getBuffer(obj, obj3).getFloat(JSType.toInt32(obj2));
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static double getFloat32(Object obj, int i) {
        try {
            return getBuffer(obj, false).getFloat(i);
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static double getFloat32(Object obj, int i, boolean z) {
        try {
            return getBuffer(obj, z).getFloat(i);
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static double getFloat64(Object obj, Object obj2, Object obj3) {
        try {
            return getBuffer(obj, obj3).getDouble(JSType.toInt32(obj2));
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static double getFloat64(Object obj, int i) {
        try {
            return getBuffer(obj, false).getDouble(i);
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static double getFloat64(Object obj, int i, boolean z) {
        try {
            return getBuffer(obj, z).getDouble(i);
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setInt8(Object obj, Object obj2, Object obj3) {
        try {
            getBuffer(obj).put(JSType.toInt32(obj2), (byte) JSType.toInt32(obj3));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setInt8(Object obj, int i, int i2) {
        try {
            getBuffer(obj).put(i, (byte) i2);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setUint8(Object obj, Object obj2, Object obj3) {
        try {
            getBuffer(obj).put(JSType.toInt32(obj2), (byte) JSType.toInt32(obj3));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setUint8(Object obj, int i, int i2) {
        try {
            getBuffer(obj).put(i, (byte) i2);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setInt16(Object obj, Object obj2, Object obj3, Object obj4) {
        try {
            getBuffer(obj, obj4).putShort(JSType.toInt32(obj2), (short) JSType.toInt32(obj3));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setInt16(Object obj, int i, int i2) {
        try {
            getBuffer(obj, false).putShort(i, (short) i2);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setInt16(Object obj, int i, int i2, boolean z) {
        try {
            getBuffer(obj, z).putShort(i, (short) i2);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setUint16(Object obj, Object obj2, Object obj3, Object obj4) {
        try {
            getBuffer(obj, obj4).putShort(JSType.toInt32(obj2), (short) JSType.toInt32(obj3));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setUint16(Object obj, int i, int i2) {
        try {
            getBuffer(obj, false).putShort(i, (short) i2);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setUint16(Object obj, int i, int i2, boolean z) {
        try {
            getBuffer(obj, z).putShort(i, (short) i2);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setInt32(Object obj, Object obj2, Object obj3, Object obj4) {
        try {
            getBuffer(obj, obj4).putInt(JSType.toInt32(obj2), JSType.toInt32(obj3));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setInt32(Object obj, int i, int i2) {
        try {
            getBuffer(obj, false).putInt(i, i2);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setInt32(Object obj, int i, int i2, boolean z) {
        try {
            getBuffer(obj, z).putInt(i, i2);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setUint32(Object obj, Object obj2, Object obj3, Object obj4) {
        try {
            getBuffer(obj, obj4).putInt(JSType.toInt32(obj2), (int) JSType.toUint32(obj3));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setUint32(Object obj, int i, double d) {
        try {
            getBuffer(obj, false).putInt(i, (int) JSType.toUint32(d));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setUint32(Object obj, int i, double d, boolean z) {
        try {
            getBuffer(obj, z).putInt(i, (int) JSType.toUint32(d));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setFloat32(Object obj, Object obj2, Object obj3, Object obj4) {
        try {
            getBuffer(obj, obj4).putFloat((int) JSType.toUint32(obj2), (float) JSType.toNumber(obj3));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setFloat32(Object obj, int i, double d) {
        try {
            getBuffer(obj, false).putFloat(i, (float) d);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setFloat32(Object obj, int i, double d, boolean z) {
        try {
            getBuffer(obj, z).putFloat(i, (float) d);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setFloat64(Object obj, Object obj2, Object obj3, Object obj4) {
        try {
            getBuffer(obj, obj4).putDouble((int) JSType.toUint32(obj2), JSType.toNumber(obj3));
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setFloat64(Object obj, int i, double d) {
        try {
            getBuffer(obj, false).putDouble(i, d);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    public static Object setFloat64(Object obj, int i, double d, boolean z) {
        try {
            getBuffer(obj, z).putDouble(i, d);
            return ScriptRuntime.UNDEFINED;
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.offset", new String[0]);
        }
    }

    private static ByteBuffer bufferFrom(NativeArrayBuffer nativeArrayBuffer, int i) {
        try {
            return nativeArrayBuffer.getBuffer(i);
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.constructor.offset", new String[0]);
        }
    }

    private static ByteBuffer bufferFrom(NativeArrayBuffer nativeArrayBuffer, int i, int i2) {
        try {
            return nativeArrayBuffer.getBuffer(i, i2);
        } catch (IllegalArgumentException e) {
            throw ECMAErrors.rangeError(e, "dataview.constructor.offset", new String[0]);
        }
    }

    private static NativeDataView checkSelf(Object obj) {
        if (!(obj instanceof NativeDataView)) {
            throw ECMAErrors.typeError("not.an.arraybuffer.in.dataview", new String[]{ScriptRuntime.safeToString(obj)});
        }
        return (NativeDataView) obj;
    }

    private static ByteBuffer getBuffer(Object obj) {
        return checkSelf(obj).buf;
    }

    private static ByteBuffer getBuffer(Object obj, Object obj2) {
        return getBuffer(obj, JSType.toBoolean(obj2));
    }

    private static ByteBuffer getBuffer(Object obj, boolean z) {
        return getBuffer(obj).order(z ? ByteOrder.LITTLE_ENDIAN : ByteOrder.BIG_ENDIAN);
    }
}
