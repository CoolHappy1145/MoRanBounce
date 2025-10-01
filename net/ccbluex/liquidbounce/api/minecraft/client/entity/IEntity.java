package net.ccbluex.liquidbounce.api.minecraft.client.entity;

import java.util.UUID;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Metadata;
import kotlin.jvm.JvmName;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.block.material.IMaterial;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0096\u0001\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\bf\u0018\ufffd\ufffd2\u00020\u0001J%\u0010\u008a\u0001\u001a\u00030\u008b\u00012\u0007\u0010\u008c\u0001\u001a\u00020?2\u0007\u0010\u008d\u0001\u001a\u00020?2\u0007\u0010\u008e\u0001\u001a\u00020?H&J\n\u0010\u008f\u0001\u001a\u00030\u0090\u0001H&J\n\u0010\u0091\u0001\u001a\u00030\u0092\u0001H&J\n\u0010\u0093\u0001\u001a\u00030\u0094\u0001H&J\t\u0010\u0095\u0001\u001a\u00020\u0003H&J\t\u0010\u0096\u0001\u001a\u00020\u0003H&J\u0014\u0010\u0097\u0001\u001a\u00030\u008b\u00012\b\u0010\u0098\u0001\u001a\u00030\u0099\u0001H&J$\u0010\u009a\u0001\u001a\u00020?2\u0007\u0010\u008c\u0001\u001a\u00020?2\u0007\u0010\u008d\u0001\u001a\u00020?2\u0007\u0010\u008e\u0001\u001a\u00020?H&J\u0012\u0010\u009b\u0001\u001a\u00020?2\u0007\u0010\u009c\u0001\u001a\u00020fH&J\u0012\u0010\u009d\u0001\u001a\u00020?2\u0007\u0010\u009c\u0001\u001a\u00020fH&J\u0012\u0010\u009e\u0001\u001a\u00020?2\u0007\u0010\u009f\u0001\u001a\u00020\ufffd\ufffdH&J\u0012\u0010\u00a0\u0001\u001a\u00020\u00072\u0007\u0010\u009f\u0001\u001a\u00020\ufffd\ufffdH&J\u0012\u0010\u00a1\u0001\u001a\u00020G2\u0007\u0010\u00a2\u0001\u001a\u00020\u0007H&J\u0012\u0010\u00a3\u0001\u001a\u00020G2\u0007\u0010\u00a2\u0001\u001a\u00020\u0007H&J\u0013\u0010\u00a4\u0001\u001a\u00020\u00032\b\u0010\u00a5\u0001\u001a\u00030\u00a6\u0001H&J%\u0010\u00a7\u0001\u001a\u00030\u008b\u00012\u0007\u0010\u008c\u0001\u001a\u00020?2\u0007\u0010\u008d\u0001\u001a\u00020?2\u0007\u0010\u008e\u0001\u001a\u00020?H&J\u001e\u0010\u00a8\u0001\u001a\u0005\u0018\u00010\u00a9\u00012\u0007\u0010\u00aa\u0001\u001a\u00020?2\u0007\u0010\u00a2\u0001\u001a\u00020\u0007H&J%\u0010\u00ab\u0001\u001a\u00030\u008b\u00012\u0007\u0010\u008c\u0001\u001a\u00020?2\u0007\u0010\u008d\u0001\u001a\u00020?2\u0007\u0010\u008e\u0001\u001a\u00020?H&J5\u0010\u00ac\u0001\u001a\u00030\u008b\u00012\u0007\u0010\u00ad\u0001\u001a\u00020?2\u0007\u0010\u00ae\u0001\u001a\u00020?2\u0007\u0010\u00af\u0001\u001a\u00020?2\u0006\u0010w\u001a\u00020\u00072\u0006\u0010t\u001a\u00020\u0007H&J\"\u0010\u00b0\u0001\u001a\u00030\u008b\u00012\u0006\u0010^\u001a\u00020?2\u0006\u0010`\u001a\u00020?2\u0006\u0010c\u001a\u00020?H&R\u0014\u0010\u0002\u001a\u00020\u00038gX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u0004\u0018\u00010\u000bX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0018\u0010\u000e\u001a\u00020\u0007X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\t\"\u0004\b\u0010\u0010\u0011R\u0018\u0010\u0012\u001a\u00020\u0007X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0013\u0010\t\"\u0004\b\u0014\u0010\u0011R\u0014\u0010\u0015\u001a\u00020\u00038gX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0005R\u0018\u0010\u0017\u001a\u00020\u0018X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\u0019\u0010\u001a\"\u0004\b\u001b\u0010\u001cR\u0012\u0010\u001d\u001a\u00020\u001eX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001f\u0010 R\u0012\u0010!\u001a\u00020\u0007X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b\"\u0010\tR\u0018\u0010#\u001a\u00020\u0007X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b$\u0010\t\"\u0004\b%\u0010\u0011R\u0012\u0010&\u001a\u00020\u0007X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b'\u0010\tR\u0012\u0010(\u001a\u00020)X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b*\u0010+R\u0012\u0010,\u001a\u00020\u001eX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b-\u0010 R\u0012\u0010.\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b/\u0010\u0005R\u0014\u00100\u001a\u00020\u00038gX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b1\u0010\u0005R\u0018\u00102\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b2\u0010\u0005\"\u0004\b3\u00104R\u0012\u00105\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b5\u0010\u0005R\u0012\u00106\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b6\u0010\u0005R\u0018\u00107\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b7\u0010\u0005\"\u0004\b8\u00104R\u0012\u00109\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b9\u0010\u0005R\u0012\u0010:\u001a\u00020\u0003X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b:\u0010\u0005R\u0018\u0010;\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b;\u0010\u0005\"\u0004\b<\u00104R\u0014\u0010=\u001a\u00020\u00038gX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b=\u0010\u0005R\u0012\u0010>\u001a\u00020?X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b@\u0010AR\u0012\u0010B\u001a\u00020?X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bC\u0010AR\u0012\u0010D\u001a\u00020?X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bE\u0010AR\u0014\u0010F\u001a\u0004\u0018\u00010GX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bH\u0010IR\u0018\u0010J\u001a\u00020?X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\bK\u0010A\"\u0004\bL\u0010MR\u0018\u0010N\u001a\u00020?X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\bO\u0010A\"\u0004\bP\u0010MR\u0018\u0010Q\u001a\u00020?X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\bR\u0010A\"\u0004\bS\u0010MR\u0014\u0010T\u001a\u0004\u0018\u00010UX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bV\u0010WR\u0018\u0010X\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\bY\u0010\u0005\"\u0004\bZ\u00104R\u0018\u0010[\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b\\\u0010\u0005\"\u0004\b]\u00104R\u0012\u0010^\u001a\u00020?X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b_\u0010AR\u0018\u0010`\u001a\u00020?X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\ba\u0010A\"\u0004\bb\u0010MR\u0012\u0010c\u001a\u00020?X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bd\u0010AR\u0012\u0010e\u001a\u00020fX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bg\u0010hR\u0012\u0010i\u001a\u00020GX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bj\u0010IR\u0012\u0010k\u001a\u00020?X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bl\u0010AR\u0012\u0010m\u001a\u00020?X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bn\u0010AR\u0012\u0010o\u001a\u00020?X\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\bp\u0010AR\u0014\u0010q\u001a\u0004\u0018\u00010\ufffd\ufffdX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\br\u0010sR\u0018\u0010t\u001a\u00020\u0007X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\bu\u0010\t\"\u0004\bv\u0010\u0011R\u0018\u0010w\u001a\u00020\u0007X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\bx\u0010\t\"\u0004\by\u0010\u0011R\u0014\u0010z\u001a\u00020\u00038gX\u00a6\u0004\u00a2\u0006\u0006\u001a\u0004\b{\u0010\u0005R\u0018\u0010|\u001a\u00020\u0003X\u00a6\u000e\u00a2\u0006\f\u001a\u0004\b}\u0010\u0005\"\u0004\b~\u00104R\u001a\u0010\u007f\u001a\u00020\u0007X\u00a6\u000e\u00a2\u0006\u000e\u001a\u0005\b\u0080\u0001\u0010\t\"\u0005\b\u0081\u0001\u0010\u0011R\u0014\u0010\u0082\u0001\u001a\u00020\u001eX\u00a6\u0004\u00a2\u0006\u0007\u001a\u0005\b\u0083\u0001\u0010 R\u0016\u0010\u0084\u0001\u001a\u00030\u0085\u0001X\u00a6\u0004\u00a2\u0006\b\u001a\u0006\b\u0086\u0001\u0010\u0087\u0001R\u0014\u0010\u0088\u0001\u001a\u00020\u0007X\u00a6\u0004\u00a2\u0006\u0007\u001a\u0005\b\u0089\u0001\u0010\t\u00a8\u0006\u00b1\u0001"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "", "burning", "", "isBurning", "()Z", "collisionBorderSize", "", "getCollisionBorderSize", "()F", "displayName", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "getDisplayName", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "distanceWalkedModified", "getDistanceWalkedModified", "setDistanceWalkedModified", "(F)V", "distanceWalkedOnStepModified", "getDistanceWalkedOnStepModified", "setDistanceWalkedOnStepModified", "entityAlive", "isEntityAlive", "entityBoundingBox", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "getEntityBoundingBox", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "setEntityBoundingBox", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;)V", "entityId", "", "getEntityId", "()I", "eyeHeight", "getEyeHeight", "fallDistance", "getFallDistance", "setFallDistance", "height", "getHeight", "horizontalFacing", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "getHorizontalFacing", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "hurtResistantTime", "getHurtResistantTime", "ignoreFrustumCheck", "getIgnoreFrustumCheck", "invisible", "isInvisible", "isAirBorne", "setAirBorne", "(Z)V", "isCollidedHorizontally", "isCollidedVertically", "isDead", "setDead", "isInLava", "isInWater", "isInWeb", "setInWeb", "isRiding", "lastTickPosX", "", "getLastTickPosX", "()D", "lastTickPosY", "getLastTickPosY", "lastTickPosZ", "getLastTickPosZ", "lookVec", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "getLookVec", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "motionX", "getMotionX", "setMotionX", "(D)V", "motionY", "getMotionY", "setMotionY", "motionZ", "getMotionZ", "setMotionZ", "name", "", "getName", "()Ljava/lang/String;", "noClip", "getNoClip", "setNoClip", "onGround", "getOnGround", "setOnGround", "posX", "getPosX", "posY", "getPosY", "setPosY", "posZ", "getPosZ", "position", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getPosition", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "positionVector", "getPositionVector", "prevPosX", "getPrevPosX", "prevPosY", "getPrevPosY", "prevPosZ", "getPrevPosZ", "ridingEntity", "getRidingEntity", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "rotationPitch", "getRotationPitch", "setRotationPitch", "rotationYaw", "getRotationYaw", "setRotationYaw", "sneaking", "isSneaking", "sprinting", "getSprinting", "setSprinting", "stepHeight", "getStepHeight", "setStepHeight", "ticksExisted", "getTicksExisted", "uniqueID", "Ljava/util/UUID;", "getUniqueID", "()Ljava/util/UUID;", "width", "getWidth", "addVelocity", "", "x", "y", "z", "asEntityLivingBase", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "asEntityPlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "asEntityTNTPrimed", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityTNTPrimed;", "canBeCollidedWith", "canRiderInteract", "copyLocationAndAnglesFrom", "player", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "getDistance", "getDistanceSq", "blockPos", "getDistanceSqToCenter", "getDistanceSqToEntity", "it", "getDistanceToEntity", "getLook", "partialTicks", "getPositionEyes", "isInsideOfMaterial", "material", "Lnet/ccbluex/liquidbounce/api/minecraft/block/material/IMaterial;", "moveEntity", "rayTrace", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", AsmConstants.CODERANGE, "setPosition", "setPositionAndRotation", "oldX", "oldY", "oldZ", "setPositionAndUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity.class */
public interface IEntity {
    float getDistanceWalkedOnStepModified();

    void setDistanceWalkedOnStepModified(float f);

    float getDistanceWalkedModified();

    void setDistanceWalkedModified(float f);

    @JvmName(name = "isSneaking")
    boolean isSneaking();

    float getStepHeight();

    void setStepHeight(float f);

    @NotNull
    IEnumFacing getHorizontalFacing();

    @Nullable
    WVec3 getLookVec();

    boolean isDead();

    void setDead(boolean z);

    boolean isCollidedVertically();

    boolean isCollidedHorizontally();

    boolean isAirBorne();

    void setAirBorne(boolean z);

    int getHurtResistantTime();

    boolean getNoClip();

    void setNoClip(boolean z);

    boolean getSprinting();

    void setSprinting(boolean z);

    @NotNull
    WVec3 getPositionVector();

    @JvmName(name = "isRiding")
    boolean isRiding();

    @NotNull
    WBlockPos getPosition();

    @JvmName(name = "isBurning")
    boolean isBurning();

    float getFallDistance();

    void setFallDistance(float f);

    boolean isInWater();

    boolean isInWeb();

    void setInWeb(boolean z);

    boolean isInLava();

    float getWidth();

    float getHeight();

    boolean getOnGround();

    void setOnGround(boolean z);

    @Nullable
    IEntity getRidingEntity();

    float getCollisionBorderSize();

    double getMotionX();

    void setMotionX(double d);

    double getMotionY();

    void setMotionY(double d);

    double getMotionZ();

    void setMotionZ(double d);

    float getEyeHeight();

    @NotNull
    IAxisAlignedBB getEntityBoundingBox();

    void setEntityBoundingBox(@NotNull IAxisAlignedBB iAxisAlignedBB);

    double getPosX();

    double getPosY();

    void setPosY(double d);

    double getPosZ();

    double getLastTickPosX();

    double getLastTickPosY();

    double getLastTickPosZ();

    double getPrevPosX();

    double getPrevPosY();

    double getPrevPosZ();

    float getRotationYaw();

    void setRotationYaw(float f);

    float getRotationPitch();

    void setRotationPitch(float f);

    int getEntityId();

    @Nullable
    IIChatComponent getDisplayName();

    @NotNull
    UUID getUniqueID();

    @Nullable
    String getName();

    boolean getIgnoreFrustumCheck();

    int getTicksExisted();

    @JvmName(name = "isEntityAlive")
    boolean isEntityAlive();

    @JvmName(name = "isInvisible")
    boolean isInvisible();

    @NotNull
    WVec3 getPositionEyes(float f);

    boolean canBeCollidedWith();

    boolean canRiderInteract();

    void moveEntity(double d, double d2, double d3);

    float getDistanceToEntity(@NotNull IEntity iEntity);

    double getDistanceSqToEntity(@NotNull IEntity iEntity);

    void addVelocity(double d, double d2, double d3);

    @NotNull
    IEntityPlayer asEntityPlayer();

    @NotNull
    IEntityLivingBase asEntityLivingBase();

    @NotNull
    IEntityTNTPrimed asEntityTNTPrimed();

    double getDistance(double d, double d2, double d3);

    void setPosition(double d, double d2, double d3);

    double getDistanceSq(@NotNull WBlockPos wBlockPos);

    void setPositionAndUpdate(double d, double d2, double d3);

    @Nullable
    IMovingObjectPosition rayTrace(double d, float f);

    @NotNull
    WVec3 getLook(float f);

    double getDistanceSqToCenter(@NotNull WBlockPos wBlockPos);

    boolean isInsideOfMaterial(@NotNull IMaterial iMaterial);

    void copyLocationAndAnglesFrom(@NotNull IEntityPlayerSP iEntityPlayerSP);

    void setPositionAndRotation(double d, double d2, double d3, float f, float f2);
}
