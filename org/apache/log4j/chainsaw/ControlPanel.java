package org.apache.log4j.chainsaw;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.Priority;

/* loaded from: L-out.jar:org/apache/log4j/chainsaw/ControlPanel.class */
class ControlPanel extends JPanel {
    private static final Logger LOG;
    static Class class$org$apache$log4j$chainsaw$ControlPanel;

    static {
        Class clsClass$;
        if (class$org$apache$log4j$chainsaw$ControlPanel == null) {
            clsClass$ = class$("org.apache.log4j.chainsaw.ControlPanel");
            class$org$apache$log4j$chainsaw$ControlPanel = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$chainsaw$ControlPanel;
        }
        LOG = Logger.getLogger(clsClass$);
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    ControlPanel(MyTableModel myTableModel) {
        setBorder(BorderFactory.createTitledBorder("Controls: "));
        GridBagLayout gridBagLayout = new GridBagLayout();
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        setLayout(gridBagLayout);
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.ipady = 5;
        gridBagConstraints.gridx = 0;
        gridBagConstraints.anchor = 13;
        gridBagConstraints.gridy = 0;
        JLabel jLabel = new JLabel("Filter Level:");
        gridBagLayout.setConstraints(jLabel, gridBagConstraints);
        add(jLabel);
        gridBagConstraints.gridy++;
        JLabel jLabel2 = new JLabel("Filter Thread:");
        gridBagLayout.setConstraints(jLabel2, gridBagConstraints);
        add(jLabel2);
        gridBagConstraints.gridy++;
        JLabel jLabel3 = new JLabel("Filter Logger:");
        gridBagLayout.setConstraints(jLabel3, gridBagConstraints);
        add(jLabel3);
        gridBagConstraints.gridy++;
        JLabel jLabel4 = new JLabel("Filter NDC:");
        gridBagLayout.setConstraints(jLabel4, gridBagConstraints);
        add(jLabel4);
        gridBagConstraints.gridy++;
        JLabel jLabel5 = new JLabel("Filter Message:");
        gridBagLayout.setConstraints(jLabel5, gridBagConstraints);
        add(jLabel5);
        gridBagConstraints.weightx = 1.0d;
        gridBagConstraints.gridx = 1;
        gridBagConstraints.anchor = 17;
        gridBagConstraints.gridy = 0;
        Level[] levelArr = {Level.FATAL, Level.ERROR, Level.WARN, Level.INFO, Level.DEBUG, Level.TRACE};
        JComboBox jComboBox = new JComboBox(levelArr);
        Level level = levelArr[levelArr.length - 1];
        jComboBox.setSelectedItem(level);
        myTableModel.setPriorityFilter(level);
        gridBagLayout.setConstraints(jComboBox, gridBagConstraints);
        add(jComboBox);
        jComboBox.setEditable(false);
        jComboBox.addActionListener(new ActionListener(this, myTableModel, jComboBox) { // from class: org.apache.log4j.chainsaw.ControlPanel.1
            private final MyTableModel val$aModel;
            private final JComboBox val$priorities;
            private final ControlPanel this$0;

            {
                this.this$0 = this;
                this.val$aModel = myTableModel;
                this.val$priorities = jComboBox;
            }

            public void actionPerformed(ActionEvent actionEvent) {
                this.val$aModel.setPriorityFilter((Priority) this.val$priorities.getSelectedItem());
            }
        });
        gridBagConstraints.fill = 2;
        gridBagConstraints.gridy++;
        JTextField jTextField = new JTextField("");
        jTextField.getDocument().addDocumentListener(new DocumentListener(this, myTableModel, jTextField) { // from class: org.apache.log4j.chainsaw.ControlPanel.2
            private final MyTableModel val$aModel;
            private final JTextField val$threadField;
            private final ControlPanel this$0;

            {
                this.this$0 = this;
                this.val$aModel = myTableModel;
                this.val$threadField = jTextField;
            }

            public void insertUpdate(DocumentEvent documentEvent) {
                this.val$aModel.setThreadFilter(this.val$threadField.getText());
            }

            public void removeUpdate(DocumentEvent documentEvent) {
                this.val$aModel.setThreadFilter(this.val$threadField.getText());
            }

            public void changedUpdate(DocumentEvent documentEvent) {
                this.val$aModel.setThreadFilter(this.val$threadField.getText());
            }
        });
        gridBagLayout.setConstraints(jTextField, gridBagConstraints);
        add(jTextField);
        gridBagConstraints.gridy++;
        JTextField jTextField2 = new JTextField("");
        jTextField2.getDocument().addDocumentListener(new DocumentListener(this, myTableModel, jTextField2) { // from class: org.apache.log4j.chainsaw.ControlPanel.3
            private final MyTableModel val$aModel;
            private final JTextField val$catField;
            private final ControlPanel this$0;

            {
                this.this$0 = this;
                this.val$aModel = myTableModel;
                this.val$catField = jTextField2;
            }

            public void insertUpdate(DocumentEvent documentEvent) {
                this.val$aModel.setCategoryFilter(this.val$catField.getText());
            }

            public void removeUpdate(DocumentEvent documentEvent) {
                this.val$aModel.setCategoryFilter(this.val$catField.getText());
            }

            public void changedUpdate(DocumentEvent documentEvent) {
                this.val$aModel.setCategoryFilter(this.val$catField.getText());
            }
        });
        gridBagLayout.setConstraints(jTextField2, gridBagConstraints);
        add(jTextField2);
        gridBagConstraints.gridy++;
        JTextField jTextField3 = new JTextField("");
        jTextField3.getDocument().addDocumentListener(new DocumentListener(this, myTableModel, jTextField3) { // from class: org.apache.log4j.chainsaw.ControlPanel.4
            private final MyTableModel val$aModel;
            private final JTextField val$ndcField;
            private final ControlPanel this$0;

            {
                this.this$0 = this;
                this.val$aModel = myTableModel;
                this.val$ndcField = jTextField3;
            }

            public void insertUpdate(DocumentEvent documentEvent) {
                this.val$aModel.setNDCFilter(this.val$ndcField.getText());
            }

            public void removeUpdate(DocumentEvent documentEvent) {
                this.val$aModel.setNDCFilter(this.val$ndcField.getText());
            }

            public void changedUpdate(DocumentEvent documentEvent) {
                this.val$aModel.setNDCFilter(this.val$ndcField.getText());
            }
        });
        gridBagLayout.setConstraints(jTextField3, gridBagConstraints);
        add(jTextField3);
        gridBagConstraints.gridy++;
        JTextField jTextField4 = new JTextField("");
        jTextField4.getDocument().addDocumentListener(new DocumentListener(this, myTableModel, jTextField4) { // from class: org.apache.log4j.chainsaw.ControlPanel.5
            private final MyTableModel val$aModel;
            private final JTextField val$msgField;
            private final ControlPanel this$0;

            {
                this.this$0 = this;
                this.val$aModel = myTableModel;
                this.val$msgField = jTextField4;
            }

            public void insertUpdate(DocumentEvent documentEvent) {
                this.val$aModel.setMessageFilter(this.val$msgField.getText());
            }

            public void removeUpdate(DocumentEvent documentEvent) {
                this.val$aModel.setMessageFilter(this.val$msgField.getText());
            }

            public void changedUpdate(DocumentEvent documentEvent) {
                this.val$aModel.setMessageFilter(this.val$msgField.getText());
            }
        });
        gridBagLayout.setConstraints(jTextField4, gridBagConstraints);
        add(jTextField4);
        gridBagConstraints.weightx = 0.0d;
        gridBagConstraints.fill = 2;
        gridBagConstraints.anchor = 13;
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        JButton jButton = new JButton("Exit");
        jButton.setMnemonic('x');
        jButton.addActionListener(ExitAction.INSTANCE);
        gridBagLayout.setConstraints(jButton, gridBagConstraints);
        add(jButton);
        gridBagConstraints.gridy++;
        JButton jButton2 = new JButton("Clear");
        jButton2.setMnemonic('c');
        jButton2.addActionListener(new ActionListener(this, myTableModel) { // from class: org.apache.log4j.chainsaw.ControlPanel.6
            private final MyTableModel val$aModel;
            private final ControlPanel this$0;

            {
                this.this$0 = this;
                this.val$aModel = myTableModel;
            }

            public void actionPerformed(ActionEvent actionEvent) {
                this.val$aModel.clear();
            }
        });
        gridBagLayout.setConstraints(jButton2, gridBagConstraints);
        add(jButton2);
        gridBagConstraints.gridy++;
        JButton jButton3 = new JButton("Pause");
        jButton3.setMnemonic('p');
        jButton3.addActionListener(new ActionListener(this, myTableModel, jButton3) { // from class: org.apache.log4j.chainsaw.ControlPanel.7
            private final MyTableModel val$aModel;
            private final JButton val$toggleButton;
            private final ControlPanel this$0;

            {
                this.this$0 = this;
                this.val$aModel = myTableModel;
                this.val$toggleButton = jButton3;
            }

            public void actionPerformed(ActionEvent actionEvent) {
                this.val$aModel.toggle();
                this.val$toggleButton.setText(this.val$aModel.isPaused() ? "Resume" : "Pause");
            }
        });
        gridBagLayout.setConstraints(jButton3, gridBagConstraints);
        add(jButton3);
    }
}
