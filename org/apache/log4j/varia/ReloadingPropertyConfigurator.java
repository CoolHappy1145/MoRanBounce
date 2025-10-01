package org.apache.log4j.varia;

import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.spi.Configurator;

/* loaded from: L-out.jar:org/apache/log4j/varia/ReloadingPropertyConfigurator.class */
public class ReloadingPropertyConfigurator implements Configurator {
    PropertyConfigurator delegate = new PropertyConfigurator();
}
