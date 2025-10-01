package kotlin;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.internal.InlineOnly;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\f\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u001a\u000f\u0010\ufffd\ufffd\u001a\u00020\u0001*\u0004\u0018\u00010\u0002H\u0087\b\u00a8\u0006\u0003"}, m27d2 = {"hashCode", "", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/HashCodeKt.class */
public final class HashCodeKt {
    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final int hashCode(@Nullable Object obj) {
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }
}
