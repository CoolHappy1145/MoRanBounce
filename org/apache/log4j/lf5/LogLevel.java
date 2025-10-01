package org.apache.log4j.lf5;

import java.awt.Color;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: L-out.jar:org/apache/log4j/lf5/LogLevel.class */
public class LogLevel implements Serializable {
    protected String _label;
    protected int _precedence;
    private static Map _logLevelColorMap;
    public static final LogLevel FATAL = new LogLevel("FATAL", 0);
    public static final LogLevel ERROR = new LogLevel("ERROR", 1);
    public static final LogLevel WARN = new LogLevel("WARN", 2);
    public static final LogLevel INFO = new LogLevel("INFO", 3);
    public static final LogLevel DEBUG = new LogLevel("DEBUG", 4);
    public static final LogLevel SEVERE = new LogLevel("SEVERE", 1);
    public static final LogLevel WARNING = new LogLevel("WARNING", 2);
    public static final LogLevel CONFIG = new LogLevel("CONFIG", 4);
    public static final LogLevel FINE = new LogLevel("FINE", 5);
    public static final LogLevel FINER = new LogLevel("FINER", 6);
    public static final LogLevel FINEST = new LogLevel("FINEST", 7);
    private static Map _registeredLogLevelMap = new HashMap();
    private static LogLevel[] _log4JLevels = {FATAL, ERROR, WARN, INFO, DEBUG};
    private static LogLevel[] _jdk14Levels = {SEVERE, WARNING, INFO, CONFIG, FINE, FINER, FINEST};
    private static LogLevel[] _allDefaultLevels = {FATAL, ERROR, WARN, INFO, DEBUG, SEVERE, WARNING, CONFIG, FINE, FINER, FINEST};
    private static Map _logLevelMap = new HashMap();

    static {
        for (int i = 0; i < _allDefaultLevels.length; i++) {
            _logLevelMap.put(_allDefaultLevels[i].getLabel(), _allDefaultLevels[i]);
        }
        _logLevelColorMap = new HashMap();
        for (int i2 = 0; i2 < _allDefaultLevels.length; i2++) {
            _logLevelColorMap.put(_allDefaultLevels[i2], Color.black);
        }
    }

    public LogLevel(String str, int i) {
        this._label = str;
        this._precedence = i;
    }

    public String getLabel() {
        return this._label;
    }

    public boolean encompasses(LogLevel logLevel) {
        if (logLevel.getPrecedence() <= getPrecedence()) {
            return true;
        }
        return false;
    }

    public static LogLevel valueOf(String str) throws LogLevelFormatException {
        LogLevel logLevel = null;
        if (str != null) {
            str = str.trim().toUpperCase();
            logLevel = (LogLevel) _logLevelMap.get(str);
        }
        if (logLevel == null && _registeredLogLevelMap.size() > 0) {
            logLevel = (LogLevel) _registeredLogLevelMap.get(str);
        }
        if (logLevel == null) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(new StringBuffer().append("Error while trying to parse (").append(str).append(") into").toString());
            stringBuffer.append(" a LogLevel.");
            throw new LogLevelFormatException(stringBuffer.toString());
        }
        return logLevel;
    }

    public static LogLevel register(LogLevel logLevel) {
        if (logLevel != null && _logLevelMap.get(logLevel.getLabel()) == null) {
            return (LogLevel) _registeredLogLevelMap.put(logLevel.getLabel(), logLevel);
        }
        return null;
    }

    public static void register(LogLevel[] logLevelArr) {
        if (logLevelArr != null) {
            for (LogLevel logLevel : logLevelArr) {
                register(logLevel);
            }
        }
    }

    public static void register(List list) {
        if (list != null) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                register((LogLevel) it.next());
            }
        }
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if ((obj instanceof LogLevel) && getPrecedence() == ((LogLevel) obj).getPrecedence()) {
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

    public void setLogLevelColorMap(LogLevel logLevel, Color color) {
        _logLevelColorMap.remove(logLevel);
        if (color == null) {
            color = Color.black;
        }
        _logLevelColorMap.put(logLevel, color);
    }

    public static void resetLogLevelColorMap() {
        _logLevelColorMap.clear();
        for (int i = 0; i < _allDefaultLevels.length; i++) {
            _logLevelColorMap.put(_allDefaultLevels[i], Color.black);
        }
    }

    public static List getLog4JLevels() {
        return Arrays.asList(_log4JLevels);
    }

    public static List getJdk14Levels() {
        return Arrays.asList(_jdk14Levels);
    }

    public static List getAllDefaultLevels() {
        return Arrays.asList(_allDefaultLevels);
    }

    public static Map getLogLevelColorMap() {
        return _logLevelColorMap;
    }

    protected int getPrecedence() {
        return this._precedence;
    }
}
