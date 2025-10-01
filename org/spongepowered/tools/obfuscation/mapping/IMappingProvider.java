package org.spongepowered.tools.obfuscation.mapping;

import java.io.File;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mapping/IMappingProvider.class */
public interface IMappingProvider {
    void clear();

    boolean isEmpty();

    void read(File file);

    MappingMethod getMethodMapping(MappingMethod mappingMethod);

    MappingField getFieldMapping(MappingField mappingField);

    String getClassMapping(String str);

    String getPackageMapping(String str);
}
