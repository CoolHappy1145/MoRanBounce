package net.ccbluex.liquidbounce.injection.forge.mixins.resources;

import com.mojang.authlib.GameProfile;
import java.util.HashMap;
import java.util.Objects;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.features.module.modules.misc.NameProtect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.SkinManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({SkinManager.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/resources/MixinSkinManager.class */
public class MixinSkinManager {
    @Inject(method = {"loadSkinFromCache"}, cancellable = true, m59at = {@InterfaceC0563At("HEAD")})
    private void injectSkinProtect(GameProfile gameProfile, CallbackInfoReturnable callbackInfoReturnable) {
        if (gameProfile == null) {
            return;
        }
        NameProtect nameProtect = (NameProtect) LiquidBounce.moduleManager.getModule(NameProtect.class);
        if (nameProtect.getState() && ((Boolean) nameProtect.skinProtectValue.get()).booleanValue()) {
            if (((Boolean) nameProtect.allPlayersValue.get()).booleanValue() || Objects.equals(gameProfile.getId(), Minecraft.func_71410_x().func_110432_I().func_148256_e().getId())) {
                callbackInfoReturnable.setReturnValue(new HashMap());
                callbackInfoReturnable.cancel();
            }
        }
    }
}
