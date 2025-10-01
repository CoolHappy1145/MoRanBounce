package org.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import jdk.nashorn.tools.Shell;

/* loaded from: L-out.jar:org/json/JSONTokener.class */
public class JSONTokener {
    private long character;
    private boolean eof;
    private long index;
    private long line;
    private char previous;
    private final Reader reader;
    private boolean usePrevious;
    private long characterPreviousLine;

    public JSONTokener(Reader reader) {
        this.reader = reader.markSupported() ? reader : new BufferedReader(reader);
        this.eof = false;
        this.usePrevious = false;
        this.previous = (char) 0;
        this.index = 0L;
        this.character = 1L;
        this.characterPreviousLine = 0L;
        this.line = 1L;
    }

    public JSONTokener(InputStream inputStream) {
        this(new InputStreamReader(inputStream));
    }

    public JSONTokener(String str) {
        this(new StringReader(str));
    }

    public void back() {
        if (this.usePrevious || this.index <= 0) {
            throw new JSONException("Stepping back two steps is not supported");
        }
        decrementIndexes();
        this.usePrevious = true;
        this.eof = false;
    }

    private void decrementIndexes() {
        this.index--;
        if (this.previous == '\r' || this.previous == '\n') {
            this.line--;
            this.character = this.characterPreviousLine;
        } else if (this.character > 0) {
            this.character--;
        }
    }

    public boolean end() {
        return this.eof && !this.usePrevious;
    }

    public boolean more() throws IOException {
        if (this.usePrevious) {
            return true;
        }
        try {
            this.reader.mark(1);
            try {
                if (this.reader.read() <= 0) {
                    this.eof = true;
                    return false;
                }
                this.reader.reset();
                return true;
            } catch (IOException e) {
                throw new JSONException("Unable to read the next character from the stream", e);
            }
        } catch (IOException e2) {
            throw new JSONException("Unable to preserve stream position", e2);
        }
    }

    public char next() throws IOException {
        int i;
        if (this.usePrevious) {
            this.usePrevious = false;
            i = this.previous;
        } else {
            try {
                i = this.reader.read();
            } catch (IOException e) {
                throw new JSONException(e);
            }
        }
        if (i <= 0) {
            this.eof = true;
            return (char) 0;
        }
        incrementIndexes(i);
        this.previous = (char) i;
        return this.previous;
    }

    private void incrementIndexes(int i) {
        if (i > 0) {
            this.index++;
            if (i == 13) {
                this.line++;
                this.characterPreviousLine = this.character;
                this.character = 0L;
            } else {
                if (i == 10) {
                    if (this.previous != '\r') {
                        this.line++;
                        this.characterPreviousLine = this.character;
                    }
                    this.character = 0L;
                    return;
                }
                this.character++;
            }
        }
    }

    public char next(char c) throws IOException {
        char next = next();
        if (next != c) {
            if (next > 0) {
                throw syntaxError("Expected '" + c + "' and instead saw '" + next + "'");
            }
            throw syntaxError("Expected '" + c + "' and instead saw ''");
        }
        return next;
    }

    public String next(int i) {
        if (i == 0) {
            return "";
        }
        char[] cArr = new char[i];
        for (int i2 = 0; i2 < i; i2++) {
            cArr[i2] = next();
            if (end()) {
                throw syntaxError("Substring bounds error");
            }
        }
        return new String(cArr);
    }

    public char nextClean() throws IOException {
        char next;
        do {
            next = next();
            if (next == 0) {
                break;
            }
        } while (next <= ' ');
        return next;
    }

    public String nextString(char c) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            char next = next();
            switch (next) {
                case 0:
                case '\n':
                case CharacterType.ALNUM /* 13 */:
                    throw syntaxError("Unterminated string");
                case '\\':
                    char next2 = next();
                    switch (next2) {
                        case '\"':
                        case OPCode.SEMI_END_BUF /* 39 */:
                        case OPCode.BACKREF_WITH_LEVEL /* 47 */:
                        case '\\':
                            sb.append(next2);
                            break;
                        case 'b':
                            sb.append('\b');
                            break;
                        case Shell.RUNTIME_ERROR /* 102 */:
                            sb.append('\f');
                            break;
                        case 'n':
                            sb.append('\n');
                            break;
                        case 'r':
                            sb.append('\r');
                            break;
                        case 't':
                            sb.append('\t');
                            break;
                        case 'u':
                            try {
                                sb.append((char) Integer.parseInt(next(4), 16));
                                break;
                            } catch (NumberFormatException e) {
                                throw syntaxError("Illegal escape.", e);
                            }
                        default:
                            throw syntaxError("Illegal escape.");
                    }
                default:
                    if (next == c) {
                        return sb.toString();
                    }
                    sb.append(next);
                    break;
            }
        }
    }

    public String nextTo(char c) throws IOException {
        char next;
        StringBuilder sb = new StringBuilder();
        while (true) {
            next = next();
            if (next == c || next == 0 || next == '\n' || next == '\r') {
                break;
            }
            sb.append(next);
        }
        if (next != 0) {
            back();
        }
        return sb.toString().trim();
    }

    public String nextTo(String str) throws IOException {
        char next;
        StringBuilder sb = new StringBuilder();
        while (true) {
            next = next();
            if (str.indexOf(next) >= 0 || next == 0 || next == '\n' || next == '\r') {
                break;
            }
            sb.append(next);
        }
        if (next != 0) {
            back();
        }
        return sb.toString().trim();
    }

    public Object nextValue() throws IOException {
        char cNextClean = nextClean();
        switch (cNextClean) {
            case '\"':
            case OPCode.SEMI_END_BUF /* 39 */:
                return nextString(cNextClean);
            case '[':
                back();
                return new JSONArray(this);
            case '{':
                back();
                return new JSONObject(this);
            default:
                StringBuilder sb = new StringBuilder();
                while (cNextClean >= ' ' && ",:]}/\\\"[{;=#".indexOf(cNextClean) < 0) {
                    sb.append(cNextClean);
                    cNextClean = next();
                }
                back();
                String strTrim = sb.toString().trim();
                if ("".equals(strTrim)) {
                    throw syntaxError("Missing value");
                }
                return JSONObject.stringToValue(strTrim);
        }
    }

    public char skipTo(char c) throws IOException {
        char next;
        try {
            long j = this.index;
            long j2 = this.character;
            long j3 = this.line;
            this.reader.mark(1000000);
            do {
                next = next();
                if (next == 0) {
                    this.reader.reset();
                    this.index = j;
                    this.character = j2;
                    this.line = j3;
                    return (char) 0;
                }
            } while (next != c);
            this.reader.mark(1);
            back();
            return next;
        } catch (IOException e) {
            throw new JSONException(e);
        }
    }

    public JSONException syntaxError(String str) {
        return new JSONException(str + toString());
    }

    public JSONException syntaxError(String str, Throwable th) {
        return new JSONException(str + toString(), th);
    }

    public String toString() {
        return " at " + this.index + " [character " + this.character + " line " + this.line + "]";
    }
}
