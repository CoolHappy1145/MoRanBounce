package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.concurrent.Callable;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/UserAccessorProperty.class */
public final class UserAccessorProperty extends SpillProperty {
    private static final long serialVersionUID = -5928687246526840321L;
    private static final MethodHandles.Lookup LOOKUP;
    private static final MethodHandle INVOKE_OBJECT_GETTER;
    private static final MethodHandle INVOKE_INT_GETTER;
    private static final MethodHandle INVOKE_NUMBER_GETTER;
    private static final MethodHandle INVOKE_OBJECT_SETTER;
    private static final MethodHandle INVOKE_INT_SETTER;
    private static final MethodHandle INVOKE_NUMBER_SETTER;
    private static final Object OBJECT_GETTER_INVOKER_KEY;
    private static final Object OBJECT_SETTER_INVOKER_KEY;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !UserAccessorProperty.class.desiredAssertionStatus();
        LOOKUP = MethodHandles.lookup();
        INVOKE_OBJECT_GETTER = findOwnMH_S("invokeObjectGetter", Object.class, new Class[]{Accessors.class, MethodHandle.class, Object.class});
        INVOKE_INT_GETTER = findOwnMH_S("invokeIntGetter", Integer.TYPE, new Class[]{Accessors.class, MethodHandle.class, Integer.TYPE, Object.class});
        INVOKE_NUMBER_GETTER = findOwnMH_S("invokeNumberGetter", Double.TYPE, new Class[]{Accessors.class, MethodHandle.class, Integer.TYPE, Object.class});
        INVOKE_OBJECT_SETTER = findOwnMH_S("invokeObjectSetter", Void.TYPE, new Class[]{Accessors.class, MethodHandle.class, String.class, Object.class, Object.class});
        INVOKE_INT_SETTER = findOwnMH_S("invokeIntSetter", Void.TYPE, new Class[]{Accessors.class, MethodHandle.class, String.class, Object.class, Integer.TYPE});
        INVOKE_NUMBER_SETTER = findOwnMH_S("invokeNumberSetter", Void.TYPE, new Class[]{Accessors.class, MethodHandle.class, String.class, Object.class, Double.TYPE});
        OBJECT_GETTER_INVOKER_KEY = new Object();
        OBJECT_SETTER_INVOKER_KEY = new Object();
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/UserAccessorProperty$Accessors.class */
    static final class Accessors {
        Object getter;
        Object setter;

        Accessors(Object obj, Object obj2) {
            set(obj, obj2);
        }

        final void set(Object obj, Object obj2) {
            this.getter = obj;
            this.setter = obj2;
        }

        public String toString() {
            return "[getter=" + this.getter + " setter=" + this.setter + ']';
        }
    }

    private static MethodHandle getObjectGetterInvoker() {
        return Context.getGlobal().getDynamicInvoker(OBJECT_GETTER_INVOKER_KEY, new Callable() { // from class: jdk.nashorn.internal.runtime.UserAccessorProperty.1
            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public MethodHandle call() {
                return UserAccessorProperty.getINVOKE_UA_GETTER(Object.class, -1);
            }
        });
    }

    static MethodHandle getINVOKE_UA_GETTER(Class cls, int i) {
        if (UnwarrantedOptimismException.isValid(i)) {
            return Bootstrap.createDynamicInvoker("dyn:call", 8 | (i << 11), cls, new Class[]{Object.class, Object.class});
        }
        return Bootstrap.createDynamicInvoker("dyn:call", Object.class, new Class[]{Object.class, Object.class});
    }

    private static MethodHandle getObjectSetterInvoker() {
        return Context.getGlobal().getDynamicInvoker(OBJECT_SETTER_INVOKER_KEY, new Callable() { // from class: jdk.nashorn.internal.runtime.UserAccessorProperty.2
            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public MethodHandle call() {
                return UserAccessorProperty.getINVOKE_UA_SETTER(Object.class);
            }
        });
    }

    static MethodHandle getINVOKE_UA_SETTER(Class cls) {
        return Bootstrap.createDynamicInvoker("dyn:call", Void.TYPE, new Class[]{Object.class, Object.class, cls});
    }

    UserAccessorProperty(String str, int i, int i2) {
        super(str, i, i2);
    }

    private UserAccessorProperty(UserAccessorProperty userAccessorProperty) {
        super(userAccessorProperty);
    }

    private UserAccessorProperty(UserAccessorProperty userAccessorProperty, Class cls) {
        super(userAccessorProperty, cls);
    }

    @Override // jdk.nashorn.internal.runtime.SpillProperty, jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    public Property copy() {
        return new UserAccessorProperty(this);
    }

    @Override // jdk.nashorn.internal.runtime.SpillProperty, jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    public Property copy(Class cls) {
        return new UserAccessorProperty(this, cls);
    }

    void setAccessors(ScriptObject scriptObject, PropertyMap propertyMap, Accessors accessors) {
        try {
            (void) super.getSetter(Object.class, propertyMap).invokeExact(scriptObject, accessors);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    Accessors getAccessors(ScriptObject scriptObject) {
        try {
            return (Accessors) (Object) super.getGetter(Object.class).invokeExact(scriptObject);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    public boolean hasGetterFunction(ScriptObject scriptObject) {
        return getAccessors(scriptObject).getter != null;
    }

    public boolean hasSetterFunction(ScriptObject scriptObject) {
        return getAccessors(scriptObject).setter != null;
    }

    @Override // jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    public int getIntValue(ScriptObject scriptObject, ScriptObject scriptObject2) {
        return ((Integer) getObjectValue(scriptObject, scriptObject2)).intValue();
    }

    @Override // jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    public double getDoubleValue(ScriptObject scriptObject, ScriptObject scriptObject2) {
        return ((Double) getObjectValue(scriptObject, scriptObject2)).doubleValue();
    }

    @Override // jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    public Object getObjectValue(ScriptObject scriptObject, ScriptObject scriptObject2) {
        try {
            return invokeObjectGetter(getAccessors(scriptObject2 != null ? scriptObject2 : scriptObject), getObjectGetterInvoker(), scriptObject);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    @Override // jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    public void setValue(ScriptObject scriptObject, ScriptObject scriptObject2, int i, boolean z) {
        setValue(scriptObject, scriptObject2, Integer.valueOf(i), z);
    }

    @Override // jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    public void setValue(ScriptObject scriptObject, ScriptObject scriptObject2, double d, boolean z) {
        setValue(scriptObject, scriptObject2, Double.valueOf(d), z);
    }

    @Override // jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    public void setValue(ScriptObject scriptObject, ScriptObject scriptObject2, Object obj, boolean z) {
        try {
            invokeObjectSetter(getAccessors(scriptObject2 != null ? scriptObject2 : scriptObject), getObjectSetterInvoker(), z ? getKey() : null, scriptObject, obj);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    @Override // jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    public MethodHandle getGetter(Class cls) {
        return Lookup.filterReturnType(INVOKE_OBJECT_GETTER, cls);
    }

    @Override // jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    public MethodHandle getOptimisticGetter(Class cls, int i) {
        if (cls == Integer.TYPE) {
            return INVOKE_INT_GETTER;
        }
        if (cls == Double.TYPE) {
            return INVOKE_NUMBER_GETTER;
        }
        if ($assertionsDisabled || cls == Object.class) {
            return INVOKE_OBJECT_GETTER;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.runtime.SpillProperty, jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    void initMethodHandles(Class cls) {
        throw new UnsupportedOperationException();
    }

    public ScriptFunction getGetterFunction(ScriptObject scriptObject) {
        Object obj = getAccessors(scriptObject).getter;
        if (obj instanceof ScriptFunction) {
            return (ScriptFunction) obj;
        }
        return null;
    }

    @Override // jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    public MethodHandle getSetter(Class cls, PropertyMap propertyMap) {
        if (cls == Integer.TYPE) {
            return INVOKE_INT_SETTER;
        }
        if (cls == Double.TYPE) {
            return INVOKE_NUMBER_SETTER;
        }
        if ($assertionsDisabled || cls == Object.class) {
            return INVOKE_OBJECT_SETTER;
        }
        throw new AssertionError();
    }

    public ScriptFunction getSetterFunction(ScriptObject scriptObject) {
        Object obj = getAccessors(scriptObject).setter;
        if (obj instanceof ScriptFunction) {
            return (ScriptFunction) obj;
        }
        return null;
    }

    MethodHandle getAccessorsGetter() {
        return super.getGetter(Object.class).asType(MethodType.methodType((Class<?>) Accessors.class, (Class<?>) Object.class));
    }

    private static Object invokeObjectGetter(Accessors accessors, MethodHandle methodHandle, Object obj) {
        Object obj2 = accessors.getter;
        if (obj2 instanceof ScriptFunction) {
            return (Object) methodHandle.invokeExact(obj2, obj);
        }
        return ScriptRuntime.UNDEFINED;
    }

    private static int invokeIntGetter(Accessors accessors, MethodHandle methodHandle, int i, Object obj) {
        Object obj2 = accessors.getter;
        if (obj2 instanceof ScriptFunction) {
            return (int) methodHandle.invokeExact(obj2, obj);
        }
        throw new UnwarrantedOptimismException(ScriptRuntime.UNDEFINED, i);
    }

    private static double invokeNumberGetter(Accessors accessors, MethodHandle methodHandle, int i, Object obj) {
        Object obj2 = accessors.getter;
        if (obj2 instanceof ScriptFunction) {
            return (double) methodHandle.invokeExact(obj2, obj);
        }
        throw new UnwarrantedOptimismException(ScriptRuntime.UNDEFINED, i);
    }

    private static void invokeObjectSetter(Accessors accessors, MethodHandle methodHandle, String str, Object obj, Object obj2) {
        Object obj3 = accessors.setter;
        if (obj3 instanceof ScriptFunction) {
            (void) methodHandle.invokeExact(obj3, obj, obj2);
        } else if (str != null) {
            throw ECMAErrors.typeError("property.has.no.setter", new String[]{str, ScriptRuntime.safeToString(obj)});
        }
    }

    private static void invokeIntSetter(Accessors accessors, MethodHandle methodHandle, String str, Object obj, int i) {
        Object obj2 = accessors.setter;
        if (obj2 instanceof ScriptFunction) {
            (void) methodHandle.invokeExact(obj2, obj, i);
        } else if (str != null) {
            throw ECMAErrors.typeError("property.has.no.setter", new String[]{str, ScriptRuntime.safeToString(obj)});
        }
    }

    private static void invokeNumberSetter(Accessors accessors, MethodHandle methodHandle, String str, Object obj, double d) {
        Object obj2 = accessors.setter;
        if (obj2 instanceof ScriptFunction) {
            (void) methodHandle.invokeExact(obj2, obj, d);
        } else if (str != null) {
            throw ECMAErrors.typeError("property.has.no.setter", new String[]{str, ScriptRuntime.safeToString(obj)});
        }
    }

    private static MethodHandle findOwnMH_S(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(LOOKUP, UserAccessorProperty.class, str, Lookup.f31MH.type(cls, clsArr));
    }
}
