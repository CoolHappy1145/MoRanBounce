package jdk.nashorn.internal.runtime.regexp;

import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import jdk.nashorn.internal.runtime.regexp.joni.Matcher;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;
import jdk.nashorn.internal.runtime.regexp.joni.Region;
import jdk.nashorn.internal.runtime.regexp.joni.Syntax;
import jdk.nashorn.internal.runtime.regexp.joni.exception.JOniException;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/JoniRegExp.class */
public class JoniRegExp extends RegExp {
    private Regex regex;

    public JoniRegExp(String str, String str2) {
        super(str, str2);
        int i = isIgnoreCase() ? 9 : 8;
        i = isMultiline() ? (i & (-9)) | 64 : i;
        try {
            try {
                RegExpScanner regExpScannerScan = RegExpScanner.scan(str);
                if (regExpScannerScan != null) {
                    char[] charArray = regExpScannerScan.getJavaPattern().toCharArray();
                    this.regex = new Regex(charArray, 0, charArray.length, i, Syntax.JAVASCRIPT);
                    this.groupsInNegativeLookahead = regExpScannerScan.getGroupsInNegativeLookahead();
                }
            } catch (PatternSyntaxException e) {
                Pattern.compile(str, 0);
                throw e;
            }
        } catch (PatternSyntaxException | JOniException e2) {
            throwParserException("syntax", e2.getMessage());
        }
    }

    @Override // jdk.nashorn.internal.runtime.regexp.RegExp
    public RegExpMatcher match(String str) {
        if (this.regex == null) {
            return null;
        }
        return new JoniMatcher(this, str);
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/JoniRegExp$Factory.class */
    public static class Factory extends RegExpFactory {
        @Override // jdk.nashorn.internal.runtime.regexp.RegExpFactory
        public RegExp compile(String str, String str2) {
            return new JoniRegExp(str, str2);
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/JoniRegExp$JoniMatcher.class */
    class JoniMatcher implements RegExpMatcher {
        final String input;
        final Matcher joniMatcher;
        final JoniRegExp this$0;

        JoniMatcher(JoniRegExp joniRegExp, String str) {
            this.this$0 = joniRegExp;
            this.input = str;
            this.joniMatcher = joniRegExp.regex.matcher(str.toCharArray());
        }

        @Override // jdk.nashorn.internal.runtime.regexp.RegExpMatcher
        public boolean search(int i) {
            return this.joniMatcher.search(i, this.input.length(), 0) > -1;
        }

        @Override // jdk.nashorn.internal.runtime.regexp.RegExpMatcher
        public String getInput() {
            return this.input;
        }

        @Override // java.util.regex.MatchResult
        public int start() {
            return this.joniMatcher.getBegin();
        }

        @Override // java.util.regex.MatchResult
        public int start(int i) {
            return i == 0 ? start() : this.joniMatcher.getRegion().beg[i];
        }

        @Override // java.util.regex.MatchResult
        public int end() {
            return this.joniMatcher.getEnd();
        }

        @Override // java.util.regex.MatchResult
        public int end(int i) {
            return i == 0 ? end() : this.joniMatcher.getRegion().end[i];
        }

        @Override // java.util.regex.MatchResult
        public String group() {
            return this.input.substring(this.joniMatcher.getBegin(), this.joniMatcher.getEnd());
        }

        @Override // java.util.regex.MatchResult
        public String group(int i) {
            if (i == 0) {
                return group();
            }
            Region region = this.joniMatcher.getRegion();
            return this.input.substring(region.beg[i], region.end[i]);
        }

        @Override // java.util.regex.MatchResult
        public int groupCount() {
            Region region = this.joniMatcher.getRegion();
            if (region == null) {
                return 0;
            }
            return region.numRegs - 1;
        }
    }
}
