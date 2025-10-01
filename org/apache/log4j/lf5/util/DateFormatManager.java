package org.apache.log4j.lf5.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: L-out.jar:org/apache/log4j/lf5/util/DateFormatManager.class */
public class DateFormatManager {
    private TimeZone _timeZone;
    private Locale _locale;
    private String _pattern;
    private DateFormat _dateFormat;

    public DateFormatManager() {
        this._timeZone = null;
        this._locale = null;
        this._pattern = null;
        this._dateFormat = null;
        configure();
    }

    public DateFormatManager(TimeZone timeZone) {
        this._timeZone = null;
        this._locale = null;
        this._pattern = null;
        this._dateFormat = null;
        this._timeZone = timeZone;
        configure();
    }

    public DateFormatManager(Locale locale) {
        this._timeZone = null;
        this._locale = null;
        this._pattern = null;
        this._dateFormat = null;
        this._locale = locale;
        configure();
    }

    public DateFormatManager(String str) {
        this._timeZone = null;
        this._locale = null;
        this._pattern = null;
        this._dateFormat = null;
        this._pattern = str;
        configure();
    }

    public DateFormatManager(TimeZone timeZone, Locale locale) {
        this._timeZone = null;
        this._locale = null;
        this._pattern = null;
        this._dateFormat = null;
        this._timeZone = timeZone;
        this._locale = locale;
        configure();
    }

    public DateFormatManager(TimeZone timeZone, String str) {
        this._timeZone = null;
        this._locale = null;
        this._pattern = null;
        this._dateFormat = null;
        this._timeZone = timeZone;
        this._pattern = str;
        configure();
    }

    public DateFormatManager(Locale locale, String str) {
        this._timeZone = null;
        this._locale = null;
        this._pattern = null;
        this._dateFormat = null;
        this._locale = locale;
        this._pattern = str;
        configure();
    }

    public DateFormatManager(TimeZone timeZone, Locale locale, String str) {
        this._timeZone = null;
        this._locale = null;
        this._pattern = null;
        this._dateFormat = null;
        this._timeZone = timeZone;
        this._locale = locale;
        this._pattern = str;
        configure();
    }

    public TimeZone getTimeZone() {
        if (this._timeZone == null) {
            return TimeZone.getDefault();
        }
        return this._timeZone;
    }

    public void setTimeZone(TimeZone timeZone) {
        this._timeZone = timeZone;
        configure();
    }

    public Locale getLocale() {
        if (this._locale == null) {
            return Locale.getDefault();
        }
        return this._locale;
    }

    public void setLocale(Locale locale) {
        this._locale = locale;
        configure();
    }

    public String getPattern() {
        return this._pattern;
    }

    public void setPattern(String str) {
        this._pattern = str;
        configure();
    }

    public String getOutputFormat() {
        return this._pattern;
    }

    public void setOutputFormat(String str) {
        this._pattern = str;
        configure();
    }

    public DateFormat getDateFormatInstance() {
        return this._dateFormat;
    }

    public void setDateFormatInstance(DateFormat dateFormat) {
        this._dateFormat = dateFormat;
    }

    public String format(Date date) {
        return getDateFormatInstance().format(date);
    }

    public String format(Date date, String str) {
        DateFormat dateFormatInstance = getDateFormatInstance();
        if (dateFormatInstance instanceof SimpleDateFormat) {
            dateFormatInstance = (SimpleDateFormat) dateFormatInstance.clone();
            ((SimpleDateFormat) dateFormatInstance).applyPattern(str);
        }
        return dateFormatInstance.format(date);
    }

    public Date parse(String str) {
        return getDateFormatInstance().parse(str);
    }

    public Date parse(String str, String str2) {
        DateFormat dateFormatInstance = getDateFormatInstance();
        if (dateFormatInstance instanceof SimpleDateFormat) {
            dateFormatInstance = (SimpleDateFormat) dateFormatInstance.clone();
            ((SimpleDateFormat) dateFormatInstance).applyPattern(str2);
        }
        return dateFormatInstance.parse(str);
    }

    private void configure() {
        this._dateFormat = SimpleDateFormat.getDateTimeInstance(0, 0, getLocale());
        this._dateFormat.setTimeZone(getTimeZone());
        if (this._pattern != null) {
            ((SimpleDateFormat) this._dateFormat).applyPattern(this._pattern);
        }
    }
}
