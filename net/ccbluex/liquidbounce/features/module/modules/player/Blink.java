package net.ccbluex.liquidbounce.features.module.modules.player;

import java.awt.Color;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.INetworkManager;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityOtherPlayerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.render.Breadcrumbs;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd`\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0010\u0013\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\u0018H\u0016J\b\u0010\u001a\u001a\u00020\u0018H\u0016J\u0010\u0010\u001b\u001a\u00020\u00182\u0006\u0010\u001c\u001a\u00020\u001dH\u0007J\u0012\u0010\u001e\u001a\u00020\u00182\b\u0010\u001c\u001a\u0004\u0018\u00010\u001fH\u0007J\u0012\u0010 \u001a\u00020\u00182\b\u0010\u001c\u001a\u0004\u0018\u00010!H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\u0012X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\""}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/Blink;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "disableLogger", "", "fakePlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityOtherPlayerMP;", "packets", "Ljava/util/concurrent/LinkedBlockingQueue;", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "positions", "Ljava/util/LinkedList;", "", "pulseDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "pulseTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "pulseValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "tag", "", "getTag", "()Ljava/lang/String;", "blink", "", "onDisable", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Blink", description = "Suspends all movement packets.", category = ModuleCategory.PLAYER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/Blink.class */
public final class Blink extends Module {
    private IEntityOtherPlayerMP fakePlayer;
    private boolean disableLogger;
    private final LinkedBlockingQueue packets = new LinkedBlockingQueue();
    private final LinkedList positions = new LinkedList();
    private final BoolValue pulseValue = new BoolValue("Pulse", false);
    private final IntegerValue pulseDelayValue = new IntegerValue("PulseDelay", 1000, SharedScopeCall.SLOW_SCOPE_CALL_THRESHOLD, 5000);
    private final MSTimer pulseTimer = new MSTimer();

    public void onEnable() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            if (!((Boolean) this.pulseValue.get()).booleanValue()) {
                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                if (theWorld == null) {
                    Intrinsics.throwNpe();
                }
                IEntityOtherPlayerMP iEntityOtherPlayerMPCreateEntityOtherPlayerMP = iClassProvider.createEntityOtherPlayerMP(theWorld, thePlayer.getGameProfile());
                iEntityOtherPlayerMPCreateEntityOtherPlayerMP.setRotationYawHead(thePlayer.getRotationYawHead());
                iEntityOtherPlayerMPCreateEntityOtherPlayerMP.setRenderYawOffset(thePlayer.getRenderYawOffset());
                iEntityOtherPlayerMPCreateEntityOtherPlayerMP.copyLocationAndAnglesFrom(thePlayer);
                iEntityOtherPlayerMPCreateEntityOtherPlayerMP.setRotationYawHead(thePlayer.getRotationYawHead());
                IWorldClient theWorld2 = MinecraftInstance.f157mc.getTheWorld();
                if (theWorld2 == null) {
                    Intrinsics.throwNpe();
                }
                theWorld2.addEntityToWorld(-1337, iEntityOtherPlayerMPCreateEntityOtherPlayerMP);
                this.fakePlayer = iEntityOtherPlayerMPCreateEntityOtherPlayerMP;
            }
            synchronized (this.positions) {
                this.positions.add(new double[]{thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY() + (thePlayer.getEyeHeight() / 2.0f), thePlayer.getPosZ()});
                this.positions.add(new double[]{thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY(), thePlayer.getPosZ()});
            }
            this.pulseTimer.reset();
        }
    }

    public void onDisable() throws InterruptedException {
        if (MinecraftInstance.f157mc.getThePlayer() == null) {
            return;
        }
        blink();
        IEntityOtherPlayerMP iEntityOtherPlayerMP = this.fakePlayer;
        if (iEntityOtherPlayerMP != null) {
            IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld != null) {
                theWorld.removeEntityFromWorld(iEntityOtherPlayerMP.getEntityId());
            }
            this.fakePlayer = (IEntityOtherPlayerMP) null;
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();
        if (MinecraftInstance.f157mc.getThePlayer() == null || this.disableLogger) {
            return;
        }
        if (MinecraftInstance.classProvider.isCPacketPlayer(packet)) {
            event.cancelEvent();
        }
        if (MinecraftInstance.classProvider.isCPacketPlayerPosition(packet) || MinecraftInstance.classProvider.isCPacketPlayerPosLook(packet) || MinecraftInstance.classProvider.isCPacketPlayerBlockPlacement(packet) || MinecraftInstance.classProvider.isCPacketAnimation(packet) || MinecraftInstance.classProvider.isCPacketEntityAction(packet) || MinecraftInstance.classProvider.isCPacketUseEntity(packet)) {
            event.cancelEvent();
            this.packets.add(packet);
        }
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent updateEvent) throws InterruptedException {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            synchronized (this.positions) {
                this.positions.add(new double[]{thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY(), thePlayer.getPosZ()});
            }
            if (((Boolean) this.pulseValue.get()).booleanValue() && this.pulseTimer.hasTimePassed(((Number) this.pulseDelayValue.get()).intValue())) {
                blink();
                this.pulseTimer.reset();
            }
        }
    }

    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent render3DEvent) {
        Breadcrumbs breadcrumbs = (Breadcrumbs) LiquidBounce.INSTANCE.getModuleManager().getModule(Breadcrumbs.class);
        if (breadcrumbs == null) {
            Intrinsics.throwNpe();
        }
        Color colorRainbow = ((Boolean) breadcrumbs.getColorRainbow().get()).booleanValue() ? ColorUtils.rainbow() : new Color(((Number) breadcrumbs.getColorRedValue().get()).intValue(), ((Number) breadcrumbs.getColorGreenValue().get()).intValue(), ((Number) breadcrumbs.getColorBlueValue().get()).intValue());
        synchronized (this.positions) {
            GL11.glPushMatrix();
            GL11.glDisable(SGL.GL_TEXTURE_2D);
            GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(SGL.GL_LINE_SMOOTH);
            GL11.glEnable(SGL.GL_BLEND);
            GL11.glDisable(SGL.GL_DEPTH_TEST);
            MinecraftInstance.f157mc.getEntityRenderer().disableLightmap();
            GL11.glBegin(3);
            RenderUtils.glColor(colorRainbow);
            double viewerPosX = MinecraftInstance.f157mc.getRenderManager().getViewerPosX();
            double viewerPosY = MinecraftInstance.f157mc.getRenderManager().getViewerPosY();
            double viewerPosZ = MinecraftInstance.f157mc.getRenderManager().getViewerPosZ();
            Iterator it = this.positions.iterator();
            while (it.hasNext()) {
                double[] dArr = (double[]) it.next();
                GL11.glVertex3d(dArr[0] - viewerPosX, dArr[1] - viewerPosY, dArr[2] - viewerPosZ);
            }
            GL11.glColor4d(1.0d, 1.0d, 1.0d, 1.0d);
            GL11.glEnd();
            GL11.glEnable(SGL.GL_DEPTH_TEST);
            GL11.glDisable(SGL.GL_LINE_SMOOTH);
            GL11.glDisable(SGL.GL_BLEND);
            GL11.glEnable(SGL.GL_TEXTURE_2D);
            GL11.glPopMatrix();
            Unit unit = Unit.INSTANCE;
        }
    }

    @NotNull
    public String getTag() {
        return String.valueOf(this.packets.size());
    }

    private final void blink() throws InterruptedException {
        try {
            this.disableLogger = true;
            while (!this.packets.isEmpty()) {
                INetworkManager networkManager = MinecraftInstance.f157mc.getNetHandler().getNetworkManager();
                Object objTake = this.packets.take();
                Intrinsics.checkExpressionValueIsNotNull(objTake, "packets.take()");
                networkManager.sendPacket((IPacket) objTake);
            }
            this.disableLogger = false;
        } catch (Exception e) {
            e.printStackTrace();
            this.disableLogger = false;
        }
        synchronized (this.positions) {
            this.positions.clear();
            Unit unit = Unit.INSTANCE;
        }
    }
}
