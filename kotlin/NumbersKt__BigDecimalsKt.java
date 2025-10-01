package kotlin;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd$\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u0006\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u0007\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\b\u0002\u001a\r\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0002\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\u0004\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0005\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0006\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\t\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u000bH\u0087\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u000eH\u0087\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u000e2\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u000fH\u0087\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u000f2\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\r\u0010\n\u001a\u00020\u0001*\u00020\u0010H\u0087\b\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u00102\u0006\u0010\f\u001a\u00020\rH\u0087\b\u001a\r\u0010\u0011\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u00a8\u0006\u0012"}, m27d2 = {"dec", "Ljava/math/BigDecimal;", "div", "other", "inc", "minus", "mod", "plus", "rem", "times", "toBigDecimal", "", "mathContext", "Ljava/math/MathContext;", "", "", "", "unaryMinus", "kotlin-stdlib"}, m28xs = "kotlin/NumbersKt")
/* loaded from: L-out.jar:kotlin/NumbersKt__BigDecimalsKt.class */
class NumbersKt__BigDecimalsKt {
    @InlineOnly
    private static final BigDecimal plus(@NotNull BigDecimal plus, BigDecimal bigDecimal) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        BigDecimal bigDecimalAdd = plus.add(bigDecimal);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimalAdd, "this.add(other)");
        return bigDecimalAdd;
    }

    @InlineOnly
    private static final BigDecimal minus(@NotNull BigDecimal minus, BigDecimal bigDecimal) {
        Intrinsics.checkParameterIsNotNull(minus, "$this$minus");
        BigDecimal bigDecimalSubtract = minus.subtract(bigDecimal);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimalSubtract, "this.subtract(other)");
        return bigDecimalSubtract;
    }

    @InlineOnly
    private static final BigDecimal times(@NotNull BigDecimal times, BigDecimal bigDecimal) {
        Intrinsics.checkParameterIsNotNull(times, "$this$times");
        BigDecimal bigDecimalMultiply = times.multiply(bigDecimal);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimalMultiply, "this.multiply(other)");
        return bigDecimalMultiply;
    }

    @InlineOnly
    private static final BigDecimal div(@NotNull BigDecimal div, BigDecimal bigDecimal) {
        Intrinsics.checkParameterIsNotNull(div, "$this$div");
        BigDecimal bigDecimalDivide = div.divide(bigDecimal, RoundingMode.HALF_EVEN);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimalDivide, "this.divide(other, RoundingMode.HALF_EVEN)");
        return bigDecimalDivide;
    }

    @Deprecated(message = "Use rem(other) instead", replaceWith = @ReplaceWith(imports = {}, expression = "rem(other)"), level = DeprecationLevel.ERROR)
    @InlineOnly
    private static final BigDecimal mod(@NotNull BigDecimal mod, BigDecimal bigDecimal) {
        Intrinsics.checkParameterIsNotNull(mod, "$this$mod");
        BigDecimal bigDecimalRemainder = mod.remainder(bigDecimal);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimalRemainder, "this.remainder(other)");
        return bigDecimalRemainder;
    }

    @InlineOnly
    private static final BigDecimal rem(@NotNull BigDecimal rem, BigDecimal bigDecimal) {
        Intrinsics.checkParameterIsNotNull(rem, "$this$rem");
        BigDecimal bigDecimalRemainder = rem.remainder(bigDecimal);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimalRemainder, "this.remainder(other)");
        return bigDecimalRemainder;
    }

    @InlineOnly
    private static final BigDecimal unaryMinus(@NotNull BigDecimal unaryMinus) {
        Intrinsics.checkParameterIsNotNull(unaryMinus, "$this$unaryMinus");
        BigDecimal bigDecimalNegate = unaryMinus.negate();
        Intrinsics.checkExpressionValueIsNotNull(bigDecimalNegate, "this.negate()");
        return bigDecimalNegate;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigDecimal inc(@NotNull BigDecimal inc) {
        Intrinsics.checkParameterIsNotNull(inc, "$this$inc");
        BigDecimal bigDecimalAdd = inc.add(BigDecimal.ONE);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimalAdd, "this.add(BigDecimal.ONE)");
        return bigDecimalAdd;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigDecimal dec(@NotNull BigDecimal dec) {
        Intrinsics.checkParameterIsNotNull(dec, "$this$dec");
        BigDecimal bigDecimalSubtract = dec.subtract(BigDecimal.ONE);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimalSubtract, "this.subtract(BigDecimal.ONE)");
        return bigDecimalSubtract;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(int i) {
        BigDecimal bigDecimalValueOf = BigDecimal.valueOf(i);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimalValueOf, "BigDecimal.valueOf(this.toLong())");
        return bigDecimalValueOf;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(int i, MathContext mathContext) {
        return new BigDecimal(i, mathContext);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(long j) {
        BigDecimal bigDecimalValueOf = BigDecimal.valueOf(j);
        Intrinsics.checkExpressionValueIsNotNull(bigDecimalValueOf, "BigDecimal.valueOf(this)");
        return bigDecimalValueOf;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(long j, MathContext mathContext) {
        return new BigDecimal(j, mathContext);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(float f) {
        return new BigDecimal(String.valueOf(f));
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(float f, MathContext mathContext) {
        return new BigDecimal(String.valueOf(f), mathContext);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(double d) {
        return new BigDecimal(String.valueOf(d));
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(double d, MathContext mathContext) {
        return new BigDecimal(String.valueOf(d), mathContext);
    }
}
