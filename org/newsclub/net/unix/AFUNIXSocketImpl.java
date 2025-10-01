package org.newsclub.net.unix;

import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.SocketImpl;

/* loaded from: L-out.jar:org/newsclub/net/unix/AFUNIXSocketImpl.class */
class AFUNIXSocketImpl extends SocketImpl {
    private static final int SHUT_RD = 0;
    private static final int SHUT_WR = 1;
    private static final int SHUT_RD_WR = 2;
    private String socketFile;
    private boolean closed = false;
    private boolean bound = false;
    private boolean connected = false;
    private boolean closedInputStream = false;
    private boolean closedOutputStream = false;

    /* renamed from: in */
    private final AFUNIXInputStream f212in = new AFUNIXInputStream(this, null);
    private final AFUNIXOutputStream out = new AFUNIXOutputStream(this, null);

    AFUNIXSocketImpl() {
        this.fd = new FileDescriptor();
    }

    FileDescriptor getFD() {
        return this.fd;
    }

    @Override // java.net.SocketImpl
    protected void accept(SocketImpl socketImpl) {
        AFUNIXSocketImpl aFUNIXSocketImpl = (AFUNIXSocketImpl) socketImpl;
        NativeUnixSocket.accept(this.socketFile, this.fd, aFUNIXSocketImpl.fd);
        aFUNIXSocketImpl.socketFile = this.socketFile;
        aFUNIXSocketImpl.connected = true;
    }

    @Override // java.net.SocketImpl
    protected int available() {
        return NativeUnixSocket.available(this.fd);
    }

    protected void bind(SocketAddress socketAddress) {
        bind(0, socketAddress);
    }

    protected void bind(int i, SocketAddress socketAddress) {
        if (!(socketAddress instanceof AFUNIXSocketAddress)) {
            throw new SocketException("Cannot bind to this type of address: " + socketAddress.getClass());
        }
        AFUNIXSocketAddress aFUNIXSocketAddress = (AFUNIXSocketAddress) socketAddress;
        this.socketFile = aFUNIXSocketAddress.getSocketFile();
        NativeUnixSocket.bind(this.socketFile, this.fd, i);
        this.bound = true;
        this.localport = aFUNIXSocketAddress.getPort();
    }

    @Override // java.net.SocketImpl
    protected void bind(InetAddress inetAddress, int i) throws SocketException {
        throw new SocketException("Cannot bind to this type of address: " + InetAddress.class);
    }

    private void checkClose() {
        if (!this.closedInputStream || this.closedOutputStream) {
        }
    }

    @Override // java.net.SocketImpl
    protected void close() {
        if (this.closed) {
            return;
        }
        this.closed = true;
        if (this.fd.valid()) {
            NativeUnixSocket.shutdown(this.fd, 2);
            NativeUnixSocket.close(this.fd);
        }
        if (this.bound) {
            NativeUnixSocket.unlink(this.socketFile);
        }
        this.connected = false;
    }

    @Override // java.net.SocketImpl
    protected void connect(String str, int i) throws SocketException {
        throw new SocketException("Cannot bind to this type of address: " + InetAddress.class);
    }

    @Override // java.net.SocketImpl
    protected void connect(InetAddress inetAddress, int i) throws SocketException {
        throw new SocketException("Cannot bind to this type of address: " + InetAddress.class);
    }

    @Override // java.net.SocketImpl
    protected void connect(SocketAddress socketAddress, int i) {
        if (!(socketAddress instanceof AFUNIXSocketAddress)) {
            throw new SocketException("Cannot bind to this type of address: " + socketAddress.getClass());
        }
        AFUNIXSocketAddress aFUNIXSocketAddress = (AFUNIXSocketAddress) socketAddress;
        this.socketFile = aFUNIXSocketAddress.getSocketFile();
        NativeUnixSocket.connect(this.socketFile, this.fd);
        this.address = aFUNIXSocketAddress.getAddress();
        this.port = aFUNIXSocketAddress.getPort();
        this.localport = 0;
        this.connected = true;
    }

    @Override // java.net.SocketImpl
    protected InputStream getInputStream() throws IOException {
        if (!this.connected && !this.bound) {
            throw new IOException("Not connected/not bound");
        }
        return this.f212in;
    }

    @Override // java.net.SocketImpl
    protected OutputStream getOutputStream() throws IOException {
        if (!this.connected && !this.bound) {
            throw new IOException("Not connected/not bound");
        }
        return this.out;
    }

    @Override // java.net.SocketImpl
    protected void listen(int i) {
        NativeUnixSocket.listen(this.fd, i);
    }

    @Override // java.net.SocketImpl
    protected void sendUrgentData(int i) {
        NativeUnixSocket.write(this.fd, new byte[]{(byte) (i & 255)}, 0, 1);
    }

    /* loaded from: L-out.jar:org/newsclub/net/unix/AFUNIXSocketImpl$AFUNIXInputStream.class */
    private final class AFUNIXInputStream extends InputStream {
        private boolean streamClosed;
        final AFUNIXSocketImpl this$0;

        private AFUNIXInputStream(AFUNIXSocketImpl aFUNIXSocketImpl) {
            this.this$0 = aFUNIXSocketImpl;
            this.streamClosed = false;
        }

        AFUNIXInputStream(AFUNIXSocketImpl aFUNIXSocketImpl, C05431 c05431) {
            this(aFUNIXSocketImpl);
        }

        @Override // java.io.InputStream
        public int read(byte[] bArr, int i, int i2) throws IOException {
            if (this.streamClosed) {
                throw new IOException("This InputStream has already been closed.");
            }
            if (i2 == 0) {
                return 0;
            }
            int length = bArr.length - i;
            if (i2 > length) {
                i2 = length;
            }
            try {
                return NativeUnixSocket.read(this.this$0.fd, bArr, i, i2);
            } catch (IOException e) {
                throw ((IOException) new IOException(e.getMessage() + " at " + this.this$0.toString()).initCause(e));
            }
        }

        @Override // java.io.InputStream
        public int read() {
            byte[] bArr = new byte[1];
            if (read(bArr, 0, 1) <= 0) {
                return -1;
            }
            return bArr[0] & 255;
        }

        @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (this.streamClosed) {
                return;
            }
            this.streamClosed = true;
            if (this.this$0.fd.valid()) {
                NativeUnixSocket.shutdown(this.this$0.fd, 0);
            }
            this.this$0.closedInputStream = true;
            this.this$0.checkClose();
        }

        @Override // java.io.InputStream
        public int available() {
            return NativeUnixSocket.available(this.this$0.fd);
        }
    }

    /* loaded from: L-out.jar:org/newsclub/net/unix/AFUNIXSocketImpl$AFUNIXOutputStream.class */
    private final class AFUNIXOutputStream extends OutputStream {
        private boolean streamClosed;
        final AFUNIXSocketImpl this$0;

        private AFUNIXOutputStream(AFUNIXSocketImpl aFUNIXSocketImpl) {
            this.this$0 = aFUNIXSocketImpl;
            this.streamClosed = false;
        }

        AFUNIXOutputStream(AFUNIXSocketImpl aFUNIXSocketImpl, C05431 c05431) {
            this(aFUNIXSocketImpl);
        }

        @Override // java.io.OutputStream
        public void write(int i) throws IOException {
            write(new byte[]{(byte) i}, 0, 1);
        }

        @Override // java.io.OutputStream
        public void write(byte[] bArr, int i, int i2) throws IOException {
            if (this.streamClosed) {
                throw new AFUNIXSocketException("This OutputStream has already been closed.");
            }
            if (i2 > bArr.length - i) {
                throw new IndexOutOfBoundsException();
            }
            while (i2 > 0) {
                try {
                    if (Thread.interrupted()) {
                        break;
                    }
                    int iWrite = NativeUnixSocket.write(this.this$0.fd, bArr, i, i2);
                    if (iWrite == -1) {
                        throw new IOException("Unspecific error while writing");
                    }
                    i2 -= iWrite;
                    i += iWrite;
                } catch (IOException e) {
                    throw ((IOException) new IOException(e.getMessage() + " at " + this.this$0.toString()).initCause(e));
                }
            }
        }

        @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            if (this.streamClosed) {
                return;
            }
            this.streamClosed = true;
            if (this.this$0.fd.valid()) {
                NativeUnixSocket.shutdown(this.this$0.fd, 1);
            }
            this.this$0.closedOutputStream = true;
            this.this$0.checkClose();
        }
    }

    @Override // java.net.SocketImpl
    public String toString() {
        return super.toString() + "[fd=" + this.fd + "; file=" + this.socketFile + "; connected=" + this.connected + "; bound=" + this.bound + "]";
    }

    private static int expectInteger(Object obj) throws AFUNIXSocketException {
        try {
            return ((Integer) obj).intValue();
        } catch (ClassCastException e) {
            throw new AFUNIXSocketException("Unsupported value: " + obj, e);
        } catch (NullPointerException e2) {
            throw new AFUNIXSocketException("Value must not be null", e2);
        }
    }

    private static int expectBoolean(Object obj) throws AFUNIXSocketException {
        try {
            return ((Boolean) obj).booleanValue() ? 1 : 0;
        } catch (ClassCastException e) {
            throw new AFUNIXSocketException("Unsupported value: " + obj, e);
        } catch (NullPointerException e2) {
            throw new AFUNIXSocketException("Value must not be null", e2);
        }
    }

    @Override // java.net.SocketOptions
    public Object getOption(int i) throws AFUNIXSocketException {
        try {
            try {
                switch (i) {
                    case 1:
                    case 8:
                        return Boolean.valueOf(NativeUnixSocket.getSocketOptionInt(this.fd, i) != 0);
                    case 128:
                    case 4097:
                    case 4098:
                    case 4102:
                        return Integer.valueOf(NativeUnixSocket.getSocketOptionInt(this.fd, i));
                    default:
                        throw new AFUNIXSocketException("Unsupported option: " + i);
                }
            } catch (Exception e) {
                throw new AFUNIXSocketException("Error while getting option", e);
            }
        } catch (AFUNIXSocketException e2) {
            throw e2;
        }
    }

    @Override // java.net.SocketOptions
    public void setOption(int i, Object obj) throws SocketException {
        try {
            try {
                switch (i) {
                    case 1:
                    case 8:
                        NativeUnixSocket.setSocketOptionInt(this.fd, i, expectBoolean(obj));
                        return;
                    case 128:
                        if (obj instanceof Boolean) {
                            if (((Boolean) obj).booleanValue()) {
                                throw new SocketException("Only accepting Boolean.FALSE here");
                            }
                            NativeUnixSocket.setSocketOptionInt(this.fd, i, -1);
                            return;
                        }
                        NativeUnixSocket.setSocketOptionInt(this.fd, i, expectInteger(obj));
                        return;
                    case 4097:
                    case 4098:
                    case 4102:
                        NativeUnixSocket.setSocketOptionInt(this.fd, i, expectInteger(obj));
                        return;
                    default:
                        throw new AFUNIXSocketException("Unsupported option: " + i);
                }
            } catch (Exception e) {
                throw new AFUNIXSocketException("Error while setting option", e);
            }
        } catch (AFUNIXSocketException e2) {
            throw e2;
        }
    }

    @Override // java.net.SocketImpl
    protected void shutdownInput() {
        if (!this.closed && this.fd.valid()) {
            NativeUnixSocket.shutdown(this.fd, 0);
        }
    }

    @Override // java.net.SocketImpl
    protected void shutdownOutput() {
        if (!this.closed && this.fd.valid()) {
            NativeUnixSocket.shutdown(this.fd, 1);
        }
    }

    /* loaded from: L-out.jar:org/newsclub/net/unix/AFUNIXSocketImpl$Lenient.class */
    static class Lenient extends AFUNIXSocketImpl {
        Lenient() {
        }

        @Override // org.newsclub.net.unix.AFUNIXSocketImpl, java.net.SocketOptions
        public void setOption(int i, Object obj) throws SocketException {
            try {
                super.setOption(i, obj);
            } catch (SocketException e) {
                switch (i) {
                    case 1:
                        return;
                    default:
                        throw e;
                }
            }
        }

        @Override // org.newsclub.net.unix.AFUNIXSocketImpl, java.net.SocketOptions
        public Object getOption(int i) throws SocketException {
            try {
                return super.getOption(i);
            } catch (SocketException e) {
                switch (i) {
                    case 1:
                    case 8:
                        return false;
                    default:
                        throw e;
                }
            }
        }
    }
}
