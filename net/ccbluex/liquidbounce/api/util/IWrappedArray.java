package net.ccbluex.liquidbounce.api.util;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u001c\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\bf\u0018\ufffd\ufffd \u000b*\u0004\b\ufffd\ufffd\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002:\u0001\u000bJ\u0016\u0010\u0003\u001a\u00028\ufffd\ufffd2\u0006\u0010\u0004\u001a\u00020\u0005H\u00a6\u0002\u00a2\u0006\u0002\u0010\u0006J\u001e\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\t\u001a\u00028\ufffd\ufffdH\u00a6\u0002\u00a2\u0006\u0002\u0010\n\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/util/IWrappedArray;", "T", "", PropertyDescriptor.GET, "index", "", "(I)Ljava/lang/Object;", PropertyDescriptor.SET, "", PropertyDescriptor.VALUE, "(ILjava/lang/Object;)V", "Companion", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/util/IWrappedArray.class */
public interface IWrappedArray extends Iterable, KMappedMarker {
    public static final Companion Companion = Companion.$$INSTANCE;

    Object get(int i);

    void set(int i, Object obj);

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd(\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002JD\u0010\u0003\u001a\u00020\u0004\"\u0004\b\u0001\u0010\u0005*\n\u0012\u0006\b\u0001\u0012\u0002H\u00050\u00062'\u0010\u0007\u001a#\u0012\u0013\u0012\u00110\t\u00a2\u0006\f\b\n\u0012\b\b\u000b\u0012\u0004\b\b(\f\u0012\u0004\u0012\u0002H\u0005\u0012\u0004\u0012\u00020\u00040\bH\u0086\b\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/util/IWrappedArray$Companion;", "", "()V", "forEachIndexed", "", "T", "Lnet/ccbluex/liquidbounce/api/util/IWrappedArray;", "action", "Lkotlin/Function2;", "", "Lkotlin/ParameterName;", "name", "index", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/util/IWrappedArray$Companion.class */
    public static final class Companion {
        static final Companion $$INSTANCE = new Companion();

        private Companion() {
        }

        public final void forEachIndexed(@NotNull IWrappedArray forEachIndexed, @NotNull Function2 action) {
            Intrinsics.checkParameterIsNotNull(forEachIndexed, "$this$forEachIndexed");
            Intrinsics.checkParameterIsNotNull(action, "action");
            int i = 0;
            for (Object obj : forEachIndexed) {
                Integer numValueOf = Integer.valueOf(i);
                i++;
                action.invoke(numValueOf, obj);
            }
        }
    }
}
