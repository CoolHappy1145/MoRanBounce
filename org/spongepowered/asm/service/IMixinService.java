package org.spongepowered.asm.service;

import java.io.InputStream;
import java.util.Collection;
import org.spongepowered.asm.launch.platform.container.IContainerHandle;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.util.ReEntranceLock;

/* loaded from: L-out.jar:org/spongepowered/asm/service/IMixinService.class */
public interface IMixinService {
    String getName();

    boolean isValid();

    void prepare();

    MixinEnvironment.Phase getInitialPhase();

    void init();

    void beginPhase();

    void checkEnv(Object obj);

    ReEntranceLock getReEntranceLock();

    IClassProvider getClassProvider();

    IClassBytecodeProvider getBytecodeProvider();

    ITransformerProvider getTransformerProvider();

    IClassTracker getClassTracker();

    IMixinAuditTrail getAuditTrail();

    Collection getPlatformAgents();

    IContainerHandle getPrimaryContainer();

    Collection getMixinContainers();

    InputStream getResourceAsStream(String str);

    String getSideName();

    MixinEnvironment.CompatibilityLevel getMinCompatibilityLevel();

    MixinEnvironment.CompatibilityLevel getMaxCompatibilityLevel();
}
