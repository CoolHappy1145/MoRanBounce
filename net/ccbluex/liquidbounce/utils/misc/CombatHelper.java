package net.ccbluex.liquidbounce.utils.misc;

import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Listenable;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/misc/CombatHelper.class */
public class CombatHelper implements Listenable {
    private static int kills;
    private static IEntityLivingBase attackEntity = null;
    private static boolean canAdd;
    public static int totalPlayed;
    public static int win;

    @EventTarget
    public void onUpdate(UpdateEvent updateEvent) {
        if (attackEntity != null && attackEntity.isDead() && canAdd && attackEntity.getHealth() == 0.0f) {
            kills++;
            attackEntity = null;
            canAdd = false;
        }
    }

    @EventTarget
    public void onPacket(PacketEvent packetEvent) {
        IPacket packet = packetEvent.getPacket();
        if (MinecraftInstance.classProvider.isSPacketTitle(packet)) {
            String strFunc_150254_d = packet.asSPacketTitle().getMessage().func_150254_d();
            if (strFunc_150254_d.contains("Winner")) {
                win++;
            }
            if (strFunc_150254_d.contains("BedWar")) {
                totalPlayed++;
            }
            if (strFunc_150254_d.contains("SkyWar")) {
                totalPlayed++;
            }
        }
    }

    @EventTarget
    public void onWorld(WorldEvent worldEvent) {
        attackEntity = null;
        canAdd = false;
    }

    @EventTarget
    public void onAttack(AttackEvent attackEvent) {
        if (MinecraftInstance.classProvider.isEntityLivingBase(attackEvent.getTargetEntity())) {
            attackEntity = attackEvent.getTargetEntity().asEntityLivingBase();
            canAdd = true;
        }
    }

    public static int getKills() {
        return kills;
    }
}
