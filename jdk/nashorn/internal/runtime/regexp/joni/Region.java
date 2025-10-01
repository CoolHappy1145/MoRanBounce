package jdk.nashorn.internal.runtime.regexp.joni;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/Region.class */
public final class Region {
    static final int REGION_NOTPOS = -1;
    public final int numRegs;
    public final int[] beg;
    public final int[] end;

    public Region(int i) {
        this.numRegs = i;
        this.beg = new int[i];
        this.end = new int[i];
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Region: \n");
        for (int i = 0; i < this.beg.length; i++) {
            sb.append(" " + i + ": (" + this.beg[i] + "-" + this.end[i] + ")");
        }
        return sb.toString();
    }

    void clear() {
        for (int i = 0; i < this.beg.length; i++) {
            this.end[i] = -1;
            this.beg[i] = -1;
        }
    }
}
