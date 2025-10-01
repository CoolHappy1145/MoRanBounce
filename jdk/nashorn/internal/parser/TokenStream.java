package jdk.nashorn.internal.parser;

/* loaded from: L-out.jar:jdk/nashorn/internal/parser/TokenStream.class */
public class TokenStream {
    private static final int INITIAL_SIZE = 256;
    private long[] buffer = new long[256];
    private int count = 0;

    /* renamed from: in */
    private int f38in = 0;
    private int out = 0;
    private int base = 0;

    private int next(int i) {
        int i2 = i + 1;
        if (i2 >= this.buffer.length) {
            i2 = 0;
        }
        return i2;
    }

    private int index(int i) {
        int length = i - (this.base - this.out);
        if (length >= this.buffer.length) {
            length -= this.buffer.length;
        }
        return length;
    }

    public boolean isEmpty() {
        return this.count == 0;
    }

    public boolean isFull() {
        return this.count == this.buffer.length;
    }

    public int count() {
        return this.count;
    }

    public int first() {
        return this.base;
    }

    public int last() {
        return (this.base + this.count) - 1;
    }

    public void removeLast() {
        if (this.count != 0) {
            this.count--;
            this.f38in--;
            if (this.f38in < 0) {
                this.f38in = this.buffer.length - 1;
            }
        }
    }

    public void put(long j) {
        if (this.count == this.buffer.length) {
            grow();
        }
        this.buffer[this.f38in] = j;
        this.count++;
        this.f38in = next(this.f38in);
    }

    public long get(int i) {
        return this.buffer[index(i)];
    }

    public void commit(int i) {
        this.out = index(i);
        this.count -= i - this.base;
        this.base = i;
    }

    public void grow() {
        long[] jArr = new long[this.buffer.length * 2];
        if (this.f38in > this.out) {
            System.arraycopy(this.buffer, this.out, jArr, 0, this.count);
        } else {
            int length = this.buffer.length - this.out;
            System.arraycopy(this.buffer, this.out, jArr, 0, length);
            System.arraycopy(this.buffer, 0, jArr, length, this.count - length);
        }
        this.out = 0;
        this.f38in = this.count;
        this.buffer = jArr;
    }

    void reset() {
        this.base = 0;
        this.count = 0;
        this.out = 0;
        this.f38in = 0;
    }
}
