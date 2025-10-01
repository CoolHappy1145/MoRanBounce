package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({LayerHeldItem.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinLayerHeldItem.class */
public abstract class MixinLayerHeldItem {

    @Shadow
    @Final
    protected RenderLivingBase field_177206_a;

    @Shadow
    protected abstract void func_188358_a(EntityLivingBase entityLivingBase, ItemStack itemStack, ItemCameraTransforms.TransformType transformType, EnumHandSide enumHandSide);

    @Overwrite
    public void func_177141_a(EntityLivingBase entityLivingBase, float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        boolean z = entityLivingBase.func_184591_cq() == EnumHandSide.RIGHT;
        ItemStack itemStackFunc_184592_cb = z ? entityLivingBase.func_184592_cb() : entityLivingBase.func_184614_ca();
        ItemStack itemStackFunc_184614_ca = z ? entityLivingBase.func_184614_ca() : entityLivingBase.func_184592_cb();
        if (!itemStackFunc_184592_cb.func_190926_b() || !itemStackFunc_184614_ca.func_190926_b()) {
            GlStateManager.func_179094_E();
            if (this.field_177206_a.func_177087_b().field_78091_s) {
                GlStateManager.func_179109_b(0.0f, 0.75f, 0.0f);
                GlStateManager.func_179152_a(0.5f, 0.5f, 0.5f);
            }
            func_188358_a(entityLivingBase, itemStackFunc_184614_ca, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
            func_188358_a(entityLivingBase, itemStackFunc_184592_cb, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
            GlStateManager.func_179121_F();
        }
    }
}
