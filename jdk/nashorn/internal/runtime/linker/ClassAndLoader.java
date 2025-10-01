package jdk.nashorn.internal.runtime.linker;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import jdk.nashorn.internal.runtime.ECMAErrors;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/linker/ClassAndLoader.class */
final class ClassAndLoader {
    private static final AccessControlContext GET_LOADER_ACC_CTXT;
    private final Class representativeClass;
    private ClassLoader loader;
    private boolean loaderRetrieved;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ClassAndLoader.class.desiredAssertionStatus();
        GET_LOADER_ACC_CTXT = createPermAccCtxt(new String[]{"getClassLoader"});
    }

    static AccessControlContext createPermAccCtxt(String[] strArr) {
        Permissions permissions = new Permissions();
        for (String str : strArr) {
            permissions.add(new RuntimePermission(str));
        }
        return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, permissions)});
    }

    ClassAndLoader(Class cls, boolean z) {
        this.representativeClass = cls;
        if (z) {
            retrieveLoader();
        }
    }

    Class getRepresentativeClass() {
        return this.representativeClass;
    }

    boolean canSee(ClassAndLoader classAndLoader) {
        try {
            Class<?> representativeClass = classAndLoader.getRepresentativeClass();
            return Class.forName(representativeClass.getName(), false, getLoader()) == representativeClass;
        } catch (ClassNotFoundException unused) {
            return false;
        }
    }

    ClassLoader getLoader() {
        if (!this.loaderRetrieved) {
            retrieveLoader();
        }
        return getRetrievedLoader();
    }

    ClassLoader getRetrievedLoader() {
        if ($assertionsDisabled || this.loaderRetrieved) {
            return this.loader;
        }
        throw new AssertionError();
    }

    private void retrieveLoader() {
        this.loader = this.representativeClass.getClassLoader();
        this.loaderRetrieved = true;
    }

    public boolean equals(Object obj) {
        return (obj instanceof ClassAndLoader) && ((ClassAndLoader) obj).getRetrievedLoader() == getRetrievedLoader();
    }

    public int hashCode() {
        return System.identityHashCode(getRetrievedLoader());
    }

    static ClassAndLoader getDefiningClassAndLoader(Class[] clsArr) {
        if (clsArr.length == 1) {
            return new ClassAndLoader(clsArr[0], false);
        }
        return (ClassAndLoader) AccessController.doPrivileged(new PrivilegedAction(clsArr) { // from class: jdk.nashorn.internal.runtime.linker.ClassAndLoader.1
            final Class[] val$types;

            {
                this.val$types = clsArr;
            }

            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            @Override // java.security.PrivilegedAction
            public ClassAndLoader run() {
                return ClassAndLoader.getDefiningClassAndLoaderPrivileged(this.val$types);
            }
        }, GET_LOADER_ACC_CTXT);
    }

    static ClassAndLoader getDefiningClassAndLoaderPrivileged(Class[] clsArr) {
        Collection maximumVisibilityLoaders = getMaximumVisibilityLoaders(clsArr);
        Iterator it = maximumVisibilityLoaders.iterator();
        if (maximumVisibilityLoaders.size() == 1) {
            return (ClassAndLoader) it.next();
        }
        if (!$assertionsDisabled && maximumVisibilityLoaders.size() <= 1) {
            throw new AssertionError();
        }
        StringBuilder sb = new StringBuilder();
        sb.append(((ClassAndLoader) it.next()).getRepresentativeClass().getCanonicalName());
        while (it.hasNext()) {
            sb.append(", ").append(((ClassAndLoader) it.next()).getRepresentativeClass().getCanonicalName());
        }
        throw ECMAErrors.typeError("extend.ambiguous.defining.class", new String[]{sb.toString()});
    }

    private static Collection getMaximumVisibilityLoaders(Class[] clsArr) {
        LinkedList linkedList = new LinkedList();
        for (ClassAndLoader classAndLoader : getClassLoadersForTypes(clsArr)) {
            Iterator it = linkedList.iterator();
            while (true) {
                if (it.hasNext()) {
                    ClassAndLoader classAndLoader2 = (ClassAndLoader) it.next();
                    boolean zCanSee = classAndLoader.canSee(classAndLoader2);
                    boolean zCanSee2 = classAndLoader2.canSee(classAndLoader);
                    if (zCanSee) {
                        if (!zCanSee2) {
                            it.remove();
                        }
                    } else if (zCanSee2) {
                        break;
                    }
                } else {
                    linkedList.add(classAndLoader);
                    break;
                }
            }
        }
        return linkedList;
    }

    private static Collection getClassLoadersForTypes(Class[] clsArr) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Class cls : clsArr) {
            ClassAndLoader classAndLoader = new ClassAndLoader(cls, true);
            if (!linkedHashMap.containsKey(classAndLoader)) {
                linkedHashMap.put(classAndLoader, classAndLoader);
            }
        }
        return linkedHashMap.keySet();
    }
}
