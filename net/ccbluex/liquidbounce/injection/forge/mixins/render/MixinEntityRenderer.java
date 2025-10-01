package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import com.google.common.base.Predicates;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.Render3DEvent;
import net.ccbluex.liquidbounce.features.module.modules.player.Reach;
import net.ccbluex.liquidbounce.features.module.modules.render.CameraClip;
import net.ccbluex.liquidbounce.features.module.modules.render.NoHurtCam;
import net.ccbluex.liquidbounce.features.module.modules.render.Tracers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({EntityRenderer.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinEntityRenderer.class */
public abstract class MixinEntityRenderer {

    @Shadow
    private Entity field_78528_u;

    @Shadow
    @Final
    private Minecraft field_78531_r;

    @Shadow
    @Final
    private float field_78490_B;

    @Shadow
    private boolean field_78500_U;

    @Shadow
    private float field_78491_C;

    @Shadow
    public abstract void func_175069_a(ResourceLocation resourceLocation);

    @Shadow
    public abstract void func_78479_a(float f, int i);

    @Inject(method = {"renderWorldPass"}, m59at = {@InterfaceC0563At(value = "FIELD", target = "Lnet/minecraft/client/renderer/EntityRenderer;renderHand:Z", shift = InterfaceC0563At.Shift.BEFORE)})
    private void renderWorldPass(int i, float f, long j, CallbackInfo callbackInfo) {
        LiquidBounce.eventManager.callEvent(new Render3DEvent(f));
    }

    @Inject(method = {"hurtCameraEffect"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void injectHurtCameraEffect(CallbackInfo callbackInfo) {
        if (LiquidBounce.moduleManager.getModule(NoHurtCam.class).getState()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"orientCamera"}, m59at = {@InterfaceC0563At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;distanceTo(Lnet/minecraft/util/math/Vec3d;)D")}, cancellable = true)
    private void cameraClip(float f, CallbackInfo callbackInfo) {
        if (LiquidBounce.moduleManager.getModule(CameraClip.class).getState()) {
            callbackInfo.cancel();
            EntityLivingBase entityLivingBaseFunc_175606_aa = this.field_78531_r.func_175606_aa();
            float fFunc_70047_e = entityLivingBaseFunc_175606_aa.func_70047_e();
            if ((entityLivingBaseFunc_175606_aa instanceof EntityLivingBase) && entityLivingBaseFunc_175606_aa.func_70608_bn()) {
                fFunc_70047_e = (float) (fFunc_70047_e + 1.0d);
                GlStateManager.func_179109_b(0.0f, 0.3f, 0.0f);
                if (!this.field_78531_r.field_71474_y.field_74325_U) {
                    BlockPos blockPos = new BlockPos(entityLivingBaseFunc_175606_aa);
                    ForgeHooksClient.orientBedCamera(this.field_78531_r.field_71441_e, blockPos, this.field_78531_r.field_71441_e.func_180495_p(blockPos), entityLivingBaseFunc_175606_aa);
                    GlStateManager.func_179114_b(((Entity) entityLivingBaseFunc_175606_aa).field_70126_B + ((((Entity) entityLivingBaseFunc_175606_aa).field_70177_z - ((Entity) entityLivingBaseFunc_175606_aa).field_70126_B) * f) + 180.0f, 0.0f, -1.0f, 0.0f);
                    GlStateManager.func_179114_b(((Entity) entityLivingBaseFunc_175606_aa).field_70127_C + ((((Entity) entityLivingBaseFunc_175606_aa).field_70125_A - ((Entity) entityLivingBaseFunc_175606_aa).field_70127_C) * f), -1.0f, 0.0f, 0.0f);
                }
            } else if (this.field_78531_r.field_71474_y.field_74320_O > 0) {
                double d = this.field_78491_C + ((this.field_78490_B - this.field_78491_C) * f);
                if (this.field_78531_r.field_71474_y.field_74325_U) {
                    GlStateManager.func_179109_b(0.0f, 0.0f, (float) (-d));
                } else {
                    float f2 = ((Entity) entityLivingBaseFunc_175606_aa).field_70177_z;
                    float f3 = ((Entity) entityLivingBaseFunc_175606_aa).field_70125_A;
                    if (this.field_78531_r.field_71474_y.field_74320_O == 2) {
                        f3 += 180.0f;
                    }
                    if (this.field_78531_r.field_71474_y.field_74320_O == 2) {
                        GlStateManager.func_179114_b(180.0f, 0.0f, 1.0f, 0.0f);
                    }
                    GlStateManager.func_179114_b(((Entity) entityLivingBaseFunc_175606_aa).field_70125_A - f3, 1.0f, 0.0f, 0.0f);
                    GlStateManager.func_179114_b(((Entity) entityLivingBaseFunc_175606_aa).field_70177_z - f2, 0.0f, 1.0f, 0.0f);
                    GlStateManager.func_179109_b(0.0f, 0.0f, (float) (-d));
                    GlStateManager.func_179114_b(f2 - ((Entity) entityLivingBaseFunc_175606_aa).field_70177_z, 0.0f, 1.0f, 0.0f);
                    GlStateManager.func_179114_b(f3 - ((Entity) entityLivingBaseFunc_175606_aa).field_70125_A, 1.0f, 0.0f, 0.0f);
                }
            } else {
                GlStateManager.func_179109_b(0.0f, 0.0f, -0.1f);
            }
            if (!this.field_78531_r.field_71474_y.field_74325_U) {
                float f4 = ((Entity) entityLivingBaseFunc_175606_aa).field_70126_B + ((((Entity) entityLivingBaseFunc_175606_aa).field_70177_z - ((Entity) entityLivingBaseFunc_175606_aa).field_70126_B) * f) + 180.0f;
                float f5 = ((Entity) entityLivingBaseFunc_175606_aa).field_70127_C + ((((Entity) entityLivingBaseFunc_175606_aa).field_70125_A - ((Entity) entityLivingBaseFunc_175606_aa).field_70127_C) * f);
                if (entityLivingBaseFunc_175606_aa instanceof EntityAnimal) {
                    EntityAnimal entityAnimal = (EntityAnimal) entityLivingBaseFunc_175606_aa;
                    f4 = entityAnimal.field_70758_at + ((entityAnimal.field_70759_as - entityAnimal.field_70758_at) * f) + 180.0f;
                }
                EntityViewRenderEvent.CameraSetup cameraSetup = new EntityViewRenderEvent.CameraSetup((EntityRenderer) this, entityLivingBaseFunc_175606_aa, ActiveRenderInfo.func_186703_a(this.field_78531_r.field_71441_e, entityLivingBaseFunc_175606_aa, f), f, f4, f5, 0.0f);
                MinecraftForge.EVENT_BUS.post(cameraSetup);
                GlStateManager.func_179114_b(cameraSetup.getRoll(), 0.0f, 0.0f, 1.0f);
                GlStateManager.func_179114_b(cameraSetup.getPitch(), 1.0f, 0.0f, 0.0f);
                GlStateManager.func_179114_b(cameraSetup.getYaw(), 0.0f, 1.0f, 0.0f);
            }
            GlStateManager.func_179109_b(0.0f, -fFunc_70047_e, 0.0f);
            this.field_78500_U = this.field_78531_r.field_71438_f.func_72721_a(((Entity) entityLivingBaseFunc_175606_aa).field_70169_q + ((((Entity) entityLivingBaseFunc_175606_aa).field_70165_t - ((Entity) entityLivingBaseFunc_175606_aa).field_70169_q) * f), ((Entity) entityLivingBaseFunc_175606_aa).field_70167_r + ((((Entity) entityLivingBaseFunc_175606_aa).field_70163_u - ((Entity) entityLivingBaseFunc_175606_aa).field_70167_r) * f) + fFunc_70047_e, ((Entity) entityLivingBaseFunc_175606_aa).field_70166_s + ((((Entity) entityLivingBaseFunc_175606_aa).field_70161_v - ((Entity) entityLivingBaseFunc_175606_aa).field_70166_s) * f), f);
        }
    }

    @Inject(method = {"setupCameraTransform"}, m59at = {@InterfaceC0563At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;applyBobbing(F)V", shift = InterfaceC0563At.Shift.BEFORE)})
    private void setupCameraViewBobbingBefore(CallbackInfo callbackInfo) {
        if (LiquidBounce.moduleManager.getModule(Tracers.class).getState()) {
            GL11.glPushMatrix();
        }
    }

    @Inject(method = {"setupCameraTransform"}, m59at = {@InterfaceC0563At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/EntityRenderer;applyBobbing(F)V", shift = InterfaceC0563At.Shift.AFTER)})
    private void setupCameraViewBobbingAfter(CallbackInfo callbackInfo) {
        if (LiquidBounce.moduleManager.getModule(Tracers.class).getState()) {
            GL11.glPopMatrix();
        }
    }

    @Overwrite
    public void func_78473_a(float f) {
        Entity entityFunc_175606_aa = this.field_78531_r.func_175606_aa();
        if (entityFunc_175606_aa != null && this.field_78531_r.field_71441_e != null) {
            this.field_78531_r.field_71424_I.func_76320_a("pick");
            this.field_78531_r.field_147125_j = null;
            Reach reach = (Reach) LiquidBounce.moduleManager.getModule(Reach.class);
            double maxRange = reach.getState() ? reach.getMaxRange() : this.field_78531_r.field_71442_b.func_78757_d();
            this.field_78531_r.field_71476_x = entityFunc_175606_aa.func_174822_a(reach.getState() ? ((Float) reach.getBuildReachValue().get()).floatValue() : maxRange, f);
            Vec3d vec3dFunc_174824_e = entityFunc_175606_aa.func_174824_e(f);
            boolean z = false;
            double dFloatValue = maxRange;
            if (this.field_78531_r.field_71442_b.func_78749_i()) {
                dFloatValue = 6.0d;
                maxRange = 6.0d;
            } else if (maxRange > 3.0d) {
                z = true;
            }
            if (this.field_78531_r.field_71476_x != null) {
                dFloatValue = this.field_78531_r.field_71476_x.field_72307_f.func_72438_d(vec3dFunc_174824_e);
            }
            if (reach.getState()) {
                dFloatValue = ((Float) reach.getCombatReachValue().get()).floatValue();
                RayTraceResult rayTraceResultFunc_174822_a = entityFunc_175606_aa.func_174822_a(dFloatValue, f);
                if (rayTraceResultFunc_174822_a != null) {
                    dFloatValue = rayTraceResultFunc_174822_a.field_72307_f.func_72438_d(vec3dFunc_174824_e);
                }
            }
            Vec3d vec3dFunc_70676_i = entityFunc_175606_aa.func_70676_i(1.0f);
            Vec3d vec3dFunc_72441_c = vec3dFunc_174824_e.func_72441_c(vec3dFunc_70676_i.field_72450_a * maxRange, vec3dFunc_70676_i.field_72448_b * maxRange, vec3dFunc_70676_i.field_72449_c * maxRange);
            this.field_78528_u = null;
            Vec3d vec3d = null;
            double d = dFloatValue;
            for (Entity entity : this.field_78531_r.field_71441_e.func_175674_a(entityFunc_175606_aa, entityFunc_175606_aa.func_174813_aQ().func_72321_a(vec3dFunc_70676_i.field_72450_a * maxRange, vec3dFunc_70676_i.field_72448_b * maxRange, vec3dFunc_70676_i.field_72449_c * maxRange).func_72314_b(1.0d, 1.0d, 1.0d), Predicates.and(EntitySelectors.field_180132_d, MixinEntityRenderer::lambda$getMouseOver$0))) {
                AxisAlignedBB axisAlignedBBFunc_186662_g = entity.func_174813_aQ().func_186662_g(entity.func_70111_Y());
                RayTraceResult rayTraceResultFunc_72327_a = axisAlignedBBFunc_186662_g.func_72327_a(vec3dFunc_174824_e, vec3dFunc_72441_c);
                if (axisAlignedBBFunc_186662_g.func_72318_a(vec3dFunc_174824_e)) {
                    if (d >= 0.0d) {
                        this.field_78528_u = entity;
                        vec3d = rayTraceResultFunc_72327_a == null ? vec3dFunc_174824_e : rayTraceResultFunc_72327_a.field_72307_f;
                        d = 0.0d;
                    }
                } else if (rayTraceResultFunc_72327_a != null) {
                    double dFunc_72438_d = vec3dFunc_174824_e.func_72438_d(rayTraceResultFunc_72327_a.field_72307_f);
                    if (dFunc_72438_d < d || d == 0.0d) {
                        if (entity.func_184208_bv() != entityFunc_175606_aa.func_184208_bv() || entity.canRiderInteract()) {
                            this.field_78528_u = entity;
                            vec3d = rayTraceResultFunc_72327_a.field_72307_f;
                            d = dFunc_72438_d;
                        } else if (d == 0.0d) {
                            this.field_78528_u = entity;
                            vec3d = rayTraceResultFunc_72327_a.field_72307_f;
                        }
                    }
                }
            }
            if (this.field_78528_u != null && z) {
                if (vec3dFunc_174824_e.func_72438_d(vec3d) > (reach.getState() ? ((Float) reach.getCombatReachValue().get()).floatValue() : 3.0d)) {
                    this.field_78528_u = null;
                    this.field_78531_r.field_71476_x = new RayTraceResult(RayTraceResult.Type.MISS, vec3d, (EnumFacing) null, new BlockPos(vec3d));
                }
            }
            if (this.field_78528_u != null && (d < dFloatValue || this.field_78531_r.field_71476_x == null)) {
                this.field_78531_r.field_71476_x = new RayTraceResult(this.field_78528_u, vec3d);
                if ((this.field_78528_u instanceof EntityLivingBase) || (this.field_78528_u instanceof EntityItemFrame)) {
                    this.field_78531_r.field_147125_j = this.field_78528_u;
                }
            }
            this.field_78531_r.field_71424_I.func_76319_b();
        }
    }

    private static boolean lambda$getMouseOver$0(Entity entity) {
        return entity != null && entity.func_70067_L();
    }
}
