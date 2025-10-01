package jdk.nashorn.internal.runtime.regexp;

import jdk.nashorn.internal.runtime.BitVector;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.tools.Shell;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/RegExp.class */
public abstract class RegExp {
    private final String source;
    private boolean global;
    private boolean ignoreCase;
    private boolean multiline;
    protected BitVector groupsInNegativeLookahead;

    public abstract RegExpMatcher match(String str);

    protected RegExp(String str, String str2) {
        this.source = str.length() == 0 ? "(?:)" : str;
        for (int i = 0; i < str2.length(); i++) {
            char cCharAt = str2.charAt(i);
            switch (cCharAt) {
                case Shell.IO_ERROR /* 103 */:
                    if (this.global) {
                        throwParserException("repeated.flag", "g");
                    }
                    this.global = true;
                    break;
                case 'i':
                    if (this.ignoreCase) {
                        throwParserException("repeated.flag", "i");
                    }
                    this.ignoreCase = true;
                    break;
                case 'm':
                    if (this.multiline) {
                        throwParserException("repeated.flag", "m");
                    }
                    this.multiline = true;
                    break;
                default:
                    throwParserException("unsupported.flag", Character.toString(cCharAt));
                    break;
            }
        }
    }

    public String getSource() {
        return this.source;
    }

    public void setGlobal(boolean z) {
        this.global = z;
    }

    public boolean isGlobal() {
        return this.global;
    }

    public boolean isIgnoreCase() {
        return this.ignoreCase;
    }

    public boolean isMultiline() {
        return this.multiline;
    }

    public BitVector getGroupsInNegativeLookahead() {
        return this.groupsInNegativeLookahead;
    }

    protected static void throwParserException(String str, String str2) {
        throw new ParserException(ECMAErrors.getMessage("parser.error.regex." + str, new String[]{str2}));
    }
}
