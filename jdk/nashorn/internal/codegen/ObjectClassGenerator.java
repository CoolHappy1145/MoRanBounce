package jdk.nashorn.internal.codegen;

import java.io.IOException;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.nashorn.internal.codegen.ClassEmitter;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.AllocationStrategy;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.FunctionScope;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.Undefined;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(name = "fields")
/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/ObjectClassGenerator.class */
public final class ObjectClassGenerator implements Loggable {
    private static final MethodHandle IS_TYPE_GUARD;
    private static final String SCOPE_MARKER = "P";
    static final int FIELD_PADDING = 4;
    private final DebugLogger log;
    private static final Type[] FIELD_TYPES_OBJECT;
    private static final Type[] FIELD_TYPES_DUAL;
    public static final Type PRIMITIVE_FIELD_TYPE;
    private static final MethodHandle GET_DIFFERENT;
    private static final MethodHandle GET_DIFFERENT_UNDEFINED;
    private static boolean initialized;
    private final Context context;
    private final boolean dualFields;
    public static final MethodHandle PACK_DOUBLE;
    public static final MethodHandle UNPACK_DOUBLE;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ObjectClassGenerator.class.desiredAssertionStatus();
        IS_TYPE_GUARD = findOwnMH("isType", Boolean.TYPE, new Class[]{Class.class, Object.class});
        FIELD_TYPES_OBJECT = new Type[]{Type.OBJECT};
        FIELD_TYPES_DUAL = new Type[]{Type.LONG, Type.OBJECT};
        PRIMITIVE_FIELD_TYPE = Type.LONG;
        GET_DIFFERENT = findOwnMH("getDifferent", Object.class, new Class[]{Object.class, Class.class, MethodHandle.class, MethodHandle.class, Integer.TYPE});
        GET_DIFFERENT_UNDEFINED = findOwnMH("getDifferentUndefined", Object.class, new Class[]{Integer.TYPE});
        initialized = false;
        PACK_DOUBLE = Lookup.f31MH.explicitCastArguments(Lookup.f31MH.findStatic(MethodHandles.publicLookup(), Double.class, "doubleToRawLongBits", Lookup.f31MH.type(Long.TYPE, new Class[]{Double.TYPE})), Lookup.f31MH.type(Long.TYPE, new Class[]{Double.TYPE}));
        UNPACK_DOUBLE = Lookup.f31MH.findStatic(MethodHandles.publicLookup(), Double.class, "longBitsToDouble", Lookup.f31MH.type(Double.TYPE, new Class[]{Long.TYPE}));
    }

    public ObjectClassGenerator(Context context, boolean z) {
        this.context = context;
        this.dualFields = z;
        if (!$assertionsDisabled && context == null) {
            throw new AssertionError();
        }
        this.log = initLogger(context);
        if (!initialized) {
            initialized = true;
            if (!z) {
                this.log.warning("Running with object fields only - this is a deprecated configuration.");
            }
        }
    }

    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger getLogger() {
        return this.log;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger initLogger(Context context) {
        return context.getLogger(getClass());
    }

    public static long pack(Number number) {
        if (number instanceof Integer) {
            return number.intValue();
        }
        if (number instanceof Long) {
            return number.longValue();
        }
        if (number instanceof Double) {
            return Double.doubleToRawLongBits(number.doubleValue());
        }
        throw new AssertionError("cannot pack" + number);
    }

    private static String getPrefixName(boolean z) {
        return z ? CompilerConstants.JS_OBJECT_DUAL_FIELD_PREFIX.symbolName() : CompilerConstants.JS_OBJECT_SINGLE_FIELD_PREFIX.symbolName();
    }

    private static String getPrefixName(String str) {
        if (str.startsWith(CompilerConstants.JS_OBJECT_DUAL_FIELD_PREFIX.symbolName())) {
            return getPrefixName(true);
        }
        if (str.startsWith(CompilerConstants.JS_OBJECT_SINGLE_FIELD_PREFIX.symbolName())) {
            return getPrefixName(false);
        }
        throw new AssertionError("Not a structure class: " + str);
    }

    public static String getClassName(int i, boolean z) {
        String prefixName = getPrefixName(z);
        return i != 0 ? "jdk/nashorn/internal/scripts/" + prefixName + i : "jdk/nashorn/internal/scripts/" + prefixName;
    }

    public static String getClassName(int i, int i2, boolean z) {
        return "jdk/nashorn/internal/scripts/" + getPrefixName(z) + i + SCOPE_MARKER + i2;
    }

    public static int getFieldCount(Class cls) {
        String simpleName = cls.getSimpleName();
        String prefixName = getPrefixName(simpleName);
        if (prefixName.equals(simpleName)) {
            return 0;
        }
        int iIndexOf = simpleName.indexOf(SCOPE_MARKER);
        return Integer.parseInt(iIndexOf == -1 ? simpleName.substring(prefixName.length()) : simpleName.substring(prefixName.length(), iIndexOf));
    }

    public static String getFieldName(int i, Type type) {
        return type.getDescriptor().substring(0, 1) + i;
    }

    private void initializeToUndefined(MethodEmitter methodEmitter, String str, List list) {
        if (this.dualFields || list.isEmpty()) {
            return;
        }
        methodEmitter.load(Type.OBJECT, CompilerConstants.JAVA_THIS.slot());
        methodEmitter.loadUndefined(Type.OBJECT);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str2 = (String) it.next();
            if (it.hasNext()) {
                methodEmitter.dup2();
            }
            methodEmitter.putField(str, str2, Type.OBJECT.getDescriptor());
        }
    }

    public byte[] generate(String str) {
        String[] strArrSplit = str.split(SCOPE_MARKER);
        int iIntValue = Integer.valueOf(strArrSplit[0]).intValue();
        if (strArrSplit.length == 1) {
            return generate(iIntValue);
        }
        return generate(iIntValue, Integer.valueOf(strArrSplit[1]).intValue());
    }

    public byte[] generate(int i) {
        String className = getClassName(i, this.dualFields);
        ClassEmitter classEmitterNewClassEmitter = newClassEmitter(className, CompilerConstants.className(ScriptObject.class));
        addFields(classEmitterNewClassEmitter, i);
        MethodEmitter methodEmitterNewInitMethod = newInitMethod(classEmitterNewClassEmitter);
        methodEmitterNewInitMethod.returnVoid();
        methodEmitterNewInitMethod.end();
        MethodEmitter methodEmitterNewInitWithSpillArraysMethod = newInitWithSpillArraysMethod(classEmitterNewClassEmitter, ScriptObject.class);
        methodEmitterNewInitWithSpillArraysMethod.returnVoid();
        methodEmitterNewInitWithSpillArraysMethod.end();
        newEmptyInit(className, classEmitterNewClassEmitter);
        newAllocate(className, classEmitterNewClassEmitter);
        return toByteArray(className, classEmitterNewClassEmitter);
    }

    public byte[] generate(int i, int i2) {
        String className = getClassName(i, i2, this.dualFields);
        ClassEmitter classEmitterNewClassEmitter = newClassEmitter(className, CompilerConstants.className(FunctionScope.class));
        List listAddFields = addFields(classEmitterNewClassEmitter, i);
        MethodEmitter methodEmitterNewInitScopeMethod = newInitScopeMethod(classEmitterNewClassEmitter);
        initializeToUndefined(methodEmitterNewInitScopeMethod, className, listAddFields);
        methodEmitterNewInitScopeMethod.returnVoid();
        methodEmitterNewInitScopeMethod.end();
        MethodEmitter methodEmitterNewInitWithSpillArraysMethod = newInitWithSpillArraysMethod(classEmitterNewClassEmitter, FunctionScope.class);
        initializeToUndefined(methodEmitterNewInitWithSpillArraysMethod, className, listAddFields);
        methodEmitterNewInitWithSpillArraysMethod.returnVoid();
        methodEmitterNewInitWithSpillArraysMethod.end();
        MethodEmitter methodEmitterNewInitScopeWithArgumentsMethod = newInitScopeWithArgumentsMethod(classEmitterNewClassEmitter);
        initializeToUndefined(methodEmitterNewInitScopeWithArgumentsMethod, className, listAddFields);
        methodEmitterNewInitScopeWithArgumentsMethod.returnVoid();
        methodEmitterNewInitScopeWithArgumentsMethod.end();
        return toByteArray(className, classEmitterNewClassEmitter);
    }

    private List addFields(ClassEmitter classEmitter, int i) {
        LinkedList linkedList = new LinkedList();
        Type[] typeArr = this.dualFields ? FIELD_TYPES_DUAL : FIELD_TYPES_OBJECT;
        for (int i2 = 0; i2 < i; i2++) {
            for (Type type : typeArr) {
                String fieldName = getFieldName(i2, type);
                classEmitter.field(fieldName, type.getTypeClass());
                if (type == Type.OBJECT) {
                    linkedList.add(fieldName);
                }
            }
        }
        return linkedList;
    }

    private ClassEmitter newClassEmitter(String str, String str2) {
        ClassEmitter classEmitter = new ClassEmitter(this.context, str, str2, new String[0]);
        classEmitter.begin();
        return classEmitter;
    }

    private static MethodEmitter newInitMethod(ClassEmitter classEmitter) {
        MethodEmitter methodEmitterInit = classEmitter.init(new Class[]{PropertyMap.class});
        methodEmitterInit.begin();
        methodEmitterInit.load(Type.OBJECT, CompilerConstants.JAVA_THIS.slot());
        methodEmitterInit.load(Type.OBJECT, CompilerConstants.INIT_MAP.slot());
        methodEmitterInit.invoke(CompilerConstants.constructorNoLookup(ScriptObject.class, new Class[]{PropertyMap.class}));
        return methodEmitterInit;
    }

    private static MethodEmitter newInitWithSpillArraysMethod(ClassEmitter classEmitter, Class cls) {
        MethodEmitter methodEmitterInit = classEmitter.init(new Class[]{PropertyMap.class, long[].class, Object[].class});
        methodEmitterInit.begin();
        methodEmitterInit.load(Type.OBJECT, CompilerConstants.JAVA_THIS.slot());
        methodEmitterInit.load(Type.OBJECT, CompilerConstants.INIT_MAP.slot());
        methodEmitterInit.load(Type.LONG_ARRAY, 2);
        methodEmitterInit.load(Type.OBJECT_ARRAY, 3);
        methodEmitterInit.invoke(CompilerConstants.constructorNoLookup(cls, new Class[]{PropertyMap.class, long[].class, Object[].class}));
        return methodEmitterInit;
    }

    private static MethodEmitter newInitScopeMethod(ClassEmitter classEmitter) {
        MethodEmitter methodEmitterInit = classEmitter.init(new Class[]{PropertyMap.class, ScriptObject.class});
        methodEmitterInit.begin();
        methodEmitterInit.load(Type.OBJECT, CompilerConstants.JAVA_THIS.slot());
        methodEmitterInit.load(Type.OBJECT, CompilerConstants.INIT_MAP.slot());
        methodEmitterInit.load(Type.OBJECT, CompilerConstants.INIT_SCOPE.slot());
        methodEmitterInit.invoke(CompilerConstants.constructorNoLookup(FunctionScope.class, new Class[]{PropertyMap.class, ScriptObject.class}));
        return methodEmitterInit;
    }

    private static MethodEmitter newInitScopeWithArgumentsMethod(ClassEmitter classEmitter) {
        MethodEmitter methodEmitterInit = classEmitter.init(new Class[]{PropertyMap.class, ScriptObject.class, ScriptObject.class});
        methodEmitterInit.begin();
        methodEmitterInit.load(Type.OBJECT, CompilerConstants.JAVA_THIS.slot());
        methodEmitterInit.load(Type.OBJECT, CompilerConstants.INIT_MAP.slot());
        methodEmitterInit.load(Type.OBJECT, CompilerConstants.INIT_SCOPE.slot());
        methodEmitterInit.load(Type.OBJECT, CompilerConstants.INIT_ARGUMENTS.slot());
        methodEmitterInit.invoke(CompilerConstants.constructorNoLookup(FunctionScope.class, new Class[]{PropertyMap.class, ScriptObject.class, ScriptObject.class}));
        return methodEmitterInit;
    }

    private static void newEmptyInit(String str, ClassEmitter classEmitter) {
        MethodEmitter methodEmitterInit = classEmitter.init();
        methodEmitterInit.begin();
        methodEmitterInit.load(Type.OBJECT, CompilerConstants.JAVA_THIS.slot());
        methodEmitterInit.loadNull();
        methodEmitterInit.invoke(CompilerConstants.constructorNoLookup(str, new Class[]{PropertyMap.class}));
        methodEmitterInit.returnVoid();
        methodEmitterInit.end();
    }

    private static void newAllocate(String str, ClassEmitter classEmitter) {
        MethodEmitter methodEmitterMethod = classEmitter.method(EnumSet.of(ClassEmitter.Flag.PUBLIC, ClassEmitter.Flag.STATIC), CompilerConstants.ALLOCATE.symbolName(), ScriptObject.class, new Class[]{PropertyMap.class});
        methodEmitterMethod.begin();
        methodEmitterMethod._new(str, Type.typeFor((Class<?>) ScriptObject.class));
        methodEmitterMethod.dup();
        methodEmitterMethod.load(Type.typeFor((Class<?>) PropertyMap.class), 0);
        methodEmitterMethod.invoke(CompilerConstants.constructorNoLookup(str, new Class[]{PropertyMap.class}));
        methodEmitterMethod._return();
        methodEmitterMethod.end();
    }

    private byte[] toByteArray(String str, ClassEmitter classEmitter) throws IOException {
        classEmitter.end();
        byte[] byteArray = classEmitter.toByteArray();
        ScriptEnvironment env = this.context.getEnv();
        DumpBytecode.dumpBytecode(env, this.log, byteArray, str);
        if (env._verify_code) {
            this.context.verify(byteArray);
        }
        return byteArray;
    }

    private static Object getDifferent(Object obj, Class cls, MethodHandle methodHandle, MethodHandle methodHandle2, int i) {
        MethodHandle methodHandle3 = getterForType(cls, methodHandle, methodHandle2);
        try {
            throw new UnwarrantedOptimismException((Object) Lookup.f31MH.asType(methodHandle3, methodHandle3.type().changeReturnType(Object.class)).invokeExact(obj), i);
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    private static Object getDifferentUndefined(int i) {
        throw new UnwarrantedOptimismException(Undefined.getUndefined(), i);
    }

    private static MethodHandle getterForType(Class cls, MethodHandle methodHandle, MethodHandle methodHandle2) {
        switch (JSType.getAccessorTypeIndex(cls)) {
            case 0:
                return Lookup.f31MH.explicitCastArguments(methodHandle, methodHandle.type().changeReturnType(Integer.TYPE));
            case 1:
                return Lookup.f31MH.filterReturnValue(methodHandle, UNPACK_DOUBLE);
            case 2:
                return methodHandle2;
            default:
                throw new AssertionError(cls);
        }
    }

    private static MethodHandle createGetterInner(Class cls, Class cls2, MethodHandle methodHandle, MethodHandle methodHandle2, List list, int i) {
        int accessorTypeIndex = cls == null ? -1 : JSType.getAccessorTypeIndex(cls);
        int accessorTypeIndex2 = JSType.getAccessorTypeIndex(cls2);
        boolean z = list == JSType.CONVERT_OBJECT_OPTIMISTIC;
        MethodHandle methodHandle3 = (methodHandle != null && (cls != null && cls.isPrimitive())) ? methodHandle : methodHandle2;
        MethodHandle methodHandle4 = methodHandle3;
        if (cls == null) {
            return z ? accessorTypeIndex2 == 2 ? Lookup.f31MH.dropArguments((MethodHandle) JSType.GET_UNDEFINED.get(2), 0, new Class[]{Object.class}) : Lookup.f31MH.asType(Lookup.f31MH.dropArguments(Lookup.f31MH.insertArguments(GET_DIFFERENT_UNDEFINED, 0, new Object[]{Integer.valueOf(i)}), 0, new Class[]{Object.class}), methodHandle4.type().changeReturnType((Class<?>) cls2)) : Lookup.f31MH.dropArguments((MethodHandle) JSType.GET_UNDEFINED.get(accessorTypeIndex2), 0, new Class[]{Object.class});
        }
        if (!$assertionsDisabled && methodHandle == null && cls != Object.class) {
            throw new AssertionError(cls);
        }
        if (z) {
            if (accessorTypeIndex < accessorTypeIndex2) {
                if (!$assertionsDisabled && accessorTypeIndex == -1) {
                    throw new AssertionError();
                }
                MethodHandle methodHandle5 = getterForType(cls, methodHandle, methodHandle2);
                return Lookup.f31MH.asType(methodHandle5, methodHandle5.type().changeReturnType((Class<?>) cls2));
            }
            if (accessorTypeIndex == accessorTypeIndex2) {
                return getterForType(cls, methodHandle, methodHandle2);
            }
            if (!$assertionsDisabled && accessorTypeIndex <= accessorTypeIndex2) {
                throw new AssertionError();
            }
            if (accessorTypeIndex == 2) {
                return Lookup.f31MH.filterReturnValue(methodHandle2, Lookup.f31MH.insertArguments((MethodHandle) list.get(accessorTypeIndex2), 1, new Object[]{Integer.valueOf(i)}));
            }
            return Lookup.f31MH.asType(Lookup.f31MH.filterArguments(methodHandle2, 0, new MethodHandle[]{Lookup.f31MH.insertArguments(GET_DIFFERENT, 1, new Object[]{cls, methodHandle, methodHandle2, Integer.valueOf(i)})}), methodHandle2.type().changeReturnType((Class<?>) cls2));
        }
        if (!$assertionsDisabled && z) {
            throw new AssertionError();
        }
        MethodHandle methodHandle6 = getterForType(cls, methodHandle, methodHandle2);
        if (accessorTypeIndex == 2) {
            if (accessorTypeIndex != accessorTypeIndex2) {
                return Lookup.f31MH.filterReturnValue(methodHandle6, (MethodHandle) JSType.CONVERT_OBJECT.get(accessorTypeIndex2));
            }
            return methodHandle6;
        }
        if (!$assertionsDisabled && methodHandle == null) {
            throw new AssertionError();
        }
        MethodType methodTypeType = methodHandle6.type();
        switch (accessorTypeIndex) {
            case 0:
                return Lookup.f31MH.asType(methodHandle6, methodTypeType.changeReturnType((Class<?>) cls2));
            case 1:
                switch (accessorTypeIndex2) {
                    case 0:
                        return Lookup.f31MH.filterReturnValue(methodHandle6, JSType.TO_INT32_D.methodHandle);
                    case 1:
                        if ($assertionsDisabled || methodTypeType.returnType() == Double.TYPE) {
                            return methodHandle6;
                        }
                        throw new AssertionError();
                    default:
                        return Lookup.f31MH.asType(methodHandle6, methodTypeType.changeReturnType(Object.class));
                }
            default:
                throw new UnsupportedOperationException(cls + "=>" + cls2);
        }
    }

    public static MethodHandle createGetter(Class cls, Class cls2, MethodHandle methodHandle, MethodHandle methodHandle2, int i) {
        return createGetterInner(cls, cls2, methodHandle, methodHandle2, UnwarrantedOptimismException.isValid(i) ? JSType.CONVERT_OBJECT_OPTIMISTIC : JSType.CONVERT_OBJECT, i);
    }

    public static MethodHandle createSetter(Class cls, Class cls2, MethodHandle methodHandle, MethodHandle methodHandle2) {
        if (!$assertionsDisabled && cls == null) {
            throw new AssertionError();
        }
        int accessorTypeIndex = JSType.getAccessorTypeIndex(cls);
        int accessorTypeIndex2 = JSType.getAccessorTypeIndex(cls2);
        if (accessorTypeIndex == 2 || methodHandle == null) {
            if (accessorTypeIndex2 == 2) {
                return methodHandle2;
            }
            return Lookup.f31MH.asType(methodHandle2, methodHandle2.type().changeParameterType(1, (Class<?>) cls2));
        }
        MethodType methodTypeType = methodHandle.type();
        switch (accessorTypeIndex) {
            case 0:
                switch (accessorTypeIndex2) {
                    case 0:
                        return Lookup.f31MH.asType(methodHandle, methodTypeType.changeParameterType(1, Integer.TYPE));
                    case 1:
                        return Lookup.f31MH.filterArguments(methodHandle, 1, new MethodHandle[]{PACK_DOUBLE});
                    default:
                        return methodHandle2;
                }
            case 1:
                return accessorTypeIndex2 == 2 ? methodHandle2 : Lookup.f31MH.asType(Lookup.f31MH.filterArguments(methodHandle, 1, new MethodHandle[]{PACK_DOUBLE}), methodTypeType.changeParameterType(1, (Class<?>) cls2));
            default:
                throw new UnsupportedOperationException(cls + "=>" + cls2);
        }
    }

    private static boolean isType(Class cls, Object obj) {
        return obj != null && obj.getClass() == cls;
    }

    private static Class getBoxedType(Class cls) {
        if (cls == Integer.TYPE) {
            return Integer.class;
        }
        if (cls == Long.TYPE) {
            return Long.class;
        }
        if (cls == Double.TYPE) {
            return Double.class;
        }
        if ($assertionsDisabled) {
            return null;
        }
        throw new AssertionError();
    }

    public static MethodHandle createGuardBoxedPrimitiveSetter(Class cls, MethodHandle methodHandle, MethodHandle methodHandle2) {
        return Lookup.f31MH.guardWithTest(Lookup.f31MH.insertArguments(Lookup.f31MH.dropArguments(IS_TYPE_GUARD, 1, new Class[]{Object.class}), 0, new Object[]{getBoxedType(cls)}), Lookup.f31MH.asType(methodHandle, methodHandle2.type()), methodHandle2);
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), ObjectClassGenerator.class, str, Lookup.f31MH.type(cls, clsArr));
    }

    static AllocationStrategy createAllocationStrategy(int i, boolean z) {
        return new AllocationStrategy(((i / 4) * 4) + 4, z);
    }
}
