package net.ccbluex.liquidbounce.api.util;

import java.util.Iterator;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.util.WrappedCollection;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd2\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0011\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010(\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u0001*\u0004\b\u0001\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00020\u0003B;\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0005\u0012\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0007\u0012\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u00010\u0007\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\u0010\u001a\u00028\u00012\u0006\u0010\u0011\u001a\u00020\u0012H\u0096\u0002\u00a2\u0006\u0002\u0010\u0013J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00010\u0015H\u0096\u0002J\u001e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0018\u001a\u00028\u0001H\u0096\u0002\u00a2\u0006\u0002\u0010\u0019R\u001d\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0007\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000bR\u0019\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0005\u00a2\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\rR\u001d\u0010\b\u001a\u000e\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u00010\u0007\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000f\u0010\u000b\u00a8\u0006\u001a"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/util/WrappedArray;", "O", "T", "Lnet/ccbluex/liquidbounce/api/util/IWrappedArray;", "wrapped", "", "unwrapper", "Lkotlin/Function1;", "wrapper", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", "getUnwrapper", "()Lkotlin/jvm/functions/Function1;", "getWrapped", "()[Ljava/lang/Object;", "[Ljava/lang/Object;", "getWrapper", PropertyDescriptor.GET, "index", "", "(I)Ljava/lang/Object;", "iterator", "", PropertyDescriptor.SET, "", PropertyDescriptor.VALUE, "(ILjava/lang/Object;)V", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/util/WrappedArray.class */
public final class WrappedArray implements IWrappedArray {

    @NotNull
    private final Object[] wrapped;

    @NotNull
    private final Function1 unwrapper;

    @NotNull
    private final Function1 wrapper;

    @NotNull
    public final Object[] getWrapped() {
        return this.wrapped;
    }

    @NotNull
    public final Function1 getUnwrapper() {
        return this.unwrapper;
    }

    @NotNull
    public final Function1 getWrapper() {
        return this.wrapper;
    }

    public WrappedArray(@NotNull Object[] wrapped, @NotNull Function1 unwrapper, @NotNull Function1 wrapper) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        Intrinsics.checkParameterIsNotNull(unwrapper, "unwrapper");
        Intrinsics.checkParameterIsNotNull(wrapper, "wrapper");
        this.wrapped = wrapped;
        this.unwrapper = unwrapper;
        this.wrapper = wrapper;
    }

    @Override // net.ccbluex.liquidbounce.api.util.IWrappedArray
    public Object get(int i) {
        return this.wrapper.invoke(this.wrapped[i]);
    }

    @Override // net.ccbluex.liquidbounce.api.util.IWrappedArray
    public void set(int i, Object obj) {
        this.wrapped[i] = this.unwrapper.invoke(obj);
    }

    @Override // java.lang.Iterable
    @NotNull
    public Iterator iterator() {
        return new WrappedCollection.WrappedCollectionIterator(ArrayIteratorKt.iterator(this.wrapped), this.wrapper);
    }
}
