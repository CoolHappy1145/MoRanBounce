package jdk.internal.dynalink.beans;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Map;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/StaticClassIntrospector.class */
class StaticClassIntrospector extends FacetIntrospector {
    StaticClassIntrospector(Class cls) {
        super(cls, false);
    }

    @Override // jdk.internal.dynalink.beans.FacetIntrospector
    Map getInnerClassGetters() {
        HashMap map = new HashMap();
        for (Class cls : this.membersLookup.getInnerClasses()) {
            map.put(cls.getSimpleName(), editMethodHandle(MethodHandles.constant(StaticClass.class, StaticClass.forClass(cls))));
        }
        return map;
    }

    @Override // jdk.internal.dynalink.beans.FacetIntrospector
    MethodHandle editMethodHandle(MethodHandle methodHandle) {
        return editStaticMethodHandle(methodHandle);
    }

    static MethodHandle editStaticMethodHandle(MethodHandle methodHandle) {
        return dropReceiver(methodHandle, Object.class);
    }

    static MethodHandle editConstructorMethodHandle(MethodHandle methodHandle) {
        return dropReceiver(methodHandle, StaticClass.class);
    }

    private static MethodHandle dropReceiver(MethodHandle methodHandle, Class cls) {
        MethodHandle methodHandleDropArguments = MethodHandles.dropArguments(methodHandle, 0, (Class<?>[]) new Class[]{cls});
        if (methodHandle.isVarargsCollector() && !methodHandleDropArguments.isVarargsCollector()) {
            MethodType methodTypeType = methodHandle.type();
            methodHandleDropArguments = methodHandleDropArguments.asVarargsCollector(methodTypeType.parameterType(methodTypeType.parameterCount() - 1));
        }
        return methodHandleDropArguments;
    }
}
