package net.vitox;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.vitox.particle.util.RenderUtils;

@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/vitox/ParticleGenerator.class */
public class ParticleGenerator {
    private final List particles = new ArrayList();
    private final int amount;
    private int prevWidth;
    private int prevHeight;

    public ParticleGenerator(int i) {
        this.amount = i;
    }

    public void draw(int i, int i2) {
        if (this.particles.isEmpty() || this.prevWidth != Minecraft.func_71410_x().field_71443_c || this.prevHeight != Minecraft.func_71410_x().field_71440_d) {
            this.particles.clear();
            create();
        }
        this.prevWidth = Minecraft.func_71410_x().field_71443_c;
        this.prevHeight = Minecraft.func_71410_x().field_71440_d;
        for (Particle particle : this.particles) {
            particle.fall();
            particle.interpolation();
            if (((float) i) >= particle.f182x - 50.0f && ((float) i2) >= particle.f183y - 50.0f && ((float) i) <= particle.f182x + 50.0f && ((float) i2) <= particle.f183y + 50.0f) {
                int i3 = 50;
                this.particles.stream().filter((v2) -> {
                    return lambda$draw$0(r1, r2, v2);
                }).forEach((v1) -> {
                    lambda$draw$1(r1, v1);
                });
            }
            RenderUtils.drawCircle(particle.getX(), particle.getY(), particle.size, -1);
        }
    }

    private static boolean lambda$draw$0(Particle particle, int i, Particle particle2) {
        return particle2.getX() > particle.getX() && particle2.getX() - particle.getX() < ((float) i) && particle.getX() - particle2.getX() < ((float) i) && ((particle2.getY() > particle.getY() && particle2.getY() - particle.getY() < ((float) i)) || (particle.getY() > particle2.getY() && particle.getY() - particle2.getY() < ((float) i)));
    }

    private static void lambda$draw$1(Particle particle, Particle particle2) {
        particle.connect(particle2.getX(), particle2.getY());
    }

    private void create() {
        Random random = new Random();
        for (int i = 0; i < this.amount; i++) {
            this.particles.add(new Particle(random.nextInt(Minecraft.func_71410_x().field_71443_c), random.nextInt(Minecraft.func_71410_x().field_71440_d)));
        }
    }
}
