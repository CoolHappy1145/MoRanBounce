package kotlin.text;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SinceKotlin(version = "1.1")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\bg\u0018\ufffd\ufffd2\u00020\u0001J\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6\u0002\u00a8\u0006\u0006"}, m27d2 = {"Lkotlin/text/MatchNamedGroupCollection;", "Lkotlin/text/MatchGroupCollection;", PropertyDescriptor.GET, "Lkotlin/text/MatchGroup;", "name", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/text/MatchNamedGroupCollection.class */
public interface MatchNamedGroupCollection extends MatchGroupCollection {
    @Nullable
    MatchGroup get(@NotNull String str);
}
