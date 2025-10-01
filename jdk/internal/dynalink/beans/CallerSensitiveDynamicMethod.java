package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import jdk.internal.dynalink.support.Lookup;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/CallerSensitiveDynamicMethod.class */
class CallerSensitiveDynamicMethod extends SingleDynamicMethod {
    private final AccessibleObject target;
    private final MethodType type;

    CallerSensitiveDynamicMethod(AccessibleObject accessibleObject) {
        super(getName(accessibleObject));
        this.target = accessibleObject;
        this.type = getMethodType(accessibleObject);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static String getName(AccessibleObject accessibleObject) {
        Member member = (Member) accessibleObject;
        boolean z = member instanceof Constructor;
        return getMethodNameWithSignature(getMethodType(accessibleObject), z ? member.getName() : getClassAndMethodName(member.getDeclaringClass(), member.getName()), !z);
    }

    @Override // jdk.internal.dynalink.beans.SingleDynamicMethod
    MethodType getMethodType() {
        return this.type;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static MethodType getMethodType(AccessibleObject accessibleObject) {
        Class declaringClass;
        boolean z = accessibleObject instanceof Method;
        MethodType methodType = MethodType.methodType(z ? ((Method) accessibleObject).getReturnType() : ((Constructor) accessibleObject).getDeclaringClass(), z ? ((Method) accessibleObject).getParameterTypes() : ((Constructor) accessibleObject).getParameterTypes());
        Member member = (Member) accessibleObject;
        Class<?>[] clsArr = new Class[1];
        if (z) {
            declaringClass = Modifier.isStatic(member.getModifiers()) ? Object.class : member.getDeclaringClass();
        } else {
            declaringClass = StaticClass.class;
        }
        clsArr[0] = declaringClass;
        return methodType.insertParameterTypes(0, clsArr);
    }

    @Override // jdk.internal.dynalink.beans.SingleDynamicMethod
    boolean isVarArgs() {
        return this.target instanceof Method ? ((Method) this.target).isVarArgs() : ((Constructor) this.target).isVarArgs();
    }

    @Override // jdk.internal.dynalink.beans.SingleDynamicMethod
    MethodHandle getTarget(MethodHandles.Lookup lookup) {
        if (this.target instanceof Method) {
            MethodHandle methodHandleUnreflect = Lookup.unreflect(lookup, (Method) this.target);
            if (Modifier.isStatic(((Member) this.target).getModifiers())) {
                return StaticClassIntrospector.editStaticMethodHandle(methodHandleUnreflect);
            }
            return methodHandleUnreflect;
        }
        return StaticClassIntrospector.editConstructorMethodHandle(Lookup.unreflectConstructor(lookup, (Constructor) this.target));
    }

    boolean isConstructor() {
        return this.target instanceof Constructor;
    }
}
