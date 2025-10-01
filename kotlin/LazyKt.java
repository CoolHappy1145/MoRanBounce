package kotlin;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 4, m30xi = 1, m26d1 = {"kotlin/LazyKt__LazyJVMKt", "kotlin/LazyKt__LazyKt"})
/* loaded from: L-out.jar:kotlin/LazyKt.class */
public final class LazyKt extends LazyKt__LazyKt {

    @Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:kotlin/LazyKt$WhenMappings.class */
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0 = new int[LazyThreadSafetyMode.values().length];

        static {
            $EnumSwitchMapping$0[LazyThreadSafetyMode.SYNCHRONIZED.ordinal()] = 1;
            $EnumSwitchMapping$0[LazyThreadSafetyMode.PUBLICATION.ordinal()] = 2;
            $EnumSwitchMapping$0[LazyThreadSafetyMode.NONE.ordinal()] = 3;
        }
    }

    private LazyKt() {
    }
}
