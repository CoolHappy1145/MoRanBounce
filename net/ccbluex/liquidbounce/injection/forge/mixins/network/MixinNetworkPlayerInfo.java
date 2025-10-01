package net.ccbluex.liquidbounce.injection.forge.mixins.network;

import com.mojang.authlib.GameProfile;
import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.DefaultPlayerSkin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({NetworkPlayerInfo.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/network/MixinNetworkPlayerInfo.class */
public class MixinNetworkPlayerInfo {

    @Shadow
    @Final
    private GameProfile field_178867_a;

    @Inject(method = {"getLocationSkin"}, cancellable = true, m59at = {@InterfaceC0563At("HEAD")})
    private void injectSkinProtect(CallbackInfoReturnable callbackInfoReturnable) {
        NameProtect nameProtect = (NameProtect) LiquidBounce.moduleManager.getModule(NameProtect.class);
        if (nameProtect.getState() && ((Boolean) nameProtect.skinProtectValue.get()).booleanValue()) {
            if (((Boolean) nameProtect.allPlayersValue.get()).booleanValue() || Objects.equals(this.field_178867_a.getId(), Minecraft.func_71410_x().func_110432_I().func_148256_e().getId())) {
                callbackInfoReturnable.setReturnValue(DefaultPlayerSkin.func_177334_a(this.field_178867_a.getId()));
                callbackInfoReturnable.cancel();
            }
        }
    }
}
