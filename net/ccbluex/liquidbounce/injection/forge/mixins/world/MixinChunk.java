package net.ccbluex.liquidbounce.injection.forge.mixins.world;

import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.ProphuntESP;
import net.ccbluex.liquidbounce.injection.backend.ChunkImplKt;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.ccbluex.liquidbounce.utils.render.MiniMapRegister;
import net.minecraft.block.state.IBlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Chunk.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/world/MixinChunk.class */
public class MixinChunk {

    @Shadow
    @Final
    public int field_76635_g;

    @Shadow
    @Final
    public int field_76647_h;

    @Inject(method = {"setBlockState"}, m59at = {@InterfaceC0563At("HEAD")})
    private void setProphuntBlock(BlockPos blockPos, IBlockState iBlockState, CallbackInfoReturnable callbackInfoReturnable) {
        MiniMapRegister.INSTANCE.updateChunk(ChunkImplKt.wrap((Chunk) this));
        ProphuntESP prophuntESP = (ProphuntESP) LiquidBounce.moduleManager.getModule(ProphuntESP.class);
        if (((ProphuntESP) Objects.requireNonNull(prophuntESP)).getState()) {
            synchronized (prophuntESP.getBlocks()) {
                prophuntESP.getBlocks().put(BackendExtentionsKt.wrap(blockPos), Long.valueOf(System.currentTimeMillis()));
            }
        }
    }

    @Inject(method = {"onUnload"}, m59at = {@InterfaceC0563At("HEAD")})
    private void injectFillChunk(CallbackInfo callbackInfo) {
        MiniMapRegister.INSTANCE.unloadChunk(this.field_76635_g, this.field_76647_h);
    }

    @Inject(method = {"read"}, m59at = {@InterfaceC0563At("RETURN")})
    private void injectFillChunk(PacketBuffer packetBuffer, int i, boolean z, CallbackInfo callbackInfo) {
        MiniMapRegister.INSTANCE.updateChunk(ChunkImplKt.wrap((Chunk) this));
    }
}
