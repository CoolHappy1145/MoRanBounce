package org.spongepowered.asm.mixin.injection.struct;

import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.ModifyArgsInjector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

@InjectionInfo.AnnotationType(ModifyArgs.class)
@InjectionInfo.HandlerPrefix("args")
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/ModifyArgsInjectionInfo.class */
public class ModifyArgsInjectionInfo extends InjectionInfo {
    public ModifyArgsInjectionInfo(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode) {
        super(mixinTargetContext, methodNode, annotationNode);
    }

    @Override // org.spongepowered.asm.mixin.injection.struct.InjectionInfo
    protected Injector parseInjector(AnnotationNode annotationNode) {
        return new ModifyArgsInjector(this);
    }
}
