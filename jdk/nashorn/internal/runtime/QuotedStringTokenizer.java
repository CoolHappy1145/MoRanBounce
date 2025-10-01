package jdk.nashorn.internal.runtime;

import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/QuotedStringTokenizer.class */
public final class QuotedStringTokenizer {
    private final LinkedList tokens;
    private final char[] quotes;

    public QuotedStringTokenizer(String str) {
        this(str, " ");
    }

    public QuotedStringTokenizer(String str, String str2) {
        this(str, str2, new char[]{'\"', '\''});
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x009a, code lost:
    
        r5.tokens.add(stripQuotes(r11));
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private QuotedStringTokenizer(String str, String str2, char[] cArr) {
        this.quotes = cArr;
        boolean z = true;
        int i = 0;
        while (true) {
            if (i >= str2.length()) {
                break;
            }
            if (Character.isWhitespace(str2.charAt(i))) {
                i++;
            } else {
                z = false;
                break;
            }
        }
        StringTokenizer stringTokenizer = new StringTokenizer(str, str2);
        this.tokens = new LinkedList();
        while (stringTokenizer.hasMoreTokens()) {
            String strNextToken = stringTokenizer.nextToken();
            while (true) {
                String str3 = strNextToken;
                if (unmatchedQuotesIn(str3)) {
                    if (!stringTokenizer.hasMoreTokens()) {
                        throw new IndexOutOfBoundsException(str3);
                    }
                    strNextToken = str3 + (z ? " " : str2) + stringTokenizer.nextToken();
                }
            }
        }
    }

    public int countTokens() {
        return this.tokens.size();
    }

    public boolean hasMoreTokens() {
        return countTokens() > 0;
    }

    public String nextToken() {
        return (String) this.tokens.removeFirst();
    }

    private String stripQuotes(String str) {
        String strTrim = str.trim();
        for (char c : this.quotes) {
            if (strTrim.length() >= 2 && strTrim.startsWith("" + c) && strTrim.endsWith("" + c)) {
                strTrim = strTrim.substring(1, strTrim.length() - 1).replace("\\" + c, "" + c);
            }
        }
        return strTrim;
    }

    private boolean unmatchedQuotesIn(String str) {
        Stack stack = new Stack();
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            for (char c : this.quotes) {
                if (cCharAt == c) {
                    if (stack.isEmpty()) {
                        stack.push(Character.valueOf(cCharAt));
                    } else {
                        char cCharValue = ((Character) stack.pop()).charValue();
                        if (cCharValue != cCharAt) {
                            stack.push(Character.valueOf(cCharValue));
                            stack.push(Character.valueOf(cCharAt));
                        }
                    }
                }
            }
        }
        return !stack.isEmpty();
    }
}
