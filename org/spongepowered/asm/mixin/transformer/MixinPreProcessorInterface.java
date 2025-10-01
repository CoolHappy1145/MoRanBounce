package org.spongepowered.asm.mixin.transformer;

import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.FieldNode;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.MixinInfo;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidInterfaceMixinException;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinPreProcessorInterface.class */
class MixinPreProcessorInterface extends MixinPreProcessorStandard {
    MixinPreProcessorInterface(MixinInfo mixinInfo, MixinInfo.MixinClassNode mixinClassNode) {
        super(mixinInfo, mixinClassNode);
    }

    @Override // org.spongepowered.asm.mixin.transformer.MixinPreProcessorStandard
    protected void prepareMethod(MixinInfo.MixinMethodNode mixinMethodNode, ClassInfo.Method method) {
        if (!Bytecode.hasFlag(mixinMethodNode, 1) && !Bytecode.hasFlag(mixinMethodNode, 4096)) {
            throw new InvalidInterfaceMixinException(this.mixin, "Interface mixin contains a non-public method! Found " + method + " in " + this.mixin);
        }
        super.prepareMethod(mixinMethodNode, method);
    }

    @Override // org.spongepowered.asm.mixin.transformer.MixinPreProcessorStandard
    protected boolean validateField(MixinTargetContext mixinTargetContext, FieldNode fieldNode, AnnotationNode annotationNode) {
        if (!Bytecode.isStatic(fieldNode)) {
            throw new InvalidInterfaceMixinException(this.mixin, "Interface mixin contains an instance field! Found " + fieldNode.name + " in " + this.mixin);
        }
        return super.validateField(mixinTargetContext, fieldNode, annotationNode);
    }
}
