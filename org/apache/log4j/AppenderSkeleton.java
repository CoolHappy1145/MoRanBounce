package org.apache.log4j;

import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OnlyOnceErrorHandler;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.OptionHandler;

/* loaded from: L-out.jar:org/apache/log4j/AppenderSkeleton.class */
public abstract class AppenderSkeleton implements Appender, OptionHandler {
    protected Layout layout;
    protected String name;
    protected Priority threshold;
    protected Filter headFilter;
    protected Filter tailFilter;
    protected ErrorHandler errorHandler = new OnlyOnceErrorHandler();
    protected boolean closed = false;

    protected abstract void append(LoggingEvent loggingEvent);

    public AppenderSkeleton() {
    }

    protected AppenderSkeleton(boolean z) {
    }

    @Override // org.apache.log4j.Appender
    public void addFilter(Filter filter) {
        if (this.headFilter == null) {
            this.tailFilter = filter;
            this.headFilter = filter;
        } else {
            this.tailFilter.setNext(filter);
            this.tailFilter = filter;
        }
    }

    @Override // org.apache.log4j.Appender
    public void clearFilters() {
        this.tailFilter = null;
        this.headFilter = null;
    }

    public void finalize() {
        if (this.closed) {
            return;
        }
        LogLog.debug(new StringBuffer().append("Finalizing appender named [").append(this.name).append("].").toString());
        close();
    }

    @Override // org.apache.log4j.Appender
    public ErrorHandler getErrorHandler() {
        return this.errorHandler;
    }

    @Override // org.apache.log4j.Appender
    public Filter getFilter() {
        return this.headFilter;
    }

    public final Filter getFirstFilter() {
        return this.headFilter;
    }

    @Override // org.apache.log4j.Appender
    public Layout getLayout() {
        return this.layout;
    }

    @Override // org.apache.log4j.Appender
    public final String getName() {
        return this.name;
    }

    public Priority getThreshold() {
        return this.threshold;
    }

    public boolean isAsSevereAsThreshold(Priority priority) {
        return this.threshold == null || priority.isGreaterOrEqual(this.threshold);
    }

    @Override // org.apache.log4j.Appender
    public void doAppend(LoggingEvent loggingEvent) {
        if (this.closed) {
            LogLog.error(new StringBuffer().append("Attempted to append to closed appender named [").append(this.name).append("].").toString());
            return;
        }
        if (!isAsSevereAsThreshold(loggingEvent.getLevel())) {
            return;
        }
        Filter next = this.headFilter;
        while (next != null) {
            switch (next.decide(loggingEvent)) {
                case -1:
                    return;
                case 0:
                    next = next.getNext();
                    continue;
            }
            append(loggingEvent);
        }
        append(loggingEvent);
    }

    @Override // org.apache.log4j.Appender
    public void setErrorHandler(ErrorHandler errorHandler) {
        if (errorHandler == null) {
            LogLog.warn("You have tried to set a null error-handler.");
        } else {
            this.errorHandler = errorHandler;
        }
    }

    @Override // org.apache.log4j.Appender
    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    @Override // org.apache.log4j.Appender
    public void setName(String str) {
        this.name = str;
    }

    public void setThreshold(Priority priority) {
        this.threshold = priority;
    }
}
