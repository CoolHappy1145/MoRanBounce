package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import jdk.internal.dynalink.beans.BeansLinker;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.GuardingDynamicLinker;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.internal.dynalink.linker.TypeBasedGuardingDynamicLinker;
import jdk.internal.dynalink.support.CallSiteDescriptorFactory;
import jdk.internal.dynalink.support.LinkRequestImpl;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ECMAException;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.linker.InvokeByName;
import jdk.nashorn.internal.runtime.linker.NashornBeansLinker;

/* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeObject.class */
public final class NativeObject {
    public static final MethodHandle GET__PROTO__;
    public static final MethodHandle SET__PROTO__;
    private static final Object TO_STRING;
    private static final MethodType MIRROR_GETTER_TYPE;
    private static final MethodType MIRROR_SETTER_TYPE;
    private static PropertyMap $nasgenmap$;
    static final boolean $assertionsDisabled;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeObject$Constructor.class */
    final class Constructor extends ScriptFunction {
        private Object setIndexedPropertiesToExternalArrayData;
        private Object getPrototypeOf;
        private Object setPrototypeOf;
        private Object getOwnPropertyDescriptor;
        private Object getOwnPropertyNames;
        private Object create;
        private Object defineProperty;
        private Object defineProperties;
        private Object seal;
        private Object freeze;
        private Object preventExtensions;
        private Object isSealed;
        private Object isFrozen;
        private Object isExtensible;
        private Object keys;
        private Object bindProperties;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$setIndexedPropertiesToExternalArrayData() {
            return this.setIndexedPropertiesToExternalArrayData;
        }

        public void S$setIndexedPropertiesToExternalArrayData(Object obj) {
            this.setIndexedPropertiesToExternalArrayData = obj;
        }

        public Object G$getPrototypeOf() {
            return this.getPrototypeOf;
        }

        public void S$getPrototypeOf(Object obj) {
            this.getPrototypeOf = obj;
        }

        public Object G$setPrototypeOf() {
            return this.setPrototypeOf;
        }

        public void S$setPrototypeOf(Object obj) {
            this.setPrototypeOf = obj;
        }

        public Object G$getOwnPropertyDescriptor() {
            return this.getOwnPropertyDescriptor;
        }

        public void S$getOwnPropertyDescriptor(Object obj) {
            this.getOwnPropertyDescriptor = obj;
        }

        public Object G$getOwnPropertyNames() {
            return this.getOwnPropertyNames;
        }

        public void S$getOwnPropertyNames(Object obj) {
            this.getOwnPropertyNames = obj;
        }

        public Object G$create() {
            return this.create;
        }

        public void S$create(Object obj) {
            this.create = obj;
        }

        public Object G$defineProperty() {
            return this.defineProperty;
        }

        public void S$defineProperty(Object obj) {
            this.defineProperty = obj;
        }

        public Object G$defineProperties() {
            return this.defineProperties;
        }

        public void S$defineProperties(Object obj) {
            this.defineProperties = obj;
        }

        public Object G$seal() {
            return this.seal;
        }

        public void S$seal(Object obj) {
            this.seal = obj;
        }

        public Object G$freeze() {
            return this.freeze;
        }

        public void S$freeze(Object obj) {
            this.freeze = obj;
        }

        public Object G$preventExtensions() {
            return this.preventExtensions;
        }

        public void S$preventExtensions(Object obj) {
            this.preventExtensions = obj;
        }

        public Object G$isSealed() {
            return this.isSealed;
        }

        public void S$isSealed(Object obj) {
            this.isSealed = obj;
        }

        public Object G$isFrozen() {
            return this.isFrozen;
        }

        public void S$isFrozen(Object obj) {
            this.isFrozen = obj;
        }

        public Object G$isExtensible() {
            return this.isExtensible;
        }

        public void S$isExtensible(Object obj) {
            this.isExtensible = obj;
        }

        public Object G$keys() {
            return this.keys;
        }

        public void S$keys(Object obj) {
            this.keys = obj;
        }

        public Object G$bindProperties() {
            return this.bindProperties;
        }

        public void S$bindProperties(Object obj) {
            this.bindProperties = obj;
        }

        /*  JADX ERROR: Method load error
            jadx.core.utils.exceptions.DecodeException: Load method exception: ArrayIndexOutOfBoundsException: Index 228 out of bounds for length 202 in method: jdk.nashorn.internal.objects.NativeObject.Constructor.<init>():void, file: L-out.jar:jdk/nashorn/internal/objects/NativeObject$Constructor.class
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:168)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:458)
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:464)
            	at jadx.core.ProcessClass.process(ProcessClass.java:69)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            Caused by: java.lang.ArrayIndexOutOfBoundsException: Index 228 out of bounds for length 202
            	at jadx.plugins.input.java.data.code.JavaInsnsRegister.get(JavaInsnsRegister.java:413)
            	at jadx.plugins.input.java.data.code.JavaCodeReader.visitInstructions(JavaCodeReader.java:73)
            	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:46)
            	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:158)
            	... 7 more
            */
        Constructor() {
            /*
            // Can't load method instructions: Load method exception: ArrayIndexOutOfBoundsException: null in method: jdk.nashorn.internal.objects.NativeObject.Constructor.<init>():void, file: L-out.jar:jdk/nashorn/internal/objects/NativeObject$Constructor.class
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeObject.Constructor.<init>():void");
        }
    }

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeObject$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object toString;
        private Object toLocaleString;
        private Object valueOf;
        private Object hasOwnProperty;
        private Object isPrototypeOf;
        private Object propertyIsEnumerable;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$toString() {
            return this.toString;
        }

        public void S$toString(Object obj) {
            this.toString = obj;
        }

        public Object G$toLocaleString() {
            return this.toLocaleString;
        }

        public void S$toLocaleString(Object obj) {
            this.toLocaleString = obj;
        }

        public Object G$valueOf() {
            return this.valueOf;
        }

        public void S$valueOf(Object obj) {
            this.valueOf = obj;
        }

        public Object G$hasOwnProperty() {
            return this.hasOwnProperty;
        }

        public void S$hasOwnProperty(Object obj) {
            this.hasOwnProperty = obj;
        }

        public Object G$isPrototypeOf() {
            return this.isPrototypeOf;
        }

        public void S$isPrototypeOf(Object obj) {
            this.isPrototypeOf = obj;
        }

        public Object G$propertyIsEnumerable() {
            return this.propertyIsEnumerable;
        }

        public void S$propertyIsEnumerable(Object obj) {
            this.propertyIsEnumerable = obj;
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
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
            	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:464)
            	at jadx.core.ProcessClass.process(ProcessClass.java:69)
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            */
        /*  JADX ERROR: Failed to decode insn: 0x0028: CONST
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            */
        /*  JADX ERROR: Failed to decode insn: 0x0032: CONST
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            */
        /*  JADX ERROR: Failed to decode insn: 0x003C: CONST
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
            	at jadx.core.ProcessClass.generateCode(ProcessClass.java:109)
            	at jadx.core.dex.nodes.ClassNode.generateClassCode(ClassNode.java:401)
            	at jadx.core.dex.nodes.ClassNode.decompile(ClassNode.java:389)
            	at jadx.core.dex.nodes.ClassNode.getCode(ClassNode.java:339)
            */
        Prototype() {
            /*
                r4 = this;
                r0 = r4
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeObject.Prototype.$nasgenmap$
                r0.<init>(r1)
                r0 = r4
                java.lang.String r1 = "toString"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r0 = r0 >>> r1
                r-1.toString = r0
                r-1 = r4
                java.lang.String r0 = "toLocaleString"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-1 = r-1 >>> r0
                r-2.toLocaleString = r-1
                r-2 = r4
                java.lang.String r-1 = "valueOf"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-2 = r-2 >>> r-1
                r-3.valueOf = r-2
                r-3 = r4
                java.lang.String r-2 = "hasOwnProperty"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-3 = r-3 >>> r-2
                r-4.hasOwnProperty = r-3
                r-4 = r4
                java.lang.String r-3 = "isPrototypeOf"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-4 = r-4 >>> r-3
                r-5.isPrototypeOf = r-4
                r-5 = r4
                java.lang.String r-4 = "propertyIsEnumerable"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                int r-5 = r-5 >>> r-4
                r-6.propertyIsEnumerable = r-5
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeObject.Prototype.<init>():void");
        }
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    static {
        $assertionsDisabled = !NativeObject.class.desiredAssertionStatus();
        GET__PROTO__ = findOwnMH("get__proto__", ScriptObject.class, new Class[]{Object.class});
        SET__PROTO__ = findOwnMH("set__proto__", Object.class, new Class[]{Object.class, Object.class});
        TO_STRING = new Object();
        MIRROR_GETTER_TYPE = MethodType.methodType((Class<?>) Object.class, (Class<?>) ScriptObjectMirror.class);
        MIRROR_SETTER_TYPE = MethodType.methodType(Object.class, ScriptObjectMirror.class, Object.class);
        $clinit$();
    }

    private static InvokeByName getTO_STRING() {
        return Global.instance().getInvokeByName(TO_STRING, new Callable() { // from class: jdk.nashorn.internal.objects.NativeObject.1
            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public InvokeByName call() {
                return new InvokeByName("toString", ScriptObject.class);
            }
        });
    }

    private static ScriptObject get__proto__(Object obj) {
        return Global.checkObject(Global.toObject(obj)).getProto();
    }

    private static Object set__proto__(Object obj, Object obj2) {
        Global.checkObjectCoercible(obj);
        if (!(obj instanceof ScriptObject)) {
            return ScriptRuntime.UNDEFINED;
        }
        ScriptObject scriptObject = (ScriptObject) obj;
        if (obj2 == null || (obj2 instanceof ScriptObject)) {
            scriptObject.setPrototypeOf(obj2);
        }
        return ScriptRuntime.UNDEFINED;
    }

    private NativeObject() {
        throw new UnsupportedOperationException();
    }

    private static ECMAException notAnObject(Object obj) {
        return ECMAErrors.typeError("not.an.object", new String[]{ScriptRuntime.safeToString(obj)});
    }

    public static ScriptObject setIndexedPropertiesToExternalArrayData(Object obj, Object obj2, Object obj3) {
        Global.checkObject(obj2);
        ScriptObject scriptObject = (ScriptObject) obj2;
        if (obj3 instanceof ByteBuffer) {
            scriptObject.setArray(ArrayData.allocate((ByteBuffer) obj3));
            return scriptObject;
        }
        throw ECMAErrors.typeError("not.a.bytebuffer", new String[]{"setIndexedPropertiesToExternalArrayData's buf argument"});
    }

    public static Object getPrototypeOf(Object obj, Object obj2) {
        if (obj2 instanceof ScriptObject) {
            return ((ScriptObject) obj2).getProto();
        }
        if (obj2 instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj2).getProto();
        }
        if (JSType.m12of(obj2) == JSType.OBJECT) {
            return null;
        }
        throw notAnObject(obj2);
    }

    public static Object setPrototypeOf(Object obj, Object obj2, Object obj3) {
        if (obj2 instanceof ScriptObject) {
            ((ScriptObject) obj2).setPrototypeOf(obj3);
            return obj2;
        }
        if (obj2 instanceof ScriptObjectMirror) {
            ((ScriptObjectMirror) obj2).setProto(obj3);
            return obj2;
        }
        throw notAnObject(obj2);
    }

    public static Object getOwnPropertyDescriptor(Object obj, Object obj2, Object obj3) {
        if (obj2 instanceof ScriptObject) {
            return ((ScriptObject) obj2).getOwnPropertyDescriptor(JSType.toString(obj3));
        }
        if (obj2 instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj2).getOwnPropertyDescriptor(JSType.toString(obj3));
        }
        throw notAnObject(obj2);
    }

    public static ScriptObject getOwnPropertyNames(Object obj, Object obj2) {
        if (obj2 instanceof ScriptObject) {
            return new NativeArray(((ScriptObject) obj2).getOwnKeys(true));
        }
        if (obj2 instanceof ScriptObjectMirror) {
            return new NativeArray(((ScriptObjectMirror) obj2).getOwnKeys(true));
        }
        throw notAnObject(obj2);
    }

    public static ScriptObject create(Object obj, Object obj2, Object obj3) {
        if (obj2 != null) {
            Global.checkObject(obj2);
        }
        ScriptObject scriptObjectNewEmptyInstance = Global.newEmptyInstance();
        scriptObjectNewEmptyInstance.setProto((ScriptObject) obj2);
        if (obj3 != ScriptRuntime.UNDEFINED) {
            defineProperties(obj, scriptObjectNewEmptyInstance, obj3);
        }
        return scriptObjectNewEmptyInstance;
    }

    public static ScriptObject defineProperty(Object obj, Object obj2, Object obj3, Object obj4) {
        ScriptObject scriptObjectCheckObject = Global.checkObject(obj2);
        scriptObjectCheckObject.defineOwnProperty(JSType.toString(obj3), obj4, true);
        return scriptObjectCheckObject;
    }

    public static ScriptObject defineProperties(Object obj, Object obj2, Object obj3) {
        ScriptObject scriptObjectCheckObject = Global.checkObject(obj2);
        Object object = Global.toObject(obj3);
        if (object instanceof ScriptObject) {
            for (String str : ((ScriptObject) object).getOwnKeys(false)) {
                String string = JSType.toString(str);
                scriptObjectCheckObject.defineOwnProperty(string, ((ScriptObject) object).get(string), true);
            }
        }
        return scriptObjectCheckObject;
    }

    public static Object seal(Object obj, Object obj2) {
        if (obj2 instanceof ScriptObject) {
            return ((ScriptObject) obj2).seal();
        }
        if (obj2 instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj2).seal();
        }
        throw notAnObject(obj2);
    }

    public static Object freeze(Object obj, Object obj2) {
        if (obj2 instanceof ScriptObject) {
            return ((ScriptObject) obj2).freeze();
        }
        if (obj2 instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj2).freeze();
        }
        throw notAnObject(obj2);
    }

    public static Object preventExtensions(Object obj, Object obj2) {
        if (obj2 instanceof ScriptObject) {
            return ((ScriptObject) obj2).preventExtensions();
        }
        if (obj2 instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj2).preventExtensions();
        }
        throw notAnObject(obj2);
    }

    public static boolean isSealed(Object obj, Object obj2) {
        if (obj2 instanceof ScriptObject) {
            return ((ScriptObject) obj2).isSealed();
        }
        if (obj2 instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj2).isSealed();
        }
        throw notAnObject(obj2);
    }

    public static boolean isFrozen(Object obj, Object obj2) {
        if (obj2 instanceof ScriptObject) {
            return ((ScriptObject) obj2).isFrozen();
        }
        if (obj2 instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj2).isFrozen();
        }
        throw notAnObject(obj2);
    }

    public static boolean isExtensible(Object obj, Object obj2) {
        if (obj2 instanceof ScriptObject) {
            return ((ScriptObject) obj2).isExtensible();
        }
        if (obj2 instanceof ScriptObjectMirror) {
            return ((ScriptObjectMirror) obj2).isExtensible();
        }
        throw notAnObject(obj2);
    }

    public static ScriptObject keys(Object obj, Object obj2) {
        if (obj2 instanceof ScriptObject) {
            return new NativeArray(((ScriptObject) obj2).getOwnKeys(false));
        }
        if (obj2 instanceof ScriptObjectMirror) {
            return new NativeArray(((ScriptObjectMirror) obj2).getOwnKeys(false));
        }
        throw notAnObject(obj2);
    }

    public static Object construct(boolean z, Object obj, Object obj2) {
        JSType jSTypeOfNoFunction = JSType.ofNoFunction(obj2);
        if (z || jSTypeOfNoFunction == JSType.NULL || jSTypeOfNoFunction == JSType.UNDEFINED) {
            switch (C01922.$SwitchMap$jdk$nashorn$internal$runtime$JSType[jSTypeOfNoFunction.ordinal()]) {
                case 1:
                case 2:
                case 3:
                    return Global.toObject(obj2);
                case 4:
                    return obj2;
                case 5:
                case 6:
                default:
                    return Global.newEmptyInstance();
            }
        }
        return Global.toObject(obj2);
    }

    /* renamed from: jdk.nashorn.internal.objects.NativeObject$2 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeObject$2.class */
    static /* synthetic */ class C01922 {
        static final int[] $SwitchMap$jdk$nashorn$internal$runtime$JSType = new int[JSType.values().length];

        static {
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSType[JSType.BOOLEAN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSType[JSType.NUMBER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSType[JSType.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSType[JSType.OBJECT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSType[JSType.NULL.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSType[JSType.UNDEFINED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static String toString(Object obj) {
        return ScriptRuntime.builtinObjectToString(obj);
    }

    public static Object toLocaleString(Object obj) {
        Object scriptObject = JSType.toScriptObject(obj);
        if (scriptObject instanceof ScriptObject) {
            InvokeByName to_string = getTO_STRING();
            ScriptObject scriptObject2 = (ScriptObject) scriptObject;
            try {
                Object objInvokeExact = (Object) to_string.getGetter().invokeExact(scriptObject2);
                if (Bootstrap.isCallable(objInvokeExact)) {
                    return (Object) to_string.getInvoker().invokeExact(objInvokeExact, scriptObject2);
                }
                throw ECMAErrors.typeError("not.a.function", new String[]{"toString"});
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable th) {
                throw new RuntimeException(th);
            }
        }
        return ScriptRuntime.builtinObjectToString(obj);
    }

    public static Object valueOf(Object obj) {
        return Global.toObject(obj);
    }

    public static boolean hasOwnProperty(Object obj, Object obj2) {
        Object primitive = JSType.toPrimitive(obj2, String.class);
        Object object = Global.toObject(obj);
        return (object instanceof ScriptObject) && ((ScriptObject) object).hasOwnProperty(primitive);
    }

    public static boolean isPrototypeOf(Object obj, Object obj2) {
        if (!(obj2 instanceof ScriptObject)) {
            return false;
        }
        Object object = Global.toObject(obj);
        ScriptObject proto = (ScriptObject) obj2;
        do {
            proto = proto.getProto();
            if (proto == object) {
                return true;
            }
        } while (proto != null);
        return false;
    }

    public static boolean propertyIsEnumerable(Object obj, Object obj2) {
        Property propertyFindProperty;
        String string = JSType.toString(obj2);
        Object object = Global.toObject(obj);
        return (object instanceof ScriptObject) && (propertyFindProperty = ((ScriptObject) object).getMap().findProperty(string)) != null && propertyFindProperty.isEnumerable();
    }

    public static Object bindProperties(Object obj, Object obj2, Object obj3) {
        ScriptObject scriptObjectCheckObject = Global.checkObject(obj2);
        Global.checkObjectCoercible(obj3);
        if (obj3 instanceof ScriptObject) {
            ScriptObject scriptObject = (ScriptObject) obj3;
            Property[] properties = scriptObject.getMap().getProperties();
            ArrayList arrayList = new ArrayList();
            for (Property property : properties) {
                if (property.isEnumerable()) {
                    Object obj4 = scriptObject.get(property.getKey());
                    property.setType(Object.class);
                    property.setValue(scriptObject, scriptObject, obj4, false);
                    arrayList.add(property);
                }
            }
            if (!arrayList.isEmpty()) {
                scriptObjectCheckObject.addBoundProperties(scriptObject, (Property[]) arrayList.toArray(new Property[arrayList.size()]));
            }
        } else if (obj3 instanceof ScriptObjectMirror) {
            String[] ownKeys = ((ScriptObjectMirror) obj3).getOwnKeys(false);
            if (ownKeys.length == 0) {
                return obj2;
            }
            AccessorProperty[] accessorPropertyArr = new AccessorProperty[ownKeys.length];
            for (int i = 0; i < ownKeys.length; i++) {
                String str = ownKeys[i];
                accessorPropertyArr[i] = AccessorProperty.create(str, 0, Bootstrap.createDynamicInvoker("dyn:getMethod|getProp|getElem:" + str, MIRROR_GETTER_TYPE), Bootstrap.createDynamicInvoker("dyn:setProp|setElem:" + str, MIRROR_SETTER_TYPE));
            }
            scriptObjectCheckObject.addBoundProperties(obj3, accessorPropertyArr);
        } else if (obj3 instanceof StaticClass) {
            Class representedClass = ((StaticClass) obj3).getRepresentedClass();
            Bootstrap.checkReflectionAccess(representedClass, true);
            bindBeanProperties(scriptObjectCheckObject, obj3, BeansLinker.getReadableStaticPropertyNames(representedClass), BeansLinker.getWritableStaticPropertyNames(representedClass), BeansLinker.getStaticMethodNames(representedClass));
        } else {
            Class<?> cls = obj3.getClass();
            Bootstrap.checkReflectionAccess(cls, false);
            bindBeanProperties(scriptObjectCheckObject, obj3, BeansLinker.getReadableInstancePropertyNames(cls), BeansLinker.getWritableInstancePropertyNames(cls), BeansLinker.getInstanceMethodNames(cls));
        }
        return obj2;
    }

    public static Object bindAllProperties(ScriptObject scriptObject, ScriptObjectMirror scriptObjectMirror) {
        Set<String> setKeySet = scriptObjectMirror.keySet();
        AccessorProperty[] accessorPropertyArr = new AccessorProperty[setKeySet.size()];
        int i = 0;
        for (String str : setKeySet) {
            accessorPropertyArr[i] = AccessorProperty.create(str, 0, Bootstrap.createDynamicInvoker("dyn:getMethod|getProp|getElem:" + str, MIRROR_GETTER_TYPE), Bootstrap.createDynamicInvoker("dyn:setProp|setElem:" + str, MIRROR_SETTER_TYPE));
            i++;
        }
        scriptObject.addBoundProperties(scriptObjectMirror, accessorPropertyArr);
        return scriptObject;
    }

    private static void bindBeanProperties(ScriptObject scriptObject, Object obj, Collection collection, Collection collection2, Collection collection3) {
        MethodHandle beanOperation;
        MethodHandle beanOperation2;
        HashSet<String> hashSet = new HashSet(collection);
        hashSet.addAll(collection2);
        Class<?> cls = obj.getClass();
        MethodType methodType = MethodType.methodType((Class<?>) Object.class, cls);
        MethodType methodType2 = MethodType.methodType(Object.class, cls, Object.class);
        TypeBasedGuardingDynamicLinker linkerForClass = BeansLinker.getLinkerForClass(cls);
        ArrayList arrayList = new ArrayList(hashSet.size() + collection3.size());
        Iterator it = collection3.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            try {
                arrayList.add(AccessorProperty.create(str, 1, getBoundBeanMethodGetter(obj, getBeanOperation(linkerForClass, "dyn:getMethod:" + str, methodType, obj)), Lookup.EMPTY_SETTER));
            } catch (IllegalAccessError unused) {
            }
        }
        for (String str2 : hashSet) {
            if (collection.contains(str2)) {
                try {
                    beanOperation = getBeanOperation(linkerForClass, "dyn:getProp:" + str2, methodType, obj);
                } catch (IllegalAccessError unused2) {
                    beanOperation = Lookup.EMPTY_GETTER;
                }
            } else {
                beanOperation = Lookup.EMPTY_GETTER;
            }
            boolean zContains = collection2.contains(str2);
            if (zContains) {
                try {
                    beanOperation2 = getBeanOperation(linkerForClass, "dyn:setProp:" + str2, methodType2, obj);
                } catch (IllegalAccessError unused3) {
                    beanOperation2 = Lookup.EMPTY_SETTER;
                }
            } else {
                beanOperation2 = Lookup.EMPTY_SETTER;
            }
            if (beanOperation != Lookup.EMPTY_GETTER || beanOperation2 != Lookup.EMPTY_SETTER) {
                arrayList.add(AccessorProperty.create(str2, zContains ? 0 : 1, beanOperation, beanOperation2));
            }
        }
        scriptObject.addBoundProperties(obj, (AccessorProperty[]) arrayList.toArray(new AccessorProperty[arrayList.size()]));
    }

    private static MethodHandle getBoundBeanMethodGetter(Object obj, MethodHandle methodHandle) {
        try {
            return MethodHandles.dropArguments(MethodHandles.constant(Object.class, Bootstrap.bindCallable((Object) methodHandle.invoke(obj), obj, null)), 0, (Class<?>[]) new Class[]{Object.class});
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    private static MethodHandle getBeanOperation(GuardingDynamicLinker guardingDynamicLinker, String str, MethodType methodType, Object obj) {
        try {
            GuardedInvocation guardedInvocation = NashornBeansLinker.getGuardedInvocation(guardingDynamicLinker, createLinkRequest(str, methodType, obj), Bootstrap.getLinkerServices());
            if (!$assertionsDisabled && !passesGuard(obj, guardedInvocation.getGuard())) {
                throw new AssertionError();
            }
            if ($assertionsDisabled || guardedInvocation.getSwitchPoints() == null) {
                return guardedInvocation.getInvocation();
            }
            throw new AssertionError();
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    private static boolean passesGuard(Object obj, MethodHandle methodHandle) {
        return methodHandle == null || (boolean) methodHandle.invoke(obj);
    }

    private static LinkRequest createLinkRequest(String str, MethodType methodType, Object obj) {
        return new LinkRequestImpl(CallSiteDescriptorFactory.create(MethodHandles.publicLookup(), str, methodType), null, 0, false, new Object[]{obj});
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), NativeObject.class, str, Lookup.f31MH.type(cls, clsArr));
    }
}
