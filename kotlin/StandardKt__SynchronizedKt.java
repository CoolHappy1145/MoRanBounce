package kotlin;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.internal.InlineOnly;
import kotlin.jvm.functions.Function0;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\b\u0003\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a7\u0010\ufffd\ufffd\u001a\u0002H\u0001\"\u0004\b\ufffd\ufffd\u0010\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0005H\u0087\b\u0082\u0002\n\n\b\b\u0001\u0012\u0002\u0010\u0002 \u0001\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0007"}, m27d2 = {"synchronized", "R", "lock", "", "block", "Lkotlin/Function0;", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "kotlin-stdlib"}, m28xs = "kotlin/StandardKt")
/* loaded from: L-out.jar:kotlin/StandardKt__SynchronizedKt.class */
class StandardKt__SynchronizedKt extends StandardKt__StandardKt {
    @InlineOnly
    /* renamed from: synchronized, reason: not valid java name */
    private static final Object m510synchronized(Object obj, Function0 function0) {
        Object objInvoke;
        synchronized (obj) {
            try {
                objInvoke = function0.invoke();
            } catch (Throwable th) {
                throw th;
            }
        }
        return objInvoke;
    }
}
