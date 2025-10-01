package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JTree;

/* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/categoryexplorer/CategoryNodeEditorRenderer.class */
public class CategoryNodeEditorRenderer extends CategoryNodeRenderer {
    private static final long serialVersionUID = -6094804684259929574L;

    @Override // org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNodeRenderer
    public Component getTreeCellRendererComponent(JTree jTree, Object obj, boolean z, boolean z2, boolean z3, int i, boolean z4) {
        return super.getTreeCellRendererComponent(jTree, obj, z, z2, z3, i, z4);
    }

    public JCheckBox getCheckBox() {
        return this._checkBox;
    }
}
