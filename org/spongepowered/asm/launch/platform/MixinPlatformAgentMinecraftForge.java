package org.spongepowered.asm.launch.platform;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.TypesafeMap;
import java.util.Locale;
import org.spongepowered.asm.launch.platform.IMixinPlatformAgent;
import org.spongepowered.asm.launch.platform.container.IContainerHandle;
import org.spongepowered.asm.util.Constants;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/platform/MixinPlatformAgentMinecraftForge.class */
public class MixinPlatformAgentMinecraftForge extends MixinPlatformAgentAbstract implements IMixinPlatformServiceAgent {
    @Override // org.spongepowered.asm.launch.platform.MixinPlatformAgentAbstract, org.spongepowered.asm.launch.platform.IMixinPlatformAgent
    public IMixinPlatformAgent.AcceptResult accept(MixinPlatformManager mixinPlatformManager, IContainerHandle iContainerHandle) {
        return IMixinPlatformAgent.AcceptResult.REJECTED;
    }

    @Override // org.spongepowered.asm.launch.platform.IMixinPlatformServiceAgent
    public String getSideName() {
        String lowerCase = ((String) Launcher.INSTANCE.environment().getProperty((TypesafeMap.Key) IEnvironment.Keys.LAUNCHTARGET.get()).orElse("missing")).toLowerCase(Locale.ROOT);
        if (lowerCase.contains("server")) {
            return Constants.SIDE_SERVER;
        }
        if (lowerCase.contains("client")) {
            return Constants.SIDE_CLIENT;
        }
        return null;
    }
}
