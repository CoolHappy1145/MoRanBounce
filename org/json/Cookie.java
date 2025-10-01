package org.json;

import java.io.IOException;
import jdk.nashorn.internal.runtime.PropertyDescriptor;

/* loaded from: L-out.jar:org/json/Cookie.class */
public class Cookie {
    public static String escape(String str) {
        String strTrim = str.trim();
        int length = strTrim.length();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            char cCharAt = strTrim.charAt(i);
            if (cCharAt < ' ' || cCharAt == '+' || cCharAt == '%' || cCharAt == '=' || cCharAt == ';') {
                sb.append('%');
                sb.append(Character.forDigit((char) ((cCharAt >>> 4) & 15), 16));
                sb.append(Character.forDigit((char) (cCharAt & 15), 16));
            } else {
                sb.append(cCharAt);
            }
        }
        return sb.toString();
    }

    public static JSONObject toJSONObject(String str) throws IOException {
        Object objUnescape;
        JSONObject jSONObject = new JSONObject();
        JSONTokener jSONTokener = new JSONTokener(str);
        jSONObject.put("name", jSONTokener.nextTo('='));
        jSONTokener.next('=');
        jSONObject.put(PropertyDescriptor.VALUE, jSONTokener.nextTo(';'));
        jSONTokener.next();
        while (jSONTokener.more()) {
            String strUnescape = unescape(jSONTokener.nextTo("=;"));
            if (jSONTokener.next() != '=') {
                if (strUnescape.equals("secure")) {
                    objUnescape = Boolean.TRUE;
                } else {
                    throw jSONTokener.syntaxError("Missing '=' in cookie parameter.");
                }
            } else {
                objUnescape = unescape(jSONTokener.nextTo(';'));
                jSONTokener.next();
            }
            jSONObject.put(strUnescape, objUnescape);
        }
        return jSONObject;
    }

    public static String toString(JSONObject jSONObject) {
        StringBuilder sb = new StringBuilder();
        sb.append(escape(jSONObject.getString("name")));
        sb.append("=");
        sb.append(escape(jSONObject.getString(PropertyDescriptor.VALUE)));
        if (jSONObject.has("expires")) {
            sb.append(";expires=");
            sb.append(jSONObject.getString("expires"));
        }
        if (jSONObject.has("domain")) {
            sb.append(";domain=");
            sb.append(escape(jSONObject.getString("domain")));
        }
        if (jSONObject.has("path")) {
            sb.append(";path=");
            sb.append(escape(jSONObject.getString("path")));
        }
        if (jSONObject.optBoolean("secure")) {
            sb.append(";secure");
        }
        return sb.toString();
    }

    public static String unescape(String str) {
        int i;
        int i2;
        int length = str.length();
        StringBuilder sb = new StringBuilder(length);
        int i3 = 0;
        while (i3 < length) {
            char cCharAt = str.charAt(i3);
            if (cCharAt == '+') {
                cCharAt = ' ';
            } else if (cCharAt == '%' && i3 + 2 < length) {
                char cCharAt2 = str.charAt(i3 + 1);
                if (cCharAt2 >= '0' && cCharAt2 <= '9') {
                    i = cCharAt2 - '0';
                } else if (cCharAt2 >= 'A' && cCharAt2 <= 'F') {
                    i = cCharAt2 - '7';
                } else if (cCharAt2 >= 'a' && cCharAt2 <= 'f') {
                    i = cCharAt2 - 'W';
                } else {
                    i = -1;
                }
                int i4 = i;
                char cCharAt3 = str.charAt(i3 + 2);
                if (cCharAt3 >= '0' && cCharAt3 <= '9') {
                    i2 = cCharAt3 - '0';
                } else if (cCharAt3 >= 'A' && cCharAt3 <= 'F') {
                    i2 = cCharAt3 - '7';
                } else if (cCharAt3 >= 'a' && cCharAt3 <= 'f') {
                    i2 = cCharAt3 - 'W';
                } else {
                    i2 = -1;
                }
                int i5 = i2;
                if (i4 >= 0 && i5 >= 0) {
                    cCharAt = (char) ((i4 * 16) + i5);
                    i3 += 2;
                }
            }
            sb.append(cCharAt);
            i3++;
        }
        return sb.toString();
    }
}
