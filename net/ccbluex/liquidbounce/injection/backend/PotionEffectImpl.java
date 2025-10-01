package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0014H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0015"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/PotionEffectImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotionEffect;", "wrapped", "Lnet/minecraft/potion/PotionEffect;", "(Lnet/minecraft/potion/PotionEffect;)V", "amplifier", "", "getAmplifier", "()I", "duration", "getDuration", "potionID", "getPotionID", "getWrapped", "()Lnet/minecraft/potion/PotionEffect;", "equals", "", "other", "", "getDurationString", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/PotionEffectImpl.class */
public final class PotionEffectImpl implements IPotionEffect {

    @NotNull
    private final PotionEffect wrapped;

    @NotNull
    public final PotionEffect getWrapped() {
        return this.wrapped;
    }

    public PotionEffectImpl(@NotNull PotionEffect wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect
    @NotNull
    public String getDurationString() {
        String strFunc_188410_a = Potion.func_188410_a(this.wrapped, 1.0f);
        Intrinsics.checkExpressionValueIsNotNull(strFunc_188410_a, "Potion.getPotionDurationString(wrapped, 1.0f)");
        return strFunc_188410_a;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect
    public int getAmplifier() {
        return this.wrapped.func_76458_c();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect
    public int getDuration() {
        return this.wrapped.func_76459_b();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.potion.IPotionEffect
    public int getPotionID() {
        return Potion.func_188409_a(this.wrapped.func_188419_a());
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof PotionEffectImpl) && Intrinsics.areEqual(((PotionEffectImpl) obj).wrapped, this.wrapped);
    }
}
