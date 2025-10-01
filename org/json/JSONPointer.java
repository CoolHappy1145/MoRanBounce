package org.json;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: L-out.jar:org/json/JSONPointer.class */
public class JSONPointer {
    private static final String ENCODING = "utf-8";
    private final List refTokens;

    /* loaded from: L-out.jar:org/json/JSONPointer$Builder.class */
    public static class Builder {
        private final List refTokens = new ArrayList();

        public JSONPointer build() {
            return new JSONPointer(this.refTokens);
        }

        public Builder append(String str) {
            if (str == null) {
                throw new NullPointerException("token cannot be null");
            }
            this.refTokens.add(str);
            return this;
        }

        public Builder append(int i) {
            this.refTokens.add(String.valueOf(i));
            return this;
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    public JSONPointer(String str) throws UnsupportedEncodingException {
        String strSubstring;
        if (str == null) {
            throw new NullPointerException("pointer cannot be null");
        }
        if (str.isEmpty() || str.equals("#")) {
            this.refTokens = Collections.emptyList();
            return;
        }
        if (str.startsWith("#/")) {
            try {
                strSubstring = URLDecoder.decode(str.substring(2), ENCODING);
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        } else if (str.startsWith("/")) {
            strSubstring = str.substring(1);
        } else {
            throw new IllegalArgumentException("a JSON pointer should start with '/' or '#/'");
        }
        this.refTokens = new ArrayList();
        for (String str2 : strSubstring.split("/")) {
            this.refTokens.add(unescape(str2));
        }
    }

    public JSONPointer(List list) {
        this.refTokens = new ArrayList(list);
    }

    private String unescape(String str) {
        return str.replace("~1", "/").replace("~0", "~").replace("\\\"", "\"").replace("\\\\", "\\");
    }

    public Object queryFrom(Object obj) throws NumberFormatException {
        if (this.refTokens.isEmpty()) {
            return obj;
        }
        Object objOpt = obj;
        for (String str : this.refTokens) {
            if (objOpt instanceof JSONObject) {
                objOpt = ((JSONObject) objOpt).opt(unescape(str));
            } else if (objOpt instanceof JSONArray) {
                objOpt = readByIndexToken(objOpt, str);
            } else {
                throw new JSONPointerException(String.format("value [%s] is not an array or object therefore its key %s cannot be resolved", objOpt, str));
            }
        }
        return objOpt;
    }

    private Object readByIndexToken(Object obj, String str) throws NumberFormatException {
        try {
            int i = Integer.parseInt(str);
            JSONArray jSONArray = (JSONArray) obj;
            if (i >= jSONArray.length()) {
                throw new JSONPointerException(String.format("index %d is out of bounds - the array has %d elements", Integer.valueOf(i), Integer.valueOf(jSONArray.length())));
            }
            return jSONArray.get(i);
        } catch (NumberFormatException e) {
            throw new JSONPointerException(String.format("%s is not an array index", str), e);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("");
        Iterator it = this.refTokens.iterator();
        while (it.hasNext()) {
            sb.append('/').append(escape((String) it.next()));
        }
        return sb.toString();
    }

    private String escape(String str) {
        return str.replace("~", "~0").replace("/", "~1").replace("\\", "\\\\").replace("\"", "\\\"");
    }

    public String toURIFragment() {
        try {
            StringBuilder sb = new StringBuilder("#");
            Iterator it = this.refTokens.iterator();
            while (it.hasNext()) {
                sb.append('/').append(URLEncoder.encode((String) it.next(), ENCODING));
            }
            return sb.toString();
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
