package net.ccbluex.liquidbounce.features.module.modules.combat;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/combat/Trigger;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delay", "", "lastSwing", "maxCPS", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "minCPS", "onRender", "", "event", "Lnet/ccbluex/liquidbounce/event/Render3DEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Trigger", description = "Automatically attacks the entity you are looking at.", category = ModuleCategory.COMBAT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/combat/Trigger.class */
public final class Trigger extends Module {
    private final IntegerValue maxCPS;
    private final IntegerValue minCPS;
    private long delay;
    private long lastSwing;

    public Trigger() {
        final String str = "MaxCPS";
        final int i = 8;
        final int i2 = 1;
        final int i3 = 20;
        this.maxCPS = new IntegerValue(this, str, i, i2, i3) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.Trigger$maxCPS$1
            final Trigger this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i4, int i5) {
                int iIntValue = ((Number) this.this$0.minCPS.get()).intValue();
                if (iIntValue > i5) {
                    set((Object) Integer.valueOf(iIntValue));
                }
                this.this$0.delay = TimeUtils.randomClickDelay(((Number) this.this$0.minCPS.get()).intValue(), ((Number) get()).intValue());
            }
        };
        final String str2 = "MinCPS";
        final int i4 = 5;
        final int i5 = 1;
        final int i6 = 20;
        this.minCPS = new IntegerValue(this, str2, i4, i5, i6) { // from class: net.ccbluex.liquidbounce.features.module.modules.combat.Trigger$minCPS$1
            final Trigger this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i7, int i8) {
                int iIntValue = ((Number) this.this$0.maxCPS.get()).intValue();
                if (iIntValue < i8) {
                    set((Object) Integer.valueOf(iIntValue));
                }
                this.this$0.delay = TimeUtils.randomClickDelay(((Number) get()).intValue(), ((Number) this.this$0.maxCPS.get()).intValue());
            }
        };
        this.delay = TimeUtils.randomClickDelay(((Number) this.minCPS.get()).intValue(), ((Number) this.maxCPS.get()).intValue());
    }

    @EventTarget
    public final void onRender(@NotNull Render3DEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        IMovingObjectPosition objectMouseOver = MinecraftInstance.f157mc.getObjectMouseOver();
        if (objectMouseOver != null && System.currentTimeMillis() - this.lastSwing >= this.delay && EntityUtils.isSelected(objectMouseOver.getEntityHit(), true)) {
            MinecraftInstance.f157mc.getGameSettings().getKeyBindAttack().onTick(MinecraftInstance.f157mc.getGameSettings().getKeyBindAttack().getKeyCode());
            this.lastSwing = System.currentTimeMillis();
            this.delay = TimeUtils.randomClickDelay(((Number) this.minCPS.get()).intValue(), ((Number) this.maxCPS.get()).intValue());
        }
    }
}
