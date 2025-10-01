package net.ccbluex.liquidbounce.features.module.modules.player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.MaterialType;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.Skid.MotionData;
import net.ccbluex.liquidbounce.utils.Skid.TimerUtil;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdR\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\nH\u0002J\b\u0010\u0018\u001a\u00020\u0016H\u0016J\b\u0010\u0019\u001a\u00020\u0016H\u0016J\u0010\u0010\u001a\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u0012\u0010\u001d\u001a\u00020\u00162\b\u0010\u001b\u001a\u0004\u0018\u00010\u001eH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/AntiFall;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "distanceValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "lastGround", "Lnet/ccbluex/liquidbounce/utils/Skid/MotionData;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "onGround", "", "overVoid", "packetList", "", "Lnet/ccbluex/liquidbounce/api/minecraft/network/IPacket;", "tag", "", "getTag", "()Ljava/lang/String;", "voidTimer", "Lnet/ccbluex/liquidbounce/utils/Skid/TimerUtil;", "clearPacket", "", "isOverVoid", "onDisable", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "AntiFall", description = "Prevents you from falling in void.", category = ModuleCategory.PLAYER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/AntiFall.class */
public final class AntiFall extends Module {
    private MotionData lastGround;
    private boolean overVoid;
    private boolean onGround;
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Hypixel", "AntiCheat"}, "Hypixel");
    private final FloatValue distanceValue = new FloatValue("Distance", 7.0f, 0.1f, 15.0f);
    private final List packetList = new ArrayList();
    private final TimerUtil voidTimer = new TimerUtil();

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }

    public void onEnable() {
        this.lastGround = (MotionData) null;
        this.overVoid = false;
        this.packetList.clear();
        this.voidTimer.reset();
    }

    public void onDisable() {
        this.packetList.clear();
        this.voidTimer.reset();
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent updateEvent) {
        if (Intrinsics.areEqual((String) this.modeValue.get(), "AntiCheat")) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer.getFallDistance() >= 10.0f) {
                clearPacket();
            }
            if (this.onGround) {
                this.lastGround = (MotionData) null;
                if (MovementUtils.INSTANCE.isOnGround(1.0E-4d)) {
                    this.onGround = false;
                    return;
                }
                return;
            }
            if (!isOverVoid()) {
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                double posX = thePlayer2.getPosX();
                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer3 == null) {
                    Intrinsics.throwNpe();
                }
                double posY = thePlayer3.getPosY();
                IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer4 == null) {
                    Intrinsics.throwNpe();
                }
                double posZ = thePlayer4.getPosZ();
                IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer5 == null) {
                    Intrinsics.throwNpe();
                }
                this.lastGround = new MotionData(posX, posY, posZ, -8.412878165707724E-4d, thePlayer5.getMotionY(), -0.11784442128235458d);
                clearPacket();
                this.overVoid = false;
                return;
            }
            IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer6 == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer6.getFallDistance() >= 6.0f && this.overVoid && this.voidTimer.hasReached(500.0d)) {
                this.packetList.clear();
                if (this.lastGround != null) {
                    MotionData motionData = this.lastGround;
                    if (motionData == null) {
                        Intrinsics.throwNpe();
                    }
                    MovementUtils.setPosition(motionData);
                }
                this.overVoid = false;
                this.onGround = true;
                this.voidTimer.reset();
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();
        if (Intrinsics.areEqual((String) this.modeValue.get(), "AntiCheat") && MinecraftInstance.f157mc.getThePlayer() != null && isOverVoid() && MinecraftInstance.classProvider.isCPacketPlayer(packet)) {
            this.packetList.add(packet);
            event.cancelEvent();
            this.overVoid = true;
        }
        if (Intrinsics.areEqual((String) this.modeValue.get(), "Hypixel") && MinecraftInstance.classProvider.isCPacketPlayer(packet)) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer.getFallDistance() <= ((Number) this.distanceValue.get()).floatValue() || MovementUtils.isBlockUnder()) {
                return;
            }
            ICPacketPlayer iCPacketPlayerAsCPacketPlayer = packet.asCPacketPlayer();
            iCPacketPlayerAsCPacketPlayer.setY(iCPacketPlayerAsCPacketPlayer.getY() + ((Number) this.distanceValue.get()).floatValue() + 4.0f);
        }
    }

    private final void clearPacket() {
        if (!this.packetList.isEmpty()) {
            Iterator it = this.packetList.iterator();
            while (it.hasNext()) {
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue((IPacket) it.next());
            }
        }
        this.packetList.clear();
    }

    private final boolean isOverVoid() {
        boolean z = true;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        double posX = thePlayer.getPosX();
        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        double posY = thePlayer2.getPosY() - 1.0d;
        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer3 == null) {
            Intrinsics.throwNpe();
        }
        WBlockPos wBlockPos = new WBlockPos(posX, posY, thePlayer3.getPosZ());
        IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer4 == null) {
            Intrinsics.throwNpe();
        }
        double posY2 = thePlayer4.getPosY() + 1.0d;
        while (true) {
            double d = posY2;
            if (d <= 0.0d) {
                break;
            }
            IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
            if (theWorld == null) {
                Intrinsics.throwNpe();
            }
            if (theWorld.getBlockState(wBlockPos).getBlock() != MinecraftInstance.classProvider.getMaterialEnum(MaterialType.AIR)) {
                z = false;
                break;
            }
            wBlockPos = wBlockPos.add(0, -1, 0);
            posY2 = d - 0.5d;
        }
        double d2 = 0.0d;
        while (true) {
            double d3 = d2;
            if (d3 < 10.0d) {
                if (MovementUtils.INSTANCE.isOnGround(d3) && z) {
                    z = false;
                    break;
                }
                d2 = d3 + 0.1d;
            } else {
                break;
            }
        }
        return z;
    }
}
