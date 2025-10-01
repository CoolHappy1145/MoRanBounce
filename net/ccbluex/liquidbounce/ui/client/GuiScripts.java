package net.ccbluex.liquidbounce.p005ui.client;

import java.awt.Color;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.IMinecraft;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiSlot;
import net.ccbluex.liquidbounce.p005ui.client.clickgui.ClickGui;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.script.Script;
import net.ccbluex.liquidbounce.utils.ClientUtils;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0010\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0004\n\u0002\u0010\f\n\u0002\b\u0003\u0018\ufffd\ufffd2\u00020\u0001:\u0001\u0017B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J \u0010\u000b\u001a\u00020\b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\b\u0010\u0011\u001a\u00020\bH\u0016J\b\u0010\u0012\u001a\u00020\bH\u0016J\u0018\u0010\u0013\u001a\u00020\b2\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\rH\u0016R\u0012\u0010\u0005\u001a\u00060\u0006R\u00020\ufffd\ufffdX\u0082.\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0018"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiScripts;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;)V", "list", "Lnet/ccbluex/liquidbounce/ui/client/GuiScripts$GuiList;", "actionPerformed", "", "button", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "handleMouseInput", "initGui", "keyTyped", "typedChar", "", "keyCode", "GuiList", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/GuiScripts.class */
public final class GuiScripts extends WrappedGuiScreen {
    private GuiList list;
    private final IGuiScreen prevGui;

    public GuiScripts(@NotNull IGuiScreen prevGui) {
        Intrinsics.checkParameterIsNotNull(prevGui, "prevGui");
        this.prevGui = prevGui;
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
        guiList2.elementClicked(-1, false, 0, 0);
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(0, getRepresentedScreen().getWidth() - 80, getRepresentedScreen().getHeight() - 65, 70, 20, "Back"));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(1, getRepresentedScreen().getWidth() - 80, 46, 70, 20, "Import"));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(2, getRepresentedScreen().getWidth() - 80, 70, 70, 20, "Delete"));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(3, getRepresentedScreen().getWidth() - 80, 94, 70, 20, "Reload"));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(4, getRepresentedScreen().getWidth() - 80, 118, 70, 20, "Folder"));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(5, getRepresentedScreen().getWidth() - 80, 142, 70, 20, "Docs"));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(6, getRepresentedScreen().getWidth() - 80, 166, 70, 20, "Find Scripts"));
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        getRepresentedScreen().drawBackground(0);
        GuiList guiList = this.list;
        if (guiList == null) {
            Intrinsics.throwUninitializedPropertyAccessException("list");
        }
        guiList.getRepresented().drawScreen(i, i2, f);
        Fonts.font40.drawCenteredString("\u00a79\u00a7lScripts", getRepresentedScreen().getWidth() / 2.0f, 28.0f, 16777215);
        super.drawScreen(i, i2, f);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void actionPerformed(@NotNull IGuiButton button) {
        Intrinsics.checkParameterIsNotNull(button, "button");
        switch (button.getId()) {
            case 0:
                MinecraftInstance.f157mc.displayGuiScreen(this.prevGui);
                break;
            case 1:
                try {
                    File fileOpenFileChooser = MiscUtils.openFileChooser();
                    if (fileOpenFileChooser != null) {
                        String fileName = fileOpenFileChooser.getName();
                        Intrinsics.checkExpressionValueIsNotNull(fileName, "fileName");
                        if (StringsKt.endsWith$default(fileName, ".js", false, 2, (Object) null)) {
                            LiquidBounce.INSTANCE.getScriptManager().importScript(fileOpenFileChooser);
                            LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                            LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                            break;
                        } else if (StringsKt.endsWith$default(fileName, ".zip", false, 2, (Object) null)) {
                            ZipFile zipFile = new ZipFile(fileOpenFileChooser);
                            Enumeration<? extends ZipEntry> enumerationEntries = zipFile.entries();
                            ArrayList arrayList = new ArrayList();
                            while (enumerationEntries.hasMoreElements()) {
                                ZipEntry entry = enumerationEntries.nextElement();
                                Intrinsics.checkExpressionValueIsNotNull(entry, "entry");
                                String entryName = entry.getName();
                                File file = new File(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder(), entryName);
                                if (entry.isDirectory()) {
                                    file.mkdir();
                                } else {
                                    InputStream inputStream = zipFile.getInputStream(entry);
                                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                                    IOUtils.copy(inputStream, fileOutputStream);
                                    fileOutputStream.close();
                                    inputStream.close();
                                    Intrinsics.checkExpressionValueIsNotNull(entryName, "entryName");
                                    if (!StringsKt.contains$default((CharSequence) entryName, (CharSequence) "/", false, 2, (Object) null)) {
                                        arrayList.add(file);
                                    }
                                }
                            }
                            Iterator it = arrayList.iterator();
                            while (it.hasNext()) {
                                LiquidBounce.INSTANCE.getScriptManager().loadScript((File) it.next());
                            }
                            LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                            LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                            LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
                            break;
                        } else {
                            MiscUtils.showErrorPopup("Wrong file extension.", "The file extension has to be .js or .zip");
                            break;
                        }
                    }
                } catch (Throwable th) {
                    ClientUtils.getLogger().error("Something went wrong while importing a script.", th);
                    MiscUtils.showErrorPopup(th.getClass().getName(), th.getMessage());
                    return;
                }
                break;
            case 2:
                try {
                    GuiList guiList = this.list;
                    if (guiList == null) {
                        Intrinsics.throwUninitializedPropertyAccessException("list");
                    }
                    if (guiList.getSelectedSlot$LiquidSense() != -1) {
                        List scripts = LiquidBounce.INSTANCE.getScriptManager().getScripts();
                        GuiList guiList2 = this.list;
                        if (guiList2 == null) {
                            Intrinsics.throwUninitializedPropertyAccessException("list");
                        }
                        LiquidBounce.INSTANCE.getScriptManager().deleteScript((Script) scripts.get(guiList2.getSelectedSlot$LiquidSense()));
                        LiquidBounce.INSTANCE.setClickGui(new ClickGui());
                        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().clickGuiConfig);
                        LiquidBounce.INSTANCE.getFileManager().loadConfig(LiquidBounce.INSTANCE.getFileManager().hudConfig);
                        break;
                    }
                } catch (Throwable th2) {
                    ClientUtils.getLogger().error("Something went wrong while deleting a script.", th2);
                    MiscUtils.showErrorPopup(th2.getClass().getName(), th2.getMessage());
                    return;
                }
                break;
            case 3:
                try {
                    LiquidBounce.INSTANCE.getScriptManager().reloadScripts();
                    break;
                } catch (Throwable th3) {
                    ClientUtils.getLogger().error("Something went wrong while reloading all scripts.", th3);
                    MiscUtils.showErrorPopup(th3.getClass().getName(), th3.getMessage());
                    return;
                }
            case 4:
                try {
                    Desktop.getDesktop().open(LiquidBounce.INSTANCE.getScriptManager().getScriptsFolder());
                    break;
                } catch (Throwable th4) {
                    ClientUtils.getLogger().error("Something went wrong while trying to open your scripts folder.", th4);
                    MiscUtils.showErrorPopup(th4.getClass().getName(), th4.getMessage());
                    return;
                }
            case 5:
                try {
                    Desktop.getDesktop().browse(new URL("https://liquidbounce.net/docs/ScriptAPI/Getting%20Started").toURI());
                    break;
                } catch (Exception unused) {
                    return;
                }
            case 6:
                try {
                    Desktop.getDesktop().browse(new URL("https://forum.ccbluex.net/viewforum.php?id=16").toURI());
                    break;
                } catch (Exception unused2) {
                }
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

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\ufffd\ufffd\n\u0002\u0010\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0082\u0004\u0018\ufffd\ufffd2\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\b\u0010\u0007\u001a\u00020\bH\u0016J8\u0010\t\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\u000f\u001a\u00020\u0006H\u0016J(\u0010\u0010\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u0006H\u0016J\r\u0010\u0014\u001a\u00020\u0006H\ufffd\ufffd\u00a2\u0006\u0002\b\u0015J\b\u0010\u0016\u001a\u00020\u0006H\u0016J\u0010\u0010\u0017\u001a\u00020\u00122\u0006\u0010\n\u001a\u00020\u0006H\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\ufffd\ufffd\u00a8\u0006\u0018"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiScripts$GuiList;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiSlot;", "gui", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "(Lnet/ccbluex/liquidbounce/ui/client/GuiScripts;Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;)V", "selectedSlot", "", "drawBackground", "", "drawSlot", "id", "x", "y", "var4", "var5", "var6", "elementClicked", "doubleClick", "", "var3", "getSelectedSlot", "getSelectedSlot$LiquidSense", "getSize", "isSelected", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/GuiScripts$GuiList.class */
    private final class GuiList extends WrappedGuiSlot {
        private int selectedSlot;
        final GuiScripts this$0;

        /* JADX WARN: Illegal instructions before constructor call */
        public GuiList(@NotNull GuiScripts guiScripts, IGuiScreen gui) {
            Intrinsics.checkParameterIsNotNull(gui, "gui");
            this.this$0 = guiScripts;
            IMinecraft mc = MinecraftInstance.f157mc;
            Intrinsics.checkExpressionValueIsNotNull(mc, "mc");
            super(mc, gui.getWidth(), gui.getHeight(), 40, gui.getHeight() - 40, 30);
        }

        @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiSlot
        public boolean isSelected(int i) {
            return this.selectedSlot == i;
        }

        public final int getSelectedSlot$LiquidSense() {
            if (this.selectedSlot > LiquidBounce.INSTANCE.getScriptManager().getScripts().size()) {
                return -1;
            }
            return this.selectedSlot;
        }

        @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiSlot
        public int getSize() {
            return LiquidBounce.INSTANCE.getScriptManager().getScripts().size();
        }

        @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiSlot
        public void elementClicked(int i, boolean z, int i2, int i3) {
            this.selectedSlot = i;
        }

        @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiSlot
        public void drawSlot(int i, int i2, int i3, int i4, int i5, int i6) {
            Script script = (Script) LiquidBounce.INSTANCE.getScriptManager().getScripts().get(i);
            Color color = Color.LIGHT_GRAY;
            Intrinsics.checkExpressionValueIsNotNull(color, "Color.LIGHT_GRAY");
            Fonts.font40.drawCenteredString("\u00a79" + script.getScriptName() + " \u00a77v" + script.getScriptVersion(), this.this$0.getRepresentedScreen().getWidth() / 2.0f, i3 + 2.0f, color.getRGB());
            Color color2 = Color.LIGHT_GRAY;
            Intrinsics.checkExpressionValueIsNotNull(color2, "Color.LIGHT_GRAY");
            Fonts.font40.drawCenteredString("by \u00a7c" + ArraysKt.joinToString$default(script.getScriptAuthors(), ", ", (CharSequence) null, (CharSequence) null, 0, (CharSequence) null, (Function1) null, 62, (Object) null), this.this$0.getRepresentedScreen().getWidth() / 2.0f, i3 + 15.0f, color2.getRGB());
        }
    }
}
