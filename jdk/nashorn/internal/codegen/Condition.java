package jdk.nashorn.internal.codegen;

import kotlin.text.Typography;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/Condition.class */
enum Condition {
    EQ,
    NE,
    LE,
    LT,
    GE,
    GT;

    /* renamed from: jdk.nashorn.internal.codegen.Condition$1 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/Condition$1.class */
    static /* synthetic */ class C01211 {
        static final int[] $SwitchMap$jdk$nashorn$internal$codegen$Condition = new int[Condition.values().length];

        static {
            try {
                $SwitchMap$jdk$nashorn$internal$codegen$Condition[Condition.EQ.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$codegen$Condition[Condition.NE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$codegen$Condition[Condition.LE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$codegen$Condition[Condition.LT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$codegen$Condition[Condition.GE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$codegen$Condition[Condition.GT.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    static int toUnary(Condition condition) {
        switch (C01211.$SwitchMap$jdk$nashorn$internal$codegen$Condition[condition.ordinal()]) {
            case 1:
                return 153;
            case 2:
                return 154;
            case 3:
                return 158;
            case 4:
                return 155;
            case 5:
                return 156;
            case 6:
                return 157;
            default:
                throw new UnsupportedOperationException("toUnary:" + condition.toString());
        }
    }

    static int toBinary(Condition condition, boolean z) {
        switch (C01211.$SwitchMap$jdk$nashorn$internal$codegen$Condition[condition.ordinal()]) {
            case 1:
                return z ? 165 : 159;
            case 2:
                return z ? 166 : 160;
            case 3:
                return 164;
            case 4:
                return 161;
            case 5:
                return Typography.cent;
            case 6:
                return Typography.pound;
            default:
                throw new UnsupportedOperationException("toBinary:" + condition.toString());
        }
    }
}
