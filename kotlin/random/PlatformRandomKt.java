package kotlin.random;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u001e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\t\u0010\ufffd\ufffd\u001a\u00020\u0001H\u0081\b\u001a\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0005H\ufffd\ufffd\u001a\f\u0010\u0007\u001a\u00020\b*\u00020\u0001H\u0007\u001a\f\u0010\t\u001a\u00020\u0001*\u00020\bH\u0007\u00a8\u0006\n"}, m27d2 = {"defaultPlatformRandom", "Lkotlin/random/Random;", "doubleFromParts", "", "hi26", "", "low27", "asJavaRandom", "Ljava/util/Random;", "asKotlinRandom", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/random/PlatformRandomKt.class */
public final class PlatformRandomKt {
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final java.util.Random asJavaRandom(@NotNull Random asJavaRandom) {
        Intrinsics.checkParameterIsNotNull(asJavaRandom, "$this$asJavaRandom");
        Random random = asJavaRandom;
        if (!(random instanceof AbstractPlatformRandom)) {
            random = null;
        }
        AbstractPlatformRandom abstractPlatformRandom = (AbstractPlatformRandom) random;
        if (abstractPlatformRandom != null) {
            java.util.Random impl = abstractPlatformRandom.getImpl();
            if (impl != null) {
                return impl;
            }
        }
        return new KotlinRandom(asJavaRandom);
    }

    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Random asKotlinRandom(@NotNull java.util.Random asKotlinRandom) {
        Intrinsics.checkParameterIsNotNull(asKotlinRandom, "$this$asKotlinRandom");
        java.util.Random random = asKotlinRandom;
        if (!(random instanceof KotlinRandom)) {
            random = null;
        }
        KotlinRandom kotlinRandom = (KotlinRandom) random;
        if (kotlinRandom != null) {
            Random impl = kotlinRandom.getImpl();
            if (impl != null) {
                return impl;
            }
        }
        return new PlatformRandom(asKotlinRandom);
    }

    @InlineOnly
    private static final Random defaultPlatformRandom() {
        return PlatformImplementationsKt.IMPLEMENTATIONS.defaultPlatformRandom();
    }
}
