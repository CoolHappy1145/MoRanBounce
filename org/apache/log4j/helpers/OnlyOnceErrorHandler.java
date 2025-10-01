package org.apache.log4j.helpers;

import java.io.InterruptedIOException;
import org.apache.log4j.spi.ErrorHandler;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/helpers/OnlyOnceErrorHandler.class */
public class OnlyOnceErrorHandler implements ErrorHandler {
    final String WARN_PREFIX = "log4j warning: ";
    final String ERROR_PREFIX = "log4j error: ";
    boolean firstTime = true;

    @Override // org.apache.log4j.spi.ErrorHandler
    public void error(String str, Exception exc, int i) {
        error(str, exc, i, null);
    }

    @Override // org.apache.log4j.spi.ErrorHandler
    public void error(String str, Exception exc, int i, LoggingEvent loggingEvent) {
        if ((exc instanceof InterruptedIOException) || (exc instanceof InterruptedException)) {
            Thread.currentThread().interrupt();
        }
        if (this.firstTime) {
            LogLog.error(str, exc);
            this.firstTime = false;
        }
    }

    @Override // org.apache.log4j.spi.ErrorHandler
    public void error(String str) {
        if (this.firstTime) {
            LogLog.error(str);
            this.firstTime = false;
        }
    }
}
