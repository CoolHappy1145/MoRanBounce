package org.spongepowered.tools.obfuscation.service;

import java.util.Collection;
import java.util.Set;
import org.spongepowered.tools.obfuscation.interfaces.IMixinAnnotationProcessor;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/service/IObfuscationService.class */
public interface IObfuscationService {
    Set getSupportedOptions();

    Collection getObfuscationTypes(IMixinAnnotationProcessor iMixinAnnotationProcessor);
}
