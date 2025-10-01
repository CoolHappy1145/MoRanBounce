package kotlin.text;

import java.io.IOException;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffdB\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0010\r\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0007\u001a.\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u001b\u0010\u0004\u001a\u0017\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\u0002\b\tH\u0087\b\u001a&\u0010\ufffd\ufffd\u001a\u00020\u00012\u001b\u0010\u0004\u001a\u0017\u0012\b\u0012\u00060\u0006j\u0002`\u0007\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\u0002\b\tH\u0087\b\u001a5\u0010\n\u001a\u0002H\u000b\"\f\b\ufffd\ufffd\u0010\u000b*\u00060\fj\u0002`\r*\u0002H\u000b2\u0016\u0010\u000e\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00100\u000f\"\u0004\u0018\u00010\u0010\u00a2\u0006\u0002\u0010\u0011\u001a/\u0010\n\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0016\u0010\u000e\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00120\u000f\"\u0004\u0018\u00010\u0012\u00a2\u0006\u0002\u0010\u0013\u001a/\u0010\n\u001a\u00060\u0006j\u0002`\u0007*\u00060\u0006j\u0002`\u00072\u0016\u0010\u000e\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010\u00010\u000f\"\u0004\u0018\u00010\u0001\u00a2\u0006\u0002\u0010\u0014\u001a9\u0010\u0015\u001a\u00020\b\"\u0004\b\ufffd\ufffd\u0010\u000b*\u00060\fj\u0002`\r2\u0006\u0010\u0016\u001a\u0002H\u000b2\u0014\u0010\u0017\u001a\u0010\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u0005H\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0018\u00a8\u0006\u0019"}, m27d2 = {"buildString", "", "capacity", "", "builderAction", "Lkotlin/Function1;", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "", "Lkotlin/ExtensionFunctionType;", "append", "T", "Ljava/lang/Appendable;", "Lkotlin/text/Appendable;", PropertyDescriptor.VALUE, "", "", "(Ljava/lang/Appendable;[Ljava/lang/CharSequence;)Ljava/lang/Appendable;", "", "(Ljava/lang/StringBuilder;[Ljava/lang/Object;)Ljava/lang/StringBuilder;", "(Ljava/lang/StringBuilder;[Ljava/lang/String;)Ljava/lang/StringBuilder;", "appendElement", "element", "transform", "(Ljava/lang/Appendable;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "kotlin-stdlib"}, m28xs = "kotlin/text/StringsKt")
/* loaded from: L-out.jar:kotlin/text/StringsKt__StringBuilderKt.class */
class StringsKt__StringBuilderKt extends StringsKt__StringBuilderJVMKt {
    @InlineOnly
    private static final String buildString(Function1 function1) {
        StringBuilder sb = new StringBuilder();
        function1.invoke(sb);
        String string = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder().apply(builderAction).toString()");
        return string;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final String buildString(int i, Function1 function1) {
        StringBuilder sb = new StringBuilder(i);
        function1.invoke(sb);
        String string = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder(capacity).\u2026builderAction).toString()");
        return string;
    }

    @NotNull
    public static final Appendable append(@NotNull Appendable append, @NotNull CharSequence[] value) throws IOException {
        Intrinsics.checkParameterIsNotNull(append, "$this$append");
        Intrinsics.checkParameterIsNotNull(value, "value");
        for (CharSequence charSequence : value) {
            append.append(charSequence);
        }
        return append;
    }

    @NotNull
    public static final StringBuilder append(@NotNull StringBuilder append, @NotNull String[] value) {
        Intrinsics.checkParameterIsNotNull(append, "$this$append");
        Intrinsics.checkParameterIsNotNull(value, "value");
        for (String str : value) {
            append.append(str);
        }
        return append;
    }

    @NotNull
    public static final StringBuilder append(@NotNull StringBuilder append, @NotNull Object[] value) {
        Intrinsics.checkParameterIsNotNull(append, "$this$append");
        Intrinsics.checkParameterIsNotNull(value, "value");
        for (Object obj : value) {
            append.append(obj);
        }
        return append;
    }

    public static final void appendElement(@NotNull Appendable appendElement, Object obj, @Nullable Function1 function1) throws IOException {
        Intrinsics.checkParameterIsNotNull(appendElement, "$this$appendElement");
        if (function1 == null) {
            if (!(obj != null ? obj instanceof CharSequence : true)) {
                if (!(obj instanceof Character)) {
                    appendElement.append(String.valueOf(obj));
                    return;
                } else {
                    appendElement.append(((Character) obj).charValue());
                    return;
                }
            }
            appendElement.append((CharSequence) obj);
            return;
        }
        appendElement.append((CharSequence) function1.invoke(obj));
    }
}
