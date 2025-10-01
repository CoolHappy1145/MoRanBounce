package org.apache.log4j.pattern;

import java.util.ArrayList;
import java.util.List;

/* loaded from: L-out.jar:org/apache/log4j/pattern/NameAbbreviator.class */
public abstract class NameAbbreviator {
    private static final NameAbbreviator DEFAULT = new NOPAbbreviator();

    /* loaded from: L-out.jar:org/apache/log4j/pattern/NameAbbreviator$NOPAbbreviator.class */
    private static class NOPAbbreviator extends NameAbbreviator {
    }

    public abstract void abbreviate(int i, StringBuffer stringBuffer);

    public static NameAbbreviator getAbbreviator(String str) throws NumberFormatException {
        int iCharAt;
        if (str.length() > 0) {
            String strTrim = str.trim();
            if (strTrim.length() == 0) {
                return DEFAULT;
            }
            int i = 0;
            if (strTrim.length() > 0) {
                if (strTrim.charAt(0) == '-') {
                    i = 0 + 1;
                }
                while (i < strTrim.length() && strTrim.charAt(i) >= '0' && strTrim.charAt(i) <= '9') {
                    i++;
                }
            }
            if (i == strTrim.length()) {
                int i2 = Integer.parseInt(strTrim);
                if (i2 >= 0) {
                    return new MaxElementAbbreviator(i2);
                }
                return new DropElementAbbreviator(-i2);
            }
            ArrayList arrayList = new ArrayList(5);
            int i3 = 0;
            while (i3 < strTrim.length() && i3 >= 0) {
                int i4 = i3;
                if (strTrim.charAt(i3) == '*') {
                    iCharAt = Integer.MAX_VALUE;
                    i4++;
                } else if (strTrim.charAt(i3) >= '0' && strTrim.charAt(i3) <= '9') {
                    iCharAt = strTrim.charAt(i3) - '0';
                    i4++;
                } else {
                    iCharAt = 0;
                }
                char cCharAt = 0;
                if (i4 < strTrim.length()) {
                    cCharAt = strTrim.charAt(i4);
                    if (cCharAt == '.') {
                        cCharAt = 0;
                    }
                }
                arrayList.add(new PatternAbbreviatorFragment(iCharAt, cCharAt));
                int iIndexOf = strTrim.indexOf(".", i3);
                if (iIndexOf == -1) {
                    break;
                }
                i3 = iIndexOf + 1;
            }
            return new PatternAbbreviator(arrayList);
        }
        return DEFAULT;
    }

    public static NameAbbreviator getDefaultAbbreviator() {
        return DEFAULT;
    }

    /* loaded from: L-out.jar:org/apache/log4j/pattern/NameAbbreviator$MaxElementAbbreviator.class */
    private static class MaxElementAbbreviator extends NameAbbreviator {
        private final int count;

        public MaxElementAbbreviator(int i) {
            this.count = i;
        }

        @Override // org.apache.log4j.pattern.NameAbbreviator
        public void abbreviate(int i, StringBuffer stringBuffer) {
            int length = stringBuffer.length() - 1;
            String string = stringBuffer.toString();
            for (int i2 = this.count; i2 > 0; i2--) {
                length = string.lastIndexOf(".", length - 1);
                if (length == -1 || length < i) {
                    return;
                }
            }
            stringBuffer.delete(i, length + 1);
        }
    }

    /* loaded from: L-out.jar:org/apache/log4j/pattern/NameAbbreviator$DropElementAbbreviator.class */
    private static class DropElementAbbreviator extends NameAbbreviator {
        private final int count;

        public DropElementAbbreviator(int i) {
            this.count = i;
        }

        @Override // org.apache.log4j.pattern.NameAbbreviator
        public void abbreviate(int i, StringBuffer stringBuffer) {
            int i2 = this.count;
            int iIndexOf = stringBuffer.indexOf(".", i);
            while (true) {
                int i3 = iIndexOf;
                if (i3 != -1) {
                    i2--;
                    if (i2 != 0) {
                        iIndexOf = stringBuffer.indexOf(".", i3 + 1);
                    } else {
                        stringBuffer.delete(i, i3 + 1);
                        return;
                    }
                } else {
                    return;
                }
            }
        }
    }

    /* loaded from: L-out.jar:org/apache/log4j/pattern/NameAbbreviator$PatternAbbreviatorFragment.class */
    private static class PatternAbbreviatorFragment {
        private final int charCount;
        private final char ellipsis;

        public PatternAbbreviatorFragment(int i, char c) {
            this.charCount = i;
            this.ellipsis = c;
        }

        public int abbreviate(StringBuffer stringBuffer, int i) {
            int iIndexOf = stringBuffer.toString().indexOf(".", i);
            if (iIndexOf != -1) {
                if (iIndexOf - i > this.charCount) {
                    stringBuffer.delete(i + this.charCount, iIndexOf);
                    iIndexOf = i + this.charCount;
                    if (this.ellipsis != 0) {
                        stringBuffer.insert(iIndexOf, this.ellipsis);
                        iIndexOf++;
                    }
                }
                iIndexOf++;
            }
            return iIndexOf;
        }
    }

    /* loaded from: L-out.jar:org/apache/log4j/pattern/NameAbbreviator$PatternAbbreviator.class */
    private static class PatternAbbreviator extends NameAbbreviator {
        private final PatternAbbreviatorFragment[] fragments;

        public PatternAbbreviator(List list) {
            if (list.size() == 0) {
                throw new IllegalArgumentException("fragments must have at least one element");
            }
            this.fragments = new PatternAbbreviatorFragment[list.size()];
            list.toArray(this.fragments);
        }

        @Override // org.apache.log4j.pattern.NameAbbreviator
        public void abbreviate(int i, StringBuffer stringBuffer) {
            int iAbbreviate = i;
            for (int i2 = 0; i2 < this.fragments.length - 1 && iAbbreviate < stringBuffer.length(); i2++) {
                iAbbreviate = this.fragments[i2].abbreviate(stringBuffer, iAbbreviate);
            }
            PatternAbbreviatorFragment patternAbbreviatorFragment = this.fragments[this.fragments.length - 1];
            while (iAbbreviate < stringBuffer.length() && iAbbreviate >= 0) {
                iAbbreviate = patternAbbreviatorFragment.abbreviate(stringBuffer, iAbbreviate);
            }
        }
    }
}
