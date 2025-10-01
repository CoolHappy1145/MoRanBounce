package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.types.Type;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/LocalVariableConversion.class */
public final class LocalVariableConversion {
    private final Symbol symbol;
    private final Type from;

    /* renamed from: to */
    private final Type f19to;
    private final LocalVariableConversion next;

    public LocalVariableConversion(Symbol symbol, Type type, Type type2, LocalVariableConversion localVariableConversion) {
        this.symbol = symbol;
        this.from = type;
        this.f19to = type2;
        this.next = localVariableConversion;
    }

    public Type getFrom() {
        return this.from;
    }

    public Type getTo() {
        return this.f19to;
    }

    public LocalVariableConversion getNext() {
        return this.next;
    }

    public Symbol getSymbol() {
        return this.symbol;
    }

    public boolean isLive() {
        return this.symbol.hasSlotFor(this.f19to);
    }

    public boolean isAnyLive() {
        return isLive() || isAnyLive(this.next);
    }

    public static boolean hasLiveConversion(JoinPredecessor joinPredecessor) {
        return isAnyLive(joinPredecessor.getLocalVariableConversion());
    }

    private static boolean isAnyLive(LocalVariableConversion localVariableConversion) {
        return localVariableConversion != null && localVariableConversion.isAnyLive();
    }

    public String toString() {
        return toString(new StringBuilder()).toString();
    }

    public StringBuilder toString(StringBuilder sb) {
        if (isLive()) {
            return toStringNext(sb.append('\u27e6'), true).append("\u27e7 ");
        }
        return this.next == null ? sb : this.next.toString(sb);
    }

    public static StringBuilder toString(LocalVariableConversion localVariableConversion, StringBuilder sb) {
        return localVariableConversion == null ? sb : localVariableConversion.toString(sb);
    }

    private StringBuilder toStringNext(StringBuilder sb, boolean z) {
        if (!isLive()) {
            return this.next == null ? sb : this.next.toStringNext(sb, z);
        }
        if (!z) {
            sb.append(", ");
        }
        sb.append(this.symbol.getName()).append(':').append(getTypeChar(this.from)).append('\u2192').append(getTypeChar(this.f19to));
        return this.next == null ? sb : this.next.toStringNext(sb, false);
    }

    /*  JADX ERROR: Types fix failed
        java.lang.NullPointerException
        */
    /* JADX WARN: Not initialized variable reg: 5, insn: 0x000b: MOVE (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) (LINE:506), block:B:6:0x000a */
    private static char getTypeChar(jdk.nashorn.internal.codegen.types.Type r4) {
        /*
            r0 = r4
            jdk.nashorn.internal.codegen.types.Type r1 = jdk.nashorn.internal.codegen.types.Type.UNDEFINED
            if (r0 != r1) goto La
            r0 = 85
            return r0
        La:
            r0 = r4
            r1 = r5
            boolean r1 = r1 instanceof jdk.nashorn.internal.codegen.types.ObjectType
            if (r1 == 0) goto L15
            r1 = 79
            return r1
        L15:
            r1 = r4
            jdk.nashorn.internal.codegen.types.Type r2 = jdk.nashorn.internal.codegen.types.Type.BOOLEAN
            if (r1 != r2) goto L1f
            r1 = 90
            return r1
        L1f:
            r1 = r4
            char r1 = r1.getBytecodeStackType()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.p001ir.LocalVariableConversion.getTypeChar(jdk.nashorn.internal.codegen.types.Type):char");
    }
}
