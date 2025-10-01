package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.minecraft.block.material.IMaterial;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0016J\b\u0010\u0007\u001a\u00020\u0006H\u0016J\u0012\u0010\b\u001a\u00020\u00062\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/IceSpeed;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "onDisable", "", "onEnable", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "IceSpeed", description = "Allows you to walk faster on ice.", category = ModuleCategory.MOVEMENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/IceSpeed.class */
public final class IceSpeed extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"NCP", "AAC", "Spartan"}, "NCP");

    public void onEnable() {
        if (StringsKt.equals((String) this.modeValue.get(), "NCP", true)) {
            MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE).setSlipperiness(0.39f);
            MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED).setSlipperiness(0.39f);
        }
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent updateEvent) {
        String str = (String) this.modeValue.get();
        if (StringsKt.equals(str, "NCP", true)) {
            MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE).setSlipperiness(0.39f);
            MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED).setSlipperiness(0.39f);
        } else {
            MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE).setSlipperiness(0.98f);
            MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED).setSlipperiness(0.98f);
        }
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null && thePlayer.getOnGround() && !thePlayer.isOnLadder() && !thePlayer.isSneaking() && thePlayer.getSprinting() && thePlayer.getMovementInput().getMoveForward() > 0.0d) {
            if (StringsKt.equals(str, "AAC", true)) {
                IMaterial material = BlockUtils.getMaterial(thePlayer.getPosition().down());
                if (Intrinsics.areEqual(material, MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE)) || Intrinsics.areEqual(material, MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED))) {
                    thePlayer.setMotionX(thePlayer.getMotionX() * 1.342d);
                    thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.342d);
                    MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE).setSlipperiness(0.6f);
                    MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED).setSlipperiness(0.6f);
                }
            }
            if (StringsKt.equals(str, "Spartan", true)) {
                IMaterial material2 = BlockUtils.getMaterial(thePlayer.getPosition().down());
                if (Intrinsics.areEqual(material2, MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE)) || Intrinsics.areEqual(material2, MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED))) {
                    if (!MinecraftInstance.classProvider.isBlockAir(BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() + 2.0d, thePlayer.getPosZ())))) {
                        thePlayer.setMotionX(thePlayer.getMotionX() * 1.342d);
                        thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.342d);
                    } else {
                        thePlayer.setMotionX(thePlayer.getMotionX() * 1.18d);
                        thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.18d);
                    }
                    MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE).setSlipperiness(0.6f);
                    MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED).setSlipperiness(0.6f);
                }
            }
        }
    }

    public void onDisable() {
        MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE).setSlipperiness(0.98f);
        MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED).setSlipperiness(0.98f);
    }
}
