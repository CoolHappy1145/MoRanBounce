package jdk.nashorn.internal.runtime.regexp.joni;

import java.util.Arrays;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.IntHolder;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/EncodingHelper.class */
public final class EncodingHelper {
    static final int NEW_LINE = 10;
    static final int RETURN = 13;
    static final int LINE_SEPARATOR = 8232;
    static final int PARAGRAPH_SEPARATOR = 8233;
    static final int[][] codeRanges = null;

    public static int odigitVal(int i) {
        return i - 48;
    }

    public static boolean isXDigit(int i) {
        return Character.isDigit(i) || (i >= 97 && i <= 102) || (i >= 65 && i <= 70);
    }

    public static int xdigitVal(int i) {
        if (Character.isDigit(i)) {
            return i - 48;
        }
        if (i >= 97 && i <= 102) {
            return (i - 97) + 10;
        }
        return (i - 65) + 10;
    }

    public static boolean isWord(int i) {
        return ((1 << Character.getType(i)) & CharacterType.WORD_MASK) != 0;
    }

    public static boolean isNewLine(char[] cArr, int i, int i2) {
        if (i < i2) {
            char c = cArr[i];
            if (c == '\n' || c == '\r' || c == LINE_SEPARATOR || c == PARAGRAPH_SEPARATOR) {
                return true;
            }
        }
        return false;
    }

    public static int rightAdjustCharHeadWithPrev(int i, IntHolder intHolder) {
        if (intHolder != null) {
            intHolder.value = -1;
        }
        return i;
    }

    public static char[] caseFoldCodesByString(int i, char c) {
        char c2;
        char[] cArrCopyOf = EMPTYCHARS;
        char upperCase = toUpperCase(c);
        if (upperCase != toLowerCase(upperCase)) {
            int i2 = 0;
            char c3 = 0;
            do {
                if (toUpperCase(c3) == upperCase && c3 != c) {
                    cArrCopyOf = i2 == 0 ? new char[1] : Arrays.copyOf(cArrCopyOf, i2 + 1);
                    int i3 = i2;
                    i2++;
                    cArrCopyOf[i3] = c3;
                }
                c2 = c3;
                c3 = (char) (c3 + 1);
            } while (c2 < '\uffff');
        }
        return cArrCopyOf;
    }

    public static void applyAllCaseFold(int i, ApplyCaseFold applyCaseFold, Object obj) {
        int upperCase;
        int upperCase2;
        for (int i2 = 0; i2 < 65535; i2++) {
            if (Character.isLowerCase(i2) && (upperCase2 = toUpperCase(i2)) != i2) {
                ApplyCaseFold.apply(i2, upperCase2, obj);
            }
        }
        for (int i3 = 0; i3 < 65535; i3++) {
            if (Character.isLowerCase(i3) && (upperCase = toUpperCase(i3)) != i3) {
                ApplyCaseFold.apply(upperCase, i3, obj);
            }
        }
    }

    public static char toLowerCase(char c) {
        return (char) toLowerCase((int) c);
    }

    public static int toLowerCase(int i) {
        if (i < 128) {
            return (65 > i || i > 90) ? i : i + 32;
        }
        int lowerCase = Character.toLowerCase(i);
        return lowerCase < 128 ? i : lowerCase;
    }

    public static char toUpperCase(char c) {
        return (char) toUpperCase((int) c);
    }

    public static int toUpperCase(int i) {
        if (i < 128) {
            return (97 > i || i > 122) ? i : i - 32;
        }
        int upperCase = Character.toUpperCase(i);
        return upperCase < 128 ? i : upperCase;
    }

    public static int[] ctypeCodeRange(int i, IntHolder intHolder) {
        intHolder.value = 256;
        int[] iArrCopyOf = null;
        if (i < codeRanges.length) {
            iArrCopyOf = codeRanges[i];
            if (iArrCopyOf == null) {
                iArrCopyOf = new int[16];
                int i2 = 0;
                int i3 = -2;
                for (int i4 = 0; i4 <= 65535; i4++) {
                    if (isCodeCType(i4, i)) {
                        if (i3 < i4 - 1) {
                            if ((i2 * 2) + 2 >= iArrCopyOf.length) {
                                iArrCopyOf = Arrays.copyOf(iArrCopyOf, iArrCopyOf.length * 2);
                            }
                            iArrCopyOf[(i2 * 2) + 1] = i4;
                            i2++;
                        }
                        int i5 = i4;
                        i3 = i5;
                        iArrCopyOf[i2 * 2] = i5;
                    }
                }
                if ((i2 * 2) + 1 < iArrCopyOf.length) {
                    iArrCopyOf = Arrays.copyOf(iArrCopyOf, (i2 * 2) + 1);
                }
                iArrCopyOf[0] = i2;
                codeRanges[i] = iArrCopyOf;
            }
        }
        return iArrCopyOf;
    }

    public static boolean isCodeCType(int i, int i2) {
        switch (i2) {
            case 0:
                return i == 10 || i == 13 || i == LINE_SEPARATOR || i == PARAGRAPH_SEPARATOR;
            case 1:
                return ((1 << Character.getType(i)) & CharacterType.ALPHA_MASK) != 0;
            case 2:
                return i == 9 || Character.getType(i) == 12;
            case 3:
                int type = Character.getType(i);
                return ((1 << type) & CharacterType.CNTRL_MASK) != 0 || type == 0;
            case 4:
                return i >= 48 && i <= 57;
            case 5:
                switch (i) {
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                        return false;
                    default:
                        int type2 = Character.getType(i);
                        return ((1 << type2) & CharacterType.GRAPH_MASK) == 0 && type2 != 0;
                }
            case 6:
                return Character.isLowerCase(i);
            case 7:
                int type3 = Character.getType(i);
                return ((1 << type3) & CharacterType.PRINT_MASK) == 0 && type3 != 0;
            case 8:
                return ((1 << Character.getType(i)) & CharacterType.PUNCT_MASK) != 0;
            case 9:
                switch (i) {
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                    case 13:
                        return true;
                    default:
                        return ((1 << Character.getType(i)) & CharacterType.SPACE_MASK) != 0 || i == 65279;
                }
            case 10:
                return Character.isUpperCase(i);
            case 11:
                return isXDigit(i);
            case 12:
                return ((1 << Character.getType(i)) & CharacterType.WORD_MASK) != 0;
            case 13:
                return ((1 << Character.getType(i)) & CharacterType.ALNUM_MASK) != 0;
            case 14:
                return i < 128;
            default:
                throw new RuntimeException("illegal character type: " + i2);
        }
    }
}
