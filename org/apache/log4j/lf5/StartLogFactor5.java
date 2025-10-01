package org.apache.log4j.lf5;

import org.apache.log4j.lf5.viewer.LogBrokerMonitor;

/* loaded from: L-out.jar:org/apache/log4j/lf5/StartLogFactor5.class */
public class StartLogFactor5 {
    public static final void main(String[] strArr) throws NumberFormatException {
        LogBrokerMonitor logBrokerMonitor = new LogBrokerMonitor(LogLevel.getLog4JLevels());
        logBrokerMonitor.setFrameSize(LF5Appender.getDefaultMonitorWidth(), LF5Appender.getDefaultMonitorHeight());
        logBrokerMonitor.setFontSize(12);
        logBrokerMonitor.show();
    }
}
