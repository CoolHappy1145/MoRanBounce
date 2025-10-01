package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdD\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0007J\u0010\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0011H\u0007J\u0010\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0013H\u0007J\u0010\u0010\u0014\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0015H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0016"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/WallClimb;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "checkerClimbMotionValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "clipMode", "Lnet/ccbluex/liquidbounce/value/ListValue;", "glitch", "", "modeValue", "waited", "", "onBlockBB", "", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "WallClimb", description = "Allows you to climb up walls like a spider.", category = ModuleCategory.MOVEMENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/WallClimb.class */
public final class WallClimb extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Simple", "CheckerClimb", "Clip", "AAC3.3.12", "AACGlide"}, "Simple");
    private final ListValue clipMode = new ListValue("ClipMode", new String[]{"Jump", "Fast"}, "Fast");
    private final FloatValue checkerClimbMotionValue = new FloatValue("CheckerClimbMotion", 0.0f, 0.0f, 1.0f);
    private boolean glitch;
    private int waited;

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null && thePlayer.isCollidedHorizontally() && !thePlayer.isOnLadder() && !thePlayer.isInWater() && !thePlayer.isInLava() && StringsKt.equals("simple", (String) this.modeValue.get(), true)) {
            event.setY(0.2d);
            thePlayer.setMotionY(0.0d);
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (event.getEventState() != EventState.POST || thePlayer == null) {
            return;
        }
        String str = (String) this.modeValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case 3056464:
                if (lowerCase.equals("clip")) {
                    if (thePlayer.getMotionY() < 0.0d) {
                        this.glitch = true;
                    }
                    if (thePlayer.isCollidedHorizontally()) {
                        String str2 = (String) this.clipMode.get();
                        if (str2 == null) {
                            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                        }
                        String lowerCase2 = str2.toLowerCase();
                        Intrinsics.checkExpressionValueIsNotNull(lowerCase2, "(this as java.lang.String).toLowerCase()");
                        switch (lowerCase2.hashCode()) {
                            case 3135580:
                                if (lowerCase2.equals("fast")) {
                                    if (thePlayer.getOnGround()) {
                                        thePlayer.setMotionY(0.42d);
                                        return;
                                    } else {
                                        if (thePlayer.getMotionY() < 0.0d) {
                                            thePlayer.setMotionY(-0.3d);
                                            return;
                                        }
                                        return;
                                    }
                                }
                                return;
                            case 3273774:
                                if (lowerCase2.equals("jump") && thePlayer.getOnGround()) {
                                    thePlayer.jump();
                                    return;
                                }
                                return;
                            default:
                                return;
                        }
                    }
                    return;
                }
                return;
            case 375151938:
                if (lowerCase.equals("aacglide") && thePlayer.isCollidedHorizontally() && !thePlayer.isOnLadder()) {
                    thePlayer.setMotionY(-0.19d);
                    return;
                }
                return;
            case 1076723744:
                if (lowerCase.equals("checkerclimb")) {
                    boolean zCollideBlockIntersects = BlockUtils.collideBlockIntersects(thePlayer.getEntityBoundingBox(), new Function1() { // from class: net.ccbluex.liquidbounce.features.module.modules.movement.WallClimb$onUpdate$isInsideBlock$1
                        @Override // kotlin.jvm.functions.Function1
                        public Object invoke(Object obj) {
                            return Boolean.valueOf(invoke((IBlock) obj));
                        }

                        public final boolean invoke(@Nullable IBlock iBlock) {
                            return !MinecraftInstance.classProvider.isBlockAir(iBlock);
                        }
                    });
                    float fFloatValue = ((Number) this.checkerClimbMotionValue.get()).floatValue();
                    if (zCollideBlockIntersects && fFloatValue != 0.0f) {
                        thePlayer.setMotionY(fFloatValue);
                        return;
                    }
                    return;
                }
                return;
            case 1492139162:
                if (lowerCase.equals("aac3.3.12")) {
                    if (thePlayer.isCollidedHorizontally() && !thePlayer.isOnLadder()) {
                        this.waited++;
                        if (this.waited == 1) {
                            thePlayer.setMotionY(0.43d);
                        }
                        if (this.waited == 12) {
                            thePlayer.setMotionY(0.43d);
                        }
                        if (this.waited == 23) {
                            thePlayer.setMotionY(0.43d);
                        }
                        if (this.waited == 29) {
                            thePlayer.setPosition(thePlayer.getPosX(), thePlayer.getPosY() + 0.5d, thePlayer.getPosZ());
                        }
                        if (this.waited >= 30) {
                            this.waited = 0;
                            return;
                        }
                        return;
                    }
                    if (thePlayer.getOnGround()) {
                        this.waited = 0;
                        return;
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.classProvider.isCPacketPlayer(event.getPacket())) {
            ICPacketPlayer iCPacketPlayerAsCPacketPlayer = event.getPacket().asCPacketPlayer();
            if (this.glitch) {
                float direction = (float) MovementUtils.getDirection();
                iCPacketPlayerAsCPacketPlayer.setX(iCPacketPlayerAsCPacketPlayer.getX() - (((float) Math.sin(direction)) * 1.0E-8d));
                iCPacketPlayerAsCPacketPlayer.setZ(iCPacketPlayerAsCPacketPlayer.getZ() + (((float) Math.cos(direction)) * 1.0E-8d));
                this.glitch = false;
            }
        }
    }

    @EventTarget
    public final void onBlockBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            String str = (String) this.modeValue.get();
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = str.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case 3056464:
                    if (!lowerCase.equals("clip") || event.getBlock() == null || MinecraftInstance.f157mc.getThePlayer() == null || !MinecraftInstance.classProvider.isBlockAir(event.getBlock()) || event.getY() >= thePlayer.getPosY() || !thePlayer.isCollidedHorizontally() || thePlayer.isOnLadder() || thePlayer.isInWater() || thePlayer.isInLava()) {
                        return;
                    }
                    event.setBoundingBox(MinecraftInstance.classProvider.createAxisAlignedBB(0.0d, 0.0d, 0.0d, 1.0d, 1.0d, 1.0d).offset(thePlayer.getPosX(), ((int) thePlayer.getPosY()) - 1.0d, thePlayer.getPosZ()));
                    return;
                case 1076723744:
                    if (!lowerCase.equals("checkerclimb") || event.getY() <= thePlayer.getPosY()) {
                        return;
                    }
                    event.setBoundingBox((IAxisAlignedBB) null);
                    return;
                default:
                    return;
            }
        }
    }
}
