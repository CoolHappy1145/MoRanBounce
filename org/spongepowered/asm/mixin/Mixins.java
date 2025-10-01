package org.spongepowered.asm.mixin;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.GlobalProperties;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.Config;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/Mixins.class */
public final class Mixins {
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private static final GlobalProperties.Keys CONFIGS_KEY = GlobalProperties.Keys.m49of(GlobalProperties.Keys.CONFIGS + ".queue");
    private static final Set errorHandlers = new LinkedHashSet();
    private static final Set registeredConfigs = new HashSet();

    private Mixins() {
    }

    public static void addConfigurations(String[] strArr) {
        MixinEnvironment defaultEnvironment = MixinEnvironment.getDefaultEnvironment();
        for (String str : strArr) {
            createConfiguration(str, defaultEnvironment);
        }
    }

    public static void addConfiguration(String str) {
        createConfiguration(str, MixinEnvironment.getDefaultEnvironment());
    }

    @Deprecated
    static void addConfiguration(String str, MixinEnvironment mixinEnvironment) {
        createConfiguration(str, mixinEnvironment);
    }

    private static void createConfiguration(String str, MixinEnvironment mixinEnvironment) {
        Config configCreate = null;
        try {
            configCreate = Config.create(str, mixinEnvironment);
        } catch (Exception e) {
            logger.error("Error encountered reading mixin config " + str + ": " + e.getClass().getName() + " " + e.getMessage(), e);
        }
        registerConfiguration(configCreate);
    }

    private static void registerConfiguration(Config config) {
        if (config == null || registeredConfigs.contains(config.getName())) {
            return;
        }
        MixinEnvironment environment = config.getEnvironment();
        if (environment != null) {
            environment.registerConfig(config.getName());
        }
        getConfigs().add(config);
        registeredConfigs.add(config.getName());
        Config parent = config.getParent();
        if (parent != null) {
            registerConfiguration(parent);
        }
    }

    public static int getUnvisitedCount() {
        int i = 0;
        Iterator it = getConfigs().iterator();
        while (it.hasNext()) {
            if (!((Config) it.next()).isVisited()) {
                i++;
            }
        }
        return i;
    }

    public static Set getConfigs() {
        Set linkedHashSet = (Set) GlobalProperties.get(CONFIGS_KEY);
        if (linkedHashSet == null) {
            linkedHashSet = new LinkedHashSet();
            GlobalProperties.put(CONFIGS_KEY, linkedHashSet);
        }
        return linkedHashSet;
    }

    public static Set getMixinsForClass(String str) {
        ClassInfo classInfoFromCache = ClassInfo.fromCache(str);
        if (classInfoFromCache != null) {
            return classInfoFromCache.getAppliedMixins();
        }
        return Collections.emptySet();
    }

    public static void registerErrorHandlerClass(String str) {
        if (str != null) {
            errorHandlers.add(str);
        }
    }

    public static Set getErrorHandlerClasses() {
        return Collections.unmodifiableSet(errorHandlers);
    }
}
