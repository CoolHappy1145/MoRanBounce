package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import kotlin.text.Typography;

/* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/categoryexplorer/CategoryNodeRenderer.class */
public class CategoryNodeRenderer extends DefaultTreeCellRenderer {
    private static final long serialVersionUID = -6046702673278595048L;
    protected JCheckBox _checkBox = new JCheckBox();
    protected JPanel _panel = new JPanel();
    public static final Color FATAL_CHILDREN = new Color(Typography.half, 113, 0);
    protected static ImageIcon _sat = null;

    public CategoryNodeRenderer() {
        this._panel.setBackground(UIManager.getColor("Tree.textBackground"));
        if (_sat == null) {
            _sat = new ImageIcon(getClass().getResource("/org/apache/log4j/lf5/viewer/images/channelexplorer_satellite.gif"));
        }
        setOpaque(false);
        this._checkBox.setOpaque(false);
        this._panel.setOpaque(false);
        this._panel.setLayout(new FlowLayout(0, 0, 0));
        this._panel.add(this._checkBox);
        this._panel.add(this);
        setOpenIcon(_sat);
        setClosedIcon(_sat);
        setLeafIcon(_sat);
    }

    public Component getTreeCellRendererComponent(JTree jTree, Object obj, boolean z, boolean z2, boolean z3, int i, boolean z4) {
        CategoryNode categoryNode = (CategoryNode) obj;
        super.getTreeCellRendererComponent(jTree, obj, z, z2, z3, i, z4);
        if (i == 0) {
            this._checkBox.setVisible(false);
        } else {
            this._checkBox.setVisible(true);
            this._checkBox.setSelected(categoryNode.isSelected());
        }
        this._panel.setToolTipText(buildToolTip(categoryNode));
        if (categoryNode.hasFatalChildren()) {
            setForeground(FATAL_CHILDREN);
        }
        if (categoryNode.hasFatalRecords()) {
            setForeground(Color.red);
        }
        return this._panel;
    }

    public Dimension getCheckBoxOffset() {
        return new Dimension(0, 0);
    }

    protected String buildToolTip(CategoryNode categoryNode) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(categoryNode.getTitle()).append(" contains a total of ");
        stringBuffer.append(categoryNode.getTotalNumberOfRecords());
        stringBuffer.append(" LogRecords.");
        stringBuffer.append(" Right-click for more info.");
        return stringBuffer.toString();
    }
}
