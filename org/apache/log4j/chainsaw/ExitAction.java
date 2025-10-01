package org.apache.log4j.chainsaw;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import org.apache.log4j.Logger;

/* loaded from: L-out.jar:org/apache/log4j/chainsaw/ExitAction.class */
class ExitAction extends AbstractAction {
    private static final Logger LOG;
    public static final ExitAction INSTANCE;
    static Class class$org$apache$log4j$chainsaw$ExitAction;

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }

    static {
        Class clsClass$;
        if (class$org$apache$log4j$chainsaw$ExitAction == null) {
            clsClass$ = class$("org.apache.log4j.chainsaw.ExitAction");
            class$org$apache$log4j$chainsaw$ExitAction = clsClass$;
        } else {
            clsClass$ = class$org$apache$log4j$chainsaw$ExitAction;
        }
        LOG = Logger.getLogger(clsClass$);
        INSTANCE = new ExitAction();
    }

    private ExitAction() {
    }

    public void actionPerformed(ActionEvent actionEvent) {
        LOG.info("shutting down");
        System.exit(0);
    }
}
