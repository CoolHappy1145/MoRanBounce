package net.ccbluex.liquidbounce.utils.extensions;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.utils.block.BlockUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 2, m26d1 = {"\ufffd\ufffd\u0012\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u001a\f\u0010\ufffd\ufffd\u001a\u0004\u0018\u00010\u0001*\u00020\u0002\u001a\n\u0010\u0003\u001a\u00020\u0004*\u00020\u0002\u00a8\u0006\u0005"}, m27d2 = {"getBlock", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getVec", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/extensions/BlockExtensionKt.class */
public final class BlockExtensionKt {
    @Nullable
    public static final IBlock getBlock(@NotNull WBlockPos getBlock) {
        Intrinsics.checkParameterIsNotNull(getBlock, "$this$getBlock");
        return BlockUtils.getBlock(getBlock);
    }

    @NotNull
    public static final WVec3 getVec(@NotNull WBlockPos getVec) {
        Intrinsics.checkParameterIsNotNull(getVec, "$this$getVec");
        return new WVec3(getVec.getX() + 0.5d, getVec.getY() + 0.5d, getVec.getZ() + 0.5d);
    }
}
