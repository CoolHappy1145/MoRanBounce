package org.json;

import java.util.Locale;
import java.util.Map;

/* loaded from: L-out.jar:org/json/HTTP.class */
public class HTTP {
    public static final String CRLF = "\r\n";

    public static JSONObject toJSONObject(String str) {
        JSONObject jSONObject = new JSONObject();
        HTTPTokener hTTPTokener = new HTTPTokener(str);
        String strNextToken = hTTPTokener.nextToken();
        if (strNextToken.toUpperCase(Locale.ROOT).startsWith("HTTP")) {
            jSONObject.put("HTTP-Version", strNextToken);
            jSONObject.put("Status-Code", hTTPTokener.nextToken());
            jSONObject.put("Reason-Phrase", hTTPTokener.nextTo((char) 0));
            hTTPTokener.next();
        } else {
            jSONObject.put("Method", strNextToken);
            jSONObject.put("Request-URI", hTTPTokener.nextToken());
            jSONObject.put("HTTP-Version", hTTPTokener.nextToken());
        }
        while (hTTPTokener.more()) {
            String strNextTo = hTTPTokener.nextTo(':');
            hTTPTokener.next(':');
            jSONObject.put(strNextTo, hTTPTokener.nextTo((char) 0));
            hTTPTokener.next();
        }
        return jSONObject;
    }

    public static String toString(JSONObject jSONObject) {
        StringBuilder sb = new StringBuilder();
        if (jSONObject.has("Status-Code") && jSONObject.has("Reason-Phrase")) {
            sb.append(jSONObject.getString("HTTP-Version"));
            sb.append(' ');
            sb.append(jSONObject.getString("Status-Code"));
            sb.append(' ');
            sb.append(jSONObject.getString("Reason-Phrase"));
        } else if (jSONObject.has("Method") && jSONObject.has("Request-URI")) {
            sb.append(jSONObject.getString("Method"));
            sb.append(' ');
            sb.append('\"');
            sb.append(jSONObject.getString("Request-URI"));
            sb.append('\"');
            sb.append(' ');
            sb.append(jSONObject.getString("HTTP-Version"));
        } else {
            throw new JSONException("Not enough material for an HTTP header.");
        }
        sb.append(CRLF);
        for (Map.Entry entry : jSONObject.entrySet()) {
            String str = (String) entry.getKey();
            if (!"HTTP-Version".equals(str) && !"Status-Code".equals(str) && !"Reason-Phrase".equals(str) && !"Method".equals(str) && !"Request-URI".equals(str) && !JSONObject.NULL.equals(entry.getValue())) {
                sb.append(str);
                sb.append(": ");
                sb.append(jSONObject.optString(str));
                sb.append(CRLF);
            }
        }
        sb.append(CRLF);
        return sb.toString();
    }
}
