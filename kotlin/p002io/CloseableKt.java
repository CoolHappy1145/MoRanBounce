package kotlin.p002io;

import java.io.Closeable;
import java.io.IOException;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.PublishedApi;
import kotlin.SinceKotlin;
import kotlin.internal.InlineOnly;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function1;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u001c\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a\u0018\u0010\ufffd\ufffd\u001a\u00020\u0001*\u0004\u0018\u00010\u00022\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0001\u001a;\u0010\u0005\u001a\u0002H\u0006\"\n\b\ufffd\ufffd\u0010\u0007*\u0004\u0018\u00010\u0002\"\u0004\b\u0001\u0010\u0006*\u0002H\u00072\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\u00060\tH\u0087\b\u00f8\u0001\ufffd\ufffd\u00a2\u0006\u0002\u0010\u000b\u0082\u0002\b\n\u0006\b\u0011(\n0\u0001\u00a8\u0006\f"}, m27d2 = {"closeFinally", "", "Ljava/io/Closeable;", "cause", "", "use", "R", "T", "block", "Lkotlin/Function1;", "Requires newer compiler version to be inlined correctly.", "(Ljava/io/Closeable;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlin-stdlib"})
@JvmName(name = "CloseableKt")
/* loaded from: L-out.jar:kotlin/io/CloseableKt.class */
public final class CloseableKt {
    @InlineOnly
    private static final Object use(Closeable closeable, Function1 function1) throws IOException {
        Throwable th = (Throwable) null;
        try {
            Object objInvoke = function1.invoke(closeable);
            if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 1, 0)) {
                closeFinally(closeable, th);
            } else if (closeable != null) {
                closeable.close();
            }
            return objInvoke;
        } catch (Throwable th2) {
            throw th2;
        }
    }

    @SinceKotlin(version = "1.1")
    @PublishedApi
    public static final void closeFinally(@Nullable Closeable closeable, @Nullable Throwable th) {
        if (closeable != null) {
            if (th != null) {
                try {
                    closeable.close();
                    return;
                } catch (Throwable th2) {
                    ExceptionsKt.addSuppressed(th, th2);
                    return;
                }
            }
            closeable.close();
        }
    }
}
