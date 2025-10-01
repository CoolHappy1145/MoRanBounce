package jdk.nashorn.internal.runtime.regexp.joni.ast;

import jdk.nashorn.internal.runtime.regexp.joni.BitSet;
import jdk.nashorn.internal.runtime.regexp.joni.CodeRangeBuffer;
import jdk.nashorn.internal.runtime.regexp.joni.EncodingHelper;
import jdk.nashorn.internal.runtime.regexp.joni.ScanEnvironment;
import jdk.nashorn.internal.runtime.regexp.joni.Syntax;
import jdk.nashorn.internal.runtime.regexp.joni.constants.CCSTATE;
import jdk.nashorn.internal.runtime.regexp.joni.constants.CCVALTYPE;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/ast/CClassNode.class */
public final class CClassNode extends Node {
    private static final int FLAG_NCCLASS_NOT = 1;
    private static final int FLAG_NCCLASS_SHARE = 2;
    int flags;

    /* renamed from: bs */
    public final BitSet f78bs = new BitSet();
    public CodeRangeBuffer mbuf;
    private int ctype;
    private static final short[] AsciiCtypeTable = {16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16908, 16905, 16904, 16904, 16904, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 16392, 17028, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 30896, 16800, 16800, 16800, 16800, 16800, 16800, 16800, 31906, 31906, 31906, 31906, 31906, 31906, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 29858, 16800, 16800, 16800, 16800, 20896, 16800, 30946, 30946, 30946, 30946, 30946, 30946, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 28898, 16800, 16800, 16800, 16800, 16392, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/ast/CClassNode$CCStateArg.class */
    public static final class CCStateArg {

        /* renamed from: v */
        public int f80v;

        /* renamed from: vs */
        public int f81vs;
        public boolean vsIsRaw;
        public boolean vIsRaw;
        public CCVALTYPE inType;
        public CCVALTYPE type;
        public CCSTATE state;
    }

    public void clear() {
        this.f78bs.clear();
        this.flags = 0;
        this.mbuf = null;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof CClassNode)) {
            return false;
        }
        CClassNode cClassNode = (CClassNode) obj;
        return this.ctype == cClassNode.ctype && isNot() == cClassNode.isNot();
    }

    public int hashCode() {
        return super.hashCode();
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.ast.Node
    public String toString(int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n  flags: " + flagsToString());
        sb.append("\n  bs: " + pad(this.f78bs, i + 1));
        sb.append("\n  mbuf: " + pad(this.mbuf, i + 1));
        return sb.toString();
    }

    public String flagsToString() {
        StringBuilder sb = new StringBuilder();
        if (isNot()) {
            sb.append("NOT ");
        }
        if (isShare()) {
            sb.append("SHARE ");
        }
        return sb.toString();
    }

    public boolean isEmpty() {
        return this.mbuf == null && this.f78bs.isEmpty();
    }

    public void addCodeRangeToBuf(int i, int i2) {
        this.mbuf = CodeRangeBuffer.addCodeRangeToBuff(this.mbuf, i, i2);
    }

    public void addCodeRange(ScanEnvironment scanEnvironment, int i, int i2) {
        this.mbuf = CodeRangeBuffer.addCodeRange(this.mbuf, scanEnvironment, i, i2);
    }

    public void addAllMultiByteRange() {
        this.mbuf = CodeRangeBuffer.addAllMultiByteRange(this.mbuf);
    }

    public void clearNotFlag() {
        if (isNot()) {
            this.f78bs.invert();
            this.mbuf = CodeRangeBuffer.notCodeRangeBuff(this.mbuf);
            clearNot();
        }
    }

    public void and(CClassNode cClassNode) {
        CodeRangeBuffer codeRangeBufferAndCodeRangeBuff;
        boolean zIsNot = isNot();
        BitSet bitSet = this.f78bs;
        CodeRangeBuffer codeRangeBuffer = this.mbuf;
        boolean zIsNot2 = cClassNode.isNot();
        BitSet bitSet2 = cClassNode.f78bs;
        CodeRangeBuffer codeRangeBuffer2 = cClassNode.mbuf;
        if (zIsNot) {
            BitSet bitSet3 = new BitSet();
            bitSet.invertTo(bitSet3);
            bitSet = bitSet3;
        }
        if (zIsNot2) {
            BitSet bitSet4 = new BitSet();
            bitSet2.invertTo(bitSet4);
            bitSet2 = bitSet4;
        }
        bitSet.and(bitSet2);
        if (bitSet != this.f78bs) {
            this.f78bs.copy(bitSet);
            BitSet bitSet5 = this.f78bs;
        }
        if (zIsNot) {
            this.f78bs.invert();
        }
        if (zIsNot && zIsNot2) {
            codeRangeBufferAndCodeRangeBuff = CodeRangeBuffer.orCodeRangeBuff(codeRangeBuffer, false, codeRangeBuffer2, false);
        } else {
            codeRangeBufferAndCodeRangeBuff = CodeRangeBuffer.andCodeRangeBuff(codeRangeBuffer, zIsNot, codeRangeBuffer2, zIsNot2);
            if (zIsNot) {
                codeRangeBufferAndCodeRangeBuff = CodeRangeBuffer.notCodeRangeBuff(codeRangeBufferAndCodeRangeBuff);
            }
        }
        this.mbuf = codeRangeBufferAndCodeRangeBuff;
    }

    /* renamed from: or */
    public void m22or(CClassNode cClassNode) {
        CodeRangeBuffer codeRangeBufferOrCodeRangeBuff;
        boolean zIsNot = isNot();
        BitSet bitSet = this.f78bs;
        CodeRangeBuffer codeRangeBuffer = this.mbuf;
        boolean zIsNot2 = cClassNode.isNot();
        BitSet bitSet2 = cClassNode.f78bs;
        CodeRangeBuffer codeRangeBuffer2 = cClassNode.mbuf;
        if (zIsNot) {
            BitSet bitSet3 = new BitSet();
            bitSet.invertTo(bitSet3);
            bitSet = bitSet3;
        }
        if (zIsNot2) {
            BitSet bitSet4 = new BitSet();
            bitSet2.invertTo(bitSet4);
            bitSet2 = bitSet4;
        }
        bitSet.m21or(bitSet2);
        if (bitSet != this.f78bs) {
            this.f78bs.copy(bitSet);
            BitSet bitSet5 = this.f78bs;
        }
        if (zIsNot) {
            this.f78bs.invert();
        }
        if (zIsNot && zIsNot2) {
            codeRangeBufferOrCodeRangeBuff = CodeRangeBuffer.andCodeRangeBuff(codeRangeBuffer, false, codeRangeBuffer2, false);
        } else {
            codeRangeBufferOrCodeRangeBuff = CodeRangeBuffer.orCodeRangeBuff(codeRangeBuffer, zIsNot, codeRangeBuffer2, zIsNot2);
            if (zIsNot) {
                codeRangeBufferOrCodeRangeBuff = CodeRangeBuffer.notCodeRangeBuff(codeRangeBufferOrCodeRangeBuff);
            }
        }
        this.mbuf = codeRangeBufferOrCodeRangeBuff;
    }

    public void addCTypeByRange(int i, boolean z, int i2, int[] iArr) {
        int i3 = iArr[0];
        if (!z) {
            int i4 = 0;
            while (i4 < i3) {
                for (int i5 = iArr[(i4 * 2) + 1]; i5 <= iArr[(i4 * 2) + 2]; i5++) {
                    if (i5 >= i2) {
                        if (i5 >= iArr[(i4 * 2) + 1]) {
                            addCodeRangeToBuf(i5, iArr[(i4 * 2) + 2]);
                            i4++;
                        }
                        while (i4 < i3) {
                            addCodeRangeToBuf(iArr[(2 * i4) + 1], iArr[(2 * i4) + 2]);
                            i4++;
                        }
                        return;
                    }
                    this.f78bs.set(i5);
                }
                i4++;
            }
            for (int i6 = 0; i6 < i3; i6++) {
                addCodeRangeToBuf(iArr[(2 * i6) + 1], iArr[(2 * i6) + 2]);
            }
            return;
        }
        int i7 = 0;
        for (int i8 = 0; i8 < i3; i8++) {
            for (int i9 = i7; i9 < iArr[(2 * i8) + 1]; i9++) {
                if (i9 >= i2) {
                    int i10 = i2;
                    for (int i11 = 0; i11 < i3; i11++) {
                        if (i10 < iArr[(2 * i11) + 1]) {
                            addCodeRangeToBuf(i10, iArr[(i11 * 2) + 1] - 1);
                        }
                        i10 = iArr[(i11 * 2) + 2] + 1;
                    }
                    if (i10 < Integer.MAX_VALUE) {
                        addCodeRangeToBuf(i10, Integer.MAX_VALUE);
                        return;
                    }
                    return;
                }
                this.f78bs.set(i9);
            }
            i7 = iArr[(2 * i8) + 2] + 1;
        }
        for (int i12 = i7; i12 < i2; i12++) {
            this.f78bs.set(i12);
        }
        int i13 = i2;
        for (int i14 = 0; i14 < i3; i14++) {
            if (i13 < iArr[(2 * i14) + 1]) {
                addCodeRangeToBuf(i13, iArr[(i14 * 2) + 1] - 1);
            }
            i13 = iArr[(i14 * 2) + 2] + 1;
        }
        if (i13 < Integer.MAX_VALUE) {
            addCodeRangeToBuf(i13, Integer.MAX_VALUE);
        }
    }

    public void addCType(int i, boolean z, ScanEnvironment scanEnvironment, IntHolder intHolder) {
        int i2 = i;
        switch (i2) {
            case CharacterType.f90D /* 260 */:
            case CharacterType.f89S /* 265 */:
            case CharacterType.f91W /* 268 */:
                i2 ^= 256;
                if (scanEnvironment.syntax != Syntax.JAVASCRIPT || i2 != 9) {
                    if (z) {
                        for (int i3 = 0; i3 < 256; i3++) {
                            if ((AsciiCtypeTable[i3] & (1 << i2)) == 0) {
                                this.f78bs.set(i3);
                            }
                        }
                        addAllMultiByteRange();
                        return;
                    }
                    for (int i4 = 0; i4 < 256; i4++) {
                        if ((AsciiCtypeTable[i4] & (1 << i2)) != 0) {
                            this.f78bs.set(i4);
                        }
                    }
                    return;
                }
                break;
        }
        int[] iArrCtypeCodeRange = EncodingHelper.ctypeCodeRange(i2, intHolder);
        if (iArrCtypeCodeRange != null) {
            addCTypeByRange(i2, z, intHolder.value, iArrCtypeCodeRange);
            return;
        }
        switch (i2) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 6:
            case 8:
            case 9:
            case 10:
            case 11:
            case CharacterType.ALNUM /* 13 */:
            case 14:
                if (z) {
                    for (int i5 = 0; i5 < 256; i5++) {
                        if (!EncodingHelper.isCodeCType(i5, i2)) {
                            this.f78bs.set(i5);
                        }
                    }
                    addAllMultiByteRange();
                    return;
                }
                for (int i6 = 0; i6 < 256; i6++) {
                    if (EncodingHelper.isCodeCType(i6, i2)) {
                        this.f78bs.set(i6);
                    }
                }
                return;
            case 5:
            case 7:
                if (z) {
                    for (int i7 = 0; i7 < 256; i7++) {
                        if (!EncodingHelper.isCodeCType(i7, i2)) {
                            this.f78bs.set(i7);
                        }
                    }
                    return;
                }
                for (int i8 = 0; i8 < 256; i8++) {
                    if (EncodingHelper.isCodeCType(i8, i2)) {
                        this.f78bs.set(i8);
                    }
                }
                addAllMultiByteRange();
                return;
            case 12:
                if (!z) {
                    for (int i9 = 0; i9 < 256; i9++) {
                        if (EncodingHelper.isWord(i9)) {
                            this.f78bs.set(i9);
                        }
                    }
                    addAllMultiByteRange();
                    return;
                }
                for (int i10 = 0; i10 < 256; i10++) {
                    if (!EncodingHelper.isWord(i10)) {
                        this.f78bs.set(i10);
                    }
                }
                return;
            default:
                throw new InternalException(ErrorMessages.ERR_PARSER_BUG);
        }
    }

    public void nextStateClass(CCStateArg cCStateArg, ScanEnvironment scanEnvironment) {
        if (cCStateArg.state == CCSTATE.RANGE) {
            throw new SyntaxException(ErrorMessages.ERR_CHAR_CLASS_VALUE_AT_END_OF_RANGE);
        }
        if (cCStateArg.state == CCSTATE.VALUE && cCStateArg.type != CCVALTYPE.CLASS) {
            if (cCStateArg.type == CCVALTYPE.SB) {
                this.f78bs.set(cCStateArg.f81vs);
            } else if (cCStateArg.type == CCVALTYPE.CODE_POINT) {
                addCodeRange(scanEnvironment, cCStateArg.f81vs, cCStateArg.f81vs);
            }
        }
        cCStateArg.state = CCSTATE.VALUE;
        cCStateArg.type = CCVALTYPE.CLASS;
    }

    /* renamed from: jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode$1 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/ast/CClassNode$1.class */
    static /* synthetic */ class C02741 {

        /* renamed from: $SwitchMap$jdk$nashorn$internal$runtime$regexp$joni$constants$CCSTATE */
        static final int[] f79xadfa2ff4 = new int[CCSTATE.values().length];

        static {
            try {
                f79xadfa2ff4[CCSTATE.VALUE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f79xadfa2ff4[CCSTATE.RANGE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f79xadfa2ff4[CCSTATE.COMPLETE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f79xadfa2ff4[CCSTATE.START.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public void nextStateValue(CCStateArg cCStateArg, ScanEnvironment scanEnvironment) {
        switch (C02741.f79xadfa2ff4[cCStateArg.state.ordinal()]) {
            case 1:
                if (cCStateArg.type == CCVALTYPE.SB) {
                    if (cCStateArg.f81vs > 255) {
                        throw new ValueException(ErrorMessages.ERR_INVALID_CODE_POINT_VALUE);
                    }
                    this.f78bs.set(cCStateArg.f81vs);
                    break;
                } else if (cCStateArg.type == CCVALTYPE.CODE_POINT) {
                    addCodeRange(scanEnvironment, cCStateArg.f81vs, cCStateArg.f81vs);
                    break;
                }
                break;
            case 2:
                if (cCStateArg.inType == cCStateArg.type) {
                    if (cCStateArg.inType == CCVALTYPE.SB) {
                        if (cCStateArg.f81vs > 255 || cCStateArg.f80v > 255) {
                            throw new ValueException(ErrorMessages.ERR_INVALID_CODE_POINT_VALUE);
                        }
                        if (cCStateArg.f81vs > cCStateArg.f80v) {
                            if (scanEnvironment.syntax.allowEmptyRangeInCC()) {
                                cCStateArg.state = CCSTATE.COMPLETE;
                                break;
                            } else {
                                throw new ValueException(ErrorMessages.ERR_EMPTY_RANGE_IN_CHAR_CLASS);
                            }
                        } else {
                            this.f78bs.setRange(cCStateArg.f81vs, cCStateArg.f80v);
                        }
                    } else {
                        addCodeRange(scanEnvironment, cCStateArg.f81vs, cCStateArg.f80v);
                    }
                    cCStateArg.state = CCSTATE.COMPLETE;
                    break;
                } else if (cCStateArg.f81vs > cCStateArg.f80v) {
                    if (scanEnvironment.syntax.allowEmptyRangeInCC()) {
                        cCStateArg.state = CCSTATE.COMPLETE;
                        break;
                    } else {
                        throw new ValueException(ErrorMessages.ERR_EMPTY_RANGE_IN_CHAR_CLASS);
                    }
                } else {
                    this.f78bs.setRange(cCStateArg.f81vs, cCStateArg.f80v < 255 ? cCStateArg.f80v : 255);
                    addCodeRange(scanEnvironment, cCStateArg.f81vs, cCStateArg.f80v);
                    cCStateArg.state = CCSTATE.COMPLETE;
                }
                break;
            case 3:
            case 4:
                cCStateArg.state = CCSTATE.VALUE;
                break;
        }
        cCStateArg.vsIsRaw = cCStateArg.vIsRaw;
        cCStateArg.f81vs = cCStateArg.f80v;
        cCStateArg.type = cCStateArg.inType;
    }

    public boolean isCodeInCCLength(int i) {
        boolean zM20at;
        if (i > 255) {
            zM20at = this.mbuf != null && this.mbuf.isInCodeRange(i);
        } else {
            zM20at = this.f78bs.m20at(i);
        }
        if (isNot()) {
            return !zM20at;
        }
        return zM20at;
    }

    public boolean isCodeInCC(int i) {
        return isCodeInCCLength(i);
    }

    public void setNot() {
        this.flags |= 1;
    }

    public void clearNot() {
        this.flags &= -2;
    }

    public boolean isNot() {
        return (this.flags & 1) != 0;
    }

    public void setShare() {
        this.flags |= 2;
    }

    public void clearShare() {
        this.flags &= -3;
    }

    public boolean isShare() {
        return (this.flags & 2) != 0;
    }
}
