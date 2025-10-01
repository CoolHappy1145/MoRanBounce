package net.ccbluex.liquidbounce.api.minecraft.client.render;

import java.awt.image.BufferedImage;
import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\bf\u0018\ufffd\ufffd2\u00020\u0001J\u0014\u0010\u0002\u001a\u0004\u0018\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0003H&J\b\u0010\u0005\u001a\u00020\u0006H&\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/client/render/WIImageBuffer;", "", "parseUserSkin", "Ljava/awt/image/BufferedImage;", "image", "skinAvailable", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/client/render/WIImageBuffer.class */
public interface WIImageBuffer {
    @Nullable
    BufferedImage parseUserSkin(@Nullable BufferedImage bufferedImage);

    void skinAvailable();
}
