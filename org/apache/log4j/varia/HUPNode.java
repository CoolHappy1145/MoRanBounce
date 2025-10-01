package org.apache.log4j.varia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.Socket;
import org.apache.log4j.helpers.LogLog;

/* loaded from: L-out.jar:org/apache/log4j/varia/HUPNode.class */
class HUPNode implements Runnable {
    Socket socket;
    DataInputStream dis;
    DataOutputStream dos;

    /* renamed from: er */
    ExternallyRolledFileAppender f208er;

    public HUPNode(Socket socket, ExternallyRolledFileAppender externallyRolledFileAppender) {
        this.socket = socket;
        this.f208er = externallyRolledFileAppender;
        try {
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
        } catch (InterruptedIOException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (RuntimeException e3) {
            e3.printStackTrace();
        }
    }

    @Override // java.lang.Runnable
    public void run() throws IOException {
        try {
            String utf = this.dis.readUTF();
            LogLog.debug("Got external roll over signal.");
            if (ExternallyRolledFileAppender.ROLL_OVER.equals(utf)) {
                synchronized (this.f208er) {
                    this.f208er.rollOver();
                }
                this.dos.writeUTF(ExternallyRolledFileAppender.f206OK);
            } else {
                this.dos.writeUTF("Expecting [RollOver] string.");
            }
            this.dos.close();
        } catch (InterruptedIOException e) {
            Thread.currentThread().interrupt();
            LogLog.error("Unexpected exception. Exiting HUPNode.", e);
        } catch (IOException e2) {
            LogLog.error("Unexpected exception. Exiting HUPNode.", e2);
        } catch (RuntimeException e3) {
            LogLog.error("Unexpected exception. Exiting HUPNode.", e3);
        }
    }
}
