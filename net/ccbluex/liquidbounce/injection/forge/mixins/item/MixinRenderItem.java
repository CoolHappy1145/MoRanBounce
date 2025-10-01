package net.ccbluex.liquidbounce.injection.forge.mixins.item;

import java.awt.Color;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.EnchantEffect;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({RenderItem.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/item/MixinRenderItem.class */
public abstract class MixinRenderItem implements IResourceManagerReloadListener {

    @Shadow
    private static final ResourceLocation field_110798_h = new ResourceLocation("textures/misc/enchanted_item_glint.png");

    @Shadow
    private TextureManager field_175057_n;

    @Shadow
    public abstract void func_110549_a(IResourceManager iResourceManager);

    @Shadow
    protected abstract void func_191965_a(IBakedModel iBakedModel, int i);

    @Overwrite
    private void func_191966_a(IBakedModel iBakedModel) {
        EnchantEffect enchantEffect = (EnchantEffect) LiquidBounce.moduleManager.get(EnchantEffect.class);
        Color colorRainbow = ((Boolean) enchantEffect.getRainbow().get()).booleanValue() ? ColorUtils.rainbow() : new Color(((Integer) enchantEffect.getRedValue().get()).intValue(), ((Integer) enchantEffect.getGreenValue().get()).intValue(), ((Integer) enchantEffect.getBlueValue().get()).intValue(), ((Integer) enchantEffect.getalphaValue().get()).intValue());
        GlStateManager.func_179132_a(false);
        GlStateManager.func_179143_c(SGL.GL_EQUAL);
        GlStateManager.func_179140_f();
        GlStateManager.func_179112_b(768, 1);
        this.field_175057_n.func_110577_a(field_110798_h);
        GlStateManager.func_179128_n(5890);
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a(8.0f, 8.0f, 8.0f);
        GlStateManager.func_179109_b(((Minecraft.func_71386_F() % 3000) / 3000.0f) / 8.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(-50.0f, 0.0f, 0.0f, 1.0f);
        if (enchantEffect.getState()) {
            func_191965_a(iBakedModel, colorRainbow.getRGB());
        } else {
            func_191965_a(iBakedModel, -8372020);
        }
        GlStateManager.func_179121_F();
        GlStateManager.func_179094_E();
        GlStateManager.func_179152_a(8.0f, 8.0f, 8.0f);
        GlStateManager.func_179109_b(-(((Minecraft.func_71386_F() % 4873) / 4873.0f) / 8.0f), 0.0f, 0.0f);
        GlStateManager.func_179114_b(10.0f, 0.0f, 0.0f, 1.0f);
        if (enchantEffect.getState()) {
            func_191965_a(iBakedModel, colorRainbow.getRGB());
        } else {
            func_191965_a(iBakedModel, -8372020);
        }
        GlStateManager.func_179121_F();
        GlStateManager.func_179128_n(5888);
        GlStateManager.func_179112_b(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.func_179145_e();
        GlStateManager.func_179143_c(515);
        GlStateManager.func_179132_a(true);
        this.field_175057_n.func_110577_a(TextureMap.field_110575_b);
    }
}
