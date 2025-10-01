package net.ccbluex.liquidbounce.features.special;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0003\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\ufffd\ufffdR$\u0010\u0007\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004@FX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001e\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r@BX\u0086\u000e\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0010"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/special/AutoReconnect;", "", "()V", "MAX", "", "MIN", PropertyDescriptor.VALUE, "delay", "getDelay", "()I", "setDelay", "(I)V", "<set-?>", "", "isEnabled", "()Z", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/special/AutoReconnect.class */
public final class AutoReconnect {
    public static final int MAX = 60000;
    public static final int MIN = 1000;
    public static final AutoReconnect INSTANCE = new AutoReconnect();
    private static boolean isEnabled = true;
    private static int delay = 5000;

    private AutoReconnect() {
    }

    public final boolean isEnabled() {
        return isEnabled;
    }

    public final int getDelay() {
        return delay;
    }

    public final void setDelay(int i) {
        isEnabled = i < 60000;
        delay = i;
    }
}
