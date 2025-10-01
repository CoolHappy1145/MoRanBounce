package net.ccbluex.liquidbounce.p005ui.client.tools;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import kotlin.jvm.internal.CharCompanionObject;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiButton;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiScreen;
import net.ccbluex.liquidbounce.api.minecraft.client.gui.IGuiTextField;
import net.ccbluex.liquidbounce.api.util.WrappedGuiScreen;
import net.ccbluex.liquidbounce.p005ui.font.Fonts;
import net.ccbluex.liquidbounce.utils.TabUtils;
import net.ccbluex.liquidbounce.utils.misc.MiscUtils;
import org.json.HTTP;
import org.lwjgl.input.Keyboard;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/ui/client/tools/GuiPortScanner.class */
public class GuiPortScanner extends WrappedGuiScreen {
    private final IGuiScreen prevGui;
    private IGuiTextField hostField;
    private IGuiTextField minPortField;
    private IGuiTextField maxPortField;
    private IGuiTextField threadsField;
    private IGuiButton buttonToggle;
    private boolean running;
    private String host;
    private int currentPort;
    private int maxPort;
    private int minPort;
    private int checkedPort;
    private final List ports = new ArrayList();
    private String status = "\u00a77Waiting...";

    public GuiPortScanner(IGuiScreen iGuiScreen) {
        this.prevGui = iGuiScreen;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.hostField = classProvider.createGuiTextField(0, Fonts.font40, (this.representedScreen.getWidth() / 2) - 100, 60, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 20);
        this.hostField.setFocused(true);
        this.hostField.setMaxStringLength(Integer.MAX_VALUE);
        this.hostField.setText("localhost");
        this.minPortField = classProvider.createGuiTextField(1, Fonts.font40, (this.representedScreen.getHeight() / 2) - 100, 90, 90, 20);
        this.minPortField.setMaxStringLength(5);
        this.minPortField.setText(String.valueOf(1));
        this.maxPortField = classProvider.createGuiTextField(2, Fonts.font40, (this.representedScreen.getWidth() / 2) + 10, 90, 90, 20);
        this.maxPortField.setMaxStringLength(5);
        this.maxPortField.setText(String.valueOf(CharCompanionObject.MAX_VALUE));
        this.threadsField = classProvider.createGuiTextField(3, Fonts.font40, (this.representedScreen.getWidth() / 2) - 100, 120, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 20);
        this.threadsField.setMaxStringLength(Integer.MAX_VALUE);
        this.threadsField.setText(String.valueOf(SharedScopeCall.SLOW_SCOPE_CALL_THRESHOLD));
        List buttonList = this.representedScreen.getButtonList();
        IGuiButton iGuiButtonCreateGuiButton = classProvider.createGuiButton(1, (this.representedScreen.getWidth() / 2) - 100, (this.representedScreen.getHeight() / 4) + 95, this.running ? "Stop" : "Start");
        this.buttonToggle = iGuiButtonCreateGuiButton;
        buttonList.add(iGuiButtonCreateGuiButton);
        this.representedScreen.getButtonList().add(classProvider.createGuiButton(0, (this.representedScreen.getWidth() / 2) - 100, (this.representedScreen.getHeight() / 4) + 120, "Back"));
        this.representedScreen.getButtonList().add(classProvider.createGuiButton(2, (this.representedScreen.getWidth() / 2) - 100, (this.representedScreen.getHeight() / 4) + 155, "Export"));
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void drawScreen(int i, int i2, float f) {
        this.representedScreen.drawBackground(0);
        Fonts.font40.drawCenteredString("Port Scanner", this.representedScreen.getWidth() / 2.0f, 34.0f, 16777215);
        Fonts.font35.drawCenteredString(this.running ? "\u00a77" + this.checkedPort + " \u00a78/ \u00a77" + this.maxPort : this.status == null ? "" : this.status, this.representedScreen.getWidth() / 2.0f, (this.representedScreen.getHeight() / 4.0f) + 80.0f, 16777215);
        this.buttonToggle.setDisplayString(this.running ? "Stop" : "Start");
        this.hostField.drawTextBox();
        this.minPortField.drawTextBox();
        this.maxPortField.drawTextBox();
        this.threadsField.drawTextBox();
        Fonts.font40.drawString("\u00a7c\u00a7lPorts:", 2, 2, Color.WHITE.hashCode());
        synchronized (this.ports) {
            int fontHeight = 12;
            Iterator it = this.ports.iterator();
            while (it.hasNext()) {
                Fonts.font35.drawString(String.valueOf((Integer) it.next()), 2, fontHeight, Color.WHITE.hashCode());
                fontHeight += Fonts.font35.getFontHeight();
            }
        }
        super.drawScreen(i, i2, f);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void actionPerformed(IGuiButton iGuiButton) throws NumberFormatException, IOException {
        switch (iGuiButton.getId()) {
            case 0:
                f157mc.displayGuiScreen(this.prevGui);
                break;
            case 1:
                if (this.running) {
                    this.running = false;
                } else {
                    this.host = this.hostField.getText();
                    if (this.host.isEmpty()) {
                        this.status = "\u00a7cInvalid host";
                        return;
                    }
                    try {
                        this.minPort = Integer.parseInt(this.minPortField.getText());
                        try {
                            this.maxPort = Integer.parseInt(this.maxPortField.getText());
                            try {
                                int i = Integer.parseInt(this.threadsField.getText());
                                this.ports.clear();
                                this.currentPort = this.minPort - 1;
                                this.checkedPort = this.minPort;
                                for (int i2 = 0; i2 < i; i2++) {
                                    new Thread(this::lambda$actionPerformed$0).start();
                                }
                                this.running = true;
                            } catch (NumberFormatException unused) {
                                this.status = "\u00a7cInvalid threads";
                                return;
                            }
                        } catch (NumberFormatException unused2) {
                            this.status = "\u00a7cInvalid max port";
                            return;
                        }
                    } catch (NumberFormatException unused3) {
                        this.status = "\u00a7cInvalid min port";
                        return;
                    }
                }
                this.buttonToggle.setDisplayString(this.running ? "Stop" : "Start");
                break;
            case 2:
                File fileSaveFileChooser = MiscUtils.saveFileChooser();
                if (fileSaveFileChooser == null || fileSaveFileChooser.isDirectory()) {
                    return;
                }
                try {
                    if (!fileSaveFileChooser.exists()) {
                        fileSaveFileChooser.createNewFile();
                    }
                    FileWriter fileWriter = new FileWriter(fileSaveFileChooser);
                    fileWriter.write("Portscan\r\n");
                    fileWriter.write("Host: " + this.host + "\r\n\r\n");
                    fileWriter.write("Ports (" + this.minPort + " - " + this.maxPort + "):\r\n");
                    Iterator it = this.ports.iterator();
                    while (it.hasNext()) {
                        fileWriter.write(((Integer) it.next()) + HTTP.CRLF);
                    }
                    fileWriter.flush();
                    fileWriter.close();
                    JOptionPane.showMessageDialog((Component) null, "Exported successfully!", "Port Scanner", 1);
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    MiscUtils.showErrorPopup("Error", "Exception class: " + e.getClass().getName() + "\nMessage: " + e.getMessage());
                    break;
                }
                break;
        }
        super.actionPerformed(iGuiButton);
    }

    private void lambda$actionPerformed$0() throws IOException {
        while (this.running && this.currentPort < this.maxPort) {
            try {
                this.currentPort++;
                int i = this.currentPort;
                try {
                    Socket socket = new Socket();
                    socket.connect(new InetSocketAddress(this.host, i), SharedScopeCall.SLOW_SCOPE_CALL_THRESHOLD);
                    socket.close();
                    synchronized (this.ports) {
                        if (!this.ports.contains(Integer.valueOf(i))) {
                            this.ports.add(Integer.valueOf(i));
                        }
                    }
                } catch (Exception unused) {
                }
                if (this.checkedPort < i) {
                    this.checkedPort = i;
                }
            } catch (Exception e) {
                this.status = "\u00a7a\u00a7l" + e.getClass().getSimpleName() + ": \u00a7c" + e.getMessage();
                return;
            }
        }
        this.running = false;
        this.buttonToggle.setDisplayString("Start");
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void keyTyped(char c, int i) {
        if (1 == i) {
            f157mc.displayGuiScreen(this.prevGui);
            return;
        }
        if (15 == i) {
            TabUtils.tab(new IGuiTextField[]{this.hostField, this.minPortField, this.maxPortField});
        }
        if (this.running) {
            return;
        }
        if (this.hostField.isFocused()) {
            this.hostField.textboxKeyTyped(c, i);
        }
        if (this.minPortField.isFocused() && !Character.isLetter(c)) {
            this.minPortField.textboxKeyTyped(c, i);
        }
        if (this.maxPortField.isFocused() && !Character.isLetter(c)) {
            this.maxPortField.textboxKeyTyped(c, i);
        }
        if (this.threadsField.isFocused() && !Character.isLetter(c)) {
            this.threadsField.textboxKeyTyped(c, i);
        }
        super.keyTyped(c, i);
    }

    @Override // net.ccbluex.liquidbounce.api.util.WrappedGuiScreen
    public void mouseClicked(int i, int i2, int i3) {
        this.hostField.mouseClicked(i, i2, i3);
        this.minPortField.mouseClicked(i, i2, i3);
        this.maxPortField.mouseClicked(i, i2, i3);
        this.threadsField.mouseClicked(i, i2, i3);
        super.mouseClicked(i, i2, i3);
    }

    public void updateScreen() {
        this.hostField.updateCursorCounter();
        this.minPortField.updateCursorCounter();
        this.maxPortField.updateCursorCounter();
        this.threadsField.updateCursorCounter();
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.running = false;
    }
}
