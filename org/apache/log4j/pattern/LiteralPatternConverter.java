package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/pattern/LiteralPatternConverter.class */
public final class LiteralPatternConverter extends LoggingEventPatternConverter {
    private final String literal;

    public LiteralPatternConverter(String str) {
        super("Literal", "literal");
        this.literal = str;
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent loggingEvent, StringBuffer stringBuffer) {
        stringBuffer.append(this.literal);
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter, org.apache.log4j.pattern.PatternConverter
    public void format(Object obj, StringBuffer stringBuffer) {
        stringBuffer.append(this.literal);
    }
}
