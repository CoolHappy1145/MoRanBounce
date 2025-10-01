package net.ccbluex.liquidbounce.injection.backend;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScore;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScoreObjective;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScoreboard;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam;
import net.ccbluex.liquidbounce.api.util.WrappedCollection;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdB\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0013\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\nH\u0096\u0002J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0014\u0010\u000f\u001a\u0004\u0018\u00010\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0016J\u0016\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010\u0016\u001a\u00020\fH\u0016R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0017"}, m27d2 = {"Lnet/ccbluex/liquidbounce/injection/backend/ScoreboardImpl;", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/IScoreboard;", "wrapped", "Lnet/minecraft/scoreboard/Scoreboard;", "(Lnet/minecraft/scoreboard/Scoreboard;)V", "getWrapped", "()Lnet/minecraft/scoreboard/Scoreboard;", "equals", "", "other", "", "getObjectiveInDisplaySlot", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/IScoreObjective;", "index", "", "getPlayersTeam", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/ITeam;", "name", "", "getSortedScores", "", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/IScore;", "objective", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ScoreboardImpl.class */
public final class ScoreboardImpl implements IScoreboard {

    @NotNull
    private final Scoreboard wrapped;

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/minecraft/scoreboard/Score;", "p1", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/IScore;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.ScoreboardImpl$getSortedScores$1 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ScoreboardImpl$getSortedScores$1.class */
    static final /* synthetic */ class C04491 extends FunctionReference implements Function1 {
        public static final C04491 INSTANCE = new C04491();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(ScoreImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04491() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((IScore) obj);
        }

        @NotNull
        public final Score invoke(@NotNull IScore p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return ((ScoreImpl) p1).getWrapped();
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3, m26d1 = {"\ufffd\ufffd\u000e\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\u0010\ufffd\ufffd\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\b\u0004"}, m27d2 = {"<anonymous>", "Lnet/ccbluex/liquidbounce/api/minecraft/scoreboard/IScore;", "p1", "Lnet/minecraft/scoreboard/Score;", "invoke"})
    /* renamed from: net.ccbluex.liquidbounce.injection.backend.ScoreboardImpl$getSortedScores$2 */
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/injection/backend/ScoreboardImpl$getSortedScores$2.class */
    static final /* synthetic */ class C04502 extends FunctionReference implements Function1 {
        public static final C04502 INSTANCE = new C04502();

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(ScoreImplKt.class, LiquidBounce.CLIENT_NAME);
        }

        C04502() {
            super(1);
        }

        @Override // kotlin.jvm.functions.Function1
        public Object invoke(Object obj) {
            return invoke((Score) obj);
        }

        @NotNull
        public final IScore invoke(@NotNull Score p1) {
            Intrinsics.checkParameterIsNotNull(p1, "p1");
            return new ScoreImpl(p1);
        }
    }

    @NotNull
    public final Scoreboard getWrapped() {
        return this.wrapped;
    }

    public ScoreboardImpl(@NotNull Scoreboard wrapped) {
        Intrinsics.checkParameterIsNotNull(wrapped, "wrapped");
        this.wrapped = wrapped;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScoreboard
    @Nullable
    public ITeam getPlayersTeam(@Nullable String str) {
        Team teamFunc_96509_i = this.wrapped.func_96509_i(str);
        if (teamFunc_96509_i != null) {
            return new TeamImpl(teamFunc_96509_i);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScoreboard
    @Nullable
    public IScoreObjective getObjectiveInDisplaySlot(int i) {
        ScoreObjective scoreObjectiveFunc_96539_a = this.wrapped.func_96539_a(i);
        if (scoreObjectiveFunc_96539_a != null) {
            return new ScoreObjectiveImpl(scoreObjectiveFunc_96539_a);
        }
        return null;
    }

    @Override // net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScoreboard
    @NotNull
    public Collection getSortedScores(@NotNull IScoreObjective objective) {
        Intrinsics.checkParameterIsNotNull(objective, "objective");
        return new WrappedCollection(this.wrapped.func_96534_i(((ScoreObjectiveImpl) objective).getWrapped()), C04491.INSTANCE, C04502.INSTANCE);
    }

    public boolean equals(@Nullable Object obj) {
        return (obj instanceof ScoreboardImpl) && Intrinsics.areEqual(((ScoreboardImpl) obj).wrapped, this.wrapped);
    }
}
