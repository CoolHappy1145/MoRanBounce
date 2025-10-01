package org.apache.log4j.lf5;

import org.apache.log4j.spi.ThrowableInformation;

/* loaded from: L-out.jar:org/apache/log4j/lf5/Log4JLogRecord.class */
public class Log4JLogRecord extends LogRecord {
    @Override // org.apache.log4j.lf5.LogRecord
    public boolean isSevereLevel() {
        boolean z = false;
        if (LogLevel.ERROR.equals(getLevel()) || LogLevel.FATAL.equals(getLevel())) {
            z = true;
        }
        return z;
    }

    public void setThrownStackTrace(ThrowableInformation throwableInformation) {
        String[] throwableStrRep = throwableInformation.getThrowableStrRep();
        StringBuffer stringBuffer = new StringBuffer();
        for (String str : throwableStrRep) {
            stringBuffer.append(new StringBuffer().append(str).append("\n").toString());
        }
        this._thrownStackTrace = stringBuffer.toString();
    }
}
