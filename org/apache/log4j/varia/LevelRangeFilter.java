package org.apache.log4j.varia;

import org.apache.log4j.Level;
import org.apache.log4j.spi.Filter;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/varia/LevelRangeFilter.class */
public class LevelRangeFilter extends Filter {
    boolean acceptOnMatch = false;
    Level levelMin;
    Level levelMax;

    @Override // org.apache.log4j.spi.Filter
    public int decide(LoggingEvent loggingEvent) {
        if (this.levelMin != null && !loggingEvent.getLevel().isGreaterOrEqual(this.levelMin)) {
            return -1;
        }
        if (this.levelMax != null && loggingEvent.getLevel().toInt() > this.levelMax.toInt()) {
            return -1;
        }
        if (this.acceptOnMatch) {
            return 1;
        }
        return 0;
    }

    public Level getLevelMax() {
        return this.levelMax;
    }

    public Level getLevelMin() {
        return this.levelMin;
    }

    public boolean getAcceptOnMatch() {
        return this.acceptOnMatch;
    }

    public void setLevelMax(Level level) {
        this.levelMax = level;
    }

    public void setLevelMin(Level level) {
        this.levelMin = level;
    }

    public void setAcceptOnMatch(boolean z) {
        this.acceptOnMatch = z;
    }
}
