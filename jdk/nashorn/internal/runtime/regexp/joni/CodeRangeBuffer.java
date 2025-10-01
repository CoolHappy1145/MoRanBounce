package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/CodeRangeBuffer.class */
public final class CodeRangeBuffer implements Cloneable {
    private static final int INIT_MULTI_BYTE_RANGE_SIZE = 5;
    private static final int ALL_MULTI_BYTE_RANGE = Integer.MAX_VALUE;

    /* renamed from: p */
    int[] f66p;
    int used;

    /* renamed from: clone, reason: collision with other method in class */
    public Object m469clone() {
        return clone();
    }

    public CodeRangeBuffer() {
        this.f66p = new int[5];
        writeCodePoint(0, 0);
    }

    public boolean isInCodeRange(int i) {
        int i2 = 0;
        int i3 = this.f66p[0];
        int i4 = i3;
        while (i2 < i4) {
            int i5 = (i2 + i4) >> 1;
            if (i > this.f66p[(i5 << 1) + 2]) {
                i2 = i5 + 1;
            } else {
                i4 = i5;
            }
        }
        return i2 < i3 && i >= this.f66p[(i2 << 1) + 1];
    }

    private CodeRangeBuffer(CodeRangeBuffer codeRangeBuffer) {
        this.f66p = new int[codeRangeBuffer.f66p.length];
        System.arraycopy(codeRangeBuffer.f66p, 0, this.f66p, 0, this.f66p.length);
        this.used = codeRangeBuffer.used;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CodeRange");
        sb.append("\n  used: ").append(this.used);
        sb.append("\n  code point: ").append(this.f66p[0]);
        sb.append("\n  ranges: ");
        for (int i = 0; i < this.f66p[0]; i++) {
            sb.append("[").append(rangeNumToString(this.f66p[(i * 2) + 1])).append("..").append(rangeNumToString(this.f66p[(i * 2) + 2])).append("]");
            if (i > 0 && i % 6 == 0) {
                sb.append("\n          ");
            }
        }
        return sb.toString();
    }

    private static String rangeNumToString(int i) {
        return "0x" + Integer.toString(i, 16);
    }

    public void expand(int i) {
        int length = this.f66p.length;
        do {
            length <<= 1;
        } while (length < i);
        int[] iArr = new int[length];
        System.arraycopy(this.f66p, 0, iArr, 0, this.used);
        this.f66p = iArr;
    }

    public void ensureSize(int i) {
        int i2;
        int length = this.f66p.length;
        while (true) {
            i2 = length;
            if (i2 >= i) {
                break;
            } else {
                length = i2 << 1;
            }
        }
        if (this.f66p.length != i2) {
            int[] iArr = new int[i2];
            System.arraycopy(this.f66p, 0, iArr, 0, this.used);
            this.f66p = iArr;
        }
    }

    private void moveRight(int i, int i2, int i3) {
        if (i2 + i3 > this.f66p.length) {
            expand(i2 + i3);
        }
        System.arraycopy(this.f66p, i, this.f66p, i2, i3);
        if (i2 + i3 > this.used) {
            this.used = i2 + i3;
        }
    }

    protected void moveLeft(int i, int i2, int i3) {
        System.arraycopy(this.f66p, i, this.f66p, i2, i3);
    }

    private void moveLeftAndReduce(int i, int i2) {
        System.arraycopy(this.f66p, i, this.f66p, i2, this.used - i);
        this.used -= i - i2;
    }

    public void writeCodePoint(int i, int i2) {
        int i3 = i + 1;
        if (this.f66p.length < i3) {
            expand(i3);
        }
        this.f66p[i] = i2;
        if (this.used < i3) {
            this.used = i3;
        }
    }

    public CodeRangeBuffer clone() {
        return new CodeRangeBuffer(this);
    }

    public static CodeRangeBuffer addCodeRangeToBuff(CodeRangeBuffer codeRangeBuffer, int i, int i2) {
        int i3 = i;
        int i4 = i2;
        CodeRangeBuffer codeRangeBuffer2 = codeRangeBuffer;
        if (i3 > i4) {
            i3 = i4;
            i4 = i3;
        }
        if (codeRangeBuffer2 == null) {
            codeRangeBuffer2 = new CodeRangeBuffer();
        }
        int[] iArr = codeRangeBuffer2.f66p;
        int i5 = iArr[0];
        int i6 = 0;
        int i7 = i5;
        while (i6 < i7) {
            int i8 = (i6 + i7) >>> 1;
            if (i3 > iArr[(i8 * 2) + 2]) {
                i6 = i8 + 1;
            } else {
                i7 = i8;
            }
        }
        int i9 = i6;
        int i10 = i5;
        while (i9 < i10) {
            int i11 = (i9 + i10) >>> 1;
            if (i4 >= iArr[(i11 * 2) + 1] - 1) {
                i9 = i11 + 1;
            } else {
                i10 = i11;
            }
        }
        int i12 = (i6 + 1) - i9;
        if (i5 + i12 > 10000) {
            throw new ValueException(ErrorMessages.ERR_TOO_MANY_MULTI_BYTE_RANGES);
        }
        if (i12 != 1) {
            if (i3 > iArr[(i6 * 2) + 1]) {
                i3 = iArr[(i6 * 2) + 1];
            }
            if (i4 < iArr[((i9 - 1) * 2) + 2]) {
                i4 = iArr[((i9 - 1) * 2) + 2];
            }
        }
        if (i12 != 0 && i9 < i5) {
            int i13 = 1 + (i9 * 2);
            int i14 = 1 + ((i6 + 1) * 2);
            int i15 = (i5 - i9) * 2;
            if (i12 > 0) {
                codeRangeBuffer2.moveRight(i13, i14, i15);
            } else {
                codeRangeBuffer2.moveLeftAndReduce(i13, i14);
            }
        }
        int i16 = 1 + (i6 * 2);
        codeRangeBuffer2.writeCodePoint(i16, i3);
        codeRangeBuffer2.writeCodePoint(i16 + 1, i4);
        codeRangeBuffer2.writeCodePoint(0, i5 + i12);
        return codeRangeBuffer2;
    }

    public static CodeRangeBuffer addCodeRange(CodeRangeBuffer codeRangeBuffer, ScanEnvironment scanEnvironment, int i, int i2) {
        if (i > i2) {
            if (scanEnvironment.syntax.allowEmptyRangeInCC()) {
                return codeRangeBuffer;
            }
            throw new ValueException(ErrorMessages.ERR_EMPTY_RANGE_IN_CHAR_CLASS);
        }
        return addCodeRangeToBuff(codeRangeBuffer, i, i2);
    }

    protected static CodeRangeBuffer setAllMultiByteRange(CodeRangeBuffer codeRangeBuffer) {
        return addCodeRangeToBuff(codeRangeBuffer, 128, Integer.MAX_VALUE);
    }

    public static CodeRangeBuffer addAllMultiByteRange(CodeRangeBuffer codeRangeBuffer) {
        return setAllMultiByteRange(codeRangeBuffer);
    }

    public static CodeRangeBuffer notCodeRangeBuff(CodeRangeBuffer codeRangeBuffer) {
        CodeRangeBuffer codeRangeBufferAddCodeRangeToBuff = null;
        if (codeRangeBuffer == null) {
            return setAllMultiByteRange(null);
        }
        int[] iArr = codeRangeBuffer.f66p;
        int i = iArr[0];
        if (i > 0) {
            int i2 = 128;
            int i3 = 0;
            for (int i4 = 0; i4 < i; i4++) {
                int i5 = iArr[(i4 * 2) + 1];
                i3 = iArr[(i4 * 2) + 2];
                if (i2 <= i5 - 1) {
                    codeRangeBufferAddCodeRangeToBuff = addCodeRangeToBuff(codeRangeBufferAddCodeRangeToBuff, i2, i5 - 1);
                }
                if (i3 == Integer.MAX_VALUE) {
                    break;
                }
                i2 = i3 + 1;
            }
            if (i3 < Integer.MAX_VALUE) {
                codeRangeBufferAddCodeRangeToBuff = addCodeRangeToBuff(codeRangeBufferAddCodeRangeToBuff, i3 + 1, Integer.MAX_VALUE);
            }
            return codeRangeBufferAddCodeRangeToBuff;
        }
        return setAllMultiByteRange(null);
    }

    public static CodeRangeBuffer orCodeRangeBuff(CodeRangeBuffer codeRangeBuffer, boolean z, CodeRangeBuffer codeRangeBuffer2, boolean z2) {
        CodeRangeBuffer codeRangeBufferNotCodeRangeBuff = null;
        CodeRangeBuffer codeRangeBuffer3 = codeRangeBuffer;
        CodeRangeBuffer codeRangeBuffer4 = codeRangeBuffer2;
        boolean z3 = z;
        boolean z4 = z2;
        if (codeRangeBuffer3 == null && codeRangeBuffer4 == null) {
            if (z3 || z4) {
                return setAllMultiByteRange(null);
            }
            return null;
        }
        if (codeRangeBuffer4 == null) {
            z3 = z4;
            z4 = z3;
            codeRangeBuffer3 = codeRangeBuffer4;
            codeRangeBuffer4 = codeRangeBuffer3;
        }
        if (codeRangeBuffer3 == null) {
            if (z3) {
                return setAllMultiByteRange(null);
            }
            if (!z4) {
                return codeRangeBuffer4.clone();
            }
            return notCodeRangeBuff(codeRangeBuffer4);
        }
        if (z3) {
            boolean z5 = z3;
            z3 = z4;
            z4 = z5;
            CodeRangeBuffer codeRangeBuffer5 = codeRangeBuffer3;
            codeRangeBuffer3 = codeRangeBuffer4;
            codeRangeBuffer4 = codeRangeBuffer5;
        }
        if (!z4 && !z3) {
            codeRangeBufferNotCodeRangeBuff = codeRangeBuffer4.clone();
        } else if (!z3) {
            codeRangeBufferNotCodeRangeBuff = notCodeRangeBuff(codeRangeBuffer4);
        }
        int[] iArr = codeRangeBuffer3.f66p;
        int i = iArr[0];
        for (int i2 = 0; i2 < i; i2++) {
            codeRangeBufferNotCodeRangeBuff = addCodeRangeToBuff(codeRangeBufferNotCodeRangeBuff, iArr[(i2 * 2) + 1], iArr[(i2 * 2) + 2]);
        }
        return codeRangeBufferNotCodeRangeBuff;
    }

    public static CodeRangeBuffer andCodeRange1(CodeRangeBuffer codeRangeBuffer, int i, int i2, int[] iArr, int i3) {
        CodeRangeBuffer codeRangeBufferAddCodeRangeToBuff = codeRangeBuffer;
        int i4 = i;
        int i5 = i2;
        for (int i6 = 0; i6 < i3; i6++) {
            int i7 = iArr[(i6 * 2) + 1];
            int i8 = iArr[(i6 * 2) + 2];
            if (i7 < i4) {
                if (i8 < i4) {
                    continue;
                } else {
                    i4 = i8 + 1;
                }
            } else if (i7 <= i5) {
                if (i8 < i5) {
                    if (i4 <= i7 - 1) {
                        codeRangeBufferAddCodeRangeToBuff = addCodeRangeToBuff(codeRangeBufferAddCodeRangeToBuff, i4, i7 - 1);
                    }
                    i4 = i8 + 1;
                } else {
                    i5 = i7 - 1;
                }
            } else {
                i4 = i7;
            }
            if (i4 > i5) {
                break;
            }
        }
        if (i4 <= i5) {
            codeRangeBufferAddCodeRangeToBuff = addCodeRangeToBuff(codeRangeBufferAddCodeRangeToBuff, i4, i5);
        }
        return codeRangeBufferAddCodeRangeToBuff;
    }

    public static CodeRangeBuffer andCodeRangeBuff(CodeRangeBuffer codeRangeBuffer, boolean z, CodeRangeBuffer codeRangeBuffer2, boolean z2) {
        CodeRangeBuffer codeRangeBufferAndCodeRange1 = null;
        CodeRangeBuffer codeRangeBuffer3 = codeRangeBuffer;
        CodeRangeBuffer codeRangeBuffer4 = codeRangeBuffer2;
        boolean z3 = z;
        boolean z4 = z2;
        if (codeRangeBuffer3 == null) {
            if (z3 && codeRangeBuffer4 != null) {
                return codeRangeBuffer4.clone();
            }
            return null;
        }
        if (codeRangeBuffer4 == null) {
            if (z4) {
                return codeRangeBuffer3.clone();
            }
            return null;
        }
        if (z3) {
            z3 = z4;
            z4 = z3;
            codeRangeBuffer3 = codeRangeBuffer4;
            codeRangeBuffer4 = codeRangeBuffer3;
        }
        int[] iArr = codeRangeBuffer3.f66p;
        int i = iArr[0];
        int[] iArr2 = codeRangeBuffer4.f66p;
        int i2 = iArr2[0];
        if (!z4 && !z3) {
            for (int i3 = 0; i3 < i; i3++) {
                int i4 = iArr[(i3 * 2) + 1];
                int i5 = iArr[(i3 * 2) + 2];
                for (int i6 = 0; i6 < i2; i6++) {
                    int i7 = iArr2[(i6 * 2) + 1];
                    int i8 = iArr2[(i6 * 2) + 2];
                    if (i7 > i5) {
                        break;
                    }
                    if (i8 >= i4) {
                        codeRangeBufferAndCodeRange1 = addCodeRangeToBuff(codeRangeBufferAndCodeRange1, i4 > i7 ? i4 : i7, i5 < i8 ? i5 : i8);
                    }
                }
            }
        } else if (!z3) {
            for (int i9 = 0; i9 < i; i9++) {
                codeRangeBufferAndCodeRange1 = andCodeRange1(codeRangeBufferAndCodeRange1, iArr[(i9 * 2) + 1], iArr[(i9 * 2) + 2], iArr2, i2);
            }
        }
        return codeRangeBufferAndCodeRange1;
    }
}
