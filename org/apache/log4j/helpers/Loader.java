package org.apache.log4j.helpers;

import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

/* loaded from: L-out.jar:org/apache/log4j/helpers/Loader.class */
public class Loader {
    static final String TSTR = "Caught Exception while in Loader.getResource. This may be innocuous.";
    private static boolean java1;
    private static boolean ignoreTCL;
    static Class class$org$apache$log4j$helpers$Loader;
    static Class class$java$lang$Thread;

    static {
        int iIndexOf;
        java1 = true;
        ignoreTCL = false;
        String systemProperty = OptionConverter.getSystemProperty("java.version", null);
        if (systemProperty != null && (iIndexOf = systemProperty.indexOf(46)) != -1 && systemProperty.charAt(iIndexOf + 1) != '1') {
            java1 = false;
        }
        String systemProperty2 = OptionConverter.getSystemProperty("log4j.ignoreTCL", null);
        if (systemProperty2 != null) {
            ignoreTCL = OptionConverter.toBoolean(systemProperty2, true);
        }
    }

    public static URL getResource(String str, Class cls) {
        return getResource(str);
    }

    public static URL getResource(String str) {
        Class clsClass$;
        ClassLoader tcl;
        try {
            if (!java1 && !ignoreTCL && (tcl = getTCL()) != null) {
                LogLog.debug(new StringBuffer().append("Trying to find [").append(str).append("] using context classloader ").append(tcl).append(".").toString());
                URL resource = tcl.getResource(str);
                if (resource != null) {
                    return resource;
                }
            }
            if (class$org$apache$log4j$helpers$Loader == null) {
                clsClass$ = class$("org.apache.log4j.helpers.Loader");
                class$org$apache$log4j$helpers$Loader = clsClass$;
            } else {
                clsClass$ = class$org$apache$log4j$helpers$Loader;
            }
            ClassLoader classLoader = clsClass$.getClassLoader();
            if (classLoader != null) {
                LogLog.debug(new StringBuffer().append("Trying to find [").append(str).append("] using ").append(classLoader).append(" class loader.").toString());
                URL resource2 = classLoader.getResource(str);
                if (resource2 != null) {
                    return resource2;
                }
            }
        } catch (IllegalAccessException e) {
            LogLog.warn(TSTR, e);
        } catch (InvocationTargetException e2) {
            if ((e2.getTargetException() instanceof InterruptedException) || (e2.getTargetException() instanceof InterruptedIOException)) {
                Thread.currentThread().interrupt();
            }
            LogLog.warn(TSTR, e2);
        } catch (Throwable th) {
            LogLog.warn(TSTR, th);
        }
        LogLog.debug(new StringBuffer().append("Trying to find [").append(str).append("] using ClassLoader.getSystemResource().").toString());
        return ClassLoader.getSystemResource(str);
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public static boolean isJava1() {
        return java1;
    }

    private static ClassLoader getTCL() throws Throwable {
        Class clsClass$;
        try {
            if (class$java$lang$Thread == null) {
                clsClass$ = class$("java.lang.Thread");
                class$java$lang$Thread = clsClass$;
            } else {
                clsClass$ = class$java$lang$Thread;
            }
            return (ClassLoader) clsClass$.getMethod("getContextClassLoader", null).invoke(Thread.currentThread(), null);
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    public static Class loadClass(String str) {
        if (java1 || ignoreTCL) {
            return Class.forName(str);
        }
        try {
            return getTCL().loadClass(str);
        } catch (InvocationTargetException e) {
            if ((e.getTargetException() instanceof InterruptedException) || (e.getTargetException() instanceof InterruptedIOException)) {
                Thread.currentThread().interrupt();
            }
            return Class.forName(str);
        } catch (Throwable unused) {
            return Class.forName(str);
        }
    }
}
