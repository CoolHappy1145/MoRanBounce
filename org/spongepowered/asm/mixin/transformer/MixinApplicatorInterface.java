package org.spongepowered.asm.mixin.transformer;

import java.util.Iterator;
import java.util.Map;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.InjectionInfo;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.transformer.throwables.InvalidInterfaceMixinException;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/MixinApplicatorInterface.class */
class MixinApplicatorInterface extends MixinApplicatorStandard {
    MixinApplicatorInterface(TargetClassContext targetClassContext) {
        super(targetClassContext);
    }

    @Override // org.spongepowered.asm.mixin.transformer.MixinApplicatorStandard
    protected void applyInterfaces(MixinTargetContext mixinTargetContext) {
        for (String str : mixinTargetContext.getInterfaces()) {
            if (!this.targetClass.name.equals(str) && !this.targetClass.interfaces.contains(str)) {
                this.targetClass.interfaces.add(str);
                mixinTargetContext.getTargetClassInfo().addInterface(str);
            }
        }
    }

    @Override // org.spongepowered.asm.mixin.transformer.MixinApplicatorStandard
    protected void applyFields(MixinTargetContext mixinTargetContext) {
        Iterator it = mixinTargetContext.getShadowFields().iterator();
        while (it.hasNext()) {
            FieldNode fieldNode = (FieldNode) ((Map.Entry) it.next()).getKey();
            this.logger.error("Ignoring redundant @Shadow field {}:{} in {}", new Object[]{fieldNode.name, fieldNode.desc, mixinTargetContext});
        }
        mergeNewFields(mixinTargetContext);
    }

    @Override // org.spongepowered.asm.mixin.transformer.MixinApplicatorStandard
    protected void prepareInjections(MixinTargetContext mixinTargetContext) {
        for (MethodNode methodNode : this.targetClass.methods) {
            try {
                InjectionInfo injectionInfo = InjectionInfo.parse(mixinTargetContext, methodNode);
                if (injectionInfo != null) {
                    throw new InvalidInterfaceMixinException(mixinTargetContext, injectionInfo + " is not supported on interface mixin method " + methodNode.name);
                }
            } catch (InvalidInjectionException e) {
                throw new InvalidInterfaceMixinException(mixinTargetContext, (e.getInjectionInfo() != null ? e.getInjectionInfo().toString() : "Injection") + " is not supported in interface mixin");
            }
        }
    }
}
