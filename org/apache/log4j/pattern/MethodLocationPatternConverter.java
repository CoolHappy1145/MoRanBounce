package org.apache.log4j.pattern;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/pattern/MethodLocationPatternConverter.class */
public final class MethodLocationPatternConverter extends LoggingEventPatternConverter {
    private static final MethodLocationPatternConverter INSTANCE = new MethodLocationPatternConverter();

    private MethodLocationPatternConverter() {
        super("Method", "method");
    }

    public static MethodLocationPatternConverter newInstance(String[] strArr) {
        return INSTANCE;
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent loggingEvent, StringBuffer stringBuffer) {
        LocationInfo locationInformation = loggingEvent.getLocationInformation();
        if (locationInformation != null) {
            stringBuffer.append(locationInformation.getMethodName());
        }
    }
}
