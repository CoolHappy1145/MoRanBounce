package org.json;

import java.util.Iterator;
import java.util.Map;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import org.apache.log4j.spi.Configurator;

/* loaded from: L-out.jar:org/json/XML.class */
public class XML {
    public static final Character AMP = '&';
    public static final Character APOS = '\'';
    public static final Character BANG = '!';

    /* renamed from: EQ */
    public static final Character f209EQ = '=';

    /* renamed from: GT */
    public static final Character f210GT = '>';

    /* renamed from: LT */
    public static final Character f211LT = '<';
    public static final Character QUEST = '?';
    public static final Character QUOT = '\"';
    public static final Character SLASH = '/';

    /* renamed from: org.json.XML$1 */
    /* loaded from: L-out.jar:org/json/XML$1.class */
    static class C05411 implements Iterable {
        final String val$string;

        C05411(String str) {
            this.val$string = str;
        }

        @Override // java.lang.Iterable
        public Iterator iterator() {
            return new Iterator(this) { // from class: org.json.XML.1.1
                private int nextIndex = 0;
                private int length;
                final C05411 this$0;

                {
                    this.this$0 = this;
                    this.length = this.this$0.val$string.length();
                }

                @Override // java.util.Iterator
                public Object next() {
                    return next();
                }

                @Override // java.util.Iterator
                public boolean hasNext() {
                    return this.nextIndex < this.length;
                }

                @Override // java.util.Iterator
                public Integer next() {
                    int iCodePointAt = this.this$0.val$string.codePointAt(this.nextIndex);
                    this.nextIndex += Character.charCount(iCodePointAt);
                    return Integer.valueOf(iCodePointAt);
                }

                @Override // java.util.Iterator
                public void remove() {
                    throw new UnsupportedOperationException();
                }
            };
        }
    }

    private static Iterable codePointIterator(String str) {
        return new C05411(str);
    }

    public static String escape(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        Iterator it = codePointIterator(str).iterator();
        while (it.hasNext()) {
            int iIntValue = ((Integer) it.next()).intValue();
            switch (iIntValue) {
                case 34:
                    sb.append("&quot;");
                    break;
                case 38:
                    sb.append("&amp;");
                    break;
                case OPCode.SEMI_END_BUF /* 39 */:
                    sb.append("&apos;");
                    break;
                case 60:
                    sb.append("&lt;");
                    break;
                case 62:
                    sb.append("&gt;");
                    break;
                default:
                    if (mustEscape(iIntValue)) {
                        sb.append("&#x");
                        sb.append(Integer.toHexString(iIntValue));
                        sb.append(';');
                        break;
                    } else {
                        sb.appendCodePoint(iIntValue);
                        break;
                    }
            }
        }
        return sb.toString();
    }

    private static boolean mustEscape(int i) {
        return !(!Character.isISOControl(i) || i == 9 || i == 10 || i == 13) || ((i < 32 || i > 55295) && ((i < 57344 || i > 65533) && (i < 65536 || i > 1114111)));
    }

    public static String unescape(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        int length = 0;
        int length2 = str.length();
        while (length < length2) {
            char cCharAt = str.charAt(length);
            if (cCharAt == '&') {
                int iIndexOf = str.indexOf(59, length);
                if (iIndexOf > length) {
                    String strSubstring = str.substring(length + 1, iIndexOf);
                    sb.append(XMLTokener.unescapeEntity(strSubstring));
                    length += strSubstring.length() + 1;
                } else {
                    sb.append(cCharAt);
                }
            } else {
                sb.append(cCharAt);
            }
            length++;
        }
        return sb.toString();
    }

    public static void noSpace(String str) {
        int length = str.length();
        if (length == 0) {
            throw new JSONException("Empty string.");
        }
        for (int i = 0; i < length; i++) {
            if (Character.isWhitespace(str.charAt(i))) {
                throw new JSONException("'" + str + "' contains a space character.");
            }
        }
    }

    private static boolean parse(XMLTokener xMLTokener, JSONObject jSONObject, String str, boolean z) {
        Object objNextToken = xMLTokener.nextToken();
        if (objNextToken == BANG) {
            char next = xMLTokener.next();
            if (next == '-') {
                if (xMLTokener.next() == '-') {
                    xMLTokener.skipPast("-->");
                    return false;
                }
                xMLTokener.back();
            } else if (next == '[') {
                if ("CDATA".equals(xMLTokener.nextToken()) && xMLTokener.next() == '[') {
                    String strNextCDATA = xMLTokener.nextCDATA();
                    if (strNextCDATA.length() > 0) {
                        jSONObject.accumulate("content", strNextCDATA);
                        return false;
                    }
                    return false;
                }
                throw xMLTokener.syntaxError("Expected 'CDATA['");
            }
            int i = 1;
            do {
                Object objNextMeta = xMLTokener.nextMeta();
                if (objNextMeta == null) {
                    throw xMLTokener.syntaxError("Missing '>' after '<!'.");
                }
                if (objNextMeta == f211LT) {
                    i++;
                } else if (objNextMeta == f210GT) {
                    i--;
                }
            } while (i > 0);
            return false;
        }
        if (objNextToken == QUEST) {
            xMLTokener.skipPast("?>");
            return false;
        }
        if (objNextToken == SLASH) {
            Object objNextToken2 = xMLTokener.nextToken();
            if (str == null) {
                throw xMLTokener.syntaxError("Mismatched close tag " + objNextToken2);
            }
            if (!objNextToken2.equals(str)) {
                throw xMLTokener.syntaxError("Mismatched " + str + " and " + objNextToken2);
            }
            if (xMLTokener.nextToken() != f210GT) {
                throw xMLTokener.syntaxError("Misshaped close tag");
            }
            return true;
        }
        if (objNextToken instanceof Character) {
            throw xMLTokener.syntaxError("Misshaped tag");
        }
        String str2 = (String) objNextToken;
        Object objNextToken3 = null;
        JSONObject jSONObject2 = new JSONObject();
        while (true) {
            if (objNextToken3 == null) {
                objNextToken3 = xMLTokener.nextToken();
            }
            if (objNextToken3 instanceof String) {
                String str3 = (String) objNextToken3;
                objNextToken3 = xMLTokener.nextToken();
                if (objNextToken3 == f209EQ) {
                    Object objNextToken4 = xMLTokener.nextToken();
                    if (!(objNextToken4 instanceof String)) {
                        throw xMLTokener.syntaxError("Missing value");
                    }
                    jSONObject2.accumulate(str3, z ? (String) objNextToken4 : stringToValue((String) objNextToken4));
                    objNextToken3 = null;
                } else {
                    jSONObject2.accumulate(str3, "");
                }
            } else {
                if (objNextToken3 == SLASH) {
                    if (xMLTokener.nextToken() != f210GT) {
                        throw xMLTokener.syntaxError("Misshaped tag");
                    }
                    if (jSONObject2.length() > 0) {
                        jSONObject.accumulate(str2, jSONObject2);
                        return false;
                    }
                    jSONObject.accumulate(str2, "");
                    return false;
                }
                if (objNextToken3 != f210GT) {
                    throw xMLTokener.syntaxError("Misshaped tag");
                }
                while (true) {
                    Object objNextContent = xMLTokener.nextContent();
                    if (objNextContent == null) {
                        if (str2 != null) {
                            throw xMLTokener.syntaxError("Unclosed tag " + str2);
                        }
                        return false;
                    }
                    if (objNextContent instanceof String) {
                        String str4 = (String) objNextContent;
                        if (str4.length() > 0) {
                            jSONObject2.accumulate("content", z ? str4 : stringToValue(str4));
                        }
                    } else if (objNextContent == f211LT && parse(xMLTokener, jSONObject2, str2, z)) {
                        if (jSONObject2.length() == 0) {
                            jSONObject.accumulate(str2, "");
                            return false;
                        }
                        if (jSONObject2.length() == 1 && jSONObject2.opt("content") != null) {
                            jSONObject.accumulate(str2, jSONObject2.opt("content"));
                            return false;
                        }
                        jSONObject.accumulate(str2, jSONObject2);
                        return false;
                    }
                }
            }
        }
    }

    public static Object stringToValue(String str) {
        return JSONObject.stringToValue(str);
    }

    public static JSONObject toJSONObject(String str) {
        return toJSONObject(str, false);
    }

    public static JSONObject toJSONObject(String str, boolean z) {
        JSONObject jSONObject = new JSONObject();
        XMLTokener xMLTokener = new XMLTokener(str);
        while (xMLTokener.more() && xMLTokener.skipPast("<")) {
            parse(xMLTokener, jSONObject, null, z);
        }
        return jSONObject;
    }

    public static String toString(Object obj) {
        return toString(obj, null);
    }

    public static String toString(Object obj, String str) {
        JSONArray jSONArray;
        StringBuilder sb = new StringBuilder();
        if (obj instanceof JSONObject) {
            if (str != null) {
                sb.append('<');
                sb.append(str);
                sb.append('>');
            }
            for (Map.Entry entry : ((JSONObject) obj).entrySet()) {
                String str2 = (String) entry.getKey();
                Object value = entry.getValue();
                if (value == null) {
                    value = "";
                } else if (value.getClass().isArray()) {
                    value = new JSONArray(value);
                }
                if ("content".equals(str2)) {
                    if (value instanceof JSONArray) {
                        int i = 0;
                        Iterator it = ((JSONArray) value).iterator();
                        while (it.hasNext()) {
                            Object next = it.next();
                            if (i > 0) {
                                sb.append('\n');
                            }
                            sb.append(escape(next.toString()));
                            i++;
                        }
                    } else {
                        sb.append(escape(value.toString()));
                    }
                } else if (value instanceof JSONArray) {
                    Iterator it2 = ((JSONArray) value).iterator();
                    while (it2.hasNext()) {
                        Object next2 = it2.next();
                        if (next2 instanceof JSONArray) {
                            sb.append('<');
                            sb.append(str2);
                            sb.append('>');
                            sb.append(toString(next2));
                            sb.append("</");
                            sb.append(str2);
                            sb.append('>');
                        } else {
                            sb.append(toString(next2, str2));
                        }
                    }
                } else if ("".equals(value)) {
                    sb.append('<');
                    sb.append(str2);
                    sb.append("/>");
                } else {
                    sb.append(toString(value, str2));
                }
            }
            if (str != null) {
                sb.append("</");
                sb.append(str);
                sb.append('>');
            }
            return sb.toString();
        }
        if (obj != null && ((obj instanceof JSONArray) || obj.getClass().isArray())) {
            if (obj.getClass().isArray()) {
                jSONArray = new JSONArray(obj);
            } else {
                jSONArray = (JSONArray) obj;
            }
            Iterator it3 = jSONArray.iterator();
            while (it3.hasNext()) {
                sb.append(toString(it3.next(), str == null ? "array" : str));
            }
            return sb.toString();
        }
        String strEscape = obj == null ? Configurator.NULL : escape(obj.toString());
        return str == null ? "\"" + strEscape + "\"" : strEscape.length() == 0 ? "<" + str + "/>" : "<" + str + ">" + strEscape + "</" + str + ">";
    }
}
