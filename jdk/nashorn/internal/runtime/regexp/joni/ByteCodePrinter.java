package jdk.nashorn.internal.runtime.regexp.joni;

import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.ast.CClassNode;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/ByteCodePrinter.class */
class ByteCodePrinter {
    final int[] code;
    final int codeLength;
    final char[][] templates;
    Object[] operands;
    private static final String[] OpCodeNames = {"finish", AsmConstants.END, "exact1", "exact2", "exact3", "exact4", "exact5", "exactn", "exactmb2-n1", "exactmb2-n2", "exactmb2-n3", "exactmb2-n", "exactmb3n", "exactmbn", "exact1-ic", "exactn-ic", "cclass", "cclass-mb", "cclass-mix", "cclass-not", "cclass-mb-not", "cclass-mix-not", "cclass-node", "anychar", "anychar-ml", "anychar*", "anychar-ml*", "anychar*-peek-next", "anychar-ml*-peek-next", "word", "not-word", "word-bound", "not-word-bound", "word-begin", "word-end", "begin-buf", "end-buf", "begin-line", "end-line", "semi-end-buf", "begin-position", "backref1", "backref2", "backrefn", "backrefn-ic", "backref_multi", "backref_multi-ic", "backref_at_level", "mem-start", "mem-start-push", "mem-end-push", "mem-end-push-rec", "mem-end", "mem-end-rec", "fail", "jump", "push", "pop", "push-or-jump-e1", "push-if-peek-next", "repeat", "repeat-ng", "repeat-inc", "repeat-inc-ng", "repeat-inc-sg", "repeat-inc-ng-sg", "null-check-start", "null-check-end", "null-check-end-memst", "null-check-end-memst-push", "push-pos", "pop-pos", "push-pos-not", "fail-pos", "push-stop-bt", "pop-stop-bt", "look-behind", "push-look-behind-not", "fail-look-behind-not", "call", "return", "state-check-push", "state-check-push-or-jump", "state-check", "state-check-anychar*", "state-check-anychar-ml*", "set-option-push", "set-option"};
    private static final int[] OpCodeArgTypes = {0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 0, 0, 0, -1, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4, -1, -1, -1, -1, 4, 4, 4, 4, 4, 4, 0, 1, 1, 0, -1, -1, -1, -1, 4, 4, 4, 4, 4, 4, 4, 4, 0, 0, 1, 0, 0, 0, -1, -1, 0, 2, 0, -1, -1, 6, 6, 6, 5, 5};

    public ByteCodePrinter(Regex regex) {
        this.code = regex.code;
        this.codeLength = regex.codeLength;
        this.operands = regex.operands;
        this.templates = regex.templates;
    }

    public String byteCodeListToString() {
        return compiledByteCodeListToString();
    }

    private void pString(StringBuilder sb, int i, int i2) {
        sb.append(CallSiteDescriptor.TOKEN_DELIMITER);
        sb.append(new String(this.code, i2, i));
    }

    private void pLenString(StringBuilder sb, int i, int i2) {
        sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(i).append(CallSiteDescriptor.TOKEN_DELIMITER);
        sb.append(new String(this.code, i2, i));
    }

    private static void pLenStringFromTemplate(StringBuilder sb, int i, char[] cArr, int i2) {
        sb.append(":T:").append(i).append(CallSiteDescriptor.TOKEN_DELIMITER);
        sb.append(cArr, i2, i);
    }

    public int compiledByteCodeToString(StringBuilder sb, int i) {
        int i2;
        sb.append("[").append(OpCodeNames[this.code[i]]);
        int i3 = OpCodeArgTypes[this.code[i]];
        if (i3 == -1) {
            int i4 = i + 1;
            switch (this.code[i]) {
                case 2:
                case OPCode.ANYCHAR_STAR_PEEK_NEXT /* 27 */:
                case OPCode.ANYCHAR_ML_STAR_PEEK_NEXT /* 28 */:
                    i2 = i4 + 1;
                    pString(sb, 1, i4);
                    break;
                case 3:
                    pString(sb, 2, i4);
                    i2 = i4 + 2;
                    break;
                case 4:
                    pString(sb, 3, i4);
                    i2 = i4 + 3;
                    break;
                case 5:
                    pString(sb, 4, i4);
                    i2 = i4 + 4;
                    break;
                case 6:
                    pString(sb, 5, i4);
                    i2 = i4 + 5;
                    break;
                case 7:
                    int i5 = this.code[i4];
                    int i6 = i4 + 1;
                    int i7 = this.code[i6];
                    int i8 = i6 + 1;
                    int i9 = this.code[i8];
                    i2 = i8 + 1;
                    pLenStringFromTemplate(sb, i5, this.templates[i7], i9);
                    break;
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case CharacterType.ALNUM /* 13 */:
                case OPCode.ANYCHAR /* 23 */:
                case 24:
                case OPCode.ANYCHAR_STAR /* 25 */:
                case OPCode.ANYCHAR_ML_STAR /* 26 */:
                case OPCode.WORD /* 29 */:
                case 30:
                case 31:
                case 32:
                case OPCode.WORD_BEGIN /* 33 */:
                case 34:
                case OPCode.BEGIN_BUF /* 35 */:
                case 36:
                case OPCode.BEGIN_LINE /* 37 */:
                case 38:
                case OPCode.SEMI_END_BUF /* 39 */:
                case 40:
                case OPCode.BACKREF1 /* 41 */:
                case OPCode.BACKREF2 /* 42 */:
                case OPCode.BACKREFN /* 43 */:
                case 48:
                case OPCode.MEMORY_START_PUSH /* 49 */:
                case OPCode.MEMORY_END_PUSH /* 50 */:
                case OPCode.MEMORY_END_PUSH_REC /* 51 */:
                case OPCode.MEMORY_END /* 52 */:
                case OPCode.MEMORY_END_REC /* 53 */:
                case OPCode.FAIL /* 54 */:
                case OPCode.JUMP /* 55 */:
                case 56:
                case OPCode.POP /* 57 */:
                case 62:
                case OPCode.REPEAT_INC_NG /* 63 */:
                case 64:
                case OPCode.REPEAT_INC_NG_SG /* 65 */:
                case OPCode.NULL_CHECK_START /* 66 */:
                case OPCode.NULL_CHECK_END /* 67 */:
                case OPCode.NULL_CHECK_END_MEMST /* 68 */:
                case OPCode.NULL_CHECK_END_MEMST_PUSH /* 69 */:
                case OPCode.PUSH_POS /* 70 */:
                case OPCode.POP_POS /* 71 */:
                case 72:
                case OPCode.FAIL_POS /* 73 */:
                case OPCode.PUSH_STOP_BT /* 74 */:
                case OPCode.POP_STOP_BT /* 75 */:
                case OPCode.FAIL_LOOK_BEHIND_NOT /* 78 */:
                case OPCode.CALL /* 79 */:
                case 80:
                default:
                    throw new InternalException("undefined code: " + this.code[i4 - 1]);
                case 14:
                    pString(sb, 1, i4);
                    i2 = i4 + 1;
                    break;
                case OPCode.EXACTN_IC /* 15 */:
                    int i10 = this.code[i4];
                    int i11 = i4 + 1;
                    int i12 = this.code[i11];
                    int i13 = i11 + 1;
                    int i14 = this.code[i13];
                    i2 = i13 + 1;
                    pLenStringFromTemplate(sb, i10, this.templates[i12], i14);
                    break;
                case 16:
                    BitSet bitSet = new BitSet();
                    System.arraycopy(this.code, i4, bitSet.bits, 0, 8);
                    i2 = i4 + 8;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(bitSet.numOn());
                    break;
                case OPCode.CCLASS_MB /* 17 */:
                case 20:
                    int i15 = this.code[i4];
                    int i16 = i4 + 1;
                    int i17 = this.code[i16];
                    i2 = i16 + i15;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(i17).append(CallSiteDescriptor.TOKEN_DELIMITER).append(i15);
                    break;
                case OPCode.CCLASS_MIX /* 18 */:
                case OPCode.CCLASS_MIX_NOT /* 21 */:
                    BitSet bitSet2 = new BitSet();
                    System.arraycopy(this.code, i4, bitSet2.bits, 0, 8);
                    int iNumOn = bitSet2.numOn();
                    int i18 = i4 + 8;
                    int i19 = this.code[i18];
                    int i20 = i18 + 1;
                    int i21 = this.code[i20];
                    i2 = i20 + i19;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(iNumOn).append(CallSiteDescriptor.TOKEN_DELIMITER).append(i21).append(CallSiteDescriptor.TOKEN_DELIMITER).append(i19);
                    break;
                case OPCode.CCLASS_NOT /* 19 */:
                    BitSet bitSet3 = new BitSet();
                    System.arraycopy(this.code, i4, bitSet3.bits, 0, 8);
                    i2 = i4 + 8;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(bitSet3.numOn());
                    break;
                case OPCode.CCLASS_NODE /* 22 */:
                    CClassNode cClassNode = (CClassNode) this.operands[this.code[i4]];
                    i2 = i4 + 1;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(cClassNode).append(CallSiteDescriptor.TOKEN_DELIMITER).append(cClassNode.f78bs.numOn());
                    break;
                case OPCode.BACKREFN_IC /* 44 */:
                    int i22 = this.code[i4];
                    i2 = i4 + 1;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(i22);
                    break;
                case OPCode.BACKREF_MULTI /* 45 */:
                case OPCode.BACKREF_MULTI_IC /* 46 */:
                    sb.append(" ");
                    int i23 = this.code[i4];
                    i2 = i4 + 1;
                    for (int i24 = 0; i24 < i23; i24++) {
                        int i25 = this.code[i2];
                        i2++;
                        if (i24 > 0) {
                            sb.append(", ");
                        }
                        sb.append(i25);
                    }
                    break;
                case OPCode.BACKREF_WITH_LEVEL /* 47 */:
                    int i26 = this.code[i4];
                    int i27 = i4 + 1;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(i26);
                    int i28 = this.code[i27];
                    int i29 = i27 + 1;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(i28);
                    sb.append(" ");
                    int i30 = this.code[i29];
                    i2 = i29 + 1;
                    for (int i31 = 0; i31 < i30; i31++) {
                        int i32 = this.code[i2];
                        i2++;
                        if (i31 > 0) {
                            sb.append(", ");
                        }
                        sb.append(i32);
                    }
                    break;
                case OPCode.PUSH_OR_JUMP_EXACT1 /* 58 */:
                case OPCode.PUSH_IF_PEEK_NEXT /* 59 */:
                    int i33 = this.code[i4];
                    int i34 = i4 + 1;
                    sb.append(":(").append(i33).append(")");
                    pString(sb, 1, i34);
                    i2 = i34 + 1;
                    break;
                case 60:
                case OPCode.REPEAT_NG /* 61 */:
                    int i35 = this.code[i4];
                    int i36 = i4 + 1;
                    int i37 = this.code[i36];
                    i2 = i36 + 1;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(i35).append(CallSiteDescriptor.TOKEN_DELIMITER).append(i37);
                    break;
                case OPCode.LOOK_BEHIND /* 76 */:
                    int i38 = this.code[i4];
                    i2 = i4 + 1;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(i38);
                    break;
                case OPCode.PUSH_LOOK_BEHIND_NOT /* 77 */:
                    int i39 = this.code[i4];
                    int i40 = i4 + 1;
                    int i41 = this.code[i40];
                    i2 = i40 + 1;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(i41).append(":(").append(i39).append(")");
                    break;
                case OPCode.STATE_CHECK_PUSH /* 81 */:
                case OPCode.STATE_CHECK_PUSH_OR_JUMP /* 82 */:
                    int i42 = this.code[i4];
                    int i43 = i4 + 1;
                    int i44 = this.code[i43];
                    i2 = i43 + 1;
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(i42).append(":(").append(i44).append(")");
                    break;
            }
        } else {
            i2 = i + 1;
            switch (i3) {
                case 1:
                    sb.append(":(").append(this.code[i2]).append(")");
                    i2++;
                    break;
                case 2:
                    sb.append(":(").append(this.code[i2]).append(")");
                    i2++;
                    break;
                case 3:
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(this.code[i2]);
                    i2++;
                    break;
                case 4:
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(this.code[i2]);
                    i2++;
                    break;
                case 5:
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(this.code[i2]);
                    i2++;
                    break;
                case 6:
                    sb.append(CallSiteDescriptor.TOKEN_DELIMITER).append(this.code[i2]);
                    i2 += 2;
                    break;
            }
        }
        sb.append("]");
        return i2;
    }

    private String compiledByteCodeListToString() {
        StringBuilder sb = new StringBuilder();
        sb.append("code length: ").append(this.codeLength).append("\n");
        int i = 0;
        int iCompiledByteCodeToString = 0;
        int i2 = this.codeLength;
        while (iCompiledByteCodeToString < i2) {
            i++;
            if (iCompiledByteCodeToString > 0) {
                sb.append(i % 5 == 0 ? "\n" : " ");
            }
            iCompiledByteCodeToString = compiledByteCodeToString(sb, iCompiledByteCodeToString);
        }
        sb.append("\n");
        return sb.toString();
    }
}
