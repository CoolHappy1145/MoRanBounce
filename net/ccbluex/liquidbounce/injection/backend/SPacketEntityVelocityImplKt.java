package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketEntityVelocity;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\r\u0010\ufffd\ufffd\u001a\u00020\u0001*\u00020\u0002H\u0086\b\u001a\r\u0010\u0003\u001a\u00020\u0002*\u00020\u0001H\u0086\b\u00a8\u0006\u0004"}, m27d2 = {"unwrap", "Lnet/minecraft/network/play/server/SPacketEntityVelocity;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketEntityVelocity;", "wrap", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/SPacketEntityVelocityImplKt.class */
public final class SPacketEntityVelocityImplKt {
    @NotNull
    public static final SPacketEntityVelocity unwrap(@NotNull ISPacketEntityVelocity unwrap) {
        Intrinsics.checkParameterIsNotNull(unwrap, "$this$unwrap");
        return ((SPacketEntityVelocityImpl) unwrap).getWrapped();
    }

    @NotNull
    public static final ISPacketEntityVelocity wrap(@NotNull SPacketEntityVelocity wrap) {
        Intrinsics.checkParameterIsNotNull(wrap, "$this$wrap");
        return new SPacketEntityVelocityImpl(wrap);
    }
}
