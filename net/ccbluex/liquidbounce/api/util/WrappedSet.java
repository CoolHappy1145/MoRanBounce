package net.ccbluex.liquidbounce.api.util;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\ufffd\ufffd*\u0004\b\ufffd\ufffd\u0010\u0001*\u0004\b\u0001\u0010\u00022\u001a\u0012\u0004\u0012\u0002H\u0001\u0012\u0004\u0012\u0002H\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00040\u00032\b\u0012\u0004\u0012\u0002H\u00020\u0005B;\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\ufffd\ufffd0\u0005\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\ufffd\ufffd0\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00028\ufffd\ufffd\u0012\u0004\u0012\u00028\u00010\b\u00a2\u0006\u0002\u0010\n\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/util/WrappedSet;", "O", "T", "Lnet/ccbluex/liquidbounce/api/util/WrappedCollection;", "", "", "wrapped", "unwrapper", "Lkotlin/Function1;", "wrapper", "(Ljava/util/Set;Lkotlin/jvm/functions/Function1;Lkotlin/jvm/functions/Function1;)V", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/util/WrappedSet.class */
public final class WrappedSet extends WrappedCollection implements Set, KMappedMarker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WrappedSet(@NotNull Set wrapped, @NotNull Function1 unwrapper, @NotNull Function1 wrapper) {
        super(wrapped, unwrapper, wrapper);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        Intrinsics.checkParameterIsNotNull(unwrapper, "unwrapper");
        Intrinsics.checkParameterIsNotNull(wrapper, "wrapper");
    }
}
