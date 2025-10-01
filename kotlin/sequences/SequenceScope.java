package kotlin.sequences;

import java.util.Collection;
import java.util.Iterator;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.SinceKotlin;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.RestrictsSuspension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RestrictsSuspension
@SinceKotlin(version = "1.3")
@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b'\u0018\ufffd\ufffd*\u0006\b\ufffd\ufffd\u0010\u0001 \ufffd\ufffd2\u00020\u0002B\u0007\b\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0003J\u0019\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00028\ufffd\ufffdH\u00a6@\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0007J\u001f\u0010\b\u001a\u00020\u00052\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\nH\u0086@\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u000bJ\u001f\u0010\b\u001a\u00020\u00052\f\u0010\f\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\rH\u00a6@\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u000eJ\u001f\u0010\b\u001a\u00020\u00052\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0010H\u0086@\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0011\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0012"}, m27d2 = {"Lkotlin/sequences/SequenceScope;", "T", "", "()V", "yield", "", PropertyDescriptor.VALUE, "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "yieldAll", "elements", "", "(Ljava/lang/Iterable;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "iterator", "", "(Ljava/util/Iterator;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "sequence", "Lkotlin/sequences/Sequence;", "(Lkotlin/sequences/Sequence;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/sequences/SequenceScope.class */
public abstract class SequenceScope {
    @Nullable
    public abstract Object yield(Object obj, @NotNull Continuation continuation);

    @Nullable
    public abstract Object yieldAll(@NotNull Iterator it, @NotNull Continuation continuation);

    @Nullable
    public final Object yieldAll(@NotNull Iterable iterable, @NotNull Continuation continuation) {
        return ((iterable instanceof Collection) && ((Collection) iterable).isEmpty()) ? Unit.INSTANCE : yieldAll(iterable.iterator(), continuation);
    }

    @Nullable
    public final Object yieldAll(@NotNull Sequence sequence, @NotNull Continuation continuation) {
        return yieldAll(sequence.iterator(), continuation);
    }
}
