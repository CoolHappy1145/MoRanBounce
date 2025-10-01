package net.ccbluex.liquidbounce.p005ui.client;

import jdk.nashorn.tools.Shell;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.p005ui.client.altmanager.GuiAltManager;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.render.RenderUtils;
import org.apache.log4j.net.SyslogAppender;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0002\u0018\ufffd\ufffd2\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J \u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\t2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\b\u0010\r\u001a\u00020\u0004H\u0016\u00a8\u0006\u000e"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiMainMenu;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "()V", "actionPerformed", "", "button", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/GuiMainMenu.class */
public final class GuiMainMenu extends WrappedGuiScreen {
    public void initGui() {
        int height = (getRepresentedScreen().getHeight() / 4) + 48;
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(100, (getRepresentedScreen().getWidth() / 2) - 75, height + 96, SyslogAppender.LOG_LOCAL2, 20, "AltManager"));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(Shell.RUNTIME_ERROR, (getRepresentedScreen().getWidth() / 2) - 75, height + 72, SyslogAppender.LOG_LOCAL2, 20, "Background"));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(1, (getRepresentedScreen().getWidth() / 2) - 75, height + 24, SyslogAppender.LOG_LOCAL2, 20, MinecraftInstance.functions.formatI18n("menu.singleplayer", new String[0])));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(2, (getRepresentedScreen().getWidth() / 2) - 75, height + 48, SyslogAppender.LOG_LOCAL2, 20, MinecraftInstance.functions.formatI18n("menu.multiplayer", new String[0])));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(0, (getRepresentedScreen().getWidth() / 2) - 75, height + 120, SyslogAppender.LOG_LOCAL2, 20, MinecraftInstance.functions.formatI18n("menu.options", new String[0])));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(4, (getRepresentedScreen().getWidth() / 2) - 75, height + SyslogAppender.LOG_LOCAL2, SyslogAppender.LOG_LOCAL2, 20, MinecraftInstance.functions.formatI18n("menu.quit", new String[0])));
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        getRepresentedScreen().drawBackground(0);
        RenderUtils.drawImage(MinecraftInstance.classProvider.createResourceLocation("liquidbounce/logo.png"), (getRepresentedScreen().getWidth() / 2) - 65, (getRepresentedScreen().getHeight() / 6) - 10, 133, 55);
        getRepresentedScreen().superDrawScreen(i, i2, f);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void actionPerformed(@NotNull IGuiButton button) {
        Intrinsics.checkParameterIsNotNull(button, "button");
        switch (button.getId()) {
            case 0:
                MinecraftInstance.f157mc.displayGuiScreen(MinecraftInstance.classProvider.createGuiOptions(getRepresentedScreen(), MinecraftInstance.f157mc.getGameSettings()));
                break;
            case 1:
                MinecraftInstance.f157mc.displayGuiScreen(MinecraftInstance.classProvider.createGuiSelectWorld(getRepresentedScreen()));
                break;
            case 2:
                MinecraftInstance.f157mc.displayGuiScreen(MinecraftInstance.classProvider.createGuiMultiplayer(getRepresentedScreen()));
                break;
            case 4:
                MinecraftInstance.f157mc.shutdown();
                break;
            case Shell.COMMANDLINE_ERROR /* 100 */:
                MinecraftInstance.f157mc.displayGuiScreen(MinecraftInstance.classProvider.wrapGuiScreen(new GuiAltManager(getRepresentedScreen())));
                break;
            case Shell.COMPILATION_ERROR /* 101 */:
                MinecraftInstance.f157mc.displayGuiScreen(MinecraftInstance.classProvider.wrapGuiScreen(new GuiServerStatus(getRepresentedScreen())));
                break;
            case Shell.RUNTIME_ERROR /* 102 */:
                MinecraftInstance.f157mc.displayGuiScreen(MinecraftInstance.classProvider.wrapGuiScreen(new GuiBackground(getRepresentedScreen())));
                break;
            case Shell.IO_ERROR /* 103 */:
                MinecraftInstance.f157mc.displayGuiScreen(MinecraftInstance.classProvider.wrapGuiScreen(new GuiModsMenu(getRepresentedScreen())));
                break;
            case 108:
                MinecraftInstance.f157mc.displayGuiScreen(MinecraftInstance.classProvider.wrapGuiScreen(new GuiContributors(getRepresentedScreen())));
                break;
        }
    }
}
