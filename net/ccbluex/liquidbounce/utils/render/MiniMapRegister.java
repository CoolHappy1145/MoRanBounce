package net.ccbluex.liquidbounce.utils.render;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.block.state.IIBlockState;
import net.ccbluex.liquidbounce.api.minecraft.client.block.IBlock;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.util.WBlockPos;
import net.ccbluex.liquidbounce.api.minecraft.world.IChunk;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.MiniMapRegister;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdF\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0007\b\u00c6\u0002\u0018\ufffd\ufffd2\u00020\u0001:\u0002\u001a\u001bB\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u000f\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011J\u0006\u0010\u0013\u001a\u00020\u0011J\u0006\u0010\u0014\u001a\u00020\u0015J\u0016\u0010\u0016\u001a\u00020\u00152\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0011J\u000e\u0010\u0017\u001a\u00020\u00152\u0006\u0010\u0018\u001a\u00020\u000eJ\u0006\u0010\u0019\u001a\u00020\u0015R*\u0010\u0003\u001a\u001e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0004j\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0006`\u0007X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001e\u0010\n\u001a\u0012\u0012\u0004\u0012\u00020\u00050\u000bj\b\u0012\u0004\u0012\u00020\u0005`\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001e\u0010\r\u001a\u0012\u0012\u0004\u0012\u00020\u000e0\u000bj\b\u0012\u0004\u0012\u00020\u000e`\fX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001c"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/render/MiniMapRegister;", "Lnet/ccbluex/liquidbounce/utils/MinecraftInstance;", "()V", "chunkTextureMap", "Ljava/util/HashMap;", "Lnet/ccbluex/liquidbounce/utils/render/MiniMapRegister$ChunkLocation;", "Lnet/ccbluex/liquidbounce/utils/render/MiniMapRegister$MiniMapTexture;", "Lkotlin/collections/HashMap;", "deleteAllChunks", "Ljava/util/concurrent/atomic/AtomicBoolean;", "queuedChunkDeletions", "Ljava/util/HashSet;", "Lkotlin/collections/HashSet;", "queuedChunkUpdates", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IChunk;", "getChunkTextureAt", "x", "", "z", "getLoadedChunkCount", "unloadAllChunks", "", "unloadChunk", "updateChunk", "chunk", "updateChunks", "ChunkLocation", "MiniMapTexture", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/MiniMapRegister.class */
public final class MiniMapRegister extends MinecraftInstance {
    public static final MiniMapRegister INSTANCE = new MiniMapRegister();
    private static final HashMap chunkTextureMap = new HashMap();
    private static final HashSet queuedChunkUpdates = new HashSet(256);
    private static final HashSet queuedChunkDeletions = new HashSet(256);
    private static final AtomicBoolean deleteAllChunks = new AtomicBoolean(false);

    private MiniMapRegister() {
    }

    public final void updateChunk(@NotNull IChunk chunk) {
        Intrinsics.checkParameterIsNotNull(chunk, "chunk");
        synchronized (queuedChunkUpdates) {
            queuedChunkUpdates.add(chunk);
        }
    }

    @Nullable
    public final MiniMapTexture getChunkTextureAt(int i, int i2) {
        return (MiniMapTexture) chunkTextureMap.get(new ChunkLocation(i, i2));
    }

    /* JADX WARN: Removed duplicated region for block: B:42:0x0100 A[Catch: all -> 0x0147, LOOP:1: B:40:0x00f6->B:42:0x0100, LOOP_END, TryCatch #1 {, blocks: (B:4:0x0006, B:6:0x000f, B:7:0x0016, B:8:0x0017, B:9:0x0023, B:16:0x002f, B:17:0x004b, B:19:0x0055, B:20:0x0075, B:39:0x00e5, B:40:0x00f6, B:42:0x0100, B:43:0x0138, B:13:0x002b, B:15:0x002e, B:21:0x0085, B:22:0x008c, B:23:0x008d, B:24:0x009e, B:26:0x00a8, B:28:0x00c3, B:31:0x00cd, B:32:0x00d9, B:36:0x00e1, B:38:0x00e4), top: B:54:0x0006, inners: #0, #2 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void updateChunks() {
        synchronized (queuedChunkUpdates) {
            if (deleteAllChunks.get()) {
                synchronized (queuedChunkDeletions) {
                    queuedChunkDeletions.clear();
                    Unit unit = Unit.INSTANCE;
                }
                queuedChunkUpdates.clear();
                Iterator it = chunkTextureMap.entrySet().iterator();
                while (it.hasNext()) {
                    ((MiniMapTexture) ((Map.Entry) it.next()).getValue()).delete$LiquidSense();
                }
                chunkTextureMap.clear();
                deleteAllChunks.set(false);
                for (IChunk iChunk : queuedChunkUpdates) {
                    ((MiniMapTexture) chunkTextureMap.computeIfAbsent(new ChunkLocation(iChunk.getX(), iChunk.getZ()), new Function() { // from class: net.ccbluex.liquidbounce.utils.render.MiniMapRegister$updateChunks$1$4$1
                        @Override // java.util.function.Function
                        public Object apply(Object obj) {
                            return apply((MiniMapRegister.ChunkLocation) obj);
                        }

                        @NotNull
                        public final MiniMapRegister.MiniMapTexture apply(@NotNull MiniMapRegister.ChunkLocation it2) {
                            Intrinsics.checkParameterIsNotNull(it2, "it");
                            return new MiniMapRegister.MiniMapTexture();
                        }
                    })).updateChunkData(iChunk);
                }
                queuedChunkUpdates.clear();
                Unit unit2 = Unit.INSTANCE;
            } else {
                synchronized (queuedChunkDeletions) {
                    Iterator it2 = queuedChunkDeletions.iterator();
                    while (it2.hasNext()) {
                        MiniMapTexture miniMapTexture = (MiniMapTexture) chunkTextureMap.remove((ChunkLocation) it2.next());
                        if (miniMapTexture != null) {
                            miniMapTexture.delete$LiquidSense();
                        }
                    }
                    queuedChunkDeletions.clear();
                    Unit unit3 = Unit.INSTANCE;
                }
                while (r0.hasNext()) {
                }
                queuedChunkUpdates.clear();
                Unit unit22 = Unit.INSTANCE;
            }
        }
    }

    public final int getLoadedChunkCount() {
        return chunkTextureMap.size();
    }

    public final void unloadChunk(int i, int i2) {
        synchronized (queuedChunkDeletions) {
            queuedChunkDeletions.add(new ChunkLocation(i, i2));
        }
    }

    public final void unloadAllChunks() {
        deleteAllChunks.set(true);
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd*\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\r\u0010\r\u001a\u00020\u000eH\ufffd\ufffd\u00a2\u0006\u0002\b\u000fJ\b\u0010\u0010\u001a\u00020\u000eH\u0004J\u000e\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0012\u001a\u00020\u0013R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u0011\u0010\t\u001a\u00020\n\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u0014"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/render/MiniMapRegister$MiniMapTexture;", "", "()V", "deleted", "", "getDeleted", "()Z", "setDeleted", "(Z)V", "texture", "Lnet/minecraft/client/renderer/texture/DynamicTexture;", "getTexture", "()Lnet/minecraft/client/renderer/texture/DynamicTexture;", "delete", "", "delete$LiquidSense", "finalize", "updateChunkData", "chunk", "Lnet/ccbluex/liquidbounce/api/minecraft/world/IChunk;", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/MiniMapRegister$MiniMapTexture.class */
    public static final class MiniMapTexture {

        @NotNull
        private final DynamicTexture texture = new DynamicTexture(16, 16);
        private boolean deleted;

        @NotNull
        public final DynamicTexture getTexture() {
            return this.texture;
        }

        public final boolean getDeleted() {
            return this.deleted;
        }

        public final void setDeleted(boolean z) {
            this.deleted = z;
        }

        public final void updateChunkData(@NotNull IChunk chunk) {
            Intrinsics.checkParameterIsNotNull(chunk, "chunk");
            int[] iArrFunc_110565_c = this.texture.func_110565_c();
            for (int i = 0; i <= 15; i++) {
                for (int i2 = 0; i2 <= 15; i2++) {
                    WBlockPos wBlockPos = new WBlockPos(i, chunk.getHeightValue(i, i2) - 1, i2);
                    IIBlockState blockState = chunk.getBlockState(wBlockPos);
                    int length = iArrFunc_110565_c.length - (((i2 * 16) + i) + 1);
                    IBlock block = blockState.getBlock();
                    IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
                    if (theWorld == null) {
                        Intrinsics.throwNpe();
                    }
                    iArrFunc_110565_c[length] = block.getMapColor(blockState, theWorld, wBlockPos) | (-16777216);
                }
            }
            this.texture.func_110564_a();
        }

        public final void delete$LiquidSense() {
            if (!this.deleted) {
                this.texture.func_147631_c();
                this.deleted = true;
            }
        }

        protected final void finalize() {
            if (!this.deleted) {
                this.texture.func_147631_c();
            }
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd \n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\ufffd\ufffd\b\u0086\b\u0018\ufffd\ufffd2\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\n\u001a\u00020\u0003H\u00c6\u0003J\u001d\u0010\u000b\u001a\u00020\ufffd\ufffd2\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u000f\u001a\u00020\u0003H\u00d6\u0001J\t\u0010\u0010\u001a\u00020\u0011H\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\u0007\u00a8\u0006\u0012"}, m27d2 = {"Lnet/ccbluex/liquidbounce/utils/render/MiniMapRegister$ChunkLocation;", "", "x", "", "z", "(II)V", "getX", "()I", "getZ", "component1", "component2", "copy", "equals", "", "other", "hashCode", "toString", "", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/render/MiniMapRegister$ChunkLocation.class */
    public static final class ChunkLocation {

        /* renamed from: x */
        private final int f176x;

        /* renamed from: z */
        private final int f177z;

        public final int component1() {
            return this.f176x;
        }

        public final int component2() {
            return this.f177z;
        }

        @NotNull
        public final ChunkLocation copy(int i, int i2) {
            return new ChunkLocation(i, i2);
        }

        public static ChunkLocation copy$default(ChunkLocation chunkLocation, int i, int i2, int i3, Object obj) {
            if ((i3 & 1) != 0) {
                i = chunkLocation.f176x;
            }
            if ((i3 & 2) != 0) {
                i2 = chunkLocation.f177z;
            }
            return chunkLocation.copy(i, i2);
        }

        @NotNull
        public String toString() {
            return "ChunkLocation(x=" + this.f176x + ", z=" + this.f177z + ")";
        }

        public int hashCode() {
            return (Integer.hashCode(this.f176x) * 31) + Integer.hashCode(this.f177z);
        }

        public boolean equals(@Nullable Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ChunkLocation)) {
                return false;
            }
            ChunkLocation chunkLocation = (ChunkLocation) obj;
            return this.f176x == chunkLocation.f176x && this.f177z == chunkLocation.f177z;
        }

        public final int getX() {
            return this.f176x;
        }

        public final int getZ() {
            return this.f177z;
        }

        public ChunkLocation(int i, int i2) {
            this.f176x = i;
            this.f177z = i2;
        }
    }
}
