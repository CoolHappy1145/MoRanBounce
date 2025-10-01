package kotlin.concurrent;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd:\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\u001aJ\u0010\ufffd\ufffd\u001a\u00020\u00012\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b2\b\b\u0002\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f\u001a0\u0010\u000e\u001a\u0002H\u000f\"\b\b\ufffd\ufffd\u0010\u000f*\u00020\u0010*\b\u0012\u0004\u0012\u0002H\u000f0\u00112\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u000f0\fH\u0087\b\u00a2\u0006\u0002\u0010\u0013\u00a8\u0006\u0014"}, m27d2 = {"thread", "Ljava/lang/Thread;", "start", "", "isDaemon", "contextClassLoader", "Ljava/lang/ClassLoader;", "name", "", "priority", "", "block", "Lkotlin/Function0;", "", "getOrSet", "T", "", "Ljava/lang/ThreadLocal;", "default", "(Ljava/lang/ThreadLocal;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "kotlin-stdlib"})
@JvmName(name = "ThreadsKt")
/* loaded from: L-out.jar:kotlin/concurrent/ThreadsKt.class */
public final class ThreadsKt {
    public static Thread thread$default(boolean z, boolean z2, ClassLoader classLoader, String str, int i, Function0 function0, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            z = true;
        }
        if ((i2 & 2) != 0) {
            z2 = false;
        }
        if ((i2 & 4) != 0) {
            classLoader = (ClassLoader) null;
        }
        if ((i2 & 8) != 0) {
            str = (String) null;
        }
        if ((i2 & 16) != 0) {
            i = -1;
        }
        return thread(z, z2, classLoader, str, i, function0);
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [kotlin.concurrent.ThreadsKt$thread$thread$1] */
    @NotNull
    public static final Thread thread(boolean z, boolean z2, @Nullable ClassLoader classLoader, @Nullable String str, int i, @NotNull final Function0 block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        ?? r0 = new Thread(block) { // from class: kotlin.concurrent.ThreadsKt$thread$thread$1
            final Function0 $block;

            {
                this.$block = block;
            }

            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                this.$block.invoke();
            }
        };
        if (z2) {
            r0.setDaemon(true);
        }
        if (i > 0) {
            r0.setPriority(i);
        }
        if (str != null) {
            r0.setName(str);
        }
        if (classLoader != null) {
            r0.setContextClassLoader(classLoader);
        }
        if (z) {
            r0.start();
        }
        return (Thread) r0;
    }

    @InlineOnly
    private static final Object getOrSet(@NotNull ThreadLocal threadLocal, Function0 function0) {
        Object obj = threadLocal.get();
        if (obj != null) {
            return obj;
        }
        Object objInvoke = function0.invoke();
        threadLocal.set(objInvoke);
        return objInvoke;
    }
}
