package net.ccbluex.liquidbounce.injection.forge.mixins.item;

import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.combat.KillAura;
import net.ccbluex.liquidbounce.features.module.modules.render.Animations;
import net.ccbluex.liquidbounce.features.module.modules.render.AntiBlind;
import net.ccbluex.liquidbounce.utils.timer.MSTimer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ItemRenderer.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/item/MixinItemRenderer.class */
public abstract class MixinItemRenderer {

    @Shadow
    private ItemStack field_187467_d;

    @Shadow
    private ItemStack field_187468_e;

    @Shadow
    private float field_187469_f;

    @Shadow
    private float field_187470_g;

    @Shadow
    private float field_187471_h;

    @Shadow
    private float field_187472_i;

    @Shadow
    @Final
    private Minecraft field_78455_a;
    float delay = 0.0f;
    MSTimer rotateTimer = new MSTimer();

    @Shadow
    protected abstract void func_187463_a(float f, float f2, float f3);

    @Shadow
    protected abstract void func_187453_a(EnumHandSide enumHandSide, float f);

    @Shadow
    protected abstract void func_187454_a(float f, EnumHandSide enumHandSide, ItemStack itemStack);

    @Shadow
    protected abstract void func_187456_a(float f, float f2, EnumHandSide enumHandSide);

    @Shadow
    protected abstract void func_187465_a(float f, EnumHandSide enumHandSide, float f2, ItemStack itemStack);

    @Shadow
    protected abstract void func_187459_b(EnumHandSide enumHandSide, float f);

    @Shadow
    public abstract void func_187462_a(EntityLivingBase entityLivingBase, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, boolean z);

    @Overwrite
    public void func_187457_a(AbstractClientPlayer abstractClientPlayer, float f, float f2, EnumHand enumHand, float f3, ItemStack itemStack, float f4) {
        boolean z = enumHand == EnumHand.MAIN_HAND;
        EntityPlayerSP entityPlayerSP = this.field_78455_a.field_71439_g;
        EnumHandSide enumHandSideFunc_184591_cq = z ? abstractClientPlayer.func_184591_cq() : abstractClientPlayer.func_184591_cq().func_188468_a();
        GlStateManager.func_179094_E();
        if (itemStack.func_190926_b()) {
            if (z && !abstractClientPlayer.func_82150_aj()) {
                func_187456_a(f4, f3, enumHandSideFunc_184591_cq);
            }
        } else if (itemStack.func_77973_b() instanceof ItemMap) {
            if (z && this.field_187468_e.func_190926_b()) {
                func_187463_a(f2, f4, f3);
            } else {
                func_187465_a(f4, enumHandSideFunc_184591_cq, f3, itemStack);
            }
        } else {
            KillAura killAura = (KillAura) LiquidBounce.moduleManager.getModule(KillAura.class);
            Animations animations = (Animations) LiquidBounce.moduleManager.getModule(Animations.class);
            boolean z2 = enumHandSideFunc_184591_cq == EnumHandSide.RIGHT;
            if (abstractClientPlayer.func_184587_cr() && abstractClientPlayer.func_184605_cv() > 0 && abstractClientPlayer.func_184600_cs() == enumHand) {
                int i = z2 ? 1 : -1;
                switch (C04611.$SwitchMap$net$minecraft$item$EnumAction[(killAura.getBlockingStatus() ? EnumAction.BLOCK : itemStack.func_77975_n()).ordinal()]) {
                    case 1:
                        func_187459_b(enumHandSideFunc_184591_cq, f4);
                        break;
                    case 2:
                        transformSideFirstPersonBlock(enumHandSideFunc_184591_cq, f4, f3);
                        break;
                    case 3:
                    case 4:
                        func_187454_a(f, enumHandSideFunc_184591_cq, itemStack);
                        func_187459_b(enumHandSideFunc_184591_cq, f4);
                        break;
                    case 5:
                        func_187459_b(enumHandSideFunc_184591_cq, f4);
                        GlStateManager.func_179109_b(i * (-0.2785682f), 0.18344387f, 0.15731531f);
                        GlStateManager.func_179114_b(-13.935f, 1.0f, 0.0f, 0.0f);
                        GlStateManager.func_179114_b(i * 35.3f, 0.0f, 1.0f, 0.0f);
                        GlStateManager.func_179114_b(i * (-9.785f), 0.0f, 0.0f, 1.0f);
                        float fFunc_77988_m = itemStack.func_77988_m() - ((this.field_78455_a.field_71439_g.func_184605_cv() - f) + 1.0f);
                        float f5 = fFunc_77988_m / 20.0f;
                        float f6 = ((f5 * f5) + (f5 * 2.0f)) / 3.0f;
                        if (f6 > 1.0f) {
                            f6 = 1.0f;
                        }
                        if (f6 > 0.1f) {
                            float fFunc_76126_a = MathHelper.func_76126_a((fFunc_77988_m - 0.1f) * 1.3f) * (f6 - 0.1f);
                            GlStateManager.func_179109_b(fFunc_76126_a * 0.0f, fFunc_76126_a * 0.004f, fFunc_76126_a * 0.0f);
                        }
                        GlStateManager.func_179109_b(f6 * 0.0f, f6 * 0.0f, f6 * 0.04f);
                        GlStateManager.func_179152_a(1.0f, 1.0f, 1.0f + (f6 * 0.2f));
                        GlStateManager.func_179114_b(i * 45.0f, 0.0f, -1.0f, 0.0f);
                        break;
                }
            } else if (this.field_78455_a.field_71439_g.func_184614_ca().func_77973_b() != null && (this.field_78455_a.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword) && (((killAura.getTarget() != null && killAura.getBlockingStatus()) || this.field_78455_a.field_71474_y.field_74313_G.field_74513_e) && animations.getState())) {
                GlStateManager.func_179109_b(((Float) Animations.itemXValue.get()).floatValue(), ((Float) Animations.itemYValue.get()).floatValue(), ((Float) Animations.itemZValue.get()).floatValue());
                float f7 = ((Boolean) Animations.SPValue.get()).booleanValue() ? f4 : 0.0f;
                if (((String) Animations.Sword.get()).equals("1.7")) {
                    transformSideFirstPersonBlock(enumHandSideFunc_184591_cq, f7, f3);
                }
                if (((String) Animations.Sword.get()).equals("Old")) {
                    transformSideFirstPersonBlock(enumHandSideFunc_184591_cq, (-0.1f) + f7, f3);
                }
                if (((String) Animations.Sword.get()).equals("Push")) {
                    Push(enumHandSideFunc_184591_cq, f7, f3);
                }
                if (((String) Animations.Sword.get()).equals("WindMill")) {
                    WindMill(enumHandSideFunc_184591_cq, (-0.2f) + f7, f3);
                }
                if (((String) Animations.Sword.get()).equals("Smooth")) {
                    SmoothBlock(enumHandSideFunc_184591_cq, f7, f3);
                }
                if (((String) Animations.Sword.get()).equals("SigmaOld")) {
                    sigmaold(enumHandSideFunc_184591_cq, f7, f3);
                }
                if (((String) Animations.Sword.get()).equals("Jello")) {
                    jello(enumHandSideFunc_184591_cq, f7, f3);
                }
                GlStateManager.func_179152_a(((Float) Animations.itemScaleValue.get()).floatValue(), ((Float) Animations.itemScaleValue.get()).floatValue(), ((Float) Animations.itemScaleValue.get()).floatValue());
            } else {
                if (((Boolean) Animations.heldValue.get()).booleanValue()) {
                    GlStateManager.func_179109_b(((Float) Animations.itemXValue.get()).floatValue(), ((Float) Animations.itemYValue.get()).floatValue(), ((Float) Animations.itemZValue.get()).floatValue());
                }
                if (!((Boolean) Animations.SwingAnimValue.get()).booleanValue()) {
                    GlStateManager.func_179109_b((z2 ? 1 : -1) * (-0.4f) * MathHelper.func_76126_a(MathHelper.func_76129_c(f3) * 3.1415927f), 0.2f * MathHelper.func_76126_a(MathHelper.func_76129_c(f3) * 6.2831855f), (-0.2f) * MathHelper.func_76126_a(f3 * 3.1415927f));
                    func_187459_b(enumHandSideFunc_184591_cq, f4);
                    func_187453_a(enumHandSideFunc_184591_cq, f3);
                } else {
                    func_187459_b(enumHandSideFunc_184591_cq, f4);
                    func_187453_a(enumHandSideFunc_184591_cq, f3);
                }
                rotateItemAnim();
                if (((Boolean) Animations.heldValue.get()).booleanValue()) {
                    GlStateManager.func_179152_a(((Float) Animations.itemScaleValue.get()).floatValue(), ((Float) Animations.itemScaleValue.get()).floatValue(), ((Float) Animations.itemScaleValue.get()).floatValue());
                }
            }
            if (((Boolean) Animations.shieldValue.get()).booleanValue()) {
                if ((itemStack.func_77973_b() instanceof ItemShield) && (this.field_187467_d.func_77973_b() instanceof ItemSword)) {
                    GlStateManager.func_179121_F();
                    return;
                }
                func_187462_a(abstractClientPlayer, itemStack, z2 ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !z2);
            } else {
                func_187462_a(abstractClientPlayer, itemStack, z2 ? ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND : ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND, !z2);
            }
        }
        GlStateManager.func_179121_F();
    }

    /* renamed from: net.ccbluex.liquidbounce.injection.forge.mixins.item.MixinItemRenderer$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/item/MixinItemRenderer$1.class */
    static /* synthetic */ class C04611 {
        static final int[] $SwitchMap$net$minecraft$item$EnumAction = new int[EnumAction.values().length];

        static {
            try {
                $SwitchMap$net$minecraft$item$EnumAction[EnumAction.NONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$net$minecraft$item$EnumAction[EnumAction.BLOCK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$net$minecraft$item$EnumAction[EnumAction.EAT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$net$minecraft$item$EnumAction[EnumAction.DRINK.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$net$minecraft$item$EnumAction[EnumAction.BOW.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private static void jello(EnumHandSide enumHandSide, float f, float f2) {
        int i = enumHandSide == EnumHandSide.RIGHT ? 1 : -1;
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179114_b(-102.25f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(i * 13.365f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(i * 78.05f, 0.0f, 0.0f, 1.0f);
        float fFunc_76126_a = MathHelper.func_76126_a(f2 * f2 * 3.1415927f);
        float fFunc_76126_a2 = MathHelper.func_76126_a(MathHelper.func_76129_c(f2) * 3.1415927f);
        GlStateManager.func_179114_b(fFunc_76126_a * (-35.0f), 0.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(fFunc_76126_a2 * 0.0f, 0.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(fFunc_76126_a2 * 20.0f, 1.0f, 1.0f, 1.0f);
    }

    private void tap(EnumHandSide enumHandSide, float f, float f2) {
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179109_b(0.0f, f * (-0.15f), 0.0f);
        GlStateManager.func_179114_b(45.0f, 0.0f, 1.0f, 0.0f);
        MathHelper.func_76126_a(f2 * f2 * 3.1415927f);
        MathHelper.func_76126_a(MathHelper.func_76129_c(f2) * 3.1415927f);
        GlStateManager.func_179114_b(((f2 * 0.8f) - ((f2 * f2) * 0.8f)) * (-90.0f), 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179152_a(0.37f, 0.37f, 0.37f);
    }

    private void rotateItemAnim() {
        if (((String) Animations.transformFirstPersonRotate.get()).equalsIgnoreCase("RotateY")) {
            GlStateManager.func_179114_b(this.delay, 0.0f, 1.0f, 0.0f);
        }
        if (((String) Animations.transformFirstPersonRotate.get()).equalsIgnoreCase("RotateXY")) {
            GlStateManager.func_179114_b(this.delay, 1.0f, 1.0f, 0.0f);
        }
        if (((String) Animations.transformFirstPersonRotate.get()).equalsIgnoreCase("Custom")) {
            GlStateManager.func_179114_b(this.delay, ((Float) Animations.customRotate1.get()).floatValue(), ((Float) Animations.customRotate2.get()).floatValue(), ((Float) Animations.customRotate3.get()).floatValue());
        }
        if (this.rotateTimer.hasTimePassed(1L)) {
            this.delay += 1.0f;
            this.delay += ((Float) Animations.SpeedRotate.get()).floatValue();
            this.rotateTimer.reset();
        }
        if (this.delay > 360.0f) {
            this.delay = 0.0f;
        }
    }

    private static void sigmaold(EnumHandSide enumHandSide, float f, float f2) {
        int i = enumHandSide == EnumHandSide.RIGHT ? 1 : -1;
        GlStateManager.func_179109_b(0.56f, -0.52f, -0.71999997f);
        GlStateManager.func_179109_b(0.0f, f * (-0.6f), 0.0f);
        GlStateManager.func_179114_b(-102.25f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(i * 13.365f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(i * 78.05f, 0.0f, 0.0f, 1.0f);
        float fFunc_76126_a = MathHelper.func_76126_a(f2 * f2 * 3.1415927f);
        float fFunc_76126_a2 = MathHelper.func_76126_a(MathHelper.func_76129_c(f2) * 3.1415927f);
        GlStateManager.func_179114_b(fFunc_76126_a * (-15.0f), 0.0f, 1.0f, 0.2f);
        GlStateManager.func_179114_b(fFunc_76126_a2 * (-10.0f), 0.2f, 0.1f, 1.0f);
        GlStateManager.func_179114_b(fFunc_76126_a2 * (-30.0f), 1.3f, 0.1f, 0.2f);
    }

    private static void WindMill(EnumHandSide enumHandSide, float f, float f2) {
        int i = enumHandSide == EnumHandSide.RIGHT ? 1 : -1;
        GlStateManager.func_179137_b(i * 0.56d, (-0.52d) + (f * (-0.6d)), -0.72d);
        GlStateManager.func_179137_b(i * (-0.1414214d), 0.08d, 0.1414214d);
        GlStateManager.func_179114_b(-102.25f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(i * 13.365f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(i * 78.05f, 0.0f, 0.0f, 1.0f);
        double dSin = Math.sin(f2 * f2 * 3.141592653589793d);
        double dSin2 = Math.sin(Math.sqrt(f2) * 3.141592653589793d);
        GlStateManager.func_179114_b((float) (dSin * (-20.0d)), 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b((float) (dSin2 * (-20.0d)), 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b((float) (dSin2 * (-50.0d)), 1.0f, 0.0f, 0.0f);
    }

    private static void Push(EnumHandSide enumHandSide, float f, float f2) {
        int i = enumHandSide == EnumHandSide.RIGHT ? 1 : -1;
        GlStateManager.func_179137_b(i * 0.56d, (-0.52d) + (f * (-0.6d)), -0.72d);
        GlStateManager.func_179137_b(i * (-0.1414214d), 0.08d, 0.1414214d);
        GlStateManager.func_179114_b(-102.25f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(i * 13.365f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(i * 78.05f, 0.0f, 0.0f, 1.0f);
        double dSin = Math.sin(f2 * f2 * 3.141592653589793d);
        double dSin2 = Math.sin(Math.sqrt(f2) * 3.141592653589793d);
        GlStateManager.func_179114_b((float) (dSin * (-10.0d)), 1.0f, 1.0f, 1.0f);
        GlStateManager.func_179114_b((float) (dSin2 * (-10.0d)), 1.0f, 1.0f, 1.0f);
        GlStateManager.func_179114_b((float) (dSin2 * (-10.0d)), 1.0f, 1.0f, 1.0f);
    }

    private static void transformSideFirstPersonBlock(EnumHandSide enumHandSide, float f, float f2) {
        int i = enumHandSide == EnumHandSide.RIGHT ? 1 : -1;
        GlStateManager.func_179137_b(i * 0.56d, (-0.52d) + (f * (-0.6d)), -0.72d);
        GlStateManager.func_179137_b(i * (-0.1414214d), 0.08d, 0.1414214d);
        GlStateManager.func_179114_b(-102.25f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(i * 13.365f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(i * 78.05f, 0.0f, 0.0f, 1.0f);
        double dSin = Math.sin(f2 * f2 * 3.141592653589793d);
        double dSin2 = Math.sin(Math.sqrt(f2) * 3.141592653589793d);
        GlStateManager.func_179114_b((float) (dSin * (-20.0d)), 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b((float) (dSin2 * (-20.0d)), 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b((float) (dSin2 * (-80.0d)), 1.0f, 0.0f, 0.0f);
    }

    private static void SmoothBlock(EnumHandSide enumHandSide, float f, float f2) {
        int i = enumHandSide == EnumHandSide.RIGHT ? 1 : -1;
        GlStateManager.func_179137_b(i * 0.56d, (-0.52d) + (f * (-0.6d)), -0.72d);
        GlStateManager.func_179137_b(i * (-0.1414214d), 0.08d, 0.1414214d);
        GlStateManager.func_179114_b(-102.25f, 1.0f, 0.0f, 0.0f);
        GlStateManager.func_179114_b(i * 13.365f, 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b(i * 78.05f, 0.0f, 0.0f, 1.0f);
        double dSin = Math.sin(f2 * f2 * 3.141592653589793d);
        double dSin2 = Math.sin(Math.sqrt(f2) * 3.141592653589793d);
        GlStateManager.func_179114_b((float) (dSin * (-20.0d)), 0.0f, 1.0f, 0.0f);
        GlStateManager.func_179114_b((float) (dSin2 * (-20.0d)), 0.0f, 0.0f, 1.0f);
        GlStateManager.func_179114_b((float) (dSin2 * (-30.0d)), 1.0f, 0.0f, 0.0f);
    }

    @Inject(method = {"renderFireInFirstPerson"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void renderFireInFirstPerson(CallbackInfo callbackInfo) {
        AntiBlind antiBlind = (AntiBlind) LiquidBounce.moduleManager.getModule(AntiBlind.class);
        if (!antiBlind.getState() || !((Boolean) antiBlind.getFireEffect().get()).booleanValue()) {
            return;
        }
        callbackInfo.cancel();
    }

    @Overwrite
    public void func_78441_a() {
        this.field_187470_g = this.field_187469_f;
        this.field_187472_i = this.field_187471_h;
        EntityPlayerSP entityPlayerSP = this.field_78455_a.field_71439_g;
        ItemStack itemStackFunc_184614_ca = entityPlayerSP.func_184614_ca();
        ItemStack itemStackFunc_184592_cb = entityPlayerSP.func_184592_cb();
        if (entityPlayerSP.func_184838_M()) {
            this.field_187469_f = MathHelper.func_76131_a(this.field_187469_f - 0.4f, 0.0f, 1.0f);
            this.field_187471_h = MathHelper.func_76131_a(this.field_187471_h - 0.4f, 0.0f, 1.0f);
        } else {
            float fFunc_184825_o = entityPlayerSP.func_184825_o(1.0f);
            boolean zShouldCauseReequipAnimation = ForgeHooksClient.shouldCauseReequipAnimation(this.field_187467_d, itemStackFunc_184614_ca, entityPlayerSP.field_71071_by.field_70461_c);
            boolean zShouldCauseReequipAnimation2 = ForgeHooksClient.shouldCauseReequipAnimation(this.field_187468_e, itemStackFunc_184592_cb, -1);
            if (!zShouldCauseReequipAnimation && !Objects.equals(this.field_187467_d, itemStackFunc_184614_ca)) {
                this.field_187467_d = itemStackFunc_184614_ca;
            }
            if (!zShouldCauseReequipAnimation && !Objects.equals(this.field_187468_e, itemStackFunc_184592_cb)) {
                this.field_187468_e = itemStackFunc_184592_cb;
            }
            this.field_187469_f += MathHelper.func_76131_a((!zShouldCauseReequipAnimation ? ((Boolean) Animations.oldSPValue.get()).booleanValue() ? 1.0f : (fFunc_184825_o * fFunc_184825_o) * fFunc_184825_o : 0.0f) - this.field_187469_f, -0.4f, 0.4f);
            this.field_187471_h += MathHelper.func_76131_a((!zShouldCauseReequipAnimation2 ? 1 : 0) - this.field_187471_h, -0.4f, 0.4f);
        }
        if (this.field_187469_f < 0.1f) {
            this.field_187467_d = itemStackFunc_184614_ca;
        }
        if (this.field_187471_h < 0.1f) {
            this.field_187468_e = itemStackFunc_184592_cb;
        }
    }
}
