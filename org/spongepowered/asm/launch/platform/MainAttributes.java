package org.spongepowered.asm.launch.platform;

import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/* loaded from: L-out.jar:org/spongepowered/asm/launch/platform/MainAttributes.class */
public final class MainAttributes {
    private static final Map instances = new HashMap();
    protected final Attributes attributes;

    private MainAttributes() {
        this.attributes = new Attributes();
    }

    private MainAttributes(File file) {
        this.attributes = getAttributes(file);
    }

    public final String get(String str) {
        if (this.attributes != null) {
            return this.attributes.getValue(str);
        }
        return null;
    }

    private static Attributes getAttributes(File file) {
        Attributes dirAttributes;
        Attributes jarAttributes;
        if (file == null) {
            return null;
        }
        if (file.isFile() && (jarAttributes = getJarAttributes(file)) != null) {
            return jarAttributes;
        }
        if (file.isDirectory() && (dirAttributes = getDirAttributes(file)) != null) {
            return dirAttributes;
        }
        return new Attributes();
    }

    private static Attributes getJarAttributes(File file) {
        JarFile jarFile = null;
        try {
            jarFile = new JarFile(file);
            Manifest manifest = jarFile.getManifest();
            if (manifest != null) {
                Attributes mainAttributes = manifest.getMainAttributes();
                if (jarFile != null) {
                    try {
                        jarFile.close();
                    } catch (IOException unused) {
                    }
                }
                return mainAttributes;
            }
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException unused2) {
                    return null;
                }
            }
            return null;
        } catch (IOException unused3) {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException unused4) {
                    return null;
                }
            }
            return null;
        } catch (Throwable th) {
            if (jarFile != null) {
                try {
                    jarFile.close();
                } catch (IOException unused5) {
                    throw th;
                }
            }
            throw th;
        }
    }

    private static Attributes getDirAttributes(File file) throws IOException {
        File file2 = new File(file, "META-INF/MANIFEST.MF");
        if (!file2.isFile()) {
            return null;
        }
        InputStream inputStreamOpenBufferedStream = null;
        try {
            inputStreamOpenBufferedStream = Files.asByteSource(file2).openBufferedStream();
            Attributes mainAttributes = new Manifest(inputStreamOpenBufferedStream).getMainAttributes();
            if (inputStreamOpenBufferedStream != null) {
                try {
                    inputStreamOpenBufferedStream.close();
                } catch (IOException unused) {
                }
            }
            return mainAttributes;
        } catch (IOException unused2) {
            if (inputStreamOpenBufferedStream != null) {
                try {
                    inputStreamOpenBufferedStream.close();
                } catch (IOException unused3) {
                    return null;
                }
            }
            return null;
        } catch (Throwable th) {
            if (inputStreamOpenBufferedStream != null) {
                try {
                    inputStreamOpenBufferedStream.close();
                } catch (IOException unused4) {
                    throw th;
                }
            }
            throw th;
        }
    }

    /* renamed from: of */
    public static MainAttributes m51of(File file) {
        return m52of(file.toURI());
    }

    /* renamed from: of */
    public static MainAttributes m52of(URI uri) {
        MainAttributes mainAttributes = (MainAttributes) instances.get(uri);
        if (mainAttributes == null) {
            mainAttributes = new MainAttributes(new File(uri));
            instances.put(uri, mainAttributes);
        }
        return mainAttributes;
    }
}
