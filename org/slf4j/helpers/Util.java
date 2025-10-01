package org.slf4j.helpers;

/* loaded from: L-out.jar:org/slf4j/helpers/Util.class */
public final class Util {
    private static ClassContextSecurityManager SECURITY_MANAGER;
    private static boolean SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED = false;

    private Util() {
    }

    public static String safeGetSystemProperty(String str) {
        if (str == null) {
            throw new IllegalArgumentException("null input");
        }
        String property = null;
        try {
            property = System.getProperty(str);
        } catch (SecurityException unused) {
        }
        return property;
    }

    public static boolean safeGetBooleanSystemProperty(String str) {
        String strSafeGetSystemProperty = safeGetSystemProperty(str);
        if (strSafeGetSystemProperty == null) {
            return false;
        }
        return strSafeGetSystemProperty.equalsIgnoreCase("true");
    }

    /* loaded from: L-out.jar:org/slf4j/helpers/Util$ClassContextSecurityManager.class */
    private static final class ClassContextSecurityManager extends SecurityManager {
        private ClassContextSecurityManager() {
        }

        ClassContextSecurityManager(C05481 c05481) {
            this();
        }

        @Override // java.lang.SecurityManager
        protected Class[] getClassContext() {
            return super.getClassContext();
        }
    }

    private static ClassContextSecurityManager getSecurityManager() {
        if (SECURITY_MANAGER != null) {
            return SECURITY_MANAGER;
        }
        if (SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED) {
            return null;
        }
        SECURITY_MANAGER = safeCreateSecurityManager();
        SECURITY_MANAGER_CREATION_ALREADY_ATTEMPTED = true;
        return SECURITY_MANAGER;
    }

    private static ClassContextSecurityManager safeCreateSecurityManager() {
        try {
            return new ClassContextSecurityManager(null);
        } catch (SecurityException unused) {
            return null;
        }
    }

    public static Class getCallingClass() {
        ClassContextSecurityManager securityManager = getSecurityManager();
        if (securityManager == null) {
            return null;
        }
        Class[] classContext = securityManager.getClassContext();
        String name = Util.class.getName();
        int i = 0;
        while (i < classContext.length && !name.equals(classContext[i].getName())) {
            i++;
        }
        if (i >= classContext.length || i + 2 >= classContext.length) {
            throw new IllegalStateException("Failed to find org.slf4j.helpers.Util or its caller in the stack; this should not happen");
        }
        return classContext[i + 2];
    }

    public static final void report(String str, Throwable th) {
        System.err.println(str);
        System.err.println("Reported exception:");
        th.printStackTrace();
    }

    public static final void report(String str) {
        System.err.println("SLF4J: " + str);
    }
}
