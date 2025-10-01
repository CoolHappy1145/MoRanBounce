package org.apache.log4j;

import org.apache.log4j.helpers.FileWatchdog;

/* loaded from: L-out.jar:org/apache/log4j/PropertyWatchdog.class */
class PropertyWatchdog extends FileWatchdog {
    PropertyWatchdog(String str) {
        super(str);
    }

    @Override // org.apache.log4j.helpers.FileWatchdog
    public void doOnChange() throws Throwable {
        new PropertyConfigurator().doConfigure(this.filename, LogManager.getLoggerRepository());
    }
}
