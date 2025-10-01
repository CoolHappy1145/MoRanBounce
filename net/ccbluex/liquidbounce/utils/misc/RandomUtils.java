package net.ccbluex.liquidbounce.utils.misc;

import java.util.Random;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u0019\n\u0002\b\u0003\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004J\u0016\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\b2\u0006\u0010\u0006\u001a\u00020\bJ\u0018\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0007J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\u0010J\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\n2\u0006\u0010\u000f\u001a\u00020\rJ\u000e\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\nJ\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\nH\u0007\u00a8\u0006\u0013"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/misc/RandomUtils;", "", "()V", "nextDouble", "", "startInclusive", "endInclusive", "nextFloat", "", "nextInt", "", "endExclusive", "random", "", "length", "chars", "", "randomNumber", "randomString", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/misc/RandomUtils.class */
public final class RandomUtils {
    public static final RandomUtils INSTANCE = new RandomUtils();

    private RandomUtils() {
    }

    @JvmStatic
    public static final int nextInt(int i, int i2) {
        return i2 - i <= 0 ? i : i + new Random().nextInt(i2 - i);
    }

    public final double nextDouble(double d, double d2) {
        return (d == d2 || d2 - d <= 0.0d) ? d : d + ((d2 - d) * Math.random());
    }

    public final float nextFloat(float f, float f2) {
        return (f == f2 || f2 - f <= 0.0f) ? f : (float) (f + ((f2 - f) * Math.random()));
    }

    @NotNull
    public final String randomNumber(int i) {
        return random(i, "123456789");
    }

    @JvmStatic
    @NotNull
    public static final String randomString(int i) {
        return INSTANCE.random(i, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    }

    @NotNull
    public final String random(int i, @NotNull String chars) {
        Intrinsics.checkParameterIsNotNull(chars, "chars");
        char[] charArray = chars.toCharArray();
        Intrinsics.checkExpressionValueIsNotNull(charArray, "(this as java.lang.String).toCharArray()");
        return random(i, charArray);
    }

    @NotNull
    public final String random(int i, @NotNull char[] chars) {
        Intrinsics.checkParameterIsNotNull(chars, "chars");
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(chars[new Random().nextInt(chars.length)]);
        }
        String string = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "stringBuilder.toString()");
        return string;
    }
}
