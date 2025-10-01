package jdk.nashorn.internal.runtime.regexp;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.PatternSyntaxException;
import jdk.nashorn.internal.parser.Scanner;
import jdk.nashorn.internal.runtime.BitVector;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.tools.Shell;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/RegExpScanner.class */
final class RegExpScanner extends Scanner {

    /* renamed from: sb */
    private final StringBuilder f62sb;
    private final Map expected;
    private final List caps;
    private final LinkedList forwardReferences;
    private int negLookaheadLevel;
    private int negLookaheadGroup;
    private boolean inCharClass;
    private boolean inNegativeClass;
    private static final String NON_IDENT_ESCAPES = "$^*+(){}[]|\\.?-";
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !RegExpScanner.class.desiredAssertionStatus();
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/RegExpScanner$Capture.class */
    private static class Capture {
        private final int negLookaheadLevel;
        private final int negLookaheadGroup;

        Capture(int i, int i2) {
            this.negLookaheadGroup = i;
            this.negLookaheadLevel = i2;
        }

        boolean canBeReferencedFrom(int i, int i2) {
            return this.negLookaheadLevel == 0 || (i == this.negLookaheadGroup && i2 >= this.negLookaheadLevel);
        }
    }

    private RegExpScanner(String str) {
        super(str);
        this.expected = new HashMap();
        this.caps = new LinkedList();
        this.forwardReferences = new LinkedList();
        this.inCharClass = false;
        this.inNegativeClass = false;
        this.f62sb = new StringBuilder(this.limit);
        reset(0);
        this.expected.put(']', 0);
        this.expected.put('}', 0);
    }

    private void processForwardReferences() {
        Iterator itDescendingIterator = this.forwardReferences.descendingIterator();
        while (itDescendingIterator.hasNext()) {
            int iIntValue = ((Integer) itDescendingIterator.next()).intValue();
            int iIntValue2 = ((Integer) itDescendingIterator.next()).intValue();
            if (iIntValue2 > this.caps.size()) {
                StringBuilder sb = new StringBuilder();
                octalOrLiteral(Integer.toString(iIntValue2), sb);
                this.f62sb.insert(iIntValue, (CharSequence) sb);
            }
        }
        this.forwardReferences.clear();
    }

    public static RegExpScanner scan(String str) {
        RegExpScanner regExpScanner = new RegExpScanner(str);
        try {
            regExpScanner.disjunction();
            regExpScanner.processForwardReferences();
            if (regExpScanner.position != str.length()) {
                String string = regExpScanner.getStringBuilder().toString();
                throw new PatternSyntaxException(str, string, string.length() + 1);
            }
            return regExpScanner;
        } catch (Exception e) {
            throw new PatternSyntaxException(e.getMessage(), str, regExpScanner.position);
        }
    }

    final StringBuilder getStringBuilder() {
        return this.f62sb;
    }

    String getJavaPattern() {
        return this.f62sb.toString();
    }

    BitVector getGroupsInNegativeLookahead() {
        BitVector bitVector = null;
        for (int i = 0; i < this.caps.size(); i++) {
            if (((Capture) this.caps.get(i)).negLookaheadLevel > 0) {
                if (bitVector == null) {
                    bitVector = new BitVector(this.caps.size() + 1);
                }
                bitVector.set(i + 1);
            }
        }
        return bitVector;
    }

    private boolean commit(int i) {
        switch (i) {
            case 1:
                this.f62sb.append(this.ch0);
                skip(1);
                return true;
            case 2:
                this.f62sb.append(this.ch0);
                this.f62sb.append(this.ch1);
                skip(2);
                return true;
            case 3:
                this.f62sb.append(this.ch0);
                this.f62sb.append(this.ch1);
                this.f62sb.append(this.ch2);
                skip(3);
                return true;
            default:
                if ($assertionsDisabled) {
                    return true;
                }
                throw new AssertionError("Should not reach here");
        }
    }

    private void restart(int i, int i2) {
        reset(i);
        this.f62sb.setLength(i2);
    }

    private void push(char c) {
        this.expected.put(Character.valueOf(c), Integer.valueOf(((Integer) this.expected.get(Character.valueOf(c))).intValue() + 1));
    }

    private void pop(char c) {
        this.expected.put(Character.valueOf(c), Integer.valueOf(Math.min(0, ((Integer) this.expected.get(Character.valueOf(c))).intValue() - 1)));
    }

    private void disjunction() {
        while (true) {
            alternative();
            if (this.ch0 == '|') {
                commit(1);
            } else {
                return;
            }
        }
    }

    private void alternative() {
        while (term()) {
        }
    }

    private boolean term() {
        int i = this.position;
        int length = this.f62sb.length();
        if (assertion()) {
            return true;
        }
        if (atom()) {
            quantifier();
            return true;
        }
        restart(i, length);
        return false;
    }

    private boolean assertion() {
        int i = this.position;
        int length = this.f62sb.length();
        switch (this.ch0) {
            case '$':
            case '^':
                return commit(1);
            case '(':
                if (this.ch1 == '?' && (this.ch2 == '=' || this.ch2 == '!')) {
                    boolean z = this.ch2 == '!';
                    commit(3);
                    if (z) {
                        if (this.negLookaheadLevel == 0) {
                            this.negLookaheadGroup++;
                        }
                        this.negLookaheadLevel++;
                    }
                    disjunction();
                    if (z) {
                        this.negLookaheadLevel--;
                    }
                    if (this.ch0 == ')') {
                        return commit(1);
                    }
                }
                break;
            case '\\':
                if (this.ch1 == 'b' || this.ch1 == 'B') {
                    return commit(2);
                }
                break;
        }
        restart(i, length);
        return false;
    }

    private boolean quantifier() {
        if (quantifierPrefix()) {
            if (this.ch0 == '?') {
                commit(1);
                return true;
            }
            return true;
        }
        return false;
    }

    private boolean quantifierPrefix() {
        int i = this.position;
        int length = this.f62sb.length();
        switch (this.ch0) {
            case OPCode.BACKREF2 /* 42 */:
            case OPCode.BACKREFN /* 43 */:
            case OPCode.REPEAT_INC_NG /* 63 */:
                return commit(1);
            case '{':
                commit(1);
                if (decimalDigits()) {
                    push('}');
                    if (this.ch0 == ',') {
                        commit(1);
                        decimalDigits();
                    }
                    if (this.ch0 == '}') {
                        pop('}');
                        commit(1);
                        return true;
                    }
                    restart(i, length);
                    return false;
                }
                break;
        }
        restart(i, length);
        return false;
    }

    private boolean atom() {
        int i = this.position;
        int length = this.f62sb.length();
        if (patternCharacter()) {
            return true;
        }
        if (this.ch0 == '.') {
            return commit(1);
        }
        if (this.ch0 == '\\') {
            commit(1);
            if (atomEscape()) {
                return true;
            }
        }
        if (characterClass()) {
            return true;
        }
        if (this.ch0 == '(') {
            commit(1);
            if (this.ch0 == '?' && this.ch1 == ':') {
                commit(2);
            } else {
                this.caps.add(new Capture(this.negLookaheadGroup, this.negLookaheadLevel));
            }
            disjunction();
            if (this.ch0 == ')') {
                commit(1);
                return true;
            }
        }
        restart(i, length);
        return false;
    }

    private boolean patternCharacter() {
        if (atEOF()) {
            return false;
        }
        switch (this.ch0) {
            case '$':
            case '(':
            case OPCode.BACKREF1 /* 41 */:
            case OPCode.BACKREF2 /* 42 */:
            case OPCode.BACKREFN /* 43 */:
            case OPCode.BACKREF_MULTI_IC /* 46 */:
            case OPCode.REPEAT_INC_NG /* 63 */:
            case '[':
            case '\\':
            case '^':
            case '|':
                return false;
            case ']':
            case '}':
                if (((Integer) this.expected.get(Character.valueOf(this.ch0))).intValue() != 0) {
                    return false;
                }
                break;
            case '{':
                break;
            default:
                return commit(1);
        }
        if (!quantifierPrefix()) {
            this.f62sb.append('\\');
            return commit(1);
        }
        return false;
    }

    private boolean atomEscape() {
        return decimalEscape() || characterClassEscape() || characterEscape() || identityEscape();
    }

    private boolean characterEscape() {
        int i = this.position;
        int length = this.f62sb.length();
        if (controlEscape()) {
            return true;
        }
        if (this.ch0 == 'c') {
            commit(1);
            if (controlLetter()) {
                return true;
            }
            restart(i, length);
        }
        if (hexEscapeSequence() || unicodeEscapeSequence()) {
            return true;
        }
        restart(i, length);
        return false;
    }

    private boolean scanEscapeSequence(char c, int i) {
        int i2 = this.position;
        int length = this.f62sb.length();
        if (this.ch0 != c) {
            return false;
        }
        commit(1);
        for (int i3 = 0; i3 < i; i3++) {
            char lowerCase = Character.toLowerCase(this.ch0);
            if (lowerCase < 'a' || lowerCase > 'f') {
                char c2 = this.ch0;
                if (!(c2 >= '0' && c2 <= '9')) {
                    restart(i2, length);
                    return false;
                }
            }
            commit(1);
        }
        return true;
    }

    private boolean hexEscapeSequence() {
        return scanEscapeSequence('x', 2);
    }

    private boolean unicodeEscapeSequence() {
        return scanEscapeSequence('u', 4);
    }

    private boolean controlEscape() {
        switch (this.ch0) {
            case Shell.RUNTIME_ERROR /* 102 */:
            case 'n':
            case 'r':
            case 't':
            case 'v':
                return commit(1);
            default:
                return false;
        }
    }

    private boolean controlLetter() {
        if ((this.ch0 < 'A' || this.ch0 > 'Z') && (this.ch0 < 'a' || this.ch0 > 'z')) {
            if (!this.inCharClass) {
                return false;
            }
            char c = this.ch0;
            if (!(c >= '0' && c <= '9') && this.ch0 != '_') {
                return false;
            }
        }
        this.f62sb.setLength(this.f62sb.length() - 1);
        unicode(this.ch0 % ' ', this.f62sb);
        skip(1);
        return true;
    }

    private boolean identityEscape() {
        if (atEOF()) {
            throw new RuntimeException("\\ at end of pattern");
        }
        if (this.ch0 == 'c') {
            this.f62sb.append('\\');
        } else if (NON_IDENT_ESCAPES.indexOf(this.ch0) == -1) {
            this.f62sb.setLength(this.f62sb.length() - 1);
        }
        return commit(1);
    }

    private boolean decimalEscape() {
        int i = this.position;
        int length = this.f62sb.length();
        if (this.ch0 == '0') {
            char c = this.ch1;
            if (!(c >= '0' && c <= '7')) {
                skip(1);
                this.f62sb.append("\ufffd\ufffd");
                return true;
            }
        }
        char c2 = this.ch0;
        if (c2 >= '0' && c2 <= '9') {
            if (this.ch0 == '0') {
                if (this.inCharClass) {
                    int i2 = 0;
                    while (true) {
                        char c3 = this.ch0;
                        if (c3 >= '0' && c3 <= '7') {
                            i2 = ((i2 * 8) + this.ch0) - 48;
                            skip(1);
                        } else {
                            unicode(i2, this.f62sb);
                            return true;
                        }
                    }
                } else {
                    decimalDigits();
                    return true;
                }
            } else {
                int i3 = 0;
                while (true) {
                    char c4 = this.ch0;
                    if (!(c4 >= '0' && c4 <= '9')) {
                        break;
                    }
                    i3 = ((i3 * 10) + this.ch0) - 48;
                    skip(1);
                }
                if (this.inCharClass) {
                    this.f62sb.setLength(this.f62sb.length() - 1);
                    octalOrLiteral(Integer.toString(i3), this.f62sb);
                    return true;
                }
                if (i3 <= this.caps.size()) {
                    if (!((Capture) this.caps.get(i3 - 1)).canBeReferencedFrom(this.negLookaheadGroup, this.negLookaheadLevel)) {
                        this.f62sb.setLength(this.f62sb.length() - 1);
                        return true;
                    }
                    this.f62sb.append(i3);
                    return true;
                }
                this.f62sb.setLength(this.f62sb.length() - 1);
                this.forwardReferences.add(Integer.valueOf(i3));
                this.forwardReferences.add(Integer.valueOf(this.f62sb.length()));
                return true;
            }
        } else {
            restart(i, length);
            return false;
        }
    }

    private boolean characterClassEscape() {
        switch (this.ch0) {
            case OPCode.NULL_CHECK_END_MEMST /* 68 */:
            case OPCode.SET_OPTION /* 87 */:
            case Shell.COMMANDLINE_ERROR /* 100 */:
            case 'w':
                return commit(1);
            case OPCode.STATE_CHECK /* 83 */:
                if (RegExpFactory.usesJavaUtilRegex()) {
                    this.f62sb.setLength(this.f62sb.length() - 1);
                    this.f62sb.append(this.inNegativeClass ? "&&[" : "[^").append("\\u000a\\u000d\\u2028\\u2029\\u0009\\u0020\\u000b\\u000c\\u00a0\\u1680\\u180e\\u2000\\u2001\\u2002\\u2003\\u2004\\u2005\\u2006\\u2007\\u2008\\u2009\\u200a\\u202f\\u205f\\u3000\\ufeff").append(']');
                    skip(1);
                    return true;
                }
                return commit(1);
            case 's':
                if (RegExpFactory.usesJavaUtilRegex()) {
                    this.f62sb.setLength(this.f62sb.length() - 1);
                    if (this.inCharClass) {
                        this.f62sb.append("\\u000a\\u000d\\u2028\\u2029\\u0009\\u0020\\u000b\\u000c\\u00a0\\u1680\\u180e\\u2000\\u2001\\u2002\\u2003\\u2004\\u2005\\u2006\\u2007\\u2008\\u2009\\u200a\\u202f\\u205f\\u3000\\ufeff");
                    } else {
                        this.f62sb.append('[').append("\\u000a\\u000d\\u2028\\u2029\\u0009\\u0020\\u000b\\u000c\\u00a0\\u1680\\u180e\\u2000\\u2001\\u2002\\u2003\\u2004\\u2005\\u2006\\u2007\\u2008\\u2009\\u200a\\u202f\\u205f\\u3000\\ufeff").append(']');
                    }
                    skip(1);
                    return true;
                }
                return commit(1);
            default:
                return false;
        }
    }

    private boolean characterClass() {
        int i = this.position;
        int length = this.f62sb.length();
        if (this.ch0 == '[') {
            try {
                this.inCharClass = true;
                push(']');
                commit(1);
                if (this.ch0 == '^') {
                    this.inNegativeClass = true;
                    commit(1);
                }
                if (classRanges() && this.ch0 == ']') {
                    pop(']');
                    commit(1);
                    if (this.position == i + 2) {
                        this.f62sb.setLength(this.f62sb.length() - 1);
                        this.f62sb.append("^\\s\\S]");
                    } else if (this.position == i + 3 && this.inNegativeClass) {
                        this.f62sb.setLength(this.f62sb.length() - 2);
                        this.f62sb.append("\\s\\S]");
                    }
                    this.inCharClass = false;
                    this.inNegativeClass = false;
                    return true;
                }
            } finally {
                this.inCharClass = false;
                this.inNegativeClass = false;
            }
        }
        restart(i, length);
        return false;
    }

    private boolean classRanges() {
        nonemptyClassRanges();
        return true;
    }

    private boolean nonemptyClassRanges() {
        int i = this.position;
        int length = this.f62sb.length();
        if (classAtom()) {
            if (this.ch0 == '-') {
                commit(1);
                if (classAtom() && classRanges()) {
                    return true;
                }
            }
            nonemptyClassRangesNoDash();
            return true;
        }
        restart(i, length);
        return false;
    }

    private boolean nonemptyClassRangesNoDash() {
        int i = this.position;
        int length = this.f62sb.length();
        if (classAtomNoDash()) {
            if (this.ch0 == '-') {
                commit(1);
                if (classAtom() && classRanges()) {
                    return true;
                }
            }
            nonemptyClassRangesNoDash();
            return true;
        }
        if (classAtom()) {
            return true;
        }
        restart(i, length);
        return false;
    }

    private boolean classAtom() {
        if (this.ch0 == '-') {
            return commit(1);
        }
        return classAtomNoDash();
    }

    private boolean classAtomNoDash() {
        if (atEOF()) {
            return false;
        }
        int i = this.position;
        int length = this.f62sb.length();
        switch (this.ch0) {
            case OPCode.BACKREF_MULTI /* 45 */:
            case ']':
                return false;
            case '[':
                this.f62sb.append('\\');
                return commit(1);
            case '\\':
                commit(1);
                if (classEscape()) {
                    return true;
                }
                restart(i, length);
                return false;
            default:
                return commit(1);
        }
    }

    private boolean classEscape() {
        if (decimalEscape()) {
            return true;
        }
        if (this.ch0 != 'b') {
            return characterEscape() || characterClassEscape() || identityEscape();
        }
        this.f62sb.setLength(this.f62sb.length() - 1);
        this.f62sb.append('\b');
        skip(1);
        return true;
    }

    private boolean decimalDigits() {
        char c = this.ch0;
        if (!(c >= '0' && c <= '9')) {
            return false;
        }
        while (true) {
            char c2 = this.ch0;
            if (c2 >= '0' && c2 <= '9') {
                commit(1);
            } else {
                return true;
            }
        }
    }

    private static void unicode(int i, StringBuilder sb) {
        String hexString = Integer.toHexString(i);
        sb.append('u');
        for (int i2 = 0; i2 < 4 - hexString.length(); i2++) {
            sb.append('0');
        }
        sb.append(hexString);
    }

    private static void octalOrLiteral(String str, StringBuilder sb) {
        int length = str.length();
        int i = 0;
        int i2 = 0;
        while (i2 < length && i < 32) {
            char cCharAt = str.charAt(i2);
            if (!(cCharAt >= '0' && cCharAt <= '7')) {
                break;
            }
            i = ((i * 8) + cCharAt) - 48;
            i2++;
        }
        if (i > 0) {
            sb.append('\\');
            unicode(i, sb);
            sb.append(str.substring(i2));
            return;
        }
        sb.append(str);
    }
}
