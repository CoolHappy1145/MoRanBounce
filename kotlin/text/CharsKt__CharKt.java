package kotlin.text;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\u0010\f\n\u0002\b\u0004\n\u0002\u0010\u000e\n\ufffd\ufffd\u001a\u001c\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00022\b\b\u0002\u0010\u0004\u001a\u00020\u0001\u001a\n\u0010\u0005\u001a\u00020\u0001*\u00020\u0002\u001a\u0015\u0010\u0006\u001a\u00020\u0007*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0007H\u0087\n\u00a8\u0006\b"}, m27d2 = {"equals", "", "", "other", "ignoreCase", "isSurrogate", "plus", "", "kotlin-stdlib"}, m28xs = "kotlin/text/CharsKt")
/* loaded from: L-out.jar:kotlin/text/CharsKt__CharKt.class */
class CharsKt__CharKt extends CharsKt__CharJVMKt {
    @InlineOnly
    private static final String plus(char c, String str) {
        return String.valueOf(c) + str;
    }

    public static boolean equals$default(char c, char c2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return CharsKt.equals(c, c2, z);
    }

    public static final boolean equals(char c, char c2, boolean z) {
        if (c == c2) {
            return true;
        }
        if (!z) {
            return false;
        }
        if (Character.toUpperCase(c) == Character.toUpperCase(c2) || Character.toLowerCase(c) == Character.toLowerCase(c2)) {
            return true;
        }
        return false;
    }
}
