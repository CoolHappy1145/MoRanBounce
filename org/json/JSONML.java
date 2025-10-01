package org.json;

import java.util.Map;

/* loaded from: L-out.jar:org/json/JSONML.class */
public class JSONML {
    /* JADX WARN: Code restructure failed: missing block: B:90:0x01e2, code lost:
    
        throw r5.syntaxError("Reserved attribute.");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static Object parse(XMLTokener xMLTokener, boolean z, JSONArray jSONArray, boolean z2) {
        Object objUnescape;
        loop0: while (xMLTokener.more()) {
            Object objNextContent = xMLTokener.nextContent();
            if (objNextContent == XML.f211LT) {
                Object objNextToken = xMLTokener.nextToken();
                if (objNextToken instanceof Character) {
                    if (objNextToken == XML.SLASH) {
                        Object objNextToken2 = xMLTokener.nextToken();
                        if (!(objNextToken2 instanceof String)) {
                            throw new JSONException("Expected a closing name instead of '" + objNextToken2 + "'.");
                        }
                        if (xMLTokener.nextToken() != XML.f210GT) {
                            throw xMLTokener.syntaxError("Misshaped close tag");
                        }
                        return objNextToken2;
                    }
                    if (objNextToken == XML.BANG) {
                        char next = xMLTokener.next();
                        if (next == '-') {
                            if (xMLTokener.next() == '-') {
                                xMLTokener.skipPast("-->");
                            } else {
                                xMLTokener.back();
                            }
                        } else if (next == '[') {
                            if (xMLTokener.nextToken().equals("CDATA") && xMLTokener.next() == '[') {
                                if (jSONArray != null) {
                                    jSONArray.put(xMLTokener.nextCDATA());
                                }
                            } else {
                                throw xMLTokener.syntaxError("Expected 'CDATA['");
                            }
                        } else {
                            int i = 1;
                            do {
                                Object objNextMeta = xMLTokener.nextMeta();
                                if (objNextMeta == null) {
                                    throw xMLTokener.syntaxError("Missing '>' after '<!'.");
                                }
                                if (objNextMeta == XML.f211LT) {
                                    i++;
                                } else if (objNextMeta == XML.f210GT) {
                                    i--;
                                }
                            } while (i > 0);
                        }
                    } else if (objNextToken == XML.QUEST) {
                        xMLTokener.skipPast("?>");
                    } else {
                        throw xMLTokener.syntaxError("Misshaped tag");
                    }
                } else {
                    if (!(objNextToken instanceof String)) {
                        throw xMLTokener.syntaxError("Bad tagName '" + objNextToken + "'.");
                    }
                    String str = (String) objNextToken;
                    JSONArray jSONArray2 = new JSONArray();
                    JSONObject jSONObject = new JSONObject();
                    if (z) {
                        jSONArray2.put(str);
                        if (jSONArray != null) {
                            jSONArray.put(jSONArray2);
                        }
                    } else {
                        jSONObject.put("tagName", str);
                        if (jSONArray != null) {
                            jSONArray.put(jSONObject);
                        }
                    }
                    Object objNextToken3 = null;
                    while (true) {
                        if (objNextToken3 == null) {
                            objNextToken3 = xMLTokener.nextToken();
                        }
                        if (objNextToken3 == null) {
                            throw xMLTokener.syntaxError("Misshaped tag");
                        }
                        if (objNextToken3 instanceof String) {
                            String str2 = (String) objNextToken3;
                            if (!z && ("tagName".equals(str2) || "childNode".equals(str2))) {
                                break loop0;
                            }
                            objNextToken3 = xMLTokener.nextToken();
                            if (objNextToken3 == XML.f209EQ) {
                                Object objNextToken4 = xMLTokener.nextToken();
                                if (!(objNextToken4 instanceof String)) {
                                    throw xMLTokener.syntaxError("Missing value");
                                }
                                jSONObject.accumulate(str2, z2 ? (String) objNextToken4 : XML.stringToValue((String) objNextToken4));
                                objNextToken3 = null;
                            } else {
                                jSONObject.accumulate(str2, "");
                            }
                        } else {
                            if (z && jSONObject.length() > 0) {
                                jSONArray2.put(jSONObject);
                            }
                            if (objNextToken3 == XML.SLASH) {
                                if (xMLTokener.nextToken() != XML.f210GT) {
                                    throw xMLTokener.syntaxError("Misshaped tag");
                                }
                                if (jSONArray == null) {
                                    if (z) {
                                        return jSONArray2;
                                    }
                                    return jSONObject;
                                }
                            } else {
                                if (objNextToken3 != XML.f210GT) {
                                    throw xMLTokener.syntaxError("Misshaped tag");
                                }
                                String str3 = (String) parse(xMLTokener, z, jSONArray2, z2);
                                if (str3 == null) {
                                    continue;
                                } else {
                                    if (!str3.equals(str)) {
                                        throw xMLTokener.syntaxError("Mismatched '" + str + "' and '" + str3 + "'");
                                    }
                                    if (!z && jSONArray2.length() > 0) {
                                        jSONObject.put("childNodes", jSONArray2);
                                    }
                                    if (jSONArray == null) {
                                        if (z) {
                                            return jSONArray2;
                                        }
                                        return jSONObject;
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (jSONArray != null) {
                if (objNextContent instanceof String) {
                    objUnescape = z2 ? XML.unescape((String) objNextContent) : XML.stringToValue((String) objNextContent);
                } else {
                    objUnescape = objNextContent;
                }
                jSONArray.put(objUnescape);
            }
        }
        throw xMLTokener.syntaxError("Bad XML");
    }

    public static JSONArray toJSONArray(String str) {
        return (JSONArray) parse(new XMLTokener(str), true, null, false);
    }

    public static JSONArray toJSONArray(String str, boolean z) {
        return (JSONArray) parse(new XMLTokener(str), true, null, z);
    }

    public static JSONArray toJSONArray(XMLTokener xMLTokener, boolean z) {
        return (JSONArray) parse(xMLTokener, true, null, z);
    }

    public static JSONArray toJSONArray(XMLTokener xMLTokener) {
        return (JSONArray) parse(xMLTokener, true, null, false);
    }

    public static JSONObject toJSONObject(String str) {
        return (JSONObject) parse(new XMLTokener(str), false, null, false);
    }

    public static JSONObject toJSONObject(String str, boolean z) {
        return (JSONObject) parse(new XMLTokener(str), false, null, z);
    }

    public static JSONObject toJSONObject(XMLTokener xMLTokener) {
        return (JSONObject) parse(xMLTokener, false, null, false);
    }

    public static JSONObject toJSONObject(XMLTokener xMLTokener, boolean z) {
        return (JSONObject) parse(xMLTokener, false, null, z);
    }

    public static String toString(JSONArray jSONArray) {
        int i;
        StringBuilder sb = new StringBuilder();
        String string = jSONArray.getString(0);
        XML.noSpace(string);
        String strEscape = XML.escape(string);
        sb.append('<');
        sb.append(strEscape);
        Object objOpt = jSONArray.opt(1);
        if (objOpt instanceof JSONObject) {
            i = 2;
            for (Map.Entry entry : ((JSONObject) objOpt).entrySet()) {
                String str = (String) entry.getKey();
                XML.noSpace(str);
                Object value = entry.getValue();
                if (value != null) {
                    sb.append(' ');
                    sb.append(XML.escape(str));
                    sb.append('=');
                    sb.append('\"');
                    sb.append(XML.escape(value.toString()));
                    sb.append('\"');
                }
            }
        } else {
            i = 1;
        }
        int length = jSONArray.length();
        if (i >= length) {
            sb.append('/');
            sb.append('>');
        } else {
            sb.append('>');
            do {
                Object obj = jSONArray.get(i);
                i++;
                if (obj != null) {
                    if (obj instanceof String) {
                        sb.append(XML.escape(obj.toString()));
                    } else if (obj instanceof JSONObject) {
                        sb.append(toString((JSONObject) obj));
                    } else if (obj instanceof JSONArray) {
                        sb.append(toString((JSONArray) obj));
                    } else {
                        sb.append(obj.toString());
                    }
                }
            } while (i < length);
            sb.append('<');
            sb.append('/');
            sb.append(strEscape);
            sb.append('>');
        }
        return sb.toString();
    }

    public static String toString(JSONObject jSONObject) {
        StringBuilder sb = new StringBuilder();
        String strOptString = jSONObject.optString("tagName");
        if (strOptString == null) {
            return XML.escape(jSONObject.toString());
        }
        XML.noSpace(strOptString);
        String strEscape = XML.escape(strOptString);
        sb.append('<');
        sb.append(strEscape);
        for (Map.Entry entry : jSONObject.entrySet()) {
            String str = (String) entry.getKey();
            if (!"tagName".equals(str) && !"childNodes".equals(str)) {
                XML.noSpace(str);
                Object value = entry.getValue();
                if (value != null) {
                    sb.append(' ');
                    sb.append(XML.escape(str));
                    sb.append('=');
                    sb.append('\"');
                    sb.append(XML.escape(value.toString()));
                    sb.append('\"');
                }
            }
        }
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray("childNodes");
        if (jSONArrayOptJSONArray == null) {
            sb.append('/');
            sb.append('>');
        } else {
            sb.append('>');
            int length = jSONArrayOptJSONArray.length();
            for (int i = 0; i < length; i++) {
                Object obj = jSONArrayOptJSONArray.get(i);
                if (obj != null) {
                    if (obj instanceof String) {
                        sb.append(XML.escape(obj.toString()));
                    } else if (obj instanceof JSONObject) {
                        sb.append(toString((JSONObject) obj));
                    } else if (obj instanceof JSONArray) {
                        sb.append(toString((JSONArray) obj));
                    } else {
                        sb.append(obj.toString());
                    }
                }
            }
            sb.append('<');
            sb.append('/');
            sb.append(strEscape);
            sb.append('>');
        }
        return sb.toString();
    }
}
