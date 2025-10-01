package org.newsclub.net.unix;

import java.io.FileDescriptor;
import java.lang.reflect.Field;
import java.net.InetSocketAddress;

/* loaded from: L-out.jar:org/newsclub/net/unix/NativeUnixSocket.class */
final class NativeUnixSocket {
    private static boolean loaded;

    static native void bind(String str, FileDescriptor fileDescriptor, int i);

    static native void listen(FileDescriptor fileDescriptor, int i);

    static native void accept(String str, FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2);

    static native void connect(String str, FileDescriptor fileDescriptor);

    static native int read(FileDescriptor fileDescriptor, byte[] bArr, int i, int i2);

    static native int write(FileDescriptor fileDescriptor, byte[] bArr, int i, int i2);

    static native void close(FileDescriptor fileDescriptor);

    static native void shutdown(FileDescriptor fileDescriptor, int i);

    static native int getSocketOptionInt(FileDescriptor fileDescriptor, int i);

    static native void setSocketOptionInt(FileDescriptor fileDescriptor, int i, int i2);

    static native void unlink(String str);

    static native int available(FileDescriptor fileDescriptor);

    static native void initServerImpl(AFUNIXServerSocket aFUNIXServerSocket, AFUNIXSocketImpl aFUNIXSocketImpl);

    static native void setCreated(AFUNIXSocket aFUNIXSocket);

    static native void setConnected(AFUNIXSocket aFUNIXSocket);

    static native void setBound(AFUNIXSocket aFUNIXSocket);

    static native void setCreatedServer(AFUNIXServerSocket aFUNIXServerSocket);

    static native void setBoundServer(AFUNIXServerSocket aFUNIXServerSocket);

    static native void setPort(AFUNIXSocketAddress aFUNIXSocketAddress, int i);

    NativeUnixSocket() {
    }

    static {
        loaded = false;
        try {
            Class.forName("org.newsclub.net.unix.NarSystem").getMethod("loadLibrary", new Class[0]).invoke(null, new Object[0]);
            loaded = true;
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Could not find NarSystem class.\n\n*** ECLIPSE USERS ***\nIf you're running from within Eclipse, please try closing the \"junixsocket-native-common\" project\n", e);
        } catch (Exception e2) {
            throw new IllegalStateException(e2);
        }
    }

    static boolean isLoaded() {
        return loaded;
    }

    static void setPort1(AFUNIXSocketAddress aFUNIXSocketAddress, int i) {
        Field declaredField;
        if (i < 0) {
            throw new IllegalArgumentException("port out of range:" + i);
        }
        boolean z = false;
        try {
            Field declaredField2 = InetSocketAddress.class.getDeclaredField("holder");
            if (declaredField2 != null) {
                declaredField2.setAccessible(true);
                Object obj = declaredField2.get(aFUNIXSocketAddress);
                if (obj != null && (declaredField = obj.getClass().getDeclaredField("port")) != null) {
                    declaredField.setAccessible(true);
                    declaredField.set(obj, Integer.valueOf(i));
                    z = true;
                }
            } else {
                setPort(aFUNIXSocketAddress, i);
            }
            if (!z) {
                throw new AFUNIXSocketException("Could not set port");
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e2) {
            if (e2 instanceof AFUNIXSocketException) {
                throw ((AFUNIXSocketException) e2);
            }
            throw new AFUNIXSocketException("Could not set port", e2);
        }
    }
}
