package jdk.nashorn.internal.runtime;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.WeakHashMap;
import jdk.nashorn.api.scripting.URLReader;
import jdk.nashorn.internal.runtime.DebuggerSupport;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(name = "source")
/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/Source.class */
public final class Source implements Loggable {
    private static final int BUF_SIZE = 8192;
    private static final Cache CACHE = new Cache(null);
    private static final Base64.Encoder BASE64 = Base64.getUrlEncoder().withoutPadding();
    private final String name;
    private final String base;
    private final Data data;
    private int hash;
    private byte[] digest;
    private String explicitURL;

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/Source$Data.class */
    private interface Data {
        URL url();

        int length();

        long lastModified();

        char[] array();

        boolean isEvalCode();
    }

    private Source(String str, String str2, Data data) {
        this.name = str;
        this.base = str2;
        this.data = data;
    }

    private static Source sourceFor(String str, String str2, URLData uRLData) throws IOException {
        try {
            Source source = new Source(str, str2, uRLData);
            Source source2 = CACHE.get(source);
            if (source2 != null) {
                uRLData.checkPermissionAndClose();
                return source2;
            }
            uRLData.load();
            CACHE.put(source, source);
            return source;
        } catch (RuntimeException e) {
            Throwable cause = e.getCause();
            if (cause instanceof IOException) {
                throw ((IOException) cause);
            }
            throw e;
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/Source$Cache.class */
    private static class Cache extends WeakHashMap {
        static final boolean $assertionsDisabled;

        private Cache() {
        }

        Cache(C02391 c02391) {
            this();
        }

        static {
            $assertionsDisabled = !Source.class.desiredAssertionStatus();
        }

        public Source get(Source source) {
            WeakReference weakReference = (WeakReference) super.get((Object) source);
            if (weakReference == null) {
                return null;
            }
            return (Source) weakReference.get();
        }

        public void put(Source source, Source source2) {
            if (!$assertionsDisabled && (source2.data instanceof RawData)) {
                throw new AssertionError();
            }
            put((Cache) source, (Source) new WeakReference(source2));
        }
    }

    DebuggerSupport.SourceInfo getSourceInfo() {
        return new DebuggerSupport.SourceInfo(getName(), this.data.hashCode(), this.data.url(), this.data.array());
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/Source$RawData.class */
    private static class RawData implements Data {
        private final char[] array;
        private final boolean evalCode;
        private int hash;

        RawData(char[] cArr, boolean z, C02391 c02391) {
            this(cArr, z);
        }

        RawData(String str, boolean z, C02391 c02391) {
            this(str, z);
        }

        RawData(Reader reader, C02391 c02391) {
            this(reader);
        }

        private RawData(char[] cArr, boolean z) {
            this.array = (char[]) Objects.requireNonNull(cArr);
            this.evalCode = z;
        }

        private RawData(String str, boolean z) {
            this.array = ((String) Objects.requireNonNull(str)).toCharArray();
            this.evalCode = z;
        }

        private RawData(Reader reader) {
            this(Source.readFully(reader), false);
        }

        public int hashCode() {
            int i = this.hash;
            if (i == 0) {
                int iHashCode = Arrays.hashCode(this.array) ^ (this.evalCode ? 1 : 0);
                this.hash = iHashCode;
                i = iHashCode;
            }
            return i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj instanceof RawData) {
                RawData rawData = (RawData) obj;
                return Arrays.equals(this.array, rawData.array) && this.evalCode == rawData.evalCode;
            }
            return false;
        }

        public String toString() {
            return new String(array());
        }

        @Override // jdk.nashorn.internal.runtime.Source.Data
        public int length() {
            return this.array.length;
        }

        @Override // jdk.nashorn.internal.runtime.Source.Data
        public char[] array() {
            return this.array;
        }

        @Override // jdk.nashorn.internal.runtime.Source.Data
        public boolean isEvalCode() {
            return this.evalCode;
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/Source$URLData.class */
    private static class URLData implements Data {
        private final URL url;

        /* renamed from: cs */
        protected final Charset f52cs;
        private int hash;
        protected char[] array;
        protected int length;
        protected long lastModified;
        static final boolean $assertionsDisabled;

        URLData(URL url, Charset charset, C02391 c02391) {
            this(url, charset);
        }

        static {
            $assertionsDisabled = !Source.class.desiredAssertionStatus();
        }

        private URLData(URL url, Charset charset) {
            this.url = (URL) Objects.requireNonNull(url);
            this.f52cs = charset;
        }

        public int hashCode() {
            int i = this.hash;
            if (i == 0) {
                int iHashCode = this.url.hashCode();
                this.hash = iHashCode;
                i = iHashCode;
            }
            return i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof URLData)) {
                return false;
            }
            URLData uRLData = (URLData) obj;
            if (this.url.equals(uRLData.url)) {
                try {
                    if (isDeferred()) {
                        if (!$assertionsDisabled && uRLData.isDeferred()) {
                            throw new AssertionError();
                        }
                        loadMeta();
                    } else if (uRLData.isDeferred()) {
                        uRLData.loadMeta();
                    }
                    return this.length == uRLData.length && this.lastModified == uRLData.lastModified;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return false;
        }

        public String toString() {
            return new String(array());
        }

        @Override // jdk.nashorn.internal.runtime.Source.Data
        public URL url() {
            return this.url;
        }

        @Override // jdk.nashorn.internal.runtime.Source.Data
        public int length() {
            return this.length;
        }

        @Override // jdk.nashorn.internal.runtime.Source.Data
        public long lastModified() {
            return this.lastModified;
        }

        @Override // jdk.nashorn.internal.runtime.Source.Data
        public char[] array() {
            if ($assertionsDisabled || !isDeferred()) {
                return this.array;
            }
            throw new AssertionError();
        }

        boolean isDeferred() {
            return this.array == null;
        }

        /* JADX WARN: Type inference failed for: r1v0, types: [java.io.InputStream, java.lang.Throwable] */
        protected void checkPermissionAndClose() throws IOException {
            if (this.url.openStream() != null) {
                close();
            }
            Source.debug(new Object[]{"permission checked for ", this.url});
        }

        /* JADX WARN: Type inference failed for: r1v0, types: [java.io.InputStream, java.lang.Throwable] */
        /* JADX WARN: Type inference failed for: r1v13, types: [java.io.InputStream, java.lang.Throwable] */
        protected void load() throws IOException {
            if (this.array == null) {
                URLConnection uRLConnectionOpenConnection = this.url.openConnection();
                InputStream inputStream = uRLConnectionOpenConnection.getInputStream();
                try {
                    this.array = this.f52cs == null ? Source.readFully(inputStream) : Source.readFully(inputStream, this.f52cs);
                    this.length = this.array.length;
                    this.lastModified = uRLConnectionOpenConnection.getLastModified();
                    Source.debug(new Object[]{"loaded content for ", this.url});
                    if (inputStream != null) {
                        close();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        protected void loadMeta() throws IOException {
            if (this.length == 0 && this.lastModified == 0) {
                URLConnection uRLConnectionOpenConnection = this.url.openConnection();
                this.length = uRLConnectionOpenConnection.getContentLength();
                this.lastModified = uRLConnectionOpenConnection.getLastModified();
                Source.debug(new Object[]{"loaded metadata for ", this.url});
            }
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/Source$FileData.class */
    private static class FileData extends URLData {
        private final File file;

        FileData(File file, Charset charset, C02391 c02391) {
            this(file, charset);
        }

        private FileData(File file, Charset charset) {
            super(Source.getURLFromFile(file), charset, null);
            this.file = file;
        }

        @Override // jdk.nashorn.internal.runtime.Source.URLData
        protected void checkPermissionAndClose() throws FileNotFoundException {
            if (this.file.canRead()) {
                Source.debug(new Object[]{"permission checked for ", this.file});
                return;
            }
            throw new FileNotFoundException(this.file + " (Permission Denied)");
        }

        @Override // jdk.nashorn.internal.runtime.Source.URLData
        protected void loadMeta() {
            if (this.length == 0 && this.lastModified == 0) {
                this.length = (int) this.file.length();
                this.lastModified = this.file.lastModified();
                Source.debug(new Object[]{"loaded metadata for ", this.file});
            }
        }

        @Override // jdk.nashorn.internal.runtime.Source.URLData
        protected void load() {
            if (this.array == null) {
                this.array = this.f52cs == null ? Source.readFully(this.file) : Source.readFully(this.file, this.f52cs);
                this.length = this.array.length;
                this.lastModified = this.file.lastModified();
                Source.debug(new Object[]{"loaded content for ", this.file});
            }
        }
    }

    private static void debug(Object[] objArr) {
        DebugLogger loggerStatic = getLoggerStatic();
        if (loggerStatic != null) {
            loggerStatic.info(objArr);
        }
    }

    private char[] data() {
        return this.data.array();
    }

    public static Source sourceFor(String str, char[] cArr, boolean z) {
        return new Source(str, baseName(str), new RawData(cArr, z, (C02391) null));
    }

    public static Source sourceFor(String str, char[] cArr) {
        return sourceFor(str, cArr, false);
    }

    public static Source sourceFor(String str, String str2, boolean z) {
        return new Source(str, baseName(str), new RawData(str2, z, (C02391) null));
    }

    public static Source sourceFor(String str, String str2) {
        return sourceFor(str, str2, false);
    }

    public static Source sourceFor(String str, URL url) {
        return sourceFor(str, url, (Charset) null);
    }

    public static Source sourceFor(String str, URL url, Charset charset) {
        return sourceFor(str, baseURL(url), new URLData(url, charset, null));
    }

    public static Source sourceFor(String str, File file) {
        return sourceFor(str, file, (Charset) null);
    }

    public static Source sourceFor(String str, File file, Charset charset) {
        return sourceFor(str, dirName(file.getAbsoluteFile(), null), new FileData(file, charset, null));
    }

    public static Source sourceFor(String str, Reader reader) {
        if (reader instanceof URLReader) {
            URLReader uRLReader = (URLReader) reader;
            return sourceFor(str, uRLReader.getURL(), uRLReader.getCharset());
        }
        return new Source(str, baseName(str), new RawData(reader, (C02391) null));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Source)) {
            return false;
        }
        Source source = (Source) obj;
        return Objects.equals(this.name, source.name) && this.data.equals(source.data);
    }

    public int hashCode() {
        int i = this.hash;
        if (i == 0) {
            int iHashCode = this.data.hashCode() ^ Objects.hashCode(this.name);
            this.hash = iHashCode;
            i = iHashCode;
        }
        return i;
    }

    public String getString() {
        return this.data.toString();
    }

    public String getName() {
        return this.name;
    }

    public long getLastModified() {
        return this.data.lastModified();
    }

    public String getBase() {
        return this.base;
    }

    public String getString(int i, int i2) {
        return new String(data(), i, i2);
    }

    public String getString(long j) {
        return new String(data(), (int) (j >>> 32), ((int) j) >>> 8);
    }

    public URL getURL() {
        return this.data.url();
    }

    public String getExplicitURL() {
        return this.explicitURL;
    }

    public void setExplicitURL(String str) {
        this.explicitURL = str;
    }

    public boolean isEvalCode() {
        return this.data.isEvalCode();
    }

    private int findBOLN(int i) {
        char[] cArrData = data();
        for (int i2 = i - 1; i2 > 0; i2--) {
            char c = cArrData[i2];
            if (c == '\n' || c == '\r') {
                return i2 + 1;
            }
        }
        return 0;
    }

    private int findEOLN(int i) {
        char[] cArrData = data();
        int length = cArrData.length;
        for (int i2 = i; i2 < length; i2++) {
            char c = cArrData[i2];
            if (c == '\n' || c == '\r') {
                return i2 - 1;
            }
        }
        return length - 1;
    }

    public int getLine(int i) {
        char[] cArrData = data();
        int i2 = 1;
        for (int i3 = 0; i3 < i; i3++) {
            if (cArrData[i3] == '\n') {
                i2++;
            }
        }
        return i2;
    }

    public int getColumn(int i) {
        return i - findBOLN(i);
    }

    public String getSourceLine(int i) {
        int iFindBOLN = findBOLN(i);
        return new String(data(), iFindBOLN, (findEOLN(i) - iFindBOLN) + 1);
    }

    public char[] getContent() {
        return data();
    }

    public int getLength() {
        return this.data.length();
    }

    public static char[] readFully(Reader reader) throws IOException {
        char[] cArr = new char[8192];
        StringBuilder sb = new StringBuilder();
        while (true) {
            try {
                int i = reader.read(cArr, 0, cArr.length);
                if (i > 0) {
                    sb.append(cArr, 0, i);
                } else {
                    return sb.toString().toCharArray();
                }
            } finally {
                reader.close();
            }
        }
    }

    public static char[] readFully(File file) throws IOException {
        if (!file.isFile()) {
            throw new IOException(file + " is not a file");
        }
        return byteToCharArray(Files.readAllBytes(file.toPath()));
    }

    public static char[] readFully(File file, Charset charset) throws IOException {
        if (!file.isFile()) {
            throw new IOException(file + " is not a file");
        }
        byte[] allBytes = Files.readAllBytes(file.toPath());
        return charset != null ? new String(allBytes, charset).toCharArray() : byteToCharArray(allBytes);
    }

    public static char[] readFully(URL url) {
        return readFully(url.openStream());
    }

    public static char[] readFully(URL url, Charset charset) {
        return readFully(url.openStream(), charset);
    }

    public String getDigest() {
        return new String(getDigestBytes(), StandardCharsets.US_ASCII);
    }

    private byte[] getDigestBytes() throws NoSuchAlgorithmException {
        byte[] bArr = this.digest;
        if (bArr == null) {
            char[] cArrData = data();
            byte[] bArr2 = new byte[cArrData.length * 2];
            for (int i = 0; i < cArrData.length; i++) {
                bArr2[i * 2] = (byte) (cArrData[i] & '\u00ff');
                bArr2[(i * 2) + 1] = (byte) ((cArrData[i] & '\uff00') >> 8);
            }
            try {
                MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
                if (this.name != null) {
                    messageDigest.update(this.name.getBytes(StandardCharsets.UTF_8));
                }
                if (this.base != null) {
                    messageDigest.update(this.base.getBytes(StandardCharsets.UTF_8));
                }
                if (getURL() != null) {
                    messageDigest.update(getURL().toString().getBytes(StandardCharsets.UTF_8));
                }
                byte[] bArrEncode = BASE64.encode(messageDigest.digest(bArr2));
                bArr = bArrEncode;
                this.digest = bArrEncode;
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException(e);
            }
        }
        return bArr;
    }

    public static String baseURL(URL url) {
        if (url.getProtocol().equals("file")) {
            try {
                Path parent = Paths.get(url.toURI()).getParent();
                if (parent != null) {
                    return parent + File.separator;
                }
                return null;
            } catch (IOError | SecurityException | URISyntaxException unused) {
                return null;
            }
        }
        String path = url.getPath();
        if (path.isEmpty()) {
            return null;
        }
        try {
            return new URL(url.getProtocol(), url.getHost(), url.getPort(), path.substring(0, path.lastIndexOf(47) + 1)).toString();
        } catch (MalformedURLException unused2) {
            return null;
        }
    }

    private static String dirName(File file, String str) {
        String parent = file.getParent();
        return parent != null ? parent + File.separator : str;
    }

    private static String baseName(String str) {
        int iLastIndexOf = str.lastIndexOf(47);
        if (iLastIndexOf == -1) {
            iLastIndexOf = str.lastIndexOf(92);
        }
        if (iLastIndexOf != -1) {
            return str.substring(0, iLastIndexOf + 1);
        }
        return null;
    }

    private static char[] readFully(InputStream inputStream, Charset charset) {
        return charset != null ? new String(readBytes(inputStream), charset).toCharArray() : readFully(inputStream);
    }

    private static char[] readFully(InputStream inputStream) {
        return byteToCharArray(readBytes(inputStream));
    }

    private static char[] byteToCharArray(byte[] bArr) {
        Charset charsetForName = StandardCharsets.UTF_8;
        int i = 0;
        if (bArr.length > 1 && bArr[0] == -2 && bArr[1] == -1) {
            i = 2;
            charsetForName = StandardCharsets.UTF_16BE;
        } else if (bArr.length > 1 && bArr[0] == -1 && bArr[1] == -2) {
            if (bArr.length > 3 && bArr[2] == 0 && bArr[3] == 0) {
                i = 4;
                charsetForName = Charset.forName("UTF-32LE");
            } else {
                i = 2;
                charsetForName = StandardCharsets.UTF_16LE;
            }
        } else if (bArr.length > 2 && bArr[0] == -17 && bArr[1] == -69 && bArr[2] == -65) {
            i = 3;
            charsetForName = StandardCharsets.UTF_8;
        } else if (bArr.length > 3 && bArr[0] == 0 && bArr[1] == 0 && bArr[2] == -2 && bArr[3] == -1) {
            i = 4;
            charsetForName = Charset.forName("UTF-32BE");
        }
        return new String(bArr, i, bArr.length - i, charsetForName).toCharArray();
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [java.io.ByteArrayOutputStream, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.ByteArrayOutputStream, java.lang.Throwable] */
    static byte[] readBytes(InputStream inputStream) throws IOException {
        byte[] bArr = new byte[8192];
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            while (true) {
                try {
                    int i = inputStream.read(bArr, 0, bArr.length);
                    if (i <= 0) {
                        break;
                    }
                    byteArrayOutputStream.write(bArr, 0, i);
                } catch (Throwable th) {
                    throw th;
                }
            }
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            if (byteArrayOutputStream != null) {
                close();
            }
            return byteArray;
        } finally {
            inputStream.close();
        }
    }

    public String toString() {
        return getName();
    }

    private static URL getURLFromFile(File file) {
        try {
            return file.toURI().toURL();
        } catch (SecurityException | MalformedURLException unused) {
            return null;
        }
    }

    private static DebugLogger getLoggerStatic() {
        Context contextTrustedOrNull = Context.getContextTrustedOrNull();
        if (contextTrustedOrNull == null) {
            return null;
        }
        return contextTrustedOrNull.getLogger(Source.class);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger initLogger(Context context) {
        return context.getLogger(getClass());
    }

    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger getLogger() {
        return initLogger(Context.getContextTrusted());
    }

    private File dumpFile(File file) {
        URL url = getURL();
        StringBuilder sb = new StringBuilder();
        sb.append(LocalDateTime.now().toString());
        sb.append('_');
        if (url != null) {
            sb.append(url.toString().replace('/', '_').replace('\\', '_'));
        } else {
            sb.append(getName());
        }
        return new File(file, sb.toString());
    }

    /* JADX WARN: Type inference failed for: r1v4, types: [java.io.FileOutputStream, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v9, types: [java.io.FileOutputStream, java.lang.Throwable] */
    void dump(String str) {
        File file = new File(str);
        File fileDumpFile = dumpFile(file);
        if (!file.exists() && !file.mkdirs()) {
            debug(new Object[]{"Skipping source dump for " + this.name});
            return;
        }
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(fileDumpFile);
            try {
                PrintWriter printWriter = new PrintWriter(fileOutputStream);
                printWriter.print(this.data.toString());
                printWriter.flush();
                if (fileOutputStream != null) {
                    close();
                }
            } catch (Throwable th) {
                throw th;
            }
        } catch (IOException e) {
            debug(new Object[]{"Skipping source dump for " + this.name + ": " + ECMAErrors.getMessage("io.error.cant.write", new String[]{str.toString() + " : " + e.toString()})});
        }
    }
}
