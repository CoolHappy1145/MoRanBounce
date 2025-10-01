package org.apache.log4j.spi;

import java.util.Enumeration;
import java.util.Vector;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/* loaded from: L-out.jar:org/apache/log4j/spi/NOPLogger.class */
public final class NOPLogger extends Logger {
    public NOPLogger(NOPLoggerRepository nOPLoggerRepository, String str) {
        super(str);
        this.repository = nOPLoggerRepository;
        this.level = Level.OFF;
        this.parent = this;
    }

    @Override // org.apache.log4j.Category, org.apache.log4j.spi.AppenderAttachable
    public Enumeration getAllAppenders() {
        return new Vector().elements();
    }

    @Override // org.apache.log4j.Category
    public Level getEffectiveLevel() {
        return Level.OFF;
    }

    @Override // org.apache.log4j.Category
    public Priority getChainedPriority() {
        return getEffectiveLevel();
    }
}
