package org.apache.log4j.lf5.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.apache.log4j.lf5.LogLevel;
import org.apache.log4j.lf5.LogRecord;

/* loaded from: L-out.jar:org/apache/log4j/lf5/util/AdapterLogRecord.class */
public class AdapterLogRecord extends LogRecord {
    private static LogLevel severeLevel = null;

    /* renamed from: sw */
    private static StringWriter f194sw = new StringWriter();

    /* renamed from: pw */
    private static PrintWriter f195pw = new PrintWriter(f194sw);

    @Override // org.apache.log4j.lf5.LogRecord
    public void setCategory(String str) {
        super.setCategory(str);
        super.setLocation(getLocationInfo(str));
    }

    @Override // org.apache.log4j.lf5.LogRecord
    public boolean isSevereLevel() {
        if (severeLevel == null) {
            return false;
        }
        return severeLevel.equals(getLevel());
    }

    public static void setSevereLevel(LogLevel logLevel) {
        severeLevel = logLevel;
    }

    public static LogLevel getSevereLevel() {
        return severeLevel;
    }

    protected String getLocationInfo(String str) {
        return parseLine(stackTraceToString(new Throwable()), str);
    }

    protected String stackTraceToString(Throwable th) {
        String string;
        synchronized (f194sw) {
            th.printStackTrace(f195pw);
            string = f194sw.toString();
            f194sw.getBuffer().setLength(0);
        }
        return string;
    }

    protected String parseLine(String str, String str2) {
        int iIndexOf = str.indexOf(str2);
        if (iIndexOf == -1) {
            return null;
        }
        String strSubstring = str.substring(iIndexOf);
        return strSubstring.substring(0, strSubstring.indexOf(")") + 1);
    }
}
