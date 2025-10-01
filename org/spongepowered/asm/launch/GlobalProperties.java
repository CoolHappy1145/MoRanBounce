package org.spongepowered.asm.launch;

import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.service.IGlobalPropertyService;
import org.spongepowered.asm.service.IPropertyKey;
import org.spongepowered.asm.service.MixinService;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/GlobalProperties.class */
public final class GlobalProperties {
    private static IGlobalPropertyService service;

    /* loaded from: L-out.jar:org/spongepowered/asm/launch/GlobalProperties$Keys.class */
    public static final class Keys {
        public static final Keys INIT = m49of("mixin.initialised");
        public static final Keys AGENTS = m49of("mixin.agents");
        public static final Keys CONFIGS = m49of("mixin.configs");
        public static final Keys PLATFORM_MANAGER = m49of("mixin.platform");
        public static final Keys FML_LOAD_CORE_MOD = m49of("mixin.launch.fml.loadcoremodmethod");
        public static final Keys FML_GET_REPARSEABLE_COREMODS = m49of("mixin.launch.fml.reparseablecoremodsmethod");
        public static final Keys FML_CORE_MOD_MANAGER = m49of("mixin.launch.fml.coremodmanagerclass");
        public static final Keys FML_GET_IGNORED_MODS = m49of("mixin.launch.fml.ignoredmodsmethod");
        private static Map keys;
        private final String name;
        private IPropertyKey key;

        private Keys(String str) {
            this.name = str;
        }

        IPropertyKey resolve(IGlobalPropertyService iGlobalPropertyService) {
            if (this.key != null) {
                return this.key;
            }
            if (iGlobalPropertyService == null) {
                return null;
            }
            IPropertyKey iPropertyKeyResolveKey = iGlobalPropertyService.resolveKey(this.name);
            this.key = iPropertyKeyResolveKey;
            return iPropertyKeyResolveKey;
        }

        /* renamed from: of */
        public static Keys m49of(String str) {
            if (keys == null) {
                keys = new HashMap();
            }
            Keys keys2 = (Keys) keys.get(str);
            if (keys2 == null) {
                keys2 = new Keys(str);
                keys.put(str, keys2);
            }
            return keys2;
        }
    }

    private GlobalProperties() {
    }

    private static IGlobalPropertyService getService() {
        if (service == null) {
            service = MixinService.getGlobalPropertyService();
        }
        return service;
    }

    public static Object get(Keys keys) {
        IGlobalPropertyService service2 = getService();
        return service2.getProperty(keys.resolve(service2));
    }

    public static void put(Keys keys, Object obj) {
        IGlobalPropertyService service2 = getService();
        service2.setProperty(keys.resolve(service2), obj);
    }

    public static Object get(Keys keys, Object obj) {
        IGlobalPropertyService service2 = getService();
        return service2.getProperty(keys.resolve(service2), obj);
    }

    public static String getString(Keys keys, String str) {
        IGlobalPropertyService service2 = getService();
        return service2.getPropertyString(keys.resolve(service2), str);
    }
}
