package org.apache.log4j.helpers;

import java.io.File;

/* loaded from: L-out.jar:org/apache/log4j/helpers/FileWatchdog.class */
public abstract class FileWatchdog extends Thread {
    public static final long DEFAULT_DELAY = 60000;
    protected String filename;
    protected long delay;
    File file;
    long lastModif;
    boolean warnedAlready;
    boolean interrupted;

    protected abstract void doOnChange();

    protected FileWatchdog(String str) {
        super("FileWatchdog");
        this.delay = DEFAULT_DELAY;
        this.lastModif = 0L;
        this.warnedAlready = false;
        this.interrupted = false;
        this.filename = str;
        this.file = new File(str);
        setDaemon(true);
        checkAndConfigure();
    }

    public void setDelay(long j) {
        this.delay = j;
    }

    protected void checkAndConfigure() {
        try {
            if (this.file.exists()) {
                long jLastModified = this.file.lastModified();
                if (jLastModified > this.lastModif) {
                    this.lastModif = jLastModified;
                    doOnChange();
                    this.warnedAlready = false;
                    return;
                }
                return;
            }
            if (!this.warnedAlready) {
                LogLog.debug(new StringBuffer().append("[").append(this.filename).append("] does not exist.").toString());
                this.warnedAlready = true;
            }
        } catch (SecurityException unused) {
            LogLog.warn(new StringBuffer().append("Was not allowed to read check file existance, file:[").append(this.filename).append("].").toString());
            this.interrupted = true;
        }
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws InterruptedException {
        while (!this.interrupted) {
            try {
                Thread.sleep(this.delay);
            } catch (InterruptedException unused) {
            }
            checkAndConfigure();
        }
    }
}
