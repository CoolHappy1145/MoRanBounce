package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/ScannerSupport.class */
abstract class ScannerSupport extends IntHolder implements ErrorMessages {
    protected final char[] chars;

    /* renamed from: p */
    protected int f69p;
    protected int stop;
    private int lastFetched;

    /* renamed from: c */
    protected int f70c;
    private final int begin;
    private final int end;

    /* renamed from: _p */
    protected int f71_p;
    private static final int INT_SIGN_BIT = Integer.MIN_VALUE;

    protected ScannerSupport(char[] cArr, int i, int i2) {
        this.chars = cArr;
        this.begin = i;
        this.end = i2;
        reset();
    }

    protected int getBegin() {
        return this.begin;
    }

    protected int getEnd() {
        return this.end;
    }

    protected final int scanUnsignedNumber() {
        int i;
        int i2 = this.f70c;
        int i3 = 0;
        do {
            if (left()) {
                fetch();
                if (Character.isDigit(this.f70c)) {
                    i = i3;
                    i3 = (i3 * 10) + (this.f70c - 48);
                } else {
                    unfetch();
                }
            }
            this.f70c = i2;
            return i3;
        } while (((i ^ i3) & Integer.MIN_VALUE) == 0);
        return -1;
    }

    protected final int scanUnsignedHexadecimalNumber(int i) {
        int i2;
        int i3 = this.f70c;
        int iXdigitVal = 0;
        int i4 = i;
        do {
            if (left()) {
                int i5 = i4;
                i4--;
                if (i5 != 0) {
                    fetch();
                    if (EncodingHelper.isXDigit(this.f70c)) {
                        i2 = iXdigitVal;
                        iXdigitVal = (iXdigitVal << 4) + EncodingHelper.xdigitVal(this.f70c);
                    } else {
                        unfetch();
                    }
                }
            }
            this.f70c = i3;
            return iXdigitVal;
        } while (((i2 ^ iXdigitVal) & Integer.MIN_VALUE) == 0);
        return -1;
    }

    protected final int scanUnsignedOctalNumber(int i) {
        int i2 = this.f70c;
        int iOdigitVal = 0;
        int i3 = i;
        while (left()) {
            int i4 = i3;
            i3--;
            if (i4 == 0) {
                break;
            }
            fetch();
            if (Character.isDigit(this.f70c) && this.f70c < 56) {
                int i5 = iOdigitVal;
                iOdigitVal = (iOdigitVal << 3) + EncodingHelper.odigitVal(this.f70c);
                if (((i5 ^ iOdigitVal) & Integer.MIN_VALUE) != 0) {
                    return -1;
                }
            } else {
                unfetch();
                break;
            }
        }
        this.f70c = i2;
        return iOdigitVal;
    }

    protected final void reset() {
        this.f69p = this.begin;
        this.stop = this.end;
    }

    protected final void mark() {
        this.f71_p = this.f69p;
    }

    protected final void restore() {
        this.f69p = this.f71_p;
    }

    protected final void inc() {
        this.lastFetched = this.f69p;
        this.f69p++;
    }

    protected final void fetch() {
        this.lastFetched = this.f69p;
        char[] cArr = this.chars;
        int i = this.f69p;
        this.f69p = i + 1;
        this.f70c = cArr[i];
    }

    protected int fetchTo() {
        this.lastFetched = this.f69p;
        char[] cArr = this.chars;
        int i = this.f69p;
        this.f69p = i + 1;
        return cArr[i];
    }

    protected final void unfetch() {
        this.f69p = this.lastFetched;
    }

    protected final int peek() {
        if (this.f69p < this.stop) {
            return this.chars[this.f69p];
        }
        return 0;
    }

    protected final boolean peekIs(int i) {
        return peek() == i;
    }

    protected final boolean left() {
        return this.f69p < this.stop;
    }
}
