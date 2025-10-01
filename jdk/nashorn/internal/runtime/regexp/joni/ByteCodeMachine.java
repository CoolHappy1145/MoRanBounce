package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder;
import jdk.nashorn.internal.runtime.regexp.joni.exception.ErrorMessages;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/ByteCodeMachine.class */
class ByteCodeMachine extends StackMachine {
    private int bestLen;

    /* renamed from: s */
    private int f64s;
    private int range;
    private int sprev;
    private int sstart;
    private int sbegin;
    private final int[] code;

    /* renamed from: ip */
    private int f65ip;

    ByteCodeMachine(Regex regex, char[] cArr, int i, int i2) {
        super(regex, cArr, i, i2);
        this.f64s = 0;
        this.code = regex.code;
    }

    private boolean stringCmpIC(int i, int i2, IntHolder intHolder, int i3, int i4) {
        int i5 = i2;
        int i6 = intHolder.value;
        int i7 = i5 + i3;
        while (i5 < i7) {
            int i8 = i5;
            i5++;
            char lowerCase = EncodingHelper.toLowerCase(this.chars[i8]);
            int i9 = i6;
            i6++;
            if (lowerCase != EncodingHelper.toLowerCase(this.chars[i9])) {
                return false;
            }
        }
        intHolder.value = i6;
        return true;
    }

    private void debugMatchBegin() {
        Config.log.println("match_at: str: " + this.str + ", end: " + this.end + ", start: " + this.sstart + ", sprev: " + this.sprev);
        Config.log.println("size: " + (this.end - this.str) + ", start offset: " + (this.sstart - this.str));
    }

    @Override // jdk.nashorn.internal.runtime.regexp.joni.Matcher
    protected final int matchAt(int i, int i2, int i3) {
        this.range = i;
        this.sstart = i2;
        this.sprev = i3;
        this.stk = 0;
        this.f65ip = 0;
        init();
        this.bestLen = -1;
        this.f64s = i2;
        int[] iArr = this.code;
        while (true) {
            this.sbegin = this.f64s;
            int i4 = this.f65ip;
            this.f65ip = i4 + 1;
            switch (iArr[i4]) {
                case 0:
                    return finish();
                case 1:
                    if (!opEnd()) {
                        break;
                    } else {
                        return finish();
                    }
                case 2:
                    opExact1();
                    break;
                case 3:
                    opExact2();
                    break;
                case 4:
                    opExact3();
                    break;
                case 5:
                    opExact4();
                    break;
                case 6:
                    opExact5();
                    break;
                case 7:
                    opExactN();
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case CharacterType.ALNUM /* 13 */:
                case OPCode.NULL_CHECK_END_MEMST_PUSH /* 69 */:
                default:
                    throw new InternalException(ErrorMessages.ERR_UNDEFINED_BYTECODE);
                case 14:
                    opExact1IC();
                    break;
                case OPCode.EXACTN_IC /* 15 */:
                    opExactNIC();
                    break;
                case 16:
                    opCClass();
                    break;
                case OPCode.CCLASS_MB /* 17 */:
                    opCClassMB();
                    break;
                case OPCode.CCLASS_MIX /* 18 */:
                    opCClassMIX();
                    break;
                case OPCode.CCLASS_NOT /* 19 */:
                    opCClassNot();
                    break;
                case 20:
                    opCClassMBNot();
                    break;
                case OPCode.CCLASS_MIX_NOT /* 21 */:
                    opCClassMIXNot();
                    break;
                case OPCode.CCLASS_NODE /* 22 */:
                    opCClassNode();
                    break;
                case OPCode.ANYCHAR /* 23 */:
                    opAnyChar();
                    break;
                case 24:
                    opAnyCharML();
                    break;
                case OPCode.ANYCHAR_STAR /* 25 */:
                    opAnyCharStar();
                    break;
                case OPCode.ANYCHAR_ML_STAR /* 26 */:
                    opAnyCharMLStar();
                    break;
                case OPCode.ANYCHAR_STAR_PEEK_NEXT /* 27 */:
                    opAnyCharStarPeekNext();
                    break;
                case OPCode.ANYCHAR_ML_STAR_PEEK_NEXT /* 28 */:
                    opAnyCharMLStarPeekNext();
                    break;
                case OPCode.WORD /* 29 */:
                    opWord();
                    break;
                case 30:
                    opNotWord();
                    break;
                case 31:
                    opWordBound();
                    break;
                case 32:
                    opNotWordBound();
                    break;
                case OPCode.WORD_BEGIN /* 33 */:
                    opWordBegin();
                    break;
                case 34:
                    opWordEnd();
                    break;
                case OPCode.BEGIN_BUF /* 35 */:
                    opBeginBuf();
                    break;
                case 36:
                    opEndBuf();
                    break;
                case OPCode.BEGIN_LINE /* 37 */:
                    opBeginLine();
                    break;
                case 38:
                    opEndLine();
                    break;
                case OPCode.SEMI_END_BUF /* 39 */:
                    opSemiEndBuf();
                    break;
                case 40:
                    opBeginPosition();
                    break;
                case OPCode.BACKREF1 /* 41 */:
                    opBackRef1();
                    break;
                case OPCode.BACKREF2 /* 42 */:
                    opBackRef2();
                    break;
                case OPCode.BACKREFN /* 43 */:
                    opBackRefN();
                    break;
                case OPCode.BACKREFN_IC /* 44 */:
                    opBackRefNIC();
                    break;
                case OPCode.BACKREF_MULTI /* 45 */:
                    opBackRefMulti();
                    break;
                case OPCode.BACKREF_MULTI_IC /* 46 */:
                    opBackRefMultiIC();
                    break;
                case OPCode.BACKREF_WITH_LEVEL /* 47 */:
                    opBackRefAtLevel();
                    break;
                case 48:
                    opMemoryStart();
                    break;
                case OPCode.MEMORY_START_PUSH /* 49 */:
                    opMemoryStartPush();
                    break;
                case OPCode.MEMORY_END_PUSH /* 50 */:
                    opMemoryEndPush();
                    break;
                case OPCode.MEMORY_END_PUSH_REC /* 51 */:
                    opMemoryEndPushRec();
                    break;
                case OPCode.MEMORY_END /* 52 */:
                    opMemoryEnd();
                    break;
                case OPCode.MEMORY_END_REC /* 53 */:
                    opMemoryEndRec();
                    break;
                case OPCode.FAIL /* 54 */:
                    opFail();
                    break;
                case OPCode.JUMP /* 55 */:
                    opJump();
                    break;
                case 56:
                    opPush();
                    break;
                case OPCode.POP /* 57 */:
                    opPop();
                    break;
                case OPCode.PUSH_OR_JUMP_EXACT1 /* 58 */:
                    opPushOrJumpExact1();
                    break;
                case OPCode.PUSH_IF_PEEK_NEXT /* 59 */:
                    opPushIfPeekNext();
                    break;
                case 60:
                    opRepeat();
                    break;
                case OPCode.REPEAT_NG /* 61 */:
                    opRepeatNG();
                    break;
                case 62:
                    opRepeatInc();
                    break;
                case OPCode.REPEAT_INC_NG /* 63 */:
                    opRepeatIncNG();
                    break;
                case 64:
                    opRepeatIncSG();
                    break;
                case OPCode.REPEAT_INC_NG_SG /* 65 */:
                    opRepeatIncNGSG();
                    break;
                case OPCode.NULL_CHECK_START /* 66 */:
                    opNullCheckStart();
                    break;
                case OPCode.NULL_CHECK_END /* 67 */:
                    opNullCheckEnd();
                    break;
                case OPCode.NULL_CHECK_END_MEMST /* 68 */:
                    opNullCheckEndMemST();
                    break;
                case OPCode.PUSH_POS /* 70 */:
                    opPushPos();
                    break;
                case OPCode.POP_POS /* 71 */:
                    opPopPos();
                    break;
                case 72:
                    opPushPosNot();
                    break;
                case OPCode.FAIL_POS /* 73 */:
                    opFailPos();
                    break;
                case OPCode.PUSH_STOP_BT /* 74 */:
                    opPushStopBT();
                    break;
                case OPCode.POP_STOP_BT /* 75 */:
                    opPopStopBT();
                    break;
                case OPCode.LOOK_BEHIND /* 76 */:
                    opLookBehind();
                    break;
                case OPCode.PUSH_LOOK_BEHIND_NOT /* 77 */:
                    opPushLookBehindNot();
                    break;
                case OPCode.FAIL_LOOK_BEHIND_NOT /* 78 */:
                    opFailLookBehindNot();
                    break;
            }
        }
    }

    private boolean opEnd() {
        int i = this.f64s - this.sstart;
        if (i > this.bestLen) {
            if ((this.regex.options & 16) != 0) {
                if (i > this.msaBestLen) {
                    this.msaBestLen = i;
                    this.msaBestS = this.sstart;
                } else {
                    return endBestLength();
                }
            }
            this.bestLen = i;
            Region region = this.msaRegion;
            if (region != null) {
                int[] iArr = region.beg;
                int i2 = this.sstart - this.str;
                this.msaBegin = i2;
                iArr[0] = i2;
                int[] iArr2 = region.end;
                int i3 = this.f64s - this.str;
                this.msaEnd = i3;
                iArr2[0] = i3;
                for (int i4 = 1; i4 <= this.regex.numMem; i4++) {
                    if (this.repeatStk[this.memEndStk + i4] != -1) {
                        int[] iArr3 = region.beg;
                        int i5 = i4;
                        int i6 = this.regex.btMemStart;
                        int i7 = i4;
                        iArr3[i5] = (i7 < 32 ? i6 & (1 << i7) : i6 & 1) != 0 ? this.stack[this.repeatStk[this.memStartStk + i4]].getMemPStr() - this.str : this.repeatStk[this.memStartStk + i4] - this.str;
                        int[] iArr4 = region.end;
                        int i8 = i4;
                        int i9 = this.regex.btMemEnd;
                        int i10 = i4;
                        iArr4[i8] = (i10 < 32 ? i9 & (1 << i10) : i9 & 1) != 0 ? this.stack[this.repeatStk[this.memEndStk + i4]].getMemPStr() : this.repeatStk[this.memEndStk + i4] - this.str;
                    } else {
                        region.end[i4] = -1;
                        region.beg[i4] = -1;
                    }
                }
            } else {
                this.msaBegin = this.sstart - this.str;
                this.msaEnd = this.f64s - this.str;
            }
        } else {
            Region region2 = this.msaRegion;
            if (region2 != null) {
                region2.clear();
            } else {
                this.msaEnd = 0;
                this.msaBegin = 0;
            }
        }
        return endBestLength();
    }

    private boolean endBestLength() {
        if ((this.regex.options & 48) != 0) {
            if (!((this.regex.options & 32) != 0) || this.f64s != this.sstart) {
                if (((this.regex.options & 16) != 0) && this.f64s < this.range) {
                    opFail();
                    return false;
                }
                return true;
            }
            this.bestLen = -1;
            opFail();
            return false;
        }
        return true;
    }

    private void opExact1() {
        if (this.f64s < this.range) {
            int i = this.code[this.f65ip];
            char[] cArr = this.chars;
            int i2 = this.f64s;
            this.f64s = i2 + 1;
            if (i == cArr[i2]) {
                this.f65ip++;
                this.sprev = this.sbegin;
                return;
            }
        }
        opFail();
    }

    private void opExact2() {
        if (this.f64s + 2 > this.range) {
            opFail();
            return;
        }
        if (this.code[this.f65ip] != this.chars[this.f64s]) {
            opFail();
            return;
        }
        this.f65ip++;
        this.f64s++;
        if (this.code[this.f65ip] != this.chars[this.f64s]) {
            opFail();
            return;
        }
        this.sprev = this.f64s;
        this.f65ip++;
        this.f64s++;
    }

    private void opExact3() {
        if (this.f64s + 3 > this.range) {
            opFail();
            return;
        }
        if (this.code[this.f65ip] != this.chars[this.f64s]) {
            opFail();
            return;
        }
        this.f65ip++;
        this.f64s++;
        if (this.code[this.f65ip] != this.chars[this.f64s]) {
            opFail();
            return;
        }
        this.f65ip++;
        this.f64s++;
        if (this.code[this.f65ip] != this.chars[this.f64s]) {
            opFail();
            return;
        }
        this.sprev = this.f64s;
        this.f65ip++;
        this.f64s++;
    }

    private void opExact4() {
        if (this.f64s + 4 > this.range) {
            opFail();
            return;
        }
        if (this.code[this.f65ip] != this.chars[this.f64s]) {
            opFail();
            return;
        }
        this.f65ip++;
        this.f64s++;
        if (this.code[this.f65ip] != this.chars[this.f64s]) {
            opFail();
            return;
        }
        this.f65ip++;
        this.f64s++;
        if (this.code[this.f65ip] != this.chars[this.f64s]) {
            opFail();
            return;
        }
        this.f65ip++;
        this.f64s++;
        if (this.code[this.f65ip] != this.chars[this.f64s]) {
            opFail();
            return;
        }
        this.sprev = this.f64s;
        this.f65ip++;
        this.f64s++;
    }

    private void opExact5() {
        if (this.f64s + 5 > this.range) {
            opFail();
            return;
        }
        if (this.code[this.f65ip] != this.chars[this.f64s]) {
            opFail();
            return;
        }
        this.f65ip++;
        this.f64s++;
        if (this.code[this.f65ip] != this.chars[this.f64s]) {
            opFail();
            return;
        }
        this.f65ip++;
        this.f64s++;
        if (this.code[this.f65ip] != this.chars[this.f64s]) {
            opFail();
            return;
        }
        this.f65ip++;
        this.f64s++;
        if (this.code[this.f65ip] != this.chars[this.f64s]) {
            opFail();
            return;
        }
        this.f65ip++;
        this.f64s++;
        if (this.code[this.f65ip] != this.chars[this.f64s]) {
            opFail();
            return;
        }
        this.sprev = this.f64s;
        this.f65ip++;
        this.f64s++;
    }

    private void opExactN() {
        char c;
        char[] cArr;
        int i;
        int[] iArr = this.code;
        int i2 = this.f65ip;
        this.f65ip = i2 + 1;
        int i3 = iArr[i2];
        if (this.f64s + i3 > this.range) {
            opFail();
            return;
        }
        char[][] cArr2 = this.regex.templates;
        int[] iArr2 = this.code;
        int i4 = this.f65ip;
        this.f65ip = i4 + 1;
        char[] cArr3 = cArr2[iArr2[i4]];
        int[] iArr3 = this.code;
        int i5 = this.f65ip;
        this.f65ip = i5 + 1;
        int i6 = iArr3[i5];
        do {
            int i7 = i3;
            i3--;
            if (i7 > 0) {
                int i8 = i6;
                i6++;
                c = cArr3[i8];
                cArr = this.chars;
                i = this.f64s;
                this.f64s = i + 1;
            } else {
                this.sprev = this.f64s - 1;
                return;
            }
        } while (c == cArr[i]);
        opFail();
    }

    private void opExact1IC() {
        if (this.f64s < this.range) {
            int i = this.code[this.f65ip];
            char[] cArr = this.chars;
            int i2 = this.f64s;
            this.f64s = i2 + 1;
            if (i == EncodingHelper.toLowerCase(cArr[i2])) {
                this.f65ip++;
                this.sprev = this.sbegin;
                return;
            }
        }
        opFail();
    }

    private void opExactNIC() {
        char c;
        char[] cArr;
        int i;
        int[] iArr = this.code;
        int i2 = this.f65ip;
        this.f65ip = i2 + 1;
        int i3 = iArr[i2];
        if (this.f64s + i3 > this.range) {
            opFail();
            return;
        }
        char[][] cArr2 = this.regex.templates;
        int[] iArr2 = this.code;
        int i4 = this.f65ip;
        this.f65ip = i4 + 1;
        char[] cArr3 = cArr2[iArr2[i4]];
        int[] iArr3 = this.code;
        int i5 = this.f65ip;
        this.f65ip = i5 + 1;
        int i6 = iArr3[i5];
        do {
            int i7 = i3;
            i3--;
            if (i7 > 0) {
                int i8 = i6;
                i6++;
                c = cArr3[i8];
                cArr = this.chars;
                i = this.f64s;
                this.f64s = i + 1;
            } else {
                this.sprev = this.f64s - 1;
                return;
            }
        } while (c == EncodingHelper.toLowerCase(cArr[i]));
        opFail();
    }

    private boolean isInBitSet() {
        char c = this.chars[this.f64s];
        return c <= '\u00ff' && (this.code[this.f65ip + (c >>> BitSet.ROOM_SHIFT)] & (1 << c)) != 0;
    }

    private void opCClass() {
        if (this.f64s >= this.range || !isInBitSet()) {
            opFail();
            return;
        }
        this.f65ip += 8;
        this.f64s++;
        this.sprev = this.sbegin;
    }

    private boolean isInClassMB() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        int i2 = iArr[i];
        if (this.f64s >= this.range) {
            return false;
        }
        int i3 = this.f64s;
        this.f64s++;
        char c = this.chars[i3];
        int[] iArr2 = this.code;
        int i4 = this.f65ip;
        int i5 = 0;
        int i6 = iArr2[i4];
        int i7 = i6;
        while (i5 < i7) {
            int i8 = (i5 + i7) >> 1;
            if (c > iArr2[(i8 << 1) + 2 + i4]) {
                i5 = i8 + 1;
            } else {
                i7 = i8;
            }
        }
        if (!(i5 < i6 && c >= iArr2[((i5 << 1) + 1) + i4])) {
            return false;
        }
        this.f65ip += i2;
        return true;
    }

    private void opCClassMB() {
        if (this.f64s >= this.range || this.chars[this.f64s] <= '\u00ff') {
            opFail();
        } else if (isInClassMB()) {
            this.sprev = this.sbegin;
        } else {
            opFail();
        }
    }

    private void opCClassMIX() {
        if (this.f64s >= this.range) {
            opFail();
            return;
        }
        if (this.chars[this.f64s] > '\u00ff') {
            this.f65ip += 8;
            if (!isInClassMB()) {
                opFail();
                return;
            }
        } else {
            if (!isInBitSet()) {
                opFail();
                return;
            }
            this.f65ip += 8;
            int[] iArr = this.code;
            int i = this.f65ip;
            this.f65ip = i + 1;
            this.f65ip += iArr[i];
            this.f64s++;
        }
        this.sprev = this.sbegin;
    }

    private void opCClassNot() {
        if (this.f64s >= this.range || isInBitSet()) {
            opFail();
            return;
        }
        this.f65ip += 8;
        this.f64s++;
        this.sprev = this.sbegin;
    }

    private boolean isNotInClassMB() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        int i2 = iArr[i];
        if (this.f64s + 1 > this.range) {
            if (this.f64s >= this.range) {
                return false;
            }
            this.f64s = this.end;
            this.f65ip += i2;
            return true;
        }
        int i3 = this.f64s;
        this.f64s++;
        char c = this.chars[i3];
        int[] iArr2 = this.code;
        int i4 = this.f65ip;
        int i5 = 0;
        int i6 = iArr2[i4];
        int i7 = i6;
        while (i5 < i7) {
            int i8 = (i5 + i7) >> 1;
            if (c > iArr2[(i8 << 1) + 2 + i4]) {
                i5 = i8 + 1;
            } else {
                i7 = i8;
            }
        }
        if (i5 < i6 && c >= iArr2[((i5 << 1) + 1) + i4]) {
            return false;
        }
        this.f65ip += i2;
        return true;
    }

    private void opCClassMBNot() {
        if (this.f64s >= this.range) {
            opFail();
            return;
        }
        if (this.chars[this.f64s] <= '\u00ff') {
            this.f64s++;
            int[] iArr = this.code;
            int i = this.f65ip;
            this.f65ip = i + 1;
            this.f65ip += iArr[i];
            this.sprev = this.sbegin;
            return;
        }
        if (isNotInClassMB()) {
            this.sprev = this.sbegin;
        } else {
            opFail();
        }
    }

    private void opCClassMIXNot() {
        if (this.f64s >= this.range) {
            opFail();
            return;
        }
        if (this.chars[this.f64s] > '\u00ff') {
            this.f65ip += 8;
            if (!isNotInClassMB()) {
                opFail();
                return;
            }
        } else {
            if (isInBitSet()) {
                opFail();
                return;
            }
            this.f65ip += 8;
            int[] iArr = this.code;
            int i = this.f65ip;
            this.f65ip = i + 1;
            this.f65ip += iArr[i];
            this.f64s++;
        }
        this.sprev = this.sbegin;
    }

    private void opCClassNode() {
        if (this.f64s >= this.range) {
            opFail();
            return;
        }
        Object[] objArr = this.regex.operands;
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        CClassNode cClassNode = (CClassNode) objArr[iArr[i]];
        int i2 = this.f64s;
        this.f64s++;
        if (cClassNode.isCodeInCCLength(this.chars[i2])) {
            this.sprev = this.sbegin;
        } else {
            opFail();
        }
    }

    private void opAnyChar() {
        if (this.f64s >= this.range) {
            opFail();
            return;
        }
        char c = this.chars[this.f64s];
        if (c == '\n' || c == '\r' || c == '\u2028' || c == '\u2029') {
            opFail();
        } else {
            this.f64s++;
            this.sprev = this.sbegin;
        }
    }

    private void opAnyCharML() {
        if (this.f64s >= this.range) {
            opFail();
        } else {
            this.f64s++;
            this.sprev = this.sbegin;
        }
    }

    private void opAnyCharStar() {
        char[] cArr = this.chars;
        while (this.f64s < this.range) {
            pushAlt(this.f65ip, this.f64s, this.sprev);
            if (EncodingHelper.isNewLine(cArr, this.f64s, this.end)) {
                opFail();
                return;
            } else {
                this.sprev = this.f64s;
                this.f64s++;
            }
        }
        this.sprev = this.sbegin;
    }

    private void opAnyCharMLStar() {
        while (this.f64s < this.range) {
            pushAlt(this.f65ip, this.f64s, this.sprev);
            this.sprev = this.f64s;
            this.f64s++;
        }
        this.sprev = this.sbegin;
    }

    private void opAnyCharStarPeekNext() {
        char c = (char) this.code[this.f65ip];
        char[] cArr = this.chars;
        while (this.f64s < this.range) {
            char c2 = cArr[this.f64s];
            if (c == c2) {
                pushAlt(this.f65ip + 1, this.f64s, this.sprev);
            }
            if (c2 == '\n' || c2 == '\r' || c2 == '\u2028' || c2 == '\u2029') {
                opFail();
                return;
            } else {
                this.sprev = this.f64s;
                this.f64s++;
            }
        }
        this.f65ip++;
        this.sprev = this.sbegin;
    }

    private void opAnyCharMLStarPeekNext() {
        char c = (char) this.code[this.f65ip];
        char[] cArr = this.chars;
        while (this.f64s < this.range) {
            if (c == cArr[this.f64s]) {
                pushAlt(this.f65ip + 1, this.f64s, this.sprev);
            }
            this.sprev = this.f64s;
            this.f64s++;
        }
        this.f65ip++;
        this.sprev = this.sbegin;
    }

    private void opWord() {
        if (this.f64s >= this.range || !EncodingHelper.isWord(this.chars[this.f64s])) {
            opFail();
        } else {
            this.f64s++;
            this.sprev = this.sbegin;
        }
    }

    private void opNotWord() {
        if (this.f64s >= this.range || EncodingHelper.isWord(this.chars[this.f64s])) {
            opFail();
        } else {
            this.f64s++;
            this.sprev = this.sbegin;
        }
    }

    private void opWordBound() {
        if (this.f64s == this.str) {
            if (this.f64s >= this.range || !EncodingHelper.isWord(this.chars[this.f64s])) {
                opFail();
                return;
            }
            return;
        }
        if (this.f64s == this.end) {
            if (this.sprev >= this.end || !EncodingHelper.isWord(this.chars[this.sprev])) {
                opFail();
                return;
            }
            return;
        }
        if (EncodingHelper.isWord(this.chars[this.f64s]) == EncodingHelper.isWord(this.chars[this.sprev])) {
            opFail();
        }
    }

    private void opNotWordBound() {
        if (this.f64s == this.str) {
            if (this.f64s >= this.range || !EncodingHelper.isWord(this.chars[this.f64s])) {
                return;
            }
            opFail();
            return;
        }
        if (this.f64s == this.end) {
            if (this.sprev >= this.end || !EncodingHelper.isWord(this.chars[this.sprev])) {
                return;
            }
            opFail();
            return;
        }
        if (EncodingHelper.isWord(this.chars[this.f64s]) != EncodingHelper.isWord(this.chars[this.sprev])) {
            opFail();
        }
    }

    private void opWordBegin() {
        if (this.f64s < this.range && EncodingHelper.isWord(this.chars[this.f64s]) && (this.f64s == this.str || !EncodingHelper.isWord(this.chars[this.sprev]))) {
            return;
        }
        opFail();
    }

    private void opWordEnd() {
        if (this.f64s != this.str && EncodingHelper.isWord(this.chars[this.sprev]) && (this.f64s == this.end || !EncodingHelper.isWord(this.chars[this.f64s]))) {
            return;
        }
        opFail();
    }

    private void opBeginBuf() {
        if (this.f64s != this.str) {
            opFail();
        }
    }

    private void opEndBuf() {
        if (this.f64s != this.end) {
            opFail();
        }
    }

    private void opBeginLine() {
        if (this.f64s == this.str) {
            if ((this.msaOptions & 512) != 0) {
                opFail();
            }
        } else {
            if (EncodingHelper.isNewLine(this.chars, this.sprev, this.end) && this.f64s != this.end) {
                return;
            }
            opFail();
        }
    }

    private void opEndLine() {
        if (this.f64s == this.end) {
            if (this.str == this.end || !EncodingHelper.isNewLine(this.chars, this.sprev, this.end)) {
                if ((this.msaOptions & 1024) != 0) {
                    opFail();
                    return;
                }
                return;
            }
            return;
        }
        if (EncodingHelper.isNewLine(this.chars, this.f64s, this.end)) {
            return;
        }
        opFail();
    }

    private void opSemiEndBuf() {
        if (this.f64s == this.end) {
            if (this.str == this.end || !EncodingHelper.isNewLine(this.chars, this.sprev, this.end)) {
                if ((this.msaOptions & 1024) != 0) {
                    opFail();
                    return;
                }
                return;
            }
            return;
        }
        if (EncodingHelper.isNewLine(this.chars, this.f64s, this.end) && this.f64s + 1 == this.end) {
            return;
        }
        opFail();
    }

    private void opBeginPosition() {
        if (this.f64s != this.msaStart) {
            opFail();
        }
    }

    private void opMemoryStartPush() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        pushMemStart(iArr[i], this.f64s);
    }

    private void opMemoryStart() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        this.repeatStk[this.memStartStk + iArr[i]] = this.f64s;
    }

    private void opMemoryEndPush() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        pushMemEnd(iArr[i], this.f64s);
    }

    private void opMemoryEnd() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        this.repeatStk[this.memEndStk + iArr[i]] = this.f64s;
    }

    private void opMemoryEndPushRec() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        int i2 = iArr[i];
        int memStart = getMemStart(i2);
        pushMemEnd(i2, this.f64s);
        this.repeatStk[this.memStartStk + i2] = memStart;
    }

    private void opMemoryEndRec() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        int i2 = iArr[i];
        this.repeatStk[this.memEndStk + i2] = this.f64s;
        int memStart = getMemStart(i2);
        int i3 = this.regex.btMemStart;
        if ((i2 < 32 ? i3 & (1 << i2) : i3 & 1) != 0) {
            this.repeatStk[this.memStartStk + i2] = memStart;
        } else {
            this.repeatStk[this.memStartStk + i2] = this.stack[memStart].getMemPStr();
        }
        pushMemEndMark(i2);
    }

    private boolean backrefInvalid(int i) {
        return this.repeatStk[this.memEndStk + i] == -1 || this.repeatStk[this.memStartStk + i] == -1;
    }

    private int backrefStart(int i) {
        int i2 = this.regex.btMemStart;
        return (i < 32 ? i2 & (1 << i) : i2 & 1) != 0 ? this.stack[this.repeatStk[this.memStartStk + i]].getMemPStr() : this.repeatStk[this.memStartStk + i];
    }

    private int backrefEnd(int i) {
        int i2 = this.regex.btMemEnd;
        return (i < 32 ? i2 & (1 << i) : i2 & 1) != 0 ? this.stack[this.repeatStk[this.memEndStk + i]].getMemPStr() : this.repeatStk[this.memEndStk + i];
    }

    private void backref(int i) {
        char c;
        char[] cArr;
        int i2;
        if (i > this.regex.numMem || backrefInvalid(i)) {
            opFail();
            return;
        }
        int iBackrefStart = backrefStart(i);
        int iBackrefEnd = backrefEnd(i) - iBackrefStart;
        if (this.f64s + iBackrefEnd > this.range) {
            opFail();
            return;
        }
        this.sprev = this.f64s;
        do {
            int i3 = iBackrefEnd;
            iBackrefEnd--;
            if (i3 > 0) {
                int i4 = iBackrefStart;
                iBackrefStart++;
                c = this.chars[i4];
                cArr = this.chars;
                i2 = this.f64s;
                this.f64s = i2 + 1;
            } else {
                if (this.sprev < this.range) {
                    while (this.sprev + 1 < this.f64s) {
                        this.sprev++;
                    }
                    return;
                }
                return;
            }
        } while (c == cArr[i2]);
        opFail();
    }

    private void opBackRef1() {
        backref(1);
    }

    private void opBackRef2() {
        backref(2);
    }

    private void opBackRefN() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        backref(iArr[i]);
    }

    private void opBackRefNIC() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        int i2 = iArr[i];
        if (i2 > this.regex.numMem || backrefInvalid(i2)) {
            opFail();
            return;
        }
        int iBackrefStart = backrefStart(i2);
        int iBackrefEnd = backrefEnd(i2) - iBackrefStart;
        if (this.f64s + iBackrefEnd > this.range) {
            opFail();
            return;
        }
        this.sprev = this.f64s;
        this.value = this.f64s;
        if (!stringCmpIC(this.regex.caseFoldFlag, iBackrefStart, this, iBackrefEnd, this.end)) {
            opFail();
            return;
        }
        this.f64s = this.value;
        while (this.sprev + 1 < this.f64s) {
            this.sprev++;
        }
    }

    private void opBackRefMulti() {
        int i;
        int i2;
        int[] iArr = this.code;
        int i3 = this.f65ip;
        this.f65ip = i3 + 1;
        int i4 = iArr[i3];
        int i5 = 0;
        while (true) {
            if (i5 >= i4) {
                break;
            }
            int[] iArr2 = this.code;
            int i6 = this.f65ip;
            this.f65ip = i6 + 1;
            int i7 = iArr2[i6];
            if (!backrefInvalid(i7)) {
                int iBackrefStart = backrefStart(i7);
                int iBackrefEnd = backrefEnd(i7) - iBackrefStart;
                if (this.f64s + iBackrefEnd > this.range) {
                    opFail();
                    return;
                }
                this.sprev = this.f64s;
                int i8 = this.f64s;
                do {
                    int i9 = iBackrefEnd;
                    iBackrefEnd--;
                    if (i9 > 0) {
                        i = iBackrefStart;
                        iBackrefStart++;
                        i2 = i8;
                        i8++;
                    } else {
                        this.f64s = i8;
                        if (this.sprev < this.range) {
                            while (this.sprev + 1 < this.f64s) {
                                this.sprev++;
                            }
                        }
                        this.f65ip += (i4 - i5) - 1;
                    }
                } while (this.chars[i] == this.chars[i2]);
            }
            i5++;
        }
        if (i5 == i4) {
            opFail();
        }
    }

    private void opBackRefMultiIC() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        int i2 = iArr[i];
        int i3 = 0;
        while (true) {
            if (i3 >= i2) {
                break;
            }
            int[] iArr2 = this.code;
            int i4 = this.f65ip;
            this.f65ip = i4 + 1;
            int i5 = iArr2[i4];
            if (!backrefInvalid(i5)) {
                int iBackrefStart = backrefStart(i5);
                int iBackrefEnd = backrefEnd(i5) - iBackrefStart;
                if (this.f64s + iBackrefEnd > this.range) {
                    opFail();
                    return;
                }
                this.sprev = this.f64s;
                this.value = this.f64s;
                if (stringCmpIC(this.regex.caseFoldFlag, iBackrefStart, this, iBackrefEnd, this.end)) {
                    this.f64s = this.value;
                    while (this.sprev + 1 < this.f64s) {
                        this.sprev++;
                    }
                    this.f65ip += (i2 - i3) - 1;
                }
            }
            i3++;
        }
        if (i3 == i2) {
            opFail();
        }
    }

    private boolean memIsInMemp(int i, int i2, int i3) {
        int i4 = i3;
        for (int i5 = 0; i5 < i2; i5++) {
            int i6 = i4;
            i4++;
            if (i == this.code[i6]) {
                return true;
            }
        }
        return false;
    }

    private boolean backrefMatchAtNestedLevel(boolean z, int i, int i2, int i3, int i4) {
        int memPStr = -1;
        int i5 = 0;
        for (int i6 = this.stk - 1; i6 >= 0; i6--) {
            StackEntry stackEntry = this.stack[i6];
            if (stackEntry.type == 2048) {
                i5--;
            } else if (stackEntry.type == 2304) {
                i5++;
            } else if (i5 != i2) {
                continue;
            } else if (stackEntry.type == 256) {
                if (memIsInMemp(stackEntry.getMemNum(), i3, i4)) {
                    int memPStr2 = stackEntry.getMemPStr();
                    if (memPStr != -1) {
                        if (memPStr - memPStr2 > this.end - this.f64s) {
                            return false;
                        }
                        int i7 = memPStr2;
                        this.value = this.f64s;
                        if (z) {
                            if (!stringCmpIC(i, memPStr2, this, memPStr - memPStr2, this.end)) {
                                return false;
                            }
                        } else {
                            while (i7 < memPStr) {
                                int i8 = i7;
                                i7++;
                                char c = this.chars[i8];
                                char[] cArr = this.chars;
                                int i9 = this.value;
                                this.value = i9 + 1;
                                if (c != cArr[i9]) {
                                    return false;
                                }
                            }
                        }
                        this.f64s = this.value;
                        return true;
                    }
                } else {
                    continue;
                }
            } else if (stackEntry.type == 33280 && memIsInMemp(stackEntry.getMemNum(), i3, i4)) {
                memPStr = stackEntry.getMemPStr();
            }
        }
        return false;
    }

    private void opBackRefAtLevel() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        int i2 = iArr[i];
        int[] iArr2 = this.code;
        int i3 = this.f65ip;
        this.f65ip = i3 + 1;
        int i4 = iArr2[i3];
        int[] iArr3 = this.code;
        int i5 = this.f65ip;
        this.f65ip = i5 + 1;
        int i6 = iArr3[i5];
        this.sprev = this.f64s;
        if (backrefMatchAtNestedLevel(i2 != 0, this.regex.caseFoldFlag, i4, i6, this.f65ip)) {
            while (this.sprev + 1 < this.f64s) {
                this.sprev++;
            }
            this.f65ip += i6;
            return;
        }
        opFail();
    }

    private void opNullCheckStart() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        pushNullCheckStart(iArr[i], this.f64s);
    }

    private void nullCheckFound() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        switch (iArr[i]) {
            case OPCode.JUMP /* 55 */:
            case 56:
                this.f65ip++;
                return;
            case OPCode.POP /* 57 */:
            case OPCode.PUSH_OR_JUMP_EXACT1 /* 58 */:
            case OPCode.PUSH_IF_PEEK_NEXT /* 59 */:
            case 60:
            case OPCode.REPEAT_NG /* 61 */:
            default:
                throw new InternalException(ErrorMessages.ERR_UNEXPECTED_BYTECODE);
            case 62:
            case OPCode.REPEAT_INC_NG /* 63 */:
            case 64:
            case OPCode.REPEAT_INC_NG_SG /* 65 */:
                this.f65ip++;
                return;
        }
    }

    private void opNullCheckEnd() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        if (nullCheck(iArr[i], this.f64s) != 0) {
            nullCheckFound();
        }
    }

    private void opNullCheckEndMemST() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        int iNullCheckMemSt = nullCheckMemSt(iArr[i], this.f64s);
        if (iNullCheckMemSt != 0) {
            if (iNullCheckMemSt == -1) {
                opFail();
            } else {
                nullCheckFound();
            }
        }
    }

    private void opJump() {
        this.f65ip += this.code[this.f65ip] + 1;
    }

    private void opPush() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        pushAlt(this.f65ip + iArr[i], this.f64s, this.sprev);
    }

    private void opPop() {
        popOne();
    }

    private void opPushOrJumpExact1() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        int i2 = iArr[i];
        if (this.f64s < this.range && this.code[this.f65ip] == this.chars[this.f64s]) {
            this.f65ip++;
            pushAlt(this.f65ip + i2, this.f64s, this.sprev);
        } else {
            this.f65ip += i2 + 1;
        }
    }

    private void opPushIfPeekNext() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        int i2 = iArr[i];
        if (this.f64s < this.range && this.code[this.f65ip] == this.chars[this.f64s]) {
            this.f65ip++;
            pushAlt(this.f65ip + i2, this.f64s, this.sprev);
        } else {
            this.f65ip++;
        }
    }

    private void opRepeat() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        int i2 = iArr[i];
        int[] iArr2 = this.code;
        int i3 = this.f65ip;
        this.f65ip = i3 + 1;
        int i4 = iArr2[i3];
        this.repeatStk[i2] = this.stk;
        pushRepeat(i2, this.f65ip);
        if (this.regex.repeatRangeLo[i2] == 0) {
            pushAlt(this.f65ip + i4, this.f64s, this.sprev);
        }
    }

    private void opRepeatNG() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        int i2 = iArr[i];
        int[] iArr2 = this.code;
        int i3 = this.f65ip;
        this.f65ip = i3 + 1;
        int i4 = iArr2[i3];
        this.repeatStk[i2] = this.stk;
        pushRepeat(i2, this.f65ip);
        if (this.regex.repeatRangeLo[i2] == 0) {
            pushAlt(this.f65ip, this.f64s, this.sprev);
            this.f65ip += i4;
        }
    }

    private void repeatInc(int i, int i2) {
        StackEntry stackEntry = this.stack[i2];
        stackEntry.increaseRepeatCount();
        if (stackEntry.getRepeatCount() < this.regex.repeatRangeHi[i]) {
            if (stackEntry.getRepeatCount() >= this.regex.repeatRangeLo[i]) {
                pushAlt(this.f65ip, this.f64s, this.sprev);
                this.f65ip = stackEntry.getRepeatPCode();
            } else {
                this.f65ip = stackEntry.getRepeatPCode();
            }
        }
        pushRepeatInc(i2);
    }

    private void opRepeatInc() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        int i2 = iArr[i];
        repeatInc(i2, this.repeatStk[i2]);
    }

    private void opRepeatIncSG() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        int i2 = iArr[i];
        repeatInc(i2, getRepeat(i2));
    }

    private void repeatIncNG(int i, int i2) {
        StackEntry stackEntry = this.stack[i2];
        stackEntry.increaseRepeatCount();
        if (stackEntry.getRepeatCount() < this.regex.repeatRangeHi[i]) {
            if (stackEntry.getRepeatCount() >= this.regex.repeatRangeLo[i]) {
                int repeatPCode = stackEntry.getRepeatPCode();
                pushRepeatInc(i2);
                pushAlt(repeatPCode, this.f64s, this.sprev);
                return;
            } else {
                this.f65ip = stackEntry.getRepeatPCode();
                pushRepeatInc(i2);
                return;
            }
        }
        if (stackEntry.getRepeatCount() == this.regex.repeatRangeHi[i]) {
            pushRepeatInc(i2);
        }
    }

    private void opRepeatIncNG() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        int i2 = iArr[i];
        repeatIncNG(i2, this.repeatStk[i2]);
    }

    private void opRepeatIncNGSG() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        int i2 = iArr[i];
        repeatIncNG(i2, getRepeat(i2));
    }

    private void opPushPos() {
        pushPos(this.f64s, this.sprev);
    }

    private void opPopPos() {
        StackEntry stackEntry = this.stack[posEnd()];
        this.f64s = stackEntry.getStatePStr();
        this.sprev = stackEntry.getStatePStrPrev();
    }

    private void opPushPosNot() {
        int[] iArr = this.code;
        int i = this.f65ip;
        this.f65ip = i + 1;
        pushPosNot(this.f65ip + iArr[i], this.f64s, this.sprev);
    }

    private void opFailPos() {
        popTilPosNot();
        opFail();
    }

    private void opPushStopBT() {
        pushStopBT();
    }

    private void opPopStopBT() {
        stopBtEnd();
    }

    private void opLookBehind() {
        int i;
        int[] iArr = this.code;
        int i2 = this.f65ip;
        this.f65ip = i2 + 1;
        int i3 = iArr[i2];
        int i4 = this.str;
        int i5 = this.f64s;
        int i6 = i3;
        while (i5 != -1) {
            int i7 = i6;
            i6--;
            if (i7 <= 0) {
                break;
            }
            if (i5 <= i4) {
                i = -1;
                break;
            }
            i5--;
        }
        i = i5;
        this.f64s = i;
        if (this.f64s == -1) {
            opFail();
            return;
        }
        int i8 = this.str;
        int i9 = this.f64s;
        this.sprev = i9 <= i8 ? -1 : i9 - 1;
    }

    private void opPushLookBehindNot() {
        int i;
        int[] iArr = this.code;
        int i2 = this.f65ip;
        this.f65ip = i2 + 1;
        int i3 = iArr[i2];
        int[] iArr2 = this.code;
        int i4 = this.f65ip;
        this.f65ip = i4 + 1;
        int i5 = iArr2[i4];
        int i6 = this.str;
        int i7 = this.f64s;
        int i8 = i5;
        while (i7 != -1) {
            int i9 = i8;
            i8--;
            if (i9 <= 0) {
                break;
            }
            if (i7 <= i6) {
                i = -1;
                break;
            }
            i7--;
        }
        i = i7;
        int i10 = i;
        if (i10 == -1) {
            this.f65ip += i3;
            return;
        }
        pushLookBehindNot(this.f65ip + i3, this.f64s, this.sprev);
        this.f64s = i10;
        int i11 = this.str;
        int i12 = this.f64s;
        this.sprev = i12 <= i11 ? -1 : i12 - 1;
    }

    private void opFailLookBehindNot() {
        popTilLookBehindNot();
        opFail();
    }

    private void opFail() {
        if (this.stack == null) {
            this.f65ip = this.regex.codeLength - 1;
            return;
        }
        StackEntry stackEntryPop = pop();
        this.f65ip = stackEntryPop.getStatePCode();
        this.f64s = stackEntryPop.getStatePStr();
        this.sprev = stackEntryPop.getStatePStrPrev();
    }

    private int finish() {
        return this.bestLen;
    }
}
