package org.apache.log4j.pattern;

import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/pattern/RelativeTimePatternConverter.class */
public class RelativeTimePatternConverter extends LoggingEventPatternConverter {
    private CachedTimestamp lastTimestamp;

    public RelativeTimePatternConverter() {
        super("Time", "time");
        this.lastTimestamp = new CachedTimestamp(0L, "");
    }

    public static RelativeTimePatternConverter newInstance(String[] strArr) {
        return new RelativeTimePatternConverter();
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent loggingEvent, StringBuffer stringBuffer) {
        long j = loggingEvent.timeStamp;
        if (!this.lastTimestamp.format(j, stringBuffer)) {
            String string = Long.toString(j - LoggingEvent.getStartTime());
            stringBuffer.append(string);
            this.lastTimestamp = new CachedTimestamp(j, string);
        }
    }

    /* loaded from: L-out.jar:org/apache/log4j/pattern/RelativeTimePatternConverter$CachedTimestamp.class */
    private static final class CachedTimestamp {
        private final long timestamp;
        private final String formatted;

        public CachedTimestamp(long j, String str) {
            this.timestamp = j;
            this.formatted = str;
        }

        public boolean format(long j, StringBuffer stringBuffer) {
            if (j == this.timestamp) {
                stringBuffer.append(this.formatted);
                return true;
            }
            return false;
        }
    }
}
