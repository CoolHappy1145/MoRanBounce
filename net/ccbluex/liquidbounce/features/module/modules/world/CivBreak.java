package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.ClickBlockEvent;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0007J\u0010\u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0015H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0016"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/CivBreak;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "airResetValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "enumFacing", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", AsmConstants.CODERANGE, "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rangeResetValue", "rotationsValue", "visualSwingValue", "onBlockClick", "", "event", "Lnet/ccbluex/liquidbounce/event/ClickBlockEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "CivBreak", description = "Allows you to break blocks instantly.", category = ModuleCategory.WORLD)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/world/CivBreak.class */
public final class CivBreak extends Module {
    private WBlockPos blockPos;
    private IEnumFacing enumFacing;
    private final FloatValue range = new FloatValue("Range", 5.0f, 1.0f, 6.0f);
    private final BoolValue rotationsValue = new BoolValue("Rotations", true);
    private final BoolValue visualSwingValue = new BoolValue("VisualSwing", true);
    private final BoolValue airResetValue = new BoolValue("Air-Reset", true);
    private final BoolValue rangeResetValue = new BoolValue("Range-Reset", true);

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/world/CivBreak$WhenMappings.class */
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0 = new int[EventState.values().length];

        static {
            $EnumSwitchMapping$0[EventState.PRE.ordinal()] = 1;
            $EnumSwitchMapping$0[EventState.POST.ordinal()] = 2;
        }
    }

    @EventTarget
    public final void onBlockClick(@NotNull ClickBlockEvent event) {
        IBlock block;
        Intrinsics.checkParameterIsNotNull(event, "event");
        IClassProvider iClassProvider = MinecraftInstance.classProvider;
        WBlockPos clickedBlock = event.getClickedBlock();
        if (clickedBlock != null) {
            iClassProvider = iClassProvider;
            block = BlockUtils.getBlock(clickedBlock);
        } else {
            block = null;
        }
        if (iClassProvider.isBlockBedrock(block)) {
            return;
        }
        WBlockPos clickedBlock2 = event.getClickedBlock();
        if (clickedBlock2 != null) {
            this.blockPos = clickedBlock2;
            IEnumFacing wEnumFacing = event.getWEnumFacing();
            if (wEnumFacing != null) {
                this.enumFacing = wEnumFacing;
                IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
                ICPacketPlayerDigging.WAction wAction = ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK;
                WBlockPos wBlockPos = this.blockPos;
                if (wBlockPos == null) {
                    Intrinsics.throwNpe();
                }
                IEnumFacing iEnumFacing = this.enumFacing;
                if (iEnumFacing == null) {
                    Intrinsics.throwNpe();
                }
                netHandler.addToSendQueue(iClassProvider2.createCPacketPlayerDigging(wAction, wBlockPos, iEnumFacing));
                IINetHandlerPlayClient netHandler2 = MinecraftInstance.f157mc.getNetHandler();
                IClassProvider iClassProvider3 = MinecraftInstance.classProvider;
                ICPacketPlayerDigging.WAction wAction2 = ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK;
                WBlockPos wBlockPos2 = this.blockPos;
                if (wBlockPos2 == null) {
                    Intrinsics.throwNpe();
                }
                IEnumFacing iEnumFacing2 = this.enumFacing;
                if (iEnumFacing2 == null) {
                    Intrinsics.throwNpe();
                }
                netHandler2.addToSendQueue(iClassProvider3.createCPacketPlayerDigging(wAction2, wBlockPos2, iEnumFacing2));
            }
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        WBlockPos wBlockPos = this.blockPos;
        if (wBlockPos != null) {
            if ((((Boolean) this.airResetValue.get()).booleanValue() && MinecraftInstance.classProvider.isBlockAir(BlockUtils.getBlock(wBlockPos))) || (((Boolean) this.rangeResetValue.get()).booleanValue() && BlockUtils.getCenterDistance(wBlockPos) > ((Number) this.range.get()).doubleValue())) {
                this.blockPos = (WBlockPos) null;
            }
            if (MinecraftInstance.classProvider.isBlockAir(BlockUtils.getBlock(wBlockPos)) || BlockUtils.getCenterDistance(wBlockPos) > ((Number) this.range.get()).doubleValue()) {
                return;
            }
            switch (WhenMappings.$EnumSwitchMapping$0[event.getEventState().ordinal()]) {
                case 1:
                    if (((Boolean) this.rotationsValue.get()).booleanValue()) {
                        VecRotation vecRotationFaceBlock = RotationUtils.faceBlock(wBlockPos);
                        if (vecRotationFaceBlock != null) {
                            RotationUtils.setTargetRotation(vecRotationFaceBlock.getRotation());
                            break;
                        }
                    }
                    break;
                case 2:
                    if (((Boolean) this.visualSwingValue.get()).booleanValue()) {
                        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer.swingItem();
                    } else {
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketAnimation());
                    }
                    IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                    IClassProvider iClassProvider = MinecraftInstance.classProvider;
                    ICPacketPlayerDigging.WAction wAction = ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK;
                    WBlockPos wBlockPos2 = this.blockPos;
                    if (wBlockPos2 == null) {
                        Intrinsics.throwNpe();
                    }
                    IEnumFacing iEnumFacing = this.enumFacing;
                    if (iEnumFacing == null) {
                        Intrinsics.throwNpe();
                    }
                    netHandler.addToSendQueue(iClassProvider.createCPacketPlayerDigging(wAction, wBlockPos2, iEnumFacing));
                    IINetHandlerPlayClient netHandler2 = MinecraftInstance.f157mc.getNetHandler();
                    IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
                    ICPacketPlayerDigging.WAction wAction2 = ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK;
                    WBlockPos wBlockPos3 = this.blockPos;
                    if (wBlockPos3 == null) {
                        Intrinsics.throwNpe();
                    }
                    IEnumFacing iEnumFacing2 = this.enumFacing;
                    if (iEnumFacing2 == null) {
                        Intrinsics.throwNpe();
                    }
                    netHandler2.addToSendQueue(iClassProvider2.createCPacketPlayerDigging(wAction2, wBlockPos3, iEnumFacing2));
                    IPlayerControllerMP playerController = MinecraftInstance.f157mc.getPlayerController();
                    WBlockPos wBlockPos4 = this.blockPos;
                    if (wBlockPos4 == null) {
                        Intrinsics.throwNpe();
                    }
                    IEnumFacing iEnumFacing3 = this.enumFacing;
                    if (iEnumFacing3 == null) {
                        Intrinsics.throwNpe();
                    }
                    playerController.clickBlock(wBlockPos4, iEnumFacing3);
                    break;
            }
        }
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        WBlockPos wBlockPos = this.blockPos;
        if (wBlockPos != null) {
            RenderUtils.drawBlockBox(wBlockPos, Color.RED, true);
        }
    }
}
