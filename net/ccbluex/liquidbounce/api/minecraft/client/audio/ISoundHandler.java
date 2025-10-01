package net.ccbluex.liquidbounce.api.minecraft.client.audio;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u0007\n\ufffd\ufffd\bf\u0018\ufffd\ufffd2\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&\u00a8\u0006\b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/client/audio/ISoundHandler;", "", "playSound", "", "name", "", "pitch", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/client/audio/ISoundHandler.class */
public interface ISoundHandler {
    void playSound(@NotNull String str, float f);
}
