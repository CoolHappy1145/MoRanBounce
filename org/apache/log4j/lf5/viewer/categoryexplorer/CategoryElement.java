package org.apache.log4j.lf5.viewer.categoryexplorer;

/* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/categoryexplorer/CategoryElement.class */
public class CategoryElement {
    protected String _categoryTitle;

    public CategoryElement() {
    }

    public CategoryElement(String str) {
        this._categoryTitle = str;
    }

    public String getTitle() {
        return this._categoryTitle;
    }

    public void setTitle(String str) {
        this._categoryTitle = str;
    }
}
