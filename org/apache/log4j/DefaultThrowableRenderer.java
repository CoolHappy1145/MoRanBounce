package org.apache.log4j;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import org.apache.log4j.spi.ThrowableRenderer;

/* loaded from: L-out.jar:org/apache/log4j/DefaultThrowableRenderer.class */
public final class DefaultThrowableRenderer implements ThrowableRenderer {
    @Override // org.apache.log4j.spi.ThrowableRenderer
    public String[] doRender(Throwable th) {
        return render(th);
    }

    public static String[] render(Throwable th) throws IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        try {
            th.printStackTrace(printWriter);
        } catch (RuntimeException unused) {
        }
        printWriter.flush();
        LineNumberReader lineNumberReader = new LineNumberReader(new StringReader(stringWriter.toString()));
        ArrayList arrayList = new ArrayList();
        try {
            for (String line = lineNumberReader.readLine(); line != null; line = lineNumberReader.readLine()) {
                arrayList.add(line);
            }
        } catch (IOException e) {
            if (e instanceof InterruptedIOException) {
                Thread.currentThread().interrupt();
            }
            arrayList.add(e.toString());
        }
        String[] strArr = new String[arrayList.size()];
        arrayList.toArray(strArr);
        return strArr;
    }
}
