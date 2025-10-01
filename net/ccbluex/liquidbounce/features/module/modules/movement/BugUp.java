package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.renderer.entity.IRenderManager;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.misc.FallingPlayer;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdT\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u0010\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0007J\u0010\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001aH\u0007J\u0010\u0010\u001b\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u001cH\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001d"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/BugUp;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "detectedLocation", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "indicator", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "lastFound", "", "maxDistanceWithoutGround", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "maxFallDistance", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "prevX", "", "prevY", "prevZ", "onDisable", "", "onPacket", "e", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "BugUp", description = "Automatically setbacks you after falling a certain distance.", category = ModuleCategory.MOVEMENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/BugUp.class */
public final class BugUp extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"TeleportBack", "FlyFlag", "HytAntiFall", "OnGroundSpoof", "MotionTeleport-Flag"}, "FlyFlag");
    private final IntegerValue maxFallDistance = new IntegerValue("MaxFallDistance", 10, 2, 255);
    private final FloatValue maxDistanceWithoutGround = new FloatValue("MaxDistanceToSetback", 2.5f, 1.0f, 30.0f);
    private final BoolValue indicator = new BoolValue("Indicator", true);
    private WBlockPos detectedLocation;
    private float lastFound;
    private double prevX;
    private double prevY;
    private double prevZ;

    public void onDisable() {
        this.prevX = 0.0d;
        this.prevY = 0.0d;
        this.prevZ = 0.0d;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        this.detectedLocation = (WBlockPos) null;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            if (thePlayer.getOnGround() && !MinecraftInstance.classProvider.isBlockAir(BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() - 1.0d, thePlayer.getPosZ())))) {
                this.prevX = thePlayer.getPrevPosX();
                this.prevY = thePlayer.getPrevPosY();
                this.prevZ = thePlayer.getPrevPosZ();
            }
            if (!thePlayer.getOnGround() && !thePlayer.isOnLadder() && !thePlayer.isInWater()) {
                FallingPlayer.CollisionResult collisionResultFindCollision = new FallingPlayer(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ(), thePlayer.getMotionX(), thePlayer.getMotionY(), thePlayer.getMotionZ(), thePlayer.getRotationYaw(), thePlayer.getMoveStrafing(), thePlayer.getMoveForward()).findCollision(60);
                this.detectedLocation = collisionResultFindCollision != null ? collisionResultFindCollision.getPos() : null;
                if (this.detectedLocation != null) {
                    double posY = thePlayer.getPosY();
                    if (this.detectedLocation == null) {
                        Intrinsics.throwNpe();
                    }
                    if (Math.abs(posY - r1.getY()) + thePlayer.getFallDistance() <= ((Number) this.maxFallDistance.get()).doubleValue()) {
                        this.lastFound = thePlayer.getFallDistance();
                    }
                }
                if (thePlayer.getFallDistance() - this.lastFound > ((Number) this.maxDistanceWithoutGround.get()).floatValue()) {
                    String str = (String) this.modeValue.get();
                    if (str == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String lowerCase = str.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                    switch (lowerCase.hashCode()) {
                        case -1718744413:
                            if (lowerCase.equals("ongroundspoof")) {
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayer(true));
                                return;
                            }
                            return;
                        case -757065121:
                            if (lowerCase.equals("flyflag")) {
                                thePlayer.setMotionY(thePlayer.getMotionY() + 0.1d);
                                thePlayer.setFallDistance(0.0f);
                                return;
                            }
                            return;
                        case -198873454:
                            if (lowerCase.equals("teleportback")) {
                                thePlayer.setPositionAndUpdate(this.prevX, this.prevY, this.prevZ);
                                thePlayer.setFallDistance(0.0f);
                                thePlayer.setMotionY(0.0d);
                                return;
                            }
                            return;
                        case 1873339608:
                            if (lowerCase.equals("motionteleport-flag")) {
                                thePlayer.setPositionAndUpdate(thePlayer.getPosX(), thePlayer.getPosY() + 1.0d, thePlayer.getPosZ());
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ(), true));
                                thePlayer.setMotionY(0.1d);
                                MovementUtils.strafe$default(0.0f, 1, null);
                                thePlayer.setFallDistance(0.0f);
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        this.detectedLocation = (WBlockPos) null;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            if (thePlayer.getOnGround() && !MinecraftInstance.classProvider.isBlockAir(BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() - 1.0d, thePlayer.getPosZ())))) {
                this.prevX = thePlayer.getPrevPosX();
                this.prevY = thePlayer.getPrevPosY();
                this.prevZ = thePlayer.getPrevPosZ();
            }
            if (!thePlayer.getOnGround() && !thePlayer.isOnLadder() && !thePlayer.isInWater()) {
                FallingPlayer.CollisionResult collisionResultFindCollision = new FallingPlayer(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ(), thePlayer.getMotionX(), thePlayer.getMotionY(), thePlayer.getMotionZ(), thePlayer.getRotationYaw(), thePlayer.getMoveStrafing(), thePlayer.getMoveForward()).findCollision(60);
                this.detectedLocation = collisionResultFindCollision != null ? collisionResultFindCollision.getPos() : null;
                if (this.detectedLocation != null) {
                    double posY = thePlayer.getPosY();
                    if (this.detectedLocation == null) {
                        Intrinsics.throwNpe();
                    }
                    if (Math.abs(posY - r1.getY()) + thePlayer.getFallDistance() <= ((Number) this.maxFallDistance.get()).doubleValue()) {
                        this.lastFound = thePlayer.getFallDistance();
                    }
                }
                if (thePlayer.getFallDistance() - this.lastFound > ((Number) this.maxDistanceWithoutGround.get()).floatValue()) {
                    String str = (String) this.modeValue.get();
                    if (str == null) {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                    String lowerCase = str.toLowerCase();
                    Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                    switch (lowerCase.hashCode()) {
                        case 97156192:
                            if (lowerCase.equals("hytantifall") && MinecraftInstance.classProvider.isCPacketPlayer(e.getPacket())) {
                                IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer2 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posX = thePlayer2.getPosX();
                                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer3 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double posY2 = thePlayer3.getPosY() + 0.0135d;
                                IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer4 == null) {
                                    Intrinsics.throwNpe();
                                }
                                netHandler.addToSendQueue(iClassProvider.createCPacketPlayerPosition(posX, posY2, thePlayer4.getPosZ(), true));
                                return;
                            }
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null && this.detectedLocation != null && ((Boolean) this.indicator.get()).booleanValue()) {
            double fallDistance = thePlayer.getFallDistance();
            double posY = thePlayer.getPosY();
            if (this.detectedLocation == null) {
                Intrinsics.throwNpe();
            }
            if (fallDistance + (posY - (r2.getY() + 1)) < 3.0d) {
                return;
            }
            WBlockPos wBlockPos = this.detectedLocation;
            if (wBlockPos == null) {
                Intrinsics.throwNpe();
            }
            int x = wBlockPos.getX();
            WBlockPos wBlockPos2 = this.detectedLocation;
            if (wBlockPos2 == null) {
                Intrinsics.throwNpe();
            }
            int y = wBlockPos2.getY();
            WBlockPos wBlockPos3 = this.detectedLocation;
            if (wBlockPos3 == null) {
                Intrinsics.throwNpe();
            }
            int z = wBlockPos3.getZ();
            IRenderManager renderManager = MinecraftInstance.f157mc.getRenderManager();
            GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(SGL.GL_BLEND);
            GL11.glLineWidth(2.0f);
            GL11.glDisable(SGL.GL_TEXTURE_2D);
            GL11.glDisable(SGL.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            RenderUtils.glColor(new Color(255, 0, 0, 90));
            RenderUtils.drawFilledBox(MinecraftInstance.classProvider.createAxisAlignedBB(x - renderManager.getRenderPosX(), (y + 1) - renderManager.getRenderPosY(), z - renderManager.getRenderPosZ(), (x - renderManager.getRenderPosX()) + 1.0d, (y + 1.2d) - renderManager.getRenderPosY(), (z - renderManager.getRenderPosZ()) + 1.0d));
            GL11.glEnable(SGL.GL_TEXTURE_2D);
            GL11.glEnable(SGL.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            GL11.glDisable(SGL.GL_BLEND);
            int iFloor = (int) Math.floor(thePlayer.getFallDistance() + (thePlayer.getPosY() - (y + 0.5d)));
            RenderUtils.renderNameTag(iFloor + "m (~" + Math.max(0, iFloor - 3) + " damage)", x + 0.5d, y + 1.7d, z + 0.5d);
            MinecraftInstance.classProvider.getGlStateManager().resetColor();
        }
    }
}
