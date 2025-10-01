package org.scijava.nativelib;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/* loaded from: L-out.jar:org/scijava/nativelib/BaseJniExtractor.class */
public abstract class BaseJniExtractor implements JniExtractor {
    private static final Logger LOGGER = Logger.getLogger("org.scijava.nativelib.BaseJniExtractor");
    private static final String JAVA_TMPDIR = "java.io.tmpdir";
    private Class libraryJarClass;
    private String[] nativeResourcePaths;

    public abstract File getNativeDir();

    public abstract File getJniDir();

    public BaseJniExtractor() {
        init(null);
    }

    public BaseJniExtractor(Class cls) {
        init(cls);
    }

    private void init(Class cls) {
        this.libraryJarClass = cls;
        String mxSysInfo = MxSysInfo.getMxSysInfo();
        if (mxSysInfo != null) {
            this.nativeResourcePaths = new String[]{"META-INF/lib/" + mxSysInfo + "/", "META-INF/lib/"};
        } else {
            this.nativeResourcePaths = new String[]{"META-INF/lib/"};
        }
    }

    @Override // org.scijava.nativelib.JniExtractor
    public File extractJni(String str, String str2) throws IOException {
        String strMapLibraryName = System.mapLibraryName(str2);
        LOGGER.log(Level.FINE, "mappedLib is " + strMapLibraryName);
        if (null == this.libraryJarClass) {
            this.libraryJarClass = getClass();
        }
        URL resource = this.libraryJarClass.getClassLoader().getResource(str + strMapLibraryName);
        if (null == resource && strMapLibraryName.endsWith(".jnilib")) {
            resource = getClass().getClassLoader().getResource(str + strMapLibraryName.substring(0, strMapLibraryName.length() - 7) + ".dylib");
            if (null != resource) {
                strMapLibraryName = strMapLibraryName.substring(0, strMapLibraryName.length() - 7) + ".dylib";
            }
        }
        if (null != resource) {
            LOGGER.log(Level.FINE, "URL is " + resource.toString());
            LOGGER.log(Level.FINE, "URL path is " + resource.getPath());
            return extractResource(getJniDir(), resource, strMapLibraryName);
        }
        LOGGER.log(Level.INFO, "Couldn't find resource " + str + " " + strMapLibraryName);
        throw new IOException("Couldn't find resource " + str + " " + strMapLibraryName);
    }

    @Override // org.scijava.nativelib.JniExtractor
    public void extractRegistered() throws IOException {
        LOGGER.log(Level.FINE, "Extracting libraries registered in classloader " + getClass().getClassLoader());
        for (int i = 0; i < this.nativeResourcePaths.length; i++) {
            Enumeration<URL> resources = getClass().getClassLoader().getResources(this.nativeResourcePaths[i] + "AUTOEXTRACT.LIST");
            while (resources.hasMoreElements()) {
                URL urlNextElement = resources.nextElement();
                LOGGER.log(Level.FINE, "Extracting libraries listed in " + urlNextElement);
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlNextElement.openStream(), "UTF-8"));
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line != null) {
                        URL resource = null;
                        for (int i2 = 0; i2 < this.nativeResourcePaths.length; i2++) {
                            resource = getClass().getClassLoader().getResource(this.nativeResourcePaths[i2] + line);
                            if (resource != null) {
                                break;
                            }
                        }
                        if (resource != null) {
                            extractResource(getNativeDir(), resource, line);
                        } else {
                            throw new IOException("Couldn't find native library " + line + "on the classpath");
                        }
                    }
                }
            }
        }
    }

    File extractResource(File file, URL url, String str) throws IOException {
        InputStream inputStreamOpenStream = url.openStream();
        String strSubstring = str;
        String strSubstring2 = null;
        int iLastIndexOf = str.lastIndexOf(46);
        if (-1 != iLastIndexOf) {
            strSubstring = str.substring(0, iLastIndexOf);
            strSubstring2 = str.substring(iLastIndexOf);
        }
        deleteLeftoverFiles(strSubstring, strSubstring2);
        File fileCreateTempFile = File.createTempFile(strSubstring, strSubstring2);
        LOGGER.log(Level.FINE, "Extracting '" + url + "' to '" + fileCreateTempFile.getAbsolutePath() + "'");
        FileOutputStream fileOutputStream = new FileOutputStream(fileCreateTempFile);
        copy(inputStreamOpenStream, fileOutputStream);
        fileOutputStream.close();
        inputStreamOpenStream.close();
        fileCreateTempFile.deleteOnExit();
        return fileCreateTempFile;
    }

    void deleteLeftoverFiles(String str, String str2) {
        File[] fileArrListFiles = new File(System.getProperty(JAVA_TMPDIR)).listFiles(new FilenameFilter(this, str, str2) { // from class: org.scijava.nativelib.BaseJniExtractor.1
            final String val$prefix;
            final String val$suffix;
            final BaseJniExtractor this$0;

            {
                this.this$0 = this;
                this.val$prefix = str;
                this.val$suffix = str2;
            }

            @Override // java.io.FilenameFilter
            public boolean accept(File file, String str3) {
                return str3.startsWith(this.val$prefix) && str3.endsWith(this.val$suffix);
            }
        });
        if (fileArrListFiles == null) {
            return;
        }
        for (File file : fileArrListFiles) {
            try {
                file.delete();
            } catch (SecurityException unused) {
            }
        }
    }

    static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        while (true) {
            int i = inputStream.read(bArr);
            if (i > 0) {
                outputStream.write(bArr, 0, i);
            } else {
                return;
            }
        }
    }
}
