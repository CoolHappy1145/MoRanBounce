package kotlin;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.jvm.JvmField;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u00c1\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0016\u0010\u0003\u001a\u00020\u00048\ufffd\ufffdX\u0081\u0004\u00a2\u0006\b\n\ufffd\ufffd\u0012\u0004\b\u0005\u0010\u0002\u00a8\u0006\u0006"}, m27d2 = {"Lkotlin/_Assertions;", "", "()V", "ENABLED", "", "ENABLED$annotations", "kotlin-stdlib"})
@PublishedApi
/* loaded from: L-out.jar:kotlin/_Assertions.class */
public final class _Assertions {

    @JvmField
    public static final boolean ENABLED;
    public static final _Assertions INSTANCE;

    private _Assertions() {
    }

    static {
        _Assertions _assertions = new _Assertions();
        INSTANCE = _assertions;
        ENABLED = _assertions.getClass().desiredAssertionStatus();
    }
}
