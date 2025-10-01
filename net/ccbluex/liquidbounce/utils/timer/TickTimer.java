package net.ccbluex.liquidbounce.utils.timer;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/timer/TickTimer.class */
public final class TickTimer {
    private int tick;

    public void update() {
        this.tick++;
    }

    public void reset() {
        this.tick = 0;
    }

    public boolean hasTimePassed(int i) {
        return this.tick >= i;
    }
}
