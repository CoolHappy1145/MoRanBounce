package org.apache.log4j.xml;

import org.apache.log4j.LogManager;
import org.apache.log4j.helpers.FileWatchdog;

/* loaded from: L-out.jar:org/apache/log4j/xml/XMLWatchdog.class */
class XMLWatchdog extends FileWatchdog {
    XMLWatchdog(String str) {
        super(str);
    }

    @Override // org.apache.log4j.helpers.FileWatchdog
    public void doOnChange() {
        new DOMConfigurator().doConfigure(this.filename, LogManager.getLoggerRepository());
    }
}
