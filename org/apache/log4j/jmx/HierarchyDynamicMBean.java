package org.apache.log4j.jmx;

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
import javax.management.Notification;
import javax.management.NotificationBroadcaster;
import javax.management.NotificationBroadcasterSupport;
import javax.management.NotificationFilter;
import javax.management.NotificationFilterSupport;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.ReflectionException;
import javax.management.RuntimeOperationsException;
import org.apache.log4j.Appender;
import org.apache.log4j.Category;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.HierarchyEventListener;
import org.apache.log4j.spi.LoggerRepository;

/* loaded from: L-out.jar:org/apache/log4j/jmx/HierarchyDynamicMBean.class */
public class HierarchyDynamicMBean extends AbstractDynamicMBean implements HierarchyEventListener, NotificationBroadcaster {
    static final String ADD_APPENDER = "addAppender.";
    static final String THRESHOLD = "threshold";
    private MBeanConstructorInfo[] dConstructors = new MBeanConstructorInfo[1];
    private MBeanOperationInfo[] dOperations = new MBeanOperationInfo[1];
    private Vector vAttributes = new Vector();
    private String dClassName = getClass().getName();
    private String dDescription = "This MBean acts as a management facade for org.apache.log4j.Hierarchy.";
    private NotificationBroadcasterSupport nbs = new NotificationBroadcasterSupport();
    private LoggerRepository hierarchy = LogManager.getLoggerRepository();
    private static Logger log;
    static Class class$org$apache$log4j$jmx$HierarchyDynamicMBean;

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static {
        Class clsClass$;
        if (class$org$apache$log4j$jmx$HierarchyDynamicMBean == null) {
            clsClass$ = class$("org.apache.log4j.jmx.HierarchyDynamicMBean");
            class$org$apache$log4j$jmx$HierarchyDynamicMBean = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$jmx$HierarchyDynamicMBean;
        }
        log = Logger.getLogger(clsClass$);
    }

    public HierarchyDynamicMBean() {
        buildDynamicMBeanInfo();
    }

    private void buildDynamicMBeanInfo() {
        this.dConstructors[0] = new MBeanConstructorInfo("HierarchyDynamicMBean(): Constructs a HierarchyDynamicMBean instance", getClass().getConstructors()[0]);
        this.vAttributes.add(new MBeanAttributeInfo(THRESHOLD, "java.lang.String", "The \"threshold\" state of the hiearchy.", true, true, false));
        this.dOperations[0] = new MBeanOperationInfo("addLoggerMBean", "addLoggerMBean(): add a loggerMBean", new MBeanParameterInfo[]{new MBeanParameterInfo("name", "java.lang.String", "Create a logger MBean")}, "javax.management.ObjectName", 1);
    }

    public ObjectName addLoggerMBean(String str) {
        Logger loggerExists = LogManager.exists(str);
        if (loggerExists != null) {
            return addLoggerMBean(loggerExists);
        }
        return null;
    }

    ObjectName addLoggerMBean(Logger logger) {
        String name = logger.getName();
        ObjectName objectName = null;
        try {
            LoggerDynamicMBean loggerDynamicMBean = new LoggerDynamicMBean(logger);
            objectName = new ObjectName("log4j", "logger", name);
            if (!this.server.isRegistered(objectName)) {
                registerMBean(loggerDynamicMBean, objectName);
                NotificationFilterSupport notificationFilterSupport = new NotificationFilterSupport();
                notificationFilterSupport.enableType(new StringBuffer().append(ADD_APPENDER).append(logger.getName()).toString());
                log.debug(new StringBuffer().append("---Adding logger [").append(name).append("] as listener.").toString());
                this.nbs.addNotificationListener(loggerDynamicMBean, notificationFilterSupport, (Object) null);
                this.vAttributes.add(new MBeanAttributeInfo(new StringBuffer().append("logger=").append(name).toString(), "javax.management.ObjectName", new StringBuffer().append("The ").append(name).append(" logger.").toString(), true, true, false));
            }
        } catch (RuntimeException e) {
            log.error(new StringBuffer().append("Could not add loggerMBean for [").append(name).append("].").toString(), e);
        } catch (JMException e2) {
            log.error(new StringBuffer().append("Could not add loggerMBean for [").append(name).append("].").toString(), e2);
        }
        return objectName;
    }

    public void addNotificationListener(NotificationListener notificationListener, NotificationFilter notificationFilter, Object obj) {
        this.nbs.addNotificationListener(notificationListener, notificationFilter, obj);
    }

    @Override // org.apache.log4j.jmx.AbstractDynamicMBean
    protected Logger getLogger() {
        return log;
    }

    public MBeanInfo getMBeanInfo() {
        MBeanAttributeInfo[] mBeanAttributeInfoArr = new MBeanAttributeInfo[this.vAttributes.size()];
        this.vAttributes.toArray(mBeanAttributeInfoArr);
        return new MBeanInfo(this.dClassName, this.dDescription, mBeanAttributeInfoArr, this.dConstructors, this.dOperations, new MBeanNotificationInfo[0]);
    }

    public MBeanNotificationInfo[] getNotificationInfo() {
        return this.nbs.getNotificationInfo();
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.management.ReflectionException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.management.RuntimeOperationsException */
    public Object invoke(String str, Object[] objArr, String[] strArr) throws ReflectionException, RuntimeOperationsException {
        if (str == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Operation name cannot be null"), new StringBuffer().append("Cannot invoke a null operation in ").append(this.dClassName).toString());
        }
        if (str.equals("addLoggerMBean")) {
            return addLoggerMBean((String) objArr[0]);
        }
        throw new ReflectionException(new NoSuchMethodException(str), new StringBuffer().append("Cannot find the operation ").append(str).append(" in ").append(this.dClassName).toString());
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.management.AttributeNotFoundException */
    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.management.RuntimeOperationsException */
    public Object getAttribute(String str) throws AttributeNotFoundException, RuntimeOperationsException {
        if (str == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), new StringBuffer().append("Cannot invoke a getter of ").append(this.dClassName).append(" with null attribute name").toString());
        }
        log.debug(new StringBuffer().append("Called getAttribute with [").append(str).append("].").toString());
        if (str.equals(THRESHOLD)) {
            return this.hierarchy.getThreshold();
        }
        if (str.startsWith("logger")) {
            int iIndexOf = str.indexOf("%3D");
            String string = str;
            if (iIndexOf > 0) {
                string = new StringBuffer().append(str.substring(0, iIndexOf)).append('=').append(str.substring(iIndexOf + 3)).toString();
            }
            try {
                return new ObjectName(new StringBuffer().append("log4j:").append(string).toString());
            } catch (JMException unused) {
                log.error(new StringBuffer().append("Could not create ObjectName").append(string).toString());
            } catch (RuntimeException unused2) {
                log.error(new StringBuffer().append("Could not create ObjectName").append(string).toString());
            }
        }
        throw new AttributeNotFoundException(new StringBuffer().append("Cannot find ").append(str).append(" attribute in ").append(this.dClassName).toString());
    }

    @Override // org.apache.log4j.spi.HierarchyEventListener
    public void addAppenderEvent(Category category, Appender appender) {
        log.debug(new StringBuffer().append("addAppenderEvent called: logger=").append(category.getName()).append(", appender=").append(appender.getName()).toString());
        Notification notification = new Notification(new StringBuffer().append(ADD_APPENDER).append(category.getName()).toString(), this, 0L);
        notification.setUserData(appender);
        log.debug("sending notification.");
        this.nbs.sendNotification(notification);
    }

    @Override // org.apache.log4j.spi.HierarchyEventListener
    public void removeAppenderEvent(Category category, Appender appender) {
        log.debug(new StringBuffer().append("removeAppenderCalled: logger=").append(category.getName()).append(", appender=").append(appender.getName()).toString());
    }

    public void postRegister(Boolean bool) {
        log.debug("postRegister is called.");
        this.hierarchy.addHierarchyEventListener(this);
        addLoggerMBean(this.hierarchy.getRootLogger());
    }

    public void removeNotificationListener(NotificationListener notificationListener) {
        this.nbs.removeNotificationListener(notificationListener);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.management.RuntimeOperationsException */
    public void setAttribute(Attribute attribute) throws Throwable {
        if (attribute == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute cannot be null"), new StringBuffer().append("Cannot invoke a setter of ").append(this.dClassName).append(" with null attribute").toString());
        }
        String name = attribute.getName();
        Object value = attribute.getValue();
        if (name == null) {
            throw new RuntimeOperationsException(new IllegalArgumentException("Attribute name cannot be null"), new StringBuffer().append("Cannot invoke the setter of ").append(this.dClassName).append(" with null attribute name").toString());
        }
        if (name.equals(THRESHOLD)) {
            this.hierarchy.setThreshold(OptionConverter.toLevel((String) value, this.hierarchy.getThreshold()));
        }
    }
}
