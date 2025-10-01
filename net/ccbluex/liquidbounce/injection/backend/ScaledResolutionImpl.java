package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution;
import net.minecraft.client.gui.ScaledResolution;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0014\u0010\u000b\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0013"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ScaledResolutionImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IScaledResolution;", "wrapped", "Lnet/minecraft/client/gui/ScaledResolution;", "(Lnet/minecraft/client/gui/ScaledResolution;)V", "scaleFactor", "", "getScaleFactor", "()I", "scaledHeight", "getScaledHeight", "scaledWidth", "getScaledWidth", "getWrapped", "()Lnet/minecraft/client/gui/ScaledResolution;", "equals", "", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ScaledResolutionImpl.class */
public final class ScaledResolutionImpl implements IScaledResolution {

    @NotNull
    private final ScaledResolution wrapped;

    @NotNull
    public final ScaledResolution getWrapped() {
        return this.wrapped;
    }

    public ScaledResolutionImpl(@NotNull ScaledResolution wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution
    public int getScaledWidth() {
        return this.wrapped.func_78326_a();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution
    public int getScaledHeight() {
        return this.wrapped.func_78328_b();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IScaledResolution
    public int getScaleFactor() {
        return this.wrapped.func_78325_e();
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof ScaledResolutionImpl) && Intrinsics.areEqual(((ScaledResolutionImpl) obj).wrapped, this.wrapped);
    }
}
