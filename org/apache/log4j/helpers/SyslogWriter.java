package org.apache.log4j.helpers;

import java.io.IOException;
import java.io.Writer;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import net.ccbluex.liquidbounce.utils.Skid.SGL;

/* loaded from: L-out.jar:org/apache/log4j/helpers/SyslogWriter.class */
public class SyslogWriter extends Writer {
    final int SYSLOG_PORT = SGL.GL_EQUAL;
    static String syslogHost;
    private InetAddress address;
    private final int port;

    /* renamed from: ds */
    private DatagramSocket f193ds;

    public SyslogWriter(String str) {
        syslogHost = str;
        if (str == null) {
            throw new NullPointerException("syslogHost");
        }
        String host = str;
        int port = -1;
        if (host.indexOf("[") != -1 || host.indexOf(58) == host.lastIndexOf(58)) {
            try {
                URL url = new URL(new StringBuffer().append("http://").append(host).toString());
                if (url.getHost() != null) {
                    host = url.getHost();
                    if (host.startsWith("[") && host.charAt(host.length() - 1) == ']') {
                        host = host.substring(1, host.length() - 1);
                    }
                    port = url.getPort();
                }
            } catch (MalformedURLException e) {
                LogLog.error("Malformed URL: will attempt to interpret as InetAddress.", e);
            }
        }
        this.port = port == -1 ? 514 : port;
        try {
            this.address = InetAddress.getByName(host);
        } catch (UnknownHostException e2) {
            LogLog.error(new StringBuffer().append("Could not find ").append(host).append(". All logging will FAIL.").toString(), e2);
        }
        try {
            this.f193ds = new DatagramSocket();
        } catch (SocketException e3) {
            e3.printStackTrace();
            LogLog.error(new StringBuffer().append("Could not instantiate DatagramSocket to ").append(host).append(". All logging will FAIL.").toString(), e3);
        }
    }

    @Override // java.io.Writer
    public void write(char[] cArr, int i, int i2) throws IOException {
        write(new String(cArr, i, i2));
    }

    @Override // java.io.Writer
    public void write(String str) throws IOException {
        if (this.f193ds != null && this.address != null) {
            byte[] bytes = str.getBytes();
            int length = bytes.length;
            if (length >= 1024) {
                length = 1024;
            }
            this.f193ds.send(new DatagramPacket(bytes, length, this.address, this.port));
        }
    }

    @Override // java.io.Writer, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        if (this.f193ds != null) {
            this.f193ds.close();
        }
    }
}
