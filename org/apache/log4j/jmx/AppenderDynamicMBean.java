package org.apache.log4j.jmx;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Vector;
import javax.management.Attribute;
import javax.management.AttributeNotFoundException;
import javax.management.JMException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanConstructorInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanNotificationInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.RuntimeOperationsException;
import org.apache.log4j.Appender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.OptionHandler;

/* loaded from: L-out.jar:org/apache/log4j/jmx/AppenderDynamicMBean.class */
public class AppenderDynamicMBean extends AbstractDynamicMBean {
    private MBeanConstructorInfo[] dConstructors = new MBeanConstructorInfo[1];
    private Vector dAttributes = new Vector();
    private String dClassName = getClass().getName();
    private Hashtable dynamicProps = new Hashtable(5);
    private MBeanOperationInfo[] dOperations = new MBeanOperationInfo[2];
    private String dDescription = "This MBean acts as a management facade for log4j appenders.";
    private static Logger cat;
    private Appender appender;
    static Class class$org$apache$log4j$jmx$AppenderDynamicMBean;
    static Class class$org$apache$log4j$Priority;
    static Class class$java$lang$String;
    static Class class$org$apache$log4j$Layout;

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static {
        Class clsClass$;
        if (class$org$apache$log4j$jmx$AppenderDynamicMBean == null) {
            clsClass$ = class$("org.apache.log4j.jmx.AppenderDynamicMBean");
            class$org$apache$log4j$jmx$AppenderDynamicMBean = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$jmx$AppenderDynamicMBean;
        }
        cat = Logger.getLogger(clsClass$);
    }

    public AppenderDynamicMBean(Appender appender) throws Throwable {
        this.appender = appender;
        buildDynamicMBeanInfo();
    }

    private void buildDynamicMBeanInfo() throws Throwable {
        Class<?> clsClass$;
        String name;
        this.dConstructors[0] = new MBeanConstructorInfo("AppenderDynamicMBean(): Constructs a AppenderDynamicMBean instance", getClass().getConstructors()[0]);
        PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(this.appender.getClass()).getPropertyDescriptors();
        int length = propertyDescriptors.length;
        for (int i = 0; i < length; i++) {
            String name2 = propertyDescriptors[i].getName();
            Method readMethod = propertyDescriptors[i].getReadMethod();
            Method writeMethod = propertyDescriptors[i].getWriteMethod();
            if (readMethod != null) {
                Class<?> returnType = readMethod.getReturnType();
                if (isSupportedType(returnType)) {
                    if (class$org$apache$log4j$Priority == null) {
                        clsClass$ = class$("org.apache.log4j.Priority");
                        class$org$apache$log4j$Priority = clsClass$;
                    } else {
                        clsClass$ = class$org$apache$log4j$Priority;
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
        this.dOperations[0] = new MBeanOperationInfo("activateOptions", "activateOptions(): add an appender", new MBeanParameterInfo[0], "void", 1);
        this.dOperations[1] = new MBeanOperationInfo("setLayout", "setLayout(): add a layout", new MBeanParameterInfo[]{new MBeanParameterInfo("layout class", "java.lang.String", "layout class")}, "void", 1);
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
        if (class$org$apache$log4j$Priority == null) {
            clsClass$2 = class$("org.apache.log4j.Priority");
            class$org$apache$log4j$Priority = clsClass$2;
        } else {
            clsClass$2 = class$org$apache$log4j$Priority;
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

    public Object invoke(String str, Object[] objArr, String[] strArr) throws Throwable {
        Class clsClass$;
        if (str.equals("activateOptions") && (this.appender instanceof OptionHandler)) {
            ((OptionHandler) this.appender).activateOptions();
            return "Options activated.";
        }
        if (str.equals("setLayout")) {
            String str2 = (String) objArr[0];
            if (class$org$apache$log4j$Layout == null) {
                clsClass$ = class$("org.apache.log4j.Layout");
                class$org$apache$log4j$Layout = clsClass$;
            } else {
                clsClass$ = class$org$apache$log4j$Layout;
            }
            Layout layout = (Layout) OptionConverter.instantiateByClassName(str2, clsClass$, null);
            this.appender.setLayout(layout);
            registerLayoutMBean(layout);
            return null;
        }
        return null;
    }

    void registerLayoutMBean(Layout layout) {
        if (layout == null) {
            return;
        }
        String string = new StringBuffer().append(getAppenderName(this.appender)).append(",layout=").append(layout.getClass().getName()).toString();
        cat.debug(new StringBuffer().append("Adding LayoutMBean:").append(string).toString());
        try {
            LayoutDynamicMBean layoutDynamicMBean = new LayoutDynamicMBean(layout);
            ObjectName objectName = new ObjectName(new StringBuffer().append("log4j:appender=").append(string).toString());
            if (!this.server.isRegistered(objectName)) {
                registerMBean(layoutDynamicMBean, objectName);
                this.dAttributes.add(new MBeanAttributeInfo(new StringBuffer().append("appender=").append(string).toString(), "javax.management.ObjectName", new StringBuffer().append("The ").append(string).append(" layout.").toString(), true, true, false));
            }
        } catch (IntrospectionException e) {
            cat.error(new StringBuffer().append("Could not add DynamicLayoutMBean for [").append(string).append("].").toString(), e);
        } catch (JMException e2) {
            cat.error(new StringBuffer().append("Could not add DynamicLayoutMBean for [").append(string).append("].").toString(), e2);
        } catch (RuntimeException e3) {
            cat.error(new StringBuffer().append("Could not add DynamicLayoutMBean for [").append(string).append("].").toString(), e3);
        }
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
        cat.debug(new StringBuffer().append("getAttribute called with [").append(str).append("].").toString());
        if (str.startsWith(new StringBuffer().append("appender=").append(this.appender.getName()).append(",layout").toString())) {
            try {
                return new ObjectName(new StringBuffer().append("log4j:").append(str).toString());
            } catch (RuntimeException e) {
                cat.error("attributeName", e);
            } catch (MalformedObjectNameException e2) {
                cat.error("attributeName", e2);
            }
        }
        MethodUnion methodUnion = (MethodUnion) this.dynamicProps.get(str);
        if (methodUnion != null && methodUnion.readMethod != null) {
            try {
                return methodUnion.readMethod.invoke(this.appender, null);
            } catch (IllegalAccessException unused) {
                return null;
            } catch (RuntimeException unused2) {
                return null;
            } catch (InvocationTargetException e3) {
                if ((e3.getTargetException() instanceof InterruptedException) || (e3.getTargetException() instanceof InterruptedIOException)) {
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
        if (methodUnion == null || methodUnion.writeMethod == null) {
            if (!name.endsWith(".layout")) {
                throw new AttributeNotFoundException(new StringBuffer().append("Attribute ").append(name).append(" not found in ").append(getClass().getName()).toString());
            }
            return;
        }
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
            methodUnion.writeMethod.invoke(this.appender, objArr);
        } catch (IllegalAccessException e) {
            cat.error("FIXME", e);
        } catch (RuntimeException e2) {
            cat.error("FIXME", e2);
        } catch (InvocationTargetException e3) {
            if ((e3.getTargetException() instanceof InterruptedException) || (e3.getTargetException() instanceof InterruptedIOException)) {
                Thread.currentThread().interrupt();
            }
            cat.error("FIXME", e3);
        }
    }

    @Override // org.apache.log4j.jmx.AbstractDynamicMBean
    public ObjectName preRegister(MBeanServer mBeanServer, ObjectName objectName) {
        cat.debug(new StringBuffer().append("preRegister called. Server=").append(mBeanServer).append(", name=").append(objectName).toString());
        this.server = mBeanServer;
        registerLayoutMBean(this.appender.getLayout());
        return objectName;
    }
}
