package org.apache.log4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import org.apache.log4j.helpers.Loader;
import org.apache.log4j.helpers.ThreadLocalMap;

/* loaded from: L-out.jar:org/apache/log4j/MDC.class */
public class MDC {
    static final MDC mdc = new MDC();
    static final int HT_SIZE = 7;
    boolean java1 = Loader.isJava1();
    Object tlm;
    private Method removeMethod;
    static Class class$java$lang$ThreadLocal;

    private MDC() throws Throwable {
        Class clsClass$;
        if (!this.java1) {
            this.tlm = new ThreadLocalMap();
        }
        try {
            if (class$java$lang$ThreadLocal == null) {
                clsClass$ = class$("java.lang.ThreadLocal");
                class$java$lang$ThreadLocal = clsClass$;
            } else {
                clsClass$ = class$java$lang$ThreadLocal;
            }
            this.removeMethod = clsClass$.getMethod("remove", null);
        } catch (NoSuchMethodException unused) {
        }
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public static void put(String str, Object obj) {
        if (mdc != null) {
            mdc.put0(str, obj);
        }
    }

    public static Object get(String str) {
        if (mdc != null) {
            return mdc.get0(str);
        }
        return null;
    }

    public static void remove(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (mdc != null) {
            mdc.remove0(str);
        }
    }

    public static Hashtable getContext() {
        if (mdc != null) {
            return mdc.getContext0();
        }
        return null;
    }

    public static void clear() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (mdc != null) {
            mdc.clear0();
        }
    }

    private void put0(String str, Object obj) {
        if (this.java1 || this.tlm == null) {
            return;
        }
        Hashtable hashtable = (Hashtable) ((ThreadLocalMap) this.tlm).get();
        if (hashtable == null) {
            hashtable = new Hashtable(7);
            ((ThreadLocalMap) this.tlm).set(hashtable);
        }
        hashtable.put(str, obj);
    }

    private Object get0(String str) {
        Hashtable hashtable;
        if (!this.java1 && this.tlm != null && (hashtable = (Hashtable) ((ThreadLocalMap) this.tlm).get()) != null && str != null) {
            return hashtable.get(str);
        }
        return null;
    }

    private void remove0(String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Hashtable hashtable;
        if (!this.java1 && this.tlm != null && (hashtable = (Hashtable) ((ThreadLocalMap) this.tlm).get()) != null) {
            hashtable.remove(str);
            if (hashtable.isEmpty()) {
                clear0();
            }
        }
    }

    private Hashtable getContext0() {
        if (this.java1 || this.tlm == null) {
            return null;
        }
        return (Hashtable) ((ThreadLocalMap) this.tlm).get();
    }

    private void clear0() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (!this.java1 && this.tlm != null) {
            Hashtable hashtable = (Hashtable) ((ThreadLocalMap) this.tlm).get();
            if (hashtable != null) {
                hashtable.clear();
            }
            if (this.removeMethod != null) {
                try {
                    this.removeMethod.invoke(this.tlm, null);
                } catch (IllegalAccessException unused) {
                } catch (InvocationTargetException unused2) {
                }
            }
        }
    }
}
