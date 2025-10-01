package org.json;

/* loaded from: L-out.jar:org/json/JSONException.class */
public class JSONException extends RuntimeException {
    private static final long serialVersionUID = 0;

    public JSONException(String str) {
        super(str);
    }

    public JSONException(String str, Throwable th) {
        super(str, th);
    }

    public JSONException(Throwable th) {
        super(th.getMessage(), th);
    }
}
