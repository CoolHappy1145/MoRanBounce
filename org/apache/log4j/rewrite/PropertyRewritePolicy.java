package org.apache.log4j.rewrite;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/rewrite/PropertyRewritePolicy.class */
public class PropertyRewritePolicy implements RewritePolicy {
    private Map properties = Collections.EMPTY_MAP;

    public void setProperties(String str) {
        HashMap map = new HashMap();
        StringTokenizer stringTokenizer = new StringTokenizer(str, ",");
        while (stringTokenizer.hasMoreTokens()) {
            StringTokenizer stringTokenizer2 = new StringTokenizer(stringTokenizer.nextToken(), "=");
            map.put(stringTokenizer2.nextElement().toString().trim(), stringTokenizer2.nextElement().toString().trim());
        }
        synchronized (this) {
            this.properties = map;
        }
    }

    @Override // org.apache.log4j.rewrite.RewritePolicy
    public LoggingEvent rewrite(LoggingEvent loggingEvent) {
        if (!this.properties.isEmpty()) {
            HashMap map = new HashMap(loggingEvent.getProperties());
            for (Map.Entry entry : this.properties.entrySet()) {
                if (!map.containsKey(entry.getKey())) {
                    map.put(entry.getKey(), entry.getValue());
                }
            }
            return new LoggingEvent(loggingEvent.getFQNOfLoggerClass(), loggingEvent.getLogger() != null ? loggingEvent.getLogger() : Logger.getLogger(loggingEvent.getLoggerName()), loggingEvent.getTimeStamp(), loggingEvent.getLevel(), loggingEvent.getMessage(), loggingEvent.getThreadName(), loggingEvent.getThrowableInformation(), loggingEvent.getNDC(), loggingEvent.getLocationInformation(), map);
        }
        return loggingEvent;
    }
}
