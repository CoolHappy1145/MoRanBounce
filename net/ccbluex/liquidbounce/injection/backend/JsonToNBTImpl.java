package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.nbt.IJsonToNBT;
import net.ccbluex.liquidbounce.api.minecraft.nbt.INBTTagCompound;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u0007"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/JsonToNBTImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/IJsonToNBT;", "()V", "getTagFromJson", "Lnet/ccbluex/liquidbounce/api/minecraft/nbt/INBTTagCompound;", "s", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/JsonToNBTImpl.class */
public final class JsonToNBTImpl implements IJsonToNBT {
    public static final JsonToNBTImpl INSTANCE = new JsonToNBTImpl();

    private JsonToNBTImpl() {
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.nbt.IJsonToNBT
    @NotNull
    public INBTTagCompound getTagFromJson(@NotNull String s) {
        Intrinsics.checkParameterIsNotNull(s, "s");
        NBTTagCompound nBTTagCompoundFunc_180713_a = JsonToNBT.func_180713_a(s);
        Intrinsics.checkExpressionValueIsNotNull(nBTTagCompoundFunc_180713_a, "JsonToNBT.getTagFromJson(s)");
        return new NBTTagCompoundImpl(nBTTagCompoundFunc_180713_a);
    }
}
