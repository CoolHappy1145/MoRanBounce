package jdk.nashorn.internal.runtime;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.CodeSource;
import java.security.Permission;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.security.SecureClassLoader;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/NashornLoader.class */
abstract class NashornLoader extends SecureClassLoader {
    private static final String OBJECTS_PKG = "jdk.nashorn.internal.objects";
    private static final String RUNTIME_PKG = "jdk.nashorn.internal.runtime";
    private static final String RUNTIME_ARRAYS_PKG = "jdk.nashorn.internal.runtime.arrays";
    private static final String RUNTIME_LINKER_PKG = "jdk.nashorn.internal.runtime.linker";
    private static final String SCRIPTS_PKG = "jdk.nashorn.internal.scripts";
    private static final Permission[] SCRIPT_PERMISSIONS = {new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.runtime"), new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.runtime.linker"), new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.objects"), new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.scripts"), new RuntimePermission("accessClassInPackage.jdk.nashorn.internal.runtime.arrays")};

    NashornLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    protected static void checkPackageAccess(String str) {
        SecurityManager securityManager;
        String strSubstring;
        int iLastIndexOf = str.lastIndexOf(46);
        if (iLastIndexOf != -1 && (securityManager = System.getSecurityManager()) != null) {
            strSubstring = str.substring(0, iLastIndexOf);
            switch (strSubstring) {
                case "jdk.nashorn.internal.runtime":
                case "jdk.nashorn.internal.runtime.arrays":
                case "jdk.nashorn.internal.runtime.linker":
                case "jdk.nashorn.internal.objects":
                case "jdk.nashorn.internal.scripts":
                    break;
                default:
                    securityManager.checkPackageAccess(strSubstring);
                    break;
            }
        }
    }

    @Override // java.security.SecureClassLoader
    protected PermissionCollection getPermissions(CodeSource codeSource) {
        Permissions permissions = new Permissions();
        for (Permission permission : SCRIPT_PERMISSIONS) {
            permissions.add(permission);
        }
        return permissions;
    }

    static ClassLoader createClassLoader(String str, ClassLoader classLoader) {
        return URLClassLoader.newInstance(pathToURLs(str), classLoader);
    }

    private static URL[] pathToURLs(String str) throws IOException {
        String[] strArrSplit = str.split(File.pathSeparator);
        URL[] urlArr = new URL[strArrSplit.length];
        int i = 0;
        while (i < strArrSplit.length) {
            URL urlFileToURL = fileToURL(new File(strArrSplit[i]));
            if (urlFileToURL != null) {
                int i2 = i;
                i++;
                urlArr[i2] = urlFileToURL;
            }
        }
        if (urlArr.length != i) {
            URL[] urlArr2 = new URL[i];
            System.arraycopy(urlArr, 0, urlArr2, 0, i);
            urlArr = urlArr2;
        }
        return urlArr;
    }

    private static URL fileToURL(File file) throws IOException {
        String absolutePath;
        try {
            absolutePath = file.getCanonicalPath();
        } catch (IOException unused) {
            absolutePath = file.getAbsolutePath();
        }
        String strReplace = absolutePath.replace(File.separatorChar, '/');
        if (!strReplace.startsWith("/")) {
            strReplace = "/" + strReplace;
        }
        if (!file.isFile()) {
            strReplace = strReplace + "/";
        }
        try {
            return new URL("file", "", strReplace);
        } catch (MalformedURLException unused2) {
            throw new IllegalArgumentException("file");
        }
    }
}
