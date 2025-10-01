package org.apache.log4j.lf5.viewer;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/LogTableColumn.class */
public class LogTableColumn implements Serializable {
    private static final long serialVersionUID = -4275827753626456547L;
    protected String _label;
    public static final LogTableColumn DATE = new LogTableColumn("Date");
    public static final LogTableColumn THREAD = new LogTableColumn("Thread");
    public static final LogTableColumn MESSAGE_NUM = new LogTableColumn("Message #");
    public static final LogTableColumn LEVEL = new LogTableColumn("Level");
    public static final LogTableColumn NDC = new LogTableColumn("NDC");
    public static final LogTableColumn CATEGORY = new LogTableColumn("Category");
    public static final LogTableColumn MESSAGE = new LogTableColumn("Message");
    public static final LogTableColumn LOCATION = new LogTableColumn("Location");
    public static final LogTableColumn THROWN = new LogTableColumn("Thrown");
    private static LogTableColumn[] _log4JColumns = {DATE, THREAD, MESSAGE_NUM, LEVEL, NDC, CATEGORY, MESSAGE, LOCATION, THROWN};
    private static Map _logTableColumnMap = new HashMap();

    static {
        for (int i = 0; i < _log4JColumns.length; i++) {
            _logTableColumnMap.put(_log4JColumns[i].getLabel(), _log4JColumns[i]);
        }
    }

    public LogTableColumn(String str) {
        this._label = str;
    }

    public String getLabel() {
        return this._label;
    }

    public static LogTableColumn valueOf(String str) throws LogTableColumnFormatException {
        LogTableColumn logTableColumn = null;
        if (str != null) {
            str = str.trim();
            logTableColumn = (LogTableColumn) _logTableColumnMap.get(str);
        }
        if (logTableColumn == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(new StringBuffer().append("Error while trying to parse (").append(str).append(") into").toString());
            stringBuffer.append(" a LogTableColumn.");
            throw new LogTableColumnFormatException(stringBuffer.toString());
        }
        return logTableColumn;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if ((obj instanceof LogTableColumn) && getLabel() == ((LogTableColumn) obj).getLabel()) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return this._label.hashCode();
    }

    public String toString() {
        return this._label;
    }

    public static List getLogTableColumns() {
        return Arrays.asList(_log4JColumns);
    }

    public static LogTableColumn[] getLogTableColumnArray() {
        return _log4JColumns;
    }
}
