package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.creativetabs.ICreativeTabs;
import net.minecraft.creativetab.CreativeTabs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011H\u0096\u0002R$\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u00068V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\f\u0010\r\u00a8\u0006\u0012"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/CreativeTabsImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/creativetabs/ICreativeTabs;", "wrapped", "Lnet/minecraft/creativetab/CreativeTabs;", "(Lnet/minecraft/creativetab/CreativeTabs;)V", PropertyDescriptor.VALUE, "", "backgroundImageName", "getBackgroundImageName", "()Ljava/lang/String;", "setBackgroundImageName", "(Ljava/lang/String;)V", "getWrapped", "()Lnet/minecraft/creativetab/CreativeTabs;", "equals", "", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/CreativeTabsImpl.class */
public final class CreativeTabsImpl implements ICreativeTabs {

    @NotNull
    private final CreativeTabs wrapped;

    @NotNull
    public final CreativeTabs getWrapped() {
        return this.wrapped;
    }

    public CreativeTabsImpl(@NotNull CreativeTabs wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.creativetabs.ICreativeTabs
    @NotNull
    public String getBackgroundImageName() {
        String strFunc_78015_f = this.wrapped.func_78015_f();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_78015_f, "wrapped.backgroundImageName");
        return strFunc_78015_f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.creativetabs.ICreativeTabs
    public void setBackgroundImageName(@NotNull String value) {
        Intrinsics.checkParameterIsNotNull(value, "value");
        this.wrapped.func_78025_a(value);
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof CreativeTabsImpl) && Intrinsics.areEqual(((CreativeTabsImpl) obj).wrapped, this.wrapped);
    }
}
