package org.apache.log4j.pattern;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/pattern/FullLocationPatternConverter.class */
public final class FullLocationPatternConverter extends LoggingEventPatternConverter {
    private static final FullLocationPatternConverter INSTANCE = new FullLocationPatternConverter();

    private FullLocationPatternConverter() {
        super("Full Location", "fullLocation");
    }

    public static FullLocationPatternConverter newInstance(String[] strArr) {
        return INSTANCE;
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent loggingEvent, StringBuffer stringBuffer) {
        LocationInfo locationInformation = loggingEvent.getLocationInformation();
        if (locationInformation != null) {
            stringBuffer.append(locationInformation.fullInfo);
        }
    }
}
