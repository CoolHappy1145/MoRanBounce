package net.ccbluex.liquidbounce.features.module.modules.render;

import java.awt.Color;
import jdk.nashorn.tools.Shell;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0015H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0013\u0010\t\u001a\u0004\u0018\u00010\n8F\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\r\u001a\u00020\u0007\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0016"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/BlockOverlay;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "colorBlueValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "colorGreenValue", "colorRainbow", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "colorRedValue", "currentBlock", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getCurrentBlock", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "infoValue", "getInfoValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "BlockOverlay", description = "Allows you to change the design of the block overlay.", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/BlockOverlay.class */
public final class BlockOverlay extends Module {
    private final IntegerValue colorRedValue = new IntegerValue("R", 68, 0, 255);
    private final IntegerValue colorGreenValue = new IntegerValue("G", 117, 0, 255);
    private final IntegerValue colorBlueValue = new IntegerValue("B", 255, 0, 255);
    private final BoolValue colorRainbow = new BoolValue("Rainbow", false);

    @NotNull
    private final BoolValue infoValue = new BoolValue("Info", false);

    @NotNull
    public final BoolValue getInfoValue() {
        return this.infoValue;
    }

    @Nullable
    public final WBlockPos getCurrentBlock() {
        WBlockPos blockPos;
        IMovingObjectPosition objectMouseOver = MinecraftInstance.f157mc.getObjectMouseOver();
        if (objectMouseOver == null || (blockPos = objectMouseOver.getBlockPos()) == null || !BlockUtils.canBeClicked(blockPos)) {
            return null;
        }
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        if (theWorld.getWorldBorder().contains(blockPos)) {
            return blockPos;
        }
        return null;
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        WBlockPos currentBlock = getCurrentBlock();
        if (currentBlock != null) {
            IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld == null) {
                Intrinsics.throwNpe();
            }
            IBlock block = theWorld.getBlockState(currentBlock).getBlock();
            float partialTicks = event.getPartialTicks();
            Color colorRainbow = ((Boolean) this.colorRainbow.get()).booleanValue() ? ColorUtils.rainbow(0.4f) : new Color(((Number) this.colorRedValue.get()).intValue(), ((Number) this.colorGreenValue.get()).intValue(), ((Number) this.colorBlueValue.get()).intValue(), Shell.RUNTIME_ERROR);
            MinecraftInstance.classProvider.getGlStateManager().enableBlend();
            MinecraftInstance.classProvider.getGlStateManager().tryBlendFuncSeparate(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            RenderUtils.glColor(colorRainbow);
            GL11.glLineWidth(2.0f);
            MinecraftInstance.classProvider.getGlStateManager().disableTexture2D();
            GL11.glDepthMask(false);
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer != null) {
                double lastTickPosX = thePlayer.getLastTickPosX() + ((thePlayer.getPosX() - thePlayer.getLastTickPosX()) * partialTicks);
                double lastTickPosY = thePlayer.getLastTickPosY() + ((thePlayer.getPosY() - thePlayer.getLastTickPosY()) * partialTicks);
                double lastTickPosZ = thePlayer.getLastTickPosZ() + ((thePlayer.getPosZ() - thePlayer.getLastTickPosZ()) * partialTicks);
                IWorldClient theWorld2 = MinecraftInstance.f157mc.getTheWorld();
                if (theWorld2 == null) {
                    Intrinsics.throwNpe();
                }
                IWorldClient iWorldClient = theWorld2;
                IWorldClient theWorld3 = MinecraftInstance.f157mc.getTheWorld();
                if (theWorld3 == null) {
                    Intrinsics.throwNpe();
                }
                IAxisAlignedBB iAxisAlignedBBOffset = block.getSelectedBoundingBox(iWorldClient, theWorld3.getBlockState(currentBlock), currentBlock).expand(0.0020000000949949026d, 0.0020000000949949026d, 0.0020000000949949026d).offset(-lastTickPosX, -lastTickPosY, -lastTickPosZ);
                RenderUtils.drawSelectionBoundingBox(iAxisAlignedBBOffset);
                RenderUtils.drawFilledBox(iAxisAlignedBBOffset);
                GL11.glDepthMask(true);
                MinecraftInstance.classProvider.getGlStateManager().enableTexture2D();
                MinecraftInstance.classProvider.getGlStateManager().disableBlend();
                MinecraftInstance.classProvider.getGlStateManager().resetColor();
            }
        }
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        WBlockPos currentBlock;
        IBlock block;
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (!((Boolean) this.infoValue.get()).booleanValue() || (currentBlock = getCurrentBlock()) == null || (block = BlockUtils.getBlock(currentBlock)) == null) {
            return;
        }
        String str = block.getLocalizedName() + " \u00a77ID: " + MinecraftInstance.functions.getIdFromBlock(block);
        IClassProvider iClassProvider = MinecraftInstance.classProvider;
        IMinecraft mc = MinecraftInstance.f157mc;
        Intrinsics.checkExpressionValueIsNotNull(mc, "mc");
        IScaledResolution iScaledResolutionCreateScaledResolution = iClassProvider.createScaledResolution(mc);
        Color color = Color.BLACK;
        Intrinsics.checkExpressionValueIsNotNull(color, "Color.BLACK");
        int rgb = color.getRGB();
        Color color2 = Color.BLACK;
        Intrinsics.checkExpressionValueIsNotNull(color2, "Color.BLACK");
        RenderUtils.drawBorderedRect((iScaledResolutionCreateScaledResolution.getScaledWidth() / 2) - 2.0f, (iScaledResolutionCreateScaledResolution.getScaledHeight() / 2) + 5.0f, (iScaledResolutionCreateScaledResolution.getScaledWidth() / 2) + Fonts.font40.getStringWidth(str) + 2.0f, (iScaledResolutionCreateScaledResolution.getScaledHeight() / 2) + 16.0f, 3.0f, rgb, color2.getRGB());
        MinecraftInstance.classProvider.getGlStateManager().resetColor();
        Color color3 = Color.WHITE;
        Intrinsics.checkExpressionValueIsNotNull(color3, "Color.WHITE");
        Fonts.font40.drawString(str, iScaledResolutionCreateScaledResolution.getScaledWidth() / 2.0f, (iScaledResolutionCreateScaledResolution.getScaledHeight() / 2.0f) + 7.0f, color3.getRGB(), false);
    }
}
