package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.ccbluex.liquidbounce.api.minecraft.client.particle.IParticleManager;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.util.EnumParticleTypes;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd4\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016JH\u0010\r\u001a\u00020\b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\u0013\u001a\u00020\u00112\u0006\u0010\u0014\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00112\u0006\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u000fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0018"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ParticleManagerImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/particle/IParticleManager;", "wrapped", "Lnet/minecraft/client/particle/ParticleManager;", "(Lnet/minecraft/client/particle/ParticleManager;)V", "getWrapped", "()Lnet/minecraft/client/particle/ParticleManager;", "emitParticleAtEntity", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "buffer", "Lnet/minecraft/util/EnumParticleTypes;", "spawnEffectParticle", "particleID", "", "posX", "", "posY", "posZ", "motionX", "motionY", "motionZ", "StateId", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ParticleManagerImpl.class */
public final class ParticleManagerImpl implements IParticleManager {

    @NotNull
    private final ParticleManager wrapped;

    @NotNull
    public final ParticleManager getWrapped() {
        return this.wrapped;
    }

    public ParticleManagerImpl(@NotNull ParticleManager wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.particle.IParticleManager
    public void emitParticleAtEntity(@NotNull IEntity entity, @NotNull EnumParticleTypes buffer) {
        Intrinsics.checkParameterIsNotNull(entity, "entity");
        Intrinsics.checkParameterIsNotNull(buffer, "buffer");
        this.wrapped.func_178926_a(((EntityImpl) entity).getWrapped(), buffer);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.client.particle.IParticleManager
    public void spawnEffectParticle(int i, double d, double d2, double d3, double d4, double d5, double d6, int i2) {
        this.wrapped.func_178927_a(i, d, d2, d3, d4, d5, d6, new int[]{i2});
    }
}
