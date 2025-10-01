package kotlin.text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\u001e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000b\u001a!\u0010\ufffd\ufffd\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0002\u00a2\u0006\u0002\b\u0004\u001a\u0011\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0002\u00a2\u0006\u0002\b\u0007\u001a\u0014\u0010\b\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u001aJ\u0010\t\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001H\u0082\b\u00a2\u0006\u0002\b\u000e\u001a\u0014\u0010\u000f\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u0002\u001a\u001e\u0010\u0011\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002\u001a\n\u0010\u0013\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010\u0014\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002\u00a8\u0006\u0015"}, m27d2 = {"getIndentFunction", "Lkotlin/Function1;", "", "indent", "getIndentFunction$StringsKt__IndentKt", "indentWidth", "", "indentWidth$StringsKt__IndentKt", "prependIndent", "reindent", "", "resultSizeEstimate", "indentAddFunction", "indentCutFunction", "reindent$StringsKt__IndentKt", "replaceIndent", "newIndent", "replaceIndentByMargin", "marginPrefix", "trimIndent", "trimMargin", "kotlin-stdlib"}, m28xs = "kotlin/text/StringsKt")
/* loaded from: L-out.jar:kotlin/text/StringsKt__IndentKt.class */
class StringsKt__IndentKt {
    public static String trimMargin$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = CallSiteDescriptor.OPERATOR_DELIMITER;
        }
        return StringsKt.trimMargin(str, str2);
    }

    @NotNull
    public static final String trimMargin(@NotNull String trimMargin, @NotNull String marginPrefix) {
        Intrinsics.checkParameterIsNotNull(trimMargin, "$this$trimMargin");
        Intrinsics.checkParameterIsNotNull(marginPrefix, "marginPrefix");
        return StringsKt.replaceIndentByMargin(trimMargin, "", marginPrefix);
    }

    public static String replaceIndentByMargin$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "";
        }
        if ((i & 2) != 0) {
            str3 = CallSiteDescriptor.OPERATOR_DELIMITER;
        }
        return StringsKt.replaceIndentByMargin(str, str2, str3);
    }

    /* JADX WARN: Removed duplicated region for block: B:50:0x017b  */
    @NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final String replaceIndentByMargin(@NotNull String replaceIndentByMargin, @NotNull String newIndent, @NotNull String marginPrefix) {
        String str;
        int i;
        String strSubstring;
        Intrinsics.checkParameterIsNotNull(replaceIndentByMargin, "$this$replaceIndentByMargin");
        Intrinsics.checkParameterIsNotNull(newIndent, "newIndent");
        Intrinsics.checkParameterIsNotNull(marginPrefix, "marginPrefix");
        if (!(!StringsKt.isBlank(marginPrefix))) {
            throw new IllegalArgumentException("marginPrefix must be non-blank string.".toString());
        }
        List listLines = StringsKt.lines(replaceIndentByMargin);
        int length = replaceIndentByMargin.length() + (newIndent.length() * listLines.size());
        Function1 indentFunction$StringsKt__IndentKt = getIndentFunction$StringsKt__IndentKt(newIndent);
        int lastIndex = CollectionsKt.getLastIndex(listLines);
        List list = listLines;
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        for (Object obj : list) {
            int i3 = i2;
            i2++;
            if (i3 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String str2 = (String) obj;
            if ((i3 == 0 || i3 == lastIndex) && StringsKt.isBlank(str2)) {
                str = null;
            } else {
                String str3 = str2;
                int i4 = 0;
                int length2 = str3.length();
                while (true) {
                    if (i4 >= length2) {
                        i = -1;
                        break;
                    }
                    if (!CharsKt.isWhitespace(str3.charAt(i4))) {
                        i = i4;
                        break;
                    }
                    i4++;
                }
                int i5 = i;
                if (i5 != -1 && StringsKt.startsWith$default(str2, marginPrefix, i5, false, 4, (Object) null)) {
                    int length3 = i5 + marginPrefix.length();
                    if (str2 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    strSubstring = str2.substring(length3);
                    Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
                } else {
                    strSubstring = null;
                }
                if (strSubstring != null) {
                    str = (String) indentFunction$StringsKt__IndentKt.invoke(strSubstring);
                    if (str == null) {
                        str = str2;
                    }
                }
            }
            if (str != null) {
                arrayList.add(str);
            }
        }
        String string = ((StringBuilder) CollectionsKt.joinTo$default(arrayList, new StringBuilder(length), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "mapIndexedNotNull { inde\u2026\"\\n\")\n        .toString()");
        return string;
    }

    @NotNull
    public static final String trimIndent(@NotNull String trimIndent) {
        Intrinsics.checkParameterIsNotNull(trimIndent, "$this$trimIndent");
        return StringsKt.replaceIndent(trimIndent, "");
    }

    public static String replaceIndent$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "";
        }
        return StringsKt.replaceIndent(str, str2);
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x01a3  */
    @NotNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final String replaceIndent(@NotNull String replaceIndent, @NotNull String newIndent) {
        String str;
        Intrinsics.checkParameterIsNotNull(replaceIndent, "$this$replaceIndent");
        Intrinsics.checkParameterIsNotNull(newIndent, "newIndent");
        List listLines = StringsKt.lines(replaceIndent);
        List list = listLines;
        ArrayList arrayList = new ArrayList();
        for (Object obj : list) {
            if (!StringsKt.isBlank((String) obj)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(Integer.valueOf(indentWidth$StringsKt__IndentKt((String) it.next())));
        }
        Integer num = (Integer) CollectionsKt.min((Iterable<Double>) arrayList3);
        int iIntValue = num != null ? num.intValue() : 0;
        int length = replaceIndent.length() + (newIndent.length() * listLines.size());
        Function1 indentFunction$StringsKt__IndentKt = getIndentFunction$StringsKt__IndentKt(newIndent);
        int lastIndex = CollectionsKt.getLastIndex(listLines);
        List list2 = listLines;
        ArrayList arrayList4 = new ArrayList();
        int i = 0;
        for (Object obj2 : list2) {
            int i2 = i;
            i++;
            if (i2 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String str2 = (String) obj2;
            if ((i2 == 0 || i2 == lastIndex) && StringsKt.isBlank(str2)) {
                str = null;
            } else {
                String strDrop = StringsKt.drop(str2, iIntValue);
                if (strDrop != null) {
                    str = (String) indentFunction$StringsKt__IndentKt.invoke(strDrop);
                    if (str == null) {
                        str = str2;
                    }
                }
            }
            if (str != null) {
                arrayList4.add(str);
            }
        }
        String string = ((StringBuilder) CollectionsKt.joinTo$default(arrayList4, new StringBuilder(length), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "mapIndexedNotNull { inde\u2026\"\\n\")\n        .toString()");
        return string;
    }

    public static String prependIndent$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "    ";
        }
        return StringsKt.prependIndent(str, str2);
    }

    @NotNull
    public static final String prependIndent(@NotNull String prependIndent, @NotNull String indent) {
        Intrinsics.checkParameterIsNotNull(prependIndent, "$this$prependIndent");
        Intrinsics.checkParameterIsNotNull(indent, "indent");
        return SequencesKt.joinToString$default(SequencesKt.map(StringsKt.lineSequence(prependIndent), new Function1(indent) { // from class: kotlin.text.StringsKt__IndentKt.prependIndent.1
            final String $indent;

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return invoke((String) obj);
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.$indent = indent;
            }

            @NotNull
            public final String invoke(@NotNull String it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                if (StringsKt.isBlank(it)) {
                    return it.length() < this.$indent.length() ? this.$indent : it;
                }
                return this.$indent + it;
            }
        }), "\n", null, null, 0, null, null, 62, null);
    }

    private static final int indentWidth$StringsKt__IndentKt(@NotNull String str) {
        int i;
        String str2 = str;
        int i2 = 0;
        int length = str2.length();
        while (true) {
            if (i2 >= length) {
                i = -1;
                break;
            }
            if (!CharsKt.isWhitespace(str2.charAt(i2))) {
                i = i2;
                break;
            }
            i2++;
        }
        int i3 = i;
        return i3 == -1 ? str.length() : i3;
    }

    private static final Function1 getIndentFunction$StringsKt__IndentKt(final String str) {
        if (str.length() == 0) {
            return new Function1() { // from class: kotlin.text.StringsKt__IndentKt$getIndentFunction$1
                @Override // kotlin.jvm.functions.Function1
                public Object invoke(Object obj) {
                    return invoke((String) obj);
                }

                @NotNull
                public final String invoke(@NotNull String line) {
                    Intrinsics.checkParameterIsNotNull(line, "line");
                    return line;
                }
            };
        }
        return new Function1(str) { // from class: kotlin.text.StringsKt__IndentKt$getIndentFunction$2
            final String $indent;

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                return invoke((String) obj);
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.$indent = str;
            }

            @NotNull
            public final String invoke(@NotNull String line) {
                Intrinsics.checkParameterIsNotNull(line, "line");
                return this.$indent + line;
            }
        };
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x00b9  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static final String reindent$StringsKt__IndentKt(@NotNull List list, int i, Function1 function1, Function1 function12) {
        String str;
        int lastIndex = CollectionsKt.getLastIndex(list);
        ArrayList arrayList = new ArrayList();
        int i2 = 0;
        for (Object obj : list) {
            int i3 = i2;
            i2++;
            if (i3 < 0) {
                if (!PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
                    throw new ArithmeticException("Index overflow has happened.");
                }
                CollectionsKt.throwIndexOverflow();
            }
            String str2 = (String) obj;
            if ((i3 == 0 || i3 == lastIndex) && StringsKt.isBlank(str2)) {
                str = null;
            } else {
                String str3 = (String) function12.invoke(str2);
                if (str3 != null) {
                    str = (String) function1.invoke(str3);
                    if (str == null) {
                        str = str2;
                    }
                }
            }
            if (str != null) {
                arrayList.add(str);
            }
        }
        String string = ((StringBuilder) CollectionsKt.joinTo$default(arrayList, new StringBuilder(i), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "mapIndexedNotNull { inde\u2026\"\\n\")\n        .toString()");
        return string;
    }
}
