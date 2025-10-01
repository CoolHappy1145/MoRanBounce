package net.ccbluex.liquidbounce.injection.forge.mixins.entity;

import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.cape.CapeInfo;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.ccbluex.liquidbounce.features.module.modules.render.Cape;
import net.ccbluex.liquidbounce.features.module.modules.render.NoFOV;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.init.Items;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({AbstractClientPlayer.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/entity/MixinAbstractClientPlayer.class */
public abstract class MixinAbstractClientPlayer extends MixinEntityPlayer {
    private CapeInfo capeInfo;

    @Inject(method = {"getLocationCape"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void getCape(CallbackInfoReturnable callbackInfoReturnable) {
        Cape cape = (Cape) LiquidBounce.moduleManager.getModule(Cape.class);
        if (cape.getState() && Objects.equals(func_146103_bH().getName(), Minecraft.func_71410_x().field_71439_g.func_146103_bH().getName())) {
            callbackInfoReturnable.setReturnValue(cape.getCapeLocation((String) cape.getStyleValue().get()));
        }
    }

    @Inject(method = {"getFovModifier"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void getFovModifier(CallbackInfoReturnable callbackInfoReturnable) {
        NoFOV noFOV = (NoFOV) LiquidBounce.moduleManager.getModule(NoFOV.class);
        if (((NoFOV) Objects.requireNonNull(noFOV)).getState()) {
            float fFloatValue = ((Float) noFOV.getFovValue().get()).floatValue();
            if (!func_184587_cr()) {
                callbackInfoReturnable.setReturnValue(Float.valueOf(fFloatValue));
            } else if (func_184607_cu().func_77973_b() != Items.field_151031_f) {
                callbackInfoReturnable.setReturnValue(Float.valueOf(fFloatValue));
            } else {
                float fFunc_184605_cv = func_184605_cv() / 20.0f;
                callbackInfoReturnable.setReturnValue(Float.valueOf(fFloatValue * (1.0f - ((fFunc_184605_cv > 1.0f ? 1.0f : fFunc_184605_cv * fFunc_184605_cv) * 0.15f))));
            }
        }
    }

    @Inject(method = {"getLocationSkin()Lnet/minecraft/util/ResourceLocation;"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void getSkin(CallbackInfoReturnable callbackInfoReturnable) {
        NameProtect nameProtect = (NameProtect) LiquidBounce.moduleManager.getModule(NameProtect.class);
        if (((NameProtect) Objects.requireNonNull(nameProtect)).getState() && ((Boolean) nameProtect.skinProtectValue.get()).booleanValue()) {
            if (!((Boolean) nameProtect.allPlayersValue.get()).booleanValue() && !Objects.equals(func_146103_bH().getName(), Minecraft.func_71410_x().field_71439_g.func_146103_bH().getName())) {
                return;
            }
            callbackInfoReturnable.setReturnValue(DefaultPlayerSkin.func_177334_a(func_110124_au()));
        }
    }
}
