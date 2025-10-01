package jdk.nashorn.internal;

/* loaded from: L-out.jar:jdk/nashorn/internal/IntDeque.class */
public class IntDeque {
    private int[] deque = new int[16];
    private int nextFree = 0;

    public void push(int i) {
        if (this.nextFree == this.deque.length) {
            int[] iArr = new int[this.nextFree * 2];
            System.arraycopy(this.deque, 0, iArr, 0, this.nextFree);
            this.deque = iArr;
        }
        int[] iArr2 = this.deque;
        int i2 = this.nextFree;
        this.nextFree = i2 + 1;
        iArr2[i2] = i;
    }

    public int pop() {
        int[] iArr = this.deque;
        int i = this.nextFree - 1;
        this.nextFree = i;
        return iArr[i];
    }

    public int peek() {
        return this.deque[this.nextFree - 1];
    }

    public int getAndIncrement() {
        int[] iArr = this.deque;
        int i = this.nextFree - 1;
        int i2 = iArr[i];
        iArr[i] = i2 + 1;
        return i2;
    }

    public int decrementAndGet() {
        int[] iArr = this.deque;
        int i = this.nextFree - 1;
        int i2 = iArr[i] - 1;
        iArr[i] = i2;
        return i2;
    }

    public boolean isEmpty() {
        return this.nextFree == 0;
    }
}
