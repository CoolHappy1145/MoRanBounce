package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.tools.Shell;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/types/IntType.class */
class IntType extends BitwiseType {
    private static final long serialVersionUID = 1;
    private static final CompilerConstants.Call TO_STRING;
    private static final CompilerConstants.Call VALUE_OF;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !IntType.class.desiredAssertionStatus();
        TO_STRING = CompilerConstants.staticCallNoLookup(Integer.class, "toString", String.class, new Class[]{Integer.TYPE});
        VALUE_OF = CompilerConstants.staticCallNoLookup(Integer.class, "valueOf", Integer.class, new Class[]{Integer.TYPE});
    }

    protected IntType() {
        super("int", Integer.TYPE, 2, 1);
    }

    @Override // jdk.nashorn.internal.codegen.types.Type
    public Type nextWider() {
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type ldc(MethodVisitor methodVisitor, Object obj) {
        if (!$assertionsDisabled && !(obj instanceof Integer)) {
            throw new AssertionError();
        }
        int iIntValue = ((Integer) obj).intValue();
        switch (iIntValue) {
            case -1:
                methodVisitor.visitInsn(2);
                break;
            case 0:
                methodVisitor.visitInsn(3);
                break;
            case 1:
                methodVisitor.visitInsn(4);
                break;
            case 2:
                methodVisitor.visitInsn(5);
                break;
            case 3:
                methodVisitor.visitInsn(6);
                break;
            case 4:
                methodVisitor.visitInsn(7);
                break;
            case 5:
                methodVisitor.visitInsn(8);
                break;
            default:
                if (iIntValue == ((byte) iIntValue)) {
                    methodVisitor.visitIntInsn(16, iIntValue);
                    break;
                } else if (iIntValue == ((short) iIntValue)) {
                    methodVisitor.visitIntInsn(17, iIntValue);
                    break;
                } else {
                    methodVisitor.visitLdcInsn(obj);
                    break;
                }
        }
        return Type.INT;
    }

    /*  JADX ERROR: Types fix failed
        java.lang.NullPointerException
        */
    /* JADX WARN: Not initialized variable reg: 9, insn: 0x0048: MOVE (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r9 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) (LINE:506), block:B:18:0x0047 */
    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public jdk.nashorn.internal.codegen.types.Type convert(jdk.internal.org.objectweb.asm.MethodVisitor r7, jdk.nashorn.internal.codegen.types.Type r8) {
        /*
            r6 = this;
            r0 = r8
            r1 = r6
            boolean r0 = r0.isEquivalentTo(r1)
            if (r0 == 0) goto La
            r0 = r8
            return r0
        La:
            r0 = r8
            boolean r0 = r0.isNumber()
            if (r0 == 0) goto L1b
            r0 = r7
            r1 = 135(0x87, float:1.89E-43)
            r0.visitInsn(r1)
            goto L7d
        L1b:
            r0 = r8
            boolean r0 = r0.isLong()
            if (r0 == 0) goto L2c
            r0 = r7
            r1 = 133(0x85, float:1.86E-43)
            r0.visitInsn(r1)
            goto L7d
        L2c:
            r0 = r8
            boolean r0 = r0.isBoolean()
            if (r0 == 0) goto L36
            goto L7d
        L36:
            r0 = r8
            boolean r0 = r0.isString()
            if (r0 == 0) goto L47
            r0 = r7
            jdk.nashorn.internal.codegen.CompilerConstants$Call r1 = jdk.nashorn.internal.codegen.types.IntType.TO_STRING
            invokestatic(r0, r1)
            goto L7d
        L47:
            r0 = r8
            r1 = r9
            boolean r1 = r1 instanceof jdk.nashorn.internal.codegen.types.ObjectType
            if (r1 == 0) goto L59
            r1 = r7
            jdk.nashorn.internal.codegen.CompilerConstants$Call r2 = jdk.nashorn.internal.codegen.types.IntType.VALUE_OF
            invokestatic(r1, r2)
            goto L7d
        L59:
            java.lang.UnsupportedOperationException r1 = new java.lang.UnsupportedOperationException
            r2 = r1
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r4 = r3
            r4.<init>()
            java.lang.String r4 = "Illegal conversion "
            java.lang.StringBuilder r3 = r3.append(r4)
            r4 = r6
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r4 = " -> "
            java.lang.StringBuilder r3 = r3.append(r4)
            r4 = r8
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.String r3 = r3.toString()
            r2.<init>(r3)
            throw r1
        L7d:
            r0 = r8
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.codegen.types.IntType.convert(jdk.internal.org.objectweb.asm.MethodVisitor, jdk.nashorn.internal.codegen.types.Type):jdk.nashorn.internal.codegen.types.Type");
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type add(MethodVisitor methodVisitor, int i) {
        if (i == -1) {
            methodVisitor.visitInsn(96);
        } else {
            methodVisitor.visitInvokeDynamicInsn("iadd", "(II)I", MATHBOOTSTRAP, new Object[]{Integer.valueOf(i)});
        }
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeBitwiseOps
    public Type shr(MethodVisitor methodVisitor) {
        methodVisitor.visitInsn(124);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeBitwiseOps
    public Type sar(MethodVisitor methodVisitor) {
        methodVisitor.visitInsn(122);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeBitwiseOps
    public Type shl(MethodVisitor methodVisitor) {
        methodVisitor.visitInsn(120);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeBitwiseOps
    public Type and(MethodVisitor methodVisitor) {
        methodVisitor.visitInsn(126);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeBitwiseOps
    /* renamed from: or */
    public Type mo6or(MethodVisitor methodVisitor) {
        methodVisitor.visitInsn(128);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeBitwiseOps
    public Type xor(MethodVisitor methodVisitor) {
        methodVisitor.visitInsn(130);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type load(MethodVisitor methodVisitor, int i) {
        if (!$assertionsDisabled && i == -1) {
            throw new AssertionError();
        }
        methodVisitor.visitVarInsn(21, i);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public void store(MethodVisitor methodVisitor, int i) {
        if (!$assertionsDisabled && i == -1) {
            throw new AssertionError();
        }
        methodVisitor.visitVarInsn(54, i);
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type sub(MethodVisitor methodVisitor, int i) {
        if (i == -1) {
            methodVisitor.visitInsn(100);
        } else {
            methodVisitor.visitInvokeDynamicInsn("isub", "(II)I", MATHBOOTSTRAP, new Object[]{Integer.valueOf(i)});
        }
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type mul(MethodVisitor methodVisitor, int i) {
        if (i == -1) {
            methodVisitor.visitInsn(Shell.INTERNAL_ERROR);
        } else {
            methodVisitor.visitInvokeDynamicInsn("imul", "(II)I", MATHBOOTSTRAP, new Object[]{Integer.valueOf(i)});
        }
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type div(MethodVisitor methodVisitor, int i) {
        if (i == -1) {
            JSType.DIV_ZERO.invoke(methodVisitor);
        } else {
            methodVisitor.visitInvokeDynamicInsn("idiv", "(II)I", MATHBOOTSTRAP, new Object[]{Integer.valueOf(i)});
        }
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type rem(MethodVisitor methodVisitor, int i) {
        if (i == -1) {
            JSType.REM_ZERO.invoke(methodVisitor);
        } else {
            methodVisitor.visitInvokeDynamicInsn("irem", "(II)I", MATHBOOTSTRAP, new Object[]{Integer.valueOf(i)});
        }
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type neg(MethodVisitor methodVisitor, int i) {
        if (i == -1) {
            methodVisitor.visitInsn(116);
        } else {
            methodVisitor.visitInvokeDynamicInsn("ineg", "(I)I", MATHBOOTSTRAP, new Object[]{Integer.valueOf(i)});
        }
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public void _return(MethodVisitor methodVisitor) {
        methodVisitor.visitInsn(172);
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type loadUndefined(MethodVisitor methodVisitor) {
        methodVisitor.visitLdcInsn(0);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type loadForcedInitializer(MethodVisitor methodVisitor) {
        methodVisitor.visitInsn(3);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type cmp(MethodVisitor methodVisitor, boolean z) {
        throw new UnsupportedOperationException("cmp" + (z ? 'g' : 'l'));
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeBitwiseOps
    public Type cmp(MethodVisitor methodVisitor) {
        throw new UnsupportedOperationException("cmp");
    }
}
