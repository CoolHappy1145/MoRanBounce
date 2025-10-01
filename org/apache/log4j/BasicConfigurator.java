package org.apache.log4j;

/* loaded from: L-out.jar:org/apache/log4j/BasicConfigurator.class */
public class BasicConfigurator {
    protected BasicConfigurator() {
    }

    public static void configure() {
        Logger.getRootLogger().addAppender(new ConsoleAppender(new PatternLayout("%r [%t] %p %c %x - %m%n")));
    }

    public static void configure(Appender appender) {
        Logger.getRootLogger().addAppender(appender);
    }

    public static void resetConfiguration() {
        LogManager.resetConfiguration();
    }
}
