package kotlin;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd(\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\t\n\u0002\b\u0003\u001a\u0015\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\r\u0010\u0003\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0004\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\u0005\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\r\u0010\u0006\u001a\u00020\u0001*\u00020\u0001H\u0087\b\u001a\u0015\u0010\u0007\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u001a\u0015\u0010\t\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\n\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\u0015\u0010\u000b\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\u0087\f\u001a\u0015\u0010\u000e\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\u0087\f\u001a\u0015\u0010\u000f\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\n\u001a\r\u0010\u0010\u001a\u00020\u0011*\u00020\u0001H\u0087\b\u001a!\u0010\u0010\u001a\u00020\u0011*\u00020\u00012\b\b\u0002\u0010\u0012\u001a\u00020\r2\b\b\u0002\u0010\u0013\u001a\u00020\u0014H\u0087\b\u001a\r\u0010\u0015\u001a\u00020\u0001*\u00020\rH\u0087\b\u001a\r\u0010\u0015\u001a\u00020\u0001*\u00020\u0016H\u0087\b\u001a\r\u0010\u0017\u001a\u00020\u0001*\u00020\u0001H\u0087\n\u001a\u0015\u0010\u0018\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0087\f\u00a8\u0006\u0019"}, m27d2 = {"and", "Ljava/math/BigInteger;", "other", "dec", "div", "inc", "inv", "minus", "or", "plus", "rem", "shl", "n", "", "shr", "times", "toBigDecimal", "Ljava/math/BigDecimal;", "scale", "mathContext", "Ljava/math/MathContext;", "toBigInteger", "", "unaryMinus", "xor", "kotlin-stdlib"}, m28xs = "kotlin/NumbersKt")
/* loaded from: L-out.jar:kotlin/NumbersKt__BigIntegersKt.class */
class NumbersKt__BigIntegersKt extends NumbersKt__BigDecimalsKt {
    @InlineOnly
    private static final BigInteger plus(@NotNull BigInteger plus, BigInteger bigInteger) {
        Intrinsics.checkParameterIsNotNull(plus, "$this$plus");
        BigInteger bigIntegerAdd = plus.add(bigInteger);
        Intrinsics.checkExpressionValueIsNotNull(bigIntegerAdd, "this.add(other)");
        return bigIntegerAdd;
    }

    @InlineOnly
    private static final BigInteger minus(@NotNull BigInteger minus, BigInteger bigInteger) {
        Intrinsics.checkParameterIsNotNull(minus, "$this$minus");
        BigInteger bigIntegerSubtract = minus.subtract(bigInteger);
        Intrinsics.checkExpressionValueIsNotNull(bigIntegerSubtract, "this.subtract(other)");
        return bigIntegerSubtract;
    }

    @InlineOnly
    private static final BigInteger times(@NotNull BigInteger times, BigInteger bigInteger) {
        Intrinsics.checkParameterIsNotNull(times, "$this$times");
        BigInteger bigIntegerMultiply = times.multiply(bigInteger);
        Intrinsics.checkExpressionValueIsNotNull(bigIntegerMultiply, "this.multiply(other)");
        return bigIntegerMultiply;
    }

    @InlineOnly
    private static final BigInteger div(@NotNull BigInteger div, BigInteger bigInteger) {
        Intrinsics.checkParameterIsNotNull(div, "$this$div");
        BigInteger bigIntegerDivide = div.divide(bigInteger);
        Intrinsics.checkExpressionValueIsNotNull(bigIntegerDivide, "this.divide(other)");
        return bigIntegerDivide;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final BigInteger rem(@NotNull BigInteger rem, BigInteger bigInteger) {
        Intrinsics.checkParameterIsNotNull(rem, "$this$rem");
        BigInteger bigIntegerRemainder = rem.remainder(bigInteger);
        Intrinsics.checkExpressionValueIsNotNull(bigIntegerRemainder, "this.remainder(other)");
        return bigIntegerRemainder;
    }

    @InlineOnly
    private static final BigInteger unaryMinus(@NotNull BigInteger unaryMinus) {
        Intrinsics.checkParameterIsNotNull(unaryMinus, "$this$unaryMinus");
        BigInteger bigIntegerNegate = unaryMinus.negate();
        Intrinsics.checkExpressionValueIsNotNull(bigIntegerNegate, "this.negate()");
        return bigIntegerNegate;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigInteger inc(@NotNull BigInteger inc) {
        Intrinsics.checkParameterIsNotNull(inc, "$this$inc");
        BigInteger bigIntegerAdd = inc.add(BigInteger.ONE);
        Intrinsics.checkExpressionValueIsNotNull(bigIntegerAdd, "this.add(BigInteger.ONE)");
        return bigIntegerAdd;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigInteger dec(@NotNull BigInteger dec) {
        Intrinsics.checkParameterIsNotNull(dec, "$this$dec");
        BigInteger bigIntegerSubtract = dec.subtract(BigInteger.ONE);
        Intrinsics.checkExpressionValueIsNotNull(bigIntegerSubtract, "this.subtract(BigInteger.ONE)");
        return bigIntegerSubtract;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigInteger inv(@NotNull BigInteger bigInteger) {
        BigInteger bigIntegerNot = bigInteger.not();
        Intrinsics.checkExpressionValueIsNotNull(bigIntegerNot, "this.not()");
        return bigIntegerNot;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigInteger and(@NotNull BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger bigIntegerAnd = bigInteger.and(bigInteger2);
        Intrinsics.checkExpressionValueIsNotNull(bigIntegerAnd, "this.and(other)");
        return bigIntegerAnd;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    /* renamed from: or */
    private static final BigInteger m31or(@NotNull BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger bigIntegerOr = bigInteger.or(bigInteger2);
        Intrinsics.checkExpressionValueIsNotNull(bigIntegerOr, "this.or(other)");
        return bigIntegerOr;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigInteger xor(@NotNull BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger bigIntegerXor = bigInteger.xor(bigInteger2);
        Intrinsics.checkExpressionValueIsNotNull(bigIntegerXor, "this.xor(other)");
        return bigIntegerXor;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigInteger shl(@NotNull BigInteger bigInteger, int i) {
        BigInteger bigIntegerShiftLeft = bigInteger.shiftLeft(i);
        Intrinsics.checkExpressionValueIsNotNull(bigIntegerShiftLeft, "this.shiftLeft(n)");
        return bigIntegerShiftLeft;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigInteger shr(@NotNull BigInteger bigInteger, int i) {
        BigInteger bigIntegerShiftRight = bigInteger.shiftRight(i);
        Intrinsics.checkExpressionValueIsNotNull(bigIntegerShiftRight, "this.shiftRight(n)");
        return bigIntegerShiftRight;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigInteger toBigInteger(int i) {
        BigInteger bigIntegerValueOf = BigInteger.valueOf(i);
        Intrinsics.checkExpressionValueIsNotNull(bigIntegerValueOf, "BigInteger.valueOf(this.toLong())");
        return bigIntegerValueOf;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigInteger toBigInteger(long j) {
        BigInteger bigIntegerValueOf = BigInteger.valueOf(j);
        Intrinsics.checkExpressionValueIsNotNull(bigIntegerValueOf, "BigInteger.valueOf(this)");
        return bigIntegerValueOf;
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(@NotNull BigInteger bigInteger) {
        return new BigDecimal(bigInteger);
    }

    static BigDecimal toBigDecimal$default(BigInteger bigInteger, int i, MathContext mathContext, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        if ((i2 & 2) != 0) {
            MathContext mathContext2 = MathContext.UNLIMITED;
            Intrinsics.checkExpressionValueIsNotNull(mathContext2, "MathContext.UNLIMITED");
            mathContext = mathContext2;
        }
        return new BigDecimal(bigInteger, i, mathContext);
    }

    @SinceKotlin(version = "1.2")
    @InlineOnly
    private static final BigDecimal toBigDecimal(@NotNull BigInteger bigInteger, int i, MathContext mathContext) {
        return new BigDecimal(bigInteger, i, mathContext);
    }
}
