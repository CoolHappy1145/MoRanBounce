package org.slf4j.event;

import org.slf4j.Marker;

/* loaded from: L-out.jar:org/slf4j/event/LoggingEvent.class */
public interface LoggingEvent {
    Level getLevel();

    Marker getMarker();

    String getLoggerName();

    String getMessage();

    String getThreadName();

    Object[] getArgumentArray();

    long getTimeStamp();

    Throwable getThrowable();
}
