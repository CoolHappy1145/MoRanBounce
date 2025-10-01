package kotlin;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd:\n\ufffd\ufffd\n\u0002\u0010\u0001\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\u001a\t\u0010\ufffd\ufffd\u001a\u00020\u0001H\u0087\b\u001a\u0011\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b\u001a0\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00050\tH\u0087\b\u0082\u0002\b\n\u0006\b\u0001\u0012\u0002\u0010\u0002\u001a/\u0010\n\u001a\u0002H\u000b\"\u0004\b\ufffd\ufffd\u0010\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\rH\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u00a2\u0006\u0002\u0010\u000e\u001aH\u0010\u000f\u001a\u0002H\u000b\"\u0004\b\ufffd\ufffd\u0010\u0010\"\u0004\b\u0001\u0010\u000b2\u0006\u0010\u0011\u001a\u0002H\u00102\u0017\u0010\f\u001a\u0013\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u000b0\t\u00a2\u0006\u0002\b\u0012H\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u00a2\u0006\u0002\u0010\u0013\u001a9\u0010\u0014\u001a\u0002H\u0010\"\u0004\b\ufffd\ufffd\u0010\u0010*\u0002H\u00102\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u00050\tH\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u00a2\u0006\u0002\u0010\u0013\u001a>\u0010\u0015\u001a\u0002H\u0010\"\u0004\b\ufffd\ufffd\u0010\u0010*\u0002H\u00102\u0017\u0010\f\u001a\u0013\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u00050\t\u00a2\u0006\u0002\b\u0012H\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u00a2\u0006\u0002\u0010\u0013\u001a?\u0010\u0016\u001a\u0002H\u000b\"\u0004\b\ufffd\ufffd\u0010\u0010\"\u0004\b\u0001\u0010\u000b*\u0002H\u00102\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u000b0\tH\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u00a2\u0006\u0002\u0010\u0013\u001aD\u0010\n\u001a\u0002H\u000b\"\u0004\b\ufffd\ufffd\u0010\u0010\"\u0004\b\u0001\u0010\u000b*\u0002H\u00102\u0017\u0010\f\u001a\u0013\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u0002H\u000b0\t\u00a2\u0006\u0002\b\u0012H\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u00a2\u0006\u0002\u0010\u0013\u001a;\u0010\u0017\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\ufffd\ufffd\u0010\u0010*\u0002H\u00102\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u00190\tH\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u00a2\u0006\u0002\u0010\u0013\u001a;\u0010\u001a\u001a\u0004\u0018\u0001H\u0010\"\u0004\b\ufffd\ufffd\u0010\u0010*\u0002H\u00102\u0012\u0010\u0018\u001a\u000e\u0012\u0004\u0012\u0002H\u0010\u0012\u0004\u0012\u00020\u00190\tH\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \u0001\u00a2\u0006\u0002\u0010\u0013\u00a8\u0006\u001b"}, m27d2 = {"TODO", "", "reason", "", "repeat", "", "times", "", "action", "Lkotlin/Function1;", "run", "R", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "with", "T", "receiver", "Lkotlin/ExtensionFunctionType;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "also", "apply", "let", "takeIf", "predicate", "", "takeUnless", "kotlin-stdlib"}, m28xs = "kotlin/StandardKt")
/* loaded from: L-out.jar:kotlin/StandardKt__StandardKt.class */
class StandardKt__StandardKt {
    @InlineOnly
    private static final Void TODO() {
        throw new NotImplementedError(null, 1, null);
    }

    @InlineOnly
    private static final Void TODO(String str) {
        throw new NotImplementedError("An operation is not implemented: " + str);
    }

    @InlineOnly
    private static final Object run(Function0 function0) {
        return function0.invoke();
    }

    @InlineOnly
    private static final Object run(Object obj, Function1 function1) {
        return function1.invoke(obj);
    }

    @InlineOnly
    private static final Object with(Object obj, Function1 function1) {
        return function1.invoke(obj);
    }

    @InlineOnly
    private static final Object apply(Object obj, Function1 function1) {
        function1.invoke(obj);
        return obj;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final Object also(Object obj, Function1 function1) {
        function1.invoke(obj);
        return obj;
    }

    @InlineOnly
    private static final Object let(Object obj, Function1 function1) {
        return function1.invoke(obj);
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final Object takeIf(Object obj, Function1 function1) {
        if (((Boolean) function1.invoke(obj)).booleanValue()) {
            return obj;
        }
        return null;
    }

    @SinceKotlin(version = "1.1")
    @InlineOnly
    private static final Object takeUnless(Object obj, Function1 function1) {
        if (((Boolean) function1.invoke(obj)).booleanValue()) {
            return null;
        }
        return obj;
    }

    @InlineOnly
    private static final void repeat(int i, Function1 function1) {
        for (int i2 = 0; i2 < i; i2++) {
            function1.invoke(Integer.valueOf(i2));
        }
    }
}
