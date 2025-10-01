package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.JvmField;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.IINetHandlerPlayClient;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\nH\u0016J\u0010\u0010\f\u001a\u00020\n2\u0006\u0010\r\u001a\u00020\u000eH\u0007R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0007\u001a\u00020\b8\u0006X\u0087\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/Sneak;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "sneaked", "", "stopMoveValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "onDisable", "", "onEnable", "onMotion", "event", "Lnet/ccbluex/liquidbounce/event/MotionEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Sneak", description = "Automatically sneaks all the time.", category = ModuleCategory.MOVEMENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/Sneak.class */
public final class Sneak extends Module {

    @JvmField
    @NotNull
    public final ListValue modeValue = new ListValue("Mode", new String[]{"Legit", "Vanilla", "Switch", "MineSecure"}, "MineSecure");

    @JvmField
    @NotNull
    public final BoolValue stopMoveValue = new BoolValue("StopMove", false);
    private boolean sneaked;

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/Sneak$WhenMappings.class */
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0 = new int[EventState.values().length];

        static {
            $EnumSwitchMapping$0[EventState.PRE.ordinal()] = 1;
            $EnumSwitchMapping$0[EventState.POST.ordinal()] = 2;
        }
    }

    public void onEnable() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null && StringsKt.equals("vanilla", (String) this.modeValue.get(), true)) {
            MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketEntityAction(thePlayer, ICPacketEntityAction.WAction.START_SNEAKING));
        }
    }

    @EventTarget
    public final void onMotion(@NotNull MotionEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (((Boolean) this.stopMoveValue.get()).booleanValue() && MovementUtils.isMoving()) {
            if (this.sneaked) {
                onDisable();
                this.sneaked = false;
                return;
            }
            return;
        }
        this.sneaked = true;
        String str = (String) this.modeValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -889473228:
                if (lowerCase.equals("switch")) {
                    switch (WhenMappings.$EnumSwitchMapping$0[event.getEventState().ordinal()]) {
                        case 1:
                            if (MovementUtils.isMoving()) {
                                IINetHandlerPlayClient netHandler = MinecraftInstance.f157mc.getNetHandler();
                                IClassProvider iClassProvider = MinecraftInstance.classProvider;
                                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer == null) {
                                    Intrinsics.throwNpe();
                                }
                                netHandler.addToSendQueue(iClassProvider.createCPacketEntityAction(thePlayer, ICPacketEntityAction.WAction.START_SNEAKING));
                                IINetHandlerPlayClient netHandler2 = MinecraftInstance.f157mc.getNetHandler();
                                IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
                                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                                if (thePlayer2 == null) {
                                    Intrinsics.throwNpe();
                                }
                                netHandler2.addToSendQueue(iClassProvider2.createCPacketEntityAction(thePlayer2, ICPacketEntityAction.WAction.STOP_SNEAKING));
                                return;
                            }
                            return;
                        case 2:
                            IINetHandlerPlayClient netHandler3 = MinecraftInstance.f157mc.getNetHandler();
                            IClassProvider iClassProvider3 = MinecraftInstance.classProvider;
                            IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer3 == null) {
                                Intrinsics.throwNpe();
                            }
                            netHandler3.addToSendQueue(iClassProvider3.createCPacketEntityAction(thePlayer3, ICPacketEntityAction.WAction.STOP_SNEAKING));
                            IINetHandlerPlayClient netHandler4 = MinecraftInstance.f157mc.getNetHandler();
                            IClassProvider iClassProvider4 = MinecraftInstance.classProvider;
                            IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                            if (thePlayer4 == null) {
                                Intrinsics.throwNpe();
                            }
                            netHandler4.addToSendQueue(iClassProvider4.createCPacketEntityAction(thePlayer4, ICPacketEntityAction.WAction.START_SNEAKING));
                            return;
                        default:
                            return;
                    }
                }
                return;
            case 102851513:
                if (lowerCase.equals("legit")) {
                    MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak().setPressed(true);
                    return;
                }
                return;
            case 518567306:
                if (!lowerCase.equals("minesecure") || event.getEventState() == EventState.PRE) {
                    return;
                }
                IINetHandlerPlayClient netHandler5 = MinecraftInstance.f157mc.getNetHandler();
                IClassProvider iClassProvider5 = MinecraftInstance.classProvider;
                IEntityPlayerSP thePlayer5 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer5 == null) {
                    Intrinsics.throwNpe();
                }
                netHandler5.addToSendQueue(iClassProvider5.createCPacketEntityAction(thePlayer5, ICPacketEntityAction.WAction.START_SNEAKING));
                return;
            default:
                return;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00c7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onDisable() {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            String str = (String) this.modeValue.get();
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = str.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case -889473228:
                    if (lowerCase.equals("switch")) {
                        MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketEntityAction(thePlayer, ICPacketEntityAction.WAction.STOP_SNEAKING));
                        break;
                    }
                    break;
                case 102851513:
                    if (lowerCase.equals("legit") && !MinecraftInstance.f157mc.getGameSettings().isKeyDown(MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak())) {
                        MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak().setPressed(false);
                        break;
                    }
                    break;
                case 233102203:
                    if (lowerCase.equals("vanilla")) {
                    }
                    break;
                case 518567306:
                    if (lowerCase.equals("minesecure")) {
                    }
                    break;
            }
        }
    }
}
