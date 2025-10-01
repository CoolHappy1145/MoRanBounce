package org.spongepowered.asm.launch;

import java.io.File;
import java.util.List;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;
import org.spongepowered.asm.launch.platform.CommandLineOptions;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/MixinTweaker.class */
public class MixinTweaker implements ITweaker {
    public MixinTweaker() {
        MixinBootstrap.start();
    }

    public final void acceptOptions(List list, File file, File file2, String str) {
        MixinBootstrap.doInit(CommandLineOptions.ofArgs(list));
    }

    public final void injectIntoClassLoader(LaunchClassLoader launchClassLoader) {
        MixinBootstrap.inject();
    }

    public String getLaunchTarget() {
        MixinBootstrap.getPlatform();
        return "net.minecraft.client.main.Main";
    }
}
