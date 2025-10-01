package org.scijava.nativelib;

import java.io.File;
import java.io.IOException;

/* loaded from: L-out.jar:org/scijava/nativelib/WebappJniExtractor.class */
public class WebappJniExtractor extends BaseJniExtractor {
    private File nativeDir = new File(System.getProperty("java.library.tmpdir", "tmplib"));
    private File jniSubDir;

    public WebappJniExtractor(String str) throws IOException {
        this.nativeDir.mkdirs();
        if (!this.nativeDir.isDirectory()) {
            throw new IOException("Unable to create native library working directory " + this.nativeDir);
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        int i = 0;
        while (true) {
            File file = new File(this.nativeDir, str + "." + jCurrentTimeMillis + "." + i);
            if (!file.mkdir()) {
                if (file.exists()) {
                    i++;
                } else {
                    throw new IOException("Unable to create native library working directory " + file);
                }
            } else {
                this.jniSubDir = file;
                this.jniSubDir.deleteOnExit();
                return;
            }
        }
    }

    protected void finalize() {
        for (File file : this.jniSubDir.listFiles()) {
            file.delete();
        }
        this.jniSubDir.delete();
    }

    @Override // org.scijava.nativelib.BaseJniExtractor
    public File getJniDir() {
        return this.jniSubDir;
    }

    @Override // org.scijava.nativelib.BaseJniExtractor
    public File getNativeDir() {
        return this.nativeDir;
    }
}
