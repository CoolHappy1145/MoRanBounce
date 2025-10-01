package jdk.nashorn.internal.runtime;

import java.security.CodeSource;
import java.util.Objects;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/ScriptLoader.class */
final class ScriptLoader extends NashornLoader {
    private static final String NASHORN_PKG_PREFIX = "jdk.nashorn.internal.";
    private final Context context;

    Context getContext() {
        return this.context;
    }

    ScriptLoader(Context context) {
        super(context.getStructLoader());
        this.context = context;
    }

    @Override // java.lang.ClassLoader
    protected Class loadClass(String str, boolean z) {
        checkPackageAccess(str);
        return super.loadClass(str, z);
    }

    @Override // java.lang.ClassLoader
    protected Class findClass(String str) throws ClassNotFoundException {
        ClassLoader appLoader = this.context.getAppLoader();
        if (appLoader == null || str.startsWith(NASHORN_PKG_PREFIX)) {
            throw new ClassNotFoundException(str);
        }
        return appLoader.loadClass(str);
    }

    Class installClass(String str, byte[] bArr, CodeSource codeSource) {
        return defineClass(str, bArr, 0, bArr.length, (CodeSource) Objects.requireNonNull(codeSource));
    }
}
