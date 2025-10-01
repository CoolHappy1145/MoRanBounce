package net.ccbluex.liquidbounce.utils.timer;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/timer/MSTimer.class */
public final class MSTimer {
    private long time = -1;
    public long lastMS = 0;

    public boolean hasTimePassed(long j) {
        return System.currentTimeMillis() >= this.time + j;
    }

    public boolean hasReached(long j) {
        return getCurrentMS() - this.lastMS >= j;
    }

    public long getCurrentMS() {
        return System.nanoTime() / 1000000;
    }

    public long hasTimeLeft(long j) {
        return (j + this.time) - System.currentTimeMillis();
    }

    public void reset() {
        this.time = System.currentTimeMillis();
    }

    public void resetIsRise() {
        this.lastMS = getCurrentMS();
    }
}
