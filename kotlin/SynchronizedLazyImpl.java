package kotlin;

import java.io.Serializable;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, OPCode.EXACTN_IC}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd0\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0006\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0002\u0018\ufffd\ufffd*\u0006\b\ufffd\ufffd\u0010\u0001 \u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u0004B\u001f\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0006\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b\u00a2\u0006\u0002\u0010\tJ\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\bH\u0002R\u0010\u0010\n\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0016\u0010\u0005\u001a\n\u0012\u0004\u0012\u00028\ufffd\ufffd\u0018\u00010\u0006X\u0088\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u000b\u001a\u00028\ufffd\ufffd8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0013"}, m27d2 = {"Lkotlin/SynchronizedLazyImpl;", "T", "Lkotlin/Lazy;", "Ljava/io/Serializable;", "Lkotlin/io/Serializable;", "initializer", "Lkotlin/Function0;", "lock", "", "(Lkotlin/jvm/functions/Function0;Ljava/lang/Object;)V", "_value", PropertyDescriptor.VALUE, "getValue", "()Ljava/lang/Object;", "isInitialized", "", "toString", "", "writeReplace", "kotlin-stdlib"})
/* loaded from: L-out.jar:kotlin/SynchronizedLazyImpl.class */
final class SynchronizedLazyImpl implements Lazy, Serializable {
    private Function0 initializer;
    private Object _value;
    private final Object lock;

    public SynchronizedLazyImpl(@NotNull Function0 initializer, @Nullable Object obj) {
        Intrinsics.checkParameterIsNotNull(initializer, "initializer");
        this.initializer = initializer;
        this._value = UNINITIALIZED_VALUE.INSTANCE;
        Object obj2 = obj;
        this.lock = obj2 == null ? this : obj2;
    }

    public SynchronizedLazyImpl(Function0 function0, Object obj, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(function0, (i & 2) != 0 ? null : obj);
    }

    @Override // kotlin.Lazy
    public Object getValue() {
        Object obj;
        Object obj2;
        Object obj3 = this._value;
        if (obj3 != UNINITIALIZED_VALUE.INSTANCE) {
            return obj3;
        }
        synchronized (this.lock) {
            Object obj4 = this._value;
            if (obj4 != UNINITIALIZED_VALUE.INSTANCE) {
                obj = obj4;
            } else {
                Function0 function0 = this.initializer;
                if (function0 == null) {
                    Intrinsics.throwNpe();
                }
                Object objInvoke = function0.invoke();
                this._value = objInvoke;
                this.initializer = (Function0) null;
                obj = objInvoke;
            }
            obj2 = obj;
        }
        return obj2;
    }

    @Override // kotlin.Lazy
    public boolean isInitialized() {
        return this._value != UNINITIALIZED_VALUE.INSTANCE;
    }

    @NotNull
    public String toString() {
        return isInitialized() ? String.valueOf(getValue()) : "Lazy value not initialized yet.";
    }

    private final Object writeReplace() {
        return new InitializedLazyImpl(getValue());
    }
}
