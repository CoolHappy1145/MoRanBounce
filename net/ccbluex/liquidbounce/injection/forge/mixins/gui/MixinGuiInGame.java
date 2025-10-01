package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind;
import net.ccbluex.liquidbounce.features.module.modules.render.HUD;
import net.ccbluex.liquidbounce.features.module.modules.render.NoScoreboard;
import net.ccbluex.liquidbounce.p005ui.font.AWTFontRenderer;
import net.ccbluex.liquidbounce.utils.ClassUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiIngame.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiInGame.class */
public abstract class MixinGuiInGame extends MixinGui {

    @Shadow
    @Final
    protected static ResourceLocation field_110330_c;

    @Shadow
    @Final
    protected Minecraft field_73839_d;

    @Shadow
    protected abstract void func_184044_a(int i, int i2, float f, EntityPlayer entityPlayer, ItemStack itemStack);

    @Inject(method = {"renderScoreboard"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void renderScoreboard(CallbackInfo callbackInfo) {
        if (LiquidBounce.moduleManager.getModule(HUD.class).getState() || NoScoreboard.INSTANCE.getState()) {
            callbackInfo.cancel();
        }
    }

    @Overwrite
    protected void func_180479_a(ScaledResolution scaledResolution, float f) {
        if (Minecraft.func_71410_x().func_175606_aa() instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) this.field_73839_d.func_175606_aa();
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            ItemStack itemStackFunc_184592_cb = entityPlayer.func_184592_cb();
            EnumHandSide enumHandSideFunc_188468_a = entityPlayer.func_184591_cq().func_188468_a();
            int iFunc_78326_a = scaledResolution.func_78326_a() / 2;
            float f2 = this.field_73735_i;
            this.field_73735_i = -90.0f;
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
            GuiIngame.func_73734_a(iFunc_78326_a - 91, scaledResolution.func_78328_b() - 24, iFunc_78326_a + 90, scaledResolution.func_78328_b(), Integer.MIN_VALUE);
            GuiIngame.func_73734_a(((iFunc_78326_a - 91) - 1) + (entityPlayer.field_71071_by.field_70461_c * 20) + 1, scaledResolution.func_78328_b() - 24, ((iFunc_78326_a - 91) - 1) + (entityPlayer.field_71071_by.field_70461_c * 20) + 22, ((scaledResolution.func_78328_b() - 22) - 1) + 24, Integer.MAX_VALUE);
            if (!itemStackFunc_184592_cb.func_190926_b()) {
                GuiIngame.func_73734_a((iFunc_78326_a - 91) - 30, scaledResolution.func_78328_b() - 24, iFunc_78326_a - 100, scaledResolution.func_78328_b(), Integer.MIN_VALUE);
            }
            this.field_73839_d.func_110434_K().func_110577_a(field_110330_c);
            this.field_73735_i = f2;
            GlStateManager.func_179091_B();
            GlStateManager.func_179147_l();
            GlStateManager.func_187428_a(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            RenderHelper.func_74520_c();
            for (int i = 0; i < 9; i++) {
                func_184044_a((iFunc_78326_a - 90) + (i * 20) + 2, (scaledResolution.func_78328_b() - 16) - 3, f, entityPlayer, (ItemStack) entityPlayer.field_71071_by.field_70462_a.get(i));
            }
            if (!itemStackFunc_184592_cb.func_190926_b()) {
                int iFunc_78328_b = (scaledResolution.func_78328_b() - 16) - 3;
                if (enumHandSideFunc_188468_a == EnumHandSide.LEFT) {
                    func_184044_a((iFunc_78326_a - 91) - 26, iFunc_78328_b, f, entityPlayer, itemStackFunc_184592_cb);
                } else {
                    func_184044_a(iFunc_78326_a + 91 + 10, iFunc_78328_b, f, entityPlayer, itemStackFunc_184592_cb);
                }
            }
            if (this.field_73839_d.field_71474_y.field_186716_M == 2) {
                float fFunc_184825_o = this.field_73839_d.field_71439_g.func_184825_o(0.0f);
                if (fFunc_184825_o < 1.0f) {
                    int iFunc_78328_b2 = scaledResolution.func_78328_b() - 20;
                    int i2 = iFunc_78326_a + 91 + 6;
                    if (enumHandSideFunc_188468_a == EnumHandSide.RIGHT) {
                        i2 = (iFunc_78326_a - 91) - 22;
                    }
                    this.field_73839_d.func_110434_K().func_110577_a(Gui.field_110324_m);
                    int i3 = (int) (fFunc_184825_o * 19.0f);
                    GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 1.0f);
                    func_73729_b(i2, iFunc_78328_b2, 0, 94, 18, 18);
                    func_73729_b(i2, (iFunc_78328_b2 + 18) - i3, 18, 112 - i3, 18, i3);
                }
            }
            RenderHelper.func_74518_a();
            GlStateManager.func_179101_C();
            GlStateManager.func_179084_k();
        }
    }

    @Inject(method = {"renderHotbar"}, m59at = {@InterfaceC0563At("RETURN")})
    private void renderTooltipPost(ScaledResolution scaledResolution, float f, CallbackInfo callbackInfo) {
        if (!ClassUtils.hasClass("net.labymod.api.LabyModAPI")) {
            LiquidBounce.eventManager.callEvent(new Render2DEvent(f));
            AWTFontRenderer.Companion.garbageCollectionTick();
        }
    }

    @Inject(method = {"renderPumpkinOverlay"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void renderPumpkinOverlay(CallbackInfo callbackInfo) {
        AntiBlind antiBlind = (AntiBlind) LiquidBounce.moduleManager.getModule(AntiBlind.class);
        if (antiBlind.getState() && ((Boolean) antiBlind.getPumpkinEffect().get()).booleanValue()) {
            callbackInfo.cancel();
        }
    }
}
