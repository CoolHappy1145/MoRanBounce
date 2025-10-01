package org.spongepowered.asm.mixin.transformer;

import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckClass;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionCheckInterfaces;
import org.spongepowered.asm.mixin.transformer.ext.extensions.ExtensionClassExporter;
import org.spongepowered.asm.service.ISyntheticClassInfo;
import org.spongepowered.asm.util.IConsumer;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/DefaultExtensions.class */
final class DefaultExtensions {
    private DefaultExtensions() {
    }

    static void create(MixinEnvironment mixinEnvironment, Extensions extensions, SyntheticClassRegistry syntheticClassRegistry) {
        IConsumer iConsumer = new IConsumer(syntheticClassRegistry) { // from class: org.spongepowered.asm.mixin.transformer.DefaultExtensions.1
            final SyntheticClassRegistry val$registry;

            {
                this.val$registry = syntheticClassRegistry;
            }

            @Override // org.spongepowered.asm.util.IConsumer
            public void accept(Object obj) {
                accept((ISyntheticClassInfo) obj);
            }

            public void accept(ISyntheticClassInfo iSyntheticClassInfo) {
                this.val$registry.registerSyntheticClass(iSyntheticClassInfo);
            }
        };
        extensions.add(new ArgsClassGenerator(iConsumer));
        extensions.add(new InnerClassGenerator(iConsumer));
        extensions.add(new ExtensionClassExporter(mixinEnvironment));
        extensions.add(new ExtensionCheckClass());
        extensions.add(new ExtensionCheckInterfaces());
    }
}
