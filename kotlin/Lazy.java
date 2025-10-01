package kotlin;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0004\n\u0002\u0010\u000b\n\ufffd\ufffd\bf\u0018\ufffd\ufffd*\u0006\b\ufffd\ufffd\u0010\u0001 \u00012\u00020\u0002J\b\u0010\u0006\u001a\u00020\u0007H&R\u0012\u0010\u0003\u001a\u00028\ufffd\ufffdX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\b"}, m27d2 = {"Lkotlin/Lazy;", "T", "", PropertyDescriptor.VALUE, "getValue", "()Ljava/lang/Object;", "isInitialized", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/Lazy.class */
public interface Lazy {
    Object getValue();

    boolean isInitialized();
}
