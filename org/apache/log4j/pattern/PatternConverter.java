package org.apache.log4j.pattern;

/* loaded from: L-out.jar:org/apache/log4j/pattern/PatternConverter.class */
public abstract class PatternConverter {
    private final String name;
    private final String style;

    public abstract void format(Object obj, StringBuffer stringBuffer);

    protected PatternConverter(String str, String str2) {
        this.name = str;
        this.style = str2;
    }

    public final String getName() {
        return this.name;
    }

    public String getStyleClass(Object obj) {
        return this.style;
    }
}
