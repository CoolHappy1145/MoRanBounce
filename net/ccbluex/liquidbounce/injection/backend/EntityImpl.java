package net.ccbluex.liquidbounce.injection.backend;

import java.util.UUID;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.block.material.IMaterial;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityTNTPrimed;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.player.IEntityPlayer;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IEnumFacing;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u00a6\u0001\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u0006\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u001e\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\b\u0016\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0005J%\u0010\u0092\u0001\u001a\u00030\u0093\u00012\u0007\u0010\u0094\u0001\u001a\u00020D2\u0007\u0010\u0095\u0001\u001a\u00020D2\u0007\u0010\u0096\u0001\u001a\u00020DH\u0016J\n\u0010\u0097\u0001\u001a\u00030\u0098\u0001H\u0016J\n\u0010\u0099\u0001\u001a\u00030\u009a\u0001H\u0016J\n\u0010\u009b\u0001\u001a\u00030\u009c\u0001H\u0016J\t\u0010\u009d\u0001\u001a\u00020\u0007H\u0016J\t\u0010\u009e\u0001\u001a\u00020\u0007H\u0016J\u0014\u0010\u009f\u0001\u001a\u00030\u0093\u00012\b\u0010\u00a0\u0001\u001a\u00030\u00a1\u0001H\u0016J\u0016\u0010\u00a2\u0001\u001a\u00020\u00072\n\u0010\u00a3\u0001\u001a\u0005\u0018\u00010\u00a4\u0001H\u0096\u0002J$\u0010\u00a5\u0001\u001a\u00020D2\u0007\u0010\u0094\u0001\u001a\u00020D2\u0007\u0010\u0095\u0001\u001a\u00020D2\u0007\u0010\u0096\u0001\u001a\u00020DH\u0016J\u0012\u0010\u00a6\u0001\u001a\u00020D2\u0007\u0010\u00a7\u0001\u001a\u00020kH\u0016J\u0012\u0010\u00a8\u0001\u001a\u00020D2\u0007\u0010\u00a7\u0001\u001a\u00020kH\u0016J\u0012\u0010\u00a9\u0001\u001a\u00020D2\u0007\u0010\u00aa\u0001\u001a\u00020\u0003H\u0016J\u0012\u0010\u00ab\u0001\u001a\u00020\u000b2\u0007\u0010\u00aa\u0001\u001a\u00020\u0003H\u0016J\u0012\u0010\u00ac\u0001\u001a\u00020L2\u0007\u0010\u00ad\u0001\u001a\u00020\u000bH\u0016J\u0012\u0010\u00ae\u0001\u001a\u00020L2\u0007\u0010\u00ad\u0001\u001a\u00020\u000bH\u0016J\u0013\u0010\u00af\u0001\u001a\u00020\u00072\b\u0010\u00b0\u0001\u001a\u00030\u00b1\u0001H\u0016J%\u0010\u00b2\u0001\u001a\u00030\u0093\u00012\u0007\u0010\u0094\u0001\u001a\u00020D2\u0007\u0010\u0095\u0001\u001a\u00020D2\u0007\u0010\u0096\u0001\u001a\u00020DH\u0016J\u001e\u0010\u00b3\u0001\u001a\u0005\u0018\u00010\u00b4\u00012\u0007\u0010\u00b5\u0001\u001a\u00020D2\u0007\u0010\u00ad\u0001\u001a\u00020\u000bH\u0016J%\u0010\u00b6\u0001\u001a\u00030\u0093\u00012\u0007\u0010\u0094\u0001\u001a\u00020D2\u0007\u0010\u0095\u0001\u001a\u00020D2\u0007\u0010\u0096\u0001\u001a\u00020DH\u0016J5\u0010\u00b7\u0001\u001a\u00030\u0093\u00012\u0007\u0010\u00b8\u0001\u001a\u00020D2\u0007\u0010\u00b9\u0001\u001a\u00020D2\u0007\u0010\u00ba\u0001\u001a\u00020D2\u0006\u0010|\u001a\u00020\u000b2\u0006\u0010y\u001a\u00020\u000bH\u0016J\"\u0010\u00bb\u0001\u001a\u00030\u0093\u00012\u0006\u0010c\u001a\u00020D2\u0006\u0010e\u001a\u00020D2\u0006\u0010h\u001a\u00020DH\u0016R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011R$\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0014\u0010\r\"\u0004\b\u0015\u0010\u0016R$\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0018\u0010\r\"\u0004\b\u0019\u0010\u0016R\u0014\u0010\u001a\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u001b\u0010\tR$\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u0012\u001a\u00020\u001c8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u0014\u0010\"\u001a\u00020#8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b$\u0010%R\u0014\u0010&\u001a\u00020\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b'\u0010\rR$\u0010(\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b)\u0010\r\"\u0004\b*\u0010\u0016R\u0014\u0010+\u001a\u00020\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b,\u0010\rR\u0014\u0010-\u001a\u00020.8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b/\u00100R\u0014\u00101\u001a\u00020#8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b2\u0010%R\u0014\u00103\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b4\u0010\tR\u0014\u00105\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b6\u0010\tR$\u00107\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00078V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b7\u0010\t\"\u0004\b8\u00109R\u0014\u0010:\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b:\u0010\tR\u0014\u0010;\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b;\u0010\tR$\u0010<\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00078V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b<\u0010\t\"\u0004\b=\u00109R\u0014\u0010>\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b>\u0010\tR\u0014\u0010?\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b?\u0010\tR$\u0010@\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00078V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b@\u0010\t\"\u0004\bA\u00109R\u0014\u0010B\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bB\u0010\tR\u0014\u0010C\u001a\u00020D8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bE\u0010FR\u0014\u0010G\u001a\u00020D8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bH\u0010FR\u0014\u0010I\u001a\u00020D8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bJ\u0010FR\u0016\u0010K\u001a\u0004\u0018\u00010L8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bM\u0010NR$\u0010O\u001a\u00020D2\u0006\u0010\u0012\u001a\u00020D8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\bP\u0010F\"\u0004\bQ\u0010RR$\u0010S\u001a\u00020D2\u0006\u0010\u0012\u001a\u00020D8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\bT\u0010F\"\u0004\bU\u0010RR$\u0010V\u001a\u00020D2\u0006\u0010\u0012\u001a\u00020D8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\bW\u0010F\"\u0004\bX\u0010RR\u0016\u0010Y\u001a\u0004\u0018\u00010Z8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b[\u0010\\R$\u0010]\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00078V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b^\u0010\t\"\u0004\b_\u00109R$\u0010`\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00078V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\ba\u0010\t\"\u0004\bb\u00109R\u0014\u0010c\u001a\u00020D8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bd\u0010FR$\u0010e\u001a\u00020D2\u0006\u0010\u0012\u001a\u00020D8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\bf\u0010F\"\u0004\bg\u0010RR\u0014\u0010h\u001a\u00020D8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bi\u0010FR\u0014\u0010j\u001a\u00020k8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bl\u0010mR\u0014\u0010n\u001a\u00020L8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bo\u0010NR\u0014\u0010p\u001a\u00020D8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bq\u0010FR\u0014\u0010r\u001a\u00020D8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bs\u0010FR\u0014\u0010t\u001a\u00020D8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bu\u0010FR\u0016\u0010v\u001a\u0004\u0018\u00010\u00038VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\bw\u0010xR$\u0010y\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\bz\u0010\r\"\u0004\b{\u0010\u0016R$\u0010|\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b}\u0010\r\"\u0004\b~\u0010\u0016R\u0015\u0010\u007f\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0007\u001a\u0005\b\u0080\u0001\u0010\tR'\u0010\u0081\u0001\u001a\u00020\u00072\u0006\u0010\u0012\u001a\u00020\u00078V@VX\u0096\u000e\u00a2\u0006\u000e\u001a\u0005\b\u0082\u0001\u0010\t\"\u0005\b\u0083\u0001\u00109R'\u0010\u0084\u0001\u001a\u00020\u000b2\u0006\u0010\u0012\u001a\u00020\u000b8V@VX\u0096\u000e\u00a2\u0006\u000e\u001a\u0005\b\u0085\u0001\u0010\r\"\u0005\b\u0086\u0001\u0010\u0016R\u0016\u0010\u0087\u0001\u001a\u00020#8VX\u0096\u0004\u00a2\u0006\u0007\u001a\u0005\b\u0088\u0001\u0010%R\u0018\u0010\u0089\u0001\u001a\u00030\u008a\u00018VX\u0096\u0004\u00a2\u0006\b\u001a\u0006\b\u008b\u0001\u0010\u008c\u0001R\u0016\u0010\u008d\u0001\u001a\u00020\u000b8VX\u0096\u0004\u00a2\u0006\u0007\u001a\u0005\b\u008e\u0001\u0010\rR\u0016\u0010\u0004\u001a\u00028\ufffd\ufffd\u00a2\u0006\r\n\u0003\u0010\u0091\u0001\u001a\u0006\b\u008f\u0001\u0010\u0090\u0001\u00a8\u0006\u00bc\u0001"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/EntityImpl;", "T", "Lnet/minecraft/entity/Entity;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "wrapped", "(Lnet/minecraft/entity/Entity;)V", "burning", "", "getBurning", "()Z", "collisionBorderSize", "", "getCollisionBorderSize", "()F", "displayName", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "getDisplayName", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", PropertyDescriptor.VALUE, "distanceWalkedModified", "getDistanceWalkedModified", "setDistanceWalkedModified", "(F)V", "distanceWalkedOnStepModified", "getDistanceWalkedOnStepModified", "setDistanceWalkedOnStepModified", "entityAlive", "getEntityAlive", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "entityBoundingBox", "getEntityBoundingBox", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "setEntityBoundingBox", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;)V", "entityId", "", "getEntityId", "()I", "eyeHeight", "getEyeHeight", "fallDistance", "getFallDistance", "setFallDistance", "height", "getHeight", "horizontalFacing", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "getHorizontalFacing", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IEnumFacing;", "hurtResistantTime", "getHurtResistantTime", "ignoreFrustumCheck", "getIgnoreFrustumCheck", "invisible", "getInvisible", "isAirBorne", "setAirBorne", "(Z)V", "isCollidedHorizontally", "isCollidedVertically", "isDead", "setDead", "isInLava", "isInWater", "isInWeb", "setInWeb", "isRiding", "lastTickPosX", "", "getLastTickPosX", "()D", "lastTickPosY", "getLastTickPosY", "lastTickPosZ", "getLastTickPosZ", "lookVec", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "getLookVec", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", "motionX", "getMotionX", "setMotionX", "(D)V", "motionY", "getMotionY", "setMotionY", "motionZ", "getMotionZ", "setMotionZ", "name", "", "getName", "()Ljava/lang/String;", "noClip", "getNoClip", "setNoClip", "onGround", "getOnGround", "setOnGround", "posX", "getPosX", "posY", "getPosY", "setPosY", "posZ", "getPosZ", "position", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getPosition", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "positionVector", "getPositionVector", "prevPosX", "getPrevPosX", "prevPosY", "getPrevPosY", "prevPosZ", "getPrevPosZ", "ridingEntity", "getRidingEntity", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "rotationPitch", "getRotationPitch", "setRotationPitch", "rotationYaw", "getRotationYaw", "setRotationYaw", "sneaking", "getSneaking", "sprinting", "getSprinting", "setSprinting", "stepHeight", "getStepHeight", "setStepHeight", "ticksExisted", "getTicksExisted", "uniqueID", "Ljava/util/UUID;", "getUniqueID", "()Ljava/util/UUID;", "width", "getWidth", "getWrapped", "()Lnet/minecraft/entity/Entity;", "Lnet/minecraft/entity/Entity;", "addVelocity", "", "x", "y", "z", "asEntityLivingBase", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "asEntityPlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/player/IEntityPlayer;", "asEntityTNTPrimed", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityTNTPrimed;", "canBeCollidedWith", "canRiderInteract", "copyLocationAndAnglesFrom", "player", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "equals", "other", "", "getDistance", "getDistanceSq", "blockPos", "getDistanceSqToCenter", "getDistanceSqToEntity", "it", "getDistanceToEntity", "getLook", "partialTicks", "getPositionEyes", "isInsideOfMaterial", "material", "Lnet/ccbluex/liquidbounce/api/minecraft/block/material/IMaterial;", "moveEntity", "rayTrace", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", AsmConstants.CODERANGE, "setPosition", "setPositionAndRotation", "oldX", "oldY", "oldZ", "setPositionAndUpdate", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/EntityImpl.class */
public class EntityImpl implements IEntity {

    @NotNull
    private final Entity wrapped;

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean isSneaking() {
        return getSneaking();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean isBurning() {
        return getBurning();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean isEntityAlive() {
        return getEntityAlive();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean isInvisible() {
        return getInvisible();
    }

    @NotNull
    public final Entity getWrapped() {
        return this.wrapped;
    }

    public EntityImpl(@NotNull Entity wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public float getDistanceWalkedOnStepModified() {
        return this.wrapped.field_82151_R;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setDistanceWalkedOnStepModified(float f) {
        this.wrapped.field_82151_R = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public float getDistanceWalkedModified() {
        return this.wrapped.field_70140_Q;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setDistanceWalkedModified(float f) {
        this.wrapped.field_70140_Q = f;
    }

    public boolean getSneaking() {
        return this.wrapped.func_70093_af();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public float getStepHeight() {
        return this.wrapped.field_70138_W;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setStepHeight(float f) {
        this.wrapped.field_70138_W = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    @NotNull
    public IEnumFacing getHorizontalFacing() {
        EnumFacing enumFacingFunc_174811_aO = this.wrapped.func_174811_aO();
        Intrinsics.checkExpressionValueIsNotNull(enumFacingFunc_174811_aO, "wrapped.horizontalFacing");
        return new EnumFacingImpl(enumFacingFunc_174811_aO);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    @Nullable
    public WVec3 getLookVec() {
        Vec3d vec3dFunc_70040_Z = this.wrapped.func_70040_Z();
        Intrinsics.checkExpressionValueIsNotNull(vec3dFunc_70040_Z, "wrapped.lookVec");
        return new WVec3(vec3dFunc_70040_Z.field_72450_a, vec3dFunc_70040_Z.field_72448_b, vec3dFunc_70040_Z.field_72449_c);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean isDead() {
        return this.wrapped.field_70128_L;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setDead(boolean z) {
        this.wrapped.field_70128_L = z;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean isCollidedVertically() {
        return this.wrapped.field_70124_G;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean isCollidedHorizontally() {
        return this.wrapped.field_70123_F;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean isAirBorne() {
        return this.wrapped.field_70160_al;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setAirBorne(boolean z) {
        this.wrapped.field_70160_al = z;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public int getHurtResistantTime() {
        return this.wrapped.field_70172_ad;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean getNoClip() {
        return this.wrapped.field_70145_X;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setNoClip(boolean z) {
        this.wrapped.field_70145_X = z;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean getSprinting() {
        return this.wrapped.func_70051_ag();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setSprinting(boolean z) {
        this.wrapped.func_70031_b(z);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    @NotNull
    public WVec3 getPositionVector() {
        Vec3d vec3dFunc_174791_d = this.wrapped.func_174791_d();
        Intrinsics.checkExpressionValueIsNotNull(vec3dFunc_174791_d, "wrapped.positionVector");
        return new WVec3(vec3dFunc_174791_d.field_72450_a, vec3dFunc_174791_d.field_72448_b, vec3dFunc_174791_d.field_72449_c);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean isRiding() {
        return this.wrapped.func_184218_aH();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    @NotNull
    public WBlockPos getPosition() {
        BlockPos blockPosFunc_180425_c = this.wrapped.func_180425_c();
        Intrinsics.checkExpressionValueIsNotNull(blockPosFunc_180425_c, "wrapped.position");
        return new WBlockPos(blockPosFunc_180425_c.func_177958_n(), blockPosFunc_180425_c.func_177956_o(), blockPosFunc_180425_c.func_177952_p());
    }

    public boolean getBurning() {
        return this.wrapped.func_70027_ad();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public float getFallDistance() {
        return this.wrapped.field_70143_R;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setFallDistance(float f) {
        this.wrapped.field_70143_R = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean isInWater() {
        return this.wrapped.func_70090_H();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean isInWeb() {
        return this.wrapped.field_70134_J;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setInWeb(boolean z) {
        this.wrapped.field_70134_J = z;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean isInLava() {
        return this.wrapped.func_180799_ab();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public float getWidth() {
        return this.wrapped.field_70130_N;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public float getHeight() {
        return this.wrapped.field_70131_O;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean getOnGround() {
        return this.wrapped.field_70122_E;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setOnGround(boolean z) {
        this.wrapped.field_70122_E = z;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    @Nullable
    public IEntity getRidingEntity() {
        Entity entity = this.wrapped.field_184239_as;
        if (entity != null) {
            return new EntityImpl(entity);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public float getCollisionBorderSize() {
        return this.wrapped.func_70111_Y();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public double getMotionX() {
        return this.wrapped.field_70159_w;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setMotionX(double d) {
        this.wrapped.field_70159_w = d;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public double getMotionY() {
        return this.wrapped.field_70181_x;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setMotionY(double d) {
        this.wrapped.field_70181_x = d;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public double getMotionZ() {
        return this.wrapped.field_70179_y;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setMotionZ(double d) {
        this.wrapped.field_70179_y = d;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public float getEyeHeight() {
        return this.wrapped.func_70047_e();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    @NotNull
    public IAxisAlignedBB getEntityBoundingBox() {
        AxisAlignedBB axisAlignedBBFunc_174813_aQ = this.wrapped.func_174813_aQ();
        Intrinsics.checkExpressionValueIsNotNull(axisAlignedBBFunc_174813_aQ, "wrapped.entityBoundingBox");
        return new AxisAlignedBBImpl(axisAlignedBBFunc_174813_aQ);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setEntityBoundingBox(@NotNull IAxisAlignedBB value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.wrapped.func_174826_a(((AxisAlignedBBImpl) value).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public double getPosX() {
        return this.wrapped.field_70165_t;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public double getPosY() {
        return this.wrapped.field_70163_u;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setPosY(double d) {
        this.wrapped.field_70163_u = d;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean getIgnoreFrustumCheck() {
        return this.wrapped.field_70158_ak;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public double getPosZ() {
        return this.wrapped.field_70161_v;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public double getLastTickPosX() {
        return this.wrapped.field_70142_S;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public double getLastTickPosY() {
        return this.wrapped.field_70137_T;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public double getLastTickPosZ() {
        return this.wrapped.field_70136_U;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public double getPrevPosX() {
        return this.wrapped.field_70169_q;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public double getPrevPosY() {
        return this.wrapped.field_70167_r;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public double getPrevPosZ() {
        return this.wrapped.field_70166_s;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public float getRotationYaw() {
        return this.wrapped.field_70177_z;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setRotationYaw(float f) {
        this.wrapped.field_70177_z = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public float getRotationPitch() {
        return this.wrapped.field_70127_C;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setRotationPitch(float f) {
        this.wrapped.field_70125_A = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public int getEntityId() {
        return this.wrapped.func_145782_y();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    @Nullable
    public IIChatComponent getDisplayName() {
        ITextComponent iTextComponentFunc_145748_c_ = this.wrapped.func_145748_c_();
        Intrinsics.checkExpressionValueIsNotNull(iTextComponentFunc_145748_c_, "wrapped.displayName");
        return new IChatComponentImpl(iTextComponentFunc_145748_c_);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    @NotNull
    public UUID getUniqueID() {
        UUID uuidFunc_110124_au = this.wrapped.func_110124_au();
        Intrinsics.checkExpressionValueIsNotNull(uuidFunc_110124_au, "wrapped.uniqueID");
        return uuidFunc_110124_au;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    @Nullable
    public String getName() {
        return this.wrapped.func_70005_c_();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public int getTicksExisted() {
        return this.wrapped.field_70173_aa;
    }

    public boolean getEntityAlive() {
        return this.wrapped.func_70089_S();
    }

    public boolean getInvisible() {
        return this.wrapped.func_82150_aj();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    @NotNull
    public WVec3 getPositionEyes(float f) {
        Vec3d vec3dFunc_174824_e = this.wrapped.func_174824_e(f);
        Intrinsics.checkExpressionValueIsNotNull(vec3dFunc_174824_e, "wrapped.getPositionEyes(partialTicks)");
        return new WVec3(vec3dFunc_174824_e.field_72450_a, vec3dFunc_174824_e.field_72448_b, vec3dFunc_174824_e.field_72449_c);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean canBeCollidedWith() {
        return this.wrapped.func_70067_L();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean canRiderInteract() {
        return this.wrapped.canRiderInteract();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void moveEntity(double d, double d2, double d3) {
        this.wrapped.func_70091_d(MoverType.PLAYER, d, d2, d3);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public float getDistanceToEntity(@NotNull IEntity it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        return this.wrapped.func_70032_d(((EntityImpl) it).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public double getDistanceSqToEntity(@NotNull IEntity it) {
        Intrinsics.checkParameterIsNotNull(it, "it");
        return this.wrapped.func_70068_e(((EntityImpl) it).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    @NotNull
    public IEntityPlayer asEntityPlayer() {
        EntityPlayer entityPlayer = this.wrapped;
        if (entityPlayer == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.entity.player.EntityPlayer");
        }
        return new EntityPlayerImpl(entityPlayer);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    @NotNull
    public IEntityLivingBase asEntityLivingBase() {
        EntityLivingBase entityLivingBase = this.wrapped;
        if (entityLivingBase == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.entity.EntityLivingBase");
        }
        return new EntityLivingBaseImpl(entityLivingBase);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    @NotNull
    public IEntityTNTPrimed asEntityTNTPrimed() {
        EntityTNTPrimed entityTNTPrimed = this.wrapped;
        if (entityTNTPrimed == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.entity.item.EntityTNTPrimed");
        }
        return new EntityTNTPrimedImpl(entityTNTPrimed);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public double getDistance(double d, double d2, double d3) {
        return this.wrapped.func_70011_f(d, d2, d3);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setPosition(double d, double d2, double d3) {
        this.wrapped.func_70107_b(d, d2, d3);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public double getDistanceSq(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        return this.wrapped.func_174818_b(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public double getDistanceSqToCenter(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        return this.wrapped.func_174831_c(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setPositionAndUpdate(double d, double d2, double d3) {
        this.wrapped.func_70634_a(d, d2, d3);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    @Nullable
    public IMovingObjectPosition rayTrace(double d, float f) {
        RayTraceResult rayTraceResultFunc_174822_a = this.wrapped.func_174822_a(d, f);
        if (rayTraceResultFunc_174822_a != null) {
            return new MovingObjectPositionImpl(rayTraceResultFunc_174822_a);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    @NotNull
    public WVec3 getLook(float f) {
        Vec3d vec3dFunc_70676_i = this.wrapped.func_70676_i(f);
        Intrinsics.checkExpressionValueIsNotNull(vec3dFunc_70676_i, "wrapped.getLook(partialTicks)");
        return new WVec3(vec3dFunc_70676_i.field_72450_a, vec3dFunc_70676_i.field_72448_b, vec3dFunc_70676_i.field_72449_c);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void addVelocity(double d, double d2, double d3) {
        this.wrapped.func_70024_g(d, d2, d3);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public boolean isInsideOfMaterial(@NotNull IMaterial material) {
        Intrinsics.checkParameterIsNotNull(material, "material");
        return this.wrapped.func_70055_a(((MaterialImpl) material).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void copyLocationAndAnglesFrom(@NotNull IEntityPlayerSP player) {
        Intrinsics.checkParameterIsNotNull(player, "player");
        this.wrapped.func_82149_j((EntityPlayerSP) ((EntityPlayerSPImpl) player).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity
    public void setPositionAndRotation(double d, double d2, double d3, float f, float f2) {
        this.wrapped.func_70080_a(d, d2, d3, f, f2);
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof EntityImpl) && Intrinsics.areEqual(((EntityImpl) obj).wrapped, this.wrapped);
    }
}
