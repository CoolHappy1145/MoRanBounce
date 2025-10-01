package org.apache.log4j.varia;

import org.apache.log4j.Level;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/varia/LevelMatchFilter.class */
public class LevelMatchFilter extends Filter {
    boolean acceptOnMatch = true;
    Level levelToMatch;

    public void setLevelToMatch(String str) {
        this.levelToMatch = OptionConverter.toLevel(str, null);
    }

    public String getLevelToMatch() {
        if (this.levelToMatch == null) {
            return null;
        }
        return this.levelToMatch.toString();
    }

    public void setAcceptOnMatch(boolean z) {
        this.acceptOnMatch = z;
    }

    public boolean getAcceptOnMatch() {
        return this.acceptOnMatch;
    }

    @Override // org.apache.log4j.spi.Filter
    public int decide(LoggingEvent loggingEvent) {
        if (this.levelToMatch == null) {
            return 0;
        }
        boolean z = false;
        if (this.levelToMatch.equals(loggingEvent.getLevel())) {
            z = true;
        }
        if (z) {
            if (this.acceptOnMatch) {
                return 1;
            }
            return -1;
        }
        return 0;
    }
}
