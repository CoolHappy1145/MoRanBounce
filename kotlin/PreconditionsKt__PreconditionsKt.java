package kotlin;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\"\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0005\n\u0002\u0010\u0001\n\u0002\b\u0004\u001a\u001c\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b\u0082\u0002\b\n\u0006\b\ufffd\ufffd\u001a\u0002\u0010\u0001\u001a*\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\b\u0082\u0002\b\n\u0006\b\ufffd\ufffd\u001a\u0002\u0010\u0001\u001a/\u0010\u0007\u001a\u0002H\b\"\b\b\ufffd\ufffd\u0010\b*\u00020\u00062\b\u0010\u0002\u001a\u0004\u0018\u0001H\bH\u0087\b\u0082\u0002\n\n\b\b\ufffd\ufffd\u001a\u0004\b\u0003\u0010\u0001\u00a2\u0006\u0002\u0010\t\u001a=\u0010\u0007\u001a\u0002H\b\"\b\b\ufffd\ufffd\u0010\b*\u00020\u00062\b\u0010\u0002\u001a\u0004\u0018\u0001H\b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\b\u0082\u0002\n\n\b\b\ufffd\ufffd\u001a\u0004\b\u0003\u0010\u0001\u00a2\u0006\u0002\u0010\n\u001a\u0011\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0006H\u0087\b\u001a\u001c\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b\u0082\u0002\b\n\u0006\b\ufffd\ufffd\u001a\u0002\u0010\u0001\u001a*\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\b\u0082\u0002\b\n\u0006\b\ufffd\ufffd\u001a\u0002\u0010\u0001\u001a/\u0010\u000f\u001a\u0002H\b\"\b\b\ufffd\ufffd\u0010\b*\u00020\u00062\b\u0010\u0002\u001a\u0004\u0018\u0001H\bH\u0087\b\u0082\u0002\n\n\b\b\ufffd\ufffd\u001a\u0004\b\u0003\u0010\u0001\u00a2\u0006\u0002\u0010\t\u001a=\u0010\u000f\u001a\u0002H\b\"\b\b\ufffd\ufffd\u0010\b*\u00020\u00062\b\u0010\u0002\u001a\u0004\u0018\u0001H\b2\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\b\u0082\u0002\n\n\b\b\ufffd\ufffd\u001a\u0004\b\u0003\u0010\u0001\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u0010"}, m27d2 = {"check", "", PropertyDescriptor.VALUE, "", "lazyMessage", "Lkotlin/Function0;", "", "checkNotNull", "T", "(Ljava/lang/Object;)Ljava/lang/Object;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "error", "", "message", "require", "requireNotNull", "kotlin-stdlib"}, m28xs = "kotlin/PreconditionsKt")
/* loaded from: L-out.jar:kotlin/PreconditionsKt__PreconditionsKt.class */
class PreconditionsKt__PreconditionsKt extends PreconditionsKt__AssertionsJVMKt {
    @InlineOnly
    private static final void require(boolean z) {
        if (!z) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
    }

    @InlineOnly
    private static final void require(boolean z, Function0 function0) {
        if (!z) {
            throw new IllegalArgumentException(function0.invoke().toString());
        }
    }

    @InlineOnly
    private static final Object requireNotNull(Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Required value was null.".toString());
        }
        return obj;
    }

    @InlineOnly
    private static final Object requireNotNull(Object obj, Function0 function0) {
        if (obj == null) {
            throw new IllegalArgumentException(function0.invoke().toString());
        }
        return obj;
    }

    @InlineOnly
    private static final void check(boolean z) {
        if (!z) {
            throw new IllegalStateException("Check failed.".toString());
        }
    }

    @InlineOnly
    private static final void check(boolean z, Function0 function0) {
        if (!z) {
            throw new IllegalStateException(function0.invoke().toString());
        }
    }

    @InlineOnly
    private static final Object checkNotNull(Object obj) {
        if (obj == null) {
            throw new IllegalStateException("Required value was null.".toString());
        }
        return obj;
    }

    @InlineOnly
    private static final Object checkNotNull(Object obj, Function0 function0) {
        if (obj == null) {
            throw new IllegalStateException(function0.invoke().toString());
        }
        return obj;
    }

    @InlineOnly
    private static final Void error(Object obj) {
        throw new IllegalStateException(obj.toString());
    }
}
