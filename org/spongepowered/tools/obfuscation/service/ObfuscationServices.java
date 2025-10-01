package org.spongepowered.tools.obfuscation.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.Set;
import javax.tools.Diagnostic;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.SupportedOptions;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/service/ObfuscationServices.class */
public final class ObfuscationServices {
    private static ObfuscationServices instance;
    private final Set services = new HashSet();
    private final ServiceLoader serviceLoader = ServiceLoader.load(IObfuscationService.class, getClass().getClassLoader());

    private ObfuscationServices() {
    }

    public static ObfuscationServices getInstance() {
        if (instance == null) {
            instance = new ObfuscationServices();
        }
        return instance;
    }

    public void initProviders(IMixinAnnotationProcessor iMixinAnnotationProcessor) {
        boolean zIsDefault = false;
        try {
            Iterator it = this.serviceLoader.iterator();
            while (it.hasNext()) {
                IObfuscationService iObfuscationService = (IObfuscationService) it.next();
                if (!this.services.contains(iObfuscationService)) {
                    this.services.add(iObfuscationService);
                    String simpleName = iObfuscationService.getClass().getSimpleName();
                    Collection obfuscationTypes = iObfuscationService.getObfuscationTypes(iMixinAnnotationProcessor);
                    if (obfuscationTypes != null) {
                        Iterator it2 = obfuscationTypes.iterator();
                        while (it2.hasNext()) {
                            try {
                                ObfuscationType obfuscationTypeCreate = ObfuscationType.create((ObfuscationTypeDescriptor) it2.next(), iMixinAnnotationProcessor);
                                iMixinAnnotationProcessor.printMessage(Diagnostic.Kind.NOTE, simpleName + " supports type: \"" + obfuscationTypeCreate + "\"");
                                zIsDefault |= obfuscationTypeCreate.isDefault();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (ServiceConfigurationError e2) {
            iMixinAnnotationProcessor.printMessage(Diagnostic.Kind.ERROR, e2.getClass().getSimpleName() + ": " + e2.getMessage());
            e2.printStackTrace();
        }
        if (!zIsDefault) {
            String option = iMixinAnnotationProcessor.getOption(SupportedOptions.DEFAULT_OBFUSCATION_ENV);
            if (option == null) {
                iMixinAnnotationProcessor.printMessage(Diagnostic.Kind.WARNING, "No default obfuscation environment was specified and \"searge\" is not available. Please ensure defaultObfuscationEnv is specified in your build configuration");
            } else {
                iMixinAnnotationProcessor.printMessage(Diagnostic.Kind.WARNING, "Specified default obfuscation environment \"" + option.toLowerCase(Locale.ROOT) + "\" was not defined. This probably means your build configuration is out of date or a required service is missing");
            }
        }
    }

    public Set getSupportedOptions() {
        HashSet hashSet = new HashSet();
        Iterator it = this.services.iterator();
        while (it.hasNext()) {
            Set supportedOptions = ((IObfuscationService) it.next()).getSupportedOptions();
            if (supportedOptions != null) {
                hashSet.addAll(supportedOptions);
            }
        }
        return hashSet;
    }

    public IObfuscationService getService(Class cls) {
        for (IObfuscationService iObfuscationService : this.services) {
            if (cls.getName().equals(iObfuscationService.getClass().getName())) {
                return iObfuscationService;
            }
        }
        return null;
    }
}
