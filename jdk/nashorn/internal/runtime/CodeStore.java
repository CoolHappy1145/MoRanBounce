package jdk.nashorn.internal.runtime;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.AccessControlException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;
import jdk.nashorn.internal.codegen.OptimisticTypesPersistence;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;
import jdk.nashorn.internal.runtime.options.Options;

@Logger(name = "codestore")
/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/CodeStore.class */
public abstract class CodeStore implements Loggable {
    public static final String NASHORN_PROVIDE_CODE_STORE = "nashorn.provideCodeStore";
    private DebugLogger log;

    public abstract StoredScript store(String str, Source source, StoredScript storedScript);

    public abstract StoredScript load(Source source, String str);

    protected CodeStore() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger initLogger(Context context) {
        this.log = context.getLogger(getClass());
        return this.log;
    }

    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger getLogger() {
        return this.log;
    }

    public static CodeStore newCodeStore(Context context) {
        try {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                securityManager.checkPermission(new RuntimePermission(NASHORN_PROVIDE_CODE_STORE));
            }
            Iterator it = ServiceLoader.load(CodeStore.class).iterator();
            if (it.hasNext()) {
                CodeStore codeStore = (CodeStore) it.next();
                codeStore.initLogger(context).info(new Object[]{"using code store provider ", codeStore.getClass().getCanonicalName()});
                return codeStore;
            }
        } catch (AccessControlException e) {
            context.getLogger(CodeStore.class).warning(new Object[]{"failed to load code store provider ", e});
        }
        try {
            DirectoryCodeStore directoryCodeStore = new DirectoryCodeStore(context);
            directoryCodeStore.initLogger(context);
            return directoryCodeStore;
        } catch (IOException e2) {
            context.getLogger(CodeStore.class).warning(new Object[]{"failed to create cache directory ", e2});
            return null;
        }
    }

    public StoredScript store(String str, Source source, String str2, Map map, Map map2, Object[] objArr, int i) {
        return store(str, source, storedScriptFor(source, str2, map, map2, objArr, i));
    }

    public StoredScript storedScriptFor(Source source, String str, Map map, Map map2, Object[] objArr, int i) {
        for (Object obj : objArr) {
            if (!(obj instanceof Serializable)) {
                getLogger().warning(new Object[]{"cannot store ", source, " non serializable constant ", obj});
                return null;
            }
        }
        return new StoredScript(i, str, map, map2, objArr);
    }

    public static String getCacheKey(Object obj, Type[] typeArr) {
        StringBuilder sbAppend = new StringBuilder().append(obj);
        if (typeArr != null && typeArr.length > 0) {
            sbAppend.append('-');
            for (Type type : typeArr) {
                sbAppend.append(Type.getShortSignatureDescriptor(type));
            }
        }
        return sbAppend.toString();
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/CodeStore$DirectoryCodeStore.class */
    public static class DirectoryCodeStore extends CodeStore {
        private static final int DEFAULT_MIN_SIZE = 1000;
        private final File dir;
        private final boolean readOnly;
        private final int minSize;

        public DirectoryCodeStore(Context context) {
            this(context, Options.getStringProperty("nashorn.persistent.code.cache", "nashorn_code_cache"), false, 1000);
        }

        public DirectoryCodeStore(Context context, String str, boolean z, int i) {
            this.dir = checkDirectory(str, context.getEnv(), z);
            this.readOnly = z;
            this.minSize = i;
        }

        private static File checkDirectory(String str, ScriptEnvironment scriptEnvironment, boolean z) throws IOException {
            try {
                return (File) AccessController.doPrivileged(new PrivilegedExceptionAction(str, scriptEnvironment, z) { // from class: jdk.nashorn.internal.runtime.CodeStore.DirectoryCodeStore.1
                    final String val$path;
                    final ScriptEnvironment val$env;
                    final boolean val$readOnly;

                    {
                        this.val$path = str;
                        this.val$env = scriptEnvironment;
                        this.val$readOnly = z;
                    }

                    @Override // java.security.PrivilegedExceptionAction
                    public Object run() {
                        return run();
                    }

                    @Override // java.security.PrivilegedExceptionAction
                    public File run() throws IOException {
                        File absoluteFile = new File(this.val$path, DirectoryCodeStore.getVersionDir(this.val$env)).getAbsoluteFile();
                        if (this.val$readOnly) {
                            if (!absoluteFile.exists() || !absoluteFile.isDirectory()) {
                                throw new IOException("Not a directory: " + absoluteFile.getPath());
                            }
                            if (!absoluteFile.canRead()) {
                                throw new IOException("Directory not readable: " + absoluteFile.getPath());
                            }
                        } else {
                            if (!absoluteFile.exists() && !absoluteFile.mkdirs()) {
                                throw new IOException("Could not create directory: " + absoluteFile.getPath());
                            }
                            if (!absoluteFile.isDirectory()) {
                                throw new IOException("Not a directory: " + absoluteFile.getPath());
                            }
                            if (!absoluteFile.canRead() || !absoluteFile.canWrite()) {
                                throw new IOException("Directory not readable or writable: " + absoluteFile.getPath());
                            }
                        }
                        return absoluteFile;
                    }
                });
            } catch (PrivilegedActionException e) {
                throw ((IOException) e.getException());
            }
        }

        private static String getVersionDir(ScriptEnvironment scriptEnvironment) throws IOException {
            try {
                String versionDirName = OptimisticTypesPersistence.getVersionDirName();
                return scriptEnvironment._optimistic_types ? versionDirName + "_opt" : versionDirName;
            } catch (Exception e) {
                throw new IOException(e);
            }
        }

        @Override // jdk.nashorn.internal.runtime.CodeStore
        public StoredScript load(Source source, String str) {
            if (belowThreshold(source)) {
                return null;
            }
            try {
                return (StoredScript) AccessController.doPrivileged(new PrivilegedExceptionAction(this, getCacheFile(source, str), source, str) { // from class: jdk.nashorn.internal.runtime.CodeStore.DirectoryCodeStore.2
                    final File val$file;
                    final Source val$source;
                    final String val$functionKey;
                    final DirectoryCodeStore this$0;

                    {
                        this.this$0 = this;
                        this.val$file = file;
                        this.val$source = source;
                        this.val$functionKey = str;
                    }

                    @Override // java.security.PrivilegedExceptionAction
                    public Object run() {
                        return run();
                    }

                    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.ObjectInputStream, java.lang.Throwable] */
                    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.ObjectInputStream, java.lang.Throwable] */
                    @Override // java.security.PrivilegedExceptionAction
                    public StoredScript run() throws IOException {
                        if (!this.val$file.exists()) {
                            return null;
                        }
                        ObjectInputStream objectInputStream = new ObjectInputStream(new BufferedInputStream(new FileInputStream(this.val$file)));
                        try {
                            StoredScript storedScript = (StoredScript) objectInputStream.readObject();
                            this.this$0.getLogger().info(new Object[]{"loaded ", this.val$source, "-", this.val$functionKey});
                            if (objectInputStream != null) {
                                close();
                            }
                            return storedScript;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                });
            } catch (PrivilegedActionException e) {
                getLogger().warning(new Object[]{"failed to load ", source, "-", str, ": ", e.getException()});
                return null;
            }
        }

        @Override // jdk.nashorn.internal.runtime.CodeStore
        public StoredScript store(String str, Source source, StoredScript storedScript) {
            if (this.readOnly || storedScript == null || belowThreshold(source)) {
                return null;
            }
            try {
                return (StoredScript) AccessController.doPrivileged(new PrivilegedExceptionAction(this, getCacheFile(source, str), storedScript, source, str) { // from class: jdk.nashorn.internal.runtime.CodeStore.DirectoryCodeStore.3
                    final File val$file;
                    final StoredScript val$script;
                    final Source val$source;
                    final String val$functionKey;
                    final DirectoryCodeStore this$0;

                    {
                        this.this$0 = this;
                        this.val$file = file;
                        this.val$script = storedScript;
                        this.val$source = source;
                        this.val$functionKey = str;
                    }

                    @Override // java.security.PrivilegedExceptionAction
                    public Object run() {
                        return run();
                    }

                    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.ObjectOutputStream, java.lang.Throwable] */
                    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.ObjectOutputStream, java.lang.Throwable] */
                    @Override // java.security.PrivilegedExceptionAction
                    public StoredScript run() throws IOException {
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(this.val$file)));
                        try {
                            objectOutputStream.writeObject(this.val$script);
                            if (objectOutputStream != null) {
                                close();
                            }
                            this.this$0.getLogger().info(new Object[]{"stored ", this.val$source, "-", this.val$functionKey});
                            return this.val$script;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                });
            } catch (PrivilegedActionException e) {
                getLogger().warning(new Object[]{"failed to store ", storedScript, "-", str, ": ", e.getException()});
                return null;
            }
        }

        private File getCacheFile(Source source, String str) {
            return new File(this.dir, source.getDigest() + '-' + str);
        }

        private boolean belowThreshold(Source source) {
            if (source.getLength() < this.minSize) {
                getLogger().info(new Object[]{"below size threshold ", source});
                return true;
            }
            return false;
        }
    }
}
