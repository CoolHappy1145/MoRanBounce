package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.internal.dynalink.linker.LinkRequest;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.FindProperty;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.PrototypeObject;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
import jdk.nashorn.internal.scripts.C0277JO;
import org.apache.log4j.spi.Configurator;

/* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeJSAdapter.class */
public final class NativeJSAdapter extends ScriptObject {
    public static final String __get__ = "__get__";
    public static final String __put__ = "__put__";
    public static final String __call__ = "__call__";
    public static final String __new__ = "__new__";
    public static final String __getIds__ = "__getIds__";
    public static final String __getKeys__ = "__getKeys__";
    public static final String __getValues__ = "__getValues__";
    public static final String __has__ = "__has__";
    public static final String __delete__ = "__delete__";
    public static final String __preventExtensions__ = "__preventExtensions__";
    public static final String __isExtensible__ = "__isExtensible__";
    public static final String __seal__ = "__seal__";
    public static final String __isSealed__ = "__isSealed__";
    public static final String __freeze__ = "__freeze__";
    public static final String __isFrozen__ = "__isFrozen__";
    private final ScriptObject adaptee;
    private final boolean overrides;
    private static final MethodHandle IS_JSADAPTOR = findOwnMH("isJSAdaptor", Boolean.TYPE, new Class[]{Object.class, Object.class, MethodHandle.class, Object.class, ScriptFunction.class});
    private static PropertyMap $nasgenmap$;

    /*  JADX ERROR: NullPointerException in pass: ProcessKotlinInternals
        java.lang.NullPointerException
        */
    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeJSAdapter$Constructor.class */
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
                java.lang.String r1 = "JSAdapter"
                // decode failed: Unsupported constant type: METHOD_HANDLE
                r-2.<init>(r-1, r0, r1)
                r-2 = r5
                jdk.nashorn.internal.objects.NativeJSAdapter$Prototype r-1 = new jdk.nashorn.internal.objects.NativeJSAdapter$Prototype
                r0 = r-1
                r0.<init>()
                r0 = r-1
                r1 = r5
                jdk.nashorn.internal.runtime.PrototypeObject.setConstructor(r0, r1)
                r-2.setPrototype(r-1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.objects.NativeJSAdapter.Constructor.<init>():void");
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeJSAdapter$Prototype.class */
    final class Prototype extends PrototypeObject {
        Prototype() {
        }
    }

    public static void $clinit$() {
        $nasgenmap$ = PropertyMap.newMap(Collections.EMPTY_LIST);
    }

    static {
        $clinit$();
    }

    NativeJSAdapter(Object obj, ScriptObject scriptObject, ScriptObject scriptObject2, PropertyMap propertyMap) {
        super(scriptObject2, propertyMap);
        this.adaptee = wrapAdaptee(scriptObject);
        if (obj instanceof ScriptObject) {
            this.overrides = true;
            addBoundProperties((ScriptObject) obj);
        } else {
            this.overrides = false;
        }
    }

    private static ScriptObject wrapAdaptee(ScriptObject scriptObject) {
        return new C0277JO(scriptObject);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(Object obj, int i) {
        return (this.overrides && super.hasOwnProperty(obj)) ? super.getInt(obj, i) : callAdapteeInt(i, __get__, new Object[]{obj});
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(double d, int i) {
        return (this.overrides && super.hasOwnProperty(d)) ? super.getInt(d, i) : callAdapteeInt(i, __get__, new Object[]{Double.valueOf(d)});
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public int getInt(int i, int i2) {
        return (this.overrides && super.hasOwnProperty(i)) ? super.getInt(i, i2) : callAdapteeInt(i2, __get__, new Object[]{Integer.valueOf(i)});
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(Object obj, int i) {
        return (this.overrides && super.hasOwnProperty(obj)) ? super.getDouble(obj, i) : callAdapteeDouble(i, __get__, new Object[]{obj});
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(double d, int i) {
        return (this.overrides && super.hasOwnProperty(d)) ? super.getDouble(d, i) : callAdapteeDouble(i, __get__, new Object[]{Double.valueOf(d)});
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public double getDouble(int i, int i2) {
        return (this.overrides && super.hasOwnProperty(i)) ? super.getDouble(i, i2) : callAdapteeDouble(i2, __get__, new Object[]{Integer.valueOf(i)});
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(Object obj) {
        return (this.overrides && super.hasOwnProperty(obj)) ? super.get(obj) : callAdaptee(__get__, new Object[]{obj});
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(double d) {
        return (this.overrides && super.hasOwnProperty(d)) ? super.get(d) : callAdaptee(__get__, new Object[]{Double.valueOf(d)});
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public Object get(int i) {
        return (this.overrides && super.hasOwnProperty(i)) ? super.get(i) : callAdaptee(__get__, new Object[]{Integer.valueOf(i)});
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(Object obj, int i, int i2) {
        if (this.overrides && super.hasOwnProperty(obj)) {
            super.set(obj, i, i2);
        } else {
            callAdaptee(__put__, new Object[]{obj, Integer.valueOf(i), Integer.valueOf(i2)});
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(Object obj, double d, int i) {
        if (this.overrides && super.hasOwnProperty(obj)) {
            super.set(obj, d, i);
        } else {
            callAdaptee(__put__, new Object[]{obj, Double.valueOf(d), Integer.valueOf(i)});
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(Object obj, Object obj2, int i) {
        if (this.overrides && super.hasOwnProperty(obj)) {
            super.set(obj, obj2, i);
        } else {
            callAdaptee(__put__, new Object[]{obj, obj2, Integer.valueOf(i)});
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(double d, int i, int i2) {
        if (this.overrides && super.hasOwnProperty(d)) {
            super.set(d, i, i2);
        } else {
            callAdaptee(__put__, new Object[]{Double.valueOf(d), Integer.valueOf(i), Integer.valueOf(i2)});
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(double d, double d2, int i) {
        if (this.overrides && super.hasOwnProperty(d)) {
            super.set(d, d2, i);
        } else {
            callAdaptee(__put__, new Object[]{Double.valueOf(d), Double.valueOf(d2), Integer.valueOf(i)});
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(double d, Object obj, int i) {
        if (this.overrides && super.hasOwnProperty(d)) {
            super.set(d, obj, i);
        } else {
            callAdaptee(__put__, new Object[]{Double.valueOf(d), obj, Integer.valueOf(i)});
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(int i, int i2, int i3) {
        if (this.overrides && super.hasOwnProperty(i)) {
            super.set(i, i2, i3);
        } else {
            callAdaptee(__put__, new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)});
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(int i, double d, int i2) {
        if (this.overrides && super.hasOwnProperty(i)) {
            super.set(i, d, i2);
        } else {
            callAdaptee(__put__, new Object[]{Integer.valueOf(i), Double.valueOf(d), Integer.valueOf(i2)});
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public void set(int i, Object obj, int i2) {
        if (this.overrides && super.hasOwnProperty(i)) {
            super.set(i, obj, i2);
        } else {
            callAdaptee(__put__, new Object[]{Integer.valueOf(i), obj, Integer.valueOf(i2)});
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(Object obj) {
        if (this.overrides && super.hasOwnProperty(obj)) {
            return true;
        }
        return JSType.toBoolean(callAdaptee(Boolean.FALSE, __has__, new Object[]{obj}));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(int i) {
        if (this.overrides && super.hasOwnProperty(i)) {
            return true;
        }
        return JSType.toBoolean(callAdaptee(Boolean.FALSE, __has__, new Object[]{Integer.valueOf(i)}));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean has(double d) {
        if (this.overrides && super.hasOwnProperty(d)) {
            return true;
        }
        return JSType.toBoolean(callAdaptee(Boolean.FALSE, __has__, new Object[]{Double.valueOf(d)}));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(int i, boolean z) {
        if (this.overrides && super.hasOwnProperty(i)) {
            return super.delete(i, z);
        }
        return JSType.toBoolean(callAdaptee(Boolean.TRUE, __delete__, new Object[]{Integer.valueOf(i), Boolean.valueOf(z)}));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(double d, boolean z) {
        if (this.overrides && super.hasOwnProperty(d)) {
            return super.delete(d, z);
        }
        return JSType.toBoolean(callAdaptee(Boolean.TRUE, __delete__, new Object[]{Double.valueOf(d), Boolean.valueOf(z)}));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(Object obj, boolean z) {
        if (this.overrides && super.hasOwnProperty(obj)) {
            return super.delete(obj, z);
        }
        return JSType.toBoolean(callAdaptee(Boolean.TRUE, __delete__, new Object[]{obj, Boolean.valueOf(z)}));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Iterator propertyIterator() {
        Object nativeArray;
        Object obj = this.adaptee.get(__getIds__);
        if (!(obj instanceof ScriptFunction)) {
            obj = this.adaptee.get(__getKeys__);
        }
        if (obj instanceof ScriptFunction) {
            nativeArray = ScriptRuntime.apply((ScriptFunction) obj, this.adaptee, new Object[0]);
        } else {
            nativeArray = new NativeArray(0L);
        }
        ArrayList arrayList = new ArrayList();
        ArrayLikeIterator arrayLikeIterator = ArrayLikeIterator.arrayLikeIterator(nativeArray);
        while (arrayLikeIterator.hasNext()) {
            arrayList.add((String) arrayLikeIterator.next());
        }
        return arrayList.iterator();
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Iterator valueIterator() {
        return ArrayLikeIterator.arrayLikeIterator(callAdaptee(new NativeArray(0L), __getValues__, new Object[0]));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public ScriptObject preventExtensions() {
        callAdaptee(__preventExtensions__, new Object[0]);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public boolean isExtensible() {
        return JSType.toBoolean(callAdaptee(Boolean.TRUE, __isExtensible__, new Object[0]));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public ScriptObject seal() {
        callAdaptee(__seal__, new Object[0]);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public boolean isSealed() {
        return JSType.toBoolean(callAdaptee(Boolean.FALSE, __isSealed__, new Object[0]));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public ScriptObject freeze() {
        callAdaptee(__freeze__, new Object[0]);
        return this;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public boolean isFrozen() {
        return JSType.toBoolean(callAdaptee(Boolean.FALSE, __isFrozen__, new Object[0]));
    }

    public static NativeJSAdapter construct(boolean z, Object obj, Object[] objArr) {
        Object obj2;
        Object jSAdapterPrototype = ScriptRuntime.UNDEFINED;
        Object obj3 = ScriptRuntime.UNDEFINED;
        if (objArr == null || objArr.length == 0) {
            throw ECMAErrors.typeError("not.an.object", new String[]{Configurator.NULL});
        }
        switch (objArr.length) {
            case 1:
                obj2 = objArr[0];
                break;
            case 2:
                obj3 = objArr[0];
                obj2 = objArr[1];
                break;
            case 3:
            default:
                jSAdapterPrototype = objArr[0];
                obj3 = objArr[1];
                obj2 = objArr[2];
                break;
        }
        if (!(obj2 instanceof ScriptObject)) {
            throw ECMAErrors.typeError("not.an.object", new String[]{ScriptRuntime.safeToString(obj2)});
        }
        Global globalInstance = Global.instance();
        if (jSAdapterPrototype != null && !(jSAdapterPrototype instanceof ScriptObject)) {
            jSAdapterPrototype = globalInstance.getJSAdapterPrototype();
        }
        return new NativeJSAdapter(obj3, (ScriptObject) obj2, (ScriptObject) jSAdapterPrototype, $nasgenmap$);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected GuardedInvocation findNewMethod(CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        return findHook(callSiteDescriptor, __new__, false);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected GuardedInvocation findCallMethodMethod(CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        if (this.overrides && super.hasOwnProperty(callSiteDescriptor.getNameToken(2))) {
            try {
                GuardedInvocation guardedInvocationFindCallMethodMethod = super.findCallMethodMethod(callSiteDescriptor, linkRequest);
                if (guardedInvocationFindCallMethodMethod != null) {
                    return guardedInvocationFindCallMethodMethod;
                }
            } catch (Exception unused) {
            }
        }
        return findHook(callSiteDescriptor, __call__);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected GuardedInvocation findGetMethod(CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest, String str) {
        String nameToken;
        nameToken = callSiteDescriptor.getNameToken(2);
        if (this.overrides && super.hasOwnProperty(nameToken)) {
            try {
                GuardedInvocation guardedInvocationFindGetMethod = super.findGetMethod(callSiteDescriptor, linkRequest, str);
                if (guardedInvocationFindGetMethod != null) {
                    return guardedInvocationFindGetMethod;
                }
            } catch (Exception unused) {
            }
        }
        switch (str) {
            case "getProp":
            case "getElem":
                return findHook(callSiteDescriptor, __get__);
            case "getMethod":
                FindProperty findProperty = this.adaptee.findProperty(__call__, true);
                if (findProperty != null) {
                    Object objectValue = findProperty.getObjectValue();
                    if (objectValue instanceof ScriptFunction) {
                        return new GuardedInvocation(Lookup.f31MH.dropArguments(Lookup.f31MH.constant(Object.class, ((ScriptFunction) objectValue).createBound(this, new Object[]{nameToken})), 0, new Class[]{Object.class}), testJSAdaptor(this.adaptee, null, null, null), this.adaptee.getProtoSwitchPoints(__call__, findProperty.getOwner()), (Class) null);
                    }
                }
                throw ECMAErrors.typeError("no.such.function", new String[]{callSiteDescriptor.getNameToken(2), ScriptRuntime.safeToString(this)});
            default:
                throw new AssertionError("should not reach here");
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    protected GuardedInvocation findSetMethod(CallSiteDescriptor callSiteDescriptor, LinkRequest linkRequest) {
        if (this.overrides && super.hasOwnProperty(callSiteDescriptor.getNameToken(2))) {
            try {
                GuardedInvocation guardedInvocationFindSetMethod = super.findSetMethod(callSiteDescriptor, linkRequest);
                if (guardedInvocationFindSetMethod != null) {
                    return guardedInvocationFindSetMethod;
                }
            } catch (Exception unused) {
            }
        }
        return findHook(callSiteDescriptor, __put__);
    }

    private Object callAdaptee(String str, Object[] objArr) {
        return callAdaptee(ScriptRuntime.UNDEFINED, str, objArr);
    }

    private double callAdapteeDouble(int i, String str, Object[] objArr) {
        return JSType.toNumberMaybeOptimistic(callAdaptee(str, objArr), i);
    }

    private int callAdapteeInt(int i, String str, Object[] objArr) {
        return JSType.toInt32MaybeOptimistic(callAdaptee(str, objArr), i);
    }

    private Object callAdaptee(Object obj, String str, Object[] objArr) {
        Object obj2 = this.adaptee.get(str);
        if (obj2 instanceof ScriptFunction) {
            return ScriptRuntime.apply((ScriptFunction) obj2, this.adaptee, objArr);
        }
        return obj;
    }

    private GuardedInvocation findHook(CallSiteDescriptor callSiteDescriptor, String str) {
        return findHook(callSiteDescriptor, str, true);
    }

    private GuardedInvocation findHook(CallSiteDescriptor callSiteDescriptor, String str, boolean z) {
        MethodType methodType;
        MethodHandle methodHandleEmptyGetter;
        FindProperty findProperty = this.adaptee.findProperty(str, true);
        methodType = callSiteDescriptor.getMethodType();
        if (findProperty != null) {
            String nameToken = callSiteDescriptor.getNameTokenCount() > 2 ? callSiteDescriptor.getNameToken(2) : null;
            Object objectValue = findProperty.getObjectValue();
            if (objectValue instanceof ScriptFunction) {
                ScriptFunction scriptFunction = (ScriptFunction) objectValue;
                MethodHandle callMethodHandle = getCallMethodHandle(findProperty, methodType, z ? nameToken : null);
                if (callMethodHandle != null) {
                    return new GuardedInvocation(callMethodHandle, testJSAdaptor(this.adaptee, findProperty.getGetter(Object.class, -1, null), findProperty.getOwner(), scriptFunction), this.adaptee.getProtoSwitchPoints(str, findProperty.getOwner()), (Class) null);
                }
            }
        }
        switch (str) {
            case "__call__":
                throw ECMAErrors.typeError("no.such.function", new String[]{callSiteDescriptor.getNameToken(2), ScriptRuntime.safeToString(this)});
            default:
                if (str.equals(__put__)) {
                    methodHandleEmptyGetter = Lookup.f31MH.asType(Lookup.EMPTY_SETTER, methodType);
                } else {
                    methodHandleEmptyGetter = Lookup.emptyGetter((Class) methodType.returnType());
                }
                return new GuardedInvocation(methodHandleEmptyGetter, testJSAdaptor(this.adaptee, null, null, null), this.adaptee.getProtoSwitchPoints(str, null), (Class) null);
        }
    }

    private static MethodHandle testJSAdaptor(Object obj, MethodHandle methodHandle, Object obj2, ScriptFunction scriptFunction) {
        return Lookup.f31MH.insertArguments(IS_JSADAPTOR, 1, new Object[]{obj, methodHandle, obj2, scriptFunction});
    }

    private static boolean isJSAdaptor(Object obj, Object obj2, MethodHandle methodHandle, Object obj3, ScriptFunction scriptFunction) {
        boolean z = (obj instanceof NativeJSAdapter) && ((NativeJSAdapter) obj).getAdaptee() == obj2;
        if (z && methodHandle != null) {
            try {
                return (Object) methodHandle.invokeExact(obj3) == scriptFunction;
            } catch (Error | RuntimeException e) {
                throw e;
            } catch (Throwable th) {
                throw new RuntimeException(th);
            }
        }
        return z;
    }

    public ScriptObject getAdaptee() {
        return this.adaptee;
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), NativeJSAdapter.class, str, Lookup.f31MH.type(cls, clsArr));
    }
}
