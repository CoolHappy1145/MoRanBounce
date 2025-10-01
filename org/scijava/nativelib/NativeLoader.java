package org.scijava.nativelib;

import java.io.IOException;

/* loaded from: L-out.jar:org/scijava/nativelib/NativeLoader.class */
public class NativeLoader {
    private static JniExtractor jniExtractor;

    static {
        jniExtractor = null;
        try {
            if (NativeLoader.class.getClassLoader() == ClassLoader.getSystemClassLoader()) {
                jniExtractor = new DefaultJniExtractor();
            } else {
                jniExtractor = new WebappJniExtractor("Classloader");
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void loadLibrary(String str) {
        System.load(jniExtractor.extractJni("", str).getAbsolutePath());
    }

    public static void extractRegistered() {
        jniExtractor.extractRegistered();
    }

    public static JniExtractor getJniExtractor() {
        return jniExtractor;
    }

    public static void setJniExtractor(JniExtractor jniExtractor2) {
        jniExtractor = jniExtractor2;
    }
}
