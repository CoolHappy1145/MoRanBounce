package org.apache.log4j.pattern;

/* loaded from: L-out.jar:org/apache/log4j/pattern/NamePatternConverter.class */
public abstract class NamePatternConverter extends LoggingEventPatternConverter {
    private final NameAbbreviator abbreviator;

    protected NamePatternConverter(String str, String str2, String[] strArr) {
        super(str, str2);
        if (strArr != null && strArr.length > 0) {
            this.abbreviator = NameAbbreviator.getAbbreviator(strArr[0]);
        } else {
            this.abbreviator = NameAbbreviator.getDefaultAbbreviator();
        }
    }

    protected final void abbreviate(int i, StringBuffer stringBuffer) {
        this.abbreviator.abbreviate(i, stringBuffer);
    }
}
