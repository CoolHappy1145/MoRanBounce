package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.potion.IPotion;
import net.minecraft.potion.Potion;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd0\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0017\u001a\u00020\u00062\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\fR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0014\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006\u001a"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/PotionImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/potion/IPotion;", "wrapped", "Lnet/minecraft/potion/Potion;", "(Lnet/minecraft/potion/Potion;)V", "hasStatusIcon", "", "getHasStatusIcon", "()Z", "id", "", "getId", "()I", "liquidColor", "getLiquidColor", "name", "", "getName", "()Ljava/lang/String;", "statusIconIndex", "getStatusIconIndex", "getWrapped", "()Lnet/minecraft/potion/Potion;", "equals", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/PotionImpl.class */
public final class PotionImpl implements IPotion {

    @NotNull
    private final Potion wrapped;

    @NotNull
    public final Potion getWrapped() {
        return this.wrapped;
    }

    public PotionImpl(@NotNull Potion wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.potion.IPotion
    public int getLiquidColor() {
        return this.wrapped.func_76401_j();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.potion.IPotion
    public int getId() {
        return Potion.func_188409_a(this.wrapped);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.potion.IPotion
    @NotNull
    public String getName() {
        String strFunc_76393_a = this.wrapped.func_76393_a();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_76393_a, "wrapped.name");
        return strFunc_76393_a;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.potion.IPotion
    public boolean getHasStatusIcon() {
        return this.wrapped.func_76400_d();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.potion.IPotion
    public int getStatusIconIndex() {
        return this.wrapped.func_76392_e();
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof PotionImpl) && Intrinsics.areEqual(((PotionImpl) obj).wrapped, this.wrapped);
    }
}
