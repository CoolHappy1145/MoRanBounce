package kotlin.text;

import java.io.IOException;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffdT\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\f\n\u0002\u0010\r\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\u0010\u0005\n\u0002\u0010\u0019\n\u0002\u0010\u0006\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\u0010\n\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u001a\u0012\u0010\ufffd\ufffd\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u0002\u001a\u001d\u0010\ufffd\ufffd\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u001f\u0010\ufffd\ufffd\u001a\u00060\u0001j\u0002`\u0002*\u00060\u0001j\u0002`\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0005H\u0087\b\u001a\u0012\u0010\ufffd\ufffd\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u0007\u001a\u001f\u0010\ufffd\ufffd\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\b\u0010\u0003\u001a\u0004\u0018\u00010\bH\u0087\b\u001a\u001f\u0010\ufffd\ufffd\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\b\u0010\u0003\u001a\u0004\u0018\u00010\tH\u0087\b\u001a\u001d\u0010\ufffd\ufffd\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\nH\u0087\b\u001a\u001d\u0010\ufffd\ufffd\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\u000bH\u0087\b\u001a\u001d\u0010\ufffd\ufffd\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u001d\u0010\ufffd\ufffd\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\fH\u0087\b\u001a\u001f\u0010\ufffd\ufffd\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\b\u0010\u0003\u001a\u0004\u0018\u00010\u0005H\u0087\b\u001a\u001d\u0010\ufffd\ufffd\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\rH\u0087\b\u001a\u001d\u0010\ufffd\ufffd\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\u000eH\u0087\b\u001a\u001d\u0010\ufffd\ufffd\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\u000fH\u0087\b\u001a\u001d\u0010\ufffd\ufffd\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\u0010H\u0087\b\u001a\u001d\u0010\ufffd\ufffd\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0003\u001a\u00020\u0011H\u0087\b\u001a\u001f\u0010\ufffd\ufffd\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\b\u0010\u0003\u001a\u0004\u0018\u00010\u0012H\u0087\b\u001a%\u0010\ufffd\ufffd\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u000e\u0010\u0003\u001a\n\u0018\u00010\u0006j\u0004\u0018\u0001`\u0007H\u0087\b\u001a\u0014\u0010\u0013\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u0007H\u0007\u001a!\u0010\u0014\u001a\u00020\u0015*\u00060\u0006j\u0002`\u00072\u0006\u0010\u0016\u001a\u00020\u000f2\u0006\u0010\u0003\u001a\u00020\u0004H\u0087\n\u00a8\u0006\u0017"}, m27d2 = {"appendln", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", PropertyDescriptor.VALUE, "", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "Ljava/lang/StringBuffer;", "", "", "", "", "", "", "", "", "", "", "clear", PropertyDescriptor.SET, "", "index", "kotlin-stdlib"}, m28xs = "kotlin/text/StringsKt")
/* loaded from: L-out.jar:kotlin/text/StringsKt__StringBuilderJVMKt.class */
class StringsKt__StringBuilderJVMKt extends StringsKt__RegexExtensionsKt {
    @InlineOnly
    private static final void set(@NotNull StringBuilder set, int i, char c) {
        Intrinsics.checkParameterIsNotNull(set, "$this$set");
        set.setCharAt(i, c);
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final StringBuilder clear(@NotNull StringBuilder clear) {
        Intrinsics.checkParameterIsNotNull(clear, "$this$clear");
        clear.setLength(0);
        return clear;
    }

    @NotNull
    public static final Appendable appendln(@NotNull Appendable appendln) throws IOException {
        Intrinsics.checkParameterIsNotNull(appendln, "$this$appendln");
        Appendable appendableAppend = appendln.append(SystemProperties.LINE_SEPARATOR);
        Intrinsics.checkExpressionValueIsNotNull(appendableAppend, "append(SystemProperties.LINE_SEPARATOR)");
        return appendableAppend;
    }

    @InlineOnly
    private static final Appendable appendln(@NotNull Appendable appendable, CharSequence charSequence) throws IOException {
        Appendable appendableAppend = appendable.append(charSequence);
        Intrinsics.checkExpressionValueIsNotNull(appendableAppend, "append(value)");
        return StringsKt.appendln(appendableAppend);
    }

    @InlineOnly
    private static final Appendable appendln(@NotNull Appendable appendable, char c) throws IOException {
        Appendable appendableAppend = appendable.append(c);
        Intrinsics.checkExpressionValueIsNotNull(appendableAppend, "append(value)");
        return StringsKt.appendln(appendableAppend);
    }

    @NotNull
    public static final StringBuilder appendln(@NotNull StringBuilder appendln) {
        Intrinsics.checkParameterIsNotNull(appendln, "$this$appendln");
        StringBuilder sbAppend = appendln.append(SystemProperties.LINE_SEPARATOR);
        Intrinsics.checkExpressionValueIsNotNull(sbAppend, "append(SystemProperties.LINE_SEPARATOR)");
        return sbAppend;
    }

    @InlineOnly
    private static final StringBuilder appendln(@NotNull StringBuilder sb, StringBuffer stringBuffer) {
        StringBuilder sbAppend = sb.append(stringBuffer);
        Intrinsics.checkExpressionValueIsNotNull(sbAppend, "append(value)");
        return StringsKt.appendln(sbAppend);
    }

    @InlineOnly
    private static final StringBuilder appendln(@NotNull StringBuilder sb, CharSequence charSequence) {
        StringBuilder sbAppend = sb.append(charSequence);
        Intrinsics.checkExpressionValueIsNotNull(sbAppend, "append(value)");
        return StringsKt.appendln(sbAppend);
    }

    @InlineOnly
    private static final StringBuilder appendln(@NotNull StringBuilder sb, String str) {
        StringBuilder sbAppend = sb.append(str);
        Intrinsics.checkExpressionValueIsNotNull(sbAppend, "append(value)");
        return StringsKt.appendln(sbAppend);
    }

    @InlineOnly
    private static final StringBuilder appendln(@NotNull StringBuilder sb, Object obj) {
        StringBuilder sbAppend = sb.append(obj);
        Intrinsics.checkExpressionValueIsNotNull(sbAppend, "append(value)");
        return StringsKt.appendln(sbAppend);
    }

    @InlineOnly
    private static final StringBuilder appendln(@NotNull StringBuilder sb, StringBuilder sb2) {
        StringBuilder sbAppend = sb.append((CharSequence) sb2);
        Intrinsics.checkExpressionValueIsNotNull(sbAppend, "append(value)");
        return StringsKt.appendln(sbAppend);
    }

    @InlineOnly
    private static final StringBuilder appendln(@NotNull StringBuilder sb, char[] cArr) {
        StringBuilder sbAppend = sb.append(cArr);
        Intrinsics.checkExpressionValueIsNotNull(sbAppend, "append(value)");
        return StringsKt.appendln(sbAppend);
    }

    @InlineOnly
    private static final StringBuilder appendln(@NotNull StringBuilder sb, char c) {
        StringBuilder sbAppend = sb.append(c);
        Intrinsics.checkExpressionValueIsNotNull(sbAppend, "append(value)");
        return StringsKt.appendln(sbAppend);
    }

    @InlineOnly
    private static final StringBuilder appendln(@NotNull StringBuilder sb, boolean z) {
        StringBuilder sbAppend = sb.append(z);
        Intrinsics.checkExpressionValueIsNotNull(sbAppend, "append(value)");
        return StringsKt.appendln(sbAppend);
    }

    @InlineOnly
    private static final StringBuilder appendln(@NotNull StringBuilder sb, int i) {
        StringBuilder sbAppend = sb.append(i);
        Intrinsics.checkExpressionValueIsNotNull(sbAppend, "append(value)");
        return StringsKt.appendln(sbAppend);
    }

    @InlineOnly
    private static final StringBuilder appendln(@NotNull StringBuilder sb, short s) {
        StringBuilder sbAppend = sb.append((int) s);
        Intrinsics.checkExpressionValueIsNotNull(sbAppend, "append(value.toInt())");
        return StringsKt.appendln(sbAppend);
    }

    @InlineOnly
    private static final StringBuilder appendln(@NotNull StringBuilder sb, byte b) {
        StringBuilder sbAppend = sb.append((int) b);
        Intrinsics.checkExpressionValueIsNotNull(sbAppend, "append(value.toInt())");
        return StringsKt.appendln(sbAppend);
    }

    @InlineOnly
    private static final StringBuilder appendln(@NotNull StringBuilder sb, long j) {
        StringBuilder sbAppend = sb.append(j);
        Intrinsics.checkExpressionValueIsNotNull(sbAppend, "append(value)");
        return StringsKt.appendln(sbAppend);
    }

    @InlineOnly
    private static final StringBuilder appendln(@NotNull StringBuilder sb, float f) {
        StringBuilder sbAppend = sb.append(f);
        Intrinsics.checkExpressionValueIsNotNull(sbAppend, "append(value)");
        return StringsKt.appendln(sbAppend);
    }

    @InlineOnly
    private static final StringBuilder appendln(@NotNull StringBuilder sb, double d) {
        StringBuilder sbAppend = sb.append(d);
        Intrinsics.checkExpressionValueIsNotNull(sbAppend, "append(value)");
        return StringsKt.appendln(sbAppend);
    }
}
