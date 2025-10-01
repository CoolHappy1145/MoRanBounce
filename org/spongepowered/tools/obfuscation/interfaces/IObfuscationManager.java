package org.spongepowered.tools.obfuscation.interfaces;

import java.util.List;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/interfaces/IObfuscationManager.class */
public interface IObfuscationManager {
    void init();

    IObfuscationDataProvider getDataProvider();

    IReferenceManager getReferenceManager();

    IMappingConsumer createMappingConsumer();

    List getEnvironments();

    void writeMappings();

    void writeReferences();
}
