package org.apache.log4j.config;

import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;
import java.util.Hashtable;
import org.apache.log4j.Appender;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.config.PropertyGetter;

/* loaded from: L-out.jar:org/apache/log4j/config/PropertyPrinter.class */
public class PropertyPrinter implements PropertyGetter.PropertyCallback {
    protected int numAppenders;
    protected Hashtable appenderNames;
    protected Hashtable layoutNames;
    protected PrintWriter out;
    protected boolean doCapitalize;

    public PropertyPrinter(PrintWriter printWriter) {
        this(printWriter, false);
    }

    public PropertyPrinter(PrintWriter printWriter, boolean z) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        this.numAppenders = 0;
        this.appenderNames = new Hashtable();
        this.layoutNames = new Hashtable();
        this.out = printWriter;
        this.doCapitalize = z;
        print(printWriter);
        printWriter.flush();
    }

    protected String genAppName() {
        StringBuffer stringBufferAppend = new StringBuffer().append("A");
        int i = this.numAppenders;
        this.numAppenders = i + 1;
        return stringBufferAppend.append(i).toString();
    }

    protected boolean isGenAppName(String str) {
        if (str.length() < 2 || str.charAt(0) != 'A') {
            return false;
        }
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    public void print(PrintWriter printWriter) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        printOptions(printWriter, Logger.getRootLogger());
        Enumeration currentLoggers = LogManager.getCurrentLoggers();
        while (currentLoggers.hasMoreElements()) {
            printOptions(printWriter, (Logger) currentLoggers.nextElement());
        }
    }

    protected void printOptions(PrintWriter printWriter, Category category) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String str;
        Enumeration allAppenders = category.getAllAppenders();
        Level level = category.getLevel();
        String string = level == null ? "" : level.toString();
        while (true) {
            str = string;
            if (!allAppenders.hasMoreElements()) {
                break;
            }
            Appender appender = (Appender) allAppenders.nextElement();
            String str2 = (String) this.appenderNames.get(appender);
            String strGenAppName = str2;
            if (str2 == null) {
                String name = appender.getName();
                strGenAppName = name;
                if (name == null || isGenAppName(strGenAppName)) {
                    strGenAppName = genAppName();
                }
                this.appenderNames.put(appender, strGenAppName);
                printOptions(printWriter, appender, new StringBuffer().append("log4j.appender.").append(strGenAppName).toString());
                if (appender.getLayout() != null) {
                    printOptions(printWriter, appender.getLayout(), new StringBuffer().append("log4j.appender.").append(strGenAppName).append(".layout").toString());
                }
            }
            string = new StringBuffer().append(str).append(", ").append(strGenAppName).toString();
        }
        String string2 = category == Logger.getRootLogger() ? "log4j.rootLogger" : new StringBuffer().append("log4j.logger.").append(category.getName()).toString();
        if (str != "") {
            printWriter.println(new StringBuffer().append(string2).append("=").append(str).toString());
        }
        if (!category.getAdditivity() && category != Logger.getRootLogger()) {
            printWriter.println(new StringBuffer().append("log4j.additivity.").append(category.getName()).append("=false").toString());
        }
    }

    protected void printOptions(PrintWriter printWriter, Logger logger) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        printOptions(printWriter, (Category) logger);
    }

    protected void printOptions(PrintWriter printWriter, Object obj, String str) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        printWriter.println(new StringBuffer().append(str).append("=").append(obj.getClass().getName()).toString());
        PropertyGetter.getProperties(obj, this, new StringBuffer().append(str).append(".").toString());
    }

    @Override // org.apache.log4j.config.PropertyGetter.PropertyCallback
    public void foundProperty(Object obj, String str, String str2, Object obj2) {
        if ((obj instanceof Appender) && "name".equals(str2)) {
            return;
        }
        if (this.doCapitalize) {
            str2 = capitalize(str2);
        }
        this.out.println(new StringBuffer().append(str).append(str2).append("=").append(obj2.toString()).toString());
    }

    public static String capitalize(String str) {
        if (Character.isLowerCase(str.charAt(0)) && (str.length() == 1 || Character.isLowerCase(str.charAt(1)))) {
            StringBuffer stringBuffer = new StringBuffer(str);
            stringBuffer.setCharAt(0, Character.toUpperCase(str.charAt(0)));
            return stringBuffer.toString();
        }
        return str;
    }

    public static void main(String[] strArr) {
        new PropertyPrinter(new PrintWriter(System.out));
    }
}
