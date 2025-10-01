package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/types/BytecodeBitwiseOps.class */
interface BytecodeBitwiseOps {
    Type shr(MethodVisitor methodVisitor);

    Type sar(MethodVisitor methodVisitor);

    Type shl(MethodVisitor methodVisitor);

    Type and(MethodVisitor methodVisitor);

    /* renamed from: or */
    Type mo6or(MethodVisitor methodVisitor);

    Type xor(MethodVisitor methodVisitor);

    Type cmp(MethodVisitor methodVisitor);
}
