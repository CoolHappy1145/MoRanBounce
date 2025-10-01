package org.apache.log4j;

import java.text.MessageFormat;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Vector;
import org.apache.log4j.helpers.AppenderAttachableImpl;
import org.apache.log4j.helpers.NullEnumeration;
import org.apache.log4j.spi.AppenderAttachable;
import org.apache.log4j.spi.HierarchyEventListener;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/Category.class */
public class Category implements AppenderAttachable {
    protected String name;
    protected Level level;
    protected Category parent;
    private static final String FQCN;
    protected ResourceBundle resourceBundle;
    protected LoggerRepository repository;
    AppenderAttachableImpl aai;
    protected boolean additive = true;
    static Class class$org$apache$log4j$Category;

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static {
        Class clsClass$;
        if (class$org$apache$log4j$Category == null) {
            clsClass$ = class$("org.apache.log4j.Category");
            class$org$apache$log4j$Category = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$Category;
        }
        FQCN = clsClass$.getName();
    }

    protected Category(String str) {
        this.name = str;
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public void addAppender(Appender appender) {
        if (this.aai == null) {
            this.aai = new AppenderAttachableImpl();
        }
        this.aai.addAppender(appender);
        this.repository.fireAddAppenderEvent(this, appender);
    }

    public void assertLog(boolean z, String str) {
        if (!z) {
            error(str);
        }
    }

    public void callAppenders(LoggingEvent loggingEvent) {
        int iAppendLoopOnAppenders = 0;
        Category category = this;
        while (true) {
            Category category2 = category;
            if (category2 == null) {
                break;
            }
            synchronized (category2) {
                if (category2.aai != null) {
                    iAppendLoopOnAppenders += category2.aai.appendLoopOnAppenders(loggingEvent);
                }
                if (!category2.additive) {
                    break;
                }
            }
            break;
            category = category2.parent;
        }
        if (iAppendLoopOnAppenders == 0) {
            this.repository.emitNoAppenderWarning(this);
        }
    }

    void closeNestedAppenders() {
        Enumeration allAppenders = getAllAppenders();
        if (allAppenders != null) {
            while (allAppenders.hasMoreElements()) {
                Appender appender = (Appender) allAppenders.nextElement();
                if (appender instanceof AppenderAttachable) {
                    appender.close();
                }
            }
        }
    }

    public void debug(Object obj) {
        if (!this.repository.isDisabled(10000) && Level.DEBUG.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.DEBUG, obj, null);
        }
    }

    public void debug(Object obj, Throwable th) {
        if (!this.repository.isDisabled(10000) && Level.DEBUG.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.DEBUG, obj, th);
        }
    }

    public void error(Object obj) {
        if (!this.repository.isDisabled(Priority.ERROR_INT) && Level.ERROR.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.ERROR, obj, null);
        }
    }

    public void error(Object obj, Throwable th) {
        if (!this.repository.isDisabled(Priority.ERROR_INT) && Level.ERROR.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.ERROR, obj, th);
        }
    }

    public static Logger exists(String str) {
        return LogManager.exists(str);
    }

    public void fatal(Object obj) {
        if (!this.repository.isDisabled(Priority.FATAL_INT) && Level.FATAL.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.FATAL, obj, null);
        }
    }

    public void fatal(Object obj, Throwable th) {
        if (!this.repository.isDisabled(Priority.FATAL_INT) && Level.FATAL.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.FATAL, obj, th);
        }
    }

    protected void forcedLog(String str, Priority priority, Object obj, Throwable th) {
        callAppenders(new LoggingEvent(str, this, priority, obj, th));
    }

    public boolean getAdditivity() {
        return this.additive;
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public Enumeration getAllAppenders() {
        if (this.aai == null) {
            return NullEnumeration.getInstance();
        }
        return this.aai.getAllAppenders();
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public Appender getAppender(String str) {
        if (this.aai == null || str == null) {
            return null;
        }
        return this.aai.getAppender(str);
    }

    public Level getEffectiveLevel() {
        Category category = this;
        while (true) {
            Category category2 = category;
            if (category2 != null) {
                if (category2.level == null) {
                    category = category2.parent;
                } else {
                    return category2.level;
                }
            } else {
                return null;
            }
        }
    }

    public Priority getChainedPriority() {
        Category category = this;
        while (true) {
            Category category2 = category;
            if (category2 != null) {
                if (category2.level == null) {
                    category = category2.parent;
                } else {
                    return category2.level;
                }
            } else {
                return null;
            }
        }
    }

    public static Enumeration getCurrentCategories() {
        return LogManager.getCurrentLoggers();
    }

    public static LoggerRepository getDefaultHierarchy() {
        return LogManager.getLoggerRepository();
    }

    public LoggerRepository getHierarchy() {
        return this.repository;
    }

    public LoggerRepository getLoggerRepository() {
        return this.repository;
    }

    public static Category getInstance(String str) {
        return LogManager.getLogger(str);
    }

    public static Category getInstance(Class cls) {
        return LogManager.getLogger(cls);
    }

    public final String getName() {
        return this.name;
    }

    public final Category getParent() {
        return this.parent;
    }

    public final Level getLevel() {
        return this.level;
    }

    public final Level getPriority() {
        return this.level;
    }

    public static final Category getRoot() {
        return LogManager.getRootLogger();
    }

    public ResourceBundle getResourceBundle() {
        Category category = this;
        while (true) {
            Category category2 = category;
            if (category2 != null) {
                if (category2.resourceBundle == null) {
                    category = category2.parent;
                } else {
                    return category2.resourceBundle;
                }
            } else {
                return null;
            }
        }
    }

    protected String getResourceBundleString(String str) {
        ResourceBundle resourceBundle = getResourceBundle();
        if (resourceBundle == null) {
            return null;
        }
        try {
            return resourceBundle.getString(str);
        } catch (MissingResourceException unused) {
            error(new StringBuffer().append("No resource is associated with key \"").append(str).append("\".").toString());
            return null;
        }
    }

    public void info(Object obj) {
        if (!this.repository.isDisabled(Priority.INFO_INT) && Level.INFO.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.INFO, obj, null);
        }
    }

    public void info(Object obj, Throwable th) {
        if (!this.repository.isDisabled(Priority.INFO_INT) && Level.INFO.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.INFO, obj, th);
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public boolean isAttached(Appender appender) {
        if (appender == null || this.aai == null) {
            return false;
        }
        return this.aai.isAttached(appender);
    }

    public boolean isDebugEnabled() {
        if (this.repository.isDisabled(10000)) {
            return false;
        }
        return Level.DEBUG.isGreaterOrEqual(getEffectiveLevel());
    }

    public boolean isEnabledFor(Priority priority) {
        if (this.repository.isDisabled(priority.level)) {
            return false;
        }
        return priority.isGreaterOrEqual(getEffectiveLevel());
    }

    public boolean isInfoEnabled() {
        if (this.repository.isDisabled(Priority.INFO_INT)) {
            return false;
        }
        return Level.INFO.isGreaterOrEqual(getEffectiveLevel());
    }

    public void l7dlog(Priority priority, String str, Throwable th) {
        if (!this.repository.isDisabled(priority.level) && priority.isGreaterOrEqual(getEffectiveLevel())) {
            String resourceBundleString = getResourceBundleString(str);
            if (resourceBundleString == null) {
                resourceBundleString = str;
            }
            forcedLog(FQCN, priority, resourceBundleString, th);
        }
    }

    public void l7dlog(Priority priority, String str, Object[] objArr, Throwable th) {
        String str2;
        if (!this.repository.isDisabled(priority.level) && priority.isGreaterOrEqual(getEffectiveLevel())) {
            String resourceBundleString = getResourceBundleString(str);
            if (resourceBundleString == null) {
                str2 = str;
            } else {
                str2 = MessageFormat.format(resourceBundleString, objArr);
            }
            forcedLog(FQCN, priority, str2, th);
        }
    }

    public void log(Priority priority, Object obj, Throwable th) {
        if (!this.repository.isDisabled(priority.level) && priority.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, priority, obj, th);
        }
    }

    public void log(Priority priority, Object obj) {
        if (!this.repository.isDisabled(priority.level) && priority.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, priority, obj, null);
        }
    }

    public void log(String str, Priority priority, Object obj, Throwable th) {
        if (!this.repository.isDisabled(priority.level) && priority.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(str, priority, obj, th);
        }
    }

    private void fireRemoveAppenderEvent(Appender appender) {
        if (appender != null) {
            if (this.repository instanceof Hierarchy) {
                ((Hierarchy) this.repository).fireRemoveAppenderEvent(this, appender);
            } else if (this.repository instanceof HierarchyEventListener) {
                ((HierarchyEventListener) this.repository).removeAppenderEvent(this, appender);
            }
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public void removeAllAppenders() {
        if (this.aai != null) {
            Vector vector = new Vector();
            Enumeration allAppenders = this.aai.getAllAppenders();
            while (allAppenders != null && allAppenders.hasMoreElements()) {
                vector.add(allAppenders.nextElement());
            }
            this.aai.removeAllAppenders();
            Enumeration enumerationElements = vector.elements();
            while (enumerationElements.hasMoreElements()) {
                fireRemoveAppenderEvent((Appender) enumerationElements.nextElement());
            }
            this.aai = null;
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public void removeAppender(Appender appender) {
        if (appender == null || this.aai == null) {
            return;
        }
        boolean zIsAttached = this.aai.isAttached(appender);
        this.aai.removeAppender(appender);
        if (zIsAttached) {
            fireRemoveAppenderEvent(appender);
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public void removeAppender(String str) {
        if (str == null || this.aai == null) {
            return;
        }
        Appender appender = this.aai.getAppender(str);
        this.aai.removeAppender(str);
        if (appender != null) {
            fireRemoveAppenderEvent(appender);
        }
    }

    public void setAdditivity(boolean z) {
        this.additive = z;
    }

    final void setHierarchy(LoggerRepository loggerRepository) {
        this.repository = loggerRepository;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public void setPriority(Priority priority) {
        this.level = (Level) priority;
    }

    public void setResourceBundle(ResourceBundle resourceBundle) {
        this.resourceBundle = resourceBundle;
    }

    public static void shutdown() {
        LogManager.shutdown();
    }

    public void warn(Object obj) {
        if (!this.repository.isDisabled(Priority.WARN_INT) && Level.WARN.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.WARN, obj, null);
        }
    }

    public void warn(Object obj, Throwable th) {
        if (!this.repository.isDisabled(Priority.WARN_INT) && Level.WARN.isGreaterOrEqual(getEffectiveLevel())) {
            forcedLog(FQCN, Level.WARN, obj, th);
        }
    }
}
