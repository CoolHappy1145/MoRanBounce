package org.spongepowered.asm.mixin.transformer;

import com.google.common.base.Strings;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinInitialisationError;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.extensibility.IMixinConfig;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/Config.class */
public class Config {
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private static final Map allConfigs = new HashMap();
    private final String name;
    private final MixinConfig config;

    public Config(MixinConfig mixinConfig) {
        this.name = mixinConfig.getName();
        this.config = mixinConfig;
    }

    public String getName() {
        return this.name;
    }

    MixinConfig get() {
        return this.config;
    }

    public boolean isVisited() {
        return this.config.isVisited();
    }

    public IMixinConfig getConfig() {
        return this.config;
    }

    public MixinEnvironment getEnvironment() {
        return this.config.getEnvironment();
    }

    public Config getParent() {
        MixinConfig parent = this.config.getParent();
        if (parent != null) {
            return parent.getHandle();
        }
        return null;
    }

    public String toString() {
        return this.config.toString();
    }

    public boolean equals(Object obj) {
        return (obj instanceof Config) && this.name.equals(((Config) obj).name);
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    @Deprecated
    public static Config create(String str, MixinEnvironment mixinEnvironment) {
        Config config = (Config) allConfigs.get(str);
        if (config != null) {
            return config;
        }
        try {
            Config configCreate = MixinConfig.create(str, mixinEnvironment);
            if (configCreate != null) {
                allConfigs.put(configCreate.getName(), configCreate);
            }
            if (configCreate == null) {
                return null;
            }
            String parentName = configCreate.get().getParentName();
            if (!Strings.isNullOrEmpty(parentName)) {
                try {
                    Config configCreate2 = create(parentName, mixinEnvironment);
                    if (configCreate2 != null) {
                        if (!configCreate.get().assignParent(configCreate2)) {
                            configCreate = null;
                        }
                    }
                    if (configCreate2 == null) {
                        logger.error("Error encountered initialising mixin config {0}: The parent {1} could not be read.", new Object[]{str, parentName});
                    }
                } catch (Throwable th) {
                    throw new MixinInitialisationError("Error initialising parent mixin config " + parentName + " of " + str, th);
                }
            }
            return configCreate;
        } catch (Exception e) {
            throw new MixinInitialisationError("Error initialising mixin config " + str, e);
        }
    }

    public static Config create(String str) {
        return MixinConfig.create(str, MixinEnvironment.getDefaultEnvironment());
    }
}
