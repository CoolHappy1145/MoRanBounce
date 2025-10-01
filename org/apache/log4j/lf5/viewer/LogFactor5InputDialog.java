package org.apache.log4j.lf5.viewer;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/LogFactor5InputDialog.class */
public class LogFactor5InputDialog extends LogFactor5Dialog {
    public static final int SIZE = 30;
    private JTextField _textField;

    public LogFactor5InputDialog(JFrame jFrame, String str, String str2) {
        this(jFrame, str, str2, 30);
    }

    public LogFactor5InputDialog(JFrame jFrame, String str, String str2, int i) {
        super(jFrame, str, true);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout(new FlowLayout());
        jPanel2.add(new JLabel(str2));
        this._textField = new JTextField(i);
        jPanel2.add(this._textField);
        addKeyListener(new KeyAdapter(this) { // from class: org.apache.log4j.lf5.viewer.LogFactor5InputDialog.1
            private final LogFactor5InputDialog this$0;

            {
                this.this$0 = this;
            }

            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == 10) {
                    this.this$0.hide();
                }
            }
        });
        JButton jButton = new JButton("Ok");
        jButton.addActionListener(new ActionListener(this) { // from class: org.apache.log4j.lf5.viewer.LogFactor5InputDialog.2
            private final LogFactor5InputDialog this$0;

            {
                this.this$0 = this;
            }

            public void actionPerformed(ActionEvent actionEvent) {
                this.this$0.hide();
            }
        });
        JButton jButton2 = new JButton("Cancel");
        jButton2.addActionListener(new ActionListener(this) { // from class: org.apache.log4j.lf5.viewer.LogFactor5InputDialog.3
            private final LogFactor5InputDialog this$0;

            {
                this.this$0 = this;
            }

            public void actionPerformed(ActionEvent actionEvent) {
                this.this$0.hide();
                this.this$0._textField.setText("");
            }
        });
        jPanel.add(jButton);
        jPanel.add(jButton2);
        getContentPane().add(jPanel2, "Center");
        getContentPane().add(jPanel, "South");
        pack();
        centerWindow(this);
        show();
    }

    public String getText() {
        String text = this._textField.getText();
        if (text != null && text.trim().length() == 0) {
            return null;
        }
        return text;
    }
}
