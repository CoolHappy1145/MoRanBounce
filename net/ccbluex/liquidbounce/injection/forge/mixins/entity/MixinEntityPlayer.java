package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.util.FoodStats;
import net.minecraft.util.SoundEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({EntityPlayer.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/entity/MixinEntityPlayer.class */
public abstract class MixinEntityPlayer extends MixinEntityLivingBase {

    @Shadow
    @Final
    protected static DataParameter field_184828_bq;

    @Shadow
    public PlayerCapabilities field_71075_bZ;

    @Shadow
    protected int field_71101_bC;

    @Shadow
    public abstract ItemStack func_184582_a(EntityEquipmentSlot entityEquipmentSlot);

    @Shadow
    public abstract GameProfile func_146103_bH();

    @Override // net.ccbluex.liquidbounce.injection.forge.mixins.entity.MixinEntity
    @Shadow
    protected abstract boolean func_70041_e_();

    @Override // net.ccbluex.liquidbounce.injection.forge.mixins.entity.MixinEntity
    @Shadow
    protected abstract SoundEvent func_184184_Z();

    @Shadow
    public abstract FoodStats func_71024_bL();
}
