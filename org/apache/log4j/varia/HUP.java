package org.apache.log4j.varia;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.log4j.helpers.LogLog;

/* loaded from: L-out.jar:org/apache/log4j/varia/HUP.class */
class HUP extends Thread {
    int port;

    /* renamed from: er */
    ExternallyRolledFileAppender f207er;

    HUP(ExternallyRolledFileAppender externallyRolledFileAppender, int i) {
        this.f207er = externallyRolledFileAppender;
        this.port = i;
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws IOException {
        while (!isInterrupted()) {
            try {
                while (true) {
                    Socket socketAccept = new ServerSocket(this.port).accept();
                    LogLog.debug(new StringBuffer().append("Connected to client at ").append(socketAccept.getInetAddress()).toString());
                    new Thread(new HUPNode(socketAccept, this.f207er), "ExternallyRolledFileAppender-HUP").start();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (RuntimeException e2) {
                e2.printStackTrace();
            }
        }
    }
}
