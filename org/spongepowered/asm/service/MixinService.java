package org.spongepowered.asm.service;

import com.google.common.base.Joiner;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinLaunchPlugin;

/* loaded from: L-out.jar:org/spongepowered/asm/service/MixinService.class */
public final class MixinService {
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private static MixinService instance;
    private ServiceLoader bootstrapServiceLoader;
    private ServiceLoader serviceLoader;
    private IGlobalPropertyService propertyService;
    private final Set bootedServices = new HashSet();
    private IMixinService service = null;

    private MixinService() {
        runBootServices();
    }

    private void runBootServices() {
        this.bootstrapServiceLoader = ServiceLoader.load(IMixinServiceBootstrap.class, getClass().getClassLoader());
        Iterator it = this.bootstrapServiceLoader.iterator();
        while (it.hasNext()) {
            try {
                IMixinServiceBootstrap iMixinServiceBootstrap = (IMixinServiceBootstrap) it.next();
                iMixinServiceBootstrap.bootstrap();
                this.bootedServices.add(iMixinServiceBootstrap.getServiceClassName());
            } catch (ServiceInitialisationException e) {
                logger.debug("Mixin bootstrap service {} is not available: {}", new Object[]{e.getStackTrace()[0].getClassName(), e.getMessage()});
            } catch (Throwable th) {
                logger.debug("Catching {}:{} initialising service", new Object[]{th.getClass().getName(), th.getMessage(), th});
            }
        }
    }

    private static MixinService getInstance() {
        if (instance == null) {
            instance = new MixinService();
        }
        return instance;
    }

    public static void boot() {
        getInstance();
    }

    public static IMixinService getService() {
        return getInstance().getServiceInstance();
    }

    private IMixinService getServiceInstance() {
        if (this.service == null) {
            this.service = initService();
        }
        return this.service;
    }

    private IMixinService initService() {
        IMixinService iMixinService;
        this.serviceLoader = ServiceLoader.load(IMixinService.class, getClass().getClassLoader());
        Iterator it = this.serviceLoader.iterator();
        ArrayList arrayList = new ArrayList();
        int i = 0;
        while (it.hasNext()) {
            try {
                iMixinService = (IMixinService) it.next();
                if (this.bootedServices.contains(iMixinService.getClass().getName())) {
                    logger.debug("MixinService [{}] was successfully booted in {}", new Object[]{iMixinService.getName(), getClass().getClassLoader()});
                }
            } catch (ServiceConfigurationError unused) {
                i++;
            } catch (Throwable th) {
                String className = th.getStackTrace()[0].getClassName();
                logger.debug("MixinService [{}] failed initialisation: {}", new Object[]{className, th.getMessage()});
                int iLastIndexOf = className.lastIndexOf(46);
                Object[] objArr = new Object[1];
                objArr[0] = iLastIndexOf < 0 ? className : className.substring(iLastIndexOf + 1);
                arrayList.add(String.format("ERROR[%s]", objArr));
            }
            if (iMixinService.isValid()) {
                return iMixinService;
            }
            logger.debug("MixinService [{}] is not valid", new Object[]{iMixinService.getName()});
            arrayList.add(String.format("INVALID[%s]", iMixinService.getName()));
        }
        throw new ServiceNotAvailableError("No mixin host service is available. Services: " + Joiner.on(", ").join(arrayList) + (i == 0 ? "" : " and " + i + " other invalid services."));
    }

    public static IGlobalPropertyService getGlobalPropertyService() {
        return getInstance().getGlobalPropertyServiceInstance();
    }

    private IGlobalPropertyService getGlobalPropertyServiceInstance() {
        if (this.propertyService == null) {
            this.propertyService = initPropertyService();
        }
        return this.propertyService;
    }

    private IGlobalPropertyService initPropertyService() {
        Iterator it = ServiceLoader.load(IGlobalPropertyService.class, getClass().getClassLoader()).iterator();
        while (it.hasNext()) {
            try {
                return (IGlobalPropertyService) it.next();
            } catch (ServiceConfigurationError unused) {
            } catch (Throwable unused2) {
            }
        }
        throw new ServiceNotAvailableError("No mixin global property service is available");
    }
}
