package org.slf4j.helpers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;

/* loaded from: L-out.jar:org/slf4j/helpers/SubstituteLoggerFactory.class */
public class SubstituteLoggerFactory implements ILoggerFactory {
    boolean postInitialization = false;
    final Map loggers = new HashMap();
    final LinkedBlockingQueue eventQueue = new LinkedBlockingQueue();

    @Override // org.slf4j.ILoggerFactory
    public Logger getLogger(String str) {
        SubstituteLogger substituteLogger = (SubstituteLogger) this.loggers.get(str);
        if (substituteLogger == null) {
            substituteLogger = new SubstituteLogger(str, this.eventQueue, this.postInitialization);
            this.loggers.put(str, substituteLogger);
        }
        return substituteLogger;
    }

    public List getLoggerNames() {
        return new ArrayList(this.loggers.keySet());
    }

    public List getLoggers() {
        return new ArrayList(this.loggers.values());
    }

    public LinkedBlockingQueue getEventQueue() {
        return this.eventQueue;
    }

    public void postInitialization() {
        this.postInitialization = true;
    }

    public void clear() {
        this.loggers.clear();
        this.eventQueue.clear();
    }
}
