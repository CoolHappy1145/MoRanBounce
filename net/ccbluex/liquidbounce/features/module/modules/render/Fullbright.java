package net.ccbluex.liquidbounce.features.module.modules.render;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.settings.IGameSettings;
import net.ccbluex.liquidbounce.api.minecraft.potion.PotionType;
import net.ccbluex.liquidbounce.event.ClientShutdownEvent;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0007\u001a\u00020\bH\u0016J\b\u0010\t\u001a\u00020\bH\u0016J\u0012\u0010\n\u001a\u00020\b2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007J\u0012\u0010\r\u001a\u00020\b2\b\u0010\u000b\u001a\u0004\u0018\u00010\u000eH\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u000f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/render/Fullbright;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "modeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "prevGamma", "", "onDisable", "", "onEnable", "onShutdown", "event", "Lnet/ccbluex/liquidbounce/event/ClientShutdownEvent;", "onUpdate", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "Fullbright", description = "Brightens up the world around you.", category = ModuleCategory.RENDER)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/render/Fullbright.class */
public final class Fullbright extends Module {
    private final ListValue modeValue = new ListValue("Mode", new String[]{"Gamma", "NightVision"}, "Gamma");
    private float prevGamma = -1.0f;

    public void onEnable() {
        this.prevGamma = MinecraftInstance.f157mc.getGameSettings().getGammaSetting();
    }

    public void onDisable() {
        if (this.prevGamma == -1.0f) {
            return;
        }
        MinecraftInstance.f157mc.getGameSettings().setGammaSetting(this.prevGamma);
        this.prevGamma = -1.0f;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer != null) {
            thePlayer.removePotionEffectClient(MinecraftInstance.classProvider.getPotionEnum(PotionType.NIGHT_VISION).getId());
        }
    }

    @EventTarget(ignoreCondition = true)
    public final void onUpdate(@Nullable UpdateEvent updateEvent) {
        if (!getState()) {
            Module module = LiquidBounce.INSTANCE.getModuleManager().getModule(XRay.class);
            if (module == null) {
                Intrinsics.throwNpe();
            }
            if (!module.getState()) {
                if (this.prevGamma != -1.0f) {
                    MinecraftInstance.f157mc.getGameSettings().setGammaSetting(this.prevGamma);
                    this.prevGamma = -1.0f;
                    return;
                }
                return;
            }
        }
        String str = (String) this.modeValue.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -820818432:
                if (lowerCase.equals("nightvision")) {
                    IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
                    if (thePlayer != null) {
                        thePlayer.addPotionEffect(MinecraftInstance.classProvider.createPotionEffect(MinecraftInstance.classProvider.getPotionEnum(PotionType.NIGHT_VISION).getId(), 1337, 1));
                        return;
                    }
                    return;
                }
                return;
            case 98120615:
                if (!lowerCase.equals("gamma") || MinecraftInstance.f157mc.getGameSettings().getGammaSetting() > 100.0f) {
                    return;
                }
                IGameSettings gameSettings = MinecraftInstance.f157mc.getGameSettings();
                gameSettings.setGammaSetting(gameSettings.getGammaSetting() + 1.0f);
                return;
            default:
                return;
        }
    }

    @EventTarget(ignoreCondition = true)
    public final void onShutdown(@Nullable ClientShutdownEvent clientShutdownEvent) {
        onDisable();
    }
}
