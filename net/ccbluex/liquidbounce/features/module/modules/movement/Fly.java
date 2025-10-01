package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.enums.EnumFacingType;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemStack;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketPlayer;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.event.BlockBBEvent;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TickTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.input.Keyboard;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u009c\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0007\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\t\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u00109\u001a\u00020\tH\u0002J\b\u0010:\u001a\u00020;H\u0002J\u0010\u0010<\u001a\u00020;2\u0006\u0010=\u001a\u00020>H\u0007J\b\u0010?\u001a\u00020;H\u0016J\b\u0010@\u001a\u00020;H\u0016J\u0010\u0010A\u001a\u00020;2\u0006\u0010B\u001a\u00020CH\u0007J\u0010\u0010D\u001a\u00020;2\u0006\u0010=\u001a\u00020EH\u0007J\u0010\u0010F\u001a\u00020;2\u0006\u0010=\u001a\u00020GH\u0007J\u0010\u0010H\u001a\u00020;2\u0006\u0010=\u001a\u00020IH\u0007J\u0012\u0010J\u001a\u00020;2\b\u0010=\u001a\u0004\u0018\u00010KH\u0007J\u0010\u0010L\u001a\u00020;2\u0006\u0010B\u001a\u00020MH\u0007J\u0012\u0010N\u001a\u00020;2\b\u0010=\u001a\u0004\u0018\u00010OH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0018\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0019\u001a\u00020\u0017X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001a\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001e\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010 \u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010!\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\"\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010#\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010$\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010%\u001a\u00020&X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010'\u001a\u00020(\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b)\u0010*R\u000e\u0010+\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010,\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010-\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010.\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010/\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00100\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00101\u001a\u00020\tX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u00102\u001a\u0002038VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b4\u00105R\u000e\u00106\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00107\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u00108\u001a\u00020\u0013X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006P"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Fly;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aac3delay", "", "aac3glideDelay", "aacFast", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "aacJump", "", "aacMotion", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "aacMotion2", "aacSpeedValue", "boostHypixelState", "cubecraft2TickTimer", "Lnet/ccbluex/liquidbounce/utils/timer/TickTimer;", "cubecraftTeleportTickTimer", "failedStart", "", "flyTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "freeHypixelPitch", "", "freeHypixelTimer", "freeHypixelYaw", "groundTimer", "hypixelBoost", "hypixelBoostDelay", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "hypixelBoostTimer", "hypixelTimer", "lastDistance", "markValue", "mineSecureVClipTimer", "mineplexSpeedValue", "mineplexTimer", "minesuchtTP", "", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "moveSpeed", "ncpMotionValue", "neruxVaceTicks", "noFlag", "noPacketModify", "spartanTimer", "startY", "tag", "", "getTag", "()Ljava/lang/String;", "vanillaKickBypassValue", "vanillaSpeedValue", "wasDead", "calculateGround", "handleVanillaKickBypass", "", "onBB", "event", "Lnet/ccbluex/liquidbounce/event/BlockBBEvent;", "onDisable", "onEnable", "onJump", "e", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMotion", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onPacket", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onRender3D", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onStep", "Lnet/ccbluex/liquidbounce/event/StepEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Fly", description = "Allows you to fly in survival mode.", category = ModuleCategory.MOVEMENT, keyBind = OPCode.WORD_BEGIN)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/Fly.class */
public final class Fly extends Module {
    private double startY;
    private boolean noPacketModify;
    private double aacJump;
    private int aac3delay;
    private int aac3glideDelay;
    private boolean noFlag;
    private long minesuchtTP;
    private boolean wasDead;
    private double moveSpeed;
    private double lastDistance;
    private boolean failedStart;
    private float freeHypixelYaw;
    private float freeHypixelPitch;

    @NotNull
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Vanilla", "SmoothVanilla", "NCP", "OldNCP", "AAC1.9.10", "AAC3.0.5", "AAC3.1.6-Gomme", "AAC3.3.12", "AAC3.3.12-Glide", "AAC3.3.13", "CubeCraft", "Hypixel", "BoostHypixel", "FreeHypixel", "Rewinside", "TeleportRewinside", "Mineplex", "NeruxVace", "Minesucht", "Spartan", "Spartan2", "BugSpartan", "MineSecure", "HawkEye", "HAC", "WatchCat", "Jetpack", "KeepAlive", "Flag"}, "Vanilla");
    private final FloatValue vanillaSpeedValue = new FloatValue("VanillaSpeed", 2.0f, 0.0f, 5.0f);
    private final BoolValue vanillaKickBypassValue = new BoolValue("VanillaKickBypass", false);
    private final FloatValue ncpMotionValue = new FloatValue("NCPMotion", 0.0f, 0.0f, 1.0f);
    private final FloatValue aacSpeedValue = new FloatValue("AAC1.9.10-Speed", 0.3f, 0.0f, 1.0f);
    private final BoolValue aacFast = new BoolValue("AAC3.0.5-Fast", true);
    private final FloatValue aacMotion = new FloatValue("AAC3.3.12-Motion", 10.0f, 0.1f, 10.0f);
    private final FloatValue aacMotion2 = new FloatValue("AAC3.3.13-Motion", 10.0f, 0.1f, 10.0f);
    private final BoolValue hypixelBoost = new BoolValue("Hypixel-Boost", true);
    private final IntegerValue hypixelBoostDelay = new IntegerValue("Hypixel-BoostDelay", 1200, 0, 2000);
    private final FloatValue hypixelBoostTimer = new FloatValue("Hypixel-BoostTimer", 1.0f, 0.0f, 5.0f);
    private final FloatValue mineplexSpeedValue = new FloatValue("MineplexSpeed", 1.0f, 0.5f, 10.0f);
    private final IntegerValue neruxVaceTicks = new IntegerValue("NeruxVace-Ticks", 6, 0, 20);
    private final BoolValue markValue = new BoolValue("Mark", true);
    private final MSTimer flyTimer = new MSTimer();
    private final MSTimer groundTimer = new MSTimer();
    private final MSTimer mineSecureVClipTimer = new MSTimer();
    private final TickTimer spartanTimer = new TickTimer();
    private final MSTimer mineplexTimer = new MSTimer();
    private final TickTimer hypixelTimer = new TickTimer();
    private int boostHypixelState = 1;
    private final TickTimer cubecraft2TickTimer = new TickTimer();
    private final TickTimer cubecraftTeleportTickTimer = new TickTimer();
    private final TickTimer freeHypixelTimer = new TickTimer();

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/Fly$WhenMappings.class */
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0 = new int[EventState.values().length];

        static {
            $EnumSwitchMapping$0[EventState.PRE.ordinal()] = 1;
            $EnumSwitchMapping$0[EventState.POST.ordinal()] = 2;
        }
    }

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    public void onEnable() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            this.flyTimer.reset();
            this.noPacketModify = true;
            double posX = thePlayer.getPosX();
            double posY = thePlayer.getPosY();
            double posZ = thePlayer.getPosZ();
            String str = (String) this.modeValue.get();
            Fly fly = this;
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = str.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case -1693125473:
                    if (lowerCase.equals("bugspartan")) {
                        for (int i = 0; i <= 64; i++) {
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(posX, posY + 0.049d, posZ, false));
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(posX, posY, posZ, false));
                        }
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(posX, posY + 0.1d, posZ, true));
                        thePlayer.setMotionX(thePlayer.getMotionX() * 0.1d);
                        thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.1d);
                        thePlayer.swingItem();
                        break;
                    }
                    break;
                case -1014303276:
                    if (lowerCase.equals("oldncp") && thePlayer.getOnGround()) {
                        for (int i2 = 0; i2 <= 3; i2++) {
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(posX, posY + 1.01d, posZ, false));
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(posX, posY, posZ, false));
                        }
                        thePlayer.jump();
                        thePlayer.swingItem();
                        break;
                    }
                    break;
                case -926713373:
                    if (lowerCase.equals("infinitycubecraft")) {
                        ClientUtils.displayChatMessage("\u00a78[\u00a7c\u00a7lCubeCraft-\u00a7a\u00a7lFly\u00a78] \u00a7aPlace a block before landing.");
                        break;
                    }
                    break;
                case 108891:
                    if (lowerCase.equals("ncp") && thePlayer.getOnGround()) {
                        for (int i3 = 0; i3 <= 64; i3++) {
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(posX, posY + 0.049d, posZ, false));
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(posX, posY, posZ, false));
                        }
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(posX, posY + 0.1d, posZ, true));
                        thePlayer.setMotionX(thePlayer.getMotionX() * 0.1d);
                        thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.1d);
                        thePlayer.swingItem();
                        break;
                    }
                    break;
                case 502330237:
                    if (lowerCase.equals("infinityvcubecraft")) {
                        ClientUtils.displayChatMessage("\u00a78[\u00a7c\u00a7lCubeCraft-\u00a7a\u00a7lFly\u00a78] \u00a7aPlace a block before landing.");
                        thePlayer.setPosition(thePlayer.getPosX(), thePlayer.getPosY() + 2.0d, thePlayer.getPosZ());
                        break;
                    }
                    break;
                case 1814517522:
                    if (lowerCase.equals("boosthypixel") && thePlayer.getOnGround()) {
                        for (int i4 = 0; i4 <= 9; i4++) {
                            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ(), true));
                        }
                        double d = 3.0125d;
                        while (true) {
                            double d2 = d;
                            if (d2 > 0.0d) {
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + 0.0624986421d, thePlayer.getPosZ(), false));
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + 0.0625d, thePlayer.getPosZ(), false));
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + 0.0624986421d, thePlayer.getPosZ(), false));
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + 1.3579E-6d, thePlayer.getPosZ(), false));
                                d = d2 - 0.0624986421d;
                            } else {
                                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ(), true));
                                thePlayer.jump();
                                thePlayer.setPosY(thePlayer.getPosY() + 0.41999998688697815d);
                                fly.boostHypixelState = 1;
                                fly.moveSpeed = 0.1d;
                                fly.lastDistance = 0.0d;
                                fly.failedStart = false;
                                break;
                            }
                        }
                    }
                    break;
            }
            this.startY = thePlayer.getPosY();
            this.aacJump = -3.8d;
            this.noPacketModify = false;
            if (StringsKt.equals(str, "freehypixel", true)) {
                this.freeHypixelTimer.reset();
                thePlayer.setPositionAndUpdate(thePlayer.getPosX(), thePlayer.getPosY() + 0.42d, thePlayer.getPosZ());
                this.freeHypixelYaw = thePlayer.getRotationYaw();
                this.freeHypixelPitch = thePlayer.getRotationPitch();
            }
        }
    }

    public void onDisable() {
        this.wasDead = false;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            this.noFlag = false;
            String str = (String) this.modeValue.get();
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String upperCase = str.toUpperCase();
            Intrinsics.checkExpressionValueIsNotNull(upperCase, "(this as java.lang.String).toUpperCase()");
            if (!StringsKt.startsWith$default(upperCase, "AAC", false, 2, (Object) null) && !StringsKt.equals(str, "Hypixel", true) && !StringsKt.equals(str, "CubeCraft", true)) {
                thePlayer.setMotionX(0.0d);
                thePlayer.setMotionY(0.0d);
                thePlayer.setMotionZ(0.0d);
            }
            thePlayer.getCapabilities().setFlying(false);
            MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
            thePlayer.setSpeedInAir(0.02f);
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent updateEvent) {
        float fFloatValue = ((Number) this.vanillaSpeedValue.get()).floatValue();
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        Fly fly = this;
        String str = (String) fly.modeValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -2011701869:
                if (lowerCase.equals("spartan")) {
                    thePlayer.setMotionY(0.0d);
                    fly.spartanTimer.update();
                    if (fly.spartanTimer.hasTimePassed(12)) {
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + 8.0d, thePlayer.getPosZ(), true));
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() - 8.0d, thePlayer.getPosZ(), true));
                        fly.spartanTimer.reset();
                        break;
                    }
                }
                break;
            case -1848285483:
                if (lowerCase.equals("teleportrewinside")) {
                    WVec3 wVec3 = new WVec3(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ());
                    float f = -thePlayer.getRotationYaw();
                    float f2 = -thePlayer.getRotationPitch();
                    WVec3 wVec32 = new WVec3((Math.sin(Math.toRadians(f)) * Math.cos(Math.toRadians(f2)) * 9.9d) + wVec3.getXCoord(), (Math.sin(Math.toRadians(f2)) * 9.9d) + wVec3.getYCoord(), (Math.cos(Math.toRadians(f)) * Math.cos(Math.toRadians(f2)) * 9.9d) + wVec3.getZCoord());
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(wVec32.getXCoord(), thePlayer.getPosY() + 2.0d, wVec32.getZCoord(), true));
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(wVec3.getXCoord(), thePlayer.getPosY() + 2.0d, wVec3.getZCoord(), true));
                    thePlayer.setMotionY(0.0d);
                    break;
                }
                break;
            case -1745954712:
                if (lowerCase.equals("keepalive")) {
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketKeepAlive());
                    thePlayer.getCapabilities().setFlying(false);
                    thePlayer.setMotionY(0.0d);
                    thePlayer.setMotionX(0.0d);
                    thePlayer.setMotionZ(0.0d);
                    if (MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                        thePlayer.setMotionY(thePlayer.getMotionY() + fFloatValue);
                    }
                    if (MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                        thePlayer.setMotionY(thePlayer.getMotionY() - fFloatValue);
                    }
                    MovementUtils.strafe(fFloatValue);
                    break;
                }
                break;
            case -1706751950:
                if (lowerCase.equals("jetpack") && MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                    thePlayer.setMotionY(thePlayer.getMotionY() + 0.15d);
                    thePlayer.setMotionX(thePlayer.getMotionX() * 1.1d);
                    thePlayer.setMotionZ(thePlayer.getMotionZ() * 1.1d);
                    break;
                }
                break;
            case -1693125473:
                if (lowerCase.equals("bugspartan")) {
                    thePlayer.getCapabilities().setFlying(false);
                    thePlayer.setMotionY(0.0d);
                    thePlayer.setMotionX(0.0d);
                    thePlayer.setMotionZ(0.0d);
                    if (MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                        thePlayer.setMotionY(thePlayer.getMotionY() + fFloatValue);
                    }
                    if (MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                        thePlayer.setMotionY(thePlayer.getMotionY() - fFloatValue);
                    }
                    MovementUtils.strafe(fFloatValue);
                    break;
                }
                break;
            case -1362669950:
                if (lowerCase.equals("mineplex")) {
                    if (thePlayer.getInventory().getCurrentItemInHand() == null) {
                        if (MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown() && fly.mineplexTimer.hasTimePassed(100L)) {
                            thePlayer.setPosition(thePlayer.getPosX(), thePlayer.getPosY() + 0.6d, thePlayer.getPosZ());
                            fly.mineplexTimer.reset();
                        }
                        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer2 == null) {
                            Intrinsics.throwNpe();
                        }
                        if (thePlayer2.isSneaking() && fly.mineplexTimer.hasTimePassed(100L)) {
                            thePlayer.setPosition(thePlayer.getPosX(), thePlayer.getPosY() - 0.6d, thePlayer.getPosZ());
                            fly.mineplexTimer.reset();
                        }
                        double posX = thePlayer.getPosX();
                        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer3 == null) {
                            Intrinsics.throwNpe();
                        }
                        WBlockPos wBlockPos = new WBlockPos(posX, thePlayer3.getEntityBoundingBox().getMinY() - 1.0d, thePlayer.getPosZ());
                        WVec3 wVec33 = new WVec3(wBlockPos);
                        WVec3 wVec34 = new WVec3(wVec33.getXCoord() + 0.4d, wVec33.getYCoord() + 0.4d, wVec33.getZCoord() + 0.4d);
                        WVec3 wVec35 = new WVec3(MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.UP).getDirectionVec());
                        WVec3 wVec36 = new WVec3(wVec34.getXCoord() + wVec35.getXCoord(), wVec34.getYCoord() + wVec35.getYCoord(), wVec34.getZCoord() + wVec35.getZCoord());
                        IPlayerControllerMP playerController = MinecraftInstance.f157mc.getPlayerController();
                        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                        if (theWorld == null) {
                            Intrinsics.throwNpe();
                        }
                        IItemStack currentItemInHand = thePlayer.getInventory().getCurrentItemInHand();
                        if (currentItemInHand == null) {
                            Intrinsics.throwNpe();
                        }
                        playerController.onPlayerRightClick(thePlayer, theWorld, currentItemInHand, wBlockPos, MinecraftInstance.classProvider.getEnumFacing(EnumFacingType.UP), new WVec3(wVec36.getXCoord() * 0.4000000059604645d, wVec36.getYCoord() * 0.4000000059604645d, wVec36.getZCoord() * 0.4000000059604645d));
                        MovementUtils.strafe(0.27f);
                        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f + ((Number) fly.mineplexSpeedValue.get()).floatValue());
                        break;
                    } else {
                        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
                        fly.setState(false);
                        ClientUtils.displayChatMessage("\u00a78[\u00a7c\u00a7lMineplex-\u00a7a\u00a7lFly\u00a78] \u00a7aSelect an empty slot to fly.");
                        break;
                    }
                }
                break;
            case -1031473397:
                if (lowerCase.equals("cubecraft")) {
                    MinecraftInstance.f157mc.getTimer().setTimerSpeed(0.6f);
                    fly.cubecraftTeleportTickTimer.update();
                    break;
                }
                break;
            case -1014303276:
                if (lowerCase.equals("oldncp")) {
                    if (fly.startY > thePlayer.getPosY()) {
                        thePlayer.setMotionY(-1.0E-33d);
                    }
                    if (MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                        thePlayer.setMotionY(-0.2d);
                    }
                    if (MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown() && thePlayer.getPosY() < fly.startY - 0.1d) {
                        thePlayer.setMotionY(0.2d);
                    }
                    MovementUtils.strafe$default(0.0f, 1, null);
                    break;
                }
                break;
            case -385327063:
                if (lowerCase.equals("freehypixel")) {
                    if (fly.freeHypixelTimer.hasTimePassed(10)) {
                        thePlayer.getCapabilities().setFlying(true);
                        break;
                    } else {
                        thePlayer.setRotationYaw(fly.freeHypixelYaw);
                        thePlayer.setRotationPitch(fly.freeHypixelPitch);
                        thePlayer.setMotionY(0.0d);
                        thePlayer.setMotionZ(thePlayer.getMotionY());
                        thePlayer.setMotionX(thePlayer.getMotionZ());
                        if (fly.startY == new BigDecimal(thePlayer.getPosY()).setScale(3, RoundingMode.HALF_DOWN).doubleValue()) {
                            fly.freeHypixelTimer.update();
                            break;
                        }
                    }
                }
                break;
            case -321358:
                if (lowerCase.equals("aac3.3.12-glide")) {
                    if (!thePlayer.getOnGround()) {
                        fly.aac3glideDelay++;
                    }
                    if (fly.aac3glideDelay == 2) {
                        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
                    }
                    if (fly.aac3glideDelay == 12) {
                        MinecraftInstance.f157mc.getTimer().setTimerSpeed(0.1f);
                    }
                    if (fly.aac3glideDelay >= 12 && !thePlayer.getOnGround()) {
                        fly.aac3glideDelay = 0;
                        thePlayer.setMotionY(0.015d);
                        break;
                    }
                }
                break;
            case 103050:
                if (lowerCase.equals("hac")) {
                    thePlayer.setMotionX(thePlayer.getMotionX() * 0.8d);
                    thePlayer.setMotionZ(thePlayer.getMotionZ() * 0.8d);
                    thePlayer.setMotionY(thePlayer.getMotionY() <= -0.42d ? 0.42d : -0.42d);
                    break;
                }
                break;
            case 108891:
                if (lowerCase.equals("ncp")) {
                    thePlayer.setMotionY(-((Number) fly.ncpMotionValue.get()).floatValue());
                    if (MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                        thePlayer.setMotionY(-0.5d);
                    }
                    MovementUtils.strafe$default(0.0f, 1, null);
                    break;
                }
                break;
            case 3145580:
                if (lowerCase.equals("flag")) {
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosLook(thePlayer.getPosX() + (thePlayer.getMotionX() * 999.0d), (thePlayer.getPosY() + (MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown() ? 1.5624d : 1.0E-8d)) - (MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak().isKeyDown() ? 0.0624d : 2.0E-8d), thePlayer.getPosZ() + (thePlayer.getMotionZ() * 999.0d), thePlayer.getRotationYaw(), thePlayer.getRotationPitch(), true));
                    MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosLook(thePlayer.getPosX() + (thePlayer.getMotionX() * 999.0d), thePlayer.getPosY() - 6969.0d, thePlayer.getPosZ() + (thePlayer.getMotionZ() * 999.0d), thePlayer.getRotationYaw(), thePlayer.getRotationPitch(), true));
                    thePlayer.setPosition(thePlayer.getPosX() + (thePlayer.getMotionX() * 11.0d), thePlayer.getPosY(), thePlayer.getPosZ() + (thePlayer.getMotionZ() * 11.0d));
                    thePlayer.setMotionY(0.0d);
                    break;
                }
                break;
            case 65876907:
                if (lowerCase.equals("aac3.1.6-gomme")) {
                    thePlayer.getCapabilities().setFlying(true);
                    if (fly.aac3delay == 2) {
                        thePlayer.setMotionY(thePlayer.getMotionY() + 0.05d);
                    } else if (fly.aac3delay > 2) {
                        thePlayer.setMotionY(thePlayer.getMotionY() - 0.05d);
                        fly.aac3delay = 0;
                    }
                    fly.aac3delay++;
                    if (!fly.noFlag) {
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ(), thePlayer.getOnGround()));
                    }
                    if (thePlayer.getPosY() <= 0.0d) {
                        fly.noFlag = true;
                        break;
                    }
                }
                break;
            case 233102203:
                if (lowerCase.equals("vanilla")) {
                    thePlayer.getCapabilities().setFlying(false);
                    thePlayer.setMotionY(0.0d);
                    thePlayer.setMotionX(0.0d);
                    thePlayer.setMotionZ(0.0d);
                    if (MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                        thePlayer.setMotionY(thePlayer.getMotionY() + fFloatValue);
                    }
                    if (MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                        thePlayer.setMotionY(thePlayer.getMotionY() - fFloatValue);
                    }
                    MovementUtils.strafe(fFloatValue);
                    fly.handleVanillaKickBypass();
                    break;
                }
                break;
            case 238938827:
                if (lowerCase.equals("neruxvace")) {
                    if (!thePlayer.getOnGround()) {
                        fly.aac3glideDelay++;
                    }
                    if (fly.aac3glideDelay >= ((Number) fly.neruxVaceTicks.get()).intValue() && !thePlayer.getOnGround()) {
                        fly.aac3glideDelay = 0;
                        thePlayer.setMotionY(0.015d);
                        break;
                    }
                }
                break;
            case 325225305:
                if (lowerCase.equals("aac3.0.5")) {
                    if (fly.aac3delay == 2) {
                        thePlayer.setMotionY(0.1d);
                    } else if (fly.aac3delay > 2) {
                        fly.aac3delay = 0;
                    }
                    if (((Boolean) fly.aacFast.get()).booleanValue()) {
                        if (thePlayer.getMovementInput().getMoveStrafe() == 0.0f) {
                            thePlayer.setJumpMovementFactor(0.08f);
                        } else {
                            thePlayer.setJumpMovementFactor(0.0f);
                        }
                    }
                    fly.aac3delay++;
                    break;
                }
                break;
            case 518567306:
                if (lowerCase.equals("minesecure")) {
                    thePlayer.getCapabilities().setFlying(false);
                    if (!MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                        thePlayer.setMotionY(-0.01d);
                    }
                    thePlayer.setMotionX(0.0d);
                    thePlayer.setMotionZ(0.0d);
                    MovementUtils.strafe(fFloatValue);
                    if (fly.mineSecureVClipTimer.hasTimePassed(150L) && MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + 5.0d, thePlayer.getPosZ(), false));
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(0.5d, -1000.0d, 0.5d, false));
                        double radians = Math.toRadians(thePlayer.getRotationYaw());
                        thePlayer.setPosition(thePlayer.getPosX() + ((-Math.sin(radians)) * 0.4d), thePlayer.getPosY(), thePlayer.getPosZ() + (Math.cos(radians) * 0.4d));
                        fly.mineSecureVClipTimer.reset();
                        break;
                    }
                }
                break;
            case 545150119:
                if (lowerCase.equals("watchcat")) {
                    MovementUtils.strafe(0.15f);
                    IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer4 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer4.setSprinting(true);
                    if (thePlayer.getPosY() >= fly.startY + 2.0d) {
                        if (fly.startY > thePlayer.getPosY()) {
                            MovementUtils.strafe(0.0f);
                            break;
                        }
                    } else {
                        thePlayer.setMotionY(Math.random() * 0.5d);
                        break;
                    }
                }
                break;
            case 701317508:
                if (lowerCase.equals("hawkeye")) {
                    thePlayer.setMotionY(thePlayer.getMotionY() <= -0.42d ? 0.42d : -0.42d);
                    break;
                }
                break;
            case 709940890:
                if (lowerCase.equals("minesucht")) {
                    double posX2 = thePlayer.getPosX();
                    double posY = thePlayer.getPosY();
                    double posZ = thePlayer.getPosZ();
                    if (MinecraftInstance.f157mc.getGameSettings().getKeyBindForward().isKeyDown()) {
                        if (System.currentTimeMillis() - fly.minesuchtTP > 99) {
                            WVec3 positionEyes = thePlayer.getPositionEyes(0.0f);
                            IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer5 == null) {
                                Intrinsics.throwNpe();
                            }
                            WVec3 look = thePlayer5.getLook(0.0f);
                            WVec3 wVec37 = new WVec3(positionEyes.getXCoord() + (look.getXCoord() * 7.0d), positionEyes.getYCoord() + (look.getYCoord() * 7.0d), positionEyes.getZCoord() + (look.getZCoord() * 7.0d));
                            if (thePlayer.getFallDistance() > 0.8d) {
                                thePlayer.getSendQueue().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(posX2, posY + 50.0d, posZ, false));
                                IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer6 == null) {
                                    Intrinsics.throwNpe();
                                }
                                thePlayer6.fall(100.0f, 100.0f);
                                thePlayer.setFallDistance(0.0f);
                                thePlayer.getSendQueue().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(posX2, posY + 20.0d, posZ, true));
                            }
                            thePlayer.getSendQueue().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(wVec37.getXCoord(), thePlayer.getPosY() + 50.0d, wVec37.getZCoord(), true));
                            thePlayer.getSendQueue().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(posX2, posY, posZ, false));
                            thePlayer.getSendQueue().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(wVec37.getXCoord(), posY, wVec37.getZCoord(), true));
                            thePlayer.getSendQueue().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(posX2, posY, posZ, false));
                            fly.minesuchtTP = System.currentTimeMillis();
                            break;
                        } else {
                            thePlayer.getSendQueue().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ(), false));
                            thePlayer.getSendQueue().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(posX2, posY, posZ, true));
                            break;
                        }
                    }
                }
                break;
            case 1381910549:
                if (lowerCase.equals("hypixel")) {
                    int iIntValue = ((Number) fly.hypixelBoostDelay.get()).intValue();
                    if (((Boolean) fly.hypixelBoost.get()).booleanValue() && !fly.flyTimer.hasTimePassed(iIntValue)) {
                        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f + (((Number) fly.hypixelBoostTimer.get()).floatValue() * (fly.flyTimer.hasTimeLeft(iIntValue) / iIntValue)));
                    }
                    fly.hypixelTimer.update();
                    if (fly.hypixelTimer.hasTimePassed(2)) {
                        thePlayer.setPosition(thePlayer.getPosX(), thePlayer.getPosY() + 1.0E-5d, thePlayer.getPosZ());
                        fly.hypixelTimer.reset();
                        break;
                    }
                }
                break;
            case 1435059604:
                if (lowerCase.equals("aac1.9.10")) {
                    if (MinecraftInstance.f157mc.getGameSettings().getKeyBindJump().isKeyDown()) {
                        fly.aacJump += 0.2d;
                    }
                    if (MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                        fly.aacJump -= 0.2d;
                    }
                    if (fly.startY + fly.aacJump > thePlayer.getPosY()) {
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayer(true));
                        thePlayer.setMotionY(0.8d);
                        MovementUtils.strafe(((Number) fly.aacSpeedValue.get()).floatValue());
                    }
                    MovementUtils.strafe$default(0.0f, 1, null);
                    break;
                }
                break;
            case 1457669645:
                if (lowerCase.equals("smoothvanilla")) {
                    thePlayer.getCapabilities().setFlying(true);
                    fly.handleVanillaKickBypass();
                    break;
                }
                break;
            case 1492139162:
                if (lowerCase.equals("aac3.3.12")) {
                    if (thePlayer.getPosY() < -70.0d) {
                        thePlayer.setMotionY(((Number) fly.aacMotion.get()).floatValue());
                    }
                    MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
                    if (Keyboard.isKeyDown(29)) {
                        MinecraftInstance.f157mc.getTimer().setTimerSpeed(0.2f);
                        MinecraftInstance.f157mc.setRightClickDelayTimer(0);
                        break;
                    }
                }
                break;
            case 1492139163:
                if (lowerCase.equals("aac3.3.13")) {
                    if (thePlayer.isDead()) {
                        fly.wasDead = true;
                    }
                    if (fly.wasDead || thePlayer.getOnGround()) {
                        fly.wasDead = false;
                        thePlayer.setMotionY(((Number) fly.aacMotion2.get()).floatValue());
                        thePlayer.setOnGround(false);
                    }
                    MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
                    if (Keyboard.isKeyDown(29)) {
                        MinecraftInstance.f157mc.getTimer().setTimerSpeed(0.2f);
                        MinecraftInstance.f157mc.setRightClickDelayTimer(0);
                        break;
                    }
                }
                break;
            case 2061751551:
                if (lowerCase.equals("spartan2")) {
                    MovementUtils.strafe(0.264f);
                    if (thePlayer.getTicksExisted() % 8 == 0) {
                        thePlayer.getSendQueue().addToSendQueue(MinecraftInstance.classProvider.createCPacketPlayerPosition(thePlayer.getPosX(), thePlayer.getPosY() + 10.0d, thePlayer.getPosZ(), true));
                        break;
                    }
                }
                break;
        }
        Unit unit = Unit.INSTANCE;
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (StringsKt.equals((String) this.modeValue.get(), "boosthypixel", true)) {
            switch (WhenMappings.$EnumSwitchMapping$0[event.getEventState().ordinal()]) {
                case 1:
                    this.hypixelTimer.update();
                    if (this.hypixelTimer.hasTimePassed(2)) {
                        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer == null) {
                            Intrinsics.throwNpe();
                        }
                        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer2 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posX = thePlayer2.getPosX();
                        IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer3 == null) {
                            Intrinsics.throwNpe();
                        }
                        double posY = thePlayer3.getPosY() + 1.0E-5d;
                        IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer4 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer.setPosition(posX, posY, thePlayer4.getPosZ());
                        this.hypixelTimer.reset();
                    }
                    if (!this.failedStart) {
                        IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer5 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer5.setMotionY(0.0d);
                        break;
                    }
                    break;
                case 2:
                    IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer6 == null) {
                        Intrinsics.throwNpe();
                    }
                    double posX2 = thePlayer6.getPosX();
                    IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer7 == null) {
                        Intrinsics.throwNpe();
                    }
                    double prevPosX = posX2 - thePlayer7.getPrevPosX();
                    IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer8 == null) {
                        Intrinsics.throwNpe();
                    }
                    double posZ = thePlayer8.getPosZ();
                    IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer9 == null) {
                        Intrinsics.throwNpe();
                    }
                    double prevPosZ = posZ - thePlayer9.getPrevPosZ();
                    this.lastDistance = Math.sqrt((prevPosX * prevPosX) + (prevPosZ * prevPosZ));
                    break;
            }
        }
    }

    @EventTarget
    public final void onRender3D(@Nullable Render3DEvent render3DEvent) {
        String str = (String) this.modeValue.get();
        if (!((Boolean) this.markValue.get()).booleanValue() || StringsKt.equals(str, "Vanilla", true) || StringsKt.equals(str, "SmoothVanilla", true)) {
            return;
        }
        double d = this.startY + 2.0d;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        RenderUtils.drawPlatform(d, thePlayer.getEntityBoundingBox().getMaxY() < d ? new Color(0, 255, 0, 90) : new Color(255, 0, 0, 90), 1.0d);
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case 1435059604:
                if (lowerCase.equals("aac1.9.10")) {
                    RenderUtils.drawPlatform(this.startY + this.aacJump, new Color(0, 0, 255, 90), 1.0d);
                    return;
                }
                return;
            case 1492139162:
                if (lowerCase.equals("aac3.3.12")) {
                    RenderUtils.drawPlatform(-70.0d, new Color(0, 0, 255, 90), 1.0d);
                    return;
                }
                return;
            default:
                return;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x008d  */
    @EventTarget
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (this.noPacketModify) {
            return;
        }
        if (MinecraftInstance.classProvider.isCPacketPlayer(event.getPacket())) {
            ICPacketPlayer iCPacketPlayerAsCPacketPlayer = event.getPacket().asCPacketPlayer();
            String str = (String) this.modeValue.get();
            if (!StringsKt.equals(str, "NCP", true) && !StringsKt.equals(str, "Rewinside", true)) {
                if (StringsKt.equals(str, "Mineplex", true)) {
                    IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer.getInventory().getCurrentItemInHand() == null) {
                    }
                }
                if (!StringsKt.equals(str, "Hypixel", true)) {
                    iCPacketPlayerAsCPacketPlayer.setOnGround(false);
                }
            } else {
                iCPacketPlayerAsCPacketPlayer.setOnGround(true);
                if (!StringsKt.equals(str, "Hypixel", true) || StringsKt.equals(str, "BoostHypixel", true)) {
                    iCPacketPlayerAsCPacketPlayer.setOnGround(false);
                }
            }
        }
        if (MinecraftInstance.classProvider.isSPacketPlayerPosLook(event.getPacket()) && StringsKt.equals((String) this.modeValue.get(), "BoostHypixel", true)) {
            this.failedStart = true;
            ClientUtils.displayChatMessage("\u00a78[\u00a7c\u00a7lBoostHypixel-\u00a7a\u00a7lFly\u00a78] \u00a7cSetback detected.");
        }
    }

    @EventTarget
    public final void onMove(@NotNull MoveEvent event) {
        double amplifier;
        Intrinsics.checkParameterIsNotNull(event, "event");
        String str = (String) this.modeValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1031473397:
                if (lowerCase.equals("cubecraft")) {
                    if (MinecraftInstance.f157mc.getThePlayer() == null) {
                        Intrinsics.throwNpe();
                    }
                    double radians = Math.toRadians(r0.getRotationYaw());
                    if (this.cubecraftTeleportTickTimer.hasTimePassed(2)) {
                        event.setX((-Math.sin(radians)) * 2.4d);
                        event.setZ(Math.cos(radians) * 2.4d);
                        this.cubecraftTeleportTickTimer.reset();
                        return;
                    } else {
                        event.setX((-Math.sin(radians)) * 0.2d);
                        event.setZ(Math.cos(radians) * 0.2d);
                        return;
                    }
                }
                return;
            case -385327063:
                if (!lowerCase.equals("freehypixel") || this.freeHypixelTimer.hasTimePassed(10)) {
                    return;
                }
                event.zero();
                return;
            case 1814517522:
                if (lowerCase.equals("boosthypixel")) {
                    if (!MovementUtils.isMoving()) {
                        event.setX(0.0d);
                        event.setZ(0.0d);
                        return;
                    }
                    if (this.failedStart) {
                        return;
                    }
                    IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer == null) {
                        Intrinsics.throwNpe();
                    }
                    if (thePlayer.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SPEED))) {
                        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer2 == null) {
                            Intrinsics.throwNpe();
                        }
                        if (thePlayer2.getActivePotionEffect(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SPEED)) == null) {
                            Intrinsics.throwNpe();
                        }
                        amplifier = 0.2d * (r2.getAmplifier() + 1.0d);
                    } else {
                        amplifier = 0.0d;
                    }
                    double d = 0.29d * (1.0d + amplifier);
                    switch (this.boostHypixelState) {
                        case 1:
                            IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer3 == null) {
                                Intrinsics.throwNpe();
                            }
                            this.moveSpeed = (thePlayer3.isPotionActive(MinecraftInstance.classProvider.getPotionEnum(PotionType.MOVE_SPEED)) ? 1.56d : 2.034d) * d;
                            this.boostHypixelState = 2;
                            break;
                        case 2:
                            this.moveSpeed *= 2.16d;
                            this.boostHypixelState = 3;
                            break;
                        case 3:
                            double d2 = this.lastDistance;
                            IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer4 == null) {
                                Intrinsics.throwNpe();
                            }
                            this.moveSpeed = d2 - ((thePlayer4.getTicksExisted() % 2 == 0 ? 0.0103d : 0.0123d) * (this.lastDistance - d));
                            this.boostHypixelState = 4;
                            break;
                        default:
                            this.moveSpeed = this.lastDistance - (this.lastDistance / 159.8d);
                            break;
                    }
                    this.moveSpeed = Math.max(this.moveSpeed, 0.3d);
                    double direction = MovementUtils.getDirection();
                    event.setX((-Math.sin(direction)) * this.moveSpeed);
                    event.setZ(Math.cos(direction) * this.moveSpeed);
                    IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer5 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer5.setMotionX(event.getX());
                    IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer6 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer6.setMotionZ(event.getZ());
                    return;
                }
                return;
            default:
                return;
        }
    }

    @EventTarget
    public final void onBB(@NotNull BlockBBEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.f157mc.getThePlayer() == null) {
            return;
        }
        String str = (String) this.modeValue.get();
        if (MinecraftInstance.classProvider.isBlockAir(event.getBlock())) {
            if (!StringsKt.equals(str, "Hypixel", true) && !StringsKt.equals(str, "BoostHypixel", true) && !StringsKt.equals(str, "Rewinside", true)) {
                if (!StringsKt.equals(str, "Mineplex", true)) {
                    return;
                }
                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer == null) {
                    Intrinsics.throwNpe();
                }
                if (thePlayer.getInventory().getCurrentItemInHand() != null) {
                    return;
                }
            }
            double y = event.getY();
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            if (y < thePlayer2.getPosY()) {
                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                double x = event.getX();
                double y2 = event.getY();
                double z = event.getZ();
                double x2 = event.getX() + 1.0d;
                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer3 == null) {
                    Intrinsics.throwNpe();
                }
                event.setBoundingBox(iClassProvider.createAxisAlignedBB(x, y2, z, x2, thePlayer3.getPosY(), event.getZ() + 1.0d));
            }
        }
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        String str = (String) this.modeValue.get();
        if (!StringsKt.equals(str, "Hypixel", true) && !StringsKt.equals(str, "BoostHypixel", true) && !StringsKt.equals(str, "Rewinside", true)) {
            if (!StringsKt.equals(str, "Mineplex", true)) {
                return;
            }
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer.getInventory().getCurrentItemInHand() != null) {
                return;
            }
        }
        e.cancelEvent();
    }

    @EventTarget
    public final void onStep(@NotNull StepEvent e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        String str = (String) this.modeValue.get();
        if (!StringsKt.equals(str, "Hypixel", true) && !StringsKt.equals(str, "BoostHypixel", true) && !StringsKt.equals(str, "Rewinside", true)) {
            if (!StringsKt.equals(str, "Mineplex", true)) {
                return;
            }
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            if (thePlayer.getInventory().getCurrentItemInHand() != null) {
                return;
            }
        }
        e.setStepHeight(0.0f);
    }

    private final void handleVanillaKickBypass() {
        if (((Boolean) this.vanillaKickBypassValue.get()).booleanValue() && this.groundTimer.hasTimePassed(1000L)) {
            double dCalculateGround = calculateGround();
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            double posY = thePlayer.getPosY();
            while (true) {
                double d = posY;
                if (d <= dCalculateGround) {
                    break;
                }
                IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                double posX = thePlayer2.getPosX();
                IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer3 == null) {
                    Intrinsics.throwNpe();
                }
                netHandler.addToSendQueue(iClassProvider.createCPacketPlayerPosition(posX, d, thePlayer3.getPosZ(), true));
                if (d - 8.0d < dCalculateGround) {
                    break;
                } else {
                    posY = d - 8.0d;
                }
            }
            IINetHandlerPlayClient netHandler2 = MinecraftInstance.f157mc.getNetHandler();
            IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
            IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer4 == null) {
                Intrinsics.throwNpe();
            }
            double posX2 = thePlayer4.getPosX();
            IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer5 == null) {
                Intrinsics.throwNpe();
            }
            netHandler2.addToSendQueue(iClassProvider2.createCPacketPlayerPosition(posX2, dCalculateGround, thePlayer5.getPosZ(), true));
            double d2 = dCalculateGround;
            while (true) {
                double d3 = d2;
                IEntityPlayerSP thePlayer6 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer6 == null) {
                    Intrinsics.throwNpe();
                }
                if (d3 >= thePlayer6.getPosY()) {
                    break;
                }
                IINetHandlerPlayClient netHandler3 = MinecraftInstance.f157mc.getNetHandler();
                IClassProvider iClassProvider3 = MinecraftInstance.classProvider;
                IEntityPlayerSP thePlayer7 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer7 == null) {
                    Intrinsics.throwNpe();
                }
                double posX3 = thePlayer7.getPosX();
                IEntityPlayerSP thePlayer8 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer8 == null) {
                    Intrinsics.throwNpe();
                }
                netHandler3.addToSendQueue(iClassProvider3.createCPacketPlayerPosition(posX3, d3, thePlayer8.getPosZ(), true));
                double d4 = d3 + 8.0d;
                IEntityPlayerSP thePlayer9 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer9 == null) {
                    Intrinsics.throwNpe();
                }
                if (d4 > thePlayer9.getPosY()) {
                    break;
                } else {
                    d2 = d3 + 8.0d;
                }
            }
            IINetHandlerPlayClient netHandler4 = MinecraftInstance.f157mc.getNetHandler();
            IClassProvider iClassProvider4 = MinecraftInstance.classProvider;
            IEntityPlayerSP thePlayer10 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer10 == null) {
                Intrinsics.throwNpe();
            }
            double posX4 = thePlayer10.getPosX();
            IEntityPlayerSP thePlayer11 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer11 == null) {
                Intrinsics.throwNpe();
            }
            double posY2 = thePlayer11.getPosY();
            IEntityPlayerSP thePlayer12 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer12 == null) {
                Intrinsics.throwNpe();
            }
            netHandler4.addToSendQueue(iClassProvider4.createCPacketPlayerPosition(posX4, posY2, thePlayer12.getPosZ(), true));
            this.groundTimer.reset();
        }
    }

    private final double calculateGround() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        IAxisAlignedBB entityBoundingBox = thePlayer.getEntityBoundingBox();
        double d = 1.0d;
        IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer2 == null) {
            Intrinsics.throwNpe();
        }
        double posY = thePlayer2.getPosY();
        while (true) {
            double d2 = posY;
            if (d2 > 0.0d) {
                IAxisAlignedBB iAxisAlignedBBCreateAxisAlignedBB = MinecraftInstance.classProvider.createAxisAlignedBB(entityBoundingBox.getMaxX(), d2 + d, entityBoundingBox.getMaxZ(), entityBoundingBox.getMinX(), d2, entityBoundingBox.getMinZ());
                IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                if (theWorld == null) {
                    Intrinsics.throwNpe();
                }
                if (theWorld.checkBlockCollision(iAxisAlignedBBCreateAxisAlignedBB)) {
                    if (d <= 0.05d) {
                        return d2 + d;
                    }
                    d2 += d;
                    d = 0.05d;
                }
                posY = d2 - d;
            } else {
                return 0.0d;
            }
        }
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
