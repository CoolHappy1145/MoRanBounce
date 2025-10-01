package kotlin.coroutines.experimental.migration;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.coroutines.experimental.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0002\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u00012\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0003\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0002B!\u0012\u001a\u0010\u0005\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0002\u00a2\u0006\u0002\u0010\u0007J\u0019\u0010\n\u001a\u0004\u0018\u00010\u00042\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0003H\u0096\u0002R%\u0010\u0005\u001a\u0016\u0012\n\u0012\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0006\u0012\u0006\u0012\u0004\u0018\u00010\u00040\u0002\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\t\u00a8\u0006\f"}, m27d2 = {"Lkotlin/coroutines/experimental/migration/ExperimentalSuspendFunction0Migration;", "R", "Lkotlin/Function1;", "Lkotlin/coroutines/experimental/Continuation;", "", "function", "Lkotlin/coroutines/Continuation;", "(Lkotlin/jvm/functions/Function1;)V", "getFunction", "()Lkotlin/jvm/functions/Function1;", "invoke", "continuation", "kotlin-stdlib-coroutines"})
/* loaded from: L-out.jar:kotlin/coroutines/experimental/migration/ExperimentalSuspendFunction0Migration.class */
final class ExperimentalSuspendFunction0Migration implements Function1 {

    @NotNull
    private final Function1 function;

    @Override // kotlin.jvm.functions.Function1
    public Object invoke(Object obj) {
        return invoke((Continuation) obj);
    }

    public ExperimentalSuspendFunction0Migration(@NotNull Function1 function) {
        Intrinsics.checkParameterIsNotNull(function, "function");
        this.function = function;
    }

    @NotNull
    public final Function1 getFunction() {
        return this.function;
    }

    @Nullable
    public Object invoke(@NotNull Continuation continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        return this.function.invoke(CoroutinesMigrationKt.toContinuation(continuation));
    }
}
