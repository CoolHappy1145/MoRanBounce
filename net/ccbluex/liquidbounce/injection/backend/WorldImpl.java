package net.ccbluex.liquidbounce.injection.backend;

import com.google.common.base.Predicate;
import java.util.Collection;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScoreboard;
import net.ccbluex.liquidbounce.api.minecraft.util.IAxisAlignedBB;
import net.ccbluex.liquidbounce.api.minecraft.util.IMovingObjectPosition;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.util.WVec3;
import net.ccbluex.liquidbounce.api.minecraft.world.IChunk;
import net.ccbluex.liquidbounce.api.minecraft.world.IWorld;
import net.ccbluex.liquidbounce.api.minecraft.world.border.IWorldBorder;
import net.ccbluex.liquidbounce.api.util.WrappedCollection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdv\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u001e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0016\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0005J\u0010\u0010\u0014\u001a\u00020\u00072\u0006\u0010\u0015\u001a\u00020\u0016H\u0016J\u0013\u0010\u0017\u001a\u00020\u00072\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0096\u0002J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0016J\u0018\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020!H\u0016J\u001e\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00160$2\u0006\u0010%\u001a\u00020&2\u0006\u0010'\u001a\u00020\u0016H\u0016J\u0016\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00160$2\u0006\u0010'\u001a\u00020\u0016H\u0016J6\u0010)\u001a\b\u0012\u0004\u0012\u00020&0$2\b\u0010*\u001a\u0004\u0018\u00010&2\u0006\u0010+\u001a\u00020\u00162\u0014\u0010,\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010&\u0012\u0004\u0012\u00020\u00070-H\u0016J \u0010.\u001a\b\u0012\u0004\u0012\u00020&0$2\b\u0010%\u001a\u0004\u0018\u00010&2\u0006\u0010'\u001a\u00020\u0016H\u0016J\u0012\u0010/\u001a\u0004\u0018\u00010&2\u0006\u00100\u001a\u00020!H\u0016J\u001a\u00101\u001a\u0004\u0018\u0001022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u000204H\u0016J\"\u00101\u001a\u0004\u0018\u0001022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u0002042\u0006\u00106\u001a\u00020\u0007H\u0016J2\u00101\u001a\u0004\u0018\u0001022\u0006\u00103\u001a\u0002042\u0006\u00105\u001a\u0002042\u0006\u00106\u001a\u00020\u00072\u0006\u00107\u001a\u00020\u00072\u0006\u00108\u001a\u00020\u0007H\u0016R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0006\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0004\u001a\u00028\ufffd\ufffd\u00a2\u0006\n\n\u0002\u0010\u0013\u001a\u0004\b\u0011\u0010\u0012\u00a8\u00069"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/WorldImpl;", "T", "Lnet/minecraft/world/World;", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IWorld;", "wrapped", "(Lnet/minecraft/world/World;)V", "isRemote", "", "()Z", "scoreboard", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/IScoreboard;", "getScoreboard", "()Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/IScoreboard;", "worldBorder", "Lnet/ccbluex/liquidbounce/api/minecraft/world/border/IWorldBorder;", "getWorldBorder", "()Lnet/ccbluex/liquidbounce/api/minecraft/world/border/IWorldBorder;", "getWrapped", "()Lnet/minecraft/world/World;", "Lnet/minecraft/world/World;", "checkBlockCollision", "aabb", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "equals", "other", "", "getBlockState", "Lnet/ccbluex/liquidbounce/api/minecraft/block/state/IIBlockState;", "blockPos", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WBlockPos;", "getChunkFromChunkCoords", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IChunk;", "x", "", "z", "getCollidingBoundingBoxes", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "bb", "getCollisionBoxes", "getEntitiesInAABBexcluding", "entityIn", "boundingBox", "predicate", "Lkotlin/Function1;", "getEntitiesWithinAABBExcludingEntity", "getEntityByID", "id", "rayTraceBlocks", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IMovingObjectPosition;", "start", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WVec3;", AsmConstants.END, "stopOnLiquid", "ignoreBlockWithoutBoundingBox", "returnLastUncollidableBlock", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/WorldImpl.class */
public class WorldImpl implements IWorld {

    @NotNull
    private final World wrapped;

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u0001\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\b\u0006"}, m27d2 = {"<anonymous>", "Lnet/minecraft/util/math/AxisAlignedBB;", "T", "Lnet/minecraft/world/World;", "p1", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.WorldImpl$getCollidingBoundingBoxes$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/WorldImpl$getCollidingBoundingBoxes$1.class */
    static final /* synthetic */ class C04511 extends FunctionReference implements Function1 {
        public static final C04511 INSTANCE = new C04511();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(AxisAlignedBBImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04511() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((IAxisAlignedBB) obj);
        }

        @NotNull
        public final AxisAlignedBB invoke(@NotNull IAxisAlignedBB p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return ((AxisAlignedBBImpl) p1).getWrapped();
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u0001\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\b\u0006"}, m27d2 = {"<anonymous>", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "T", "Lnet/minecraft/world/World;", "p1", "Lnet/minecraft/util/math/AxisAlignedBB;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.WorldImpl$getCollidingBoundingBoxes$2 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/WorldImpl$getCollidingBoundingBoxes$2.class */
    static final /* synthetic */ class C04522 extends FunctionReference implements Function1 {
        public static final C04522 INSTANCE = new C04522();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(AxisAlignedBBImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04522() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((AxisAlignedBB) obj);
        }

        @NotNull
        public final IAxisAlignedBB invoke(@NotNull AxisAlignedBB p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return new AxisAlignedBBImpl(p1);
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u0001\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\b\u0006"}, m27d2 = {"<anonymous>", "Lnet/minecraft/util/math/AxisAlignedBB;", "T", "Lnet/minecraft/world/World;", "p1", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.WorldImpl$getCollisionBoxes$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/WorldImpl$getCollisionBoxes$1.class */
    static final /* synthetic */ class C04531 extends FunctionReference implements Function1 {
        public static final C04531 INSTANCE = new C04531();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(AxisAlignedBBImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04531() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((IAxisAlignedBB) obj);
        }

        @NotNull
        public final AxisAlignedBB invoke(@NotNull IAxisAlignedBB p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return ((AxisAlignedBBImpl) p1).getWrapped();
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u0001\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\b\u0006"}, m27d2 = {"<anonymous>", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IAxisAlignedBB;", "T", "Lnet/minecraft/world/World;", "p1", "Lnet/minecraft/util/math/AxisAlignedBB;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.WorldImpl$getCollisionBoxes$2 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/WorldImpl$getCollisionBoxes$2.class */
    static final /* synthetic */ class C04542 extends FunctionReference implements Function1 {
        public static final C04542 INSTANCE = new C04542();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(AxisAlignedBBImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04542() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((AxisAlignedBB) obj);
        }

        @NotNull
        public final IAxisAlignedBB invoke(@NotNull AxisAlignedBB p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return new AxisAlignedBBImpl(p1);
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u0001\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\b\u0006"}, m27d2 = {"<anonymous>", "Lnet/minecraft/entity/Entity;", "T", "Lnet/minecraft/world/World;", "p1", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.WorldImpl$getEntitiesInAABBexcluding$2 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/WorldImpl$getEntitiesInAABBexcluding$2.class */
    static final /* synthetic */ class C04562 extends FunctionReference implements Function1 {
        public static final C04562 INSTANCE = new C04562();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(EntityImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04562() {
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

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u0001\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\b\u0006"}, m27d2 = {"<anonymous>", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "T", "Lnet/minecraft/world/World;", "p1", "Lnet/minecraft/entity/Entity;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.WorldImpl$getEntitiesInAABBexcluding$3 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/WorldImpl$getEntitiesInAABBexcluding$3.class */
    static final /* synthetic */ class C04573 extends FunctionReference implements Function1 {
        public static final C04573 INSTANCE = new C04573();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(EntityImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04573() {
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

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u0001\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\b\u0006"}, m27d2 = {"<anonymous>", "Lnet/minecraft/entity/Entity;", "T", "Lnet/minecraft/world/World;", "p1", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.WorldImpl$getEntitiesWithinAABBExcludingEntity$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/WorldImpl$getEntitiesWithinAABBExcludingEntity$1.class */
    static final /* synthetic */ class C04581 extends FunctionReference implements Function1 {
        public static final C04581 INSTANCE = new C04581();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(EntityImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04581() {
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

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u0001\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\b\u0006"}, m27d2 = {"<anonymous>", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "T", "Lnet/minecraft/world/World;", "p1", "Lnet/minecraft/entity/Entity;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.WorldImpl$getEntitiesWithinAABBExcludingEntity$2 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/WorldImpl$getEntitiesWithinAABBExcludingEntity$2.class */
    static final /* synthetic */ class C04592 extends FunctionReference implements Function1 {
        public static final C04592 INSTANCE = new C04592();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(EntityImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04592() {
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

    @NotNull
    public final World getWrapped() {
        return this.wrapped;
    }

    public WorldImpl(@NotNull World wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IWorld
    public boolean isRemote() {
        return this.wrapped.field_72995_K;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IWorld
    @NotNull
    public IScoreboard getScoreboard() {
        Scoreboard scoreboardFunc_96441_U = this.wrapped.func_96441_U();
        Intrinsics.checkExpressionValueIsNotNull(scoreboardFunc_96441_U, "wrapped.scoreboard");
        return new ScoreboardImpl(scoreboardFunc_96441_U);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IWorld
    @NotNull
    public IWorldBorder getWorldBorder() {
        WorldBorder worldBorderFunc_175723_af = this.wrapped.func_175723_af();
        Intrinsics.checkExpressionValueIsNotNull(worldBorderFunc_175723_af, "wrapped.worldBorder");
        return new WorldBorderImpl(worldBorderFunc_175723_af);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IWorld
    @Nullable
    public IEntity getEntityByID(int i) {
        Entity entityFunc_73045_a = this.wrapped.func_73045_a(i);
        if (entityFunc_73045_a != null) {
            return new EntityImpl(entityFunc_73045_a);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IWorld
    @Nullable
    public IMovingObjectPosition rayTraceBlocks(@NotNull WVec3 start, @NotNull WVec3 end) {
        Intrinsics.checkParameterIsNotNull(start, "start");
        Intrinsics.checkParameterIsNotNull(end, "end");
        RayTraceResult rayTraceResultFunc_72933_a = this.wrapped.func_72933_a(new Vec3d(start.getXCoord(), start.getYCoord(), start.getZCoord()), new Vec3d(end.getXCoord(), end.getYCoord(), end.getZCoord()));
        if (rayTraceResultFunc_72933_a != null) {
            return new MovingObjectPositionImpl(rayTraceResultFunc_72933_a);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IWorld
    @Nullable
    public IMovingObjectPosition rayTraceBlocks(@NotNull WVec3 start, @NotNull WVec3 end, boolean z) {
        Intrinsics.checkParameterIsNotNull(start, "start");
        Intrinsics.checkParameterIsNotNull(end, "end");
        RayTraceResult rayTraceResultFunc_72901_a = this.wrapped.func_72901_a(new Vec3d(start.getXCoord(), start.getYCoord(), start.getZCoord()), new Vec3d(end.getXCoord(), end.getYCoord(), end.getZCoord()), z);
        if (rayTraceResultFunc_72901_a != null) {
            return new MovingObjectPositionImpl(rayTraceResultFunc_72901_a);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IWorld
    @Nullable
    public IMovingObjectPosition rayTraceBlocks(@NotNull WVec3 start, @NotNull WVec3 end, boolean z, boolean z2, boolean z3) {
        Intrinsics.checkParameterIsNotNull(start, "start");
        Intrinsics.checkParameterIsNotNull(end, "end");
        RayTraceResult rayTraceResultFunc_147447_a = this.wrapped.func_147447_a(new Vec3d(start.getXCoord(), start.getYCoord(), start.getZCoord()), new Vec3d(end.getXCoord(), end.getYCoord(), end.getZCoord()), z, z2, z3);
        if (rayTraceResultFunc_147447_a != null) {
            return new MovingObjectPositionImpl(rayTraceResultFunc_147447_a);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IWorld
    @NotNull
    public Collection getEntitiesInAABBexcluding(@Nullable IEntity iEntity, @NotNull IAxisAlignedBB boundingBox, @NotNull Function1 predicate) {
        Entity wrapped;
        Intrinsics.checkParameterIsNotNull(boundingBox, "boundingBox");
        Intrinsics.checkParameterIsNotNull(predicate, "predicate");
        World world = this.wrapped;
        if (iEntity == null) {
            wrapped = null;
        } else {
            if (iEntity == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.EntityImpl<*>");
            }
            world = world;
            wrapped = ((EntityImpl) iEntity).getWrapped();
        }
        return new WrappedCollection(world.func_175674_a(wrapped, ((AxisAlignedBBImpl) boundingBox).getWrapped(), new Predicate(predicate) { // from class: net.ccbluex.liquidbounce.injection.backend.WorldImpl.getEntitiesInAABBexcluding.1
            final Function1 $predicate;

            {
                this.$predicate = predicate;
            }

            public boolean apply(Object obj) {
                return apply((Entity) obj);
            }

            public final boolean apply(@Nullable Entity entity) {
                EntityImpl entityImpl;
                Function1 function1 = this.$predicate;
                if (entity != null) {
                    function1 = function1;
                    entityImpl = new EntityImpl(entity);
                } else {
                    entityImpl = null;
                }
                return ((Boolean) function1.invoke(entityImpl)).booleanValue();
            }
        }), C04562.INSTANCE, C04573.INSTANCE);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IWorld
    @NotNull
    public IIBlockState getBlockState(@NotNull WBlockPos blockPos) {
        Intrinsics.checkParameterIsNotNull(blockPos, "blockPos");
        IBlockState iBlockStateFunc_180495_p = this.wrapped.func_180495_p(new BlockPos(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        Intrinsics.checkExpressionValueIsNotNull(iBlockStateFunc_180495_p, "wrapped.getBlockState(blockPos.unwrap())");
        return new IBlockStateImpl(iBlockStateFunc_180495_p);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IWorld
    @NotNull
    public Collection getEntitiesWithinAABBExcludingEntity(@Nullable IEntity iEntity, @NotNull IAxisAlignedBB bb) {
        Entity wrapped;
        Intrinsics.checkParameterIsNotNull(bb, "bb");
        World world = this.wrapped;
        if (iEntity == null) {
            wrapped = null;
        } else {
            if (iEntity == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.EntityImpl<*>");
            }
            world = world;
            wrapped = ((EntityImpl) iEntity).getWrapped();
        }
        return new WrappedCollection(world.func_72839_b(wrapped, ((AxisAlignedBBImpl) bb).getWrapped()), C04581.INSTANCE, C04592.INSTANCE);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IWorld
    @NotNull
    public Collection getCollidingBoundingBoxes(@NotNull IEntity entity, @NotNull IAxisAlignedBB bb) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        Intrinsics.checkParameterIsNotNull(bb, "bb");
        return new WrappedCollection(this.wrapped.func_184144_a(((EntityImpl) entity).getWrapped(), ((AxisAlignedBBImpl) bb).getWrapped()), C04511.INSTANCE, C04522.INSTANCE);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IWorld
    public boolean checkBlockCollision(@NotNull IAxisAlignedBB aabb) {
        Intrinsics.checkParameterIsNotNull(aabb, "aabb");
        return this.wrapped.func_72829_c(((AxisAlignedBBImpl) aabb).getWrapped());
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IWorld
    @NotNull
    public Collection getCollisionBoxes(@NotNull IAxisAlignedBB bb) {
        Intrinsics.checkParameterIsNotNull(bb, "bb");
        return new WrappedCollection(this.wrapped.func_184144_a((Entity) null, ((AxisAlignedBBImpl) bb).getWrapped()), C04531.INSTANCE, C04542.INSTANCE);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.world.IWorld
    @NotNull
    public IChunk getChunkFromChunkCoords(int i, int i2) {
        Chunk chunkFunc_72964_e = this.wrapped.func_72964_e(i, i2);
        Intrinsics.checkExpressionValueIsNotNull(chunkFunc_72964_e, "wrapped.getChunkFromChunkCoords(x, z)");
        return new ChunkImpl(chunkFunc_72964_e);
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof WorldImpl) && Intrinsics.areEqual(((WorldImpl) obj).wrapped, this.wrapped);
    }
}
