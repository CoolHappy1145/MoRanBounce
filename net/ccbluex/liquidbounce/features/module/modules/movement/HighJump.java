package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
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

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0007J\u0012\u0010\u0011\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0012H\u0007J\u0012\u0010\u0013\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0014H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0015"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/HighJump;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "glassValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "heightValue", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "onJump", "", "event", "Lnet/ccbluex/liquidbounce/event/JumpEvent;", "onMove", "Lnet/ccbluex/liquidbounce/event/MoveEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "HighJump", description = "Allows you to jump higher.", category = ModuleCategory.MOVEMENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/HighJump.class */
public final class HighJump extends Module {
    private final FloatValue heightValue = new FloatValue("Height", 2.0f, 1.1f, 5.0f);
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Vanilla", "Damage", "AACv3", "DAC", "Mineplex"}, "Vanilla");
    private final BoolValue glassValue = new BoolValue("OnlyGlassPane", false);

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent updateEvent) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (((Boolean) this.glassValue.get()).booleanValue() && !MinecraftInstance.classProvider.isBlockPane(BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ())))) {
            return;
        }
        String str = (String) this.modeValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1362669950:
                if (!lowerCase.equals("mineplex") || thePlayer.getOnGround()) {
                    return;
                }
                MovementUtils.strafe(0.35f);
                return;
            case -1339126929:
                if (!lowerCase.equals("damage") || thePlayer.getHurtTime() <= 0 || !thePlayer.getOnGround()) {
                    return;
                }
                thePlayer.setMotionY(thePlayer.getMotionY() + (0.42f * ((Number) this.heightValue.get()).floatValue()));
                return;
            case 99206:
                if (!lowerCase.equals("dac") || thePlayer.getOnGround()) {
                    return;
                }
                thePlayer.setMotionY(thePlayer.getMotionY() + 0.049999d);
                return;
            case 92570112:
                if (!lowerCase.equals("aacv3") || thePlayer.getOnGround()) {
                    return;
                }
                thePlayer.setMotionY(thePlayer.getMotionY() + 0.059d);
                return;
            default:
                return;
        }
    }

    @EventTarget
    public final void onMove(@Nullable MoveEvent moveEvent) {
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            if ((!((Boolean) this.glassValue.get()).booleanValue() || MinecraftInstance.classProvider.isBlockPane(BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ())))) && !thePlayer.getOnGround()) {
                String str = (String) this.modeValue.get();
                if (str == null) {
                    throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                }
                String lowerCase = str.toLowerCase();
                Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                if (Intrinsics.areEqual("mineplex", lowerCase)) {
                    thePlayer.setMotionY(thePlayer.getMotionY() + (thePlayer.getFallDistance() == 0.0f ? 0.0499d : 0.05d));
                }
            }
        }
    }

    @EventTarget
    public final void onJump(@NotNull JumpEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            if (((Boolean) this.glassValue.get()).booleanValue() && !MinecraftInstance.classProvider.isBlockPane(BlockUtils.getBlock(new WBlockPos(thePlayer.getPosX(), thePlayer.getPosY(), thePlayer.getPosZ())))) {
                return;
            }
            String str = (String) this.modeValue.get();
            if (str == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            }
            String lowerCase = str.toLowerCase();
            Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
            switch (lowerCase.hashCode()) {
                case -1362669950:
                    if (lowerCase.equals("mineplex")) {
                        event.setMotion(0.47f);
                        return;
                    }
                    return;
                case 233102203:
                    if (lowerCase.equals("vanilla")) {
                        event.setMotion(event.getMotion() * ((Number) this.heightValue.get()).floatValue());
                        return;
                    }
                    return;
                default:
                    return;
            }
        }
    }

    @NotNull
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
