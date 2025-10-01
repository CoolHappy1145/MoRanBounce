package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.entity.player.IPlayerCapabilities;
import net.minecraft.entity.player.PlayerCapabilities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u000b\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0010\u001a\u00020\u00062\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\bR$\u0010\u000b\u001a\u00020\u00062\u0006\u0010\n\u001a\u00020\u00068V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u000b\u0010\b\"\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0013"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/PlayerCapabilitiesImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/entity/player/IPlayerCapabilities;", "wrapped", "Lnet/minecraft/entity/player/PlayerCapabilities;", "(Lnet/minecraft/entity/player/PlayerCapabilities;)V", "allowFlying", "", "getAllowFlying", "()Z", "isCreativeMode", PropertyDescriptor.VALUE, "isFlying", "setFlying", "(Z)V", "getWrapped", "()Lnet/minecraft/entity/player/PlayerCapabilities;", "equals", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/PlayerCapabilitiesImpl.class */
public final class PlayerCapabilitiesImpl implements IPlayerCapabilities {

    @NotNull
    private final PlayerCapabilities wrapped;

    @NotNull
    public final PlayerCapabilities getWrapped() {
        return this.wrapped;
    }

    public PlayerCapabilitiesImpl(@NotNull PlayerCapabilities wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.entity.player.IPlayerCapabilities
    public boolean getAllowFlying() {
        return this.wrapped.field_75101_c;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.entity.player.IPlayerCapabilities
    public boolean isFlying() {
        return this.wrapped.field_75100_b;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.entity.player.IPlayerCapabilities
    public void setFlying(boolean z) {
        this.wrapped.field_75100_b = z;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.entity.player.IPlayerCapabilities
    public boolean isCreativeMode() {
        return this.wrapped.field_75098_d;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof PlayerCapabilitiesImpl) && Intrinsics.areEqual(((PlayerCapabilitiesImpl) obj).wrapped, this.wrapped);
    }
}
