package org.spongepowered.asm.launch;

import com.google.common.collect.ImmutableList;
import cpw.mods.modlauncher.api.IEnvironment;
import cpw.mods.modlauncher.api.ITransformationService;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import joptsimple.ArgumentAcceptingOptionSpec;
import joptsimple.OptionSpecBuilder;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/MixinTransformationService.class */
public class MixinTransformationService implements ITransformationService {
    private ArgumentAcceptingOptionSpec mixinsArgument;
    private List commandLineMixins = new ArrayList();
    private MixinLaunchPlugin plugin;

    public void arguments(BiFunction biFunction) {
        this.mixinsArgument = ((OptionSpecBuilder) biFunction.apply("config", "a mixin config to load")).withRequiredArg().ofType(String.class);
    }

    public void argumentValues(ITransformationService.OptionResult optionResult) {
        this.commandLineMixins.addAll(optionResult.values(this.mixinsArgument));
    }

    public void initialize(IEnvironment iEnvironment) {
        Optional optionalFindLaunchPlugin = iEnvironment.findLaunchPlugin(MixinLaunchPlugin.NAME);
        if (!optionalFindLaunchPlugin.isPresent()) {
            throw new MixinInitialisationError("Mixin Launch Plugin Service could not be located");
        }
        ILaunchPluginService iLaunchPluginService = (ILaunchPluginService) optionalFindLaunchPlugin.get();
        if (!(iLaunchPluginService instanceof MixinLaunchPlugin)) {
            throw new MixinInitialisationError("Mixin Launch Plugin Service is present but not compatible");
        }
        this.plugin = (MixinLaunchPlugin) iLaunchPluginService;
        MixinBootstrap.start();
        this.plugin.init(iEnvironment, this.commandLineMixins);
    }

    public List transformers() {
        return ImmutableList.of();
    }
}
