package net.ccbluex.liquidbounce.features.module.modules.world;

import java.text.DecimalFormat;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.elements.Notification;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.elements.NotifyType;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/LightningCheck;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "decimalFormat", "Ljava/text/DecimalFormat;", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "LightningCheck", description = "UHC.", category = ModuleCategory.WORLD)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/world/LightningCheck.class */
public final class LightningCheck extends Module {
    private final DecimalFormat decimalFormat = new DecimalFormat("0.0");

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IPacket packet = event.getPacket();
        if (MinecraftInstance.classProvider.isSPacketSpawnGlobalEntity(packet) && packet.asSPacketSpawnGlobalEntity().getType() != -1) {
            LiquidBounce.INSTANCE.getHud().addNotification(new Notification(getName(), "Lightning at X:" + this.decimalFormat.format(packet.asSPacketSpawnGlobalEntity().getX() / 32.0d) + " Y:" + this.decimalFormat.format(packet.asSPacketSpawnGlobalEntity().getY() / 32.0d) + " Z:" + this.decimalFormat.format(packet.asSPacketSpawnGlobalEntity().getZ() / 32.0d), NotifyType.WARNING, 5000, 0, 16, null));
        }
    }
}
