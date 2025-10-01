package net.ccbluex.liquidbounce.features.module.modules.combat;

import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.enums.ItemType;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;

@ModuleInfo(name = "Ignite", description = "Automatically sets targets around you on fire.", category = ModuleCategory.COMBAT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/Ignite.class */
public class Ignite extends Module {
    private final BoolValue lighterValue = new BoolValue("Lighter", true);
    private final BoolValue lavaBucketValue = new BoolValue("Lava", true);
    private final MSTimer msTimer = new MSTimer();

    @EventTarget
    public void onUpdate(UpdateEvent updateEvent) {
        if (this.msTimer.hasTimePassed(500L)) {
            IEntityPlayerSP thePlayer = f157mc.getThePlayer();
            IWorldClient theWorld = f157mc.getTheWorld();
            if (thePlayer == null || theWorld == null) {
                return;
            }
            int iFindItem = ((Boolean) this.lighterValue.get()).booleanValue() ? InventoryUtils.findItem(36, 45, classProvider.getItemEnum(ItemType.FLINT_AND_STEEL)) : -1;
            int iFindItem2 = ((Boolean) this.lavaBucketValue.get()).booleanValue() ? InventoryUtils.findItem(26, 45, classProvider.getItemEnum(ItemType.LAVA_BUCKET)) : -1;
            if (iFindItem == -1 && iFindItem2 == -1) {
                return;
            }
            int i = iFindItem != -1 ? iFindItem : iFindItem2;
            for (IEntity iEntity : theWorld.getLoadedEntityList()) {
                if (EntityUtils.isSelected(iEntity, true) && !iEntity.isBurning()) {
                    WBlockPos position = iEntity.getPosition();
                    if (f157mc.getThePlayer().getDistanceSq(position) < 22.3d && BlockUtils.isReplaceable(position) && classProvider.isBlockAir(BlockUtils.getBlock(position))) {
                        RotationUtils.keepCurrentRotation = true;
                        f157mc.getNetHandler().addToSendQueue(classProvider.createCPacketHeldItemChange(i - 36));
                        IItemStack stackInSlot = f157mc.getThePlayer().getInventory().getStackInSlot(i);
                        if (!classProvider.isItemBucket(stackInSlot.getItem())) {
                            EnumFacingType[] enumFacingTypeArrValues = EnumFacingType.values();
                            int length = enumFacingTypeArrValues.length;
                            int i2 = 0;
                            while (true) {
                                if (i2 >= length) {
                                    break;
                                }
                                IEnumFacing enumFacing = classProvider.getEnumFacing(enumFacingTypeArrValues[i2]);
                                WBlockPos wBlockPosOffset = position.offset(enumFacing);
                                if (BlockUtils.canBeClicked(wBlockPosOffset)) {
                                    double x = (wBlockPosOffset.getX() + 0.5d) - thePlayer.getPosX();
                                    double y = (wBlockPosOffset.getY() + 0.5d) - (thePlayer.getEntityBoundingBox().getMinY() + thePlayer.getEyeHeight());
                                    double z = (wBlockPosOffset.getZ() + 0.5d) - thePlayer.getPosZ();
                                    double dSqrt = Math.sqrt((x * x) + (z * z));
                                    float fAtan2 = ((float) ((Math.atan2(z, x) * 180.0d) / 3.141592653589793d)) - 90.0f;
                                    float f = (float) (-((Math.atan2(y, dSqrt) * 180.0d) / 3.141592653589793d));
                                    IINetHandlerPlayClient netHandler = f157mc.getNetHandler();
                                    IClassProvider iClassProvider = classProvider;
                                    float rotationYaw = thePlayer.getRotationYaw();
                                    float rotationYaw2 = (fAtan2 - thePlayer.getRotationYaw()) % 360.0f;
                                    if (rotationYaw2 >= 180.0f) {
                                        rotationYaw2 -= 360.0f;
                                    }
                                    if (rotationYaw2 < -180.0f) {
                                        rotationYaw2 += 360.0f;
                                    }
                                    float f2 = rotationYaw + rotationYaw2;
                                    float rotationPitch = thePlayer.getRotationPitch();
                                    float rotationPitch2 = (f - thePlayer.getRotationPitch()) % 360.0f;
                                    if (rotationPitch2 >= 180.0f) {
                                        rotationPitch2 -= 360.0f;
                                    }
                                    if (rotationPitch2 < -180.0f) {
                                        rotationPitch2 += 360.0f;
                                    }
                                    netHandler.addToSendQueue(iClassProvider.createCPacketPlayerLook(f2, rotationPitch + rotationPitch2, thePlayer.getOnGround()));
                                    if (f157mc.getPlayerController().onPlayerRightClick(thePlayer, theWorld, stackInSlot, wBlockPosOffset, enumFacing.getOpposite(), new WVec3(enumFacing.getDirectionVec()))) {
                                        thePlayer.swingItem();
                                        break;
                                    }
                                }
                                i2++;
                            }
                        } else {
                            double x2 = (position.getX() + 0.5d) - f157mc.getThePlayer().getPosX();
                            double y2 = (position.getY() + 0.5d) - (thePlayer.getEntityBoundingBox().getMinY() + thePlayer.getEyeHeight());
                            double z2 = (position.getZ() + 0.5d) - thePlayer.getPosZ();
                            double dSqrt2 = Math.sqrt((x2 * x2) + (z2 * z2));
                            float fAtan22 = ((float) ((Math.atan2(z2, x2) * 180.0d) / 3.141592653589793d)) - 90.0f;
                            float f3 = (float) (-((Math.atan2(y2, dSqrt2) * 180.0d) / 3.141592653589793d));
                            IINetHandlerPlayClient netHandler2 = f157mc.getNetHandler();
                            IClassProvider iClassProvider2 = classProvider;
                            float rotationYaw3 = thePlayer.getRotationYaw();
                            float rotationYaw4 = (fAtan22 - thePlayer.getRotationYaw()) % 360.0f;
                            if (rotationYaw4 >= 180.0f) {
                                rotationYaw4 -= 360.0f;
                            }
                            if (rotationYaw4 < -180.0f) {
                                rotationYaw4 += 360.0f;
                            }
                            float f4 = rotationYaw3 + rotationYaw4;
                            float rotationPitch3 = thePlayer.getRotationPitch();
                            float rotationPitch4 = (f3 - thePlayer.getRotationPitch()) % 360.0f;
                            if (rotationPitch4 >= 180.0f) {
                                rotationPitch4 -= 360.0f;
                            }
                            if (rotationPitch4 < -180.0f) {
                                rotationPitch4 += 360.0f;
                            }
                            netHandler2.addToSendQueue(iClassProvider2.createCPacketPlayerLook(f4, rotationPitch3 + rotationPitch4, thePlayer.getOnGround()));
                            f157mc.getPlayerController().sendUseItem(thePlayer, theWorld, stackInSlot);
                        }
                        f157mc.getNetHandler().addToSendQueue(classProvider.createCPacketHeldItemChange(thePlayer.getInventory().getCurrentItem()));
                        RotationUtils.keepCurrentRotation = false;
                        f157mc.getNetHandler().addToSendQueue(classProvider.createCPacketPlayerLook(thePlayer.getRotationYaw(), thePlayer.getRotationPitch(), thePlayer.getOnGround()));
                        this.msTimer.reset();
                        return;
                    }
                }
            }
        }
    }
}
