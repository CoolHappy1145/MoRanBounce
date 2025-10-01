package org.spongepowered.asm.mixin.injection;

import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/IInjectionPointContext.class */
public interface IInjectionPointContext {
    IMixinContext getContext();

    MethodNode getMethod();

    AnnotationNode getAnnotation();
}
