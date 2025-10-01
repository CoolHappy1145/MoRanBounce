package org.apache.log4j.net;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;
import org.json.HTTP;

/* loaded from: L-out.jar:org/apache/log4j/net/TelnetAppender.class */
public class TelnetAppender extends AppenderSkeleton {

    /* renamed from: sh */
    private SocketHandler f199sh;
    private int port = 23;

    @Override // org.apache.log4j.spi.OptionHandler
    public void activateOptions() {
        try {
            this.f199sh = new SocketHandler(this, this.port);
            this.f199sh.start();
        } catch (InterruptedIOException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (RuntimeException e3) {
            e3.printStackTrace();
        }
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int i) {
        this.port = i;
    }

    @Override // org.apache.log4j.Appender
    public void close() throws IOException {
        if (this.f199sh != null) {
            this.f199sh.close();
            try {
                this.f199sh.join();
            } catch (InterruptedException unused) {
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override // org.apache.log4j.AppenderSkeleton
    protected void append(LoggingEvent loggingEvent) {
        String[] throwableStrRep;
        if (this.f199sh != null) {
            this.f199sh.send(this.layout.format(loggingEvent));
            if (this.layout.ignoresThrowable() && (throwableStrRep = loggingEvent.getThrowableStrRep()) != null) {
                StringBuffer stringBuffer = new StringBuffer();
                for (String str : throwableStrRep) {
                    stringBuffer.append(str);
                    stringBuffer.append(HTTP.CRLF);
                }
                this.f199sh.send(stringBuffer.toString());
            }
        }
    }

    /* loaded from: L-out.jar:org/apache/log4j/net/TelnetAppender$SocketHandler.class */
    protected class SocketHandler extends Thread {
        private ServerSocket serverSocket;
        private final TelnetAppender this$0;
        private Vector writers = new Vector();
        private Vector connections = new Vector();
        private int MAX_CONNECTIONS = 20;

        public void finalize() throws IOException {
            close();
        }

        public void close() throws IOException {
            synchronized (this) {
                Enumeration enumerationElements = this.connections.elements();
                while (enumerationElements.hasMoreElements()) {
                    try {
                        ((Socket) enumerationElements.nextElement()).close();
                    } catch (InterruptedIOException unused) {
                        Thread.currentThread().interrupt();
                    } catch (IOException unused2) {
                    } catch (RuntimeException unused3) {
                    }
                }
            }
            try {
                this.serverSocket.close();
            } catch (InterruptedIOException unused4) {
                Thread.currentThread().interrupt();
            } catch (IOException unused5) {
            } catch (RuntimeException unused6) {
            }
        }

        public void send(String str) {
            Iterator it = this.connections.iterator();
            Iterator it2 = this.writers.iterator();
            while (it2.hasNext()) {
                it.next();
                PrintWriter printWriter = (PrintWriter) it2.next();
                printWriter.print(str);
                if (printWriter.checkError()) {
                    it.remove();
                    it2.remove();
                }
            }
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws IOException {
            while (!this.serverSocket.isClosed()) {
                try {
                    Socket socketAccept = this.serverSocket.accept();
                    PrintWriter printWriter = new PrintWriter(socketAccept.getOutputStream());
                    if (this.connections.size() < this.MAX_CONNECTIONS) {
                        synchronized (this) {
                            this.connections.addElement(socketAccept);
                            this.writers.addElement(printWriter);
                            printWriter.print(new StringBuffer().append("TelnetAppender v1.0 (").append(this.connections.size()).append(" active connections)\r\n\r\n").toString());
                            printWriter.flush();
                        }
                    } else {
                        printWriter.print("Too many connections.\r\n");
                        printWriter.flush();
                        socketAccept.close();
                    }
                } catch (Exception e) {
                    if ((e instanceof InterruptedIOException) || (e instanceof InterruptedException)) {
                        Thread.currentThread().interrupt();
                    }
                    if (!this.serverSocket.isClosed()) {
                        LogLog.error("Encountered error while in SocketHandler loop.", e);
                    }
                }
            }
            try {
                this.serverSocket.close();
            } catch (InterruptedIOException unused) {
                Thread.currentThread().interrupt();
            } catch (IOException unused2) {
            }
        }

        public SocketHandler(TelnetAppender telnetAppender, int i) {
            this.this$0 = telnetAppender;
            this.serverSocket = new ServerSocket(i);
            setName(new StringBuffer().append("TelnetAppender-").append(getName()).append("-").append(i).toString());
        }
    }
}
