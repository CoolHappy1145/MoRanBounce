package org.apache.log4j.rewrite;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import org.apache.log4j.Logger;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/rewrite/ReflectionRewritePolicy.class */
public class ReflectionRewritePolicy implements RewritePolicy {
    static Class class$java$lang$Object;

    @Override // org.apache.log4j.rewrite.RewritePolicy
    public LoggingEvent rewrite(LoggingEvent loggingEvent) throws Throwable {
        Class clsClass$;
        Object message = loggingEvent.getMessage();
        if (!(message instanceof String)) {
            Object obj = message;
            HashMap map = new HashMap(loggingEvent.getProperties());
            try {
                Class<?> cls = message.getClass();
                if (class$java$lang$Object == null) {
                    clsClass$ = class$("java.lang.Object");
                    class$java$lang$Object = clsClass$;
                } else {
                    clsClass$ = class$java$lang$Object;
                }
                PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(cls, clsClass$).getPropertyDescriptors();
                if (propertyDescriptors.length > 0) {
                    for (int i = 0; i < propertyDescriptors.length; i++) {
                        try {
                            Object objInvoke = propertyDescriptors[i].getReadMethod().invoke(message, (Object[]) null);
                            if ("message".equalsIgnoreCase(propertyDescriptors[i].getName())) {
                                obj = objInvoke;
                            } else {
                                map.put(propertyDescriptors[i].getName(), objInvoke);
                            }
                        } catch (Exception e) {
                            LogLog.warn(new StringBuffer().append("Unable to evaluate property ").append(propertyDescriptors[i].getName()).toString(), e);
                        }
                    }
                    return new LoggingEvent(loggingEvent.getFQNOfLoggerClass(), loggingEvent.getLogger() != null ? loggingEvent.getLogger() : Logger.getLogger(loggingEvent.getLoggerName()), loggingEvent.getTimeStamp(), loggingEvent.getLevel(), obj, loggingEvent.getThreadName(), loggingEvent.getThrowableInformation(), loggingEvent.getNDC(), loggingEvent.getLocationInformation(), map);
                }
            } catch (Exception e2) {
                LogLog.warn("Unable to get property descriptors", e2);
            }
        }
        return loggingEvent;
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }
}
