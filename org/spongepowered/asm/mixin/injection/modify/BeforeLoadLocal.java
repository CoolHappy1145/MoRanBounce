package org.spongepowered.asm.mixin.injection.modify;

import java.util.Collection;
import java.util.ListIterator;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.VarInsnNode;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.modify.ModifyVariableInjector;
import org.spongepowered.asm.mixin.injection.struct.InjectionPointData;
import org.spongepowered.asm.mixin.injection.struct.Target;

@InjectionPoint.AtCode("LOAD")
/* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/modify/BeforeLoadLocal.class */
public class BeforeLoadLocal extends ModifyVariableInjector.ContextualInjectionPoint {
    private final Type returnType;
    private final LocalVariableDiscriminator discriminator;
    private final int opcode;
    private final int ordinal;
    private boolean opcodeAfter;

    @Override // org.spongepowered.asm.mixin.injection.modify.ModifyVariableInjector.ContextualInjectionPoint, org.spongepowered.asm.mixin.injection.InjectionPoint
    public boolean find(String str, InsnList insnList, Collection collection) {
        return super.find(str, insnList, collection);
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/injection/modify/BeforeLoadLocal$SearchState.class */
    static class SearchState {
        private final boolean print;
        private final int targetOrdinal;
        private int ordinal = 0;
        private boolean pendingCheck = false;
        private boolean found = false;
        private VarInsnNode varNode;

        SearchState(int i, boolean z) {
            this.targetOrdinal = i;
            this.print = z;
        }

        boolean success() {
            return this.found;
        }

        boolean isPendingCheck() {
            return this.pendingCheck;
        }

        void setPendingCheck() {
            this.pendingCheck = true;
        }

        void register(VarInsnNode varInsnNode) {
            this.varNode = varInsnNode;
        }

        void check(Collection collection, AbstractInsnNode abstractInsnNode, int i) {
            this.pendingCheck = false;
            if (i != this.varNode.var && (i > -2 || !this.print)) {
                return;
            }
            if (this.targetOrdinal == -1 || this.targetOrdinal == this.ordinal) {
                collection.add(abstractInsnNode);
                this.found = true;
            }
            this.ordinal++;
            this.varNode = null;
        }
    }

    protected BeforeLoadLocal(InjectionPointData injectionPointData) {
        this(injectionPointData, 21, false);
    }

    protected BeforeLoadLocal(InjectionPointData injectionPointData, int i, boolean z) {
        super(injectionPointData.getContext());
        this.returnType = injectionPointData.getMethodReturnType();
        this.discriminator = injectionPointData.getLocalVariableDiscriminator();
        this.opcode = injectionPointData.getOpcode(this.returnType.getOpcode(i));
        this.ordinal = injectionPointData.getOrdinal();
        this.opcodeAfter = z;
    }

    @Override // org.spongepowered.asm.mixin.injection.modify.ModifyVariableInjector.ContextualInjectionPoint
    boolean find(Target target, Collection collection) {
        SearchState searchState = new SearchState(this.ordinal, this.discriminator.printLVT());
        ListIterator it = target.method.instructions.iterator();
        while (it.hasNext()) {
            AbstractInsnNode abstractInsnNode = (AbstractInsnNode) it.next();
            if (searchState.isPendingCheck()) {
                searchState.check(collection, abstractInsnNode, this.discriminator.findLocal(this.returnType, this.discriminator.isArgsOnly(), target, abstractInsnNode));
            } else if ((abstractInsnNode instanceof VarInsnNode) && abstractInsnNode.getOpcode() == this.opcode && (this.ordinal == -1 || !searchState.success())) {
                searchState.register((VarInsnNode) abstractInsnNode);
                if (this.opcodeAfter) {
                    searchState.setPendingCheck();
                } else {
                    searchState.check(collection, abstractInsnNode, this.discriminator.findLocal(this.returnType, this.discriminator.isArgsOnly(), target, abstractInsnNode));
                }
            }
        }
        return searchState.success();
    }
}
