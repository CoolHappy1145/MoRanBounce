package org.spongepowered.asm.launch.platform;

import net.minecraft.launchwrapper.Launch;
import org.spongepowered.asm.launch.platform.IMixinPlatformAgent;
import org.spongepowered.asm.launch.platform.container.IContainerHandle;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/platform/MixinPlatformAgentLiteLoaderLegacy.class */
public class MixinPlatformAgentLiteLoaderLegacy extends MixinPlatformAgentAbstract implements IMixinPlatformServiceAgent {
    private static final String GETSIDE_METHOD = "getEnvironmentType";
    private static final String LITELOADER_TWEAKER_NAME = "com.mumfrey.liteloader.launch.LiteLoaderTweaker";

    @Override // org.spongepowered.asm.launch.platform.MixinPlatformAgentAbstract, org.spongepowered.asm.launch.platform.IMixinPlatformAgent
    public IMixinPlatformAgent.AcceptResult accept(MixinPlatformManager mixinPlatformManager, IContainerHandle iContainerHandle) {
        return IMixinPlatformAgent.AcceptResult.REJECTED;
    }

    @Override // org.spongepowered.asm.launch.platform.IMixinPlatformServiceAgent
    public String getSideName() {
        return MixinPlatformAgentAbstract.invokeStringMethod(Launch.classLoader, LITELOADER_TWEAKER_NAME, GETSIDE_METHOD);
    }
}
