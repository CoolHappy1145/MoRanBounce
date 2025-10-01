package jdk.nashorn.internal.runtime;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.lang.invoke.SwitchPoint;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.logging.Level;
import jdk.internal.dynalink.linker.GuardedInvocation;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.codegen.TypeMap;
import jdk.nashorn.internal.codegen.types.ArrayType;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.annotations.SpecializedFunction;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.runtime.events.RecompilationEvent;
import jdk.nashorn.internal.runtime.linker.Bootstrap;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.spongepowered.asm.mixin.transformer.ActivityStack;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/CompiledFunction.class */
final class CompiledFunction {
    private static final MethodHandle NEWFILTER;
    private static final MethodHandle RELINK_COMPOSABLE_INVOKER;
    private static final MethodHandle HANDLE_REWRITE_EXCEPTION;
    private static final MethodHandle RESTOF_INVOKER;
    private final DebugLogger log;
    static final Collection NO_FUNCTIONS;
    private MethodHandle invoker;
    private MethodHandle constructor;
    private OptimismInfo optimismInfo;
    private final int flags;
    private final MethodType callSiteType;
    private final Specialization specialization;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !CompiledFunction.class.desiredAssertionStatus();
        NEWFILTER = findOwnMH("newFilter", Object.class, new Class[]{Object.class, Object.class});
        RELINK_COMPOSABLE_INVOKER = findOwnMH("relinkComposableInvoker", Void.TYPE, new Class[]{CallSite.class, CompiledFunction.class, Boolean.TYPE});
        HANDLE_REWRITE_EXCEPTION = findOwnMH("handleRewriteException", MethodHandle.class, new Class[]{CompiledFunction.class, OptimismInfo.class, RewriteException.class});
        RESTOF_INVOKER = MethodHandles.exactInvoker(MethodType.methodType((Class<?>) Object.class, (Class<?>) RewriteException.class));
        NO_FUNCTIONS = Collections.emptySet();
    }

    CompiledFunction(MethodHandle methodHandle) {
        this(methodHandle, null, null);
    }

    static CompiledFunction createBuiltInConstructor(MethodHandle methodHandle, Specialization specialization) {
        return new CompiledFunction(Lookup.f31MH.insertArguments(methodHandle, 0, new Object[]{false}), createConstructorFromInvoker(Lookup.f31MH.insertArguments(methodHandle, 0, new Object[]{true})), specialization);
    }

    CompiledFunction(MethodHandle methodHandle, MethodHandle methodHandle2, Specialization specialization) {
        this(methodHandle, methodHandle2, 0, null, specialization, DebugLogger.DISABLED_LOGGER);
    }

    CompiledFunction(MethodHandle methodHandle, MethodHandle methodHandle2, int i, MethodType methodType, Specialization specialization, DebugLogger debugLogger) {
        this.specialization = specialization;
        if (specialization != null && specialization.isOptimistic()) {
            this.invoker = Lookup.f31MH.insertArguments(methodHandle, methodHandle.type().parameterCount() - 1, new Object[]{1});
            throw new AssertionError("Optimistic (UnwarrantedOptimismException throwing) builtin functions are currently not in use");
        }
        this.invoker = methodHandle;
        this.constructor = methodHandle2;
        this.flags = i;
        this.callSiteType = methodType;
        this.log = debugLogger;
    }

    CompiledFunction(MethodHandle methodHandle, RecompilableScriptFunctionData recompilableScriptFunctionData, Map map, MethodType methodType, int i) {
        this(methodHandle, null, i, methodType, null, recompilableScriptFunctionData.getLogger());
        if ((i & 2048) != 0) {
            this.optimismInfo = new OptimismInfo(recompilableScriptFunctionData, map);
        } else {
            this.optimismInfo = null;
        }
    }

    static CompiledFunction createBuiltInConstructor(MethodHandle methodHandle) {
        return new CompiledFunction(Lookup.f31MH.insertArguments(methodHandle, 0, new Object[]{false}), createConstructorFromInvoker(Lookup.f31MH.insertArguments(methodHandle, 0, new Object[]{true})), null);
    }

    boolean isSpecialization() {
        return this.specialization != null;
    }

    boolean hasLinkLogic() {
        return getLinkLogicClass() != null;
    }

    Class getLinkLogicClass() {
        if (isSpecialization()) {
            Class linkLogicClass = this.specialization.getLinkLogicClass();
            if (!$assertionsDisabled) {
                if (linkLogicClass == SpecializedFunction.LinkLogic.Empty.class) {
                    throw new AssertionError("empty link logic classes should have been removed by nasgen");
                }
            }
            return linkLogicClass;
        }
        return null;
    }

    int getFlags() {
        return this.flags;
    }

    boolean isOptimistic() {
        if (isSpecialization()) {
            return this.specialization.isOptimistic();
        }
        return false;
    }

    boolean isApplyToCall() {
        return (this.flags & 4096) != 0;
    }

    boolean isVarArg() {
        return isVarArgsType(this.invoker.type());
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        Class linkLogicClass = getLinkLogicClass();
        sb.append("[invokerType=").append(this.invoker.type()).append(" ctor=").append(this.constructor).append(" weight=").append(weight()).append(" linkLogic=").append(linkLogicClass != null ? linkLogicClass.getSimpleName() : "none");
        return sb.toString();
    }

    boolean needsCallee() {
        return ScriptFunctionData.needsCallee(this.invoker);
    }

    MethodHandle createComposableInvoker() {
        return createComposableInvoker(false);
    }

    private MethodHandle getConstructor() {
        if (this.constructor == null) {
            this.constructor = createConstructorFromInvoker(createInvokerForPessimisticCaller());
        }
        return this.constructor;
    }

    private MethodHandle createInvokerForPessimisticCaller() {
        return createInvoker(Object.class, -1);
    }

    private static MethodHandle createConstructorFromInvoker(MethodHandle methodHandle) {
        boolean zNeedsCallee = ScriptFunctionData.needsCallee(methodHandle);
        MethodHandle methodHandleSwapCalleeAndThis = zNeedsCallee ? swapCalleeAndThis(methodHandle) : methodHandle;
        MethodHandle methodHandleAsType = Lookup.f31MH.asType(methodHandleSwapCalleeAndThis, methodHandleSwapCalleeAndThis.type().changeReturnType(Object.class));
        MethodHandle methodHandleFoldArguments = Lookup.f31MH.foldArguments(Lookup.f31MH.dropArguments(NEWFILTER, 2, methodHandleAsType.type().dropParameterTypes(0, 1).parameterArray()), methodHandleAsType);
        return zNeedsCallee ? Lookup.f31MH.foldArguments(methodHandleFoldArguments, ScriptFunction.ALLOCATE) : Lookup.f31MH.filterArguments(methodHandleFoldArguments, 0, new MethodHandle[]{ScriptFunction.ALLOCATE});
    }

    private static MethodHandle swapCalleeAndThis(MethodHandle methodHandle) {
        MethodType methodTypeType = methodHandle.type();
        if (!$assertionsDisabled && methodTypeType.parameterType(0) != ScriptFunction.class) {
            throw new AssertionError(methodTypeType);
        }
        if (!$assertionsDisabled && methodTypeType.parameterType(1) != Object.class) {
            throw new AssertionError(methodTypeType);
        }
        MethodType methodTypeChangeParameterType = methodTypeType.changeParameterType(0, Object.class).changeParameterType(1, ScriptFunction.class);
        int[] iArr = new int[methodTypeType.parameterCount()];
        iArr[0] = 1;
        if (!$assertionsDisabled && iArr[1] != 0) {
            throw new AssertionError();
        }
        for (int i = 2; i < iArr.length; i++) {
            iArr[i] = i;
        }
        return MethodHandles.permuteArguments(methodHandle, methodTypeChangeParameterType, iArr);
    }

    MethodHandle createComposableConstructor() {
        return createComposableInvoker(true);
    }

    boolean hasConstructor() {
        return this.constructor != null;
    }

    MethodType type() {
        return this.invoker.type();
    }

    int weight() {
        return weight(type());
    }

    private static int weight(MethodType methodType) {
        if (isVarArgsType(methodType)) {
            return Integer.MAX_VALUE;
        }
        int weight = Type.typeFor(methodType.returnType()).getWeight();
        for (int i = 0; i < methodType.parameterCount(); i++) {
            weight += Type.typeFor(methodType.parameterType(i)).getWeight() * 2;
        }
        return weight + methodType.parameterCount();
    }

    static boolean isVarArgsType(MethodType methodType) {
        if ($assertionsDisabled || methodType.parameterCount() >= 1) {
            return methodType.parameterType(methodType.parameterCount() - 1) == Object[].class;
        }
        throw new AssertionError(methodType);
    }

    static boolean moreGenericThan(MethodType methodType, MethodType methodType2) {
        return weight(methodType) > weight(methodType2);
    }

    boolean betterThanFinal(CompiledFunction compiledFunction, MethodType methodType) {
        if (compiledFunction == null) {
            return true;
        }
        return betterThanFinal(this, compiledFunction, methodType);
    }

    private static boolean betterThanFinal(CompiledFunction compiledFunction, CompiledFunction compiledFunction2, MethodType methodType) {
        MethodType methodTypeType = compiledFunction.type();
        MethodType methodTypeType2 = compiledFunction2.type();
        int paramCount = getParamCount(methodTypeType);
        int paramCount2 = getParamCount(methodTypeType2);
        int paramCount3 = getParamCount(methodType);
        boolean z = paramCount3 == Integer.MAX_VALUE;
        int i = z ? paramCount3 : paramCount3 - 1;
        int iMax = Math.max(i - paramCount, 0);
        int iMax2 = Math.max(i - paramCount2, 0);
        if (iMax < iMax2) {
            return true;
        }
        if (iMax > iMax2) {
            return false;
        }
        boolean z2 = paramCount == Integer.MAX_VALUE;
        boolean z3 = paramCount2 == Integer.MAX_VALUE;
        if (!z2 || !z3 || !z) {
            Type[] typeWithoutCallee = toTypeWithoutCallee(methodTypeType, 0);
            Type[] typeWithoutCallee2 = toTypeWithoutCallee(methodTypeType2, 0);
            Type[] typeWithoutCallee3 = toTypeWithoutCallee(methodType, 1);
            int iMax3 = 0;
            int iMax4 = 0;
            int iMin = Math.min(Math.min(paramCount, paramCount2), i);
            for (int i2 = 0; i2 < iMin; i2++) {
                int weight = getParamType(i2, typeWithoutCallee3, z).getWeight();
                int weight2 = getParamType(i2, typeWithoutCallee, z2).getWeight() - weight;
                int weight3 = getParamType(i2, typeWithoutCallee2, z3).getWeight() - weight;
                iMax3 += Math.max(-weight2, 0) - Math.max(-weight3, 0);
                iMax4 += Math.max(weight2, 0) - Math.max(weight3, 0);
            }
            if (!z2) {
                for (int i3 = i; i3 < paramCount; i3++) {
                    iMax3 += Math.max(Type.OBJECT.getWeight() - typeWithoutCallee[i3].getWeight(), 0);
                }
            }
            if (!z3) {
                for (int i4 = i; i4 < paramCount2; i4++) {
                    iMax3 -= Math.max(Type.OBJECT.getWeight() - typeWithoutCallee2[i4].getWeight(), 0);
                }
            }
            if (iMax3 < 0) {
                return true;
            }
            if (iMax3 > 0) {
                return false;
            }
            if (iMax4 < 0) {
                return true;
            }
            if (iMax4 > 0) {
                return false;
            }
        }
        if (paramCount == i && paramCount2 != i) {
            return true;
        }
        if (paramCount != i && paramCount2 == i) {
            return false;
        }
        if (z2) {
            if (!z3) {
                return true;
            }
        } else if (z3) {
            return false;
        }
        int i5 = paramCount - paramCount2;
        if (i5 < 0) {
            return true;
        }
        if (i5 > 0) {
            return false;
        }
        int weight4 = Type.typeFor(methodType.returnType()).getWeight();
        int weight5 = Type.typeFor(methodTypeType.returnType()).getWeight() - weight4;
        int weight6 = Type.typeFor(methodTypeType2.returnType()).getWeight() - weight4;
        int iMax5 = Math.max(weight5, 0) - Math.max(weight6, 0);
        if (iMax5 < 0) {
            return true;
        }
        if (iMax5 > 0) {
            return false;
        }
        int iMax6 = Math.max(-weight5, 0) - Math.max(-weight6, 0);
        if (iMax6 < 0) {
            return true;
        }
        if (iMax6 > 0) {
            return false;
        }
        if (compiledFunction.isSpecialization() != compiledFunction2.isSpecialization()) {
            return compiledFunction.isSpecialization();
        }
        if (compiledFunction.isSpecialization() && compiledFunction2.isSpecialization()) {
            return compiledFunction.getLinkLogicClass() != null;
        }
        throw new AssertionError(methodTypeType + " identically applicable to " + methodTypeType2 + " for " + methodType);
    }

    private static Type[] toTypeWithoutCallee(MethodType methodType, int i) {
        int iParameterCount = methodType.parameterCount();
        Type[] typeArr = new Type[iParameterCount - i];
        for (int i2 = i; i2 < iParameterCount; i2++) {
            typeArr[i2 - i] = Type.typeFor(methodType.parameterType(i2));
        }
        return typeArr;
    }

    private static Type getParamType(int i, Type[] typeArr, boolean z) {
        if (i < typeArr.length - (z ? 1 : 0)) {
            return typeArr[i];
        }
        if ($assertionsDisabled || z) {
            return ((ArrayType) typeArr[typeArr.length - 1]).getElementType();
        }
        throw new AssertionError();
    }

    boolean matchesCallSite(MethodType methodType, boolean z) {
        if (methodType.equals(this.callSiteType)) {
            return true;
        }
        MethodType methodTypeType = type();
        int paramCount = getParamCount(methodTypeType);
        if (paramCount == Integer.MAX_VALUE) {
            return z;
        }
        int paramCount2 = getParamCount(methodType);
        boolean z2 = paramCount2 == Integer.MAX_VALUE;
        if (z2 && isApplyToCall()) {
            return false;
        }
        int i = needsCallee() ? 1 : 0;
        int i2 = paramCount - i;
        int iMin = Math.min(paramCount2 - 1, i2);
        for (int i3 = 0; i3 < iMin; i3++) {
            if (!Type.typeFor(methodTypeType.parameterType(i3 + i)).isEquivalentTo(z2 ? Type.OBJECT : Type.typeFor(methodType.parameterType(i3 + 1)))) {
                return false;
            }
        }
        for (int i4 = iMin; i4 < i2; i4++) {
            if (!Type.typeFor(methodTypeType.parameterType(i4 + i)).isEquivalentTo(Type.OBJECT)) {
                return false;
            }
        }
        return true;
    }

    private static int getParamCount(MethodType methodType) {
        int iParameterCount = methodType.parameterCount();
        if (methodType.parameterType(iParameterCount - 1).isArray()) {
            return Integer.MAX_VALUE;
        }
        return iParameterCount;
    }

    private boolean canBeDeoptimized() {
        return this.optimismInfo != null;
    }

    private MethodHandle createComposableInvoker(boolean z) throws InterruptedException {
        MethodHandle invokerOrConstructor = getInvokerOrConstructor(z);
        if (!canBeDeoptimized()) {
            return invokerOrConstructor;
        }
        MutableCallSite mutableCallSite = new MutableCallSite(invokerOrConstructor.type());
        relinkComposableInvoker(mutableCallSite, this, z);
        return mutableCallSite.dynamicInvoker();
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/CompiledFunction$HandleAndAssumptions.class */
    private static class HandleAndAssumptions {
        final MethodHandle handle;
        final SwitchPoint assumptions;

        HandleAndAssumptions(MethodHandle methodHandle, SwitchPoint switchPoint) {
            this.handle = methodHandle;
            this.assumptions = switchPoint;
        }

        GuardedInvocation createInvocation() {
            return new GuardedInvocation(this.handle, this.assumptions);
        }
    }

    private HandleAndAssumptions getValidOptimisticInvocation(Supplier supplier) throws InterruptedException {
        MethodHandle methodHandle;
        SwitchPoint switchPoint;
        while (true) {
            methodHandle = (MethodHandle) supplier.get();
            switchPoint = canBeDeoptimized() ? this.optimismInfo.optimisticAssumptions : null;
            if (switchPoint == null || !switchPoint.hasBeenInvalidated()) {
                break;
            }
            try {
                wait();
            } catch (InterruptedException unused) {
            }
        }
        return new HandleAndAssumptions(methodHandle, switchPoint);
    }

    private static void relinkComposableInvoker(CallSite callSite, CompiledFunction compiledFunction, boolean z) throws InterruptedException {
        MethodHandle methodHandleGuardWithTest;
        HandleAndAssumptions validOptimisticInvocation = compiledFunction.getValidOptimisticInvocation(new Supplier(compiledFunction, z) { // from class: jdk.nashorn.internal.runtime.CompiledFunction.1
            final CompiledFunction val$inv;
            final boolean val$constructor;

            {
                this.val$inv = compiledFunction;
                this.val$constructor = z;
            }

            @Override // java.util.function.Supplier
            public Object get() {
                return get();
            }

            @Override // java.util.function.Supplier
            public MethodHandle get() {
                return this.val$inv.getInvokerOrConstructor(this.val$constructor);
            }
        });
        MethodHandle methodHandle = validOptimisticInvocation.handle;
        SwitchPoint switchPoint = validOptimisticInvocation.assumptions;
        if (switchPoint == null) {
            methodHandleGuardWithTest = methodHandle;
        } else {
            methodHandleGuardWithTest = switchPoint.guardWithTest(methodHandle, MethodHandles.foldArguments(callSite.dynamicInvoker(), MethodHandles.insertArguments(RELINK_COMPOSABLE_INVOKER, 0, callSite, compiledFunction, Boolean.valueOf(z))));
        }
        callSite.setTarget(methodHandleGuardWithTest.asType(callSite.type()));
    }

    private MethodHandle getInvokerOrConstructor(boolean z) {
        return z ? getConstructor() : createInvokerForPessimisticCaller();
    }

    GuardedInvocation createFunctionInvocation(Class cls, int i) {
        return getValidOptimisticInvocation(new Supplier(this, cls, i) { // from class: jdk.nashorn.internal.runtime.CompiledFunction.2
            final Class val$callSiteReturnType;
            final int val$callerProgramPoint;
            final CompiledFunction this$0;

            {
                this.this$0 = this;
                this.val$callSiteReturnType = cls;
                this.val$callerProgramPoint = i;
            }

            @Override // java.util.function.Supplier
            public Object get() {
                return get();
            }

            @Override // java.util.function.Supplier
            public MethodHandle get() {
                return this.this$0.createInvoker(this.val$callSiteReturnType, this.val$callerProgramPoint);
            }
        }).createInvocation();
    }

    GuardedInvocation createConstructorInvocation() {
        return getValidOptimisticInvocation(new Supplier(this) { // from class: jdk.nashorn.internal.runtime.CompiledFunction.3
            final CompiledFunction this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.function.Supplier
            public Object get() {
                return get();
            }

            @Override // java.util.function.Supplier
            public MethodHandle get() {
                return this.this$0.getConstructor();
            }
        }).createInvocation();
    }

    private MethodHandle createInvoker(Class cls, int i) {
        boolean zCanBeDeoptimized = canBeDeoptimized();
        MethodHandle methodHandleCreateRewriteExceptionHandler = zCanBeDeoptimized ? createRewriteExceptionHandler() : null;
        MethodHandle methodHandleChangeReturnType = this.invoker;
        if (UnwarrantedOptimismException.isValid(i)) {
            methodHandleChangeReturnType = changeReturnType(OptimisticReturnFilters.filterOptimisticReturnValue(methodHandleChangeReturnType, cls, i), cls);
            if (cls.isPrimitive() && methodHandleCreateRewriteExceptionHandler != null) {
                methodHandleCreateRewriteExceptionHandler = OptimisticReturnFilters.filterOptimisticReturnValue(methodHandleCreateRewriteExceptionHandler, cls, i);
            }
        } else if (zCanBeDeoptimized) {
            methodHandleChangeReturnType = changeReturnType(methodHandleChangeReturnType, cls);
        }
        if (zCanBeDeoptimized) {
            if ($assertionsDisabled || methodHandleCreateRewriteExceptionHandler != null) {
                return Lookup.f31MH.catchException(methodHandleChangeReturnType, RewriteException.class, changeReturnType(methodHandleCreateRewriteExceptionHandler, methodHandleChangeReturnType.type().returnType()));
            }
            throw new AssertionError();
        }
        return methodHandleChangeReturnType;
    }

    private MethodHandle createRewriteExceptionHandler() {
        return Lookup.f31MH.foldArguments(RESTOF_INVOKER, Lookup.f31MH.insertArguments(HANDLE_REWRITE_EXCEPTION, 0, new Object[]{this, this.optimismInfo}));
    }

    private static MethodHandle changeReturnType(MethodHandle methodHandle, Class cls) {
        return Bootstrap.getLinkerServices().asType(methodHandle, methodHandle.type().changeReturnType((Class<?>) cls));
    }

    private static MethodHandle handleRewriteException(CompiledFunction compiledFunction, OptimismInfo optimismInfo, RewriteException rewriteException) {
        return compiledFunction.handleRewriteException(optimismInfo, rewriteException);
    }

    private static List toStringInvalidations(Map map) {
        String strValueOf;
        if (map == null) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : map.entrySet()) {
            char bytecodeStackType = ((Type) entry.getValue()).getBytecodeStackType();
            switch (((Type) entry.getValue()).getBytecodeStackType()) {
                case OPCode.REPEAT_INC_NG_SG /* 65 */:
                    strValueOf = "object";
                    break;
                case OPCode.NULL_CHECK_START /* 66 */:
                case OPCode.NULL_CHECK_END /* 67 */:
                case OPCode.NULL_CHECK_END_MEMST_PUSH /* 69 */:
                case OPCode.PUSH_POS /* 70 */:
                case OPCode.POP_POS /* 71 */:
                case 'H':
                default:
                    strValueOf = String.valueOf(bytecodeStackType);
                    break;
                case OPCode.NULL_CHECK_END_MEMST /* 68 */:
                    strValueOf = "double";
                    break;
                case OPCode.FAIL_POS /* 73 */:
                    strValueOf = "int";
                    break;
                case OPCode.PUSH_STOP_BT /* 74 */:
                    strValueOf = "long";
                    break;
            }
            String str = strValueOf;
            StringBuilder sb = new StringBuilder();
            sb.append('[').append("program point: ").append(entry.getKey()).append(ActivityStack.GLUE_STRING).append(str).append(']');
            arrayList.add(sb.toString());
        }
        return arrayList;
    }

    private void logRecompile(String str, FunctionNode functionNode, MethodType methodType, Map map) {
        if (this.log.isEnabled()) {
            this.log.info(new Object[]{str, DebugLogger.quote(functionNode.getName()), " signature: ", methodType});
            this.log.indent();
            Iterator it = toStringInvalidations(map).iterator();
            while (it.hasNext()) {
                this.log.fine((String) it.next());
            }
            this.log.unindent();
        }
    }

    private MethodHandle handleRewriteException(OptimismInfo optimismInfo, RewriteException rewriteException) {
        if (this.log.isEnabled()) {
            this.log.info(new RecompilationEvent(Level.INFO, rewriteException, rewriteException.getReturnValueNonDestructive()), new Object[]{"caught RewriteException ", rewriteException.getMessageShort()});
            this.log.indent();
        }
        MethodType methodTypeType = type();
        MethodType methodTypeInsertParameterTypes = methodTypeType.parameterType(0) == ScriptFunction.class ? methodTypeType : methodTypeType.insertParameterTypes(0, ScriptFunction.class);
        OptimismInfo optimismInfo2 = this.optimismInfo;
        boolean z = optimismInfo2 != null && optimismInfo2.requestRecompile(rewriteException);
        OptimismInfo optimismInfo3 = optimismInfo2 != null ? optimismInfo2 : optimismInfo;
        FunctionNode functionNodeReparse = optimismInfo3.reparse();
        boolean zIsCached = functionNodeReparse.isCached();
        Compiler compiler = optimismInfo3.getCompiler(functionNodeReparse, methodTypeInsertParameterTypes, rewriteException);
        if (!z) {
            logRecompile("Rest-of compilation [STANDALONE] ", functionNodeReparse, methodTypeInsertParameterTypes, optimismInfo3.invalidatedProgramPoints);
            return restOfHandle(optimismInfo3, compiler.compile(functionNodeReparse, zIsCached ? Compiler.CompilationPhases.COMPILE_CACHED_RESTOF : Compiler.CompilationPhases.COMPILE_ALL_RESTOF), optimismInfo2 != null);
        }
        logRecompile("Deoptimizing recompilation (up to bytecode) ", functionNodeReparse, methodTypeInsertParameterTypes, optimismInfo3.invalidatedProgramPoints);
        FunctionNode functionNodeCompile = compiler.compile(functionNodeReparse, zIsCached ? Compiler.CompilationPhases.RECOMPILE_CACHED_UPTO_BYTECODE : Compiler.CompilationPhases.COMPILE_UPTO_BYTECODE);
        this.log.fine("Reusable IR generated");
        this.log.info("Generating and installing bytecode from reusable IR...");
        logRecompile("Rest-of compilation [CODE PIPELINE REUSE] ", functionNodeCompile, methodTypeInsertParameterTypes, optimismInfo3.invalidatedProgramPoints);
        FunctionNode functionNodeCompile2 = compiler.compile(functionNodeCompile, Compiler.CompilationPhases.GENERATE_BYTECODE_AND_INSTALL);
        if (optimismInfo3.data.usePersistentCodeCache()) {
            RecompilableScriptFunctionData recompilableScriptFunctionData = optimismInfo3.data;
            int functionNodeId = recompilableScriptFunctionData.getFunctionNodeId();
            TypeMap typeMap = recompilableScriptFunctionData.typeMap(methodTypeInsertParameterTypes);
            compiler.persistClassInfo(CodeStore.getCacheKey(Integer.valueOf(functionNodeId), typeMap == null ? null : typeMap.getParameterTypes(functionNodeId)), functionNodeCompile2);
        }
        boolean zCanBeDeoptimized = functionNodeCompile2.canBeDeoptimized();
        if (this.log.isEnabled()) {
            this.log.unindent();
            this.log.info("Done.");
            DebugLogger debugLogger = this.log;
            Object[] objArr = new Object[6];
            objArr[0] = "Recompiled '";
            objArr[1] = functionNodeCompile.getName();
            objArr[2] = "' (";
            objArr[3] = Debug.m11id(this);
            objArr[4] = ") ";
            objArr[5] = zCanBeDeoptimized ? "can still be deoptimized." : " is completely deoptimized.";
            debugLogger.info(objArr);
            this.log.finest("Looking up invoker...");
        }
        MethodHandle methodHandleLookup = optimismInfo3.data.lookup(functionNodeCompile);
        this.invoker = methodHandleLookup.asType(methodTypeType.changeReturnType(methodHandleLookup.type().returnType()));
        this.constructor = null;
        this.log.info(new Object[]{"Done: ", this.invoker});
        MethodHandle methodHandleRestOfHandle = restOfHandle(optimismInfo3, compiler.compile(functionNodeCompile, Compiler.CompilationPhases.GENERATE_BYTECODE_AND_INSTALL_RESTOF), zCanBeDeoptimized);
        if (!zCanBeDeoptimized) {
            this.optimismInfo = null;
        } else {
            optimismInfo3.newOptimisticAssumptions();
        }
        notifyAll();
        return methodHandleRestOfHandle;
    }

    private MethodHandle restOfHandle(OptimismInfo optimismInfo, FunctionNode functionNode, boolean z) {
        if (!$assertionsDisabled && optimismInfo == null) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !functionNode.getCompileUnit().getUnitClassName().contains("restOf")) {
            throw new AssertionError();
        }
        MethodHandle methodHandleChangeReturnType = changeReturnType(optimismInfo.data.lookupCodeMethod(functionNode.getCompileUnit().getCode(), Lookup.f31MH.type(functionNode.getReturnType().getTypeClass(), new Class[]{RewriteException.class})), Object.class);
        if (!z) {
            return methodHandleChangeReturnType;
        }
        return Lookup.f31MH.catchException(methodHandleChangeReturnType, RewriteException.class, createRewriteExceptionHandler());
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/CompiledFunction$OptimismInfo.class */
    private static class OptimismInfo {
        private final RecompilableScriptFunctionData data;
        private final Map invalidatedProgramPoints;
        private SwitchPoint optimisticAssumptions;
        private final DebugLogger log;

        OptimismInfo(RecompilableScriptFunctionData recompilableScriptFunctionData, Map map) {
            this.data = recompilableScriptFunctionData;
            this.log = recompilableScriptFunctionData.getLogger();
            this.invalidatedProgramPoints = map == null ? new TreeMap() : map;
            newOptimisticAssumptions();
        }

        private void newOptimisticAssumptions() {
            this.optimisticAssumptions = new SwitchPoint();
        }

        boolean requestRecompile(RewriteException rewriteException) {
            String name;
            Type returnType = rewriteException.getReturnType();
            Type type = (Type) this.invalidatedProgramPoints.put(Integer.valueOf(rewriteException.getProgramPoint()), returnType);
            if (type != null && !type.narrowerThan(returnType)) {
                StackTraceElement[] stackTrace = rewriteException.getStackTrace();
                if (stackTrace.length == 0) {
                    name = this.data.getName();
                } else {
                    name = stackTrace[0].getClassName() + "." + stackTrace[0].getMethodName();
                }
                this.log.info(new Object[]{"RewriteException for an already invalidated program point ", Integer.valueOf(rewriteException.getProgramPoint()), " in ", name, ". This is okay for a recursive function invocation, but a bug otherwise."});
                return false;
            }
            SwitchPoint.invalidateAll(new SwitchPoint[]{this.optimisticAssumptions});
            return true;
        }

        Compiler getCompiler(FunctionNode functionNode, MethodType methodType, RewriteException rewriteException) {
            return this.data.getCompiler(functionNode, methodType, rewriteException.getRuntimeScope(), this.invalidatedProgramPoints, getEntryPoints(rewriteException));
        }

        private static int[] getEntryPoints(RewriteException rewriteException) {
            int[] iArr;
            int[] previousContinuationEntryPoints = rewriteException.getPreviousContinuationEntryPoints();
            if (previousContinuationEntryPoints == null) {
                iArr = new int[1];
            } else {
                int length = previousContinuationEntryPoints.length;
                iArr = new int[length + 1];
                System.arraycopy(previousContinuationEntryPoints, 0, iArr, 1, length);
            }
            iArr[0] = rewriteException.getProgramPoint();
            return iArr;
        }

        FunctionNode reparse() {
            return this.data.reparse();
        }
    }

    private static Object newFilter(Object obj, Object obj2) {
        return ((obj instanceof ScriptObject) || !JSType.isPrimitive(obj)) ? obj : obj2;
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), CompiledFunction.class, str, Lookup.f31MH.type(cls, clsArr));
    }
}
