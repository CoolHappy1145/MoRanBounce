package org.apache.log4j.jmx;

import java.beans.IntrospectionException;
import java.util.Enumeration;
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
import javax.management.MalformedObjectNameException;
import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.RuntimeOperationsException;
import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.DateLayout;
import org.apache.log4j.helpers.OptionConverter;

/* loaded from: L-out.jar:org/apache/log4j/jmx/LoggerDynamicMBean.class */
public class LoggerDynamicMBean extends AbstractDynamicMBean implements NotificationListener {
    private MBeanConstructorInfo[] dConstructors = new MBeanConstructorInfo[1];
    private MBeanOperationInfo[] dOperations = new MBeanOperationInfo[1];
    private Vector dAttributes = new Vector();
    private String dClassName = getClass().getName();
    private String dDescription = "This MBean acts as a management facade for a org.apache.log4j.Logger instance.";
    private static Logger cat;
    private Logger logger;
    static Class class$org$apache$log4j$jmx$LoggerDynamicMBean;
    static Class class$org$apache$log4j$Appender;

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static {
        Class clsClass$;
        if (class$org$apache$log4j$jmx$LoggerDynamicMBean == null) {
            clsClass$ = class$("org.apache.log4j.jmx.LoggerDynamicMBean");
            class$org$apache$log4j$jmx$LoggerDynamicMBean = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$jmx$LoggerDynamicMBean;
        }
        cat = Logger.getLogger(clsClass$);
    }

    public LoggerDynamicMBean(Logger logger) {
        this.logger = logger;
        buildDynamicMBeanInfo();
    }

    public void handleNotification(Notification notification, Object obj) {
        cat.debug(new StringBuffer().append("Received notification: ").append(notification.getType()).toString());
        registerAppenderMBean((Appender) notification.getUserData());
    }

    private void buildDynamicMBeanInfo() {
        this.dConstructors[0] = new MBeanConstructorInfo("HierarchyDynamicMBean(): Constructs a HierarchyDynamicMBean instance", getClass().getConstructors()[0]);
        this.dAttributes.add(new MBeanAttributeInfo("name", "java.lang.String", "The name of this Logger.", true, false, false));
        this.dAttributes.add(new MBeanAttributeInfo("priority", "java.lang.String", "The priority of this logger.", true, true, false));
        this.dOperations[0] = new MBeanOperationInfo("addAppender", "addAppender(): add an appender", new MBeanParameterInfo[]{new MBeanParameterInfo("class name", "java.lang.String", "add an appender to this logger"), new MBeanParameterInfo("appender name", "java.lang.String", "name of the appender")}, "void", 1);
    }

    @Override // org.apache.log4j.jmx.AbstractDynamicMBean
    protected Logger getLogger() {
        return this.logger;
    }

    public MBeanInfo getMBeanInfo() {
        MBeanAttributeInfo[] mBeanAttributeInfoArr = new MBeanAttributeInfo[this.dAttributes.size()];
        this.dAttributes.toArray(mBeanAttributeInfoArr);
        return new MBeanInfo(this.dClassName, this.dDescription, mBeanAttributeInfoArr, this.dConstructors, this.dOperations, new MBeanNotificationInfo[0]);
    }

    public Object invoke(String str, Object[] objArr, String[] strArr) throws Throwable {
        if (str.equals("addAppender")) {
            addAppender((String) objArr[0], (String) objArr[1]);
            return "Hello world.";
        }
        return null;
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.management.AttributeNotFoundException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.management.RuntimeOperationsException */
    public Object getAttribute(String str) throws AttributeNotFoundException, RuntimeOperationsException {
        if (str == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), new StringBuffer().append("Cannot invoke a getter of ").append(this.dClassName).append(" with null attribute name").toString());
        }
        if (str.equals("name")) {
            return this.logger.getName();
        }
        if (str.equals("priority")) {
            Level level = this.logger.getLevel();
            if (level == null) {
                return null;
            }
            return level.toString();
        }
        if (str.startsWith("appender=")) {
            try {
                return new ObjectName(new StringBuffer().append("log4j:").append(str).toString());
            } catch (MalformedObjectNameException unused) {
                cat.error(new StringBuffer().append("Could not create ObjectName").append(str).toString());
            } catch (RuntimeException unused2) {
                cat.error(new StringBuffer().append("Could not create ObjectName").append(str).toString());
            }
        }
        throw new AttributeNotFoundException(new StringBuffer().append("Cannot find ").append(str).append(" attribute in ").append(this.dClassName).toString());
    }

    void addAppender(String str, String str2) throws Throwable {
        Class clsClass$;
        cat.debug(new StringBuffer().append("addAppender called with ").append(str).append(", ").append(str2).toString());
        if (class$org$apache$log4j$Appender == null) {
            clsClass$ = class$("org.apache.log4j.Appender");
            class$org$apache$log4j$Appender = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$Appender;
        }
        Appender appender = (Appender) OptionConverter.instantiateByClassName(str, clsClass$, null);
        appender.setName(str2);
        this.logger.addAppender(appender);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.management.AttributeNotFoundException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.management.RuntimeOperationsException */
    public void setAttribute(Attribute attribute) throws Throwable {
        Level level;
        if (attribute == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute cannot be null"), new StringBuffer().append("Cannot invoke a setter of ").append(this.dClassName).append(" with null attribute").toString());
        }
        String name = attribute.getName();
        Object value = attribute.getValue();
        if (name == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), new StringBuffer().append("Cannot invoke the setter of ").append(this.dClassName).append(" with null attribute name").toString());
        }
        if (name.equals("priority")) {
            if (value instanceof String) {
                String str = (String) value;
                Level level2 = this.logger.getLevel();
                if (str.equalsIgnoreCase(DateLayout.NULL_DATE_FORMAT)) {
                    level = null;
                } else {
                    level = OptionConverter.toLevel(str, level2);
                }
                this.logger.setLevel(level);
                return;
            }
            return;
        }
        throw new AttributeNotFoundException(new StringBuffer().append("Attribute ").append(name).append(" not found in ").append(getClass().getName()).toString());
    }

    void appenderMBeanRegistration() {
        Enumeration allAppenders = this.logger.getAllAppenders();
        while (allAppenders.hasMoreElements()) {
            registerAppenderMBean((Appender) allAppenders.nextElement());
        }
    }

    void registerAppenderMBean(Appender appender) {
        String appenderName = getAppenderName(appender);
        cat.debug(new StringBuffer().append("Adding AppenderMBean for appender named ").append(appenderName).toString());
        try {
            AppenderDynamicMBean appenderDynamicMBean = new AppenderDynamicMBean(appender);
            ObjectName objectName = new ObjectName("log4j", "appender", appenderName);
            if (!this.server.isRegistered(objectName)) {
                registerMBean(appenderDynamicMBean, objectName);
                this.dAttributes.add(new MBeanAttributeInfo(new StringBuffer().append("appender=").append(appenderName).toString(), "javax.management.ObjectName", new StringBuffer().append("The ").append(appenderName).append(" appender.").toString(), true, true, false));
            }
        } catch (RuntimeException e) {
            cat.error(new StringBuffer().append("Could not add appenderMBean for [").append(appenderName).append("].").toString(), e);
        } catch (IntrospectionException e2) {
            cat.error(new StringBuffer().append("Could not add appenderMBean for [").append(appenderName).append("].").toString(), e2);
        } catch (JMException e3) {
            cat.error(new StringBuffer().append("Could not add appenderMBean for [").append(appenderName).append("].").toString(), e3);
        }
    }

    public void postRegister(Boolean bool) {
        appenderMBeanRegistration();
    }
}
