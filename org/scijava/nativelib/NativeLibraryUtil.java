package org.scijava.nativelib;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: L-out.jar:org/scijava/nativelib/NativeLibraryUtil.class */
public class NativeLibraryUtil {
    private static final String DELIM = "/";
    private static final String JAVA_TMPDIR = "java.io.tmpdir";
    private static Architecture architecture = Architecture.UNKNOWN;
    private static final Logger LOGGER = Logger.getLogger("org.scijava.nativelib.NativeLibraryUtil");

    /* loaded from: L-out.jar:org/scijava/nativelib/NativeLibraryUtil$Architecture.class */
    public enum Architecture {
        UNKNOWN,
        LINUX_32,
        LINUX_64,
        WINDOWS_32,
        WINDOWS_64,
        OSX_32,
        OSX_64,
        OSX_PPC
    }

    /* loaded from: L-out.jar:org/scijava/nativelib/NativeLibraryUtil$Processor.class */
    private enum Processor {
        UNKNOWN,
        INTEL_32,
        INTEL_64,
        PPC
    }

    public static Architecture getArchitecture() {
        Processor processor;
        if (Architecture.UNKNOWN == architecture && Processor.UNKNOWN != (processor = getProcessor())) {
            String lowerCase = System.getProperty("os.name").toLowerCase();
            if (lowerCase.indexOf("nix") >= 0 || lowerCase.indexOf("nux") >= 0) {
                if (Processor.INTEL_32 == processor) {
                    architecture = Architecture.LINUX_32;
                } else if (Processor.INTEL_64 == processor) {
                    architecture = Architecture.LINUX_64;
                }
            } else if (lowerCase.indexOf("win") >= 0) {
                if (Processor.INTEL_32 == processor) {
                    architecture = Architecture.WINDOWS_32;
                } else if (Processor.INTEL_64 == processor) {
                    architecture = Architecture.WINDOWS_64;
                }
            } else if (lowerCase.indexOf("mac") >= 0) {
                if (Processor.INTEL_32 == processor) {
                    architecture = Architecture.OSX_32;
                } else if (Processor.INTEL_64 == processor) {
                    architecture = Architecture.OSX_64;
                } else if (Processor.PPC == processor) {
                    architecture = Architecture.OSX_PPC;
                }
            }
        }
        LOGGER.log(Level.FINE, "architecture is " + architecture + " os.name is " + System.getProperty("os.name").toLowerCase());
        return architecture;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static Processor getProcessor() {
        Processor processor = Processor.UNKNOWN;
        String lowerCase = System.getProperty("os.arch").toLowerCase();
        if (lowerCase.indexOf("ppc") >= 0) {
            processor = Processor.PPC;
        } else if (lowerCase.indexOf("86") >= 0 || lowerCase.indexOf("amd") >= 0) {
            boolean z = 32;
            if (lowerCase.indexOf("64") >= 0) {
                z = 64;
            }
            processor = 32 == z ? Processor.INTEL_32 : Processor.INTEL_64;
        }
        LOGGER.log(Level.FINE, "processor is " + processor + " os.arch is " + System.getProperty("os.arch").toLowerCase());
        return processor;
    }

    public static String getPlatformLibraryPath() {
        String str = "META-INF/lib/" + getArchitecture().name().toLowerCase() + DELIM;
        LOGGER.log(Level.FINE, "platform specific path is " + str);
        return str;
    }

    /* renamed from: org.scijava.nativelib.NativeLibraryUtil$1 */
    /* loaded from: L-out.jar:org/scijava/nativelib/NativeLibraryUtil$1.class */
    static /* synthetic */ class C05451 {
        static final int[] $SwitchMap$org$scijava$nativelib$NativeLibraryUtil$Architecture = new int[Architecture.values().length];

        static {
            try {
                $SwitchMap$org$scijava$nativelib$NativeLibraryUtil$Architecture[Architecture.LINUX_32.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$scijava$nativelib$NativeLibraryUtil$Architecture[Architecture.LINUX_64.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$org$scijava$nativelib$NativeLibraryUtil$Architecture[Architecture.WINDOWS_32.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$org$scijava$nativelib$NativeLibraryUtil$Architecture[Architecture.WINDOWS_64.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$org$scijava$nativelib$NativeLibraryUtil$Architecture[Architecture.OSX_32.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$org$scijava$nativelib$NativeLibraryUtil$Architecture[Architecture.OSX_64.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public static String getPlatformLibraryName(String str) {
        String str2 = null;
        switch (C05451.$SwitchMap$org$scijava$nativelib$NativeLibraryUtil$Architecture[getArchitecture().ordinal()]) {
            case 1:
            case 2:
                str2 = str + ".so";
                break;
            case 3:
            case 4:
                str2 = str + ".dll";
                break;
            case 5:
            case 6:
                str2 = "lib" + str + ".dylib";
                break;
        }
        LOGGER.log(Level.FINE, "native library name " + str2);
        return str2;
    }

    public static String getVersionedLibraryName(Class cls, String str) {
        String implementationVersion = cls.getPackage().getImplementationVersion();
        if (null != implementationVersion && implementationVersion.length() > 0) {
            str = str + "-" + implementationVersion;
        }
        return str;
    }

    public static boolean loadVersionedNativeLibrary(Class cls, String str) {
        return loadNativeLibrary(cls, getVersionedLibraryName(cls, str));
    }

    public static boolean loadNativeLibrary(Class cls, String str) {
        boolean z = false;
        if (Architecture.UNKNOWN == getArchitecture()) {
            LOGGER.log(Level.WARNING, "No native library available for this platform.");
        } else {
            try {
                System.load(new DefaultJniExtractor(cls, System.getProperty(JAVA_TMPDIR)).extractJni(getPlatformLibraryPath(), str).getPath());
                z = true;
            } catch (IOException e) {
                LOGGER.log(Level.WARNING, "IOException creating DefaultJniExtractor", (Throwable) e);
            } catch (SecurityException e2) {
                LOGGER.log(Level.WARNING, "Can't load dynamic library", (Throwable) e2);
            } catch (UnsatisfiedLinkError e3) {
                LOGGER.log(Level.WARNING, "Problem with library", (Throwable) e3);
            }
        }
        return z;
    }
}
