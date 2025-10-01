package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import java.util.Arrays;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventState;
import net.ccbluex.liquidbounce.event.MotionEvent;
import net.ccbluex.liquidbounce.event.MoveEvent;
import net.ccbluex.liquidbounce.event.PushOutEvent;
import net.ccbluex.liquidbounce.event.SlowDownEvent;
import net.ccbluex.liquidbounce.event.StepConfirmEvent;
import net.ccbluex.liquidbounce.event.StepEvent;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.exploit.AntiHunger;
import net.ccbluex.liquidbounce.features.module.modules.exploit.Derp;
import net.ccbluex.liquidbounce.features.module.modules.exploit.PortalMenu;
import net.ccbluex.liquidbounce.features.module.modules.movement.InventoryMove;
import net.ccbluex.liquidbounce.features.module.modules.movement.NoSlow;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sneak;
import net.ccbluex.liquidbounce.features.module.modules.movement.Sprint;
import net.ccbluex.liquidbounce.features.module.modules.render.NoSwing;
import net.ccbluex.liquidbounce.features.module.modules.world.Scaffold;
import net.ccbluex.liquidbounce.utils.MovementUtils;
import net.ccbluex.liquidbounce.utils.Rotation;
import net.ccbluex.liquidbounce.utils.RotationUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.crash.CrashReport;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IJumpingMount;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.MovementInput;
import net.minecraft.util.ReportedException;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.PlayerSPPushOutOfBlocksEvent;
import net.minecraftforge.common.MinecraftForge;
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

@Mixin({EntityPlayerSP.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/entity/MixinEntityPlayerSP.class */
public abstract class MixinEntityPlayerSP extends MixinAbstractClientPlayer {

    @Shadow
    public boolean field_175171_bO;

    @Shadow
    public int field_71157_e;

    @Shadow
    public float field_71086_bY;

    @Shadow
    public float field_71080_cy;

    @Shadow
    public MovementInput field_71158_b;

    @Shadow
    public float field_110321_bQ;

    @Shadow
    public int field_110320_a;

    @Shadow
    @Final
    public NetHandlerPlayClient field_71174_a;

    @Shadow
    protected int field_71156_d;

    @Shadow
    protected Minecraft field_71159_c;

    @Shadow
    private boolean field_175170_bN;

    @Shadow
    private double field_175172_bI;

    @Shadow
    private int field_175168_bP;

    @Shadow
    private double field_175166_bJ;

    @Shadow
    private double field_175167_bK;

    @Shadow
    private float field_175164_bL;

    @Shadow
    private float field_175165_bM;

    @Shadow
    private int field_189812_cs;

    @Shadow
    private boolean field_189813_ct;

    @Shadow
    private boolean field_184841_cd;

    @Shadow
    private boolean field_189811_cr;

    @Override // net.ccbluex.liquidbounce.injection.forge.mixins.entity.MixinEntity
    @Shadow
    public abstract void func_184185_a(SoundEvent soundEvent, float f, float f2);

    @Shadow
    public abstract void func_70031_b(boolean z);

    @Shadow
    protected abstract boolean func_145771_j(double d, double d2, double d3);

    @Shadow
    public abstract void func_71016_p();

    @Shadow
    protected abstract void func_110318_g();

    @Shadow
    public abstract boolean func_110317_t();

    @Override // net.ccbluex.liquidbounce.injection.forge.mixins.entity.MixinEntity
    @Shadow
    public abstract boolean func_70093_af();

    @Shadow
    protected abstract boolean func_175160_A();

    @Shadow
    public abstract void func_71053_j();

    @Override // net.ccbluex.liquidbounce.injection.forge.mixins.entity.MixinEntityLivingBase
    @Shadow
    public abstract boolean func_184587_cr();

    @Shadow
    public abstract float func_110319_bJ();

    @Shadow
    protected abstract void func_189810_i(float f, float f2);

    @Overwrite
    public void func_175161_p() {
        try {
            LiquidBounce.eventManager.callEvent(new MotionEvent(EventState.PRE));
            InventoryMove inventoryMove = (InventoryMove) LiquidBounce.moduleManager.getModule(InventoryMove.class);
            Sneak sneak = (Sneak) LiquidBounce.moduleManager.getModule(Sneak.class);
            boolean z = func_70051_ag() && !((inventoryMove.getState() && ((Boolean) inventoryMove.getAacAdditionProValue().get()).booleanValue()) || LiquidBounce.moduleManager.getModule(AntiHunger.class).getState() || (sneak.getState() && ((!MovementUtils.isMoving() || !((Boolean) sneak.stopMoveValue.get()).booleanValue()) && ((String) sneak.modeValue.get()).equalsIgnoreCase("MineSecure"))));
            if (z != this.field_175171_bO) {
                if (z) {
                    this.field_71174_a.func_147297_a(new CPacketEntityAction((EntityPlayerSP) this, CPacketEntityAction.Action.START_SPRINTING));
                } else {
                    this.field_71174_a.func_147297_a(new CPacketEntityAction((EntityPlayerSP) this, CPacketEntityAction.Action.STOP_SPRINTING));
                }
                this.field_175171_bO = z;
            }
            boolean zFunc_70093_af = func_70093_af();
            if (zFunc_70093_af != this.field_175170_bN && (!sneak.getState() || ((String) sneak.modeValue.get()).equalsIgnoreCase("Legit"))) {
                if (zFunc_70093_af) {
                    this.field_71174_a.func_147297_a(new CPacketEntityAction((EntityPlayerSP) this, CPacketEntityAction.Action.START_SNEAKING));
                } else {
                    this.field_71174_a.func_147297_a(new CPacketEntityAction((EntityPlayerSP) this, CPacketEntityAction.Action.STOP_SNEAKING));
                }
                this.field_175170_bN = zFunc_70093_af;
            }
            if (func_175160_A()) {
                float yaw = this.field_70177_z;
                float pitch = this.field_70125_A;
                float yaw2 = RotationUtils.serverRotation.getYaw();
                float pitch2 = RotationUtils.serverRotation.getPitch();
                Derp derp = (Derp) LiquidBounce.moduleManager.getModule(Derp.class);
                if (derp.getState()) {
                    float[] rotation = derp.getRotation();
                    yaw = rotation[0];
                    pitch = rotation[1];
                }
                if (RotationUtils.targetRotation != null) {
                    yaw = RotationUtils.targetRotation.getYaw();
                    pitch = RotationUtils.targetRotation.getPitch();
                }
                AxisAlignedBB axisAlignedBBFunc_174813_aQ = func_174813_aQ();
                double d = this.field_70165_t - this.field_175172_bI;
                double d2 = axisAlignedBBFunc_174813_aQ.field_72338_b - this.field_175166_bJ;
                double d3 = this.field_70161_v - this.field_175167_bK;
                double d4 = yaw - yaw2;
                double d5 = pitch - pitch2;
                this.field_175168_bP++;
                boolean z2 = ((d * d) + (d2 * d2)) + (d3 * d3) > 9.0E-4d || this.field_175168_bP >= 20;
                boolean z3 = (d4 == 0.0d && d5 == 0.0d) ? false : true;
                if (func_184218_aH()) {
                    this.field_71174_a.func_147297_a(new CPacketPlayer.PositionRotation(this.field_70159_w, -999.0d, this.field_70179_y, this.field_70177_z, this.field_70125_A, this.field_70122_E));
                    z2 = false;
                } else if (z2 && z3) {
                    this.field_71174_a.func_147297_a(new CPacketPlayer.PositionRotation(this.field_70165_t, axisAlignedBBFunc_174813_aQ.field_72338_b, this.field_70161_v, this.field_70177_z, this.field_70125_A, this.field_70122_E));
                } else if (z2) {
                    this.field_71174_a.func_147297_a(new CPacketPlayer.Position(this.field_70165_t, axisAlignedBBFunc_174813_aQ.field_72338_b, this.field_70161_v, this.field_70122_E));
                } else if (z3) {
                    this.field_71174_a.func_147297_a(new CPacketPlayer.Rotation(this.field_70177_z, this.field_70125_A, this.field_70122_E));
                } else if (this.field_184841_cd != this.field_70122_E) {
                    this.field_71174_a.func_147297_a(new CPacketPlayer(this.field_70122_E));
                }
                if (z2) {
                    this.field_175172_bI = this.field_70165_t;
                    this.field_175166_bJ = axisAlignedBBFunc_174813_aQ.field_72338_b;
                    this.field_175167_bK = this.field_70161_v;
                    this.field_175168_bP = 0;
                }
                if (z3) {
                    this.field_175164_bL = this.field_70177_z;
                    this.field_175165_bM = this.field_70125_A;
                }
                this.field_184841_cd = this.field_70122_E;
                this.field_189811_cr = this.field_71159_c.field_71474_y.field_189989_R;
            }
            LiquidBounce.eventManager.callEvent(new MotionEvent(EventState.POST));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Inject(method = {"swingArm"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void swingItem(EnumHand enumHand, CallbackInfo callbackInfo) {
        NoSwing noSwing = (NoSwing) LiquidBounce.moduleManager.getModule(NoSwing.class);
        if (noSwing.getState()) {
            callbackInfo.cancel();
            if (!((Boolean) noSwing.getServerSideValue().get()).booleanValue()) {
                this.field_71174_a.func_147297_a(new CPacketAnimation(enumHand));
            }
        }
    }

    @Inject(method = {"pushOutOfBlocks"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void onPushOutOfBlocks(CallbackInfoReturnable callbackInfoReturnable) {
        PushOutEvent pushOutEvent = new PushOutEvent();
        if (this.field_70145_X) {
            pushOutEvent.cancelEvent();
        }
        LiquidBounce.eventManager.callEvent(pushOutEvent);
        if (pushOutEvent.isCancelled()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Overwrite
    public void func_70636_d() {
        try {
            LiquidBounce.eventManager.callEvent(new UpdateEvent());
        } catch (Throwable th) {
            th.printStackTrace();
        }
        this.field_71157_e++;
        if (this.field_71156_d > 0) {
            this.field_71156_d--;
        }
        this.field_71080_cy = this.field_71086_bY;
        if (this.field_71087_bX) {
            if (this.field_71159_c.field_71462_r != null && !this.field_71159_c.field_71462_r.func_73868_f() && !LiquidBounce.moduleManager.getModule(PortalMenu.class).getState()) {
                if (this.field_71159_c.field_71462_r instanceof GuiContainer) {
                    func_71053_j();
                }
                this.field_71159_c.func_147108_a((GuiScreen) null);
            }
            if (this.field_71086_bY == 0.0f) {
                this.field_71159_c.func_147118_V().func_147682_a(PositionedSoundRecord.func_184371_a(SoundEvents.field_187814_ei, (this.field_70146_Z.nextFloat() * 0.4f) + 0.8f));
            }
            this.field_71086_bY += 0.0125f;
            if (this.field_71086_bY >= 1.0f) {
                this.field_71086_bY = 1.0f;
            }
            this.field_71087_bX = false;
        } else if (!func_70644_a(MobEffects.field_76431_k) || func_70660_b(MobEffects.field_76431_k).func_76459_b() <= 60) {
            if (this.field_71086_bY > 0.0f) {
                this.field_71086_bY -= 0.05f;
            }
            if (this.field_71086_bY < 0.0f) {
                this.field_71086_bY = 0.0f;
            }
        } else {
            this.field_71086_bY += 0.006666667f;
            if (this.field_71086_bY > 1.0f) {
                this.field_71086_bY = 1.0f;
            }
        }
        if (this.field_71088_bW > 0) {
            this.field_71088_bW--;
        }
        boolean z = this.field_71158_b.field_78901_c;
        boolean z2 = this.field_71158_b.field_78899_d;
        boolean z3 = this.field_71158_b.field_192832_b >= 0.8f;
        this.field_71158_b.func_78898_a();
        NoSlow noSlow = (NoSlow) LiquidBounce.moduleManager.getModule(NoSlow.class);
        KillAura killAura = (KillAura) LiquidBounce.moduleManager.getModule(KillAura.class);
        ForgeHooksClient.onInputUpdate((EntityPlayerSP) this, this.field_71158_b);
        this.field_71159_c.func_193032_ao().func_193293_a(this.field_71158_b);
        if (func_184587_cr() || ((func_184586_b(EnumHand.MAIN_HAND).func_77973_b() instanceof ItemSword) && killAura.getBlockingStatus() && !func_184218_aH())) {
            SlowDownEvent slowDownEvent = new SlowDownEvent(0.2f, 0.2f);
            LiquidBounce.eventManager.callEvent(slowDownEvent);
            this.field_71158_b.field_78902_a *= slowDownEvent.getStrafe();
            this.field_71158_b.field_192832_b *= slowDownEvent.getForward();
            this.field_71156_d = 0;
        }
        boolean z4 = false;
        if (this.field_189812_cs > 0) {
            this.field_189812_cs--;
            z4 = true;
            this.field_71158_b.field_78901_c = true;
        }
        PlayerSPPushOutOfBlocksEvent playerSPPushOutOfBlocksEvent = new PlayerSPPushOutOfBlocksEvent((EntityPlayerSP) this, func_174813_aQ());
        if (!MinecraftForge.EVENT_BUS.post(playerSPPushOutOfBlocksEvent)) {
            AxisAlignedBB entityBoundingBox = playerSPPushOutOfBlocksEvent.getEntityBoundingBox();
            func_145771_j(this.field_70165_t - (this.field_70130_N * 0.35d), entityBoundingBox.field_72338_b + 0.5d, this.field_70161_v + (this.field_70130_N * 0.35d));
            func_145771_j(this.field_70165_t - (this.field_70130_N * 0.35d), entityBoundingBox.field_72338_b + 0.5d, this.field_70161_v - (this.field_70130_N * 0.35d));
            func_145771_j(this.field_70165_t + (this.field_70130_N * 0.35d), entityBoundingBox.field_72338_b + 0.5d, this.field_70161_v - (this.field_70130_N * 0.35d));
            func_145771_j(this.field_70165_t + (this.field_70130_N * 0.35d), entityBoundingBox.field_72338_b + 0.5d, this.field_70161_v + (this.field_70130_N * 0.35d));
        }
        Sprint sprint = (Sprint) LiquidBounce.moduleManager.getModule(Sprint.class);
        boolean z5 = !((Boolean) sprint.foodValue.get()).booleanValue() || ((float) func_71024_bL().func_75116_a()) > 6.0f || this.field_71075_bZ.field_75101_c;
        if (this.field_70122_E && !z2 && !z3 && this.field_71158_b.field_192832_b >= 0.8f && !func_70051_ag() && z5 && !func_184587_cr() && !func_70644_a(MobEffects.field_76440_q)) {
            if (this.field_71156_d <= 0 && !this.field_71159_c.field_71474_y.field_151444_V.func_151470_d()) {
                this.field_71156_d = 7;
            } else {
                func_70031_b(true);
            }
        }
        if (!func_70051_ag() && this.field_71158_b.field_192832_b >= 0.8f && z5 && ((noSlow.getState() || !func_184587_cr()) && !func_70644_a(MobEffects.field_76440_q) && this.field_71159_c.field_71474_y.field_151444_V.func_151470_d())) {
            func_70031_b(true);
        }
        Scaffold scaffold = (Scaffold) LiquidBounce.moduleManager.getModule(Scaffold.class);
        if ((scaffold.getState() && !scaffold.getCanSprint()) || (sprint.getState() && ((Boolean) sprint.checkServerSide.get()).booleanValue() && ((this.field_70122_E || !((Boolean) sprint.checkServerSideGround.get()).booleanValue()) && !((Boolean) sprint.allDirectionsValue.get()).booleanValue() && RotationUtils.targetRotation != null && RotationUtils.getRotationDifference(new Rotation(this.field_71159_c.field_71439_g.field_70177_z, this.field_71159_c.field_71439_g.field_70125_A)) > 30.0d))) {
            func_70031_b(false);
        }
        if (func_70051_ag() && (this.field_71158_b.field_192832_b < 0.8f || this.field_70123_F || !z5)) {
            func_70031_b(false);
        }
        if (this.field_71075_bZ.field_75101_c) {
            if (this.field_71159_c.field_71442_b.func_178887_k()) {
                if (!this.field_71075_bZ.field_75100_b) {
                    this.field_71075_bZ.field_75100_b = true;
                    func_71016_p();
                }
            } else if (!z && this.field_71158_b.field_78901_c && !z4) {
                if (this.field_71101_bC == 0) {
                    this.field_71101_bC = 7;
                } else {
                    this.field_71075_bZ.field_75100_b = !this.field_71075_bZ.field_75100_b;
                    func_71016_p();
                    this.field_71101_bC = 0;
                }
            }
        }
        if (this.field_71158_b.field_78901_c && !z && !this.field_70122_E && this.field_70181_x < 0.0d && !func_184613_cA() && !this.field_71075_bZ.field_75100_b) {
            ItemStack itemStackFunc_184582_a = func_184582_a(EntityEquipmentSlot.CHEST);
            if (itemStackFunc_184582_a.func_77973_b() == Items.field_185160_cR && ItemElytra.func_185069_d(itemStackFunc_184582_a)) {
                this.field_71174_a.func_147297_a(new CPacketEntityAction((EntityPlayerSP) this, CPacketEntityAction.Action.START_FALL_FLYING));
            }
        }
        this.field_189813_ct = func_184613_cA();
        if (this.field_71075_bZ.field_75100_b && func_175160_A()) {
            if (this.field_71158_b.field_78899_d) {
                this.field_71158_b.field_78902_a = (float) (this.field_71158_b.field_78902_a / 0.3d);
                this.field_71158_b.field_192832_b = (float) (this.field_71158_b.field_192832_b / 0.3d);
                this.field_70181_x -= this.field_71075_bZ.func_75093_a() * 3.0f;
            }
            if (this.field_71158_b.field_78901_c) {
                this.field_70181_x += this.field_71075_bZ.func_75093_a() * 3.0f;
            }
        }
        if (func_110317_t()) {
            IJumpingMount iJumpingMountFunc_184187_bx = func_184187_bx();
            if (this.field_110320_a < 0) {
                this.field_110320_a++;
                if (this.field_110320_a == 0) {
                    this.field_110321_bQ = 0.0f;
                }
            }
            if (z && !this.field_71158_b.field_78901_c) {
                this.field_110320_a = -10;
                iJumpingMountFunc_184187_bx.func_110206_u(MathHelper.func_76141_d(func_110319_bJ() * 100.0f));
                func_110318_g();
            } else if (!z && this.field_71158_b.field_78901_c) {
                this.field_110320_a = 0;
                this.field_110321_bQ = 0.0f;
            } else if (z) {
                this.field_110320_a++;
                if (this.field_110320_a < 10) {
                    this.field_110321_bQ = this.field_110320_a * 0.1f;
                } else {
                    this.field_110321_bQ = 0.8f + ((2.0f / (this.field_110320_a - 9)) * 0.1f);
                }
            }
        } else {
            this.field_110321_bQ = 0.0f;
        }
        if (!this.field_71159_c.field_71439_g.func_184587_cr() && noSlow.getState() && (this.field_71159_c.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword)) {
            this.field_71159_c.field_71439_g.field_71071_by.field_184439_c.set(0, ItemStack.field_190927_a);
        }
        super.func_70636_d();
        if (this.field_70122_E && this.field_71075_bZ.field_75100_b && !this.field_71159_c.field_71442_b.func_178887_k()) {
            this.field_71075_bZ.field_75100_b = false;
            func_71016_p();
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: net.minecraft.util.ReportedException */
    @Overwrite
    public void func_70091_d(MoverType moverType, double d, double d2, double d3) throws ReportedException {
        double d4;
        double d5;
        double d6;
        MoveEvent moveEvent = new MoveEvent(d, d2, d3);
        LiquidBounce.eventManager.callEvent(moveEvent);
        if (moveEvent.isCancelled()) {
            return;
        }
        double x = moveEvent.getX();
        double y = moveEvent.getY();
        double z = moveEvent.getZ();
        if (this.field_70145_X) {
            func_174826_a(func_174813_aQ().func_72317_d(x, y, z));
            func_174829_m();
        } else {
            if (moverType == MoverType.PISTON) {
                long jFunc_82737_E = this.field_70170_p.func_82737_E();
                if (jFunc_82737_E != this.field_191506_aJ) {
                    Arrays.fill(this.field_191505_aI, 0.0d);
                    this.field_191506_aJ = jFunc_82737_E;
                }
                if (x != 0.0d) {
                    int iOrdinal = EnumFacing.Axis.X.ordinal();
                    double dFunc_151237_a = MathHelper.func_151237_a(x + this.field_191505_aI[iOrdinal], -0.51d, 0.51d);
                    x = dFunc_151237_a - this.field_191505_aI[iOrdinal];
                    this.field_191505_aI[iOrdinal] = dFunc_151237_a;
                    if (Math.abs(x) <= 9.999999747378752E-6d) {
                        return;
                    }
                } else if (y != 0.0d) {
                    int iOrdinal2 = EnumFacing.Axis.Y.ordinal();
                    double dFunc_151237_a2 = MathHelper.func_151237_a(y + this.field_191505_aI[iOrdinal2], -0.51d, 0.51d);
                    y = dFunc_151237_a2 - this.field_191505_aI[iOrdinal2];
                    this.field_191505_aI[iOrdinal2] = dFunc_151237_a2;
                    if (Math.abs(y) <= 9.999999747378752E-6d) {
                        return;
                    }
                } else {
                    if (z == 0.0d) {
                        return;
                    }
                    int iOrdinal3 = EnumFacing.Axis.Z.ordinal();
                    double dFunc_151237_a3 = MathHelper.func_151237_a(z + this.field_191505_aI[iOrdinal3], -0.51d, 0.51d);
                    z = dFunc_151237_a3 - this.field_191505_aI[iOrdinal3];
                    this.field_191505_aI[iOrdinal3] = dFunc_151237_a3;
                    if (Math.abs(z) <= 9.999999747378752E-6d) {
                        return;
                    }
                }
            }
            this.field_70170_p.field_72984_F.func_76320_a("move");
            double d7 = this.field_70165_t;
            double d8 = this.field_70163_u;
            double d9 = this.field_70161_v;
            if (this.field_70134_J) {
                this.field_70134_J = false;
                x *= 0.25d;
                y *= 0.05000000074505806d;
                z *= 0.25d;
                this.field_70159_w = 0.0d;
                this.field_70181_x = 0.0d;
                this.field_70179_y = 0.0d;
            }
            double d10 = x;
            double d11 = y;
            double d12 = z;
            if ((moverType == MoverType.SELF || moverType == MoverType.PLAYER) && (((this.field_70122_E && func_70093_af()) || moveEvent.isSafeWalk()) && (this instanceof EntityPlayer))) {
                while (x != 0.0d && this.field_70170_p.func_184144_a((EntityPlayerSP) this, func_174813_aQ().func_72317_d(x, -this.field_70138_W, 0.0d)).isEmpty()) {
                    if (x < 0.05d && x >= -0.05d) {
                        d6 = 0.0d;
                    } else if (x > 0.0d) {
                        d6 = x - 0.05d;
                    } else {
                        d6 = x + 0.05d;
                    }
                    x = d6;
                    d10 = x;
                }
                while (z != 0.0d && this.field_70170_p.func_184144_a((EntityPlayerSP) this, func_174813_aQ().func_72317_d(0.0d, -this.field_70138_W, z)).isEmpty()) {
                    if (z < 0.05d && z >= -0.05d) {
                        d5 = 0.0d;
                    } else if (z > 0.0d) {
                        d5 = z - 0.05d;
                    } else {
                        d5 = z + 0.05d;
                    }
                    z = d5;
                    d12 = z;
                }
                while (x != 0.0d && z != 0.0d && this.field_70170_p.func_184144_a((EntityPlayerSP) this, func_174813_aQ().func_72317_d(x, -this.field_70138_W, z)).isEmpty()) {
                    if (x < 0.05d && x >= -0.05d) {
                        x = 0.0d;
                    } else if (x > 0.0d) {
                        x -= 0.05d;
                    } else {
                        x += 0.05d;
                    }
                    d10 = x;
                    if (z < 0.05d && z >= -0.05d) {
                        d4 = 0.0d;
                    } else if (z > 0.0d) {
                        d4 = z - 0.05d;
                    } else {
                        d4 = z + 0.05d;
                    }
                    z = d4;
                    d12 = z;
                }
            }
            List listFunc_184144_a = this.field_70170_p.func_184144_a((EntityPlayerSP) this, func_174813_aQ().func_72321_a(x, y, z));
            AxisAlignedBB axisAlignedBBFunc_174813_aQ = func_174813_aQ();
            if (y != 0.0d) {
                int size = listFunc_184144_a.size();
                for (int i = 0; i < size; i++) {
                    y = ((AxisAlignedBB) listFunc_184144_a.get(i)).func_72323_b(func_174813_aQ(), y);
                }
                func_174826_a(func_174813_aQ().func_72317_d(0.0d, y, 0.0d));
            }
            if (x != 0.0d) {
                int size2 = listFunc_184144_a.size();
                for (int i2 = 0; i2 < size2; i2++) {
                    x = ((AxisAlignedBB) listFunc_184144_a.get(i2)).func_72316_a(func_174813_aQ(), x);
                }
                if (x != 0.0d) {
                    func_174826_a(func_174813_aQ().func_72317_d(x, 0.0d, 0.0d));
                }
            }
            if (z != 0.0d) {
                int size3 = listFunc_184144_a.size();
                for (int i3 = 0; i3 < size3; i3++) {
                    z = ((AxisAlignedBB) listFunc_184144_a.get(i3)).func_72322_c(func_174813_aQ(), z);
                }
                if (z != 0.0d) {
                    func_174826_a(func_174813_aQ().func_72317_d(0.0d, 0.0d, z));
                }
            }
            boolean z2 = this.field_70122_E || (d11 != y && d11 < 0.0d);
            if (this.field_70138_W > 0.0f && z2 && (d10 != x || d12 != z)) {
                StepEvent stepEvent = new StepEvent(this.field_70138_W);
                LiquidBounce.eventManager.callEvent(stepEvent);
                double d13 = x;
                double d14 = y;
                double d15 = z;
                AxisAlignedBB axisAlignedBBFunc_174813_aQ2 = func_174813_aQ();
                func_174826_a(axisAlignedBBFunc_174813_aQ);
                double stepHeight = stepEvent.getStepHeight();
                List listFunc_184144_a2 = this.field_70170_p.func_184144_a((EntityPlayerSP) this, func_174813_aQ().func_72321_a(d10, stepHeight, d12));
                AxisAlignedBB axisAlignedBBFunc_174813_aQ3 = func_174813_aQ();
                AxisAlignedBB axisAlignedBBFunc_72321_a = axisAlignedBBFunc_174813_aQ3.func_72321_a(d10, 0.0d, d12);
                double dFunc_72323_b = stepHeight;
                int size4 = listFunc_184144_a2.size();
                for (int i4 = 0; i4 < size4; i4++) {
                    dFunc_72323_b = ((AxisAlignedBB) listFunc_184144_a2.get(i4)).func_72323_b(axisAlignedBBFunc_72321_a, dFunc_72323_b);
                }
                AxisAlignedBB axisAlignedBBFunc_72317_d = axisAlignedBBFunc_174813_aQ3.func_72317_d(0.0d, dFunc_72323_b, 0.0d);
                double dFunc_72316_a = d10;
                int size5 = listFunc_184144_a2.size();
                for (int i5 = 0; i5 < size5; i5++) {
                    dFunc_72316_a = ((AxisAlignedBB) listFunc_184144_a2.get(i5)).func_72316_a(axisAlignedBBFunc_72317_d, dFunc_72316_a);
                }
                AxisAlignedBB axisAlignedBBFunc_72317_d2 = axisAlignedBBFunc_72317_d.func_72317_d(dFunc_72316_a, 0.0d, 0.0d);
                double dFunc_72322_c = d12;
                int size6 = listFunc_184144_a2.size();
                for (int i6 = 0; i6 < size6; i6++) {
                    dFunc_72322_c = ((AxisAlignedBB) listFunc_184144_a2.get(i6)).func_72322_c(axisAlignedBBFunc_72317_d2, dFunc_72322_c);
                }
                AxisAlignedBB axisAlignedBBFunc_72317_d3 = axisAlignedBBFunc_72317_d2.func_72317_d(0.0d, 0.0d, dFunc_72322_c);
                AxisAlignedBB axisAlignedBBFunc_174813_aQ4 = func_174813_aQ();
                double dFunc_72323_b2 = stepHeight;
                int size7 = listFunc_184144_a2.size();
                for (int i7 = 0; i7 < size7; i7++) {
                    dFunc_72323_b2 = ((AxisAlignedBB) listFunc_184144_a2.get(i7)).func_72323_b(axisAlignedBBFunc_174813_aQ4, dFunc_72323_b2);
                }
                AxisAlignedBB axisAlignedBBFunc_72317_d4 = axisAlignedBBFunc_174813_aQ4.func_72317_d(0.0d, dFunc_72323_b2, 0.0d);
                double dFunc_72316_a2 = d10;
                int size8 = listFunc_184144_a2.size();
                for (int i8 = 0; i8 < size8; i8++) {
                    dFunc_72316_a2 = ((AxisAlignedBB) listFunc_184144_a2.get(i8)).func_72316_a(axisAlignedBBFunc_72317_d4, dFunc_72316_a2);
                }
                AxisAlignedBB axisAlignedBBFunc_72317_d5 = axisAlignedBBFunc_72317_d4.func_72317_d(dFunc_72316_a2, 0.0d, 0.0d);
                double dFunc_72322_c2 = d12;
                int size9 = listFunc_184144_a2.size();
                for (int i9 = 0; i9 < size9; i9++) {
                    dFunc_72322_c2 = ((AxisAlignedBB) listFunc_184144_a2.get(i9)).func_72322_c(axisAlignedBBFunc_72317_d5, dFunc_72322_c2);
                }
                AxisAlignedBB axisAlignedBBFunc_72317_d6 = axisAlignedBBFunc_72317_d5.func_72317_d(0.0d, 0.0d, dFunc_72322_c2);
                if ((dFunc_72316_a * dFunc_72316_a) + (dFunc_72322_c * dFunc_72322_c) > (dFunc_72316_a2 * dFunc_72316_a2) + (dFunc_72322_c2 * dFunc_72322_c2)) {
                    x = dFunc_72316_a;
                    z = dFunc_72322_c;
                    y = -dFunc_72323_b;
                    func_174826_a(axisAlignedBBFunc_72317_d3);
                } else {
                    x = dFunc_72316_a2;
                    z = dFunc_72322_c2;
                    y = -dFunc_72323_b2;
                    func_174826_a(axisAlignedBBFunc_72317_d6);
                }
                int size10 = listFunc_184144_a2.size();
                for (int i10 = 0; i10 < size10; i10++) {
                    y = ((AxisAlignedBB) listFunc_184144_a2.get(i10)).func_72323_b(func_174813_aQ(), y);
                }
                func_174826_a(func_174813_aQ().func_72317_d(0.0d, y, 0.0d));
                if ((d13 * d13) + (d15 * d15) >= (x * x) + (z * z)) {
                    x = d13;
                    y = d14;
                    z = d15;
                    func_174826_a(axisAlignedBBFunc_174813_aQ2);
                } else {
                    LiquidBounce.eventManager.callEvent(new StepConfirmEvent());
                }
            }
            this.field_70170_p.field_72984_F.func_76319_b();
            this.field_70170_p.field_72984_F.func_76320_a("rest");
            func_174829_m();
            this.field_70123_F = (d10 == x && d12 == z) ? false : true;
            this.field_70124_G = d11 != y;
            this.field_70122_E = this.field_70124_G && d11 < 0.0d;
            this.field_70132_H = this.field_70123_F || this.field_70124_G;
            BlockPos blockPos = new BlockPos(MathHelper.func_76128_c(this.field_70165_t), MathHelper.func_76128_c(this.field_70163_u - 0.20000000298023224d), MathHelper.func_76128_c(this.field_70161_v));
            IBlockState iBlockStateFunc_180495_p = this.field_70170_p.func_180495_p(blockPos);
            if (iBlockStateFunc_180495_p.func_185904_a() == Material.field_151579_a) {
                BlockPos blockPosFunc_177977_b = blockPos.func_177977_b();
                IBlockState iBlockStateFunc_180495_p2 = this.field_70170_p.func_180495_p(blockPosFunc_177977_b);
                Block blockFunc_177230_c = iBlockStateFunc_180495_p2.func_177230_c();
                if ((blockFunc_177230_c instanceof BlockFence) || (blockFunc_177230_c instanceof BlockWall) || (blockFunc_177230_c instanceof BlockFenceGate)) {
                    iBlockStateFunc_180495_p = iBlockStateFunc_180495_p2;
                    blockPos = blockPosFunc_177977_b;
                }
            }
            func_184231_a(y, this.field_70122_E, iBlockStateFunc_180495_p, blockPos);
            if (d10 != x) {
                this.field_70159_w = 0.0d;
            }
            if (d12 != z) {
                this.field_70179_y = 0.0d;
            }
            Block blockFunc_177230_c2 = iBlockStateFunc_180495_p.func_177230_c();
            if (d11 != y) {
                blockFunc_177230_c2.func_176216_a(this.field_70170_p, (EntityPlayerSP) this);
            }
            if (func_70041_e_() && ((!this.field_70122_E || !func_70093_af() || !(this instanceof EntityPlayer)) && !func_184218_aH())) {
                double d16 = this.field_70165_t - d7;
                double d17 = this.field_70163_u - d8;
                double d18 = this.field_70161_v - d9;
                if (blockFunc_177230_c2 != Blocks.field_150468_ap) {
                    d17 = 0.0d;
                }
                if (blockFunc_177230_c2 != null && this.field_70122_E) {
                    blockFunc_177230_c2.func_176199_a(this.field_70170_p, blockPos, (EntityPlayerSP) this);
                }
                this.field_70140_Q = (float) (this.field_70140_Q + (MathHelper.func_76133_a((d16 * d16) + (d18 * d18)) * 0.6d));
                this.field_82151_R = (float) (this.field_82151_R + (MathHelper.func_76133_a((d16 * d16) + (d17 * d17) + (d18 * d18)) * 0.6d));
                if (this.field_82151_R > this.field_70150_b && iBlockStateFunc_180495_p.func_185904_a() != Material.field_151579_a) {
                    this.field_70150_b = ((int) this.field_82151_R) + 1;
                    if (func_70090_H()) {
                        Entity entityFunc_184179_bs = (!func_184207_aI() || func_184179_bs() == null) ? (EntityPlayerSP) this : func_184179_bs();
                        float fFunc_76133_a = MathHelper.func_76133_a((entityFunc_184179_bs.field_70159_w * entityFunc_184179_bs.field_70159_w * 0.20000000298023224d) + (entityFunc_184179_bs.field_70181_x * entityFunc_184179_bs.field_70181_x) + (entityFunc_184179_bs.field_70179_y * entityFunc_184179_bs.field_70179_y * 0.20000000298023224d)) * (entityFunc_184179_bs == this ? 0.35f : 0.4f);
                        if (fFunc_76133_a > 1.0f) {
                            fFunc_76133_a = 1.0f;
                        }
                        func_184185_a(func_184184_Z(), fFunc_76133_a, 1.0f + ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4f));
                    } else {
                        func_180429_a(blockPos, blockFunc_177230_c2);
                    }
                } else if (this.field_82151_R > this.field_191959_ay && func_191957_ae() && iBlockStateFunc_180495_p.func_185904_a() == Material.field_151579_a) {
                    this.field_191959_ay = func_191954_d(this.field_82151_R);
                }
            }
            try {
                func_145775_I();
                boolean zFunc_70026_G = func_70026_G();
                if (this.field_70170_p.func_147470_e(func_174813_aQ().func_186664_h(0.001d))) {
                    func_70081_e(1);
                    if (!zFunc_70026_G) {
                        this.field_190534_ay++;
                        if (this.field_190534_ay == 0) {
                            func_70015_d(8);
                        }
                    }
                } else if (this.field_190534_ay <= 0) {
                    this.field_190534_ay = -func_190531_bD();
                }
                if (zFunc_70026_G && func_70027_ad()) {
                    func_184185_a(SoundEvents.field_187541_bC, 0.7f, 1.6f + ((this.field_70146_Z.nextFloat() - this.field_70146_Z.nextFloat()) * 0.4f));
                    this.field_190534_ay = -func_190531_bD();
                }
                this.field_70170_p.field_72984_F.func_76319_b();
            } catch (Throwable th) {
                CrashReport crashReportFunc_85055_a = CrashReport.func_85055_a(th, "Checking entity block collision");
                func_85029_a(crashReportFunc_85055_a.func_85058_a("Entity being checked for collision"));
                throw new ReportedException(crashReportFunc_85055_a);
            }
        }
        func_189810_i((float) (this.field_70165_t - this.field_70165_t), (float) (this.field_70161_v - this.field_70161_v));
    }
}
