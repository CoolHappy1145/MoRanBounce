package org.apache.log4j.lf5;

import org.apache.log4j.lf5.viewer.LogBrokerMonitor;

/* loaded from: L-out.jar:org/apache/log4j/lf5/AppenderFinalizer.class */
public class AppenderFinalizer {
    protected LogBrokerMonitor _defaultMonitor;

    public AppenderFinalizer(LogBrokerMonitor logBrokerMonitor) {
        this._defaultMonitor = null;
        this._defaultMonitor = logBrokerMonitor;
    }

    protected void finalize() {
        System.out.println("Disposing of the default LogBrokerMonitor instance");
        this._defaultMonitor.dispose();
    }
}
