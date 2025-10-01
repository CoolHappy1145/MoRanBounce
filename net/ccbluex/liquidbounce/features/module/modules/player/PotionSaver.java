package net.ccbluex.liquidbounce.features.module.modules.player;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.MinecraftVersion;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0007\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/player/PotionSaver;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "onPacket", "", "e", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "PotionSaver", description = "Freezes all potion effects while you are standing still.", category = ModuleCategory.PLAYER, supportedVersions = {MinecraftVersion.MC_1_8})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/player/PotionSaver.class */
public final class PotionSaver extends Module {
    @EventTarget
    public final void onPacket(@NotNull PacketEvent e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        IPacket packet = e.getPacket();
        if (!MinecraftInstance.classProvider.isCPacketPlayer(packet) || MinecraftInstance.classProvider.isCPacketPlayerPosition(packet) || MinecraftInstance.classProvider.isCPacketPlayerPosLook(packet) || MinecraftInstance.classProvider.isCPacketPlayerPosLook(packet) || MinecraftInstance.f157mc.getThePlayer() == null) {
            return;
        }
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (!thePlayer.isUsingItem()) {
            e.cancelEvent();
        }
    }
}
