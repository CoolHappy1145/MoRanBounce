package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.awt.Color;
import net.ccbluex.liquidbounce.injection.backend.FontRendererImpl;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.client.config.GuiButtonExt;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin({GuiButtonExt.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiButtonExt.class */
public abstract class MixinGuiButtonExt extends GuiButton {
    private float cut;
    private float alpha;

    public MixinGuiButtonExt(int i, int i2, int i3, String str) {
        super(i, i2, i3, str);
    }

    public MixinGuiButtonExt(int i, int i2, int i3, int i4, int i5, String str) {
        super(i, i2, i3, i4, i5, str);
    }

    @Overwrite
    public void func_191745_a(Minecraft minecraft, int i, int i2, float f) {
        int rgb;
        if (this.field_146125_m) {
            FontRenderer wrapped = minecraft.func_135016_M().func_135042_a() ? minecraft.field_71466_p : ((FontRendererImpl) Fonts.font35).getWrapped();
            this.field_146123_n = i >= this.field_146128_h && i2 >= this.field_146129_i && i < this.field_146128_h + this.field_146120_f && i2 < this.field_146129_i + this.field_146121_g;
            int i3 = RenderUtils.deltaTime;
            if (this.field_146124_l && this.field_146123_n) {
                this.cut += 0.05f * i3;
                if (this.cut >= 4.0f) {
                    this.cut = 4.0f;
                }
                this.alpha += 0.3f * i3;
                if (this.alpha >= 210.0f) {
                    this.alpha = 210.0f;
                }
            } else {
                this.cut -= 0.05f * i3;
                if (this.cut <= 0.0f) {
                    this.cut = 0.0f;
                }
                this.alpha -= 0.3f * i3;
                if (this.alpha <= 120.0f) {
                    this.alpha = 120.0f;
                }
            }
            int i4 = this.field_146128_h + ((int) this.cut);
            int i5 = this.field_146129_i;
            int i6 = (this.field_146128_h + this.field_146120_f) - ((int) this.cut);
            int i7 = this.field_146129_i + this.field_146121_g;
            if (this.field_146124_l) {
                rgb = new Color(0.0f, 0.0f, 0.0f, this.alpha / 255.0f).getRGB();
            } else {
                rgb = new Color(0.5f, 0.5f, 0.5f, 0.5f).getRGB();
            }
            Gui.func_73734_a(i4, i5, i6, i7, rgb);
            minecraft.func_110434_K().func_110577_a(field_146122_a);
            func_146119_b(minecraft, i, i2);
            wrapped.func_175063_a(this.field_146126_j, (this.field_146128_h + (this.field_146120_f / 2)) - (wrapped.func_78256_a(this.field_146126_j) / 2), this.field_146129_i + ((this.field_146121_g - 5) / 2.0f), 14737632);
            GlStateManager.func_179117_G();
        }
    }
}
