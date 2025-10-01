package org.spongepowered.tools.obfuscation.mapping;

import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mapping/IMappingWriter.class */
public interface IMappingWriter {
    void write(String str, ObfuscationType obfuscationType, IMappingConsumer.MappingSet mappingSet, IMappingConsumer.MappingSet mappingSet2);
}
