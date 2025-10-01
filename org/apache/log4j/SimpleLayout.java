package org.apache.log4j;

import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/SimpleLayout.class */
public class SimpleLayout extends Layout {
    StringBuffer sbuf = new StringBuffer(128);

    @Override // org.apache.log4j.Layout
    public String format(LoggingEvent loggingEvent) {
        this.sbuf.setLength(0);
        this.sbuf.append(loggingEvent.getLevel().toString());
        this.sbuf.append(" - ");
        this.sbuf.append(loggingEvent.getRenderedMessage());
        this.sbuf.append(LINE_SEP);
        return this.sbuf.toString();
    }
}
