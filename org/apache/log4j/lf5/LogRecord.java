package org.apache.log4j.lf5;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;

/* loaded from: L-out.jar:org/apache/log4j/lf5/LogRecord.class */
public abstract class LogRecord implements Serializable {
    protected static long _seqCount = 0;
    protected String _thrownStackTrace;
    protected Throwable _thrown;
    protected long _millis = System.currentTimeMillis();
    protected String _category = "Debug";
    protected String _message = "";
    protected LogLevel _level = LogLevel.INFO;
    protected long _sequenceNumber = getNextId();
    protected String _thread = Thread.currentThread().toString();
    protected String _ndc = "";
    protected String _location = "";

    public abstract boolean isSevereLevel();

    public LogLevel getLevel() {
        return this._level;
    }

    public void setLevel(LogLevel logLevel) {
        this._level = logLevel;
    }

    public boolean hasThrown() {
        String string;
        Throwable thrown = getThrown();
        return (thrown == null || (string = thrown.toString()) == null || string.trim().length() == 0) ? false : true;
    }

    public boolean isFatal() {
        return isSevereLevel() || hasThrown();
    }

    public String getCategory() {
        return this._category;
    }

    public void setCategory(String str) {
        this._category = str;
    }

    public String getMessage() {
        return this._message;
    }

    public void setMessage(String str) {
        this._message = str;
    }

    public long getSequenceNumber() {
        return this._sequenceNumber;
    }

    public void setSequenceNumber(long j) {
        this._sequenceNumber = j;
    }

    public long getMillis() {
        return this._millis;
    }

    public void setMillis(long j) {
        this._millis = j;
    }

    public String getThreadDescription() {
        return this._thread;
    }

    public void setThreadDescription(String str) {
        this._thread = str;
    }

    public String getThrownStackTrace() {
        return this._thrownStackTrace;
    }

    public void setThrownStackTrace(String str) {
        this._thrownStackTrace = str;
    }

    public Throwable getThrown() {
        return this._thrown;
    }

    public void setThrown(Throwable th) throws IOException {
        if (th == null) {
            return;
        }
        this._thrown = th;
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        printWriter.flush();
        this._thrownStackTrace = stringWriter.toString();
        try {
            printWriter.close();
            stringWriter.close();
        } catch (IOException unused) {
        }
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(new StringBuffer().append("LogRecord: [").append(this._level).append(", ").append(this._message).append("]").toString());
        return stringBuffer.toString();
    }

    public String getNDC() {
        return this._ndc;
    }

    public void setNDC(String str) {
        this._ndc = str;
    }

    public String getLocation() {
        return this._location;
    }

    public void setLocation(String str) {
        this._location = str;
    }

    public static void resetSequenceNumber() {
        _seqCount = 0L;
    }

    protected static long getNextId() {
        _seqCount++;
        return _seqCount;
    }
}
