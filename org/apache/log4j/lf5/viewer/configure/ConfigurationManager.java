package org.apache.log4j.lf5.viewer.configure;

import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.tree.TreePath;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.lf5.LogLevel;
import org.apache.log4j.lf5.LogLevelFormatException;
import org.apache.log4j.lf5.viewer.LogBrokerMonitor;
import org.apache.log4j.lf5.viewer.LogTable;
import org.apache.log4j.lf5.viewer.LogTableColumn;
import org.apache.log4j.lf5.viewer.LogTableColumnFormatException;
import org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerModel;
import org.apache.log4j.lf5.viewer.categoryexplorer.CategoryExplorerTree;
import org.apache.log4j.lf5.viewer.categoryexplorer.CategoryNode;
import org.apache.log4j.lf5.viewer.categoryexplorer.CategoryPath;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/* loaded from: L-out.jar:org/apache/log4j/lf5/viewer/configure/ConfigurationManager.class */
public class ConfigurationManager {
    private static final String CONFIG_FILE_NAME = "lf5_configuration.xml";
    private static final String NAME = "name";
    private static final String PATH = "path";
    private static final String SELECTED = "selected";
    private static final String EXPANDED = "expanded";
    private static final String CATEGORY = "category";
    private static final String FIRST_CATEGORY_NAME = "Categories";
    private static final String LEVEL = "level";
    private static final String COLORLEVEL = "colorlevel";
    private static final String RED = "red";
    private static final String GREEN = "green";
    private static final String BLUE = "blue";
    private static final String COLUMN = "column";
    private static final String NDCTEXTFILTER = "searchtext";
    private LogBrokerMonitor _monitor;
    private LogTable _table;

    public ConfigurationManager(LogBrokerMonitor logBrokerMonitor, LogTable logTable) throws SAXException, IOException {
        this._monitor = null;
        this._table = null;
        this._monitor = logBrokerMonitor;
        this._table = logTable;
        load();
    }

    public void save() {
        CategoryNode rootCategoryNode = this._monitor.getCategoryExplorerTree().getExplorerModel().getRootCategoryNode();
        StringBuffer stringBuffer = new StringBuffer(2048);
        openXMLDocument(stringBuffer);
        openConfigurationXML(stringBuffer);
        processLogRecordFilter(this._monitor.getNDCTextFilter(), stringBuffer);
        processLogLevels(this._monitor.getLogLevelMenuItems(), stringBuffer);
        processLogLevelColors(this._monitor.getLogLevelMenuItems(), LogLevel.getLogLevelColorMap(), stringBuffer);
        processLogTableColumns(LogTableColumn.getLogTableColumns(), stringBuffer);
        processConfigurationNode(rootCategoryNode, stringBuffer);
        closeConfigurationXML(stringBuffer);
        store(stringBuffer.toString());
    }

    public void reset() {
        deleteConfigurationFile();
        collapseTree();
        selectAllNodes();
    }

    public static String treePathToString(TreePath treePath) {
        StringBuffer stringBuffer = new StringBuffer();
        Object[] path = treePath.getPath();
        for (int i = 1; i < path.length; i++) {
            CategoryNode categoryNode = (CategoryNode) path[i];
            if (i > 1) {
                stringBuffer.append(".");
            }
            stringBuffer.append(categoryNode.getTitle());
        }
        return stringBuffer.toString();
    }

    protected void load() throws SAXException, IOException {
        File file = new File(getFilename());
        if (file.exists()) {
            try {
                Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
                processRecordFilter(document);
                processCategories(document);
                processLogLevels(document);
                processLogLevelColors(document);
                processLogTableColumns(document);
            } catch (Exception e) {
                System.err.println(new StringBuffer().append("Unable process configuration file at ").append(getFilename()).append(". Error Message=").append(e.getMessage()).toString());
            }
        }
    }

    protected void processRecordFilter(Document document) {
        String value;
        Node nodeItem = document.getElementsByTagName(NDCTEXTFILTER).item(0);
        if (nodeItem == null || (value = getValue(nodeItem.getAttributes(), NAME)) == null || value.equals("")) {
            return;
        }
        this._monitor.setNDCLogRecordFilter(value);
    }

    protected void processCategories(Document document) {
        CategoryExplorerTree categoryExplorerTree = this._monitor.getCategoryExplorerTree();
        CategoryExplorerModel explorerModel = categoryExplorerTree.getExplorerModel();
        NodeList elementsByTagName = document.getElementsByTagName(CATEGORY);
        int i = getValue(elementsByTagName.item(0).getAttributes(), NAME).equalsIgnoreCase(FIRST_CATEGORY_NAME) ? 1 : 0;
        for (int length = elementsByTagName.getLength() - 1; length >= i; length--) {
            NamedNodeMap attributes = elementsByTagName.item(length).getAttributes();
            CategoryNode categoryNodeAddCategory = explorerModel.addCategory(new CategoryPath(getValue(attributes, PATH)));
            categoryNodeAddCategory.setSelected(getValue(attributes, SELECTED).equalsIgnoreCase("true"));
            if (getValue(attributes, EXPANDED).equalsIgnoreCase("true")) {
            }
            categoryExplorerTree.expandPath(explorerModel.getTreePathToRoot(categoryNodeAddCategory));
        }
    }

    protected void processLogLevels(Document document) {
        NodeList elementsByTagName = document.getElementsByTagName(LEVEL);
        Map logLevelMenuItems = this._monitor.getLogLevelMenuItems();
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            NamedNodeMap attributes = elementsByTagName.item(i).getAttributes();
            try {
                ((JCheckBoxMenuItem) logLevelMenuItems.get(LogLevel.valueOf(getValue(attributes, NAME)))).setSelected(getValue(attributes, SELECTED).equalsIgnoreCase("true"));
            } catch (LogLevelFormatException unused) {
            }
        }
    }

    protected void processLogLevelColors(Document document) {
        Node nodeItem;
        NodeList elementsByTagName = document.getElementsByTagName(COLORLEVEL);
        LogLevel.getLogLevelColorMap();
        for (int i = 0; i < elementsByTagName.getLength() && (nodeItem = elementsByTagName.item(i)) != null; i++) {
            NamedNodeMap attributes = nodeItem.getAttributes();
            try {
                LogLevel logLevelValueOf = LogLevel.valueOf(getValue(attributes, NAME));
                Color color = new Color(Integer.parseInt(getValue(attributes, RED)), Integer.parseInt(getValue(attributes, GREEN)), Integer.parseInt(getValue(attributes, BLUE)));
                if (logLevelValueOf != null) {
                    logLevelValueOf.setLogLevelColorMap(logLevelValueOf, color);
                }
            } catch (LogLevelFormatException unused) {
            }
        }
    }

    protected void processLogTableColumns(Document document) {
        Node nodeItem;
        NodeList elementsByTagName = document.getElementsByTagName(COLUMN);
        Map logTableColumnMenuItems = this._monitor.getLogTableColumnMenuItems();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < elementsByTagName.getLength() && (nodeItem = elementsByTagName.item(i)) != null; i++) {
            NamedNodeMap attributes = nodeItem.getAttributes();
            try {
                LogTableColumn logTableColumnValueOf = LogTableColumn.valueOf(getValue(attributes, NAME));
                JCheckBoxMenuItem jCheckBoxMenuItem = (JCheckBoxMenuItem) logTableColumnMenuItems.get(logTableColumnValueOf);
                jCheckBoxMenuItem.setSelected(getValue(attributes, SELECTED).equalsIgnoreCase("true"));
                if (jCheckBoxMenuItem.isSelected()) {
                    arrayList.add(logTableColumnValueOf);
                }
            } catch (LogTableColumnFormatException unused) {
            }
            if (arrayList.isEmpty()) {
                this._table.setDetailedView();
            } else {
                this._table.setView(arrayList);
            }
        }
    }

    protected String getValue(NamedNodeMap namedNodeMap, String str) {
        return namedNodeMap.getNamedItem(str).getNodeValue();
    }

    protected void collapseTree() {
        CategoryExplorerTree categoryExplorerTree = this._monitor.getCategoryExplorerTree();
        for (int rowCount = categoryExplorerTree.getRowCount() - 1; rowCount > 0; rowCount--) {
            categoryExplorerTree.collapseRow(rowCount);
        }
    }

    protected void selectAllNodes() {
        Enumeration enumerationBreadthFirstEnumeration = this._monitor.getCategoryExplorerTree().getExplorerModel().getRootCategoryNode().breadthFirstEnumeration();
        while (enumerationBreadthFirstEnumeration.hasMoreElements()) {
            ((CategoryNode) enumerationBreadthFirstEnumeration.nextElement()).setSelected(true);
        }
    }

    protected void store(String str) {
        try {
            PrintWriter printWriter = new PrintWriter(new FileWriter(getFilename()));
            printWriter.print(str);
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void deleteConfigurationFile() {
        try {
            File file = new File(getFilename());
            if (file.exists()) {
                file.delete();
            }
        } catch (SecurityException unused) {
            System.err.println(new StringBuffer().append("Cannot delete ").append(getFilename()).append(" because a security violation occured.").toString());
        }
    }

    protected String getFilename() {
        String property = System.getProperty("user.home");
        String property2 = System.getProperty("file.separator");
        return new StringBuffer().append(property).append(property2).append("lf5").append(property2).append(CONFIG_FILE_NAME).toString();
    }

    private void processConfigurationNode(CategoryNode categoryNode, StringBuffer stringBuffer) {
        CategoryExplorerModel explorerModel = this._monitor.getCategoryExplorerTree().getExplorerModel();
        Enumeration enumerationBreadthFirstEnumeration = categoryNode.breadthFirstEnumeration();
        while (enumerationBreadthFirstEnumeration.hasMoreElements()) {
            CategoryNode categoryNode2 = (CategoryNode) enumerationBreadthFirstEnumeration.nextElement();
            exportXMLElement(categoryNode2, explorerModel.getTreePathToRoot(categoryNode2), stringBuffer);
        }
    }

    private void processLogLevels(Map map, StringBuffer stringBuffer) {
        stringBuffer.append("\t<loglevels>\r\n");
        for (LogLevel logLevel : map.keySet()) {
            exportLogLevelXMLElement(logLevel.getLabel(), ((JCheckBoxMenuItem) map.get(logLevel)).isSelected(), stringBuffer);
        }
        stringBuffer.append("\t</loglevels>\r\n");
    }

    private void processLogLevelColors(Map map, Map map2, StringBuffer stringBuffer) {
        stringBuffer.append("\t<loglevelcolors>\r\n");
        for (LogLevel logLevel : map.keySet()) {
            exportLogLevelColorXMLElement(logLevel.getLabel(), (Color) map2.get(logLevel), stringBuffer);
        }
        stringBuffer.append("\t</loglevelcolors>\r\n");
    }

    private void processLogTableColumns(List list, StringBuffer stringBuffer) {
        stringBuffer.append("\t<logtablecolumns>\r\n");
        Iterator it = list.iterator();
        while (it.hasNext()) {
            LogTableColumn logTableColumn = (LogTableColumn) it.next();
            exportLogTableColumnXMLElement(logTableColumn.getLabel(), this._monitor.getTableColumnMenuItem(logTableColumn).isSelected(), stringBuffer);
        }
        stringBuffer.append("\t</logtablecolumns>\r\n");
    }

    private void processLogRecordFilter(String str, StringBuffer stringBuffer) {
        stringBuffer.append("\t<").append(NDCTEXTFILTER).append(" ");
        stringBuffer.append(NAME).append("=\"").append(str).append("\"");
        stringBuffer.append("/>\r\n");
    }

    private void openXMLDocument(StringBuffer stringBuffer) {
        stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\r\n");
    }

    private void openConfigurationXML(StringBuffer stringBuffer) {
        stringBuffer.append("<configuration>\r\n");
    }

    private void closeConfigurationXML(StringBuffer stringBuffer) {
        stringBuffer.append("</configuration>\r\n");
    }

    private void exportXMLElement(CategoryNode categoryNode, TreePath treePath, StringBuffer stringBuffer) {
        CategoryExplorerTree categoryExplorerTree = this._monitor.getCategoryExplorerTree();
        stringBuffer.append("\t<").append(CATEGORY).append(" ");
        stringBuffer.append(NAME).append("=\"").append(categoryNode.getTitle()).append("\" ");
        stringBuffer.append(PATH).append("=\"").append(treePathToString(treePath)).append("\" ");
        stringBuffer.append(EXPANDED).append("=\"").append(categoryExplorerTree.isExpanded(treePath)).append("\" ");
        stringBuffer.append(SELECTED).append("=\"").append(categoryNode.isSelected()).append("\"/>\r\n");
    }

    private void exportLogLevelXMLElement(String str, boolean z, StringBuffer stringBuffer) {
        stringBuffer.append("\t\t<").append(LEVEL).append(" ").append(NAME);
        stringBuffer.append("=\"").append(str).append("\" ");
        stringBuffer.append(SELECTED).append("=\"").append(z);
        stringBuffer.append("\"/>\r\n");
    }

    private void exportLogLevelColorXMLElement(String str, Color color, StringBuffer stringBuffer) {
        stringBuffer.append("\t\t<").append(COLORLEVEL).append(" ").append(NAME);
        stringBuffer.append("=\"").append(str).append("\" ");
        stringBuffer.append(RED).append("=\"").append(color.getRed()).append("\" ");
        stringBuffer.append(GREEN).append("=\"").append(color.getGreen()).append("\" ");
        stringBuffer.append(BLUE).append("=\"").append(color.getBlue());
        stringBuffer.append("\"/>\r\n");
    }

    private void exportLogTableColumnXMLElement(String str, boolean z, StringBuffer stringBuffer) {
        stringBuffer.append("\t\t<").append(COLUMN).append(" ").append(NAME);
        stringBuffer.append("=\"").append(str).append("\" ");
        stringBuffer.append(SELECTED).append("=\"").append(z);
        stringBuffer.append("\"/>\r\n");
    }
}
