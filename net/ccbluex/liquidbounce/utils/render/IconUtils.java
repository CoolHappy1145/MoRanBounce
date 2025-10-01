package net.ccbluex.liquidbounce.utils.render;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/IconUtils.class */
public final class IconUtils {
    public static ByteBuffer[] getFavicon() {
        try {
            return new ByteBuffer[]{readImageToBuffer(IconUtils.class.getResourceAsStream("/assets/minecraft/liquidbounce/icon_16x16.png")), readImageToBuffer(IconUtils.class.getResourceAsStream("/assets/minecraft/liquidbounce/icon_32x32.png"))};
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static ByteBuffer readImageToBuffer(InputStream inputStream) {
        if (inputStream == null) {
            return null;
        }
        BufferedImage bufferedImage = ImageIO.read(inputStream);
        int[] rgb = bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), (int[]) null, 0, bufferedImage.getWidth());
        ByteBuffer byteBufferAllocate = ByteBuffer.allocate(4 * rgb.length);
        for (int i : rgb) {
            byteBufferAllocate.putInt((i << 8) | ((i >> 24) & 255));
        }
        byteBufferAllocate.flip();
        return byteBufferAllocate;
    }
}
