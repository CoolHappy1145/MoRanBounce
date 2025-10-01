package org.apache.log4j.chainsaw;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Properties;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/* loaded from: L-out.jar:org/apache/log4j/chainsaw/Main.class */
public class Main extends JFrame {
    private static final int DEFAULT_PORT = 4445;
    public static final String PORT_PROP_NAME = "chainsaw.port";
    private static final Logger LOG;
    static Class class$org$apache$log4j$chainsaw$Main;

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static {
        Class clsClass$;
        if (class$org$apache$log4j$chainsaw$Main == null) {
            clsClass$ = class$("org.apache.log4j.chainsaw.Main");
            class$org$apache$log4j$chainsaw$Main = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$chainsaw$Main;
        }
        LOG = Logger.getLogger(clsClass$);
    }

    private Main() throws NumberFormatException {
        super("CHAINSAW - Log4J Log Viewer");
        MyTableModel myTableModel = new MyTableModel();
        JMenuBar jMenuBar = new JMenuBar();
        setJMenuBar(jMenuBar);
        JMenu jMenu = new JMenu("File");
        jMenuBar.add(jMenu);
        try {
            LoadXMLAction loadXMLAction = new LoadXMLAction(this, myTableModel);
            JMenuItem jMenuItem = new JMenuItem("Load file...");
            jMenu.add(jMenuItem);
            jMenuItem.addActionListener(loadXMLAction);
        } catch (Exception e) {
            LOG.info("Unable to create the action to load XML files", e);
            JOptionPane.showMessageDialog(this, "Unable to create a XML parser - unable to load XML events.", "CHAINSAW", 0);
        } catch (NoClassDefFoundError e2) {
            LOG.info("Missing classes for XML parser", e2);
            JOptionPane.showMessageDialog(this, "XML parser not in classpath - unable to load XML events.", "CHAINSAW", 0);
        }
        JMenuItem jMenuItem2 = new JMenuItem("Exit");
        jMenu.add(jMenuItem2);
        jMenuItem2.addActionListener(ExitAction.INSTANCE);
        getContentPane().add(new ControlPanel(myTableModel), "North");
        JTable jTable = new JTable(myTableModel);
        jTable.setSelectionMode(0);
        JScrollPane jScrollPane = new JScrollPane(jTable);
        jScrollPane.setBorder(BorderFactory.createTitledBorder("Events: "));
        jScrollPane.setPreferredSize(new Dimension(900, 300));
        DetailPanel detailPanel = new DetailPanel(jTable, myTableModel);
        detailPanel.setPreferredSize(new Dimension(900, 300));
        getContentPane().add(new JSplitPane(0, jScrollPane, detailPanel), "Center");
        addWindowListener(new WindowAdapter(this) { // from class: org.apache.log4j.chainsaw.Main.1
            private final Main this$0;

            {
                this.this$0 = this;
            }

            public void windowClosing(WindowEvent windowEvent) {
                ExitAction.INSTANCE.actionPerformed(null);
            }
        });
        pack();
        setVisible(true);
        setupReceiver(myTableModel);
    }

    private void setupReceiver(MyTableModel myTableModel) throws NumberFormatException {
        int i = DEFAULT_PORT;
        String property = System.getProperty(PORT_PROP_NAME);
        if (property != null) {
            try {
                i = Integer.parseInt(property);
            } catch (NumberFormatException unused) {
                LOG.fatal(new StringBuffer().append("Unable to parse chainsaw.port property with value ").append(property).append(".").toString());
                JOptionPane.showMessageDialog(this, new StringBuffer().append("Unable to parse port number from '").append(property).append("', quitting.").toString(), "CHAINSAW", 0);
                System.exit(1);
            }
        }
        try {
            new LoggingReceiver(myTableModel, i).start();
        } catch (IOException e) {
            LOG.fatal("Unable to connect to socket server, quiting", e);
            JOptionPane.showMessageDialog(this, new StringBuffer().append("Unable to create socket on port ").append(i).append(", quitting.").toString(), "CHAINSAW", 0);
            System.exit(1);
        }
    }

    private static void initLog4J() throws Throwable {
        Properties properties = new Properties();
        properties.setProperty("log4j.rootLogger", "DEBUG, A1");
        properties.setProperty("log4j.appender.A1", "org.apache.log4j.ConsoleAppender");
        properties.setProperty("log4j.appender.A1.layout", "org.apache.log4j.TTCCLayout");
        PropertyConfigurator.configure(properties);
    }

    public static void main(String[] strArr) throws Throwable {
        initLog4J();
        new Main();
    }
}
