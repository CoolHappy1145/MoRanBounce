package org.apache.log4j;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.log4j.helpers.AppenderAttachableImpl;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.AppenderAttachable;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/AsyncAppender.class */
public class AsyncAppender extends AppenderSkeleton implements AppenderAttachable {
    public static final int DEFAULT_BUFFER_SIZE = 128;
    private final List buffer = new ArrayList();
    private final Map discardMap = new HashMap();
    private int bufferSize = 128;
    private boolean locationInfo = false;
    private boolean blocking = true;
    private final AppenderAttachableImpl appenders = new AppenderAttachableImpl();
    AppenderAttachableImpl aai = this.appenders;
    private final Thread dispatcher = new Thread(new Dispatcher(this, this.buffer, this.discardMap, this.appenders));

    public AsyncAppender() {
        this.dispatcher.setDaemon(true);
        this.dispatcher.setName(new StringBuffer().append("AsyncAppender-Dispatcher-").append(this.dispatcher.getName()).toString());
        this.dispatcher.start();
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public void addAppender(Appender appender) {
        synchronized (this.appenders) {
            this.appenders.addAppender(appender);
        }
    }

    @Override // org.apache.log4j.AppenderSkeleton
    public void append(LoggingEvent loggingEvent) {
        if (this.dispatcher == null || !this.dispatcher.isAlive() || this.bufferSize <= 0) {
            synchronized (this.appenders) {
                this.appenders.appendLoopOnAppenders(loggingEvent);
            }
            return;
        }
        loggingEvent.getNDC();
        loggingEvent.getThreadName();
        loggingEvent.getMDCCopy();
        if (this.locationInfo) {
            loggingEvent.getLocationInformation();
        }
        loggingEvent.getRenderedMessage();
        loggingEvent.getThrowableStrRep();
        synchronized (this.buffer) {
            while (true) {
                int size = this.buffer.size();
                if (size < this.bufferSize) {
                    this.buffer.add(loggingEvent);
                    if (size == 0) {
                        this.buffer.notifyAll();
                    }
                } else {
                    boolean z = true;
                    if (this.blocking && !Thread.interrupted() && Thread.currentThread() != this.dispatcher) {
                        try {
                            this.buffer.wait();
                            z = false;
                        } catch (InterruptedException unused) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    if (z) {
                        String loggerName = loggingEvent.getLoggerName();
                        DiscardSummary discardSummary = (DiscardSummary) this.discardMap.get(loggerName);
                        if (discardSummary == null) {
                            this.discardMap.put(loggerName, new DiscardSummary(loggingEvent));
                        } else {
                            discardSummary.add(loggingEvent);
                        }
                    }
                }
            }
        }
    }

    @Override // org.apache.log4j.Appender
    public void close() throws InterruptedException {
        synchronized (this.buffer) {
            this.closed = true;
            this.buffer.notifyAll();
        }
        try {
            this.dispatcher.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LogLog.error("Got an InterruptedException while waiting for the dispatcher to finish.", e);
        }
        synchronized (this.appenders) {
            Enumeration allAppenders = this.appenders.getAllAppenders();
            if (allAppenders != null) {
                while (allAppenders.hasMoreElements()) {
                    Object objNextElement = allAppenders.nextElement();
                    if (objNextElement instanceof Appender) {
                        ((Appender) objNextElement).close();
                    }
                }
            }
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public Enumeration getAllAppenders() {
        Enumeration allAppenders;
        synchronized (this.appenders) {
            allAppenders = this.appenders.getAllAppenders();
        }
        return allAppenders;
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public Appender getAppender(String str) {
        Appender appender;
        synchronized (this.appenders) {
            appender = this.appenders.getAppender(str);
        }
        return appender;
    }

    public boolean getLocationInfo() {
        return this.locationInfo;
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public boolean isAttached(Appender appender) {
        boolean zIsAttached;
        synchronized (this.appenders) {
            zIsAttached = this.appenders.isAttached(appender);
        }
        return zIsAttached;
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public void removeAllAppenders() {
        synchronized (this.appenders) {
            this.appenders.removeAllAppenders();
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public void removeAppender(Appender appender) {
        synchronized (this.appenders) {
            this.appenders.removeAppender(appender);
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public void removeAppender(String str) {
        synchronized (this.appenders) {
            this.appenders.removeAppender(str);
        }
    }

    public void setLocationInfo(boolean z) {
        this.locationInfo = z;
    }

    public void setBufferSize(int i) {
        if (i < 0) {
            throw new NegativeArraySizeException("size");
        }
        synchronized (this.buffer) {
            this.bufferSize = i < 1 ? 1 : i;
            this.buffer.notifyAll();
        }
    }

    public int getBufferSize() {
        return this.bufferSize;
    }

    public void setBlocking(boolean z) {
        synchronized (this.buffer) {
            this.blocking = z;
            this.buffer.notifyAll();
        }
    }

    public boolean getBlocking() {
        return this.blocking;
    }

    /* loaded from: L-out.jar:org/apache/log4j/AsyncAppender$DiscardSummary.class */
    private static final class DiscardSummary {
        private LoggingEvent maxEvent;
        private int count = 1;

        public DiscardSummary(LoggingEvent loggingEvent) {
            this.maxEvent = loggingEvent;
        }

        public void add(LoggingEvent loggingEvent) {
            if (loggingEvent.getLevel().toInt() > this.maxEvent.getLevel().toInt()) {
                this.maxEvent = loggingEvent;
            }
            this.count++;
        }

        public LoggingEvent createEvent() {
            return new LoggingEvent("org.apache.log4j.AsyncAppender.DONT_REPORT_LOCATION", Logger.getLogger(this.maxEvent.getLoggerName()), this.maxEvent.getLevel(), MessageFormat.format("Discarded {0} messages due to full event buffer including: {1}", new Integer(this.count), this.maxEvent.getMessage()), null);
        }
    }

    /* loaded from: L-out.jar:org/apache/log4j/AsyncAppender$Dispatcher.class */
    private static class Dispatcher implements Runnable {
        private final AsyncAppender parent;
        private final List buffer;
        private final Map discardMap;
        private final AppenderAttachableImpl appenders;

        public Dispatcher(AsyncAppender asyncAppender, List list, Map map, AppenderAttachableImpl appenderAttachableImpl) {
            this.parent = asyncAppender;
            this.buffer = list;
            this.appenders = appenderAttachableImpl;
            this.discardMap = map;
        }

        @Override // java.lang.Runnable
        public void run() {
            boolean z = true;
            while (z) {
                try {
                    LoggingEvent[] loggingEventArr = null;
                    synchronized (this.buffer) {
                        int size = this.buffer.size();
                        z = !this.parent.closed;
                        while (size == 0 && z) {
                            this.buffer.wait();
                            size = this.buffer.size();
                            z = !this.parent.closed;
                        }
                        if (size > 0) {
                            loggingEventArr = new LoggingEvent[size + this.discardMap.size()];
                            this.buffer.toArray(loggingEventArr);
                            int i = size;
                            Iterator it = this.discardMap.values().iterator();
                            while (it.hasNext()) {
                                int i2 = i;
                                i++;
                                loggingEventArr[i2] = ((DiscardSummary) it.next()).createEvent();
                            }
                            this.buffer.clear();
                            this.discardMap.clear();
                            this.buffer.notifyAll();
                        }
                    }
                    if (loggingEventArr != null) {
                        for (LoggingEvent loggingEvent : loggingEventArr) {
                            synchronized (this.appenders) {
                                this.appenders.appendLoopOnAppenders(loggingEvent);
                            }
                        }
                    }
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
    }
}
