package org.spongepowered.asm.service;

import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

/* loaded from: L-out.jar:org/spongepowered/asm/service/ISyntheticClassInfo.class */
public interface ISyntheticClassInfo {
    IMixinInfo getMixin();

    String getName();

    String getClassName();

    boolean isLoaded();
}
