package org.spongepowered.asm.launch.platform;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.launch.platform.IMixinPlatformAgent;
import org.spongepowered.asm.launch.platform.container.IContainerHandle;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/platform/MixinPlatformAgentAbstract.class */
public abstract class MixinPlatformAgentAbstract implements IMixinPlatformAgent {
    protected static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    protected MixinPlatformManager manager;
    protected IContainerHandle handle;

    protected MixinPlatformAgentAbstract() {
    }

    @Override // org.spongepowered.asm.launch.platform.IMixinPlatformAgent
    public IMixinPlatformAgent.AcceptResult accept(MixinPlatformManager mixinPlatformManager, IContainerHandle iContainerHandle) {
        this.manager = mixinPlatformManager;
        this.handle = iContainerHandle;
        return IMixinPlatformAgent.AcceptResult.ACCEPTED;
    }

    public String toString() {
        return String.format("PlatformAgent[%s:%s]", getClass().getSimpleName(), this.handle);
    }

    protected static String invokeStringMethod(ClassLoader classLoader, String str, String str2) {
        try {
            return ((Enum) Class.forName(str, false, classLoader).getDeclaredMethod(str2, new Class[0]).invoke(null, new Object[0])).name();
        } catch (Exception unused) {
            return null;
        }
    }
}
