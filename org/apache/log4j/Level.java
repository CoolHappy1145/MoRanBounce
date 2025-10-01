package org.apache.log4j;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/* loaded from: L-out.jar:org/apache/log4j/Level.class */
public class Level extends Priority implements Serializable {
    public static final int TRACE_INT = 5000;
    public static final Level OFF = new Level(Integer.MAX_VALUE, "OFF", 0);
    public static final Level FATAL = new Level(Priority.FATAL_INT, "FATAL", 0);
    public static final Level ERROR = new Level(Priority.ERROR_INT, "ERROR", 3);
    public static final Level WARN = new Level(Priority.WARN_INT, "WARN", 4);
    public static final Level INFO = new Level(Priority.INFO_INT, "INFO", 6);
    public static final Level DEBUG = new Level(10000, "DEBUG", 7);
    public static final Level TRACE = new Level(5000, "TRACE", 7);
    public static final Level ALL = new Level(Integer.MIN_VALUE, "ALL", 7);
    static final long serialVersionUID = 3491141966387921974L;
    static Class class$org$apache$log4j$Level;

    protected Level(int i, String str, int i2) {
        super(i, str, i2);
    }

    public static Level toLevel(String str) {
        return toLevel(str, DEBUG);
    }

    public static Level toLevel(int i) {
        return toLevel(i, DEBUG);
    }

    public static Level toLevel(int i, Level level) {
        switch (i) {
            case Integer.MIN_VALUE:
                return ALL;
            case 5000:
                return TRACE;
            case 10000:
                return DEBUG;
            case Priority.INFO_INT /* 20000 */:
                return INFO;
            case Priority.WARN_INT /* 30000 */:
                return WARN;
            case Priority.ERROR_INT /* 40000 */:
                return ERROR;
            case Priority.FATAL_INT /* 50000 */:
                return FATAL;
            case Integer.MAX_VALUE:
                return OFF;
            default:
                return level;
        }
    }

    public static Level toLevel(String str, Level level) {
        if (str == null) {
            return level;
        }
        String upperCase = str.toUpperCase();
        return upperCase.equals("ALL") ? ALL : upperCase.equals("DEBUG") ? DEBUG : upperCase.equals("INFO") ? INFO : upperCase.equals("WARN") ? WARN : upperCase.equals("ERROR") ? ERROR : upperCase.equals("FATAL") ? FATAL : upperCase.equals("OFF") ? OFF : upperCase.equals("TRACE") ? TRACE : upperCase.equals("\u0130NFO") ? INFO : level;
    }

    private void readObject(ObjectInputStream objectInputStream) throws ClassNotFoundException, IOException {
        objectInputStream.defaultReadObject();
        this.level = objectInputStream.readInt();
        this.syslogEquivalent = objectInputStream.readInt();
        this.levelStr = objectInputStream.readUTF();
        if (this.levelStr == null) {
            this.levelStr = "";
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.level);
        objectOutputStream.writeInt(this.syslogEquivalent);
        objectOutputStream.writeUTF(this.levelStr);
    }

    private Object readResolve() throws Throwable {
        Class<?> clsClass$;
        Class<?> cls = getClass();
        if (class$org$apache$log4j$Level == null) {
            clsClass$ = class$("org.apache.log4j.Level");
            class$org$apache$log4j$Level = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$Level;
        }
        if (cls == clsClass$) {
            return toLevel(this.level);
        }
        return this;
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }
}
