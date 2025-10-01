package net.ccbluex.liquidbounce.injection.backend;

import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;
import jdk.nashorn.internal.runtime.regexp.joni.encoding.CharacterType;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam;
import net.ccbluex.liquidbounce.api.minecraft.util.WEnumChatFormatting;
import net.ccbluex.liquidbounce.injection.backend.utils.BackendExtentionsKt;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.text.TextFormatting;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0004\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0096\u0002J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0010H\u0016J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0001H\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\n\u00a8\u0006\u0014"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/TeamImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/ITeam;", "wrapped", "Lnet/minecraft/scoreboard/Team;", "(Lnet/minecraft/scoreboard/Team;)V", "chatFormat", "Lnet/ccbluex/liquidbounce/api/minecraft/util/WEnumChatFormatting;", "getChatFormat", "()Lnet/ccbluex/liquidbounce/api/minecraft/util/WEnumChatFormatting;", "getWrapped", "()Lnet/minecraft/scoreboard/Team;", "equals", "", "other", "", "formatString", "", "name", "isSameTeam", "team", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/TeamImpl.class */
public final class TeamImpl implements ITeam {

    @NotNull
    private final Team wrapped;

    @NotNull
    public final Team getWrapped() {
        return this.wrapped;
    }

    public TeamImpl(@NotNull Team wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam
    @NotNull
    public WEnumChatFormatting getChatFormat() {
        ScorePlayerTeam scorePlayerTeam = this.wrapped;
        if (scorePlayerTeam == null) {
            throw new TypeCastException("null cannot be cast to non-null type net.minecraft.scoreboard.ScorePlayerTeam");
        }
        TextFormatting textFormattingFunc_178775_l = scorePlayerTeam.func_178775_l();
        Intrinsics.checkExpressionValueIsNotNull(textFormattingFunc_178775_l, "(wrapped as ScorePlayerTeam).color");
        switch (BackendExtentionsKt.WhenMappings.$EnumSwitchMapping$3[textFormattingFunc_178775_l.ordinal()]) {
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

    @Override // net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam
    @NotNull
    public String formatString(@NotNull String name) {
        Intrinsics.checkParameterIsNotNull(name, "name");
        String strFunc_142053_d = this.wrapped.func_142053_d(name);
        Intrinsics.checkExpressionValueIsNotNull(strFunc_142053_d, "wrapped.formatString(name)");
        return strFunc_142053_d;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam
    public boolean isSameTeam(@NotNull ITeam team) {
        Intrinsics.checkParameterIsNotNull(team, "team");
        return this.wrapped.func_142054_a(((TeamImpl) team).getWrapped());
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof TeamImpl) && Intrinsics.areEqual(((TeamImpl) obj).wrapped, this.wrapped);
    }
}
