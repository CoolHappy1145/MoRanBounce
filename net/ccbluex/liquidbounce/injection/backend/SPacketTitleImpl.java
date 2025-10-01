package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketTitle;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketTitle;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\n\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/SPacketTitleImpl;", "T", "Lnet/minecraft/network/play/server/SPacketTitle;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketTitle;", "wrapped", "(Lnet/minecraft/network/play/server/SPacketTitle;)V", "message", "Lnet/minecraft/util/text/ITextComponent;", "getMessage", "()Lnet/minecraft/util/text/ITextComponent;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/SPacketTitleImpl.class */
public final class SPacketTitleImpl extends PacketImpl implements ISPacketTitle {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SPacketTitleImpl(@NotNull SPacketTitle wrapped) {
        super((Packet) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketTitle
    @NotNull
    public ITextComponent getMessage() {
        ITextComponent iTextComponentFunc_179805_b = getWrapped().func_179805_b();
        Intrinsics.checkExpressionValueIsNotNull(iTextComponentFunc_179805_b, "wrapped.message");
        return iTextComponentFunc_179805_b;
    }
}
