package kotlin.coroutines.experimental.migration;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.coroutines.experimental.Continuation;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u0001*\u0004\b\u0001\u0010\u00022\u001c\u0012\u0004\u0012\u0002H\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0004\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0003B'\u0012 \u0010\u0006\u001a\u001c\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0003\u00a2\u0006\u0002\u0010\bJ&\u0010\u000b\u001a\u0004\u0018\u00010\u00052\u0006\u0010\f\u001a\u00028\ufffd\ufffd2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004H\u0096\u0002\u00a2\u0006\u0002\u0010\u000eR+\u0010\u0006\u001a\u001c\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\u0007\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000f"}, m27d2 = {"Lkotlin/coroutines/experimental/migration/ExperimentalSuspendFunction1Migration;", "T1", "R", "Lkotlin/Function2;", "Lkotlin/coroutines/experimental/Continuation;", "", "function", "Lkotlin/coroutines/Continuation;", "(Lkotlin/jvm/functions/Function2;)V", "getFunction", "()Lkotlin/jvm/functions/Function2;", "invoke", "t1", "continuation", "(Ljava/lang/Object;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "kotlin-stdlib-coroutines"})
/* loaded from: L-out.jar:kotlin/coroutines/experimental/migration/ExperimentalSuspendFunction1Migration.class */
final class ExperimentalSuspendFunction1Migration implements Function2 {

    @NotNull
    private final Function2 function;

    @Override // kotlin.jvm.functions.Function2
    public Object invoke(Object obj, Object obj2) {
        return invoke(obj, (Continuation) obj2);
    }

    public ExperimentalSuspendFunction1Migration(@NotNull Function2 function) {
        Intrinsics.checkParameterIsNotNull(function, "function");
        this.function = function;
    }

    @NotNull
    public final Function2 getFunction() {
        return this.function;
    }

    @Nullable
    public Object invoke(Object obj, @NotNull Continuation continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        return this.function.invoke(obj, CoroutinesMigrationKt.toContinuation(continuation));
    }
}
