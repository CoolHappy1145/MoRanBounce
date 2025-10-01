package org.json;

import java.io.IOException;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

/* loaded from: L-out.jar:org/json/CDL.class */
public class CDL {
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0097, code lost:
    
        throw r4.syntaxError("Missing close quote '" + r0 + "'.");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String getValue(JSONTokener jSONTokener) throws IOException {
        char next;
        char next2;
        while (true) {
            next = jSONTokener.next();
            if (next != ' ' && next != '\t') {
                break;
            }
        }
        switch (next) {
            case 0:
                return null;
            case '\"':
            case OPCode.SEMI_END_BUF /* 39 */:
                StringBuffer stringBuffer = new StringBuffer();
                while (true) {
                    char next3 = jSONTokener.next();
                    if (next3 == next && (next2 = jSONTokener.next()) != '\"') {
                        if (next2 > 0) {
                            jSONTokener.back();
                        }
                        return stringBuffer.toString();
                    }
                    if (next3 == 0 || next3 == '\n' || next3 == '\r') {
                        break;
                    } else {
                        stringBuffer.append(next3);
                    }
                }
                break;
            case OPCode.BACKREFN_IC /* 44 */:
                jSONTokener.back();
                return "";
            default:
                jSONTokener.back();
                return jSONTokener.nextTo(',');
        }
    }

    public static JSONArray rowToJSONArray(JSONTokener jSONTokener) throws IOException {
        JSONArray jSONArray = new JSONArray();
        while (true) {
            String value = getValue(jSONTokener);
            char next = jSONTokener.next();
            if (value != null) {
                if (jSONArray.length() == 0 && value.length() == 0 && next != ',') {
                    return null;
                }
                jSONArray.put(value);
                while (next != ',') {
                    if (next != ' ') {
                        if (next == '\n' || next == '\r' || next == 0) {
                            return jSONArray;
                        }
                        throw jSONTokener.syntaxError("Bad character '" + next + "' (" + ((int) next) + ").");
                    }
                    next = jSONTokener.next();
                }
            } else {
                return null;
            }
        }
    }

    public static JSONObject rowToJSONObject(JSONArray jSONArray, JSONTokener jSONTokener) throws IOException {
        JSONArray jSONArrayRowToJSONArray = rowToJSONArray(jSONTokener);
        if (jSONArrayRowToJSONArray != null) {
            return jSONArrayRowToJSONArray.toJSONObject(jSONArray);
        }
        return null;
    }

    public static String rowToString(JSONArray jSONArray) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < jSONArray.length(); i++) {
            if (i > 0) {
                sb.append(',');
            }
            Object objOpt = jSONArray.opt(i);
            if (objOpt != null) {
                String string = objOpt.toString();
                if (string.length() > 0 && (string.indexOf(44) >= 0 || string.indexOf(10) >= 0 || string.indexOf(13) >= 0 || string.indexOf(0) >= 0 || string.charAt(0) == '\"')) {
                    sb.append('\"');
                    int length = string.length();
                    for (int i2 = 0; i2 < length; i2++) {
                        char cCharAt = string.charAt(i2);
                        if (cCharAt >= ' ' && cCharAt != '\"') {
                            sb.append(cCharAt);
                        }
                    }
                    sb.append('\"');
                } else {
                    sb.append(string);
                }
            }
        }
        sb.append('\n');
        return sb.toString();
    }

    public static JSONArray toJSONArray(String str) {
        return toJSONArray(new JSONTokener(str));
    }

    public static JSONArray toJSONArray(JSONTokener jSONTokener) {
        return toJSONArray(rowToJSONArray(jSONTokener), jSONTokener);
    }

    public static JSONArray toJSONArray(JSONArray jSONArray, String str) {
        return toJSONArray(jSONArray, new JSONTokener(str));
    }

    public static JSONArray toJSONArray(JSONArray jSONArray, JSONTokener jSONTokener) throws IOException {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        JSONArray jSONArray2 = new JSONArray();
        while (true) {
            JSONObject jSONObjectRowToJSONObject = rowToJSONObject(jSONArray, jSONTokener);
            if (jSONObjectRowToJSONObject == null) {
                break;
            }
            jSONArray2.put(jSONObjectRowToJSONObject);
        }
        if (jSONArray2.length() == 0) {
            return null;
        }
        return jSONArray2;
    }

    public static String toString(JSONArray jSONArray) {
        JSONArray jSONArrayNames;
        JSONObject jSONObjectOptJSONObject = jSONArray.optJSONObject(0);
        if (jSONObjectOptJSONObject != null && (jSONArrayNames = jSONObjectOptJSONObject.names()) != null) {
            return rowToString(jSONArrayNames) + toString(jSONArrayNames, jSONArray);
        }
        return null;
    }

    public static String toString(JSONArray jSONArray, JSONArray jSONArray2) {
        if (jSONArray == null || jSONArray.length() == 0) {
            return null;
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < jSONArray2.length(); i++) {
            JSONObject jSONObjectOptJSONObject = jSONArray2.optJSONObject(i);
            if (jSONObjectOptJSONObject != null) {
                stringBuffer.append(rowToString(jSONObjectOptJSONObject.toJSONArray(jSONArray)));
            }
        }
        return stringBuffer.toString();
    }
}
