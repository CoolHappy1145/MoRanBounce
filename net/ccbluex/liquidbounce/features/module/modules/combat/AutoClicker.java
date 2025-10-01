package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.RandomUtils;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd4\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0007J\u0010\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0014H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000b\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000e\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0015"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/AutoClicker;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "jitterValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "leftDelay", "", "leftLastSwing", "leftValue", "maxCPSValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "minCPSValue", "rightDelay", "rightLastSwing", "rightValue", "onRender", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "AutoClicker", description = "Constantly clicks when holding down a mouse button.", category = ModuleCategory.COMBAT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/AutoClicker.class */
public final class AutoClicker extends Module {
    private final IntegerValue maxCPSValue;
    private final IntegerValue minCPSValue;
    private final BoolValue rightValue = new BoolValue("Right", true);
    private final BoolValue leftValue = new BoolValue("Left", true);
    private final BoolValue jitterValue = new BoolValue("Jitter", false);
    private long rightDelay;
    private long rightLastSwing;
    private long leftDelay;
    private long leftLastSwing;

    public AutoClicker() {
        final String str = "MaxCPS";
        final int i = 8;
        final int i2 = 1;
        final int i3 = 20;
        this.maxCPSValue = new IntegerValue(this, str, i, i2, i3) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.AutoClicker$maxCPSValue$1
            final AutoClicker this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i4, int i5) {
                int iIntValue = ((Number) this.this$0.minCPSValue.get()).intValue();
                if (iIntValue > i5) {
                    set((Object) Integer.valueOf(iIntValue));
                }
            }
        };
        final String str2 = "MinCPS";
        final int i4 = 5;
        final int i5 = 1;
        final int i6 = 20;
        this.minCPSValue = new IntegerValue(this, str2, i4, i5, i6) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.AutoClicker$minCPSValue$1
            final AutoClicker this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i7, int i8) {
                int iIntValue = ((Number) this.this$0.maxCPSValue.get()).intValue();
                if (iIntValue < i8) {
                    set((Object) Integer.valueOf(iIntValue));
                }
            }
        };
        this.rightDelay = TimeUtils.randomClickDelay(((Number) this.minCPSValue.get()).intValue(), ((Number) this.maxCPSValue.get()).intValue());
        this.leftDelay = TimeUtils.randomClickDelay(((Number) this.minCPSValue.get()).intValue(), ((Number) this.maxCPSValue.get()).intValue());
    }

    @EventTarget
    public final void onRender(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.f157mc.getGameSettings().getKeyBindAttack().isKeyDown() && ((Boolean) this.leftValue.get()).booleanValue() && System.currentTimeMillis() - this.leftLastSwing >= this.leftDelay && MinecraftInstance.f157mc.getPlayerController().getCurBlockDamageMP() == 0.0f) {
            MinecraftInstance.f157mc.getGameSettings().getKeyBindAttack().onTick(MinecraftInstance.f157mc.getGameSettings().getKeyBindAttack().getKeyCode());
            this.leftLastSwing = System.currentTimeMillis();
            this.leftDelay = TimeUtils.randomClickDelay(((Number) this.minCPSValue.get()).intValue(), ((Number) this.maxCPSValue.get()).intValue());
        }
        if (MinecraftInstance.f157mc.getGameSettings().getKeyBindUseItem().isKeyDown()) {
            IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
            if (thePlayer == null) {
                Intrinsics.throwNpe();
            }
            if (!thePlayer.isUsingItem() && ((Boolean) this.rightValue.get()).booleanValue() && System.currentTimeMillis() - this.rightLastSwing >= this.rightDelay) {
                MinecraftInstance.f157mc.getGameSettings().getKeyBindAttack().onTick(MinecraftInstance.f157mc.getGameSettings().getKeyBindUseItem().getKeyCode());
                this.rightLastSwing = System.currentTimeMillis();
                this.rightDelay = TimeUtils.randomClickDelay(((Number) this.minCPSValue.get()).intValue(), ((Number) this.maxCPSValue.get()).intValue());
            }
        }
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null && ((Boolean) this.jitterValue.get()).booleanValue()) {
            if ((((Boolean) this.leftValue.get()).booleanValue() && MinecraftInstance.f157mc.getGameSettings().getKeyBindAttack().isKeyDown() && MinecraftInstance.f157mc.getPlayerController().getCurBlockDamageMP() == 0.0f) || (((Boolean) this.rightValue.get()).booleanValue() && MinecraftInstance.f157mc.getGameSettings().getKeyBindUseItem().isKeyDown() && !thePlayer.isUsingItem())) {
                if (Random.Default.nextBoolean()) {
                    thePlayer.setRotationYaw(thePlayer.getRotationYaw() + (Random.Default.nextBoolean() ? -RandomUtils.INSTANCE.nextFloat(0.0f, 1.0f) : RandomUtils.INSTANCE.nextFloat(0.0f, 1.0f)));
                }
                if (Random.Default.nextBoolean()) {
                    thePlayer.setRotationPitch(thePlayer.getRotationPitch() + (Random.Default.nextBoolean() ? -RandomUtils.INSTANCE.nextFloat(0.0f, 1.0f) : RandomUtils.INSTANCE.nextFloat(0.0f, 1.0f)));
                    if (thePlayer.getRotationPitch() > 90.0f) {
                        thePlayer.setRotationPitch(90.0f);
                    } else if (thePlayer.getRotationPitch() < -90.0f) {
                        thePlayer.setRotationPitch(-90.0f);
                    }
                }
            }
        }
    }
}
