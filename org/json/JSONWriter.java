package org.json;

import java.io.IOException;

/* loaded from: L-out.jar:org/json/JSONWriter.class */
public class JSONWriter {
    private static final int maxdepth = 200;
    private boolean comma = false;
    protected char mode = 'i';
    private final JSONObject[] stack = new JSONObject[200];
    private int top = 0;
    protected Appendable writer;

    public JSONWriter(Appendable appendable) {
        this.writer = appendable;
    }

    private JSONWriter append(String str) throws IOException {
        if (str == null) {
            throw new JSONException("Null pointer");
        }
        if (this.mode == 'o' || this.mode == 'a') {
            try {
                if (this.comma && this.mode == 'a') {
                    this.writer.append(',');
                }
                this.writer.append(str);
                if (this.mode == 'o') {
                    this.mode = 'k';
                }
                this.comma = true;
                return this;
            } catch (IOException e) {
                throw new JSONException(e);
            }
        }
        throw new JSONException("Value out of sequence.");
    }

    public JSONWriter array() throws IOException {
        if (this.mode == 'i' || this.mode == 'o' || this.mode == 'a') {
            push(null);
            append("[");
            this.comma = false;
            return this;
        }
        throw new JSONException("Misplaced array.");
    }

    private JSONWriter end(char c, char c2) throws IOException {
        if (this.mode != c) {
            throw new JSONException(c == 'a' ? "Misplaced endArray." : "Misplaced endObject.");
        }
        pop(c);
        try {
            this.writer.append(c2);
            this.comma = true;
            return this;
        } catch (IOException e) {
            throw new JSONException(e);
        }
    }

    public JSONWriter endArray() {
        return end('a', ']');
    }

    public JSONWriter endObject() {
        return end('k', '}');
    }

    public JSONWriter key(String str) throws IOException {
        if (str == null) {
            throw new JSONException("Null key.");
        }
        if (this.mode == 'k') {
            try {
                this.stack[this.top - 1].putOnce(str, Boolean.TRUE);
                if (this.comma) {
                    this.writer.append(',');
                }
                this.writer.append(JSONObject.quote(str));
                this.writer.append(':');
                this.comma = false;
                this.mode = 'o';
                return this;
            } catch (IOException e) {
                throw new JSONException(e);
            }
        }
        throw new JSONException("Misplaced key.");
    }

    public JSONWriter object() throws IOException {
        if (this.mode == 'i') {
            this.mode = 'o';
        }
        if (this.mode == 'o' || this.mode == 'a') {
            append("{");
            push(new JSONObject());
            this.comma = false;
            return this;
        }
        throw new JSONException("Misplaced object.");
    }

    private void pop(char c) {
        if (this.top <= 0) {
            throw new JSONException("Nesting error.");
        }
        if ((this.stack[this.top - 1] == null ? 'a' : 'k') != c) {
            throw new JSONException("Nesting error.");
        }
        this.top--;
        this.mode = this.top == 0 ? 'd' : this.stack[this.top - 1] == null ? 'a' : 'k';
    }

    private void push(JSONObject jSONObject) {
        if (this.top >= 200) {
            throw new JSONException("Nesting too deep.");
        }
        this.stack[this.top] = jSONObject;
        this.mode = jSONObject == null ? 'a' : 'k';
        this.top++;
    }

    public JSONWriter value(boolean z) {
        return append(z ? "true" : "false");
    }

    public JSONWriter value(double d) {
        return value(new Double(d));
    }

    public JSONWriter value(long j) {
        return append(Long.toString(j));
    }

    public JSONWriter value(Object obj) {
        return append(JSONObject.valueToString(obj));
    }
}
