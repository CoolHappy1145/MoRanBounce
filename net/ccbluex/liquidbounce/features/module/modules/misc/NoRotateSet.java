package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.play.server.ISPacketPosLook;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/NoRotateSet;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "confirmValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "illegalRotationValue", "noZeroValue", "onPacket", "", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "NoRotateSet", description = "Prevents the server from rotating your head.", category = ModuleCategory.MISC)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/misc/NoRotateSet.class */
public final class NoRotateSet extends Module {
    private final BoolValue confirmValue = new BoolValue("Confirm", true);
    private final BoolValue illegalRotationValue = new BoolValue("ConfirmIllegalRotation", false);
    private final BoolValue noZeroValue = new BoolValue("NoZero", false);

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null && MinecraftInstance.classProvider.isSPacketPlayerPosLook(event.getPacket())) {
            ISPacketPosLook iSPacketPosLookAsSPacketPosLook = event.getPacket().asSPacketPosLook();
            if (((Boolean) this.noZeroValue.get()).booleanValue() && iSPacketPosLookAsSPacketPosLook.getYaw() == 0.0f && iSPacketPosLookAsSPacketPosLook.getPitch() == 0.0f) {
                return;
            }
            if ((((Boolean) this.illegalRotationValue.get()).booleanValue() || (iSPacketPosLookAsSPacketPosLook.getPitch() <= 90.0f && iSPacketPosLookAsSPacketPosLook.getPitch() >= -90.0f && RotationUtils.serverRotation != null && iSPacketPosLookAsSPacketPosLook.getYaw() != RotationUtils.serverRotation.getYaw() && iSPacketPosLookAsSPacketPosLook.getPitch() != RotationUtils.serverRotation.getPitch())) && ((Boolean) this.confirmValue.get()).booleanValue()) {
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerLook(iSPacketPosLookAsSPacketPosLook.getYaw(), iSPacketPosLookAsSPacketPosLook.getPitch(), thePlayer.getOnGround()));
            }
            iSPacketPosLookAsSPacketPosLook.setYaw(thePlayer.getRotationYaw());
            iSPacketPosLookAsSPacketPosLook.setPitch(thePlayer.getRotationPitch());
        }
    }
}
