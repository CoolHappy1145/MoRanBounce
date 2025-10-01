package kotlin.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.internal.InlineOnly;
import kotlin.jvm.JvmName;
import kotlin.jvm.functions.Function0;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u001a\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u001a&\u0010\ufffd\ufffd\u001a\u0002H\u0001\"\u0004\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0087\b\u00a2\u0006\u0002\u0010\u0005\u001a&\u0010\u0006\u001a\u0002H\u0001\"\u0004\b\ufffd\ufffd\u0010\u0001*\u00020\u00072\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0087\b\u00a2\u0006\u0002\u0010\b\u001a&\u0010\t\u001a\u0002H\u0001\"\u0004\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0087\b\u00a2\u0006\u0002\u0010\u0005\u00a8\u0006\n"}, m27d2 = {"read", "T", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "action", "Lkotlin/Function0;", "(Ljava/util/concurrent/locks/ReentrantReadWriteLock;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "withLock", "Ljava/util/concurrent/locks/Lock;", "(Ljava/util/concurrent/locks/Lock;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "write", "kotlin-stdlib"})
@JvmName(name = "LocksKt")
/* loaded from: L-out.jar:kotlin/concurrent/LocksKt.class */
public final class LocksKt {
    @InlineOnly
    private static final Object withLock(@NotNull Lock lock, Function0 function0) {
        lock.lock();
        try {
            Object objInvoke = function0.invoke();
            lock.unlock();
            return objInvoke;
        } catch (Throwable th) {
            lock.unlock();
            throw th;
        }
    }

    @InlineOnly
    private static final Object read(@NotNull ReentrantReadWriteLock reentrantReadWriteLock, Function0 function0) {
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        lock.lock();
        try {
            Object objInvoke = function0.invoke();
            lock.unlock();
            return objInvoke;
        } catch (Throwable th) {
            lock.unlock();
            throw th;
        }
    }

    @InlineOnly
    private static final Object write(@NotNull ReentrantReadWriteLock reentrantReadWriteLock, Function0 function0) {
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i = 0; i < readHoldCount; i++) {
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            Object objInvoke = function0.invoke();
            for (int i2 = 0; i2 < readHoldCount; i2++) {
                lock.lock();
            }
            writeLock.unlock();
            return objInvoke;
        } catch (Throwable th) {
            for (int i3 = 0; i3 < readHoldCount; i3++) {
                lock.lock();
            }
            writeLock.unlock();
            throw th;
        }
    }
}
