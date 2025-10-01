package org.spongepowered.asm.mixin.transformer;

import java.util.List;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.mixin.transformer.ext.Extensions;
import org.spongepowered.asm.mixin.transformer.ext.IExtensionRegistry;
import org.spongepowered.asm.mixin.transformer.ext.IHotSwap;
import org.spongepowered.asm.transformers.TreeTransformer;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.asm.ASM;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinTransformer.class */
class MixinTransformer extends TreeTransformer implements IMixinTransformer {
    private static final String MIXIN_AGENT_CLASS = "org.spongepowered.tools.agent.MixinAgent";
    private final SyntheticClassRegistry syntheticClassRegistry;
    private final Extensions extensions;
    private final IHotSwap hotSwapper;
    private final MixinProcessor processor;
    private final MixinClassGenerator generator;

    public MixinTransformer() {
        MixinEnvironment currentEnvironment = MixinEnvironment.getCurrentEnvironment();
        if (currentEnvironment.getActiveTransformer() instanceof IMixinTransformer) {
            throw new MixinException("Terminating MixinTransformer instance " + this);
        }
        currentEnvironment.setActiveTransformer(this);
        this.syntheticClassRegistry = new SyntheticClassRegistry();
        this.extensions = new Extensions(this.syntheticClassRegistry);
        this.hotSwapper = initHotSwapper(currentEnvironment);
        this.processor = new MixinProcessor(currentEnvironment, this.extensions, this.hotSwapper);
        this.generator = new MixinClassGenerator(currentEnvironment, this.extensions);
        DefaultExtensions.create(currentEnvironment, this.extensions, this.syntheticClassRegistry);
    }

    private IHotSwap initHotSwapper(MixinEnvironment mixinEnvironment) {
        if (!mixinEnvironment.getOption(MixinEnvironment.Option.HOT_SWAP)) {
            return null;
        }
        try {
            MixinProcessor.logger.info("Attempting to load Hot-Swap agent");
            return (IHotSwap) Class.forName(MIXIN_AGENT_CLASS).getDeclaredConstructor(IMixinTransformer.class).newInstance(this);
        } catch (Throwable th) {
            MixinProcessor.logger.info("Hot-swap agent could not be loaded, hot swapping of mixins won't work. {}: {}", new Object[]{th.getClass().getSimpleName(), th.getMessage()});
            return null;
        }
    }

    @Override // org.spongepowered.asm.mixin.transformer.IMixinTransformer
    public IExtensionRegistry getExtensions() {
        return this.extensions;
    }

    @Override // org.spongepowered.asm.service.ITransformer
    public String getName() {
        return getClass().getName();
    }

    @Override // org.spongepowered.asm.mixin.transformer.IMixinTransformer
    public void audit(MixinEnvironment mixinEnvironment) {
        this.processor.audit(mixinEnvironment);
    }

    @Override // org.spongepowered.asm.mixin.transformer.IMixinTransformer
    public List reload(String str, ClassNode classNode) {
        return this.processor.reload(str, classNode);
    }

    @Override // org.spongepowered.asm.service.ILegacyClassTransformer, org.spongepowered.asm.mixin.transformer.IMixinTransformer
    public byte[] transformClassBytes(String str, String str2, byte[] bArr) {
        if (str2 == null) {
            return bArr;
        }
        MixinEnvironment currentEnvironment = MixinEnvironment.getCurrentEnvironment();
        if (bArr == null) {
            return generateClass(currentEnvironment, str2);
        }
        return transformClass(currentEnvironment, str2, bArr);
    }

    public byte[] transformClass(MixinEnvironment mixinEnvironment, String str, byte[] bArr) {
        ClassNode classNode = readClass(bArr);
        if (this.processor.applyMixins(mixinEnvironment, str, classNode)) {
            return writeClass(classNode);
        }
        return bArr;
    }

    public boolean transformClass(MixinEnvironment mixinEnvironment, String str, ClassNode classNode) {
        return this.processor.applyMixins(mixinEnvironment, str, classNode);
    }

    public byte[] generateClass(MixinEnvironment mixinEnvironment, String str) {
        ClassNode classNodeCreateEmptyClass = createEmptyClass(str);
        if (this.generator.generateClass(mixinEnvironment, str, classNodeCreateEmptyClass)) {
            return writeClass(classNodeCreateEmptyClass);
        }
        return null;
    }

    public boolean generateClass(MixinEnvironment mixinEnvironment, String str, ClassNode classNode) {
        return this.generator.generateClass(mixinEnvironment, str, classNode);
    }

    private static ClassNode createEmptyClass(String str) {
        ClassNode classNode = new ClassNode(ASM.API_VERSION);
        classNode.name = str.replace('.', '/');
        classNode.version = MixinEnvironment.getCompatibilityLevel().classVersion();
        classNode.superName = Constants.OBJECT;
        return classNode;
    }
}
