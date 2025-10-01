package org.apache.log4j.spi;

import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import jdk.internal.dynalink.CallSiteDescriptor;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.LogLog;

/* loaded from: L-out.jar:org/apache/log4j/spi/LocationInfo.class */
public class LocationInfo implements Serializable {
    String lineNumber;
    String fileName;
    String className;
    String methodName;
    public String fullInfo;
    private static Method getStackTraceMethod;
    private static Method getClassNameMethod;
    private static Method getMethodNameMethod;
    private static Method getFileNameMethod;
    private static Method getLineNumberMethod;
    static final long serialVersionUID = -1325822038990805636L;
    static boolean inVisualAge;
    static Class class$java$lang$Throwable;

    /* renamed from: sw */
    private static StringWriter f202sw = new StringWriter();

    /* renamed from: pw */
    private static PrintWriter f203pw = new PrintWriter(f202sw);

    /* renamed from: NA */
    public static final String f204NA = "?";
    public static final LocationInfo NA_LOCATION_INFO = new LocationInfo(f204NA, f204NA, f204NA, f204NA);

    static {
        Class clsClass$;
        inVisualAge = false;
        try {
            inVisualAge = Class.forName("com.ibm.uvm.tools.DebugSupport") != null;
            LogLog.debug("Detected IBM VisualAge environment.");
        } catch (Throwable unused) {
        }
        try {
            if (class$java$lang$Throwable == null) {
                clsClass$ = class$("java.lang.Throwable");
                class$java$lang$Throwable = clsClass$;
            } else {
                clsClass$ = class$java$lang$Throwable;
            }
            getStackTraceMethod = clsClass$.getMethod("getStackTrace", null);
            Class<?> cls = Class.forName("java.lang.StackTraceElement");
            getClassNameMethod = cls.getMethod("getClassName", null);
            getMethodNameMethod = cls.getMethod("getMethodName", null);
            getFileNameMethod = cls.getMethod("getFileName", null);
            getLineNumberMethod = cls.getMethod("getLineNumber", null);
        } catch (ClassNotFoundException unused2) {
            LogLog.debug("LocationInfo will use pre-JDK 1.4 methods to determine location.");
        } catch (NoSuchMethodException unused3) {
            LogLog.debug("LocationInfo will use pre-JDK 1.4 methods to determine location.");
        }
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public LocationInfo(Throwable th, String str) {
        String string;
        int iLastIndexOf;
        if (th == null || str == null) {
            return;
        }
        if (getLineNumberMethod != null) {
            try {
                Object[] objArr = (Object[]) getStackTraceMethod.invoke(th, null);
                String str2 = f204NA;
                for (int length = objArr.length - 1; length >= 0; length--) {
                    String str3 = (String) getClassNameMethod.invoke(objArr[length], null);
                    if (str.equals(str3)) {
                        int i = length + 1;
                        if (i < objArr.length) {
                            this.className = str2;
                            this.methodName = (String) getMethodNameMethod.invoke(objArr[i], null);
                            this.fileName = (String) getFileNameMethod.invoke(objArr[i], null);
                            if (this.fileName == null) {
                                this.fileName = f204NA;
                            }
                            int iIntValue = ((Integer) getLineNumberMethod.invoke(objArr[i], null)).intValue();
                            if (iIntValue < 0) {
                                this.lineNumber = f204NA;
                            } else {
                                this.lineNumber = String.valueOf(iIntValue);
                            }
                            StringBuffer stringBuffer = new StringBuffer();
                            stringBuffer.append(this.className);
                            stringBuffer.append(".");
                            stringBuffer.append(this.methodName);
                            stringBuffer.append("(");
                            stringBuffer.append(this.fileName);
                            stringBuffer.append(CallSiteDescriptor.TOKEN_DELIMITER);
                            stringBuffer.append(this.lineNumber);
                            stringBuffer.append(")");
                            this.fullInfo = stringBuffer.toString();
                            return;
                        }
                        return;
                    }
                    str2 = str3;
                }
                return;
            } catch (IllegalAccessException e) {
                LogLog.debug("LocationInfo failed using JDK 1.4 methods", e);
            } catch (RuntimeException e2) {
                LogLog.debug("LocationInfo failed using JDK 1.4 methods", e2);
            } catch (InvocationTargetException e3) {
                if ((e3.getTargetException() instanceof InterruptedException) || (e3.getTargetException() instanceof InterruptedIOException)) {
                    Thread.currentThread().interrupt();
                }
                LogLog.debug("LocationInfo failed using JDK 1.4 methods", e3);
            }
        }
        synchronized (f202sw) {
            th.printStackTrace(f203pw);
            string = f202sw.toString();
            f202sw.getBuffer().setLength(0);
        }
        int iLastIndexOf2 = string.lastIndexOf(str);
        if (iLastIndexOf2 == -1) {
            return;
        }
        if (iLastIndexOf2 + str.length() < string.length() && string.charAt(iLastIndexOf2 + str.length()) != '.' && (iLastIndexOf = string.lastIndexOf(new StringBuffer().append(str).append(".").toString())) != -1) {
            iLastIndexOf2 = iLastIndexOf;
        }
        int iIndexOf = string.indexOf(Layout.LINE_SEP, iLastIndexOf2);
        if (iIndexOf == -1) {
            return;
        }
        int i2 = iIndexOf + Layout.LINE_SEP_LEN;
        int iIndexOf2 = string.indexOf(Layout.LINE_SEP, i2);
        if (iIndexOf2 == -1) {
            return;
        }
        if (!inVisualAge) {
            int iLastIndexOf3 = string.lastIndexOf("at ", iIndexOf2);
            if (iLastIndexOf3 == -1) {
                return;
            } else {
                i2 = iLastIndexOf3 + 3;
            }
        }
        this.fullInfo = string.substring(i2, iIndexOf2);
    }

    private static final void appendFragment(StringBuffer stringBuffer, String str) {
        if (str == null) {
            stringBuffer.append(f204NA);
        } else {
            stringBuffer.append(str);
        }
    }

    public LocationInfo(String str, String str2, String str3, String str4) {
        this.fileName = str;
        this.className = str2;
        this.methodName = str3;
        this.lineNumber = str4;
        StringBuffer stringBuffer = new StringBuffer();
        appendFragment(stringBuffer, str2);
        stringBuffer.append(".");
        appendFragment(stringBuffer, str3);
        stringBuffer.append("(");
        appendFragment(stringBuffer, str);
        stringBuffer.append(CallSiteDescriptor.TOKEN_DELIMITER);
        appendFragment(stringBuffer, str4);
        stringBuffer.append(")");
        this.fullInfo = stringBuffer.toString();
    }

    public String getClassName() {
        if (this.fullInfo == null) {
            return f204NA;
        }
        if (this.className == null) {
            int iLastIndexOf = this.fullInfo.lastIndexOf(40);
            if (iLastIndexOf == -1) {
                this.className = f204NA;
            } else {
                int iLastIndexOf2 = this.fullInfo.lastIndexOf(46, iLastIndexOf);
                int iLastIndexOf3 = 0;
                if (inVisualAge) {
                    iLastIndexOf3 = this.fullInfo.lastIndexOf(32, iLastIndexOf2) + 1;
                }
                if (iLastIndexOf2 == -1) {
                    this.className = f204NA;
                } else {
                    this.className = this.fullInfo.substring(iLastIndexOf3, iLastIndexOf2);
                }
            }
        }
        return this.className;
    }

    public String getFileName() {
        if (this.fullInfo == null) {
            return f204NA;
        }
        if (this.fileName == null) {
            int iLastIndexOf = this.fullInfo.lastIndexOf(58);
            if (iLastIndexOf == -1) {
                this.fileName = f204NA;
            } else {
                this.fileName = this.fullInfo.substring(this.fullInfo.lastIndexOf(40, iLastIndexOf - 1) + 1, iLastIndexOf);
            }
        }
        return this.fileName;
    }

    public String getLineNumber() {
        if (this.fullInfo == null) {
            return f204NA;
        }
        if (this.lineNumber == null) {
            int iLastIndexOf = this.fullInfo.lastIndexOf(41);
            int iLastIndexOf2 = this.fullInfo.lastIndexOf(58, iLastIndexOf - 1);
            if (iLastIndexOf2 == -1) {
                this.lineNumber = f204NA;
            } else {
                this.lineNumber = this.fullInfo.substring(iLastIndexOf2 + 1, iLastIndexOf);
            }
        }
        return this.lineNumber;
    }

    public String getMethodName() {
        if (this.fullInfo == null) {
            return f204NA;
        }
        if (this.methodName == null) {
            int iLastIndexOf = this.fullInfo.lastIndexOf(40);
            int iLastIndexOf2 = this.fullInfo.lastIndexOf(46, iLastIndexOf);
            if (iLastIndexOf2 == -1) {
                this.methodName = f204NA;
            } else {
                this.methodName = this.fullInfo.substring(iLastIndexOf2 + 1, iLastIndexOf);
            }
        }
        return this.methodName;
    }
}
