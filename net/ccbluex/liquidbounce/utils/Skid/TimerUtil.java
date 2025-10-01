package net.ccbluex.liquidbounce.utils.Skid;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/Skid/TimerUtil.class */
public class TimerUtil {
    private long lastMS;

    private long getCurrentMS() {
        return System.nanoTime() / 1000000;
    }

    public boolean hasReached(double d) {
        return ((double) (getCurrentMS() - this.lastMS)) >= d;
    }

    public final long getElapsedTime() {
        return getCurrentMS() - this.lastMS;
    }

    public void reset() {
        this.lastMS = getCurrentMS();
    }

    public boolean delay(float f) {
        return ((float) (getTime() - this.lastMS)) >= f;
    }

    public long getTime() {
        return System.nanoTime() / 1000000;
    }

    public boolean isDelayComplete(long j) {
        return System.currentTimeMillis() - this.lastMS > j;
    }
}
