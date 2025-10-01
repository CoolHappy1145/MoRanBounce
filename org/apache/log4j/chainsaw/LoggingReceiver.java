package org.apache.log4j.chainsaw;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import org.apache.log4j.Logger;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/chainsaw/LoggingReceiver.class */
class LoggingReceiver extends Thread {
    private static final Logger LOG;
    private MyTableModel mModel;
    private ServerSocket mSvrSock;
    static Class class$org$apache$log4j$chainsaw$LoggingReceiver;

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static {
        Class clsClass$;
        if (class$org$apache$log4j$chainsaw$LoggingReceiver == null) {
            clsClass$ = class$("org.apache.log4j.chainsaw.LoggingReceiver");
            class$org$apache$log4j$chainsaw$LoggingReceiver = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$chainsaw$LoggingReceiver;
        }
        LOG = Logger.getLogger(clsClass$);
    }

    /* loaded from: L-out.jar:org/apache/log4j/chainsaw/LoggingReceiver$Slurper.class */
    private class Slurper implements Runnable {
        private final Socket mClient;
        private final LoggingReceiver this$0;

        Slurper(LoggingReceiver loggingReceiver, Socket socket) {
            this.this$0 = loggingReceiver;
            this.mClient = socket;
        }

        @Override // java.lang.Runnable
        public void run() throws IOException {
            LoggingReceiver.LOG.debug("Starting to get data");
            try {
                while (true) {
                    this.this$0.mModel.addEvent(new EventDetails((LoggingEvent) new ObjectInputStream(this.mClient.getInputStream()).readObject()));
                }
            } catch (SocketException unused) {
                LoggingReceiver.LOG.info("Caught SocketException, closing connection");
                try {
                    this.mClient.close();
                } catch (IOException e) {
                    LoggingReceiver.LOG.warn("Error closing connection", e);
                }
            } catch (IOException e2) {
                LoggingReceiver.LOG.warn("Got IOException, closing connection", e2);
                this.mClient.close();
            } catch (ClassNotFoundException e3) {
                LoggingReceiver.LOG.warn("Got ClassNotFoundException, closing connection", e3);
                this.mClient.close();
            }
        }
    }

    LoggingReceiver(MyTableModel myTableModel, int i) {
        setDaemon(true);
        this.mModel = myTableModel;
        this.mSvrSock = new ServerSocket(i);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() throws IOException {
        LOG.info("Thread started");
        while (true) {
            LOG.debug("Waiting for a connection");
            Socket socketAccept = this.mSvrSock.accept();
            LOG.debug(new StringBuffer().append("Got a connection from ").append(socketAccept.getInetAddress().getHostName()).toString());
            Thread thread = new Thread(new Slurper(this, socketAccept));
            thread.setDaemon(true);
            thread.start();
        }
    }
}
