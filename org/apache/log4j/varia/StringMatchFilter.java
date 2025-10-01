package org.apache.log4j.varia;

import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/varia/StringMatchFilter.class */
public class StringMatchFilter extends Filter {
    public static final String STRING_TO_MATCH_OPTION = "StringToMatch";
    public static final String ACCEPT_ON_MATCH_OPTION = "AcceptOnMatch";
    boolean acceptOnMatch = true;
    String stringToMatch;

    public void setOption(String str, String str2) {
        if (str.equalsIgnoreCase(STRING_TO_MATCH_OPTION)) {
            this.stringToMatch = str2;
        } else if (str.equalsIgnoreCase(ACCEPT_ON_MATCH_OPTION)) {
            this.acceptOnMatch = OptionConverter.toBoolean(str2, this.acceptOnMatch);
        }
    }

    public void setStringToMatch(String str) {
        this.stringToMatch = str;
    }

    public String getStringToMatch() {
        return this.stringToMatch;
    }

    public void setAcceptOnMatch(boolean z) {
        this.acceptOnMatch = z;
    }

    public boolean getAcceptOnMatch() {
        return this.acceptOnMatch;
    }

    @Override // org.apache.log4j.spi.Filter
    public int decide(LoggingEvent loggingEvent) {
        String renderedMessage = loggingEvent.getRenderedMessage();
        if (renderedMessage == null || this.stringToMatch == null || renderedMessage.indexOf(this.stringToMatch) == -1) {
            return 0;
        }
        if (this.acceptOnMatch) {
            return 1;
        }
        return -1;
    }
}
