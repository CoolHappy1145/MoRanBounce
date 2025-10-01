package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.AWTEventMulticaster;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import org.apache.log4j.lf5.LogRecord;

/* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/categoryexplorer/CategoryExplorerModel.class */
public class CategoryExplorerModel extends DefaultTreeModel {
    private static final long serialVersionUID = -3413887384316015901L;
    protected boolean _renderFatal;
    protected ActionListener _listener;
    protected ActionEvent _event;

    public CategoryExplorerModel(CategoryNode categoryNode) {
        super(categoryNode);
        this._renderFatal = true;
        this._listener = null;
        this._event = new ActionEvent(this, 1001, "Nodes Selection changed");
    }

    public void addLogRecord(LogRecord logRecord) {
        CategoryPath categoryPath = new CategoryPath(logRecord.getCategory());
        addCategory(categoryPath);
        CategoryNode categoryNode = getCategoryNode(categoryPath);
        categoryNode.addRecord();
        if (this._renderFatal && logRecord.isFatal()) {
            CategoryNode[] pathToRoot = getPathToRoot(categoryNode);
            int length = pathToRoot.length;
            for (int i = 1; i < length - 1; i++) {
                CategoryNode categoryNode2 = pathToRoot[i];
                categoryNode2.setHasFatalChildren(true);
                nodeChanged(categoryNode2);
            }
            categoryNode.setHasFatalRecords(true);
            nodeChanged(categoryNode);
        }
    }

    public CategoryNode getRootCategoryNode() {
        return (CategoryNode) getRoot();
    }

    public CategoryNode getCategoryNode(String str) {
        return getCategoryNode(new CategoryPath(str));
    }

    public CategoryNode getCategoryNode(CategoryPath categoryPath) {
        CategoryNode categoryNode = (CategoryNode) getRoot();
        for (int i = 0; i < categoryPath.size(); i++) {
            CategoryElement categoryElementCategoryElementAt = categoryPath.categoryElementAt(i);
            Enumeration enumerationChildren = categoryNode.children();
            boolean z = false;
            while (true) {
                if (!enumerationChildren.hasMoreElements()) {
                    break;
                }
                CategoryNode categoryNode2 = (CategoryNode) enumerationChildren.nextElement();
                if (categoryNode2.getTitle().toLowerCase().equals(categoryElementCategoryElementAt.getTitle().toLowerCase())) {
                    z = true;
                    categoryNode = categoryNode2;
                    break;
                }
            }
            if (!z) {
                return null;
            }
        }
        return categoryNode;
    }

    public boolean isCategoryPathActive(CategoryPath categoryPath) {
        CategoryNode categoryNode = (CategoryNode) getRoot();
        boolean z = false;
        for (int i = 0; i < categoryPath.size(); i++) {
            CategoryElement categoryElementCategoryElementAt = categoryPath.categoryElementAt(i);
            Enumeration enumerationChildren = categoryNode.children();
            boolean z2 = false;
            z = false;
            while (true) {
                if (!enumerationChildren.hasMoreElements()) {
                    break;
                }
                CategoryNode categoryNode2 = (CategoryNode) enumerationChildren.nextElement();
                if (categoryNode2.getTitle().toLowerCase().equals(categoryElementCategoryElementAt.getTitle().toLowerCase())) {
                    z2 = true;
                    categoryNode = categoryNode2;
                    if (categoryNode.isSelected()) {
                        z = true;
                    }
                }
            }
            if (!z || !z2) {
                return false;
            }
        }
        return z;
    }

    public CategoryNode addCategory(CategoryPath categoryPath) {
        CategoryNode categoryNode = (CategoryNode) getRoot();
        for (int i = 0; i < categoryPath.size(); i++) {
            CategoryElement categoryElementCategoryElementAt = categoryPath.categoryElementAt(i);
            Enumeration enumerationChildren = categoryNode.children();
            boolean z = false;
            while (true) {
                if (!enumerationChildren.hasMoreElements()) {
                    break;
                }
                CategoryNode categoryNode2 = (CategoryNode) enumerationChildren.nextElement();
                if (categoryNode2.getTitle().toLowerCase().equals(categoryElementCategoryElementAt.getTitle().toLowerCase())) {
                    z = true;
                    categoryNode = categoryNode2;
                    break;
                }
            }
            if (!z) {
                CategoryNode categoryNode3 = new CategoryNode(categoryElementCategoryElementAt.getTitle());
                insertNodeInto(categoryNode3, categoryNode, categoryNode.getChildCount());
                refresh(categoryNode3);
                categoryNode = categoryNode3;
            }
        }
        return categoryNode;
    }

    public void update(CategoryNode categoryNode, boolean z) {
        if (categoryNode.isSelected() == z) {
            return;
        }
        if (z) {
            setParentSelection(categoryNode, true);
        } else {
            setDescendantSelection(categoryNode, false);
        }
    }

    public void setDescendantSelection(CategoryNode categoryNode, boolean z) {
        Enumeration enumerationDepthFirstEnumeration = categoryNode.depthFirstEnumeration();
        while (enumerationDepthFirstEnumeration.hasMoreElements()) {
            CategoryNode categoryNode2 = (CategoryNode) enumerationDepthFirstEnumeration.nextElement();
            if (categoryNode2.isSelected() != z) {
                categoryNode2.setSelected(z);
                nodeChanged(categoryNode2);
            }
        }
        notifyActionListeners();
    }

    public void setParentSelection(CategoryNode categoryNode, boolean z) {
        CategoryNode[] pathToRoot = getPathToRoot(categoryNode);
        int length = pathToRoot.length;
        for (int i = 1; i < length; i++) {
            CategoryNode categoryNode2 = pathToRoot[i];
            if (categoryNode2.isSelected() != z) {
                categoryNode2.setSelected(z);
                nodeChanged(categoryNode2);
            }
        }
        notifyActionListeners();
    }

    public void addActionListener(ActionListener actionListener) {
        this._listener = AWTEventMulticaster.add(this._listener, actionListener);
    }

    public void removeActionListener(ActionListener actionListener) {
        this._listener = AWTEventMulticaster.remove(this._listener, actionListener);
    }

    public void resetAllNodeCounts() {
        Enumeration enumerationDepthFirstEnumeration = getRootCategoryNode().depthFirstEnumeration();
        while (enumerationDepthFirstEnumeration.hasMoreElements()) {
            CategoryNode categoryNode = (CategoryNode) enumerationDepthFirstEnumeration.nextElement();
            categoryNode.resetNumberOfContainedRecords();
            nodeChanged(categoryNode);
        }
    }

    public TreePath getTreePathToRoot(CategoryNode categoryNode) {
        if (categoryNode == null) {
            return null;
        }
        return new TreePath(getPathToRoot(categoryNode));
    }

    protected void notifyActionListeners() {
        if (this._listener != null) {
            this._listener.actionPerformed(this._event);
        }
    }

    protected void refresh(CategoryNode categoryNode) {
        SwingUtilities.invokeLater(new Runnable(this, categoryNode) { // from class: org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerModel.1
            private final CategoryNode val$node;
            private final CategoryExplorerModel this$0;

            {
                this.this$0 = this;
                this.val$node = categoryNode;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.this$0.nodeChanged(this.val$node);
            }
        });
    }
}
