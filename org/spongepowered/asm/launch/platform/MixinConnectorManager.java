package org.spongepowered.asm.launch.platform;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.connect.IMixinConnector;
import org.spongepowered.asm.service.IClassProvider;
import org.spongepowered.asm.service.MixinService;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/platform/MixinConnectorManager.class */
public class MixinConnectorManager {
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private final Set connectorClasses = new LinkedHashSet();
    private final List connectors = new ArrayList();

    MixinConnectorManager() {
    }

    void addConnector(String str) {
        this.connectorClasses.add(str);
    }

    void inject() {
        loadConnectors();
        initConnectors();
    }

    void loadConnectors() {
        IClassProvider classProvider = MixinService.getService().getClassProvider();
        for (String str : this.connectorClasses) {
            try {
                Class clsFindClass = classProvider.findClass(str);
                if (!IMixinConnector.class.isAssignableFrom(clsFindClass)) {
                    logger.error("Mixin Connector [" + str + "] does not implement IMixinConnector");
                } else {
                    try {
                        this.connectors.add((IMixinConnector) clsFindClass.newInstance());
                        logger.info("Successfully loaded Mixin Connector [" + str + "]");
                    } catch (ReflectiveOperationException e) {
                        logger.warn("Error loading Mixin Connector [" + str + "]", e);
                    }
                }
            } catch (ClassNotFoundException e2) {
                logger.catching(e2);
            }
        }
        this.connectorClasses.clear();
    }

    void initConnectors() {
        for (IMixinConnector iMixinConnector : this.connectors) {
            try {
                iMixinConnector.connect();
            } catch (Exception e) {
                logger.warn("Error initialising Mixin Connector [" + iMixinConnector.getClass().getName() + "]", e);
            }
        }
    }
}
