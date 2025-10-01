package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\b\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/NoWeb;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "tag", "", "getTag", "()Ljava/lang/String;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "NoWeb", description = "Prevents you from getting slowed down in webs.", category = ModuleCategory.MOVEMENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/NoWeb.class */
public final class NoWeb extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"None", "AAC", "LAAC", "Rewi"}, "None");

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null || !thePlayer.isInWeb()) {
            return;
        }
        String str = (String) this.modeValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case 96323:
                if (lowerCase.equals("aac")) {
                    thePlayer.setJumpMovementFactor(0.59f);
                    if (!MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                        thePlayer.setMotionY(0.0d);
                        return;
                    }
                    return;
                }
                return;
            case 3313751:
                if (lowerCase.equals("laac")) {
                    thePlayer.setJumpMovementFactor(thePlayer.getMovementInput().getMoveStrafe() != 0.0f ? 1.0f : 1.21f);
                    if (!MinecraftInstance.f157mc.getGameSettings().getKeyBindSneak().isKeyDown()) {
                        thePlayer.setMotionY(0.0d);
                    }
                    if (thePlayer.getOnGround()) {
                        thePlayer.jump();
                        return;
                    }
                    return;
                }
                return;
            case 3387192:
                if (lowerCase.equals("none")) {
                    thePlayer.setInWeb(false);
                    return;
                }
                return;
            case 3497029:
                if (lowerCase.equals("rewi")) {
                    thePlayer.setJumpMovementFactor(0.42f);
                    if (thePlayer.getOnGround()) {
                        thePlayer.jump();
                        return;
                    }
                    return;
                }
                return;
            default:
                return;
        }
    }

    @Nullable
    public String getTag() {
        return (String) this.modeValue.get();
    }
}
