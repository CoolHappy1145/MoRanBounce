package org.spongepowered.asm.mixin.struct;

import jdk.internal.dynalink.CallSiteDescriptor;
import kotlin.text.Typography;
import org.apache.log4j.net.SyslogAppender;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.spongepowered.asm.mixin.transformer.throwables.MixinTransformerError;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/struct/MemberRef.class */
public abstract class MemberRef {
    private static final int[] H_OPCODES = {0, 180, 178, 181, 179, Typography.paragraph, SyslogAppender.LOG_LOCAL7, Typography.middleDot, Typography.middleDot, 185};

    public abstract boolean isField();

    public abstract int getOpcode();

    public abstract void setOpcode(int i);

    public abstract String getOwner();

    public abstract void setOwner(String str);

    public abstract String getName();

    public abstract void setName(String str);

    public abstract String getDesc();

    public abstract void setDesc(String str);

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/struct/MemberRef$Method.class */
    public static final class Method extends MemberRef {
        private static final int OPCODES = 191;
        public final MethodInsnNode insn;

        public Method(MethodInsnNode methodInsnNode) {
            this.insn = methodInsnNode;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public int getOpcode() {
            return this.insn.getOpcode();
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setOpcode(int i) {
            if ((i & OPCODES) == 0) {
                throw new IllegalArgumentException("Invalid opcode for method instruction: 0x" + Integer.toHexString(i));
            }
            this.insn.setOpcode(i);
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getOwner() {
            return this.insn.owner;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setOwner(String str) {
            this.insn.owner = str;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getName() {
            return this.insn.name;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setName(String str) {
            this.insn.name = str;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getDesc() {
            return this.insn.desc;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setDesc(String str) {
            this.insn.desc = str;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/struct/MemberRef$Field.class */
    public static final class Field extends MemberRef {
        private static final int OPCODES = 183;
        public final FieldInsnNode insn;

        public Field(FieldInsnNode fieldInsnNode) {
            this.insn = fieldInsnNode;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public int getOpcode() {
            return this.insn.getOpcode();
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setOpcode(int i) {
            if ((i & 183) == 0) {
                throw new IllegalArgumentException("Invalid opcode for field instruction: 0x" + Integer.toHexString(i));
            }
            this.insn.setOpcode(i);
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getOwner() {
            return this.insn.owner;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setOwner(String str) {
            this.insn.owner = str;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getName() {
            return this.insn.name;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setName(String str) {
            this.insn.name = str;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getDesc() {
            return this.insn.desc;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setDesc(String str) {
            this.insn.desc = str;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/struct/MemberRef$Handle.class */
    public static final class Handle extends MemberRef {
        private org.objectweb.asm.Handle handle;

        public Handle(org.objectweb.asm.Handle handle) {
            this.handle = handle;
        }

        public org.objectweb.asm.Handle getMethodHandle() {
            return this.handle;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public boolean isField() {
            switch (this.handle.getTag()) {
                case 1:
                case 2:
                case 3:
                case 4:
                    return true;
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                    return false;
                default:
                    throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + ".");
            }
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public int getOpcode() {
            int iOpcodeFromTag = MemberRef.opcodeFromTag(this.handle.getTag());
            if (iOpcodeFromTag == 0) {
                throw new MixinTransformerError("Invalid tag " + this.handle.getTag() + " for method handle " + this.handle + ".");
            }
            return iOpcodeFromTag;
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setOpcode(int i) {
            int iTagFromOpcode = MemberRef.tagFromOpcode(i);
            if (iTagFromOpcode == 0) {
                throw new MixinTransformerError("Invalid opcode " + Bytecode.getOpcodeName(i) + " for method handle " + this.handle + ".");
            }
            this.handle = new org.objectweb.asm.Handle(iTagFromOpcode, this.handle.getOwner(), this.handle.getName(), this.handle.getDesc(), iTagFromOpcode == 9);
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getOwner() {
            return this.handle.getOwner();
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setOwner(String str) {
            this.handle = new org.objectweb.asm.Handle(this.handle.getTag(), str, this.handle.getName(), this.handle.getDesc(), this.handle.getTag() == 9);
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getName() {
            return this.handle.getName();
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setName(String str) {
            this.handle = new org.objectweb.asm.Handle(this.handle.getTag(), this.handle.getOwner(), str, this.handle.getDesc(), this.handle.getTag() == 9);
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public String getDesc() {
            return this.handle.getDesc();
        }

        @Override // org.spongepowered.asm.mixin.struct.MemberRef
        public void setDesc(String str) {
            this.handle = new org.objectweb.asm.Handle(this.handle.getTag(), this.handle.getOwner(), this.handle.getName(), str, this.handle.getTag() == 9);
        }
    }

    public String toString() {
        Object[] objArr = new Object[5];
        objArr[0] = Bytecode.getOpcodeName(getOpcode());
        objArr[1] = getOwner();
        objArr[2] = getName();
        objArr[3] = isField() ? CallSiteDescriptor.TOKEN_DELIMITER : "";
        objArr[4] = getDesc();
        return String.format("%s for %s.%s%s%s", objArr);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof MemberRef)) {
            return false;
        }
        MemberRef memberRef = (MemberRef) obj;
        return getOpcode() == memberRef.getOpcode() && getOwner().equals(memberRef.getOwner()) && getName().equals(memberRef.getName()) && getDesc().equals(memberRef.getDesc());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    static int opcodeFromTag(int i) {
        if (i < 0 || i >= H_OPCODES.length) {
            return 0;
        }
        return H_OPCODES[i];
    }

    static int tagFromOpcode(int i) {
        for (int i2 = 1; i2 < H_OPCODES.length; i2++) {
            if (H_OPCODES[i2] == i) {
                return i2;
            }
        }
        return 0;
    }
}
