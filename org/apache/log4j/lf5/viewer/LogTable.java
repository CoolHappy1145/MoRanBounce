package org.apache.log4j.lf5.viewer;

import java.awt.Font;
import java.awt.Graphics;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import org.apache.log4j.lf5.util.DateFormatManager;

/* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/LogTable.class */
public class LogTable extends JTable {
    private static final long serialVersionUID = 4867085140195148458L;
    protected JTextArea _detailTextArea;
    protected int _rowHeight = 30;
    protected int _numCols = 9;
    protected TableColumn[] _tableColumns = new TableColumn[this._numCols];
    protected int[] _colWidths = {40, 40, 40, 70, 70, 360, 440, SharedScopeCall.FAST_SCOPE_GET_THRESHOLD, 60};
    protected LogTableColumn[] _colNames = LogTableColumn.getLogTableColumnArray();
    protected int _colDate = 0;
    protected int _colThread = 1;
    protected int _colMessageNum = 2;
    protected int _colLevel = 3;
    protected int _colNDC = 4;
    protected int _colCategory = 5;
    protected int _colMessage = 6;
    protected int _colLocation = 7;
    protected int _colThrown = 8;
    protected DateFormatManager _dateFormatManager = null;

    public LogTable(JTextArea jTextArea) {
        init();
        this._detailTextArea = jTextArea;
        setModel(new FilteredLogTableModel());
        Enumeration columns = getColumnModel().getColumns();
        int i = 0;
        while (columns.hasMoreElements()) {
            TableColumn tableColumn = (TableColumn) columns.nextElement();
            tableColumn.setCellRenderer(new LogTableRowRenderer());
            tableColumn.setPreferredWidth(this._colWidths[i]);
            this._tableColumns[i] = tableColumn;
            i++;
        }
        getSelectionModel().addListSelectionListener(new LogTableListSelectionListener(this, this));
    }

    public DateFormatManager getDateFormatManager() {
        return this._dateFormatManager;
    }

    public void setDateFormatManager(DateFormatManager dateFormatManager) {
        this._dateFormatManager = dateFormatManager;
    }

    public void clearLogRecords() {
        getFilteredLogTableModel().clear();
    }

    public FilteredLogTableModel getFilteredLogTableModel() {
        return getModel();
    }

    public void setDetailedView() {
        TableColumnModel columnModel = getColumnModel();
        for (int i = 0; i < this._numCols; i++) {
            columnModel.removeColumn(this._tableColumns[i]);
        }
        for (int i2 = 0; i2 < this._numCols; i2++) {
            columnModel.addColumn(this._tableColumns[i2]);
        }
        sizeColumnsToFit(-1);
    }

    public void setView(List list) {
        TableColumnModel columnModel = getColumnModel();
        for (int i = 0; i < this._numCols; i++) {
            columnModel.removeColumn(this._tableColumns[i]);
        }
        Iterator it = list.iterator();
        Vector columnNameAndNumber = getColumnNameAndNumber();
        while (it.hasNext()) {
            columnModel.addColumn(this._tableColumns[columnNameAndNumber.indexOf(it.next())]);
        }
        sizeColumnsToFit(-1);
    }

    public void setFont(Font font) {
        super.setFont(font);
        Graphics graphics = getGraphics();
        if (graphics != null) {
            int height = graphics.getFontMetrics(font).getHeight();
            this._rowHeight = height + (height / 3);
            setRowHeight(this._rowHeight);
        }
    }

    protected void init() {
        setRowHeight(this._rowHeight);
        setSelectionMode(0);
    }

    protected Vector getColumnNameAndNumber() {
        Vector vector = new Vector();
        for (int i = 0; i < this._colNames.length; i++) {
            vector.add(i, this._colNames[i]);
        }
        return vector;
    }

    /* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/LogTable$LogTableListSelectionListener.class */
    class LogTableListSelectionListener implements ListSelectionListener {
        protected JTable _table;
        private final LogTable this$0;

        public LogTableListSelectionListener(LogTable logTable, JTable jTable) {
            this.this$0 = logTable;
            this._table = jTable;
        }

        public void valueChanged(ListSelectionEvent listSelectionEvent) {
            if (listSelectionEvent.getValueIsAdjusting()) {
                return;
            }
            ListSelectionModel listSelectionModel = (ListSelectionModel) listSelectionEvent.getSource();
            if (!listSelectionModel.isSelectionEmpty()) {
                StringBuffer stringBuffer = new StringBuffer();
                int minSelectionIndex = listSelectionModel.getMinSelectionIndex();
                for (int i = 0; i < this.this$0._numCols - 1; i++) {
                    String string = "";
                    Object valueAt = this._table.getModel().getValueAt(minSelectionIndex, i);
                    if (valueAt != null) {
                        string = valueAt.toString();
                    }
                    stringBuffer.append(new StringBuffer().append(this.this$0._colNames[i]).append(CallSiteDescriptor.TOKEN_DELIMITER).toString());
                    stringBuffer.append("\t");
                    if (i == this.this$0._colThread || i == this.this$0._colMessage || i == this.this$0._colLevel) {
                        stringBuffer.append("\t");
                    }
                    if (i == this.this$0._colDate || i == this.this$0._colNDC) {
                        stringBuffer.append("\t\t");
                    }
                    stringBuffer.append(string);
                    stringBuffer.append("\n");
                }
                stringBuffer.append(new StringBuffer().append(this.this$0._colNames[this.this$0._numCols - 1]).append(":\n").toString());
                Object valueAt2 = this._table.getModel().getValueAt(minSelectionIndex, this.this$0._numCols - 1);
                if (valueAt2 != null) {
                    stringBuffer.append(valueAt2.toString());
                }
                this.this$0._detailTextArea.setText(stringBuffer.toString());
            }
        }
    }
}
