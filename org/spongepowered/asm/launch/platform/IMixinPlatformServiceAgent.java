package org.spongepowered.asm.launch.platform;

import java.util.Collection;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.util.IConsumer;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/platform/IMixinPlatformServiceAgent.class */
public interface IMixinPlatformServiceAgent extends IMixinPlatformAgent {
    void init();

    String getSideName();

    Collection getMixinContainers();

    @Deprecated
    void wire(MixinEnvironment.Phase phase, IConsumer iConsumer);

    @Deprecated
    void unwire();
}
