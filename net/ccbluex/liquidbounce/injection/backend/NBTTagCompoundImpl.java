package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTBase;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\n\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0018\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u000f\u001a\u00020\tH\u0016J\u0018\u0010\u0012\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0014H\u0016\u00a8\u0006\u0015"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/NBTTagCompoundImpl;", "Lnet/ccbluex/liquidbounce/injection/backend/NBTBaseImpl;", "Lnet/minecraft/nbt/NBTTagCompound;", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;", "wrapped", "(Lnet/minecraft/nbt/NBTTagCompound;)V", "getShort", "", "name", "", "hasKey", "", "setInteger", "", "key", PropertyDescriptor.VALUE, "", "setString", "setTag", "tag", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTBase;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/NBTTagCompoundImpl.class */
public final class NBTTagCompoundImpl extends NBTBaseImpl implements INBTTagCompound {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NBTTagCompoundImpl(@NotNull NBTTagCompound wrapped) {
        super((NBTBase) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound
    public boolean hasKey(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return getWrapped().func_74764_b(name);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound
    public short getShort(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        return getWrapped().func_74765_d(name);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound
    public void setString(@NotNull String key, @NotNull String value) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(value, "value");
        getWrapped().func_74778_a(key, value);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound
    public void setTag(@NotNull String key, @NotNull INBTBase tag) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        getWrapped().func_74782_a(key, ((NBTBaseImpl) tag).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound
    public void setInteger(@NotNull String key, int i) {
        Intrinsics.checkParameterIsNotNull(key, "key");
        getWrapped().func_74768_a(key, i);
    }
}
