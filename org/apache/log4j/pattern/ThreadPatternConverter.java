package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/pattern/ThreadPatternConverter.class */
public class ThreadPatternConverter extends LoggingEventPatternConverter {
    private static final ThreadPatternConverter INSTANCE = new ThreadPatternConverter();

    private ThreadPatternConverter() {
        super("Thread", "thread");
    }

    public static ThreadPatternConverter newInstance(String[] strArr) {
        return INSTANCE;
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent loggingEvent, StringBuffer stringBuffer) {
        stringBuffer.append(loggingEvent.getThreadName());
    }
}
