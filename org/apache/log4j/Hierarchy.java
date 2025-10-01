package org.apache.log4j;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.p007or.ObjectRenderer;
import org.apache.log4j.p007or.RendererMap;
import org.apache.log4j.spi.HierarchyEventListener;
import org.apache.log4j.spi.LoggerFactory;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.RendererSupport;
import org.apache.log4j.spi.ThrowableRenderer;
import org.apache.log4j.spi.ThrowableRendererSupport;

/* loaded from: L-out.jar:org/apache/log4j/Hierarchy.class */
public class Hierarchy implements LoggerRepository, RendererSupport, ThrowableRendererSupport {
    private LoggerFactory defaultFactory;
    Logger root;
    RendererMap rendererMap;
    int thresholdInt;
    Level threshold;
    boolean emittedNoAppenderWarning = false;
    boolean emittedNoResourceBundleWarning = false;
    private ThrowableRenderer throwableRenderer = null;

    /* renamed from: ht */
    Hashtable f186ht = new Hashtable();
    private Vector listeners = new Vector(1);

    public Hierarchy(Logger logger) {
        this.root = logger;
        setThreshold(Level.ALL);
        this.root.setHierarchy(this);
        this.rendererMap = new RendererMap();
        this.defaultFactory = new DefaultCategoryFactory();
    }

    public void addRenderer(Class cls, ObjectRenderer objectRenderer) {
        this.rendererMap.put(cls, objectRenderer);
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void addHierarchyEventListener(HierarchyEventListener hierarchyEventListener) {
        if (this.listeners.contains(hierarchyEventListener)) {
            LogLog.warn("Ignoring attempt to add an existent listener.");
        } else {
            this.listeners.addElement(hierarchyEventListener);
        }
    }

    public void clear() {
        this.f186ht.clear();
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void emitNoAppenderWarning(Category category) {
        if (!this.emittedNoAppenderWarning) {
            LogLog.warn(new StringBuffer().append("No appenders could be found for logger (").append(category.getName()).append(").").toString());
            LogLog.warn("Please initialize the log4j system properly.");
            LogLog.warn("See http://logging.apache.org/log4j/1.2/faq.html#noconfig for more info.");
            this.emittedNoAppenderWarning = true;
        }
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Logger exists(String str) {
        Object obj = this.f186ht.get(new CategoryKey(str));
        if (obj instanceof Logger) {
            return (Logger) obj;
        }
        return null;
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void setThreshold(String str) {
        Level level = Level.toLevel(str, (Level) null);
        if (level != null) {
            setThreshold(level);
        } else {
            LogLog.warn(new StringBuffer().append("Could not convert [").append(str).append("] to Level.").toString());
        }
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void setThreshold(Level level) {
        if (level != null) {
            this.thresholdInt = level.level;
            this.threshold = level;
        }
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void fireAddAppenderEvent(Category category, Appender appender) {
        if (this.listeners != null) {
            int size = this.listeners.size();
            for (int i = 0; i < size; i++) {
                ((HierarchyEventListener) this.listeners.elementAt(i)).addAppenderEvent(category, appender);
            }
        }
    }

    void fireRemoveAppenderEvent(Category category, Appender appender) {
        if (this.listeners != null) {
            int size = this.listeners.size();
            for (int i = 0; i < size; i++) {
                ((HierarchyEventListener) this.listeners.elementAt(i)).removeAppenderEvent(category, appender);
            }
        }
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Level getThreshold() {
        return this.threshold;
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Logger getLogger(String str) {
        return getLogger(str, this.defaultFactory);
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Logger getLogger(String str, LoggerFactory loggerFactory) {
        CategoryKey categoryKey = new CategoryKey(str);
        synchronized (this.f186ht) {
            Object obj = this.f186ht.get(categoryKey);
            if (obj == null) {
                Logger loggerMakeNewLoggerInstance = loggerFactory.makeNewLoggerInstance(str);
                loggerMakeNewLoggerInstance.setHierarchy(this);
                this.f186ht.put(categoryKey, loggerMakeNewLoggerInstance);
                updateParents(loggerMakeNewLoggerInstance);
                return loggerMakeNewLoggerInstance;
            }
            if (obj instanceof Logger) {
                return (Logger) obj;
            }
            if (obj instanceof ProvisionNode) {
                Logger loggerMakeNewLoggerInstance2 = loggerFactory.makeNewLoggerInstance(str);
                loggerMakeNewLoggerInstance2.setHierarchy(this);
                this.f186ht.put(categoryKey, loggerMakeNewLoggerInstance2);
                updateChildren((ProvisionNode) obj, loggerMakeNewLoggerInstance2);
                updateParents(loggerMakeNewLoggerInstance2);
                return loggerMakeNewLoggerInstance2;
            }
            return null;
        }
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Enumeration getCurrentLoggers() {
        Vector vector = new Vector(this.f186ht.size());
        Enumeration enumerationElements = this.f186ht.elements();
        while (enumerationElements.hasMoreElements()) {
            Object objNextElement = enumerationElements.nextElement();
            if (objNextElement instanceof Logger) {
                vector.addElement(objNextElement);
            }
        }
        return vector.elements();
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Enumeration getCurrentCategories() {
        return getCurrentLoggers();
    }

    @Override // org.apache.log4j.spi.RendererSupport
    public RendererMap getRendererMap() {
        return this.rendererMap;
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Logger getRootLogger() {
        return this.root;
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public boolean isDisabled(int i) {
        return this.thresholdInt > i;
    }

    public void overrideAsNeeded(String str) {
        LogLog.warn("The Hiearchy.overrideAsNeeded method has been deprecated.");
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void resetConfiguration() {
        getRootLogger().setLevel(Level.DEBUG);
        this.root.setResourceBundle(null);
        setThreshold(Level.ALL);
        synchronized (this.f186ht) {
            shutdown();
            Enumeration currentLoggers = getCurrentLoggers();
            while (currentLoggers.hasMoreElements()) {
                Logger logger = (Logger) currentLoggers.nextElement();
                logger.setLevel(null);
                logger.setAdditivity(true);
                logger.setResourceBundle(null);
            }
        }
        this.rendererMap.clear();
        this.throwableRenderer = null;
    }

    public void setDisableOverride(String str) {
        LogLog.warn("The Hiearchy.setDisableOverride method has been deprecated.");
    }

    @Override // org.apache.log4j.spi.RendererSupport
    public void setRenderer(Class cls, ObjectRenderer objectRenderer) {
        this.rendererMap.put(cls, objectRenderer);
    }

    @Override // org.apache.log4j.spi.ThrowableRendererSupport
    public void setThrowableRenderer(ThrowableRenderer throwableRenderer) {
        this.throwableRenderer = throwableRenderer;
    }

    @Override // org.apache.log4j.spi.ThrowableRendererSupport
    public ThrowableRenderer getThrowableRenderer() {
        return this.throwableRenderer;
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public void shutdown() {
        Logger rootLogger = getRootLogger();
        rootLogger.closeNestedAppenders();
        synchronized (this.f186ht) {
            Enumeration currentLoggers = getCurrentLoggers();
            while (currentLoggers.hasMoreElements()) {
                ((Logger) currentLoggers.nextElement()).closeNestedAppenders();
            }
            rootLogger.removeAllAppenders();
            Enumeration currentLoggers2 = getCurrentLoggers();
            while (currentLoggers2.hasMoreElements()) {
                ((Logger) currentLoggers2.nextElement()).removeAllAppenders();
            }
        }
    }

    private final void updateParents(Logger logger) {
        String str = logger.name;
        boolean z = false;
        int iLastIndexOf = str.lastIndexOf(46, str.length() - 1);
        while (true) {
            int i = iLastIndexOf;
            if (i < 0) {
                break;
            }
            CategoryKey categoryKey = new CategoryKey(str.substring(0, i));
            Object obj = this.f186ht.get(categoryKey);
            if (obj == null) {
                this.f186ht.put(categoryKey, new ProvisionNode(logger));
            } else if (obj instanceof Category) {
                z = true;
                logger.parent = (Category) obj;
                break;
            } else if (obj instanceof ProvisionNode) {
                ((ProvisionNode) obj).addElement(logger);
            } else {
                new IllegalStateException(new StringBuffer().append("unexpected object type ").append(obj.getClass()).append(" in ht.").toString()).printStackTrace();
            }
            iLastIndexOf = str.lastIndexOf(46, i - 1);
        }
        if (!z) {
            logger.parent = this.root;
        }
    }

    private final void updateChildren(ProvisionNode provisionNode, Logger logger) {
        int size = provisionNode.size();
        for (int i = 0; i < size; i++) {
            Logger logger2 = (Logger) provisionNode.elementAt(i);
            if (!logger2.parent.name.startsWith(logger.name)) {
                logger.parent = logger2.parent;
                logger2.parent = logger;
            }
        }
    }
}
