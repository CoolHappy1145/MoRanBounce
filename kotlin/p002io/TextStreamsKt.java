package kotlin.p002io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffdX\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0012\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a\u0017\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u0017\u0010\ufffd\ufffd\u001a\u00020\u0005*\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004H\u0087\b\u001a\u001c\u0010\u0007\u001a\u00020\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\u00062\b\b\u0002\u0010\u0003\u001a\u00020\u0004\u001a\u001e\u0010\n\u001a\u00020\u000b*\u00020\u00022\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u000b0\r\u001a\u0010\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0010*\u00020\u0001\u001a\n\u0010\u0011\u001a\u00020\u0012*\u00020\u0013\u001a\u0010\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0015*\u00020\u0002\u001a\n\u0010\u0016\u001a\u00020\u000e*\u00020\u0002\u001a\u0017\u0010\u0016\u001a\u00020\u000e*\u00020\u00132\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0087\b\u001a\r\u0010\u0019\u001a\u00020\u001a*\u00020\u000eH\u0087\b\u001a5\u0010\u001b\u001a\u0002H\u001c\"\u0004\b\ufffd\ufffd\u0010\u001c*\u00020\u00022\u0018\u0010\u001d\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000e0\u0010\u0012\u0004\u0012\u0002H\u001c0\rH\u0086\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u001f\u0082\u0002\b\n\u0006\b\u0011(\u001e0\u0001\u00a8\u0006 "}, m27d2 = {"buffered", "Ljava/io/BufferedReader;", "Ljava/io/Reader;", "bufferSize", "", "Ljava/io/BufferedWriter;", "Ljava/io/Writer;", "copyTo", "", "out", "forEachLine", "", "action", "Lkotlin/Function1;", "", "lineSequence", "Lkotlin/sequences/Sequence;", "readBytes", "", "Ljava/net/URL;", "readLines", "", "readText", "charset", "Ljava/nio/charset/Charset;", "reader", "Ljava/io/StringReader;", "useLines", "T", "block", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/Reader;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib"})
@JvmName(name = "TextStreamsKt")
/* loaded from: L-out.jar:kotlin/io/TextStreamsKt.class */
public final class TextStreamsKt {
    static BufferedReader buffered$default(Reader reader, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 8192;
        }
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader, i);
    }

    @InlineOnly
    private static final BufferedReader buffered(@NotNull Reader reader, int i) {
        return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader, i);
    }

    static BufferedWriter buffered$default(Writer writer, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 8192;
        }
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer, i);
    }

    @InlineOnly
    private static final BufferedWriter buffered(@NotNull Writer writer, int i) {
        return writer instanceof BufferedWriter ? (BufferedWriter) writer : new BufferedWriter(writer, i);
    }

    public static final void forEachLine(@NotNull Reader forEachLine, @NotNull Function1 action) {
        Intrinsics.checkParameterIsNotNull(forEachLine, "$this$forEachLine");
        Intrinsics.checkParameterIsNotNull(action, "action");
        BufferedReader bufferedReader = forEachLine instanceof BufferedReader ? (BufferedReader) forEachLine : new BufferedReader(forEachLine, 8192);
        Throwable th = (Throwable) null;
        try {
            Iterator it = lineSequence(bufferedReader).iterator();
            while (it.hasNext()) {
                action.invoke(it.next());
            }
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(bufferedReader, th);
        } catch (Throwable th2) {
            throw th2;
        }
    }

    @NotNull
    public static final List readLines(@NotNull Reader readLines) {
        Intrinsics.checkParameterIsNotNull(readLines, "$this$readLines");
        ArrayList arrayList = new ArrayList();
        forEachLine(readLines, new Function1(arrayList) { // from class: kotlin.io.TextStreamsKt.readLines.1
            final ArrayList $result;

            @Override // kotlin.jvm.functions.Function1
            public Object invoke(Object obj) {
                invoke((String) obj);
                return Unit.INSTANCE;
            }

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
                this.$result = arrayList;
            }

            public final void invoke(@NotNull String it) {
                Intrinsics.checkParameterIsNotNull(it, "it");
                this.$result.add(it);
            }
        });
        return arrayList;
    }

    public static final Object useLines(@NotNull Reader useLines, @NotNull Function1 block) throws IOException {
        Intrinsics.checkParameterIsNotNull(useLines, "$this$useLines");
        Intrinsics.checkParameterIsNotNull(block, "block");
        BufferedReader bufferedReader = useLines instanceof BufferedReader ? (BufferedReader) useLines : new BufferedReader(useLines, 8192);
        Throwable th = (Throwable) null;
        try {
            Object objInvoke = block.invoke(lineSequence(bufferedReader));
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
                CloseableKt.closeFinally(bufferedReader, th);
            } else {
                bufferedReader.close();
            }
            return objInvoke;
        } catch (Throwable th2) {
            throw th2;
        }
    }

    @InlineOnly
    private static final StringReader reader(@NotNull String str) {
        return new StringReader(str);
    }

    @NotNull
    public static final Sequence lineSequence(@NotNull BufferedReader lineSequence) {
        Intrinsics.checkParameterIsNotNull(lineSequence, "$this$lineSequence");
        return SequencesKt.constrainOnce(new LinesSequence(lineSequence));
    }

    @NotNull
    public static final String readText(@NotNull Reader readText) {
        Intrinsics.checkParameterIsNotNull(readText, "$this$readText");
        StringWriter stringWriter = new StringWriter();
        copyTo$default(readText, stringWriter, 0, 2, null);
        String string = stringWriter.toString();
        Intrinsics.checkExpressionValueIsNotNull(string, "buffer.toString()");
        return string;
    }

    public static long copyTo$default(Reader reader, Writer writer, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 8192;
        }
        return copyTo(reader, writer, i);
    }

    public static final long copyTo(@NotNull Reader copyTo, @NotNull Writer out, int i) throws IOException {
        Intrinsics.checkParameterIsNotNull(copyTo, "$this$copyTo");
        Intrinsics.checkParameterIsNotNull(out, "out");
        long j = 0;
        char[] cArr = new char[i];
        int i2 = copyTo.read(cArr);
        while (true) {
            int i3 = i2;
            if (i3 >= 0) {
                out.write(cArr, 0, i3);
                j += i3;
                i2 = copyTo.read(cArr);
            } else {
                return j;
            }
        }
    }

    @InlineOnly
    private static final String readText(@NotNull URL url, Charset charset) {
        return new String(readBytes(url), charset);
    }

    static String readText$default(URL url, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return new String(readBytes(url), charset);
    }

    @NotNull
    public static final byte[] readBytes(@NotNull URL readBytes) throws IOException {
        Intrinsics.checkParameterIsNotNull(readBytes, "$this$readBytes");
        InputStream inputStreamOpenStream = readBytes.openStream();
        Throwable th = (Throwable) null;
        try {
            InputStream it = inputStreamOpenStream;
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            byte[] bytes = ByteStreamsKt.readBytes(it);
            CloseableKt.closeFinally(inputStreamOpenStream, th);
            return bytes;
        } catch (Throwable th2) {
            throw th2;
        }
    }
}
