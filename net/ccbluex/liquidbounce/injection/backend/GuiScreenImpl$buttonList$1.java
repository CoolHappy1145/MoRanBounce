package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.minecraft.client.gui.GuiButton;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u0014\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u0001\"\b\b\ufffd\ufffd\u0010\u0002*\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\b\u0006"}, m27d2 = {"<anonymous>", "Lnet/minecraft/client/gui/GuiButton;", "T", "Lnet/minecraft/client/gui/GuiScreen;", "p1", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "invoke"})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/GuiScreenImpl$buttonList$1.class */
final /* synthetic */ class GuiScreenImpl$buttonList$1 extends FunctionReference implements Function1 {
    public static final GuiScreenImpl$buttonList$1 INSTANCE = new GuiScreenImpl$buttonList$1();

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(GuiButtonImplKt.class, LiquidBounce.CLIENT_NAME);
    }

    GuiScreenImpl$buttonList$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public Object invoke(Object obj) {
        return invoke((IGuiButton) obj);
    }

    @NotNull
    public final GuiButton invoke(@NotNull IGuiButton p1) {
        Intrinsics.checkParameterIsNotNull(p1, "p1");
        return ((GuiButtonImpl) p1).getWrapped();
    }
}
