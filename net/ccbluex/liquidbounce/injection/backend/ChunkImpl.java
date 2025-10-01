package net.ccbluex.liquidbounce.injection.backend;

import com.google.common.base.Predicate;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.world.IChunk;
import net.ccbluex.liquidbounce.api.util.WrappedMutableList;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdV\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010!\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0001\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0016J0\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001a2\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u001d0\u001c2\b\u0010\u001e\u001a\u0004\u0018\u00010\u001fH\u0016J\u0018\u0010 \u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\bH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\b8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\f\u0010\n\u00a8\u0006!"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ChunkImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IChunk;", "wrapped", "Lnet/minecraft/world/chunk/Chunk;", "(Lnet/minecraft/world/chunk/Chunk;)V", "getWrapped", "()Lnet/minecraft/world/chunk/Chunk;", "x", "", "getX", "()I", "z", "getZ", "equals", "", "other", "", "getBlockState", "Lnet/ccbluex/liquidbounce/api/minecraft/block/state/IIBlockState;", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getEntitiesWithinAABBForEntity", "", "thePlayer", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntityPlayerSP;", "arrowBox", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "collidedEntities", "", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "nothing", "", "getHeightValue", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ChunkImpl.class */
public final class ChunkImpl implements IChunk {

    @NotNull
    private final Chunk wrapped;

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "p1", "Lnet/minecraft/entity/Entity;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.ChunkImpl$getEntitiesWithinAABBForEntity$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ChunkImpl$getEntitiesWithinAABBForEntity$1.class */
    static final /* synthetic */ class C04351 extends FunctionReference implements Function1 {
        public static final C04351 INSTANCE = new C04351();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(EntityImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04351() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((Entity) obj);
        }

        @NotNull
        public final IEntity invoke(@NotNull Entity p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return new EntityImpl(p1);
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/minecraft/entity/Entity;", "p1", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.ChunkImpl$getEntitiesWithinAABBForEntity$2 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ChunkImpl$getEntitiesWithinAABBForEntity$2.class */
    static final /* synthetic */ class C04362 extends FunctionReference implements Function1 {
        public static final C04362 INSTANCE = new C04362();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(EntityImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04362() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((IEntity) obj);
        }

        @NotNull
        public final Entity invoke(@NotNull IEntity p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return ((EntityImpl) p1).getWrapped();
        }
    }

    @NotNull
    public final Chunk getWrapped() {
        return this.wrapped;
    }

    public ChunkImpl(@NotNull Chunk wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IChunk
    public int getX() {
        return this.wrapped.field_76635_g;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IChunk
    public int getZ() {
        return this.wrapped.field_76647_h;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IChunk
    public void getEntitiesWithinAABBForEntity(@NotNull IEntityPlayerSP thePlayer, @NotNull IAxisAlignedBB arrowBox, @NotNull List collidedEntities, @Nullable Void r13) {
        Intrinsics.checkParameterIsNotNull(thePlayer, "thePlayer");
        Intrinsics.checkParameterIsNotNull(arrowBox, "arrowBox");
        Intrinsics.checkParameterIsNotNull(collidedEntities, "collidedEntities");
        this.wrapped.func_177414_a((EntityPlayerSP) ((EntityPlayerSPImpl) thePlayer).getWrapped(), ((AxisAlignedBBImpl) arrowBox).getWrapped(), new WrappedMutableList(collidedEntities, C04351.INSTANCE, C04362.INSTANCE), (Predicate) null);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IChunk
    public int getHeightValue(int i, int i2) {
        return this.wrapped.func_76611_b(i, i2);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IChunk
    @NotNull
    public IIBlockState getBlockState(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        IBlockState iBlockStateFunc_177435_g = this.wrapped.func_177435_g(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        Intrinsics.checkExpressionValueIsNotNull(iBlockStateFunc_177435_g, "wrapped.getBlockState(blockPos.unwrap())");
        return new IBlockStateImpl(iBlockStateFunc_177435_g);
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof ChunkImpl) && Intrinsics.areEqual(((ChunkImpl) obj).wrapped, this.wrapped);
    }
}
