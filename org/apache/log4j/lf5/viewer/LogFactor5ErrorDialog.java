package org.apache.log4j.lf5.viewer;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/LogFactor5ErrorDialog.class */
public class LogFactor5ErrorDialog extends LogFactor5Dialog {
    public LogFactor5ErrorDialog(JFrame jFrame, String str) {
        super(jFrame, "Error", true);
        JButton jButton = new JButton("Ok");
        jButton.addActionListener(new ActionListener(this) { // from class: org.apache.log4j.lf5.viewer.LogFactor5ErrorDialog.1
            private final LogFactor5ErrorDialog this$0;

            {
                this.this$0 = this;
            }

            public void actionPerformed(ActionEvent actionEvent) {
                this.this$0.hide();
            }
        });
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.add(jButton);
        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(new GridBagLayout());
        wrapStringOnPanel(str, jPanel2);
        getContentPane().add(jPanel2, "Center");
        getContentPane().add(jPanel, "South");
        show();
    }
}
