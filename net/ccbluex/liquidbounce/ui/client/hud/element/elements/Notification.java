package net.ccbluex.liquidbounce.p005ui.client.hud.element.elements;

import java.awt.Color;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.EaseUtils;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd8\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0010\u000b\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b\u0012\b\b\u0002\u0010\t\u001a\u00020\b\u00a2\u0006\u0002\u0010\nJ\u000e\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u00020\bR\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\fR\u001a\u0010\r\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012R\u001a\u0010\u0013\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0014\u0010\u0010\"\u0004\b\u0015\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\u000eX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0019\u0010\u0010\"\u0004\b\u001a\u0010\u0012R\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\bX\u0086D\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\"\u0010\fR\u001a\u0010#\u001a\u00020\bX\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b$\u0010\f\"\u0004\b%\u0010&R\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b'\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b(\u0010\u0017R\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b)\u0010*R\u0011\u0010+\u001a\u00020\b\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b,\u0010\f\u00a8\u00060"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification;", "", "title", "", "content", "type", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/NotifyType;", "time", "", "animeTime", "(Ljava/lang/String;Ljava/lang/String;Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/NotifyType;II)V", "getAnimeTime", "()I", "animeXTime", "", "getAnimeXTime", "()J", "setAnimeXTime", "(J)V", "animeYTime", "getAnimeYTime", "setAnimeYTime", "getContent", "()Ljava/lang/String;", "displayTime", "getDisplayTime", "setDisplayTime", "fadeState", "Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/FadeState;", "getFadeState", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/FadeState;", "setFadeState", "(Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/FadeState;)V", "height", "getHeight", "nowY", "getNowY", "setNowY", "(I)V", "getTime", "getTitle", "getType", "()Lnet/ccbluex/liquidbounce/ui/client/hud/element/elements/NotifyType;", "width", "getWidth", "drawNotification", "", "index", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification.class */
public final class Notification {
    private final int width;
    private final int height = 30;

    @NotNull
    private FadeState fadeState;
    private int nowY;
    private long displayTime;
    private long animeXTime;
    private long animeYTime;

    @NotNull
    private final String title;

    @NotNull
    private final String content;

    @NotNull
    private final NotifyType type;
    private final int time;
    private final int animeTime;

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 3)
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/hud/element/elements/Notification$WhenMappings.class */
    public final class WhenMappings {
        public static final int[] $EnumSwitchMapping$0 = new int[FadeState.values().length];

        static {
            $EnumSwitchMapping$0[FadeState.IN.ordinal()] = 1;
            $EnumSwitchMapping$0[FadeState.STAY.ordinal()] = 2;
            $EnumSwitchMapping$0[FadeState.OUT.ordinal()] = 3;
            $EnumSwitchMapping$0[FadeState.END.ordinal()] = 4;
        }
    }

    public Notification(@NotNull String title, @NotNull String content, @NotNull NotifyType type, int i, int i2) {
        Intrinsics.checkParameterIsNotNull(title, "title");
        Intrinsics.checkParameterIsNotNull(content, "content");
        Intrinsics.checkParameterIsNotNull(type, "type");
        this.title = title;
        this.content = content;
        this.type = type;
        this.time = i;
        this.animeTime = i2;
        this.width = RangesKt.coerceAtLeast(100, RangesKt.coerceAtLeast(Fonts.wqy36.getStringWidth(this.title), Fonts.wqy36.getStringWidth(this.content)) + 10);
        this.height = 30;
        this.fadeState = FadeState.IN;
        this.nowY = -this.height;
        this.displayTime = System.currentTimeMillis();
        this.animeXTime = System.currentTimeMillis();
        this.animeYTime = System.currentTimeMillis();
    }

    @NotNull
    public final String getTitle() {
        return this.title;
    }

    @NotNull
    public final String getContent() {
        return this.content;
    }

    @NotNull
    public final NotifyType getType() {
        return this.type;
    }

    public final int getTime() {
        return this.time;
    }

    public Notification(String str, String str2, NotifyType notifyType, int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, notifyType, (i3 & 8) != 0 ? 1500 : i, (i3 & 16) != 0 ? 500 : i2);
    }

    public final int getAnimeTime() {
        return this.animeTime;
    }

    public final int getWidth() {
        return this.width;
    }

    public final int getHeight() {
        return this.height;
    }

    @NotNull
    public final FadeState getFadeState() {
        return this.fadeState;
    }

    public final void setFadeState(@NotNull FadeState fadeState) {
        Intrinsics.checkParameterIsNotNull(fadeState, "<set-?>");
        this.fadeState = fadeState;
    }

    public final int getNowY() {
        return this.nowY;
    }

    public final void setNowY(int i) {
        this.nowY = i;
    }

    public final long getDisplayTime() {
        return this.displayTime;
    }

    public final void setDisplayTime(long j) {
        this.displayTime = j;
    }

    public final long getAnimeXTime() {
        return this.animeXTime;
    }

    public final void setAnimeXTime(long j) {
        this.animeXTime = j;
    }

    public final long getAnimeYTime() {
        return this.animeYTime;
    }

    public final void setAnimeYTime(long j) {
        this.animeYTime = j;
    }

    public final boolean drawNotification(int i) {
        double dEaseOutExpo;
        int i2 = (-(i + 1)) * this.height;
        long jCurrentTimeMillis = System.currentTimeMillis();
        IResourceLocation iResourceLocationCreateResourceLocation = MinecraftInstance.classProvider.createResourceLocation("liquidbounce/notification/" + this.type.name() + ".png");
        if (this.nowY != i2) {
            double d = (jCurrentTimeMillis - this.animeYTime) / this.animeTime;
            if (d > 1.0d) {
                this.nowY = i2;
                dEaseOutExpo = 1.0d;
            } else {
                dEaseOutExpo = EaseUtils.easeOutExpo(d);
            }
            GL11.glTranslated(0.0d, (i2 - this.nowY) * dEaseOutExpo, 0.0d);
        } else {
            this.animeYTime = jCurrentTimeMillis;
        }
        GL11.glTranslated(0.0d, this.nowY, 0.0d);
        double dEaseInExpo = (jCurrentTimeMillis - this.animeXTime) / this.animeTime;
        switch (WhenMappings.$EnumSwitchMapping$0[this.fadeState.ordinal()]) {
            case 1:
                if (dEaseInExpo > 1.0d) {
                    this.fadeState = FadeState.STAY;
                    this.animeXTime = jCurrentTimeMillis;
                    dEaseInExpo = 1.0d;
                }
                dEaseInExpo = EaseUtils.easeOutExpo(dEaseInExpo);
                break;
            case 2:
                dEaseInExpo = 1.0d;
                if (jCurrentTimeMillis - this.animeXTime > this.time) {
                    this.fadeState = FadeState.OUT;
                    this.animeXTime = jCurrentTimeMillis;
                    break;
                }
                break;
            case 3:
                if (dEaseInExpo > 1.0d) {
                    this.fadeState = FadeState.END;
                    this.animeXTime = jCurrentTimeMillis;
                    dEaseInExpo = 1.0d;
                }
                dEaseInExpo = 1.0d - EaseUtils.easeInExpo(dEaseInExpo);
                break;
            case 4:
                return true;
        }
        GL11.glTranslated(this.width - (this.width * dEaseInExpo), 0.0d, 0.0d);
        GL11.glTranslatef(-this.width, 0.0f, 0.0f);
        RenderUtils.drawShadow(-22, 0, this.width + 22, this.height);
        RenderUtils.drawRect(-22.0f, 0.0f, this.width, this.height, this.type.getRenderColor());
        RenderUtils.drawRect(-22.0f, 0.0f, this.width, this.height, new Color(0, 0, 0, 150));
        RenderUtils.drawRect(-22.0f, this.height - 2.0f, Math.max(this.width - (this.width * ((jCurrentTimeMillis - this.displayTime) / ((this.animeTime * 2.0f) + this.time))), -22.0f), this.height, this.type.getRenderColor());
        Fonts.Sfui35.drawString(this.title, 7.0f, 4.0f, -1);
        Fonts.wqy30.drawString(this.content, 7.0f, 17.0f, -1);
        RenderUtils.drawImage(iResourceLocationCreateResourceLocation, -19, 3, 22, 22);
        MinecraftInstance.classProvider.getGlStateManager().resetColor();
        return false;
    }
}
