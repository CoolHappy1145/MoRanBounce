package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode("INVOKE_ASSIGN")
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/points/AfterInvoke.class */
public class AfterInvoke extends BeforeInvoke {
    public AfterInvoke(InjectionPointData injectionPointData) {
        super(injectionPointData);
    }

    @Override // org.spongepowered.asm.mixin.injection.points.BeforeInvoke
    protected boolean addInsn(InsnList insnList, Collection collection, AbstractInsnNode abstractInsnNode) {
        if (Type.getReturnType(((MethodInsnNode) abstractInsnNode).desc) == Type.VOID_TYPE) {
            return false;
        }
        AbstractInsnNode abstractInsnNodeNextNode = InjectionPoint.nextNode(insnList, abstractInsnNode);
        if ((abstractInsnNodeNextNode instanceof VarInsnNode) && abstractInsnNodeNextNode.getOpcode() >= 54) {
            abstractInsnNodeNextNode = InjectionPoint.nextNode(insnList, abstractInsnNodeNextNode);
        }
        collection.add(abstractInsnNodeNextNode);
        return true;
    }
}
