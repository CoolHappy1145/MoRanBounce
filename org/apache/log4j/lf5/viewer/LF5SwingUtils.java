package org.apache.log4j.lf5.viewer;

import java.awt.Adjustable;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.table.TableModel;

/* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/LF5SwingUtils.class */
public class LF5SwingUtils {
    public static void selectRow(int i, JTable jTable, JScrollPane jScrollPane) {
        if (jTable == null || jScrollPane == null || !contains(i, jTable.getModel())) {
            return;
        }
        moveAdjustable(i * jTable.getRowHeight(), jScrollPane.getVerticalScrollBar());
        selectRow(i, jTable.getSelectionModel());
        repaintLater(jTable);
    }

    public static void makeScrollBarTrack(Adjustable adjustable) {
        if (adjustable == null) {
            return;
        }
        adjustable.addAdjustmentListener(new TrackingAdjustmentListener());
    }

    public static void makeVerticalScrollBarTrack(JScrollPane jScrollPane) {
        if (jScrollPane == null) {
            return;
        }
        makeScrollBarTrack(jScrollPane.getVerticalScrollBar());
    }

    protected static boolean contains(int i, TableModel tableModel) {
        if (tableModel == null || i < 0 || i >= tableModel.getRowCount()) {
            return false;
        }
        return true;
    }

    protected static void selectRow(int i, ListSelectionModel listSelectionModel) {
        if (listSelectionModel == null) {
            return;
        }
        listSelectionModel.setSelectionInterval(i, i);
    }

    protected static void moveAdjustable(int i, Adjustable adjustable) {
        if (adjustable == null) {
            return;
        }
        adjustable.setValue(i);
    }

    protected static void repaintLater(JComponent jComponent) {
        SwingUtilities.invokeLater(new Runnable(jComponent) { // from class: org.apache.log4j.lf5.viewer.LF5SwingUtils.1
            private final JComponent val$component;

            {
                this.val$component = jComponent;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.val$component.repaint();
            }
        });
    }
}
