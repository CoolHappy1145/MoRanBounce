package net.ccbluex.liquidbounce.p005ui.client;

import java.util.List;
import net.ccbluex.liquidbounce.LiquidBounce;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.features.special.AntiForge;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/GuiAntiForge.class */
public class GuiAntiForge extends WrappedGuiScreen {
    private final IGuiScreen prevGui;
    private IGuiButton enabledButton;
    private IGuiButton fmlButton;
    private IGuiButton proxyButton;
    private IGuiButton payloadButton;

    public GuiAntiForge(IGuiScreen iGuiScreen) {
        this.prevGui = iGuiScreen;
    }

    public void initGui() {
        List buttonList = this.representedScreen.getButtonList();
        IGuiButton iGuiButtonCreateGuiButton = classProvider.createGuiButton(1, (this.representedScreen.getWidth() / 2) - 100, (this.representedScreen.getHeight() / 4) + 35, "Enabled (" + (AntiForge.enabled ? "On" : "Off") + ")");
        this.enabledButton = iGuiButtonCreateGuiButton;
        buttonList.add(iGuiButtonCreateGuiButton);
        List buttonList2 = this.representedScreen.getButtonList();
        IGuiButton iGuiButtonCreateGuiButton2 = classProvider.createGuiButton(2, (this.representedScreen.getWidth() / 2) - 100, (this.representedScreen.getHeight() / 4) + 50 + 25, "Block FML (" + (AntiForge.blockFML ? "On" : "Off") + ")");
        this.fmlButton = iGuiButtonCreateGuiButton2;
        buttonList2.add(iGuiButtonCreateGuiButton2);
        List buttonList3 = this.representedScreen.getButtonList();
        IGuiButton iGuiButtonCreateGuiButton3 = classProvider.createGuiButton(3, (this.representedScreen.getWidth() / 2) - 100, (this.representedScreen.getHeight() / 4) + 50 + 50, "Block FML Proxy Packet (" + (AntiForge.blockProxyPacket ? "On" : "Off") + ")");
        this.proxyButton = iGuiButtonCreateGuiButton3;
        buttonList3.add(iGuiButtonCreateGuiButton3);
        List buttonList4 = this.representedScreen.getButtonList();
        IGuiButton iGuiButtonCreateGuiButton4 = classProvider.createGuiButton(4, (this.representedScreen.getWidth() / 2) - 100, (this.representedScreen.getHeight() / 4) + 50 + 75, "Block Payload Packets (" + (AntiForge.blockPayloadPackets ? "On" : "Off") + ")");
        this.payloadButton = iGuiButtonCreateGuiButton4;
        buttonList4.add(iGuiButtonCreateGuiButton4);
        this.representedScreen.getButtonList().add(classProvider.createGuiButton(0, (this.representedScreen.getWidth() / 2) - 100, (this.representedScreen.getHeight() / 4) + 55 + 100 + 5, "Back"));
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void actionPerformed(IGuiButton iGuiButton) {
        switch (iGuiButton.getId()) {
            case 0:
                f157mc.displayGuiScreen(this.prevGui);
                break;
            case 1:
                AntiForge.enabled = !AntiForge.enabled;
                this.enabledButton.setDisplayString("Enabled (" + (AntiForge.enabled ? "On" : "Off") + ")");
                LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
                break;
            case 2:
                AntiForge.blockFML = !AntiForge.blockFML;
                this.enabledButton.setDisplayString("Block FML (" + (AntiForge.blockFML ? "On" : "Off") + ")");
                LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
                break;
            case 3:
                AntiForge.blockProxyPacket = !AntiForge.blockProxyPacket;
                this.enabledButton.setDisplayString("Block FML Proxy Packet (" + (AntiForge.blockProxyPacket ? "On" : "Off") + ")");
                LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
                break;
            case 4:
                AntiForge.blockPayloadPackets = !AntiForge.blockPayloadPackets;
                this.enabledButton.setDisplayString("Block Payload Packets (" + (AntiForge.blockPayloadPackets ? "On" : "Off") + ")");
                LiquidBounce.fileManager.saveConfig(LiquidBounce.fileManager.valuesConfig);
                break;
        }
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        this.representedScreen.drawBackground(0);
        Fonts.fontBold180.drawCenteredString("AntiForge", (int) (this.representedScreen.getWidth() / 2.0f), (int) ((this.representedScreen.getHeight() / 8.0f) + 5.0f), 4673984, true);
        super.drawScreen(i, i2, f);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void keyTyped(char c, int i) {
        if (1 == i) {
            f157mc.displayGuiScreen(this.prevGui);
        } else {
            super.keyTyped(c, i);
        }
    }
}
