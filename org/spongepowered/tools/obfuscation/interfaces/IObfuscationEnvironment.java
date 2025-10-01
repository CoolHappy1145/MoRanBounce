package org.spongepowered.tools.obfuscation.interfaces;

import java.util.Collection;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/interfaces/IObfuscationEnvironment.class */
public interface IObfuscationEnvironment {
    MappingMethod getObfMethod(ITargetSelectorRemappable iTargetSelectorRemappable);

    MappingMethod getObfMethod(MappingMethod mappingMethod);

    MappingMethod getObfMethod(MappingMethod mappingMethod, boolean z);

    MappingField getObfField(ITargetSelectorRemappable iTargetSelectorRemappable);

    MappingField getObfField(MappingField mappingField);

    MappingField getObfField(MappingField mappingField, boolean z);

    String getObfClass(String str);

    ITargetSelectorRemappable remapDescriptor(ITargetSelectorRemappable iTargetSelectorRemappable);

    String remapDescriptor(String str);

    void writeMappings(Collection collection);
}
