package org.apache.log4j.helpers;

import java.io.IOException;
import java.io.Writer;
import org.apache.log4j.spi.ErrorHandler;

/* loaded from: L-out.jar:org/apache/log4j/helpers/SyslogQuietWriter.class */
public class SyslogQuietWriter extends QuietWriter {
    int syslogFacility;
    int level;

    public SyslogQuietWriter(Writer writer, int i, ErrorHandler errorHandler) {
        super(writer, errorHandler);
        this.syslogFacility = i;
    }

    public void setLevel(int i) {
        this.level = i;
    }

    public void setSyslogFacility(int i) {
        this.syslogFacility = i;
    }

    @Override // org.apache.log4j.helpers.QuietWriter, java.io.Writer
    public void write(String str) throws IOException {
        super.write(new StringBuffer().append("<").append(this.syslogFacility | this.level).append(">").append(str).toString());
    }
}
