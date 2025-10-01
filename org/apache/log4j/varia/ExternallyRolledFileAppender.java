package org.apache.log4j.varia;

import org.apache.log4j.RollingFileAppender;

/* loaded from: L-out.jar:org/apache/log4j/varia/ExternallyRolledFileAppender.class */
public class ExternallyRolledFileAppender extends RollingFileAppender {
    public static final String ROLL_OVER = "RollOver";

    /* renamed from: OK */
    public static final String f206OK = "OK";
    int port = 0;
    HUP hup;

    public void setPort(int i) {
        this.port = i;
    }

    public int getPort() {
        return this.port;
    }

    @Override // org.apache.log4j.FileAppender, org.apache.log4j.WriterAppender, org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
        super.activateOptions();
        if (this.port != 0) {
            if (this.hup != null) {
                this.hup.interrupt();
            }
            this.hup = new HUP(this, this.port);
            this.hup.setDaemon(true);
            this.hup.start();
        }
    }
}
