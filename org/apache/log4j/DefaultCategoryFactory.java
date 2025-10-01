package org.apache.log4j;

import org.apache.log4j.spi.LoggerFactory;

/* loaded from: L-out.jar:org/apache/log4j/DefaultCategoryFactory.class */
class DefaultCategoryFactory implements LoggerFactory {
    DefaultCategoryFactory() {
    }

    @Override // org.apache.log4j.spi.LoggerFactory
    public Logger makeNewLoggerInstance(String str) {
        return new Logger(str);
    }
}
