package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.table.TableCellEditor;
import javax.swing.tree.TreeCellEditor;

/* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/categoryexplorer/CategoryAbstractCellEditor.class */
public class CategoryAbstractCellEditor implements TableCellEditor, TreeCellEditor {
    protected Object _value;
    static Class class$javax$swing$event$CellEditorListener;
    protected EventListenerList _listenerList = new EventListenerList();
    protected ChangeEvent _changeEvent = null;
    protected int _clickCountToStart = 1;

    public Object getCellEditorValue() {
        return this._value;
    }

    public void setCellEditorValue(Object obj) {
        this._value = obj;
    }

    public void setClickCountToStart(int i) {
        this._clickCountToStart = i;
    }

    public int getClickCountToStart() {
        return this._clickCountToStart;
    }

    public boolean isCellEditable(EventObject eventObject) {
        if ((eventObject instanceof MouseEvent) && ((MouseEvent) eventObject).getClickCount() < this._clickCountToStart) {
            return false;
        }
        return true;
    }

    public boolean shouldSelectCell(EventObject eventObject) {
        if (isCellEditable(eventObject)) {
            if (eventObject == null || ((MouseEvent) eventObject).getClickCount() >= this._clickCountToStart) {
                return true;
            }
            return false;
        }
        return false;
    }

    public boolean stopCellEditing() throws Throwable {
        fireEditingStopped();
        return true;
    }

    public void cancelCellEditing() throws Throwable {
        fireEditingCanceled();
    }

    public void addCellEditorListener(CellEditorListener cellEditorListener) throws Throwable {
        Class clsClass$;
        EventListenerList eventListenerList = this._listenerList;
        if (class$javax$swing$event$CellEditorListener == null) {
            clsClass$ = class$("javax.swing.event.CellEditorListener");
            class$javax$swing$event$CellEditorListener = clsClass$;
        } else {
            clsClass$ = class$javax$swing$event$CellEditorListener;
        }
        eventListenerList.add(clsClass$, cellEditorListener);
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    public void removeCellEditorListener(CellEditorListener cellEditorListener) throws Throwable {
        Class clsClass$;
        EventListenerList eventListenerList = this._listenerList;
        if (class$javax$swing$event$CellEditorListener == null) {
            clsClass$ = class$("javax.swing.event.CellEditorListener");
            class$javax$swing$event$CellEditorListener = clsClass$;
        } else {
            clsClass$ = class$javax$swing$event$CellEditorListener;
        }
        eventListenerList.remove(clsClass$, cellEditorListener);
    }

    protected void fireEditingStopped() throws Throwable {
        Class clsClass$;
        Object[] listenerList = this._listenerList.getListenerList();
        for (int length = listenerList.length - 2; length >= 0; length -= 2) {
            Object obj = listenerList[length];
            if (class$javax$swing$event$CellEditorListener == null) {
                clsClass$ = class$("javax.swing.event.CellEditorListener");
                class$javax$swing$event$CellEditorListener = clsClass$;
            } else {
                clsClass$ = class$javax$swing$event$CellEditorListener;
            }
            if (obj == clsClass$) {
                if (this._changeEvent == null) {
                    this._changeEvent = new ChangeEvent(this);
                }
                ((CellEditorListener) listenerList[length + 1]).editingStopped(this._changeEvent);
            }
        }
    }

    protected void fireEditingCanceled() throws Throwable {
        Class clsClass$;
        Object[] listenerList = this._listenerList.getListenerList();
        for (int length = listenerList.length - 2; length >= 0; length -= 2) {
            Object obj = listenerList[length];
            if (class$javax$swing$event$CellEditorListener == null) {
                clsClass$ = class$("javax.swing.event.CellEditorListener");
                class$javax$swing$event$CellEditorListener = clsClass$;
            } else {
                clsClass$ = class$javax$swing$event$CellEditorListener;
            }
            if (obj == clsClass$) {
                if (this._changeEvent == null) {
                    this._changeEvent = new ChangeEvent(this);
                }
                ((CellEditorListener) listenerList[length + 1]).editingCanceled(this._changeEvent);
            }
        }
    }
}
