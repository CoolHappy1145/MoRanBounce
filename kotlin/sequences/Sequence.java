package kotlin.sequences;

import java.util.Iterator;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010(\n\ufffd\ufffd\bf\u0018\ufffd\ufffd*\u0006\b\ufffd\ufffd\u0010\u0001 \u00012\u00020\u0002J\u000f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0004H\u00a6\u0002\u00a8\u0006\u0005"}, m27d2 = {"Lkotlin/sequences/Sequence;", "T", "", "iterator", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/sequences/Sequence.class */
public interface Sequence {
    @NotNull
    Iterator iterator();
}
