package org.apache.log4j.helpers;

import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/helpers/PatternConverter.class */
public abstract class PatternConverter {
    public PatternConverter next;
    int min;
    int max;
    boolean leftAlign;
    static String[] SPACES = {" ", "  ", "    ", "        ", "                ", "                                "};

    protected abstract String convert(LoggingEvent loggingEvent);

    protected PatternConverter() {
        this.min = -1;
        this.max = Integer.MAX_VALUE;
        this.leftAlign = false;
    }

    protected PatternConverter(FormattingInfo formattingInfo) {
        this.min = -1;
        this.max = Integer.MAX_VALUE;
        this.leftAlign = false;
        this.min = formattingInfo.min;
        this.max = formattingInfo.max;
        this.leftAlign = formattingInfo.leftAlign;
    }

    public void format(StringBuffer stringBuffer, LoggingEvent loggingEvent) {
        String strConvert = convert(loggingEvent);
        if (strConvert == null) {
            if (0 < this.min) {
                spacePad(stringBuffer, this.min);
                return;
            }
            return;
        }
        int length = strConvert.length();
        if (length > this.max) {
            stringBuffer.append(strConvert.substring(length - this.max));
            return;
        }
        if (length < this.min) {
            if (this.leftAlign) {
                stringBuffer.append(strConvert);
                spacePad(stringBuffer, this.min - length);
                return;
            } else {
                spacePad(stringBuffer, this.min - length);
                stringBuffer.append(strConvert);
                return;
            }
        }
        stringBuffer.append(strConvert);
    }

    public void spacePad(StringBuffer stringBuffer, int i) {
        while (i >= 32) {
            stringBuffer.append(SPACES[5]);
            i -= 32;
        }
        for (int i2 = 4; i2 >= 0; i2--) {
            if ((i & (1 << i2)) != 0) {
                stringBuffer.append(SPACES[i2]);
            }
        }
    }
}
