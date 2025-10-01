package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.util.Random;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: AutoLeave.kt */
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoLeave;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "healthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "AutoLeave", description = "Automatically makes you leave the server whenever your health is low.", category = ModuleCategory.COMBAT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/AutoLeave.class */
public final class AutoLeave extends Module {
    private final FloatValue healthValue = new FloatValue("Health", 8.0f, 0.0f, 20.0f);
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Quit", "InvalidPacket", "SelfHurt", "IllegalChat"}, "Quit");

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null && thePlayer.getHealth() <= ((Number) this.healthValue.get()).floatValue() && !thePlayer.getCapabilities().isCreativeMode() && !MinecraftInstance.f157mc.isIntegratedServerRunning()) {
            String str = (String) this.modeValue.get();
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = str.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case -1647903794:
                    if (lowerCase.equals("illegalchat")) {
                        thePlayer.sendChatMessage(String.valueOf(new Random().nextInt()) + "\u00a7\u00a7\u00a7" + new Random().nextInt());
                        break;
                    }
                    break;
                case -367089345:
                    if (lowerCase.equals("invalidpacket")) {
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(DoubleCompanionObject.INSTANCE.getNaN(), DoubleCompanionObject.INSTANCE.getNEGATIVE_INFINITY(), DoubleCompanionObject.INSTANCE.getPOSITIVE_INFINITY(), !thePlayer.getOnGround()));
                        break;
                    }
                    break;
                case 3482191:
                    if (lowerCase.equals("quit")) {
                        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                        if (theWorld == null) {
                            Intrinsics.throwNpe();
                        }
                        theWorld.sendQuittingDisconnectingPacket();
                        break;
                    }
                    break;
                case 1192645979:
                    if (lowerCase.equals("selfhurt")) {
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketUseEntity(thePlayer, ICPacketUseEntity.WAction.ATTACK));
                        break;
                    }
                    break;
            }
            setState(false);
        }
    }
}
