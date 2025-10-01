package org.newsclub.net.unix;

import java.net.SocketException;

/* loaded from: L-out.jar:org/newsclub/net/unix/AFUNIXSocketException.class */
public class AFUNIXSocketException extends SocketException {
    private static final long serialVersionUID = 1;
    private final String socketFile;

    public AFUNIXSocketException(String str) {
        this(str, (String) null);
    }

    public AFUNIXSocketException(String str, Throwable th) {
        this(str, (String) null);
        initCause(th);
    }

    public AFUNIXSocketException(String str, String str2) {
        super(str);
        this.socketFile = str2;
    }

    @Override // java.lang.Throwable
    public String toString() {
        if (this.socketFile == null) {
            return super.toString();
        }
        return super.toString() + " (socket: " + this.socketFile + ")";
    }
}
