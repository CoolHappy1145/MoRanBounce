package net.ccbluex.liquidbounce.features.module.modules.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.network.INetworkPlayerInfo;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.PacketEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.ccbluex.liquidbounce.utils.timer.TimeUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import org.apache.log4j.Priority;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdJ\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010!\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015H\u0007J\u0012\u0010\u0016\u001a\u00020\u00122\b\u0010\u0014\u001a\u0004\u0018\u00010\u0017H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\f0\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0018"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/AtAllProvider;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "delay", "", "maxDelayValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "minDelayValue", "msTimer", "Lnet/ccbluex/liquidbounce/utils/timer/MSTimer;", "retryQueue", "", "", "retryValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "sendQueue", "Ljava/util/concurrent/LinkedBlockingQueue;", "onDisable", "", "onPacket", "event", "Lnet/ccbluex/liquidbounce/event/PacketEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "AtAllProvider", description = "Automatically mentions everyone on the server when using '@a' in your message.", category = ModuleCategory.MISC)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/misc/AtAllProvider.class */
public final class AtAllProvider extends Module {
    private final IntegerValue minDelayValue;
    private final IntegerValue maxDelayValue;
    private final BoolValue retryValue;
    private final LinkedBlockingQueue sendQueue;
    private final List retryQueue;
    private final MSTimer msTimer;
    private long delay;

    public AtAllProvider() {
        final String str = "MinDelay";
        final int i = SharedScopeCall.SLOW_SCOPE_CALL_THRESHOLD;
        final int i2 = 0;
        final int i3 = Priority.INFO_INT;
        this.minDelayValue = new IntegerValue(this, str, i, i2, i3) { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.AtAllProvider$minDelayValue$1
            final AtAllProvider this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i4, int i5) {
                int iIntValue = ((Number) this.this$0.maxDelayValue.get()).intValue();
                if (iIntValue < i5) {
                    set((Object) Integer.valueOf(iIntValue));
                }
            }
        };
        final String str2 = "MaxDelay";
        final int i4 = 1000;
        final int i5 = 0;
        final int i6 = Priority.INFO_INT;
        this.maxDelayValue = new IntegerValue(this, str2, i4, i5, i6) { // from class: net.ccbluex.liquidbounce.features.module.modules.misc.AtAllProvider$maxDelayValue$1
            final AtAllProvider this$0;

            public void onChanged(Object obj, Object obj2) {
                onChanged(((Number) obj).intValue(), ((Number) obj2).intValue());
            }

            {
                this.this$0 = this;
            }

            protected void onChanged(int i7, int i8) {
                int iIntValue = ((Number) this.this$0.minDelayValue.get()).intValue();
                if (iIntValue > i8) {
                    set((Object) Integer.valueOf(iIntValue));
                }
            }
        };
        this.retryValue = new BoolValue("Retry", false);
        this.sendQueue = new LinkedBlockingQueue();
        this.retryQueue = new ArrayList();
        this.msTimer = new MSTimer();
        this.delay = TimeUtils.randomDelay(((Number) this.minDelayValue.get()).intValue(), ((Number) this.maxDelayValue.get()).intValue());
    }

    public void onDisable() {
        synchronized (this.sendQueue) {
            this.sendQueue.clear();
            Unit unit = Unit.INSTANCE;
        }
        synchronized (this.retryQueue) {
            this.retryQueue.clear();
            Unit unit2 = Unit.INSTANCE;
        }
    }

    @EventTarget
    public final void onUpdate(@Nullable UpdateEvent updateEvent) {
        if (!this.msTimer.hasTimePassed(this.delay)) {
            return;
        }
        try {
            synchronized (this.sendQueue) {
                if (this.sendQueue.isEmpty()) {
                    if (!((Boolean) this.retryValue.get()).booleanValue() || this.retryQueue.isEmpty()) {
                        return;
                    } else {
                        this.sendQueue.addAll(this.retryQueue);
                    }
                }
                IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                if (thePlayer == null) {
                    Intrinsics.throwNpe();
                }
                Object objTake = this.sendQueue.take();
                Intrinsics.checkExpressionValueIsNotNull(objTake, "sendQueue.take()");
                thePlayer.sendChatMessage((String) objTake);
                this.msTimer.reset();
                this.delay = TimeUtils.randomDelay(((Number) this.minDelayValue.get()).intValue(), ((Number) this.maxDelayValue.get()).intValue());
                Unit unit = Unit.INSTANCE;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @EventTarget
    public final void onPacket(@NotNull PacketEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        if (MinecraftInstance.classProvider.isCPacketChatMessage(event.getPacket())) {
            String message = event.getPacket().asCPacketChatMessage().getMessage();
            if (StringsKt.contains$default((CharSequence) message, (CharSequence) "@a", false, 2, (Object) null)) {
                synchronized (this.sendQueue) {
                    Iterator it = MinecraftInstance.f157mc.getNetHandler().getPlayerInfoMap().iterator();
                    while (it.hasNext()) {
                        String playerName = ((INetworkPlayerInfo) it.next()).getGameProfile().getName();
                        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                        if (thePlayer == null) {
                            Intrinsics.throwNpe();
                        }
                        if (!Intrinsics.areEqual(playerName, thePlayer.getName())) {
                            LinkedBlockingQueue linkedBlockingQueue = this.sendQueue;
                            Intrinsics.checkExpressionValueIsNotNull(playerName, "playerName");
                            linkedBlockingQueue.add(StringsKt.replace$default(message, "@a", playerName, false, 4, (Object) null));
                        }
                    }
                    if (((Boolean) this.retryValue.get()).booleanValue()) {
                        synchronized (this.retryQueue) {
                            this.retryQueue.clear();
                            List list = this.retryQueue;
                            Object[] array = this.sendQueue.toArray(new String[0]);
                            if (array == null) {
                                throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                            }
                            String[] strArr = (String[]) array;
                            list.addAll(CollectionsKt.listOf(Arrays.copyOf(strArr, strArr.length)));
                        }
                    }
                    Unit unit = Unit.INSTANCE;
                }
                event.cancelEvent();
            }
        }
    }
}
