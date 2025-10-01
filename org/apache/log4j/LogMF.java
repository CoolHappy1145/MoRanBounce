package org.apache.log4j;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/LogMF.class */
public final class LogMF extends LogXF {
    private static NumberFormat numberFormat = null;
    private static Locale numberLocale = null;
    private static DateFormat dateFormat = null;
    private static Locale dateLocale = null;
    private static final String FQCN;
    static Class class$org$apache$log4j$LogMF;

    private LogMF() {
    }

    static {
        Class clsClass$;
        if (class$org$apache$log4j$LogMF == null) {
            clsClass$ = class$("org.apache.log4j.LogMF");
            class$org$apache$log4j$LogMF = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$LogMF;
        }
        FQCN = clsClass$.getName();
    }

    private static String formatNumber(Object obj) {
        Locale locale = Locale.getDefault();
        if (locale != numberLocale || numberFormat == null) {
            numberLocale = locale;
            numberFormat = NumberFormat.getInstance(locale);
        }
        return numberFormat.format(obj);
    }

    private static String formatDate(Object obj) {
        Locale locale = Locale.getDefault();
        if (locale != dateLocale || dateFormat == null) {
            dateLocale = locale;
            dateFormat = DateFormat.getDateTimeInstance(3, 3, locale);
        }
        return dateFormat.format(obj);
    }

    private static String formatObject(Object obj) {
        if (obj instanceof String) {
            return obj.toString();
        }
        if ((obj instanceof Double) || (obj instanceof Float)) {
            return formatNumber(obj);
        }
        if (obj instanceof Date) {
            return formatDate(obj);
        }
        return String.valueOf(obj);
    }

    private static boolean isSimple(String str) {
        if (str.indexOf(39) != -1) {
            return false;
        }
        int iIndexOf = str.indexOf(123);
        while (true) {
            int i = iIndexOf;
            if (i != -1) {
                if (i + 2 < str.length() && str.charAt(i + 2) == '}' && str.charAt(i + 1) >= '0' && str.charAt(i + 1) <= '9') {
                    iIndexOf = str.indexOf(123, i + 1);
                } else {
                    return false;
                }
            } else {
                return true;
            }
        }
    }

    private static String format(String str, Object[] objArr) {
        if (str == null) {
            return null;
        }
        if (isSimple(str)) {
            String[] strArr = new String[10];
            int i = 0;
            String string = "";
            int iIndexOf = str.indexOf(123);
            while (true) {
                int i2 = iIndexOf;
                if (i2 >= 0) {
                    if (i2 + 2 < str.length() && str.charAt(i2 + 2) == '}' && str.charAt(i2 + 1) >= '0' && str.charAt(i2 + 1) <= '9') {
                        int iCharAt = str.charAt(i2 + 1) - '0';
                        String string2 = new StringBuffer().append(string).append(str.substring(i, i2)).toString();
                        if (strArr[iCharAt] == null) {
                            if (objArr == null || iCharAt >= objArr.length) {
                                strArr[iCharAt] = str.substring(i2, i2 + 3);
                            } else {
                                strArr[iCharAt] = formatObject(objArr[iCharAt]);
                            }
                        }
                        string = new StringBuffer().append(string2).append(strArr[iCharAt]).toString();
                        i = i2 + 3;
                        iIndexOf = str.indexOf(123, i);
                    } else {
                        iIndexOf = str.indexOf(123, i2 + 1);
                    }
                } else {
                    return new StringBuffer().append(string).append(str.substring(i)).toString();
                }
            }
        } else {
            try {
                return MessageFormat.format(str, objArr);
            } catch (IllegalArgumentException unused) {
                return str;
            }
        }
    }

    private static String format(String str, Object obj) {
        String string;
        if (str == null) {
            return null;
        }
        if (isSimple(str)) {
            String object = null;
            int i = 0;
            String str2 = "";
            int iIndexOf = str.indexOf(123);
            while (true) {
                int i2 = iIndexOf;
                if (i2 >= 0) {
                    if (i2 + 2 < str.length() && str.charAt(i2 + 2) == '}' && str.charAt(i2 + 1) >= '0' && str.charAt(i2 + 1) <= '9') {
                        int iCharAt = str.charAt(i2 + 1) - '0';
                        String string2 = new StringBuffer().append(str2).append(str.substring(i, i2)).toString();
                        if (iCharAt != 0) {
                            string = new StringBuffer().append(string2).append(str.substring(i2, i2 + 3)).toString();
                        } else {
                            if (object == null) {
                                object = formatObject(obj);
                            }
                            string = new StringBuffer().append(string2).append(object).toString();
                        }
                        str2 = string;
                        i = i2 + 3;
                        iIndexOf = str.indexOf(123, i);
                    } else {
                        iIndexOf = str.indexOf(123, i2 + 1);
                    }
                } else {
                    return new StringBuffer().append(str2).append(str.substring(i)).toString();
                }
            }
        } else {
            try {
                return MessageFormat.format(str, obj);
            } catch (IllegalArgumentException unused) {
                return str;
            }
        }
    }

    private static String format(String str, String str2, Object[] objArr) {
        String string;
        if (str != null) {
            try {
                string = ResourceBundle.getBundle(str).getString(str2);
            } catch (Exception unused) {
                string = str2;
            }
        } else {
            string = str2;
        }
        return format(string, objArr);
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    private static void forcedLog(Logger logger, Level level, String str) {
        logger.callAppenders(new LoggingEvent(FQCN, logger, level, str, null));
    }

    private static void forcedLog(Logger logger, Level level, String str, Throwable th) {
        logger.callAppenders(new LoggingEvent(FQCN, logger, level, str, th));
    }

    public static void trace(Logger logger, String str, Object[] objArr) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(str, objArr));
        }
    }

    public static void debug(Logger logger, String str, Object[] objArr) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(str, objArr));
        }
    }

    public static void info(Logger logger, String str, Object[] objArr) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(str, objArr));
        }
    }

    public static void warn(Logger logger, String str, Object[] objArr) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(str, objArr));
        }
    }

    public static void error(Logger logger, String str, Object[] objArr) {
        if (logger.isEnabledFor(Level.ERROR)) {
            forcedLog(logger, Level.ERROR, format(str, objArr));
        }
    }

    public static void fatal(Logger logger, String str, Object[] objArr) {
        if (logger.isEnabledFor(Level.FATAL)) {
            forcedLog(logger, Level.FATAL, format(str, objArr));
        }
    }

    public static void trace(Logger logger, Throwable th, String str, Object[] objArr) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(str, objArr), th);
        }
    }

    public static void debug(Logger logger, Throwable th, String str, Object[] objArr) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(str, objArr), th);
        }
    }

    public static void info(Logger logger, Throwable th, String str, Object[] objArr) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(str, objArr), th);
        }
    }

    public static void warn(Logger logger, Throwable th, String str, Object[] objArr) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(str, objArr), th);
        }
    }

    public static void error(Logger logger, Throwable th, String str, Object[] objArr) {
        if (logger.isEnabledFor(Level.ERROR)) {
            forcedLog(logger, Level.ERROR, format(str, objArr), th);
        }
    }

    public static void fatal(Logger logger, Throwable th, String str, Object[] objArr) {
        if (logger.isEnabledFor(Level.FATAL)) {
            forcedLog(logger, Level.FATAL, format(str, objArr), th);
        }
    }

    public static void trace(Logger logger, String str, boolean z) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(str, valueOf(z)));
        }
    }

    public static void trace(Logger logger, String str, char c) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(str, valueOf(c)));
        }
    }

    public static void trace(Logger logger, String str, byte b) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(str, valueOf(b)));
        }
    }

    public static void trace(Logger logger, String str, short s) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(str, valueOf(s)));
        }
    }

    public static void trace(Logger logger, String str, int i) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(str, valueOf(i)));
        }
    }

    public static void trace(Logger logger, String str, long j) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(str, valueOf(j)));
        }
    }

    public static void trace(Logger logger, String str, float f) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(str, valueOf(f)));
        }
    }

    public static void trace(Logger logger, String str, double d) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(str, valueOf(d)));
        }
    }

    public static void trace(Logger logger, String str, Object obj) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(str, obj));
        }
    }

    public static void trace(Logger logger, String str, Object obj, Object obj2) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(str, toArray(obj, obj2)));
        }
    }

    public static void trace(Logger logger, String str, Object obj, Object obj2, Object obj3) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(str, toArray(obj, obj2, obj3)));
        }
    }

    public static void trace(Logger logger, String str, Object obj, Object obj2, Object obj3, Object obj4) {
        if (logger.isEnabledFor(TRACE)) {
            forcedLog(logger, TRACE, format(str, toArray(obj, obj2, obj3, obj4)));
        }
    }

    public static void debug(Logger logger, String str, boolean z) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(str, valueOf(z)));
        }
    }

    public static void debug(Logger logger, String str, char c) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(str, valueOf(c)));
        }
    }

    public static void debug(Logger logger, String str, byte b) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(str, valueOf(b)));
        }
    }

    public static void debug(Logger logger, String str, short s) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(str, valueOf(s)));
        }
    }

    public static void debug(Logger logger, String str, int i) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(str, valueOf(i)));
        }
    }

    public static void debug(Logger logger, String str, long j) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(str, valueOf(j)));
        }
    }

    public static void debug(Logger logger, String str, float f) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(str, valueOf(f)));
        }
    }

    public static void debug(Logger logger, String str, double d) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(str, valueOf(d)));
        }
    }

    public static void debug(Logger logger, String str, Object obj) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(str, obj));
        }
    }

    public static void debug(Logger logger, String str, Object obj, Object obj2) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(str, toArray(obj, obj2)));
        }
    }

    public static void debug(Logger logger, String str, Object obj, Object obj2, Object obj3) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(str, toArray(obj, obj2, obj3)));
        }
    }

    public static void debug(Logger logger, String str, Object obj, Object obj2, Object obj3, Object obj4) {
        if (logger.isDebugEnabled()) {
            forcedLog(logger, Level.DEBUG, format(str, toArray(obj, obj2, obj3, obj4)));
        }
    }

    public static void info(Logger logger, String str, boolean z) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(str, valueOf(z)));
        }
    }

    public static void info(Logger logger, String str, char c) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(str, valueOf(c)));
        }
    }

    public static void info(Logger logger, String str, byte b) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(str, valueOf(b)));
        }
    }

    public static void info(Logger logger, String str, short s) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(str, valueOf(s)));
        }
    }

    public static void info(Logger logger, String str, int i) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(str, valueOf(i)));
        }
    }

    public static void info(Logger logger, String str, long j) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(str, valueOf(j)));
        }
    }

    public static void info(Logger logger, String str, float f) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(str, valueOf(f)));
        }
    }

    public static void info(Logger logger, String str, double d) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(str, valueOf(d)));
        }
    }

    public static void info(Logger logger, String str, Object obj) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(str, obj));
        }
    }

    public static void info(Logger logger, String str, Object obj, Object obj2) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(str, toArray(obj, obj2)));
        }
    }

    public static void info(Logger logger, String str, Object obj, Object obj2, Object obj3) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(str, toArray(obj, obj2, obj3)));
        }
    }

    public static void info(Logger logger, String str, Object obj, Object obj2, Object obj3, Object obj4) {
        if (logger.isInfoEnabled()) {
            forcedLog(logger, Level.INFO, format(str, toArray(obj, obj2, obj3, obj4)));
        }
    }

    public static void warn(Logger logger, String str, boolean z) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(str, valueOf(z)));
        }
    }

    public static void warn(Logger logger, String str, char c) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(str, valueOf(c)));
        }
    }

    public static void warn(Logger logger, String str, byte b) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(str, valueOf(b)));
        }
    }

    public static void warn(Logger logger, String str, short s) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(str, valueOf(s)));
        }
    }

    public static void warn(Logger logger, String str, int i) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(str, valueOf(i)));
        }
    }

    public static void warn(Logger logger, String str, long j) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(str, valueOf(j)));
        }
    }

    public static void warn(Logger logger, String str, float f) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(str, valueOf(f)));
        }
    }

    public static void warn(Logger logger, String str, double d) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(str, valueOf(d)));
        }
    }

    public static void warn(Logger logger, String str, Object obj) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(str, obj));
        }
    }

    public static void warn(Logger logger, String str, Object obj, Object obj2) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(str, toArray(obj, obj2)));
        }
    }

    public static void warn(Logger logger, String str, Object obj, Object obj2, Object obj3) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(str, toArray(obj, obj2, obj3)));
        }
    }

    public static void warn(Logger logger, String str, Object obj, Object obj2, Object obj3, Object obj4) {
        if (logger.isEnabledFor(Level.WARN)) {
            forcedLog(logger, Level.WARN, format(str, toArray(obj, obj2, obj3, obj4)));
        }
    }

    public static void log(Logger logger, Level level, String str, Object[] objArr) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, objArr));
        }
    }

    public static void log(Logger logger, Level level, Throwable th, String str, Object[] objArr) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, objArr), th);
        }
    }

    public static void log(Logger logger, Level level, String str, Object obj) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, toArray(obj)));
        }
    }

    public static void log(Logger logger, Level level, String str, boolean z) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, toArray(valueOf(z))));
        }
    }

    public static void log(Logger logger, Level level, String str, byte b) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, toArray(valueOf(b))));
        }
    }

    public static void log(Logger logger, Level level, String str, char c) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, toArray(valueOf(c))));
        }
    }

    public static void log(Logger logger, Level level, String str, short s) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, toArray(valueOf(s))));
        }
    }

    public static void log(Logger logger, Level level, String str, int i) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, toArray(valueOf(i))));
        }
    }

    public static void log(Logger logger, Level level, String str, long j) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, toArray(valueOf(j))));
        }
    }

    public static void log(Logger logger, Level level, String str, float f) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, toArray(valueOf(f))));
        }
    }

    public static void log(Logger logger, Level level, String str, double d) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, toArray(valueOf(d))));
        }
    }

    public static void log(Logger logger, Level level, String str, Object obj, Object obj2) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, toArray(obj, obj2)));
        }
    }

    public static void log(Logger logger, Level level, String str, Object obj, Object obj2, Object obj3) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, toArray(obj, obj2, obj3)));
        }
    }

    public static void log(Logger logger, Level level, String str, Object obj, Object obj2, Object obj3, Object obj4) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, toArray(obj, obj2, obj3, obj4)));
        }
    }

    public static void logrb(Logger logger, Level level, String str, String str2, Object[] objArr) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, str2, objArr));
        }
    }

    public static void logrb(Logger logger, Level level, Throwable th, String str, String str2, Object[] objArr) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, str2, objArr), th);
        }
    }

    public static void logrb(Logger logger, Level level, String str, String str2, Object obj) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, str2, toArray(obj)));
        }
    }

    public static void logrb(Logger logger, Level level, String str, String str2, boolean z) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, str2, toArray(valueOf(z))));
        }
    }

    public static void logrb(Logger logger, Level level, String str, String str2, char c) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, str2, toArray(valueOf(c))));
        }
    }

    public static void logrb(Logger logger, Level level, String str, String str2, byte b) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, str2, toArray(valueOf(b))));
        }
    }

    public static void logrb(Logger logger, Level level, String str, String str2, short s) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, str2, toArray(valueOf(s))));
        }
    }

    public static void logrb(Logger logger, Level level, String str, String str2, int i) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, str2, toArray(valueOf(i))));
        }
    }

    public static void logrb(Logger logger, Level level, String str, String str2, long j) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, str2, toArray(valueOf(j))));
        }
    }

    public static void logrb(Logger logger, Level level, String str, String str2, float f) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, str2, toArray(valueOf(f))));
        }
    }

    public static void logrb(Logger logger, Level level, String str, String str2, double d) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, str2, toArray(valueOf(d))));
        }
    }

    public static void logrb(Logger logger, Level level, String str, String str2, Object obj, Object obj2) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, str2, toArray(obj, obj2)));
        }
    }

    public static void logrb(Logger logger, Level level, String str, String str2, Object obj, Object obj2, Object obj3) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, str2, toArray(obj, obj2, obj3)));
        }
    }

    public static void logrb(Logger logger, Level level, String str, String str2, Object obj, Object obj2, Object obj3, Object obj4) {
        if (logger.isEnabledFor(level)) {
            forcedLog(logger, level, format(str, str2, toArray(obj, obj2, obj3, obj4)));
        }
    }
}
