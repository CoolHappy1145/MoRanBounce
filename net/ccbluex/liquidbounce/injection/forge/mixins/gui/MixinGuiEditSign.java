package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.awt.Color;
import java.util.List;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiEditSign;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.event.ClickEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.InterfaceC0563At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({GuiEditSign.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiEditSign.class */
public class MixinGuiEditSign extends GuiScreen {

    @Shadow
    private int field_146851_h;

    @Shadow
    @Final
    private TileEntitySign field_146848_f;

    @Shadow
    private GuiButton field_146852_i;
    private boolean enabled;
    private GuiButton toggleButton;
    private GuiTextField signCommand1;
    private GuiTextField signCommand2;
    private GuiTextField signCommand3;
    private GuiTextField signCommand4;

    @Inject(method = {"initGui"}, m59at = {@InterfaceC0563At("RETURN")})
    private void initGui(CallbackInfo callbackInfo) {
        List list = this.field_146292_n;
        GuiButton guiButton = new GuiButton(1, (this.field_146294_l / 2) - 100, (this.field_146295_m / 4) + 145, this.enabled ? "Disable Formatting codes" : "Enable Formatting codes");
        this.toggleButton = guiButton;
        list.add(guiButton);
        this.signCommand1 = new GuiTextField(0, this.field_146289_q, (this.field_146294_l / 2) - 100, this.field_146295_m - 15, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 10);
        this.signCommand2 = new GuiTextField(1, this.field_146289_q, (this.field_146294_l / 2) - 100, this.field_146295_m - 30, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 10);
        this.signCommand3 = new GuiTextField(2, this.field_146289_q, (this.field_146294_l / 2) - 100, this.field_146295_m - 45, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 10);
        this.signCommand4 = new GuiTextField(3, this.field_146289_q, (this.field_146294_l / 2) - 100, this.field_146295_m - 60, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 10);
        this.signCommand1.func_146180_a("");
        this.signCommand2.func_146180_a("");
        this.signCommand3.func_146180_a("");
        this.signCommand4.func_146180_a("");
    }

    @Inject(method = {"actionPerformed"}, m59at = {@InterfaceC0563At("HEAD")})
    private void actionPerformed(GuiButton guiButton, CallbackInfo callbackInfo) {
        switch (guiButton.field_146127_k) {
            case 0:
                if (!this.signCommand1.func_146179_b().isEmpty()) {
                    this.field_146848_f.field_145915_a[0].func_150255_a(new Style().func_150241_a(new ClickEvent(ClickEvent.Action.RUN_COMMAND, this.signCommand1.func_146179_b())));
                }
                if (!this.signCommand2.func_146179_b().isEmpty()) {
                    this.field_146848_f.field_145915_a[1].func_150255_a(new Style().func_150241_a(new ClickEvent(ClickEvent.Action.RUN_COMMAND, this.signCommand2.func_146179_b())));
                }
                if (!this.signCommand3.func_146179_b().isEmpty()) {
                    this.field_146848_f.field_145915_a[2].func_150255_a(new Style().func_150241_a(new ClickEvent(ClickEvent.Action.RUN_COMMAND, this.signCommand3.func_146179_b())));
                }
                if (!this.signCommand4.func_146179_b().isEmpty()) {
                    this.field_146848_f.field_145915_a[3].func_150255_a(new Style().func_150241_a(new ClickEvent(ClickEvent.Action.RUN_COMMAND, this.signCommand4.func_146179_b())));
                    break;
                }
                break;
            case 1:
                this.enabled = !this.enabled;
                this.toggleButton.field_146126_j = this.enabled ? "Disable Formatting codes" : "Enable Formatting codes";
                break;
        }
    }

    @Inject(method = {"drawScreen"}, m59at = {@InterfaceC0563At("RETURN")})
    private void drawFields(CallbackInfo callbackInfo) {
        this.field_146289_q.func_78276_b("\u00a7c\u00a7lCommands \u00a77(\u00a7f\u00a7l1.8\u00a77)", (this.field_146294_l / 2) - 100, this.field_146295_m - 75, Color.WHITE.getRGB());
        this.signCommand1.func_146194_f();
        this.signCommand2.func_146194_f();
        this.signCommand3.func_146194_f();
        this.signCommand4.func_146194_f();
    }

    protected void func_73864_a(int i, int i2, int i3) {
        this.signCommand1.func_146192_a(i, i2, i3);
        this.signCommand2.func_146192_a(i, i2, i3);
        this.signCommand3.func_146192_a(i, i2, i3);
        this.signCommand4.func_146192_a(i, i2, i3);
        super.func_73864_a(i, i2, i3);
    }

    @Overwrite
    protected void func_73869_a(char c, int i) {
        this.signCommand1.func_146201_a(c, i);
        this.signCommand2.func_146201_a(c, i);
        this.signCommand3.func_146201_a(c, i);
        this.signCommand4.func_146201_a(c, i);
        if (this.signCommand1.func_146206_l() || this.signCommand2.func_146206_l() || this.signCommand3.func_146206_l() || this.signCommand4.func_146206_l()) {
            return;
        }
        if (i == 200) {
            this.field_146851_h = (this.field_146851_h - 1) & 3;
        }
        if (i == 208 || i == 28 || i == 156) {
            this.field_146851_h = (this.field_146851_h + 1) & 3;
        }
        String strFunc_150260_c = this.field_146848_f.field_145915_a[this.field_146851_h].func_150260_c();
        if (i == 14 && strFunc_150260_c.length() > 0) {
            strFunc_150260_c = strFunc_150260_c.substring(0, strFunc_150260_c.length() - 1);
        }
        if ((ChatAllowedCharacters.func_71566_a(c) || (this.enabled && c == '\u00a7')) && this.field_146289_q.func_78256_a(strFunc_150260_c + c) <= 90) {
            strFunc_150260_c = strFunc_150260_c + c;
        }
        this.field_146848_f.field_145915_a[this.field_146851_h] = new TextComponentString(strFunc_150260_c);
        if (i == 1) {
            func_146284_a(this.field_146852_i);
        }
    }
}
