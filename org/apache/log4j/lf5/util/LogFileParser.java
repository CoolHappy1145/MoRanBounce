package org.apache.log4j.lf5.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.SwingUtilities;
import org.apache.log4j.lf5.Log4JLogRecord;
import org.apache.log4j.lf5.LogLevel;
import org.apache.log4j.lf5.LogLevelFormatException;
import org.apache.log4j.lf5.LogRecord;
import org.apache.log4j.lf5.viewer.LogBrokerMonitor;
import org.apache.log4j.lf5.viewer.LogFactor5ErrorDialog;
import org.apache.log4j.lf5.viewer.LogFactor5LoadingDialog;

/* loaded from: L-out.jar:org/apache/log4j/lf5/util/LogFileParser.class */
public class LogFileParser implements Runnable {
    public static final String RECORD_DELIMITER = "[slf5s.start]";
    public static final String ATTRIBUTE_DELIMITER = "[slf5s.";
    public static final String DATE_DELIMITER = "[slf5s.DATE]";
    public static final String THREAD_DELIMITER = "[slf5s.THREAD]";
    public static final String CATEGORY_DELIMITER = "[slf5s.CATEGORY]";
    public static final String LOCATION_DELIMITER = "[slf5s.LOCATION]";
    public static final String MESSAGE_DELIMITER = "[slf5s.MESSAGE]";
    public static final String PRIORITY_DELIMITER = "[slf5s.PRIORITY]";
    public static final String NDC_DELIMITER = "[slf5s.NDC]";
    private static SimpleDateFormat _sdf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss,S");
    private LogBrokerMonitor _monitor;
    LogFactor5LoadingDialog _loadDialog;
    private InputStream _in;

    public LogFileParser(File file) {
        this(new FileInputStream(file));
    }

    public LogFileParser(InputStream inputStream) {
        this._in = null;
        this._in = inputStream;
    }

    public void parse(LogBrokerMonitor logBrokerMonitor) {
        this._monitor = logBrokerMonitor;
        new Thread(this).start();
    }

    @Override // java.lang.Runnable
    public void run() {
        LogRecord logRecordCreateLogRecord;
        int i = 0;
        boolean z = false;
        this._loadDialog = new LogFactor5LoadingDialog(this._monitor.getBaseFrame(), "Loading file...");
        try {
            String strLoadLogFile = loadLogFile(this._in);
            while (true) {
                int iIndexOf = strLoadLogFile.indexOf(RECORD_DELIMITER, i);
                if (iIndexOf == -1) {
                    break;
                }
                LogRecord logRecordCreateLogRecord2 = createLogRecord(strLoadLogFile.substring(i, iIndexOf));
                z = true;
                if (logRecordCreateLogRecord2 != null) {
                    this._monitor.addMessage(logRecordCreateLogRecord2);
                }
                i = iIndexOf + 13;
            }
            if (i < strLoadLogFile.length() && z && (logRecordCreateLogRecord = createLogRecord(strLoadLogFile.substring(i))) != null) {
                this._monitor.addMessage(logRecordCreateLogRecord);
            }
        } catch (IOException unused) {
            destroyDialog();
            displayError("Error - Unable to load log file!");
        } catch (RuntimeException unused2) {
            destroyDialog();
            displayError("Error - Invalid log file format.\nPlease see documentation on how to load log files.");
        }
        if (!z) {
            throw new RuntimeException("Invalid log file format");
        }
        SwingUtilities.invokeLater(new Runnable(this) { // from class: org.apache.log4j.lf5.util.LogFileParser.1
            private final LogFileParser this$0;

            {
                this.this$0 = this;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.this$0.destroyDialog();
            }
        });
        this._in = null;
    }

    protected void displayError(String str) {
        new LogFactor5ErrorDialog(this._monitor.getBaseFrame(), str);
    }

    private void destroyDialog() {
        this._loadDialog.hide();
        this._loadDialog.dispose();
    }

    private String loadLogFile(InputStream inputStream) throws IOException {
        StringBuffer stringBuffer;
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        int iAvailable = bufferedInputStream.available();
        if (iAvailable > 0) {
            stringBuffer = new StringBuffer(iAvailable);
        } else {
            stringBuffer = new StringBuffer(1024);
        }
        while (true) {
            int i = bufferedInputStream.read();
            if (i != -1) {
                stringBuffer.append((char) i);
            } else {
                bufferedInputStream.close();
                return stringBuffer.toString();
            }
        }
    }

    private String parseAttribute(String str, String str2) {
        int iIndexOf = str2.indexOf(str);
        if (iIndexOf == -1) {
            return null;
        }
        return getAttribute(iIndexOf, str2);
    }

    private long parseDate(String str) {
        try {
            String attribute = parseAttribute(DATE_DELIMITER, str);
            if (attribute == null) {
                return 0L;
            }
            return _sdf.parse(attribute).getTime();
        } catch (ParseException unused) {
            return 0L;
        }
    }

    private LogLevel parsePriority(String str) {
        String attribute = parseAttribute(PRIORITY_DELIMITER, str);
        if (attribute != null) {
            try {
                return LogLevel.valueOf(attribute);
            } catch (LogLevelFormatException unused) {
                return LogLevel.DEBUG;
            }
        }
        return LogLevel.DEBUG;
    }

    private String parseThread(String str) {
        return parseAttribute(THREAD_DELIMITER, str);
    }

    private String parseCategory(String str) {
        return parseAttribute(CATEGORY_DELIMITER, str);
    }

    private String parseLocation(String str) {
        return parseAttribute(LOCATION_DELIMITER, str);
    }

    private String parseMessage(String str) {
        return parseAttribute(MESSAGE_DELIMITER, str);
    }

    private String parseNDC(String str) {
        return parseAttribute(NDC_DELIMITER, str);
    }

    private String parseThrowable(String str) {
        return getAttribute(str.length(), str);
    }

    private LogRecord createLogRecord(String str) {
        if (str == null || str.trim().length() == 0) {
            return null;
        }
        Log4JLogRecord log4JLogRecord = new Log4JLogRecord();
        log4JLogRecord.setMillis(parseDate(str));
        log4JLogRecord.setLevel(parsePriority(str));
        log4JLogRecord.setCategory(parseCategory(str));
        log4JLogRecord.setLocation(parseLocation(str));
        log4JLogRecord.setThreadDescription(parseThread(str));
        log4JLogRecord.setNDC(parseNDC(str));
        log4JLogRecord.setMessage(parseMessage(str));
        log4JLogRecord.setThrownStackTrace(parseThrowable(str));
        return log4JLogRecord;
    }

    private String getAttribute(int i, String str) {
        int iLastIndexOf = str.lastIndexOf(ATTRIBUTE_DELIMITER, i - 1);
        if (iLastIndexOf == -1) {
            return str.substring(0, i);
        }
        return str.substring(str.indexOf("]", iLastIndexOf) + 1, i).trim();
    }
}
