package org.apache.log4j.lf5.viewer;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

/* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/LogFactor5LoadingDialog.class */
public class LogFactor5LoadingDialog extends LogFactor5Dialog {
    public LogFactor5LoadingDialog(JFrame jFrame, String str) {
        super(jFrame, "LogFactor5", false);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(new GridBagLayout());
        wrapStringOnPanel(str, jPanel2);
        getContentPane().add(jPanel2, "Center");
        getContentPane().add(jPanel, "South");
        show();
    }
}
