package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.ArrayList;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.enums.BlockType;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdN\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0011\u0010&\u001a\u00020'2\u0006\u0010&\u001a\u00020(H\u0082\bJ\b\u0010)\u001a\u00020'H\u0016J\b\u0010*\u001a\u00020'H\u0016J\u0010\u0010+\u001a\u00020'2\u0006\u0010,\u001a\u00020-H\u0007J\u0012\u0010.\u001a\u00020'2\b\u0010,\u001a\u0004\u0018\u00010/H\u0007J\b\u00100\u001a\u00020'H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000e\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u0010\u001a\u00020\u00078BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R\u000e\u0010\u0012\u001a\u00020\u0007X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0013\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0014\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0015\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0018\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0019\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001a\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001c\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001d\u001a\u00020\u001eX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001f\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010 \u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010!\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\"\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010#\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010$\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010%\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u00061"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/BufferSpeed;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "airStrafeValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "bufferValue", "down", "", "fastHop", "forceDown", "hadFastHop", "headBlockBoostValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "headBlockValue", "iceBoostValue", "iceValue", "isNearBlock", "()Z", "legitHop", "maxSpeedValue", "noHurtValue", "slabsBoostValue", "slabsModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "slabsValue", "slimeValue", "snowBoostValue", "snowPortValue", "snowValue", "speed", "", "speedLimitValue", "stairsBoostValue", "stairsModeValue", "stairsValue", "wallBoostValue", "wallModeValue", "wallValue", "boost", "", "", "onDisable", "onEnable", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "reset", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "BufferSpeed", description = "Allows you to walk faster on slabs and stairs.", category = ModuleCategory.MOVEMENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/BufferSpeed.class */
public final class BufferSpeed extends Module {
    private final BoolValue speedLimitValue = new BoolValue("SpeedLimit", true);
    private final FloatValue maxSpeedValue = new FloatValue("MaxSpeed", 2.0f, 1.0f, 5.0f);
    private final BoolValue bufferValue = new BoolValue("Buffer", true);
    private final BoolValue stairsValue = new BoolValue("Stairs", true);
    private final FloatValue stairsBoostValue = new FloatValue("StairsBoost", 1.87f, 1.0f, 2.0f);
    private final ListValue stairsModeValue = new ListValue("StairsMode", new String[]{"Old", "New"}, "New");
    private final BoolValue slabsValue = new BoolValue("Slabs", true);
    private final FloatValue slabsBoostValue = new FloatValue("SlabsBoost", 1.87f, 1.0f, 2.0f);
    private final ListValue slabsModeValue = new ListValue("SlabsMode", new String[]{"Old", "New"}, "New");
    private final BoolValue iceValue = new BoolValue("Ice", false);
    private final FloatValue iceBoostValue = new FloatValue("IceBoost", 1.342f, 1.0f, 2.0f);
    private final BoolValue snowValue = new BoolValue("Snow", true);
    private final FloatValue snowBoostValue = new FloatValue("SnowBoost", 1.87f, 1.0f, 2.0f);
    private final BoolValue snowPortValue = new BoolValue("SnowPort", true);
    private final BoolValue wallValue = new BoolValue("Wall", true);
    private final FloatValue wallBoostValue = new FloatValue("WallBoost", 1.87f, 1.0f, 2.0f);
    private final ListValue wallModeValue = new ListValue("WallMode", new String[]{"Old", "New"}, "New");
    private final BoolValue headBlockValue = new BoolValue("HeadBlock", true);
    private final FloatValue headBlockBoostValue = new FloatValue("HeadBlockBoost", 1.87f, 1.0f, 2.0f);
    private final BoolValue slimeValue = new BoolValue("Slime", true);
    private final BoolValue airStrafeValue = new BoolValue("AirStrafe", false);
    private final BoolValue noHurtValue = new BoolValue("NoHurt", true);
    private double speed;
    private boolean down;
    private boolean forceDown;
    private boolean fastHop;
    private boolean hadFastHop;
    private boolean legitHop;

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent updateEvent) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(Speed.class);
            if (module == null) {
                Intrinsics.throwNpe();
            }
            if (module.getState() || (((Boolean) this.noHurtValue.get()).booleanValue() && thePlayer.getHurtTime() > 0)) {
                reset();
                return;
            }
            WBlockPos wBlockPos = new WBlockPos(thePlayer.getPosX(), thePlayer.getEntityBoundingBox().getMinY(), thePlayer.getPosZ());
            if (this.forceDown || (this.down && thePlayer.getMotionY() == 0.0d)) {
                thePlayer.setMotionY(-1.0d);
                this.down = false;
                this.forceDown = false;
            }
            if (this.fastHop) {
                thePlayer.setSpeedInAir(0.0211f);
                this.hadFastHop = true;
            } else if (this.hadFastHop) {
                thePlayer.setSpeedInAir(0.02f);
                this.hadFastHop = false;
            }
            if (!MovementUtils.isMoving() || thePlayer.isSneaking() || thePlayer.isInWater() || MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                reset();
                return;
            }
            if (thePlayer.getOnGround()) {
                this.fastHop = false;
                if (((Boolean) this.slimeValue.get()).booleanValue() && (MinecraftInstance.classProvider.isBlockSlime(BlockUtils.getBlock(wBlockPos.down())) || MinecraftInstance.classProvider.isBlockSlime(BlockUtils.getBlock(wBlockPos)))) {
                    thePlayer.jump();
                    thePlayer.setMotionX(thePlayer.getMotionY() * 1.132d);
                    thePlayer.setMotionY(0.08d);
                    thePlayer.setMotionZ(thePlayer.getMotionY() * 1.132d);
                    this.down = true;
                    return;
                }
                if (((Boolean) this.slabsValue.get()).booleanValue() && MinecraftInstance.classProvider.isBlockSlab(BlockUtils.getBlock(wBlockPos))) {
                    String str = (String) this.slabsModeValue.get();
                    if (str != null) {
                        String lowerCase = str.toLowerCase();
                        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                        switch (lowerCase.hashCode()) {
                            case 108960:
                                if (lowerCase.equals("new")) {
                                    this.fastHop = true;
                                    if (this.legitHop) {
                                        thePlayer.jump();
                                        thePlayer.setOnGround(false);
                                        this.legitHop = false;
                                        return;
                                    } else {
                                        thePlayer.setOnGround(false);
                                        MovementUtils.strafe(0.375f);
                                        thePlayer.jump();
                                        thePlayer.setMotionY(0.41d);
                                        return;
                                    }
                                }
                                break;
                            case 110119:
                                if (lowerCase.equals("old")) {
                                    float fFloatValue = ((Number) this.slabsBoostValue.get()).floatValue();
                                    IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                                    if (thePlayer2 == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    thePlayer2.setMotionX(thePlayer2.getMotionX() * fFloatValue);
                                    thePlayer2.setMotionZ(thePlayer2.getMotionX() * fFloatValue);
                                    this.speed = MovementUtils.INSTANCE.getSpeed();
                                    if (((Boolean) this.speedLimitValue.get()).booleanValue() && this.speed > ((Number) this.maxSpeedValue.get()).doubleValue()) {
                                        this.speed = ((Number) this.maxSpeedValue.get()).floatValue();
                                        return;
                                    }
                                    return;
                                }
                                break;
                        }
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                }
                if (((Boolean) this.stairsValue.get()).booleanValue() && (MinecraftInstance.classProvider.isBlockStairs(BlockUtils.getBlock(wBlockPos.down())) || MinecraftInstance.classProvider.isBlockStairs(BlockUtils.getBlock(wBlockPos)))) {
                    String str2 = (String) this.stairsModeValue.get();
                    if (str2 != null) {
                        String lowerCase2 = str2.toLowerCase();
                        Intrinsics.checkExpressionValueIsNotNull(lowerCase2, "(this as java.lang.String).toLowerCase()");
                        switch (lowerCase2.hashCode()) {
                            case 108960:
                                if (lowerCase2.equals("new")) {
                                    this.fastHop = true;
                                    if (this.legitHop) {
                                        thePlayer.jump();
                                        thePlayer.setOnGround(false);
                                        this.legitHop = false;
                                        return;
                                    } else {
                                        thePlayer.setOnGround(false);
                                        MovementUtils.strafe(0.375f);
                                        thePlayer.jump();
                                        thePlayer.setMotionY(0.41d);
                                        return;
                                    }
                                }
                                break;
                            case 110119:
                                if (lowerCase2.equals("old")) {
                                    float fFloatValue2 = ((Number) this.stairsBoostValue.get()).floatValue();
                                    IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                                    if (thePlayer3 == null) {
                                        Intrinsics.throwNpe();
                                    }
                                    thePlayer3.setMotionX(thePlayer3.getMotionX() * fFloatValue2);
                                    thePlayer3.setMotionZ(thePlayer3.getMotionX() * fFloatValue2);
                                    this.speed = MovementUtils.INSTANCE.getSpeed();
                                    if (((Boolean) this.speedLimitValue.get()).booleanValue() && this.speed > ((Number) this.maxSpeedValue.get()).doubleValue()) {
                                        this.speed = ((Number) this.maxSpeedValue.get()).floatValue();
                                        return;
                                    }
                                    return;
                                }
                                break;
                        }
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                    }
                }
                this.legitHop = true;
                if (!((Boolean) this.headBlockValue.get()).booleanValue() || !Intrinsics.areEqual(BlockUtils.getBlock(wBlockPos.m44up(2)), MinecraftInstance.classProvider.getBlockEnum(BlockType.AIR))) {
                    if (!((Boolean) this.iceValue.get()).booleanValue() || (!Intrinsics.areEqual(BlockUtils.getBlock(wBlockPos.down()), MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE)) && !Intrinsics.areEqual(BlockUtils.getBlock(wBlockPos.down()), MinecraftInstance.classProvider.getBlockEnum(BlockType.ICE_PACKED)))) {
                        if (((Boolean) this.snowValue.get()).booleanValue() && Intrinsics.areEqual(BlockUtils.getBlock(wBlockPos), MinecraftInstance.classProvider.getBlockEnum(BlockType.SNOW_LAYER)) && (((Boolean) this.snowPortValue.get()).booleanValue() || thePlayer.getPosY() - ((int) thePlayer.getPosY()) >= 0.125d)) {
                            if (thePlayer.getPosY() - ((int) thePlayer.getPosY()) < 0.125d) {
                                thePlayer.jump();
                                this.forceDown = true;
                                return;
                            }
                            float fFloatValue3 = ((Number) this.snowBoostValue.get()).floatValue();
                            IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer4 == null) {
                                Intrinsics.throwNpe();
                            }
                            thePlayer4.setMotionX(thePlayer4.getMotionX() * fFloatValue3);
                            thePlayer4.setMotionZ(thePlayer4.getMotionX() * fFloatValue3);
                            this.speed = MovementUtils.INSTANCE.getSpeed();
                            if (((Boolean) this.speedLimitValue.get()).booleanValue() && this.speed > ((Number) this.maxSpeedValue.get()).doubleValue()) {
                                this.speed = ((Number) this.maxSpeedValue.get()).floatValue();
                                return;
                            }
                            return;
                        }
                        if (((Boolean) this.wallValue.get()).booleanValue()) {
                            String str3 = (String) this.wallModeValue.get();
                            if (str3 != null) {
                                String lowerCase3 = str3.toLowerCase();
                                Intrinsics.checkExpressionValueIsNotNull(lowerCase3, "(this as java.lang.String).toLowerCase()");
                                switch (lowerCase3.hashCode()) {
                                    case 108960:
                                        if (lowerCase3.equals("new") && isNearBlock() && !thePlayer.getMovementInput().getJump()) {
                                            thePlayer.jump();
                                            thePlayer.setMotionY(0.08d);
                                            thePlayer.setMotionX(thePlayer.getMotionX() * 0.99d);
                                            thePlayer.setMotionZ(thePlayer.getMotionX() * 0.99d);
                                            this.down = true;
                                            return;
                                        }
                                        break;
                                    case 110119:
                                        if (lowerCase3.equals("old") && ((thePlayer.isCollidedVertically() && isNearBlock()) || !MinecraftInstance.classProvider.isBlockAir(BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() + 2.0d, thePlayer.getPosZ()))))) {
                                            float fFloatValue4 = ((Number) this.wallBoostValue.get()).floatValue();
                                            IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                                            if (thePlayer5 == null) {
                                                Intrinsics.throwNpe();
                                            }
                                            thePlayer5.setMotionX(thePlayer5.getMotionX() * fFloatValue4);
                                            thePlayer5.setMotionZ(thePlayer5.getMotionX() * fFloatValue4);
                                            this.speed = MovementUtils.INSTANCE.getSpeed();
                                            if (((Boolean) this.speedLimitValue.get()).booleanValue() && this.speed > ((Number) this.maxSpeedValue.get()).doubleValue()) {
                                                this.speed = ((Number) this.maxSpeedValue.get()).floatValue();
                                                return;
                                            }
                                            return;
                                        }
                                        break;
                                }
                            } else {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }
                        }
                        float speed = MovementUtils.INSTANCE.getSpeed();
                        if (this.speed < speed) {
                            this.speed = speed;
                        }
                        if (((Boolean) this.bufferValue.get()).booleanValue() && this.speed > 0.20000000298023224d) {
                            this.speed /= 1.0199999809265137d;
                            MovementUtils.strafe((float) this.speed);
                            return;
                        }
                        return;
                    }
                    float fFloatValue5 = ((Number) this.iceBoostValue.get()).floatValue();
                    IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer6 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer6.setMotionX(thePlayer6.getMotionX() * fFloatValue5);
                    thePlayer6.setMotionZ(thePlayer6.getMotionX() * fFloatValue5);
                    this.speed = MovementUtils.INSTANCE.getSpeed();
                    if (((Boolean) this.speedLimitValue.get()).booleanValue() && this.speed > ((Number) this.maxSpeedValue.get()).doubleValue()) {
                        this.speed = ((Number) this.maxSpeedValue.get()).floatValue();
                        return;
                    }
                    return;
                }
                float fFloatValue6 = ((Number) this.headBlockBoostValue.get()).floatValue();
                IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer7 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer7.setMotionX(thePlayer7.getMotionX() * fFloatValue6);
                thePlayer7.setMotionZ(thePlayer7.getMotionX() * fFloatValue6);
                this.speed = MovementUtils.INSTANCE.getSpeed();
                if (((Boolean) this.speedLimitValue.get()).booleanValue() && this.speed > ((Number) this.maxSpeedValue.get()).doubleValue()) {
                    this.speed = ((Number) this.maxSpeedValue.get()).floatValue();
                    return;
                }
                return;
            }
            this.speed = 0.0d;
            if (((Boolean) this.airStrafeValue.get()).booleanValue()) {
                MovementUtils.strafe$default(0.0f, 1, null);
            }
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.classProvider.isSPacketPlayerPosLook(event.getPacket())) {
            this.speed = 0.0d;
        }
    }

    public void onEnable() {
        reset();
    }

    public void onDisable() {
        reset();
    }

    private final void reset() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            this.legitHop = true;
            this.speed = 0.0d;
            if (this.hadFastHop) {
                thePlayer.setSpeedInAir(0.02f);
                this.hadFastHop = false;
            }
        }
    }

    private final void boost(float f) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        thePlayer.setMotionX(thePlayer.getMotionX() * f);
        thePlayer.setMotionZ(thePlayer.getMotionX() * f);
        this.speed = MovementUtils.INSTANCE.getSpeed();
        if (((Boolean) this.speedLimitValue.get()).booleanValue() && this.speed > ((Number) this.maxSpeedValue.get()).doubleValue()) {
            this.speed = ((Number) this.maxSpeedValue.get()).floatValue();
        }
    }

    private final boolean isNearBlock() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        ArrayList<WBlockPos> arrayList = new ArrayList();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        arrayList.add(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() + 1.0d, thePlayer.getPosZ() - 0.7d));
        arrayList.add(new WBlockPos(thePlayer.getPosX() + 0.7d, thePlayer.getPosY() + 1.0d, thePlayer.getPosZ()));
        arrayList.add(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY() + 1.0d, thePlayer.getPosZ() + 0.7d));
        arrayList.add(new WBlockPos(thePlayer.getPosX() - 0.7d, thePlayer.getPosY() + 1.0d, thePlayer.getPosZ()));
        for (WBlockPos wBlockPos : arrayList) {
            if (theWorld == null) {
                Intrinsics.throwNpe();
            }
            IIBlockState blockState = theWorld.getBlockState(wBlockPos);
            IAxisAlignedBB collisionBoundingBox = blockState.getBlock().getCollisionBoundingBox(theWorld, wBlockPos, blockState);
            if (((collisionBoundingBox == null || collisionBoundingBox.getMaxX() == collisionBoundingBox.getMinY() + 1.0d) && !blockState.getBlock().isTranslucent(blockState) && Intrinsics.areEqual(blockState.getBlock(), MinecraftInstance.classProvider.getBlockEnum(BlockType.WATER)) && !MinecraftInstance.classProvider.isBlockSlab(blockState.getBlock())) || Intrinsics.areEqual(blockState.getBlock(), MinecraftInstance.classProvider.getBlockEnum(BlockType.BARRIER))) {
                return true;
            }
        }
        return false;
    }
}
