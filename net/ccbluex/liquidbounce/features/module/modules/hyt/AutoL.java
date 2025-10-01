package net.ccbluex.liquidbounce.features.module.modules.hyt;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityLivingBase;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.AttackEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.event.WorldEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.TextValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdN\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\b\u0010\u0016\u001a\u00020\u0013H\u0016J\b\u0010\u0017\u001a\u00020\u0013H\u0016J\u0010\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0019H\u0007J\u0012\u0010\u001a\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u001bH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0016\u0010\u000e\u001a\u0004\u0018\u00010\u000f8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0010\u0010\u0011\u00a8\u0006\u001c"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/hyt/AutoL;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "SendChatDelayTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "attackEntity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityLivingBase;", "autoLText", "Lnet/ccbluex/liquidbounce/value/TextValue;", "canSend", "", "clientName", "sendchatdelay", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "tag", "", "getTag", "()Ljava/lang/String;", "onAttack", "", "event", "Lnet/ccbluex/liquidbounce/event/AttackEvent;", "onDisable", "onEnable", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", "onWorld", "Lnet/ccbluex/liquidbounce/event/WorldEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "AutoL", description = "Auto L is dead", category = ModuleCategory.HYT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/hyt/AutoL.class */
public final class AutoL extends Module {
    private IEntityLivingBase attackEntity;
    private boolean canSend;
    private final TextValue autoLText = new TextValue("AutoLText", "Buy \u7c73\u996d\u56e2\u5b50 \u76f4\u8fdeIP \u65e0\u89c6\u8fde\u9501 \u65e0\u89c6\u5c0f\u53f7 \u5609 1809097884:");
    private final TextValue clientName = new TextValue("ClientName", "God");
    private final IntegerValue sendchatdelay = new IntegerValue("SendChatDelay", 0, 0, 3000);
    private MSTimer SendChatDelayTimer = new MSTimer();

    @EventTarget
    public final void onAttack(@NotNull AttackEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.classProvider.isEntityLivingBase(event.getTargetEntity())) {
            IEntity targetEntity = event.getTargetEntity();
            if (targetEntity == null) {
                Intrinsics.throwNpe();
            }
            this.attackEntity = targetEntity.asEntityLivingBase();
            this.canSend = true;
        }
    }

    public void onDisable() {
        this.canSend = false;
        this.attackEntity = (IEntityLivingBase) null;
    }

    public void onEnable() {
        this.attackEntity = (IEntityLivingBase) null;
        this.canSend = false;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (this.attackEntity != null) {
            IEntityLivingBase iEntityLivingBase = this.attackEntity;
            if (iEntityLivingBase == null) {
                Intrinsics.throwNpe();
            }
            if (iEntityLivingBase.isDead() && this.canSend) {
                IEntityLivingBase iEntityLivingBase2 = this.attackEntity;
                if (iEntityLivingBase2 == null) {
                    Intrinsics.throwNpe();
                }
                if (iEntityLivingBase2.getHealth() == 0.0f) {
                    if (this.SendChatDelayTimer.hasTimePassed(((Number) this.sendchatdelay.get()).intValue())) {
                        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer == null) {
                            Intrinsics.throwNpe();
                        }
                        StringBuilder sbAppend = new StringBuilder().append("@[").append((String) this.clientName.get()).append(']').append((String) this.autoLText.get());
                        IEntityLivingBase iEntityLivingBase3 = this.attackEntity;
                        if (iEntityLivingBase3 == null) {
                            Intrinsics.throwNpe();
                        }
                        thePlayer.sendChatMessage(sbAppend.append(iEntityLivingBase3.getName()).toString());
                        this.SendChatDelayTimer.reset();
                    }
                    this.attackEntity = (IEntityLivingBase) null;
                    this.canSend = false;
                }
            }
        }
    }

    @EventTarget
    public final void onWorld(@Nullable WorldEvent worldEvent) {
        this.attackEntity = (IEntityLivingBase) null;
        this.canSend = false;
    }
}
