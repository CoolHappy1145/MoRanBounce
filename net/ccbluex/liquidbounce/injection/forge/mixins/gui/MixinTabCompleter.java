package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.util.Comparator;
import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.minecraft.util.TabCompleter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({TabCompleter.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinTabCompleter.class */
public abstract class MixinTabCompleter {

    @Shadow
    protected List field_186849_f;

    @Shadow
    protected boolean field_186847_d;

    @Shadow
    protected boolean field_186846_c;

    @Shadow
    public abstract void func_186840_a(String[] strArr);

    @Inject(method = {"complete"}, m59at = {@InterfaceC0563At("HEAD")})
    private void complete(CallbackInfo callbackInfo) {
        this.field_186849_f.sort(Comparator.comparing(MixinTabCompleter::lambda$complete$0));
    }

    private static Boolean lambda$complete$0(String str) {
        return Boolean.valueOf(!LiquidBounce.fileManager.friendsConfig.isFriend(str));
    }

    @Inject(method = {"requestCompletions"}, m59at = {@InterfaceC0563At("HEAD")}, cancellable = true)
    private void handleClientCommandCompletion(String str, CallbackInfo callbackInfo) {
        if (LiquidBounce.commandManager.autoComplete(str)) {
            this.field_186847_d = true;
            String[] latestAutoComplete = LiquidBounce.commandManager.getLatestAutoComplete();
            if (str.toLowerCase().endsWith(latestAutoComplete[latestAutoComplete.length - 1].toLowerCase())) {
                return;
            }
            func_186840_a(latestAutoComplete);
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"setCompletions"}, m59at = {@InterfaceC0563At(value = "INVOKE", target = "Lnet/minecraft/util/TabCompleter;complete()V", shift = InterfaceC0563At.Shift.BEFORE)}, cancellable = true)
    private void onAutocompleteResponse(String[] strArr, CallbackInfo callbackInfo) {
        if (LiquidBounce.commandManager.getLatestAutoComplete().length != 0) {
            callbackInfo.cancel();
        }
    }
}
