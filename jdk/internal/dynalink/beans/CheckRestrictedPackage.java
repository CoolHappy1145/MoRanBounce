package jdk.internal.dynalink.beans;

import java.lang.reflect.Modifier;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;

/* loaded from: L-out.jar:jdk/internal/dynalink/beans/CheckRestrictedPackage.class */
class CheckRestrictedPackage {
    private static final AccessControlContext NO_PERMISSIONS_CONTEXT = createNoPermissionsContext();

    CheckRestrictedPackage() {
    }

    static boolean isRestrictedClass(Class cls) {
        String name;
        int iLastIndexOf;
        if (!Modifier.isPublic(cls.getModifiers())) {
            return true;
        }
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager == null || (iLastIndexOf = (name = cls.getName()).lastIndexOf(46)) == -1) {
            return false;
        }
        try {
            AccessController.doPrivileged(new PrivilegedAction(securityManager, name, iLastIndexOf) { // from class: jdk.internal.dynalink.beans.CheckRestrictedPackage.1
                final SecurityManager val$sm;
                final String val$name;
                final int val$i;

                {
                    this.val$sm = securityManager;
                    this.val$name = name;
                    this.val$i = iLastIndexOf;
                }

                @Override // java.security.PrivilegedAction
                public Object run() {
                    return run();
                }

                @Override // java.security.PrivilegedAction
                public Void run() {
                    this.val$sm.checkPackageAccess(this.val$name.substring(0, this.val$i));
                    return null;
                }
            }, NO_PERMISSIONS_CONTEXT);
            return false;
        } catch (SecurityException unused) {
            return true;
        }
    }

    private static AccessControlContext createNoPermissionsContext() {
        return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, new Permissions())});
    }
}
