package org.apache.log4j.pattern;

/* loaded from: L-out.jar:org/apache/log4j/pattern/FormattingInfo.class */
public final class FormattingInfo {
    private static final char[] SPACES = {' ', ' ', ' ', ' ', ' ', ' ', ' ', ' '};
    private static final FormattingInfo DEFAULT = new FormattingInfo(false, 0, Integer.MAX_VALUE);
    private final int minLength;
    private final int maxLength;
    private final boolean leftAlign;

    public FormattingInfo(boolean z, int i, int i2) {
        this.leftAlign = z;
        this.minLength = i;
        this.maxLength = i2;
    }

    public static FormattingInfo getDefault() {
        return DEFAULT;
    }

    public boolean isLeftAligned() {
        return this.leftAlign;
    }

    public int getMinLength() {
        return this.minLength;
    }

    public int getMaxLength() {
        return this.maxLength;
    }

    public void format(int i, StringBuffer stringBuffer) {
        int length = stringBuffer.length() - i;
        if (length > this.maxLength) {
            stringBuffer.delete(i, stringBuffer.length() - this.maxLength);
            return;
        }
        if (length < this.minLength) {
            if (this.leftAlign) {
                int length2 = stringBuffer.length();
                stringBuffer.setLength(i + this.minLength);
                for (int i2 = length2; i2 < stringBuffer.length(); i2++) {
                    stringBuffer.setCharAt(i2, ' ');
                }
                return;
            }
            int i3 = this.minLength - length;
            while (i3 > 8) {
                stringBuffer.insert(i, SPACES);
                i3 -= 8;
            }
            stringBuffer.insert(i, SPACES, 0, i3);
        }
    }
}
