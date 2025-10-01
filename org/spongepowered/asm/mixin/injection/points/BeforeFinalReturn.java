package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import java.util.ListIterator;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.spongepowered.asm.mixin.injection.IInjectionPointContext;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.throwables.InvalidInjectionException;
import org.spongepowered.asm.mixin.refmap.IMixinContext;

@InjectionPoint.AtCode("TAIL")
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/points/BeforeFinalReturn.class */
public class BeforeFinalReturn extends InjectionPoint {
    private final IMixinContext context;

    public BeforeFinalReturn(InjectionPointData injectionPointData) {
        super(injectionPointData);
        this.context = injectionPointData.getContext();
    }

    @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
    public InjectionPoint.RestrictTargetLevel getTargetRestriction(IInjectionPointContext iInjectionPointContext) {
        return InjectionPoint.RestrictTargetLevel.ALLOW_ALL;
    }

    @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
    public boolean find(String str, InsnList insnList, Collection collection) {
        AbstractInsnNode abstractInsnNode = null;
        int opcode = Type.getReturnType(str).getOpcode(172);
        ListIterator it = insnList.iterator();
        while (it.hasNext()) {
            AbstractInsnNode abstractInsnNode2 = (AbstractInsnNode) it.next();
            if ((abstractInsnNode2 instanceof InsnNode) && abstractInsnNode2.getOpcode() == opcode) {
                abstractInsnNode = abstractInsnNode2;
            }
        }
        if (abstractInsnNode == null) {
            throw new InvalidInjectionException(this.context, "TAIL could not locate a valid RETURN in the target method!");
        }
        collection.add(abstractInsnNode);
        return true;
    }
}
