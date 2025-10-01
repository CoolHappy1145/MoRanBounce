package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.item.IItemBucket;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\b\u0012\u0004\u0012\u00020\u00020\u00012\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0002\u00a2\u0006\u0002\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\b\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ItemBucketImpl;", "Lnet/ccbluex/liquidbounce/injection/backend/ItemImpl;", "Lnet/minecraft/item/ItemBucket;", "Lnet/ccbluex/liquidbounce/api/minecraft/item/IItemBucket;", "wrapped", "(Lnet/minecraft/item/ItemBucket;)V", "isFull", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ItemBucketImpl.class */
public final class ItemBucketImpl extends ItemImpl implements IItemBucket {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ItemBucketImpl(@NotNull ItemBucket wrapped) {
        super((Item) wrapped);
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.item.IItemBucket
    @NotNull
    public IBlock isFull() {
        Block block = getWrapped().field_77876_a;
        Intrinsics.checkExpressionValueIsNotNull(block, "wrapped.containedBlock");
        return new BlockImpl(block);
    }
}
