package kotlin.system;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0010\u0001\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\u001a\u0011\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0087\b\u00a8\u0006\u0004"}, m27d2 = {"exitProcess", "", "status", "", "kotlin-stdlib"})
@JvmName(name = "ProcessKt")
/* loaded from: L-out.jar:kotlin/system/ProcessKt.class */
public final class ProcessKt {
    @InlineOnly
    private static final Void exitProcess(int i) {
        System.exit(i);
        throw new RuntimeException("System.exit returned normally, while it was supposed to halt JVM.");
    }
}
