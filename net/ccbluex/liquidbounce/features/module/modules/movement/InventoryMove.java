package net.ccbluex.liquidbounce.features.module.modules.movement;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.settings.IKeyBinding;
import net.ccbluex.liquidbounce.event.ClickWindowEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\b\u0010\u0015\u001a\u00020\u0012H\u0016J\u0010\u0010\u0016\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0017H\u0007J\u0006\u0010\u0018\u001a\u00020\u0012R\u0011\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\nR\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0016\u0010\f\u001a\u0004\u0018\u00010\r8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0019"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/movement/InventoryMove;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "aacAdditionProValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getAacAdditionProValue", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "affectedBindings", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IKeyBinding;", "[Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IKeyBinding;", "noMoveClicksValue", "tag", "", "getTag", "()Ljava/lang/String;", "undetectable", "onClick", "", "event", "Lnet/ccbluex/liquidbounce/event/ClickWindowEvent;", "onDisable", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "tick", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "InventoryMove", description = "Allows you to walk while an inventory is opened.", category = ModuleCategory.MOVEMENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/movement/InventoryMove.class */
public final class InventoryMove extends Module {
    private final BoolValue undetectable = new BoolValue("Undetectable", false);

    @NotNull
    private final BoolValue aacAdditionProValue = new BoolValue("AACAdditionPro", false);
    private final BoolValue noMoveClicksValue = new BoolValue("NoMoveClicks", false);
    private final IKeyBinding[] affectedBindings = {MinecraftInstance.f157mc.getGameSettings().getKeyBindForward(), MinecraftInstance.f157mc.getGameSettings().getKeyBindBack(), MinecraftInstance.f157mc.getGameSettings().getKeyBindRight(), MinecraftInstance.f157mc.getGameSettings().getKeyBindLeft(), MinecraftInstance.f157mc.getGameSettings().getKeyBindJump(), MinecraftInstance.f157mc.getGameSettings().getKeyBindSprint()};

    @NotNull
    public final BoolValue getAacAdditionProValue() {
        return this.aacAdditionProValue;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        tick();
    }

    public final void tick() {
        if (MinecraftInstance.classProvider.isGuiChat(MinecraftInstance.f157mc.getCurrentScreen()) || MinecraftInstance.classProvider.isGuiIngameMenu(MinecraftInstance.f157mc.getCurrentScreen())) {
            return;
        }
        if (!((Boolean) this.undetectable.get()).booleanValue() || !MinecraftInstance.classProvider.isGuiContainer(MinecraftInstance.f157mc.getCurrentScreen())) {
            for (IKeyBinding iKeyBinding : this.affectedBindings) {
                iKeyBinding.setPressed(MinecraftInstance.f157mc.getGameSettings().isKeyDown(iKeyBinding));
            }
        }
    }

    @EventTarget
    public final void onClick(@NotNull ClickWindowEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (((Boolean) this.noMoveClicksValue.get()).booleanValue() && MovementUtils.isMoving()) {
            event.cancelEvent();
        }
    }

    public void onDisable() {
        boolean z = MinecraftInstance.f157mc.getCurrentScreen() != null;
        for (IKeyBinding iKeyBinding : this.affectedBindings) {
            if (!MinecraftInstance.f157mc.getGameSettings().isKeyDown(iKeyBinding) || z) {
                iKeyBinding.setPressed(false);
            }
        }
    }

    @Nullable
    public String getTag() {
        if (((Boolean) this.aacAdditionProValue.get()).booleanValue()) {
            return "AACAdditionPro";
        }
        return null;
    }
}
