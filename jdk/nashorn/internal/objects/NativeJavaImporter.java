package jdk.nashorn.internal.objects;

import java.util.Collections;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.NativeJavaPackage;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;

/* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeJavaImporter.class */
public final class NativeJavaImporter extends ScriptObject {
    private final Object[] args;
    private static PropertyMap $nasgenmap$;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeJavaImporter$Constructor.class */
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
        Constructor() {
            /*
                r5 = this;
                r0 = r5
                java.lang.String r1 = "JavaImporter"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r-2.<init>(r-1, r0, r1)
                r-2 = r5
                jdk.nashorn.internal.objects.NativeJavaImporter$Prototype r-1 = new jdk.nashorn.internal.objects.NativeJavaImporter$Prototype
                r0 = r-1
                r0.<init>()
                r0 = r-1
                r1 = r5
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r0, r1)
                r-2.setPrototype(r-1)
                r-2 = r5
                r-1 = 1
                r-2.setArity(r-1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeJavaImporter.Constructor.<init>():void");
        }
    }

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeJavaImporter$Prototype.class */
    final class Prototype extends PrototypeObject {
        private Object __noSuchProperty__;
        private Object __noSuchMethod__;
        private static final PropertyMap $nasgenmap$ = null;

        public Object G$__noSuchProperty__() {
            return this.__noSuchProperty__;
        }

        public void S$__noSuchProperty__(Object obj) {
            this.__noSuchProperty__ = obj;
        }

        public Object G$__noSuchMethod__() {
            return this.__noSuchMethod__;
        }

        public void S$__noSuchMethod__(Object obj) {
            this.__noSuchMethod__ = obj;
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
                jdk.nashorn.internal.runtime.PropertyMap r1 = jdk.nashorn.internal.objects.NativeJavaImporter.Prototype.$nasgenmap$
                r0.<init>(r1)
                r0 = r4
                java.lang.String r1 = "__noSuchProperty__"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r5 = r1
                r-1.__noSuchProperty__ = r0
                r-1 = r4
                java.lang.String r0 = "__noSuchMethod__"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r5 = r0
                r-2.__noSuchMethod__ = r-1
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeJavaImporter.Prototype.<init>():void");
        }
    }

    static {
        $clinit$();
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    private NativeJavaImporter(Object[] objArr, ScriptObject scriptObject, PropertyMap propertyMap) {
        super(scriptObject, propertyMap);
        this.args = objArr;
    }

    private NativeJavaImporter(Object[] objArr, Global global) {
        this(objArr, global.getJavaImporterPrototype(), $nasgenmap$);
    }

    private NativeJavaImporter(Object[] objArr) {
        this(objArr, Global.instance());
    }

    public static NativeJavaImporter constructor(boolean z, Object obj, Object[] objArr) {
        return new NativeJavaImporter(objArr);
    }

    public static Object __noSuchProperty__(Object obj, Object obj2) {
        if (!(obj instanceof NativeJavaImporter)) {
            throw ECMAErrors.typeError("not.a.java.importer", new String[]{ScriptRuntime.safeToString(obj)});
        }
        return ((NativeJavaImporter) obj).createProperty(JSType.toString(obj2));
    }

    public static Object __noSuchMethod__(Object obj, Object[] objArr) {
        throw ECMAErrors.typeError("not.a.function", new String[]{ScriptRuntime.safeToString(objArr[0])});
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation noSuchProperty(CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        return createAndSetProperty(callSiteDescriptor) ? super.lookup(callSiteDescriptor, linkRequest) : super.noSuchProperty(callSiteDescriptor, linkRequest);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public GuardedInvocation noSuchMethod(CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        return createAndSetProperty(callSiteDescriptor) ? super.lookup(callSiteDescriptor, linkRequest) : super.noSuchMethod(callSiteDescriptor, linkRequest);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected Object invokeNoSuchProperty(String str, boolean z, int i) {
        Object objCreateProperty = createProperty(str);
        if (UnwarrantedOptimismException.isValid(i)) {
            throw new UnwarrantedOptimismException(objCreateProperty, i);
        }
        return objCreateProperty;
    }

    private boolean createAndSetProperty(CallSiteDescriptor callSiteDescriptor) {
        String nameToken = callSiteDescriptor.getNameToken(2);
        Object objCreateProperty = createProperty(nameToken);
        if (objCreateProperty != null) {
            set(nameToken, objCreateProperty, 0);
            return true;
        }
        return false;
    }

    private Object createProperty(String str) {
        for (int length = this.args.length - 1; length > -1; length--) {
            Object obj = this.args[length];
            if (obj instanceof StaticClass) {
                if (((StaticClass) obj).getRepresentedClass().getSimpleName().equals(str)) {
                    return obj;
                }
            } else if (obj instanceof NativeJavaPackage) {
                String name = ((NativeJavaPackage) obj).getName();
                try {
                    return StaticClass.forClass(Global.instance().getContext().findClass(name.isEmpty() ? str : name + "." + str));
                } catch (ClassNotFoundException unused) {
                }
            } else {
                continue;
            }
        }
        return null;
    }
}
