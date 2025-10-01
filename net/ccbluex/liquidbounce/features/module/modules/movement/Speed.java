package net.ccbluex.liquidbounce.features.module.modules.movement;

import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.TickEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.SpeedMode;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC2BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC3BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC4BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC5BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC6BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AAC7BHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACBHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACGround;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACGround2;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACHop3313;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACHop350;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACLowHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACLowHop2;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACLowHop3;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACPort;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACYPort;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.AACYPort2;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aac.OldAACBHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aquavit.AAC4Hop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aquavit.AAC4SlowHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.aquavit.HytNewSpeed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.Boost;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.Frame;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.MiJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.NCPBHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.NCPFHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.NCPHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.NCPYPort;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.OnGround;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.SNCPBHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.YPort;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.ncp.YPort2;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.CustomSpeed;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.HiveHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.HypixelHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.MineplexGround;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.SlowHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.other.TeleportCubeCraft;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spartan.SpartanYPort;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre.SpectreBHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre.SpectreLowHop;
import net.ccbluex.liquidbounce.features.module.modules.movement.speeds.spectre.SpectreOnGround;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/* compiled from: Speed.kt */
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdX\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0002\b\u000e\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010-\u001a\u00020.H\u0016J\b\u0010/\u001a\u00020.H\u0016J\u0010\u00100\u001a\u00020.2\u0006\u00101\u001a\u000202H\u0007J\u0012\u00103\u001a\u00020.2\b\u00101\u001a\u0004\u0018\u000104H\u0007J\u0012\u00105\u001a\u00020.2\b\u00101\u001a\u0004\u0018\u000106H\u0007J\u0012\u00107\u001a\u00020.2\b\u00101\u001a\u0004\u0018\u000108H\u0007R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u0006R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u000f\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0010\u0010\u0006R\u0011\u0010\u0011\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0012\u0010\u0006R\u0011\u0010\u0013\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0014\u0010\u0006R\u0016\u0010\u0015\u001a\u0004\u0018\u00010\u00168BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\u0019\u001a\u00020\u001a\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u001b\u0010\u001cR\u001a\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b \u0010!R\u0011\u0010\"\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b#\u0010\u0006R\u0011\u0010$\u001a\u00020\f\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b%\u0010\u000eR\u0011\u0010&\u001a\u00020\f\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b'\u0010\u000eR\u0016\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00160\u001eX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010)R\u0014\u0010*\u001a\u00020\u001f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b+\u0010,\u00a8\u00069"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Speed;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aacGroundTimerValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getAacGroundTimerValue", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "cubecraftPortLengthValue", "getCubecraftPortLengthValue", "customSpeedValue", "getCustomSpeedValue", "customStrafeValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getCustomStrafeValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "customTimerValue", "getCustomTimerValue", "customYValue", "getCustomYValue", "mineplexGroundSpeedValue", "getMineplexGroundSpeedValue", "mode", "Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "getMode", "()Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "getModeValue", "()Lnet/ccbluex/liquidbounce/value/ListValue;", "modes", "", "", "getModes", "()[Ljava/lang/String;", "portMax", "getPortMax", "resetXZValue", "getResetXZValue", "resetYValue", "getResetYValue", "speedModes", "[Lnet/ccbluex/liquidbounce/features/module/modules/movement/speeds/SpeedMode;", "tag", "getTag", "()Ljava/lang/String;", "onDisable", "", "onEnable", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onTick", "Lnet/ccbluex/liquidbounce/event/TickEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Speed", description = "Allows you to move faster.", category = ModuleCategory.MOVEMENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/Speed.class */
public final class Speed extends Module {
    private final SpeedMode[] speedModes = {new AAC4Hop(), new AAC4SlowHop(), new HytNewSpeed(), new NCPBHop(), new NCPFHop(), new SNCPBHop(), new NCPHop(), new YPort(), new YPort2(), new NCPYPort(), new Boost(), new Frame(), new MiJump(), new OnGround(), new AACBHop(), new AAC2BHop(), new AAC3BHop(), new AAC4BHop(), new AAC5BHop(), new AAC6BHop(), new AAC7BHop(), new AACHop3313(), new AACHop350(), new AACLowHop(), new AACLowHop2(), new AACLowHop3(), new AACGround(), new AACGround2(), new AACYPort(), new AACYPort2(), new AACPort(), new OldAACBHop(), new SpartanYPort(), new SpectreLowHop(), new SpectreBHop(), new SpectreOnGround(), new TeleportCubeCraft(), new HiveHop(), new HypixelHop(), new MineplexGround(), new SlowHop(), new CustomSpeed()};

    @NotNull
    private final ListValue modeValue;

    @NotNull
    private final FloatValue customSpeedValue;

    @NotNull
    private final FloatValue customYValue;

    @NotNull
    private final FloatValue customTimerValue;

    @NotNull
    private final BoolValue customStrafeValue;

    @NotNull
    private final BoolValue resetXZValue;

    @NotNull
    private final BoolValue resetYValue;

    @NotNull
    private final FloatValue portMax;

    @NotNull
    private final FloatValue aacGroundTimerValue;

    @NotNull
    private final FloatValue cubecraftPortLengthValue;

    @NotNull
    private final FloatValue mineplexGroundSpeedValue;

    public Speed() {
        final String str = "Mode";
        final String[] modes = getModes();
        final String str2 = "NCPBHop";
        this.modeValue = new ListValue(this, str, modes, str2) { // from class: net.ccbluex.liquidbounce.features.module.modules.movement.Speed$modeValue$1
            final Speed this$0;

            public void onChange(Object obj, Object obj2) {
                onChange((String) obj, (String) obj2);
            }

            public void onChanged(Object obj, Object obj2) {
                onChanged((String) obj, (String) obj2);
            }

            {
                this.this$0 = this;
            }

            protected void onChange(@NotNull String oldValue, @NotNull String newValue) {
                Intrinsics.checkParameterIsNotNull(oldValue, "oldValue");
                Intrinsics.checkParameterIsNotNull(newValue, "newValue");
                if (this.this$0.getState()) {
                    this.this$0.onDisable();
                }
            }

            protected void onChanged(@NotNull String oldValue, @NotNull String newValue) {
                Intrinsics.checkParameterIsNotNull(oldValue, "oldValue");
                Intrinsics.checkParameterIsNotNull(newValue, "newValue");
                if (this.this$0.getState()) {
                    this.this$0.onEnable();
                }
            }
        };
        this.customSpeedValue = new FloatValue("CustomSpeed", 1.6f, 0.2f, 2.0f);
        this.customYValue = new FloatValue("CustomY", 0.0f, 0.0f, 4.0f);
        this.customTimerValue = new FloatValue("CustomTimer", 1.0f, 0.1f, 2.0f);
        this.customStrafeValue = new BoolValue("CustomStrafe", true);
        this.resetXZValue = new BoolValue("CustomResetXZ", false);
        this.resetYValue = new BoolValue("CustomResetY", false);
        this.portMax = new FloatValue("AAC-PortLength", 1.0f, 1.0f, 20.0f);
        this.aacGroundTimerValue = new FloatValue("AACGround-Timer", 3.0f, 1.1f, 10.0f);
        this.cubecraftPortLengthValue = new FloatValue("CubeCraft-PortLength", 1.0f, 0.1f, 2.0f);
        this.mineplexGroundSpeedValue = new FloatValue("MineplexGround-Speed", 0.5f, 0.1f, 1.0f);
    }

    @NotNull
    public final ListValue getModeValue() {
        return this.modeValue;
    }

    @NotNull
    public final FloatValue getCustomSpeedValue() {
        return this.customSpeedValue;
    }

    @NotNull
    public final FloatValue getCustomYValue() {
        return this.customYValue;
    }

    @NotNull
    public final FloatValue getCustomTimerValue() {
        return this.customTimerValue;
    }

    @NotNull
    public final BoolValue getCustomStrafeValue() {
        return this.customStrafeValue;
    }

    @NotNull
    public final BoolValue getResetXZValue() {
        return this.resetXZValue;
    }

    @NotNull
    public final BoolValue getResetYValue() {
        return this.resetYValue;
    }

    @NotNull
    public final FloatValue getPortMax() {
        return this.portMax;
    }

    @NotNull
    public final FloatValue getAacGroundTimerValue() {
        return this.aacGroundTimerValue;
    }

    @NotNull
    public final FloatValue getCubecraftPortLengthValue() {
        return this.cubecraftPortLengthValue;
    }

    @NotNull
    public final FloatValue getMineplexGroundSpeedValue() {
        return this.mineplexGroundSpeedValue;
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent event) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null || thePlayer.isSneaking()) {
            return;
        }
        if (MovementUtils.isMoving()) {
            thePlayer.setSprinting(true);
        }
        SpeedMode mode = getMode();
        if (mode != null) {
            mode.onUpdate();
        }
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null || thePlayer.isSneaking() || event.getEventState() != EventState.PRE) {
            return;
        }
        if (MovementUtils.isMoving()) {
            thePlayer.setSprinting(true);
        }
        SpeedMode mode = getMode();
        if (mode != null) {
            mode.onMotion();
        }
    }

    @EventTarget
    public final void onMove(@Nullable MoveEvent event) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.isSneaking()) {
            return;
        }
        SpeedMode mode = getMode();
        if (mode != null) {
            if (event == null) {
                Intrinsics.throwNpe();
            }
            mode.onMove(event);
        }
    }

    @EventTarget
    public final void onTick(@Nullable TickEvent event) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.isSneaking()) {
            return;
        }
        SpeedMode mode = getMode();
        if (mode != null) {
            mode.onTick();
        }
    }

    public void onEnable() {
        if (MinecraftInstance.f157mc.getThePlayer() == null) {
            return;
        }
        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
        SpeedMode mode = getMode();
        if (mode != null) {
            mode.onEnable();
        }
    }

    public void onDisable() {
        if (MinecraftInstance.f157mc.getThePlayer() == null) {
            return;
        }
        MinecraftInstance.f157mc.getTimer().setTimerSpeed(1.0f);
        SpeedMode mode = getMode();
        if (mode != null) {
            mode.onDisable();
        }
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }

    private final SpeedMode getMode() {
        String mode = (String) this.modeValue.get();
        for (SpeedMode speedMode : this.speedModes) {
            if (StringsKt.equals(speedMode.getModeName(), mode, true)) {
                return speedMode;
            }
        }
        return null;
    }

    private final String[] getModes() {
        List list = new ArrayList();
        for (SpeedMode speedMode : this.speedModes) {
            list.add(speedMode.getModeName());
        }
        List $this$toTypedArray$iv = list;
        Object[] array = $this$toTypedArray$iv.toArray(new String[0]);
        if (array == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
        }
        return (String[]) array;
    }
}
