package org.apache.log4j.net;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Vector;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.CyclicBuffer;
import org.apache.log4j.helpers.LogLog;
import org.apache.log4j.spi.LoggingEvent;

/* loaded from: L-out.jar:org/apache/log4j/net/SocketHubAppender.class */
public class SocketHubAppender extends AppenderSkeleton {
    static final int DEFAULT_PORT = 4560;
    private int port;
    private Vector oosList;
    private ServerMonitor serverMonitor;
    private boolean locationInfo;
    private CyclicBuffer buffer;
    private String application;
    private boolean advertiseViaMulticastDNS;
    private ZeroConfSupport zeroConf;
    public static final String ZONE = "_log4j_obj_tcpaccept_appender.local.";
    private ServerSocket serverSocket;

    public SocketHubAppender() {
        this.port = 4560;
        this.oosList = new Vector();
        this.serverMonitor = null;
        this.locationInfo = false;
        this.buffer = null;
    }

    public SocketHubAppender(int i) {
        this.port = 4560;
        this.oosList = new Vector();
        this.serverMonitor = null;
        this.locationInfo = false;
        this.buffer = null;
        this.port = i;
        startServer();
    }

    @Override // org.apache.log4j.spi.OptionHandler
    public void activateOptions() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if (this.advertiseViaMulticastDNS) {
            this.zeroConf = new ZeroConfSupport(ZONE, this.port, getName());
            this.zeroConf.advertise();
        }
        startServer();
    }

    @Override // org.apache.log4j.Appender
    public void close() throws IllegalAccessException, InterruptedException, IOException, IllegalArgumentException, InvocationTargetException {
        if (this.closed) {
            return;
        }
        LogLog.debug(new StringBuffer().append("closing SocketHubAppender ").append(getName()).toString());
        this.closed = true;
        if (this.advertiseViaMulticastDNS) {
            this.zeroConf.unadvertise();
        }
        cleanUp();
        LogLog.debug(new StringBuffer().append("SocketHubAppender ").append(getName()).append(" closed").toString());
    }

    public void cleanUp() throws InterruptedException, IOException {
        LogLog.debug("stopping ServerSocket");
        this.serverMonitor.stopMonitor();
        this.serverMonitor = null;
        LogLog.debug("closing client connections");
        while (this.oosList.size() != 0) {
            ObjectOutputStream objectOutputStream = (ObjectOutputStream) this.oosList.elementAt(0);
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (InterruptedIOException e) {
                    Thread.currentThread().interrupt();
                    LogLog.error("could not close oos.", e);
                } catch (IOException e2) {
                    LogLog.error("could not close oos.", e2);
                }
                this.oosList.removeElementAt(0);
            }
        }
    }

    @Override // org.apache.log4j.AppenderSkeleton
    public void append(LoggingEvent loggingEvent) throws IOException {
        if (loggingEvent != null) {
            if (this.locationInfo) {
                loggingEvent.getLocationInformation();
            }
            if (this.application != null) {
                loggingEvent.setProperty("application", this.application);
            }
            loggingEvent.getNDC();
            loggingEvent.getThreadName();
            loggingEvent.getMDCCopy();
            loggingEvent.getRenderedMessage();
            loggingEvent.getThrowableStrRep();
            if (this.buffer != null) {
                this.buffer.add(loggingEvent);
            }
        }
        if (loggingEvent == null || this.oosList.size() == 0) {
            return;
        }
        int i = 0;
        while (i < this.oosList.size()) {
            ObjectOutputStream objectOutputStream = null;
            try {
                objectOutputStream = (ObjectOutputStream) this.oosList.elementAt(i);
            } catch (ArrayIndexOutOfBoundsException unused) {
            }
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.writeObject(loggingEvent);
                    objectOutputStream.flush();
                    objectOutputStream.reset();
                } catch (IOException e) {
                    if (e instanceof InterruptedIOException) {
                        Thread.currentThread().interrupt();
                    }
                    this.oosList.removeElementAt(i);
                    LogLog.debug("dropped connection");
                    i--;
                }
                i++;
            } else {
                return;
            }
        }
    }

    public void setPort(int i) {
        this.port = i;
    }

    public void setApplication(String str) {
        this.application = str;
    }

    public String getApplication() {
        return this.application;
    }

    public int getPort() {
        return this.port;
    }

    public void setBufferSize(int i) {
        this.buffer = new CyclicBuffer(i);
    }

    public int getBufferSize() {
        if (this.buffer == null) {
            return 0;
        }
        return this.buffer.getMaxSize();
    }

    public void setLocationInfo(boolean z) {
        this.locationInfo = z;
    }

    public boolean getLocationInfo() {
        return this.locationInfo;
    }

    public void setAdvertiseViaMulticastDNS(boolean z) {
        this.advertiseViaMulticastDNS = z;
    }

    public boolean isAdvertiseViaMulticastDNS() {
        return this.advertiseViaMulticastDNS;
    }

    private void startServer() {
        this.serverMonitor = new ServerMonitor(this, this.port, this.oosList);
    }

    protected ServerSocket createServerSocket(int i) {
        return new ServerSocket(i);
    }

    /* loaded from: L-out.jar:org/apache/log4j/net/SocketHubAppender$ServerMonitor.class */
    private class ServerMonitor implements Runnable {
        private int port;
        private Vector oosList;
        private boolean keepRunning = true;
        private Thread monitorThread = new Thread(this);
        private final SocketHubAppender this$0;

        public ServerMonitor(SocketHubAppender socketHubAppender, int i, Vector vector) {
            this.this$0 = socketHubAppender;
            this.port = i;
            this.oosList = vector;
            this.monitorThread.setDaemon(true);
            this.monitorThread.setName(new StringBuffer().append("SocketHubAppender-Monitor-").append(this.port).toString());
            this.monitorThread.start();
        }

        public void stopMonitor() throws InterruptedException, IOException {
            if (this.keepRunning) {
                LogLog.debug("server monitor thread shutting down");
                this.keepRunning = false;
                try {
                    if (this.this$0.serverSocket != null) {
                        this.this$0.serverSocket.close();
                        this.this$0.serverSocket = null;
                    }
                } catch (IOException unused) {
                }
                try {
                    this.monitorThread.join();
                } catch (InterruptedException unused2) {
                    Thread.currentThread().interrupt();
                }
                this.monitorThread = null;
                LogLog.debug("server monitor thread shut down");
            }
        }

        private void sendCachedEvents(ObjectOutputStream objectOutputStream) throws IOException {
            if (this.this$0.buffer != null) {
                for (int i = 0; i < this.this$0.buffer.length(); i++) {
                    objectOutputStream.writeObject(this.this$0.buffer.get(i));
                }
                objectOutputStream.flush();
                objectOutputStream.reset();
            }
        }

        @Override // java.lang.Runnable
        public void run() throws IOException {
            this.this$0.serverSocket = null;
            try {
                try {
                    this.this$0.serverSocket = this.this$0.createServerSocket(this.port);
                    this.this$0.serverSocket.setSoTimeout(1000);
                    try {
                        this.this$0.serverSocket.setSoTimeout(1000);
                        while (this.keepRunning) {
                            Socket socketAccept = null;
                            try {
                                socketAccept = this.this$0.serverSocket.accept();
                            } catch (InterruptedIOException unused) {
                            } catch (SocketException e) {
                                LogLog.error("exception accepting socket, shutting down server socket.", e);
                                this.keepRunning = false;
                            } catch (IOException e2) {
                                LogLog.error("exception accepting socket.", e2);
                            }
                            if (socketAccept != null) {
                                try {
                                    InetAddress inetAddress = socketAccept.getInetAddress();
                                    LogLog.debug(new StringBuffer().append("accepting connection from ").append(inetAddress.getHostName()).append(" (").append(inetAddress.getHostAddress()).append(")").toString());
                                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketAccept.getOutputStream());
                                    if (this.this$0.buffer != null && this.this$0.buffer.length() > 0) {
                                        sendCachedEvents(objectOutputStream);
                                    }
                                    this.oosList.addElement(objectOutputStream);
                                } catch (IOException e3) {
                                    if (e3 instanceof InterruptedIOException) {
                                        Thread.currentThread().interrupt();
                                    }
                                    LogLog.error("exception creating output stream on socket.", e3);
                                }
                            }
                        }
                    } catch (SocketException e4) {
                        LogLog.error("exception setting timeout, shutting down server socket.", e4);
                        try {
                            this.this$0.serverSocket.close();
                        } catch (InterruptedIOException unused2) {
                            Thread.currentThread().interrupt();
                        } catch (IOException unused3) {
                        }
                    }
                } catch (Exception e5) {
                    if ((e5 instanceof InterruptedIOException) || (e5 instanceof InterruptedException)) {
                        Thread.currentThread().interrupt();
                    }
                    LogLog.error("exception setting timeout, shutting down server socket.", e5);
                    this.keepRunning = false;
                }
            } finally {
                try {
                    this.this$0.serverSocket.close();
                } catch (InterruptedIOException unused4) {
                    Thread.currentThread().interrupt();
                } catch (IOException unused5) {
                }
            }
        }
    }
}
