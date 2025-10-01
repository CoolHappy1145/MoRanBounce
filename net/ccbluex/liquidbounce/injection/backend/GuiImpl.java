package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGui;
import net.minecraft.client.gui.Gui;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0012\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u0016\u0018\ufffd\ufffd*\b\b\ufffd\ufffd\u0010\u0001*\u00020\u00022\u00020\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00028\ufffd\ufffd\u00a2\u0006\u0002\u0010\u0005R\u0013\u0010\u0004\u001a\u00028\ufffd\ufffd\u00a2\u0006\n\n\u0002\u0010\b\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\t"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/GuiImpl;", "T", "Lnet/minecraft/client/gui/Gui;", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGui;", "wrapped", "(Lnet/minecraft/client/gui/Gui;)V", "getWrapped", "()Lnet/minecraft/client/gui/Gui;", "Lnet/minecraft/client/gui/Gui;", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/GuiImpl.class */
public class GuiImpl implements IGui {

    @NotNull
    private final Gui wrapped;

    @NotNull
    public final Gui getWrapped() {
        return this.wrapped;
    }

    public GuiImpl(@NotNull Gui wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }
}
