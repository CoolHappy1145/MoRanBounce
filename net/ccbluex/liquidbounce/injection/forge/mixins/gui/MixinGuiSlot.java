package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.injection.implementations.IMixinGuiSlot;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({GuiSlot.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiSlot.class */
public abstract class MixinGuiSlot implements IMixinGuiSlot {

    @Shadow
    public int field_148152_e;

    @Shadow
    public int field_148153_b;

    @Shadow
    public int field_148155_a;

    @Shadow
    public int field_148151_d;

    @Shadow
    public int field_148154_c;

    @Shadow
    public int field_148158_l;

    @Shadow
    protected int field_148150_g;

    @Shadow
    protected int field_148162_h;

    @Shadow
    protected float field_148169_q;

    @Shadow
    protected boolean field_148165_u;

    @Shadow
    @Final
    protected Minecraft field_148161_k;

    @Shadow
    protected boolean field_178041_q;
    private int listWidth = 220;
    private boolean enableScissor = false;

    @Shadow
    protected abstract void func_148123_a();

    @Shadow
    protected abstract void func_148121_k();

    @Shadow
    protected abstract void func_148129_a(int i, int i2, Tessellator tessellator);

    @Shadow
    protected abstract int func_148138_e();

    @Shadow
    protected abstract void func_192638_a(int i, int i2, int i3, int i4, float f);

    @Shadow
    protected abstract void func_148136_c(int i, int i2, int i3, int i4);

    @Shadow
    public abstract int func_148135_f();

    @Shadow
    protected abstract void drawContainerBackground(Tessellator tessellator);

    @Shadow
    protected abstract void func_148142_b(int i, int i2);

    @Overwrite
    public void func_148128_a(int i, int i2, float f) {
        if (this.field_178041_q) {
            this.field_148150_g = i;
            this.field_148162_h = i2;
            func_148123_a();
            int iFunc_148137_d = func_148137_d();
            int i3 = iFunc_148137_d + 6;
            func_148121_k();
            GlStateManager.func_179140_f();
            GlStateManager.func_179106_n();
            Tessellator tessellatorFunc_178181_a = Tessellator.func_178181_a();
            BufferBuilder bufferBuilderFunc_178180_c = tessellatorFunc_178181_a.func_178180_c();
            int iFunc_148139_c = ((this.field_148152_e + (this.field_148155_a / 2)) - (func_148139_c() / 2)) + 2;
            int i4 = (this.field_148153_b + 4) - ((int) this.field_148169_q);
            if (this.field_148165_u) {
                func_148129_a(iFunc_148139_c, i4, tessellatorFunc_178181_a);
            }
            RenderUtils.makeScissorBox(this.field_148152_e, this.field_148153_b, this.field_148151_d, this.field_148154_c);
            GL11.glEnable(SGL.GL_SCISSOR_TEST);
            func_192638_a(iFunc_148139_c, i4, i, i2, f);
            GL11.glDisable(SGL.GL_SCISSOR_TEST);
            ScaledResolution scaledResolution = new ScaledResolution(this.field_148161_k);
            GlStateManager.func_179097_i();
            Gui.func_73734_a(0, 0, scaledResolution.func_78326_a(), this.field_148153_b, Integer.MIN_VALUE);
            Gui.func_73734_a(0, this.field_148154_c, scaledResolution.func_78326_a(), this.field_148158_l, Integer.MIN_VALUE);
            GL11.glEnable(SGL.GL_BLEND);
            GlStateManager.func_179120_a(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA, 0, 1);
            GlStateManager.func_179118_c();
            GlStateManager.func_179103_j(7425);
            GlStateManager.func_179090_x();
            bufferBuilderFunc_178180_c.func_181668_a(7, DefaultVertexFormats.field_181706_f);
            bufferBuilderFunc_178180_c.func_181662_b(this.field_148152_e, this.field_148153_b + 4, 0.0d).func_181669_b(0, 0, 0, 0).func_181675_d();
            bufferBuilderFunc_178180_c.func_181662_b(this.field_148151_d, this.field_148153_b + 4, 0.0d).func_181669_b(0, 0, 0, 0).func_181675_d();
            bufferBuilderFunc_178180_c.func_181662_b(this.field_148151_d, this.field_148153_b, 0.0d).func_181669_b(0, 0, 0, 255).func_181675_d();
            bufferBuilderFunc_178180_c.func_181662_b(this.field_148152_e, this.field_148153_b, 0.0d).func_181669_b(0, 0, 0, 255).func_181675_d();
            tessellatorFunc_178181_a.func_78381_a();
            bufferBuilderFunc_178180_c.func_181668_a(7, DefaultVertexFormats.field_181706_f);
            bufferBuilderFunc_178180_c.func_181662_b(this.field_148152_e, this.field_148154_c, 0.0d).func_181669_b(0, 0, 0, 255).func_181675_d();
            bufferBuilderFunc_178180_c.func_181662_b(this.field_148151_d, this.field_148154_c, 0.0d).func_181669_b(0, 0, 0, 255).func_181675_d();
            bufferBuilderFunc_178180_c.func_181662_b(this.field_148151_d, this.field_148154_c - 4, 0.0d).func_181669_b(0, 0, 0, 0).func_181675_d();
            bufferBuilderFunc_178180_c.func_181662_b(this.field_148152_e, this.field_148154_c - 4, 0.0d).func_181669_b(0, 0, 0, 0).func_181675_d();
            tessellatorFunc_178181_a.func_78381_a();
            int iFunc_148135_f = func_148135_f();
            if (iFunc_148135_f > 0) {
                int iFunc_76125_a = ((((int) this.field_148169_q) * ((this.field_148154_c - this.field_148153_b) - MathHelper.func_76125_a(((this.field_148154_c - this.field_148153_b) * (this.field_148154_c - this.field_148153_b)) / func_148138_e(), 32, (this.field_148154_c - this.field_148153_b) - 8))) / iFunc_148135_f) + this.field_148153_b;
                if (iFunc_76125_a < this.field_148153_b) {
                    iFunc_76125_a = this.field_148153_b;
                }
                bufferBuilderFunc_178180_c.func_181668_a(7, DefaultVertexFormats.field_181709_i);
                bufferBuilderFunc_178180_c.func_181662_b(iFunc_148137_d, this.field_148154_c, 0.0d).func_187315_a(0.0d, 1.0d).func_181669_b(0, 0, 0, 255).func_181675_d();
                bufferBuilderFunc_178180_c.func_181662_b(i3, this.field_148154_c, 0.0d).func_187315_a(1.0d, 1.0d).func_181669_b(0, 0, 0, 255).func_181675_d();
                bufferBuilderFunc_178180_c.func_181662_b(i3, this.field_148153_b, 0.0d).func_187315_a(1.0d, 0.0d).func_181669_b(0, 0, 0, 255).func_181675_d();
                bufferBuilderFunc_178180_c.func_181662_b(iFunc_148137_d, this.field_148153_b, 0.0d).func_187315_a(0.0d, 0.0d).func_181669_b(0, 0, 0, 255).func_181675_d();
                tessellatorFunc_178181_a.func_78381_a();
                bufferBuilderFunc_178180_c.func_181668_a(7, DefaultVertexFormats.field_181709_i);
                bufferBuilderFunc_178180_c.func_181662_b(iFunc_148137_d, iFunc_76125_a + r0, 0.0d).func_187315_a(0.0d, 1.0d).func_181669_b(128, 128, 128, 255).func_181675_d();
                bufferBuilderFunc_178180_c.func_181662_b(i3, iFunc_76125_a + r0, 0.0d).func_187315_a(1.0d, 1.0d).func_181669_b(128, 128, 128, 255).func_181675_d();
                bufferBuilderFunc_178180_c.func_181662_b(i3, iFunc_76125_a, 0.0d).func_187315_a(1.0d, 0.0d).func_181669_b(128, 128, 128, 255).func_181675_d();
                bufferBuilderFunc_178180_c.func_181662_b(iFunc_148137_d, iFunc_76125_a, 0.0d).func_187315_a(0.0d, 0.0d).func_181669_b(128, 128, 128, 255).func_181675_d();
                tessellatorFunc_178181_a.func_78381_a();
                bufferBuilderFunc_178180_c.func_181668_a(7, DefaultVertexFormats.field_181709_i);
                bufferBuilderFunc_178180_c.func_181662_b(iFunc_148137_d, (iFunc_76125_a + r0) - 1, 0.0d).func_187315_a(0.0d, 1.0d).func_181669_b(192, 192, 192, 255).func_181675_d();
                bufferBuilderFunc_178180_c.func_181662_b(i3 - 1, (iFunc_76125_a + r0) - 1, 0.0d).func_187315_a(1.0d, 1.0d).func_181669_b(192, 192, 192, 255).func_181675_d();
                bufferBuilderFunc_178180_c.func_181662_b(i3 - 1, iFunc_76125_a, 0.0d).func_187315_a(1.0d, 0.0d).func_181669_b(192, 192, 192, 255).func_181675_d();
                bufferBuilderFunc_178180_c.func_181662_b(iFunc_148137_d, iFunc_76125_a, 0.0d).func_187315_a(0.0d, 0.0d).func_181669_b(192, 192, 192, 255).func_181675_d();
                tessellatorFunc_178181_a.func_78381_a();
            }
            func_148142_b(i, i2);
            GlStateManager.func_179098_w();
            GlStateManager.func_179103_j(7424);
            GlStateManager.func_179141_d();
            GlStateManager.func_179084_k();
        }
    }

    @Overwrite
    protected int func_148137_d() {
        return this.field_148155_a - 5;
    }

    @Override // net.ccbluex.liquidbounce.injection.implementations.IMixinGuiSlot
    public void setEnableScissor(boolean z) {
        this.enableScissor = z;
    }

    @Overwrite
    public int func_148139_c() {
        return this.listWidth;
    }

    @Override // net.ccbluex.liquidbounce.injection.implementations.IMixinGuiSlot
    public void setListWidth(int i) {
        this.listWidth = i;
    }
}
