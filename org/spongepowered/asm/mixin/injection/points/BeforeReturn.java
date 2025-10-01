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

@InjectionPoint.AtCode("RETURN")
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/points/BeforeReturn.class */
public class BeforeReturn extends InjectionPoint {
    private final int ordinal;

    public BeforeReturn(InjectionPointData injectionPointData) {
        super(injectionPointData);
        this.ordinal = injectionPointData.getOrdinal();
    }

    @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
    public InjectionPoint.RestrictTargetLevel getTargetRestriction(IInjectionPointContext iInjectionPointContext) {
        return InjectionPoint.RestrictTargetLevel.ALLOW_ALL;
    }

    @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
    public boolean find(String str, InsnList insnList, Collection collection) {
        boolean z = false;
        int opcode = Type.getReturnType(str).getOpcode(172);
        int i = 0;
        ListIterator it = insnList.iterator();
        while (it.hasNext()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode) it.next();
            if ((abstractInsnNode instanceof InsnNode) && abstractInsnNode.getOpcode() == opcode) {
                if (this.ordinal == -1 || this.ordinal == i) {
                    collection.add(abstractInsnNode);
                    z = true;
                }
                i++;
            }
        }
        return z;
    }
}
