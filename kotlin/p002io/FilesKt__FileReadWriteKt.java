package kotlin.p002io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 5, m30xi = 1, m26d1 = {"\ufffd\ufffdz\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\u001a\u0012\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010\u0005\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a!\u0010\n\u001a\u00020\u000b*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\u0087\b\u001a!\u0010\u000e\u001a\u00020\u000f*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\f\u001a\u00020\rH\u0087\b\u001aB\u0010\u0010\u001a\u00020\u0001*\u00020\u000226\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001aJ\u0010\u0010\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0017\u001a\u00020\r26\u0010\u0011\u001a2\u0012\u0013\u0012\u00110\u0004\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\r\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u0016\u0012\u0004\u0012\u00020\u00010\u0012\u001a7\u0010\u0018\u001a\u00020\u0001*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2!\u0010\u0011\u001a\u001d\u0012\u0013\u0012\u00110\u0007\u00a2\u0006\f\b\u0013\u0012\b\b\u0014\u0012\u0004\b\b(\u001a\u0012\u0004\u0012\u00020\u00010\u0019\u001a\r\u0010\u001b\u001a\u00020\u001c*\u00020\u0002H\u0087\b\u001a\r\u0010\u001d\u001a\u00020\u001e*\u00020\u0002H\u0087\b\u001a\u0017\u0010\u001f\u001a\u00020 *\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u001a\n\u0010!\u001a\u00020\u0004*\u00020\u0002\u001a\u001a\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00070#*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0014\u0010$\u001a\u00020\u0007*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010%\u001a\u00020&*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u001a?\u0010'\u001a\u0002H(\"\u0004\b\ufffd\ufffd\u0010(*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\t2\u0018\u0010)\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070*\u0012\u0004\u0012\u0002H(0\u0019H\u0086\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010,\u001a\u0012\u0010-\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u0004\u001a\u001c\u0010.\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t\u001a\u0017\u0010/\u001a\u000200*\u00020\u00022\b\b\u0002\u0010\b\u001a\u00020\tH\u0087\b\u0082\u0002\b\n\u0006\b\u0011(+0\u0001\u00a8\u00061"}, m27d2 = {"appendBytes", "", "Ljava/io/File;", "array", "", "appendText", "text", "", "charset", "Ljava/nio/charset/Charset;", "bufferedReader", "Ljava/io/BufferedReader;", "bufferSize", "", "bufferedWriter", "Ljava/io/BufferedWriter;", "forEachBlock", "action", "Lkotlin/Function2;", "Lkotlin/ParameterName;", "name", "buffer", "bytesRead", "blockSize", "forEachLine", "Lkotlin/Function1;", "line", "inputStream", "Ljava/io/FileInputStream;", "outputStream", "Ljava/io/FileOutputStream;", "printWriter", "Ljava/io/PrintWriter;", "readBytes", "readLines", "", "readText", "reader", "Ljava/io/InputStreamReader;", "useLines", "T", "block", "Lkotlin/sequences/Sequence;", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/File;Ljava/nio/charset/Charset;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "writeBytes", "writeText", "writer", "Ljava/io/OutputStreamWriter;", "kotlin-stdlib"}, m28xs = "kotlin/io/FilesKt")
/* loaded from: L-out.jar:kotlin/io/FilesKt__FileReadWriteKt.class */
class FilesKt__FileReadWriteKt extends FilesKt__FilePathComponentsKt {
    static InputStreamReader reader$default(File file, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return new InputStreamReader(new FileInputStream(file), charset);
    }

    @InlineOnly
    private static final InputStreamReader reader(@NotNull File file, Charset charset) {
        return new InputStreamReader(new FileInputStream(file), charset);
    }

    static BufferedReader bufferedReader$default(File file, Charset charset, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if ((i2 & 2) != 0) {
            i = 8192;
        }
        Reader inputStreamReader = new InputStreamReader(new FileInputStream(file), charset);
        return inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, i);
    }

    @InlineOnly
    private static final BufferedReader bufferedReader(@NotNull File file, Charset charset, int i) {
        Reader inputStreamReader = new InputStreamReader(new FileInputStream(file), charset);
        return inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, i);
    }

    static OutputStreamWriter writer$default(File file, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return new OutputStreamWriter(new FileOutputStream(file), charset);
    }

    @InlineOnly
    private static final OutputStreamWriter writer(@NotNull File file, Charset charset) {
        return new OutputStreamWriter(new FileOutputStream(file), charset);
    }

    static BufferedWriter bufferedWriter$default(File file, Charset charset, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        if ((i2 & 2) != 0) {
            i = 8192;
        }
        Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), charset);
        return outputStreamWriter instanceof BufferedWriter ? (BufferedWriter) outputStreamWriter : new BufferedWriter(outputStreamWriter, i);
    }

    @InlineOnly
    private static final BufferedWriter bufferedWriter(@NotNull File file, Charset charset, int i) {
        Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), charset);
        return outputStreamWriter instanceof BufferedWriter ? (BufferedWriter) outputStreamWriter : new BufferedWriter(outputStreamWriter, i);
    }

    static PrintWriter printWriter$default(File file, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), charset);
        return new PrintWriter(outputStreamWriter instanceof BufferedWriter ? (BufferedWriter) outputStreamWriter : new BufferedWriter(outputStreamWriter, 8192));
    }

    @InlineOnly
    private static final PrintWriter printWriter(@NotNull File file, Charset charset) {
        Writer outputStreamWriter = new OutputStreamWriter(new FileOutputStream(file), charset);
        return new PrintWriter(outputStreamWriter instanceof BufferedWriter ? (BufferedWriter) outputStreamWriter : new BufferedWriter(outputStreamWriter, 8192));
    }

    @NotNull
    public static final byte[] readBytes(@NotNull File readBytes) {
        byte[] bArrCopyInto;
        int i;
        Intrinsics.checkParameterIsNotNull(readBytes, "$this$readBytes");
        FileInputStream fileInputStream = new FileInputStream(readBytes);
        Throwable th = (Throwable) null;
        try {
            FileInputStream fileInputStream2 = fileInputStream;
            int i2 = 0;
            long length = readBytes.length();
            if (length > 2147483647L) {
                throw new OutOfMemoryError("File " + readBytes + " is too big (" + length + " bytes) to fit in memory.");
            }
            int i3 = (int) length;
            byte[] bArr = new byte[i3];
            while (i3 > 0 && (i = fileInputStream2.read(bArr, i2, i3)) >= 0) {
                i3 -= i;
                i2 += i;
            }
            if (i3 > 0) {
                bArrCopyInto = Arrays.copyOf(bArr, i2);
                Intrinsics.checkExpressionValueIsNotNull(bArrCopyInto, "java.util.Arrays.copyOf(this, newSize)");
            } else {
                int i4 = fileInputStream2.read();
                if (i4 == -1) {
                    bArrCopyInto = bArr;
                } else {
                    ExposingBufferByteArrayOutputStream exposingBufferByteArrayOutputStream = new ExposingBufferByteArrayOutputStream(8193);
                    exposingBufferByteArrayOutputStream.write(i4);
                    ByteStreamsKt.copyTo$default(fileInputStream2, exposingBufferByteArrayOutputStream, 0, 2, null);
                    int length2 = bArr.length + exposingBufferByteArrayOutputStream.size();
                    if (length2 < 0) {
                        throw new OutOfMemoryError("File " + readBytes + " is too big to fit in memory.");
                    }
                    byte[] buffer = exposingBufferByteArrayOutputStream.getBuffer();
                    byte[] bArrCopyOf = Arrays.copyOf(bArr, length2);
                    Intrinsics.checkExpressionValueIsNotNull(bArrCopyOf, "java.util.Arrays.copyOf(this, newSize)");
                    bArrCopyInto = ArraysKt.copyInto(buffer, bArrCopyOf, bArr.length, 0, exposingBufferByteArrayOutputStream.size());
                }
            }
            byte[] bArr2 = bArrCopyInto;
            CloseableKt.closeFinally(fileInputStream, th);
            return bArr2;
        } catch (Throwable th2) {
            throw th2;
        }
    }

    public static final void writeBytes(@NotNull File writeBytes, @NotNull byte[] array) {
        Intrinsics.checkParameterIsNotNull(writeBytes, "$this$writeBytes");
        Intrinsics.checkParameterIsNotNull(array, "array");
        FileOutputStream fileOutputStream = new FileOutputStream(writeBytes);
        Throwable th = (Throwable) null;
        try {
            fileOutputStream.write(array);
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(fileOutputStream, th);
        } catch (Throwable th2) {
            throw th2;
        }
    }

    public static final void appendBytes(@NotNull File appendBytes, @NotNull byte[] array) {
        Intrinsics.checkParameterIsNotNull(appendBytes, "$this$appendBytes");
        Intrinsics.checkParameterIsNotNull(array, "array");
        FileOutputStream fileOutputStream = new FileOutputStream(appendBytes, true);
        Throwable th = (Throwable) null;
        try {
            fileOutputStream.write(array);
            Unit unit = Unit.INSTANCE;
            CloseableKt.closeFinally(fileOutputStream, th);
        } catch (Throwable th2) {
            throw th2;
        }
    }

    @NotNull
    public static final String readText(@NotNull File readText, @NotNull Charset charset) {
        Intrinsics.checkParameterIsNotNull(readText, "$this$readText");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(readText), charset);
        Throwable th = (Throwable) null;
        try {
            String text = TextStreamsKt.readText(inputStreamReader);
            CloseableKt.closeFinally(inputStreamReader, th);
            return text;
        } catch (Throwable th2) {
            throw th2;
        }
    }

    public static String readText$default(File file, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return FilesKt.readText(file, charset);
    }

    public static final void writeText(@NotNull File writeText, @NotNull String text, @NotNull Charset charset) {
        Intrinsics.checkParameterIsNotNull(writeText, "$this$writeText");
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        byte[] bytes = text.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(bytes, "(this as java.lang.String).getBytes(charset)");
        FilesKt.writeBytes(writeText, bytes);
    }

    public static void writeText$default(File file, String str, Charset charset, int i, Object obj) {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.writeText(file, str, charset);
    }

    public static final void appendText(@NotNull File appendText, @NotNull String text, @NotNull Charset charset) {
        Intrinsics.checkParameterIsNotNull(appendText, "$this$appendText");
        Intrinsics.checkParameterIsNotNull(text, "text");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        byte[] bytes = text.getBytes(charset);
        Intrinsics.checkExpressionValueIsNotNull(bytes, "(this as java.lang.String).getBytes(charset)");
        FilesKt.appendBytes(appendText, bytes);
    }

    public static void appendText$default(File file, String str, Charset charset, int i, Object obj) {
        if ((i & 2) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.appendText(file, str, charset);
    }

    public static final void forEachBlock(@NotNull File forEachBlock, @NotNull Function2 action) {
        Intrinsics.checkParameterIsNotNull(forEachBlock, "$this$forEachBlock");
        Intrinsics.checkParameterIsNotNull(action, "action");
        FilesKt.forEachBlock(forEachBlock, 4096, action);
    }

    public static final void forEachBlock(@NotNull File forEachBlock, int i, @NotNull Function2 action) {
        Intrinsics.checkParameterIsNotNull(forEachBlock, "$this$forEachBlock");
        Intrinsics.checkParameterIsNotNull(action, "action");
        byte[] bArr = new byte[RangesKt.coerceAtLeast(i, 512)];
        FileInputStream fileInputStream = new FileInputStream(forEachBlock);
        Throwable th = (Throwable) null;
        try {
            FileInputStream fileInputStream2 = fileInputStream;
            while (true) {
                int i2 = fileInputStream2.read(bArr);
                if (i2 > 0) {
                    action.invoke(bArr, Integer.valueOf(i2));
                } else {
                    Unit unit = Unit.INSTANCE;
                    CloseableKt.closeFinally(fileInputStream, th);
                    return;
                }
            }
        } catch (Throwable th2) {
            throw th2;
        }
    }

    public static void forEachLine$default(File file, Charset charset, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        FilesKt.forEachLine(file, charset, function1);
    }

    public static final void forEachLine(@NotNull File forEachLine, @NotNull Charset charset, @NotNull Function1 action) {
        Intrinsics.checkParameterIsNotNull(forEachLine, "$this$forEachLine");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        Intrinsics.checkParameterIsNotNull(action, "action");
        TextStreamsKt.forEachLine(new BufferedReader(new InputStreamReader(new FileInputStream(forEachLine), charset)), action);
    }

    @InlineOnly
    private static final FileInputStream inputStream(@NotNull File file) {
        return new FileInputStream(file);
    }

    @InlineOnly
    private static final FileOutputStream outputStream(@NotNull File file) {
        return new FileOutputStream(file);
    }

    public static List readLines$default(File file, Charset charset, int i, Object obj) {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        return FilesKt.readLines(file, charset);
    }

    @NotNull
    public static final List readLines(@NotNull File readLines, @NotNull Charset charset) {
        Intrinsics.checkParameterIsNotNull(readLines, "$this$readLines");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        ArrayList arrayList = new ArrayList();
        FilesKt.forEachLine(readLines, charset, new Function1(arrayList) { // from class: kotlin.io.FilesKt__FileReadWriteKt.readLines.1
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

    public static Object useLines$default(File useLines, Charset charset, Function1 block, int i, Object obj) throws IOException {
        if ((i & 1) != 0) {
            charset = Charsets.UTF_8;
        }
        Intrinsics.checkParameterIsNotNull(useLines, "$this$useLines");
        Charset charset2 = charset;
        Intrinsics.checkParameterIsNotNull(charset2, "charset");
        Intrinsics.checkParameterIsNotNull(block, "block");
        Reader inputStreamReader = new InputStreamReader(new FileInputStream(useLines), charset);
        BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
        Throwable th = (Throwable) null;
        try {
            Object objInvoke = block.invoke(TextStreamsKt.lineSequence(bufferedReader));
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

    public static final Object useLines(@NotNull File useLines, @NotNull Charset charset, @NotNull Function1 block) throws IOException {
        Intrinsics.checkParameterIsNotNull(useLines, "$this$useLines");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        Intrinsics.checkParameterIsNotNull(block, "block");
        Reader inputStreamReader = new InputStreamReader(new FileInputStream(useLines), charset);
        BufferedReader bufferedReader = inputStreamReader instanceof BufferedReader ? (BufferedReader) inputStreamReader : new BufferedReader(inputStreamReader, 8192);
        Throwable th = (Throwable) null;
        try {
            Object objInvoke = block.invoke(TextStreamsKt.lineSequence(bufferedReader));
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
}
