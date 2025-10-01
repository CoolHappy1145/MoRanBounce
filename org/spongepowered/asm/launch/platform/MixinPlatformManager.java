package org.spongepowered.asm.launch.platform;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.launch.platform.container.IContainerHandle;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.throwables.MixinError;
import org.spongepowered.asm.service.MixinService;
import org.spongepowered.asm.service.ServiceVersionError;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/platform/MixinPlatformManager.class */
public class MixinPlatformManager {
    private static final String DEFAULT_MAIN_CLASS = "net.minecraft.client.main.Main";
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private MixinContainer primaryContainer;
    private boolean injected;
    private final Map containers = new LinkedHashMap();
    private final MixinConnectorManager connectors = new MixinConnectorManager();
    private boolean prepared = false;

    public void init() {
        logger.debug("Initialising Mixin Platform Manager");
        this.primaryContainer = addContainer(MixinService.getService().getPrimaryContainer());
        scanForContainers();
    }

    public Collection getPhaseProviderClasses() {
        Collection phaseProviders = this.primaryContainer.getPhaseProviders();
        if (phaseProviders != null) {
            return Collections.unmodifiableCollection(phaseProviders);
        }
        return Collections.emptyList();
    }

    public final MixinContainer addContainer(IContainerHandle iContainerHandle) {
        MixinContainer mixinContainer = (MixinContainer) this.containers.get(iContainerHandle);
        if (mixinContainer != null) {
            return mixinContainer;
        }
        MixinContainer mixinContainerCreateContainerFor = createContainerFor(iContainerHandle);
        this.containers.put(iContainerHandle, mixinContainerCreateContainerFor);
        addNestedContainers(iContainerHandle);
        return mixinContainerCreateContainerFor;
    }

    private MixinContainer createContainerFor(IContainerHandle iContainerHandle) {
        logger.debug("Adding mixin platform agents for container {}", new Object[]{iContainerHandle});
        MixinContainer mixinContainer = new MixinContainer(this, iContainerHandle);
        if (this.prepared) {
            mixinContainer.prepare();
        }
        return mixinContainer;
    }

    private void addNestedContainers(IContainerHandle iContainerHandle) {
        for (IContainerHandle iContainerHandle2 : iContainerHandle.getNestedContainers()) {
            if (!this.containers.containsKey(iContainerHandle2)) {
                addContainer(iContainerHandle2);
            }
        }
    }

    public final void prepare(CommandLineOptions commandLineOptions) {
        this.prepared = true;
        Iterator it = this.containers.values().iterator();
        while (it.hasNext()) {
            ((MixinContainer) it.next()).prepare();
        }
        Iterator it2 = commandLineOptions.getConfigs().iterator();
        while (it2.hasNext()) {
            addConfig((String) it2.next());
        }
    }

    public final void inject() {
        if (this.injected) {
            return;
        }
        this.injected = true;
        if (this.primaryContainer != null) {
            this.primaryContainer.initPrimaryContainer();
        }
        scanForContainers();
        logger.debug("inject() running with {} agents", new Object[]{Integer.valueOf(this.containers.size())});
        Iterator it = this.containers.values().iterator();
        while (it.hasNext()) {
            try {
                ((MixinContainer) it.next()).inject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.connectors.inject();
    }

    private void scanForContainers() {
        try {
            Collection<IContainerHandle> mixinContainers = MixinService.getService().getMixinContainers();
            Iterator it = new ArrayList(this.containers.keySet()).iterator();
            while (it.hasNext()) {
                addNestedContainers((IContainerHandle) it.next());
            }
            for (IContainerHandle iContainerHandle : mixinContainers) {
                try {
                    logger.debug("Adding agents for Mixin Container {}", new Object[]{iContainerHandle});
                    addContainer(iContainerHandle);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (AbstractMethodError unused) {
            throw new ServiceVersionError("Mixin service is out of date");
        }
    }

    final void setCompatibilityLevel(String str) {
        try {
            MixinEnvironment.CompatibilityLevel compatibilityLevelValueOf = MixinEnvironment.CompatibilityLevel.valueOf(str.toUpperCase(Locale.ROOT));
            logger.debug("Setting mixin compatibility level: {}", new Object[]{compatibilityLevelValueOf});
            MixinEnvironment.setCompatibilityLevel(compatibilityLevelValueOf);
        } catch (IllegalArgumentException unused) {
            logger.warn("Invalid compatibility level specified: {}", new Object[]{str});
        }
    }

    final void addConfig(String str) {
        if (str.endsWith(".json")) {
            logger.debug("Registering mixin config: {}", new Object[]{str});
            Mixins.addConfiguration(str);
        } else if (str.contains(".json@")) {
            throw new MixinError("Setting config phase via manifest is no longer supported: " + str + ". Specify target in config instead");
        }
    }

    final void addTokenProvider(String str) {
        if (str.contains("@")) {
            String[] strArrSplit = str.split("@", 2);
            MixinEnvironment.Phase phaseForName = MixinEnvironment.Phase.forName(strArrSplit[1]);
            if (phaseForName != null) {
                logger.debug("Registering token provider class: {}", new Object[]{strArrSplit[0]});
                MixinEnvironment.getEnvironment(phaseForName).registerTokenProviderClass(strArrSplit[0]);
                return;
            }
            return;
        }
        MixinEnvironment.getDefaultEnvironment().registerTokenProviderClass(str);
    }

    final void addConnector(String str) {
        this.connectors.addConnector(str);
    }
}
