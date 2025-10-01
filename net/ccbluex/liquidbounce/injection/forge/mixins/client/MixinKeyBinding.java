package net.ccbluex.liquidbounce.injection.forge.mixins.client;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.movement.InventoryMove;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.IKeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({KeyBinding.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/client/MixinKeyBinding.class */
public abstract class MixinKeyBinding {

    @Shadow
    public boolean field_74513_e;

    @Shadow
    public abstract IKeyConflictContext getKeyConflictContext();

    @Shadow
    public abstract KeyModifier getKeyModifier();

    @Overwrite
    public boolean func_151470_d() {
        return ((InventoryMove) LiquidBounce.moduleManager.get(InventoryMove.class)).getState() ? this.field_74513_e : this.field_74513_e && getKeyConflictContext().isActive() && getKeyModifier().isActive(getKeyConflictContext());
    }
}
