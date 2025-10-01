package net.ccbluex.liquidbounce.features.module.modules.hyt;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IPlayerControllerMP;
import net.ccbluex.liquidbounce.api.minecraft.network.play.client.ICPacketEntityAction;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.combat.Velocity;
import net.ccbluex.liquidbounce.injection.backend.WrapperImpl;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FloatValue;
import net.ccbluex.liquidbounce.value.TextValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdN\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010(\u001a\u00020)2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-H\u0002J\u0010\u0010.\u001a\u00020)2\u0006\u0010/\u001a\u000200H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\rX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u0019X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u001a\u0010\u001b\"\u0004\b\u001c\u0010\u001dR\u001a\u0010\u001e\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001a\u0010!\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u0014\u0010$\u001a\u00020%8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b&\u0010'\u00a8\u00061"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/hyt/AutoLobby;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "canhubchat", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "getCanhubchat", "()Lnet/ccbluex/liquidbounce/value/BoolValue;", "setCanhubchat", "(Lnet/ccbluex/liquidbounce/value/BoolValue;)V", "disabler", "getDisabler", "setDisabler", "health", "Lnet/ccbluex/liquidbounce/value/FloatValue;", "getHealth", "()Lnet/ccbluex/liquidbounce/value/FloatValue;", "setHealth", "(Lnet/ccbluex/liquidbounce/value/FloatValue;)V", "hubDelayTime", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "getHubDelayTime", "()Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "setHubDelayTime", "(Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;)V", "hubchattext", "Lnet/ccbluex/liquidbounce/value/TextValue;", "getHubchattext", "()Lnet/ccbluex/liquidbounce/value/TextValue;", "setHubchattext", "(Lnet/ccbluex/liquidbounce/value/TextValue;)V", "keepArmor", "getKeepArmor", "setKeepArmor", "randomhub", "getRandomhub", "setRandomhub", "tag", "", "getTag", "()Ljava/lang/String;", "move", "", "item", "", "isArmorSlot", "", "onUpdate", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "AutoLobby", description = "Hyt AutoHub By TianKeng", category = ModuleCategory.HYT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/hyt/AutoLobby.class */
public final class AutoLobby extends Module {

    @NotNull
    private FloatValue health = new FloatValue("Health", 5.0f, 0.0f, 20.0f);

    @NotNull
    private BoolValue canhubchat = new BoolValue("CanHubChat", false);

    @NotNull
    private BoolValue randomhub = new BoolValue("RandomHub", false);

    @NotNull
    private TextValue hubchattext = new TextValue("AutoHubChatText", "You IS L");

    @NotNull
    private BoolValue disabler = new BoolValue("AutoDisable-KillAura-Velocity", true);

    @NotNull
    private BoolValue keepArmor = new BoolValue("KeepArmor", true);

    @NotNull
    private MSTimer hubDelayTime = new MSTimer();

    @NotNull
    public final FloatValue getHealth() {
        return this.health;
    }

    public final void setHealth(@NotNull FloatValue floatValue) {
        Intrinsics.checkParameterIsNotNull(floatValue, "<set-?>");
        this.health = floatValue;
    }

    @NotNull
    public final BoolValue getCanhubchat() {
        return this.canhubchat;
    }

    public final void setCanhubchat(@NotNull BoolValue boolValue) {
        Intrinsics.checkParameterIsNotNull(boolValue, "<set-?>");
        this.canhubchat = boolValue;
    }

    @NotNull
    public final BoolValue getRandomhub() {
        return this.randomhub;
    }

    public final void setRandomhub(@NotNull BoolValue boolValue) {
        Intrinsics.checkParameterIsNotNull(boolValue, "<set-?>");
        this.randomhub = boolValue;
    }

    @NotNull
    public final TextValue getHubchattext() {
        return this.hubchattext;
    }

    public final void setHubchattext(@NotNull TextValue textValue) {
        Intrinsics.checkParameterIsNotNull(textValue, "<set-?>");
        this.hubchattext = textValue;
    }

    @NotNull
    public final BoolValue getDisabler() {
        return this.disabler;
    }

    public final void setDisabler(@NotNull BoolValue boolValue) {
        Intrinsics.checkParameterIsNotNull(boolValue, "<set-?>");
        this.disabler = boolValue;
    }

    @NotNull
    public final BoolValue getKeepArmor() {
        return this.keepArmor;
    }

    public final void setKeepArmor(@NotNull BoolValue boolValue) {
        Intrinsics.checkParameterIsNotNull(boolValue, "<set-?>");
        this.keepArmor = boolValue;
    }

    @NotNull
    public final MSTimer getHubDelayTime() {
        return this.hubDelayTime;
    }

    public final void setHubDelayTime(@NotNull MSTimer mSTimer) {
        Intrinsics.checkParameterIsNotNull(mSTimer, "<set-?>");
        this.hubDelayTime = mSTimer;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        Module module = LiquidBounce.INSTANCE.getModuleManager().get(KillAura.class);
        if (module == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.KillAura");
        }
        KillAura killAura = (KillAura) module;
        Module module2 = LiquidBounce.INSTANCE.getModuleManager().get(Velocity.class);
        if (module2 == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.features.module.modules.combat.Velocity");
        }
        Velocity velocity = (Velocity) module2;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        if (thePlayer.getHealth() <= ((Number) this.health.get()).floatValue()) {
            if (((Boolean) this.keepArmor.get()).booleanValue()) {
                for (int i = 0; i <= 3; i++) {
                    move(8 - (3 - i), true);
                }
            }
            if (((Boolean) this.canhubchat.get()).booleanValue() && this.hubDelayTime.hasTimePassed(350L)) {
                IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer2 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer2.sendChatMessage((String) this.hubchattext.get());
                this.hubDelayTime.reset();
            }
            if (((Boolean) this.randomhub.get()).booleanValue()) {
                if (this.hubDelayTime.hasTimePassed(350L)) {
                    IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer3 == null) {
                        Intrinsics.throwNpe();
                    }
                    thePlayer3.sendChatMessage("/hub " + ((int) ((Math.random() * 100.0d) + 1.0d)));
                    this.hubDelayTime.reset();
                }
            } else if (this.hubDelayTime.hasTimePassed(350L)) {
                IEntityPlayerSP thePlayer4 = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer4 == null) {
                    Intrinsics.throwNpe();
                }
                thePlayer4.sendChatMessage("/hub");
                this.hubDelayTime.reset();
            }
            if (((Boolean) this.disabler.get()).booleanValue()) {
                killAura.setState(false);
                velocity.setState(false);
            }
        }
    }

    private final void move(int i, boolean z) {
        if (i != -1) {
            boolean z2 = !MinecraftInstance.classProvider.isGuiInventory(MinecraftInstance.f157mc.getCurrentScreen());
            if (z2) {
                IClassProvider classProvider = WrapperImpl.INSTANCE.getClassProvider();
                IEntityPlayerSP thePlayer = LiquidBounce.INSTANCE.getWrapper().getMinecraft().getThePlayer();
                if (thePlayer == null) {
                    Intrinsics.throwNpe();
                }
                classProvider.createCPacketEntityAction(thePlayer, ICPacketEntityAction.WAction.OPEN_INVENTORY);
            }
            IPlayerControllerMP playerController = MinecraftInstance.f157mc.getPlayerController();
            IEntityPlayerSP thePlayer2 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer2 == null) {
                Intrinsics.throwNpe();
            }
            int windowId = thePlayer2.getInventoryContainer().getWindowId();
            int i2 = (!z && i < 9) ? i + 36 : i;
            IEntityPlayerSP thePlayer3 = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer3 == null) {
                Intrinsics.throwNpe();
            }
            playerController.windowClick(windowId, i2, 0, 1, thePlayer3);
            if (z2) {
                MinecraftInstance.f157mc.getNetHandler().addToSendQueue(MinecraftInstance.classProvider.createCPacketCloseWindow());
            }
        }
    }
}
