package org.apache.log4j.jmx;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;
import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.RuntimeOperationsException;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.OptionHandler;

/* loaded from: L-out.jar:org/apache/log4j/jmx/LayoutDynamicMBean.class */
public class LayoutDynamicMBean extends AbstractDynamicMBean {
    private MBeanConstructorInfo[] dConstructors = new MBeanConstructorInfo[1];
    private Vector dAttributes = new Vector();
    private String dClassName = getClass().getName();
    private Hashtable dynamicProps = new Hashtable(5);
    private MBeanOperationInfo[] dOperations = new MBeanOperationInfo[1];
    private String dDescription = "This MBean acts as a management facade for log4j layouts.";
    private static Logger cat;
    private Layout layout;
    static Class class$org$apache$log4j$jmx$LayoutDynamicMBean;
    static Class class$org$apache$log4j$Level;
    static Class class$java$lang$String;
    static Class class$org$apache$log4j$Priority;

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static {
        Class clsClass$;
        if (class$org$apache$log4j$jmx$LayoutDynamicMBean == null) {
            clsClass$ = class$("org.apache.log4j.jmx.LayoutDynamicMBean");
            class$org$apache$log4j$jmx$LayoutDynamicMBean = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$jmx$LayoutDynamicMBean;
        }
        cat = Logger.getLogger(clsClass$);
    }

    public LayoutDynamicMBean(Layout layout) throws Throwable {
        this.layout = layout;
        buildDynamicMBeanInfo();
    }

    private void buildDynamicMBeanInfo() throws Throwable {
        Class<?> clsClass$;
        String name;
        this.dConstructors[0] = new MBeanConstructorInfo("LayoutDynamicMBean(): Constructs a LayoutDynamicMBean instance", getClass().getConstructors()[0]);
        PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(this.layout.getClass()).getPropertyDescriptors();
        int length = propertyDescriptors.length;
        for (int i = 0; i < length; i++) {
            String name2 = propertyDescriptors[i].getName();
            Method readMethod = propertyDescriptors[i].getReadMethod();
            Method writeMethod = propertyDescriptors[i].getWriteMethod();
            if (readMethod != null) {
                Class<?> returnType = readMethod.getReturnType();
                if (isSupportedType(returnType)) {
                    if (class$org$apache$log4j$Level == null) {
                        clsClass$ = class$("org.apache.log4j.Level");
                        class$org$apache$log4j$Level = clsClass$;
                    } else {
                        clsClass$ = class$org$apache$log4j$Level;
                    }
                    if (returnType.isAssignableFrom(clsClass$)) {
                        name = "java.lang.String";
                    } else {
                        name = returnType.getName();
                    }
                    this.dAttributes.add(new MBeanAttributeInfo(name2, name, "Dynamic", true, writeMethod != null, false));
                    this.dynamicProps.put(name2, new MethodUnion(readMethod, writeMethod));
                }
            }
        }
        this.dOperations[0] = new MBeanOperationInfo("activateOptions", "activateOptions(): add an layout", new MBeanParameterInfo[0], "void", 1);
    }

    private boolean isSupportedType(Class cls) throws Throwable {
        Class clsClass$;
        Class<?> clsClass$2;
        if (cls.isPrimitive()) {
            return true;
        }
        if (class$java$lang$String == null) {
            clsClass$ = class$("java.lang.String");
            class$java$lang$String = clsClass$;
        } else {
            clsClass$ = class$java$lang$String;
        }
        if (cls == clsClass$) {
            return true;
        }
        if (class$org$apache$log4j$Level == null) {
            clsClass$2 = class$("org.apache.log4j.Level");
            class$org$apache$log4j$Level = clsClass$2;
        } else {
            clsClass$2 = class$org$apache$log4j$Level;
        }
        if (cls.isAssignableFrom(clsClass$2)) {
            return true;
        }
        return false;
    }

    public MBeanInfo getMBeanInfo() {
        cat.debug("getMBeanInfo called.");
        MBeanAttributeInfo[] mBeanAttributeInfoArr = new MBeanAttributeInfo[this.dAttributes.size()];
        this.dAttributes.toArray(mBeanAttributeInfoArr);
        return new MBeanInfo(this.dClassName, this.dDescription, mBeanAttributeInfoArr, this.dConstructors, this.dOperations, new MBeanNotificationInfo[0]);
    }

    public Object invoke(String str, Object[] objArr, String[] strArr) {
        if (str.equals("activateOptions") && (this.layout instanceof OptionHandler)) {
            this.layout.activateOptions();
            return "Options activated.";
        }
        return null;
    }

    @Override // org.apache.log4j.jmx.AbstractDynamicMBean
    protected Logger getLogger() {
        return cat;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.management.AttributeNotFoundException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.management.RuntimeOperationsException */
    public Object getAttribute(String str) throws AttributeNotFoundException, RuntimeOperationsException {
        if (str == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), new StringBuffer().append("Cannot invoke a getter of ").append(this.dClassName).append(" with null attribute name").toString());
        }
        MethodUnion methodUnion = (MethodUnion) this.dynamicProps.get(str);
        cat.debug(new StringBuffer().append("----name=").append(str).append(", mu=").append(methodUnion).toString());
        if (methodUnion != null && methodUnion.readMethod != null) {
            try {
                return methodUnion.readMethod.invoke(this.layout, null);
            } catch (IllegalAccessException unused) {
                return null;
            } catch (RuntimeException unused2) {
                return null;
            } catch (InvocationTargetException e) {
                if ((e.getTargetException() instanceof InterruptedException) || (e.getTargetException() instanceof InterruptedIOException)) {
                    Thread.currentThread().interrupt();
                    return null;
                }
                return null;
            }
        }
        throw new AttributeNotFoundException(new StringBuffer().append("Cannot find ").append(str).append(" attribute in ").append(this.dClassName).toString());
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.management.AttributeNotFoundException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.management.RuntimeOperationsException */
    public void setAttribute(Attribute attribute) throws Throwable {
        Class<?> clsClass$;
        if (attribute == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute cannot be null"), new StringBuffer().append("Cannot invoke a setter of ").append(this.dClassName).append(" with null attribute").toString());
        }
        String name = attribute.getName();
        Object value = attribute.getValue();
        if (name == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), new StringBuffer().append("Cannot invoke the setter of ").append(this.dClassName).append(" with null attribute name").toString());
        }
        MethodUnion methodUnion = (MethodUnion) this.dynamicProps.get(name);
        if (methodUnion != null && methodUnion.writeMethod != null) {
            Object[] objArr = new Object[1];
            Class<?> cls = methodUnion.writeMethod.getParameterTypes()[0];
            if (class$org$apache$log4j$Priority == null) {
                clsClass$ = class$("org.apache.log4j.Priority");
                class$org$apache$log4j$Priority = clsClass$;
            } else {
                clsClass$ = class$org$apache$log4j$Priority;
            }
            if (cls == clsClass$) {
                value = OptionConverter.toLevel((String) value, (Level) getAttribute(name));
            }
            objArr[0] = value;
            try {
                methodUnion.writeMethod.invoke(this.layout, objArr);
                return;
            } catch (IllegalAccessException e) {
                cat.error("FIXME", e);
                return;
            } catch (RuntimeException e2) {
                cat.error("FIXME", e2);
                return;
            } catch (InvocationTargetException e3) {
                if ((e3.getTargetException() instanceof InterruptedException) || (e3.getTargetException() instanceof InterruptedIOException)) {
                    Thread.currentThread().interrupt();
                }
                cat.error("FIXME", e3);
                return;
            }
        }
        throw new AttributeNotFoundException(new StringBuffer().append("Attribute ").append(name).append(" not found in ").append(getClass().getName()).toString());
    }
}
