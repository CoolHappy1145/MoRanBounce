package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.nashorn.internal.codegen.CompilerConstants;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/types/LongType.class */
class LongType extends Type {
    private static final long serialVersionUID = 1;
    private static final CompilerConstants.Call VALUE_OF;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !LongType.class.desiredAssertionStatus();
        VALUE_OF = CompilerConstants.staticCallNoLookup(Long.class, "valueOf", Long.class, new Class[]{Long.TYPE});
    }

    protected LongType(String str) {
        super(str, Long.TYPE, 3, 2);
    }

    protected LongType() {
        this("long");
    }

    @Override // jdk.nashorn.internal.codegen.types.Type
    public Type nextWider() {
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type load(MethodVisitor methodVisitor, int i) {
        if (!$assertionsDisabled && i == -1) {
            throw new AssertionError();
        }
        methodVisitor.visitVarInsn(22, i);
        return LONG;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public void store(MethodVisitor methodVisitor, int i) {
        if (!$assertionsDisabled && i == -1) {
            throw new AssertionError();
        }
        methodVisitor.visitVarInsn(55, i);
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type ldc(MethodVisitor methodVisitor, Object obj) {
        if (!$assertionsDisabled && !(obj instanceof Long)) {
            throw new AssertionError();
        }
        long jLongValue = ((Long) obj).longValue();
        if (jLongValue == 0) {
            methodVisitor.visitInsn(9);
        } else if (jLongValue == serialVersionUID) {
            methodVisitor.visitInsn(10);
        } else {
            methodVisitor.visitLdcInsn(obj);
        }
        return Type.LONG;
    }

    /*  JADX ERROR: Types fix failed
        java.lang.NullPointerException
        */
    /* JADX WARN: Not initialized variable reg: 9, insn: 0x003e: MOVE (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r9 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) (LINE:506), block:B:15:0x003d */
    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public jdk.nashorn.internal.codegen.types.Type convert(jdk.internal.org.objectweb.asm.MethodVisitor r7, jdk.nashorn.internal.codegen.types.Type r8) {
        /*
            r6 = this;
            r0 = r6
            r1 = r8
            boolean r0 = r0.isEquivalentTo(r1)
            if (r0 == 0) goto La
            r0 = r8
            return r0
        La:
            r0 = r8
            boolean r0 = r0.isNumber()
            if (r0 == 0) goto L1b
            r0 = r7
            r1 = 138(0x8a, float:1.93E-43)
            r0.visitInsn(r1)
            goto L79
        L1b:
            r0 = r8
            boolean r0 = r0.isInteger()
            if (r0 == 0) goto L2c
            r0 = r7
            jdk.nashorn.internal.codegen.CompilerConstants$Call r1 = jdk.nashorn.internal.runtime.JSType.TO_INT32_L
            invokestatic(r0, r1)
            goto L79
        L2c:
            r0 = r8
            boolean r0 = r0.isBoolean()
            if (r0 == 0) goto L3d
            r0 = r7
            r1 = 136(0x88, float:1.9E-43)
            r0.visitInsn(r1)
            goto L79
        L3d:
            r0 = r8
            r1 = r9
            boolean r1 = r1 instanceof jdk.nashorn.internal.codegen.types.ObjectType
            if (r1 == 0) goto L4f
            r1 = r7
            jdk.nashorn.internal.codegen.CompilerConstants$Call r2 = jdk.nashorn.internal.codegen.types.LongType.VALUE_OF
            invokestatic(r1, r2)
            goto L79
        L4f:
            boolean r1 = jdk.nashorn.internal.codegen.types.LongType.$assertionsDisabled
            if (r1 != 0) goto L79
            java.lang.AssertionError r1 = new java.lang.AssertionError
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
        L79:
            r0 = r8
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.codegen.types.LongType.convert(jdk.internal.org.objectweb.asm.MethodVisitor, jdk.nashorn.internal.codegen.types.Type):jdk.nashorn.internal.codegen.types.Type");
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type add(MethodVisitor methodVisitor, int i) {
        if (i == -1) {
            methodVisitor.visitInsn(97);
        } else {
            methodVisitor.visitInvokeDynamicInsn("ladd", "(JJ)J", MATHBOOTSTRAP, new Object[]{Integer.valueOf(i)});
        }
        return LONG;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public void _return(MethodVisitor methodVisitor) {
        methodVisitor.visitInsn(173);
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type loadUndefined(MethodVisitor methodVisitor) {
        methodVisitor.visitLdcInsn(0L);
        return LONG;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type loadForcedInitializer(MethodVisitor methodVisitor) {
        methodVisitor.visitInsn(9);
        return LONG;
    }
}
