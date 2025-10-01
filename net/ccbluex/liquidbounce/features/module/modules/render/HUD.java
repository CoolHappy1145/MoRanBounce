package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import java.text.DecimalFormat;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IServerData;
import net.ccbluex.liquidbounce.api.minecraft.client.shader.IShaderGroup;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.KeyEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.ScreenEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.HanaBiColors;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J.\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020%J\u0010\u0010&\u001a\u00020\u001d2\u0006\u0010'\u001a\u00020(H\u0007J\u0012\u0010)\u001a\u00020\u001d2\b\u0010'\u001a\u0004\u0018\u00010*H\u0007J\u0010\u0010+\u001a\u00020\u001d2\u0006\u0010'\u001a\u00020,H\u0007J\u0012\u0010-\u001a\u00020\u001d2\b\u0010'\u001a\u0004\u0018\u00010.H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\f\u001a\u00020\b\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\r\u0010\u000bR\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0012\u001a\u00020\u000f\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0013\u0010\u0011R\u0011\u0010\u0014\u001a\u00020\u0015\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0018\u001a\u00020\u000f\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0019\u0010\u0011R\u0011\u0010\u001a\u001a\u00020\u000f\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u001b\u0010\u0011\u00a8\u0006/"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/HUD;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "DECIMAL_FORMAT", "Ljava/text/DecimalFormat;", "getDECIMAL_FORMAT", "()Ljava/text/DecimalFormat;", "blurValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "hotbar", "getHotbar", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "inventoryParticle", "getInventoryParticle", "rainbowBrightness", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getRainbowBrightness", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "rainbowSaturation", "getRainbowSaturation", "rainbowSpeed", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "getRainbowSpeed", "()Lnet/ccbluex/liquidbounce/value/IntegerValue;", "rainbowStart", "getRainbowStart", "rainbowStop", "getRainbowStop", "customRenderHotbarItem", "", "index", "", "xPos", "yPos", "partialTicks", "", "p_175184_5_", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "onKey", "event", "Lnet/ccbluex/liquidbounce/event/KeyEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onScreen", "Lnet/ccbluex/liquidbounce/event/ScreenEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "HUD", description = "Toggles visibility of the HUD.", category = ModuleCategory.RENDER, array = false)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/HUD.class */
public final class HUD extends Module {

    @NotNull
    private final BoolValue inventoryParticle = new BoolValue("InventoryParticle", false);
    private final BoolValue blurValue = new BoolValue("Blur", false);

    @NotNull
    private final BoolValue hotbar = new BoolValue("HUD_Hotbar", false);

    @NotNull
    private final FloatValue rainbowStart = new FloatValue("RainbowStart", 0.41f, 0.0f, 1.0f);

    @NotNull
    private final FloatValue rainbowBrightness = new FloatValue("RainbowBrightness", 1.0f, 0.0f, 1.0f);

    @NotNull
    private final IntegerValue rainbowSpeed = new IntegerValue("RainbowSpeed", 1500, SharedScopeCall.SLOW_SCOPE_CALL_THRESHOLD, 7000);

    @NotNull
    private final FloatValue rainbowSaturation = new FloatValue("RainbowSaturation", 0.7f, 0.0f, 1.0f);

    @NotNull
    private final FloatValue rainbowStop = new FloatValue("RainbowStop", 0.58f, 0.0f, 1.0f);

    @NotNull
    private final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    public HUD() {
        setState(true);
    }

    @NotNull
    public final BoolValue getInventoryParticle() {
        return this.inventoryParticle;
    }

    @NotNull
    public final BoolValue getHotbar() {
        return this.hotbar;
    }

    @NotNull
    public final FloatValue getRainbowStart() {
        return this.rainbowStart;
    }

    @NotNull
    public final FloatValue getRainbowBrightness() {
        return this.rainbowBrightness;
    }

    @NotNull
    public final IntegerValue getRainbowSpeed() {
        return this.rainbowSpeed;
    }

    @NotNull
    public final FloatValue getRainbowSaturation() {
        return this.rainbowSaturation;
    }

    @NotNull
    public final FloatValue getRainbowStop() {
        return this.rainbowStop;
    }

    @NotNull
    public final DecimalFormat getDECIMAL_FORMAT() {
        return this.DECIMAL_FORMAT;
    }

    @EventTarget
    public final void onRender2D(@Nullable Render2DEvent render2DEvent) {
        long pingToServer;
        if (MinecraftInstance.classProvider.isGuiHudDesigner(MinecraftInstance.f157mc.getCurrentScreen())) {
            return;
        }
        LiquidBounce.INSTANCE.getHud().render(false);
        IClassProvider iClassProvider = MinecraftInstance.classProvider;
        IMinecraft mc = MinecraftInstance.f157mc;
        Intrinsics.checkExpressionValueIsNotNull(mc, "mc");
        IScaledResolution iScaledResolutionCreateScaledResolution = iClassProvider.createScaledResolution(mc);
        float scaledWidth = iScaledResolutionCreateScaledResolution.getScaledWidth();
        float scaledHeight = iScaledResolutionCreateScaledResolution.getScaledHeight();
        if (((Boolean) this.hotbar.get()).booleanValue() && MinecraftInstance.classProvider.isEntityPlayer(MinecraftInstance.f157mc.getRenderViewEntity()) && !MinecraftInstance.f157mc.getGameSettings().getHideGUI()) {
            IFontRenderer iFontRenderer = Fonts.wqy35;
            RenderUtils.drawRect(0.0d, scaledHeight - 22.0d, scaledWidth, scaledHeight, ClientUtils.reAlpha(HanaBiColors.BLACK.f156c, 0.5f));
            if (MinecraftInstance.f157mc.getCurrentServerData() != null) {
                IServerData currentServerData = MinecraftInstance.f157mc.getCurrentServerData();
                if (currentServerData == null) {
                    Intrinsics.throwNpe();
                }
                pingToServer = currentServerData.getPingToServer();
            } else {
                pingToServer = -1;
            }
            long j = pingToServer;
            RenderUtils.drawFilledCircle(10.0f, scaledHeight - 10.5f, 3.0f, j <= 100 ? new Color(3110141).getRGB() : j <= 250 ? new Color(HanaBiColors.ORANGE.f156c).darker().getRGB() : new Color(HanaBiColors.RED.f156c).getRGB());
            StringBuilder sbAppend = new StringBuilder().append("PING:").append(String.valueOf(EntityUtils.getPing(MinecraftInstance.f157mc.getThePlayer()))).append("ms     FPS:").append(String.valueOf(MinecraftInstance.f157mc.getDebugFPS())).append(" X:");
            DecimalFormat decimalFormat = this.DECIMAL_FORMAT;
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            StringBuilder sbAppend2 = sbAppend.append(decimalFormat.format(thePlayer.getPosX()).toString()).append(" Y:");
            DecimalFormat decimalFormat2 = this.DECIMAL_FORMAT;
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            StringBuilder sbAppend3 = sbAppend2.append(decimalFormat2.format(thePlayer2.getPosY()).toString()).append(" Z:");
            DecimalFormat decimalFormat3 = this.DECIMAL_FORMAT;
            IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer3 == null) {
                Intrinsics.throwNpe();
            }
            StringBuilder sbAppend4 = sbAppend3.append(decimalFormat3.format(thePlayer3.getPosZ()).toString()).append(" HP:");
            IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer4 == null) {
                Intrinsics.throwNpe();
            }
            iFontRenderer.drawString(sbAppend4.append(thePlayer4.getHealth()).toString(), 16.0f, scaledHeight - 16.0f, -1);
            StringBuilder sbAppend5 = new StringBuilder().append("LiquidBounce Build 2022China - ");
            IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer5 == null) {
                Intrinsics.throwNpe();
            }
            String string = sbAppend5.append(thePlayer5.getName()).toString();
            Fonts.wqy35.drawString(string, (iScaledResolutionCreateScaledResolution.getScaledWidth() - iFontRenderer.getStringWidth(string)) - 5, iScaledResolutionCreateScaledResolution.getScaledHeight() - 16, -1);
            IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer6 == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer6.getInventory().getCurrentItem() == 0) {
                RenderUtils.drawRect((scaledWidth / 2.0f) - 91.0f, scaledHeight - 2.0f, ((scaledWidth / 2.0f) + 90.0f) - 160.0f, scaledHeight, new Color(47, 116, 253, 255).getRGB());
                RenderUtils.drawGradientSideways((scaledWidth / 2.0d) - 91.0d, scaledHeight - 20.0d, ((scaledWidth / 2.0d) + 90.0d) - 160.0d, scaledHeight, new Color(47, 116, 253, 180).getRGB(), new Color(47, 116, 253, 0).getRGB());
            } else {
                float f = (scaledWidth / 2.0f) - 91.0f;
                if (MinecraftInstance.f157mc.getThePlayer() == null) {
                    Intrinsics.throwNpe();
                }
                float currentItem = f + (r1.getInventory().getCurrentItem() * 20);
                float f2 = scaledHeight - 2.0f;
                float f3 = (scaledWidth / 2.0f) + 90.0f;
                if (MinecraftInstance.f157mc.getThePlayer() == null) {
                    Intrinsics.throwNpe();
                }
                RenderUtils.drawRect(currentItem, f2, f3 - (20 * (8 - r5.getInventory().getCurrentItem())), scaledHeight, new Color(47, 116, 253, 255).getRGB());
                double d = (scaledWidth / 2.0d) - 91.0d;
                if (MinecraftInstance.f157mc.getThePlayer() == null) {
                    Intrinsics.throwNpe();
                }
                double currentItem2 = d + (r1.getInventory().getCurrentItem() * 20);
                double d2 = scaledHeight - 20.0d;
                double d3 = (scaledWidth / 2.0d) + 90.0d;
                if (MinecraftInstance.f157mc.getThePlayer() == null) {
                    Intrinsics.throwNpe();
                }
                RenderUtils.drawGradientSideways(currentItem2, d2, d3 - (20 * (8 - r5.getInventory().getCurrentItem())), scaledHeight, new Color(47, 116, 253, 180).getRGB(), new Color(47, 116, 253, 0).getRGB());
            }
            RenderHelper.func_74520_c();
            for (int i = 0; i <= 8; i++) {
                int i2 = (int) (((scaledWidth / 2.0f) - 90.0f) + (i * 20) + 2.0f);
                int i3 = (int) ((scaledHeight - 16.0f) - 3.0f);
                int i4 = i;
                if (render2DEvent == null) {
                    Intrinsics.throwNpe();
                }
                float partialTicks = render2DEvent.getPartialTicks();
                IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer7 == null) {
                    Intrinsics.throwNpe();
                }
                customRenderHotbarItem(i4, i2, i3, partialTicks, thePlayer7);
            }
            GlStateManager.func_179084_k();
            GlStateManager.func_179124_c(1.0f, 1.0f, 1.0f);
            RenderHelper.func_74518_a();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public final void customRenderHotbarItem(int i, int i2, int i3, float f, @NotNull IEntityPlayer p_175184_5_) {
        Intrinsics.checkParameterIsNotNull(p_175184_5_, "p_175184_5_");
        GlStateManager.func_179084_k();
        IItemStack iItemStack = (IItemStack) p_175184_5_.getInventory().getMainInventory().get(i);
        if (iItemStack != null) {
            float animationsToGo = iItemStack.getAnimationsToGo() - f;
            if (animationsToGo > 0.0f) {
                GlStateManager.func_179094_E();
                float f2 = 1.0f + (animationsToGo / 5.0f);
                GlStateManager.func_179109_b(i2 + 8, i3 + 12, 0.0f);
                GlStateManager.func_179152_a(1.0f / f2, (f2 + 1.0f) / 2.0f, 1.0f);
                GlStateManager.func_179109_b(-(i2 + 8), -(i3 + 12), 0.0f);
            }
            MinecraftInstance.f157mc.getRenderItem().renderItemAndEffectIntoGUI(iItemStack, i2, i3);
            if (animationsToGo > 0.0f) {
                GlStateManager.func_179121_F();
            }
            MinecraftInstance.f157mc.getRenderItem().renderItemOverlays(MinecraftInstance.f157mc.getFontRendererObj(), iItemStack, i2 - 1, i3);
        }
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent updateEvent) {
        LiquidBounce.INSTANCE.getHud().update();
    }

    @EventTarget
    public final void onKey(@NotNull KeyEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        LiquidBounce.INSTANCE.getHud().handleKey('a', event.getKey());
    }

    @EventTarget(ignoreCondition = true)
    public final void onScreen(@NotNull ScreenEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.f157mc.getTheWorld() == null || MinecraftInstance.f157mc.getThePlayer() == null) {
            return;
        }
        if (!getState() || !((Boolean) this.blurValue.get()).booleanValue() || MinecraftInstance.f157mc.getEntityRenderer().isShaderActive() || event.getGuiScreen() == null || MinecraftInstance.classProvider.isGuiChat(event.getGuiScreen()) || MinecraftInstance.classProvider.isGuiHudDesigner(event.getGuiScreen())) {
            if (MinecraftInstance.f157mc.getEntityRenderer().getShaderGroup() != null) {
                IShaderGroup shaderGroup = MinecraftInstance.f157mc.getEntityRenderer().getShaderGroup();
                if (shaderGroup == null) {
                    Intrinsics.throwNpe();
                }
                if (StringsKt.contains$default((CharSequence) shaderGroup.getShaderGroupName(), (CharSequence) "liquidbounce/blur.json", false, 2, (Object) null)) {
                    MinecraftInstance.f157mc.getEntityRenderer().stopUseShader();
                    return;
                }
                return;
            }
            return;
        }
        MinecraftInstance.f157mc.getEntityRenderer().loadShader(MinecraftInstance.classProvider.createResourceLocation("liquidbounce/blur.json"));
    }
}
