package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/Matcher.class */
public abstract class Matcher extends IntHolder {
    protected final Regex regex;
    protected final char[] chars;
    protected final int str;
    protected final int end;
    protected int msaStart;
    protected int msaOptions;
    protected final Region msaRegion;
    protected int msaBestLen;
    protected int msaBestS;
    protected int msaBegin;
    protected int msaEnd;
    int low;
    int high;

    protected abstract int matchAt(int i, int i2, int i3);

    public Matcher(Regex regex, char[] cArr) {
        this(regex, cArr, 0, cArr.length);
    }

    public Matcher(Regex regex, char[] cArr, int i, int i2) {
        this.regex = regex;
        this.chars = cArr;
        this.str = i;
        this.end = i2;
        this.msaRegion = regex.numMem == 0 ? null : new Region(regex.numMem + 1);
    }

    public final Region getRegion() {
        return this.msaRegion;
    }

    public final int getBegin() {
        return this.msaBegin;
    }

    public final int getEnd() {
        return this.msaEnd;
    }

    protected final void msaInit(int i, int i2) {
        this.msaOptions = i;
        this.msaStart = i2;
        this.msaBestLen = -1;
    }

    public final int match(int i, int i2, int i3) {
        msaInit(i3, i);
        return matchAt(i2, i, i <= this.str ? -1 : i - 1);
    }

    private boolean forwardSearchRange(char[] cArr, int i, int i2, int i3, int i4, IntHolder intHolder) {
        int iSearch;
        int i5 = -1;
        int i6 = i3;
        if (this.regex.dMin > 0) {
            i6 += this.regex.dMin;
        }
        while (true) {
            iSearch = this.regex.searchAlgorithm.search(this.regex, cArr, i6, i2, i4);
            if (iSearch == -1 || iSearch >= i4) {
                return false;
            }
            if (iSearch - this.regex.dMin < i3) {
                i5 = iSearch;
                i6 = iSearch + 1;
            } else if (this.regex.subAnchor != 0) {
                switch (this.regex.subAnchor) {
                    case 2:
                        if (iSearch != i) {
                            if (!EncodingHelper.isNewLine(cArr, iSearch <= (i5 != -1 ? i5 : i) ? -1 : iSearch - 1, i2)) {
                                i5 = iSearch;
                                i6 = iSearch + 1;
                                break;
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                    case 32:
                        if (iSearch != i2 && !EncodingHelper.isNewLine(cArr, iSearch, i2)) {
                            i5 = iSearch;
                            i6 = iSearch + 1;
                            break;
                        } else {
                            break;
                        }
                }
            }
        }
        if (this.regex.dMax == 0) {
            this.low = iSearch;
            if (intHolder != null) {
                if (this.low > i3) {
                    intHolder.value = iSearch <= i3 ? -1 : iSearch - 1;
                } else {
                    intHolder.value = iSearch <= (i5 != -1 ? i5 : i) ? -1 : iSearch - 1;
                }
            }
        } else if (this.regex.dMax != Integer.MAX_VALUE) {
            this.low = iSearch - this.regex.dMax;
            if (this.low > i3) {
                this.low = EncodingHelper.rightAdjustCharHeadWithPrev(this.low, intHolder);
                if (intHolder != null && intHolder.value == -1) {
                    int i7 = i5 != -1 ? i5 : i3;
                    int i8 = this.low;
                    intHolder.value = i8 <= i7 ? -1 : i8 - 1;
                }
            } else if (intHolder != null) {
                int i9 = i5 != -1 ? i5 : i;
                int i10 = this.low;
                intHolder.value = i10 <= i9 ? -1 : i10 - 1;
            }
        }
        this.high = iSearch - this.regex.dMin;
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:33:0x00cf, code lost:
    
        if (r10.regex.dMax == Integer.MAX_VALUE) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00d2, code lost:
    
        r10.low = r0 - r10.regex.dMax;
        r10.high = r0 - r10.regex.dMin;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00ee, code lost:
    
        return true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:?, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private boolean backwardSearchRange(char[] cArr, int i, int i2, int i3, int i4, int i5) {
        int i6 = i4 + this.regex.dMin;
        int i7 = i3;
        while (true) {
            int iSearchBackward = this.regex.searchAlgorithm.searchBackward(this.regex, cArr, i6, i5, i2, i7, i3, i6);
            if (iSearchBackward != -1) {
                if (this.regex.subAnchor != 0) {
                    switch (this.regex.subAnchor) {
                        case 2:
                            if (iSearchBackward != i) {
                                int i8 = iSearchBackward <= i ? -1 : iSearchBackward - 1;
                                if (EncodingHelper.isNewLine(cArr, i8, i2)) {
                                    break;
                                } else {
                                    i7 = i8;
                                }
                            } else {
                                break;
                            }
                        case 32:
                            if (iSearchBackward == i2 || EncodingHelper.isNewLine(cArr, iSearchBackward, i2)) {
                                break;
                            } else {
                                i7 = iSearchBackward <= i5 ? -1 : iSearchBackward - 1;
                                if (i7 == -1) {
                                    return false;
                                }
                            }
                            break;
                    }
                }
            } else {
                return false;
            }
        }
    }

    private boolean matchCheck(int i, int i2, int i3) {
        if (matchAt(this.end, i2, i3) != -1) {
            if (!((this.regex.options & 16) != 0)) {
                return true;
            }
            return false;
        }
        return false;
    }

    public final int search(int i, int i2, int i3) {
        int i4;
        int i5;
        int i6;
        int i7 = i;
        int i8 = i2;
        if (i7 > this.end || i7 < this.str) {
            return -1;
        }
        if (this.regex.anchor != 0 && this.str < this.end) {
            if ((this.regex.anchor & 4) != 0) {
                if (i8 > i7) {
                    i8 = i7 + 1;
                } else {
                    i8 = i7;
                }
            } else if ((this.regex.anchor & 1) != 0) {
                if (i8 > i7) {
                    if (i7 != this.str) {
                        return -1;
                    }
                    i8 = this.str + 1;
                } else if (i8 <= this.str) {
                    i7 = this.str;
                    i8 = this.str;
                } else {
                    return -1;
                }
            } else if ((this.regex.anchor & 8) != 0) {
                int i9 = this.end;
                if (endBuf(i7, i8, i9, i9)) {
                    return -1;
                }
            } else if ((this.regex.anchor & 16) != 0) {
                int i10 = this.str;
                int i11 = this.end;
                int i12 = 1;
                while (i11 != -1) {
                    int i13 = i12;
                    i12--;
                    if (i13 <= 0) {
                        break;
                    }
                    if (i11 <= i10) {
                        i6 = -1;
                        break;
                    }
                    i11--;
                }
                i6 = i11;
                int i14 = i6;
                int i15 = this.end;
                if (EncodingHelper.isNewLine(this.chars, i14, this.end)) {
                    if (i14 > this.str && i7 <= i14 && endBuf(i7, i8, i14, i15)) {
                        return -1;
                    }
                } else if (endBuf(i7, i8, this.end, i15)) {
                    return -1;
                }
            } else if ((this.regex.anchor & 32768) != 0) {
                if (i8 > i7) {
                    i8 = i7 + 1;
                } else {
                    i8 = i7;
                }
            }
        } else if (this.str == this.end) {
            if (this.regex.thresholdLength == 0) {
                int i16 = this.str;
                msaInit(i3, i16);
                if (matchCheck(this.end, i16, -1)) {
                    return match(i16);
                }
                return mismatch();
            }
            return -1;
        }
        msaInit(i3, i7);
        int i17 = i7;
        if (i8 > i7) {
            if (i17 > this.str) {
                i5 = i17 <= this.str ? -1 : i17 - 1;
            } else {
                i5 = 0;
            }
            if (this.regex.searchAlgorithm != SearchAlgorithm.NONE) {
                int i18 = i8;
                if (this.regex.dMax != 0) {
                    if (this.regex.dMax == Integer.MAX_VALUE) {
                        i18 = this.end;
                    } else {
                        i18 += this.regex.dMax;
                        if (i18 > this.end) {
                            i18 = this.end;
                        }
                    }
                }
                if (this.end - i7 < this.regex.thresholdLength) {
                    return mismatch();
                }
                if (this.regex.dMax != Integer.MAX_VALUE) {
                    while (forwardSearchRange(this.chars, this.str, this.end, i17, i18, this)) {
                        if (i17 < this.low) {
                            i17 = this.low;
                            i5 = this.value;
                        }
                        while (i17 <= this.high) {
                            if (matchCheck(i8, i17, i5)) {
                                return match(i17);
                            }
                            i5 = i17;
                            i17++;
                        }
                        if (i17 >= i8) {
                        }
                    }
                    return mismatch();
                }
                if (!forwardSearchRange(this.chars, this.str, this.end, i17, i18, null)) {
                    return mismatch();
                }
                if ((this.regex.anchor & 16384) != 0) {
                    while (!matchCheck(i8, i17, i5)) {
                        i5 = i17;
                        i17++;
                        if (i17 >= i8) {
                            return mismatch();
                        }
                    }
                    return match(i17);
                }
            }
            while (!matchCheck(i8, i17, i5)) {
                i5 = i17;
                i17++;
                if (i17 >= i8) {
                    if (i17 == i8 && matchCheck(i8, i17, i5)) {
                        return match(i17);
                    }
                }
            }
            return match(i17);
        }
        if (this.regex.searchAlgorithm != SearchAlgorithm.NONE) {
            if (i8 < this.end) {
                i4 = i8;
            } else {
                i4 = this.end;
            }
            if (this.regex.dMax != Integer.MAX_VALUE && this.end - i8 >= this.regex.thresholdLength) {
                do {
                    int i19 = i17 + this.regex.dMax;
                    if (i19 > this.end) {
                        i19 = this.end;
                    }
                    if (!backwardSearchRange(this.chars, this.str, this.end, i19, i8, i4)) {
                        return mismatch();
                    }
                    if (i17 > this.high) {
                        i17 = this.high;
                    }
                    while (i17 != -1 && i17 >= this.low) {
                        int i20 = i17;
                        int i21 = i20 <= this.str ? -1 : i20 - 1;
                        if (matchCheck(i7, i17, i21)) {
                            return match(i17);
                        }
                        i17 = i21;
                    }
                } while (i17 >= i8);
                return mismatch();
            }
            if (this.end - i8 < this.regex.thresholdLength) {
                return mismatch();
            }
            int i22 = i17;
            if (this.regex.dMax != 0) {
                if (this.regex.dMax == Integer.MAX_VALUE) {
                    i22 = this.end;
                } else {
                    i22 += this.regex.dMax;
                    if (i22 > this.end) {
                        i22 = this.end;
                    }
                }
            }
            if (!backwardSearchRange(this.chars, this.str, this.end, i22, i8, i4)) {
                return mismatch();
            }
        }
        do {
            int i23 = i17;
            int i24 = i23 <= this.str ? -1 : i23 - 1;
            if (matchCheck(i7, i17, i24)) {
                return match(i17);
            }
            i17 = i24;
        } while (i17 >= i8);
        return mismatch();
    }

    private boolean endBuf(int i, int i2, int i3, int i4) {
        int i5 = i;
        int i6 = i2;
        if (i4 - this.str < this.regex.anchorDmin) {
            return true;
        }
        if (i6 > i5) {
            if (i3 - i5 > this.regex.anchorDmax) {
                i5 = i3 - this.regex.anchorDmax;
                if (i5 >= this.end) {
                    int i7 = this.str;
                    int i8 = this.end;
                    i5 = i8 <= i7 ? -1 : i8 - 1;
                }
            }
            if (i4 - (i6 - 1) < this.regex.anchorDmin) {
                i6 = (i4 - this.regex.anchorDmin) + 1;
            }
            if (i5 >= i6) {
                return true;
            }
            return false;
        }
        if (i3 - i6 > this.regex.anchorDmax) {
            i6 = i3 - this.regex.anchorDmax;
        }
        if (i4 - i5 < this.regex.anchorDmin) {
            i5 = i4 - this.regex.anchorDmin;
        }
        if (i6 > i5) {
            return true;
        }
        return false;
    }

    private int match(int i) {
        return i - this.str;
    }

    private int mismatch() {
        if (this.msaBestLen >= 0) {
            return match(this.msaBestS);
        }
        return -1;
    }
}
