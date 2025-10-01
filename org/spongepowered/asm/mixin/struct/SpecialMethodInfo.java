package org.spongepowered.asm.mixin.struct;

import org.objectweb.asm.tree.AnnotationNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.IInjectionPointContext;
import org.spongepowered.asm.mixin.refmap.IMixinContext;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.MixinTargetContext;
import org.spongepowered.asm.util.Bytecode;
import org.spongepowered.asm.util.asm.MethodNodeEx;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/struct/SpecialMethodInfo.class */
public abstract class SpecialMethodInfo implements IInjectionPointContext {
    protected final AnnotationNode annotation;
    protected final String annotationType;
    protected final ClassNode classNode;
    protected final MethodNode method;
    protected final String methodName;
    protected final MixinTargetContext mixin;

    public SpecialMethodInfo(MixinTargetContext mixinTargetContext, MethodNode methodNode, AnnotationNode annotationNode) {
        this.mixin = mixinTargetContext;
        this.method = methodNode;
        this.annotation = annotationNode;
        this.annotationType = this.annotation != null ? "@" + Bytecode.getSimpleName(this.annotation) : "Undecorated injector";
        this.classNode = mixinTargetContext.getTargetClassNode();
        this.methodName = MethodNodeEx.getName(methodNode);
    }

    @Override // org.spongepowered.asm.mixin.injection.IInjectionPointContext
    public final IMixinContext getContext() {
        return this.mixin;
    }

    @Override // org.spongepowered.asm.mixin.injection.IInjectionPointContext
    public final AnnotationNode getAnnotation() {
        return this.annotation;
    }

    public final ClassNode getClassNode() {
        return this.classNode;
    }

    public final ClassInfo getClassInfo() {
        return this.mixin.getClassInfo();
    }

    @Override // org.spongepowered.asm.mixin.injection.IInjectionPointContext
    public final MethodNode getMethod() {
        return this.method;
    }

    public String getMethodName() {
        return this.methodName;
    }
}
