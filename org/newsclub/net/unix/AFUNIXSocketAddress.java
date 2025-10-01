package org.newsclub.net.unix;

import java.io.File;
import java.net.InetSocketAddress;

/* loaded from: L-out.jar:org/newsclub/net/unix/AFUNIXSocketAddress.class */
public class AFUNIXSocketAddress extends InetSocketAddress {
    private static final long serialVersionUID = 1;
    private final String socketFile;

    public AFUNIXSocketAddress(File file) {
        this(file, 0);
    }

    public AFUNIXSocketAddress(File file, int i) {
        super(0);
        if (i != 0) {
            NativeUnixSocket.setPort1(this, i);
        }
        this.socketFile = file.getCanonicalPath();
    }

    public String getSocketFile() {
        return this.socketFile;
    }

    @Override // java.net.InetSocketAddress
    public String toString() {
        return getClass().getName() + "[host=" + getHostName() + ";port=" + getPort() + ";file=" + this.socketFile + "]";
    }
}
