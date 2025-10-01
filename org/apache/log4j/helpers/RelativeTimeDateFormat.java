package org.apache.log4j.helpers;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.util.Date;

/* loaded from: L-out.jar:org/apache/log4j/helpers/RelativeTimeDateFormat.class */
public class RelativeTimeDateFormat extends DateFormat {
    private static final long serialVersionUID = 7055751607085611984L;
    protected final long startTime = System.currentTimeMillis();

    @Override // java.text.DateFormat
    public StringBuffer format(Date date, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return stringBuffer.append(date.getTime() - this.startTime);
    }
}
