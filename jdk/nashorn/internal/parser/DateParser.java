package jdk.nashorn.internal.parser;

import java.util.HashMap;
import java.util.Locale;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

/* loaded from: L-out.jar:jdk/nashorn/internal/parser/DateParser.class */
public class DateParser {
    public static final int YEAR = 0;
    public static final int MONTH = 1;
    public static final int DAY = 2;
    public static final int HOUR = 3;
    public static final int MINUTE = 4;
    public static final int SECOND = 5;
    public static final int MILLISECOND = 6;
    public static final int TIMEZONE = 7;
    private final String string;
    private final int length;
    private Token token;
    private int tokenLength;
    private Name nameValue;
    private int numValue;
    private static final HashMap names = new HashMap();
    private int pos = 0;
    private int currentField = 0;
    private int yearSign = 0;
    private boolean namedMonth = false;
    private final Integer[] fields = new Integer[8];

    /* loaded from: L-out.jar:jdk/nashorn/internal/parser/DateParser$Token.class */
    private enum Token {
        UNKNOWN,
        NUMBER,
        SEPARATOR,
        PARENTHESIS,
        NAME,
        SIGN,
        END
    }

    static {
        addName("monday", -1, 0);
        addName("tuesday", -1, 0);
        addName("wednesday", -1, 0);
        addName("thursday", -1, 0);
        addName("friday", -1, 0);
        addName("saturday", -1, 0);
        addName("sunday", -1, 0);
        addName("january", 0, 1);
        addName("february", 0, 2);
        addName("march", 0, 3);
        addName("april", 0, 4);
        addName("may", 0, 5);
        addName("june", 0, 6);
        addName("july", 0, 7);
        addName("august", 0, 8);
        addName("september", 0, 9);
        addName("october", 0, 10);
        addName("november", 0, 11);
        addName("december", 0, 12);
        addName("am", 1, 0);
        addName("pm", 1, 12);
        addName("z", 2, 0);
        addName("gmt", 2, 0);
        addName("ut", 2, 0);
        addName("utc", 2, 0);
        addName("est", 2, -300);
        addName("edt", 2, -240);
        addName("cst", 2, -360);
        addName("cdt", 2, -300);
        addName("mst", 2, -420);
        addName("mdt", 2, -360);
        addName("pst", 2, -480);
        addName("pdt", 2, -420);
        addName("t", 3, 0);
    }

    public DateParser(String str) {
        this.string = str;
        this.length = str.length();
    }

    public boolean parse() {
        return parseEcmaDate() || parseLegacyDate();
    }

    public boolean parseEcmaDate() {
        if (this.token == null) {
            this.token = next();
        }
        while (this.token != Token.END) {
            switch (C02001.$SwitchMap$jdk$nashorn$internal$parser$DateParser$Token[this.token.ordinal()]) {
                case 1:
                    if (this.currentField == 0 && this.yearSign != 0) {
                        if (this.tokenLength != 6) {
                            return false;
                        }
                        this.numValue *= this.yearSign;
                    } else if (!checkEcmaField(this.currentField, this.numValue)) {
                        return false;
                    }
                    if (!skipEcmaDelimiter()) {
                        return false;
                    }
                    if (this.currentField >= 7) {
                        break;
                    } else {
                        int i = this.currentField;
                        this.currentField = i + 1;
                        set(i, this.numValue);
                        break;
                    }
                    break;
                case 2:
                    if (this.nameValue == null) {
                        return false;
                    }
                    switch (this.nameValue.type) {
                        case 2:
                            if (!this.nameValue.key.equals("z") || !setTimezone(this.nameValue.value, false)) {
                                return false;
                            }
                            break;
                        case 3:
                            if (this.currentField == 0 || this.currentField > 3) {
                                return false;
                            }
                            this.currentField = 3;
                            break;
                            break;
                        default:
                            return false;
                    }
                case 3:
                    if (peek() == -1) {
                        return false;
                    }
                    if (this.currentField == 0) {
                        this.yearSign = this.numValue;
                        break;
                    } else if (this.currentField >= 5 && setTimezone(readTimeZoneOffset(), true)) {
                        break;
                    } else {
                        return false;
                    }
                default:
                    return false;
            }
            this.token = next();
        }
        return patchResult(true);
    }

    /* renamed from: jdk.nashorn.internal.parser.DateParser$1 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/parser/DateParser$1.class */
    static /* synthetic */ class C02001 {
        static final int[] $SwitchMap$jdk$nashorn$internal$parser$DateParser$Token = new int[Token.values().length];

        static {
            try {
                $SwitchMap$jdk$nashorn$internal$parser$DateParser$Token[Token.NUMBER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$DateParser$Token[Token.NAME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$DateParser$Token[Token.SIGN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$DateParser$Token[Token.PARENTHESIS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$DateParser$Token[Token.SEPARATOR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:61:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x015a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean parseLegacyDate() {
        if (this.yearSign != 0 || this.currentField > 2) {
            return false;
        }
        if (this.token == null) {
            this.token = next();
        }
        while (this.token != Token.END) {
            switch (C02001.$SwitchMap$jdk$nashorn$internal$parser$DateParser$Token[this.token.ordinal()]) {
                case 1:
                    if (skipDelimiter(':')) {
                        if (!setTimeField(this.numValue)) {
                            return false;
                        }
                        do {
                            this.token = next();
                            if (this.token != Token.NUMBER || !setTimeField(this.numValue)) {
                                return false;
                            }
                        } while (skipDelimiter(isSet(5) ? '.' : ':'));
                    } else {
                        if (!setDateField(this.numValue)) {
                            return false;
                        }
                        skipDelimiter('-');
                        break;
                    }
                    break;
                case 2:
                    if (this.nameValue == null) {
                        return false;
                    }
                    switch (this.nameValue.type) {
                        case 0:
                            if (!setMonth(this.nameValue.value)) {
                                break;
                            }
                            if (this.nameValue.type != 2) {
                                break;
                            } else {
                                skipDelimiter('-');
                                break;
                            }
                        case 1:
                            if (!setAmPm(this.nameValue.value)) {
                                break;
                            }
                            if (this.nameValue.type != 2) {
                            }
                            break;
                        case 2:
                            if (!setTimezone(this.nameValue.value, false)) {
                                break;
                            }
                            if (this.nameValue.type != 2) {
                            }
                            break;
                        case 3:
                            break;
                        default:
                            if (this.nameValue.type != 2) {
                            }
                            break;
                    }
                    return false;
                case 3:
                    if (peek() == -1 || !setTimezone(readTimeZoneOffset(), true)) {
                        return false;
                    }
                    break;
                case 4:
                    if (!skipParentheses()) {
                        return false;
                    }
                    break;
                case 5:
                    break;
                default:
                    return false;
            }
            this.token = next();
        }
        return patchResult(false);
    }

    public Integer[] getDateFields() {
        return this.fields;
    }

    private boolean isSet(int i) {
        return this.fields[i] != null;
    }

    private Integer get(int i) {
        return this.fields[i];
    }

    private void set(int i, int i2) {
        this.fields[i] = Integer.valueOf(i2);
    }

    private int peek() {
        if (this.pos < this.length) {
            return this.string.charAt(this.pos);
        }
        return -1;
    }

    private boolean skipNumberDelimiter(char c) {
        if (this.pos < this.length - 1 && this.string.charAt(this.pos) == c && Character.getType(this.string.charAt(this.pos + 1)) == 9) {
            this.token = null;
            this.pos++;
            return true;
        }
        return false;
    }

    private boolean skipDelimiter(char c) {
        if (this.pos < this.length && this.string.charAt(this.pos) == c) {
            this.token = null;
            this.pos++;
            return true;
        }
        return false;
    }

    private Token next() {
        if (this.pos >= this.length) {
            this.tokenLength = 0;
            return Token.END;
        }
        char cCharAt = this.string.charAt(this.pos);
        if (cCharAt > '\u0080') {
            this.tokenLength = 1;
            this.pos++;
            return Token.UNKNOWN;
        }
        switch (Character.getType(cCharAt)) {
            case 1:
            case 2:
                this.nameValue = readName();
                return Token.NAME;
            case 9:
                this.numValue = readNumber(6);
                return Token.NUMBER;
            case 12:
            case 24:
                this.tokenLength = 1;
                this.pos++;
                return Token.SEPARATOR;
            default:
                this.tokenLength = 1;
                this.pos++;
                switch (cCharAt) {
                    case '(':
                        return Token.PARENTHESIS;
                    case OPCode.BACKREFN /* 43 */:
                    case OPCode.BACKREF_MULTI /* 45 */:
                        this.numValue = cCharAt == '-' ? -1 : 1;
                        return Token.SIGN;
                    default:
                        return Token.UNKNOWN;
                }
        }
    }

    private static boolean checkLegacyField(int i, int i2) {
        switch (i) {
            case 3:
                return 0 <= i2 && i2 <= 24;
            case 4:
            case 5:
                return 0 <= i2 && i2 < 60;
            case 6:
                return 0 <= i2 && i2 < 1000;
            default:
                return true;
        }
    }

    private boolean checkEcmaField(int i, int i2) {
        switch (i) {
            case 0:
                return this.tokenLength == 4;
            case 1:
                if (this.tokenLength == 2) {
                    if (1 <= i2 && i2 <= 12) {
                        return true;
                    }
                }
                return false;
            case 2:
                if (this.tokenLength == 2) {
                    if (1 <= i2 && i2 <= 31) {
                        return true;
                    }
                }
                return false;
            case 3:
                if (this.tokenLength == 2) {
                    if (0 <= i2 && i2 <= 24) {
                        return true;
                    }
                }
                return false;
            case 4:
            case 5:
                if (this.tokenLength == 2) {
                    if (0 <= i2 && i2 < 60) {
                        return true;
                    }
                }
                return false;
            case 6:
                if (this.tokenLength < 4) {
                    if (0 <= i2 && i2 < 1000) {
                        return true;
                    }
                }
                return false;
            default:
                return true;
        }
    }

    private boolean skipEcmaDelimiter() {
        switch (this.currentField) {
            case 0:
            case 1:
                return skipNumberDelimiter('-') || peek() == 84 || peek() == -1;
            case 2:
                return peek() == 84 || peek() == -1;
            case 3:
            case 4:
                return skipNumberDelimiter(':') || endOfTime();
            case 5:
                return skipNumberDelimiter('.') || endOfTime();
            default:
                return true;
        }
    }

    private boolean endOfTime() {
        int iPeek = peek();
        return iPeek == -1 || iPeek == 90 || iPeek == 45 || iPeek == 43 || iPeek == 32;
    }

    private int readNumber(int i) {
        int i2 = this.pos;
        int iCharAt = 0;
        int iMin = Math.min(this.length, this.pos + i);
        while (this.pos < iMin) {
            char cCharAt = this.string.charAt(this.pos);
            if (!('0' <= cCharAt && cCharAt <= '9')) {
                break;
            }
            String str = this.string;
            int i3 = this.pos;
            this.pos = i3 + 1;
            iCharAt = ((iCharAt * 10) + str.charAt(i3)) - 48;
        }
        this.tokenLength = this.pos - i2;
        return iCharAt;
    }

    private Name readName() {
        int i = this.pos;
        int iMin = Math.min(this.pos + 3, this.length);
        while (this.pos < iMin) {
            char cCharAt = this.string.charAt(this.pos);
            if (!(('A' <= cCharAt && cCharAt <= 'Z') || ('a' <= cCharAt && cCharAt <= 'z'))) {
                break;
            }
            this.pos++;
        }
        Name name = (Name) names.get(this.string.substring(i, this.pos).toLowerCase(Locale.ENGLISH));
        while (this.pos < this.length) {
            char cCharAt2 = this.string.charAt(this.pos);
            if (!(('A' <= cCharAt2 && cCharAt2 <= 'Z') || ('a' <= cCharAt2 && cCharAt2 <= 'z'))) {
                break;
            }
            this.pos++;
        }
        this.tokenLength = this.pos - i;
        if (name != null && name.matches(this.string, i, this.tokenLength)) {
            return name;
        }
        return null;
    }

    private int readTimeZoneOffset() {
        int i = this.string.charAt(this.pos - 1) == '+' ? 1 : -1;
        int number = readNumber(2);
        skipDelimiter(':');
        return i * ((number * 60) + readNumber(2));
    }

    private boolean skipParentheses() {
        int i = 1;
        while (this.pos < this.length && i != 0) {
            String str = this.string;
            int i2 = this.pos;
            this.pos = i2 + 1;
            char cCharAt = str.charAt(i2);
            if (cCharAt == '(') {
                i++;
            } else if (cCharAt == ')') {
                i--;
            }
        }
        return true;
    }

    private boolean setMonth(int i) {
        if (!isSet(1)) {
            this.namedMonth = true;
            set(1, i);
            return true;
        }
        return false;
    }

    private boolean setDateField(int i) {
        for (int i2 = 0; i2 != 3; i2++) {
            if (!isSet(i2)) {
                set(i2, i);
                return true;
            }
        }
        return false;
    }

    private boolean setTimeField(int i) {
        for (int i2 = 3; i2 != 7; i2++) {
            if (!isSet(i2)) {
                if (checkLegacyField(i2, i)) {
                    set(i2, i);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    private boolean setTimezone(int i, boolean z) {
        if (!isSet(7) || (z && get(7).intValue() == 0)) {
            set(7, i);
            return true;
        }
        return false;
    }

    private boolean setAmPm(int i) {
        if (!isSet(3)) {
            return false;
        }
        int iIntValue = get(3).intValue();
        if (iIntValue >= 0 && iIntValue <= 12) {
            set(3, iIntValue + i);
            return true;
        }
        return true;
    }

    private boolean patchResult(boolean z) {
        int i;
        if (!isSet(0) && !isSet(3)) {
            return false;
        }
        if (isSet(3) && !isSet(4)) {
            return false;
        }
        for (int i2 = 0; i2 <= 7; i2++) {
            if (get(i2) == null && (i2 != 7 || z)) {
                switch (i2) {
                    case 1:
                    case 2:
                        i = 1;
                        break;
                    default:
                        i = 0;
                        break;
                }
                set(i2, i);
            }
        }
        if (!z) {
            int iIntValue = get(0).intValue();
            if (1 <= iIntValue && iIntValue <= 31) {
                int iIntValue2 = get(0).intValue();
                set(0, get(2).intValue());
                if (this.namedMonth) {
                    set(2, iIntValue2);
                } else {
                    int iIntValue3 = get(1).intValue();
                    set(1, iIntValue2);
                    set(2, iIntValue3);
                }
            }
            int iIntValue4 = get(1).intValue();
            if (1 <= iIntValue4 && iIntValue4 <= 12) {
                int iIntValue5 = get(2).intValue();
                if (!(1 <= iIntValue5 && iIntValue5 <= 31)) {
                    return false;
                }
                int iIntValue6 = get(0).intValue();
                if (iIntValue6 >= 0 && iIntValue6 < 100) {
                    set(0, iIntValue6 >= 50 ? 1900 + iIntValue6 : 2000 + iIntValue6);
                }
            } else {
                return false;
            }
        } else if (get(3).intValue() == 24 && (get(4).intValue() != 0 || get(5).intValue() != 0 || get(6).intValue() != 0)) {
            return false;
        }
        set(1, get(1).intValue() - 1);
        return true;
    }

    private static void addName(String str, int i, int i2) {
        Name name = new Name(str, i, i2);
        names.put(name.key, name);
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/parser/DateParser$Name.class */
    private static class Name {
        final String name;
        final String key;
        final int value;
        final int type;
        static final int DAY_OF_WEEK = -1;
        static final int MONTH_NAME = 0;
        static final int AM_PM = 1;
        static final int TIMEZONE_ID = 2;
        static final int TIME_SEPARATOR = 3;
        static final boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !DateParser.class.desiredAssertionStatus();
        }

        Name(String str, int i, int i2) {
            if (!$assertionsDisabled && str == null) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && !str.equals(str.toLowerCase(Locale.ENGLISH))) {
                throw new AssertionError();
            }
            this.name = str;
            this.key = str.substring(0, Math.min(3, str.length()));
            this.type = i;
            this.value = i2;
        }

        public boolean matches(String str, int i, int i2) {
            return this.name.regionMatches(true, 0, str, i, i2);
        }

        public String toString() {
            return this.name;
        }
    }
}
