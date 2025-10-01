package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.combat.HitBox;
import net.ccbluex.liquidbounce.features.module.modules.exploit.NoPitchLimit;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Entity.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/entity/MixinEntity.class */
public abstract class MixinEntity {

    @Shadow
    public double field_70165_t;

    @Shadow
    public double field_70163_u;

    @Shadow
    protected EntityDataManager field_70180_af;

    @Shadow
    public double field_70161_v;

    @Shadow
    public float field_70125_A;

    @Shadow
    public float field_70177_z;

    @Shadow
    public Entity field_184239_as;

    @Shadow
    public double field_70159_w;

    @Shadow
    public double field_70181_x;

    @Shadow
    public double field_70179_y;

    @Shadow
    public boolean field_70122_E;

    @Shadow
    public boolean field_70160_al;

    @Shadow
    public boolean field_70145_X;

    @Shadow
    public World field_70170_p;

    @Shadow
    public boolean field_70134_J;

    @Shadow
    public float field_70138_W;

    @Shadow
    public boolean field_70123_F;

    @Shadow
    public boolean field_70124_G;

    @Shadow
    public boolean field_70132_H;

    @Shadow
    public float field_70140_Q;

    @Shadow
    public float field_82151_R;

    @Shadow
    public int field_71088_bW;

    @Shadow
    public float field_70130_N;

    @Shadow
    public int field_70150_b;

    @Shadow
    public int field_190534_ay;

    @Shadow
    public float field_70127_C;

    @Shadow
    public float field_70126_B;

    @Shadow
    public long field_191506_aJ;

    @Shadow
    @Final
    public double[] field_191505_aI;

    @Shadow
    public float field_191959_ay;

    @Shadow
    protected Random field_70146_Z;

    @Shadow
    protected boolean field_71087_bX;

    @Shadow
    private boolean field_83001_bt;

    @Shadow
    public abstract boolean func_70051_ag();

    @Shadow
    public abstract AxisAlignedBB func_174813_aQ();

    @Shadow
    public abstract void func_174826_a(AxisAlignedBB axisAlignedBB);

    @Shadow
    public abstract boolean func_70090_H();

    @Shadow
    protected abstract int func_190531_bD();

    @Shadow
    public abstract boolean func_184218_aH();

    @Shadow
    protected abstract void func_70081_e(int i);

    @Shadow
    public abstract boolean func_70026_G();

    @Shadow
    public abstract void func_85029_a(CrashReportCategory crashReportCategory);

    @Shadow
    protected abstract void func_145775_I();

    @Shadow
    protected abstract void func_180429_a(BlockPos blockPos, Block block);

    @Shadow
    protected abstract Vec3d func_174806_f(float f, float f2);

    @Shadow
    public abstract UUID func_110124_au();

    @Shadow
    public abstract boolean func_70093_af();

    @Shadow
    public abstract boolean func_70055_a(Material material);

    @Shadow
    @Nullable
    public abstract Entity func_184187_bx();

    @Shadow
    public abstract void func_174829_m();

    @Shadow
    protected abstract void func_184231_a(double d, boolean z, IBlockState iBlockState, BlockPos blockPos);

    @Shadow
    protected abstract boolean func_70041_e_();

    @Shadow
    public abstract boolean func_184207_aI();

    @Shadow
    @Nullable
    public abstract Entity func_184179_bs();

    @Shadow
    public abstract void func_184185_a(SoundEvent soundEvent, float f, float f2);

    @Shadow
    protected abstract SoundEvent func_184184_Z();

    @Shadow
    protected abstract boolean func_191957_ae();

    @Shadow
    protected abstract float func_191954_d(float f);

    @Shadow
    public abstract boolean func_70027_ad();

    @Shadow
    public abstract void func_70015_d(int i);

    public int getNextStepDistance() {
        return this.field_70150_b;
    }

    public void setNextStepDistance(int i) {
        this.field_70150_b = i;
    }

    public int getFire() {
        return this.field_190534_ay;
    }

    @Inject(method = {"getCollisionBorderSize"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void getCollisionBorderSize(CallbackInfoReturnable callbackInfoReturnable) {
        HitBox hitBox = (HitBox) LiquidBounce.moduleManager.getModule(HitBox.class);
        if (((HitBox) Objects.requireNonNull(hitBox)).getState()) {
            callbackInfoReturnable.setReturnValue(Float.valueOf(0.1f + ((Float) hitBox.getSizeValue().get()).floatValue()));
        }
    }

    @Overwrite
    public boolean func_180431_b(DamageSource damageSource) {
        return (!this.field_83001_bt || damageSource == DamageSource.field_76380_i || damageSource.func_180136_u()) ? false : true;
    }

    @Inject(method = {"turn"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void setAngles(float f, float f2, CallbackInfo callbackInfo) {
        if (((Module) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(NoPitchLimit.class))).getState()) {
            callbackInfo.cancel();
            float f3 = this.field_70125_A;
            float f4 = this.field_70177_z;
            this.field_70177_z = (float) (this.field_70177_z + (f * 0.15d));
            this.field_70125_A = (float) (this.field_70125_A - (f2 * 0.15d));
            this.field_70127_C += this.field_70125_A - f3;
            this.field_70126_B += this.field_70177_z - f4;
        }
    }
}
