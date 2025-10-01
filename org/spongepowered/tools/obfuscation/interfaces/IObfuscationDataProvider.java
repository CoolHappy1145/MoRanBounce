package org.spongepowered.tools.obfuscation.interfaces;

import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationData;
import org.spongepowered.tools.obfuscation.mirror.TypeHandle;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/interfaces/IObfuscationDataProvider.class */
public interface IObfuscationDataProvider {
    ObfuscationData getObfEntryRecursive(ITargetSelectorRemappable iTargetSelectorRemappable);

    ObfuscationData getObfEntry(ITargetSelectorRemappable iTargetSelectorRemappable);

    ObfuscationData getObfEntry(IMapping iMapping);

    ObfuscationData getObfMethodRecursive(ITargetSelectorRemappable iTargetSelectorRemappable);

    ObfuscationData getObfMethod(ITargetSelectorRemappable iTargetSelectorRemappable);

    ObfuscationData getRemappedMethod(ITargetSelectorRemappable iTargetSelectorRemappable);

    ObfuscationData getObfMethod(MappingMethod mappingMethod);

    ObfuscationData getRemappedMethod(MappingMethod mappingMethod);

    ObfuscationData getObfFieldRecursive(ITargetSelectorRemappable iTargetSelectorRemappable);

    ObfuscationData getObfField(ITargetSelectorRemappable iTargetSelectorRemappable);

    ObfuscationData getObfField(MappingField mappingField);

    ObfuscationData getObfClass(TypeHandle typeHandle);

    ObfuscationData getObfClass(String str);
}
