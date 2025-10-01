package kotlin.text;

import java.util.SortedSet;
import java.util.TreeSet;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\u0018\n\ufffd\ufffd\n\u0002\u0010\f\n\u0002\u0010\r\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u001a\u0015\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0010\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006*\u00020\u0002\u00a8\u0006\u0007"}, m27d2 = {"elementAt", "", "", "index", "", "toSortedSet", "Ljava/util/SortedSet;", "kotlin-stdlib"}, m28xs = "kotlin/text/StringsKt")
/* loaded from: L-out.jar:kotlin/text/StringsKt___StringsJvmKt.class */
class StringsKt___StringsJvmKt extends StringsKt__StringsKt {
    @InlineOnly
    private static final char elementAt(@NotNull CharSequence charSequence, int i) {
        return charSequence.charAt(i);
    }

    @NotNull
    public static final SortedSet toSortedSet(@NotNull CharSequence toSortedSet) {
        Intrinsics.checkParameterIsNotNull(toSortedSet, "$this$toSortedSet");
        return (SortedSet) StringsKt.toCollection(toSortedSet, new TreeSet());
    }
}
