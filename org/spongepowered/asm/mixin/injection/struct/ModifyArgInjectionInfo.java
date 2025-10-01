package org.spongepowered.asm.mixin.injection.struct;

import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.ModifyArgInjector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Annotations;

@InjectionInfo.AnnotationType(ModifyArg.class)
@InjectionInfo.HandlerPrefix("modify")
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/ModifyArgInjectionInfo.class */
public class ModifyArgInjectionInfo extends InjectionInfo {
    public ModifyArgInjectionInfo(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode) {
        super(mixinTargetContext, methodNode, annotationNode);
    }

    @Override // org.spongepowered.asm.mixin.injection.struct.InjectionInfo
    protected Injector parseInjector(AnnotationNode annotationNode) {
        return new ModifyArgInjector(this, ((Integer) Annotations.getValue(annotationNode, "index", (Object) (-1))).intValue());
    }
}
