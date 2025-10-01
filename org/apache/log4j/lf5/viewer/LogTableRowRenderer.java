package org.apache.log4j.lf5.viewer;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import org.apache.log4j.lf5.LogLevel;

/* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/LogTableRowRenderer.class */
public class LogTableRowRenderer extends DefaultTableCellRenderer {
    private static final long serialVersionUID = -3951639953706443213L;
    protected boolean _highlightFatal = true;
    protected Color _color = new Color(230, 230, 230);

    public Component getTableCellRendererComponent(JTable jTable, Object obj, boolean z, boolean z2, int i, int i2) {
        if (i % 2 == 0) {
            setBackground(this._color);
        } else {
            setBackground(Color.white);
        }
        setForeground(getLogLevelColor(jTable.getModel().getFilteredRecord(i).getLevel()));
        return super.getTableCellRendererComponent(jTable, obj, z, z2, i, i2);
    }

    protected Color getLogLevelColor(LogLevel logLevel) {
        return (Color) LogLevel.getLogLevelColorMap().get(logLevel);
    }
}
