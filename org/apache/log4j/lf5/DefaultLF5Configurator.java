package org.apache.log4j.lf5;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.Configurator;
import org.apache.log4j.spi.LoggerRepository;

/* loaded from: L-out.jar:org/apache/log4j/lf5/DefaultLF5Configurator.class */
public class DefaultLF5Configurator implements Configurator {
    static Class class$org$apache$log4j$lf5$DefaultLF5Configurator;

    private DefaultLF5Configurator() {
    }

    public static void configure() throws Throwable {
        Class clsClass$;
        if (class$org$apache$log4j$lf5$DefaultLF5Configurator == null) {
            clsClass$ = class$("org.apache.log4j.lf5.DefaultLF5Configurator");
            class$org$apache$log4j$lf5$DefaultLF5Configurator = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$lf5$DefaultLF5Configurator;
        }
        URL resource = clsClass$.getResource("/org/apache/log4j/lf5/config/defaultconfig.properties");
        if (resource != null) {
            PropertyConfigurator.configure(resource);
            return;
        }
        throw new IOException(new StringBuffer().append("Error: Unable to open the resource").append("/org/apache/log4j/lf5/config/defaultconfig.properties").toString());
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    @Override // org.apache.log4j.spi.Configurator
    public void doConfigure(InputStream inputStream, LoggerRepository loggerRepository) {
        throw new IllegalStateException("This class should NOT be instantiated!");
    }

    @Override // org.apache.log4j.spi.Configurator
    public void doConfigure(URL url, LoggerRepository loggerRepository) {
        throw new IllegalStateException("This class should NOT be instantiated!");
    }
}
