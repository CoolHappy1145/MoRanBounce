package net.ccbluex.liquidbounce.features.module.modules.misc;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.event.EventTarget;
import net.ccbluex.liquidbounce.event.UpdateEvent;
import net.ccbluex.liquidbounce.features.module.Module;
import net.ccbluex.liquidbounce.features.module.ModuleCategory;
import net.ccbluex.liquidbounce.features.module.ModuleInfo;
import net.ccbluex.liquidbounce.value.ListValue;
import net.ccbluex.liquidbounce.value.TextValue;
import org.apache.log4j.HTMLLayout;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.Display;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\b\u0007\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0019H\u0007R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001a\u0010\f\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u000e\u0010\u000f\u001a\u00020\u0010X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u001a\u0010\u0011\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0012\u0010\u0006\"\u0004\b\u0013\u0010\bR\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001a"}, m27d2 = {"Lnet/ccbluex/liquidbounce/features/module/modules/misc/CustomTitle;", "Lnet/ccbluex/liquidbounce/features/module/Module;", "()V", "H", "", "getH", "()I", "setH", "(I)V", "HM", "getHM", "setHM", "M", "getM", "setM", "ModeTitle", "Lnet/ccbluex/liquidbounce/value/ListValue;", "S", "getS", "setS", "TitleValue", "Lnet/ccbluex/liquidbounce/value/TextValue;", "onUpdate", "", "event", "Lnet/ccbluex/liquidbounce/event/UpdateEvent;", LiquidBounce.CLIENT_NAME})
@ModuleInfo(name = "CustomTitle", description = HTMLLayout.TITLE_OPTION, category = ModuleCategory.MISC)
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/features/module/modules/misc/CustomTitle.class */
public final class CustomTitle extends Module {
    private final TextValue TitleValue = new TextValue("SetTitle", "Faq\u65f6\u95f4\u5df2\u6d41\u901d\uff1a");
    private final ListValue ModeTitle = new ListValue("ModeTitle", new String[]{"Custom", "Mode1", "Mode2", "Mode3", "Mode4", "Mode5", "Mode6", "Mode7", "Mode8"}, "Custom");

    /* renamed from: S */
    private int f121S;

    /* renamed from: HM */
    private int f122HM;

    /* renamed from: M */
    private int f123M;

    /* renamed from: H */
    private int f124H;

    public final int getS() {
        return this.f121S;
    }

    public final void setS(int i) {
        this.f121S = i;
    }

    public final int getHM() {
        return this.f122HM;
    }

    public final void setHM(int i) {
        this.f122HM = i;
    }

    public final int getM() {
        return this.f123M;
    }

    public final void setM(int i) {
        this.f123M = i;
    }

    public final int getH() {
        return this.f124H;
    }

    public final void setH(int i) {
        this.f124H = i;
    }

    @EventTarget
    public final void onUpdate(@NotNull UpdateEvent event) {
        Intrinsics.checkParameterIsNotNull(event, "event");
        this.f122HM++;
        if (this.f122HM == 20) {
            this.f121S++;
            this.f122HM = 0;
        }
        if (this.f121S == 60) {
            this.f123M++;
            this.f121S = 0;
        }
        if (this.f123M == 60) {
            this.f124H++;
            this.f123M = 0;
        }
        String str = (String) this.ModeTitle.get();
        if (str == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        }
        String lowerCase = str.toLowerCase();
        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
        switch (lowerCase.hashCode()) {
            case -1349088399:
                if (lowerCase.equals("custom")) {
                    Display.setTitle(((String) this.TitleValue.get()) + this.f124H + "  \u65f6  " + this.f123M + "  \u5206  " + this.f121S + "  \u79d2  ");
                    return;
                }
                return;
            case 104069870:
                if (lowerCase.equals("mode1")) {
                    Display.setTitle("\u51b0\u51c9Sense || \u4eba\u54ea\u6709\u597d\u7684\uff0c\u53ea\u662f\u574f\u7684\u7a0b\u5ea6\u4e0d\u4e00\u6837\u800c\u5df2\u3002\u2014\u2014\u300a\u54c8\u5c14\u7684\u79fb\u52a8\u57ce\u5821\u300b || \u60a8\u5df2\u4f7f\u7528\uff1a" + this.f124H + "  \u65f6  " + this.f123M + "  \u5206  " + this.f121S + "  \u79d2  ");
                    return;
                }
                return;
            case 104069871:
                if (lowerCase.equals("mode2")) {
                    Display.setTitle("\u51b0\u51c9Sense || \u603b\u662f\u4f9d\u8d56\u522b\u4eba\u7684\u8bdd\uff0c\u5c31\u6c38\u8fdc\u957f\u4e0d\u5927\u3002\u2014\u2014\u300a\u54c6\u5566A\u68a6\u300b || \u60a8\u5df2\u4f7f\u7528\uff1a" + this.f124H + "  \u65f6  " + this.f123M + "  \u5206  " + this.f121S + "  \u79d2  ");
                    return;
                }
                return;
            case 104069872:
                if (lowerCase.equals("mode3")) {
                    Display.setTitle("\u51b0\u51c9Sense || \u6211\u4eec\u4f1a\u6b7b\u5f88\u4e45\uff0c\u6240\u4ee5\u6d3b\u7740\u7684\u65f6\u5019\u4e00\u5b9a\u8981\u5f00\u5fc3\u3002\u2014\u2014\u300a\u540d\u4fa6\u63a2\u67ef\u5357\u300b || \u60a8\u5df2\u4f7f\u7528\uff1a" + this.f124H + "  \u65f6  " + this.f123M + "  \u5206  " + this.f121S + "  \u79d2  ");
                    return;
                }
                return;
            case 104069873:
                if (lowerCase.equals("mode4")) {
                    Display.setTitle("\u51b0\u51c9Sense || \u6211\u60f3\u6210\u4e3a\u4e00\u4e2a\u6e29\u67d4\u7684\u4eba\uff0c\u56e0\u4e3a\u66fe\u88ab\u6e29\u67d4\u7684\u4eba\u90a3\u6837\u5bf9\u5f85\uff0c\u6df1\u6df1\u4e86\u89e3\u90a3\u79cd\u88ab\u6e29\u67d4\u76f8\u5f85\u7684\u611f\u89c9\u3002\u2014\u2014\u300a\u590f\u76ee\u53cb\u4eba\u5e10\u300b || \u60a8\u5df2\u4f7f\u7528\uff1a" + this.f124H + "  \u65f6  " + this.f123M + "  \u5206  " + this.f121S + "  \u79d2  ");
                    return;
                }
                return;
            case 104069874:
                if (lowerCase.equals("mode5")) {
                    Display.setTitle("\u51b0\u51c9Sense || \u68a6\u4e0d\u4f1a\u9003\u8d70\uff0c\u9003\u8d70\u7684\u4e00\u76f4\u90fd\u662f\u81ea\u5df1\u3002\u2014\u2014\u300a\u8721\u7b14\u5c0f\u65b0\u300b || \u60a8\u5df2\u4f7f\u7528\uff1a" + this.f124H + "  \u65f6  " + this.f123M + "  \u5206  " + this.f121S + "  \u79d2  ");
                    return;
                }
                return;
            case 104069875:
                if (lowerCase.equals("mode6")) {
                    Display.setTitle("\u51b0\u51c9Sense || \u4e16\u754c\u4e0a\u4e5f\u8bb8\u6709\u4eba\u559c\u6b22\u5b64\u72ec\uff0c\u4f46\u5374\u6ca1\u6709\u4eba\u80fd\u627f\u53d7\u5b64\u72ec\u3002\u2014\u2014\u300a\u5996\u7cbe\u7684\u5c3e\u5df4\u300b || \u60a8\u5df2\u4f7f\u7528\uff1a" + this.f124H + "  \u65f6  " + this.f123M + "  \u5206  " + this.f121S + "  \u79d2  ");
                    return;
                }
                return;
            case 104069876:
                if (lowerCase.equals("mode7")) {
                    Display.setTitle("\u51b0\u51c9Sense || \u4e0d\u76f8\u4fe1\u81ea\u5df1\u7684\u4eba\uff0c\u8fde\u52aa\u529b\u7684\u4ef7\u503c\u90fd\u6ca1\u6709\u3002\u2014\u2014\u300a\u706b\u5f71\u5fcd\u8005\u300b || \u60a8\u5df2\u4f7f\u7528\uff1a" + this.f124H + "  \u65f6  " + this.f123M + "  \u5206  " + this.f121S + "  \u79d2  ");
                    return;
                }
                return;
            case 104069877:
                if (lowerCase.equals("mode8")) {
                    Display.setTitle("\u51b0\u51c9Sense || \u6211\u4e0d\u77e5\u9053\u79bb\u522b\u7684\u6ecb\u5473\u662f\u8fd9\u6837\u51c4\u51c9\uff0c\u6211\u4e0d\u77e5\u9053\u8bf4\u58f0\u518d\u89c1\u8981\u8fd9\u4e48\u575a\u5f3a\u3002\u2014\u2014\u300a\u5343\u4e0e\u5343\u5bfb\u300b || \u60a8\u5df2\u4f7f\u7528\uff1a" + this.f124H + "  \u65f6  " + this.f123M + "  \u5206  " + this.f121S + "  \u79d2  ");
                    return;
                }
                return;
            default:
                return;
        }
    }
}
