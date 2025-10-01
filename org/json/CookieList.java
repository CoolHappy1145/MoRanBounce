package org.json;

import java.io.IOException;
import java.util.Map;

/* loaded from: L-out.jar:org/json/CookieList.class */
public class CookieList {
    public static JSONObject toJSONObject(String str) throws IOException {
        JSONObject jSONObject = new JSONObject();
        JSONTokener jSONTokener = new JSONTokener(str);
        while (jSONTokener.more()) {
            String strUnescape = Cookie.unescape(jSONTokener.nextTo('='));
            jSONTokener.next('=');
            jSONObject.put(strUnescape, Cookie.unescape(jSONTokener.nextTo(';')));
            jSONTokener.next();
        }
        return jSONObject;
    }

    public static String toString(JSONObject jSONObject) {
        boolean z = false;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry entry : jSONObject.entrySet()) {
            String str = (String) entry.getKey();
            Object value = entry.getValue();
            if (!JSONObject.NULL.equals(value)) {
                if (z) {
                    sb.append(';');
                }
                sb.append(Cookie.escape(str));
                sb.append("=");
                sb.append(Cookie.escape(value.toString()));
                z = true;
            }
        }
        return sb.toString();
    }
}
