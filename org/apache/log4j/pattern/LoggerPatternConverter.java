package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/pattern/LoggerPatternConverter.class */
public final class LoggerPatternConverter extends NamePatternConverter {
    private static final LoggerPatternConverter INSTANCE = new LoggerPatternConverter(null);

    private LoggerPatternConverter(String[] strArr) {
        super("Logger", "logger", strArr);
    }

    public static LoggerPatternConverter newInstance(String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return INSTANCE;
        }
        return new LoggerPatternConverter(strArr);
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent loggingEvent, StringBuffer stringBuffer) {
        int length = stringBuffer.length();
        stringBuffer.append(loggingEvent.getLoggerName());
        abbreviate(length, stringBuffer);
    }
}
