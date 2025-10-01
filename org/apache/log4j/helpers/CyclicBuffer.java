package org.apache.log4j.helpers;

import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/helpers/CyclicBuffer.class */
public class CyclicBuffer {

    /* renamed from: ea */
    LoggingEvent[] f190ea;
    int first;
    int last;
    int numElems;
    int maxSize;

    public CyclicBuffer(int i) {
        if (i < 1) {
            throw new IllegalArgumentException(new StringBuffer().append("The maxSize argument (").append(i).append(") is not a positive integer.").toString());
        }
        this.maxSize = i;
        this.f190ea = new LoggingEvent[i];
        this.first = 0;
        this.last = 0;
        this.numElems = 0;
    }

    public void add(LoggingEvent loggingEvent) {
        this.f190ea[this.last] = loggingEvent;
        int i = this.last + 1;
        this.last = i;
        if (i == this.maxSize) {
            this.last = 0;
        }
        if (this.numElems < this.maxSize) {
            this.numElems++;
            return;
        }
        int i2 = this.first + 1;
        this.first = i2;
        if (i2 == this.maxSize) {
            this.first = 0;
        }
    }

    public LoggingEvent get(int i) {
        if (i < 0 || i >= this.numElems) {
            return null;
        }
        return this.f190ea[(this.first + i) % this.maxSize];
    }

    public int getMaxSize() {
        return this.maxSize;
    }

    public LoggingEvent get() {
        LoggingEvent loggingEvent = null;
        if (this.numElems > 0) {
            this.numElems--;
            loggingEvent = this.f190ea[this.first];
            this.f190ea[this.first] = null;
            int i = this.first + 1;
            this.first = i;
            if (i == this.maxSize) {
                this.first = 0;
            }
        }
        return loggingEvent;
    }

    public int length() {
        return this.numElems;
    }

    public void resize(int i) {
        if (i < 0) {
            throw new IllegalArgumentException(new StringBuffer().append("Negative array size [").append(i).append("] not allowed.").toString());
        }
        if (i == this.numElems) {
            return;
        }
        LoggingEvent[] loggingEventArr = new LoggingEvent[i];
        int i2 = i < this.numElems ? i : this.numElems;
        for (int i3 = 0; i3 < i2; i3++) {
            loggingEventArr[i3] = this.f190ea[this.first];
            this.f190ea[this.first] = null;
            int i4 = this.first + 1;
            this.first = i4;
            if (i4 == this.numElems) {
                this.first = 0;
            }
        }
        this.f190ea = loggingEventArr;
        this.first = 0;
        this.numElems = i2;
        this.maxSize = i;
        if (i2 == i) {
            this.last = 0;
        } else {
            this.last = i2;
        }
    }
}
