package net.ccbluex.liquidbounce.features.module.modules.world;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.enums.StatType;
import net.ccbluex.liquidbounce.api.minecraft.block.material.IMaterial;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItem;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render2DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.render.BlockOverlay;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.InventoryUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.PlaceRotation;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.utils.block.PlaceInfo;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0084\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010+\u001a\u00020,H\u0002J\b\u0010-\u001a\u00020,H\u0002J\b\u0010.\u001a\u00020,H\u0016J\u0010\u0010/\u001a\u00020,2\u0006\u00100\u001a\u000201H\u0007J\u0010\u00102\u001a\u00020,2\u0006\u00100\u001a\u000203H\u0007J\u0010\u00104\u001a\u00020,2\u0006\u00100\u001a\u000205H\u0007J\u0010\u00106\u001a\u00020,2\u0006\u00100\u001a\u000207H\u0007J\b\u00108\u001a\u00020,H\u0002J\u0010\u00109\u001a\u00020:2\u0006\u0010;\u001a\u00020<H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0015\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0017\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0018\u001a\u0004\u0018\u00010\u0019X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001a\u001a\u00020\u0016X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001c\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001d\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001e\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001f\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010 \u001a\u00020!8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\"\u0010#R\u000e\u0010$\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010%\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010&\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010'\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010(\u001a\u00020)X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010*\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006="}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/world/Tower;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "autoBlockValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "blocksAmount", "", "getBlocksAmount", "()I", "constantMotionJumpGroundValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "constantMotionValue", "counterDisplayValue", "jumpDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "jumpGround", "", "jumpMotionValue", "keepRotationValue", "lockRotation", "Lnet/ccbluex/liquidbounce/utils/Rotation;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "onJumpValue", "placeInfo", "Lnet/ccbluex/liquidbounce/utils/block/PlaceInfo;", "placeModeValue", "rotationsValue", "slot", "stayAutoBlock", "stopWhenBlockAbove", "swingValue", "tag", "", "getTag", "()Ljava/lang/String;", "teleportDelayValue", "teleportGroundValue", "teleportHeightValue", "teleportNoMotionValue", "timer", "Lnet/ccbluex/liquidbounce/utils/timer/TickTimer;", "timerValue", "fakeJump", "", "move", "onDisable", "onJump", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender2D", "Lnet/ccbluex/liquidbounce/event/Render2DEvent;", "place", "search", "", "blockPosition", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Tower", description = "Automatically builds a tower beneath you.", category = ModuleCategory.WORLD, keyBind = 24)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/world/Tower.class */
public final class Tower extends Module {
    private PlaceInfo placeInfo;
    private Rotation lockRotation;
    private double jumpGround;
    private int slot;
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Jump", "Motion", "ConstantMotion", "MotionTP", "Packet", "Teleport", "AAC3.3.9", "AAC3.6.4"}, "Motion");
    private final BoolValue autoBlockValue = new BoolValue("AutoBlock", true);
    private final BoolValue stayAutoBlock = new BoolValue("StayAutoBlock", false);
    private final BoolValue swingValue = new BoolValue("Swing", true);
    private final BoolValue stopWhenBlockAbove = new BoolValue("StopWhenBlockAbove", false);
    private final BoolValue rotationsValue = new BoolValue("Rotations", true);
    private final BoolValue keepRotationValue = new BoolValue("KeepRotation", false);
    private final BoolValue onJumpValue = new BoolValue("OnJump", false);
    private final ListValue placeModeValue = new ListValue("PlaceTiming", new String[]{"Pre", "Post"}, "Post");
    private final FloatValue timerValue = new FloatValue("Timer", 1.0f, 0.0f, 10.0f);
    private final FloatValue jumpMotionValue = new FloatValue("JumpMotion", 0.42f, 0.3681289f, 0.79f);
    private final IntegerValue jumpDelayValue = new IntegerValue("JumpDelay", 0, 0, 20);
    private final FloatValue constantMotionValue = new FloatValue("ConstantMotion", 0.42f, 0.1f, 1.0f);
    private final FloatValue constantMotionJumpGroundValue = new FloatValue("ConstantMotionJumpGround", 0.79f, 0.76f, 1.0f);
    private final FloatValue teleportHeightValue = new FloatValue("TeleportHeight", 1.15f, 0.1f, 5.0f);
    private final IntegerValue teleportDelayValue = new IntegerValue("TeleportDelay", 0, 0, 20);
    private final BoolValue teleportGroundValue = new BoolValue("TeleportGround", true);
    private final BoolValue teleportNoMotionValue = new BoolValue("TeleportNoMotion", false);
    private final BoolValue counterDisplayValue = new BoolValue("Counter", true);
    private final TickTimer timer = new TickTimer();

    public void onDisable() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
            this.lockRotation = (Rotation) null;
            if (this.slot != thePlayer.getInventory().getCurrentItem()) {
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketHeldItemChange(thePlayer.getInventory().getCurrentItem()));
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0129  */
    @EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMotion(@NotNull MotionEvent event) {
        IEntityPlayerSP thePlayer;
        boolean z;
        VecRotation vecRotationFaceBlock;
        Intrinsics.checkParameterIsNotNull(event, "event");
        if ((!((Boolean) this.onJumpValue.get()).booleanValue() || MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown()) && (thePlayer = MinecraftInstance.f157mc.getThePlayer()) != null) {
            if (((Boolean) this.rotationsValue.get()).booleanValue() && ((Boolean) this.keepRotationValue.get()).booleanValue() && this.lockRotation != null) {
                RotationUtils.setTargetRotation(this.lockRotation);
            }
            MinecraftInstance.f157mc.getTimer().setTimerSpeed(((Number) this.timerValue.get()).floatValue());
            EventState eventState = event.getEventState();
            if (StringsKt.equals((String) this.placeModeValue.get(), eventState.getStateName(), true)) {
                place();
            }
            if (eventState == EventState.PRE) {
                this.placeInfo = (PlaceInfo) null;
                this.timer.update();
                if (((Boolean) this.autoBlockValue.get()).booleanValue()) {
                    if (InventoryUtils.findAutoBlockBlock() != -1) {
                        z = true;
                    } else {
                        if (thePlayer.getHeldItem() != null) {
                            IClassProvider iClassProvider = MinecraftInstance.classProvider;
                            IItemStack heldItem = thePlayer.getHeldItem();
                            if (heldItem == null) {
                                Intrinsics.throwNpe();
                            }
                            if (iClassProvider.isItemBlock(heldItem.getItem())) {
                            }
                        }
                        z = false;
                    }
                } else if (thePlayer.getHeldItem() != null) {
                    IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
                    IItemStack heldItem2 = thePlayer.getHeldItem();
                    if (heldItem2 == null) {
                        Intrinsics.throwNpe();
                    }
                    z = iClassProvider2.isItemBlock(heldItem2.getItem());
                }
                if (z) {
                    if (!((Boolean) this.stopWhenBlockAbove.get()).booleanValue() || MinecraftInstance.classProvider.isBlockAir(BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() + 2.0d, thePlayer.getPosZ())))) {
                        move();
                    }
                    WBlockPos wBlockPos = new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() - 1.0d, thePlayer.getPosZ());
                    IClassProvider iClassProvider3 = MinecraftInstance.classProvider;
                    IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                    if (theWorld == null) {
                        Intrinsics.throwNpe();
                    }
                    if (iClassProvider3.isBlockAir(theWorld.getBlockState(wBlockPos).getBlock()) && search(wBlockPos) && ((Boolean) this.rotationsValue.get()).booleanValue() && (vecRotationFaceBlock = RotationUtils.faceBlock(wBlockPos)) != null) {
                        RotationUtils.setTargetRotation(vecRotationFaceBlock.getRotation());
                        PlaceInfo placeInfo = this.placeInfo;
                        if (placeInfo == null) {
                            Intrinsics.throwNpe();
                        }
                        placeInfo.setVec3(vecRotationFaceBlock.getVec());
                    }
                }
            }
        }
    }

    private final void fakeJump() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        thePlayer.setAirBorne(true);
        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        thePlayer2.triggerAchievement(MinecraftInstance.classProvider.getStatEnum(StatType.JUMP_STAT));
    }

    private final void move() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            String str = (String) this.modeValue.get();
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = str.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case -1360201941:
                    if (lowerCase.equals("teleport")) {
                        if (((Boolean) this.teleportNoMotionValue.get()).booleanValue()) {
                            thePlayer.setMotionY(0.0d);
                        }
                        if ((thePlayer.getOnGround() || !((Boolean) this.teleportGroundValue.get()).booleanValue()) && this.timer.hasTimePassed(((Number) this.teleportDelayValue.get()).intValue())) {
                            fakeJump();
                            thePlayer.setPositionAndUpdate(thePlayer.getPosX(), thePlayer.getPosY() + ((Number) this.teleportHeightValue.get()).doubleValue(), thePlayer.getPosZ());
                            this.timer.reset();
                            return;
                        }
                        return;
                    }
                    return;
                case -1068318794:
                    if (lowerCase.equals("motion")) {
                        if (thePlayer.getOnGround()) {
                            fakeJump();
                            thePlayer.setMotionY(0.42d);
                            return;
                        } else {
                            if (thePlayer.getMotionY() < 0.1d) {
                                thePlayer.setMotionY(-0.3d);
                                return;
                            }
                            return;
                        }
                    }
                    return;
                case -995865464:
                    if (lowerCase.equals("packet") && thePlayer.getOnGround() && this.timer.hasTimePassed(2)) {
                        fakeJump();
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + 0.42d, thePlayer.getPosZ(), false));
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + 0.753d, thePlayer.getPosZ(), false));
                        thePlayer.setPosition(thePlayer.getPosX(), thePlayer.getPosY() + 1.0d, thePlayer.getPosZ());
                        this.timer.reset();
                        return;
                    }
                    return;
                case -157173582:
                    if (lowerCase.equals("motiontp")) {
                        if (thePlayer.getOnGround()) {
                            fakeJump();
                            thePlayer.setMotionY(0.42d);
                            return;
                        } else {
                            if (thePlayer.getMotionY() < 0.23d) {
                                thePlayer.setPosition(thePlayer.getPosX(), MathKt.truncate(thePlayer.getPosY()), thePlayer.getPosZ());
                                return;
                            }
                            return;
                        }
                    }
                    return;
                case 3273774:
                    if (lowerCase.equals("jump") && thePlayer.getOnGround() && this.timer.hasTimePassed(((Number) this.jumpDelayValue.get()).intValue())) {
                        fakeJump();
                        thePlayer.setMotionY(((Number) this.jumpMotionValue.get()).floatValue());
                        this.timer.reset();
                        return;
                    }
                    return;
                case 325228192:
                    if (lowerCase.equals("aac3.3.9")) {
                        if (thePlayer.getOnGround()) {
                            fakeJump();
                            thePlayer.setMotionY(0.4001d);
                        }
                        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
                        if (thePlayer.getMotionY() < 0.0d) {
                            thePlayer.setMotionY(thePlayer.getMotionY() - 9.45E-6d);
                            MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.6f);
                            return;
                        }
                        return;
                    }
                    return;
                case 325231070:
                    if (lowerCase.equals("aac3.6.4")) {
                        if (thePlayer.getTicksExisted() % 4 == 1) {
                            thePlayer.setMotionY(0.4195464d);
                            thePlayer.setPosition(thePlayer.getPosX() - 0.035d, thePlayer.getPosY(), thePlayer.getPosZ());
                            return;
                        } else {
                            if (thePlayer.getTicksExisted() % 4 == 0) {
                                thePlayer.setMotionY(-0.5d);
                                thePlayer.setPosition(thePlayer.getPosX() + 0.035d, thePlayer.getPosY(), thePlayer.getPosZ());
                                return;
                            }
                            return;
                        }
                    }
                    return;
                case 792877146:
                    if (lowerCase.equals("constantmotion")) {
                        if (thePlayer.getOnGround()) {
                            fakeJump();
                            this.jumpGround = thePlayer.getPosY();
                            thePlayer.setMotionY(((Number) this.constantMotionValue.get()).floatValue());
                        }
                        if (thePlayer.getPosY() > this.jumpGround + ((Number) this.constantMotionJumpGroundValue.get()).doubleValue()) {
                            fakeJump();
                            thePlayer.setPosition(thePlayer.getPosX(), MathKt.truncate(thePlayer.getPosY()), thePlayer.getPosZ());
                            thePlayer.setMotionY(((Number) this.constantMotionValue.get()).floatValue());
                            this.jumpGround = thePlayer.getPosY();
                            return;
                        }
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    private final void place() {
        IEntityPlayerSP thePlayer;
        if (this.placeInfo == null || (thePlayer = MinecraftInstance.f157mc.getThePlayer()) == null) {
            return;
        }
        int iFindAutoBlockBlock = -1;
        IItemStack heldItem = thePlayer.getHeldItem();
        if (heldItem == null || !MinecraftInstance.classProvider.isItemBlock(heldItem.getItem())) {
            if (!((Boolean) this.autoBlockValue.get()).booleanValue()) {
                return;
            }
            iFindAutoBlockBlock = InventoryUtils.findAutoBlockBlock();
            if (iFindAutoBlockBlock == -1) {
                return;
            }
            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketHeldItemChange(iFindAutoBlockBlock - 36));
            heldItem = thePlayer.getInventoryContainer().getSlot(iFindAutoBlockBlock).getStack();
        }
        IPlayerControllerMP playerController = MinecraftInstance.f157mc.getPlayerController();
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        IItemStack iItemStack = heldItem;
        if (iItemStack == null) {
            Intrinsics.throwNpe();
        }
        PlaceInfo placeInfo = this.placeInfo;
        if (placeInfo == null) {
            Intrinsics.throwNpe();
        }
        WBlockPos blockPos = placeInfo.getBlockPos();
        PlaceInfo placeInfo2 = this.placeInfo;
        if (placeInfo2 == null) {
            Intrinsics.throwNpe();
        }
        IEnumFacing enumFacing = placeInfo2.getEnumFacing();
        PlaceInfo placeInfo3 = this.placeInfo;
        if (placeInfo3 == null) {
            Intrinsics.throwNpe();
        }
        if (playerController.onPlayerRightClick(thePlayer, theWorld, iItemStack, blockPos, enumFacing, placeInfo3.getVec3())) {
            if (((Boolean) this.swingValue.get()).booleanValue()) {
                thePlayer.swingItem();
            } else {
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketAnimation());
            }
        }
        this.placeInfo = (PlaceInfo) null;
        if (!((Boolean) this.stayAutoBlock.get()).booleanValue() && iFindAutoBlockBlock >= 0) {
            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketHeldItemChange(thePlayer.getInventory().getCurrentItem()));
        }
    }

    private final boolean search(WBlockPos wBlockPos) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            return false;
        }
        IMaterial material = BlockUtils.getMaterial(wBlockPos);
        if (!(material != null ? material.isReplaceable() : false)) {
            return false;
        }
        WVec3 wVec3 = new WVec3(thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY() + thePlayer.getEyeHeight(), thePlayer.getPosZ());
        PlaceRotation placeRotation = (PlaceRotation) null;
        for (EnumFacingType enumFacingType : EnumFacingType.values()) {
            IEnumFacing enumFacing = MinecraftInstance.classProvider.getEnumFacing(enumFacingType);
            WBlockPos wBlockPosOffset$default = WBlockPos.offset$default(wBlockPos, enumFacing, 0, 2, null);
            if (BlockUtils.canBeClicked(wBlockPosOffset$default)) {
                WVec3 wVec32 = new WVec3(enumFacing.getDirectionVec());
                double d = 0.1d;
                while (true) {
                    double d2 = d;
                    if (d2 < 0.9d) {
                        double d3 = 0.1d;
                        while (true) {
                            double d4 = d3;
                            if (d4 < 0.9d) {
                                double d5 = 0.1d;
                                while (true) {
                                    double d6 = d5;
                                    if (d6 < 0.9d) {
                                        WVec3 wVec33 = new WVec3(wBlockPos);
                                        WVec3 wVec34 = new WVec3(wVec33.getXCoord() + d2, wVec33.getYCoord() + d4, wVec33.getZCoord() + d6);
                                        double xCoord = wVec34.getXCoord() - wVec3.getXCoord();
                                        double yCoord = wVec34.getYCoord() - wVec3.getYCoord();
                                        double zCoord = wVec34.getZCoord() - wVec3.getZCoord();
                                        double d7 = (xCoord * xCoord) + (yCoord * yCoord) + (zCoord * zCoord);
                                        WVec3 wVec35 = new WVec3(wVec32.getXCoord() * 0.5d, wVec32.getYCoord() * 0.5d, wVec32.getZCoord() * 0.5d);
                                        WVec3 wVec36 = new WVec3(wVec34.getXCoord() + wVec35.getXCoord(), wVec34.getYCoord() + wVec35.getYCoord(), wVec34.getZCoord() + wVec35.getZCoord());
                                        double xCoord2 = wVec36.getXCoord() - wVec3.getXCoord();
                                        double yCoord2 = wVec36.getYCoord() - wVec3.getYCoord();
                                        double zCoord2 = wVec36.getZCoord() - wVec3.getZCoord();
                                        if ((xCoord2 * xCoord2) + (yCoord2 * yCoord2) + (zCoord2 * zCoord2) <= 18.0d) {
                                            WVec3 wVec37 = new WVec3(wVec34.getXCoord() + wVec32.getXCoord(), wVec34.getYCoord() + wVec32.getYCoord(), wVec34.getZCoord() + wVec32.getZCoord());
                                            double xCoord3 = wVec37.getXCoord() - wVec3.getXCoord();
                                            double yCoord3 = wVec37.getYCoord() - wVec3.getYCoord();
                                            double zCoord3 = wVec37.getZCoord() - wVec3.getZCoord();
                                            if (d7 <= (xCoord3 * xCoord3) + (yCoord3 * yCoord3) + (zCoord3 * zCoord3)) {
                                                IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                                                if (theWorld == null) {
                                                    Intrinsics.throwNpe();
                                                }
                                                if (theWorld.rayTraceBlocks(wVec3, wVec36, false, true, false) == null) {
                                                    double xCoord4 = wVec36.getXCoord() - wVec3.getXCoord();
                                                    double yCoord4 = wVec36.getYCoord() - wVec3.getYCoord();
                                                    double zCoord4 = wVec36.getZCoord() - wVec3.getZCoord();
                                                    double dSqrt = Math.sqrt((xCoord4 * xCoord4) + (zCoord4 * zCoord4));
                                                    float degrees = (((float) Math.toDegrees(Math.atan2(zCoord4, xCoord4))) - 90.0f) % 360.0f;
                                                    if (degrees >= 180.0f) {
                                                        degrees -= 360.0f;
                                                    }
                                                    if (degrees < -180.0f) {
                                                        degrees += 360.0f;
                                                    }
                                                    float f = degrees;
                                                    float f2 = ((float) (-Math.toDegrees(Math.atan2(yCoord4, dSqrt)))) % 360.0f;
                                                    if (f2 >= 180.0f) {
                                                        f2 -= 360.0f;
                                                    }
                                                    if (f2 < -180.0f) {
                                                        f2 += 360.0f;
                                                    }
                                                    Rotation rotation = new Rotation(f, f2);
                                                    WVec3 vectorForRotation = RotationUtils.getVectorForRotation(rotation);
                                                    WVec3 wVec38 = new WVec3(wVec3.getXCoord() + (vectorForRotation.getXCoord() * 4.0d), wVec3.getYCoord() + (vectorForRotation.getYCoord() * 4.0d), wVec3.getZCoord() + (vectorForRotation.getZCoord() * 4.0d));
                                                    IWorldClient theWorld2 = MinecraftInstance.f157mc.getTheWorld();
                                                    if (theWorld2 == null) {
                                                        Intrinsics.throwNpe();
                                                    }
                                                    IMovingObjectPosition iMovingObjectPositionRayTraceBlocks = theWorld2.rayTraceBlocks(wVec3, wVec38, false, false, true);
                                                    if (iMovingObjectPositionRayTraceBlocks == null) {
                                                        Intrinsics.throwNpe();
                                                    }
                                                    if (iMovingObjectPositionRayTraceBlocks.getTypeOfHit() == IMovingObjectPosition.WMovingObjectType.BLOCK && Intrinsics.areEqual(iMovingObjectPositionRayTraceBlocks.getBlockPos(), wBlockPosOffset$default)) {
                                                        if (placeRotation == null || RotationUtils.getRotationDifference(rotation) < RotationUtils.getRotationDifference(placeRotation.getRotation())) {
                                                            placeRotation = new PlaceRotation(new PlaceInfo(wBlockPosOffset$default, enumFacing.getOpposite(), wVec36), rotation);
                                                        }
                                                        d5 = d6 + 0.1d;
                                                    } else {
                                                        d5 = d6 + 0.1d;
                                                    }
                                                }
                                            }
                                        }
                                        d5 = d6 + 0.1d;
                                    }
                                }
                                d3 = d4 + 0.1d;
                            }
                        }
                        d = d2 + 0.1d;
                    }
                }
            }
        }
        if (placeRotation == null) {
            return false;
        }
        if (((Boolean) this.rotationsValue.get()).booleanValue()) {
            RotationUtils.setTargetRotation(placeRotation.getRotation(), 0);
            this.lockRotation = placeRotation.getRotation();
        }
        this.placeInfo = placeRotation.getPlaceInfo();
        return true;
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.f157mc.getThePlayer() == null) {
            return;
        }
        IPacket packet = event.getPacket();
        if (MinecraftInstance.classProvider.isCPacketHeldItemChange(packet)) {
            this.slot = packet.asCPacketHeldItemChange().getSlotId();
        }
    }

    @EventTarget
    public final void onRender2D(@NotNull Render2DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (((Boolean) this.counterDisplayValue.get()).booleanValue()) {
            GL11.glPushMatrix();
            Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(BlockOverlay.class);
            if (module == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.render.BlockOverlay");
            }
            BlockOverlay blockOverlay = (BlockOverlay) module;
            if (blockOverlay.getState() && ((Boolean) blockOverlay.getInfoValue().get()).booleanValue() && blockOverlay.getCurrentBlock() != null) {
                GL11.glTranslatef(0.0f, 15.0f, 0.0f);
            }
            String str = "Blocks: \u00a77" + getBlocksAmount();
            IClassProvider iClassProvider = MinecraftInstance.classProvider;
            IMinecraft mc = MinecraftInstance.f157mc;
            Intrinsics.checkExpressionValueIsNotNull(mc, "mc");
            IScaledResolution iScaledResolutionCreateScaledResolution = iClassProvider.createScaledResolution(mc);
            Color color = Color.BLACK;
            Intrinsics.checkExpressionValueIsNotNull(color, "Color.BLACK");
            int rgb = color.getRGB();
            Color color2 = Color.BLACK;
            Intrinsics.checkExpressionValueIsNotNull(color2, "Color.BLACK");
            RenderUtils.drawBorderedRect((iScaledResolutionCreateScaledResolution.getScaledWidth() / 2) - 2.0f, (iScaledResolutionCreateScaledResolution.getScaledHeight() / 2) + 5.0f, (iScaledResolutionCreateScaledResolution.getScaledWidth() / 2) + Fonts.font40.getStringWidth(str) + 2.0f, (iScaledResolutionCreateScaledResolution.getScaledHeight() / 2) + 16.0f, 3.0f, rgb, color2.getRGB());
            MinecraftInstance.classProvider.getGlStateManager().resetColor();
            Color color3 = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(color3, "Color.WHITE");
            Fonts.font40.drawString(str, iScaledResolutionCreateScaledResolution.getScaledWidth() / 2.0f, (iScaledResolutionCreateScaledResolution.getScaledHeight() / 2) + 7.0f, color3.getRGB());
            GL11.glPopMatrix();
        }
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (((Boolean) this.onJumpValue.get()).booleanValue()) {
            event.cancelEvent();
        }
    }

    private final int getBlocksAmount() {
        int stackSize = 0;
        for (int i = 36; i <= 44; i++) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            IItemStack stack = thePlayer.getInventoryContainer().getSlot(i).getStack();
            if (stack != null && MinecraftInstance.classProvider.isItemBlock(stack.getItem())) {
                IItem item = stack.getItem();
                if (item == null) {
                    Intrinsics.throwNpe();
                }
                IBlock block = item.asItemBlock().getBlock();
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                if (Intrinsics.areEqual(thePlayer2.getHeldItem(), stack) || !InventoryUtils.BLOCK_BLACKLIST.contains(block)) {
                    stackSize += stack.getStackSize();
                }
            }
        }
        return stackSize;
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
