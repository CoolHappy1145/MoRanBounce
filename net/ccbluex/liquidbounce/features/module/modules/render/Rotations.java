package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.BowAimbot;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Derp;
import net.ccbluex.liquidbounce.features.module.modules.world.ChestAura;
import net.ccbluex.liquidbounce.features.module.modules.world.CivBreak;
import net.ccbluex.liquidbounce.features.module.modules.world.Fucker;
import net.ccbluex.liquidbounce.features.module.modules.world.Nuker;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.features.module.modules.world.Tower;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.util.Constants;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\b\u001a\u00020\t2\n\u0010\n\u001a\u0006\u0012\u0002\b\u00030\u000bH\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0011H\u0007J\b\u0010\u0012\u001a\u00020\tH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0004\n\u0002\u0010\u0007\u00a8\u0006\u0013"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Rotations;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "bodyValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "playerYaw", "", "Ljava/lang/Float;", "getState", "", "module", Constants.CLASS_DESC, "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "shouldRotate", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Rotations", description = "Allows you to see server-sided head and body rotations.", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/Rotations.class */
public final class Rotations extends Module {
    private final BoolValue bodyValue = new BoolValue("Body", true);
    private Float playerYaw;

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (RotationUtils.serverRotation != null && !((Boolean) this.bodyValue.get()).booleanValue()) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer != null) {
                thePlayer.setRotationYawHead(RotationUtils.serverRotation.getYaw());
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (!((Boolean) this.bodyValue.get()).booleanValue() || !shouldRotate() || thePlayer == null) {
            return;
        }
        IPacket packet = event.getPacket();
        if (MinecraftInstance.classProvider.isCPacketPlayerPosLook(packet) || MinecraftInstance.classProvider.isCPacketPlayerLook(packet)) {
            ICPacketPlayer iCPacketPlayerAsCPacketPlayer = packet.asCPacketPlayer();
            this.playerYaw = Float.valueOf(iCPacketPlayerAsCPacketPlayer.getYaw());
            thePlayer.setRenderYawOffset(iCPacketPlayerAsCPacketPlayer.getYaw());
            thePlayer.setRotationYawHead(iCPacketPlayerAsCPacketPlayer.getYaw());
            return;
        }
        if (this.playerYaw != null) {
            Float f = this.playerYaw;
            if (f == null) {
                Intrinsics.throwNpe();
            }
            thePlayer.setRenderYawOffset(f.floatValue());
        }
        thePlayer.setRotationYawHead(thePlayer.getRenderYawOffset());
    }

    private final boolean getState(Class cls) {
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(cls);
        if (module == null) {
            Intrinsics.throwNpe();
        }
        return module.getState();
    }

    private final boolean shouldRotate() {
        Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura.class);
        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
        }
        return getState(Scaffold.class) || getState(Tower.class) || (getState(KillAura.class) && ((KillAura) module).getTarget() != null) || getState(Derp.class) || getState(BowAimbot.class) || getState(Fucker.class) || getState(CivBreak.class) || getState(Nuker.class) || getState(ChestAura.class);
    }
}
