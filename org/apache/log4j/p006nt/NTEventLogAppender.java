package org.apache.log4j.p006nt;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.TTCCLayout;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/nt/NTEventLogAppender.class */
public class NTEventLogAppender extends AppenderSkeleton {
    private int _handle;
    private String source;
    private String server;

    private native int registerEventSource(String str, String str2);

    private native void reportEvent(int i, String str, int i2);

    private native void deregisterEventSource(int i);

    public NTEventLogAppender() {
        this(null, null, null);
    }

    public NTEventLogAppender(String str) {
        this(null, str, null);
    }

    public NTEventLogAppender(String str, String str2) {
        this(str, str2, null);
    }

    public NTEventLogAppender(Layout layout) {
        this(null, null, layout);
    }

    public NTEventLogAppender(String str, Layout layout) {
        this(null, str, layout);
    }

    public NTEventLogAppender(String str, String str2, Layout layout) {
        this._handle = 0;
        this.source = null;
        this.server = null;
        str2 = str2 == null ? "Log4j" : str2;
        if (layout == null) {
            this.layout = new TTCCLayout();
        } else {
            this.layout = layout;
        }
        try {
            this._handle = registerEventSource(str, str2);
        } catch (Exception e) {
            e.printStackTrace();
            this._handle = 0;
        }
    }

    @Override // org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
        if (this.source != null) {
            try {
                this._handle = registerEventSource(this.server, this.source);
            } catch (Exception e) {
                LogLog.error("Could not register event source.", e);
                this._handle = 0;
            }
        }
    }

    @Override // org.apache.log4j.AppenderSkeleton
    public void append(LoggingEvent loggingEvent) {
        String[] throwableStrRep;
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(this.layout.format(loggingEvent));
        if (this.layout.ignoresThrowable() && (throwableStrRep = loggingEvent.getThrowableStrRep()) != null) {
            for (String str : throwableStrRep) {
                stringBuffer.append(str);
            }
        }
        reportEvent(this._handle, stringBuffer.toString(), loggingEvent.getLevel().toInt());
    }

    @Override // org.apache.log4j.AppenderSkeleton
    public void finalize() {
        deregisterEventSource(this._handle);
        this._handle = 0;
    }

    public void setSource(String str) {
        this.source = str.trim();
    }

    public String getSource() {
        return this.source;
    }

    static {
        String[] strArr;
        try {
            strArr = new String[]{System.getProperty("os.arch")};
        } catch (SecurityException unused) {
            strArr = new String[]{"amd64", "ia64", "x86"};
        }
        boolean z = false;
        for (String str : strArr) {
            try {
                System.loadLibrary(new StringBuffer().append("NTEventLogAppender.").append(str).toString());
                z = true;
                break;
            } catch (UnsatisfiedLinkError unused2) {
                z = false;
            }
        }
        if (!z) {
            System.loadLibrary("NTEventLogAppender");
        }
    }
}
