package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Queue;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEmitter;
import net.minecraft.client.particle.ParticleManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ParticleManager.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinEffectRenderer.class */
public abstract class MixinEffectRenderer {

    @Shadow
    @Final
    private final Queue field_178933_d = Queues.newArrayDeque();

    @Shadow
    @Final
    private Queue field_187241_h;

    @Shadow
    @Final
    private ArrayDeque[][] field_78876_b;

    @Shadow
    protected abstract void func_178922_a(int i);

    @Overwrite
    public void func_78868_a() {
        for (int i = 0; i < 4; i++) {
            try {
                func_178922_a(i);
            } catch (ConcurrentModificationException unused) {
                return;
            }
        }
        if (!this.field_178933_d.isEmpty()) {
            ArrayList arrayListNewArrayList = Lists.newArrayList();
            for (ParticleEmitter particleEmitter : this.field_178933_d) {
                particleEmitter.func_189213_a();
                if (!particleEmitter.func_187113_k()) {
                    arrayListNewArrayList.add(particleEmitter);
                }
            }
            this.field_178933_d.removeAll(arrayListNewArrayList);
        }
        if (!this.field_187241_h.isEmpty()) {
            Particle particle = (Particle) this.field_187241_h.poll();
            while (particle != null) {
                int iFunc_70537_b = particle.func_70537_b();
                boolean z = !particle.func_187111_c();
                if (this.field_78876_b[iFunc_70537_b][z ? 1 : 0].size() >= 16384) {
                    this.field_78876_b[iFunc_70537_b][z ? 1 : 0].removeFirst();
                }
                this.field_78876_b[iFunc_70537_b][z ? 1 : 0].add(particle);
                particle = (Particle) this.field_187241_h.poll();
            }
        }
    }
}
