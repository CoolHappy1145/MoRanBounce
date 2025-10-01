package org.apache.log4j.lf5.viewer;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.Toolkit;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JFrame;
import jdk.nashorn.internal.codegen.SharedScopeCall;

/* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/LogFactor5Dialog.class */
public abstract class LogFactor5Dialog extends JDialog {
    protected static final Font DISPLAY_FONT = new Font("Arial", 1, 12);

    protected LogFactor5Dialog(JFrame jFrame, String str, boolean z) {
        super(jFrame, str, z);
    }

    public void show() {
        pack();
        minimumSizeDialog(this, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 100);
        centerWindow(this);
        super.show();
    }

    protected void centerWindow(Window window) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (screenSize.width < window.getSize().width) {
            window.setSize(screenSize.width, window.getSize().height);
        }
        if (screenSize.height < window.getSize().height) {
            window.setSize(window.getSize().width, screenSize.height);
        }
        window.setLocation((screenSize.width - window.getSize().width) / 2, (screenSize.height - window.getSize().height) / 2);
    }

    protected void wrapStringOnPanel(String str, Container container) {
        String strSubstring;
        String strSubstring2;
        GridBagConstraints defaultConstraints = getDefaultConstraints();
        defaultConstraints.gridwidth = 0;
        defaultConstraints.insets = new Insets(0, 0, 0, 0);
        GridBagLayout layout = container.getLayout();
        while (str.length() > 0) {
            int iIndexOf = str.indexOf(10);
            if (iIndexOf >= 0) {
                strSubstring = str.substring(0, iIndexOf);
                strSubstring2 = str.substring(iIndexOf + 1);
            } else {
                strSubstring = str;
                strSubstring2 = "";
            }
            str = strSubstring2;
            Label label = new Label(strSubstring);
            label.setFont(DISPLAY_FONT);
            layout.setConstraints(label, defaultConstraints);
            container.add(label);
        }
    }

    protected GridBagConstraints getDefaultConstraints() {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.weightx = 1.0d;
        gridBagConstraints.weighty = 1.0d;
        gridBagConstraints.gridheight = 1;
        gridBagConstraints.insets = new Insets(4, 4, 4, 4);
        gridBagConstraints.fill = 0;
        gridBagConstraints.anchor = 17;
        return gridBagConstraints;
    }

    protected void minimumSizeDialog(Component component, int i, int i2) {
        if (component.getSize().width < i) {
            component.setSize(i, component.getSize().height);
        }
        if (component.getSize().height < i2) {
            component.setSize(component.getSize().width, i2);
        }
    }
}
