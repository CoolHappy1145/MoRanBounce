package org.apache.log4j.config;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.log4j.helpers.LogLog;

/* loaded from: L-out.jar:org/apache/log4j/config/PropertyGetter.class */
public class PropertyGetter {
    protected static final Object[] NULL_ARG = new Object[0];
    protected Object obj;
    protected PropertyDescriptor[] props;
    static Class class$java$lang$String;
    static Class class$org$apache$log4j$Priority;

    /* loaded from: L-out.jar:org/apache/log4j/config/PropertyGetter$PropertyCallback.class */
    public interface PropertyCallback {
        void foundProperty(Object obj, String str, String str2, Object obj2);
    }

    public PropertyGetter(Object obj) {
        this.props = Introspector.getBeanInfo(obj.getClass()).getPropertyDescriptors();
        this.obj = obj;
    }

    public static void getProperties(Object obj, PropertyCallback propertyCallback, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            new PropertyGetter(obj).getProperties(propertyCallback, str);
        } catch (IntrospectionException e) {
            LogLog.error(new StringBuffer().append("Failed to introspect object ").append(obj).toString(), e);
        }
    }

    public void getProperties(PropertyCallback propertyCallback, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        for (int i = 0; i < this.props.length; i++) {
            Method readMethod = this.props[i].getReadMethod();
            if (readMethod != null && isHandledType(readMethod.getReturnType())) {
                String name = this.props[i].getName();
                try {
                    Object objInvoke = readMethod.invoke(this.obj, NULL_ARG);
                    if (objInvoke != null) {
                        propertyCallback.foundProperty(this.obj, str, name, objInvoke);
                    }
                } catch (IllegalAccessException unused) {
                    LogLog.warn(new StringBuffer().append("Failed to get value of property ").append(name).toString());
                } catch (RuntimeException unused2) {
                    LogLog.warn(new StringBuffer().append("Failed to get value of property ").append(name).toString());
                } catch (InvocationTargetException e) {
                    if ((e.getTargetException() instanceof InterruptedException) || (e.getTargetException() instanceof InterruptedIOException)) {
                        Thread.currentThread().interrupt();
                    }
                    LogLog.warn(new StringBuffer().append("Failed to get value of property ").append(name).toString());
                }
            }
        }
    }

    protected boolean isHandledType(Class cls) throws Throwable {
        Class clsClass$;
        Class clsClass$2;
        if (class$java$lang$String == null) {
            clsClass$ = class$("java.lang.String");
            class$java$lang$String = clsClass$;
        } else {
            clsClass$ = class$java$lang$String;
        }
        if (!clsClass$.isAssignableFrom(cls) && !Integer.TYPE.isAssignableFrom(cls) && !Long.TYPE.isAssignableFrom(cls) && !Boolean.TYPE.isAssignableFrom(cls)) {
            if (class$org$apache$log4j$Priority == null) {
                clsClass$2 = class$("org.apache.log4j.Priority");
                class$org$apache$log4j$Priority = clsClass$2;
            } else {
                clsClass$2 = class$org$apache$log4j$Priority;
            }
            if (!clsClass$2.isAssignableFrom(cls)) {
                return false;
            }
        }
        return true;
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }
}
