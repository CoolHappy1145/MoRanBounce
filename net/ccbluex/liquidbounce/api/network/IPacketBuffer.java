package net.ccbluex.liquidbounce.api.network;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u0012\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\ufffd\ufffd\bf\u0018\ufffd\ufffd2\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\u0010\u0010\u0006\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00020\bH&J\u0010\u0010\t\u001a\u00020\ufffd\ufffd2\u0006\u0010\n\u001a\u00020\u000bH&\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/network/IPacketBuffer;", "", "writeBytes", "", "payload", "", "writeItemStackToBuffer", "itemStack", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemStack;", "writeString", "vanilla", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/network/IPacketBuffer.class */
public interface IPacketBuffer {
    void writeBytes(@NotNull byte[] bArr);

    void writeItemStackToBuffer(@NotNull IItemStack iItemStack);

    @NotNull
    IPacketBuffer writeString(@NotNull String str);
}
