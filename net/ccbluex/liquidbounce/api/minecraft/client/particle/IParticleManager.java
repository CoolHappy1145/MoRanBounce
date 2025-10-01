package net.ccbluex.liquidbounce.api.minecraft.client.particle;

import kotlin.Metadata;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntity;
import net.minecraft.util.EnumParticleTypes;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0007\bf\u0018\ufffd\ufffd2\u00020\u0001J\u0018\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&JH\u0010\b\u001a\u00020\u00032\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\f2\u0006\u0010\u000f\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\f2\u0006\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020\nH&\u00a8\u0006\u0013"}, m27d2 = {"Lnet/ccbluex/liquidbounce/api/minecraft/client/particle/IParticleManager;", "", "emitParticleAtEntity", "", "entity", "Lnet/ccbluex/liquidbounce/api/minecraft/client/entity/IEntity;", "buffer", "Lnet/minecraft/util/EnumParticleTypes;", "spawnEffectParticle", "particleID", "", "posX", "", "posY", "posZ", "motionX", "motionY", "motionZ", "StateId", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/api/minecraft/client/particle/IParticleManager.class */
public interface IParticleManager {
    void emitParticleAtEntity(@NotNull IEntity iEntity, @NotNull EnumParticleTypes enumParticleTypes);

    void spawnEffectParticle(int i, double d, double d2, double d3, double d4, double d5, double d6, int i2);
}
