package org.spongepowered.tools.obfuscation.interfaces;

import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorRemappable;
import org.spongepowered.asm.mixin.refmap.ReferenceMapper;
import org.spongepowered.tools.obfuscation.ObfuscationData;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/interfaces/IReferenceManager.class */
public interface IReferenceManager {
    void setAllowConflicts(boolean z);

    boolean getAllowConflicts();

    void write();

    ReferenceMapper getMapper();

    void addMethodMapping(String str, String str2, ObfuscationData obfuscationData);

    void addMethodMapping(String str, String str2, ITargetSelectorRemappable iTargetSelectorRemappable, ObfuscationData obfuscationData);

    void addFieldMapping(String str, String str2, ITargetSelectorRemappable iTargetSelectorRemappable, ObfuscationData obfuscationData);

    void addClassMapping(String str, String str2, ObfuscationData obfuscationData);
}
