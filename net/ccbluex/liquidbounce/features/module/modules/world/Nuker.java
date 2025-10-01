package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
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
import net.ccbluex.liquidbounce.features.module.modules.player.AutoTool;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

/* compiled from: Nuker.kt */
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdf\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\ufffd\ufffd #2\u00020\u0001:\u0001#B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0007J\u0010\u0010\u001d\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001eH\u0007J\u0010\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"H\u0002R\u001e\u0010\u0003\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u0004j\b\u0012\u0004\u0012\u00020\u0005`\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\t\u001a\u0004\u0018\u00010\u0005X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000e\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0013\u001a\u00020\u0014X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0017\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0018\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006$"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/Nuker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "attackedBlocks", "Ljava/util/ArrayList;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "Lkotlin/collections/ArrayList;", "blockHitDelay", "", "currentBlock", "hitDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "layerValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "nuke", "nukeDelay", "nukeTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TickTimer;", "nukeValue", "priorityValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "radiusValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "rotationsValue", "throughWallsValue", "onRender3D", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "validBlock", "", "block", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "Companion", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Nuker", description = "Breaks all blocks around you.", category = ModuleCategory.WORLD)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/world/Nuker.class */
public final class Nuker extends Module {
    private WBlockPos currentBlock;
    private int blockHitDelay;
    private int nuke;
    private static float currentDamage;
    public static final Companion Companion = new Companion(null);
    private final FloatValue radiusValue = new FloatValue("Radius", 5.2f, 1.0f, 6.0f);
    private final BoolValue throughWallsValue = new BoolValue("ThroughWalls", false);
    private final ListValue priorityValue = new ListValue("Priority", new String[]{"Distance", "Hardness"}, "Distance");
    private final BoolValue rotationsValue = new BoolValue("Rotations", true);
    private final BoolValue layerValue = new BoolValue("Layer", false);
    private final IntegerValue hitDelayValue = new IntegerValue("HitDelay", 4, 0, 20);
    private final IntegerValue nukeValue = new IntegerValue("Nuke", 1, 1, 20);
    private final IntegerValue nukeDelay = new IntegerValue("NukeDelay", 1, 1, 20);
    private final ArrayList<WBlockPos> attackedBlocks = new ArrayList<>();
    private TickTimer nukeTimer = new TickTimer();

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        boolean z;
        Object obj;
        Map.Entry entry;
        Object obj2;
        boolean z2;
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (this.blockHitDelay > 0) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().get(FastBreak.class);
            if (module == null) {
                Intrinsics.throwNpe();
            }
            if (!module.getState()) {
                this.blockHitDelay--;
                return;
            }
        }
        this.nukeTimer.update();
        if (this.nukeTimer.hasTimePassed(((Number) this.nukeDelay.get()).intValue())) {
            this.nuke = 0;
            this.nukeTimer.reset();
        }
        this.attackedBlocks.clear();
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (MinecraftInstance.f157mc.getPlayerController().isInCreativeMode()) {
            IClassProvider iClassProvider = MinecraftInstance.classProvider;
            IItemStack heldItem = thePlayer.getHeldItem();
            if (iClassProvider.isItemSword(heldItem != null ? heldItem.getItem() : null)) {
                return;
            }
            Map $this$filter$iv = BlockUtils.searchBlocks(MathKt.roundToInt(((Number) this.radiusValue.get()).floatValue()) + 1);
            Map destination$iv$iv = new LinkedHashMap();
            for (Map.Entry element$iv$iv : $this$filter$iv.entrySet()) {
                WBlockPos pos = (WBlockPos) element$iv$iv.getKey();
                IBlock block = (IBlock) element$iv$iv.getValue();
                if (BlockUtils.getCenterDistance(pos) > ((Number) this.radiusValue.get()).doubleValue() || !validBlock(block)) {
                    z = false;
                } else if (((Boolean) this.layerValue.get()).booleanValue() && pos.getY() < thePlayer.getPosY()) {
                    z = false;
                } else if (((Boolean) this.throughWallsValue.get()).booleanValue()) {
                    z = true;
                } else {
                    WVec3 eyesPos = new WVec3(thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY() + thePlayer.getEyeHeight(), thePlayer.getPosZ());
                    WVec3 blockVec = new WVec3(pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d);
                    IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                    if (theWorld == null) {
                        Intrinsics.throwNpe();
                    }
                    IMovingObjectPosition rayTrace = theWorld.rayTraceBlocks(eyesPos, blockVec, false, true, false);
                    z = rayTrace != null && Intrinsics.areEqual(rayTrace.getBlockPos(), pos);
                }
                if (z) {
                    destination$iv$iv.put(element$iv$iv.getKey(), element$iv$iv.getValue());
                }
            }
            for (Map.Entry element$iv : destination$iv$iv.entrySet()) {
                WBlockPos pos2 = (WBlockPos) element$iv.getKey();
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK, pos2, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                thePlayer.swingItem();
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK, pos2, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                this.attackedBlocks.add(pos2);
            }
            return;
        }
        Map $this$filter$iv2 = BlockUtils.searchBlocks(MathKt.roundToInt(((Number) this.radiusValue.get()).floatValue()) + 1);
        Map destination$iv$iv2 = new LinkedHashMap();
        for (Map.Entry element$iv$iv2 : $this$filter$iv2.entrySet()) {
            WBlockPos pos3 = (WBlockPos) element$iv$iv2.getKey();
            IBlock block2 = (IBlock) element$iv$iv2.getValue();
            if (BlockUtils.getCenterDistance(pos3) > ((Number) this.radiusValue.get()).doubleValue() || !validBlock(block2)) {
                z2 = false;
            } else if (((Boolean) this.layerValue.get()).booleanValue() && pos3.getY() < thePlayer.getPosY()) {
                z2 = false;
            } else if (((Boolean) this.throughWallsValue.get()).booleanValue()) {
                z2 = true;
            } else {
                WVec3 eyesPos2 = new WVec3(thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY() + thePlayer.getEyeHeight(), thePlayer.getPosZ());
                WVec3 blockVec2 = new WVec3(pos3.getX() + 0.5d, pos3.getY() + 0.5d, pos3.getZ() + 0.5d);
                IWorldClient theWorld2 = MinecraftInstance.f157mc.getTheWorld();
                if (theWorld2 == null) {
                    Intrinsics.throwNpe();
                }
                IMovingObjectPosition rayTrace2 = theWorld2.rayTraceBlocks(eyesPos2, blockVec2, false, true, false);
                z2 = rayTrace2 != null && Intrinsics.areEqual(rayTrace2.getBlockPos(), pos3);
            }
            if (z2) {
                destination$iv$iv2.put(element$iv$iv2.getKey(), element$iv$iv2.getValue());
            }
        }
        Map validBlocks = MapsKt.toMutableMap(destination$iv$iv2);
        do {
            String str = (String) this.priorityValue.get();
            switch (str.hashCode()) {
                case 181289442:
                    if (str.equals("Hardness")) {
                        Iterator it = validBlocks.entrySet().iterator();
                        if (it.hasNext()) {
                            Object next = it.next();
                            if (it.hasNext()) {
                                Map.Entry $dstr$pos$block = (Map.Entry) next;
                                WBlockPos pos4 = (WBlockPos) $dstr$pos$block.getKey();
                                IBlock block3 = (IBlock) $dstr$pos$block.getValue();
                                IWorldClient theWorld3 = MinecraftInstance.f157mc.getTheWorld();
                                if (theWorld3 == null) {
                                    Intrinsics.throwNpe();
                                }
                                double hardness = block3.getPlayerRelativeBlockHardness(thePlayer, theWorld3, pos4);
                                WBlockPos safePos = new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() - 1, thePlayer.getPosZ());
                                double min_value = (pos4.getX() == safePos.getX() && safePos.getY() <= pos4.getY() && pos4.getZ() == safePos.getZ()) ? DoubleCompanionObject.INSTANCE.getMIN_VALUE() + hardness : hardness;
                                do {
                                    Object next2 = it.next();
                                    Map.Entry $dstr$pos$block2 = (Map.Entry) next2;
                                    WBlockPos pos5 = (WBlockPos) $dstr$pos$block2.getKey();
                                    IBlock block4 = (IBlock) $dstr$pos$block2.getValue();
                                    IWorldClient theWorld4 = MinecraftInstance.f157mc.getTheWorld();
                                    if (theWorld4 == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    double hardness2 = block4.getPlayerRelativeBlockHardness(thePlayer, theWorld4, pos5);
                                    WBlockPos safePos2 = new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() - 1, thePlayer.getPosZ());
                                    double min_value2 = (pos5.getX() == safePos2.getX() && safePos2.getY() <= pos5.getY() && pos5.getZ() == safePos2.getZ()) ? DoubleCompanionObject.INSTANCE.getMIN_VALUE() + hardness2 : hardness2;
                                    if (Double.compare(min_value, min_value2) < 0) {
                                        next = next2;
                                        min_value = min_value2;
                                    }
                                } while (it.hasNext());
                                obj2 = next;
                            } else {
                                obj2 = next;
                            }
                        } else {
                            obj2 = null;
                        }
                        entry = (Map.Entry) obj2;
                        break;
                    } else {
                        return;
                    }
                    break;
                case 353103893:
                    if (str.equals("Distance")) {
                        Iterable $this$minBy$iv$iv = validBlocks.entrySet();
                        Iterator iterator$iv$iv = $this$minBy$iv$iv.iterator();
                        if (iterator$iv$iv.hasNext()) {
                            Object minElem$iv$iv = iterator$iv$iv.next();
                            if (iterator$iv$iv.hasNext()) {
                                Map.Entry $dstr$pos$block3 = (Map.Entry) minElem$iv$iv;
                                WBlockPos pos6 = (WBlockPos) $dstr$pos$block3.getKey();
                                double distance = BlockUtils.getCenterDistance(pos6);
                                WBlockPos safePos3 = new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() - 1, thePlayer.getPosZ());
                                double minValue$iv$iv = (pos6.getX() == safePos3.getX() && safePos3.getY() <= pos6.getY() && pos6.getZ() == safePos3.getZ()) ? DoubleCompanionObject.INSTANCE.getMAX_VALUE() - distance : distance;
                                do {
                                    Object e$iv$iv = iterator$iv$iv.next();
                                    Map.Entry $dstr$pos$block4 = (Map.Entry) e$iv$iv;
                                    WBlockPos pos7 = (WBlockPos) $dstr$pos$block4.getKey();
                                    double distance2 = BlockUtils.getCenterDistance(pos7);
                                    WBlockPos safePos4 = new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() - 1, thePlayer.getPosZ());
                                    double v$iv$iv = (pos7.getX() == safePos4.getX() && safePos4.getY() <= pos7.getY() && pos7.getZ() == safePos4.getZ()) ? DoubleCompanionObject.INSTANCE.getMAX_VALUE() - distance2 : distance2;
                                    if (Double.compare(minValue$iv$iv, v$iv$iv) > 0) {
                                        minElem$iv$iv = e$iv$iv;
                                        minValue$iv$iv = v$iv$iv;
                                    }
                                } while (iterator$iv$iv.hasNext());
                                obj = minElem$iv$iv;
                            } else {
                                obj = minElem$iv$iv;
                            }
                        } else {
                            obj = null;
                        }
                        entry = (Map.Entry) obj;
                        break;
                    } else {
                        return;
                    }
                    break;
                default:
                    return;
            }
            if (entry != null) {
                Map.Entry entry2 = entry;
                WBlockPos blockPos = (WBlockPos) entry2.getKey();
                IBlock block5 = (IBlock) entry2.getValue();
                if (!Intrinsics.areEqual(blockPos, this.currentBlock)) {
                    currentDamage = 0.0f;
                }
                if (((Boolean) this.rotationsValue.get()).booleanValue()) {
                    VecRotation rotation = RotationUtils.faceBlock(blockPos);
                    if (rotation == null) {
                        return;
                    } else {
                        RotationUtils.setTargetRotation(rotation.getRotation());
                    }
                }
                this.currentBlock = blockPos;
                this.attackedBlocks.add(blockPos);
                Module module2 = LiquidBounce.INSTANCE.getModuleManager().getModule(AutoTool.class);
                if (module2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.player.AutoTool");
                }
                AutoTool autoTool = (AutoTool) module2;
                if (autoTool.getState()) {
                    autoTool.switchSlot(blockPos);
                }
                if (currentDamage == 0.0f) {
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.START_DESTROY_BLOCK, blockPos, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                    IWorldClient theWorld5 = MinecraftInstance.f157mc.getTheWorld();
                    if (theWorld5 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (block5.getPlayerRelativeBlockHardness(thePlayer, theWorld5, blockPos) >= 1.0f) {
                        currentDamage = 0.0f;
                        thePlayer.swingItem();
                        MinecraftInstance.f157mc.getPlayerController().onPlayerDestroyBlock(blockPos, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN));
                        this.blockHitDelay = ((Number) this.hitDelayValue.get()).intValue();
                        validBlocks.remove(blockPos);
                        this.nuke++;
                    }
                }
                thePlayer.swingItem();
                float f = currentDamage;
                IWorldClient theWorld6 = MinecraftInstance.f157mc.getTheWorld();
                if (theWorld6 == null) {
                    Intrinsics.throwNpe();
                }
                currentDamage = f + block5.getPlayerRelativeBlockHardness(thePlayer, theWorld6, blockPos);
                IWorldClient theWorld7 = MinecraftInstance.f157mc.getTheWorld();
                if (theWorld7 == null) {
                    Intrinsics.throwNpe();
                }
                theWorld7.sendBlockBreakProgress(thePlayer.getEntityId(), blockPos, ((int) (currentDamage * 10.0f)) - 1);
                if (currentDamage >= 1.0f) {
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.STOP_DESTROY_BLOCK, blockPos, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
                    MinecraftInstance.f157mc.getPlayerController().onPlayerDestroyBlock(blockPos, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN));
                    this.blockHitDelay = ((Number) this.hitDelayValue.get()).intValue();
                    currentDamage = 0.0f;
                    return;
                }
                return;
            }
            return;
        } while (this.nuke < ((Number) this.nukeValue.get()).intValue());
    }

    @EventTarget
    public final void onRender3D(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (!((Boolean) this.layerValue.get()).booleanValue()) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            double posX = thePlayer.getPosX();
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            double posY = thePlayer2.getPosY() - 1;
            IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer3 == null) {
                Intrinsics.throwNpe();
            }
            WBlockPos safePos = new WBlockPos(posX, posY, thePlayer3.getPosZ());
            IBlock safeBlock = BlockUtils.getBlock(safePos);
            if (safeBlock != null && validBlock(safeBlock)) {
                RenderUtils.drawBlockBox(safePos, Color.GREEN, true);
            }
        }
        Iterator<WBlockPos> it = this.attackedBlocks.iterator();
        while (it.hasNext()) {
            WBlockPos blockPos = it.next();
            RenderUtils.drawBlockBox(blockPos, Color.RED, true);
        }
    }

    private final boolean validBlock(IBlock block) {
        return (MinecraftInstance.classProvider.isBlockAir(block) || MinecraftInstance.classProvider.isBlockLiquid(block) || MinecraftInstance.classProvider.isBlockBedrock(block)) ? false : true;
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0005\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\b\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/Nuker$Companion;", "", "()V", "currentDamage", "", "getCurrentDamage", "()F", "setCurrentDamage", "(F)V", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/world/Nuker$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final float getCurrentDamage() {
            return Nuker.currentDamage;
        }

        public final void setCurrentDamage(float f) {
            Nuker.currentDamage = f;
        }
    }
}
