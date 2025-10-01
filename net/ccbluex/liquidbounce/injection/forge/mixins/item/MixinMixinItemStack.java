package net.ccbluex.liquidbounce.injection.forge.mixins.item;

import net.ccbluex.liquidbounce.injection.implementations.IMixinItemStack;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ItemStack.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/item/MixinMixinItemStack.class */
public class MixinMixinItemStack implements IMixinItemStack {
    private long itemDelay;

    @Inject(method = {"<init>(Lnet/minecraft/item/Item;IILnet/minecraft/nbt/NBTTagCompound;)V"}, m59at = {@InterfaceC0563At("RETURN")})
    private void init(CallbackInfo callbackInfo) {
        this.itemDelay = System.currentTimeMillis();
    }

    @Override // net.ccbluex.liquidbounce.injection.implementations.IMixinItemStack
    public long getItemDelay() {
        return this.itemDelay;
    }
}
