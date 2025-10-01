package net.ccbluex.liquidbounce.p005ui.client.hud.element.elements;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.entity.IEntityPlayerSP;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IFontRenderer;
import net.ccbluex.liquidbounce.api.minecraft.client.multiplayer.IWorldClient;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScore;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScoreObjective;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.IScoreboard;
import net.ccbluex.liquidbounce.api.minecraft.scoreboard.ITeam;
import net.ccbluex.liquidbounce.api.minecraft.util.WEnumChatFormatting;
import net.ccbluex.liquidbounce.features.module.modules.render.NoScoreboard;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Border;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Element;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.ElementInfo;
import net.ccbluex.liquidbounce.p005ui.client.hud.element.Side;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.ColorUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import net.ccbluex.liquidbounce.value.BoolValue;
import net.ccbluex.liquidbounce.value.FontValue;
import net.ccbluex.liquidbounce.value.IntegerValue;
import net.ccbluex.liquidbounce.value.ListValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@ElementInfo(name = "Scoreboard", force = true)
@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdN\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0010\u0007\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u00a2\u0006\u0002\u0010\tJ\b\u0010\u001d\u001a\u00020\u001eH\u0002J\n\u0010\u001f\u001a\u0004\u0018\u00010 H\u0016J\b\u0010!\u001a\u00020\u001eH\u0002R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\f\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\r\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000e\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0011\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0012\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0013\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0016\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0019\u001a\u00020\u0018X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001a\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001b\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u001c\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\""}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/ScoreboardElement;", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Element;", "x", "", "y", "scale", "", "side", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Side;", "(DDFLnet/ccbluex/liquidbounce/ui/client/hud/element/Side;)V", "backgroundColorAlphaValue", "Lnet/ccbluex/liquidbounce/value/IntegerValue;", "backgroundColorBlueValue", "backgroundColorGreenValue", "backgroundColorRedValue", "fontValue", "Lnet/ccbluex/liquidbounce/value/FontValue;", "rectColorBlueAlpha", "rectColorBlueValue", "rectColorGreenValue", "rectColorModeValue", "Lnet/ccbluex/liquidbounce/value/ListValue;", "rectColorRedValue", "rectValue", "Lnet/ccbluex/liquidbounce/value/BoolValue;", "shadowValue", "textBlueValue", "textGreenValue", "textRedValue", "backgroundColor", "Ljava/awt/Color;", "drawElement", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/Border;", "textColor", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/ScoreboardElement.class */
public final class ScoreboardElement extends Element {
    private final IntegerValue textRedValue;
    private final IntegerValue textGreenValue;
    private final IntegerValue textBlueValue;
    private final IntegerValue backgroundColorRedValue;
    private final IntegerValue backgroundColorGreenValue;
    private final IntegerValue backgroundColorBlueValue;
    private final IntegerValue backgroundColorAlphaValue;
    private final BoolValue rectValue;
    private final ListValue rectColorModeValue;
    private final IntegerValue rectColorRedValue;
    private final IntegerValue rectColorGreenValue;
    private final IntegerValue rectColorBlueValue;
    private final IntegerValue rectColorBlueAlpha;
    private final BoolValue shadowValue;
    private final FontValue fontValue;

    public ScoreboardElement() {
        this(0.0d, 0.0d, 0.0f, null, 15, null);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ScoreboardElement(double d, double d2, float f, @NotNull Side side) {
        super(d, d2, f, side);
        Intrinsics.checkParameterIsNotNull(side, "side");
        this.textRedValue = new IntegerValue("Text-R", 255, 0, 255);
        this.textGreenValue = new IntegerValue("Text-G", 255, 0, 255);
        this.textBlueValue = new IntegerValue("Text-B", 255, 0, 255);
        this.backgroundColorRedValue = new IntegerValue("Background-R", 0, 0, 255);
        this.backgroundColorGreenValue = new IntegerValue("Background-G", 0, 0, 255);
        this.backgroundColorBlueValue = new IntegerValue("Background-B", 0, 0, 255);
        this.backgroundColorAlphaValue = new IntegerValue("Background-Alpha", 95, 0, 255);
        this.rectValue = new BoolValue("Rect", false);
        this.rectColorModeValue = new ListValue("Rect-Color", new String[]{"Custom", "Rainbow"}, "Custom");
        this.rectColorRedValue = new IntegerValue("Rect-R", 0, 0, 255);
        this.rectColorGreenValue = new IntegerValue("Rect-G", 111, 0, 255);
        this.rectColorBlueValue = new IntegerValue("Rect-B", 255, 0, 255);
        this.rectColorBlueAlpha = new IntegerValue("Rect-Alpha", 255, 0, 255);
        this.shadowValue = new BoolValue("Shadow", false);
        IFontRenderer iFontRenderer = Fonts.minecraftFont;
        Intrinsics.checkExpressionValueIsNotNull(iFontRenderer, "Fonts.minecraftFont");
        this.fontValue = new FontValue("Font", iFontRenderer);
    }

    public ScoreboardElement(double d, double d2, float f, Side side, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? 5.0d : d, (i & 2) != 0 ? 0.0d : d2, (i & 4) != 0 ? 1.0f : f, (i & 8) != 0 ? new Side(Side.Horizontal.RIGHT, Side.Vertical.MIDDLE) : side);
    }

    @Override // net.ccbluex.liquidbounce.p005ui.client.hud.element.Element
    @Nullable
    public Border drawElement() {
        ArrayList arrayList;
        int colorIndex;
        if (NoScoreboard.INSTANCE.getState()) {
            return null;
        }
        IFontRenderer iFontRenderer = (IFontRenderer) this.fontValue.get();
        int rgb = textColor().getRGB();
        int rgb2 = backgroundColor().getRGB();
        String str = (String) this.rectColorModeValue.get();
        int rgb3 = new Color(((Number) this.rectColorRedValue.get()).intValue(), ((Number) this.rectColorGreenValue.get()).intValue(), ((Number) this.rectColorBlueValue.get()).intValue(), ((Number) this.rectColorBlueAlpha.get()).intValue()).getRGB();
        IWorldClient theWorld = MinecraftInstance.f157mc.getTheWorld();
        if (theWorld == null) {
            Intrinsics.throwNpe();
        }
        IScoreboard scoreboard = theWorld.getScoreboard();
        IScoreObjective objectiveInDisplaySlot = (IScoreObjective) null;
        IEntityPlayerSP thePlayer = MinecraftInstance.f157mc.getThePlayer();
        if (thePlayer == null) {
            Intrinsics.throwNpe();
        }
        ITeam playersTeam = scoreboard.getPlayersTeam(thePlayer.getName());
        if (playersTeam != null && (colorIndex = playersTeam.getChatFormat().getColorIndex()) >= 0) {
            objectiveInDisplaySlot = scoreboard.getObjectiveInDisplaySlot(3 + colorIndex);
        }
        IScoreObjective objectiveInDisplaySlot2 = objectiveInDisplaySlot;
        if (objectiveInDisplaySlot2 == null) {
            objectiveInDisplaySlot2 = scoreboard.getObjectiveInDisplaySlot(1);
        }
        if (objectiveInDisplaySlot2 == null) {
            return null;
        }
        IScoreObjective iScoreObjective = objectiveInDisplaySlot2;
        IScoreboard scoreboard2 = iScoreObjective.getScoreboard();
        Collection sortedScores = scoreboard2.getSortedScores(iScoreObjective);
        ArrayList scores = Lists.newArrayList(Iterables.filter(sortedScores, new Predicate() { // from class: net.ccbluex.liquidbounce.ui.client.hud.element.elements.ScoreboardElement$drawElement$scores$1
            public boolean apply(Object obj) {
                return apply((IScore) obj);
            }

            public final boolean apply(@Nullable IScore iScore) {
                return ((iScore != null ? iScore.getPlayerName() : null) == null || StringsKt.startsWith$default(iScore.getPlayerName(), "#", false, 2, (Object) null)) ? false : true;
            }
        }));
        if (scores.size() > 15) {
            ArrayList arrayListNewArrayList = Lists.newArrayList(Iterables.skip(scores, sortedScores.size() - 15));
            Intrinsics.checkExpressionValueIsNotNull(arrayListNewArrayList, "Lists.newArrayList(Itera\u2026oreCollection.size - 15))");
            arrayList = arrayListNewArrayList;
        } else {
            Intrinsics.checkExpressionValueIsNotNull(scores, "scores");
            arrayList = scores;
        }
        ArrayList arrayList2 = arrayList;
        int stringWidth = iFontRenderer.getStringWidth(iScoreObjective.getDisplayName());
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            IScore iScore = (IScore) it.next();
            stringWidth = RangesKt.coerceAtLeast(stringWidth, iFontRenderer.getStringWidth(MinecraftInstance.functions.scoreboardFormatPlayerName(scoreboard2.getPlayersTeam(iScore.getPlayerName()), iScore.getPlayerName()) + ": " + WEnumChatFormatting.RED + iScore.getScorePoints()));
        }
        int size = arrayList2.size() * iFontRenderer.getFontHeight();
        int i = ((-stringWidth) - 3) - (((Boolean) this.rectValue.get()).booleanValue() ? 3 : 0);
        RenderUtils.drawRect(i - 2, -2, 5, size + iFontRenderer.getFontHeight(), rgb2);
        int i2 = 0;
        for (Object obj : arrayList2) {
            int i3 = i2;
            i2++;
            if (i3 < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            IScore iScore2 = (IScore) obj;
            String strScoreboardFormatPlayerName = MinecraftInstance.functions.scoreboardFormatPlayerName(scoreboard2.getPlayersTeam(iScore2.getPlayerName()), iScore2.getPlayerName());
            String string = new StringBuilder().append(WEnumChatFormatting.RED).append(iScore2.getScorePoints()).toString();
            int i4 = ((Boolean) this.rectValue.get()).booleanValue() ? 4 : 0;
            float fontHeight = size - (i3 * iFontRenderer.getFontHeight());
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            iFontRenderer.drawString(strScoreboardFormatPlayerName, i, fontHeight, rgb, ((Boolean) this.shadowValue.get()).booleanValue());
            iFontRenderer.drawString(string, (5 - i4) - iFontRenderer.getStringWidth(string), fontHeight, rgb, ((Boolean) this.shadowValue.get()).booleanValue());
            if (i3 == arrayList2.size() - 1) {
                String displayName = iScoreObjective.getDisplayName();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                iFontRenderer.drawString(displayName, (i + (stringWidth / 2)) - (iFontRenderer.getStringWidth(displayName) / 2), fontHeight - iFontRenderer.getFontHeight(), rgb, ((Boolean) this.shadowValue.get()).booleanValue());
            }
            if (((Boolean) this.rectValue.get()).booleanValue()) {
                RenderUtils.drawRect(2.0f, i3 == arrayList2.size() - 1 ? -2.0f : fontHeight, 5.0f, i3 == 0 ? iFontRenderer.getFontHeight() : fontHeight + (iFontRenderer.getFontHeight() * 2.0f), StringsKt.equals(str, "Rainbow", true) ? ColorUtils.rainbow(400000000 * i3).getRGB() : rgb3);
            }
        }
        return new Border(((-stringWidth) - 5.0f) - (((Boolean) this.rectValue.get()).booleanValue() ? 3 : 0), -2.0f, 5.0f, size + iFontRenderer.getFontHeight());
    }

    private final Color backgroundColor() {
        return new Color(((Number) this.backgroundColorRedValue.get()).intValue(), ((Number) this.backgroundColorGreenValue.get()).intValue(), ((Number) this.backgroundColorBlueValue.get()).intValue(), ((Number) this.backgroundColorAlphaValue.get()).intValue());
    }

    private final Color textColor() {
        return new Color(((Number) this.textRedValue.get()).intValue(), ((Number) this.textGreenValue.get()).intValue(), ((Number) this.textBlueValue.get()).intValue());
    }
}
