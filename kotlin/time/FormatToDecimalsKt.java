package kotlin.time;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd.\n\ufffd\ufffd\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0003\u001a\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\ufffd\ufffd\u001a\u0018\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH\ufffd\ufffd\u001a\u0018\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH\ufffd\ufffd\"\u001c\u0010\ufffd\ufffd\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0004\"\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\"\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\"\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0012"}, m27d2 = {"precisionFormats", "", "Ljava/lang/ThreadLocal;", "Ljava/text/DecimalFormat;", "[Ljava/lang/ThreadLocal;", "rootNegativeExpFormatSymbols", "Ljava/text/DecimalFormatSymbols;", "rootPositiveExpFormatSymbols", "scientificFormat", "createFormatForDecimals", "decimals", "", "formatScientific", "", PropertyDescriptor.VALUE, "", "formatToExactDecimals", "formatUpToDecimals", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/time/FormatToDecimalsKt.class */
public final class FormatToDecimalsKt {
    private static final DecimalFormatSymbols rootNegativeExpFormatSymbols;
    private static final DecimalFormatSymbols rootPositiveExpFormatSymbols;
    private static final ThreadLocal[] precisionFormats;
    private static final ThreadLocal scientificFormat;

    static {
        DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols(Locale.ROOT);
        decimalFormatSymbols.setExponentSeparator("e");
        rootNegativeExpFormatSymbols = decimalFormatSymbols;
        DecimalFormatSymbols decimalFormatSymbols2 = new DecimalFormatSymbols(Locale.ROOT);
        decimalFormatSymbols2.setExponentSeparator("e+");
        rootPositiveExpFormatSymbols = decimalFormatSymbols2;
        ThreadLocal[] threadLocalArr = new ThreadLocal[4];
        for (int i = 0; i < 4; i++) {
            threadLocalArr[i] = new ThreadLocal();
        }
        precisionFormats = threadLocalArr;
        scientificFormat = new ThreadLocal();
    }

    private static final DecimalFormat createFormatForDecimals(int i) {
        DecimalFormat decimalFormat = new DecimalFormat("0", rootNegativeExpFormatSymbols);
        if (i > 0) {
            decimalFormat.setMinimumFractionDigits(i);
        }
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat;
    }

    @NotNull
    public static final String formatToExactDecimals(double d, int i) {
        DecimalFormat decimalFormatCreateFormatForDecimals;
        if (i < precisionFormats.length) {
            ThreadLocal threadLocal = precisionFormats[i];
            Object obj = threadLocal.get();
            if (obj == null) {
                DecimalFormat decimalFormatCreateFormatForDecimals2 = createFormatForDecimals(i);
                threadLocal.set(decimalFormatCreateFormatForDecimals2);
                obj = decimalFormatCreateFormatForDecimals2;
            }
            decimalFormatCreateFormatForDecimals = (DecimalFormat) obj;
        } else {
            decimalFormatCreateFormatForDecimals = createFormatForDecimals(i);
        }
        String str = decimalFormatCreateFormatForDecimals.format(d);
        Intrinsics.checkExpressionValueIsNotNull(str, "format.format(value)");
        return str;
    }

    @NotNull
    public static final String formatUpToDecimals(double d, int i) {
        DecimalFormat decimalFormatCreateFormatForDecimals = createFormatForDecimals(0);
        decimalFormatCreateFormatForDecimals.setMaximumFractionDigits(i);
        String str = decimalFormatCreateFormatForDecimals.format(d);
        Intrinsics.checkExpressionValueIsNotNull(str, "createFormatForDecimals(\u2026 }\n        .format(value)");
        return str;
    }

    @NotNull
    public static final String formatScientific(double d) {
        ThreadLocal threadLocal = scientificFormat;
        Object obj = threadLocal.get();
        if (obj == null) {
            DecimalFormat decimalFormat = new DecimalFormat("0E0", rootNegativeExpFormatSymbols);
            decimalFormat.setMinimumFractionDigits(2);
            threadLocal.set(decimalFormat);
            obj = decimalFormat;
        }
        Object obj2 = obj;
        ((DecimalFormat) obj2).setDecimalFormatSymbols((d >= 1.0d || d <= -1.0d) ? rootPositiveExpFormatSymbols : rootNegativeExpFormatSymbols);
        String str = ((DecimalFormat) obj2).format(d);
        Intrinsics.checkExpressionValueIsNotNull(str, "scientificFormat.getOrSe\u2026 }\n        .format(value)");
        return str;
    }
}
