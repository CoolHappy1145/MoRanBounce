package org.apache.log4j.xml;

import java.util.Arrays;
import java.util.Set;
import org.apache.log4j.Layout;
import org.apache.log4j.helpers.Transform;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;
import org.json.HTTP;

/* loaded from: L-out.jar:org/apache/log4j/xml/XMLLayout.class */
public class XMLLayout extends Layout {
    private final int DEFAULT_SIZE = 256;
    private final int UPPER_LIMIT = 2048;
    private StringBuffer buf = new StringBuffer(256);
    private boolean locationInfo = false;
    private boolean properties = false;

    public void setLocationInfo(boolean z) {
        this.locationInfo = z;
    }

    public boolean getLocationInfo() {
        return this.locationInfo;
    }

    public void setProperties(boolean z) {
        this.properties = z;
    }

    public boolean getProperties() {
        return this.properties;
    }

    @Override // org.apache.log4j.Layout
    public String format(LoggingEvent loggingEvent) {
        if (this.buf.capacity() > 2048) {
            this.buf = new StringBuffer(256);
        } else {
            this.buf.setLength(0);
        }
        this.buf.append("<log4j:event logger=\"");
        this.buf.append(Transform.escapeTags(loggingEvent.getLoggerName()));
        this.buf.append("\" timestamp=\"");
        this.buf.append(loggingEvent.timeStamp);
        this.buf.append("\" level=\"");
        this.buf.append(Transform.escapeTags(String.valueOf(loggingEvent.getLevel())));
        this.buf.append("\" thread=\"");
        this.buf.append(Transform.escapeTags(loggingEvent.getThreadName()));
        this.buf.append("\">\r\n");
        this.buf.append("<log4j:message><![CDATA[");
        Transform.appendEscapingCDATA(this.buf, loggingEvent.getRenderedMessage());
        this.buf.append("]]></log4j:message>\r\n");
        String ndc = loggingEvent.getNDC();
        if (ndc != null) {
            this.buf.append("<log4j:NDC><![CDATA[");
            Transform.appendEscapingCDATA(this.buf, ndc);
            this.buf.append("]]></log4j:NDC>\r\n");
        }
        String[] throwableStrRep = loggingEvent.getThrowableStrRep();
        if (throwableStrRep != null) {
            this.buf.append("<log4j:throwable><![CDATA[");
            for (String str : throwableStrRep) {
                Transform.appendEscapingCDATA(this.buf, str);
                this.buf.append(HTTP.CRLF);
            }
            this.buf.append("]]></log4j:throwable>\r\n");
        }
        if (this.locationInfo) {
            LocationInfo locationInformation = loggingEvent.getLocationInformation();
            this.buf.append("<log4j:locationInfo class=\"");
            this.buf.append(Transform.escapeTags(locationInformation.getClassName()));
            this.buf.append("\" method=\"");
            this.buf.append(Transform.escapeTags(locationInformation.getMethodName()));
            this.buf.append("\" file=\"");
            this.buf.append(Transform.escapeTags(locationInformation.getFileName()));
            this.buf.append("\" line=\"");
            this.buf.append(locationInformation.getLineNumber());
            this.buf.append("\"/>\r\n");
        }
        if (this.properties) {
            Set propertyKeySet = loggingEvent.getPropertyKeySet();
            if (propertyKeySet.size() > 0) {
                this.buf.append("<log4j:properties>\r\n");
                Object[] array = propertyKeySet.toArray();
                Arrays.sort(array);
                for (Object obj : array) {
                    String string = obj.toString();
                    Object mdc = loggingEvent.getMDC(string);
                    if (mdc != null) {
                        this.buf.append("<log4j:data name=\"");
                        this.buf.append(Transform.escapeTags(string));
                        this.buf.append("\" value=\"");
                        this.buf.append(Transform.escapeTags(String.valueOf(mdc)));
                        this.buf.append("\"/>\r\n");
                    }
                }
                this.buf.append("</log4j:properties>\r\n");
            }
        }
        this.buf.append("</log4j:event>\r\n\r\n");
        return this.buf.toString();
    }
}
