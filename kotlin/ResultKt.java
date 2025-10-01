package kotlin;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Result;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd:\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u0003\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0010\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0001\u001a+\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\ufffd\ufffd\u0010\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00060\bH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\t\u001a\u0084\u0001\u0010\n\u001a\u0002H\u0006\"\u0004\b\ufffd\ufffd\u0010\u0006\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\f\u001a\u001d\u0012\u0013\u0012\u0011H\u000b\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u00060\r2!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\b\u00f8\u0001\ufffd\ufffd\u0082\u0002\u0014\n\b\b\u0001\u0012\u0002\u0010\u0001 \ufffd\ufffd\n\b\b\u0001\u0012\u0002\u0010\u0002 \ufffd\ufffd\u00a2\u0006\u0002\u0010\u0012\u001a3\u0010\u0013\u001a\u0002H\u0006\"\u0004\b\ufffd\ufffd\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052\u0006\u0010\u0014\u001a\u0002H\u0006H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0015\u001a[\u0010\u0016\u001a\u0002H\u0006\"\u0004\b\ufffd\ufffd\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\b\u00f8\u0001\ufffd\ufffd\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \ufffd\ufffd\u00a2\u0006\u0002\u0010\u0017\u001a!\u0010\u0018\u001a\u0002H\u000b\"\u0004\b\ufffd\ufffd\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u0005H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0019\u001a]\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\ufffd\ufffd\u0010\u0006\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u0011H\u000b\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u00060\rH\u0087\b\u00f8\u0001\ufffd\ufffd\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \ufffd\ufffd\u00a2\u0006\u0002\u0010\u0017\u001aP\u0010\u001c\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\ufffd\ufffd\u0010\u0006\"\u0004\b\u0001\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u0011H\u000b\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u0002H\u00060\rH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0017\u001aW\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0005\"\u0004\b\ufffd\ufffd\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001d\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u00020\u001e0\rH\u0087\b\u00f8\u0001\ufffd\ufffd\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \ufffd\ufffd\u00a2\u0006\u0002\u0010\u0017\u001aW\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0005\"\u0004\b\ufffd\ufffd\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001d\u001a\u001d\u0012\u0013\u0012\u0011H\u000b\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0010\u0012\u0004\u0012\u00020\u001e0\rH\u0087\b\u00f8\u0001\ufffd\ufffd\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \ufffd\ufffd\u00a2\u0006\u0002\u0010\u0017\u001aa\u0010\u001f\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\ufffd\ufffd\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\b\u00f8\u0001\ufffd\ufffd\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0001 \ufffd\ufffd\u00a2\u0006\u0002\u0010\u0017\u001aT\u0010 \u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\ufffd\ufffd\u0010\u0006\"\b\b\u0001\u0010\u000b*\u0002H\u0006*\b\u0012\u0004\u0012\u0002H\u000b0\u00052!\u0010\u001b\u001a\u001d\u0012\u0013\u0012\u00110\u0003\u00a2\u0006\f\b\u000e\u0012\b\b\u000f\u0012\u0004\b\b(\u0002\u0012\u0004\u0012\u0002H\u00060\rH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0017\u001a@\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00060\u0005\"\u0004\b\ufffd\ufffd\u0010\u000b\"\u0004\b\u0001\u0010\u0006*\u0002H\u000b2\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\u00060\r\u00a2\u0006\u0002\b!H\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0017\u001a\u0018\u0010\"\u001a\u00020\u001e*\u0006\u0012\u0002\b\u00030\u0005H\u0001\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010#\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006$"}, m27d2 = {"createFailure", "", "exception", "", "runCatching", "Lkotlin/Result;", "R", "block", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "fold", "T", "onSuccess", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", PropertyDescriptor.VALUE, "onFailure", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getOrDefault", "defaultValue", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "getOrElse", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "getOrThrow", "(Ljava/lang/Object;)Ljava/lang/Object;", "map", "transform", "mapCatching", "action", "", "recover", "recoverCatching", "Lkotlin/ExtensionFunctionType;", "throwOnFailure", "(Ljava/lang/Object;)V", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/ResultKt.class */
public final class ResultKt {
    @SinceKotlin(version = "1.3")
    @PublishedApi
    @NotNull
    public static final Object createFailure(@NotNull Throwable exception) {
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        return new Result.Failure(exception);
    }

    @SinceKotlin(version = "1.3")
    @PublishedApi
    public static final void throwOnFailure(@NotNull Object obj) {
        if (obj instanceof Result.Failure) {
            throw ((Result.Failure) obj).exception;
        }
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object runCatching(Function0 function0) {
        Object objCreateFailure;
        try {
            Result.Companion companion = Result.Companion;
            objCreateFailure = function0.invoke();
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objCreateFailure = createFailure(th);
        }
        return objCreateFailure;
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object runCatching(Object obj, Function1 function1) {
        Object objCreateFailure;
        try {
            Result.Companion companion = Result.Companion;
            objCreateFailure = function1.invoke(obj);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objCreateFailure = createFailure(th);
        }
        return objCreateFailure;
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object getOrThrow(@NotNull Object obj) {
        throwOnFailure(obj);
        return obj;
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object getOrElse(@NotNull Object obj, Function1 function1) {
        Throwable thM502exceptionOrNullimpl = Result.m502exceptionOrNullimpl(obj);
        if (thM502exceptionOrNullimpl == null) {
            return obj;
        }
        return function1.invoke(thM502exceptionOrNullimpl);
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object getOrDefault(@NotNull Object obj, Object obj2) {
        return obj instanceof Result.Failure ? obj2 : obj;
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object fold(@NotNull Object obj, Function1 function1, Function1 function12) {
        Throwable thM502exceptionOrNullimpl = Result.m502exceptionOrNullimpl(obj);
        if (thM502exceptionOrNullimpl == null) {
            return function1.invoke(obj);
        }
        return function12.invoke(thM502exceptionOrNullimpl);
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object map(@NotNull Object obj, Function1 function1) {
        if (!(!(obj instanceof Result.Failure))) {
            return obj;
        }
        Result.Companion companion = Result.Companion;
        return function1.invoke(obj);
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object mapCatching(@NotNull Object obj, Function1 function1) {
        Object objCreateFailure;
        if (!(!(obj instanceof Result.Failure))) {
            return obj;
        }
        try {
            Result.Companion companion = Result.Companion;
            objCreateFailure = function1.invoke(obj);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objCreateFailure = createFailure(th);
        }
        return objCreateFailure;
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object recover(@NotNull Object obj, Function1 function1) {
        Throwable thM502exceptionOrNullimpl = Result.m502exceptionOrNullimpl(obj);
        if (thM502exceptionOrNullimpl == null) {
            return obj;
        }
        Result.Companion companion = Result.Companion;
        return function1.invoke(thM502exceptionOrNullimpl);
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object recoverCatching(@NotNull Object obj, Function1 function1) {
        Object objCreateFailure;
        Throwable thM502exceptionOrNullimpl = Result.m502exceptionOrNullimpl(obj);
        if (thM502exceptionOrNullimpl == null) {
            return obj;
        }
        try {
            Result.Companion companion = Result.Companion;
            objCreateFailure = function1.invoke(thM502exceptionOrNullimpl);
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            objCreateFailure = createFailure(th);
        }
        return objCreateFailure;
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object onFailure(@NotNull Object obj, Function1 function1) {
        Throwable thM502exceptionOrNullimpl = Result.m502exceptionOrNullimpl(obj);
        if (thM502exceptionOrNullimpl != null) {
            function1.invoke(thM502exceptionOrNullimpl);
        }
        return obj;
    }

    @SinceKotlin(version = "1.3")
    @InlineOnly
    private static final Object onSuccess(@NotNull Object obj, Function1 function1) {
        if (!(obj instanceof Result.Failure)) {
            function1.invoke(obj);
        }
        return obj;
    }
}
