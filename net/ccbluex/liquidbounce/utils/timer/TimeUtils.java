package net.ccbluex.liquidbounce.utils.timer;

import net.ccbluex.liquidbounce.utils.misc.RandomUtils;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/timer/TimeUtils.class */
public final class TimeUtils {
    private long lastMS;

    public static long randomDelay(int i, int i2) {
        return RandomUtils.nextInt(i, i2);
    }

    public static long randomClickDelay(int i, int i2) {
        return (long) ((Math.random() * (((1000 / i) - (1000 / i2)) + 1)) + (1000 / i2));
    }

    private long getCurrentMS() {
        return System.nanoTime() / 1000000;
    }

    public boolean hasReached(double d) {
        if (getCurrentMS() - this.lastMS >= d) {
            return true;
        }
        return false;
    }

    public boolean hasreached(long j) {
        if (getCurrentMS() - this.lastMS >= j) {
            return true;
        }
        return false;
    }

    public void reset() {
        this.lastMS = getCurrentMS();
    }

    public boolean delay(float f) {
        if (getTime() - this.lastMS >= f) {
            return true;
        }
        return false;
    }

    public static long getTime() {
        return System.nanoTime() / 1000000;
    }

    public boolean sleep(long j) {
        if (getTime() >= j) {
            reset();
            return true;
        }
        return false;
    }

    public boolean sleep(double d) {
        if (getTime() >= d) {
            reset();
            return true;
        }
        return false;
    }
}
