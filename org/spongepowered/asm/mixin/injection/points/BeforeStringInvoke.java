package org.spongepowered.asm.mixin.injection.points;

import java.util.Collection;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.LdcInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.selectors.ITargetSelectorByName;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;

@InjectionPoint.AtCode("INVOKE_STRING")
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/points/BeforeStringInvoke.class */
public class BeforeStringInvoke extends BeforeInvoke {
    private static final String STRING_VOID_SIG = "(Ljava/lang/String;)V";
    private final String ldcValue;
    private boolean foundLdc;

    public BeforeStringInvoke(InjectionPointData injectionPointData) {
        super(injectionPointData);
        this.ldcValue = injectionPointData.get("ldc", (String) null);
        if (this.ldcValue == null) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " requires named argument \"ldc\" to specify the desired target");
        }
        if (!(this.target instanceof ITargetSelectorByName) || !STRING_VOID_SIG.equals(((ITargetSelectorByName) this.target).getDesc())) {
            throw new IllegalArgumentException(getClass().getSimpleName() + " requires target method with with signature " + STRING_VOID_SIG);
        }
    }

    @Override // org.spongepowered.asm.mixin.injection.points.BeforeInvoke, org.spongepowered.asm.mixin.injection.InjectionPoint
    public boolean find(String str, InsnList insnList, Collection collection) {
        this.foundLdc = false;
        return super.find(str, insnList, collection);
    }

    @Override // org.spongepowered.asm.mixin.injection.points.BeforeInvoke
    protected void inspectInsn(String str, InsnList insnList, AbstractInsnNode abstractInsnNode) {
        if (abstractInsnNode instanceof LdcInsnNode) {
            LdcInsnNode ldcInsnNode = (LdcInsnNode) abstractInsnNode;
            if ((ldcInsnNode.cst instanceof String) && this.ldcValue.equals(ldcInsnNode.cst)) {
                log("{} > found a matching LDC with value {}", this.className, ldcInsnNode.cst);
                this.foundLdc = true;
                return;
            }
        }
        this.foundLdc = false;
    }

    @Override // org.spongepowered.asm.mixin.injection.points.BeforeInvoke
    protected boolean matchesOrdinal(int i) {
        log("{} > > found LDC \"{}\" = {}", this.className, this.ldcValue, Boolean.valueOf(this.foundLdc));
        return this.foundLdc && super.matchesOrdinal(i);
    }
}
