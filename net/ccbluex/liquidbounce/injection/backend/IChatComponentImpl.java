package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.IChatStyle;
import net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd6\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0001H\u0016J\u0010\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0015\u001a\u00020\nH\u0016J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0019H\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000e\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001a"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/IChatComponentImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IIChatComponent;", "wrapped", "Lnet/minecraft/util/text/ITextComponent;", "(Lnet/minecraft/util/text/ITextComponent;)V", "chatStyle", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IChatStyle;", "getChatStyle", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/IChatStyle;", "formattedText", "", "getFormattedText", "()Ljava/lang/String;", "unformattedText", "getUnformattedText", "getWrapped", "()Lnet/minecraft/util/text/ITextComponent;", "appendSibling", "", "component", "appendText", "text", "equals", "", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/IChatComponentImpl.class */
public final class IChatComponentImpl implements IIChatComponent {

    @NotNull
    private final ITextComponent wrapped;

    @NotNull
    public final ITextComponent getWrapped() {
        return this.wrapped;
    }

    public IChatComponentImpl(@NotNull ITextComponent wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent
    @NotNull
    public String getUnformattedText() {
        String strFunc_150260_c = this.wrapped.func_150260_c();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_150260_c, "wrapped.unformattedText");
        return strFunc_150260_c;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent
    @NotNull
    public IChatStyle getChatStyle() {
        Style styleFunc_150256_b = this.wrapped.func_150256_b();
        Intrinsics.checkExpressionValueIsNotNull(styleFunc_150256_b, "wrapped.style");
        return new ChatStyleImpl(styleFunc_150256_b);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent
    @NotNull
    public String getFormattedText() {
        String strFunc_150254_d = this.wrapped.func_150254_d();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_150254_d, "wrapped.formattedText");
        return strFunc_150254_d;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent
    public void appendText(@NotNull String text) {
        Intrinsics.checkParameterIsNotNull(text, "text");
        this.wrapped.func_150258_a(text);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IIChatComponent
    public void appendSibling(@NotNull IIChatComponent component) {
        Intrinsics.checkParameterIsNotNull(component, "component");
        this.wrapped.func_150257_a(((IChatComponentImpl) component).getWrapped());
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof IChatComponentImpl) && Intrinsics.areEqual(((IChatComponentImpl) obj).wrapped, this.wrapped);
    }
}
