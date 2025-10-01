package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketChat;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketChat;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\b\u0012\u0004\u0012\u0002H\u00010\u00032\u00020\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\nR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0011"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/SPacketChatImpl;", "T", "Lnet/minecraft/network/play/server/SPacketChat;", "Lnet/ccbluex/liquidbounce/injection/backend/PacketImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/play/server/ISPacketChat;", "wrapped", "(Lnet/minecraft/network/play/server/SPacketChat;)V", "chatComponent", "Lnet/minecraft/util/text/ITextComponent;", "getChatComponent", "()Lnet/minecraft/util/text/ITextComponent;", "getChat", "getGetChat", "type", "Lnet/minecraft/util/text/ChatType;", "getType", "()Lnet/minecraft/util/text/ChatType;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/SPacketChatImpl.class */
public final class SPacketChatImpl extends PacketImpl implements ISPacketChat {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SPacketChatImpl(@NotNull SPacketChat wrapped) {
        super((Packet) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketChat
    @NotNull
    public ITextComponent getChatComponent() {
        ITextComponent iTextComponentFunc_148915_c = getWrapped().func_148915_c();
        Intrinsics.checkExpressionValueIsNotNull(iTextComponentFunc_148915_c, "wrapped.chatComponent");
        return iTextComponentFunc_148915_c;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketChat
    @NotNull
    public ChatType getType() {
        ChatType chatTypeFunc_192590_c = getWrapped().func_192590_c();
        Intrinsics.checkExpressionValueIsNotNull(chatTypeFunc_192590_c, "wrapped.type");
        return chatTypeFunc_192590_c;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketChat
    @NotNull
    public ITextComponent getGetChat() {
        ITextComponent iTextComponentFunc_148915_c = getWrapped().func_148915_c();
        Intrinsics.checkExpressionValueIsNotNull(iTextComponentFunc_148915_c, "wrapped.getChatComponent()");
        return iTextComponentFunc_148915_c;
    }
}
