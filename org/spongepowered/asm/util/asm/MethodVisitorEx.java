package org.spongepowered.asm.util.asm;

import org.objectweb.asm.MethodVisitor;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: L-out.jar:org/spongepowered/asm/util/asm/MethodVisitorEx.class */
public class MethodVisitorEx extends MethodVisitor {
    public MethodVisitorEx(MethodVisitor methodVisitor) {
        super(ASM.API_VERSION, methodVisitor);
    }

    public void visitConstant(byte b) {
        if (b > -2 && b < 6) {
            visitInsn(Bytecode.CONSTANTS_INT[b + 1]);
        } else {
            visitIntInsn(16, b);
        }
    }
}
