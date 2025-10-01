package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/pattern/LoggingEventPatternConverter.class */
public abstract class LoggingEventPatternConverter extends PatternConverter {
    public abstract void format(LoggingEvent loggingEvent, StringBuffer stringBuffer);

    protected LoggingEventPatternConverter(String str, String str2) {
        super(str, str2);
    }

    @Override // org.apache.log4j.pattern.PatternConverter
    public void format(Object obj, StringBuffer stringBuffer) {
        if (obj instanceof LoggingEvent) {
            format((LoggingEvent) obj, stringBuffer);
        }
    }
}
