package org.apache.log4j.spi;

/* loaded from: L-out.jar:org/apache/log4j/spi/Filter.class */
public abstract class Filter implements OptionHandler {
    public Filter next;
    public static final int DENY = -1;
    public static final int NEUTRAL = 0;
    public static final int ACCEPT = 1;

    public abstract int decide(LoggingEvent loggingEvent);

    public void setNext(Filter filter) {
        this.next = filter;
    }

    public Filter getNext() {
        return this.next;
    }
}
