package org.apache.log4j.helpers;

import java.io.InputStream;
import java.io.InterruptedIOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.LoggerRepository;

/* loaded from: L-out.jar:org/apache/log4j/helpers/OptionConverter.class */
public class OptionConverter {
    static Class class$java$lang$String;
    static Class class$org$apache$log4j$Level;
    static Class class$org$apache$log4j$spi$Configurator;

    private OptionConverter() {
    }

    public static String[] concatanateArrays(String[] strArr, String[] strArr2) {
        String[] strArr3 = new String[strArr.length + strArr2.length];
        System.arraycopy(strArr, 0, strArr3, 0, strArr.length);
        System.arraycopy(strArr2, 0, strArr3, strArr.length, strArr2.length);
        return strArr3;
    }

    public static String convertSpecialChars(String str) {
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length);
        int i = 0;
        while (i < length) {
            int i2 = i;
            i++;
            char cCharAt = str.charAt(i2);
            if (cCharAt == '\\') {
                i++;
                cCharAt = str.charAt(i);
                if (cCharAt == 'n') {
                    cCharAt = '\n';
                } else if (cCharAt == 'r') {
                    cCharAt = '\r';
                } else if (cCharAt == 't') {
                    cCharAt = '\t';
                } else if (cCharAt == 'f') {
                    cCharAt = '\f';
                } else if (cCharAt == '\b') {
                    cCharAt = '\b';
                } else if (cCharAt == '\"') {
                    cCharAt = '\"';
                } else if (cCharAt == '\'') {
                    cCharAt = '\'';
                } else if (cCharAt == '\\') {
                    cCharAt = '\\';
                }
            }
            stringBuffer.append(cCharAt);
        }
        return stringBuffer.toString();
    }

    public static String getSystemProperty(String str, String str2) {
        try {
            return System.getProperty(str, str2);
        } catch (Throwable unused) {
            LogLog.debug(new StringBuffer().append("Was not allowed to read system property \"").append(str).append("\".").toString());
            return str2;
        }
    }

    public static Object instantiateByKey(Properties properties, String str, Class cls, Object obj) {
        String strFindAndSubst = findAndSubst(str, properties);
        if (strFindAndSubst == null) {
            LogLog.error(new StringBuffer().append("Could not find value for key ").append(str).toString());
            return obj;
        }
        return instantiateByClassName(strFindAndSubst.trim(), cls, obj);
    }

    public static boolean toBoolean(String str, boolean z) {
        if (str == null) {
            return z;
        }
        String strTrim = str.trim();
        if ("true".equalsIgnoreCase(strTrim)) {
            return true;
        }
        if ("false".equalsIgnoreCase(strTrim)) {
            return false;
        }
        return z;
    }

    public static int toInt(String str, int i) {
        if (str != null) {
            String strTrim = str.trim();
            try {
                return Integer.valueOf(strTrim).intValue();
            } catch (NumberFormatException e) {
                LogLog.error(new StringBuffer().append("[").append(strTrim).append("] is not in proper int form.").toString());
                e.printStackTrace();
            }
        }
        return i;
    }

    public static Level toLevel(String str, Level level) throws Throwable {
        Class<?> clsClass$;
        Class<?> clsClass$2;
        if (str == null) {
            return level;
        }
        String strTrim = str.trim();
        int iIndexOf = strTrim.indexOf(35);
        if (iIndexOf == -1) {
            if (DateLayout.NULL_DATE_FORMAT.equalsIgnoreCase(strTrim)) {
                return null;
            }
            return Level.toLevel(strTrim, level);
        }
        Level level2 = level;
        String strSubstring = strTrim.substring(iIndexOf + 1);
        String strSubstring2 = strTrim.substring(0, iIndexOf);
        if (DateLayout.NULL_DATE_FORMAT.equalsIgnoreCase(strSubstring2)) {
            return null;
        }
        LogLog.debug(new StringBuffer().append("toLevel:class=[").append(strSubstring).append("]").append(":pri=[").append(strSubstring2).append("]").toString());
        try {
            Class clsLoadClass = Loader.loadClass(strSubstring);
            Class<?>[] clsArr = new Class[2];
            if (class$java$lang$String == null) {
                clsClass$ = class$("java.lang.String");
                class$java$lang$String = clsClass$;
            } else {
                clsClass$ = class$java$lang$String;
            }
            clsArr[0] = clsClass$;
            if (class$org$apache$log4j$Level == null) {
                clsClass$2 = class$("org.apache.log4j.Level");
                class$org$apache$log4j$Level = clsClass$2;
            } else {
                clsClass$2 = class$org$apache$log4j$Level;
            }
            clsArr[1] = clsClass$2;
            level2 = (Level) clsLoadClass.getMethod("toLevel", clsArr).invoke(null, strSubstring2, level);
        } catch (ClassCastException e) {
            LogLog.warn(new StringBuffer().append("class [").append(strSubstring).append("] is not a subclass of org.apache.log4j.Level").toString(), e);
        } catch (ClassNotFoundException unused) {
            LogLog.warn(new StringBuffer().append("custom level class [").append(strSubstring).append("] not found.").toString());
        } catch (IllegalAccessException e2) {
            LogLog.warn(new StringBuffer().append("class [").append(strSubstring).append("] cannot be instantiated due to access restrictions").toString(), e2);
        } catch (NoSuchMethodException e3) {
            LogLog.warn(new StringBuffer().append("custom level class [").append(strSubstring).append("]").append(" does not have a class function toLevel(String, Level)").toString(), e3);
        } catch (RuntimeException e4) {
            LogLog.warn(new StringBuffer().append("class [").append(strSubstring).append("], level [").append(strSubstring2).append("] conversion failed.").toString(), e4);
        } catch (InvocationTargetException e5) {
            if ((e5.getTargetException() instanceof InterruptedException) || (e5.getTargetException() instanceof InterruptedIOException)) {
                Thread.currentThread().interrupt();
            }
            LogLog.warn(new StringBuffer().append("custom level class [").append(strSubstring).append("]").append(" could not be instantiated").toString(), e5);
        }
        return level2;
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public static long toFileSize(String str, long j) {
        if (str == null) {
            return j;
        }
        String upperCase = str.trim().toUpperCase();
        long j2 = 1;
        int iIndexOf = upperCase.indexOf("KB");
        if (iIndexOf != -1) {
            j2 = 1024;
            upperCase = upperCase.substring(0, iIndexOf);
        } else {
            int iIndexOf2 = upperCase.indexOf("MB");
            if (iIndexOf2 != -1) {
                j2 = 1048576;
                upperCase = upperCase.substring(0, iIndexOf2);
            } else {
                int iIndexOf3 = upperCase.indexOf("GB");
                if (iIndexOf3 != -1) {
                    j2 = 1073741824;
                    upperCase = upperCase.substring(0, iIndexOf3);
                }
            }
        }
        if (upperCase != null) {
            try {
                return Long.valueOf(upperCase).longValue() * j2;
            } catch (NumberFormatException e) {
                LogLog.error(new StringBuffer().append("[").append(upperCase).append("] is not in proper int form.").toString());
                LogLog.error(new StringBuffer().append("[").append(str).append("] not in expected format.").toString(), e);
            }
        }
        return j;
    }

    public static String findAndSubst(String str, Properties properties) {
        String property = properties.getProperty(str);
        if (property == null) {
            return null;
        }
        try {
            return substVars(property, properties);
        } catch (IllegalArgumentException e) {
            LogLog.error(new StringBuffer().append("Bad option value [").append(property).append("].").toString(), e);
            return property;
        }
    }

    public static Object instantiateByClassName(String str, Class cls, Object obj) {
        if (str != null) {
            try {
                Class<?> clsLoadClass = Loader.loadClass(str);
                if (!cls.isAssignableFrom(clsLoadClass)) {
                    LogLog.error(new StringBuffer().append("A \"").append(str).append("\" object is not assignable to a \"").append(cls.getName()).append("\" variable.").toString());
                    LogLog.error(new StringBuffer().append("The class \"").append(cls.getName()).append("\" was loaded by ").toString());
                    LogLog.error(new StringBuffer().append("[").append(cls.getClassLoader()).append("] whereas object of type ").toString());
                    LogLog.error(new StringBuffer().append("\"").append(clsLoadClass.getName()).append("\" was loaded by [").append(clsLoadClass.getClassLoader()).append("].").toString());
                    return obj;
                }
                return clsLoadClass.newInstance();
            } catch (ClassNotFoundException e) {
                LogLog.error(new StringBuffer().append("Could not instantiate class [").append(str).append("].").toString(), e);
            } catch (IllegalAccessException e2) {
                LogLog.error(new StringBuffer().append("Could not instantiate class [").append(str).append("].").toString(), e2);
            } catch (InstantiationException e3) {
                LogLog.error(new StringBuffer().append("Could not instantiate class [").append(str).append("].").toString(), e3);
            } catch (RuntimeException e4) {
                LogLog.error(new StringBuffer().append("Could not instantiate class [").append(str).append("].").toString(), e4);
            }
        }
        return obj;
    }

    public static String substVars(String str, Properties properties) {
        StringBuffer stringBuffer = new StringBuffer();
        int i = 0;
        while (true) {
            int i2 = i;
            int iIndexOf = str.indexOf("${", i2);
            if (iIndexOf == -1) {
                if (i2 == 0) {
                    return str;
                }
                stringBuffer.append(str.substring(i2, str.length()));
                return stringBuffer.toString();
            }
            stringBuffer.append(str.substring(i2, iIndexOf));
            int iIndexOf2 = str.indexOf(125, iIndexOf);
            if (iIndexOf2 == -1) {
                throw new IllegalArgumentException(new StringBuffer().append('\"').append(str).append("\" has no closing brace. Opening brace at position ").append(iIndexOf).append('.').toString());
            }
            String strSubstring = str.substring(iIndexOf + 2, iIndexOf2);
            String systemProperty = getSystemProperty(strSubstring, null);
            if (systemProperty == null && properties != null) {
                systemProperty = properties.getProperty(strSubstring);
            }
            if (systemProperty != null) {
                stringBuffer.append(substVars(systemProperty, properties));
            }
            i = iIndexOf2 + 1;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v9, types: [org.apache.log4j.spi.Configurator] */
    public static void selectAndConfigure(InputStream inputStream, String str, LoggerRepository loggerRepository) throws Throwable {
        PropertyConfigurator propertyConfigurator;
        Class clsClass$;
        if (str != null) {
            LogLog.debug(new StringBuffer().append("Preferred configurator class: ").append(str).toString());
            if (class$org$apache$log4j$spi$Configurator == null) {
                clsClass$ = class$("org.apache.log4j.spi.Configurator");
                class$org$apache$log4j$spi$Configurator = clsClass$;
            } else {
                clsClass$ = class$org$apache$log4j$spi$Configurator;
            }
            propertyConfigurator = (Configurator) instantiateByClassName(str, clsClass$, null);
            if (propertyConfigurator == null) {
                LogLog.error(new StringBuffer().append("Could not instantiate configurator [").append(str).append("].").toString());
                return;
            }
        } else {
            propertyConfigurator = new PropertyConfigurator();
        }
        propertyConfigurator.doConfigure(inputStream, loggerRepository);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v12, types: [org.apache.log4j.spi.Configurator] */
    public static void selectAndConfigure(URL url, String str, LoggerRepository loggerRepository) throws Throwable {
        PropertyConfigurator propertyConfigurator;
        Class clsClass$;
        String file = url.getFile();
        if (str == null && file != null && file.endsWith(".xml")) {
            str = "org.apache.log4j.xml.DOMConfigurator";
        }
        if (str != null) {
            LogLog.debug(new StringBuffer().append("Preferred configurator class: ").append(str).toString());
            String str2 = str;
            if (class$org$apache$log4j$spi$Configurator == null) {
                clsClass$ = class$("org.apache.log4j.spi.Configurator");
                class$org$apache$log4j$spi$Configurator = clsClass$;
            } else {
                clsClass$ = class$org$apache$log4j$spi$Configurator;
            }
            propertyConfigurator = (Configurator) instantiateByClassName(str2, clsClass$, null);
            if (propertyConfigurator == null) {
                LogLog.error(new StringBuffer().append("Could not instantiate configurator [").append(str).append("].").toString());
                return;
            }
        } else {
            propertyConfigurator = new PropertyConfigurator();
        }
        propertyConfigurator.doConfigure(url, loggerRepository);
    }
}
