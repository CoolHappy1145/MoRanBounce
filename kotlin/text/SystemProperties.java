package kotlin.text;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u000e\n\ufffd\ufffd\b\u00c2\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0005"}, m27d2 = {"Lkotlin/text/SystemProperties;", "", "()V", "LINE_SEPARATOR", "", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/text/SystemProperties.class */
final class SystemProperties {

    @JvmField
    @NotNull
    public static final String LINE_SEPARATOR;
    public static final SystemProperties INSTANCE = new SystemProperties();

    private SystemProperties() {
    }

    static {
        String property = System.getProperty("line.separator");
        if (property == null) {
            Intrinsics.throwNpe();
        }
        LINE_SEPARATOR = property;
    }
}
