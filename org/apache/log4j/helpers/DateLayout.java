package org.apache.log4j.helpers;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/helpers/DateLayout.class */
public abstract class DateLayout extends Layout {
    public static final String NULL_DATE_FORMAT = "NULL";
    public static final String RELATIVE_TIME_DATE_FORMAT = "RELATIVE";
    public static final String DATE_FORMAT_OPTION = "DateFormat";
    public static final String TIMEZONE_OPTION = "TimeZone";
    private String timeZoneID;
    private String dateFormatOption;
    protected DateFormat dateFormat;
    protected FieldPosition pos = new FieldPosition(0);
    protected Date date = new Date();

    public void setOption(String str, String str2) {
        if (str.equalsIgnoreCase(DATE_FORMAT_OPTION)) {
            this.dateFormatOption = str2.toUpperCase();
        } else if (str.equalsIgnoreCase(TIMEZONE_OPTION)) {
            this.timeZoneID = str2;
        }
    }

    public void setDateFormat(String str) {
        if (str != null) {
            this.dateFormatOption = str;
        }
        setDateFormat(this.dateFormatOption, TimeZone.getDefault());
    }

    public String getDateFormat() {
        return this.dateFormatOption;
    }

    public void setTimeZone(String str) {
        this.timeZoneID = str;
    }

    public String getTimeZone() {
        return this.timeZoneID;
    }

    @Override // org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
        setDateFormat(this.dateFormatOption);
        if (this.timeZoneID != null && this.dateFormat != null) {
            this.dateFormat.setTimeZone(TimeZone.getTimeZone(this.timeZoneID));
        }
    }

    public void dateFormat(StringBuffer stringBuffer, LoggingEvent loggingEvent) {
        if (this.dateFormat != null) {
            this.date.setTime(loggingEvent.timeStamp);
            this.dateFormat.format(this.date, stringBuffer, this.pos);
            stringBuffer.append(' ');
        }
    }

    public void setDateFormat(DateFormat dateFormat, TimeZone timeZone) {
        this.dateFormat = dateFormat;
        this.dateFormat.setTimeZone(timeZone);
    }

    public void setDateFormat(String str, TimeZone timeZone) {
        if (str == null) {
            this.dateFormat = null;
            return;
        }
        if (str.equalsIgnoreCase(NULL_DATE_FORMAT)) {
            this.dateFormat = null;
            return;
        }
        if (str.equalsIgnoreCase(RELATIVE_TIME_DATE_FORMAT)) {
            this.dateFormat = new RelativeTimeDateFormat();
            return;
        }
        if (str.equalsIgnoreCase(AbsoluteTimeDateFormat.ABS_TIME_DATE_FORMAT)) {
            this.dateFormat = new AbsoluteTimeDateFormat(timeZone);
            return;
        }
        if (str.equalsIgnoreCase(AbsoluteTimeDateFormat.DATE_AND_TIME_DATE_FORMAT)) {
            this.dateFormat = new DateTimeDateFormat(timeZone);
        } else if (str.equalsIgnoreCase(AbsoluteTimeDateFormat.ISO8601_DATE_FORMAT)) {
            this.dateFormat = new ISO8601DateFormat(timeZone);
        } else {
            this.dateFormat = new SimpleDateFormat(str);
            this.dateFormat.setTimeZone(timeZone);
        }
    }
}
