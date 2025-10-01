package org.apache.log4j.spi;

import java.util.Enumeration;
import java.util.Vector;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/* loaded from: L-out.jar:org/apache/log4j/spi/NOPLoggerRepository.class */
public final class NOPLoggerRepository implements LoggerRepository {
    @Override // org.apache.log4j.spi.LoggerRepository
    public Level getThreshold() {
        return Level.OFF;
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Logger getLogger(String str) {
        return new NOPLogger(this, str);
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Logger getLogger(String str, LoggerFactory loggerFactory) {
        return new NOPLogger(this, str);
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Logger getRootLogger() {
        return new NOPLogger(this, "root");
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Enumeration getCurrentLoggers() {
        return new Vector().elements();
    }

    @Override // org.apache.log4j.spi.LoggerRepository
    public Enumeration getCurrentCategories() {
        return getCurrentLoggers();
    }
}
