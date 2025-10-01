package net.ccbluex.liquidbounce.utils;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/RollingArrayLongBuffer.class */
public class RollingArrayLongBuffer {
    private final long[] contents;
    private int currentIndex = 0;

    public RollingArrayLongBuffer(int i) {
        this.contents = new long[i];
    }

    public long[] getContents() {
        return this.contents;
    }

    public void add(long j) {
        this.currentIndex = (this.currentIndex + 1) % this.contents.length;
        this.contents[this.currentIndex] = j;
    }

    public int getTimestampsSince(long j) {
        int i = 0;
        while (i < this.contents.length) {
            if (this.contents[this.currentIndex < i ? (this.contents.length - i) + this.currentIndex : this.currentIndex - i] >= j) {
                i++;
            } else {
                return i;
            }
        }
        return this.contents.length;
    }
}
