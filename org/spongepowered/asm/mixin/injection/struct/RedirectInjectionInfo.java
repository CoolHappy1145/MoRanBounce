package org.spongepowered.asm.mixin.injection.struct;

import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.invoke.RedirectInjector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;

@InjectionInfo.AnnotationType(Redirect.class)
@InjectionInfo.HandlerPrefix("redirect")
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/struct/RedirectInjectionInfo.class */
public class RedirectInjectionInfo extends InjectionInfo {
    public RedirectInjectionInfo(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode) {
        super(mixinTargetContext, methodNode, annotationNode);
    }

    @Override // org.spongepowered.asm.mixin.injection.struct.InjectionInfo
    protected Injector parseInjector(AnnotationNode annotationNode) {
        return new RedirectInjector(this);
    }
}
