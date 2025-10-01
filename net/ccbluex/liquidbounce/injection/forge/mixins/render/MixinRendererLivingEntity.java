package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import co.p000uk.hexeption.utils.OutlineUtils;
import java.awt.Color;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.render.Chams;
import net.ccbluex.liquidbounce.features.module.modules.render.ESP;
import net.ccbluex.liquidbounce.features.module.modules.render.NameTags;
import net.ccbluex.liquidbounce.features.module.modules.render.TrueSight;
import net.ccbluex.liquidbounce.injection.backend.EntityLivingBaseImplKt;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.EntityUtils;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({RenderLivingBase.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinRendererLivingEntity.class */
public abstract class MixinRendererLivingEntity extends MixinRender {

    @Shadow
    protected ModelBase field_77045_g;

    @Inject(method = {"doRender"}, m59at = {@InterfaceC0563At("HEAD")})
    private void injectChamsPre(EntityLivingBase entityLivingBase, double d, double d2, double d3, float f, float f2, CallbackInfo callbackInfo) {
        Chams chams = (Chams) LiquidBounce.moduleManager.getModule(Chams.class);
        if (chams.getState() && ((Boolean) chams.getTargetsValue().get()).booleanValue() && EntityUtils.isSelected(EntityLivingBaseImplKt.wrap(entityLivingBase), false)) {
            GL11.glEnable(32823);
            GL11.glPolygonOffset(1.0f, -1000000.0f);
        }
    }

    @Inject(method = {"doRender"}, m59at = {@InterfaceC0563At("RETURN")})
    private void injectChamsPost(EntityLivingBase entityLivingBase, double d, double d2, double d3, float f, float f2, CallbackInfo callbackInfo) {
        Chams chams = (Chams) LiquidBounce.moduleManager.getModule(Chams.class);
        if (chams.getState() && ((Boolean) chams.getTargetsValue().get()).booleanValue() && EntityUtils.isSelected(EntityLivingBaseImplKt.wrap(entityLivingBase), false)) {
            GL11.glPolygonOffset(1.0f, 1000000.0f);
            GL11.glDisable(32823);
        }
    }

    @Inject(method = {"canRenderName"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void canRenderName(EntityLivingBase entityLivingBase, CallbackInfoReturnable callbackInfoReturnable) {
        if (!ESP.renderNameTags || (LiquidBounce.moduleManager.getModule(NameTags.class).getState() && EntityUtils.isSelected(EntityLivingBaseImplKt.wrap(entityLivingBase), false))) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Overwrite
    protected void func_77036_a(EntityLivingBase entityLivingBase, float f, float f2, float f3, float f4, float f5, float f6) {
        boolean z = !entityLivingBase.func_82150_aj();
        TrueSight trueSight = (TrueSight) LiquidBounce.moduleManager.getModule(TrueSight.class);
        boolean z2 = !z && (!entityLivingBase.func_98034_c(Minecraft.func_71410_x().field_71439_g) || (trueSight.getState() && ((Boolean) trueSight.getEntitiesValue().get()).booleanValue()));
        if ((!z && !z2) || !func_180548_c(entityLivingBase)) {
            return;
        }
        if (z2) {
            GlStateManager.func_179094_E();
            GlStateManager.func_179131_c(1.0f, 1.0f, 1.0f, 0.15f);
            GlStateManager.func_179132_a(false);
            GL11.glEnable(SGL.GL_BLEND);
            GlStateManager.func_179112_b(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
            GlStateManager.func_179092_a(516, 0.003921569f);
        }
        ESP esp = (ESP) LiquidBounce.moduleManager.getModule(ESP.class);
        if (esp.getState() && EntityUtils.isSelected(EntityLivingBaseImplKt.wrap(entityLivingBase), false)) {
            Minecraft minecraftFunc_71410_x = Minecraft.func_71410_x();
            boolean z3 = minecraftFunc_71410_x.field_71474_y.field_74347_j;
            minecraftFunc_71410_x.field_71474_y.field_74347_j = false;
            float f7 = minecraftFunc_71410_x.field_71474_y.field_74333_Y;
            minecraftFunc_71410_x.field_71474_y.field_74333_Y = 100000.0f;
            switch (((String) esp.modeValue.get()).toLowerCase()) {
                case "wireframe":
                    GL11.glPushMatrix();
                    GL11.glPushAttrib(1048575);
                    GL11.glPolygonMode(1032, 6913);
                    GL11.glDisable(SGL.GL_TEXTURE_2D);
                    GL11.glDisable(2896);
                    GL11.glDisable(SGL.GL_DEPTH_TEST);
                    GL11.glEnable(SGL.GL_LINE_SMOOTH);
                    GL11.glEnable(SGL.GL_BLEND);
                    GL11.glBlendFunc(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA);
                    RenderUtils.glColor(esp.getColor(EntityLivingBaseImplKt.wrap(entityLivingBase)));
                    GL11.glLineWidth(((Float) esp.wireframeWidth.get()).floatValue());
                    this.field_77045_g.func_78088_a(entityLivingBase, f, f2, f3, f4, f5, f6);
                    GL11.glPopAttrib();
                    GL11.glPopMatrix();
                    break;
                case "outline":
                    ClientUtils.disableFastRender();
                    GlStateManager.func_179117_G();
                    Color color = esp.getColor(EntityLivingBaseImplKt.wrap(entityLivingBase));
                    OutlineUtils.setColor(color);
                    OutlineUtils.renderOne(((Float) esp.outlineWidth.get()).floatValue());
                    this.field_77045_g.func_78088_a(entityLivingBase, f, f2, f3, f4, f5, f6);
                    OutlineUtils.setColor(color);
                    OutlineUtils.renderTwo();
                    this.field_77045_g.func_78088_a(entityLivingBase, f, f2, f3, f4, f5, f6);
                    OutlineUtils.setColor(color);
                    OutlineUtils.renderThree();
                    this.field_77045_g.func_78088_a(entityLivingBase, f, f2, f3, f4, f5, f6);
                    OutlineUtils.setColor(color);
                    OutlineUtils.renderFour(color);
                    this.field_77045_g.func_78088_a(entityLivingBase, f, f2, f3, f4, f5, f6);
                    OutlineUtils.setColor(color);
                    OutlineUtils.renderFive();
                    OutlineUtils.setColor(Color.WHITE);
                    break;
            }
            minecraftFunc_71410_x.field_71474_y.field_74347_j = z3;
            minecraftFunc_71410_x.field_71474_y.field_74333_Y = f7;
        }
        this.field_77045_g.func_78088_a(entityLivingBase, f, f2, f3, f4, f5, f6);
        if (z2) {
            GlStateManager.func_179084_k();
            GlStateManager.func_179092_a(516, 0.1f);
            GlStateManager.func_179121_F();
            GlStateManager.func_179132_a(true);
        }
    }
}
