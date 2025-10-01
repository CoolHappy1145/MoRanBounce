package org.newsclub.net.unix;

import java.io.File;
import java.net.URL;
import org.scijava.nativelib.DefaultJniExtractor;

/* loaded from: L-out.jar:org/newsclub/net/unix/NarSystem.class */
public final class NarSystem {
    public native int runUnitTestsNative();

    private NarSystem() {
    }

    public static void loadLibrary() {
        String strMapLibraryName = System.mapLibraryName("junixsocket-native-2.0.4");
        String[] aOLs = getAOLs();
        ClassLoader classLoader = NarSystem.class.getClassLoader();
        File unpackedLibPath = getUnpackedLibPath(classLoader, aOLs, "junixsocket-native-2.0.4", strMapLibraryName);
        if (unpackedLibPath != null) {
            System.load(unpackedLibPath.getPath());
            return;
        }
        try {
            System.load(new DefaultJniExtractor(NarSystem.class, System.getProperty("java.io.tmpdir")).extractJni(getLibPath(classLoader, aOLs, strMapLibraryName), "junixsocket-native-2.0.4").getPath());
        } catch (Exception e) {
            e.printStackTrace();
            if (!(e instanceof RuntimeException)) {
                throw new RuntimeException(e);
            }
        }
    }

    public static int runUnitTests() {
        return new NarSystem().runUnitTestsNative();
    }

    private static String[] getAOLs() {
        String str = System.getProperty("os.arch") + "-" + System.getProperty("os.name").replaceAll(" ", "");
        if (str.startsWith("i386-Linux")) {
            return new String[]{"i386-Linux-ecpc", "i386-Linux-gpp", "i386-Linux-icc", "i386-Linux-ecc", "i386-Linux-icpc", "i386-Linux-linker", "i386-Linux-gcc"};
        }
        if (str.startsWith("x86-Windows")) {
            return new String[]{"x86-Windows-linker", "x86-Windows-gpp", "x86-Windows-msvc", "x86-Windows-icl", "x86-Windows-gcc"};
        }
        if (str.startsWith("amd64-Linux")) {
            return new String[]{"amd64-Linux-gpp", "amd64-Linux-icpc", "amd64-Linux-gcc", "amd64-Linux-linker"};
        }
        if (str.startsWith("amd64-Windows")) {
            return new String[]{"amd64-Windows-gpp", "amd64-Windows-msvc", "amd64-Windows-icl", "amd64-Windows-linker", "amd64-Windows-gcc"};
        }
        if (str.startsWith("amd64-FreeBSD")) {
            return new String[]{"amd64-FreeBSD-gpp", "amd64-FreeBSD-gcc", "amd64-FreeBSD-linker"};
        }
        if (str.startsWith("ppc-MacOSX")) {
            return new String[]{"ppc-MacOSX-gpp", "ppc-MacOSX-linker", "ppc-MacOSX-gcc"};
        }
        if (str.startsWith("x86_64-MacOSX")) {
            return new String[]{"x86_64-MacOSX-icc", "x86_64-MacOSX-icpc", "x86_64-MacOSX-gpp", "x86_64-MacOSX-linker", "x86_64-MacOSX-gcc"};
        }
        if (str.startsWith("ppc-AIX")) {
            return new String[]{"ppc-AIX-gpp", "ppc-AIX-xlC", "ppc-AIX-gcc", "ppc-AIX-linker"};
        }
        if (str.startsWith("i386-FreeBSD")) {
            return new String[]{"i386-FreeBSD-gpp", "i386-FreeBSD-gcc", "i386-FreeBSD-linker"};
        }
        if (str.startsWith("sparc-SunOS")) {
            return new String[]{"sparc-SunOS-cc", "sparc-SunOS-CC", "sparc-SunOS-linker"};
        }
        if (str.startsWith("arm-Linux")) {
            return new String[]{"arm-Linux-gpp", "arm-Linux-linker", "arm-Linux-gcc"};
        }
        if (str.startsWith("x86-SunOS")) {
            return new String[]{"x86-SunOS-g++", "x86-SunOS-linker"};
        }
        if (str.startsWith("i386-MacOSX")) {
            return new String[]{"i386-MacOSX-gpp", "i386-MacOSX-gcc", "i386-MacOSX-linker"};
        }
        throw new RuntimeException("Unhandled architecture/OS: " + str);
    }

    private static File getUnpackedLibPath(ClassLoader classLoader, String[] strArr, String str, String str2) {
        String str3 = NarSystem.class.getName().replace('.', '/') + ".class";
        URL resource = classLoader.getResource(str3);
        if (resource == null || !"file".equals(resource.getProtocol())) {
            return null;
        }
        String path = resource.getPath();
        String str4 = path.substring(0, path.length() - str3.length()) + "../nar/" + str + "-";
        for (String str5 : strArr) {
            File file = new File(str4 + str5 + "-jni/lib/" + str5 + "/jni/" + str2);
            if (file.isFile()) {
                return file;
            }
        }
        return null;
    }

    private static String getLibPath(ClassLoader classLoader, String[] strArr, String str) {
        for (String str2 : strArr) {
            String str3 = "lib/" + str2 + "/jni/";
            if (classLoader.getResource(str3 + str) != null) {
                return str3;
            }
        }
        throw new RuntimeException("Library '" + str + "' not found!");
    }
}
