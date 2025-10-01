package net.ccbluex.liquidbounce.value;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/value/BlockValue;", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "name", "", PropertyDescriptor.VALUE, "", "(Ljava/lang/String;I)V", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/value/BlockValue.class */
public final class BlockValue extends IntegerValue {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BlockValue(@NotNull String name, int i) {
        super(name, i, 1, 197);
        Intrinsics.checkParameterIsNotNull(name, "name");
    }
}
