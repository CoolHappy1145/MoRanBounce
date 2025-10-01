package kotlin.text;

import java.util.Set;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\u0018\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u0002H\u0087\b\u001a\u001b\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0087\b\u001a\u0015\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0005H\u0087\b\u00a8\u0006\u0007"}, m27d2 = {"toRegex", "Lkotlin/text/Regex;", "", "options", "", "Lkotlin/text/RegexOption;", "option", "kotlin-stdlib"}, m28xs = "kotlin/text/StringsKt")
/* loaded from: L-out.jar:kotlin/text/StringsKt__RegexExtensionsKt.class */
class StringsKt__RegexExtensionsKt extends StringsKt__RegexExtensionsJVMKt {
    @InlineOnly
    private static final Regex toRegex(@NotNull String str) {
        return new Regex(str);
    }

    @InlineOnly
    private static final Regex toRegex(@NotNull String str, RegexOption regexOption) {
        return new Regex(str, regexOption);
    }

    @InlineOnly
    private static final Regex toRegex(@NotNull String str, Set set) {
        return new Regex(str, set);
    }
}
