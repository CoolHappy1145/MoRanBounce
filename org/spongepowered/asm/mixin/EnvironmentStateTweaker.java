package org.spongepowered.asm.mixin;

import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/EnvironmentStateTweaker.class */
public class EnvironmentStateTweaker implements ITweaker {
    public void injectIntoClassLoader(LaunchClassLoader launchClassLoader) {
        MixinBootstrap.getPlatform().inject();
    }

    public String[] getLaunchArguments() {
        MixinEnvironment.gotoPhase(MixinEnvironment.Phase.DEFAULT);
        return new String[0];
    }
}
