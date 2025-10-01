package org.apache.log4j.chainsaw;

import org.apache.log4j.Priority;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/chainsaw/EventDetails.class */
class EventDetails {
    private final long mTimeStamp;
    private final Priority mPriority;
    private final String mCategoryName;
    private final String mNDC;
    private final String mThreadName;
    private final String mMessage;
    private final String[] mThrowableStrRep;
    private final String mLocationDetails;

    EventDetails(long j, Priority priority, String str, String str2, String str3, String str4, String[] strArr, String str5) {
        this.mTimeStamp = j;
        this.mPriority = priority;
        this.mCategoryName = str;
        this.mNDC = str2;
        this.mThreadName = str3;
        this.mMessage = str4;
        this.mThrowableStrRep = strArr;
        this.mLocationDetails = str5;
    }

    EventDetails(LoggingEvent loggingEvent) {
        this(loggingEvent.timeStamp, loggingEvent.getLevel(), loggingEvent.getLoggerName(), loggingEvent.getNDC(), loggingEvent.getThreadName(), loggingEvent.getRenderedMessage(), loggingEvent.getThrowableStrRep(), loggingEvent.getLocationInformation() == null ? null : loggingEvent.getLocationInformation().fullInfo);
    }

    long getTimeStamp() {
        return this.mTimeStamp;
    }

    Priority getPriority() {
        return this.mPriority;
    }

    String getCategoryName() {
        return this.mCategoryName;
    }

    String getNDC() {
        return this.mNDC;
    }

    String getThreadName() {
        return this.mThreadName;
    }

    String getMessage() {
        return this.mMessage;
    }

    String getLocationDetails() {
        return this.mLocationDetails;
    }

    String[] getThrowableStrRep() {
        return this.mThrowableStrRep;
    }
}
