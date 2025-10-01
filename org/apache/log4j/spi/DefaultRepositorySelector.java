package org.apache.log4j.spi;

/* loaded from: L-out.jar:org/apache/log4j/spi/DefaultRepositorySelector.class */
public class DefaultRepositorySelector implements RepositorySelector {
    final LoggerRepository repository;

    public DefaultRepositorySelector(LoggerRepository loggerRepository) {
        this.repository = loggerRepository;
    }

    @Override // org.apache.log4j.spi.RepositorySelector
    public LoggerRepository getLoggerRepository() {
        return this.repository;
    }
}
