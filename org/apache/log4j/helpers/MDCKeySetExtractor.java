package org.apache.log4j.helpers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.util.Set;
import org.apache.log4j.pattern.LogEvent;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/helpers/MDCKeySetExtractor.class */
public final class MDCKeySetExtractor {
    private final Method getKeySetMethod;
    public static final MDCKeySetExtractor INSTANCE = new MDCKeySetExtractor();
    static Class class$org$apache$log4j$spi$LoggingEvent;
    static Class class$org$apache$log4j$pattern$LogEvent;

    private MDCKeySetExtractor() throws Throwable {
        Method method;
        Class clsClass$;
        try {
            if (class$org$apache$log4j$spi$LoggingEvent == null) {
                clsClass$ = class$("org.apache.log4j.spi.LoggingEvent");
                class$org$apache$log4j$spi$LoggingEvent = clsClass$;
            } else {
                clsClass$ = class$org$apache$log4j$spi$LoggingEvent;
            }
            method = clsClass$.getMethod("getPropertyKeySet", null);
        } catch (Exception unused) {
            method = null;
        }
        this.getKeySetMethod = method;
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public Set getPropertyKeySet(LoggingEvent loggingEvent) throws Throwable {
        Class clsClass$;
        Set propertyKeySet = null;
        if (this.getKeySetMethod != null) {
            propertyKeySet = (Set) this.getKeySetMethod.invoke(loggingEvent, null);
        } else {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(loggingEvent);
            objectOutputStream.close();
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            if (class$org$apache$log4j$pattern$LogEvent == null) {
                clsClass$ = class$("org.apache.log4j.pattern.LogEvent");
                class$org$apache$log4j$pattern$LogEvent = clsClass$;
            } else {
                clsClass$ = class$org$apache$log4j$pattern$LogEvent;
            }
            String name = clsClass$.getName();
            if (byteArray[6] == 0 || byteArray[7] == name.length()) {
                for (int i = 0; i < name.length(); i++) {
                    byteArray[8 + i] = (byte) name.charAt(i);
                }
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(byteArray));
                Object object = objectInputStream.readObject();
                if (object instanceof LogEvent) {
                    propertyKeySet = ((LogEvent) object).getPropertyKeySet();
                }
                objectInputStream.close();
            }
        }
        return propertyKeySet;
    }
}
