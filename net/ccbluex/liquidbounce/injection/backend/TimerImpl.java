package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.ITimer;
import net.ccbluex.liquidbounce.injection.implementations.IMixinTimer;
import net.minecraft.util.Timer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\n\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0013H\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR$\u0010\n\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\u00068V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u000b\u0010\b\"\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0014"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/TimerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/ITimer;", "wrapped", "Lnet/minecraft/util/Timer;", "(Lnet/minecraft/util/Timer;)V", "renderPartialTicks", "", "getRenderPartialTicks", "()F", PropertyDescriptor.VALUE, "timerSpeed", "getTimerSpeed", "setTimerSpeed", "(F)V", "getWrapped", "()Lnet/minecraft/util/Timer;", "equals", "", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/TimerImpl.class */
public final class TimerImpl implements ITimer {

    @NotNull
    private final Timer wrapped;

    @NotNull
    public final Timer getWrapped() {
        return this.wrapped;
    }

    public TimerImpl(@NotNull Timer wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.ITimer
    public float getTimerSpeed() {
        IMixinTimer iMixinTimer = this.wrapped;
        if (iMixinTimer == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.implementations.IMixinTimer");
        }
        return iMixinTimer.getTimerSpeed();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.ITimer
    public void setTimerSpeed(float f) {
        IMixinTimer iMixinTimer = this.wrapped;
        if (iMixinTimer == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.implementations.IMixinTimer");
        }
        iMixinTimer.setTimerSpeed(f);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.ITimer
    public float getRenderPartialTicks() {
        return this.wrapped.field_194147_b;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof TimerImpl) && Intrinsics.areEqual(((TimerImpl) obj).wrapped, this.wrapped);
    }
}
