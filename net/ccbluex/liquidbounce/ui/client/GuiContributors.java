package net.ccbluex.liquidbounce.p005ui.client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.imageio.ImageIO;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.concurrent.ThreadsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.p002io.CloseableKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiSlot;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.Skid.SGL;
import net.ccbluex.liquidbounce.utils.misc.HttpUtils;
import net.ccbluex.liquidbounce.utils.render.CustomTexture;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffdT\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010 \n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\f\n\u0002\b\t\u0018\ufffd\ufffd2\u00020\u0001:\u0006\u001f !\"#$B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J \u0010\u0012\u001a\u00020\u000f2\u0006\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u000fH\u0016J\b\u0010\u0019\u001a\u00020\u000fH\u0016J\u0018\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u0014H\u0016J\b\u0010\u001e\u001a\u00020\u000fH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffdR\u0018\u0010\u0007\u001a\f\u0012\b\u0012\u00060\tR\u00020\ufffd\ufffd0\bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\n\u001a\u00020\u000bX\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffdR\u0012\u0010\f\u001a\u00060\rR\u00020\ufffd\ufffdX\u0082.\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006%"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiContributors;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;)V", "DECIMAL_FORMAT", "Ljava/text/DecimalFormat;", "credits", "", "Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$Credit;", "failed", "", "list", "Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GuiList;", "actionPerformed", "", "button", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "handleMouseInput", "initGui", "keyTyped", "typedChar", "", "keyCode", "loadCredits", "ContributorInformation", "Credit", "GitHubAuthor", "GitHubContributor", "GitHubWeek", "GuiList", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/GuiContributors.class */
public final class GuiContributors extends WrappedGuiScreen {
    private final DecimalFormat DECIMAL_FORMAT;
    private GuiList list;
    private List credits;
    private boolean failed;
    private final IGuiScreen prevGui;

    public GuiContributors(@NotNull IGuiScreen prevGui) {
        Intrinsics.checkParameterIsNotNull(prevGui, "prevGui");
        this.prevGui = prevGui;
        NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        if (numberFormat == null) {
            throw new TypeCastException("null cannot be cast to non-null type java.text.DecimalFormat");
        }
        this.DECIMAL_FORMAT = (DecimalFormat) numberFormat;
        List listEmptyList = Collections.emptyList();
        Intrinsics.checkExpressionValueIsNotNull(listEmptyList, "Collections.emptyList()");
        this.credits = listEmptyList;
    }

    public void initGui() {
        this.list = new GuiList(this, getRepresentedScreen());
        GuiList guiList = this.list;
        if (guiList == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
        }
        guiList.getRepresented().registerScrollButtons(7, 8);
        GuiList guiList2 = this.list;
        if (guiList2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
        }
        guiList2.getRepresented().elementClicked(-1, false, 0, 0);
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(1, (getRepresentedScreen().getWidth() / 2) - 100, getRepresentedScreen().getHeight() - 30, "Back"));
        this.failed = false;
        ThreadsKt.thread$default(false, false, null, null, 0, new Function0(this) { // from class: net.ccbluex.liquidbounce.ui.client.GuiContributors.initGui.1
            final GuiContributors this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public Object invoke() {
                m1681invoke();
                return Unit.INSTANCE;
            }

            /* renamed from: invoke, reason: collision with other method in class */
            public final void m1681invoke() {
                this.this$0.loadCredits();
            }
        }, 31, null);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        getRepresentedScreen().drawBackground(0);
        GuiList guiList = this.list;
        if (guiList == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
        }
        guiList.getRepresented().drawScreen(i, i2, f);
        RenderUtils.drawRect(getRepresentedScreen().getWidth() / 4.0f, 40.0f, getRepresentedScreen().getWidth(), getRepresentedScreen().getHeight() - 40.0f, Integer.MIN_VALUE);
        GuiList guiList2 = this.list;
        if (guiList2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
        }
        if (guiList2.getSelectedSlot$LiquidSense() != -1) {
            List list = this.credits;
            GuiList guiList3 = this.list;
            if (guiList3 == null) {
                Intrinsics.throwUninitializedPropertyAccessException("list");
            }
            Credit credit = (Credit) list.get(guiList3.getSelectedSlot$LiquidSense());
            int width = (getRepresentedScreen().getWidth() / 4) + 5;
            int i3 = 0;
            CustomTexture avatar = credit.getAvatar();
            int fontHeight = getRepresentedScreen().getFontRendererObj().getFontHeight() * 4;
            if (avatar != null) {
                GL11.glPushAttrib(1048575);
                MinecraftInstance.classProvider.getGlStateManager().enableAlpha();
                MinecraftInstance.classProvider.getGlStateManager().enableBlend();
                MinecraftInstance.classProvider.getGlStateManager().tryBlendFuncSeparate(SGL.GL_SRC_ALPHA, SGL.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                MinecraftInstance.classProvider.getGlStateManager().enableTexture2D();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                MinecraftInstance.classProvider.getGlStateManager().bindTexture(avatar.getTextureId());
                GL11.glBegin(7);
                GL11.glTexCoord2f(0.0f, 0.0f);
                GL11.glVertex2i(width, 45);
                GL11.glTexCoord2f(0.0f, 1.0f);
                GL11.glVertex2i(width, 45 + fontHeight);
                GL11.glTexCoord2f(1.0f, 1.0f);
                GL11.glVertex2i(width + fontHeight, 45 + fontHeight);
                GL11.glTexCoord2f(1.0f, 0.0f);
                GL11.glVertex2i(width + fontHeight, 45);
                GL11.glEnd();
                MinecraftInstance.classProvider.getGlStateManager().bindTexture(0);
                MinecraftInstance.classProvider.getGlStateManager().disableBlend();
                i3 = fontHeight;
                GL11.glPopAttrib();
            }
            int fontHeight2 = 45 + fontHeight;
            Color color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(color, "Color.WHITE");
            Fonts.font40.drawString("@" + credit.getName(), width + i3 + 5, 48.0f, color.getRGB(), true);
            float fontHeight3 = fontHeight2 - Fonts.font40.getFontHeight();
            Color color2 = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(color2, "Color.WHITE");
            Fonts.font40.drawString(credit.getCommits() + " commits \u00a7a" + this.DECIMAL_FORMAT.format(Integer.valueOf(credit.getAdditions())) + "++ \u00a74" + this.DECIMAL_FORMAT.format(Integer.valueOf(credit.getDeletions())) + "--", width + i3 + 5, fontHeight3, color2.getRGB(), true);
            for (String str : credit.getContributions()) {
                fontHeight2 += Fonts.font40.getFontHeight() + 2;
                MinecraftInstance.classProvider.getGlStateManager().disableTexture2D();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glBegin(1);
                GL11.glVertex2f(width, (fontHeight2 + (Fonts.font40.getFontHeight() / 2.0f)) - 1.0f);
                GL11.glVertex2f(width + 3.0f, (fontHeight2 + (Fonts.font40.getFontHeight() / 2.0f)) - 1.0f);
                GL11.glEnd();
                Color color3 = Color.WHITE;
                Intrinsics.checkExpressionValueIsNotNull(color3, "Color.WHITE");
                Fonts.font40.drawString(str, width + 5.0f, fontHeight2, color3.getRGB(), true);
            }
        }
        Fonts.font40.drawCenteredString("Contributors", getRepresentedScreen().getWidth() / 2.0f, 6.0f, 16777215);
        if (this.credits.isEmpty()) {
            if (this.failed) {
                int iSin = (int) ((Math.sin(System.currentTimeMillis() * 0.003003003003003003d) + 1.0d) * 127.5d);
                Fonts.font40.drawCenteredString("Failed to load", getRepresentedScreen().getWidth() / 8.0f, getRepresentedScreen().getHeight() / 2.0f, new Color(255, iSin, iSin).getRGB());
            } else {
                Color color4 = Color.WHITE;
                Intrinsics.checkExpressionValueIsNotNull(color4, "Color.WHITE");
                Fonts.font40.drawCenteredString("Loading...", getRepresentedScreen().getWidth() / 8.0f, getRepresentedScreen().getHeight() / 2.0f, color4.getRGB());
                RenderUtils.drawLoadingCircle(getRepresentedScreen().getWidth() / 8, (getRepresentedScreen().getHeight() / 2) - 40);
            }
        }
        super.drawScreen(i, i2, f);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void actionPerformed(@NotNull IGuiButton button) {
        Intrinsics.checkParameterIsNotNull(button, "button");
        if (button.getId() == 1) {
            MinecraftInstance.f157mc.displayGuiScreen(this.prevGui);
        }
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void keyTyped(char c, int i) {
        if (1 == i) {
            MinecraftInstance.f157mc.displayGuiScreen(this.prevGui);
        } else {
            super.keyTyped(c, i);
        }
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void handleMouseInput() {
        super.handleMouseInput();
        GuiList guiList = this.list;
        if (guiList == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
        }
        guiList.getRepresented().handleMouseInput();
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x010d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private final void loadCredits() {
        List listEmptyList;
        Gson gson = new Gson();
        JsonParser jsonParser = new JsonParser();
        GitHubContributor[] gitHubContributorArr = (GitHubContributor[]) gson.fromJson(HttpUtils.get("https://api.github.com/repos/CCBlueX/LiquidBounce/stats/contributors"), GitHubContributor[].class);
        JsonElement jsonElement = jsonParser.parse(HttpUtils.get("https://raw.githubusercontent.com/CCBlueX/LiquidCloud/master/LiquidBounce/contributors.json"));
        Intrinsics.checkExpressionValueIsNotNull(jsonElement, "jsonParser.parse(HttpUti\u2026unce/contributors.json\"))");
        JsonObject asJsonObject = jsonElement.getAsJsonObject();
        ArrayList arrayList = new ArrayList(gitHubContributorArr.length);
        for (GitHubContributor gitHubContributor : gitHubContributorArr) {
            ContributorInformation contributorInformation = (ContributorInformation) null;
            JsonElement jsonElement2 = asJsonObject.get(String.valueOf(gitHubContributor.getAuthor().getId()));
            if (jsonElement2 != null) {
                contributorInformation = (ContributorInformation) gson.fromJson(jsonElement2, ContributorInformation.class);
            }
            int additions = 0;
            int deletions = 0;
            int commits = 0;
            for (GitHubWeek gitHubWeek : gitHubContributor.getWeeks()) {
                additions += gitHubWeek.getAdditions();
                deletions += gitHubWeek.getDeletions();
                commits += gitHubWeek.getCommits();
            }
            String name = gitHubContributor.getAuthor().getName();
            String avatarUrl = gitHubContributor.getAuthor().getAvatarUrl();
            int i = additions;
            int i2 = deletions;
            int i3 = commits;
            ContributorInformation contributorInformation2 = contributorInformation;
            boolean teamMember = contributorInformation2 != null ? contributorInformation2.getTeamMember() : false;
            ContributorInformation contributorInformation3 = contributorInformation;
            if (contributorInformation3 != null) {
                listEmptyList = contributorInformation3.getContributions();
                if (listEmptyList == null) {
                    listEmptyList = Collections.emptyList();
                    Intrinsics.checkExpressionValueIsNotNull(listEmptyList, "Collections.emptyList()");
                }
            }
            arrayList.add(new Credit(this, name, avatarUrl, null, i, i2, i3, teamMember, listEmptyList));
        }
        CollectionsKt.sortWith(arrayList, new Comparator() { // from class: net.ccbluex.liquidbounce.ui.client.GuiContributors.loadCredits.1
            @Override // java.util.Comparator
            public int compare(Object obj, Object obj2) {
                return compare((Credit) obj, (Credit) obj2);
            }

            public int compare(@NotNull Credit o1, @NotNull Credit o2) {
                Intrinsics.checkParameterIsNotNull(o1, "o1");
                Intrinsics.checkParameterIsNotNull(o2, "o2");
                if (o1.isTeamMember() && o2.isTeamMember()) {
                    int commits2 = o1.getCommits();
                    int commits3 = o2.getCommits();
                    return -(commits2 < commits3 ? -1 : commits2 == commits3 ? 0 : 1);
                }
                if (o1.isTeamMember()) {
                    return -1;
                }
                if (o2.isTeamMember()) {
                    return 1;
                }
                int additions2 = o1.getAdditions();
                int additions3 = o2.getAdditions();
                return -(additions2 < additions3 ? -1 : additions2 == additions3 ? 0 : 1);
            }
        });
        this.credits = arrayList;
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Credit credit = (Credit) it.next();
            try {
                InputStream inputStreamRequestStream$default = HttpUtils.requestStream$default(HttpUtils.INSTANCE, credit.getAvatarUrl() + "?s=" + (getRepresentedScreen().getFontRendererObj().getFontHeight() * 4), "GET", null, 4, null);
                if (inputStreamRequestStream$default != null) {
                    InputStream inputStream = inputStreamRequestStream$default;
                    Throwable th = (Throwable) null;
                    try {
                        BufferedImage bufferedImage = ImageIO.read(inputStream);
                        if (bufferedImage == null) {
                            Intrinsics.throwNpe();
                        }
                        credit.setAvatar(new CustomTexture(bufferedImage));
                        Unit unit = Unit.INSTANCE;
                        CloseableKt.closeFinally(inputStream, th);
                    } catch (Throwable th2) {
                        throw th2;
                    }
                }
            } catch (Exception unused) {
            }
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u001e\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010 \n\u0002\b\b\b\u0080\u0004\u0018\ufffd\ufffd2\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u00a2\u0006\u0002\u0010\bR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$ContributorInformation;", "", "name", "", "teamMember", "", "contributions", "", "(Lnet/ccbluex/liquidbounce/ui/client/GuiContributors;Ljava/lang/String;ZLjava/util/List;)V", "getContributions", "()Ljava/util/List;", "getName", "()Ljava/lang/String;", "getTeamMember", "()Z", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/GuiContributors$ContributorInformation.class */
    public final class ContributorInformation {

        @NotNull
        private final String name;
        private final boolean teamMember;

        @NotNull
        private final List contributions;
        final GuiContributors this$0;

        @NotNull
        public final String getName() {
            return this.name;
        }

        public final boolean getTeamMember() {
            return this.teamMember;
        }

        @NotNull
        public final List getContributions() {
            return this.contributions;
        }

        public ContributorInformation(@NotNull GuiContributors guiContributors, String name, @NotNull boolean z, List contributions) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(contributions, "contributions");
            this.this$0 = guiContributors;
            this.name = name;
            this.teamMember = z;
            this.contributions = contributions;
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd&\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\b\b\u0080\u0004\u0018\ufffd\ufffd2\u00020\u0001B+\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0010\u0010\u0004\u001a\f\u0012\b\u0012\u00060\u0006R\u00020\u00070\u0005\u0012\n\u0010\b\u001a\u00060\tR\u00020\u0007\u00a2\u0006\u0002\u0010\nR\u0015\u0010\b\u001a\u00060\tR\u00020\u0007\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\fR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\r\u0010\u000eR\u001b\u0010\u0004\u001a\f\u0012\b\u0012\u00060\u0006R\u00020\u00070\u0005\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u0011"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GitHubContributor;", "", "totalContributions", "", "weeks", "", "Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GitHubWeek;", "Lnet/ccbluex/liquidbounce/ui/client/GuiContributors;", "author", "Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GitHubAuthor;", "(Lnet/ccbluex/liquidbounce/ui/client/GuiContributors;ILjava/util/List;Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GitHubAuthor;)V", "getAuthor", "()Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GitHubAuthor;", "getTotalContributions", "()I", "getWeeks", "()Ljava/util/List;", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/GuiContributors$GitHubContributor.class */
    public final class GitHubContributor {

        @SerializedName("total")
        private final int totalContributions;

        @NotNull
        private final List weeks;

        @NotNull
        private final GitHubAuthor author;
        final GuiContributors this$0;

        public final int getTotalContributions() {
            return this.totalContributions;
        }

        @NotNull
        public final List getWeeks() {
            return this.weeks;
        }

        @NotNull
        public final GitHubAuthor getAuthor() {
            return this.author;
        }

        public GitHubContributor(GuiContributors guiContributors, @NotNull int i, @NotNull List weeks, GitHubAuthor author) {
            Intrinsics.checkParameterIsNotNull(weeks, "weeks");
            Intrinsics.checkParameterIsNotNull(author, "author");
            this.this$0 = guiContributors;
            this.totalContributions = i;
            this.weeks = weeks;
            this.author = author;
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\t\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\n\b\u0080\u0004\u0018\ufffd\ufffd2\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\bR\u0016\u0010\u0004\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\t\u0010\nR\u0016\u0010\u0007\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\nR\u0016\u0010\u0006\u001a\u00020\u00058\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\f\u0010\nR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GitHubWeek;", "", "timestamp", "", "additions", "", "deletions", "commits", "(Lnet/ccbluex/liquidbounce/ui/client/GuiContributors;JIII)V", "getAdditions", "()I", "getCommits", "getDeletions", "getTimestamp", "()J", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/GuiContributors$GitHubWeek.class */
    public final class GitHubWeek {

        @SerializedName("w")
        private final long timestamp;

        @SerializedName("a")
        private final int additions;

        @SerializedName("d")
        private final int deletions;

        @SerializedName("c")
        private final int commits;
        final GuiContributors this$0;

        public final long getTimestamp() {
            return this.timestamp;
        }

        public final int getAdditions() {
            return this.additions;
        }

        public final int getDeletions() {
            return this.deletions;
        }

        public final int getCommits() {
            return this.commits;
        }

        public GitHubWeek(GuiContributors guiContributors, long j, int i, int i2, int i3) {
            this.this$0 = guiContributors;
            this.timestamp = j;
            this.additions = i;
            this.deletions = i2;
            this.commits = i3;
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0018\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u000e\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\b\b\u0080\u0004\u0018\ufffd\ufffd2\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0007R\u0016\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\f\u0010\t\u00a8\u0006\r"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GitHubAuthor;", "", "name", "", "id", "", "avatarUrl", "(Lnet/ccbluex/liquidbounce/ui/client/GuiContributors;Ljava/lang/String;ILjava/lang/String;)V", "getAvatarUrl", "()Ljava/lang/String;", "getId", "()I", "getName", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/GuiContributors$GitHubAuthor.class */
    public final class GitHubAuthor {

        @SerializedName("login")
        @NotNull
        private final String name;

        /* renamed from: id */
        private final int f132id;

        @SerializedName("avatar_url")
        @NotNull
        private final String avatarUrl;
        final GuiContributors this$0;

        @NotNull
        public final String getName() {
            return this.name;
        }

        public final int getId() {
            return this.f132id;
        }

        @NotNull
        public final String getAvatarUrl() {
            return this.avatarUrl;
        }

        public GitHubAuthor(@NotNull GuiContributors guiContributors, String name, @NotNull int i, String avatarUrl) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(avatarUrl, "avatarUrl");
            this.this$0 = guiContributors;
            this.name = name;
            this.f132id = i;
            this.avatarUrl = avatarUrl;
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd.\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\ufffd\ufffd\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\ufffd\ufffd\n\u0002\u0010 \n\u0002\b\u0010\b\u0080\u0004\u0018\ufffd\ufffd2\u00020\u0001BM\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\b\u0012\u0006\u0010\n\u001a\u00020\b\u0012\u0006\u0010\u000b\u001a\u00020\f\u0012\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e\u00a2\u0006\u0002\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\b\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0010\u0010\u0011R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0016\u0010\u0017R\u0011\u0010\n\u001a\u00020\b\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0018\u0010\u0011R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00030\u000e\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\t\u001a\u00020\b\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u001b\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u000b\u0010\u001cR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\u001d\u0010\u0017\u00a8\u0006\u001e"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$Credit;", "", "name", "", "avatarUrl", "avatar", "Lnet/ccbluex/liquidbounce/utils/render/CustomTexture;", "additions", "", "deletions", "commits", "isTeamMember", "", "contributions", "", "(Lnet/ccbluex/liquidbounce/ui/client/GuiContributors;Ljava/lang/String;Ljava/lang/String;Lnet/ccbluex/liquidbounce/utils/render/CustomTexture;IIIZLjava/util/List;)V", "getAdditions", "()I", "getAvatar", "()Lnet/ccbluex/liquidbounce/utils/render/CustomTexture;", "setAvatar", "(Lnet/ccbluex/liquidbounce/utils/render/CustomTexture;)V", "getAvatarUrl", "()Ljava/lang/String;", "getCommits", "getContributions", "()Ljava/util/List;", "getDeletions", "()Z", "getName", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/GuiContributors$Credit.class */
    public final class Credit {

        @NotNull
        private final String name;

        @NotNull
        private final String avatarUrl;

        @Nullable
        private CustomTexture avatar;
        private final int additions;
        private final int deletions;
        private final int commits;
        private final boolean isTeamMember;

        @NotNull
        private final List contributions;
        final GuiContributors this$0;

        @NotNull
        public final String getName() {
            return this.name;
        }

        @NotNull
        public final String getAvatarUrl() {
            return this.avatarUrl;
        }

        @Nullable
        public final CustomTexture getAvatar() {
            return this.avatar;
        }

        public final void setAvatar(@Nullable CustomTexture customTexture) {
            this.avatar = customTexture;
        }

        public final int getAdditions() {
            return this.additions;
        }

        public final int getDeletions() {
            return this.deletions;
        }

        public final int getCommits() {
            return this.commits;
        }

        public final boolean isTeamMember() {
            return this.isTeamMember;
        }

        @NotNull
        public final List getContributions() {
            return this.contributions;
        }

        public Credit(@NotNull GuiContributors guiContributors, @NotNull String name, @Nullable String avatarUrl, CustomTexture customTexture, int i, int i2, int i3, @NotNull boolean z, List contributions) {
            Intrinsics.checkParameterIsNotNull(name, "name");
            Intrinsics.checkParameterIsNotNull(avatarUrl, "avatarUrl");
            Intrinsics.checkParameterIsNotNull(contributions, "contributions");
            this.this$0 = guiContributors;
            this.name = name;
            this.avatarUrl = avatarUrl;
            this.avatar = customTexture;
            this.additions = i;
            this.deletions = i2;
            this.commits = i3;
            this.isTeamMember = z;
            this.contributions = contributions;
        }
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\n\n\u0002\u0010\u000b\n\u0002\b\b\b\u0082\u0004\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J8\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0006H\u0016J(\u0010\u0010\u001a\u00020\b2\u0006\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u00062\u0006\u0010\u0015\u001a\u00020\u0006H\u0016J\r\u0010\u0016\u001a\u00020\u0006H\ufffd\ufffd\u00a2\u0006\u0002\b\u0017J\b\u0010\u0018\u001a\u00020\u0006H\u0016J\u0010\u0010\u0019\u001a\u00020\u00132\u0006\u0010\u001a\u001a\u00020\u0006H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u001b"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiContributors$GuiList;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiSlot;", "gui", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "(Lnet/ccbluex/liquidbounce/ui/client/GuiContributors;Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;)V", "selectedSlot", "", "drawBackground", "", "drawSlot", "entryID", "p_180791_2_", "p_180791_3_", "p_180791_4_", "mouseXIn", "mouseYIn", "elementClicked", "index", "doubleClick", "", "var3", "var4", "getSelectedSlot", "getSelectedSlot$LiquidSense", "getSize", "isSelected", "id", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/GuiContributors$GuiList.class */
    private final class GuiList extends WrappedGuiSlot {
        private int selectedSlot;
        final GuiContributors this$0;

        /* JADX WARN: Illegal instructions before constructor call */
        public GuiList(@NotNull GuiContributors guiContributors, IGuiScreen gui) {
            Intrinsics.checkParameterIsNotNull(gui, "gui");
            this.this$0 = guiContributors;
            IMinecraft mc = MinecraftInstance.f157mc;
            Intrinsics.checkExpressionValueIsNotNull(mc, "mc");
            super(mc, gui.getWidth() / 4, gui.getHeight(), 40, gui.getHeight() - 40, 15);
            getRepresented().setListWidth((gui.getWidth() * 3) / 13);
            getRepresented().setEnableScissor(true);
        }

        @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiSlot
        public boolean isSelected(int i) {
            return this.selectedSlot == i;
        }

        @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiSlot
        public int getSize() {
            return this.this$0.credits.size();
        }

        public final int getSelectedSlot$LiquidSense() {
            if (this.selectedSlot > this.this$0.credits.size()) {
                return -1;
            }
            return this.selectedSlot;
        }

        @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiSlot
        public void elementClicked(int i, boolean z, int i2, int i3) {
            this.selectedSlot = i;
        }

        @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiSlot
        public void drawSlot(int i, int i2, int i3, int i4, int i5, int i6) {
            Credit credit = (Credit) this.this$0.credits.get(i);
            Color color = Color.WHITE;
            Intrinsics.checkExpressionValueIsNotNull(color, "Color.WHITE");
            Fonts.font40.drawCenteredString(credit.getName(), getRepresented().getWidth() / 2.0f, i3 + 2.0f, color.getRGB(), true);
        }
    }
}
