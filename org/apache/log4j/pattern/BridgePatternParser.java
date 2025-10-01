package org.apache.log4j.pattern;

/* loaded from: L-out.jar:org/apache/log4j/pattern/BridgePatternParser.class */
public final class BridgePatternParser extends org.apache.log4j.helpers.PatternParser {
    public BridgePatternParser(String str) {
        super(str);
    }

    @Override // org.apache.log4j.helpers.PatternParser
    public org.apache.log4j.helpers.PatternConverter parse() {
        return new BridgePatternConverter(this.pattern);
    }
}
