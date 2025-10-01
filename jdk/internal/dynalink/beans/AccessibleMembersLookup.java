package jdk.internal.dynalink.beans;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/AccessibleMembersLookup.class */
class AccessibleMembersLookup {
    private final Map methods = new HashMap();
    private final Set innerClasses = new LinkedHashSet();
    private final boolean instance;

    AccessibleMembersLookup(Class cls, boolean z) throws SecurityException {
        this.instance = z;
        lookupAccessibleMembers(cls);
    }

    Method getAccessibleMethod(Method method) {
        if (method == null) {
            return null;
        }
        return (Method) this.methods.get(new MethodSignature(method));
    }

    Collection getMethods() {
        return this.methods.values();
    }

    Class[] getInnerClasses() {
        return (Class[]) this.innerClasses.toArray(new Class[this.innerClasses.size()]);
    }

    /* loaded from: L-out.jar:jdk/internal/dynalink/beans/AccessibleMembersLookup$MethodSignature.class */
    static final class MethodSignature {
        private final String name;
        private final Class[] args;

        MethodSignature(String str, Class[] clsArr) {
            this.name = str;
            this.args = clsArr;
        }

        MethodSignature(Method method) {
            this(method.getName(), method.getParameterTypes());
        }

        public boolean equals(Object obj) {
            if (obj instanceof MethodSignature) {
                MethodSignature methodSignature = (MethodSignature) obj;
                return methodSignature.name.equals(this.name) && Arrays.equals(this.args, methodSignature.args);
            }
            return false;
        }

        public int hashCode() {
            return this.name.hashCode() ^ Arrays.hashCode(this.args);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[MethodSignature ").append(this.name).append('(');
            if (this.args.length > 0) {
                sb.append(this.args[0].getCanonicalName());
                for (int i = 1; i < this.args.length; i++) {
                    sb.append(", ").append(this.args[i].getCanonicalName());
                }
            }
            return sb.append(")]").toString();
        }
    }

    private void lookupAccessibleMembers(Class cls) throws SecurityException {
        boolean z;
        if (!CheckRestrictedPackage.isRestrictedClass(cls)) {
            z = false;
            for (Method method : cls.getMethods()) {
                boolean zIsStatic = Modifier.isStatic(method.getModifiers());
                if (this.instance != zIsStatic) {
                    MethodSignature methodSignature = new MethodSignature(method);
                    if (!this.methods.containsKey(methodSignature)) {
                        Class<?> declaringClass = method.getDeclaringClass();
                        if (declaringClass != cls && CheckRestrictedPackage.isRestrictedClass(declaringClass)) {
                            z = true;
                        } else if (!zIsStatic || cls == declaringClass) {
                            this.methods.put(methodSignature, method);
                        }
                    }
                }
            }
            for (Class<?> cls2 : cls.getClasses()) {
                this.innerClasses.add(cls2);
            }
        } else {
            z = true;
        }
        if (this.instance && z) {
            for (Class<?> cls3 : cls.getInterfaces()) {
                lookupAccessibleMembers(cls3);
            }
            Class superclass = cls.getSuperclass();
            if (superclass != null) {
                lookupAccessibleMembers(superclass);
            }
        }
    }
}
