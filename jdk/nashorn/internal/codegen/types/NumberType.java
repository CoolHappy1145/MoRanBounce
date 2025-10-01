package jdk.nashorn.internal.codegen.types;

import jdk.internal.org.objectweb.asm.MethodVisitor;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.tools.Shell;
import org.apache.log4j.net.SyslogAppender;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/types/NumberType.class */
class NumberType extends NumericType {
    private static final long serialVersionUID = 1;
    private static final CompilerConstants.Call VALUE_OF;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !NumberType.class.desiredAssertionStatus();
        VALUE_OF = CompilerConstants.staticCallNoLookup(Double.class, "valueOf", Double.class, new Class[]{Double.TYPE});
    }

    protected NumberType() {
        super("double", Double.TYPE, 4, 2);
    }

    @Override // jdk.nashorn.internal.codegen.types.Type
    public Type nextWider() {
        return OBJECT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type cmp(MethodVisitor methodVisitor, boolean z) {
        methodVisitor.visitInsn(z ? SyslogAppender.LOG_LOCAL3 : 151);
        return INT;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type load(MethodVisitor methodVisitor, int i) {
        if (!$assertionsDisabled && i == -1) {
            throw new AssertionError();
        }
        methodVisitor.visitVarInsn(24, i);
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public void store(MethodVisitor methodVisitor, int i) {
        if (!$assertionsDisabled && i == -1) {
            throw new AssertionError();
        }
        methodVisitor.visitVarInsn(57, i);
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type loadUndefined(MethodVisitor methodVisitor) {
        methodVisitor.visitLdcInsn(Double.valueOf(Double.NaN));
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type loadForcedInitializer(MethodVisitor methodVisitor) {
        methodVisitor.visitInsn(14);
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type ldc(MethodVisitor methodVisitor, Object obj) {
        if (!$assertionsDisabled && !(obj instanceof Double)) {
            throw new AssertionError();
        }
        double dDoubleValue = ((Double) obj).doubleValue();
        if (Double.doubleToLongBits(dDoubleValue) == 0) {
            methodVisitor.visitInsn(14);
        } else if (dDoubleValue == 1.0d) {
            methodVisitor.visitInsn(15);
        } else {
            methodVisitor.visitLdcInsn(Double.valueOf(dDoubleValue));
        }
        return NUMBER;
    }

    /*  JADX ERROR: Types fix failed
        java.lang.NullPointerException
        */
    /* JADX WARN: Not initialized variable reg: 9, insn: 0x004f: MOVE (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r9 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) (LINE:506), block:B:18:0x004e */
    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public jdk.nashorn.internal.codegen.types.Type convert(jdk.internal.org.objectweb.asm.MethodVisitor r7, jdk.nashorn.internal.codegen.types.Type r8) {
        /*
            r6 = this;
            r0 = r6
            r1 = r8
            boolean r0 = r0.isEquivalentTo(r1)
            if (r0 == 0) goto La
            r0 = 0
            return r0
        La:
            r0 = r8
            boolean r0 = r0.isInteger()
            if (r0 == 0) goto L1b
            r0 = r7
            jdk.nashorn.internal.codegen.CompilerConstants$Call r1 = jdk.nashorn.internal.runtime.JSType.TO_INT32_D
            invokestatic(r0, r1)
            goto L84
        L1b:
            r0 = r8
            boolean r0 = r0.isLong()
            if (r0 == 0) goto L2c
            r0 = r7
            jdk.nashorn.internal.codegen.CompilerConstants$Call r1 = jdk.nashorn.internal.runtime.JSType.TO_LONG_D
            invokestatic(r0, r1)
            goto L84
        L2c:
            r0 = r8
            boolean r0 = r0.isBoolean()
            if (r0 == 0) goto L3d
            r0 = r7
            jdk.nashorn.internal.codegen.CompilerConstants$Call r1 = jdk.nashorn.internal.runtime.JSType.TO_BOOLEAN_D
            invokestatic(r0, r1)
            goto L84
        L3d:
            r0 = r8
            boolean r0 = r0.isString()
            if (r0 == 0) goto L4e
            r0 = r7
            jdk.nashorn.internal.codegen.CompilerConstants$Call r1 = jdk.nashorn.internal.runtime.JSType.TO_STRING_D
            invokestatic(r0, r1)
            goto L84
        L4e:
            r0 = r8
            r1 = r9
            boolean r1 = r1 instanceof jdk.nashorn.internal.codegen.types.ObjectType
            if (r1 == 0) goto L60
            r1 = r7
            jdk.nashorn.internal.codegen.CompilerConstants$Call r2 = jdk.nashorn.internal.codegen.types.NumberType.VALUE_OF
            invokestatic(r1, r2)
            goto L84
        L60:
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
        L84:
            r0 = r8
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.codegen.types.NumberType.convert(jdk.internal.org.objectweb.asm.MethodVisitor, jdk.nashorn.internal.codegen.types.Type):jdk.nashorn.internal.codegen.types.Type");
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public Type add(MethodVisitor methodVisitor, int i) {
        methodVisitor.visitInsn(99);
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type sub(MethodVisitor methodVisitor, int i) {
        methodVisitor.visitInsn(Shell.IO_ERROR);
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type mul(MethodVisitor methodVisitor, int i) {
        methodVisitor.visitInsn(107);
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type div(MethodVisitor methodVisitor, int i) {
        methodVisitor.visitInsn(111);
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type rem(MethodVisitor methodVisitor, int i) {
        methodVisitor.visitInsn(115);
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeNumericOps
    public Type neg(MethodVisitor methodVisitor, int i) {
        methodVisitor.visitInsn(119);
        return NUMBER;
    }

    @Override // jdk.nashorn.internal.codegen.types.BytecodeOps
    public void _return(MethodVisitor methodVisitor) {
        methodVisitor.visitInsn(175);
    }
}
