package org.apache.log4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.Writer;
import org.apache.log4j.helpers.CountingQuietWriter;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.helpers.OptionConverter;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/RollingFileAppender.class */
public class RollingFileAppender extends FileAppender {
    protected long maxFileSize;
    protected int maxBackupIndex;
    private long nextRollover;

    public RollingFileAppender() {
        this.maxFileSize = 10485760L;
        this.maxBackupIndex = 1;
        this.nextRollover = 0L;
    }

    public RollingFileAppender(Layout layout, String str, boolean z) {
        super(layout, str, z);
        this.maxFileSize = 10485760L;
        this.maxBackupIndex = 1;
        this.nextRollover = 0L;
    }

    public RollingFileAppender(Layout layout, String str) {
        super(layout, str);
        this.maxFileSize = 10485760L;
        this.maxBackupIndex = 1;
        this.nextRollover = 0L;
    }

    public int getMaxBackupIndex() {
        return this.maxBackupIndex;
    }

    public long getMaximumFileSize() {
        return this.maxFileSize;
    }

    public void rollOver() {
        if (this.f189qw != null) {
            long count = ((CountingQuietWriter) this.f189qw).getCount();
            LogLog.debug(new StringBuffer().append("rolling over count=").append(count).toString());
            this.nextRollover = count + this.maxFileSize;
        }
        LogLog.debug(new StringBuffer().append("maxBackupIndex=").append(this.maxBackupIndex).toString());
        boolean zRenameTo = true;
        if (this.maxBackupIndex > 0) {
            File file = new File(new StringBuffer().append(this.fileName).append('.').append(this.maxBackupIndex).toString());
            if (file.exists()) {
                zRenameTo = file.delete();
            }
            for (int i = this.maxBackupIndex - 1; i >= 1 && zRenameTo; i--) {
                File file2 = new File(new StringBuffer().append(this.fileName).append(".").append(i).toString());
                if (file2.exists()) {
                    File file3 = new File(new StringBuffer().append(this.fileName).append('.').append(i + 1).toString());
                    LogLog.debug(new StringBuffer().append("Renaming file ").append(file2).append(" to ").append(file3).toString());
                    zRenameTo = file2.renameTo(file3);
                }
            }
            if (zRenameTo) {
                File file4 = new File(new StringBuffer().append(this.fileName).append(".").append(1).toString());
                closeFile();
                File file5 = new File(this.fileName);
                LogLog.debug(new StringBuffer().append("Renaming file ").append(file5).append(" to ").append(file4).toString());
                zRenameTo = file5.renameTo(file4);
                if (!zRenameTo) {
                    try {
                        setFile(this.fileName, true, this.bufferedIO, this.bufferSize);
                    } catch (IOException e) {
                        if (e instanceof InterruptedIOException) {
                            Thread.currentThread().interrupt();
                        }
                        LogLog.error(new StringBuffer().append("setFile(").append(this.fileName).append(", true) call failed.").toString(), e);
                    }
                }
            }
        }
        if (zRenameTo) {
            try {
                setFile(this.fileName, false, this.bufferedIO, this.bufferSize);
                this.nextRollover = 0L;
            } catch (IOException e2) {
                if (e2 instanceof InterruptedIOException) {
                    Thread.currentThread().interrupt();
                }
                LogLog.error(new StringBuffer().append("setFile(").append(this.fileName).append(", false) call failed.").toString(), e2);
            }
        }
    }

    @Override // org.apache.log4j.FileAppender
    public void setFile(String str, boolean z, boolean z2, int i) throws FileNotFoundException {
        super.setFile(str, z, this.bufferedIO, this.bufferSize);
        if (z) {
            ((CountingQuietWriter) this.f189qw).setCount(new File(str).length());
        }
    }

    public void setMaxBackupIndex(int i) {
        this.maxBackupIndex = i;
    }

    public void setMaximumFileSize(long j) {
        this.maxFileSize = j;
    }

    public void setMaxFileSize(String str) {
        this.maxFileSize = OptionConverter.toFileSize(str, this.maxFileSize + 1);
    }

    @Override // org.apache.log4j.FileAppender
    protected void setQWForFiles(Writer writer) {
        this.f189qw = new CountingQuietWriter(writer, this.errorHandler);
    }

    @Override // org.apache.log4j.WriterAppender
    protected void subAppend(LoggingEvent loggingEvent) {
        super.subAppend(loggingEvent);
        if (this.fileName != null && this.f189qw != null) {
            long count = ((CountingQuietWriter) this.f189qw).getCount();
            if (count >= this.maxFileSize && count >= this.nextRollover) {
                rollOver();
            }
        }
    }
}
