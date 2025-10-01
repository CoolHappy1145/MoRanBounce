package org.apache.log4j;

/* loaded from: L-out.jar:org/apache/log4j/Priority.class */
public class Priority {
    int level;
    String levelStr;
    int syslogEquivalent;
    public static final int OFF_INT = Integer.MAX_VALUE;
    public static final int DEBUG_INT = 10000;
    public static final int ALL_INT = Integer.MIN_VALUE;
    public static final int FATAL_INT = 50000;
    public static final Priority FATAL = new Level(FATAL_INT, "FATAL", 0);
    public static final int ERROR_INT = 40000;
    public static final Priority ERROR = new Level(ERROR_INT, "ERROR", 3);
    public static final int WARN_INT = 30000;
    public static final Priority WARN = new Level(WARN_INT, "WARN", 4);
    public static final int INFO_INT = 20000;
    public static final Priority INFO = new Level(INFO_INT, "INFO", 6);
    public static final Priority DEBUG = new Level(10000, "DEBUG", 7);

    protected Priority() {
        this.level = 10000;
        this.levelStr = "DEBUG";
        this.syslogEquivalent = 7;
    }

    protected Priority(int i, String str, int i2) {
        this.level = i;
        this.levelStr = str;
        this.syslogEquivalent = i2;
    }

    public boolean equals(Object obj) {
        return (obj instanceof Priority) && this.level == ((Priority) obj).level;
    }

    public final int getSyslogEquivalent() {
        return this.syslogEquivalent;
    }

    public boolean isGreaterOrEqual(Priority priority) {
        return this.level >= priority.level;
    }

    public static Priority[] getAllPossiblePriorities() {
        return new Priority[]{FATAL, ERROR, Level.WARN, INFO, DEBUG};
    }

    public final String toString() {
        return this.levelStr;
    }

    public final int toInt() {
        return this.level;
    }

    public static Priority toPriority(String str) {
        return Level.toLevel(str);
    }

    public static Priority toPriority(int i) {
        return toPriority(i, DEBUG);
    }

    public static Priority toPriority(int i, Priority priority) {
        return Level.toLevel(i, (Level) priority);
    }

    public static Priority toPriority(String str, Priority priority) {
        return Level.toLevel(str, (Level) priority);
    }
}
