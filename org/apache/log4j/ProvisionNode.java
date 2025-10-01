package org.apache.log4j;

import java.util.Vector;

/* loaded from: L-out.jar:org/apache/log4j/ProvisionNode.class */
class ProvisionNode extends Vector {
    private static final long serialVersionUID = -4479121426311014469L;

    ProvisionNode(Logger logger) {
        addElement(logger);
    }
}
