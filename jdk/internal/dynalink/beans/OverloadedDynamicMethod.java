package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.internal.dynalink.beans.ApplicableOverloadedMethods;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.internal.dynalink.support.TypeUtilities;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/OverloadedDynamicMethod.class */
class OverloadedDynamicMethod extends DynamicMethod {
    private final LinkedList methods;
    private final ClassLoader classLoader;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !OverloadedDynamicMethod.class.desiredAssertionStatus();
    }

    OverloadedDynamicMethod(Class cls, String str) {
        this(new LinkedList(), cls.getClassLoader(), getClassAndMethodName(cls, str));
    }

    private OverloadedDynamicMethod(LinkedList linkedList, ClassLoader classLoader, String str) {
        super(str);
        this.methods = linkedList;
        this.classLoader = classLoader;
    }

    @Override // jdk.internal.dynalink.beans.DynamicMethod
    SingleDynamicMethod getMethodForExactParamTypes(String str) {
        LinkedList linkedList = new LinkedList();
        Iterator it = this.methods.iterator();
        while (it.hasNext()) {
            SingleDynamicMethod methodForExactParamTypes = ((SingleDynamicMethod) it.next()).getMethodForExactParamTypes(str);
            if (methodForExactParamTypes != null) {
                linkedList.add(methodForExactParamTypes);
            }
        }
        switch (linkedList.size()) {
            case 0:
                return null;
            case 1:
                return (SingleDynamicMethod) linkedList.getFirst();
            default:
                throw new BootstrapMethodError("Can't choose among " + linkedList + " for argument types " + str + " for method " + getName());
        }
    }

    @Override // jdk.internal.dynalink.beans.DynamicMethod
    public MethodHandle getInvocation(CallSiteDescriptor callSiteDescriptor, LinkerServices linkerServices) {
        MethodType methodType = callSiteDescriptor.getMethodType();
        ApplicableOverloadedMethods applicables = getApplicables(methodType, ApplicableOverloadedMethods.APPLICABLE_BY_SUBTYPING);
        ApplicableOverloadedMethods applicables2 = getApplicables(methodType, ApplicableOverloadedMethods.APPLICABLE_BY_METHOD_INVOCATION_CONVERSION);
        ApplicableOverloadedMethods applicables3 = getApplicables(methodType, ApplicableOverloadedMethods.APPLICABLE_BY_VARIABLE_ARITY);
        List listFindMaximallySpecificMethods = applicables.findMaximallySpecificMethods();
        if (listFindMaximallySpecificMethods.isEmpty()) {
            listFindMaximallySpecificMethods = applicables2.findMaximallySpecificMethods();
            if (listFindMaximallySpecificMethods.isEmpty()) {
                listFindMaximallySpecificMethods = applicables3.findMaximallySpecificMethods();
            }
        }
        List list = (List) this.methods.clone();
        list.removeAll(applicables.getMethods());
        list.removeAll(applicables2.getMethods());
        list.removeAll(applicables3.getMethods());
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if (!isApplicableDynamically(linkerServices, methodType, (SingleDynamicMethod) it.next())) {
                it.remove();
            }
        }
        if (list.isEmpty() && listFindMaximallySpecificMethods.size() > 1) {
            throw new BootstrapMethodError("Can't choose among " + listFindMaximallySpecificMethods + " for argument types " + methodType);
        }
        list.addAll(listFindMaximallySpecificMethods);
        switch (list.size()) {
            case 0:
                return null;
            case 1:
                return ((SingleDynamicMethod) list.iterator().next()).getInvocation(callSiteDescriptor, linkerServices);
            default:
                ArrayList arrayList = new ArrayList(list.size());
                MethodHandles.Lookup lookup = callSiteDescriptor.getLookup();
                Iterator it2 = list.iterator();
                while (it2.hasNext()) {
                    arrayList.add(((SingleDynamicMethod) it2.next()).getTarget(lookup));
                }
                return new OverloadedMethod(arrayList, this, methodType, linkerServices).getInvoker();
        }
    }

    @Override // jdk.internal.dynalink.beans.DynamicMethod
    public boolean contains(SingleDynamicMethod singleDynamicMethod) {
        Iterator it = this.methods.iterator();
        while (it.hasNext()) {
            if (((SingleDynamicMethod) it.next()).contains(singleDynamicMethod)) {
                return true;
            }
        }
        return false;
    }

    public boolean isConstructor() {
        if ($assertionsDisabled || !this.methods.isEmpty()) {
            return ((SingleDynamicMethod) this.methods.getFirst()).isConstructor();
        }
        throw new AssertionError();
    }

    @Override // jdk.internal.dynalink.beans.DynamicMethod
    public String toString() {
        ArrayList arrayList = new ArrayList(this.methods.size());
        int length = 0;
        Iterator it = this.methods.iterator();
        while (it.hasNext()) {
            String name = ((SingleDynamicMethod) it.next()).getName();
            length += name.length();
            arrayList.add(name);
        }
        Collator collator = Collator.getInstance();
        collator.setStrength(1);
        Collections.sort(arrayList, collator);
        String name2 = getClass().getName();
        int length2 = name2.length() + length + (2 * arrayList.size()) + 3;
        StringBuilder sb = new StringBuilder(length2);
        sb.append('[').append(name2).append('\n');
        Iterator it2 = arrayList.iterator();
        while (it2.hasNext()) {
            sb.append(' ').append((String) it2.next()).append('\n');
        }
        sb.append(']');
        if ($assertionsDisabled || sb.length() == length2) {
            return sb.toString();
        }
        throw new AssertionError();
    }

    ClassLoader getClassLoader() {
        return this.classLoader;
    }

    private static boolean isApplicableDynamically(LinkerServices linkerServices, MethodType methodType, SingleDynamicMethod singleDynamicMethod) {
        MethodType methodType2 = singleDynamicMethod.getMethodType();
        boolean zIsVarArgs = singleDynamicMethod.isVarArgs();
        int iParameterCount = methodType2.parameterCount() - (zIsVarArgs ? 1 : 0);
        int iParameterCount2 = methodType.parameterCount();
        if (zIsVarArgs) {
            if (iParameterCount2 < iParameterCount) {
                return false;
            }
        } else if (iParameterCount2 != iParameterCount) {
            return false;
        }
        for (int i = 1; i < iParameterCount; i++) {
            if (!isApplicableDynamically(linkerServices, methodType.parameterType(i), methodType2.parameterType(i))) {
                return false;
            }
        }
        if (!zIsVarArgs) {
            return true;
        }
        Class<?> clsParameterType = methodType2.parameterType(iParameterCount);
        Class<?> componentType = clsParameterType.getComponentType();
        if (iParameterCount == iParameterCount2 - 1) {
            Class<?> clsParameterType2 = methodType.parameterType(iParameterCount);
            return isApplicableDynamically(linkerServices, clsParameterType2, clsParameterType) || isApplicableDynamically(linkerServices, clsParameterType2, componentType);
        }
        for (int i2 = iParameterCount; i2 < iParameterCount2; i2++) {
            if (!isApplicableDynamically(linkerServices, methodType.parameterType(i2), componentType)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isApplicableDynamically(LinkerServices linkerServices, Class cls, Class cls2) {
        return TypeUtilities.isPotentiallyConvertible(cls, cls2) || linkerServices.canConvert(cls, cls2);
    }

    private ApplicableOverloadedMethods getApplicables(MethodType methodType, ApplicableOverloadedMethods.ApplicabilityTest applicabilityTest) {
        return new ApplicableOverloadedMethods(this.methods, methodType, applicabilityTest);
    }

    public void addMethod(SingleDynamicMethod singleDynamicMethod) {
        if (!$assertionsDisabled && !constructorFlagConsistent(singleDynamicMethod)) {
            throw new AssertionError();
        }
        this.methods.add(singleDynamicMethod);
    }

    private boolean constructorFlagConsistent(SingleDynamicMethod singleDynamicMethod) {
        return this.methods.isEmpty() || ((SingleDynamicMethod) this.methods.getFirst()).isConstructor() == singleDynamicMethod.isConstructor();
    }
}
