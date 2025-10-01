package jdk.nashorn.internal.codegen;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.PrivilegedAction;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.stream.Stream;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.options.Options;
import kotlin.jvm.internal.LongCompanionObject;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/OptimisticTypesPersistence.class */
public final class OptimisticTypesPersistence {
    private static final int DEFAULT_MAX_FILES = 0;
    private static final int UNLIMITED_FILES = -1;
    private static final int DEFAULT_CLEANUP_DELAY = 20;
    private static final String DEFAULT_CACHE_SUBDIR_NAME = "com.oracle.java.NashornTypeInfo";
    private static final Object[] locks;
    private static final long ERROR_REPORT_THRESHOLD = 60000;
    private static long lastReportedError;
    private static final AtomicBoolean scheduledCleanup;
    private static final Timer cleanupTimer;
    private static final int MAX_FILES = getMaxFiles();
    private static final int CLEANUP_DELAY = Math.max(0, Options.getIntProperty("nashorn.typeInfo.cleanupDelaySeconds", 20));
    private static final File baseCacheDir = createBaseCacheDir();
    private static final File cacheDir = createCacheDir(baseCacheDir);

    static {
        locks = cacheDir == null ? null : createLockArray();
        if (baseCacheDir == null || MAX_FILES == -1) {
            scheduledCleanup = null;
            cleanupTimer = null;
        } else {
            scheduledCleanup = new AtomicBoolean();
            cleanupTimer = new Timer(true);
        }
    }

    public static Object getLocationDescriptor(Source source, int i, Type[] typeArr) {
        if (cacheDir == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(48);
        sb.append(source.getDigest()).append('-').append(i);
        if (typeArr != null && typeArr.length > 0) {
            sb.append('-');
            for (Type type : typeArr) {
                sb.append(Type.getShortSignatureDescriptor(type));
            }
        }
        return new LocationDescriptor(new File(cacheDir, sb.toString()));
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/OptimisticTypesPersistence$LocationDescriptor.class */
    private static final class LocationDescriptor {
        private final File file;

        LocationDescriptor(File file) {
            this.file = file;
        }
    }

    public static void store(Object obj, Map map) {
        if (obj == null || map.isEmpty()) {
            return;
        }
        AccessController.doPrivileged(new PrivilegedAction(((LocationDescriptor) obj).file, map) { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.1
            final File val$file;
            final Map val$optimisticTypes;

            {
                this.val$file = file;
                this.val$optimisticTypes = map;
            }

            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            /* JADX WARN: Type inference failed for: r1v4, types: [java.io.FileOutputStream, java.lang.Throwable] */
            /* JADX WARN: Type inference failed for: r1v7, types: [java.io.FileOutputStream, java.lang.Throwable] */
            @Override // java.security.PrivilegedAction
            public Void run() {
                FileOutputStream fileOutputStream;
                synchronized (OptimisticTypesPersistence.getFileLock(this.val$file)) {
                    if (!this.val$file.exists()) {
                        OptimisticTypesPersistence.scheduleCleanup();
                    }
                    try {
                        fileOutputStream = new FileOutputStream(this.val$file);
                    } catch (Exception e) {
                        OptimisticTypesPersistence.reportError("write", this.val$file, e);
                    }
                    try {
                        fileOutputStream.getChannel().lock();
                        DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(fileOutputStream));
                        Type.writeTypeMap(this.val$optimisticTypes, dataOutputStream);
                        dataOutputStream.flush();
                        if (fileOutputStream != null) {
                            close();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return null;
            }
        });
    }

    public static Map load(Object obj) {
        if (obj == null) {
            return null;
        }
        return (Map) AccessController.doPrivileged(new PrivilegedAction(((LocationDescriptor) obj).file) { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.2
            final File val$file;

            {
                this.val$file = file;
            }

            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            /* JADX WARN: Type inference failed for: r1v4, types: [java.io.FileInputStream, java.lang.Throwable] */
            /* JADX WARN: Type inference failed for: r1v7, types: [java.io.FileInputStream, java.lang.Throwable] */
            @Override // java.security.PrivilegedAction
            public Map run() throws IOException {
                Map<Integer, Type> typeMap;
                try {
                    if (this.val$file.isFile()) {
                        synchronized (OptimisticTypesPersistence.getFileLock(this.val$file)) {
                            FileInputStream fileInputStream = new FileInputStream(this.val$file);
                            try {
                                fileInputStream.getChannel().lock(0L, LongCompanionObject.MAX_VALUE, true);
                                typeMap = Type.readTypeMap(new DataInputStream(new BufferedInputStream(fileInputStream)));
                                if (fileInputStream != null) {
                                    close();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                        return typeMap;
                    }
                    return null;
                } catch (Exception e) {
                    OptimisticTypesPersistence.reportError("read", this.val$file, e);
                    return null;
                }
            }
        });
    }

    private static void reportError(String str, File file, Exception exc) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - lastReportedError > 60000) {
            reportError(String.format("Failed to %s %s", str, file), exc);
            lastReportedError = jCurrentTimeMillis;
        }
    }

    private static void reportError(String str, Exception exc) {
        getLogger().warning(new Object[]{str, "\n", exceptionToString(exc)});
    }

    private static String exceptionToString(Exception exc) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter((Writer) stringWriter, false);
        exc.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.toString();
    }

    private static File createBaseCacheDir() {
        if (MAX_FILES == 0 || Options.getBooleanProperty("nashorn.typeInfo.disabled")) {
            return null;
        }
        try {
            return createBaseCacheDirPrivileged();
        } catch (Exception e) {
            reportError("Failed to create cache dir", e);
            return null;
        }
    }

    private static File createBaseCacheDirPrivileged() {
        return (File) AccessController.doPrivileged(new PrivilegedAction() { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.3
            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            @Override // java.security.PrivilegedAction
            public File run() {
                File file;
                String property = System.getProperty("nashorn.typeInfo.cacheDir");
                if (property != null) {
                    file = new File(property);
                } else {
                    file = new File(OptimisticTypesPersistence.getSystemCacheDir(), OptimisticTypesPersistence.DEFAULT_CACHE_SUBDIR_NAME);
                    if (OptimisticTypesPersistence.isSymbolicLink(file)) {
                        return null;
                    }
                }
                return file;
            }
        });
    }

    private static File createCacheDir(File file) {
        if (file == null) {
            return null;
        }
        try {
            return createCacheDirPrivileged(file);
        } catch (Exception e) {
            reportError("Failed to create cache dir", e);
            return null;
        }
    }

    private static File createCacheDirPrivileged(File file) {
        return (File) AccessController.doPrivileged(new PrivilegedAction(file) { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.4
            final File val$baseDir;

            {
                this.val$baseDir = file;
            }

            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            @Override // java.security.PrivilegedAction
            public File run() {
                try {
                    File file2 = new File(this.val$baseDir, OptimisticTypesPersistence.getVersionDirName());
                    if (OptimisticTypesPersistence.isSymbolicLink(file2)) {
                        return null;
                    }
                    file2.mkdirs();
                    if (file2.isDirectory()) {
                        OptimisticTypesPersistence.getLogger().info("Optimistic type persistence directory is " + file2);
                        return file2;
                    }
                    OptimisticTypesPersistence.getLogger().warning("Could not create optimistic type persistence directory " + file2);
                    return null;
                } catch (Exception e) {
                    OptimisticTypesPersistence.reportError("Failed to calculate version dir name", e);
                    return null;
                }
            }
        });
    }

    private static File getSystemCacheDir() {
        String property = System.getProperty("os.name", "generic");
        if ("Mac OS X".equals(property)) {
            return new File(new File(System.getProperty("user.home"), "Library"), "Caches");
        }
        if (property.startsWith("Windows")) {
            return new File(System.getProperty("java.io.tmpdir"));
        }
        return new File(System.getProperty("user.home"), ".cache");
    }

    /* JADX WARN: Type inference failed for: r1v13, types: [java.io.InputStream, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v18, types: [java.io.InputStream, java.lang.Throwable] */
    public static String getVersionDirName() {
        URL resource = OptimisticTypesPersistence.class.getResource("anchor.properties");
        String protocol = resource.getProtocol();
        if (protocol.equals("jar")) {
            String file = resource.getFile();
            InputStream inputStreamOpenStream = new URL(file.substring(0, file.indexOf(33))).openStream();
            try {
                byte[] bArr = new byte[131072];
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
                while (true) {
                    int i = inputStreamOpenStream.read(bArr);
                    if (i == -1) {
                        break;
                    }
                    messageDigest.update(bArr, 0, i);
                }
                String strEncodeToString = Base64.getUrlEncoder().withoutPadding().encodeToString(messageDigest.digest());
                if (inputStreamOpenStream != null) {
                    close();
                }
                return strEncodeToString;
            } catch (Throwable th) {
                throw th;
            }
        }
        if (protocol.equals("file")) {
            String file2 = resource.getFile();
            return "dev-" + new SimpleDateFormat("yyyyMMdd-HHmmss").format(new Date(getLastModifiedClassFile(new File(file2.substring(0, (file2.length() - OptimisticTypesPersistence.class.getName().lastIndexOf(46)) - 1)), 0L)));
        }
        throw new AssertionError();
    }

    private static long getLastModifiedClassFile(File file, long j) {
        long j2 = j;
        for (File file2 : file.listFiles()) {
            if (file2.getName().endsWith(".class")) {
                long jLastModified = file2.lastModified();
                if (jLastModified > j2) {
                    j2 = jLastModified;
                }
            } else if (file2.isDirectory()) {
                long lastModifiedClassFile = getLastModifiedClassFile(file2, j2);
                if (lastModifiedClassFile > j2) {
                    j2 = lastModifiedClassFile;
                }
            }
        }
        return j2;
    }

    private static boolean isSymbolicLink(File file) {
        if (Files.isSymbolicLink(file.toPath())) {
            getLogger().warning("Directory " + file + " is a symlink");
            return true;
        }
        return false;
    }

    private static Object[] createLockArray() {
        Object[] objArr = new Object[Runtime.getRuntime().availableProcessors() * 2];
        for (int i = 0; i < objArr.length; i++) {
            objArr[i] = new Object();
        }
        return objArr;
    }

    private static Object getFileLock(File file) {
        return locks[(file.hashCode() & Integer.MAX_VALUE) % locks.length];
    }

    private static DebugLogger getLogger() {
        try {
            return Context.getContext().getLogger(RecompilableScriptFunctionData.class);
        } catch (Exception e) {
            e.printStackTrace();
            return DebugLogger.DISABLED_LOGGER;
        }
    }

    private static void scheduleCleanup() {
        if (MAX_FILES != -1 && scheduledCleanup.compareAndSet(false, true)) {
            cleanupTimer.schedule(new TimerTask() { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.5
                @Override // java.util.TimerTask, java.lang.Runnable
                public void run() {
                    OptimisticTypesPersistence.scheduledCleanup.set(false);
                    try {
                        OptimisticTypesPersistence.doCleanup();
                    } catch (IOException unused) {
                    }
                }
            }, TimeUnit.SECONDS.toMillis(CLEANUP_DELAY));
        }
    }

    private static void doCleanup() throws IOException {
        Path[] allRegularFilesInLastModifiedOrder = getAllRegularFilesInLastModifiedOrder();
        int length = allRegularFilesInLastModifiedOrder.length;
        int iMax = Math.max(0, length - MAX_FILES);
        int i = 0;
        for (int i2 = 0; i2 < length && i < iMax; i2++) {
            try {
                Files.deleteIfExists(allRegularFilesInLastModifiedOrder[i2]);
                i++;
            } catch (Exception unused) {
            }
            allRegularFilesInLastModifiedOrder[i2] = null;
        }
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.Throwable, java.util.stream.Stream] */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.lang.Throwable, java.util.stream.Stream] */
    private static Path[] getAllRegularFilesInLastModifiedOrder() throws IOException {
        Stream<Path> streamWalk = Files.walk(baseCacheDir.toPath(), new FileVisitOption[0]);
        try {
            Path[] pathArr = (Path[]) streamWalk.filter(new Predicate() { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.9
                @Override // java.util.function.Predicate
                public boolean test(Object obj) {
                    return test((Path) obj);
                }

                public boolean test(Path path) {
                    return !Files.isDirectory(path, new LinkOption[0]);
                }
            }).map(new Function() { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.8
                @Override // java.util.function.Function
                public Object apply(Object obj) {
                    return apply((Path) obj);
                }

                public PathAndTime apply(Path path) {
                    return new PathAndTime(path);
                }
            }).sorted().map(new Function() { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.7
                @Override // java.util.function.Function
                public Object apply(Object obj) {
                    return apply((PathAndTime) obj);
                }

                public Path apply(PathAndTime pathAndTime) {
                    return pathAndTime.path;
                }
            }).toArray(new IntFunction() { // from class: jdk.nashorn.internal.codegen.OptimisticTypesPersistence.6
                /* JADX WARN: Failed to calculate best type for var: r6v0 ??
                java.lang.NullPointerException
                 */
                /* JADX WARN: Failed to set immutable type for var: r6v0 ??
                java.lang.NullPointerException
                 */
                /* JADX WARN: Multi-variable type inference failed. Error: java.lang.NullPointerException
                 */
                /* JADX WARN: Not initialized variable reg: 6, insn: 0x0003: MOVE (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r6 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) (LINE:511), block:B:2:0x0000 */
                @Override // java.util.function.IntFunction
                public Object apply(int i) {
                    int i2;
                    return new Path[i2];
                }
            });
            if (streamWalk != null) {
                close();
            }
            return pathArr;
        } catch (Throwable th) {
            throw th;
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/OptimisticTypesPersistence$PathAndTime.class */
    private static class PathAndTime implements Comparable {
        private final Path path;
        private final long time;

        @Override // java.lang.Comparable
        public int compareTo(Object obj) {
            return compareTo((PathAndTime) obj);
        }

        PathAndTime(Path path) {
            this.path = path;
            this.time = getTime(path);
        }

        public int compareTo(PathAndTime pathAndTime) {
            return Long.compare(this.time, pathAndTime.time);
        }

        private static long getTime(Path path) {
            try {
                return Files.getLastModifiedTime(path, new LinkOption[0]).toMillis();
            } catch (IOException unused) {
                return -1L;
            }
        }
    }

    private static int getMaxFiles() {
        String stringProperty = Options.getStringProperty("nashorn.typeInfo.maxFiles", null);
        if (stringProperty == null) {
            return 0;
        }
        if ("unlimited".equals(stringProperty)) {
            return -1;
        }
        return Math.max(0, Integer.parseInt(stringProperty));
    }
}
