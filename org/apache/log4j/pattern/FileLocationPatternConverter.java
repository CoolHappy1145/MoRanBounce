package org.apache.log4j.pattern;

import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/pattern/FileLocationPatternConverter.class */
public final class FileLocationPatternConverter extends LoggingEventPatternConverter {
    private static final FileLocationPatternConverter INSTANCE = new FileLocationPatternConverter();

    private FileLocationPatternConverter() {
        super("File Location", "file");
    }

    public static FileLocationPatternConverter newInstance(String[] strArr) {
        return INSTANCE;
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent loggingEvent, StringBuffer stringBuffer) {
        LocationInfo locationInformation = loggingEvent.getLocationInformation();
        if (locationInformation != null) {
            stringBuffer.append(locationInformation.getFileName());
        }
    }
}
