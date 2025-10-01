package org.apache.log4j;

import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/LogXF.class */
public abstract class LogXF {
    protected static final Level TRACE = new Level(5000, "TRACE", 7);
    private static final String FQCN;
    static Class class$org$apache$log4j$LogXF;

    static {
        Class clsClass$;
        if (class$org$apache$log4j$LogXF == null) {
            clsClass$ = class$("org.apache.log4j.LogXF");
            class$org$apache$log4j$LogXF = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$LogXF;
        }
        FQCN = clsClass$.getName();
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    protected LogXF() {
    }

    protected static Boolean valueOf(boolean z) {
        if (z) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    protected static Character valueOf(char c) {
        return new Character(c);
    }

    protected static Byte valueOf(byte b) {
        return new Byte(b);
    }

    protected static Short valueOf(short s) {
        return new Short(s);
    }

    protected static Integer valueOf(int i) {
        return new Integer(i);
    }

    protected static Long valueOf(long j) {
        return new Long(j);
    }

    protected static Float valueOf(float f) {
        return new Float(f);
    }

    protected static Double valueOf(double d) {
        return new Double(d);
    }

    public static void entering(Logger logger, String str, String str2) {
        if (logger.isDebugEnabled()) {
            logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, new StringBuffer().append(str).append(".").append(str2).append(" ENTRY").toString(), null));
        }
    }

    public static void entering(Logger logger, String str, String str2, String str3) {
        if (logger.isDebugEnabled()) {
            logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, new StringBuffer().append(str).append(".").append(str2).append(" ENTRY ").append(str3).toString(), null));
        }
    }

    public static void entering(Logger logger, String str, String str2, Object obj) {
        if (logger.isDebugEnabled()) {
            String string = new StringBuffer().append(str).append(".").append(str2).append(" ENTRY ").toString();
            if (obj == null) {
                string = new StringBuffer().append(string).append(Configurator.NULL).toString();
            } else {
                try {
                    string = new StringBuffer().append(string).append(obj).toString();
                } catch (Throwable unused) {
                    string = new StringBuffer().append(string).append(LocationInfo.f204NA).toString();
                }
            }
            logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, string, null));
        }
    }

    public static void entering(Logger logger, String str, String str2, Object[] objArr) {
        String string;
        if (logger.isDebugEnabled()) {
            String string2 = new StringBuffer().append(str).append(".").append(str2).append(" ENTRY ").toString();
            if (objArr != null && objArr.length > 0) {
                String str3 = "{";
                for (Object obj : objArr) {
                    try {
                        string2 = new StringBuffer().append(string2).append(str3).append(obj).toString();
                    } catch (Throwable unused) {
                        string2 = new StringBuffer().append(string2).append(str3).append(LocationInfo.f204NA).toString();
                    }
                    str3 = ",";
                }
                string = new StringBuffer().append(string2).append("}").toString();
            } else {
                string = new StringBuffer().append(string2).append("{}").toString();
            }
            logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, string, null));
        }
    }

    public static void exiting(Logger logger, String str, String str2) {
        if (logger.isDebugEnabled()) {
            logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, new StringBuffer().append(str).append(".").append(str2).append(" RETURN").toString(), null));
        }
    }

    public static void exiting(Logger logger, String str, String str2, String str3) {
        if (logger.isDebugEnabled()) {
            logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, new StringBuffer().append(str).append(".").append(str2).append(" RETURN ").append(str3).toString(), null));
        }
    }

    public static void exiting(Logger logger, String str, String str2, Object obj) {
        if (logger.isDebugEnabled()) {
            String string = new StringBuffer().append(str).append(".").append(str2).append(" RETURN ").toString();
            if (obj == null) {
                string = new StringBuffer().append(string).append(Configurator.NULL).toString();
            } else {
                try {
                    string = new StringBuffer().append(string).append(obj).toString();
                } catch (Throwable unused) {
                    string = new StringBuffer().append(string).append(LocationInfo.f204NA).toString();
                }
            }
            logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, string, null));
        }
    }

    public static void throwing(Logger logger, String str, String str2, Throwable th) {
        if (logger.isDebugEnabled()) {
            logger.callAppenders(new LoggingEvent(FQCN, logger, Level.DEBUG, new StringBuffer().append(str).append(".").append(str2).append(" THROW").toString(), th));
        }
    }
}
