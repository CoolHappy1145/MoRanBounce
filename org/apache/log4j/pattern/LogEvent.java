package org.apache.log4j.pattern;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.apache.log4j.NDC;
import org.apache.log4j.Priority;
import org.apache.log4j.helpers.Loader;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.log4j.spi.RendererSupport;
import org.apache.log4j.spi.ThrowableInformation;

/* loaded from: L-out.jar:org/apache/log4j/pattern/LogEvent.class */
public class LogEvent implements Serializable {
    public final String fqnOfCategoryClass;
    private Category logger;
    public final String categoryName;
    public Priority level;
    private String ndc;
    private Hashtable mdcCopy;
    private boolean ndcLookupRequired;
    private boolean mdcCopyLookupRequired;
    private Object message;
    private String renderedMessage;
    private String threadName;
    private ThrowableInformation throwableInfo;
    public final long timeStamp;
    private LocationInfo locationInfo;
    static final long serialVersionUID = -868428216207166145L;
    static final String TO_LEVEL = "toLevel";
    static Class class$org$apache$log4j$Level;
    private static long startTime = System.currentTimeMillis();
    static final Integer[] PARAM_ARRAY = new Integer[1];
    static final Class[] TO_LEVEL_PARAMS = {Integer.TYPE};
    static final Hashtable methodCache = new Hashtable(3);

    public LogEvent(String str, Category category, Priority priority, Object obj, Throwable th) {
        this.ndcLookupRequired = true;
        this.mdcCopyLookupRequired = true;
        this.fqnOfCategoryClass = str;
        this.logger = category;
        this.categoryName = category.getName();
        this.level = priority;
        this.message = obj;
        if (th != null) {
            this.throwableInfo = new ThrowableInformation(th);
        }
        this.timeStamp = System.currentTimeMillis();
    }

    public LogEvent(String str, Category category, long j, Priority priority, Object obj, Throwable th) {
        this.ndcLookupRequired = true;
        this.mdcCopyLookupRequired = true;
        this.fqnOfCategoryClass = str;
        this.logger = category;
        this.categoryName = category.getName();
        this.level = priority;
        this.message = obj;
        if (th != null) {
            this.throwableInfo = new ThrowableInformation(th);
        }
        this.timeStamp = j;
    }

    public LogEvent(String str, Logger logger, long j, Level level, Object obj, String str2, ThrowableInformation throwableInformation, String str3, LocationInfo locationInfo, Map map) {
        this.ndcLookupRequired = true;
        this.mdcCopyLookupRequired = true;
        this.fqnOfCategoryClass = str;
        this.logger = logger;
        if (logger != null) {
            this.categoryName = logger.getName();
        } else {
            this.categoryName = null;
        }
        this.level = level;
        this.message = obj;
        if (throwableInformation != null) {
            this.throwableInfo = throwableInformation;
        }
        this.timeStamp = j;
        this.threadName = str2;
        this.ndcLookupRequired = false;
        this.ndc = str3;
        this.locationInfo = locationInfo;
        this.mdcCopyLookupRequired = false;
        if (map != null) {
            this.mdcCopy = new Hashtable(map);
        }
    }

    public LocationInfo getLocationInformation() {
        if (this.locationInfo == null) {
            this.locationInfo = new LocationInfo(new Throwable(), this.fqnOfCategoryClass);
        }
        return this.locationInfo;
    }

    public Level getLevel() {
        return (Level) this.level;
    }

    public String getLoggerName() {
        return this.categoryName;
    }

    public Object getMessage() {
        if (this.message != null) {
            return this.message;
        }
        return getRenderedMessage();
    }

    public String getNDC() {
        if (this.ndcLookupRequired) {
            this.ndcLookupRequired = false;
            this.ndc = NDC.get();
        }
        return this.ndc;
    }

    public Object getMDC(String str) {
        Object obj;
        if (this.mdcCopy != null && (obj = this.mdcCopy.get(str)) != null) {
            return obj;
        }
        return MDC.get(str);
    }

    public void getMDCCopy() {
        if (this.mdcCopyLookupRequired) {
            this.mdcCopyLookupRequired = false;
            Hashtable context = MDC.getContext();
            if (context != null) {
                this.mdcCopy = (Hashtable) context.clone();
            }
        }
    }

    public String getRenderedMessage() {
        if (this.renderedMessage == null && this.message != null) {
            if (this.message instanceof String) {
                this.renderedMessage = (String) this.message;
            } else {
                LoggerRepository loggerRepository = this.logger.getLoggerRepository();
                if (loggerRepository instanceof RendererSupport) {
                    this.renderedMessage = ((RendererSupport) loggerRepository).getRendererMap().findAndRender(this.message);
                } else {
                    this.renderedMessage = this.message.toString();
                }
            }
        }
        return this.renderedMessage;
    }

    public static long getStartTime() {
        return startTime;
    }

    public String getThreadName() {
        if (this.threadName == null) {
            this.threadName = Thread.currentThread().getName();
        }
        return this.threadName;
    }

    public ThrowableInformation getThrowableInformation() {
        return this.throwableInfo;
    }

    public String[] getThrowableStrRep() {
        if (this.throwableInfo == null) {
            return null;
        }
        return this.throwableInfo.getThrowableStrRep();
    }

    private void readLevel(ObjectInputStream objectInputStream) throws NoSuchMethodException, IOException, SecurityException {
        int i = objectInputStream.readInt();
        try {
            String str = (String) objectInputStream.readObject();
            if (str == null) {
                this.level = Level.toLevel(i);
            } else {
                Method declaredMethod = (Method) methodCache.get(str);
                if (declaredMethod == null) {
                    declaredMethod = Loader.loadClass(str).getDeclaredMethod(TO_LEVEL, TO_LEVEL_PARAMS);
                    methodCache.put(str, declaredMethod);
                }
                PARAM_ARRAY[0] = new Integer(i);
                this.level = (Level) declaredMethod.invoke(null, PARAM_ARRAY);
            }
        } catch (Exception e) {
            LogLog.warn("Level deserialization failed, reverting to default.", e);
            this.level = Level.toLevel(i);
        }
    }

    private void readObject(ObjectInputStream objectInputStream) throws NoSuchMethodException, ClassNotFoundException, IOException, SecurityException {
        objectInputStream.defaultReadObject();
        readLevel(objectInputStream);
        if (this.locationInfo == null) {
            this.locationInfo = new LocationInfo(null, null);
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws Throwable {
        getThreadName();
        getRenderedMessage();
        getNDC();
        getMDCCopy();
        getThrowableStrRep();
        objectOutputStream.defaultWriteObject();
        writeLevel(objectOutputStream);
    }

    private void writeLevel(ObjectOutputStream objectOutputStream) throws Throwable {
        Class<?> clsClass$;
        objectOutputStream.writeInt(this.level.toInt());
        Class<?> cls = this.level.getClass();
        if (class$org$apache$log4j$Level == null) {
            clsClass$ = class$("org.apache.log4j.Level");
            class$org$apache$log4j$Level = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$Level;
        }
        if (cls == clsClass$) {
            objectOutputStream.writeObject(null);
        } else {
            objectOutputStream.writeObject(cls.getName());
        }
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public final void setProperty(String str, String str2) {
        if (this.mdcCopy == null) {
            getMDCCopy();
        }
        if (this.mdcCopy == null) {
            this.mdcCopy = new Hashtable();
        }
        this.mdcCopy.put(str, str2);
    }

    public final String getProperty(String str) {
        Object mdc = getMDC(str);
        String string = null;
        if (mdc != null) {
            string = mdc.toString();
        }
        return string;
    }

    public final boolean locationInformationExists() {
        return this.locationInfo != null;
    }

    public final long getTimeStamp() {
        return this.timeStamp;
    }

    public Set getPropertyKeySet() {
        return getProperties().keySet();
    }

    public Map getProperties() {
        Map map;
        getMDCCopy();
        if (this.mdcCopy == null) {
            map = new HashMap();
        } else {
            map = this.mdcCopy;
        }
        return Collections.unmodifiableMap(map);
    }

    public String getFQNOfLoggerClass() {
        return this.fqnOfCategoryClass;
    }
}
