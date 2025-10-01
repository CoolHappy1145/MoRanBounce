package org.apache.log4j.lf5.viewer.categoryexplorer;

import java.util.LinkedList;
import java.util.StringTokenizer;

/* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/categoryexplorer/CategoryPath.class */
public class CategoryPath {
    protected LinkedList _categoryElements = new LinkedList();

    public CategoryPath() {
    }

    public CategoryPath(String str) {
        String str2 = str;
        StringTokenizer stringTokenizer = new StringTokenizer((str2 == null ? "Debug" : str2).replace('/', '.').replace('\\', '.'), ".");
        while (stringTokenizer.hasMoreTokens()) {
            addCategoryElement(new CategoryElement(stringTokenizer.nextToken()));
        }
    }

    public int size() {
        return this._categoryElements.size();
    }

    public boolean isEmpty() {
        boolean z = false;
        if (this._categoryElements.size() == 0) {
            z = true;
        }
        return z;
    }

    public void removeAllCategoryElements() {
        this._categoryElements.clear();
    }

    public void addCategoryElement(CategoryElement categoryElement) {
        this._categoryElements.addLast(categoryElement);
    }

    public CategoryElement categoryElementAt(int i) {
        return (CategoryElement) this._categoryElements.get(i);
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer(100);
        stringBuffer.append("\n");
        stringBuffer.append("===========================\n");
        stringBuffer.append("CategoryPath:                   \n");
        stringBuffer.append("---------------------------\n");
        stringBuffer.append("\nCategoryPath:\n\t");
        if (size() > 0) {
            for (int i = 0; i < size(); i++) {
                stringBuffer.append(categoryElementAt(i).toString());
                stringBuffer.append("\n\t");
            }
        } else {
            stringBuffer.append("<<NONE>>");
        }
        stringBuffer.append("\n");
        stringBuffer.append("===========================\n");
        return stringBuffer.toString();
    }
}
