package org.spongepowered.asm.mixin.injection.invoke;

import java.util.List;
import org.objectweb.asm.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.injection.code.Injector;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.struct.InjectionNodes;
import org.spongepowered.asm.mixin.injection.struct.Target;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/invoke/InvokeInjector.class */
public abstract class InvokeInjector extends Injector {
    protected abstract void injectAtInvoke(Target target, InjectionNodes.InjectionNode injectionNode);

    public InvokeInjector(InjectionInfo injectionInfo, String str) {
        super(injectionInfo, str);
    }

    @Override // org.spongepowered.asm.mixin.injection.code.Injector
    protected void sanityCheck(Target target, List list) {
        super.sanityCheck(target, list);
        checkTarget(target);
    }

    protected void checkTarget(Target target) {
        checkTargetModifiers(target, true);
    }

    @Override // org.spongepowered.asm.mixin.injection.code.Injector
    protected void inject(Target target, InjectionNodes.InjectionNode injectionNode) {
        if (!(injectionNode.getCurrentTarget() instanceof MethodInsnNode)) {
            throw new InvalidInjectionException(this.info, String.format("%s annotation on is targetting a non-method insn in %s in %s", this.annotationType, target, this));
        }
        injectAtInvoke(target, injectionNode);
    }
}
