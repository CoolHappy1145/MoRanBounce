package net.ccbluex.liquidbounce.features.module.modules.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.extensions.BlockExtensionKt;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdH\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u00c7\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016J\u0010\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0018\u001a\u00020\u0019H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\tR\u0010\u0010\n\u001a\u0004\u0018\u00010\u0007X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0014\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001a"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/ChestAura;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "chestValue", "Lnet/ccbluex/liquidbounce/value/BlockValue;", "clickedBlocks", "", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getClickedBlocks", "()Ljava/util/List;", "currentBlock", "delayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "rangeValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rotationsValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "throughWallsValue", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "visualSwing", "onDisable", "", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "ChestAura", description = "Automatically opens chests around you.", category = ModuleCategory.WORLD)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/world/ChestAura.class */
public final class ChestAura extends Module {
    private static WBlockPos currentBlock;
    public static final ChestAura INSTANCE = new ChestAura();
    private static final FloatValue rangeValue = new FloatValue("Range", 5.0f, 1.0f, 6.0f);
    private static final IntegerValue delayValue = new IntegerValue("Delay", 100, 50, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD);
    private static final BoolValue throughWallsValue = new BoolValue("ThroughWalls", true);
    private static final BoolValue visualSwing = new BoolValue("VisualSwing", true);
    private static final BlockValue chestValue = new BlockValue("Chest", MinecraftInstance.functions.getIdFromBlock(MinecraftInstance.classProvider.getBlockEnum(BlockType.CHEST)));
    private static final BoolValue rotationsValue = new BoolValue("Rotations", true);
    private static final MSTimer timer = new MSTimer();

    @NotNull
    private static final List clickedBlocks = new ArrayList();

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/world/ChestAura$WhenMappings.class */
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0 = new int[EventState.values().length];

        static {
            $EnumSwitchMapping$0[EventState.PRE.ordinal()] = 1;
            $EnumSwitchMapping$0[EventState.POST.ordinal()] = 2;
        }
    }

    private ChestAura() {
    }

    @NotNull
    public final List getClickedBlocks() {
        return clickedBlocks;
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Object obj;
        boolean z;
        Intrinsics.checkParameterIsNotNull(event, "event");
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(Blink.class);
        if (module == null) {
            Intrinsics.throwNpe();
        }
        if (module.getState()) {
        }
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        switch (WhenMappings.$EnumSwitchMapping$0[event.getEventState().ordinal()]) {
            case 1:
                if (MinecraftInstance.classProvider.isGuiContainer(MinecraftInstance.f157mc.getCurrentScreen())) {
                    timer.reset();
                }
                float fFloatValue = ((Number) rangeValue.get()).floatValue() + 1.0f;
                WVec3 wVec3 = new WVec3(thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY() + thePlayer.getEyeHeight(), thePlayer.getPosZ());
                Map mapSearchBlocks = BlockUtils.searchBlocks((int) fFloatValue);
                LinkedHashMap linkedHashMap = new LinkedHashMap();
                for (Map.Entry entry : mapSearchBlocks.entrySet()) {
                    if (MinecraftInstance.functions.getIdFromBlock((IBlock) entry.getValue()) == ((Number) chestValue.get()).intValue() && !clickedBlocks.contains(entry.getKey()) && BlockUtils.getCenterDistance((WBlockPos) entry.getKey()) < ((Number) rangeValue.get()).doubleValue()) {
                        linkedHashMap.put(entry.getKey(), entry.getValue());
                    }
                }
                LinkedHashMap linkedHashMap2 = new LinkedHashMap();
                for (Map.Entry entry2 : linkedHashMap.entrySet()) {
                    if (((Boolean) throughWallsValue.get()).booleanValue()) {
                        z = true;
                    } else {
                        WBlockPos wBlockPos = (WBlockPos) entry2.getKey();
                        IMovingObjectPosition iMovingObjectPositionRayTraceBlocks = theWorld.rayTraceBlocks(wVec3, BlockExtensionKt.getVec(wBlockPos), false, true, false);
                        z = iMovingObjectPositionRayTraceBlocks != null && Intrinsics.areEqual(iMovingObjectPositionRayTraceBlocks.getBlockPos(), wBlockPos);
                    }
                    if (z) {
                        linkedHashMap2.put(entry2.getKey(), entry2.getValue());
                    }
                }
                Iterator it = linkedHashMap2.entrySet().iterator();
                if (it.hasNext()) {
                    Object next = it.next();
                    if (it.hasNext()) {
                        double centerDistance = BlockUtils.getCenterDistance((WBlockPos) ((Map.Entry) next).getKey());
                        do {
                            Object next2 = it.next();
                            double centerDistance2 = BlockUtils.getCenterDistance((WBlockPos) ((Map.Entry) next2).getKey());
                            if (Double.compare(centerDistance, centerDistance2) > 0) {
                                next = next2;
                                centerDistance = centerDistance2;
                            }
                        } while (it.hasNext());
                        obj = next;
                    } else {
                        obj = next;
                    }
                } else {
                    obj = null;
                }
                Map.Entry entry3 = (Map.Entry) obj;
                currentBlock = entry3 != null ? (WBlockPos) entry3.getKey() : null;
                if (((Boolean) rotationsValue.get()).booleanValue()) {
                    WBlockPos wBlockPos2 = currentBlock;
                    if (wBlockPos2 != null) {
                        VecRotation vecRotationFaceBlock = RotationUtils.faceBlock(wBlockPos2);
                        if (vecRotationFaceBlock != null) {
                            RotationUtils.setTargetRotation(vecRotationFaceBlock.getRotation());
                            break;
                        }
                    }
                }
                break;
            case 2:
                if (currentBlock != null && timer.hasTimePassed(((Number) delayValue.get()).intValue())) {
                    IPlayerControllerMP playerController = MinecraftInstance.f157mc.getPlayerController();
                    IWorldClient theWorld2 = MinecraftInstance.f157mc.getTheWorld();
                    if (theWorld2 == null) {
                        Intrinsics.throwNpe();
                    }
                    IItemStack heldItem = thePlayer.getHeldItem();
                    if (heldItem == null) {
                        Intrinsics.throwNpe();
                    }
                    WBlockPos wBlockPos3 = currentBlock;
                    if (wBlockPos3 == null) {
                        Intrinsics.throwNpe();
                    }
                    IEnumFacing enumFacing = MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN);
                    WBlockPos wBlockPos4 = currentBlock;
                    if (wBlockPos4 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (playerController.onPlayerRightClick(thePlayer, theWorld2, heldItem, wBlockPos3, enumFacing, BlockExtensionKt.getVec(wBlockPos4))) {
                        if (((Boolean) visualSwing.get()).booleanValue()) {
                            thePlayer.swingItem();
                        } else {
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketAnimation());
                        }
                        List list = clickedBlocks;
                        WBlockPos wBlockPos5 = currentBlock;
                        if (wBlockPos5 == null) {
                            Intrinsics.throwNpe();
                        }
                        list.add(wBlockPos5);
                        currentBlock = (WBlockPos) null;
                        timer.reset();
                        break;
                    }
                }
                break;
        }
    }

    public void onDisable() {
        clickedBlocks.clear();
    }
}
