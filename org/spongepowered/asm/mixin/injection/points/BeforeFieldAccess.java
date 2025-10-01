package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import java.util.ListIterator;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.util.Bytecode;

@InjectionPoint.AtCode("FIELD")
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/points/BeforeFieldAccess.class */
public class BeforeFieldAccess extends BeforeInvoke {
    private static final String ARRAY_GET = "get";
    private static final String ARRAY_SET = "set";
    private static final String ARRAY_LENGTH = "length";
    public static final int ARRAY_SEARCH_FUZZ_DEFAULT = 8;
    private final int opcode;
    private final int arrOpcode;
    private final int fuzzFactor;

    public BeforeFieldAccess(InjectionPointData injectionPointData) {
        int i;
        super(injectionPointData);
        this.opcode = injectionPointData.getOpcode(-1, new int[]{180, 181, 178, 179, -1});
        String str = injectionPointData.get("array", "");
        if ("get".equalsIgnoreCase(str)) {
            i = 46;
        } else if ("set".equalsIgnoreCase(str)) {
            i = 79;
        } else {
            i = ARRAY_LENGTH.equalsIgnoreCase(str) ? 190 : 0;
        }
        this.arrOpcode = i;
        this.fuzzFactor = Math.min(Math.max(injectionPointData.get("fuzz", 8), 1), 32);
    }

    public int getFuzzFactor() {
        return this.fuzzFactor;
    }

    public int getArrayOpcode() {
        return this.arrOpcode;
    }

    private int getArrayOpcode(String str) {
        if (this.arrOpcode != 190) {
            return Type.getType(str).getElementType().getOpcode(this.arrOpcode);
        }
        return this.arrOpcode;
    }

    @Override // org.spongepowered.asm.mixin.injection.points.BeforeInvoke
    protected boolean matchesInsn(AbstractInsnNode abstractInsnNode) {
        if (!(abstractInsnNode instanceof FieldInsnNode)) {
            return false;
        }
        if (((FieldInsnNode) abstractInsnNode).getOpcode() == this.opcode || this.opcode == -1) {
            if (this.arrOpcode == 0) {
                return true;
            }
            return (abstractInsnNode.getOpcode() == 178 || abstractInsnNode.getOpcode() == 180) && Type.getType(((FieldInsnNode) abstractInsnNode).desc).getSort() == 9;
        }
        return false;
    }

    @Override // org.spongepowered.asm.mixin.injection.points.BeforeInvoke
    protected boolean addInsn(InsnList insnList, Collection collection, AbstractInsnNode abstractInsnNode) {
        if (this.arrOpcode > 0) {
            FieldInsnNode fieldInsnNode = (FieldInsnNode) abstractInsnNode;
            int arrayOpcode = getArrayOpcode(fieldInsnNode.desc);
            log("{} > > > > searching for array access opcode {} fuzz={}", this.className, Bytecode.getOpcodeName(arrayOpcode), Integer.valueOf(this.fuzzFactor));
            if (findArrayNode(insnList, fieldInsnNode, arrayOpcode, this.fuzzFactor) == null) {
                log("{} > > > > > failed to locate matching insn", this.className);
                return false;
            }
        }
        log("{} > > > > > adding matching insn", this.className);
        return super.addInsn(insnList, collection, abstractInsnNode);
    }

    public static AbstractInsnNode findArrayNode(InsnList insnList, FieldInsnNode fieldInsnNode, int i, int i2) {
        int i3 = 0;
        ListIterator it = insnList.iterator(insnList.indexOf(fieldInsnNode) + 1);
        while (it.hasNext()) {
            FieldInsnNode fieldInsnNode2 = (AbstractInsnNode) it.next();
            if (fieldInsnNode2.getOpcode() == i) {
                return fieldInsnNode2;
            }
            if (fieldInsnNode2.getOpcode() == 190 && i3 == 0) {
                return null;
            }
            if (fieldInsnNode2 instanceof FieldInsnNode) {
                FieldInsnNode fieldInsnNode3 = fieldInsnNode2;
                if (fieldInsnNode3.desc.equals(fieldInsnNode.desc) && fieldInsnNode3.name.equals(fieldInsnNode.name) && fieldInsnNode3.owner.equals(fieldInsnNode.owner)) {
                    return null;
                }
            }
            int i4 = i3;
            i3++;
            if (i4 > i2) {
                return null;
            }
        }
        return null;
    }
}
