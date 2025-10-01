package org.newsclub.net.unix;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketAddress;
import org.newsclub.net.unix.AFUNIXSocketImpl;

/* loaded from: L-out.jar:org/newsclub/net/unix/AFUNIXSocket.class */
public class AFUNIXSocket extends Socket {
    protected AFUNIXSocketImpl impl;
    AFUNIXSocketAddress addr;

    private AFUNIXSocket(AFUNIXSocketImpl aFUNIXSocketImpl) {
        super(aFUNIXSocketImpl);
        try {
            NativeUnixSocket.setCreated(this);
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }

    public static AFUNIXSocket newInstance() {
        AFUNIXSocketImpl.Lenient lenient = new AFUNIXSocketImpl.Lenient();
        AFUNIXSocket aFUNIXSocket = new AFUNIXSocket(lenient);
        aFUNIXSocket.impl = lenient;
        return aFUNIXSocket;
    }

    public static AFUNIXSocket newStrictInstance() {
        AFUNIXSocketImpl aFUNIXSocketImpl = new AFUNIXSocketImpl();
        AFUNIXSocket aFUNIXSocket = new AFUNIXSocket(aFUNIXSocketImpl);
        aFUNIXSocket.impl = aFUNIXSocketImpl;
        return aFUNIXSocket;
    }

    public static AFUNIXSocket connectTo(AFUNIXSocketAddress aFUNIXSocketAddress) throws IOException {
        AFUNIXSocket aFUNIXSocketNewInstance = newInstance();
        aFUNIXSocketNewInstance.connect(aFUNIXSocketAddress);
        return aFUNIXSocketNewInstance;
    }

    @Override // java.net.Socket
    public void bind(SocketAddress socketAddress) throws IOException {
        super.bind(socketAddress);
        this.addr = (AFUNIXSocketAddress) socketAddress;
    }

    @Override // java.net.Socket
    public void connect(SocketAddress socketAddress) throws IOException {
        connect(socketAddress, 0);
    }

    @Override // java.net.Socket
    public void connect(SocketAddress socketAddress, int i) throws IOException {
        if (!(socketAddress instanceof AFUNIXSocketAddress)) {
            throw new IOException("Can only connect to endpoints of type " + AFUNIXSocketAddress.class.getName());
        }
        this.impl.connect(socketAddress, i);
        this.addr = (AFUNIXSocketAddress) socketAddress;
        NativeUnixSocket.setConnected(this);
    }

    @Override // java.net.Socket
    public String toString() {
        if (isConnected()) {
            return "AFUNIXSocket[fd=" + this.impl.getFD() + ";path=" + this.addr.getSocketFile() + "]";
        }
        return "AFUNIXSocket[unconnected]";
    }

    public static boolean isSupported() {
        return NativeUnixSocket.isLoaded();
    }
}
