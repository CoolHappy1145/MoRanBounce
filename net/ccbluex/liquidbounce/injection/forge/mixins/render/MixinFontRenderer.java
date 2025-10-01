package net.ccbluex.liquidbounce.injection.forge.mixins.render;

import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.TextEvent;
import net.ccbluex.liquidbounce.utils.render.shader.shaders.RainbowFontShader;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL20;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Debug(export = true, print = true)
@Mixin({FontRenderer.class})
@SideOnly(Side.CLIENT)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/render/MixinFontRenderer.class */
public class MixinFontRenderer {
    private boolean rainbowEnabled0 = false;
    private boolean rainbowEnabled1 = false;

    @Debug(print = true)
    @Inject(method = {"drawString(Ljava/lang/String;FFIZ)I"}, m59at = {@InterfaceC0563At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderString(Ljava/lang/String;FFIZ)I", ordinal = 0)}, require = 1, allow = 1)
    private void injectShadow1(String str, float f, float f2, int i, boolean z, CallbackInfoReturnable callbackInfoReturnable) {
        this.rainbowEnabled0 = RainbowFontShader.INSTANCE.isInUse();
        if (this.rainbowEnabled0) {
            GL20.glUseProgram(0);
        }
    }

    @Debug(print = true)
    @Inject(method = {"drawString(Ljava/lang/String;FFIZ)I"}, m59at = {@InterfaceC0563At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;renderString(Ljava/lang/String;FFIZ)I", ordinal = 1)}, require = 1, allow = 1)
    private void injectShadow2(String str, float f, float f2, int i, boolean z, CallbackInfoReturnable callbackInfoReturnable) {
        if (this.rainbowEnabled0) {
            GL20.glUseProgram(RainbowFontShader.INSTANCE.getProgramId());
        }
    }

    @Debug(print = true)
    @Inject(method = {"renderStringAtPos"}, m59at = {@InterfaceC0563At("HEAD")}, require = 1, allow = 1)
    private void injectRainbow5(String str, boolean z, CallbackInfo callbackInfo) {
        this.rainbowEnabled1 = RainbowFontShader.INSTANCE.isInUse();
    }

    @Debug(print = true)
    @Inject(method = {"renderStringAtPos"}, m59at = {@InterfaceC0563At("RETURN")}, require = 1, allow = 1)
    private void injectRainbow6(String str, boolean z, CallbackInfo callbackInfo) {
        if (this.rainbowEnabled1) {
            GL20.glUseProgram(RainbowFontShader.INSTANCE.getProgramId());
        }
    }

    @Debug(print = true)
    @Inject(method = {"renderStringAtPos"}, m59at = {@InterfaceC0563At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;setColor(FFFF)V", ordinal = 0)}, require = 1, allow = 1)
    private void injectRainbow3(String str, boolean z, CallbackInfo callbackInfo) {
        if (this.rainbowEnabled1) {
            GL20.glUseProgram(0);
        }
    }

    @Debug(print = true)
    @Inject(method = {"renderStringAtPos"}, m59at = {@InterfaceC0563At(value = "INVOKE", target = "Lnet/minecraft/client/gui/FontRenderer;setColor(FFFF)V", ordinal = 1)}, require = 1, allow = 1)
    private void injectRainbow4(String str, boolean z, CallbackInfo callbackInfo) {
        if (this.rainbowEnabled1) {
            GL20.glUseProgram(RainbowFontShader.INSTANCE.getProgramId());
        }
    }

    @ModifyVariable(method = {"renderString"}, m63at = @InterfaceC0563At("HEAD"), require = 1, ordinal = 0)
    private String renderString(String str) {
        if (str == null) {
            return null;
        }
        if (LiquidBounce.eventManager == null) {
            return str;
        }
        TextEvent textEvent = new TextEvent(str);
        LiquidBounce.eventManager.callEvent(textEvent);
        return textEvent.getText();
    }

    @ModifyVariable(method = {"getStringWidth"}, m63at = @InterfaceC0563At("HEAD"), require = 1, ordinal = 0)
    private String getStringWidth(String str) {
        if (str == null) {
            return null;
        }
        if (LiquidBounce.eventManager == null) {
            return str;
        }
        TextEvent textEvent = new TextEvent(str);
        LiquidBounce.eventManager.callEvent(textEvent);
        return textEvent.getText();
    }
}
