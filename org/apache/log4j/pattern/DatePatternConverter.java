package org.apache.log4j.pattern;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/pattern/DatePatternConverter.class */
public final class DatePatternConverter extends LoggingEventPatternConverter {
    private static final String ABSOLUTE_FORMAT = "ABSOLUTE";
    private static final String ABSOLUTE_TIME_PATTERN = "HH:mm:ss,SSS";
    private static final String DATE_AND_TIME_FORMAT = "DATE";
    private static final String DATE_AND_TIME_PATTERN = "dd MMM yyyy HH:mm:ss,SSS";
    private static final String ISO8601_FORMAT = "ISO8601";
    private static final String ISO8601_PATTERN = "yyyy-MM-dd HH:mm:ss,SSS";

    /* renamed from: df */
    private final CachedDateFormat f200df;

    /* loaded from: L-out.jar:org/apache/log4j/pattern/DatePatternConverter$DefaultZoneDateFormat.class */
    private static class DefaultZoneDateFormat extends DateFormat {
        private static final long serialVersionUID = 1;
        private final DateFormat dateFormat;

        public DefaultZoneDateFormat(DateFormat dateFormat) {
            this.dateFormat = dateFormat;
        }

        @Override // java.text.DateFormat
        public StringBuffer format(Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
            this.dateFormat.setTimeZone(TimeZone.getDefault());
            return this.dateFormat.format(date, stringBuffer, fieldPosition);
        }

        @Override // java.text.DateFormat
        public Date parse(String str, ParsePosition parsePosition) {
            this.dateFormat.setTimeZone(TimeZone.getDefault());
            return this.dateFormat.parse(str, parsePosition);
        }
    }

    private DatePatternConverter(String[] strArr) {
        String str;
        String str2;
        DateFormat simpleDateFormat;
        super("Date", "date");
        if (strArr == null || strArr.length == 0) {
            str = null;
        } else {
            str = strArr[0];
        }
        if (str == null || str.equalsIgnoreCase("ISO8601")) {
            str2 = ISO8601_PATTERN;
        } else if (str.equalsIgnoreCase("ABSOLUTE")) {
            str2 = ABSOLUTE_TIME_PATTERN;
        } else if (str.equalsIgnoreCase("DATE")) {
            str2 = DATE_AND_TIME_PATTERN;
        } else {
            str2 = str;
        }
        int maximumCacheValidity = 1000;
        try {
            simpleDateFormat = new SimpleDateFormat(str2);
            maximumCacheValidity = CachedDateFormat.getMaximumCacheValidity(str2);
        } catch (IllegalArgumentException e) {
            LogLog.warn(new StringBuffer().append("Could not instantiate SimpleDateFormat with pattern ").append(str).toString(), e);
            simpleDateFormat = new SimpleDateFormat(ISO8601_PATTERN);
        }
        if (strArr != null && strArr.length > 1) {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone(strArr[1]));
        } else {
            simpleDateFormat = new DefaultZoneDateFormat(simpleDateFormat);
        }
        this.f200df = new CachedDateFormat(simpleDateFormat, maximumCacheValidity);
    }

    public static DatePatternConverter newInstance(String[] strArr) {
        return new DatePatternConverter(strArr);
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter
    public void format(LoggingEvent loggingEvent, StringBuffer stringBuffer) {
        synchronized (this) {
            this.f200df.format(loggingEvent.timeStamp, stringBuffer);
        }
    }

    @Override // org.apache.log4j.pattern.LoggingEventPatternConverter, org.apache.log4j.pattern.PatternConverter
    public void format(Object obj, StringBuffer stringBuffer) {
        if (obj instanceof Date) {
            format((Date) obj, stringBuffer);
        }
        super.format(obj, stringBuffer);
    }

    public void format(Date date, StringBuffer stringBuffer) {
        synchronized (this) {
            this.f200df.format(date.getTime(), stringBuffer);
        }
    }
}
