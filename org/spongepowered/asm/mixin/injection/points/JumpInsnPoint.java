package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import java.util.ListIterator;
import kotlin.text.Typography;
import org.apache.log4j.net.SyslogAppender;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode("JUMP")
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/points/JumpInsnPoint.class */
public class JumpInsnPoint extends InjectionPoint {
    private final int opCode;
    private final int ordinal;

    public JumpInsnPoint(InjectionPointData injectionPointData) {
        this.opCode = injectionPointData.getOpcode(-1, new int[]{153, 154, 155, 156, 157, 158, 159, 160, 161, Typography.cent, Typography.pound, 164, 165, 166, Typography.section, SyslogAppender.LOG_LOCAL5, 198, 199, -1});
        this.ordinal = injectionPointData.getOrdinal();
    }

    @Override // org.spongepowered.asm.mixin.injection.InjectionPoint
    public boolean find(String str, InsnList insnList, Collection collection) {
        boolean z = false;
        int i = 0;
        ListIterator it = insnList.iterator();
        while (it.hasNext()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode) it.next();
            if ((abstractInsnNode instanceof JumpInsnNode) && (this.opCode == -1 || abstractInsnNode.getOpcode() == this.opCode)) {
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
