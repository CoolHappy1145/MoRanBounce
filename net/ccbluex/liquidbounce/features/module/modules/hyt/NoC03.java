package net.ccbluex.liquidbounce.features.module.modules.hyt;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.Velocity;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;

@ModuleInfo(name = "NoC03", description = "No C03", category = ModuleCategory.HYT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/hyt/NoC03.class */
public class NoC03 extends Module {
    public BoolValue tips = new BoolValue("Tips", false);
    public FloatValue bakRange = new FloatValue("BakRange", 3.3f, 3.0f, 6.0f);

    @EventTarget
    public void onPacket(PacketEvent packetEvent) {
        if (classProvider.isCPacketPlayer(packetEvent.getPacket())) {
            packetEvent.cancelEvent();
        }
    }

    public void onEnable() {
        KillAura killAura = (KillAura) LiquidBounce.moduleManager.getModule(KillAura.class);
        Velocity velocity = (Velocity) LiquidBounce.moduleManager.getModule(Velocity.class);
        killAura.getTargetModeValue().set("Multi");
        if (((String) killAura.getRotations().get()).equals("HytRotation")) {
            killAura.getRangeValue().set((Object) Float.valueOf(5.2f));
        } else {
            killAura.getRangeValue().set((Object) Float.valueOf(5.0f));
        }
        velocity.getModeValue().set("CanCelS12");
        velocity.setState(true);
        if (((Boolean) killAura.getNorangeairban().get()).booleanValue()) {
            killAura.getNorangeairban().set(false);
        }
        if (((Boolean) this.tips.get()).booleanValue()) {
            ClientUtils.displayChatMessage("\u00a7b[\u00a7bTipsOn]\u5df2\u4e3a\u60a8\u6740\u622e\u8ddd\u79bb\u66f4\u6539\u4e3a:" + killAura.getRangeValue().get() + ",\u76ee\u6807\u6a21\u5f0f\u4e3a:" + ((String) killAura.getTargetModeValue().get()) + ",\u53cd\u51fb\u9000\u6a21\u5f0f\u4e3a:CanCelS12");
        }
    }

    public void onDisable() {
        KillAura killAura = (KillAura) LiquidBounce.moduleManager.getModule(KillAura.class);
        Velocity velocity = (Velocity) LiquidBounce.moduleManager.getModule(Velocity.class);
        killAura.getTargetModeValue().set("Single");
        killAura.getRangeValue().set(this.bakRange.get());
        if (((Boolean) killAura.getNorangeairban().get()).booleanValue()) {
            killAura.getNorangeairban().set(true);
        }
        velocity.getModeValue().set("Grim-Packet");
        velocity.setState(false);
        if (((Boolean) this.tips.get()).booleanValue()) {
            ClientUtils.displayChatMessage("\u00a7b[\u00a7bTipsOff]\u5df2\u4e3a\u60a8\u6740\u622e\u8ddd\u79bb\u66f4\u6539\u4e3a:" + killAura.getRangeValue().get() + ",\u76ee\u6807\u6a21\u5f0f\u4e3a:" + ((String) killAura.getTargetModeValue().get()));
        }
    }

    @EventTarget
    public void onMove(MoveEvent moveEvent) {
        moveEvent.zero();
    }
}
