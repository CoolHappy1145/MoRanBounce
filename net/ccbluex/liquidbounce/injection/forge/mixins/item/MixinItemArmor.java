package net.ccbluex.liquidbounce.injection.forge.mixins.item;

import net.ccbluex.liquidbounce.features.module.modules.misc.Teams;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({ItemArmor.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/item/MixinItemArmor.class */
public abstract class MixinItemArmor {

    @Shadow
    @Final
    private ItemArmor.ArmorMaterial field_77878_bZ;

    @Overwrite
    public int func_82814_b(ItemStack itemStack) {
        NBTTagCompound nBTTagCompoundFunc_74775_l;
        if (this.field_77878_bZ != ItemArmor.ArmorMaterial.LEATHER) {
            if (((Boolean) new Teams().getArmorColorValue().get()).booleanValue()) {
                return -1;
            }
            return 16777215;
        }
        NBTTagCompound nBTTagCompoundFunc_77978_p = itemStack.func_77978_p();
        if (nBTTagCompoundFunc_77978_p != null && (nBTTagCompoundFunc_74775_l = nBTTagCompoundFunc_77978_p.func_74775_l("display")) != null && nBTTagCompoundFunc_74775_l.func_150297_b("color", 3)) {
            return nBTTagCompoundFunc_74775_l.func_74762_e("color");
        }
        return 10511680;
    }
}
