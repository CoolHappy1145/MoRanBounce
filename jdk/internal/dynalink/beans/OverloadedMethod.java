package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.support.Lookup;
import jdk.internal.dynalink.support.TypeUtilities;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/OverloadedMethod.class */
class OverloadedMethod {
    private final Map argTypesToMethods = new ConcurrentHashMap();
    private final OverloadedDynamicMethod parent;
    private final MethodType callSiteType;
    private final MethodHandle invoker;
    private final LinkerServices linkerServices;
    private final ArrayList fixArgMethods;
    private final ArrayList varArgMethods;
    private static final MethodHandle SELECT_METHOD = Lookup.findOwnSpecial(MethodHandles.lookup(), "selectMethod", MethodHandle.class, new Class[]{Object[].class});
    private static final MethodHandle THROW_NO_SUCH_METHOD = Lookup.findOwnSpecial(MethodHandles.lookup(), "throwNoSuchMethod", Void.TYPE, new Class[]{Class[].class});
    private static final MethodHandle THROW_AMBIGUOUS_METHOD = Lookup.findOwnSpecial(MethodHandles.lookup(), "throwAmbiguousMethod", Void.TYPE, new Class[]{Class[].class, List.class});

    OverloadedMethod(List list, OverloadedDynamicMethod overloadedDynamicMethod, MethodType methodType, LinkerServices linkerServices) {
        this.parent = overloadedDynamicMethod;
        this.callSiteType = methodType.changeReturnType(getCommonReturnType(list));
        this.linkerServices = linkerServices;
        this.fixArgMethods = new ArrayList(list.size());
        this.varArgMethods = new ArrayList(list.size());
        int iParameterCount = methodType.parameterCount();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            MethodHandle methodHandle = (MethodHandle) it.next();
            if (methodHandle.isVarargsCollector()) {
                MethodHandle methodHandleAsFixedArity = methodHandle.asFixedArity();
                if (iParameterCount == methodHandleAsFixedArity.type().parameterCount()) {
                    this.fixArgMethods.add(methodHandleAsFixedArity);
                }
                this.varArgMethods.add(methodHandle);
            } else {
                this.fixArgMethods.add(methodHandle);
            }
        }
        this.fixArgMethods.trimToSize();
        this.varArgMethods.trimToSize();
        this.invoker = linkerServices.asTypeLosslessReturn(MethodHandles.foldArguments(MethodHandles.exactInvoker(this.callSiteType), SingleDynamicMethod.collectArguments(SELECT_METHOD.bindTo(this), iParameterCount).asType(methodType.changeReturnType(MethodHandle.class))), methodType);
    }

    MethodHandle getInvoker() {
        return this.invoker;
    }

    private MethodHandle selectMethod(Object[] objArr) {
        Class[] clsArr = new Class[objArr.length];
        for (int i = 0; i < clsArr.length; i++) {
            Object obj = objArr[i];
            clsArr[i] = obj == null ? ClassString.NULL_CLASS : obj.getClass();
        }
        ClassString classString = new ClassString(clsArr);
        MethodHandle ambiguousMethodThrower = (MethodHandle) this.argTypesToMethods.get(classString);
        if (ambiguousMethodThrower == null) {
            List maximallySpecifics = classString.getMaximallySpecifics(this.fixArgMethods, this.linkerServices, false);
            if (maximallySpecifics.isEmpty()) {
                maximallySpecifics = classString.getMaximallySpecifics(this.varArgMethods, this.linkerServices, true);
            }
            switch (maximallySpecifics.size()) {
                case 0:
                    ambiguousMethodThrower = getNoSuchMethodThrower(clsArr);
                    break;
                case 1:
                    ambiguousMethodThrower = SingleDynamicMethod.getInvocation((MethodHandle) maximallySpecifics.get(0), this.callSiteType, this.linkerServices);
                    break;
                default:
                    ambiguousMethodThrower = getAmbiguousMethodThrower(clsArr, maximallySpecifics);
                    break;
            }
            if (classString.isVisibleFrom(this.parent.getClassLoader())) {
                this.argTypesToMethods.put(classString, ambiguousMethodThrower);
            }
        }
        return ambiguousMethodThrower;
    }

    private MethodHandle getNoSuchMethodThrower(Class[] clsArr) {
        return adaptThrower(MethodHandles.insertArguments(THROW_NO_SUCH_METHOD, 0, this, clsArr));
    }

    private void throwNoSuchMethod(Class[] clsArr) throws NoSuchMethodException {
        if (this.varArgMethods.isEmpty()) {
            throw new NoSuchMethodException("None of the fixed arity signatures " + getSignatureList(this.fixArgMethods) + " of method " + this.parent.getName() + " match the argument types " + argTypesString(clsArr));
        }
        throw new NoSuchMethodException("None of the fixed arity signatures " + getSignatureList(this.fixArgMethods) + " or the variable arity signatures " + getSignatureList(this.varArgMethods) + " of the method " + this.parent.getName() + " match the argument types " + argTypesString(clsArr));
    }

    private MethodHandle getAmbiguousMethodThrower(Class[] clsArr, List list) {
        return adaptThrower(MethodHandles.insertArguments(THROW_AMBIGUOUS_METHOD, 0, this, clsArr, list));
    }

    private MethodHandle adaptThrower(MethodHandle methodHandle) {
        return MethodHandles.dropArguments(methodHandle, 0, this.callSiteType.parameterList()).asType(this.callSiteType);
    }

    private void throwAmbiguousMethod(Class[] clsArr, List list) throws NoSuchMethodException {
        throw new NoSuchMethodException("Can't unambiguously select between " + (((MethodHandle) list.get(0)).isVarargsCollector() ? "variable" : "fixed") + " arity signatures " + getSignatureList(list) + " of the method " + this.parent.getName() + " for argument types " + argTypesString(clsArr));
    }

    private static String argTypesString(Class[] clsArr) {
        StringBuilder sbAppend = new StringBuilder().append('[');
        appendTypes(sbAppend, clsArr, false);
        return sbAppend.append(']').toString();
    }

    private static String getSignatureList(List list) {
        StringBuilder sbAppend = new StringBuilder().append('[');
        Iterator it = list.iterator();
        if (it.hasNext()) {
            appendSig(sbAppend, (MethodHandle) it.next());
            while (it.hasNext()) {
                appendSig(sbAppend.append(", "), (MethodHandle) it.next());
            }
        }
        return sbAppend.append(']').toString();
    }

    private static void appendSig(StringBuilder sb, MethodHandle methodHandle) {
        sb.append('(');
        appendTypes(sb, methodHandle.type().parameterArray(), methodHandle.isVarargsCollector());
        sb.append(')');
    }

    private static void appendTypes(StringBuilder sb, Class[] clsArr, boolean z) {
        int length = clsArr.length;
        if (!z) {
            if (length > 1) {
                sb.append(clsArr[1].getCanonicalName());
                for (int i = 2; i < length; i++) {
                    sb.append(", ").append(clsArr[i].getCanonicalName());
                }
                return;
            }
            return;
        }
        for (int i2 = 1; i2 < length - 1; i2++) {
            sb.append(clsArr[i2].getCanonicalName()).append(", ");
        }
        sb.append(clsArr[length - 1].getComponentType().getCanonicalName()).append("...");
    }

    private static Class getCommonReturnType(List list) {
        Iterator it = list.iterator();
        Class<?> clsReturnType = ((MethodHandle) it.next()).type().returnType();
        while (true) {
            Class<?> cls = clsReturnType;
            if (it.hasNext()) {
                clsReturnType = TypeUtilities.getCommonLosslessConversionType(cls, ((MethodHandle) it.next()).type().returnType());
            } else {
                return cls;
            }
        }
    }
}
