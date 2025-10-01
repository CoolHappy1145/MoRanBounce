package org.newsclub.net.unix;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;

/* loaded from: L-out.jar:org/newsclub/net/unix/AFUNIXServerSocket.class */
public class AFUNIXServerSocket extends ServerSocket {
    private AFUNIXSocketAddress boundEndpoint = null;
    private final Thread shutdownThread = new Thread(this) { // from class: org.newsclub.net.unix.AFUNIXServerSocket.1
        final AFUNIXServerSocket this$0;

        {
            this.this$0 = this;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                if (this.this$0.boundEndpoint != null) {
                    NativeUnixSocket.unlink(this.this$0.boundEndpoint.getSocketFile());
                }
            } catch (IOException unused) {
            }
        }
    };
    private final AFUNIXSocketImpl implementation = new AFUNIXSocketImpl();

    protected AFUNIXServerSocket() {
        NativeUnixSocket.initServerImpl(this, this.implementation);
        Runtime.getRuntime().addShutdownHook(this.shutdownThread);
        NativeUnixSocket.setCreatedServer(this);
    }

    public static AFUNIXServerSocket newInstance() {
        return new AFUNIXServerSocket();
    }

    public static AFUNIXServerSocket bindOn(AFUNIXSocketAddress aFUNIXSocketAddress) {
        AFUNIXServerSocket aFUNIXServerSocketNewInstance = newInstance();
        aFUNIXServerSocketNewInstance.bind(aFUNIXSocketAddress);
        return aFUNIXServerSocketNewInstance;
    }

    @Override // java.net.ServerSocket
    public void bind(SocketAddress socketAddress, int i) throws IOException {
        if (isClosed()) {
            throw new SocketException("Socket is closed");
        }
        if (isBound()) {
            throw new SocketException("Already bound");
        }
        if (!(socketAddress instanceof AFUNIXSocketAddress)) {
            throw new IOException("Can only bind to endpoints of type " + AFUNIXSocketAddress.class.getName());
        }
        this.implementation.bind(i, socketAddress);
        this.boundEndpoint = (AFUNIXSocketAddress) socketAddress;
    }

    @Override // java.net.ServerSocket
    public boolean isBound() {
        return this.boundEndpoint != null;
    }

    @Override // java.net.ServerSocket
    public Socket accept() throws SocketException {
        if (isClosed()) {
            throw new SocketException("Socket is closed");
        }
        AFUNIXSocket aFUNIXSocketNewInstance = AFUNIXSocket.newInstance();
        this.implementation.accept(aFUNIXSocketNewInstance.impl);
        aFUNIXSocketNewInstance.addr = this.boundEndpoint;
        NativeUnixSocket.setConnected(aFUNIXSocketNewInstance);
        return aFUNIXSocketNewInstance;
    }

    @Override // java.net.ServerSocket
    public String toString() {
        if (!isBound()) {
            return "AFUNIXServerSocket[unbound]";
        }
        return "AFUNIXServerSocket[" + this.boundEndpoint.getSocketFile() + "]";
    }

    @Override // java.net.ServerSocket, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        if (isClosed()) {
            return;
        }
        super.close();
        this.implementation.close();
        if (this.boundEndpoint != null) {
            NativeUnixSocket.unlink(this.boundEndpoint.getSocketFile());
        }
        try {
            Runtime.getRuntime().removeShutdownHook(this.shutdownThread);
        } catch (IllegalStateException unused) {
        }
    }

    public static boolean isSupported() {
        return NativeUnixSocket.isLoaded();
    }
}
