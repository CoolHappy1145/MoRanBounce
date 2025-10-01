package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.block.material.IMaterial;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdp\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\u0007\n\u0002\b\b\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0001\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u001a\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u001b\u001a\u00020\u0019H\u0016J\u0013\u0010\u001c\u001a\u00020\u00192\b\u0010\u001d\u001a\u0004\u0018\u00010\u001eH\u0096\u0002J\"\u0010\u001f\u001a\u0004\u0018\u00010 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$2\u0006\u0010\u001a\u001a\u00020\u0006H\u0016J\u0010\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u0001H\u0016J \u0010(\u001a\u00020&2\u0006\u0010)\u001a\u00020\u00062\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020$H\u0016J\u0012\u0010-\u001a\u0004\u0018\u00010.2\u0006\u0010\u001a\u001a\u00020\u0006H\u0016J \u0010/\u001a\u00020\u00102\u0006\u00100\u001a\u0002012\u0006\u0010*\u001a\u00020\"2\u0006\u00102\u001a\u00020$H\u0016J \u00103\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010)\u001a\u00020\u00062\u0006\u00102\u001a\u00020$H\u0016J\u0010\u00104\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u0006H\u0016J\u0010\u00105\u001a\u00020\u00192\u0006\u0010)\u001a\u00020\u0006H\u0016J\u0018\u00106\u001a\u0002072\u0006\u0010!\u001a\u00020\"2\u0006\u00102\u001a\u00020$H\u0016R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\fR$\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u000f\u001a\u00020\u00108V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0016\u0010\u0017\u00a8\u00068"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/BlockImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/block/IBlock;", "wrapped", "Lnet/minecraft/block/Block;", "(Lnet/minecraft/block/Block;)V", "defaultState", "Lnet/ccbluex/liquidbounce/api/minecraft/block/state/IIBlockState;", "getDefaultState", "()Lnet/ccbluex/liquidbounce/api/minecraft/block/state/IIBlockState;", "localizedName", "", "getLocalizedName", "()Ljava/lang/String;", "registryName", "getRegistryName", PropertyDescriptor.VALUE, "", "slipperiness", "getSlipperiness", "()F", "setSlipperiness", "(F)V", "getWrapped", "()Lnet/minecraft/block/Block;", "canCollideCheck", "", "state", "hitIfLiquid", "equals", "other", "", "getCollisionBoundingBox", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "world", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorld;", "pos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getIdFromBlock", "", "block", "getMapColor", "blockState", "theWorld", "Lnet/ccbluex/liquidbounce/api/minecraft/client/multiplayer/IWorldClient;", "bp", "getMaterial", "Lnet/ccbluex/liquidbounce/api/minecraft/block/material/IMaterial;", "getPlayerRelativeBlockHardness", "thePlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "blockPos", "getSelectedBoundingBox", "isFullCube", "isTranslucent", "setBlockBoundsBasedOnState", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/BlockImpl.class */
public final class BlockImpl implements IBlock {

    @NotNull
    private final Block wrapped;

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock
    /* renamed from: setBlockBoundsBasedOnState, reason: collision with other method in class */
    public void mo1613setBlockBoundsBasedOnState(IWorld iWorld, WBlockPos wBlockPos) {
        setBlockBoundsBasedOnState(iWorld, wBlockPos);
    }

    @NotNull
    public final Block getWrapped() {
        return this.wrapped;
    }

    public BlockImpl(@NotNull Block wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock
    @NotNull
    public String getRegistryName() {
        String strFunc_149739_a = this.wrapped.func_149739_a();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_149739_a, "wrapped.unlocalizedName");
        return strFunc_149739_a;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock
    public float getSlipperiness() {
        return this.wrapped.field_149765_K;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock
    public void setSlipperiness(float f) {
        this.wrapped.field_149765_K = f;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock
    @Nullable
    public IIBlockState getDefaultState() {
        IBlockState iBlockStateFunc_176223_P = this.wrapped.func_176223_P();
        Intrinsics.checkExpressionValueIsNotNull(iBlockStateFunc_176223_P, "wrapped.defaultState");
        return new IBlockStateImpl(iBlockStateFunc_176223_P);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock
    @NotNull
    public String getLocalizedName() {
        String strFunc_149732_F = this.wrapped.func_149732_F();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_149732_F, "wrapped.localizedName");
        return strFunc_149732_F;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock
    @NotNull
    public IAxisAlignedBB getSelectedBoundingBox(@NotNull IWorld world, @NotNull IIBlockState blockState, @NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(world, "world");
        Intrinsics.checkParameterIsNotNull(blockState, "blockState");
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        AxisAlignedBB axisAlignedBBFunc_186670_a = this.wrapped.func_185496_a(((IBlockStateImpl) blockState).getWrapped(), ((WorldImpl) world).getWrapped(), new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ())).func_186670_a(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        Intrinsics.checkExpressionValueIsNotNull(axisAlignedBBFunc_186670_a, "wrapped.getBoundingBox(b\u2026offset(blockPos.unwrap())");
        return new AxisAlignedBBImpl(axisAlignedBBFunc_186670_a);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock
    @Nullable
    public IAxisAlignedBB getCollisionBoundingBox(@NotNull IWorld world, @NotNull WBlockPos pos, @NotNull IIBlockState state) {
        Intrinsics.checkParameterIsNotNull(world, "world");
        Intrinsics.checkParameterIsNotNull(pos, "pos");
        Intrinsics.checkParameterIsNotNull(state, "state");
        AxisAlignedBB axisAlignedBBFunc_180646_a = this.wrapped.func_180646_a(((IBlockStateImpl) state).getWrapped(), ((WorldImpl) world).getWrapped(), new BlockPos(pos.getX(), pos.getY(), pos.getZ()));
        if (axisAlignedBBFunc_180646_a != null) {
            return new AxisAlignedBBImpl(axisAlignedBBFunc_180646_a);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock
    public boolean canCollideCheck(@Nullable IIBlockState iIBlockState, boolean z) {
        IBlockState wrapped;
        Block block = this.wrapped;
        if (iIBlockState == null) {
            wrapped = null;
        } else {
            if (iIBlockState == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.IBlockStateImpl");
            }
            block = block;
            wrapped = ((IBlockStateImpl) iIBlockState).getWrapped();
        }
        return block.func_176209_a(wrapped, z);
    }

    @NotNull
    public Void setBlockBoundsBasedOnState(@NotNull IWorld world, @NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(world, "world");
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        Backend backend = Backend.INSTANCE;
        throw new NotImplementedError("1.12.2 doesn't support this feature'");
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock
    public float getPlayerRelativeBlockHardness(@NotNull IEntityPlayerSP thePlayer, @NotNull IWorld theWorld, @NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(thePlayer, "thePlayer");
        Intrinsics.checkParameterIsNotNull(theWorld, "theWorld");
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        Block block = this.wrapped;
        IIBlockState blockState = theWorld.getBlockState(blockPos);
        if (blockState == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.IBlockStateImpl");
        }
        return block.func_180647_a(((IBlockStateImpl) blockState).getWrapped(), (EntityPlayerSP) ((EntityPlayerSPImpl) thePlayer).getWrapped(), ((WorldImpl) theWorld).getWrapped(), new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock
    public int getIdFromBlock(@NotNull IBlock block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        return Block.func_149682_b(((BlockImpl) block).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock
    public boolean isTranslucent(@NotNull IIBlockState blockState) {
        Intrinsics.checkParameterIsNotNull(blockState, "blockState");
        return this.wrapped.func_149751_l(((IBlockStateImpl) blockState).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock
    public int getMapColor(@NotNull IIBlockState blockState, @NotNull IWorldClient theWorld, @NotNull WBlockPos bp) {
        Intrinsics.checkParameterIsNotNull(blockState, "blockState");
        Intrinsics.checkParameterIsNotNull(theWorld, "theWorld");
        Intrinsics.checkParameterIsNotNull(bp, "bp");
        return this.wrapped.func_180659_g(((IBlockStateImpl) blockState).getWrapped(), (WorldClient) ((WorldClientImpl) theWorld).getWrapped(), new BlockPos(bp.getX(), bp.getY(), bp.getZ())).field_76291_p;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock
    @Nullable
    public IMaterial getMaterial(@NotNull IIBlockState state) {
        Intrinsics.checkParameterIsNotNull(state, "state");
        Material materialFunc_149688_o = this.wrapped.func_149688_o(((IBlockStateImpl) state).getWrapped());
        Intrinsics.checkExpressionValueIsNotNull(materialFunc_149688_o, "wrapped.getMaterial(state.unwrap())");
        return new MaterialImpl(materialFunc_149688_o);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock
    public boolean isFullCube(@NotNull IIBlockState state) {
        Intrinsics.checkParameterIsNotNull(state, "state");
        return this.wrapped.func_149686_d(((IBlockStateImpl) state).getWrapped());
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof BlockImpl) && Intrinsics.areEqual(((BlockImpl) obj).wrapped, this.wrapped);
    }
}
