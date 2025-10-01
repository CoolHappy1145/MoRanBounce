package jdk.nashorn.internal.runtime;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import jdk.nashorn.internal.lookup.Lookup;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/ScriptFunctionData.class */
public abstract class ScriptFunctionData implements Serializable {
    static final int MAX_ARITY = 250;
    protected final String name;
    protected LinkedList code = new LinkedList();
    protected int flags;
    private int arity;
    private GenericInvokers genericInvokers;
    private static final MethodHandle BIND_VAR_ARGS;
    public static final int IS_STRICT = 1;
    public static final int IS_BUILTIN = 2;
    public static final int IS_CONSTRUCTOR = 4;
    public static final int NEEDS_CALLEE = 8;
    public static final int USES_THIS = 16;
    public static final int IS_VARIABLE_ARITY = 32;
    public static final int IS_PROPERTY_ACCESSOR = 64;
    public static final int IS_STRICT_OR_BUILTIN = 3;
    public static final int IS_BUILTIN_CONSTRUCTOR = 6;
    private static final long serialVersionUID = 4252901245508769114L;
    static final boolean $assertionsDisabled;

    abstract boolean needsCallee();

    abstract CompiledFunction getBest(MethodType methodType, ScriptObject scriptObject, Collection collection, boolean z);

    abstract MethodType getGenericType();

    static {
        $assertionsDisabled = !ScriptFunctionData.class.desiredAssertionStatus();
        BIND_VAR_ARGS = findOwnMH("bindVarArgs", Object[].class, new Class[]{Object[].class, Object[].class});
    }

    ScriptFunctionData(String str, int i, int i2) {
        this.name = str;
        this.flags = i2;
        setArity(i);
    }

    final int getArity() {
        return this.arity;
    }

    final boolean isVariableArity() {
        return (this.flags & 32) != 0;
    }

    final boolean isPropertyAccessor() {
        return (this.flags & 64) != 0;
    }

    void setArity(int i) {
        if (i < 0 || i > 250) {
            throw new IllegalArgumentException(String.valueOf(i));
        }
        this.arity = i;
    }

    CompiledFunction bind(CompiledFunction compiledFunction, ScriptFunction scriptFunction, Object obj, Object[] objArr) {
        MethodHandle methodHandleBindInvokeHandle = bindInvokeHandle(compiledFunction.createComposableInvoker(), scriptFunction, obj, objArr);
        if (isConstructor()) {
            return new CompiledFunction(methodHandleBindInvokeHandle, bindConstructHandle(compiledFunction.createComposableConstructor(), scriptFunction, objArr), null);
        }
        return new CompiledFunction(methodHandleBindInvokeHandle);
    }

    public final boolean isStrict() {
        return (this.flags & 1) != 0;
    }

    protected String getFunctionName() {
        return getName();
    }

    final boolean isBuiltin() {
        return (this.flags & 2) != 0;
    }

    final boolean isConstructor() {
        return (this.flags & 4) != 0;
    }

    final boolean needsWrappedThis() {
        return (this.flags & 16) != 0 && (this.flags & 3) == 0;
    }

    String toSource() {
        return "function " + (this.name == null ? "" : this.name) + "() { [native code] }";
    }

    String getName() {
        return this.name;
    }

    public String toString() {
        return this.name.isEmpty() ? "<anonymous>" : this.name;
    }

    public String toStringVerbose() {
        StringBuilder sb = new StringBuilder();
        sb.append("name='").append(this.name.isEmpty() ? "<anonymous>" : this.name).append("' ").append(this.code.size()).append(" invokers=").append(this.code);
        return sb.toString();
    }

    final CompiledFunction getBestInvoker(MethodType methodType, ScriptObject scriptObject) {
        return getBestInvoker(methodType, scriptObject, CompiledFunction.NO_FUNCTIONS);
    }

    final CompiledFunction getBestInvoker(MethodType methodType, ScriptObject scriptObject, Collection collection) {
        CompiledFunction best = getBest(methodType, scriptObject, collection);
        if ($assertionsDisabled || best != null) {
            return best;
        }
        throw new AssertionError();
    }

    final CompiledFunction getBestConstructor(MethodType methodType, ScriptObject scriptObject, Collection collection) {
        if (!isConstructor()) {
            throw ECMAErrors.typeError("not.a.constructor", new String[]{toSource()});
        }
        return getBest(methodType.insertParameterTypes(1, Object.class), scriptObject, collection);
    }

    final MethodHandle getGenericInvoker(ScriptObject scriptObject) {
        GenericInvokers genericInvokersEnsureGenericInvokers = ensureGenericInvokers();
        MethodHandle methodHandle = genericInvokersEnsureGenericInvokers.invoker;
        if (methodHandle == null) {
            MethodHandle methodHandleCreateGenericInvoker = createGenericInvoker(scriptObject);
            methodHandle = methodHandleCreateGenericInvoker;
            genericInvokersEnsureGenericInvokers.invoker = methodHandleCreateGenericInvoker;
        }
        return methodHandle;
    }

    private MethodHandle createGenericInvoker(ScriptObject scriptObject) {
        return makeGenericMethod(getGeneric(scriptObject).createComposableInvoker());
    }

    final MethodHandle getGenericConstructor(ScriptObject scriptObject) {
        GenericInvokers genericInvokersEnsureGenericInvokers = ensureGenericInvokers();
        MethodHandle methodHandle = genericInvokersEnsureGenericInvokers.constructor;
        if (methodHandle == null) {
            MethodHandle methodHandleCreateGenericConstructor = createGenericConstructor(scriptObject);
            methodHandle = methodHandleCreateGenericConstructor;
            genericInvokersEnsureGenericInvokers.constructor = methodHandleCreateGenericConstructor;
        }
        return methodHandle;
    }

    private MethodHandle createGenericConstructor(ScriptObject scriptObject) {
        return makeGenericMethod(getGeneric(scriptObject).createComposableConstructor());
    }

    private GenericInvokers ensureGenericInvokers() {
        GenericInvokers genericInvokers = this.genericInvokers;
        if (genericInvokers == null) {
            GenericInvokers genericInvokers2 = new GenericInvokers(null);
            genericInvokers = genericInvokers2;
            this.genericInvokers = genericInvokers2;
        }
        return genericInvokers;
    }

    private static MethodType widen(MethodType methodType) {
        Class[] clsArr = new Class[methodType.parameterCount()];
        for (int i = 0; i < methodType.parameterCount(); i++) {
            clsArr[i] = methodType.parameterType(i).isPrimitive() ? methodType.parameterType(i) : Object.class;
        }
        return Lookup.f31MH.type(methodType.returnType(), clsArr);
    }

    CompiledFunction lookupExactApplyToCall(MethodType methodType) {
        Iterator it = this.code.iterator();
        while (it.hasNext()) {
            CompiledFunction compiledFunction = (CompiledFunction) it.next();
            if (compiledFunction.isApplyToCall()) {
                MethodType methodTypeType = compiledFunction.type();
                if (methodTypeType.parameterCount() == methodType.parameterCount() && widen(methodTypeType).equals(widen(methodType))) {
                    return compiledFunction;
                }
            }
        }
        return null;
    }

    CompiledFunction pickFunction(MethodType methodType, boolean z) {
        Iterator it = this.code.iterator();
        while (it.hasNext()) {
            CompiledFunction compiledFunction = (CompiledFunction) it.next();
            if (compiledFunction.matchesCallSite(methodType, z)) {
                return compiledFunction;
            }
        }
        return null;
    }

    final CompiledFunction getBest(MethodType methodType, ScriptObject scriptObject, Collection collection) {
        return getBest(methodType, scriptObject, collection, true);
    }

    boolean isValidCallSite(MethodType methodType) {
        return methodType.parameterCount() >= 2 && methodType.parameterType(0).isAssignableFrom(ScriptFunction.class);
    }

    CompiledFunction getGeneric(ScriptObject scriptObject) {
        return getBest(getGenericType(), scriptObject, CompiledFunction.NO_FUNCTIONS, false);
    }

    ScriptFunctionData makeBoundFunctionData(ScriptFunction scriptFunction, Object obj, Object[] objArr) {
        Object[] objArr2 = objArr == null ? ScriptRuntime.EMPTY_ARRAY : objArr;
        int length = objArr == null ? 0 : objArr.length;
        int i = this.flags & (-9) & (-17);
        LinkedList linkedList = new LinkedList();
        ScriptObject scope = scriptFunction.getScope();
        linkedList.add(bind(new CompiledFunction(getGenericInvoker(scope), getGenericConstructor(scope), null), scriptFunction, obj, objArr2));
        return new FinalScriptFunctionData(this.name, Math.max(0, getArity() - length), linkedList, i);
    }

    private Object convertThisObject(Object obj) {
        return needsWrappedThis() ? wrapThis(obj) : obj;
    }

    static Object wrapThis(Object obj) {
        if (!(obj instanceof ScriptObject)) {
            if (JSType.nullOrUndefined(obj)) {
                return Context.getGlobal();
            }
            if (isPrimitiveThis(obj)) {
                return Context.getGlobal().wrapAsObject(obj);
            }
        }
        return obj;
    }

    static boolean isPrimitiveThis(Object obj) {
        return ((obj instanceof String) || (obj instanceof ConsString)) || (obj instanceof Number) || (obj instanceof Boolean);
    }

    private MethodHandle bindInvokeHandle(MethodHandle methodHandle, ScriptFunction scriptFunction, Object obj, Object[] objArr) {
        MethodHandle methodHandleInsertArguments;
        MethodHandle methodHandleBindTo;
        boolean zIsBoundFunction = scriptFunction.isBoundFunction();
        boolean zNeedsCallee = needsCallee(methodHandle);
        if (!$assertionsDisabled && zNeedsCallee != needsCallee()) {
            throw new AssertionError("callee contract violation 2");
        }
        if (!$assertionsDisabled && zIsBoundFunction && zNeedsCallee) {
            throw new AssertionError();
        }
        Object objConvertThisObject = zIsBoundFunction ? null : convertThisObject(obj);
        if (isVarArg(methodHandle)) {
            if (zIsBoundFunction) {
                methodHandleBindTo = methodHandle;
            } else if (zNeedsCallee) {
                methodHandleBindTo = Lookup.f31MH.insertArguments(methodHandle, 0, new Object[]{scriptFunction, objConvertThisObject});
            } else {
                methodHandleBindTo = Lookup.f31MH.bindTo(methodHandle, objConvertThisObject);
            }
            if (objArr.length > 0) {
                methodHandleInsertArguments = varArgBinder(methodHandleBindTo, objArr);
            } else {
                methodHandleInsertArguments = methodHandleBindTo;
            }
        } else {
            int i = zIsBoundFunction ? 1 : 0;
            Object[] objArr2 = new Object[Math.min(methodHandle.type().parameterCount() - i, objArr.length + (zIsBoundFunction ? 0 : zNeedsCallee ? 2 : 1))];
            int i2 = 0;
            if (!zIsBoundFunction) {
                if (zNeedsCallee) {
                    i2 = 0 + 1;
                    objArr2[0] = scriptFunction;
                }
                int i3 = i2;
                i2++;
                objArr2[i3] = objConvertThisObject;
            }
            System.arraycopy(objArr, 0, objArr2, i2, objArr2.length - i2);
            methodHandleInsertArguments = Lookup.f31MH.insertArguments(methodHandle, i, objArr2);
        }
        if (zIsBoundFunction) {
            return methodHandleInsertArguments;
        }
        return Lookup.f31MH.dropArguments(methodHandleInsertArguments, 0, new Class[]{Object.class});
    }

    private static MethodHandle bindConstructHandle(MethodHandle methodHandle, ScriptFunction scriptFunction, Object[] objArr) {
        Object[] objArr2;
        if (!$assertionsDisabled && methodHandle == null) {
            throw new AssertionError();
        }
        MethodHandle methodHandleDropArguments = scriptFunction.isBoundFunction() ? methodHandle : Lookup.f31MH.dropArguments(Lookup.f31MH.bindTo(methodHandle, scriptFunction), 0, new Class[]{ScriptFunction.class});
        if (objArr.length == 0) {
            return methodHandleDropArguments;
        }
        if (isVarArg(methodHandleDropArguments)) {
            return varArgBinder(methodHandleDropArguments, objArr);
        }
        int iParameterCount = methodHandleDropArguments.type().parameterCount() - 1;
        if (objArr.length <= iParameterCount) {
            objArr2 = objArr;
        } else {
            objArr2 = new Object[iParameterCount];
            System.arraycopy(objArr, 0, objArr2, 0, iParameterCount);
        }
        return Lookup.f31MH.insertArguments(methodHandleDropArguments, 1, objArr2);
    }

    private static MethodHandle makeGenericMethod(MethodHandle methodHandle) {
        MethodType methodTypeType = methodHandle.type();
        MethodType methodTypeMakeGenericType = makeGenericType(methodTypeType);
        return methodTypeType.equals(methodTypeMakeGenericType) ? methodHandle : methodHandle.asType(methodTypeMakeGenericType);
    }

    private static MethodType makeGenericType(MethodType methodType) {
        MethodType methodTypeGeneric = methodType.generic();
        if (isVarArg(methodType)) {
            methodTypeGeneric = methodTypeGeneric.changeParameterType(methodType.parameterCount() - 1, Object[].class);
        }
        if (needsCallee(methodType)) {
            methodTypeGeneric = methodTypeGeneric.changeParameterType(0, ScriptFunction.class);
        }
        return methodTypeGeneric;
    }

    Object invoke(ScriptFunction scriptFunction, Object obj, Object[] objArr) {
        MethodHandle genericInvoker = getGenericInvoker(scriptFunction.getScope());
        Object objConvertThisObject = convertThisObject(obj);
        Object[] objArr2 = objArr == null ? ScriptRuntime.EMPTY_ARRAY : objArr;
        if (isVarArg(genericInvoker)) {
            if (needsCallee(genericInvoker)) {
                return (Object) genericInvoker.invokeExact(scriptFunction, objConvertThisObject, objArr2);
            }
            return (Object) genericInvoker.invokeExact(objConvertThisObject, objArr2);
        }
        int iParameterCount = genericInvoker.type().parameterCount();
        if (needsCallee(genericInvoker)) {
            switch (iParameterCount) {
                case 2:
                    return (Object) genericInvoker.invokeExact(scriptFunction, objConvertThisObject);
                case 3:
                    return (Object) genericInvoker.invokeExact(scriptFunction, objConvertThisObject, getArg(objArr2, 0));
                case 4:
                    return (Object) genericInvoker.invokeExact(scriptFunction, objConvertThisObject, getArg(objArr2, 0), getArg(objArr2, 1));
                case 5:
                    return (Object) genericInvoker.invokeExact(scriptFunction, objConvertThisObject, getArg(objArr2, 0), getArg(objArr2, 1), getArg(objArr2, 2));
                case 6:
                    return (Object) genericInvoker.invokeExact(scriptFunction, objConvertThisObject, getArg(objArr2, 0), getArg(objArr2, 1), getArg(objArr2, 2), getArg(objArr2, 3));
                case 7:
                    return (Object) genericInvoker.invokeExact(scriptFunction, objConvertThisObject, getArg(objArr2, 0), getArg(objArr2, 1), getArg(objArr2, 2), getArg(objArr2, 3), getArg(objArr2, 4));
                case 8:
                    return (Object) genericInvoker.invokeExact(scriptFunction, objConvertThisObject, getArg(objArr2, 0), getArg(objArr2, 1), getArg(objArr2, 2), getArg(objArr2, 3), getArg(objArr2, 4), getArg(objArr2, 5));
                default:
                    return genericInvoker.invokeWithArguments(withArguments(scriptFunction, objConvertThisObject, iParameterCount, objArr2));
            }
        }
        switch (iParameterCount) {
            case 1:
                return (Object) genericInvoker.invokeExact(objConvertThisObject);
            case 2:
                return (Object) genericInvoker.invokeExact(objConvertThisObject, getArg(objArr2, 0));
            case 3:
                return (Object) genericInvoker.invokeExact(objConvertThisObject, getArg(objArr2, 0), getArg(objArr2, 1));
            case 4:
                return (Object) genericInvoker.invokeExact(objConvertThisObject, getArg(objArr2, 0), getArg(objArr2, 1), getArg(objArr2, 2));
            case 5:
                return (Object) genericInvoker.invokeExact(objConvertThisObject, getArg(objArr2, 0), getArg(objArr2, 1), getArg(objArr2, 2), getArg(objArr2, 3));
            case 6:
                return (Object) genericInvoker.invokeExact(objConvertThisObject, getArg(objArr2, 0), getArg(objArr2, 1), getArg(objArr2, 2), getArg(objArr2, 3), getArg(objArr2, 4));
            case 7:
                return (Object) genericInvoker.invokeExact(objConvertThisObject, getArg(objArr2, 0), getArg(objArr2, 1), getArg(objArr2, 2), getArg(objArr2, 3), getArg(objArr2, 4), getArg(objArr2, 5));
            default:
                return genericInvoker.invokeWithArguments(withArguments(null, objConvertThisObject, iParameterCount, objArr2));
        }
    }

    Object construct(ScriptFunction scriptFunction, Object[] objArr) {
        MethodHandle genericConstructor = getGenericConstructor(scriptFunction.getScope());
        Object[] objArr2 = objArr == null ? ScriptRuntime.EMPTY_ARRAY : objArr;
        if (isVarArg(genericConstructor)) {
            if (needsCallee(genericConstructor)) {
                return (Object) genericConstructor.invokeExact(scriptFunction, objArr2);
            }
            return (Object) genericConstructor.invokeExact(objArr2);
        }
        int iParameterCount = genericConstructor.type().parameterCount();
        if (needsCallee(genericConstructor)) {
            switch (iParameterCount) {
                case 1:
                    return (Object) genericConstructor.invokeExact(scriptFunction);
                case 2:
                    return (Object) genericConstructor.invokeExact(scriptFunction, getArg(objArr2, 0));
                case 3:
                    return (Object) genericConstructor.invokeExact(scriptFunction, getArg(objArr2, 0), getArg(objArr2, 1));
                case 4:
                    return (Object) genericConstructor.invokeExact(scriptFunction, getArg(objArr2, 0), getArg(objArr2, 1), getArg(objArr2, 2));
                case 5:
                    return (Object) genericConstructor.invokeExact(scriptFunction, getArg(objArr2, 0), getArg(objArr2, 1), getArg(objArr2, 2), getArg(objArr2, 3));
                case 6:
                    return (Object) genericConstructor.invokeExact(scriptFunction, getArg(objArr2, 0), getArg(objArr2, 1), getArg(objArr2, 2), getArg(objArr2, 3), getArg(objArr2, 4));
                case 7:
                    return (Object) genericConstructor.invokeExact(scriptFunction, getArg(objArr2, 0), getArg(objArr2, 1), getArg(objArr2, 2), getArg(objArr2, 3), getArg(objArr2, 4), getArg(objArr2, 5));
                default:
                    return genericConstructor.invokeWithArguments(withArguments(scriptFunction, iParameterCount, objArr2));
            }
        }
        switch (iParameterCount) {
            case 0:
                return (Object) genericConstructor.invokeExact();
            case 1:
                return (Object) genericConstructor.invokeExact(getArg(objArr2, 0));
            case 2:
                return (Object) genericConstructor.invokeExact(getArg(objArr2, 0), getArg(objArr2, 1));
            case 3:
                return (Object) genericConstructor.invokeExact(getArg(objArr2, 0), getArg(objArr2, 1), getArg(objArr2, 2));
            case 4:
                return (Object) genericConstructor.invokeExact(getArg(objArr2, 0), getArg(objArr2, 1), getArg(objArr2, 2), getArg(objArr2, 3));
            case 5:
                return (Object) genericConstructor.invokeExact(getArg(objArr2, 0), getArg(objArr2, 1), getArg(objArr2, 2), getArg(objArr2, 3), getArg(objArr2, 4));
            case 6:
                return (Object) genericConstructor.invokeExact(getArg(objArr2, 0), getArg(objArr2, 1), getArg(objArr2, 2), getArg(objArr2, 3), getArg(objArr2, 4), getArg(objArr2, 5));
            default:
                return genericConstructor.invokeWithArguments(withArguments(null, iParameterCount, objArr2));
        }
    }

    private static Object getArg(Object[] objArr, int i) {
        return i < objArr.length ? objArr[i] : ScriptRuntime.UNDEFINED;
    }

    private static Object[] withArguments(ScriptFunction scriptFunction, int i, Object[] objArr) {
        Object[] objArr2 = new Object[i];
        int i2 = 0;
        if (scriptFunction != null) {
            i2 = 0 + 1;
            objArr2[0] = scriptFunction;
        }
        int i3 = 0;
        while (i3 < objArr.length && i2 < i) {
            int i4 = i2;
            i2++;
            int i5 = i3;
            i3++;
            objArr2[i4] = objArr[i5];
        }
        while (i2 < i) {
            int i6 = i2;
            i2++;
            objArr2[i6] = ScriptRuntime.UNDEFINED;
        }
        return objArr2;
    }

    private static Object[] withArguments(ScriptFunction scriptFunction, Object obj, int i, Object[] objArr) {
        Object[] objArr2 = new Object[i];
        int i2 = 0;
        if (scriptFunction != null) {
            i2 = 0 + 1;
            objArr2[0] = scriptFunction;
        }
        int i3 = i2;
        int i4 = i2 + 1;
        objArr2[i3] = obj;
        int i5 = 0;
        while (i5 < objArr.length && i4 < i) {
            int i6 = i4;
            i4++;
            int i7 = i5;
            i5++;
            objArr2[i6] = objArr[i7];
        }
        while (i4 < i) {
            int i8 = i4;
            i4++;
            objArr2[i8] = ScriptRuntime.UNDEFINED;
        }
        return objArr2;
    }

    private static MethodHandle varArgBinder(MethodHandle methodHandle, Object[] objArr) {
        if (!$assertionsDisabled && objArr == null) {
            throw new AssertionError();
        }
        if ($assertionsDisabled || objArr.length > 0) {
            return Lookup.f31MH.filterArguments(methodHandle, methodHandle.type().parameterCount() - 1, new MethodHandle[]{Lookup.f31MH.bindTo(BIND_VAR_ARGS, objArr)});
        }
        throw new AssertionError();
    }

    protected static boolean needsCallee(MethodHandle methodHandle) {
        return needsCallee(methodHandle.type());
    }

    static boolean needsCallee(MethodType methodType) {
        int iParameterCount = methodType.parameterCount();
        if (iParameterCount == 0) {
            return false;
        }
        Class<?> clsParameterType = methodType.parameterType(0);
        return clsParameterType == ScriptFunction.class || (clsParameterType == Boolean.TYPE && iParameterCount > 1 && methodType.parameterType(1) == ScriptFunction.class);
    }

    protected static boolean isVarArg(MethodHandle methodHandle) {
        return isVarArg(methodHandle.type());
    }

    static boolean isVarArg(MethodType methodType) {
        return methodType.parameterType(methodType.parameterCount() - 1).isArray();
    }

    private static Object[] bindVarArgs(Object[] objArr, Object[] objArr2) {
        if (objArr2 == null) {
            return (Object[]) objArr.clone();
        }
        int length = objArr2.length;
        if (length == 0) {
            return (Object[]) objArr.clone();
        }
        int length2 = objArr.length;
        Object[] objArr3 = new Object[length2 + length];
        System.arraycopy(objArr, 0, objArr3, 0, length2);
        System.arraycopy(objArr2, 0, objArr3, length2, length);
        return objArr3;
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), ScriptFunctionData.class, str, Lookup.f31MH.type(cls, clsArr));
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/ScriptFunctionData$GenericInvokers.class */
    private static final class GenericInvokers {
        MethodHandle invoker;
        MethodHandle constructor;

        private GenericInvokers() {
        }

        GenericInvokers(C02341 c02341) {
            this();
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.code = new LinkedList();
    }
}
