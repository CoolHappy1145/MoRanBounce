package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemBlock;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\r\u00a8\u0006\u000e"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ItemBlockImpl;", "Lnet/ccbluex/liquidbounce/injection/backend/ItemImpl;", "Lnet/minecraft/item/ItemBlock;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemBlock;", "wrapped", "(Lnet/minecraft/item/ItemBlock;)V", "block", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "getBlock", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "unlocalizedName", "", "getUnlocalizedName", "()Ljava/lang/String;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ItemBlockImpl.class */
public final class ItemBlockImpl extends ItemImpl implements IItemBlock {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ItemBlockImpl(@NotNull ItemBlock wrapped) {
        super((Item) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemBlock
    @NotNull
    public IBlock getBlock() {
        Block blockFunc_179223_d = getWrapped().func_179223_d();
        Intrinsics.checkExpressionValueIsNotNull(blockFunc_179223_d, "wrapped.block");
        return new BlockImpl(blockFunc_179223_d);
    }

    @Override // net.ccbluex.liquidbounce.injection.backend.ItemImpl, net.ccbluex.liquidbounce.api.minecraft.item.IItem
    @NotNull
    public String getUnlocalizedName() {
        String strFunc_77658_a = getWrapped().func_77658_a();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_77658_a, "wrapped.unlocalizedName");
        return strFunc_77658_a;
    }
}
