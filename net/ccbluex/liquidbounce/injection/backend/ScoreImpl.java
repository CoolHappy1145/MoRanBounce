package net.ccbluex.liquidbounce.injection.backend;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScore;
import net.minecraft.scoreboard.Score;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0096\u0002R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\u00020\n8VX\u0096\u0004\u00a2\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u0013"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ScoreImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/IScore;", "wrapped", "Lnet/minecraft/scoreboard/Score;", "(Lnet/minecraft/scoreboard/Score;)V", "playerName", "", "getPlayerName", "()Ljava/lang/String;", "scorePoints", "", "getScorePoints", "()I", "getWrapped", "()Lnet/minecraft/scoreboard/Score;", "equals", "", "other", "", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ScoreImpl.class */
public final class ScoreImpl implements IScore {

    @NotNull
    private final Score wrapped;

    @NotNull
    public final Score getWrapped() {
        return this.wrapped;
    }

    public ScoreImpl(@NotNull Score wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScore
    public int getScorePoints() {
        return this.wrapped.func_96652_c();
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScore
    @NotNull
    public String getPlayerName() {
        String strFunc_96653_e = this.wrapped.func_96653_e();
        Intrinsics.checkExpressionValueIsNotNull(strFunc_96653_e, "wrapped.playerName");
        return strFunc_96653_e;
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof ScoreImpl) && Intrinsics.areEqual(((ScoreImpl) obj).wrapped, this.wrapped);
    }
}
