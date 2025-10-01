package org.apache.log4j.config;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.OptionHandler;

/* loaded from: L-out.jar:org/apache/log4j/config/PropertySetter.class */
public class PropertySetter {
    protected Object obj;
    protected PropertyDescriptor[] props;
    static Class class$org$apache$log4j$spi$OptionHandler;
    static Class class$java$lang$String;
    static Class class$org$apache$log4j$Priority;
    static Class class$org$apache$log4j$spi$ErrorHandler;

    public PropertySetter(Object obj) {
        this.obj = obj;
    }

    protected void introspect() {
        try {
            this.props = Introspector.getBeanInfo(this.obj.getClass()).getPropertyDescriptors();
        } catch (IntrospectionException e) {
            LogLog.error(new StringBuffer().append("Failed to introspect ").append(this.obj).append(": ").append(e.getMessage()).toString());
            this.props = new PropertyDescriptor[0];
        }
    }

    public static void setProperties(Object obj, Properties properties, String str) {
        new PropertySetter(obj).setProperties(properties, str);
    }

    public void setProperties(Properties properties, String str) {
        Class clsClass$;
        int length = str.length();
        Enumeration<?> enumerationPropertyNames = properties.propertyNames();
        while (enumerationPropertyNames.hasMoreElements()) {
            String str2 = (String) enumerationPropertyNames.nextElement();
            if (str2.startsWith(str) && str2.indexOf(46, length + 1) <= 0) {
                String strFindAndSubst = OptionConverter.findAndSubst(str2, properties);
                String strSubstring = str2.substring(length);
                if ((!"layout".equals(strSubstring) && !"errorhandler".equals(strSubstring)) || !(this.obj instanceof Appender)) {
                    PropertyDescriptor propertyDescriptor = getPropertyDescriptor(Introspector.decapitalize(strSubstring));
                    if (propertyDescriptor != null) {
                        if (class$org$apache$log4j$spi$OptionHandler == null) {
                            clsClass$ = class$("org.apache.log4j.spi.OptionHandler");
                            class$org$apache$log4j$spi$OptionHandler = clsClass$;
                        } else {
                            clsClass$ = class$org$apache$log4j$spi$OptionHandler;
                        }
                        if (clsClass$.isAssignableFrom(propertyDescriptor.getPropertyType()) && propertyDescriptor.getWriteMethod() != null) {
                            OptionHandler optionHandler = (OptionHandler) OptionConverter.instantiateByKey(properties, new StringBuffer().append(str).append(strSubstring).toString(), propertyDescriptor.getPropertyType(), null);
                            new PropertySetter(optionHandler).setProperties(properties, new StringBuffer().append(str).append(strSubstring).append(".").toString());
                            try {
                                propertyDescriptor.getWriteMethod().invoke(this.obj, optionHandler);
                            } catch (IllegalAccessException e) {
                                LogLog.warn(new StringBuffer().append("Failed to set property [").append(strSubstring).append("] to value \"").append(strFindAndSubst).append("\". ").toString(), e);
                            } catch (RuntimeException e2) {
                                LogLog.warn(new StringBuffer().append("Failed to set property [").append(strSubstring).append("] to value \"").append(strFindAndSubst).append("\". ").toString(), e2);
                            } catch (InvocationTargetException e3) {
                                if ((e3.getTargetException() instanceof InterruptedException) || (e3.getTargetException() instanceof InterruptedIOException)) {
                                    Thread.currentThread().interrupt();
                                }
                                LogLog.warn(new StringBuffer().append("Failed to set property [").append(strSubstring).append("] to value \"").append(strFindAndSubst).append("\". ").toString(), e3);
                            }
                        }
                    }
                    setProperty(strSubstring, strFindAndSubst);
                }
            }
        }
        activate();
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public void setProperty(String str, String str2) {
        if (str2 == null) {
            return;
        }
        String strDecapitalize = Introspector.decapitalize(str);
        PropertyDescriptor propertyDescriptor = getPropertyDescriptor(strDecapitalize);
        if (propertyDescriptor == null) {
            LogLog.warn(new StringBuffer().append("No such property [").append(strDecapitalize).append("] in ").append(this.obj.getClass().getName()).append(".").toString());
            return;
        }
        try {
            setProperty(propertyDescriptor, strDecapitalize, str2);
        } catch (PropertySetterException e) {
            LogLog.warn(new StringBuffer().append("Failed to set property [").append(strDecapitalize).append("] to value \"").append(str2).append("\". ").toString(), e.rootCause);
        }
    }

    public void setProperty(PropertyDescriptor propertyDescriptor, String str, String str2) throws IllegalAccessException, PropertySetterException, IllegalArgumentException, InvocationTargetException {
        Method writeMethod = propertyDescriptor.getWriteMethod();
        if (writeMethod == null) {
            throw new PropertySetterException(new StringBuffer().append("No setter for property [").append(str).append("].").toString());
        }
        Class<?>[] parameterTypes = writeMethod.getParameterTypes();
        if (parameterTypes.length != 1) {
            throw new PropertySetterException("#params for setter != 1");
        }
        try {
            Object objConvertArg = convertArg(str2, parameterTypes[0]);
            if (objConvertArg == null) {
                throw new PropertySetterException(new StringBuffer().append("Conversion to type [").append(parameterTypes[0]).append("] failed.").toString());
            }
            LogLog.debug(new StringBuffer().append("Setting property [").append(str).append("] to [").append(objConvertArg).append("].").toString());
            try {
                writeMethod.invoke(this.obj, objConvertArg);
            } catch (IllegalAccessException e) {
                throw new PropertySetterException(e);
            } catch (RuntimeException e2) {
                throw new PropertySetterException(e2);
            } catch (InvocationTargetException e3) {
                if ((e3.getTargetException() instanceof InterruptedException) || (e3.getTargetException() instanceof InterruptedIOException)) {
                    Thread.currentThread().interrupt();
                }
                throw new PropertySetterException(e3);
            }
        } catch (Throwable th) {
            throw new PropertySetterException(new StringBuffer().append("Conversion to type [").append(parameterTypes[0]).append("] failed. Reason: ").append(th).toString());
        }
    }

    protected Object convertArg(String str, Class cls) throws Throwable {
        Class clsClass$;
        Class clsClass$2;
        Class clsClass$3;
        Class clsClass$4;
        if (str == null) {
            return null;
        }
        String strTrim = str.trim();
        if (class$java$lang$String == null) {
            clsClass$ = class$("java.lang.String");
            class$java$lang$String = clsClass$;
        } else {
            clsClass$ = class$java$lang$String;
        }
        if (clsClass$.isAssignableFrom(cls)) {
            return str;
        }
        if (Integer.TYPE.isAssignableFrom(cls)) {
            return new Integer(strTrim);
        }
        if (Long.TYPE.isAssignableFrom(cls)) {
            return new Long(strTrim);
        }
        if (Boolean.TYPE.isAssignableFrom(cls)) {
            if ("true".equalsIgnoreCase(strTrim)) {
                return Boolean.TRUE;
            }
            if ("false".equalsIgnoreCase(strTrim)) {
                return Boolean.FALSE;
            }
            return null;
        }
        if (class$org$apache$log4j$Priority == null) {
            clsClass$2 = class$("org.apache.log4j.Priority");
            class$org$apache$log4j$Priority = clsClass$2;
        } else {
            clsClass$2 = class$org$apache$log4j$Priority;
        }
        if (clsClass$2.isAssignableFrom(cls)) {
            return OptionConverter.toLevel(strTrim, Level.DEBUG);
        }
        if (class$org$apache$log4j$spi$ErrorHandler == null) {
            clsClass$3 = class$("org.apache.log4j.spi.ErrorHandler");
            class$org$apache$log4j$spi$ErrorHandler = clsClass$3;
        } else {
            clsClass$3 = class$org$apache$log4j$spi$ErrorHandler;
        }
        if (clsClass$3.isAssignableFrom(cls)) {
            if (class$org$apache$log4j$spi$ErrorHandler == null) {
                clsClass$4 = class$("org.apache.log4j.spi.ErrorHandler");
                class$org$apache$log4j$spi$ErrorHandler = clsClass$4;
            } else {
                clsClass$4 = class$org$apache$log4j$spi$ErrorHandler;
            }
            return OptionConverter.instantiateByClassName(strTrim, clsClass$4, null);
        }
        return null;
    }

    protected PropertyDescriptor getPropertyDescriptor(String str) {
        if (this.props == null) {
            introspect();
        }
        for (int i = 0; i < this.props.length; i++) {
            if (str.equals(this.props[i].getName())) {
                return this.props[i];
            }
        }
        return null;
    }

    public void activate() {
        if (this.obj instanceof OptionHandler) {
            ((OptionHandler) this.obj).activateOptions();
        }
    }
}
