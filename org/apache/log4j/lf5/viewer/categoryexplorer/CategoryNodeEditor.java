package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import javax.swing.JCheckBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;

/* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/categoryexplorer/CategoryNodeEditor.class */
public class CategoryNodeEditor extends CategoryAbstractCellEditor {
    protected CategoryNode _lastEditedNode;
    protected CategoryExplorerModel _categoryModel;
    protected JTree _tree;
    protected CategoryNodeEditorRenderer _renderer = new CategoryNodeEditorRenderer();
    protected JCheckBox _checkBox = this._renderer.getCheckBox();

    public CategoryNodeEditor(CategoryExplorerModel categoryExplorerModel) {
        this._categoryModel = categoryExplorerModel;
        this._checkBox.addActionListener(new ActionListener(this) { // from class: org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNodeEditor.1
            private final CategoryNodeEditor this$0;

            {
                this.this$0 = this;
            }

            public void actionPerformed(ActionEvent actionEvent) {
                this.this$0._categoryModel.update(this.this$0._lastEditedNode, this.this$0._checkBox.isSelected());
                this.this$0.stopCellEditing();
            }
        });
        this._renderer.addMouseListener(new MouseAdapter(this) { // from class: org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNodeEditor.2
            private final CategoryNodeEditor this$0;

            {
                this.this$0 = this;
            }

            public void mousePressed(MouseEvent mouseEvent) {
                if ((mouseEvent.getModifiers() & 4) != 0) {
                    this.this$0.showPopup(this.this$0._lastEditedNode, mouseEvent.getX(), mouseEvent.getY());
                }
                this.this$0.stopCellEditing();
            }
        });
    }

    public Component getTreeCellEditorComponent(JTree jTree, Object obj, boolean z, boolean z2, boolean z3, int i) {
        this._lastEditedNode = (CategoryNode) obj;
        this._tree = jTree;
        return this._renderer.getTreeCellRendererComponent(jTree, obj, z, z2, z3, i, true);
    }

    @Override // org.apache.log4j.lf5.viewer.categoryexplorer.CategoryAbstractCellEditor
    public Object getCellEditorValue() {
        return this._lastEditedNode.getUserObject();
    }

    protected JMenuItem createPropertiesMenuItem(CategoryNode categoryNode) {
        JMenuItem jMenuItem = new JMenuItem("Properties");
        jMenuItem.addActionListener(new ActionListener(this, categoryNode) { // from class: org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNodeEditor.3
            private final CategoryNode val$node;
            private final CategoryNodeEditor this$0;

            {
                this.this$0 = this;
                this.val$node = categoryNode;
            }

            public void actionPerformed(ActionEvent actionEvent) {
                this.this$0.showPropertiesDialog(this.val$node);
            }
        });
        return jMenuItem;
    }

    protected void showPropertiesDialog(CategoryNode categoryNode) {
        JOptionPane.showMessageDialog(this._tree, getDisplayedProperties(categoryNode), new StringBuffer().append("Category Properties: ").append(categoryNode.getTitle()).toString(), -1);
    }

    protected Object getDisplayedProperties(CategoryNode categoryNode) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new StringBuffer().append("Category: ").append(categoryNode.getTitle()).toString());
        if (categoryNode.hasFatalRecords()) {
            arrayList.add("Contains at least one fatal LogRecord.");
        }
        if (categoryNode.hasFatalChildren()) {
            arrayList.add("Contains descendants with a fatal LogRecord.");
        }
        arrayList.add(new StringBuffer().append("LogRecords in this category alone: ").append(categoryNode.getNumberOfContainedRecords()).toString());
        arrayList.add(new StringBuffer().append("LogRecords in descendant categories: ").append(categoryNode.getNumberOfRecordsFromChildren()).toString());
        arrayList.add(new StringBuffer().append("LogRecords in this category including descendants: ").append(categoryNode.getTotalNumberOfRecords()).toString());
        return arrayList.toArray();
    }

    protected void showPopup(CategoryNode categoryNode, int i, int i2) {
        JPopupMenu jPopupMenu = new JPopupMenu();
        jPopupMenu.setSize(150, 400);
        if (categoryNode.getParent() == null) {
            jPopupMenu.add(createRemoveMenuItem());
            jPopupMenu.addSeparator();
        }
        jPopupMenu.add(createSelectDescendantsMenuItem(categoryNode));
        jPopupMenu.add(createUnselectDescendantsMenuItem(categoryNode));
        jPopupMenu.addSeparator();
        jPopupMenu.add(createExpandMenuItem(categoryNode));
        jPopupMenu.add(createCollapseMenuItem(categoryNode));
        jPopupMenu.addSeparator();
        jPopupMenu.add(createPropertiesMenuItem(categoryNode));
        jPopupMenu.show(this._renderer, i, i2);
    }

    protected JMenuItem createSelectDescendantsMenuItem(CategoryNode categoryNode) {
        JMenuItem jMenuItem = new JMenuItem("Select All Descendant Categories");
        jMenuItem.addActionListener(new ActionListener(this, categoryNode) { // from class: org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNodeEditor.4
            private final CategoryNode val$node;
            private final CategoryNodeEditor this$0;

            {
                this.this$0 = this;
                this.val$node = categoryNode;
            }

            public void actionPerformed(ActionEvent actionEvent) {
                this.this$0._categoryModel.setDescendantSelection(this.val$node, true);
            }
        });
        return jMenuItem;
    }

    protected JMenuItem createUnselectDescendantsMenuItem(CategoryNode categoryNode) {
        JMenuItem jMenuItem = new JMenuItem("Deselect All Descendant Categories");
        jMenuItem.addActionListener(new ActionListener(this, categoryNode) { // from class: org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNodeEditor.5
            private final CategoryNode val$node;
            private final CategoryNodeEditor this$0;

            {
                this.this$0 = this;
                this.val$node = categoryNode;
            }

            public void actionPerformed(ActionEvent actionEvent) {
                this.this$0._categoryModel.setDescendantSelection(this.val$node, false);
            }
        });
        return jMenuItem;
    }

    protected JMenuItem createExpandMenuItem(CategoryNode categoryNode) {
        JMenuItem jMenuItem = new JMenuItem("Expand All Descendant Categories");
        jMenuItem.addActionListener(new ActionListener(this, categoryNode) { // from class: org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNodeEditor.6
            private final CategoryNode val$node;
            private final CategoryNodeEditor this$0;

            {
                this.this$0 = this;
                this.val$node = categoryNode;
            }

            public void actionPerformed(ActionEvent actionEvent) {
                this.this$0.expandDescendants(this.val$node);
            }
        });
        return jMenuItem;
    }

    protected JMenuItem createCollapseMenuItem(CategoryNode categoryNode) {
        JMenuItem jMenuItem = new JMenuItem("Collapse All Descendant Categories");
        jMenuItem.addActionListener(new ActionListener(this, categoryNode) { // from class: org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNodeEditor.7
            private final CategoryNode val$node;
            private final CategoryNodeEditor this$0;

            {
                this.this$0 = this;
                this.val$node = categoryNode;
            }

            public void actionPerformed(ActionEvent actionEvent) {
                this.this$0.collapseDescendants(this.val$node);
            }
        });
        return jMenuItem;
    }

    protected JMenuItem createRemoveMenuItem() {
        JMenuItem jMenuItem = new JMenuItem("Remove All Empty Categories");
        jMenuItem.addActionListener(new ActionListener(this) { // from class: org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNodeEditor.8
            private final CategoryNodeEditor this$0;

            {
                this.this$0 = this;
            }

            public void actionPerformed(ActionEvent actionEvent) {
                while (this.this$0.removeUnusedNodes() > 0) {
                }
            }
        });
        return jMenuItem;
    }

    protected void expandDescendants(CategoryNode categoryNode) {
        Enumeration enumerationDepthFirstEnumeration = categoryNode.depthFirstEnumeration();
        while (enumerationDepthFirstEnumeration.hasMoreElements()) {
            expand((CategoryNode) enumerationDepthFirstEnumeration.nextElement());
        }
    }

    protected void collapseDescendants(CategoryNode categoryNode) {
        Enumeration enumerationDepthFirstEnumeration = categoryNode.depthFirstEnumeration();
        while (enumerationDepthFirstEnumeration.hasMoreElements()) {
            collapse((CategoryNode) enumerationDepthFirstEnumeration.nextElement());
        }
    }

    protected int removeUnusedNodes() {
        int i = 0;
        Enumeration enumerationDepthFirstEnumeration = this._categoryModel.getRootCategoryNode().depthFirstEnumeration();
        while (enumerationDepthFirstEnumeration.hasMoreElements()) {
            MutableTreeNode mutableTreeNode = (CategoryNode) enumerationDepthFirstEnumeration.nextElement();
            if (mutableTreeNode.isLeaf() && mutableTreeNode.getNumberOfContainedRecords() == 0 && mutableTreeNode.getParent() != null) {
                this._categoryModel.removeNodeFromParent(mutableTreeNode);
                i++;
            }
        }
        return i;
    }

    protected void expand(CategoryNode categoryNode) {
        this._tree.expandPath(getTreePath(categoryNode));
    }

    protected TreePath getTreePath(CategoryNode categoryNode) {
        return new TreePath(categoryNode.getPath());
    }

    protected void collapse(CategoryNode categoryNode) {
        this._tree.collapsePath(getTreePath(categoryNode));
    }
}
