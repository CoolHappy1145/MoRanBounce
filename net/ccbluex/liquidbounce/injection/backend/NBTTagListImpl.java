package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTBase;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagList;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd4\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\b\u0010\u000e\u001a\u00020\u000fH\u0016J\b\u0010\u0010\u001a\u00020\rH\u0016\u00a8\u0006\u0011"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/NBTTagListImpl;", "Lnet/ccbluex/liquidbounce/injection/backend/NBTBaseImpl;", "Lnet/minecraft/nbt/NBTTagList;", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagList;", "wrapped", "(Lnet/minecraft/nbt/NBTTagList;)V", "appendTag", "", "createNBTTagString", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTBase;", "getCompoundTagAt", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;", "index", "", "hasNoTags", "", "tagCount", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/NBTTagListImpl.class */
public final class NBTTagListImpl extends NBTBaseImpl implements INBTTagList {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public NBTTagListImpl(@NotNull NBTTagList wrapped) {
        super((NBTBase) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagList
    public boolean hasNoTags() {
        return getWrapped().func_82582_d();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagList
    public int tagCount() {
        return getWrapped().func_74745_c();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagList
    @NotNull
    public INBTTagCompound getCompoundTagAt(int i) {
        NBTTagCompound nBTTagCompoundFunc_150305_b = getWrapped().func_150305_b(i);
        Intrinsics.checkExpressionValueIsNotNull(nBTTagCompoundFunc_150305_b, "wrapped.getCompoundTagAt(index)");
        return new NBTTagCompoundImpl(nBTTagCompoundFunc_150305_b);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagList
    public void appendTag(@NotNull INBTBase createNBTTagString) {
        Intrinsics.checkParameterIsNotNull(createNBTTagString, "createNBTTagString");
        getWrapped().func_74742_a(((NBTBaseImpl) createNBTTagString).getWrapped());
    }
}
