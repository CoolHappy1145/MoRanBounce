package org.spongepowered.asm.mixin.transformer;

import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import java.util.EnumSet;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.launch.IClassProcessor;
import org.spongepowered.asm.launch.Phases;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.service.ISyntheticClassRegistry;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinTransformationHandler.class */
public class MixinTransformationHandler implements IClassProcessor {
    private final Object initialisationLock = new Object();
    private MixinTransformer transformer;
    private ISyntheticClassRegistry registry;

    @Override // org.spongepowered.asm.launch.IClassProcessor
    public EnumSet handlesClass(Type type, boolean z, String str) {
        if (!z) {
            return Phases.AFTER_ONLY;
        }
        if (this.registry == null || this.registry.findSyntheticClass(type.getClassName()) == null) {
            return null;
        }
        return Phases.AFTER_ONLY;
    }

    @Override // org.spongepowered.asm.launch.IClassProcessor
    public boolean processClass(ILaunchPluginService.Phase phase, ClassNode classNode, Type type, String str) {
        MixinTransformer mixinTransformer;
        if (phase == ILaunchPluginService.Phase.BEFORE) {
            return false;
        }
        if (this.transformer == null) {
            synchronized (this.initialisationLock) {
                mixinTransformer = this.transformer;
                if (mixinTransformer == null) {
                    MixinTransformer mixinTransformer2 = new MixinTransformer();
                    this.transformer = mixinTransformer2;
                    mixinTransformer = mixinTransformer2;
                    this.registry = mixinTransformer.getExtensions().getSyntheticClassRegistry();
                }
            }
        } else {
            mixinTransformer = this.transformer;
        }
        MixinEnvironment currentEnvironment = MixinEnvironment.getCurrentEnvironment();
        if (this.registry.findSyntheticClass(type.getClassName()) != null) {
            return mixinTransformer.generateClass(currentEnvironment, type.getClassName(), classNode);
        }
        return mixinTransformer.transformClass(currentEnvironment, type.getClassName(), classNode);
    }
}
