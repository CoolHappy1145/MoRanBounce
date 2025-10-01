package kotlin.coroutines.experimental.migration;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.coroutines.experimental.Continuation;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\t\b\u0002\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u0001*\u0004\b\u0001\u0010\u0002*\u0004\b\u0002\u0010\u00032\"\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00030\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004B-\u0012&\u0010\u0007\u001a\"\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004\u00a2\u0006\u0002\u0010\tJ.\u0010\f\u001a\u0004\u0018\u00010\u00062\u0006\u0010\r\u001a\u00028\ufffd\ufffd2\u0006\u0010\u000e\u001a\u00028\u00012\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\u00020\u0005H\u0096\u0002\u00a2\u0006\u0002\u0010\u0010R1\u0010\u0007\u001a\"\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\b\u0012\u0006\u0012\u0004\u0018\u00010\u00060\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000b\u00a8\u0006\u0011"}, m27d2 = {"Lkotlin/coroutines/experimental/migration/ExperimentalSuspendFunction2Migration;", "T1", "T2", "R", "Lkotlin/Function3;", "Lkotlin/coroutines/experimental/Continuation;", "", "function", "Lkotlin/coroutines/Continuation;", "(Lkotlin/jvm/functions/Function3;)V", "getFunction", "()Lkotlin/jvm/functions/Function3;", "invoke", "t1", "t2", "continuation", "(Ljava/lang/Object;Ljava/lang/Object;Lkotlin/coroutines/experimental/Continuation;)Ljava/lang/Object;", "kotlin-stdlib-coroutines"})
/* loaded from: L-out.jar:kotlin/coroutines/experimental/migration/ExperimentalSuspendFunction2Migration.class */
final class ExperimentalSuspendFunction2Migration implements Function3 {

    @NotNull
    private final Function3 function;

    @Override // kotlin.jvm.functions.Function3
    public Object invoke(Object obj, Object obj2, Object obj3) {
        return invoke(obj, obj2, (Continuation) obj3);
    }

    public ExperimentalSuspendFunction2Migration(@NotNull Function3 function) {
        Intrinsics.checkParameterIsNotNull(function, "function");
        this.function = function;
    }

    @NotNull
    public final Function3 getFunction() {
        return this.function;
    }

    @Nullable
    public Object invoke(Object obj, Object obj2, @NotNull Continuation continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        return this.function.invoke(obj, obj2, CoroutinesMigrationKt.toContinuation(continuation));
    }
}
