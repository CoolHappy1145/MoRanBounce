package org.json;

import java.util.HashMap;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

/* loaded from: L-out.jar:org/json/XMLTokener.class */
public class XMLTokener extends JSONTokener {
    public static final HashMap entity = new HashMap(8);

    static {
        entity.put("amp", XML.AMP);
        entity.put("apos", XML.APOS);
        entity.put("gt", XML.f210GT);
        entity.put("lt", XML.f211LT);
        entity.put("quot", XML.QUOT);
    }

    public XMLTokener(String str) {
        super(str);
    }

    public String nextCDATA() {
        StringBuilder sb = new StringBuilder();
        while (more()) {
            sb.append(next());
            int length = sb.length() - 3;
            if (length >= 0 && sb.charAt(length) == ']' && sb.charAt(length + 1) == ']' && sb.charAt(length + 2) == '>') {
                sb.setLength(length);
                return sb.toString();
            }
        }
        throw syntaxError("Unclosed CDATA");
    }

    public Object nextContent() {
        char next;
        do {
            next = next();
        } while (Character.isWhitespace(next));
        if (next == 0) {
            return null;
        }
        if (next == '<') {
            return XML.f211LT;
        }
        StringBuilder sb = new StringBuilder();
        while (next != 0) {
            if (next == '<') {
                back();
                return sb.toString().trim();
            }
            if (next == '&') {
                sb.append(nextEntity(next));
            } else {
                sb.append(next);
            }
            next = next();
        }
        return sb.toString().trim();
    }

    public Object nextEntity(char c) {
        char next;
        StringBuilder sb = new StringBuilder();
        while (true) {
            next = next();
            if (!Character.isLetterOrDigit(next) && next != '#') {
                break;
            }
            sb.append(Character.toLowerCase(next));
        }
        if (next != ';') {
            throw syntaxError("Missing ';' in XML entity: &" + ((Object) sb));
        }
        return unescapeEntity(sb.toString());
    }

    static String unescapeEntity(String str) throws NumberFormatException {
        int i;
        if (str == null || str.isEmpty()) {
            return "";
        }
        if (str.charAt(0) == '#') {
            if (str.charAt(1) == 'x') {
                i = Integer.parseInt(str.substring(2), 16);
            } else {
                i = Integer.parseInt(str.substring(1));
            }
            return new String(new int[]{i}, 0, 1);
        }
        Character ch = (Character) entity.get(str);
        if (ch == null) {
            return '&' + str + ';';
        }
        return ch.toString();
    }

    public Object nextMeta() {
        char next;
        char next2;
        do {
            next = next();
        } while (Character.isWhitespace(next));
        switch (next) {
            case 0:
                throw syntaxError("Misshaped meta tag");
            case OPCode.WORD_BEGIN /* 33 */:
                return XML.BANG;
            case '\"':
            case OPCode.SEMI_END_BUF /* 39 */:
                do {
                    next2 = next();
                    if (next2 == 0) {
                        throw syntaxError("Unterminated string");
                    }
                } while (next2 != next);
                return Boolean.TRUE;
            case OPCode.BACKREF_WITH_LEVEL /* 47 */:
                return XML.SLASH;
            case '<':
                return XML.f211LT;
            case OPCode.REPEAT_NG /* 61 */:
                return XML.f209EQ;
            case '>':
                return XML.f210GT;
            case OPCode.REPEAT_INC_NG /* 63 */:
                return XML.QUEST;
        }
        while (true) {
            char next3 = next();
            if (Character.isWhitespace(next3)) {
                return Boolean.TRUE;
            }
            switch (next3) {
                case 0:
                case OPCode.WORD_BEGIN /* 33 */:
                case '\"':
                case OPCode.SEMI_END_BUF /* 39 */:
                case OPCode.BACKREF_WITH_LEVEL /* 47 */:
                case '<':
                case OPCode.REPEAT_NG /* 61 */:
                case '>':
                case OPCode.REPEAT_INC_NG /* 63 */:
                    back();
                    return Boolean.TRUE;
            }
        }
    }

    public Object nextToken() {
        char next;
        do {
            next = next();
        } while (Character.isWhitespace(next));
        switch (next) {
            case 0:
                throw syntaxError("Misshaped element");
            case OPCode.WORD_BEGIN /* 33 */:
                return XML.BANG;
            case '\"':
            case OPCode.SEMI_END_BUF /* 39 */:
                StringBuilder sb = new StringBuilder();
                while (true) {
                    char next2 = next();
                    if (next2 == 0) {
                        throw syntaxError("Unterminated string");
                    }
                    if (next2 == next) {
                        return sb.toString();
                    }
                    if (next2 == '&') {
                        sb.append(nextEntity(next2));
                    } else {
                        sb.append(next2);
                    }
                }
            case OPCode.BACKREF_WITH_LEVEL /* 47 */:
                return XML.SLASH;
            case '<':
                throw syntaxError("Misplaced '<'");
            case OPCode.REPEAT_NG /* 61 */:
                return XML.f209EQ;
            case '>':
                return XML.f210GT;
            case OPCode.REPEAT_INC_NG /* 63 */:
                return XML.QUEST;
            default:
                StringBuilder sb2 = new StringBuilder();
                while (true) {
                    sb2.append(next);
                    next = next();
                    if (Character.isWhitespace(next)) {
                        return sb2.toString();
                    }
                    switch (next) {
                        case 0:
                            return sb2.toString();
                        case OPCode.WORD_BEGIN /* 33 */:
                        case OPCode.BACKREF_WITH_LEVEL /* 47 */:
                        case OPCode.REPEAT_NG /* 61 */:
                        case '>':
                        case OPCode.REPEAT_INC_NG /* 63 */:
                        case '[':
                        case ']':
                            back();
                            return sb2.toString();
                        case '\"':
                        case OPCode.SEMI_END_BUF /* 39 */:
                        case '<':
                            throw syntaxError("Bad character in a name");
                    }
                }
        }
    }

    public boolean skipPast(String str) {
        int i = 0;
        int length = str.length();
        char[] cArr = new char[length];
        for (int i2 = 0; i2 < length; i2++) {
            char next = next();
            if (next == 0) {
                return false;
            }
            cArr[i2] = next;
        }
        while (true) {
            int i3 = i;
            boolean z = true;
            int i4 = 0;
            while (true) {
                if (i4 >= length) {
                    break;
                }
                if (cArr[i3] != str.charAt(i4)) {
                    z = false;
                    break;
                }
                i3++;
                if (i3 >= length) {
                    i3 -= length;
                }
                i4++;
            }
            if (z) {
                return true;
            }
            char next2 = next();
            if (next2 == 0) {
                return false;
            }
            cArr[i] = next2;
            i++;
            if (i >= length) {
                i -= length;
            }
        }
    }
}
