package jdk.nashorn.internal.runtime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.SwitchPoint;
import java.util.function.Supplier;
import java.util.logging.Level;
import jdk.nashorn.internal.codegen.ObjectClassGenerator;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.lookup.MethodHandleFactory;
import jdk.nashorn.internal.objects.Global;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/AccessorProperty.class */
public class AccessorProperty extends Property {
    private static final MethodHandles.Lookup LOOKUP;
    private static final MethodHandle REPLACE_MAP;
    private static final MethodHandle INVALIDATE_SP;
    private static final int NOOF_TYPES;
    private static final long serialVersionUID = 3371720170182154920L;
    private static ClassValue GETTERS_SETTERS;
    private MethodHandle[] GETTER_CACHE;
    MethodHandle primitiveGetter;
    MethodHandle primitiveSetter;
    MethodHandle objectGetter;
    MethodHandle objectSetter;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !AccessorProperty.class.desiredAssertionStatus();
        LOOKUP = MethodHandles.lookup();
        REPLACE_MAP = findOwnMH_S("replaceMap", Object.class, new Class[]{Object.class, PropertyMap.class});
        INVALIDATE_SP = findOwnMH_S("invalidateSwitchPoint", Object.class, new Class[]{AccessorProperty.class, Object.class});
        NOOF_TYPES = JSType.getNumberOfAccessorTypes();
        GETTERS_SETTERS = new ClassValue() { // from class: jdk.nashorn.internal.runtime.AccessorProperty.1
            @Override // java.lang.ClassValue
            protected Object computeValue(Class cls) {
                return computeValue(cls);
            }

            @Override // java.lang.ClassValue
            protected Accessors computeValue(Class cls) {
                return new Accessors(cls);
            }
        };
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/AccessorProperty$Accessors.class */
    private static class Accessors {
        final MethodHandle[] objectGetters;
        final MethodHandle[] objectSetters;
        final MethodHandle[] primitiveGetters;
        final MethodHandle[] primitiveSetters;

        Accessors(Class cls) {
            int fieldCount = ObjectClassGenerator.getFieldCount(cls);
            this.objectGetters = new MethodHandle[fieldCount];
            this.objectSetters = new MethodHandle[fieldCount];
            this.primitiveGetters = new MethodHandle[fieldCount];
            this.primitiveSetters = new MethodHandle[fieldCount];
            for (int i = 0; i < fieldCount; i++) {
                String fieldName = ObjectClassGenerator.getFieldName(i, Type.OBJECT);
                Class<?> typeClass = Type.OBJECT.getTypeClass();
                this.objectGetters[i] = Lookup.f31MH.asType(Lookup.f31MH.getter(AccessorProperty.LOOKUP, cls, fieldName, typeClass), Lookup.GET_OBJECT_TYPE);
                this.objectSetters[i] = Lookup.f31MH.asType(Lookup.f31MH.setter(AccessorProperty.LOOKUP, cls, fieldName, typeClass), Lookup.SET_OBJECT_TYPE);
            }
            if (!StructureLoader.isSingleFieldStructure(cls.getName())) {
                for (int i2 = 0; i2 < fieldCount; i2++) {
                    String fieldName2 = ObjectClassGenerator.getFieldName(i2, ObjectClassGenerator.PRIMITIVE_FIELD_TYPE);
                    Class<?> typeClass2 = ObjectClassGenerator.PRIMITIVE_FIELD_TYPE.getTypeClass();
                    this.primitiveGetters[i2] = Lookup.f31MH.asType(Lookup.f31MH.getter(AccessorProperty.LOOKUP, cls, fieldName2, typeClass2), Lookup.GET_PRIMITIVE_TYPE);
                    this.primitiveSetters[i2] = Lookup.f31MH.asType(Lookup.f31MH.setter(AccessorProperty.LOOKUP, cls, fieldName2, typeClass2), Lookup.SET_PRIMITIVE_TYPE);
                }
            }
        }
    }

    public static AccessorProperty create(String str, int i, MethodHandle methodHandle, MethodHandle methodHandle2) {
        return new AccessorProperty(str, i, -1, methodHandle, methodHandle2);
    }

    AccessorProperty(AccessorProperty accessorProperty, Object obj) {
        super(accessorProperty, accessorProperty.getFlags() | 256);
        this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
        this.primitiveGetter = bindTo(accessorProperty.primitiveGetter, obj);
        this.primitiveSetter = bindTo(accessorProperty.primitiveSetter, obj);
        this.objectGetter = bindTo(accessorProperty.objectGetter, obj);
        this.objectSetter = bindTo(accessorProperty.objectSetter, obj);
        accessorProperty.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
        setType(accessorProperty.getType());
    }

    protected AccessorProperty(String str, int i, int i2, MethodHandle methodHandle, MethodHandle methodHandle2, MethodHandle methodHandle3, MethodHandle methodHandle4) {
        super(str, i, i2);
        this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
        if (!$assertionsDisabled && getClass() == AccessorProperty.class) {
            throw new AssertionError();
        }
        this.primitiveGetter = methodHandle;
        this.primitiveSetter = methodHandle2;
        this.objectGetter = methodHandle3;
        this.objectSetter = methodHandle4;
        initializeType();
    }

    private AccessorProperty(String str, int i, int i2, MethodHandle methodHandle, MethodHandle methodHandle2) {
        super(str, i | 128 | 2048 | (methodHandle.type().returnType().isPrimitive() ? 64 : 0), i2);
        this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
        if (!$assertionsDisabled && isSpill()) {
            throw new AssertionError();
        }
        Class<?> clsReturnType = methodHandle.type().returnType();
        Class<?> clsParameterType = methodHandle2 == null ? null : methodHandle2.type().parameterType(1);
        if (!$assertionsDisabled && clsParameterType != null && clsParameterType != clsReturnType) {
            throw new AssertionError();
        }
        if (clsReturnType == Integer.TYPE) {
            this.primitiveGetter = Lookup.f31MH.asType(methodHandle, Lookup.GET_PRIMITIVE_TYPE);
            this.primitiveSetter = methodHandle2 == null ? null : Lookup.f31MH.asType(methodHandle2, Lookup.SET_PRIMITIVE_TYPE);
        } else if (clsReturnType == Double.TYPE) {
            this.primitiveGetter = Lookup.f31MH.asType(Lookup.f31MH.filterReturnValue(methodHandle, ObjectClassGenerator.PACK_DOUBLE), Lookup.GET_PRIMITIVE_TYPE);
            this.primitiveSetter = methodHandle2 == null ? null : Lookup.f31MH.asType(Lookup.f31MH.filterArguments(methodHandle2, 1, new MethodHandle[]{ObjectClassGenerator.UNPACK_DOUBLE}), Lookup.SET_PRIMITIVE_TYPE);
        } else {
            this.primitiveSetter = null;
            this.primitiveGetter = null;
        }
        if (!$assertionsDisabled && this.primitiveGetter != null && this.primitiveGetter.type() != Lookup.GET_PRIMITIVE_TYPE) {
            throw new AssertionError(this.primitiveGetter + "!=" + Lookup.GET_PRIMITIVE_TYPE);
        }
        if (!$assertionsDisabled && this.primitiveSetter != null && this.primitiveSetter.type() != Lookup.SET_PRIMITIVE_TYPE) {
            throw new AssertionError(this.primitiveSetter);
        }
        this.objectGetter = methodHandle.type() != Lookup.GET_OBJECT_TYPE ? Lookup.f31MH.asType(methodHandle, Lookup.GET_OBJECT_TYPE) : methodHandle;
        this.objectSetter = (methodHandle2 == null || methodHandle2.type() == Lookup.SET_OBJECT_TYPE) ? methodHandle2 : Lookup.f31MH.asType(methodHandle2, Lookup.SET_OBJECT_TYPE);
        setType(clsReturnType);
    }

    public AccessorProperty(String str, int i, Class cls, int i2) {
        super(str, i, i2);
        this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
        initGetterSetter(cls);
        initializeType();
    }

    private void initGetterSetter(Class cls) {
        int slot = getSlot();
        if (isParameter() && hasArguments()) {
            MethodHandle methodHandle = Lookup.f31MH.getter(LOOKUP, cls, "arguments", ScriptObject.class);
            this.objectGetter = Lookup.f31MH.asType(Lookup.f31MH.insertArguments(Lookup.f31MH.filterArguments(ScriptObject.GET_ARGUMENT.methodHandle(), 0, new MethodHandle[]{methodHandle}), 1, new Object[]{Integer.valueOf(slot)}), Lookup.GET_OBJECT_TYPE);
            this.objectSetter = Lookup.f31MH.asType(Lookup.f31MH.insertArguments(Lookup.f31MH.filterArguments(ScriptObject.SET_ARGUMENT.methodHandle(), 0, new MethodHandle[]{methodHandle}), 1, new Object[]{Integer.valueOf(slot)}), Lookup.SET_OBJECT_TYPE);
            this.primitiveGetter = null;
            this.primitiveSetter = null;
        } else {
            Accessors accessors = (Accessors) GETTERS_SETTERS.get(cls);
            this.objectGetter = accessors.objectGetters[slot];
            this.primitiveGetter = accessors.primitiveGetters[slot];
            this.objectSetter = accessors.objectSetters[slot];
            this.primitiveSetter = accessors.primitiveSetters[slot];
        }
        if (!$assertionsDisabled && hasDualFields() == StructureLoader.isSingleFieldStructure(cls.getName())) {
            throw new AssertionError();
        }
    }

    protected AccessorProperty(String str, int i, int i2, ScriptObject scriptObject, Object obj) {
        this(str, i, scriptObject.getClass(), i2);
        setInitialValue(scriptObject, obj);
    }

    public AccessorProperty(String str, int i, Class cls, int i2, Class cls2) {
        this(str, i, cls, i2);
        setType(hasDualFields() ? cls2 : Object.class);
    }

    protected AccessorProperty(AccessorProperty accessorProperty, Class cls) {
        super(accessorProperty, accessorProperty.getFlags());
        this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
        this.GETTER_CACHE = cls != accessorProperty.getLocalType() ? new MethodHandle[NOOF_TYPES] : accessorProperty.GETTER_CACHE;
        this.primitiveGetter = accessorProperty.primitiveGetter;
        this.primitiveSetter = accessorProperty.primitiveSetter;
        this.objectGetter = accessorProperty.objectGetter;
        this.objectSetter = accessorProperty.objectSetter;
        setType(cls);
    }

    protected AccessorProperty(AccessorProperty accessorProperty) {
        this(accessorProperty, accessorProperty.getLocalType());
    }

    protected final void setInitialValue(ScriptObject scriptObject, Object obj) {
        setType(hasDualFields() ? JSType.unboxedFieldType(obj) : Object.class);
        if (obj instanceof Integer) {
            invokeSetter(scriptObject, ((Integer) obj).intValue());
        } else if (obj instanceof Double) {
            invokeSetter(scriptObject, ((Double) obj).doubleValue());
        } else {
            invokeSetter(scriptObject, obj);
        }
    }

    protected final void initializeType() {
        setType(!hasDualFields() ? Object.class : null);
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.GETTER_CACHE = new MethodHandle[NOOF_TYPES];
    }

    private static MethodHandle bindTo(MethodHandle methodHandle, Object obj) {
        if (methodHandle == null) {
            return null;
        }
        return Lookup.f31MH.dropArguments(Lookup.f31MH.bindTo(methodHandle, obj), 0, new Class[]{Object.class});
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public Property copy() {
        return new AccessorProperty(this);
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public Property copy(Class cls) {
        return new AccessorProperty(this, cls);
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public int getIntValue(ScriptObject scriptObject, ScriptObject scriptObject2) {
        try {
            return (int) getGetter(Integer.TYPE).invokeExact(scriptObject);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public double getDoubleValue(ScriptObject scriptObject, ScriptObject scriptObject2) {
        try {
            return (double) getGetter(Double.TYPE).invokeExact(scriptObject);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public Object getObjectValue(ScriptObject scriptObject, ScriptObject scriptObject2) {
        try {
            return (Object) getGetter(Object.class).invokeExact(scriptObject);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    protected final void invokeSetter(ScriptObject scriptObject, int i) {
        try {
            (void) getSetter(Integer.TYPE, scriptObject.getMap()).invokeExact(scriptObject, i);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    protected final void invokeSetter(ScriptObject scriptObject, double d) {
        try {
            (void) getSetter(Double.TYPE, scriptObject.getMap()).invokeExact(scriptObject, d);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    protected final void invokeSetter(ScriptObject scriptObject, Object obj) {
        try {
            (void) getSetter(Object.class, scriptObject.getMap()).invokeExact(scriptObject, obj);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public void setValue(ScriptObject scriptObject, ScriptObject scriptObject2, int i, boolean z) {
        if (!$assertionsDisabled && !isConfigurable() && !isWritable()) {
            throw new AssertionError(getKey() + " is not writable or configurable");
        }
        invokeSetter(scriptObject, i);
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public void setValue(ScriptObject scriptObject, ScriptObject scriptObject2, double d, boolean z) {
        if (!$assertionsDisabled && !isConfigurable() && !isWritable()) {
            throw new AssertionError(getKey() + " is not writable or configurable");
        }
        invokeSetter(scriptObject, d);
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public void setValue(ScriptObject scriptObject, ScriptObject scriptObject2, Object obj, boolean z) {
        invokeSetter(scriptObject, obj);
    }

    @Override // jdk.nashorn.internal.runtime.Property
    void initMethodHandles(Class cls) {
        if (!ScriptObject.class.isAssignableFrom(cls) || !StructureLoader.isStructureClass(cls.getName())) {
            throw new IllegalArgumentException();
        }
        if (!$assertionsDisabled && isSpill()) {
            throw new AssertionError();
        }
        initGetterSetter(cls);
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public MethodHandle getGetter(Class cls) {
        MethodHandle methodHandleDebug;
        int accessorTypeIndex = JSType.getAccessorTypeIndex(cls);
        if (!$assertionsDisabled && cls != Integer.TYPE && cls != Double.TYPE && cls != Object.class) {
            throw new AssertionError("invalid getter type " + cls + " for " + getKey());
        }
        checkUndeclared();
        MethodHandle[] methodHandleArr = this.GETTER_CACHE;
        MethodHandle methodHandle = methodHandleArr[accessorTypeIndex];
        if (methodHandle != null) {
            methodHandleDebug = methodHandle;
        } else {
            methodHandleDebug = debug(ObjectClassGenerator.createGetter(getLocalType(), cls, this.primitiveGetter, this.objectGetter, -1), getLocalType(), cls, PropertyDescriptor.GET);
            methodHandleArr[accessorTypeIndex] = methodHandleDebug;
        }
        if ($assertionsDisabled || (methodHandleDebug.type().returnType() == cls && methodHandleDebug.type().parameterType(0) == Object.class)) {
            return methodHandleDebug;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public MethodHandle getOptimisticGetter(Class cls, int i) {
        if (this.objectGetter == null) {
            return getOptimisticPrimitiveGetter(cls, i);
        }
        checkUndeclared();
        return debug(ObjectClassGenerator.createGetter(getLocalType(), cls, this.primitiveGetter, this.objectGetter, i), getLocalType(), cls, PropertyDescriptor.GET);
    }

    private MethodHandle getOptimisticPrimitiveGetter(Class cls, int i) {
        MethodHandle getter = getGetter(getLocalType());
        return Lookup.f31MH.asType(OptimisticReturnFilters.filterOptimisticReturnValue(getter, cls, i), getter.type().changeReturnType((Class<?>) cls));
    }

    private Property getWiderProperty(Class cls) {
        return copy(cls);
    }

    private PropertyMap getWiderMap(PropertyMap propertyMap, Property property) {
        PropertyMap propertyMapReplaceProperty = propertyMap.replaceProperty(this, property);
        if (!$assertionsDisabled && propertyMap.size() <= 0) {
            throw new AssertionError();
        }
        if ($assertionsDisabled || propertyMapReplaceProperty.size() == propertyMap.size()) {
            return propertyMapReplaceProperty;
        }
        throw new AssertionError();
    }

    private void checkUndeclared() {
        if ((getFlags() & 512) != 0) {
            throw ECMAErrors.referenceError("not.defined", new String[]{getKey()});
        }
    }

    private static Object replaceMap(Object obj, PropertyMap propertyMap) {
        ((ScriptObject) obj).setMap(propertyMap);
        return obj;
    }

    private static Object invalidateSwitchPoint(AccessorProperty accessorProperty, Object obj) {
        if (!accessorProperty.builtinSwitchPoint.hasBeenInvalidated()) {
            SwitchPoint.invalidateAll(new SwitchPoint[]{accessorProperty.builtinSwitchPoint});
        }
        return obj;
    }

    private MethodHandle generateSetter(Class cls, Class cls2) {
        return debug(ObjectClassGenerator.createSetter(cls, cls2, this.primitiveSetter, this.objectSetter), getLocalType(), cls2, PropertyDescriptor.SET);
    }

    protected final boolean isUndefined() {
        return getLocalType() == null;
    }

    @Override // jdk.nashorn.internal.runtime.Property
    public MethodHandle getSetter(Class cls, PropertyMap propertyMap) {
        MethodHandle methodHandleGenerateSetter;
        checkUndeclared();
        if (needsInvalidator(JSType.getAccessorTypeIndex(cls), JSType.getAccessorTypeIndex(getLocalType()))) {
            Property widerProperty = getWiderProperty(cls);
            PropertyMap widerMap = getWiderMap(propertyMap, widerProperty);
            MethodHandle setter = widerProperty.getSetter(cls, widerMap);
            Class localType = getLocalType();
            methodHandleGenerateSetter = Lookup.f31MH.filterArguments(setter, 0, new MethodHandle[]{Lookup.f31MH.insertArguments(debugReplace(localType, cls, propertyMap, widerMap), 1, new Object[]{widerMap})});
            if (localType != null && localType.isPrimitive() && !cls.isPrimitive()) {
                methodHandleGenerateSetter = ObjectClassGenerator.createGuardBoxedPrimitiveSetter(localType, generateSetter(localType, localType), methodHandleGenerateSetter);
            }
        } else {
            Class localType2 = isUndefined() ? cls : getLocalType();
            methodHandleGenerateSetter = generateSetter(!localType2.isPrimitive() ? Object.class : localType2, cls);
        }
        if (isBuiltin()) {
            methodHandleGenerateSetter = Lookup.f31MH.filterArguments(methodHandleGenerateSetter, 0, new MethodHandle[]{debugInvalidate(Lookup.f31MH.insertArguments(INVALIDATE_SP, 0, new Object[]{this}), getKey())});
        }
        if ($assertionsDisabled || methodHandleGenerateSetter.type().returnType() == Void.TYPE) {
            return methodHandleGenerateSetter;
        }
        throw new AssertionError(methodHandleGenerateSetter.type());
    }

    public final boolean canChangeType() {
        if (hasDualFields()) {
            return getLocalType() == null || (getLocalType() != Object.class && (isConfigurable() || isWritable()));
        }
        return false;
    }

    private boolean needsInvalidator(int i, int i2) {
        return canChangeType() && i > i2;
    }

    private MethodHandle debug(MethodHandle methodHandle, Class cls, Class cls2, String str) {
        if (!Context.DEBUG || !Global.hasInstance()) {
            return methodHandle;
        }
        Context contextTrusted = Context.getContextTrusted();
        if ($assertionsDisabled || contextTrusted != null) {
            return contextTrusted.addLoggingToHandle(ObjectClassGenerator.class, Level.INFO, methodHandle, 0, true, new Supplier(this, str, cls, cls2) { // from class: jdk.nashorn.internal.runtime.AccessorProperty.2
                final String val$tag;
                final Class val$forType;
                final Class val$type;
                final AccessorProperty this$0;

                {
                    this.this$0 = this;
                    this.val$tag = str;
                    this.val$forType = cls;
                    this.val$type = cls2;
                }

                @Override // java.util.function.Supplier
                public Object get() {
                    return get();
                }

                @Override // java.util.function.Supplier
                public String get() {
                    return this.val$tag + " '" + this.this$0.getKey() + "' (property=" + Debug.m11id(this) + ", slot=" + this.this$0.getSlot() + " " + getClass().getSimpleName() + " forType=" + MethodHandleFactory.stripName(this.val$forType) + ", type=" + MethodHandleFactory.stripName(this.val$type) + ')';
                }
            });
        }
        throw new AssertionError();
    }

    private MethodHandle debugReplace(Class cls, Class cls2, PropertyMap propertyMap, PropertyMap propertyMap2) {
        if (!Context.DEBUG || !Global.hasInstance()) {
            return REPLACE_MAP;
        }
        Context contextTrusted = Context.getContextTrusted();
        if (!$assertionsDisabled && contextTrusted == null) {
            throw new AssertionError();
        }
        return contextTrusted.addLoggingToHandle(ObjectClassGenerator.class, Level.FINEST, contextTrusted.addLoggingToHandle(ObjectClassGenerator.class, REPLACE_MAP, new Supplier(this, cls, cls2) { // from class: jdk.nashorn.internal.runtime.AccessorProperty.3
            final Class val$oldType;
            final Class val$newType;
            final AccessorProperty this$0;

            {
                this.this$0 = this;
                this.val$oldType = cls;
                this.val$newType = cls2;
            }

            @Override // java.util.function.Supplier
            public Object get() {
                return get();
            }

            @Override // java.util.function.Supplier
            public String get() {
                return "Type change for '" + this.this$0.getKey() + "' " + this.val$oldType + "=>" + this.val$newType;
            }
        }), Integer.MAX_VALUE, false, new Supplier(this, propertyMap, propertyMap2) { // from class: jdk.nashorn.internal.runtime.AccessorProperty.4
            final PropertyMap val$oldMap;
            final PropertyMap val$newMap;
            final AccessorProperty this$0;

            {
                this.this$0 = this;
                this.val$oldMap = propertyMap;
                this.val$newMap = propertyMap2;
            }

            @Override // java.util.function.Supplier
            public Object get() {
                return get();
            }

            @Override // java.util.function.Supplier
            public String get() {
                return "Setting map " + Debug.m11id(this.val$oldMap) + " => " + Debug.m11id(this.val$newMap) + " " + this.val$oldMap + " => " + this.val$newMap;
            }
        });
    }

    private static MethodHandle debugInvalidate(MethodHandle methodHandle, String str) {
        if (!Context.DEBUG || !Global.hasInstance()) {
            return methodHandle;
        }
        Context contextTrusted = Context.getContextTrusted();
        if ($assertionsDisabled || contextTrusted != null) {
            return contextTrusted.addLoggingToHandle(ObjectClassGenerator.class, methodHandle, new Supplier(str) { // from class: jdk.nashorn.internal.runtime.AccessorProperty.5
                final String val$key;

                {
                    this.val$key = str;
                }

                @Override // java.util.function.Supplier
                public Object get() {
                    return get();
                }

                @Override // java.util.function.Supplier
                public String get() {
                    return "Field change callback for " + this.val$key + " triggered ";
                }
            });
        }
        throw new AssertionError();
    }

    private static MethodHandle findOwnMH_S(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(LOOKUP, AccessorProperty.class, str, Lookup.f31MH.type(cls, clsArr));
    }
}
