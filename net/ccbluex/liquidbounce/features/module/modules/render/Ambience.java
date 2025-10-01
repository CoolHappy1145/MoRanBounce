package net.ccbluex.liquidbounce.features.module.modules.render;

import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.network.IPacket;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.injection.backend.MinecraftImpl;
import net.ccbluex.liquidbounce.injection.backend.PacketImpl;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0017H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010\u0006\u001a\u00020\u0007X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000e\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0018"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Ambience;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "changeWorldTimeSpeedValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "customWorldTimeValue", "i", "", "getI", "()J", "setI", "(J)V", "timeModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "weatherModeValue", "weatherStrengthValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "onDisable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Ambience", description = "Ambience By Tq", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/Ambience.class */
public final class Ambience extends Module {
    private final ListValue timeModeValue = new ListValue("TimeMode", new String[]{"None", "Normal", "Custom"}, "Normal");
    private final ListValue weatherModeValue = new ListValue("WeatherMode", new String[]{"None", "Sun", "Rain", "Thunder"}, "None");
    private final IntegerValue customWorldTimeValue = new IntegerValue("CustomTime", 1000, 0, 24000);
    private final IntegerValue changeWorldTimeSpeedValue = new IntegerValue("ChangeWorldTimeSpeed", 150, 10, SharedScopeCall.SLOW_SCOPE_CALL_THRESHOLD);
    private final FloatValue weatherStrengthValue = new FloatValue("WeatherStrength", 1.0f, 0.0f, 1.0f);

    /* renamed from: i */
    private long f126i;

    public final long getI() {
        return this.f126i;
    }

    public final void setI(long j) {
        this.f126i = j;
    }

    public void onDisable() {
        this.f126i = 0L;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        String str = (String) this.timeModeValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1349088399:
                if (lowerCase.equals("custom")) {
                    IMinecraft mc = MinecraftInstance.f157mc;
                    Intrinsics.checkExpressionValueIsNotNull(mc, "mc");
                    if (mc != null) {
                        WorldClient worldClient = ((MinecraftImpl) mc).getWrapped().field_71441_e;
                        Intrinsics.checkExpressionValueIsNotNull(worldClient, "mc.unwrap().world");
                        worldClient.func_72877_b(((Number) this.customWorldTimeValue.get()).intValue());
                        break;
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.MinecraftImpl");
                    }
                }
                break;
            case -1039745817:
                if (lowerCase.equals("normal")) {
                    if (this.f126i < 24000) {
                        this.f126i += ((Number) this.changeWorldTimeSpeedValue.get()).longValue();
                    } else {
                        this.f126i = 0L;
                    }
                    IMinecraft mc2 = MinecraftInstance.f157mc;
                    Intrinsics.checkExpressionValueIsNotNull(mc2, "mc");
                    if (mc2 != null) {
                        WorldClient worldClient2 = ((MinecraftImpl) mc2).getWrapped().field_71441_e;
                        Intrinsics.checkExpressionValueIsNotNull(worldClient2, "mc.unwrap().world");
                        worldClient2.func_72877_b(this.f126i);
                        break;
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.MinecraftImpl");
                    }
                }
                break;
        }
        String str2 = (String) this.weatherModeValue.get();
        if (str2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase2 = str2.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase2, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase2.hashCode()) {
            case -1334895388:
                if (lowerCase2.equals("thunder")) {
                    IMinecraft mc3 = MinecraftInstance.f157mc;
                    Intrinsics.checkExpressionValueIsNotNull(mc3, "mc");
                    if (mc3 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.MinecraftImpl");
                    }
                    ((MinecraftImpl) mc3).getWrapped().field_71441_e.func_72894_k(((Number) this.weatherStrengthValue.get()).floatValue());
                    IMinecraft mc4 = MinecraftInstance.f157mc;
                    Intrinsics.checkExpressionValueIsNotNull(mc4, "mc");
                    if (mc4 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.MinecraftImpl");
                    }
                    ((MinecraftImpl) mc4).getWrapped().field_71441_e.func_147442_i(((Number) this.weatherStrengthValue.get()).floatValue());
                    return;
                }
                return;
            case 114252:
                if (lowerCase2.equals("sun")) {
                    IMinecraft mc5 = MinecraftInstance.f157mc;
                    Intrinsics.checkExpressionValueIsNotNull(mc5, "mc");
                    if (mc5 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.MinecraftImpl");
                    }
                    ((MinecraftImpl) mc5).getWrapped().field_71441_e.func_72894_k(0.0f);
                    IMinecraft mc6 = MinecraftInstance.f157mc;
                    Intrinsics.checkExpressionValueIsNotNull(mc6, "mc");
                    if (mc6 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.MinecraftImpl");
                    }
                    ((MinecraftImpl) mc6).getWrapped().field_71441_e.func_147442_i(0.0f);
                    return;
                }
                return;
            case 3492756:
                if (lowerCase2.equals("rain")) {
                    IMinecraft mc7 = MinecraftInstance.f157mc;
                    Intrinsics.checkExpressionValueIsNotNull(mc7, "mc");
                    if (mc7 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.MinecraftImpl");
                    }
                    ((MinecraftImpl) mc7).getWrapped().field_71441_e.func_72894_k(((Number) this.weatherStrengthValue.get()).floatValue());
                    IMinecraft mc8 = MinecraftInstance.f157mc;
                    Intrinsics.checkExpressionValueIsNotNull(mc8, "mc");
                    if (mc8 == null) {
                        throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.MinecraftImpl");
                    }
                    ((MinecraftImpl) mc8).getWrapped().field_71441_e.func_147442_i(0.0f);
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
        IPacket packet = event.getPacket();
        if (!this.timeModeValue.equals("none")) {
            if (packet == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.PacketImpl<*>");
            }
            if (((PacketImpl) packet).getWrapped() instanceof SPacketTimeUpdate) {
                event.cancelEvent();
            }
        }
        if (this.weatherModeValue.equals("none")) {
            return;
        }
        if (packet == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.PacketImpl<*>");
        }
        if (!(((PacketImpl) packet).getWrapped() instanceof SPacketChangeGameState)) {
            return;
        }
        if (packet == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.PacketImpl<*>");
        }
        SPacketChangeGameState wrapped = ((PacketImpl) packet).getWrapped();
        if (wrapped == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.network.play.server.SPacketChangeGameState");
        }
        int iFunc_149138_c = wrapped.func_149138_c();
        if (7 <= iFunc_149138_c && 8 >= iFunc_149138_c) {
            event.cancelEvent();
        }
    }
}
