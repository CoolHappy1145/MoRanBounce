package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/FinalScriptFunctionData.class */
final class FinalScriptFunctionData extends ScriptFunctionData {
    private static final long serialVersionUID = -930632846167768864L;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !FinalScriptFunctionData.class.desiredAssertionStatus();
    }

    FinalScriptFunctionData(String str, int i, List list, int i2) {
        super(str, i, i2);
        this.code.addAll(list);
        if (!$assertionsDisabled && needsCallee()) {
            throw new AssertionError();
        }
    }

    FinalScriptFunctionData(String str, MethodHandle methodHandle, Specialization[] specializationArr, int i) {
        super(str, methodHandleArity(methodHandle), i);
        addInvoker(methodHandle);
        if (specializationArr != null) {
            for (Specialization specialization : specializationArr) {
                addInvoker(specialization.getMethodHandle(), specialization);
            }
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    protected boolean needsCallee() {
        boolean zNeedsCallee = ((CompiledFunction) this.code.getFirst()).needsCallee();
        if ($assertionsDisabled || allNeedCallee(zNeedsCallee)) {
            return zNeedsCallee;
        }
        throw new AssertionError();
    }

    private boolean allNeedCallee(boolean z) {
        Iterator it = this.code.iterator();
        while (it.hasNext()) {
            if (((CompiledFunction) it.next()).needsCallee() != z) {
                return false;
            }
        }
        return true;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    CompiledFunction getBest(MethodType methodType, ScriptObject scriptObject, Collection collection, boolean z) {
        if (!$assertionsDisabled && !isValidCallSite(methodType)) {
            throw new AssertionError(methodType);
        }
        CompiledFunction compiledFunction = null;
        Iterator it = this.code.iterator();
        while (it.hasNext()) {
            CompiledFunction compiledFunction2 = (CompiledFunction) it.next();
            if (z || !compiledFunction2.hasLinkLogic()) {
                if (!collection.contains(compiledFunction2) && compiledFunction2.betterThanFinal(compiledFunction, methodType)) {
                    compiledFunction = compiledFunction2;
                }
            }
        }
        return compiledFunction;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptFunctionData
    MethodType getGenericType() {
        int i = 0;
        Iterator it = this.code.iterator();
        while (it.hasNext()) {
            MethodType methodTypeType = ((CompiledFunction) it.next()).type();
            if (ScriptFunctionData.isVarArg(methodTypeType)) {
                return MethodType.genericMethodType(2, true);
            }
            int iParameterCount = methodTypeType.parameterCount() - (ScriptFunctionData.needsCallee(methodTypeType) ? 1 : 0);
            if (iParameterCount > i) {
                i = iParameterCount;
            }
        }
        return MethodType.genericMethodType(i + 1);
    }

    private CompiledFunction addInvoker(MethodHandle methodHandle, Specialization specialization) {
        CompiledFunction compiledFunction;
        if (!$assertionsDisabled && needsCallee(methodHandle)) {
            throw new AssertionError();
        }
        if (isConstructor(methodHandle)) {
            if (!$assertionsDisabled && !isConstructor()) {
                throw new AssertionError();
            }
            compiledFunction = CompiledFunction.createBuiltInConstructor(methodHandle);
        } else {
            compiledFunction = new CompiledFunction(methodHandle, null, specialization);
        }
        this.code.add(compiledFunction);
        return compiledFunction;
    }

    private CompiledFunction addInvoker(MethodHandle methodHandle) {
        return addInvoker(methodHandle, null);
    }

    private static int methodHandleArity(MethodHandle methodHandle) {
        if (isVarArg(methodHandle)) {
            return LinkerCallSite.ARGLIMIT;
        }
        return ((methodHandle.type().parameterCount() - 1) - (needsCallee(methodHandle) ? 1 : 0)) - (isConstructor(methodHandle) ? 1 : 0);
    }

    private static boolean isConstructor(MethodHandle methodHandle) {
        return methodHandle.type().parameterCount() >= 1 && methodHandle.type().parameterType(0) == Boolean.TYPE;
    }
}
