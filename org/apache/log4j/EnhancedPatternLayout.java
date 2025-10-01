package org.apache.log4j;

import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.helpers.PatternConverter;
import org.apache.log4j.helpers.PatternParser;
import org.apache.log4j.pattern.BridgePatternConverter;
import org.apache.log4j.pattern.BridgePatternParser;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/EnhancedPatternLayout.class */
public class EnhancedPatternLayout extends Layout {
    public static final String DEFAULT_CONVERSION_PATTERN = "%m%n";
    public static final String TTCC_CONVERSION_PATTERN = "%r [%t] %p %c %x - %m%n";
    protected final int BUF_SIZE = 256;
    protected final int MAX_CAPACITY = 1024;
    public static final String PATTERN_RULE_REGISTRY = "PATTERN_RULE_REGISTRY";
    private PatternConverter head;
    private String conversionPattern;
    private boolean handlesExceptions;

    public EnhancedPatternLayout() {
        this("%m%n");
    }

    public EnhancedPatternLayout(String str) {
        this.BUF_SIZE = 256;
        this.MAX_CAPACITY = 1024;
        this.conversionPattern = str;
        this.head = createPatternParser(str == null ? "%m%n" : str).parse();
        if (this.head instanceof BridgePatternConverter) {
            this.handlesExceptions = !((BridgePatternConverter) this.head).ignoresThrowable();
        } else {
            this.handlesExceptions = false;
        }
    }

    public void setConversionPattern(String str) {
        this.conversionPattern = OptionConverter.convertSpecialChars(str);
        this.head = createPatternParser(this.conversionPattern).parse();
        if (this.head instanceof BridgePatternConverter) {
            this.handlesExceptions = !((BridgePatternConverter) this.head).ignoresThrowable();
        } else {
            this.handlesExceptions = false;
        }
    }

    public String getConversionPattern() {
        return this.conversionPattern;
    }

    protected PatternParser createPatternParser(String str) {
        return new BridgePatternParser(str);
    }

    @Override // org.apache.log4j.Layout
    public String format(LoggingEvent loggingEvent) {
        StringBuffer stringBuffer = new StringBuffer();
        PatternConverter patternConverter = this.head;
        while (true) {
            PatternConverter patternConverter2 = patternConverter;
            if (patternConverter2 != null) {
                patternConverter2.format(stringBuffer, loggingEvent);
                patternConverter = patternConverter2.next;
            } else {
                return stringBuffer.toString();
            }
        }
    }

    @Override // org.apache.log4j.Layout
    public boolean ignoresThrowable() {
        return !this.handlesExceptions;
    }
}
