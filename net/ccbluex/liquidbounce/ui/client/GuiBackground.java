package net.ccbluex.liquidbounce.p005ui.client;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import javax.imageio.ImageIO;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.IClassProvider;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.minecraft.client.render.texture.ITextureManager;
import net.ccbluex.liquidbounce.api.minecraft.util.IResourceLocation;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import org.jetbrains.annotations.NotNull;

@Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\ufffd\ufffd\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0007\n\u0002\b\u0003\n\u0002\u0010\f\n\u0002\b\u0003\u0018\ufffd\ufffd \u00182\u00020\u0001:\u0001\u0018B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0006H\u0016J \u0010\r\u001a\u00020\u000b2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u000bH\u0016J\u0018\u0010\u0014\u001a\u00020\u000b2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u000fH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\ufffd\ufffdR\u000e\u0010\u0007\u001a\u00020\u0006X\u0082.\u00a2\u0006\u0002\n\ufffd\ufffdR\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\ufffd\ufffd\u001a\u0004\b\b\u0010\t\u00a8\u0006\u0019"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiBackground;", "Lnet/ccbluex/liquidbounce/api/util/WrappedGuiScreen;", "prevGui", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "(Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;)V", "enabledButton", "Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiButton;", "particlesButton", "getPrevGui", "()Lnet/ccbluex/liquidbounce/api/minecraft/client/gui/IGuiScreen;", "actionPerformed", "", "button", "drawScreen", "mouseX", "", "mouseY", "partialTicks", "", "initGui", "keyTyped", "typedChar", "", "keyCode", "Companion", LiquidBounce.CLIENT_NAME})
/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/GuiBackground.class */
public final class GuiBackground extends WrappedGuiScreen {
    private IGuiButton enabledButton;
    private IGuiButton particlesButton;

    @NotNull
    private final IGuiScreen prevGui;
    private static boolean particles;
    public static final Companion Companion = new Companion(null);
    private static boolean enabled = true;

    @NotNull
    public final IGuiScreen getPrevGui() {
        return this.prevGui;
    }

    public GuiBackground(@NotNull IGuiScreen prevGui) {
        Intrinsics.checkParameterIsNotNull(prevGui, "prevGui");
        this.prevGui = prevGui;
    }

    @Metadata(m24mv = {1, 1, 16}, m25bv = {1, 0, 3}, m23k = 1, m26d1 = {"\ufffd\ufffd\u0014\n\u0002\u0018\u0002\n\u0002\u0010\ufffd\ufffd\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\b\b\u0086\u0003\u0018\ufffd\ufffd2\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\u0004X\u0086\u000e\u00a2\u0006\u000e\n\ufffd\ufffd\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\b\u00a8\u0006\f"}, m27d2 = {"Lnet/ccbluex/liquidbounce/ui/client/GuiBackground$Companion;", "", "()V", "enabled", "", "getEnabled", "()Z", "setEnabled", "(Z)V", "particles", "getParticles", "setParticles", LiquidBounce.CLIENT_NAME})
    /* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/GuiBackground$Companion.class */
    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean getEnabled() {
            return GuiBackground.enabled;
        }

        public final void setEnabled(boolean z) {
            GuiBackground.enabled = z;
        }

        public final boolean getParticles() {
            return GuiBackground.particles;
        }

        public final void setParticles(boolean z) {
            GuiBackground.particles = z;
        }
    }

    public void initGui() {
        this.enabledButton = MinecraftInstance.classProvider.createGuiButton(1, (getRepresentedScreen().getWidth() / 2) - 100, (getRepresentedScreen().getHeight() / 4) + 35, "Enabled (" + (enabled ? "On" : "Off") + ')');
        List buttonList = getRepresentedScreen().getButtonList();
        IGuiButton iGuiButton = this.enabledButton;
        if (iGuiButton == null) {
            Intrinsics.throwUninitializedPropertyAccessException("enabledButton");
        }
        buttonList.add(iGuiButton);
        this.particlesButton = MinecraftInstance.classProvider.createGuiButton(2, (getRepresentedScreen().getWidth() / 2) - 100, (getRepresentedScreen().getHeight() / 4) + 50 + 25, "Particles (" + (particles ? "On" : "Off") + ')');
        List buttonList2 = getRepresentedScreen().getButtonList();
        IGuiButton iGuiButton2 = this.particlesButton;
        if (iGuiButton2 == null) {
            Intrinsics.throwUninitializedPropertyAccessException("particlesButton");
        }
        buttonList2.add(iGuiButton2);
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(3, (getRepresentedScreen().getWidth() / 2) - 100, (getRepresentedScreen().getHeight() / 4) + 50 + 50, 98, 20, "Change wallpaper"));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(4, (getRepresentedScreen().getWidth() / 2) + 2, (getRepresentedScreen().getHeight() / 4) + 50 + 50, 98, 20, "Reset wallpaper"));
        getRepresentedScreen().getButtonList().add(MinecraftInstance.classProvider.createGuiButton(0, (getRepresentedScreen().getWidth() / 2) - 100, (getRepresentedScreen().getHeight() / 4) + 55 + 100 + 5, "Back"));
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void actionPerformed(@NotNull IGuiButton button) throws IOException {
        Intrinsics.checkParameterIsNotNull(button, "button");
        switch (button.getId()) {
            case 0:
                MinecraftInstance.f157mc.displayGuiScreen(this.prevGui);
                break;
            case 1:
                enabled = !enabled;
                IGuiButton iGuiButton = this.enabledButton;
                if (iGuiButton == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("enabledButton");
                }
                iGuiButton.setDisplayString("Enabled (" + (enabled ? "On" : "Off") + ')');
                break;
            case 2:
                particles = !particles;
                IGuiButton iGuiButton2 = this.particlesButton;
                if (iGuiButton2 == null) {
                    Intrinsics.throwUninitializedPropertyAccessException("particlesButton");
                }
                iGuiButton2.setDisplayString("Particles (" + (particles ? "On" : "Off") + ')');
                break;
            case 3:
                File fileOpenFileChooser = MiscUtils.openFileChooser();
                if (fileOpenFileChooser != null && !fileOpenFileChooser.isDirectory()) {
                    try {
                        Files.copy(fileOpenFileChooser.toPath(), new FileOutputStream(LiquidBounce.INSTANCE.getFileManager().backgroundFile));
                        BufferedImage image = ImageIO.read(new FileInputStream(LiquidBounce.INSTANCE.getFileManager().backgroundFile));
                        IClassProvider iClassProvider = MinecraftInstance.classProvider;
                        StringBuilder sb = new StringBuilder();
                        String lowerCase = LiquidBounce.CLIENT_NAME.toLowerCase();
                        Intrinsics.checkExpressionValueIsNotNull(lowerCase, "(this as java.lang.String).toLowerCase()");
                        IResourceLocation iResourceLocationCreateResourceLocation = iClassProvider.createResourceLocation(sb.append(lowerCase).append("/background.png").toString());
                        LiquidBounce.INSTANCE.setBackground(iResourceLocationCreateResourceLocation);
                        ITextureManager textureManager = MinecraftInstance.f157mc.getTextureManager();
                        IClassProvider iClassProvider2 = MinecraftInstance.classProvider;
                        Intrinsics.checkExpressionValueIsNotNull(image, "image");
                        textureManager.loadTexture(iResourceLocationCreateResourceLocation, iClassProvider2.createDynamicTexture(image));
                        break;
                    } catch (Exception e) {
                        e.printStackTrace();
                        MiscUtils.showErrorPopup("Error", "Exception class: " + e.getClass().getName() + "\nMessage: " + e.getMessage());
                        LiquidBounce.INSTANCE.getFileManager().backgroundFile.delete();
                        return;
                    }
                }
                break;
            case 4:
                LiquidBounce.INSTANCE.setBackground((IResourceLocation) null);
                LiquidBounce.INSTANCE.getFileManager().backgroundFile.delete();
                break;
        }
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        getRepresentedScreen().drawBackground(0);
        Fonts.fontBold180.drawCenteredString("Background", getRepresentedScreen().getWidth() / 2.0f, (getRepresentedScreen().getHeight() / 8.0f) + 5.0f, 4673984, true);
        super.drawScreen(i, i2, f);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void keyTyped(char c, int i) {
        if (1 == i) {
            MinecraftInstance.f157mc.displayGuiScreen(this.prevGui);
        } else {
            super.keyTyped(c, i);
        }
    }
}
