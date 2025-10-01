package net.ccbluex.liquidbounce.features.module.modules.combat;

import javax.vecmath.Vector3d;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.PathUtils;
import net.ccbluex.liquidbounce.utils.RaycastUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;

@ModuleInfo(name = "TeleportHit", description = "Allows to hit entities from far away.", category = ModuleCategory.COMBAT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/TeleportHit.class */
public class TeleportHit extends Module {
    private IEntityLivingBase targetEntity;
    private boolean shouldHit;

    @EventTarget
    public void onMotion(MotionEvent motionEvent) {
        if (motionEvent.getEventState() != EventState.PRE) {
            return;
        }
        IClassProvider iClassProvider = classProvider;
        iClassProvider.getClass();
        IEntity iEntityRaycastEntity = RaycastUtils.raycastEntity(100.0d, (v1) -> {
            return r1.isEntityLivingBase(v1);
        });
        IEntityPlayerSP thePlayer = f157mc.getThePlayer();
        if (thePlayer == null) {
            return;
        }
        if (f157mc.getGameSettings().getKeyBindAttack().isKeyDown() && EntityUtils.isSelected(iEntityRaycastEntity, true) && iEntityRaycastEntity.getDistanceSqToEntity(thePlayer) >= 1.0d) {
            this.targetEntity = iEntityRaycastEntity.asEntityLivingBase();
        }
        if (this.targetEntity != null) {
            if (!this.shouldHit) {
                this.shouldHit = true;
                return;
            }
            if (thePlayer.getFallDistance() <= 0.0f) {
                if (thePlayer.getOnGround()) {
                    thePlayer.jump();
                    return;
                }
                return;
            }
            WVec3 vectorForRotation = RotationUtils.getVectorForRotation(new Rotation(thePlayer.getRotationYaw(), 0.0f));
            PathUtils.findPath(thePlayer.getPosX() + (vectorForRotation.getXCoord() * (thePlayer.getDistanceToEntity(this.targetEntity) - 1.0f)), this.targetEntity.getPosition().getY() + 0.25d + 1.0d, thePlayer.getPosZ() + (vectorForRotation.getZCoord() * (thePlayer.getDistanceToEntity(this.targetEntity) - 1.0f)), 4.0d).forEach(TeleportHit::lambda$onMotion$0);
            thePlayer.swingItem();
            f157mc.getNetHandler().addToSendQueue(classProvider.createCPacketUseEntity(this.targetEntity, ICPacketUseEntity.WAction.ATTACK));
            thePlayer.onCriticalHit(this.targetEntity);
            this.shouldHit = false;
            this.targetEntity = null;
            return;
        }
        this.shouldHit = false;
    }

    private static void lambda$onMotion$0(Vector3d vector3d) {
        f157mc.getNetHandler().addToSendQueue(classProvider.createCPacketPlayerPosition(vector3d.getX(), vector3d.getY(), vector3d.getZ(), false));
    }
}
