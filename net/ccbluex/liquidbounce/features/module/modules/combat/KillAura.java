package net.ccbluex.liquidbounce.features.module.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import kotlin.text.Typography;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.IExtractedFunctions;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.enums.WEnumHand;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.client.particle.IParticleManager;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayerDigging;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketUseEntity;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorldSettings;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EntityMovementEvent;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.player.Blink;
import net.ccbluex.liquidbounce.features.module.modules.render.FreeCam;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.RaycastUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.VecRotation;
import net.ccbluex.liquidbounce.utils.extensions.PlayerExtensionKt;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0092\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\t\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0010\u0007\n\u0002\b\u000e\n\u0002\u0010!\n\u0002\b\u0012\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\n\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010d\u001a\u00020e2\u0006\u0010f\u001a\u00020#H\u0002J\u0010\u0010g\u001a\u0002092\u0006\u0010f\u001a\u00020hH\u0002J\u0010\u0010i\u001a\u00020\u000e2\u0006\u0010f\u001a\u00020#H\u0002J\b\u0010j\u001a\u00020eH\u0016J\b\u0010k\u001a\u00020eH\u0016J\u0010\u0010l\u001a\u00020e2\u0006\u0010m\u001a\u00020nH\u0007J\u0010\u0010o\u001a\u00020e2\u0006\u0010m\u001a\u00020pH\u0007J\u0010\u0010q\u001a\u00020e2\u0006\u0010m\u001a\u00020rH\u0007J\u0010\u0010s\u001a\u00020e2\u0006\u0010m\u001a\u00020tH\u0007J\u0010\u0010u\u001a\u00020e2\u0006\u0010m\u001a\u00020vH\u0007J\b\u0010w\u001a\u00020eH\u0002J\u0018\u0010x\u001a\u00020e2\u0006\u0010y\u001a\u00020h2\u0006\u0010z\u001a\u00020\u000eH\u0002J\b\u0010{\u001a\u00020eH\u0002J\u0006\u0010|\u001a\u00020eJ\b\u0010}\u001a\u00020eH\u0002J\u0010\u0010~\u001a\u00020\u000e2\u0006\u0010f\u001a\u00020hH\u0002J\b\u0010\u007f\u001a\u00020eH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u0015\u0010\u0013\u001a\u00020\u000e8\u00c2\u0002X\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\u0010R\u0015\u0010\u0015\u001a\u00020\u000e8\u00c2\u0002X\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0016\u0010\u0010R\u000e\u0010\u0017\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0018\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0019\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001a\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001b\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001d\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010 \u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010!\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\"\u001a\u0004\u0018\u00010#X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010$\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010%\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010&\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010'\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010(\u001a\u00020\u000eX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010)\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010*\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010+\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010,\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001c\u0010-\u001a\u0004\u0018\u00010#X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b.\u0010/\"\u0004\b0\u00101R\u000e\u00102\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00103\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00104\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00105\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00106\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00107\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u00108\u001a\u0002098BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b:\u0010;R\u000e\u0010<\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010=\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010>\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010?\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010@\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010A\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010B\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\bC\u0010DR\u000e\u0010E\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010F\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010G\u001a\b\u0012\u0004\u0012\u00020\u001f0HX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010I\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010J\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010K\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010L\u001a\u00020\u001c\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\bM\u0010NR\u000e\u0010O\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010P\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010Q\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010R\u001a\u00020\n\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\bS\u0010TR\u000e\u0010U\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010V\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010W\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010X\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010Y\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010Z\u001a\u00020[8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\\\u0010]R\u001c\u0010^\u001a\u0004\u0018\u00010#X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b_\u0010/\"\u0004\b`\u00101R\u0011\u0010a\u001a\u00020\n\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\bb\u0010TR\u000e\u0010c\u001a\u00020\u001cX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0080\u0001"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/KillAura;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aacValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "attackDelay", "", "attackTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "autoBlockValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "blockRate", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "blockingStatus", "", "getBlockingStatus", "()Z", "setBlockingStatus", "(Z)V", "canBlock", "getCanBlock", "cancelRun", "getCancelRun", "circleAlphaValue", "circleBlueValue", "circleGreenValue", "circleRedValue", "circleThicknessValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "circleValue", "clicks", "", "containerOpen", "cooldownValue", "currentTarget", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "failRateValue", "fakeSharpValue", "fakeSwingValue", "fovValue", "hitable", "hiteffect", "hurtTimeValue", "interactAutoBlockValue", "keepSprintValue", "lastTarget", "getLastTarget", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "setLastTarget", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;)V", "lightingSoundValue", "limitedMultiTargetsValue", "livingRaycastValue", "markValue", "maxCPS", "maxPredictSize", "maxRange", "", "getMaxRange", "()F", "maxTurnSpeed", "minCPS", "minPredictSize", "minTurnSpeed", "noInventoryAttackValue", "noInventoryDelayValue", "norangeairban", "getNorangeairban", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "outborderValue", "predictValue", "prevTargetEntities", "", "priorityValue", "randomCenterValue", "rangeSprintReducementValue", "rangeValue", "getRangeValue", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "raycastIgnoredValue", "raycastValue", "rotationStrafeValue", "rotations", "getRotations", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "silentRotationValue", "stopkeepsprintinnoground", "swingValue", "switchDelayValue", "switchTimer", "tag", "", "getTag", "()Ljava/lang/String;", "target", "getTarget", "setTarget", "targetModeValue", "getTargetModeValue", "throughWallsRangeValue", "attackEntity", "", "entity", "getRange", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "isAlive", "onDisable", "onEnable", "onEntityMove", "event", "Lnet/ccbluex/liquidbounce/event/EntityMovementEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onStrafe", "Lnet/ccbluex/liquidbounce/event/StrafeEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "runAttack", "startBlocking", "interactEntity", "interact", "stopBlocking", "update", "updateHitable", "updateRotations", "updateTarget", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "KillAura", description = "Automatically attacks targets around you.", category = ModuleCategory.COMBAT, keyBind = OPCode.CCLASS_NOT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/KillAura.class */
public final class KillAura extends Module {
    private final IntegerValue maxCPS;
    private final IntegerValue minCPS;
    private final FloatValue maxTurnSpeed;
    private final FloatValue minTurnSpeed;
    private final FloatValue maxPredictSize;
    private final FloatValue minPredictSize;

    @Nullable
    private IEntityLivingBase target;

    @Nullable
    private IEntityLivingBase lastTarget;
    private IEntityLivingBase currentTarget;
    private boolean hitable;
    private long attackDelay;
    private int clicks;
    private boolean blockingStatus;
    private final IntegerValue hurtTimeValue = new IntegerValue("HurtTime", 10, 0, 10);
    private final FloatValue cooldownValue = new FloatValue("Cooldown", 1.0f, 0.0f, 1.0f);
    private final IntegerValue switchDelayValue = new IntegerValue("SwitchDelay", 700, 0, 2000);

    @NotNull
    private final FloatValue rangeValue = new FloatValue("Range", 3.7f, 1.0f, 8.0f);

    @NotNull
    private final BoolValue norangeairban = new BoolValue("NoRangeAirBan", false);
    private final FloatValue throughWallsRangeValue = new FloatValue("ThroughWallsRange", 3.0f, 0.0f, 8.0f);
    private final FloatValue rangeSprintReducementValue = new FloatValue("RangeSprintReducement", 0.0f, 0.0f, 0.4f);
    private final ListValue priorityValue = new ListValue("Priority", new String[]{"Health", "Distance", "Direction", "LivingTime", "Armor"}, "Distance");

    @NotNull
    private final ListValue targetModeValue = new ListValue("TargetMode", new String[]{"Single", "Switch", "Multi"}, "Switch");
    private final BoolValue swingValue = new BoolValue("Swing", true);
    private final BoolValue keepSprintValue = new BoolValue("KeepSprint", true);
    private final BoolValue stopkeepsprintinnoground = new BoolValue("StopKeepSprintInNnGround", false);
    private final ListValue autoBlockValue = new ListValue("AutoBlock", new String[]{"Off", "Packet", "AfterTick", "UseItem"}, "Packet");
    private final BoolValue interactAutoBlockValue = new BoolValue("InteractAutoBlock", true);
    private final IntegerValue blockRate = new IntegerValue("BlockRate", 100, 1, 100);
    private final BoolValue raycastValue = new BoolValue("RayCast", true);
    private final BoolValue raycastIgnoredValue = new BoolValue("RayCastIgnored", false);
    private final BoolValue livingRaycastValue = new BoolValue("LivingRayCast", true);
    private final BoolValue aacValue = new BoolValue("AAC", false);

    @NotNull
    private final ListValue rotations = new ListValue("RotationMode", new String[]{"Vanilla", "BackTrack", "HytRotation"}, "Vanilla");
    private final BoolValue silentRotationValue = new BoolValue("SilentRotation", true);
    private final ListValue rotationStrafeValue = new ListValue("Strafe", new String[]{"Off", "Strict", "Silent"}, "Off");
    private final BoolValue randomCenterValue = new BoolValue("RandomCenter", true);
    private final BoolValue outborderValue = new BoolValue("Outborder", false);
    private final FloatValue fovValue = new FloatValue("FOV", 180.0f, 0.0f, 180.0f);
    private final BoolValue predictValue = new BoolValue("Predict", true);
    private final FloatValue failRateValue = new FloatValue("FailRate", 0.0f, 0.0f, 100.0f);
    private final BoolValue fakeSwingValue = new BoolValue("FakeSwing", true);
    private final BoolValue noInventoryAttackValue = new BoolValue("NoInvAttack", false);
    private final IntegerValue noInventoryDelayValue = new IntegerValue("NoInvDelay", SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 0, SharedScopeCall.SLOW_SCOPE_CALL_THRESHOLD);
    private final IntegerValue limitedMultiTargetsValue = new IntegerValue("LimitedMultiTargets", 0, 0, 50);
    private final ListValue markValue = new ListValue("Mark", new String[]{"Liquid", "FDP", "Block", "Jello", "JelloRed", "Plat", "Red", "Sims", "None"}, "FDP");
    private final ListValue hiteffect = new ListValue("HitEffect", new String[]{"Lightningbolt", "Criticals", "Blood", "Fire", "Water", "Smoke", "Flame", "Heart", "None"}, "None");
    private final BoolValue lightingSoundValue = new BoolValue("lightingSoundValue", false);
    private final BoolValue circleValue = new BoolValue("Circle", true);
    private final IntegerValue circleRedValue = new IntegerValue("CircleRed", 255, 0, 255);
    private final IntegerValue circleGreenValue = new IntegerValue("CircleGreen", 255, 0, 255);
    private final IntegerValue circleBlueValue = new IntegerValue("CircleBlue", 255, 0, 255);
    private final IntegerValue circleAlphaValue = new IntegerValue("CircleAlpha", 255, 0, 255);
    private final FloatValue circleThicknessValue = new FloatValue("CircleThickness", 2.0f, 1.0f, 5.0f);
    private final BoolValue fakeSharpValue = new BoolValue("FakeSharp", true);
    private final List prevTargetEntities = new ArrayList();
    private final MSTimer attackTimer = new MSTimer();
    private final MSTimer switchTimer = new MSTimer();
    private long containerOpen = -1;

    public KillAura() {
        final String str = "MaxCPS";
        final int i = 8;
        final int i2 = 1;
        final int i3 = 20;
        this.maxCPS = new IntegerValue(this, str, i, i2, i3) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$maxCPS$1
            final KillAura this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i4, int i5) {
                int iIntValue = ((Number) this.this$0.minCPS.get()).intValue();
                if (iIntValue > i5) {
                    set((Object) Integer.valueOf(iIntValue));
                }
                this.this$0.attackDelay = TimeUtils.randomClickDelay(((Number) this.this$0.minCPS.get()).intValue(), ((Number) get()).intValue());
            }
        };
        final String str2 = "MinCPS";
        final int i4 = 5;
        final int i5 = 1;
        final int i6 = 20;
        this.minCPS = new IntegerValue(this, str2, i4, i5, i6) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$minCPS$1
            final KillAura this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i7, int i8) {
                int iIntValue = ((Number) this.this$0.maxCPS.get()).intValue();
                if (iIntValue < i8) {
                    set((Object) Integer.valueOf(iIntValue));
                }
                this.this$0.attackDelay = TimeUtils.randomClickDelay(((Number) get()).intValue(), ((Number) this.this$0.maxCPS.get()).intValue());
            }
        };
        final String str3 = "MaxTurnSpeed";
        final float f = 180.0f;
        final float f2 = 0.0f;
        final float f3 = 180.0f;
        this.maxTurnSpeed = new FloatValue(this, str3, f, f2, f3) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$maxTurnSpeed$1
            final KillAura this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).floatValue(), ((Number) obj2).floatValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(float f4, float f5) {
                float fFloatValue = ((Number) this.this$0.minTurnSpeed.get()).floatValue();
                if (fFloatValue > f5) {
                    set((Object) Float.valueOf(fFloatValue));
                }
            }
        };
        final String str4 = "MinTurnSpeed";
        final float f4 = 180.0f;
        final float f5 = 0.0f;
        final float f6 = 180.0f;
        this.minTurnSpeed = new FloatValue(this, str4, f4, f5, f6) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$minTurnSpeed$1
            final KillAura this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).floatValue(), ((Number) obj2).floatValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(float f7, float f8) {
                float fFloatValue = ((Number) this.this$0.maxTurnSpeed.get()).floatValue();
                if (fFloatValue < f8) {
                    set((Object) Float.valueOf(fFloatValue));
                }
            }
        };
        final String str5 = "MaxPredictSize";
        final float f7 = 1.0f;
        final float f8 = 0.1f;
        final float f9 = 5.0f;
        this.maxPredictSize = new FloatValue(this, str5, f7, f8, f9) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$maxPredictSize$1
            final KillAura this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).floatValue(), ((Number) obj2).floatValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(float f10, float f11) {
                float fFloatValue = ((Number) this.this$0.minPredictSize.get()).floatValue();
                if (fFloatValue > f11) {
                    set((Object) Float.valueOf(fFloatValue));
                }
            }
        };
        final String str6 = "MinPredictSize";
        final float f10 = 1.0f;
        final float f11 = 0.1f;
        final float f12 = 5.0f;
        this.minPredictSize = new FloatValue(this, str6, f10, f11, f12) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$minPredictSize$1
            final KillAura this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).floatValue(), ((Number) obj2).floatValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(float f13, float f14) {
                float fFloatValue = ((Number) this.this$0.maxPredictSize.get()).floatValue();
                if (fFloatValue < f14) {
                    set((Object) Float.valueOf(fFloatValue));
                }
            }
        };
    }

    @NotNull
    public final FloatValue getRangeValue() {
        return this.rangeValue;
    }

    @NotNull
    public final BoolValue getNorangeairban() {
        return this.norangeairban;
    }

    @NotNull
    public final ListValue getTargetModeValue() {
        return this.targetModeValue;
    }

    @NotNull
    public final ListValue getRotations() {
        return this.rotations;
    }

    @Nullable
    public final IEntityLivingBase getTarget() {
        return this.target;
    }

    public final void setTarget(@Nullable IEntityLivingBase iEntityLivingBase) {
        this.target = iEntityLivingBase;
    }

    @Nullable
    public final IEntityLivingBase getLastTarget() {
        return this.lastTarget;
    }

    public final void setLastTarget(@Nullable IEntityLivingBase iEntityLivingBase) {
        this.lastTarget = iEntityLivingBase;
    }

    public final boolean getBlockingStatus() {
        return this.blockingStatus;
    }

    public final void setBlockingStatus(boolean z) {
        this.blockingStatus = z;
    }

    public void onEnable() {
        if (MinecraftInstance.f157mc.getThePlayer() == null || MinecraftInstance.f157mc.getTheWorld() == null) {
            return;
        }
        updateTarget();
    }

    public void onDisable() {
        this.target = (IEntityLivingBase) null;
        this.currentTarget = (IEntityLivingBase) null;
        this.hitable = false;
        this.prevTargetEntities.clear();
        this.attackTimer.reset();
        this.clicks = 0;
        stopBlocking();
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x008a  */
    @EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onMotion(@NotNull MotionEvent event) {
        boolean z;
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (event.getEventState() == EventState.POST) {
            if (this.target == null || this.currentTarget == null) {
                return;
            }
            updateHitable();
            if (StringsKt.equals((String) this.autoBlockValue.get(), "Off", true)) {
                return;
            }
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer.getHeldItem() != null) {
                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                IItemStack heldItem = thePlayer2.getHeldItem();
                if (heldItem == null) {
                    Intrinsics.throwNpe();
                }
                z = iClassProvider.isItemSword(heldItem.getItem());
            }
            if (z) {
                IEntityLivingBase iEntityLivingBase = this.currentTarget;
                if (iEntityLivingBase == null) {
                    Intrinsics.throwNpe();
                }
                startBlocking(iEntityLivingBase, ((Boolean) this.interactAutoBlockValue.get()).booleanValue());
                return;
            }
            return;
        }
        if (StringsKt.equals((String) this.rotationStrafeValue.get(), "Off", true)) {
            update();
        }
    }

    @EventTarget
    public final void onStrafe(@NotNull StrafeEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (StringsKt.equals((String) this.rotationStrafeValue.get(), "Off", true)) {
            return;
        }
        update();
        if (this.currentTarget != null && RotationUtils.targetRotation != null) {
            String str = (String) this.rotationStrafeValue.get();
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = str.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case -902327211:
                    if (lowerCase.equals("silent")) {
                        update();
                        RotationUtils.targetRotation.applyStrafeToPlayer(event);
                        event.cancelEvent();
                        return;
                    }
                    return;
                case -891986231:
                    if (lowerCase.equals("strict")) {
                        Rotation rotation = RotationUtils.targetRotation;
                        if (rotation != null) {
                            float fComponent1 = rotation.component1();
                            float strafe = event.getStrafe();
                            float forward = event.getForward();
                            float friction = event.getFriction();
                            float f = (strafe * strafe) + (forward * forward);
                            if (f >= 1.0E-4f) {
                                float fSqrt = (float) Math.sqrt(f);
                                if (fSqrt < 1.0f) {
                                    fSqrt = 1.0f;
                                }
                                float f2 = friction / fSqrt;
                                float f3 = strafe * f2;
                                float f4 = forward * f2;
                                float fSin = (float) Math.sin((float) ((fComponent1 * 3.141592653589793d) / 180.0d));
                                float fCos = (float) Math.cos((float) ((fComponent1 * 3.141592653589793d) / 180.0d));
                                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer == null) {
                                    Intrinsics.throwNpe();
                                }
                                thePlayer.setMotionX(thePlayer.getMotionX() + ((f3 * fCos) - (f4 * fSin)));
                                thePlayer.setMotionZ(thePlayer.getMotionZ() + (f4 * fCos) + (f3 * fSin));
                            }
                            event.cancelEvent();
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

    /* JADX WARN: Removed duplicated region for block: B:16:0x0056  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void update() {
        boolean z;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.isSpectator()) {
            z = true;
        } else {
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            if (isAlive(thePlayer2) && !LiquidBounce.INSTANCE.getModuleManager().get(Blink.class).getState() && !LiquidBounce.INSTANCE.getModuleManager().get(FreeCam.class).getState()) {
                z = false;
            }
        }
        if (!z) {
            if (((Boolean) this.noInventoryAttackValue.get()).booleanValue() && (MinecraftInstance.classProvider.isGuiContainer(MinecraftInstance.f157mc.getCurrentScreen()) || System.currentTimeMillis() - this.containerOpen < ((Number) this.noInventoryDelayValue.get()).longValue())) {
                return;
            }
            updateTarget();
            if (this.target == null) {
                stopBlocking();
                return;
            }
            this.currentTarget = this.target;
            if (!StringsKt.equals((String) this.targetModeValue.get(), "Switch", true) && EntityUtils.isSelected(this.currentTarget, true)) {
                this.target = this.currentTarget;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0122  */
    @EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onUpdate(@NotNull UpdateEvent event) {
        boolean z;
        IEntityLivingBase iEntityLivingBase;
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.isSpectator()) {
            z = true;
        } else {
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            if (isAlive(thePlayer2) && !LiquidBounce.INSTANCE.getModuleManager().get(Blink.class).getState() && !LiquidBounce.INSTANCE.getModuleManager().get(FreeCam.class).getState()) {
                z = false;
            }
        }
        if (z) {
            this.target = (IEntityLivingBase) null;
            this.currentTarget = (IEntityLivingBase) null;
            this.hitable = false;
            stopBlocking();
            return;
        }
        IClassProvider iClassProvider = MinecraftInstance.classProvider;
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        IWorldClient iWorldClient = theWorld;
        IEntityLivingBase iEntityLivingBase2 = this.target;
        if (iEntityLivingBase2 == null) {
            Intrinsics.throwNpe();
        }
        double posX = iEntityLivingBase2.getPosX();
        IEntityLivingBase iEntityLivingBase3 = this.target;
        if (iEntityLivingBase3 == null) {
            Intrinsics.throwNpe();
        }
        double posY = iEntityLivingBase3.getPosY();
        IEntityLivingBase iEntityLivingBase4 = this.target;
        if (iEntityLivingBase4 == null) {
            Intrinsics.throwNpe();
        }
        IEntity iEntityCreateEntityLightningBolt = iClassProvider.createEntityLightningBolt(iWorldClient, posX, posY, iEntityLivingBase4.getPosZ(), false);
        if (Intrinsics.areEqual((String) this.hiteffect.get(), "Lightningbolt") && this.target != null) {
            if (this.lastTarget == null) {
                iEntityLivingBase = this.target;
            } else {
                IEntityLivingBase iEntityLivingBase5 = this.target;
                if (iEntityLivingBase5 == null) {
                    Intrinsics.throwNpe();
                }
                if (iEntityLivingBase5.getHealth() != 0.0f) {
                    IEntityLivingBase iEntityLivingBase6 = this.target;
                    if (iEntityLivingBase6 == null) {
                        Intrinsics.throwNpe();
                    }
                    if (iEntityLivingBase6.isDead() && this.target != null) {
                        IWorldClient theWorld2 = MinecraftInstance.f157mc.getTheWorld();
                        if (theWorld2 == null) {
                            Intrinsics.throwNpe();
                        }
                        theWorld2.addEntityToWorld(-1, iEntityCreateEntityLightningBolt);
                        if (((Boolean) this.lightingSoundValue.get()).booleanValue()) {
                            MinecraftInstance.f157mc.getSoundHandler().playSound("entity.lightning.impact", 1.0f);
                            MinecraftInstance.f157mc.getSoundHandler().playSound("entity.lightning.thunder", 1.0f);
                        }
                    }
                    iEntityLivingBase = this.target;
                }
            }
            this.lastTarget = iEntityLivingBase;
        }
        if (((Boolean) this.noInventoryAttackValue.get()).booleanValue() && (MinecraftInstance.classProvider.isGuiContainer(MinecraftInstance.f157mc.getCurrentScreen()) || System.currentTimeMillis() - this.containerOpen < ((Number) this.noInventoryDelayValue.get()).longValue())) {
            this.target = (IEntityLivingBase) null;
            this.currentTarget = (IEntityLivingBase) null;
            this.hitable = false;
            if (MinecraftInstance.classProvider.isGuiContainer(MinecraftInstance.f157mc.getCurrentScreen())) {
                this.containerOpen = System.currentTimeMillis();
                return;
            }
            return;
        }
        if (this.target == null || this.currentTarget == null) {
            return;
        }
        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer3 == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer3.getCooledAttackStrength(0.0f) >= ((Number) this.cooldownValue.get()).floatValue()) {
            while (this.clicks > 0) {
                runAttack();
                this.clicks--;
            }
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:16:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x029a  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x02ef  */
    @EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onRender3D(@NotNull Render3DEvent event) {
        boolean z;
        double d;
        double d2;
        double d3;
        int i;
        double d4;
        double d5;
        double d6;
        double d7;
        int i2;
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.isSpectator()) {
            z = true;
        } else {
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            if (isAlive(thePlayer2) && !LiquidBounce.INSTANCE.getModuleManager().get(Blink.class).getState() && !LiquidBounce.INSTANCE.getModuleManager().get(FreeCam.class).getState()) {
                z = false;
            }
        }
        if (z) {
            this.target = (IEntityLivingBase) null;
            this.currentTarget = (IEntityLivingBase) null;
            this.hitable = false;
            stopBlocking();
            return;
        }
        if (((Boolean) this.circleValue.get()).booleanValue()) {
            GL11.glPushMatrix();
            IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer3 == null) {
                Intrinsics.throwNpe();
            }
            double lastTickPosX = thePlayer3.getLastTickPosX();
            IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer4 == null) {
                Intrinsics.throwNpe();
            }
            double posX = thePlayer4.getPosX();
            IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer5 == null) {
                Intrinsics.throwNpe();
            }
            double lastTickPosX2 = (lastTickPosX + ((posX - thePlayer5.getLastTickPosX()) * MinecraftInstance.f157mc.getTimer().getRenderPartialTicks())) - MinecraftInstance.f157mc.getRenderManager().getRenderPosX();
            IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer6 == null) {
                Intrinsics.throwNpe();
            }
            double lastTickPosY = thePlayer6.getLastTickPosY();
            IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer7 == null) {
                Intrinsics.throwNpe();
            }
            double posY = thePlayer7.getPosY();
            IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer8 == null) {
                Intrinsics.throwNpe();
            }
            double lastTickPosY2 = (lastTickPosY + ((posY - thePlayer8.getLastTickPosY()) * MinecraftInstance.f157mc.getTimer().getRenderPartialTicks())) - MinecraftInstance.f157mc.getRenderManager().getRenderPosY();
            IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer9 == null) {
                Intrinsics.throwNpe();
            }
            double lastTickPosZ = thePlayer9.getLastTickPosZ();
            IEntityPlayerSP thePlayer10 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer10 == null) {
                Intrinsics.throwNpe();
            }
            double posZ = thePlayer10.getPosZ();
            IEntityPlayerSP thePlayer11 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer11 == null) {
                Intrinsics.throwNpe();
            }
            GL11.glTranslated(lastTickPosX2, lastTickPosY2, (lastTickPosZ + ((posZ - thePlayer11.getLastTickPosZ()) * MinecraftInstance.f157mc.getTimer().getRenderPartialTicks())) - MinecraftInstance.f157mc.getRenderManager().getRenderPosZ());
            GL11.glEnable(SGL.GL_BLEND);
            GL11.glEnable(SGL.GL_LINE_SMOOTH);
            GL11.glDisable(SGL.GL_TEXTURE_2D);
            GL11.glDisable(SGL.GL_DEPTH_TEST);
            GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glLineWidth(((Number) this.circleThicknessValue.get()).floatValue());
            GL11.glColor4f(((Number) this.circleRedValue.get()).intValue() / 255.0f, ((Number) this.circleGreenValue.get()).intValue() / 255.0f, ((Number) this.circleBlueValue.get()).intValue() / 255.0f, ((Number) this.circleAlphaValue.get()).intValue() / 255.0f);
            GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
            GL11.glBegin(3);
            IntProgression intProgressionStep = RangesKt.step(new IntRange(0, 360), 5);
            int first = intProgressionStep.getFirst();
            int last = intProgressionStep.getLast();
            int step = intProgressionStep.getStep();
            if (step < 0 ? first >= last : first <= last) {
                while (true) {
                    float fCos = (float) Math.cos((first * 3.141592653589793d) / 180.0d);
                    if (((Boolean) this.norangeairban.get()).booleanValue()) {
                        IEntityPlayerSP thePlayer12 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer12 == null) {
                            Intrinsics.throwNpe();
                        }
                        float fFloatValue = !thePlayer12.getOnGround() ? 2.9f : ((Number) this.rangeValue.get()).floatValue();
                        float f = fCos * fFloatValue;
                        float fSin = (float) Math.sin((first * 3.141592653589793d) / 180.0d);
                        if (((Boolean) this.norangeairban.get()).booleanValue()) {
                            IEntityPlayerSP thePlayer13 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer13 == null) {
                                Intrinsics.throwNpe();
                            }
                            float fFloatValue2 = !thePlayer13.getOnGround() ? 2.9f : ((Number) this.rangeValue.get()).floatValue();
                            GL11.glVertex2f(f, fSin * fFloatValue2);
                            if (first == last) {
                                break;
                            } else {
                                first += step;
                            }
                        }
                    }
                }
            }
            GL11.glEnd();
            GL11.glDisable(SGL.GL_BLEND);
            GL11.glEnable(SGL.GL_TEXTURE_2D);
            GL11.glEnable(SGL.GL_DEPTH_TEST);
            GL11.glDisable(SGL.GL_LINE_SMOOTH);
            GL11.glPopMatrix();
        }
        if (((Boolean) this.noInventoryAttackValue.get()).booleanValue() && (MinecraftInstance.classProvider.isGuiContainer(MinecraftInstance.f157mc.getCurrentScreen()) || System.currentTimeMillis() - this.containerOpen < ((Number) this.noInventoryDelayValue.get()).longValue())) {
            this.target = (IEntityLivingBase) null;
            this.currentTarget = (IEntityLivingBase) null;
            this.hitable = false;
            if (MinecraftInstance.classProvider.isGuiContainer(MinecraftInstance.f157mc.getCurrentScreen())) {
                this.containerOpen = System.currentTimeMillis();
                return;
            }
            return;
        }
        if (this.target != null) {
            String str = (String) this.hiteffect.get();
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = str.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case 3143222:
                    if (lowerCase.equals("fire")) {
                        IParticleManager effectRenderer = MinecraftInstance.f157mc.getEffectRenderer();
                        IEntityLivingBase iEntityLivingBase = this.target;
                        if (iEntityLivingBase == null) {
                            Intrinsics.throwNpe();
                        }
                        effectRenderer.emitParticleAtEntity(iEntityLivingBase, EnumParticleTypes.LAVA);
                        break;
                    }
                    break;
                case 93832698:
                    if (lowerCase.equals("blood")) {
                        for (int i3 = 0; i3 < 10; i3++) {
                            IParticleManager effectRenderer2 = MinecraftInstance.f157mc.getEffectRenderer();
                            int iFunc_179348_c = EnumParticleTypes.BLOCK_CRACK.func_179348_c();
                            IEntityLivingBase iEntityLivingBase2 = this.target;
                            if (iEntityLivingBase2 == null) {
                                Intrinsics.throwNpe();
                            }
                            double posX2 = iEntityLivingBase2.getPosX();
                            IEntityLivingBase iEntityLivingBase3 = this.target;
                            if (iEntityLivingBase3 == null) {
                                Intrinsics.throwNpe();
                            }
                            double posY2 = iEntityLivingBase3.getPosY();
                            if (this.target == null) {
                                Intrinsics.throwNpe();
                            }
                            double height = posY2 + (r4.getHeight() / 2.0f);
                            IEntityLivingBase iEntityLivingBase4 = this.target;
                            if (iEntityLivingBase4 == null) {
                                Intrinsics.throwNpe();
                            }
                            double posZ2 = iEntityLivingBase4.getPosZ();
                            IEntityLivingBase iEntityLivingBase5 = this.target;
                            if (iEntityLivingBase5 == null) {
                                Intrinsics.throwNpe();
                            }
                            double motionX = iEntityLivingBase5.getMotionX() + RandomUtils.INSTANCE.nextFloat(-0.5f, 0.5f);
                            IEntityLivingBase iEntityLivingBase6 = this.target;
                            if (iEntityLivingBase6 == null) {
                                Intrinsics.throwNpe();
                            }
                            double motionY = iEntityLivingBase6.getMotionY() + RandomUtils.INSTANCE.nextFloat(-0.5f, 0.5f);
                            IEntityLivingBase iEntityLivingBase7 = this.target;
                            if (iEntityLivingBase7 == null) {
                                Intrinsics.throwNpe();
                            }
                            double motionZ = iEntityLivingBase7.getMotionZ() + RandomUtils.INSTANCE.nextFloat(-0.5f, 0.5f);
                            Block block = Blocks.field_150451_bX;
                            Intrinsics.checkExpressionValueIsNotNull(block, "Blocks.REDSTONE_BLOCK");
                            effectRenderer2.spawnEffectParticle(iFunc_179348_c, posX2, height, posZ2, motionX, motionY, motionZ, Block.func_176210_f(block.func_176223_P()));
                            Unit unit = Unit.INSTANCE;
                        }
                        break;
                    }
                    break;
                case 97513267:
                    if (lowerCase.equals("flame")) {
                        IParticleManager effectRenderer3 = MinecraftInstance.f157mc.getEffectRenderer();
                        IEntityLivingBase iEntityLivingBase8 = this.target;
                        if (iEntityLivingBase8 == null) {
                            Intrinsics.throwNpe();
                        }
                        effectRenderer3.emitParticleAtEntity(iEntityLivingBase8, EnumParticleTypes.FLAME);
                        break;
                    }
                    break;
                case 99151942:
                    if (lowerCase.equals("heart")) {
                        IParticleManager effectRenderer4 = MinecraftInstance.f157mc.getEffectRenderer();
                        IEntityLivingBase iEntityLivingBase9 = this.target;
                        if (iEntityLivingBase9 == null) {
                            Intrinsics.throwNpe();
                        }
                        effectRenderer4.emitParticleAtEntity(iEntityLivingBase9, EnumParticleTypes.HEART);
                        break;
                    }
                    break;
                case 109562223:
                    if (lowerCase.equals("smoke")) {
                        IParticleManager effectRenderer5 = MinecraftInstance.f157mc.getEffectRenderer();
                        IEntityLivingBase iEntityLivingBase10 = this.target;
                        if (iEntityLivingBase10 == null) {
                            Intrinsics.throwNpe();
                        }
                        effectRenderer5.emitParticleAtEntity(iEntityLivingBase10, EnumParticleTypes.SMOKE_LARGE);
                        break;
                    }
                    break;
                case 112903447:
                    if (lowerCase.equals("water")) {
                        IParticleManager effectRenderer6 = MinecraftInstance.f157mc.getEffectRenderer();
                        IEntityLivingBase iEntityLivingBase11 = this.target;
                        if (iEntityLivingBase11 == null) {
                            Intrinsics.throwNpe();
                        }
                        effectRenderer6.emitParticleAtEntity(iEntityLivingBase11, EnumParticleTypes.WATER_DROP);
                        break;
                    }
                    break;
                case 387153076:
                    if (lowerCase.equals("criticals")) {
                        IParticleManager effectRenderer7 = MinecraftInstance.f157mc.getEffectRenderer();
                        IEntityLivingBase iEntityLivingBase12 = this.target;
                        if (iEntityLivingBase12 == null) {
                            Intrinsics.throwNpe();
                        }
                        effectRenderer7.emitParticleAtEntity(iEntityLivingBase12, EnumParticleTypes.CRIT);
                        break;
                    }
                    break;
            }
            String str2 = (String) this.markValue.get();
            if (str2 == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase2 = str2.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase2, "(this as java.lang.String).toLowerCase()");
            switch (lowerCase2.hashCode()) {
                case -1601998787:
                    if (lowerCase2.equals("jellored")) {
                        int iCurrentTimeMillis = (int) (System.currentTimeMillis() % 2000);
                        boolean z2 = iCurrentTimeMillis > 1000;
                        double d8 = iCurrentTimeMillis / 1000.0d;
                        if (!z2) {
                            d = 1.0d - d8;
                        } else {
                            d = d8 - 1.0d;
                        }
                        double dEaseInOutQuad = EaseUtils.easeInOutQuad(d);
                        ArrayList<WVec3> arrayList = new ArrayList();
                        IEntityLivingBase iEntityLivingBase13 = this.target;
                        if (iEntityLivingBase13 == null) {
                            Intrinsics.throwNpe();
                        }
                        IAxisAlignedBB entityBoundingBox = iEntityLivingBase13.getEntityBoundingBox();
                        double maxX = entityBoundingBox.getMaxX() - entityBoundingBox.getMinX();
                        double maxY = entityBoundingBox.getMaxY() - entityBoundingBox.getMinY();
                        IEntityLivingBase iEntityLivingBase14 = this.target;
                        if (iEntityLivingBase14 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosX3 = iEntityLivingBase14.getLastTickPosX();
                        IEntityLivingBase iEntityLivingBase15 = this.target;
                        if (iEntityLivingBase15 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posX3 = iEntityLivingBase15.getPosX();
                        IEntityLivingBase iEntityLivingBase16 = this.target;
                        if (iEntityLivingBase16 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosX4 = lastTickPosX3 + ((posX3 - iEntityLivingBase16.getLastTickPosX()) * MinecraftInstance.f157mc.getTimer().getRenderPartialTicks());
                        IEntityLivingBase iEntityLivingBase17 = this.target;
                        if (iEntityLivingBase17 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosY3 = iEntityLivingBase17.getLastTickPosY();
                        IEntityLivingBase iEntityLivingBase18 = this.target;
                        if (iEntityLivingBase18 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posY3 = iEntityLivingBase18.getPosY();
                        IEntityLivingBase iEntityLivingBase19 = this.target;
                        if (iEntityLivingBase19 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosY4 = lastTickPosY3 + ((posY3 - iEntityLivingBase19.getLastTickPosY()) * MinecraftInstance.f157mc.getTimer().getRenderPartialTicks());
                        if (z2) {
                            d2 = lastTickPosY4 - 0.5d;
                        } else {
                            d2 = lastTickPosY4 + 0.5d;
                        }
                        IEntityLivingBase iEntityLivingBase20 = this.target;
                        if (iEntityLivingBase20 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosZ2 = iEntityLivingBase20.getLastTickPosZ();
                        IEntityLivingBase iEntityLivingBase21 = this.target;
                        if (iEntityLivingBase21 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posZ3 = iEntityLivingBase21.getPosZ();
                        IEntityLivingBase iEntityLivingBase22 = this.target;
                        if (iEntityLivingBase22 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosZ3 = lastTickPosZ2 + ((posZ3 - iEntityLivingBase22.getLastTickPosZ()) * MinecraftInstance.f157mc.getTimer().getRenderPartialTicks());
                        IntProgression intProgressionStep2 = RangesKt.step(new IntRange(0, 360), 7);
                        int first2 = intProgressionStep2.getFirst();
                        int last2 = intProgressionStep2.getLast();
                        int step2 = intProgressionStep2.getStep();
                        if (step2 < 0 ? first2 >= last2 : first2 <= last2) {
                            while (true) {
                                arrayList.add(new WVec3(lastTickPosX4 - (Math.sin((first2 * 3.141592653589793d) / 180.0d) * maxX), d2 + (maxY * dEaseInOutQuad), lastTickPosZ3 + (Math.cos((first2 * 3.141592653589793d) / 180.0d) * maxX)));
                                if (first2 != last2) {
                                    first2 += step2;
                                }
                            }
                        }
                        arrayList.add(arrayList.get(0));
                        MinecraftInstance.f157mc.getEntityRenderer().disableLightmap();
                        GL11.glPushMatrix();
                        GL11.glDisable(SGL.GL_TEXTURE_2D);
                        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
                        GL11.glEnable(SGL.GL_LINE_SMOOTH);
                        GL11.glEnable(SGL.GL_BLEND);
                        GL11.glDisable(SGL.GL_DEPTH_TEST);
                        GL11.glBegin(3);
                        if (dEaseInOutQuad > 0.5d) {
                            d3 = 1.0d - dEaseInOutQuad;
                        } else {
                            d3 = dEaseInOutQuad;
                        }
                        double d9 = d3 * 2.0d;
                        double d10 = (maxY / 60.0d) * 20.0d * (1.0d - d9);
                        if (z2) {
                            i = -1;
                        } else {
                            i = 1;
                        }
                        double d11 = d10 * i;
                        for (int i4 = 0; i4 <= 20; i4++) {
                            double d12 = (maxY / 60.0d) * i4 * d9;
                            if (z2) {
                                d12 = -d12;
                            }
                            WVec3 wVec3 = (WVec3) arrayList.get(0);
                            GL11.glVertex3d(wVec3.getXCoord() - MinecraftInstance.f157mc.getRenderManager().getViewerPosX(), ((wVec3.getYCoord() - d12) - d11) - MinecraftInstance.f157mc.getRenderManager().getViewerPosY(), wVec3.getZCoord() - MinecraftInstance.f157mc.getRenderManager().getViewerPosZ());
                            GL11.glColor4f(255.0f, 0.0f, 0.0f, 0.7f * (i4 / 20.0f));
                            for (WVec3 wVec32 : arrayList) {
                                GL11.glVertex3d(wVec32.getXCoord() - MinecraftInstance.f157mc.getRenderManager().getViewerPosX(), ((wVec32.getYCoord() - d12) - d11) - MinecraftInstance.f157mc.getRenderManager().getViewerPosY(), wVec32.getZCoord() - MinecraftInstance.f157mc.getRenderManager().getViewerPosZ());
                            }
                            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
                        }
                        GL11.glEnd();
                        GL11.glEnable(SGL.GL_DEPTH_TEST);
                        GL11.glDisable(SGL.GL_LINE_SMOOTH);
                        GL11.glDisable(SGL.GL_BLEND);
                        GL11.glEnable(SGL.GL_TEXTURE_2D);
                        GL11.glPopMatrix();
                        break;
                    }
                    break;
                case -1102567108:
                    if (lowerCase2.equals("liquid")) {
                        IEntityLivingBase iEntityLivingBase23 = this.target;
                        if (iEntityLivingBase23 == null) {
                            Intrinsics.throwNpe();
                        }
                        IEntityLivingBase iEntityLivingBase24 = iEntityLivingBase23;
                        IEntityLivingBase iEntityLivingBase25 = this.target;
                        if (iEntityLivingBase25 == null) {
                            Intrinsics.throwNpe();
                        }
                        RenderUtils.drawPlatform(iEntityLivingBase24, iEntityLivingBase25.getHurtTime() <= 0 ? new Color(37, 126, 255, 170) : new Color(255, 0, 0, 170));
                        break;
                    }
                    break;
                case 101234:
                    if (lowerCase2.equals("fdp")) {
                        int iCurrentTimeMillis2 = (int) (System.currentTimeMillis() % 1500);
                        boolean z3 = iCurrentTimeMillis2 > 750;
                        double d13 = iCurrentTimeMillis2 / 750.0d;
                        if (!z3) {
                            d4 = 1.0d - d13;
                        } else {
                            d4 = d13 - 1.0d;
                        }
                        double dEaseInOutQuad2 = EaseUtils.easeInOutQuad(d4);
                        GL11.glPushMatrix();
                        GL11.glDisable(SGL.GL_TEXTURE_2D);
                        GL11.glEnable(SGL.GL_LINE_SMOOTH);
                        GL11.glEnable(SGL.GL_POLYGON_SMOOTH);
                        GL11.glEnable(SGL.GL_POINT_SMOOTH);
                        GL11.glEnable(SGL.GL_BLEND);
                        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
                        GL11.glHint(3154, 4354);
                        GL11.glHint(3155, 4354);
                        GL11.glHint(3153, 4354);
                        GL11.glDisable(SGL.GL_DEPTH_TEST);
                        GL11.glDepthMask(false);
                        IEntityLivingBase iEntityLivingBase26 = this.target;
                        if (iEntityLivingBase26 == null) {
                            Intrinsics.throwNpe();
                        }
                        IAxisAlignedBB entityBoundingBox2 = iEntityLivingBase26.getEntityBoundingBox();
                        double maxX2 = (entityBoundingBox2.getMaxX() - entityBoundingBox2.getMinX()) + 0.3d;
                        double maxY2 = entityBoundingBox2.getMaxY() - entityBoundingBox2.getMinY();
                        IEntityLivingBase iEntityLivingBase27 = this.target;
                        if (iEntityLivingBase27 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosX5 = iEntityLivingBase27.getLastTickPosX();
                        IEntityLivingBase iEntityLivingBase28 = this.target;
                        if (iEntityLivingBase28 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posX4 = iEntityLivingBase28.getPosX();
                        IEntityLivingBase iEntityLivingBase29 = this.target;
                        if (iEntityLivingBase29 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosX6 = (lastTickPosX5 + ((posX4 - iEntityLivingBase29.getLastTickPosX()) * event.getPartialTicks())) - MinecraftInstance.f157mc.getRenderManager().getViewerPosX();
                        IEntityLivingBase iEntityLivingBase30 = this.target;
                        if (iEntityLivingBase30 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosY5 = iEntityLivingBase30.getLastTickPosY();
                        IEntityLivingBase iEntityLivingBase31 = this.target;
                        if (iEntityLivingBase31 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posY4 = iEntityLivingBase31.getPosY();
                        IEntityLivingBase iEntityLivingBase32 = this.target;
                        if (iEntityLivingBase32 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosY6 = ((lastTickPosY5 + ((posY4 - iEntityLivingBase32.getLastTickPosY()) * event.getPartialTicks())) - MinecraftInstance.f157mc.getRenderManager().getViewerPosY()) + (maxY2 * dEaseInOutQuad2);
                        IEntityLivingBase iEntityLivingBase33 = this.target;
                        if (iEntityLivingBase33 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosZ4 = iEntityLivingBase33.getLastTickPosZ();
                        IEntityLivingBase iEntityLivingBase34 = this.target;
                        if (iEntityLivingBase34 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posZ4 = iEntityLivingBase34.getPosZ();
                        IEntityLivingBase iEntityLivingBase35 = this.target;
                        if (iEntityLivingBase35 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosZ5 = (lastTickPosZ4 + ((posZ4 - iEntityLivingBase35.getLastTickPosZ()) * event.getPartialTicks())) - MinecraftInstance.f157mc.getRenderManager().getViewerPosZ();
                        GL11.glLineWidth((float) (maxX2 * 5.0d));
                        GL11.glBegin(3);
                        for (int i5 = 0; i5 <= 360; i5++) {
                            if (MinecraftInstance.f157mc.getThePlayer() == null) {
                                Intrinsics.throwNpe();
                            }
                            Color color = new Color(Color.HSBtoRGB(((float) ((r0.getTicksExisted() / 70.0d) + Math.sin((i5 / 50.0d) * 1.75d))) % 1.0f, 0.7f, 1.0f));
                            GL11.glColor3f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f);
                            GL11.glVertex3d(lastTickPosX6 + (maxX2 * Math.cos((i5 * 6.283185307179586d) / 45.0d)), lastTickPosY6, lastTickPosZ5 + (maxX2 * Math.sin((i5 * 6.283185307179586d) / 45.0d)));
                        }
                        GL11.glEnd();
                        GL11.glDepthMask(true);
                        GL11.glEnable(SGL.GL_DEPTH_TEST);
                        GL11.glDisable(SGL.GL_LINE_SMOOTH);
                        GL11.glDisable(SGL.GL_POLYGON_SMOOTH);
                        GL11.glEnable(SGL.GL_POINT_SMOOTH);
                        GL11.glEnable(SGL.GL_TEXTURE_2D);
                        GL11.glPopMatrix();
                        break;
                    }
                    break;
                case 112785:
                    if (lowerCase2.equals("red")) {
                        IEntityLivingBase iEntityLivingBase36 = this.target;
                        if (iEntityLivingBase36 == null) {
                            Intrinsics.throwNpe();
                        }
                        IEntityLivingBase iEntityLivingBase37 = iEntityLivingBase36;
                        IEntityLivingBase iEntityLivingBase38 = this.target;
                        if (iEntityLivingBase38 == null) {
                            Intrinsics.throwNpe();
                        }
                        RenderUtils.drawPlatform(iEntityLivingBase37, iEntityLivingBase38.getHurtTime() <= 0 ? new Color(255, 255, 255, 255) : new Color(124, Typography.times, 255, 255));
                        break;
                    }
                    break;
                case 3443503:
                    if (lowerCase2.equals("plat")) {
                        IEntityLivingBase iEntityLivingBase39 = this.target;
                        if (iEntityLivingBase39 == null) {
                            Intrinsics.throwNpe();
                        }
                        RenderUtils.drawPlatform(iEntityLivingBase39, this.hitable ? new Color(37, 126, 255, 70) : new Color(255, 0, 0, 70));
                        break;
                    }
                    break;
                case 3530364:
                    if (lowerCase2.equals("sims")) {
                        GL11.glPushMatrix();
                        IEntityLivingBase iEntityLivingBase40 = this.target;
                        if (iEntityLivingBase40 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosX7 = iEntityLivingBase40.getLastTickPosX();
                        IEntityLivingBase iEntityLivingBase41 = this.target;
                        if (iEntityLivingBase41 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posX5 = iEntityLivingBase41.getPosX();
                        IEntityLivingBase iEntityLivingBase42 = this.target;
                        if (iEntityLivingBase42 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosX8 = (lastTickPosX7 + ((posX5 - iEntityLivingBase42.getLastTickPosX()) * event.getPartialTicks())) - MinecraftInstance.f157mc.getRenderManager().getViewerPosX();
                        IEntityLivingBase iEntityLivingBase43 = this.target;
                        if (iEntityLivingBase43 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosY7 = iEntityLivingBase43.getLastTickPosY();
                        IEntityLivingBase iEntityLivingBase44 = this.target;
                        if (iEntityLivingBase44 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posY5 = iEntityLivingBase44.getPosY();
                        IEntityLivingBase iEntityLivingBase45 = this.target;
                        if (iEntityLivingBase45 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosY8 = (lastTickPosY7 + ((posY5 - iEntityLivingBase45.getLastTickPosY()) * event.getPartialTicks())) - MinecraftInstance.f157mc.getRenderManager().getViewerPosY();
                        if (this.target == null) {
                            Intrinsics.throwNpe();
                        }
                        double height2 = lastTickPosY8 + (r2.getHeight() * 1.1d);
                        IEntityLivingBase iEntityLivingBase46 = this.target;
                        if (iEntityLivingBase46 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosZ6 = iEntityLivingBase46.getLastTickPosZ();
                        IEntityLivingBase iEntityLivingBase47 = this.target;
                        if (iEntityLivingBase47 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posZ5 = iEntityLivingBase47.getPosZ();
                        IEntityLivingBase iEntityLivingBase48 = this.target;
                        if (iEntityLivingBase48 == null) {
                            Intrinsics.throwNpe();
                        }
                        GL11.glTranslated(lastTickPosX8, height2, (lastTickPosZ6 + ((posZ5 - iEntityLivingBase48.getLastTickPosZ()) * event.getPartialTicks())) - MinecraftInstance.f157mc.getRenderManager().getViewerPosZ());
                        IEntityLivingBase iEntityLivingBase49 = this.target;
                        if (iEntityLivingBase49 == null) {
                            Intrinsics.throwNpe();
                        }
                        GL11.glRotatef(-iEntityLivingBase49.getWidth(), 0.0f, 1.0f, 0.0f);
                        if (MinecraftInstance.f157mc.getThePlayer() == null) {
                            Intrinsics.throwNpe();
                        }
                        GL11.glRotatef((r0.getTicksExisted() + MinecraftInstance.f157mc.getTimer().getRenderPartialTicks()) * 5.0f, 0.0f, 1.0f, 0.0f);
                        IEntityLivingBase iEntityLivingBase50 = this.target;
                        if (iEntityLivingBase50 == null) {
                            Intrinsics.throwNpe();
                        }
                        RenderUtils.glColor(iEntityLivingBase50.getHurtTime() <= 0 ? new Color(80, 255, 80) : new Color(255, 0, 0));
                        RenderUtils.enableSmoothLine(1.5f);
                        Cylinder cylinder = new Cylinder();
                        GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                        cylinder.draw(0.0f, 0.15f, 0.3f, 4, 1);
                        cylinder.setDrawStyle(100012);
                        GL11.glTranslated(0.0d, 0.0d, 0.3d);
                        cylinder.draw(0.15f, 0.0f, 0.3f, 4, 1);
                        GL11.glRotatef(90.0f, 0.0f, 0.0f, 1.0f);
                        GL11.glTranslated(0.0d, 0.0d, -0.3d);
                        cylinder.draw(0.0f, 0.15f, 0.3f, 4, 1);
                        GL11.glTranslated(0.0d, 0.0d, 0.3d);
                        cylinder.draw(0.15f, 0.0f, 0.3f, 4, 1);
                        RenderUtils.disableSmoothLine();
                        GL11.glPopMatrix();
                        break;
                    }
                    break;
                case 93832333:
                    if (lowerCase2.equals("block")) {
                        IEntityLivingBase iEntityLivingBase51 = this.target;
                        if (iEntityLivingBase51 == null) {
                            Intrinsics.throwNpe();
                        }
                        IAxisAlignedBB entityBoundingBox3 = iEntityLivingBase51.getEntityBoundingBox();
                        IEntityLivingBase iEntityLivingBase52 = this.target;
                        if (iEntityLivingBase52 == null) {
                            Intrinsics.throwNpe();
                        }
                        iEntityLivingBase52.setEntityBoundingBox(entityBoundingBox3.expand(0.2d, 0.2d, 0.2d));
                        IEntityLivingBase iEntityLivingBase53 = this.target;
                        if (iEntityLivingBase53 == null) {
                            Intrinsics.throwNpe();
                        }
                        IEntityLivingBase iEntityLivingBase54 = iEntityLivingBase53;
                        IEntityLivingBase iEntityLivingBase55 = this.target;
                        if (iEntityLivingBase55 == null) {
                            Intrinsics.throwNpe();
                        }
                        RenderUtils.drawEntityBox(iEntityLivingBase54, iEntityLivingBase55.getHurtTime() <= 0 ? Color.GREEN : Color.RED, true);
                        IEntityLivingBase iEntityLivingBase56 = this.target;
                        if (iEntityLivingBase56 == null) {
                            Intrinsics.throwNpe();
                        }
                        iEntityLivingBase56.setEntityBoundingBox(entityBoundingBox3);
                        break;
                    }
                    break;
                case 101009364:
                    if (lowerCase2.equals("jello")) {
                        int iCurrentTimeMillis3 = (int) (System.currentTimeMillis() % 2000);
                        boolean z4 = iCurrentTimeMillis3 > 1000;
                        double d14 = iCurrentTimeMillis3 / 1000.0d;
                        if (!z4) {
                            d5 = 1.0d - d14;
                        } else {
                            d5 = d14 - 1.0d;
                        }
                        double dEaseInOutQuad3 = EaseUtils.easeInOutQuad(d5);
                        ArrayList<WVec3> arrayList2 = new ArrayList();
                        IEntityLivingBase iEntityLivingBase57 = this.target;
                        if (iEntityLivingBase57 == null) {
                            Intrinsics.throwNpe();
                        }
                        IAxisAlignedBB entityBoundingBox4 = iEntityLivingBase57.getEntityBoundingBox();
                        double maxX3 = entityBoundingBox4.getMaxX() - entityBoundingBox4.getMinX();
                        double maxY3 = entityBoundingBox4.getMaxY() - entityBoundingBox4.getMinY();
                        IEntityLivingBase iEntityLivingBase58 = this.target;
                        if (iEntityLivingBase58 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosX9 = iEntityLivingBase58.getLastTickPosX();
                        IEntityLivingBase iEntityLivingBase59 = this.target;
                        if (iEntityLivingBase59 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posX6 = iEntityLivingBase59.getPosX();
                        IEntityLivingBase iEntityLivingBase60 = this.target;
                        if (iEntityLivingBase60 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosX10 = lastTickPosX9 + ((posX6 - iEntityLivingBase60.getLastTickPosX()) * MinecraftInstance.f157mc.getTimer().getRenderPartialTicks());
                        IEntityLivingBase iEntityLivingBase61 = this.target;
                        if (iEntityLivingBase61 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosY9 = iEntityLivingBase61.getLastTickPosY();
                        IEntityLivingBase iEntityLivingBase62 = this.target;
                        if (iEntityLivingBase62 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posY6 = iEntityLivingBase62.getPosY();
                        IEntityLivingBase iEntityLivingBase63 = this.target;
                        if (iEntityLivingBase63 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosY10 = lastTickPosY9 + ((posY6 - iEntityLivingBase63.getLastTickPosY()) * MinecraftInstance.f157mc.getTimer().getRenderPartialTicks());
                        if (z4) {
                            d6 = lastTickPosY10 - 0.5d;
                        } else {
                            d6 = lastTickPosY10 + 0.5d;
                        }
                        IEntityLivingBase iEntityLivingBase64 = this.target;
                        if (iEntityLivingBase64 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosZ7 = iEntityLivingBase64.getLastTickPosZ();
                        IEntityLivingBase iEntityLivingBase65 = this.target;
                        if (iEntityLivingBase65 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posZ6 = iEntityLivingBase65.getPosZ();
                        IEntityLivingBase iEntityLivingBase66 = this.target;
                        if (iEntityLivingBase66 == null) {
                            Intrinsics.throwNpe();
                        }
                        double lastTickPosZ8 = lastTickPosZ7 + ((posZ6 - iEntityLivingBase66.getLastTickPosZ()) * MinecraftInstance.f157mc.getTimer().getRenderPartialTicks());
                        IntProgression intProgressionStep3 = RangesKt.step(new IntRange(0, 360), 7);
                        int first3 = intProgressionStep3.getFirst();
                        int last3 = intProgressionStep3.getLast();
                        int step3 = intProgressionStep3.getStep();
                        if (step3 < 0 ? first3 >= last3 : first3 <= last3) {
                            while (true) {
                                arrayList2.add(new WVec3(lastTickPosX10 - (Math.sin((first3 * 3.141592653589793d) / 180.0d) * maxX3), d6 + (maxY3 * dEaseInOutQuad3), lastTickPosZ8 + (Math.cos((first3 * 3.141592653589793d) / 180.0d) * maxX3)));
                                if (first3 != last3) {
                                    first3 += step3;
                                }
                            }
                        }
                        arrayList2.add(arrayList2.get(0));
                        MinecraftInstance.f157mc.getEntityRenderer().disableLightmap();
                        GL11.glPushMatrix();
                        GL11.glDisable(SGL.GL_TEXTURE_2D);
                        GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
                        GL11.glEnable(SGL.GL_LINE_SMOOTH);
                        GL11.glEnable(SGL.GL_BLEND);
                        GL11.glDisable(SGL.GL_DEPTH_TEST);
                        GL11.glBegin(3);
                        if (dEaseInOutQuad3 > 0.5d) {
                            d7 = 1.0d - dEaseInOutQuad3;
                        } else {
                            d7 = dEaseInOutQuad3;
                        }
                        double d15 = d7 * 2.0d;
                        double d16 = (maxY3 / 60.0d) * 20.0d * (1.0d - d15);
                        if (z4) {
                            i2 = -1;
                        } else {
                            i2 = 1;
                        }
                        double d17 = d16 * i2;
                        for (int i6 = 0; i6 <= 20; i6++) {
                            double d18 = (maxY3 / 60.0d) * i6 * d15;
                            if (z4) {
                                d18 = -d18;
                            }
                            WVec3 wVec33 = (WVec3) arrayList2.get(0);
                            GL11.glVertex3d(wVec33.getXCoord() - MinecraftInstance.f157mc.getRenderManager().getViewerPosX(), ((wVec33.getYCoord() - d18) - d17) - MinecraftInstance.f157mc.getRenderManager().getViewerPosY(), wVec33.getZCoord() - MinecraftInstance.f157mc.getRenderManager().getViewerPosZ());
                            GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.7f * (i6 / 20.0f));
                            for (WVec3 wVec34 : arrayList2) {
                                GL11.glVertex3d(wVec34.getXCoord() - MinecraftInstance.f157mc.getRenderManager().getViewerPosX(), ((wVec34.getYCoord() - d18) - d17) - MinecraftInstance.f157mc.getRenderManager().getViewerPosY(), wVec34.getZCoord() - MinecraftInstance.f157mc.getRenderManager().getViewerPosZ());
                            }
                            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.0f);
                        }
                        GL11.glEnd();
                        GL11.glEnable(SGL.GL_DEPTH_TEST);
                        GL11.glDisable(SGL.GL_LINE_SMOOTH);
                        GL11.glDisable(SGL.GL_BLEND);
                        GL11.glEnable(SGL.GL_TEXTURE_2D);
                        GL11.glPopMatrix();
                        break;
                    }
                    break;
            }
            if (this.currentTarget != null && this.attackTimer.hasTimePassed(this.attackDelay)) {
                IEntityLivingBase iEntityLivingBase67 = this.currentTarget;
                if (iEntityLivingBase67 == null) {
                    Intrinsics.throwNpe();
                }
                if (iEntityLivingBase67.getHurtTime() <= ((Number) this.hurtTimeValue.get()).intValue()) {
                    this.clicks++;
                    this.attackTimer.reset();
                    this.attackDelay = TimeUtils.randomClickDelay(((Number) this.minCPS.get()).intValue(), ((Number) this.maxCPS.get()).intValue());
                }
            }
        }
    }

    @EventTarget
    public final void onEntityMove(@NotNull EntityMovementEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntity movedEntity = event.getMovedEntity();
        if (this.target == null || (!Intrinsics.areEqual(movedEntity, this.currentTarget))) {
            return;
        }
        updateHitable();
    }

    private final void runAttack() {
        IEntityPlayerSP thePlayer;
        IWorldClient theWorld;
        int entityId;
        int entityId2;
        if (this.target == null || this.currentTarget == null || (thePlayer = MinecraftInstance.f157mc.getThePlayer()) == null || (theWorld = MinecraftInstance.f157mc.getTheWorld()) == null) {
            return;
        }
        float fFloatValue = ((Number) this.failRateValue.get()).floatValue();
        boolean zBooleanValue = ((Boolean) this.swingValue.get()).booleanValue();
        boolean zEquals = StringsKt.equals((String) this.targetModeValue.get(), "Multi", true);
        boolean z = ((Boolean) this.aacValue.get()).booleanValue() && MinecraftInstance.classProvider.isGuiContainer(MinecraftInstance.f157mc.getCurrentScreen());
        boolean z2 = fFloatValue > 0.0f && ((float) new Random().nextInt(100)) <= fFloatValue;
        if (z) {
            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketCloseWindow());
        }
        if (!this.hitable || z2) {
            if (zBooleanValue && (((Boolean) this.fakeSwingValue.get()).booleanValue() || z2)) {
                thePlayer.swingItem();
            }
        } else {
            if (!zEquals) {
                IEntityLivingBase iEntityLivingBase = this.currentTarget;
                if (iEntityLivingBase == null) {
                    Intrinsics.throwNpe();
                }
                attackEntity(iEntityLivingBase);
            } else {
                int i = 0;
                for (IEntity iEntity : theWorld.getLoadedEntityList()) {
                    double distanceToEntityBox = PlayerExtensionKt.getDistanceToEntityBox(thePlayer, iEntity);
                    if (MinecraftInstance.classProvider.isEntityLivingBase(iEntity) && EntityUtils.isSelected(iEntity, true) && distanceToEntityBox <= getRange(iEntity)) {
                        attackEntity(iEntity.asEntityLivingBase());
                        i++;
                        if (((Number) this.limitedMultiTargetsValue.get()).intValue() != 0 && ((Number) this.limitedMultiTargetsValue.get()).intValue() <= i) {
                            break;
                        }
                    }
                }
            }
            if (this.switchTimer.hasTimePassed(((Number) this.switchDelayValue.get()).intValue()) || (!Intrinsics.areEqual((String) this.targetModeValue.get(), "Switch"))) {
                List list = this.prevTargetEntities;
                if (((Boolean) this.aacValue.get()).booleanValue()) {
                    IEntityLivingBase iEntityLivingBase2 = this.target;
                    if (iEntityLivingBase2 == null) {
                        Intrinsics.throwNpe();
                    }
                    entityId = iEntityLivingBase2.getEntityId();
                } else {
                    IEntityLivingBase iEntityLivingBase3 = this.currentTarget;
                    if (iEntityLivingBase3 == null) {
                        Intrinsics.throwNpe();
                    }
                    entityId = iEntityLivingBase3.getEntityId();
                }
                list.add(Integer.valueOf(entityId));
                this.switchTimer.reset();
            }
            List list2 = this.prevTargetEntities;
            if (((Boolean) this.aacValue.get()).booleanValue()) {
                IEntityLivingBase iEntityLivingBase4 = this.target;
                if (iEntityLivingBase4 == null) {
                    Intrinsics.throwNpe();
                }
                entityId2 = iEntityLivingBase4.getEntityId();
            } else {
                IEntityLivingBase iEntityLivingBase5 = this.currentTarget;
                if (iEntityLivingBase5 == null) {
                    Intrinsics.throwNpe();
                }
                entityId2 = iEntityLivingBase5.getEntityId();
            }
            list2.add(Integer.valueOf(entityId2));
            if (Intrinsics.areEqual(this.target, this.currentTarget)) {
                this.target = (IEntityLivingBase) null;
            }
        }
        if (z) {
            IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
            IClassProvider classProvider = WrapperImpl.INSTANCE.getClassProvider();
            IEntityPlayerSP thePlayer2 = LiquidBounce.INSTANCE.getWrapper().getMinecraft().getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            netHandler.addToSendQueue(classProvider.createCPacketEntityAction(thePlayer2, ICPacketEntityAction.WAction.OPEN_INVENTORY));
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final void updateTarget() {
        this.target = (IEntityLivingBase) null;
        int iIntValue = ((Number) this.hurtTimeValue.get()).intValue();
        float fFloatValue = ((Number) this.fovValue.get()).floatValue();
        boolean zEquals = StringsKt.equals((String) this.targetModeValue.get(), "Switch", true);
        ArrayList<IEntityLivingBase> arrayList = new ArrayList();
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        final IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        for (IEntity iEntity : theWorld.getLoadedEntityList()) {
            if (MinecraftInstance.classProvider.isEntityLivingBase(iEntity) && EntityUtils.isSelected(iEntity, true) && (!zEquals || !this.prevTargetEntities.contains(Integer.valueOf(iEntity.getEntityId())))) {
                double distanceToEntityBox = PlayerExtensionKt.getDistanceToEntityBox(thePlayer, iEntity);
                double rotationDifference = RotationUtils.getRotationDifference(iEntity);
                if (distanceToEntityBox <= getMaxRange() && (fFloatValue == 180.0f || rotationDifference <= fFloatValue)) {
                    if (iEntity.asEntityLivingBase().getHurtTime() <= iIntValue) {
                        arrayList.add(iEntity.asEntityLivingBase());
                    }
                }
            }
        }
        String str = (String) this.priorityValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1221262756:
                if (lowerCase.equals("health") && arrayList.size() > 1) {
                    CollectionsKt.sortWith(arrayList, new Comparator() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$updateTarget$$inlined$sortBy$2
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            return ComparisonsKt.compareValues(Float.valueOf(((IEntityLivingBase) obj).getHealth()), Float.valueOf(((IEntityLivingBase) obj2).getHealth()));
                        }
                    });
                    break;
                }
                break;
            case -962590849:
                if (lowerCase.equals("direction") && arrayList.size() > 1) {
                    CollectionsKt.sortWith(arrayList, new Comparator() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$updateTarget$$inlined$sortBy$3
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            return ComparisonsKt.compareValues(Double.valueOf(RotationUtils.getRotationDifference((IEntityLivingBase) obj)), Double.valueOf(RotationUtils.getRotationDifference((IEntityLivingBase) obj2)));
                        }
                    });
                    break;
                }
                break;
            case 93086015:
                if (lowerCase.equals("armor") && arrayList.size() > 1) {
                    CollectionsKt.sortWith(arrayList, new Comparator() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$updateTarget$$inlined$sortBy$5
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            return ComparisonsKt.compareValues(Integer.valueOf(-((IEntityLivingBase) obj).getTotalArmorValue()), Integer.valueOf(-((IEntityLivingBase) obj2).getTotalArmorValue()));
                        }
                    });
                    break;
                }
                break;
            case 288459765:
                if (lowerCase.equals("distance") && arrayList.size() > 1) {
                    CollectionsKt.sortWith(arrayList, new Comparator(thePlayer) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$updateTarget$$inlined$sortBy$1
                        final IEntityPlayerSP $thePlayer$inlined;

                        {
                            this.$thePlayer$inlined = thePlayer;
                        }

                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            return ComparisonsKt.compareValues(Double.valueOf(PlayerExtensionKt.getDistanceToEntityBox(this.$thePlayer$inlined, (IEntityLivingBase) obj)), Double.valueOf(PlayerExtensionKt.getDistanceToEntityBox(this.$thePlayer$inlined, (IEntityLivingBase) obj2)));
                        }
                    });
                    break;
                }
                break;
            case 886905078:
                if (lowerCase.equals("livingtime") && arrayList.size() > 1) {
                    CollectionsKt.sortWith(arrayList, new Comparator() { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$updateTarget$$inlined$sortBy$4
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            return ComparisonsKt.compareValues(Integer.valueOf(-((IEntityLivingBase) obj).getTicksExisted()), Integer.valueOf(-((IEntityLivingBase) obj2).getTicksExisted()));
                        }
                    });
                    break;
                }
                break;
        }
        for (IEntityLivingBase iEntityLivingBase : arrayList) {
            if (updateRotations(iEntityLivingBase)) {
                this.target = iEntityLivingBase;
                return;
            }
        }
        if (!this.prevTargetEntities.isEmpty()) {
            this.prevTargetEntities.clear();
            updateTarget();
        }
    }

    private final void attackEntity(IEntityLivingBase iEntityLivingBase) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        LiquidBounce.INSTANCE.getEventManager().callEvent(new AttackEvent(iEntityLivingBase));
        if (((Boolean) this.swingValue.get()).booleanValue()) {
        }
        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketUseEntity(iEntityLivingBase, ICPacketUseEntity.WAction.ATTACK));
        if (((Boolean) this.swingValue.get()).booleanValue()) {
            thePlayer.swingItem();
        }
        if (((Boolean) this.keepSprintValue.get()).booleanValue()) {
            if (((Boolean) this.stopkeepsprintinnoground.get()).booleanValue()) {
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer2.getOnGround()) {
                    if (thePlayer.getFallDistance() > 0.0f && !thePlayer.getOnGround() && !thePlayer.isOnLadder() && !thePlayer.isInWater() && !thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.BLINDNESS)) && !thePlayer.isRiding()) {
                        thePlayer.onCriticalHit(iEntityLivingBase);
                    }
                    if (MinecraftInstance.functions.getModifierForCreature(thePlayer.getHeldItem(), iEntityLivingBase.getCreatureAttribute()) > 0.0f) {
                        thePlayer.onEnchantmentCritical(iEntityLivingBase);
                    }
                } else {
                    thePlayer.setSprinting(false);
                }
            } else {
                if (thePlayer.getFallDistance() > 0.0f && !thePlayer.getOnGround() && !thePlayer.isOnLadder() && !thePlayer.isInWater() && !thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.BLINDNESS)) && !thePlayer.isRiding()) {
                    thePlayer.onCriticalHit(iEntityLivingBase);
                }
                if (MinecraftInstance.functions.getModifierForCreature(thePlayer.getHeldItem(), iEntityLivingBase.getCreatureAttribute()) > 0.0f) {
                    thePlayer.onEnchantmentCritical(iEntityLivingBase);
                }
            }
        } else if (MinecraftInstance.f157mc.getPlayerController().getCurrentGameType() != IWorldSettings.WGameType.SPECTATOR) {
            thePlayer.attackTargetEntityWithCurrentItem(iEntityLivingBase);
        }
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(Criticals.class);
        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.Criticals");
        }
        Criticals criticals = (Criticals) module;
        for (int i = 0; i <= 2; i++) {
            if ((thePlayer.getFallDistance() > 0.0f && !thePlayer.getOnGround() && !thePlayer.isOnLadder() && !thePlayer.isInWater() && !thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.BLINDNESS)) && thePlayer.getRidingEntity() == null) || (criticals.getState() && criticals.getMsTimer().hasTimePassed(((Number) criticals.getDelayValue().get()).intValue()) && !thePlayer.isInWater() && !thePlayer.isInLava() && !thePlayer.isInWeb())) {
                IEntityLivingBase iEntityLivingBase2 = this.target;
                if (iEntityLivingBase2 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer.onCriticalHit(iEntityLivingBase2);
            }
            IExtractedFunctions iExtractedFunctions = MinecraftInstance.functions;
            IItemStack heldItem = thePlayer.getHeldItem();
            IEntityLivingBase iEntityLivingBase3 = this.target;
            if (iEntityLivingBase3 == null) {
                Intrinsics.throwNpe();
            }
            if (iExtractedFunctions.getModifierForCreature(heldItem, iEntityLivingBase3.getCreatureAttribute()) > 0.0f || ((Boolean) this.fakeSharpValue.get()).booleanValue()) {
                IEntityLivingBase iEntityLivingBase4 = this.target;
                if (iEntityLivingBase4 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer.onEnchantmentCritical(iEntityLivingBase4);
            }
        }
        thePlayer.resetCooldown();
    }

    private final boolean updateRotations(IEntity iEntity) {
        if (((Number) this.maxTurnSpeed.get()).floatValue() <= 0.0f) {
            return true;
        }
        IAxisAlignedBB entityBoundingBox = iEntity.getEntityBoundingBox();
        if (StringsKt.equals((String) this.rotations.get(), "Vanilla", true)) {
            if (((Number) this.maxTurnSpeed.get()).floatValue() <= 0.0f) {
                return true;
            }
            if (((Boolean) this.predictValue.get()).booleanValue()) {
                entityBoundingBox = entityBoundingBox.offset((iEntity.getPosX() - iEntity.getPrevPosX()) * RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()), (iEntity.getPosY() - iEntity.getPrevPosY()) * RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()), (iEntity.getPosZ() - iEntity.getPrevPosZ()) * RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()));
            }
            IAxisAlignedBB iAxisAlignedBB = entityBoundingBox;
            boolean z = ((Boolean) this.outborderValue.get()).booleanValue() && !this.attackTimer.hasTimePassed(this.attackDelay / 2);
            boolean zBooleanValue = ((Boolean) this.randomCenterValue.get()).booleanValue();
            boolean zBooleanValue2 = ((Boolean) this.predictValue.get()).booleanValue();
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            VecRotation vecRotationSearchCenter = RotationUtils.searchCenter(iAxisAlignedBB, z, zBooleanValue, zBooleanValue2, PlayerExtensionKt.getDistanceToEntityBox(thePlayer, iEntity) < ((Number) this.throughWallsRangeValue.get()).doubleValue(), getMaxRange());
            if (vecRotationSearchCenter == null) {
                return false;
            }
            vecRotationSearchCenter.component1();
            Rotation rotationLimitAngleChange = RotationUtils.limitAngleChange(RotationUtils.serverRotation, vecRotationSearchCenter.component2(), (float) ((Math.random() * (((Number) this.maxTurnSpeed.get()).floatValue() - ((Number) this.minTurnSpeed.get()).floatValue())) + ((Number) this.minTurnSpeed.get()).doubleValue()));
            Intrinsics.checkExpressionValueIsNotNull(rotationLimitAngleChange, "RotationUtils.limitAngle\u2026rnSpeed.get()).toFloat())");
            if (((Boolean) this.silentRotationValue.get()).booleanValue()) {
                RotationUtils.setTargetRotation(rotationLimitAngleChange, ((Boolean) this.aacValue.get()).booleanValue() ? 15 : 0);
                return true;
            }
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            rotationLimitAngleChange.toPlayer(thePlayer2);
            return true;
        }
        if (StringsKt.equals((String) this.rotations.get(), "BackTrack", true)) {
            if (((Boolean) this.predictValue.get()).booleanValue()) {
                entityBoundingBox = entityBoundingBox.offset((iEntity.getPosX() - iEntity.getPrevPosX()) * RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()), (iEntity.getPosY() - iEntity.getPrevPosY()) * RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()), (iEntity.getPosZ() - iEntity.getPrevPosZ()) * RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()));
            }
            Rotation rotation = RotationUtils.serverRotation;
            IAxisAlignedBB iAxisAlignedBB2 = entityBoundingBox;
            WVec3 center = RotationUtils.getCenter(iEntity.getEntityBoundingBox());
            boolean zBooleanValue3 = ((Boolean) this.predictValue.get()).booleanValue();
            IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer3 == null) {
                Intrinsics.throwNpe();
            }
            Rotation rotationLimitAngleChange2 = RotationUtils.limitAngleChange(rotation, RotationUtils.OtherRotation(iAxisAlignedBB2, center, zBooleanValue3, PlayerExtensionKt.getDistanceToEntityBox(thePlayer3, iEntity) < ((Number) this.throughWallsRangeValue.get()).doubleValue(), getMaxRange()), (float) ((Math.random() * (((Number) this.maxTurnSpeed.get()).floatValue() - ((Number) this.minTurnSpeed.get()).floatValue())) + ((Number) this.minTurnSpeed.get()).doubleValue()));
            Intrinsics.checkExpressionValueIsNotNull(rotationLimitAngleChange2, "RotationUtils.limitAngle\u2026rnSpeed.get()).toFloat())");
            if (((Boolean) this.silentRotationValue.get()).booleanValue()) {
                RotationUtils.setTargetRotation(rotationLimitAngleChange2, ((Boolean) this.aacValue.get()).booleanValue() ? 15 : 0);
            } else {
                IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer4 == null) {
                    Intrinsics.throwNpe();
                }
                rotationLimitAngleChange2.toPlayer(thePlayer4);
                return true;
            }
        }
        if (StringsKt.equals((String) this.rotations.get(), "HytRotation", true)) {
            if (((Boolean) this.predictValue.get()).booleanValue()) {
                entityBoundingBox = entityBoundingBox.offset((iEntity.getPosX() - iEntity.getPrevPosX()) * RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()), (iEntity.getPosY() - iEntity.getPrevPosY()) * RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()), (iEntity.getPosZ() - iEntity.getPrevPosZ()) * RandomUtils.INSTANCE.nextFloat(((Number) this.minPredictSize.get()).floatValue(), ((Number) this.maxPredictSize.get()).floatValue()));
            }
            IAxisAlignedBB iAxisAlignedBB3 = entityBoundingBox;
            boolean z2 = ((Boolean) this.outborderValue.get()).booleanValue() && !this.attackTimer.hasTimePassed(this.attackDelay / 2);
            boolean zBooleanValue4 = ((Boolean) this.randomCenterValue.get()).booleanValue();
            boolean zBooleanValue5 = ((Boolean) this.predictValue.get()).booleanValue();
            IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer5 == null) {
                Intrinsics.throwNpe();
            }
            VecRotation vecRotationLockView = RotationUtils.lockView(iAxisAlignedBB3, z2, zBooleanValue4, zBooleanValue5, PlayerExtensionKt.getDistanceToEntityBox(thePlayer5, iEntity) < ((Number) this.throughWallsRangeValue.get()).doubleValue(), getMaxRange());
            if (vecRotationLockView == null) {
                return false;
            }
            Rotation rotationLimitAngleChange3 = RotationUtils.limitAngleChange(RotationUtils.serverRotation, vecRotationLockView.component2(), (float) ((Math.random() * (((Number) this.maxTurnSpeed.get()).floatValue() - ((Number) this.minTurnSpeed.get()).floatValue())) + ((Number) this.minTurnSpeed.get()).doubleValue()));
            Intrinsics.checkExpressionValueIsNotNull(rotationLimitAngleChange3, "RotationUtils.limitAngle\u2026rnSpeed.get()).toFloat())");
            if (((Boolean) this.silentRotationValue.get()).booleanValue()) {
                RotationUtils.setTargetRotation(rotationLimitAngleChange3, ((Boolean) this.aacValue.get()).booleanValue() ? 15 : 0);
                return true;
            }
            IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer6 == null) {
                Intrinsics.throwNpe();
            }
            rotationLimitAngleChange3.toPlayer(thePlayer6);
            return true;
        }
        return true;
    }

    private final void updateHitable() {
        if (((Number) this.maxTurnSpeed.get()).floatValue() <= 0.0f) {
            this.hitable = true;
            return;
        }
        double maxRange = getMaxRange();
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        IEntityPlayerSP iEntityPlayerSP = thePlayer;
        IEntityLivingBase iEntityLivingBase = this.target;
        if (iEntityLivingBase == null) {
            Intrinsics.throwNpe();
        }
        double dMin = Math.min(maxRange, PlayerExtensionKt.getDistanceToEntityBox(iEntityPlayerSP, iEntityLivingBase)) + 1.0d;
        if (((Boolean) this.raycastValue.get()).booleanValue()) {
            IEntity iEntityRaycastEntity = RaycastUtils.raycastEntity(dMin, new RaycastUtils.EntityFilter(this) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.KillAura$updateHitable$raycastedEntity$1
                final KillAura this$0;

                {
                    this.this$0 = this;
                }

                @Override // net.ccbluex.liquidbounce.utils.RaycastUtils.EntityFilter
                public boolean canRaycast(@Nullable IEntity iEntity) {
                    if (!((Boolean) this.this$0.livingRaycastValue.get()).booleanValue() || (MinecraftInstance.classProvider.isEntityLivingBase(iEntity) && !MinecraftInstance.classProvider.isEntityArmorStand(iEntity))) {
                        if (!EntityUtils.isSelected(iEntity, true) && !((Boolean) this.this$0.raycastIgnoredValue.get()).booleanValue()) {
                            if (((Boolean) this.this$0.aacValue.get()).booleanValue()) {
                                IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                                if (theWorld == null) {
                                    Intrinsics.throwNpe();
                                }
                                if (iEntity == null) {
                                    Intrinsics.throwNpe();
                                }
                                if (!theWorld.getEntitiesWithinAABBExcludingEntity(iEntity, iEntity.getEntityBoundingBox()).isEmpty()) {
                                }
                            }
                        }
                        return true;
                    }
                    return false;
                }
            });
            if (((Boolean) this.raycastValue.get()).booleanValue() && iEntityRaycastEntity != null && MinecraftInstance.classProvider.isEntityLivingBase(iEntityRaycastEntity) && (LiquidBounce.INSTANCE.getModuleManager().get(NoFriends.class).getState() || !MinecraftInstance.classProvider.isEntityPlayer(iEntityRaycastEntity) || !PlayerExtensionKt.isClientFriend(iEntityRaycastEntity.asEntityPlayer()))) {
                this.currentTarget = iEntityRaycastEntity.asEntityLivingBase();
            }
            this.hitable = ((Number) this.maxTurnSpeed.get()).floatValue() > 0.0f ? Intrinsics.areEqual(this.currentTarget, iEntityRaycastEntity) : true;
            return;
        }
        this.hitable = RotationUtils.isFaced(this.currentTarget, dMin);
    }

    private final void startBlocking(IEntity iEntity, boolean z) {
        if (((Number) this.blockRate.get()).intValue() <= 0 || new Random().nextInt(100) > ((Number) this.blockRate.get()).intValue()) {
            return;
        }
        if (z) {
            IEntity renderViewEntity = MinecraftInstance.f157mc.getRenderViewEntity();
            WVec3 positionEyes = renderViewEntity != null ? renderViewEntity.getPositionEyes(1.0f) : null;
            double collisionBorderSize = iEntity.getCollisionBorderSize();
            IAxisAlignedBB iAxisAlignedBBExpand = iEntity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
            Rotation rotation = RotationUtils.targetRotation;
            if (rotation == null) {
                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer == null) {
                    Intrinsics.throwNpe();
                }
                float rotationYaw = thePlayer.getRotationYaw();
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                rotation = new Rotation(rotationYaw, thePlayer2.getRotationPitch());
            }
            Rotation rotation2 = rotation;
            float fComponent1 = rotation2.component1();
            float fComponent2 = rotation2.component2();
            float fCos = (float) Math.cos(((-fComponent1) * 0.017453292f) - 3.1415927f);
            float fSin = (float) Math.sin(((-fComponent1) * 0.017453292f) - 3.1415927f);
            float f = -((float) Math.cos((-fComponent2) * 0.017453292f));
            float fSin2 = (float) Math.sin((-fComponent2) * 0.017453292f);
            double maxRange = getMaxRange();
            IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer3 == null) {
                Intrinsics.throwNpe();
            }
            double dMin = Math.min(maxRange, PlayerExtensionKt.getDistanceToEntityBox(thePlayer3, iEntity)) + 1.0d;
            if (positionEyes == null) {
                Intrinsics.throwNpe();
            }
            IMovingObjectPosition iMovingObjectPositionCalculateIntercept = iAxisAlignedBBExpand.calculateIntercept(positionEyes, new WVec3(positionEyes.getXCoord() + (fSin * f * dMin), positionEyes.getYCoord() + (fSin2 * dMin), positionEyes.getZCoord() + (fCos * f * dMin)));
            if (iMovingObjectPositionCalculateIntercept == null) {
                return;
            }
            WVec3 hitVec = iMovingObjectPositionCalculateIntercept.getHitVec();
            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketUseEntity(iEntity, new WVec3(hitVec.getXCoord() - iEntity.getPosX(), hitVec.getYCoord() - iEntity.getPosY(), hitVec.getZCoord() - iEntity.getPosZ())));
            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketUseEntity(iEntity, ICPacketUseEntity.WAction.INTERACT));
        }
        if (StringsKt.equals((String) this.autoBlockValue.get(), "Packet", true)) {
            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketTryUseItem(WEnumHand.MAIN_HAND));
        }
        if (StringsKt.equals((String) this.autoBlockValue.get(), "AfterTick", true)) {
            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketTryUseItem(WEnumHand.MAIN_HAND));
            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketTryUseItem(WEnumHand.OFF_HAND));
        }
        if (StringsKt.equals((String) this.autoBlockValue.get(), "UseItem", true)) {
            MinecraftInstance.f157mc.getGameSettings().setKeyBindState(MinecraftInstance.f157mc.getGameSettings().getKeyBindUseItem().getKeyCode(), true);
        }
        this.blockingStatus = true;
    }

    private final void stopBlocking() {
        if (this.blockingStatus) {
            if (StringsKt.equals((String) this.autoBlockValue.get(), "UseItem", true)) {
                MinecraftInstance.f157mc.getGameSettings().setKeyBindState(MinecraftInstance.f157mc.getGameSettings().getKeyBindUseItem().getKeyCode(), false);
            } else {
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerDigging(ICPacketPlayerDigging.WAction.RELEASE_USE_ITEM, WBlockPos.Companion.getORIGIN(), MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.DOWN)));
            }
            this.blockingStatus = false;
        }
    }

    private final boolean getCancelRun() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (!thePlayer.isSpectator()) {
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            if (isAlive(thePlayer2) && !LiquidBounce.INSTANCE.getModuleManager().get(Blink.class).getState() && !LiquidBounce.INSTANCE.getModuleManager().get(FreeCam.class).getState()) {
                return false;
            }
        }
        return true;
    }

    private final boolean isAlive(IEntityLivingBase iEntityLivingBase) {
        return (iEntityLivingBase.isEntityAlive() && iEntityLivingBase.getHealth() > 0.0f) || (((Boolean) this.aacValue.get()).booleanValue() && iEntityLivingBase.getHurtTime() > 5);
    }

    private final boolean getCanBlock() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.getHeldItem() != null) {
            IClassProvider iClassProvider = MinecraftInstance.classProvider;
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            IItemStack heldItem = thePlayer2.getHeldItem();
            if (heldItem == null) {
                Intrinsics.throwNpe();
            }
            if (iClassProvider.isItemSword(heldItem.getItem())) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final float getMaxRange() {
        float fFloatValue;
        if (((Boolean) this.norangeairban.get()).booleanValue()) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            fFloatValue = !thePlayer.getOnGround() ? 2.9f : ((Number) this.rangeValue.get()).floatValue();
        }
        return Math.max(fFloatValue, ((Number) this.throughWallsRangeValue.get()).floatValue());
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0054  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final float getRange(IEntity iEntity) {
        float fFloatValue;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (PlayerExtensionKt.getDistanceToEntityBox(thePlayer, iEntity) < ((Number) this.throughWallsRangeValue.get()).doubleValue()) {
            fFloatValue = ((Number) this.throughWallsRangeValue.get()).floatValue();
        } else if (((Boolean) this.norangeairban.get()).booleanValue()) {
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            fFloatValue = !thePlayer2.getOnGround() ? 2.9f : ((Number) this.rangeValue.get()).floatValue();
        }
        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer3 == null) {
            Intrinsics.throwNpe();
        }
        return fFloatValue - (thePlayer3.getSprinting() ? ((Number) this.rangeSprintReducementValue.get()).floatValue() : 0.0f);
    }

    @NotNull
    public String getTag() {
        return ((String) this.targetModeValue.get()) + "/" + ((String) this.autoBlockValue.get());
    }
}
