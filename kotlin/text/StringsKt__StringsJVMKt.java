package kotlin.text;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.ExperimentalStdlibApi;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.TypeCastException;
import kotlin.collections.AbstractList;
import kotlin.collections.ArraysKt;
import kotlin.collections.IntIterator;
import kotlin.internal.InlineOnly;
import kotlin.internal.LowPriorityInOverloadResolution;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.sequences.SequencesKt;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd~\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0012\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0019\n\ufffd\ufffd\n\u0002\u0010\u0015\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\r\n\u0002\b\t\n\u0002\u0010\u0011\n\u0002\u0010\ufffd\ufffd\n\u0002\b\b\n\u0002\u0010\f\n\u0002\b\u0011\n\u0002\u0010 \n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\r\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\b\u001a\u00020\tH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\n\u001a\u00020\u000bH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\u0019\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a)\u0010\u0007\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a\u0011\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u0014H\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a!\u0010\u0007\u001a\u00020\u00022\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011H\u0087\b\u001a\n\u0010\u0017\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010\u0017\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u001a\u0015\u0010\u001a\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0011H\u0087\b\u001a\u0015\u0010\u001c\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010\u001d\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a\u001c\u0010 \u001a\u00020\u0011*\u00020\u00022\u0006\u0010!\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a\f\u0010$\u001a\u00020\u0002*\u00020\u0014H\u0007\u001a \u0010$\u001a\u00020\u0002*\u00020\u00142\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0007\u001a\u0015\u0010&\u001a\u00020#*\u00020\u00022\u0006\u0010\n\u001a\u00020\tH\u0087\b\u001a\u0015\u0010&\u001a\u00020#*\u00020\u00022\u0006\u0010'\u001a\u00020(H\u0087\b\u001a\n\u0010)\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010)\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0007\u001a\f\u0010*\u001a\u00020\u0002*\u00020\rH\u0007\u001a*\u0010*\u001a\u00020\u0002*\u00020\r2\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u00112\b\b\u0002\u0010+\u001a\u00020#H\u0007\u001a\f\u0010,\u001a\u00020\r*\u00020\u0002H\u0007\u001a*\u0010,\u001a\u00020\r*\u00020\u00022\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u00112\b\b\u0002\u0010+\u001a\u00020#H\u0007\u001a\u001c\u0010-\u001a\u00020#*\u00020\u00022\u0006\u0010.\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a \u0010/\u001a\u00020#*\u0004\u0018\u00010\u00022\b\u0010!\u001a\u0004\u0018\u00010\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a2\u00100\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u00192\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b\u00a2\u0006\u0002\u00104\u001a*\u00100\u001a\u00020\u0002*\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b\u00a2\u0006\u0002\u00105\u001a:\u00100\u001a\u00020\u0002*\u00020\u00042\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b\u00a2\u0006\u0002\u00106\u001a2\u00100\u001a\u00020\u0002*\u00020\u00042\u0006\u00100\u001a\u00020\u00022\u0016\u00101\u001a\f\u0012\b\b\u0001\u0012\u0004\u0018\u00010302\"\u0004\u0018\u000103H\u0087\b\u00a2\u0006\u0002\u00107\u001a\r\u00108\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\n\u00109\u001a\u00020#*\u00020(\u001a\u001d\u0010:\u001a\u00020\u0011*\u00020\u00022\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010:\u001a\u00020\u0011*\u00020\u00022\u0006\u0010>\u001a\u00020\u00022\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010?\u001a\u00020\u0011*\u00020\u00022\u0006\u0010;\u001a\u00020<2\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010?\u001a\u00020\u0011*\u00020\u00022\u0006\u0010>\u001a\u00020\u00022\u0006\u0010=\u001a\u00020\u0011H\u0081\b\u001a\u001d\u0010@\u001a\u00020\u0011*\u00020\u00022\u0006\u0010\u001b\u001a\u00020\u00112\u0006\u0010A\u001a\u00020\u0011H\u0087\b\u001a4\u0010B\u001a\u00020#*\u00020(2\u0006\u0010C\u001a\u00020\u00112\u0006\u0010!\u001a\u00020(2\u0006\u0010D\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a4\u0010B\u001a\u00020#*\u00020\u00022\u0006\u0010C\u001a\u00020\u00112\u0006\u0010!\u001a\u00020\u00022\u0006\u0010D\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a\u0012\u0010E\u001a\u00020\u0002*\u00020(2\u0006\u0010F\u001a\u00020\u0011\u001a$\u0010G\u001a\u00020\u0002*\u00020\u00022\u0006\u0010H\u001a\u00020<2\u0006\u0010I\u001a\u00020<2\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010G\u001a\u00020\u0002*\u00020\u00022\u0006\u0010J\u001a\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010H\u001a\u00020<2\u0006\u0010I\u001a\u00020<2\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010L\u001a\u00020\u0002*\u00020\u00022\u0006\u0010J\u001a\u00020\u00022\u0006\u0010K\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a\"\u0010M\u001a\b\u0012\u0004\u0012\u00020\u00020N*\u00020(2\u0006\u0010O\u001a\u00020P2\b\b\u0002\u0010Q\u001a\u00020\u0011\u001a\u001c\u0010R\u001a\u00020#*\u00020\u00022\u0006\u0010S\u001a\u00020\u00022\b\b\u0002\u0010\"\u001a\u00020#\u001a$\u0010R\u001a\u00020#*\u00020\u00022\u0006\u0010S\u001a\u00020\u00022\u0006\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\"\u001a\u00020#\u001a\u0015\u0010T\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u0011H\u0087\b\u001a\u001d\u0010T\u001a\u00020\u0002*\u00020\u00022\u0006\u0010%\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a\u0017\u0010U\u001a\u00020\r*\u00020\u00022\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0087\b\u001a\r\u0010V\u001a\u00020\u0014*\u00020\u0002H\u0087\b\u001a3\u0010V\u001a\u00020\u0014*\u00020\u00022\u0006\u0010W\u001a\u00020\u00142\b\b\u0002\u0010X\u001a\u00020\u00112\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0087\b\u001a \u0010V\u001a\u00020\u0014*\u00020\u00022\b\b\u0002\u0010%\u001a\u00020\u00112\b\b\u0002\u0010\u001f\u001a\u00020\u0011H\u0007\u001a\r\u0010Y\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010Y\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\u001a\u0017\u0010Z\u001a\u00020P*\u00020\u00022\b\b\u0002\u0010[\u001a\u00020\u0011H\u0087\b\u001a\r\u0010\\\u001a\u00020\u0002*\u00020\u0002H\u0087\b\u001a\u0015\u0010\\\u001a\u00020\u0002*\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0019H\u0087\b\"%\u0010\ufffd\ufffd\u001a\u0012\u0012\u0004\u0012\u00020\u00020\u0001j\b\u0012\u0004\u0012\u00020\u0002`\u0003*\u00020\u00048F\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006]"}, m27d2 = {"CASE_INSENSITIVE_ORDER", "Ljava/util/Comparator;", "", "Lkotlin/Comparator;", "Lkotlin/String$Companion;", "getCASE_INSENSITIVE_ORDER", "(Lkotlin/jvm/internal/StringCompanionObject;)Ljava/util/Comparator;", "String", "stringBuffer", "Ljava/lang/StringBuffer;", "stringBuilder", "Ljava/lang/StringBuilder;", "bytes", "", "charset", "Ljava/nio/charset/Charset;", "offset", "", "length", "chars", "", "codePoints", "", "capitalize", "locale", "Ljava/util/Locale;", "codePointAt", "index", "codePointBefore", "codePointCount", "beginIndex", "endIndex", "compareTo", "other", "ignoreCase", "", "concatToString", "startIndex", "contentEquals", "charSequence", "", "decapitalize", "decodeToString", "throwOnInvalidSequence", "encodeToByteArray", "endsWith", "suffix", "equals", "format", "args", "", "", "(Ljava/lang/String;Ljava/util/Locale;[Ljava/lang/Object;)Ljava/lang/String;", "(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/util/Locale;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "(Lkotlin/jvm/internal/StringCompanionObject;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;", "intern", "isBlank", "nativeIndexOf", "ch", "", "fromIndex", AsmConstants.STR, "nativeLastIndexOf", "offsetByCodePoints", "codePointOffset", "regionMatches", "thisOffset", "otherOffset", "repeat", "n", "replace", "oldChar", "newChar", "oldValue", "newValue", "replaceFirst", "split", "", "regex", "Ljava/util/regex/Pattern;", "limit", "startsWith", "prefix", "substring", "toByteArray", "toCharArray", "destination", "destinationOffset", "toLowerCase", "toPattern", "flags", "toUpperCase", "kotlin-stdlib"}, m28xs = "kotlin/text/StringsKt")
/* loaded from: L-out.jar:kotlin/text/StringsKt__StringsJVMKt.class */
class StringsKt__StringsJVMKt extends StringsKt__StringNumberConversionsKt {
    @InlineOnly
    private static final int nativeIndexOf(@NotNull String str, char c, int i) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        return str.indexOf(c, i);
    }

    @InlineOnly
    private static final int nativeIndexOf(@NotNull String str, String str2, int i) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        return str.indexOf(str2, i);
    }

    @InlineOnly
    private static final int nativeLastIndexOf(@NotNull String str, char c, int i) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        return str.lastIndexOf(c, i);
    }

    @InlineOnly
    private static final int nativeLastIndexOf(@NotNull String str, String str2, int i) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        return str.lastIndexOf(str2, i);
    }

    public static boolean equals$default(String str, String str2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.equals(str, str2, z);
    }

    public static final boolean equals(@Nullable String str, @Nullable String str2, boolean z) {
        if (str == null) {
            return str2 == null;
        }
        if (!z) {
            return str.equals(str2);
        }
        return str.equalsIgnoreCase(str2);
    }

    public static String replace$default(String str, char c, char c2, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return StringsKt.replace(str, c, c2, z);
    }

    @NotNull
    public static final String replace(@NotNull String replace, char c, char c2, boolean z) {
        Intrinsics.checkParameterIsNotNull(replace, "$this$replace");
        if (!z) {
            String strReplace = replace.replace(c, c2);
            Intrinsics.checkExpressionValueIsNotNull(strReplace, "(this as java.lang.Strin\u2026replace(oldChar, newChar)");
            return strReplace;
        }
        return SequencesKt.joinToString$default(StringsKt.splitToSequence$default(replace, new char[]{c}, z, 0, 4, (Object) null), String.valueOf(c2), null, null, 0, null, null, 62, null);
    }

    public static String replace$default(String str, String str2, String str3, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return StringsKt.replace(str, str2, str3, z);
    }

    @NotNull
    public static final String replace(@NotNull String replace, @NotNull String oldValue, @NotNull String newValue, boolean z) {
        Intrinsics.checkParameterIsNotNull(replace, "$this$replace");
        Intrinsics.checkParameterIsNotNull(oldValue, "oldValue");
        Intrinsics.checkParameterIsNotNull(newValue, "newValue");
        return SequencesKt.joinToString$default(StringsKt.splitToSequence$default(replace, new String[]{oldValue}, z, 0, 4, (Object) null), newValue, null, null, 0, null, null, 62, null);
    }

    public static String replaceFirst$default(String str, char c, char c2, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return StringsKt.replaceFirst(str, c, c2, z);
    }

    @NotNull
    public static final String replaceFirst(@NotNull String replaceFirst, char c, char c2, boolean z) {
        Intrinsics.checkParameterIsNotNull(replaceFirst, "$this$replaceFirst");
        int iIndexOf$default = StringsKt.indexOf$default(replaceFirst, c, 0, z, 2, (Object) null);
        if (iIndexOf$default < 0) {
            return replaceFirst;
        }
        return StringsKt.replaceRange((CharSequence) replaceFirst, iIndexOf$default, iIndexOf$default + 1, (CharSequence) String.valueOf(c2)).toString();
    }

    public static String replaceFirst$default(String str, String str2, String str3, boolean z, int i, Object obj) {
        if ((i & 4) != 0) {
            z = false;
        }
        return StringsKt.replaceFirst(str, str2, str3, z);
    }

    @NotNull
    public static final String replaceFirst(@NotNull String replaceFirst, @NotNull String oldValue, @NotNull String newValue, boolean z) {
        Intrinsics.checkParameterIsNotNull(replaceFirst, "$this$replaceFirst");
        Intrinsics.checkParameterIsNotNull(oldValue, "oldValue");
        Intrinsics.checkParameterIsNotNull(newValue, "newValue");
        int iIndexOf$default = StringsKt.indexOf$default(replaceFirst, oldValue, 0, z, 2, (Object) null);
        if (iIndexOf$default < 0) {
            return replaceFirst;
        }
        return StringsKt.replaceRange((CharSequence) replaceFirst, iIndexOf$default, iIndexOf$default + oldValue.length(), (CharSequence) newValue).toString();
    }

    @InlineOnly
    private static final String toUpperCase(@NotNull String str) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String upperCase = str.toUpperCase();
        Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase()");
        return upperCase;
    }

    @InlineOnly
    private static final String toLowerCase(@NotNull String str) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        return lowerCase;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @NotNull
    public static final String concatToString(@NotNull char[] concatToString) {
        Intrinsics.checkParameterIsNotNull(concatToString, "$this$concatToString");
        return new String(concatToString);
    }

    public static String concatToString$default(char[] cArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = cArr.length;
        }
        return StringsKt.concatToString(cArr, i, i2);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @NotNull
    public static final String concatToString(@NotNull char[] concatToString, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(concatToString, "$this$concatToString");
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(i, i2, concatToString.length);
        return new String(concatToString, i, i2 - i);
    }

    public static char[] toCharArray$default(String str, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        return StringsKt.toCharArray(str, i, i2);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @NotNull
    public static final char[] toCharArray(@NotNull String toCharArray, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(toCharArray, "$this$toCharArray");
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(i, i2, toCharArray.length());
        char[] cArr = new char[i2 - i];
        toCharArray.getChars(i, i2, cArr, 0);
        return cArr;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @NotNull
    public static final String decodeToString(@NotNull byte[] decodeToString) {
        Intrinsics.checkParameterIsNotNull(decodeToString, "$this$decodeToString");
        return new String(decodeToString, Charsets.UTF_8);
    }

    public static String decodeToString$default(byte[] bArr, int i, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = bArr.length;
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return StringsKt.decodeToString(bArr, i, i2, z);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @NotNull
    public static final String decodeToString(@NotNull byte[] decodeToString, int i, int i2, boolean z) {
        Intrinsics.checkParameterIsNotNull(decodeToString, "$this$decodeToString");
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(i, i2, decodeToString.length);
        if (!z) {
            return new String(decodeToString, i, i2 - i, Charsets.UTF_8);
        }
        String string = Charsets.UTF_8.newDecoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT).decode(ByteBuffer.wrap(decodeToString, i, i2 - i)).toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "decoder.decode(ByteBuffe\u2026- startIndex)).toString()");
        return string;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @NotNull
    public static final byte[] encodeToByteArray(@NotNull String encodeToByteArray) {
        Intrinsics.checkParameterIsNotNull(encodeToByteArray, "$this$encodeToByteArray");
        byte[] bytes = encodeToByteArray.getBytes(Charsets.UTF_8);
        Intrinsics.checkExpressionValueIsNotNull(bytes, "(this as java.lang.String).getBytes(charset)");
        return bytes;
    }

    public static byte[] encodeToByteArray$default(String str, int i, int i2, boolean z, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = str.length();
        }
        if ((i3 & 4) != 0) {
            z = false;
        }
        return StringsKt.encodeToByteArray(str, i, i2, z);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @NotNull
    public static final byte[] encodeToByteArray(@NotNull String encodeToByteArray, int i, int i2, boolean z) throws CharacterCodingException {
        Intrinsics.checkParameterIsNotNull(encodeToByteArray, "$this$encodeToByteArray");
        AbstractList.Companion.checkBoundsIndexes$kotlin_stdlib(i, i2, encodeToByteArray.length());
        if (!z) {
            String strSubstring = encodeToByteArray.substring(i, i2);
            Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
            Charset charset = Charsets.UTF_8;
            if (strSubstring == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            byte[] bytes = strSubstring.getBytes(charset);
            Intrinsics.checkExpressionValueIsNotNull(bytes, "(this as java.lang.String).getBytes(charset)");
            return bytes;
        }
        ByteBuffer byteBufferEncode = Charsets.UTF_8.newEncoder().onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT).encode(CharBuffer.wrap(encodeToByteArray, i, i2));
        if (byteBufferEncode.hasArray() && byteBufferEncode.arrayOffset() == 0) {
            int iRemaining = byteBufferEncode.remaining();
            byte[] bArrArray = byteBufferEncode.array();
            if (bArrArray == null) {
                Intrinsics.throwNpe();
            }
            if (iRemaining == bArrArray.length) {
                byte[] bArrArray2 = byteBufferEncode.array();
                Intrinsics.checkExpressionValueIsNotNull(bArrArray2, "byteBuffer.array()");
                return bArrArray2;
            }
        }
        byte[] bArr = new byte[byteBufferEncode.remaining()];
        byteBufferEncode.get(bArr);
        return bArr;
    }

    @InlineOnly
    private static final char[] toCharArray(@NotNull String str) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        char[] charArray = str.toCharArray();
        Intrinsics.checkExpressionValueIsNotNull(charArray, "(this as java.lang.String).toCharArray()");
        return charArray;
    }

    static char[] toCharArray$default(String str, char[] cArr, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = str.length();
        }
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        str.getChars(i2, i3, cArr, i);
        return cArr;
    }

    @InlineOnly
    private static final char[] toCharArray(@NotNull String str, char[] cArr, int i, int i2, int i3) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        str.getChars(i2, i3, cArr, i);
        return cArr;
    }

    @InlineOnly
    private static final String format(@NotNull String str, Object[] objArr) {
        String str2 = String.format(str, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(str2, "java.lang.String.format(this, *args)");
        return str2;
    }

    @InlineOnly
    private static final String format(@NotNull StringCompanionObject stringCompanionObject, String str, Object[] objArr) {
        String str2 = String.format(str, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(str2, "java.lang.String.format(format, *args)");
        return str2;
    }

    @InlineOnly
    private static final String format(@NotNull String str, Locale locale, Object[] objArr) {
        String str2 = String.format(locale, str, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(str2, "java.lang.String.format(locale, this, *args)");
        return str2;
    }

    @InlineOnly
    private static final String format(@NotNull StringCompanionObject stringCompanionObject, Locale locale, String str, Object[] objArr) {
        String str2 = String.format(locale, str, Arrays.copyOf(objArr, objArr.length));
        Intrinsics.checkExpressionValueIsNotNull(str2, "java.lang.String.format(locale, format, *args)");
        return str2;
    }

    public static List split$default(CharSequence charSequence, Pattern pattern, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return StringsKt.split(charSequence, pattern, i);
    }

    @NotNull
    public static final List split(@NotNull CharSequence split, @NotNull Pattern regex, int i) {
        Intrinsics.checkParameterIsNotNull(split, "$this$split");
        Intrinsics.checkParameterIsNotNull(regex, "regex");
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("Limit must be non-negative, but was " + i + '.').toString());
        }
        String[] strArrSplit = regex.split(split, i == 0 ? -1 : i);
        Intrinsics.checkExpressionValueIsNotNull(strArrSplit, "regex.split(this, if (limit == 0) -1 else limit)");
        return ArraysKt.asList(strArrSplit);
    }

    @InlineOnly
    private static final String substring(@NotNull String str, int i) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String strSubstring = str.substring(i);
        Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.String).substring(startIndex)");
        return strSubstring;
    }

    @InlineOnly
    private static final String substring(@NotNull String str, int i, int i2) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String strSubstring = str.substring(i, i2);
        Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        return strSubstring;
    }

    public static boolean startsWith$default(String str, String str2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.startsWith(str, str2, z);
    }

    public static final boolean startsWith(@NotNull String startsWith, @NotNull String prefix, boolean z) {
        Intrinsics.checkParameterIsNotNull(startsWith, "$this$startsWith");
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        if (!z) {
            return startsWith.startsWith(prefix);
        }
        return StringsKt.regionMatches(startsWith, 0, prefix, 0, prefix.length(), z);
    }

    public static boolean startsWith$default(String str, String str2, int i, boolean z, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            z = false;
        }
        return StringsKt.startsWith(str, str2, i, z);
    }

    public static final boolean startsWith(@NotNull String startsWith, @NotNull String prefix, int i, boolean z) {
        Intrinsics.checkParameterIsNotNull(startsWith, "$this$startsWith");
        Intrinsics.checkParameterIsNotNull(prefix, "prefix");
        if (!z) {
            return startsWith.startsWith(prefix, i);
        }
        return StringsKt.regionMatches(startsWith, i, prefix, 0, prefix.length(), z);
    }

    public static boolean endsWith$default(String str, String str2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.endsWith(str, str2, z);
    }

    public static final boolean endsWith(@NotNull String endsWith, @NotNull String suffix, boolean z) {
        Intrinsics.checkParameterIsNotNull(endsWith, "$this$endsWith");
        Intrinsics.checkParameterIsNotNull(suffix, "suffix");
        if (!z) {
            return endsWith.endsWith(suffix);
        }
        return StringsKt.regionMatches(endsWith, endsWith.length() - suffix.length(), suffix, 0, suffix.length(), true);
    }

    @InlineOnly
    private static final String String(byte[] bArr, int i, int i2, Charset charset) {
        return new String(bArr, i, i2, charset);
    }

    @InlineOnly
    private static final String String(byte[] bArr, Charset charset) {
        return new String(bArr, charset);
    }

    @InlineOnly
    private static final String String(byte[] bArr, int i, int i2) {
        return new String(bArr, i, i2, Charsets.UTF_8);
    }

    @InlineOnly
    private static final String String(byte[] bArr) {
        return new String(bArr, Charsets.UTF_8);
    }

    @InlineOnly
    private static final String String(char[] cArr) {
        return new String(cArr);
    }

    @InlineOnly
    private static final String String(char[] cArr, int i, int i2) {
        return new String(cArr, i, i2);
    }

    @InlineOnly
    private static final String String(int[] iArr, int i, int i2) {
        return new String(iArr, i, i2);
    }

    @InlineOnly
    private static final String String(StringBuffer stringBuffer) {
        return new String(stringBuffer);
    }

    @InlineOnly
    private static final String String(StringBuilder sb) {
        return new String(sb);
    }

    @InlineOnly
    private static final int codePointAt(@NotNull String str, int i) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        return str.codePointAt(i);
    }

    @InlineOnly
    private static final int codePointBefore(@NotNull String str, int i) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        return str.codePointBefore(i);
    }

    @InlineOnly
    private static final int codePointCount(@NotNull String str, int i, int i2) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        return str.codePointCount(i, i2);
    }

    public static int compareTo$default(String str, String str2, boolean z, int i, Object obj) {
        if ((i & 2) != 0) {
            z = false;
        }
        return StringsKt.compareTo(str, str2, z);
    }

    public static final int compareTo(@NotNull String compareTo, @NotNull String other, boolean z) {
        Intrinsics.checkParameterIsNotNull(compareTo, "$this$compareTo");
        Intrinsics.checkParameterIsNotNull(other, "other");
        if (z) {
            return compareTo.compareToIgnoreCase(other);
        }
        return compareTo.compareTo(other);
    }

    @InlineOnly
    private static final boolean contentEquals(@NotNull String str, CharSequence charSequence) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        return str.contentEquals(charSequence);
    }

    @InlineOnly
    private static final boolean contentEquals(@NotNull String str, StringBuffer stringBuffer) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        return str.contentEquals(stringBuffer);
    }

    @InlineOnly
    private static final String intern(@NotNull String str) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String strIntern = str.intern();
        Intrinsics.checkExpressionValueIsNotNull(strIntern, "(this as java.lang.String).intern()");
        return strIntern;
    }

    public static final boolean isBlank(@NotNull CharSequence isBlank) {
        boolean z;
        Intrinsics.checkParameterIsNotNull(isBlank, "$this$isBlank");
        if (isBlank.length() != 0) {
            Iterable indices = StringsKt.getIndices(isBlank);
            if (!(indices instanceof Collection) || !((Collection) indices).isEmpty()) {
                Iterator it = indices.iterator();
                while (true) {
                    if (it.hasNext()) {
                        if (!CharsKt.isWhitespace(isBlank.charAt(((IntIterator) it).nextInt()))) {
                            z = false;
                            break;
                        }
                    } else {
                        z = true;
                        break;
                    }
                }
            } else {
                z = true;
            }
            if (!z) {
                return false;
            }
        }
        return true;
    }

    @InlineOnly
    private static final int offsetByCodePoints(@NotNull String str, int i, int i2) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        return str.offsetByCodePoints(i, i2);
    }

    public static boolean regionMatches$default(CharSequence charSequence, int i, CharSequence charSequence2, int i2, int i3, boolean z, int i4, Object obj) {
        if ((i4 & 16) != 0) {
            z = false;
        }
        return StringsKt.regionMatches(charSequence, i, charSequence2, i2, i3, z);
    }

    public static final boolean regionMatches(@NotNull CharSequence regionMatches, int i, @NotNull CharSequence other, int i2, int i3, boolean z) {
        Intrinsics.checkParameterIsNotNull(regionMatches, "$this$regionMatches");
        Intrinsics.checkParameterIsNotNull(other, "other");
        if ((regionMatches instanceof String) && (other instanceof String)) {
            return StringsKt.regionMatches((String) regionMatches, i, (String) other, i2, i3, z);
        }
        return StringsKt.regionMatchesImpl(regionMatches, i, other, i2, i3, z);
    }

    public static boolean regionMatches$default(String str, int i, String str2, int i2, int i3, boolean z, int i4, Object obj) {
        if ((i4 & 16) != 0) {
            z = false;
        }
        return StringsKt.regionMatches(str, i, str2, i2, i3, z);
    }

    public static final boolean regionMatches(@NotNull String regionMatches, int i, @NotNull String other, int i2, int i3, boolean z) {
        Intrinsics.checkParameterIsNotNull(regionMatches, "$this$regionMatches");
        Intrinsics.checkParameterIsNotNull(other, "other");
        if (!z) {
            return regionMatches.regionMatches(i, other, i2, i3);
        }
        return regionMatches.regionMatches(z, i, other, i2, i3);
    }

    @InlineOnly
    private static final String toLowerCase(@NotNull String str, Locale locale) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase(locale);
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase(locale)");
        return lowerCase;
    }

    @InlineOnly
    private static final String toUpperCase(@NotNull String str, Locale locale) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String upperCase = str.toUpperCase(locale);
        Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase(locale)");
        return upperCase;
    }

    @InlineOnly
    private static final byte[] toByteArray(@NotNull String str, Charset charset) {
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        byte[] bytes = str.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(bytes, "(this as java.lang.String).getBytes(charset)");
        return bytes;
    }

    static byte[] toByteArray$default(String str, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        byte[] bytes = str.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(bytes, "(this as java.lang.String).getBytes(charset)");
        return bytes;
    }

    static Pattern toPattern$default(String str, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        Pattern patternCompile = Pattern.compile(str, i);
        Intrinsics.checkExpressionValueIsNotNull(patternCompile, "java.util.regex.Pattern.compile(this, flags)");
        return patternCompile;
    }

    @InlineOnly
    private static final Pattern toPattern(@NotNull String str, int i) {
        Pattern patternCompile = Pattern.compile(str, i);
        Intrinsics.checkExpressionValueIsNotNull(patternCompile, "java.util.regex.Pattern.compile(this, flags)");
        return patternCompile;
    }

    @NotNull
    public static final String capitalize(@NotNull String capitalize) {
        Intrinsics.checkParameterIsNotNull(capitalize, "$this$capitalize");
        if (!(capitalize.length() > 0) || !Character.isLowerCase(capitalize.charAt(0))) {
            return capitalize;
        }
        StringBuilder sb = new StringBuilder();
        String strSubstring = capitalize.substring(0, 1);
        Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        if (strSubstring == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String upperCase = strSubstring.toUpperCase();
        Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase()");
        StringBuilder sbAppend = sb.append(upperCase);
        String strSubstring2 = capitalize.substring(1);
        Intrinsics.checkExpressionValueIsNotNull(strSubstring2, "(this as java.lang.String).substring(startIndex)");
        return sbAppend.append(strSubstring2).toString();
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @NotNull
    @LowPriorityInOverloadResolution
    public static final String capitalize(@NotNull String capitalize, @NotNull Locale locale) {
        Intrinsics.checkParameterIsNotNull(capitalize, "$this$capitalize");
        Intrinsics.checkParameterIsNotNull(locale, "locale");
        if (capitalize.length() > 0) {
            char cCharAt = capitalize.charAt(0);
            if (Character.isLowerCase(cCharAt)) {
                StringBuilder sb = new StringBuilder();
                char titleCase = Character.toTitleCase(cCharAt);
                if (titleCase != Character.toUpperCase(cCharAt)) {
                    sb.append(titleCase);
                } else {
                    String strSubstring = capitalize.substring(0, 1);
                    Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                    if (strSubstring == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String upperCase = strSubstring.toUpperCase(locale);
                    Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase(locale)");
                    sb.append(upperCase);
                }
                String strSubstring2 = capitalize.substring(1);
                Intrinsics.checkExpressionValueIsNotNull(strSubstring2, "(this as java.lang.String).substring(startIndex)");
                sb.append(strSubstring2);
                String string = sb.toString();
                Intrinsics.checkExpressionValueIsNotNull(string, "StringBuilder().apply(builderAction).toString()");
                return string;
            }
        }
        return capitalize;
    }

    @NotNull
    public static final String decapitalize(@NotNull String decapitalize) {
        Intrinsics.checkParameterIsNotNull(decapitalize, "$this$decapitalize");
        if (!(decapitalize.length() > 0) || !Character.isUpperCase(decapitalize.charAt(0))) {
            return decapitalize;
        }
        StringBuilder sb = new StringBuilder();
        String strSubstring = decapitalize.substring(0, 1);
        Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        if (strSubstring == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = strSubstring.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        StringBuilder sbAppend = sb.append(lowerCase);
        String strSubstring2 = decapitalize.substring(1);
        Intrinsics.checkExpressionValueIsNotNull(strSubstring2, "(this as java.lang.String).substring(startIndex)");
        return sbAppend.append(strSubstring2).toString();
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalStdlibApi
    @NotNull
    @LowPriorityInOverloadResolution
    public static final String decapitalize(@NotNull String decapitalize, @NotNull Locale locale) {
        Intrinsics.checkParameterIsNotNull(decapitalize, "$this$decapitalize");
        Intrinsics.checkParameterIsNotNull(locale, "locale");
        if (!(decapitalize.length() > 0) || Character.isLowerCase(decapitalize.charAt(0))) {
            return decapitalize;
        }
        StringBuilder sb = new StringBuilder();
        String strSubstring = decapitalize.substring(0, 1);
        Intrinsics.checkExpressionValueIsNotNull(strSubstring, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
        if (strSubstring == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = strSubstring.toLowerCase(locale);
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase(locale)");
        StringBuilder sbAppend = sb.append(lowerCase);
        String strSubstring2 = decapitalize.substring(1);
        Intrinsics.checkExpressionValueIsNotNull(strSubstring2, "(this as java.lang.String).substring(startIndex)");
        return sbAppend.append(strSubstring2).toString();
    }

    @NotNull
    public static final String repeat(@NotNull CharSequence repeat, int i) {
        Intrinsics.checkParameterIsNotNull(repeat, "$this$repeat");
        if (!(i >= 0)) {
            throw new IllegalArgumentException(("Count 'n' must be non-negative, but was " + i + '.').toString());
        }
        switch (i) {
            case 0:
                return "";
            case 1:
                return repeat.toString();
            default:
                switch (repeat.length()) {
                    case 0:
                        return "";
                    case 1:
                        char cCharAt = repeat.charAt(0);
                        char[] cArr = new char[i];
                        for (int i2 = 0; i2 < i; i2++) {
                            cArr[i2] = cCharAt;
                        }
                        return new String(cArr);
                    default:
                        StringBuilder sb = new StringBuilder(i * repeat.length());
                        int i3 = 1;
                        if (1 <= i) {
                            while (true) {
                                sb.append(repeat);
                                if (i3 != i) {
                                    i3++;
                                }
                            }
                        }
                        String string = sb.toString();
                        Intrinsics.checkExpressionValueIsNotNull(string, "sb.toString()");
                        return string;
                }
        }
    }

    @NotNull
    public static final Comparator getCASE_INSENSITIVE_ORDER(@NotNull StringCompanionObject CASE_INSENSITIVE_ORDER) {
        Intrinsics.checkParameterIsNotNull(CASE_INSENSITIVE_ORDER, "$this$CASE_INSENSITIVE_ORDER");
        Comparator comparator = String.CASE_INSENSITIVE_ORDER;
        Intrinsics.checkExpressionValueIsNotNull(comparator, "java.lang.String.CASE_INSENSITIVE_ORDER");
        return comparator;
    }
}
