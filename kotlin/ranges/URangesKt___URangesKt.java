package kotlin.ranges;

import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.ExperimentalUnsignedTypes;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.UByte;
import kotlin.UInt;
import kotlin.ULong;
import kotlin.UShort;
import kotlin.UnsignedKt;
import kotlin.internal.InlineOnly;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import kotlin.random.URandomKt;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffdb\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\u0010\t\n\u0002\b\n\u001a\u001e\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0003\u0010\u0004\u001a\u001e\u0010\ufffd\ufffd\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u0005H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0006\u0010\u0007\u001a\u001e\u0010\ufffd\ufffd\u001a\u00020\b*\u00020\b2\u0006\u0010\u0002\u001a\u00020\bH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\t\u0010\n\u001a\u001e\u0010\ufffd\ufffd\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u000bH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\f\u0010\r\u001a\u001e\u0010\u000e\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0010\u0010\u0004\u001a\u001e\u0010\u000e\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0011\u0010\u0007\u001a\u001e\u0010\u000e\u001a\u00020\b*\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0012\u0010\n\u001a\u001e\u0010\u000e\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000bH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0013\u0010\r\u001a&\u0010\u0014\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u0001H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0015\u0010\u0016\u001a&\u0010\u0014\u001a\u00020\u0005*\u00020\u00052\u0006\u0010\u0002\u001a\u00020\u00052\u0006\u0010\u000f\u001a\u00020\u0005H\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u0017\u0010\u0018\u001a$\u0010\u0014\u001a\u00020\u0005*\u00020\u00052\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00050\u001aH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001b\u0010\u001c\u001a&\u0010\u0014\u001a\u00020\b*\u00020\b2\u0006\u0010\u0002\u001a\u00020\b2\u0006\u0010\u000f\u001a\u00020\bH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001d\u0010\u001e\u001a$\u0010\u0014\u001a\u00020\b*\u00020\b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\b0\u001aH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b\u001f\u0010 \u001a&\u0010\u0014\u001a\u00020\u000b*\u00020\u000b2\u0006\u0010\u0002\u001a\u00020\u000b2\u0006\u0010\u000f\u001a\u00020\u000bH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b!\u0010\"\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\u0001H\u0087\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b'\u0010(\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\b\u0010)\u001a\u0004\u0018\u00010\u0005H\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\b*\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\bH\u0087\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b+\u0010,\u001a\u001f\u0010#\u001a\u00020$*\u00020%2\u0006\u0010&\u001a\u00020\u000bH\u0087\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b-\u0010.\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u0001H\u0087\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b0\u00101\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u0005H\u0087\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b2\u00103\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\b\u0010)\u001a\u0004\u0018\u00010\bH\u0087\n\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\b4\u001a\u001f\u0010#\u001a\u00020$*\u00020/2\u0006\u0010&\u001a\u00020\u000bH\u0087\u0002\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b5\u00106\u001a\u001f\u00107\u001a\u000208*\u00020\u00012\u0006\u00109\u001a\u00020\u0001H\u0087\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b:\u0010;\u001a\u001f\u00107\u001a\u000208*\u00020\u00052\u0006\u00109\u001a\u00020\u0005H\u0087\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b<\u0010=\u001a\u001f\u00107\u001a\u00020>*\u00020\b2\u0006\u00109\u001a\u00020\bH\u0087\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\b?\u0010@\u001a\u001f\u00107\u001a\u000208*\u00020\u000b2\u0006\u00109\u001a\u00020\u000bH\u0087\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bA\u0010B\u001a\u0015\u0010C\u001a\u00020\u0005*\u00020%H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010D\u001a\u001c\u0010C\u001a\u00020\u0005*\u00020%2\u0006\u0010C\u001a\u00020EH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010F\u001a\u0015\u0010C\u001a\u00020\b*\u00020/H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010G\u001a\u001c\u0010C\u001a\u00020\b*\u00020/2\u0006\u0010C\u001a\u00020EH\u0007\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010H\u001a\f\u0010I\u001a\u000208*\u000208H\u0007\u001a\f\u0010I\u001a\u00020>*\u00020>H\u0007\u001a\u0015\u0010J\u001a\u000208*\u0002082\u0006\u0010J\u001a\u00020KH\u0087\u0004\u001a\u0015\u0010J\u001a\u00020>*\u00020>2\u0006\u0010J\u001a\u00020LH\u0087\u0004\u001a\u001f\u0010M\u001a\u00020%*\u00020\u00012\u0006\u00109\u001a\u00020\u0001H\u0087\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bN\u0010O\u001a\u001f\u0010M\u001a\u00020%*\u00020\u00052\u0006\u00109\u001a\u00020\u0005H\u0087\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bP\u0010Q\u001a\u001f\u0010M\u001a\u00020/*\u00020\b2\u0006\u00109\u001a\u00020\bH\u0087\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bR\u0010S\u001a\u001f\u0010M\u001a\u00020%*\u00020\u000b2\u0006\u00109\u001a\u00020\u000bH\u0087\u0004\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0004\bT\u0010U\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006V"}, m27d2 = {"coerceAtLeast", "Lkotlin/UByte;", "minimumValue", "coerceAtLeast-Kr8caGY", "(BB)B", "Lkotlin/UInt;", "coerceAtLeast-J1ME1BU", "(II)I", "Lkotlin/ULong;", "coerceAtLeast-eb3DHEI", "(JJ)J", "Lkotlin/UShort;", "coerceAtLeast-5PvTz6A", "(SS)S", "coerceAtMost", "maximumValue", "coerceAtMost-Kr8caGY", "coerceAtMost-J1ME1BU", "coerceAtMost-eb3DHEI", "coerceAtMost-5PvTz6A", "coerceIn", "coerceIn-b33U2AM", "(BBB)B", "coerceIn-WZ9TVnA", "(III)I", AsmConstants.CODERANGE, "Lkotlin/ranges/ClosedRange;", "coerceIn-wuiCnnA", "(ILkotlin/ranges/ClosedRange;)I", "coerceIn-sambcqE", "(JJJ)J", "coerceIn-JPwROB0", "(JLkotlin/ranges/ClosedRange;)J", "coerceIn-VKSA0NQ", "(SSS)S", "contains", "", "Lkotlin/ranges/UIntRange;", PropertyDescriptor.VALUE, "contains-68kG9v0", "(Lkotlin/ranges/UIntRange;B)Z", "element", "contains-biwQdVI", "contains-fz5IDCE", "(Lkotlin/ranges/UIntRange;J)Z", "contains-ZsK3CEQ", "(Lkotlin/ranges/UIntRange;S)Z", "Lkotlin/ranges/ULongRange;", "contains-ULb-yJY", "(Lkotlin/ranges/ULongRange;B)Z", "contains-Gab390E", "(Lkotlin/ranges/ULongRange;I)Z", "contains-GYNo2lE", "contains-uhHAxoY", "(Lkotlin/ranges/ULongRange;S)Z", "downTo", "Lkotlin/ranges/UIntProgression;", "to", "downTo-Kr8caGY", "(BB)Lkotlin/ranges/UIntProgression;", "downTo-J1ME1BU", "(II)Lkotlin/ranges/UIntProgression;", "Lkotlin/ranges/ULongProgression;", "downTo-eb3DHEI", "(JJ)Lkotlin/ranges/ULongProgression;", "downTo-5PvTz6A", "(SS)Lkotlin/ranges/UIntProgression;", "random", "(Lkotlin/ranges/UIntRange;)I", "Lkotlin/random/Random;", "(Lkotlin/ranges/UIntRange;Lkotlin/random/Random;)I", "(Lkotlin/ranges/ULongRange;)J", "(Lkotlin/ranges/ULongRange;Lkotlin/random/Random;)J", "reversed", "step", "", "", "until", "until-Kr8caGY", "(BB)Lkotlin/ranges/UIntRange;", "until-J1ME1BU", "(II)Lkotlin/ranges/UIntRange;", "until-eb3DHEI", "(JJ)Lkotlin/ranges/ULongRange;", "until-5PvTz6A", "(SS)Lkotlin/ranges/UIntRange;", "kotlin-stdlib"}, m28xs = "kotlin/ranges/URangesKt")
/* loaded from: L-out.jar:kotlin/ranges/URangesKt___URangesKt.class */
class URangesKt___URangesKt {
    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final int random(@NotNull UIntRange uIntRange) {
        return URangesKt.random(uIntRange, Random.Default);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    private static final long random(@NotNull ULongRange uLongRange) {
        return URangesKt.random(uLongRange, Random.Default);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    public static final int random(@NotNull UIntRange random, @NotNull Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        return URandomKt.nextUInt(random2, random);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    public static final long random(@NotNull ULongRange random, @NotNull Random random2) {
        Intrinsics.checkParameterIsNotNull(random, "$this$random");
        Intrinsics.checkParameterIsNotNull(random2, "random");
        return URandomKt.nextULong(random2, random);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: contains-biwQdVI, reason: not valid java name */
    private static final boolean m1403containsbiwQdVI(@NotNull UIntRange contains, UInt uInt) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return uInt != null && contains.m1397containsWZ4Q5Ns(uInt.m607unboximpl());
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @InlineOnly
    /* renamed from: contains-GYNo2lE, reason: not valid java name */
    private static final boolean m1404containsGYNo2lE(@NotNull ULongRange contains, ULong uLong) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return uLong != null && contains.m1401containsVKZWuLQ(uLong.m665unboximpl());
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: contains-68kG9v0, reason: not valid java name */
    public static final boolean m1405contains68kG9v0(@NotNull UIntRange contains, byte b) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.m1397containsWZ4Q5Ns(b & 255);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: contains-ULb-yJY, reason: not valid java name */
    public static final boolean m1406containsULbyJY(@NotNull ULongRange contains, byte b) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.m1401containsVKZWuLQ(b & 255);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: contains-Gab390E, reason: not valid java name */
    public static final boolean m1407containsGab390E(@NotNull ULongRange contains, int i) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.m1401containsVKZWuLQ(i & JSType.MAX_UINT);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: contains-fz5IDCE, reason: not valid java name */
    public static final boolean m1408containsfz5IDCE(@NotNull UIntRange contains, long j) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return (j >>> 32) == 0 && contains.m1397containsWZ4Q5Ns((int) j);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: contains-ZsK3CEQ, reason: not valid java name */
    public static final boolean m1409containsZsK3CEQ(@NotNull UIntRange contains, short s) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.m1397containsWZ4Q5Ns(s & 65535);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: contains-uhHAxoY, reason: not valid java name */
    public static final boolean m1410containsuhHAxoY(@NotNull ULongRange contains, short s) {
        Intrinsics.checkParameterIsNotNull(contains, "$this$contains");
        return contains.m1401containsVKZWuLQ(s & 65535);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: downTo-Kr8caGY, reason: not valid java name */
    public static final UIntProgression m1411downToKr8caGY(byte b, byte b2) {
        return UIntProgression.Companion.m1396fromClosedRangeNkh28Cs(b & 255, b2 & 255, -1);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: downTo-J1ME1BU, reason: not valid java name */
    public static final UIntProgression m1412downToJ1ME1BU(int i, int i2) {
        return UIntProgression.Companion.m1396fromClosedRangeNkh28Cs(i, i2, -1);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: downTo-eb3DHEI, reason: not valid java name */
    public static final ULongProgression m1413downToeb3DHEI(long j, long j2) {
        return ULongProgression.Companion.m1400fromClosedRange7ftBX0g(j, j2, -1L);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: downTo-5PvTz6A, reason: not valid java name */
    public static final UIntProgression m1414downTo5PvTz6A(short s, short s2) {
        return UIntProgression.Companion.m1396fromClosedRangeNkh28Cs(s & 65535, s2 & 65535, -1);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final UIntProgression reversed(@NotNull UIntProgression reversed) {
        Intrinsics.checkParameterIsNotNull(reversed, "$this$reversed");
        return UIntProgression.Companion.m1396fromClosedRangeNkh28Cs(reversed.getLast(), reversed.getFirst(), -reversed.getStep());
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final ULongProgression reversed(@NotNull ULongProgression reversed) {
        Intrinsics.checkParameterIsNotNull(reversed, "$this$reversed");
        return ULongProgression.Companion.m1400fromClosedRange7ftBX0g(reversed.getLast(), reversed.getFirst(), -reversed.getStep());
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final UIntProgression step(@NotNull UIntProgression step, int i) {
        Intrinsics.checkParameterIsNotNull(step, "$this$step");
        RangesKt.checkStepIsPositive(i > 0, Integer.valueOf(i));
        return UIntProgression.Companion.m1396fromClosedRangeNkh28Cs(step.getFirst(), step.getLast(), step.getStep() > 0 ? i : -i);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    public static final ULongProgression step(@NotNull ULongProgression step, long j) {
        Intrinsics.checkParameterIsNotNull(step, "$this$step");
        RangesKt.checkStepIsPositive(j > 0, Long.valueOf(j));
        return ULongProgression.Companion.m1400fromClosedRange7ftBX0g(step.getFirst(), step.getLast(), step.getStep() > 0 ? j : -j);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: until-Kr8caGY, reason: not valid java name */
    public static final UIntRange m1415untilKr8caGY(byte b, byte b2) {
        int i = b2 & 255;
        return (i < 0 ? '\uffff' : i == 0 ? (char) 0 : (char) 1) <= 0 ? UIntRange.Companion.getEMPTY() : new UIntRange(b & 255, (b2 & 255) - 1, null);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: until-J1ME1BU, reason: not valid java name */
    public static final UIntRange m1416untilJ1ME1BU(int i, int i2) {
        if (UnsignedKt.uintCompare(i2, 0) <= 0) {
            return UIntRange.Companion.getEMPTY();
        }
        return new UIntRange(i, i2 - 1, null);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: until-eb3DHEI, reason: not valid java name */
    public static final ULongRange m1417untileb3DHEI(long j, long j2) {
        if ((j2 ^ Long.MIN_VALUE) <= (0 ^ Long.MIN_VALUE)) {
            return ULongRange.Companion.getEMPTY();
        }
        return new ULongRange(j, j2 - 1, null);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    @NotNull
    /* renamed from: until-5PvTz6A, reason: not valid java name */
    public static final UIntRange m1418until5PvTz6A(short s, short s2) {
        int i = s2 & 65535;
        return (i < 0 ? '\uffff' : i == 0 ? (char) 0 : (char) 1) <= 0 ? UIntRange.Companion.getEMPTY() : new UIntRange(s & 65535, (s2 & 65535) - 1, null);
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: coerceAtLeast-J1ME1BU, reason: not valid java name */
    public static final int m1419coerceAtLeastJ1ME1BU(int i, int i2) {
        return UnsignedKt.uintCompare(i, i2) < 0 ? i2 : i;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: coerceAtLeast-eb3DHEI, reason: not valid java name */
    public static final long m1420coerceAtLeasteb3DHEI(long j, long j2) {
        return (j ^ Long.MIN_VALUE) < (j2 ^ Long.MIN_VALUE) ? j2 : j;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: coerceAtLeast-Kr8caGY, reason: not valid java name */
    public static final byte m1421coerceAtLeastKr8caGY(byte b, byte b2) {
        int i = b & 255;
        int i2 = b2 & 255;
        return (i < i2 ? '\uffff' : i == i2 ? (char) 0 : (char) 1) < 0 ? b2 : b;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: coerceAtLeast-5PvTz6A, reason: not valid java name */
    public static final short m1422coerceAtLeast5PvTz6A(short s, short s2) {
        int i = s & 65535;
        int i2 = s2 & 65535;
        return (i < i2 ? '\uffff' : i == i2 ? (char) 0 : (char) 1) < 0 ? s2 : s;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: coerceAtMost-J1ME1BU, reason: not valid java name */
    public static final int m1423coerceAtMostJ1ME1BU(int i, int i2) {
        return UnsignedKt.uintCompare(i, i2) > 0 ? i2 : i;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: coerceAtMost-eb3DHEI, reason: not valid java name */
    public static final long m1424coerceAtMosteb3DHEI(long j, long j2) {
        return (j ^ Long.MIN_VALUE) > (j2 ^ Long.MIN_VALUE) ? j2 : j;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: coerceAtMost-Kr8caGY, reason: not valid java name */
    public static final byte m1425coerceAtMostKr8caGY(byte b, byte b2) {
        int i = b & 255;
        int i2 = b2 & 255;
        return (i < i2 ? '\uffff' : i == i2 ? (char) 0 : (char) 1) > 0 ? b2 : b;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: coerceAtMost-5PvTz6A, reason: not valid java name */
    public static final short m1426coerceAtMost5PvTz6A(short s, short s2) {
        int i = s & 65535;
        int i2 = s2 & 65535;
        return (i < i2 ? '\uffff' : i == i2 ? (char) 0 : (char) 1) > 0 ? s2 : s;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: coerceIn-WZ9TVnA, reason: not valid java name */
    public static final int m1427coerceInWZ9TVnA(int i, int i2, int i3) {
        if (UnsignedKt.uintCompare(i2, i3) > 0) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + UInt.m604toStringimpl(i3) + " is less than minimum " + UInt.m604toStringimpl(i2) + '.');
        }
        if (UnsignedKt.uintCompare(i, i2) < 0) {
            return i2;
        }
        if (UnsignedKt.uintCompare(i, i3) > 0) {
            return i3;
        }
        return i;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: coerceIn-sambcqE, reason: not valid java name */
    public static final long m1428coerceInsambcqE(long j, long j2, long j3) {
        if ((j2 ^ Long.MIN_VALUE) > (j3 ^ Long.MIN_VALUE)) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + ULong.m662toStringimpl(j3) + " is less than minimum " + ULong.m662toStringimpl(j2) + '.');
        }
        if ((j ^ Long.MIN_VALUE) < (j2 ^ Long.MIN_VALUE)) {
            return j2;
        }
        if ((j ^ Long.MIN_VALUE) > (j3 ^ Long.MIN_VALUE)) {
            return j3;
        }
        return j;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: coerceIn-b33U2AM, reason: not valid java name */
    public static final byte m1429coerceInb33U2AM(byte b, byte b2, byte b3) {
        int i = b2 & 255;
        int i2 = b3 & 255;
        if ((i < i2 ? '\uffff' : i == i2 ? (char) 0 : (char) 1) > 0) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + UByte.m546toStringimpl(b3) + " is less than minimum " + UByte.m546toStringimpl(b2) + '.');
        }
        int i3 = b & 255;
        int i4 = b2 & 255;
        if ((i3 < i4 ? '\uffff' : i3 == i4 ? (char) 0 : (char) 1) < 0) {
            return b2;
        }
        int i5 = b & 255;
        int i6 = b3 & 255;
        if ((i5 < i6 ? '\uffff' : i5 == i6 ? (char) 0 : (char) 1) > 0) {
            return b3;
        }
        return b;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: coerceIn-VKSA0NQ, reason: not valid java name */
    public static final short m1430coerceInVKSA0NQ(short s, short s2, short s3) {
        int i = s2 & 65535;
        int i2 = s3 & 65535;
        if ((i < i2 ? '\uffff' : i == i2 ? (char) 0 : (char) 1) > 0) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: maximum " + UShort.m745toStringimpl(s3) + " is less than minimum " + UShort.m745toStringimpl(s2) + '.');
        }
        int i3 = s & 65535;
        int i4 = s2 & 65535;
        if ((i3 < i4 ? '\uffff' : i3 == i4 ? (char) 0 : (char) 1) < 0) {
            return s2;
        }
        int i5 = s & 65535;
        int i6 = s3 & 65535;
        if ((i5 < i6 ? '\uffff' : i5 == i6 ? (char) 0 : (char) 1) > 0) {
            return s3;
        }
        return s;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: coerceIn-wuiCnnA, reason: not valid java name */
    public static final int m1431coerceInwuiCnnA(int i, @NotNull ClosedRange range) {
        Intrinsics.checkParameterIsNotNull(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return ((UInt) RangesKt.coerceIn((Comparable) UInt.m605boximpl(i), (ClosedFloatingPointRange) range)).m607unboximpl();
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        }
        if (UnsignedKt.uintCompare(i, ((UInt) range.getStart()).m607unboximpl()) < 0) {
            return ((UInt) range.getStart()).m607unboximpl();
        }
        if (UnsignedKt.uintCompare(i, ((UInt) range.getEndInclusive()).m607unboximpl()) > 0) {
            return ((UInt) range.getEndInclusive()).m607unboximpl();
        }
        return i;
    }

    @SinceKotlin(version = "1.3")
    @ExperimentalUnsignedTypes
    /* renamed from: coerceIn-JPwROB0, reason: not valid java name */
    public static final long m1432coerceInJPwROB0(long j, @NotNull ClosedRange range) {
        Intrinsics.checkParameterIsNotNull(range, "range");
        if (range instanceof ClosedFloatingPointRange) {
            return ((ULong) RangesKt.coerceIn((Comparable) ULong.m663boximpl(j), (ClosedFloatingPointRange) range)).m665unboximpl();
        }
        if (range.isEmpty()) {
            throw new IllegalArgumentException("Cannot coerce value to an empty range: " + range + '.');
        }
        if ((j ^ Long.MIN_VALUE) < (((ULong) range.getStart()).m665unboximpl() ^ Long.MIN_VALUE)) {
            return ((ULong) range.getStart()).m665unboximpl();
        }
        if ((j ^ Long.MIN_VALUE) > (((ULong) range.getEndInclusive()).m665unboximpl() ^ Long.MIN_VALUE)) {
            return ((ULong) range.getEndInclusive()).m665unboximpl();
        }
        return j;
    }
}
