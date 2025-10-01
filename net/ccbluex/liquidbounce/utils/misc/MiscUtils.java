package net.ccbluex.liquidbounce.utils.misc;

import java.awt.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import net.ccbluex.liquidbounce.utils.MinecraftInstance;

/* loaded from: L-out.jar:net/ccbluex/liquidbounce/utils/misc/MiscUtils.class */
public final class MiscUtils extends MinecraftInstance {
    public static void showErrorPopup(String str, String str2) {
        JOptionPane.showMessageDialog((Component) null, str2, str, 0);
    }

    public static void showURL(String str) {
        try {
            Desktop.getDesktop().browse(new URI(str));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static File openFileChooser() {
        if (f157mc.isFullScreen()) {
            f157mc.toggleFullscreen();
        }
        JFileChooser jFileChooser = new JFileChooser();
        JFrame jFrame = new JFrame();
        jFileChooser.setFileSelectionMode(0);
        jFrame.setVisible(true);
        jFrame.toFront();
        jFrame.setVisible(false);
        int iShowOpenDialog = jFileChooser.showOpenDialog(jFrame);
        jFrame.dispose();
        if (iShowOpenDialog == 0) {
            return jFileChooser.getSelectedFile();
        }
        return null;
    }

    public static File saveFileChooser() {
        if (f157mc.isFullScreen()) {
            f157mc.toggleFullscreen();
        }
        JFileChooser jFileChooser = new JFileChooser();
        JFrame jFrame = new JFrame();
        jFileChooser.setFileSelectionMode(0);
        jFrame.setVisible(true);
        jFrame.toFront();
        jFrame.setVisible(false);
        int iShowSaveDialog = jFileChooser.showSaveDialog(jFrame);
        jFrame.dispose();
        if (iShowSaveDialog == 0) {
            return jFileChooser.getSelectedFile();
        }
        return null;
    }
}
