package org.apache.log4j.pattern;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Date;
import java.util.TimeZone;

/* loaded from: L-out.jar:org/apache/log4j/pattern/CachedDateFormat.class */
public final class CachedDateFormat extends DateFormat {
    private static final long serialVersionUID = 1;
    public static final int NO_MILLISECONDS = -2;
    private static final String DIGITS = "0123456789";
    public static final int UNRECOGNIZED_MILLISECONDS = -1;
    private static final int MAGIC1 = 654;
    private static final String MAGICSTRING1 = "654";
    private static final int MAGIC2 = 987;
    private static final String MAGICSTRING2 = "987";
    private static final String ZERO_STRING = "000";
    private final DateFormat formatter;
    private int millisecondStart;
    private long slotBegin;
    private final int expiration;
    private long previousTime;
    private StringBuffer cache = new StringBuffer(50);
    private final Date tmpDate = new Date(0);

    public CachedDateFormat(DateFormat dateFormat, int i) {
        if (dateFormat == null) {
            throw new IllegalArgumentException("dateFormat cannot be null");
        }
        if (i < 0) {
            throw new IllegalArgumentException("expiration must be non-negative");
        }
        this.formatter = dateFormat;
        this.expiration = i;
        this.millisecondStart = 0;
        this.previousTime = Long.MIN_VALUE;
        this.slotBegin = Long.MIN_VALUE;
    }

    public static int findMillisecondStart(long j, String str, DateFormat dateFormat) {
        long j2 = (j / 1000) * 1000;
        if (j2 > j) {
            j2 -= 1000;
        }
        int i = (int) (j - j2);
        int i2 = MAGIC1;
        String str2 = MAGICSTRING1;
        if (i == MAGIC1) {
            i2 = MAGIC2;
            str2 = MAGICSTRING2;
        }
        String str3 = dateFormat.format(new Date(j2 + i2));
        if (str3.length() != str.length()) {
            return -1;
        }
        for (int i3 = 0; i3 < str.length(); i3++) {
            if (str.charAt(i3) != str3.charAt(i3)) {
                StringBuffer stringBuffer = new StringBuffer("ABC");
                millisecondFormat(i, stringBuffer, 0);
                String str4 = dateFormat.format(new Date(j2));
                if (str4.length() == str.length() && str2.regionMatches(0, str3, i3, str2.length()) && stringBuffer.toString().regionMatches(0, str, i3, str2.length()) && ZERO_STRING.regionMatches(0, str4, i3, 3)) {
                    return i3;
                }
                return -1;
            }
        }
        return -2;
    }

    @Override // java.text.DateFormat
    public StringBuffer format(Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        format(date.getTime(), stringBuffer);
        return stringBuffer;
    }

    public StringBuffer format(long j, StringBuffer stringBuffer) {
        if (j == this.previousTime) {
            stringBuffer.append(this.cache);
            return stringBuffer;
        }
        if (this.millisecondStart != -1 && j < this.slotBegin + this.expiration && j >= this.slotBegin && j < this.slotBegin + 1000) {
            if (this.millisecondStart >= 0) {
                millisecondFormat((int) (j - this.slotBegin), this.cache, this.millisecondStart);
            }
            this.previousTime = j;
            stringBuffer.append(this.cache);
            return stringBuffer;
        }
        this.cache.setLength(0);
        this.tmpDate.setTime(j);
        this.cache.append(this.formatter.format(this.tmpDate));
        stringBuffer.append(this.cache);
        this.previousTime = j;
        this.slotBegin = (this.previousTime / 1000) * 1000;
        if (this.slotBegin > this.previousTime) {
            this.slotBegin -= 1000;
        }
        if (this.millisecondStart >= 0) {
            this.millisecondStart = findMillisecondStart(j, this.cache.toString(), this.formatter);
        }
        return stringBuffer;
    }

    private static void millisecondFormat(int i, StringBuffer stringBuffer, int i2) {
        stringBuffer.setCharAt(i2, DIGITS.charAt(i / 100));
        stringBuffer.setCharAt(i2 + 1, DIGITS.charAt((i / 10) % 10));
        stringBuffer.setCharAt(i2 + 2, DIGITS.charAt(i % 10));
    }

    @Override // java.text.DateFormat
    public void setTimeZone(TimeZone timeZone) {
        this.formatter.setTimeZone(timeZone);
        this.previousTime = Long.MIN_VALUE;
        this.slotBegin = Long.MIN_VALUE;
    }

    @Override // java.text.DateFormat
    public Date parse(String str, ParsePosition parsePosition) {
        return this.formatter.parse(str, parsePosition);
    }

    @Override // java.text.DateFormat
    public NumberFormat getNumberFormat() {
        return this.formatter.getNumberFormat();
    }

    public static int getMaximumCacheValidity(String str) {
        int iIndexOf = str.indexOf(83);
        if (iIndexOf >= 0 && iIndexOf != str.lastIndexOf("SSS")) {
            return 1;
        }
        return 1000;
    }
}
