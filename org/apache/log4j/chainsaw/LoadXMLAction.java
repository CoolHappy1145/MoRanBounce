package org.apache.log4j.chainsaw;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.StringReader;
import javax.swing.AbstractAction;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.xml.parsers.SAXParserFactory;
import org.apache.log4j.Logger;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

/* loaded from: L-out.jar:org/apache/log4j/chainsaw/LoadXMLAction.class */
class LoadXMLAction extends AbstractAction {
    private static final Logger LOG;
    private final JFrame mParent;
    private final JFileChooser mChooser = new JFileChooser();
    private final XMLReader mParser;
    private final XMLFileHandler mHandler;
    static Class class$org$apache$log4j$chainsaw$LoadXMLAction;

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static {
        Class clsClass$;
        if (class$org$apache$log4j$chainsaw$LoadXMLAction == null) {
            clsClass$ = class$("org.apache.log4j.chainsaw.LoadXMLAction");
            class$org$apache$log4j$chainsaw$LoadXMLAction = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$chainsaw$LoadXMLAction;
        }
        LOG = Logger.getLogger(clsClass$);
    }

    LoadXMLAction(JFrame jFrame, MyTableModel myTableModel) {
        this.mChooser.setMultiSelectionEnabled(false);
        this.mChooser.setFileSelectionMode(0);
        this.mParent = jFrame;
        this.mHandler = new XMLFileHandler(myTableModel);
        this.mParser = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
        this.mParser.setContentHandler(this.mHandler);
    }

    public void actionPerformed(ActionEvent actionEvent) {
        LOG.info("load file called");
        if (this.mChooser.showOpenDialog(this.mParent) == 0) {
            LOG.info("Need to load a file");
            File selectedFile = this.mChooser.getSelectedFile();
            LOG.info(new StringBuffer().append("loading the contents of ").append(selectedFile.getAbsolutePath()).toString());
            try {
                JOptionPane.showMessageDialog(this.mParent, new StringBuffer().append("Loaded ").append(loadFile(selectedFile.getAbsolutePath())).append(" events.").toString(), "CHAINSAW", 1);
            } catch (Exception e) {
                LOG.warn("caught an exception loading the file", e);
                JOptionPane.showMessageDialog(this.mParent, new StringBuffer().append("Error parsing file - ").append(e.getMessage()).toString(), "CHAINSAW", 0);
            }
        }
    }

    private int loadFile(String str) {
        int numEvents;
        synchronized (this.mParser) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("<?xml version=\"1.0\" standalone=\"yes\"?>\n");
            stringBuffer.append("<!DOCTYPE log4j:eventSet ");
            stringBuffer.append("[<!ENTITY data SYSTEM \"file:///");
            stringBuffer.append(str);
            stringBuffer.append("\">]>\n");
            stringBuffer.append("<log4j:eventSet xmlns:log4j=\"Claira\">\n");
            stringBuffer.append("&data;\n");
            stringBuffer.append("</log4j:eventSet>\n");
            this.mParser.parse(new InputSource(new StringReader(stringBuffer.toString())));
            numEvents = this.mHandler.getNumEvents();
        }
        return numEvents;
    }
}
