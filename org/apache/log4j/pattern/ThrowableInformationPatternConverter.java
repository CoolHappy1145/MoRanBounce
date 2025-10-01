package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.ThrowableInformation;

/* loaded from: L-out.jar:org/apache/log4j/pattern/ThrowableInformationPatternConverter.class */
public class ThrowableInformationPatternConverter extends LoggingEventPatternConverter {
    private int maxLines;

    private ThrowableInformationPatternConverter(String[] strArr) {
        super("Throwable", "throwable");
        this.maxLines = Integer.MAX_VALUE;
        if (strArr != null && strArr.length > 0) {
            if ("none".equals(strArr[0])) {
                this.maxLines = 0;
            } else if ("short".equals(strArr[0])) {
                this.maxLines = 1;
            } else {
                try {
                    this.maxLines = Integer.parseInt(strArr[0]);
                } catch (NumberFormatException unused) {
                }
            }
        }
    }

    public static ThrowableInformationPatternConverter newInstance(String[] strArr) {
        return new ThrowableInformationPatternConverter(strArr);
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent loggingEvent, StringBuffer stringBuffer) {
        ThrowableInformation throwableInformation;
        if (this.maxLines != 0 && (throwableInformation = loggingEvent.getThrowableInformation()) != null) {
            String[] throwableStrRep = throwableInformation.getThrowableStrRep();
            int length = throwableStrRep.length;
            if (this.maxLines < 0) {
                length += this.maxLines;
            } else if (length > this.maxLines) {
                length = this.maxLines;
            }
            for (int i = 0; i < length; i++) {
                stringBuffer.append(throwableStrRep[i]).append("\n");
            }
        }
    }
}
