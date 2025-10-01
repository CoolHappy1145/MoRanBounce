package kotlin;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\u0018\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u001a\u0011\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b\u001a\u001f\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0087\b\u00a8\u0006\u0007"}, m27d2 = {"assert", "", PropertyDescriptor.VALUE, "", "lazyMessage", "Lkotlin/Function0;", "", "kotlin-stdlib"}, m28xs = "kotlin/PreconditionsKt")
/* loaded from: L-out.jar:kotlin/PreconditionsKt__AssertionsJVMKt.class */
class PreconditionsKt__AssertionsJVMKt {
    @InlineOnly
    /* renamed from: assert, reason: not valid java name */
    private static final void m499assert(boolean z) {
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError("Assertion failed");
        }
    }

    @InlineOnly
    /* renamed from: assert, reason: not valid java name */
    private static final void m500assert(boolean z, Function0 function0) {
        if (_Assertions.ENABLED && !z) {
            throw new AssertionError(function0.invoke());
        }
    }
}
