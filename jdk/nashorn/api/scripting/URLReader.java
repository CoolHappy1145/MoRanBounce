package jdk.nashorn.api.scripting;

import java.io.CharArrayReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Objects;
import jdk.Exported;
import jdk.nashorn.internal.runtime.Source;

@Exported
/* loaded from: L-out.jar:jdk/nashorn/api/scripting/URLReader.class */
public final class URLReader extends Reader {
    private final URL url;

    /* renamed from: cs */
    private final Charset f6cs;
    private Reader reader;

    public URLReader(URL url) {
        this(url, (Charset) null);
    }

    public URLReader(URL url, String str) {
        this(url, Charset.forName(str));
    }

    public URLReader(URL url, Charset charset) {
        this.url = (URL) Objects.requireNonNull(url);
        this.f6cs = charset;
    }

    @Override // java.io.Reader
    public int read(char[] cArr, int i, int i2) {
        return getReader().read(cArr, i, i2);
    }

    @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        getReader().close();
    }

    public URL getURL() {
        return this.url;
    }

    public Charset getCharset() {
        return this.f6cs;
    }

    private Reader getReader() {
        synchronized (this.lock) {
            if (this.reader == null) {
                this.reader = new CharArrayReader(Source.readFully(this.url, this.f6cs));
            }
        }
        return this.reader;
    }
}
