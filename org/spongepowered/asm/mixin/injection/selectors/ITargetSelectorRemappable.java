package org.spongepowered.asm.mixin.injection.selectors;

import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/selectors/ITargetSelectorRemappable.class */
public interface ITargetSelectorRemappable extends ITargetSelectorByName {
    IMapping asMapping();

    MappingMethod asMethodMapping();

    MappingField asFieldMapping();

    ITargetSelectorRemappable move(String str);

    ITargetSelectorRemappable transform(String str);

    ITargetSelectorRemappable remapUsing(MappingMethod mappingMethod, boolean z);
}
