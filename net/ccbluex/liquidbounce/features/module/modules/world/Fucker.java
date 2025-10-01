package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import jdk.nashorn.internal.runtime.linker.LinkerCallSite;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IExtractedFunctions;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.player.AutoTool;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BlockValue;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdl\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u00c7\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010#\u001a\u0004\u0018\u00010\u00132\u0006\u0010$\u001a\u00020\u0006H\u0002J\u0010\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u0013H\u0002J\u0010\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+H\u0007J\u0010\u0010,\u001a\u00020)2\u0006\u0010*\u001a\u00020-H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010\t\u001a\u00020\nX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0012\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0013X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0017\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0018\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0019\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u001e\u001a\u00020\u001f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b \u0010!R\u000e\u0010\"\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006."}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/Fucker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "actionValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "blockHitDelay", "", "blockValue", "Lnet/ccbluex/liquidbounce/value/BlockValue;", "currentDamage", "", "getCurrentDamage", "()F", "setCurrentDamage", "(F)V", "instantValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "noHitValue", "oldPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "pos", "rangeValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rotationsValue", "surroundingsValue", "swingValue", "switchTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "switchValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "tag", "", "getTag", "()Ljava/lang/String;", "throughWallsValue", "find", "targetID", "isHitable", "", "blockPos", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Fucker", description = "Destroys selected blocks around you. (aka.  IDNuker)", category = ModuleCategory.WORLD)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/world/Fucker.class */
public final class Fucker extends Module {
    private static WBlockPos pos;
    private static WBlockPos oldPos;
    private static int blockHitDelay;
    private static float currentDamage;
    public static final Fucker INSTANCE = new Fucker();
    private static final BlockValue blockValue = new BlockValue("Block", 26);
    private static final ListValue throughWallsValue = new ListValue("ThroughWalls", new String[]{"None", "Raycast", "Around"}, "None");
    private static final FloatValue rangeValue = new FloatValue("Range", 5.0f, 1.0f, 7.0f);
    private static final ListValue actionValue = new ListValue("Action", new String[]{"Destroy", "Use"}, "Destroy");
    private static final BoolValue instantValue = new BoolValue("Instant", false);
    private static final IntegerValue switchValue = new IntegerValue("SwitchDelay", LinkerCallSite.ARGLIMIT, 0, 1000);
    private static final BoolValue swingValue = new BoolValue("Swing", true);
    private static final BoolValue rotationsValue = new BoolValue("Rotations", true);
    private static final BoolValue surroundingsValue = new BoolValue("Surroundings", true);
    private static final BoolValue noHitValue = new BoolValue("NoHit", false);
    private static final MSTimer switchTimer = new MSTimer();

    private Fucker() {
    }

    public final float getCurrentDamage() {
        return currentDamage;
    }

    public final void setCurrentDamage(float f) {
        currentDamage = f;
    }

    /* JADX WARN: Code restructure failed: missing block: B:128:0x0308, code lost:
    
        if (r0.getPlayerRelativeBlockHardness(r0, r2, r3) >= 1.0f) goto L129;
     */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00a3  */
    @EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            if (((Boolean) noHitValue.get()).booleanValue()) {
                Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(KillAura.class);
                if (module == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
                }
                KillAura killAura = (KillAura) module;
                if (killAura.getState() && killAura.getTarget() != null) {
                    return;
                }
            }
            int iIntValue = ((Number) blockValue.get()).intValue();
            if (pos != null) {
                IExtractedFunctions iExtractedFunctions = MinecraftInstance.functions;
                WBlockPos wBlockPos = pos;
                if (wBlockPos == null) {
                    Intrinsics.throwNpe();
                }
                IBlock block = BlockUtils.getBlock(wBlockPos);
                if (block == null) {
                    Intrinsics.throwNpe();
                }
                if (iExtractedFunctions.getIdFromBlock(block) == iIntValue) {
                    WBlockPos wBlockPos2 = pos;
                    if (wBlockPos2 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (BlockUtils.getCenterDistance(wBlockPos2) > ((Number) rangeValue.get()).doubleValue()) {
                        pos = find(iIntValue);
                    }
                }
            }
            if (pos == null) {
                currentDamage = 0.0f;
                return;
            }
            WBlockPos wBlockPos3 = pos;
            if (wBlockPos3 != null) {
                WBlockPos wBlockPos4 = wBlockPos3;
                VecRotation vecRotationFaceBlock = RotationUtils.faceBlock(wBlockPos4);
                if (vecRotationFaceBlock != null) {
                    VecRotation vecRotation = vecRotationFaceBlock;
                    boolean z = false;
                    if (((Boolean) surroundingsValue.get()).booleanValue()) {
                        WVec3 positionEyes = thePlayer.getPositionEyes(1.0f);
                        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                        if (theWorld == null) {
                            Intrinsics.throwNpe();
                        }
                        IMovingObjectPosition iMovingObjectPositionRayTraceBlocks = theWorld.rayTraceBlocks(positionEyes, vecRotation.getVec(), false, false, true);
                        WBlockPos blockPos = iMovingObjectPositionRayTraceBlocks != null ? iMovingObjectPositionRayTraceBlocks.getBlockPos() : null;
                        if (blockPos != null && !MinecraftInstance.classProvider.isBlockAir(blockPos)) {
                            if (wBlockPos4.getX() != blockPos.getX() || wBlockPos4.getY() != blockPos.getY() || wBlockPos4.getZ() != blockPos.getZ()) {
                                z = true;
                            }
                            pos = blockPos;
                            WBlockPos wBlockPos5 = pos;
                            if (wBlockPos5 == null) {
                                return;
                            }
                            wBlockPos4 = wBlockPos5;
                            VecRotation vecRotationFaceBlock2 = RotationUtils.faceBlock(wBlockPos4);
                            if (vecRotationFaceBlock2 == null) {
                                return;
                            } else {
                                vecRotation = vecRotationFaceBlock2;
                            }
                        }
                    }
                    if (oldPos != null && (!Intrinsics.areEqual(oldPos, wBlockPos4))) {
                        currentDamage = 0.0f;
                        switchTimer.reset();
                    }
                    oldPos = wBlockPos4;
                    if (!switchTimer.hasTimePassed(((Number) switchValue.get()).intValue())) {
                        return;
                    }
                    if (blockHitDelay > 0) {
                        blockHitDelay--;
                        return;
                    }
                    if (((Boolean) rotationsValue.get()).booleanValue()) {
                        RotationUtils.setTargetRotation(vecRotation.getRotation());
                    }
                    if (StringsKt.equals((String) actionValue.get(), "destroy", true) || z) {
                        Module module2 = LiquidBounce.INSTANCE.getModuleManager().get(AutoTool.class);
                        if (module2 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.AutoTool");
                        }
                        AutoTool autoTool = (AutoTool) module2;
                        if (autoTool.getState()) {
                            autoTool.switchSlot(wBlockPos4);
                        }
                        if (((Boolean) instantValue.get()).booleanValue()) {
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK, wBlockPos4, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                            if (((Boolean) swingValue.get()).booleanValue()) {
                                thePlayer.swingItem();
                            }
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK, wBlockPos4, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                            currentDamage = 0.0f;
                            return;
                        }
                        IBlock block2 = wBlockPos4.getBlock();
                        if (block2 != null) {
                            if (currentDamage == 0.0f) {
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK, wBlockPos4, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                                if (!thePlayer.getCapabilities().isCreativeMode()) {
                                    IWorldClient theWorld2 = MinecraftInstance.f157mc.getTheWorld();
                                    if (theWorld2 == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    IWorldClient iWorldClient = theWorld2;
                                    WBlockPos wBlockPos6 = pos;
                                    if (wBlockPos6 == null) {
                                        Intrinsics.throwNpe();
                                    }
                                }
                                if (((Boolean) swingValue.get()).booleanValue()) {
                                    thePlayer.swingItem();
                                }
                                IPlayerControllerMP playerController = MinecraftInstance.f157mc.getPlayerController();
                                WBlockPos wBlockPos7 = pos;
                                if (wBlockPos7 == null) {
                                    Intrinsics.throwNpe();
                                }
                                playerController.onPlayerDestroyBlock(wBlockPos7, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN));
                                currentDamage = 0.0f;
                                pos = (WBlockPos) null;
                                return;
                            }
                            if (((Boolean) swingValue.get()).booleanValue()) {
                                thePlayer.swingItem();
                            }
                            float f = currentDamage;
                            IWorldClient theWorld3 = MinecraftInstance.f157mc.getTheWorld();
                            if (theWorld3 == null) {
                                Intrinsics.throwNpe();
                            }
                            currentDamage = f + block2.getPlayerRelativeBlockHardness(thePlayer, theWorld3, wBlockPos4);
                            IWorldClient theWorld4 = MinecraftInstance.f157mc.getTheWorld();
                            if (theWorld4 == null) {
                                Intrinsics.throwNpe();
                            }
                            theWorld4.sendBlockBreakProgress(thePlayer.getEntityId(), wBlockPos4, ((int) (currentDamage * 10.0f)) - 1);
                            if (currentDamage >= 1.0f) {
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK, wBlockPos4, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                                MinecraftInstance.f157mc.getPlayerController().onPlayerDestroyBlock(wBlockPos4, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN));
                                blockHitDelay = 4;
                                currentDamage = 0.0f;
                                pos = (WBlockPos) null;
                                return;
                            }
                            return;
                        }
                        return;
                    }
                    if (StringsKt.equals((String) actionValue.get(), "use", true)) {
                        IPlayerControllerMP playerController2 = MinecraftInstance.f157mc.getPlayerController();
                        IWorldClient theWorld5 = MinecraftInstance.f157mc.getTheWorld();
                        if (theWorld5 == null) {
                            Intrinsics.throwNpe();
                        }
                        IItemStack heldItem = thePlayer.getHeldItem();
                        if (heldItem == null) {
                            Intrinsics.throwNpe();
                        }
                        WBlockPos wBlockPos8 = pos;
                        if (wBlockPos8 == null) {
                            Intrinsics.throwNpe();
                        }
                        if (playerController2.onPlayerRightClick(thePlayer, theWorld5, heldItem, wBlockPos8, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN), new WVec3(wBlockPos4.getX(), wBlockPos4.getY(), wBlockPos4.getZ()))) {
                            if (((Boolean) swingValue.get()).booleanValue()) {
                                thePlayer.swingItem();
                            }
                            blockHitDelay = 4;
                            currentDamage = 0.0f;
                            pos = (WBlockPos) null;
                        }
                    }
                }
            }
        }
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        WBlockPos wBlockPos = pos;
        if (wBlockPos != null) {
            RenderUtils.drawBlockBox(wBlockPos, Color.RED, true);
        }
    }

    private final WBlockPos find(int i) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            return null;
        }
        int iFloatValue = ((int) ((Number) rangeValue.get()).floatValue()) + 1;
        DoubleCompanionObject doubleCompanionObject = DoubleCompanionObject.INSTANCE;
        double d = Double.MAX_VALUE;
        WBlockPos wBlockPos = (WBlockPos) null;
        int i2 = iFloatValue;
        int i3 = (-iFloatValue) + 1;
        if (i2 >= i3) {
            while (true) {
                int i4 = iFloatValue;
                int i5 = (-iFloatValue) + 1;
                if (i4 >= i5) {
                    while (true) {
                        int i6 = iFloatValue;
                        int i7 = (-iFloatValue) + 1;
                        if (i6 >= i7) {
                            while (true) {
                                WBlockPos wBlockPos2 = new WBlockPos(((int) thePlayer.getPosX()) + i2, ((int) thePlayer.getPosY()) + i4, ((int) thePlayer.getPosZ()) + i6);
                                IBlock block = BlockUtils.getBlock(wBlockPos2);
                                if (block != null && MinecraftInstance.functions.getIdFromBlock(block) == i) {
                                    double centerDistance = BlockUtils.getCenterDistance(wBlockPos2);
                                    if (centerDistance <= ((Number) rangeValue.get()).doubleValue() && d >= centerDistance && (isHitable(wBlockPos2) || ((Boolean) surroundingsValue.get()).booleanValue())) {
                                        d = centerDistance;
                                        wBlockPos = wBlockPos2;
                                    }
                                }
                                if (i6 == i7) {
                                    break;
                                }
                                i6--;
                            }
                        }
                        if (i4 == i5) {
                            break;
                        }
                        i4--;
                    }
                }
                if (i2 == i3) {
                    break;
                }
                i2--;
            }
        }
        return wBlockPos;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    private final boolean isHitable(WBlockPos wBlockPos) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            return false;
        }
        String str = (String) throughWallsValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1409235507:
                return (lowerCase.equals("around") && BlockUtils.isFullBlock(wBlockPos.down()) && BlockUtils.isFullBlock(wBlockPos.m43up()) && BlockUtils.isFullBlock(wBlockPos.north()) && BlockUtils.isFullBlock(wBlockPos.east()) && BlockUtils.isFullBlock(wBlockPos.south()) && BlockUtils.isFullBlock(wBlockPos.west())) ? false : true;
            case 988024425:
                if (lowerCase.equals("raycast")) {
                    WVec3 wVec3 = new WVec3(thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY() + thePlayer.getEyeHeight(), thePlayer.getPosZ());
                    IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                    if (theWorld == null) {
                        Intrinsics.throwNpe();
                    }
                    IMovingObjectPosition iMovingObjectPositionRayTraceBlocks = theWorld.rayTraceBlocks(wVec3, new WVec3(wBlockPos.getX() + 0.5d, wBlockPos.getY() + 0.5d, wBlockPos.getZ() + 0.5d), false, true, false);
                    return iMovingObjectPositionRayTraceBlocks != null && Intrinsics.areEqual(iMovingObjectPositionRayTraceBlocks.getBlockPos(), wBlockPos);
                }
                return true;
            default:
                return true;
        }
    }

    @NotNull
    public String getTag() {
        return ((Number) blockValue.get()).intValue() == 26 ? "Bed" : BlockUtils.getBlockName(((Number) blockValue.get()).intValue());
    }
}
