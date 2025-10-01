package net.ccbluex.liquidbounce.features.module.modules.render;

import co.p000uk.hexeption.utils.OutlineUtils;
import java.awt.Color;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.api.minecraft.tileentity.ITileEntity;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.world.ChestAura;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.render.shader.FramebufferShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.GlowShader;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.OutlineShader;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0011H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0012"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/StorageESP;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "chestValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "dispenserValue", "enderChestValue", "furnaceValue", "hopperValue", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "shulkerBoxValue", "onRender2D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "StorageESP", description = "Allows you to see chests, dispensers, etc. through walls.", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/StorageESP.class */
public final class StorageESP extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Box", "OtherBox", "Outline", "ShaderOutline", "ShaderGlow", "2D", "WireFrame"}, "Outline");
    private final BoolValue chestValue = new BoolValue("Chest", true);
    private final BoolValue enderChestValue = new BoolValue("EnderChest", true);
    private final BoolValue furnaceValue = new BoolValue("Furnace", true);
    private final BoolValue dispenserValue = new BoolValue("Dispenser", true);
    private final BoolValue hopperValue = new BoolValue("Hopper", true);
    private final BoolValue shulkerBoxValue = new BoolValue("ShulkerBox", true);

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Color colorBrighter;
        Intrinsics.checkParameterIsNotNull(event, "event");
        try {
            String str = (String) this.modeValue.get();
            if (StringsKt.equals(str, "outline", true)) {
                ClientUtils.disableFastRender();
                OutlineUtils.checkSetupFBO();
            }
            float gammaSetting = MinecraftInstance.f157mc.getGameSettings().getGammaSetting();
            MinecraftInstance.f157mc.getGameSettings().setGammaSetting(100000.0f);
            IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld == null) {
                Intrinsics.throwNpe();
            }
            for (ITileEntity iTileEntity : theWorld.getLoadedTileEntityList()) {
                if (((Boolean) this.chestValue.get()).booleanValue() && MinecraftInstance.classProvider.isTileEntityChest(iTileEntity) && !ChestAura.INSTANCE.getClickedBlocks().contains(iTileEntity.getPos())) {
                    colorBrighter = new Color(0, 66, 255);
                } else if (((Boolean) this.enderChestValue.get()).booleanValue() && MinecraftInstance.classProvider.isTileEntityEnderChest(iTileEntity) && !ChestAura.INSTANCE.getClickedBlocks().contains(iTileEntity.getPos())) {
                    colorBrighter = Color.MAGENTA;
                } else if (((Boolean) this.furnaceValue.get()).booleanValue() && MinecraftInstance.classProvider.isTileEntityFurnace(iTileEntity)) {
                    colorBrighter = Color.BLACK;
                } else if (((Boolean) this.dispenserValue.get()).booleanValue() && MinecraftInstance.classProvider.isTileEntityDispenser(iTileEntity)) {
                    colorBrighter = Color.BLACK;
                } else if (((Boolean) this.hopperValue.get()).booleanValue() && MinecraftInstance.classProvider.isTileEntityHopper(iTileEntity)) {
                    colorBrighter = Color.GRAY;
                } else {
                    colorBrighter = (((Boolean) this.shulkerBoxValue.get()).booleanValue() && MinecraftInstance.classProvider.isTileEntityShulkerBox(iTileEntity)) ? new Color(110, 77, 110).brighter() : null;
                }
                if (colorBrighter != null) {
                    Color color = colorBrighter;
                    if (!MinecraftInstance.classProvider.isTileEntityChest(iTileEntity) && !MinecraftInstance.classProvider.isTileEntityChest(iTileEntity)) {
                        RenderUtils.drawBlockBox(iTileEntity.getPos(), color, !StringsKt.equals(str, "otherbox", true));
                    } else {
                        if (str == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                        String lowerCase = str.toLowerCase();
                        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                        switch (lowerCase.hashCode()) {
                            case -1171135301:
                                if (lowerCase.equals("otherbox")) {
                                    break;
                                } else {
                                    break;
                                }
                            case -1106245566:
                                if (lowerCase.equals("outline")) {
                                    RenderUtils.glColor(color);
                                    OutlineUtils.renderOne(3.0f);
                                    MinecraftInstance.functions.renderTileEntity(iTileEntity, event.getPartialTicks(), -1);
                                    OutlineUtils.renderTwo();
                                    MinecraftInstance.functions.renderTileEntity(iTileEntity, event.getPartialTicks(), -1);
                                    OutlineUtils.renderThree();
                                    MinecraftInstance.functions.renderTileEntity(iTileEntity, event.getPartialTicks(), -1);
                                    OutlineUtils.renderFour(color);
                                    MinecraftInstance.functions.renderTileEntity(iTileEntity, event.getPartialTicks(), -1);
                                    OutlineUtils.renderFive();
                                    OutlineUtils.setColor(Color.WHITE);
                                    break;
                                } else {
                                    continue;
                                }
                            case -941784056:
                                if (!lowerCase.equals("wireframe")) {
                                    break;
                                } else {
                                    GL11.glPushMatrix();
                                    GL11.glPushAttrib(1048575);
                                    GL11.glPolygonMode(1032, 6913);
                                    GL11.glDisable(SGL.GL_TEXTURE_2D);
                                    GL11.glDisable(2896);
                                    GL11.glDisable(SGL.GL_DEPTH_TEST);
                                    GL11.glEnable(SGL.GL_LINE_SMOOTH);
                                    GL11.glEnable(SGL.GL_BLEND);
                                    GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
                                    MinecraftInstance.functions.renderTileEntity(iTileEntity, event.getPartialTicks(), -1);
                                    RenderUtils.glColor(color);
                                    GL11.glLineWidth(1.5f);
                                    MinecraftInstance.functions.renderTileEntity(iTileEntity, event.getPartialTicks(), -1);
                                    GL11.glPopAttrib();
                                    GL11.glPopMatrix();
                                    continue;
                                }
                            case 1650:
                                if (lowerCase.equals("2d")) {
                                    WBlockPos pos = iTileEntity.getPos();
                                    int rgb = color.getRGB();
                                    Color color2 = Color.BLACK;
                                    Intrinsics.checkExpressionValueIsNotNull(color2, "Color.BLACK");
                                    RenderUtils.draw2D(pos, rgb, color2.getRGB());
                                    break;
                                } else {
                                    continue;
                                }
                            case 97739:
                                if (!lowerCase.equals("box")) {
                                    break;
                                }
                                break;
                        }
                        RenderUtils.drawBlockBox(iTileEntity.getPos(), color, !StringsKt.equals(str, "otherbox", true));
                    }
                }
            }
            IWorldClient theWorld2 = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld2 == null) {
                Intrinsics.throwNpe();
            }
            for (IEntity iEntity : theWorld2.getLoadedEntityList()) {
                if (MinecraftInstance.classProvider.isEntityMinecartChest(iEntity)) {
                    if (str == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String lowerCase2 = str.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(lowerCase2, "(this as java.lang.String).toLowerCase()");
                    switch (lowerCase2.hashCode()) {
                        case -1171135301:
                            if (lowerCase2.equals("otherbox")) {
                                break;
                            } else {
                                break;
                            }
                        case -1106245566:
                            if (lowerCase2.equals("outline")) {
                                boolean entityShadows = MinecraftInstance.f157mc.getGameSettings().getEntityShadows();
                                MinecraftInstance.f157mc.getGameSettings().setEntityShadows(false);
                                RenderUtils.glColor(new Color(0, 66, 255));
                                OutlineUtils.renderOne(3.0f);
                                MinecraftInstance.f157mc.getRenderManager().renderEntityStatic(iEntity, MinecraftInstance.f157mc.getTimer().getRenderPartialTicks(), true);
                                OutlineUtils.renderTwo();
                                MinecraftInstance.f157mc.getRenderManager().renderEntityStatic(iEntity, MinecraftInstance.f157mc.getTimer().getRenderPartialTicks(), true);
                                OutlineUtils.renderThree();
                                MinecraftInstance.f157mc.getRenderManager().renderEntityStatic(iEntity, MinecraftInstance.f157mc.getTimer().getRenderPartialTicks(), true);
                                OutlineUtils.renderFour(new Color(0, 66, 255));
                                MinecraftInstance.f157mc.getRenderManager().renderEntityStatic(iEntity, MinecraftInstance.f157mc.getTimer().getRenderPartialTicks(), true);
                                OutlineUtils.renderFive();
                                OutlineUtils.setColor(Color.WHITE);
                                MinecraftInstance.f157mc.getGameSettings().setEntityShadows(entityShadows);
                                break;
                            } else {
                                continue;
                            }
                        case -941784056:
                            if (!lowerCase2.equals("wireframe")) {
                                break;
                            } else {
                                boolean entityShadows2 = MinecraftInstance.f157mc.getGameSettings().getEntityShadows();
                                MinecraftInstance.f157mc.getGameSettings().setEntityShadows(false);
                                GL11.glPushMatrix();
                                GL11.glPushAttrib(1048575);
                                GL11.glPolygonMode(1032, 6913);
                                GL11.glDisable(SGL.GL_TEXTURE_2D);
                                GL11.glDisable(2896);
                                GL11.glDisable(SGL.GL_DEPTH_TEST);
                                GL11.glEnable(SGL.GL_LINE_SMOOTH);
                                GL11.glEnable(SGL.GL_BLEND);
                                GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
                                RenderUtils.glColor(new Color(0, 66, 255));
                                MinecraftInstance.f157mc.getRenderManager().renderEntityStatic(iEntity, MinecraftInstance.f157mc.getTimer().getRenderPartialTicks(), true);
                                RenderUtils.glColor(new Color(0, 66, 255));
                                GL11.glLineWidth(1.5f);
                                MinecraftInstance.f157mc.getRenderManager().renderEntityStatic(iEntity, MinecraftInstance.f157mc.getTimer().getRenderPartialTicks(), true);
                                GL11.glPopAttrib();
                                GL11.glPopMatrix();
                                MinecraftInstance.f157mc.getGameSettings().setEntityShadows(entityShadows2);
                                continue;
                            }
                        case 1650:
                            if (lowerCase2.equals("2d")) {
                                WBlockPos position = iEntity.getPosition();
                                int rgb2 = new Color(0, 66, 255).getRGB();
                                Color color3 = Color.BLACK;
                                Intrinsics.checkExpressionValueIsNotNull(color3, "Color.BLACK");
                                RenderUtils.draw2D(position, rgb2, color3.getRGB());
                                break;
                            } else {
                                continue;
                            }
                        case 97739:
                            if (!lowerCase2.equals("box")) {
                                break;
                            }
                            break;
                    }
                    RenderUtils.drawEntityBox(iEntity, new Color(0, 66, 255), !StringsKt.equals(str, "otherbox", true));
                }
            }
            RenderUtils.glColor(new Color(255, 255, 255, 255));
            MinecraftInstance.f157mc.getGameSettings().setGammaSetting(gammaSetting);
        } catch (Exception unused) {
        }
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        String str = (String) this.modeValue.get();
        GlowShader glowShader = StringsKt.equals(str, "shaderoutline", true) ? OutlineShader.OUTLINE_SHADER : StringsKt.equals(str, "shaderglow", true) ? GlowShader.GLOW_SHADER : null;
        if (glowShader != null) {
            FramebufferShader framebufferShader = glowShader;
            framebufferShader.startDraw(event.getPartialTicks());
            IRenderManager renderManager = MinecraftInstance.f157mc.getRenderManager();
            IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld == null) {
                Intrinsics.throwNpe();
            }
            for (ITileEntity iTileEntity : theWorld.getLoadedTileEntityList()) {
                if (MinecraftInstance.classProvider.isTileEntityChest(iTileEntity) && !ChestAura.INSTANCE.getClickedBlocks().contains(iTileEntity.getPos())) {
                    MinecraftInstance.f157mc.getRenderManager().renderEntityAt(iTileEntity, iTileEntity.getPos().getX() - renderManager.getRenderPosX(), iTileEntity.getPos().getY() - renderManager.getRenderPosY(), iTileEntity.getPos().getZ() - renderManager.getRenderPosZ(), event.getPartialTicks());
                }
            }
            IWorldClient theWorld2 = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld2 == null) {
                Intrinsics.throwNpe();
            }
            for (IEntity iEntity : theWorld2.getLoadedEntityList()) {
                if (MinecraftInstance.classProvider.isEntityMinecartChest(iEntity)) {
                    renderManager.renderEntityStatic(iEntity, event.getPartialTicks(), true);
                }
            }
            framebufferShader.stopDraw(new Color(0, 66, 255), StringsKt.equals(str, "shaderglow", true) ? 2.5f : 1.5f, 1.0f);
        }
    }
}
