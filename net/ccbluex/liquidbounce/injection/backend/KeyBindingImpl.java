package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.settings.IKeyBinding;
import net.minecraft.client.settings.KeyBinding;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0013\u001a\u00020\u00062\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0096\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\b\u001a\u00020\tH\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000bR$\u0010\r\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u00068V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u000e\u0010\u0007\"\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u0018"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/KeyBindingImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/settings/IKeyBinding;", "wrapped", "Lnet/minecraft/client/settings/KeyBinding;", "(Lnet/minecraft/client/settings/KeyBinding;)V", "isKeyDown", "", "()Z", "keyCode", "", "getKeyCode", "()I", PropertyDescriptor.VALUE, "pressed", "getPressed", "setPressed", "(Z)V", "getWrapped", "()Lnet/minecraft/client/settings/KeyBinding;", "equals", "other", "", "onTick", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/KeyBindingImpl.class */
public final class KeyBindingImpl implements IKeyBinding {

    @NotNull
    private final KeyBinding wrapped;

    @NotNull
    public final KeyBinding getWrapped() {
        return this.wrapped;
    }

    public KeyBindingImpl(@NotNull KeyBinding wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IKeyBinding
    public int getKeyCode() {
        return this.wrapped.func_151463_i();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IKeyBinding
    public boolean getPressed() {
        return this.wrapped.field_74513_e;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IKeyBinding
    public void setPressed(boolean z) {
        this.wrapped.field_74513_e = z;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IKeyBinding
    public boolean isKeyDown() {
        return this.wrapped.func_151470_d();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.settings.IKeyBinding
    public void onTick(int i) {
        KeyBinding.func_74507_a(i);
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof KeyBindingImpl) && Intrinsics.areEqual(((KeyBindingImpl) obj).wrapped, this.wrapped);
    }
}
