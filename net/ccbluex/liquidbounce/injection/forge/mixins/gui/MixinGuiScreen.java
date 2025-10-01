package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.util.Collections;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.misc.ComponentOnHover;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.injection.backend.ResourceLocationImplKt;
import net.ccbluex.liquidbounce.p005ui.client.GuiBackground;
import net.ccbluex.liquidbounce.utils.render.ParticleUtils;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.BackgroundShader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Mouse;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiScreen.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiScreen.class */
public abstract class MixinGuiScreen {

    @Shadow
    public Minecraft field_146297_k;

    @Shadow
    public List field_146292_n;

    @Shadow
    public int field_146294_l;

    @Shadow
    public int field_146295_m;

    @Shadow
    public FontRenderer field_146289_q;

    @Shadow
    protected abstract void func_175272_a(ITextComponent iTextComponent, int i, int i2);

    @Shadow
    public abstract void func_146283_a(List list, int i, int i2);

    @Shadow
    public abstract void func_146276_q_();

    @Inject(method = {"drawWorldBackground"}, m59at = {@InterfaceC0563At("HEAD")})
    private void drawWorldBackground(CallbackInfo callbackInfo) {
        if (((Boolean) ((HUD) LiquidBounce.moduleManager.getModule(HUD.class)).getInventoryParticle().get()).booleanValue() && this.field_146297_k.field_71439_g != null) {
            ScaledResolution scaledResolution = new ScaledResolution(this.field_146297_k);
            int iFunc_78326_a = scaledResolution.func_78326_a();
            int iFunc_78328_b = scaledResolution.func_78328_b();
            ParticleUtils.drawParticles((Mouse.getX() * iFunc_78326_a) / this.field_146297_k.field_71443_c, (iFunc_78328_b - ((Mouse.getY() * iFunc_78328_b) / this.field_146297_k.field_71440_d)) - 1);
        }
    }

    @Inject(method = {"drawBackground"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void drawClientBackground(CallbackInfo callbackInfo) {
        GlStateManager.func_179140_f();
        GlStateManager.func_179106_n();
        if (GuiBackground.Companion.getEnabled()) {
            if (LiquidBounce.INSTANCE.getBackground() == null) {
                BackgroundShader.BACKGROUND_SHADER.startShader();
                Tessellator tessellatorFunc_178181_a = Tessellator.func_178181_a();
                BufferBuilder bufferBuilderFunc_178180_c = tessellatorFunc_178181_a.func_178180_c();
                bufferBuilderFunc_178180_c.func_181668_a(7, DefaultVertexFormats.field_181705_e);
                bufferBuilderFunc_178180_c.func_181662_b(0.0d, this.field_146295_m, 0.0d).func_181675_d();
                bufferBuilderFunc_178180_c.func_181662_b(this.field_146294_l, this.field_146295_m, 0.0d).func_181675_d();
                bufferBuilderFunc_178180_c.func_181662_b(this.field_146294_l, 0.0d, 0.0d).func_181675_d();
                bufferBuilderFunc_178180_c.func_181662_b(0.0d, 0.0d, 0.0d).func_181675_d();
                tessellatorFunc_178181_a.func_78381_a();
                BackgroundShader.BACKGROUND_SHADER.stopShader();
            } else {
                ScaledResolution scaledResolution = new ScaledResolution(this.field_146297_k);
                int iFunc_78326_a = scaledResolution.func_78326_a();
                int iFunc_78328_b = scaledResolution.func_78328_b();
                this.field_146297_k.func_110434_K().func_110577_a(ResourceLocationImplKt.unwrap(LiquidBounce.INSTANCE.getBackground()));
                GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                Gui.func_152125_a(0, 0, 0.0f, 0.0f, iFunc_78326_a, iFunc_78328_b, iFunc_78326_a, iFunc_78328_b, iFunc_78326_a, iFunc_78328_b);
            }
            if (GuiBackground.Companion.getParticles()) {
                ParticleUtils.drawParticles((Mouse.getX() * this.field_146294_l) / this.field_146297_k.field_71443_c, (this.field_146295_m - ((Mouse.getY() * this.field_146295_m) / this.field_146297_k.field_71440_d)) - 1);
            }
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"drawBackground"}, m59at = {@InterfaceC0563At("RETURN")})
    private void drawParticles(CallbackInfo callbackInfo) {
        if (GuiBackground.Companion.getParticles()) {
            ParticleUtils.drawParticles((Mouse.getX() * this.field_146294_l) / this.field_146297_k.field_71443_c, (this.field_146295_m - ((Mouse.getY() * this.field_146295_m) / this.field_146297_k.field_71440_d)) - 1);
        }
    }

    @Inject(method = {"sendChatMessage(Ljava/lang/String;Z)V"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void messageSend(String str, boolean z, CallbackInfo callbackInfo) {
        if (str.startsWith(String.valueOf(LiquidBounce.commandManager.getPrefix())) && z) {
            this.field_146297_k.field_71456_v.func_146158_b().func_146239_a(str);
            LiquidBounce.commandManager.executeCommands(str);
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"handleComponentHover"}, m59at = {@InterfaceC0563At("HEAD")})
    private void handleHoverOverComponent(ITextComponent iTextComponent, int i, int i2, CallbackInfo callbackInfo) {
        if (iTextComponent == null || iTextComponent.func_150256_b().func_150235_h() == null || !LiquidBounce.moduleManager.getModule(ComponentOnHover.class).getState()) {
            return;
        }
        Style styleFunc_150256_b = iTextComponent.func_150256_b();
        ClickEvent clickEventFunc_150235_h = styleFunc_150256_b.func_150235_h();
        func_146283_a(Collections.singletonList("\u00a7c\u00a7l" + clickEventFunc_150235_h.func_150669_a().func_150673_b().toUpperCase() + ": \u00a7a" + clickEventFunc_150235_h.func_150668_b()), i, i2 - (styleFunc_150256_b.func_150210_i() != null ? 17 : 0));
    }

    @Overwrite
    protected void func_146284_a(GuiButton guiButton) {
    }
}
