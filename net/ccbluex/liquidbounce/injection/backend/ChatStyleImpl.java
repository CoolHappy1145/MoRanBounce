package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.event.IClickEvent;
import net.ccbluex.liquidbounce.api.minecraft.util.IChatStyle;
import net.ccbluex.liquidbounce.api.minecraft.util.WEnumChatFormatting;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd0\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u001a\u001a\u00020\u00122\b\u0010\u001b\u001a\u0004\u0018\u00010\u001cH\u0096\u0002R(\u0010\u0007\u001a\u0004\u0018\u00010\u00062\b\u0010\u0005\u001a\u0004\u0018\u00010\u00068V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR(\u0010\r\u001a\u0004\u0018\u00010\f2\b\u0010\u0005\u001a\u0004\u0018\u00010\f8V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R$\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0005\u001a\u00020\u00128V@VX\u0096\u000e\u00a2\u0006\f\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001d"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ChatStyleImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/util/IChatStyle;", "wrapped", "Lnet/minecraft/util/text/Style;", "(Lnet/minecraft/util/text/Style;)V", PropertyDescriptor.VALUE, "Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent;", "chatClickEvent", "getChatClickEvent", "()Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent;", "setChatClickEvent", "(Lnet/ccbluex/liquidbounce/api/minecraft/event/IClickEvent;)V", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WEnumChatFormatting;", "color", "getColor", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WEnumChatFormatting;", "setColor", "(Lnet/ccbluex/liquidbounce/api/minecraft/util/WEnumChatFormatting;)V", "", "underlined", "getUnderlined", "()Z", "setUnderlined", "(Z)V", "getWrapped", "()Lnet/minecraft/util/text/Style;", "equals", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ChatStyleImpl.class */
public final class ChatStyleImpl implements IChatStyle {

    @NotNull
    private final Style wrapped;

    @NotNull
    public final Style getWrapped() {
        return this.wrapped;
    }

    public ChatStyleImpl(@NotNull Style wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IChatStyle
    @Nullable
    public IClickEvent getChatClickEvent() {
        ClickEvent clickEventFunc_150235_h = this.wrapped.func_150235_h();
        if (clickEventFunc_150235_h != null) {
            return new ClickEventImpl(clickEventFunc_150235_h);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IChatStyle
    public void setChatClickEvent(@Nullable IClickEvent iClickEvent) {
        ClickEvent wrapped;
        Style style = this.wrapped;
        if (iClickEvent == null) {
            wrapped = null;
        } else {
            if (iClickEvent == null) {
                throw new TypeCastException("null cannot be cast to non-null type net.ccbluex.liquidbounce.injection.backend.ClickEventImpl");
            }
            style = style;
            wrapped = ((ClickEventImpl) iClickEvent).getWrapped();
        }
        style.func_150241_a(wrapped);
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IChatStyle
    public boolean getUnderlined() {
        return this.wrapped.func_150234_e();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IChatStyle
    public void setUnderlined(boolean z) {
        this.wrapped.func_150228_d(Boolean.valueOf(z));
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IChatStyle
    @Nullable
    public WEnumChatFormatting getColor() {
        TextFormatting textFormattingFunc_150215_a = this.wrapped.func_150215_a();
        if (textFormattingFunc_150215_a == null) {
            return null;
        }
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$3[textFormattingFunc_150215_a.ordinal()]) {
            case 1:
                return WEnumChatFormatting.BLACK;
            case 2:
                return WEnumChatFormatting.DARK_BLUE;
            case 3:
                return WEnumChatFormatting.DARK_GREEN;
            case 4:
                return WEnumChatFormatting.DARK_AQUA;
            case 5:
                return WEnumChatFormatting.DARK_RED;
            case 6:
                return WEnumChatFormatting.DARK_PURPLE;
            case 7:
                return WEnumChatFormatting.GOLD;
            case 8:
                return WEnumChatFormatting.GRAY;
            case 9:
                return WEnumChatFormatting.DARK_GRAY;
            case 10:
                return WEnumChatFormatting.BLUE;
            case 11:
                return WEnumChatFormatting.GREEN;
            case 12:
                return WEnumChatFormatting.AQUA;
            case CharacterType.ALNUM /* 13 */:
                return WEnumChatFormatting.RED;
            case 14:
                return WEnumChatFormatting.LIGHT_PURPLE;
            case OPCode.EXACTN_IC /* 15 */:
                return WEnumChatFormatting.YELLOW;
            case 16:
                return WEnumChatFormatting.WHITE;
            case OPCode.CCLASS_MB /* 17 */:
                return WEnumChatFormatting.OBFUSCATED;
            case OPCode.CCLASS_MIX /* 18 */:
                return WEnumChatFormatting.BOLD;
            case OPCode.CCLASS_NOT /* 19 */:
                return WEnumChatFormatting.STRIKETHROUGH;
            case 20:
                return WEnumChatFormatting.UNDERLINE;
            case OPCode.CCLASS_MIX_NOT /* 21 */:
                return WEnumChatFormatting.ITALIC;
            case OPCode.CCLASS_NODE /* 22 */:
                return WEnumChatFormatting.RESET;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.util.IChatStyle
    public void setColor(@Nullable WEnumChatFormatting wEnumChatFormatting) {
        TextFormatting textFormatting;
        TextFormatting textFormatting2;
        Style style = this.wrapped;
        if (wEnumChatFormatting != null) {
            switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$4[wEnumChatFormatting.ordinal()]) {
                case 1:
                    textFormatting2 = TextFormatting.BLACK;
                    break;
                case 2:
                    textFormatting2 = TextFormatting.DARK_BLUE;
                    break;
                case 3:
                    textFormatting2 = TextFormatting.DARK_GREEN;
                    break;
                case 4:
                    textFormatting2 = TextFormatting.DARK_AQUA;
                    break;
                case 5:
                    textFormatting2 = TextFormatting.DARK_RED;
                    break;
                case 6:
                    textFormatting2 = TextFormatting.DARK_PURPLE;
                    break;
                case 7:
                    textFormatting2 = TextFormatting.GOLD;
                    break;
                case 8:
                    textFormatting2 = TextFormatting.GRAY;
                    break;
                case 9:
                    textFormatting2 = TextFormatting.DARK_GRAY;
                    break;
                case 10:
                    textFormatting2 = TextFormatting.BLUE;
                    break;
                case 11:
                    textFormatting2 = TextFormatting.GREEN;
                    break;
                case 12:
                    textFormatting2 = TextFormatting.AQUA;
                    break;
                case CharacterType.ALNUM /* 13 */:
                    textFormatting2 = TextFormatting.RED;
                    break;
                case 14:
                    textFormatting2 = TextFormatting.LIGHT_PURPLE;
                    break;
                case OPCode.EXACTN_IC /* 15 */:
                    textFormatting2 = TextFormatting.YELLOW;
                    break;
                case 16:
                    textFormatting2 = TextFormatting.WHITE;
                    break;
                case OPCode.CCLASS_MB /* 17 */:
                    textFormatting2 = TextFormatting.OBFUSCATED;
                    break;
                case OPCode.CCLASS_MIX /* 18 */:
                    textFormatting2 = TextFormatting.BOLD;
                    break;
                case OPCode.CCLASS_NOT /* 19 */:
                    textFormatting2 = TextFormatting.STRIKETHROUGH;
                    break;
                case 20:
                    textFormatting2 = TextFormatting.UNDERLINE;
                    break;
                case OPCode.CCLASS_MIX_NOT /* 21 */:
                    textFormatting2 = TextFormatting.ITALIC;
                    break;
                case OPCode.CCLASS_NODE /* 22 */:
                    textFormatting2 = TextFormatting.RESET;
                    break;
                default:
                    throw new NoWhenBranchMatchedException();
            }
            TextFormatting textFormatting3 = textFormatting2;
            style = style;
            textFormatting = textFormatting3;
        } else {
            textFormatting = null;
        }
        style.func_150238_a(textFormatting);
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof ChatStyleImpl) && Intrinsics.areEqual(((ChatStyleImpl) obj).wrapped, this.wrapped);
    }
}
