package jdk.nashorn.internal.runtime.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/JdkRegExp.class */
public class JdkRegExp extends RegExp {
    private Pattern pattern;

    public JdkRegExp(String str, String str2) {
        super(str, str2);
        int i = isIgnoreCase() ? 66 : 0;
        i = isMultiline() ? i | 8 : i;
        try {
            try {
                RegExpScanner regExpScannerScan = RegExpScanner.scan(str);
                if (regExpScannerScan != null) {
                    this.pattern = Pattern.compile(regExpScannerScan.getJavaPattern(), i);
                    this.groupsInNegativeLookahead = regExpScannerScan.getGroupsInNegativeLookahead();
                }
            } catch (PatternSyntaxException e) {
                Pattern.compile(str, i);
                throw e;
            }
        } catch (PatternSyntaxException e2) {
            throwParserException("syntax", e2.getMessage());
        }
    }

    @Override // jdk.nashorn.internal.runtime.regexp.RegExp
    public RegExpMatcher match(String str) {
        if (this.pattern == null) {
            return null;
        }
        return new DefaultMatcher(this, str);
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/JdkRegExp$DefaultMatcher.class */
    class DefaultMatcher implements RegExpMatcher {
        final String input;
        final Matcher defaultMatcher;
        final JdkRegExp this$0;

        DefaultMatcher(JdkRegExp jdkRegExp, String str) {
            this.this$0 = jdkRegExp;
            this.input = str;
            this.defaultMatcher = jdkRegExp.pattern.matcher(str);
        }

        @Override // jdk.nashorn.internal.runtime.regexp.RegExpMatcher
        public boolean search(int i) {
            return this.defaultMatcher.find(i);
        }

        @Override // jdk.nashorn.internal.runtime.regexp.RegExpMatcher
        public String getInput() {
            return this.input;
        }

        @Override // java.util.regex.MatchResult
        public int start() {
            return this.defaultMatcher.start();
        }

        @Override // java.util.regex.MatchResult
        public int start(int i) {
            return this.defaultMatcher.start(i);
        }

        @Override // java.util.regex.MatchResult
        public int end() {
            return this.defaultMatcher.end();
        }

        @Override // java.util.regex.MatchResult
        public int end(int i) {
            return this.defaultMatcher.end(i);
        }

        @Override // java.util.regex.MatchResult
        public String group() {
            return this.defaultMatcher.group();
        }

        @Override // java.util.regex.MatchResult
        public String group(int i) {
            return this.defaultMatcher.group(i);
        }

        @Override // java.util.regex.MatchResult
        public int groupCount() {
            return this.defaultMatcher.groupCount();
        }
    }
}
