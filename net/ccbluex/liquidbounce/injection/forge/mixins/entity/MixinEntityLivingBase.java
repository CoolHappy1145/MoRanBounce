package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.JumpEvent;
import net.ccbluex.liquidbounce.event.StrafeEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.modules.movement.AirJump;
import net.ccbluex.liquidbounce.features.module.modules.movement.LiquidWalk;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoJumpDelay;
import net.ccbluex.liquidbounce.features.module.modules.render.Animations;
import net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({EntityLivingBase.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/entity/MixinEntityLivingBase.class */
public abstract class MixinEntityLivingBase extends MixinEntity {

    @Shadow
    public int field_184628_bn;

    @Shadow
    protected boolean field_70703_bu;

    @Shadow
    private int field_70773_bE;

    @Shadow
    public abstract boolean func_184587_cr();

    @Shadow
    public abstract ItemStack func_184607_cu();

    @Shadow
    protected abstract float func_175134_bD();

    @Shadow
    public abstract PotionEffect func_70660_b(Potion potion);

    @Shadow
    public abstract boolean func_70644_a(Potion potion);

    @Override // net.ccbluex.liquidbounce.injection.forge.mixins.entity.MixinEntity
    @Shadow
    protected abstract void func_184231_a(double d, boolean z, IBlockState iBlockState, BlockPos blockPos);

    @Shadow
    public abstract float func_110143_aJ();

    @Shadow
    public abstract ItemStack func_184586_b(EnumHand enumHand);

    @Shadow
    protected abstract void func_70626_be();

    @Shadow
    protected abstract void func_70629_bd();

    @Shadow
    public abstract boolean func_184613_cA();

    @Shadow
    public abstract int func_184605_cv();

    @Overwrite
    protected void func_70664_aZ() {
        JumpEvent jumpEvent = new JumpEvent(func_175134_bD());
        LiquidBounce.eventManager.callEvent(jumpEvent);
        if (jumpEvent.isCancelled()) {
            return;
        }
        this.field_70181_x = jumpEvent.getMotion();
        if (func_70644_a(MobEffects.field_76430_j)) {
            this.field_70181_x += (func_70660_b(MobEffects.field_76430_j).func_76458_c() + 1) * 0.1f;
        }
        if (func_70051_ag()) {
            float f = this.field_70177_z * 0.017453292f;
            this.field_70159_w -= MathHelper.func_76126_a(f) * 0.2f;
            this.field_70179_y += MathHelper.func_76134_b(f) * 0.2f;
        }
        this.field_70160_al = true;
        ForgeHooks.onLivingJump((EntityLivingBase) this);
    }

    @Inject(method = {"onLivingUpdate"}, m59at = {@InterfaceC0563At("HEAD")})
    private void headLiving(CallbackInfo callbackInfo) {
        if (((Module) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(NoJumpDelay.class))).getState()) {
            this.field_70773_bE = 0;
        }
    }

    @Overwrite
    private int func_82166_i() {
        int iIntValue = ((Integer) Animations.SpeedSwing.get()).intValue() != 0 ? 2 + (20 - ((Integer) Animations.SpeedSwing.get()).intValue()) : 6;
        if (func_70644_a(MobEffects.field_76422_e)) {
            return iIntValue - (1 + func_70660_b(MobEffects.field_76422_e).func_76458_c());
        }
        return func_70644_a(MobEffects.field_76419_f) ? iIntValue + ((1 + func_70660_b(MobEffects.field_76419_f).func_76458_c()) * 2) : iIntValue;
    }

    @Inject(method = {"onLivingUpdate"}, m59at = {@InterfaceC0563At(value = "FIELD", target = "Lnet/minecraft/entity/EntityLivingBase;isJumping:Z", ordinal = 1)})
    private void onJumpSection(CallbackInfo callbackInfo) {
        if (((Module) Objects.requireNonNull(LiquidBounce.moduleManager.getModule(AirJump.class))).getState() && this.field_70703_bu && this.field_70773_bE == 0) {
            func_70664_aZ();
            this.field_70773_bE = 10;
        }
        LiquidWalk liquidWalk = (LiquidWalk) LiquidBounce.moduleManager.getModule(LiquidWalk.class);
        if (((LiquidWalk) Objects.requireNonNull(liquidWalk)).getState() && !this.field_70703_bu && !func_70093_af() && func_70090_H() && ((String) liquidWalk.getModeValue().get()).equalsIgnoreCase("Swim")) {
            func_70629_bd();
        }
    }

    @Inject(method = {"getLook"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void getLook(CallbackInfoReturnable callbackInfoReturnable) {
        if (((EntityLivingBase) this) instanceof EntityPlayerSP) {
            callbackInfoReturnable.setReturnValue(func_174806_f(this.field_70125_A, this.field_70177_z));
        }
    }

    @Inject(method = {"isPotionActive(Lnet/minecraft/potion/Potion;)Z"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void isPotionActive(Potion potion, CallbackInfoReturnable callbackInfoReturnable) {
        AntiBlind antiBlind = (AntiBlind) LiquidBounce.moduleManager.getModule(AntiBlind.class);
        if ((potion == MobEffects.field_76431_k || potion == MobEffects.field_76440_q) && ((AntiBlind) Objects.requireNonNull(antiBlind)).getState() && ((Boolean) antiBlind.getConfusionEffect().get()).booleanValue()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = {"moveRelative"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void handleRotations(float f, float f2, float f3, float f4, CallbackInfo callbackInfo) {
        if (this != Minecraft.func_71410_x().field_71439_g) {
            return;
        }
        StrafeEvent strafeEvent = new StrafeEvent(f, f3, f4);
        LiquidBounce.eventManager.callEvent(strafeEvent);
        if (strafeEvent.isCancelled()) {
            callbackInfo.cancel();
        }
    }
}
