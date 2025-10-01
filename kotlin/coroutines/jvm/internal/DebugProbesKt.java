package kotlin.coroutines.jvm.internal;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.coroutines.Continuation;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u0012\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\u001a\"\u0010\ufffd\ufffd\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\ufffd\ufffd\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001H\u0001\u001a\u0014\u0010\u0004\u001a\u00020\u00052\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0001H\u0001\u001a\u0014\u0010\u0007\u001a\u00020\u00052\n\u0010\u0006\u001a\u0006\u0012\u0002\b\u00030\u0001H\u0001\u00a8\u0006\b"}, m27d2 = {"probeCoroutineCreated", "Lkotlin/coroutines/Continuation;", "T", "completion", "probeCoroutineResumed", "", "frame", "probeCoroutineSuspended", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/coroutines/jvm/internal/DebugProbesKt.class */
public final class DebugProbesKt {
    @SinceKotlin(version = "1.3")
    @NotNull
    public static final Continuation probeCoroutineCreated(@NotNull Continuation completion) {
        Intrinsics.checkParameterIsNotNull(completion, "completion");
        return completion;
    }

    @SinceKotlin(version = "1.3")
    public static final void probeCoroutineResumed(@NotNull Continuation frame) {
        Intrinsics.checkParameterIsNotNull(frame, "frame");
    }

    @SinceKotlin(version = "1.3")
    public static final void probeCoroutineSuspended(@NotNull Continuation frame) {
        Intrinsics.checkParameterIsNotNull(frame, "frame");
    }
}
