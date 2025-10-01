package net.ccbluex.liquidbounce.injection.forge.mixins.gui;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin({GuiNewChat.class})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/forge/mixins/gui/MixinGuiNewChat.class */
public abstract class MixinGuiNewChat {

    @Shadow
    @Final
    private Minecraft field_146247_f;

    @Shadow
    @Final
    private List field_146253_i;

    @Shadow
    private int field_146250_j;

    @Shadow
    private boolean field_146251_k;

    @Shadow
    @Final
    private List field_146252_h;

    @Shadow
    public abstract int func_146232_i();

    @Shadow
    public abstract boolean func_146241_e();

    @Shadow
    public abstract float func_146244_h();

    @Shadow
    public abstract int func_146228_f();

    @Shadow
    public abstract void func_146242_c(int i);

    @Shadow
    public abstract void func_146229_b(int i);
}
