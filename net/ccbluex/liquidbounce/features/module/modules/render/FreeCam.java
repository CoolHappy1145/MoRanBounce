package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityOtherPlayerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u000fH\u0016J\u0010\u0010\u0011\u001a\u00020\u000f2\u0006\u0010\u0012\u001a\u00020\u0013H\u0007J\u0012\u0010\u0014\u001a\u00020\u000f2\b\u0010\u0012\u001a\u0004\u0018\u00010\u0015H\u0007R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0016"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/FreeCam;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "fakePlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityOtherPlayerMP;", "flyValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "noClipValue", "oldX", "", "oldY", "oldZ", "speedValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onDisable", "", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "FreeCam", description = "Allows you to move out of your body.", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/FreeCam.class */
public final class FreeCam extends Module {
    private final FloatValue speedValue = new FloatValue("Speed", 0.8f, 0.1f, 2.0f);
    private final BoolValue flyValue = new BoolValue("Fly", true);
    private final BoolValue noClipValue = new BoolValue("NoClip", true);
    private IEntityOtherPlayerMP fakePlayer;
    private double oldX;
    private double oldY;
    private double oldZ;

    public void onEnable() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            this.oldX = thePlayer.getPosX();
            this.oldY = thePlayer.getPosY();
            this.oldZ = thePlayer.getPosZ();
            IClassProvider iClassProvider = MinecraftInstance.classProvider;
            IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld == null) {
                Intrinsics.throwNpe();
            }
            IEntityOtherPlayerMP iEntityOtherPlayerMPCreateEntityOtherPlayerMP = iClassProvider.createEntityOtherPlayerMP(theWorld, thePlayer.getGameProfile());
            iEntityOtherPlayerMPCreateEntityOtherPlayerMP.setRotationYawHead(thePlayer.getRotationYawHead());
            iEntityOtherPlayerMPCreateEntityOtherPlayerMP.setRenderYawOffset(thePlayer.getRenderYawOffset());
            iEntityOtherPlayerMPCreateEntityOtherPlayerMP.setRotationYawHead(thePlayer.getRotationYawHead());
            iEntityOtherPlayerMPCreateEntityOtherPlayerMP.copyLocationAndAnglesFrom(thePlayer);
            IWorldClient theWorld2 = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld2 == null) {
                Intrinsics.throwNpe();
            }
            theWorld2.addEntityToWorld(-1000, iEntityOtherPlayerMPCreateEntityOtherPlayerMP);
            if (((Boolean) this.noClipValue.get()).booleanValue()) {
                thePlayer.setNoClip(true);
            }
            this.fakePlayer = iEntityOtherPlayerMPCreateEntityOtherPlayerMP;
        }
    }

    public void onDisable() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null || this.fakePlayer == null) {
            return;
        }
        thePlayer.setPositionAndRotation(this.oldX, this.oldY, this.oldZ, thePlayer.getRotationYaw(), thePlayer.getRotationPitch());
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        IEntityOtherPlayerMP iEntityOtherPlayerMP = this.fakePlayer;
        if (iEntityOtherPlayerMP == null) {
            Intrinsics.throwNpe();
        }
        theWorld.removeEntityFromWorld(iEntityOtherPlayerMP.getEntityId());
        this.fakePlayer = (IEntityOtherPlayerMP) null;
        thePlayer.setMotionX(0.0d);
        thePlayer.setMotionY(0.0d);
        thePlayer.setMotionZ(0.0d);
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent updateEvent) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (((Boolean) this.noClipValue.get()).booleanValue()) {
            thePlayer.setNoClip(true);
        }
        thePlayer.setFallDistance(0.0f);
        if (((Boolean) this.flyValue.get()).booleanValue()) {
            float fFloatValue = ((Number) this.speedValue.get()).floatValue();
            thePlayer.setMotionY(0.0d);
            thePlayer.setMotionX(0.0d);
            thePlayer.setMotionZ(0.0d);
            if (MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                thePlayer.setMotionY(thePlayer.getMotionY() + fFloatValue);
            }
            if (MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                thePlayer.setMotionY(thePlayer.getMotionY() - fFloatValue);
            }
            MovementUtils.strafe(fFloatValue);
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();
        if (MinecraftInstance.classProvider.isCPacketPlayer(packet) || MinecraftInstance.classProvider.isCPacketEntityAction(packet)) {
            event.cancelEvent();
        }
    }
}
