package jdk.nashorn.internal.objects;

import java.nio.ByteBuffer;
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
/* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeArrayBuffer.class */
public final class NativeArrayBuffer extends ScriptObject {

    /* renamed from: nb */
    private final ByteBuffer f33nb;
    private static PropertyMap $nasgenmap$;

    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeArrayBuffer$Constructor.class */
    final class Constructor extends ScriptFunction {
        private Object isView;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$isView() {
            return this.isView;
        }

        public void S$isView(Object obj) {
            this.isView = obj;
        }

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
        Constructor() {
            /*
                r6 = this;
                r0 = r6
                java.lang.String r1 = "ArrayBuffer"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r0 = r0[r1]
                r1 = 0
                r-3.<init>(r-2, r-1, r0, r1)
                r-3 = r6
                java.lang.String r-2 = "isView"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r8 = r-2
                r-4.isView = r-3
                r-4 = r6
                jdk.nashorn.internal.objects.NativeArrayBuffer$Prototype r-3 = new jdk.nashorn.internal.objects.NativeArrayBuffer$Prototype
                r-2 = r-3
                r-2.<init>()
                r-2 = r-3
                r-1 = r6
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r-2, r-1)
                r-4.setPrototype(r-3)
                r-4 = r6
                r-3 = 1
                r-4.setArity(r-3)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeArrayBuffer.Constructor.<init>():void");
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeArrayBuffer$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object slice;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$slice() {
            return this.slice;
        }

        public void S$slice(Object obj) {
            this.slice = obj;
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
        /*  JADX ERROR: Failed to decode insn: 0x0015: CONST
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
        /*  JADX ERROR: Failed to decode insn: 0x0021: CONST
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
                r11 = this;
                r0 = r11
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeArrayBuffer.Prototype.$nasgenmap$
                r0.<init>(r1)
                r0 = r11
                java.lang.String r1 = "slice"
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
                jdk.nashorn.internal.runtime.ScriptFunction r-5 = jdk.nashorn.internal.runtime.ScriptFunction.createBuiltin(r-5, r-4, r-3)
                r-6.slice = r-5
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeArrayBuffer.Prototype.<init>():void");
        }
    }

    /* JADX WARN: Failed to check method for inline after forced processjdk.nashorn.internal.objects.NativeArrayBuffer.$clinit$():void */
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
    public static void $clinit$() {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r1 = r0
            r2 = 1
            r1.<init>(r2)
            r1 = r0
            java.lang.String r2 = "byteLength"
            r3 = 7
            // decode failed: Unsupported constant type: METHOD_HANDLE
            jdk.nashorn.internal.runtime.AccessorProperty r0 = jdk.nashorn.internal.runtime.AccessorProperty.create(r0, r1, r2, r3)
            r-1.add(r0)
            jdk.nashorn.internal.runtime.PropertyMap r-2 = jdk.nashorn.internal.runtime.PropertyMap.newMap(r-2)
            jdk.nashorn.internal.objects.NativeArrayBuffer.$nasgenmap$ = r-2
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeArrayBuffer.$clinit$():void");
    }

    protected NativeArrayBuffer(ByteBuffer byteBuffer, Global global) {
        super(global.getArrayBufferPrototype(), $nasgenmap$);
        this.f33nb = byteBuffer;
    }

    protected NativeArrayBuffer(ByteBuffer byteBuffer) {
        this(byteBuffer, Global.instance());
    }

    protected NativeArrayBuffer(int i) {
        this(ByteBuffer.allocateDirect(i));
    }

    protected NativeArrayBuffer(NativeArrayBuffer nativeArrayBuffer, int i, int i2) {
        this(cloneBuffer(nativeArrayBuffer.getNioBuffer(), i, i2));
    }

    public static NativeArrayBuffer constructor(boolean z, Object obj, Object[] objArr) {
        if (!z) {
            throw ECMAErrors.typeError("constructor.requires.new", new String[]{"ArrayBuffer"});
        }
        if (objArr.length == 0) {
            return new NativeArrayBuffer(0);
        }
        return new NativeArrayBuffer(JSType.toInt32(objArr[0]));
    }

    private static ByteBuffer cloneBuffer(ByteBuffer byteBuffer, int i, int i2) {
        ByteBuffer byteBufferAllocateDirect = ByteBuffer.allocateDirect(byteBuffer.capacity());
        byteBuffer.rewind();
        byteBufferAllocateDirect.put(byteBuffer);
        byteBuffer.rewind();
        byteBufferAllocateDirect.flip();
        byteBufferAllocateDirect.position(i);
        byteBufferAllocateDirect.limit(i2);
        return byteBufferAllocateDirect.slice();
    }

    ByteBuffer getNioBuffer() {
        return this.f33nb;
    }

    public static int byteLength(Object obj) {
        return ((NativeArrayBuffer) obj).getByteLength();
    }

    public static NativeArrayBuffer slice(Object obj, Object obj2, Object obj3) {
        NativeArrayBuffer nativeArrayBuffer = (NativeArrayBuffer) obj;
        int byteLength = nativeArrayBuffer.getByteLength();
        int iAdjustIndex = adjustIndex(JSType.toInt32(obj2), byteLength);
        return new NativeArrayBuffer(nativeArrayBuffer, iAdjustIndex, Math.max(adjustIndex(obj3 != ScriptRuntime.UNDEFINED ? JSType.toInt32(obj3) : byteLength, byteLength), iAdjustIndex));
    }

    public static Object slice(Object obj, int i, int i2) {
        NativeArrayBuffer nativeArrayBuffer = (NativeArrayBuffer) obj;
        int byteLength = nativeArrayBuffer.getByteLength();
        return new NativeArrayBuffer(nativeArrayBuffer, adjustIndex(i, byteLength), Math.max(adjustIndex(i2, byteLength), i));
    }

    public static Object slice(Object obj, int i) {
        return slice(obj, i, ((NativeArrayBuffer) obj).getByteLength());
    }

    static int adjustIndex(int i, int i2) {
        if (i < 0) {
            int i3 = i + i2;
            if (i3 < 0) {
                return 0;
            }
            if (i3 > i2) {
                return i2;
            }
            return i3;
        }
        if (i < 0) {
            return 0;
        }
        if (i > i2) {
            return i2;
        }
        return i;
    }

    int getByteLength() {
        return this.f33nb.limit();
    }

    ByteBuffer getBuffer() {
        return this.f33nb;
    }

    ByteBuffer getBuffer(int i) {
        return (ByteBuffer) this.f33nb.duplicate().position(i);
    }

    ByteBuffer getBuffer(int i, int i2) {
        return (ByteBuffer) getBuffer(i).limit(i2);
    }
}
