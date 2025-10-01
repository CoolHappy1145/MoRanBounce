package org.apache.log4j.xml;

import java.util.Properties;
import org.w3c.dom.Element;

/* loaded from: L-out.jar:org/apache/log4j/xml/UnrecognizedElementHandler.class */
public interface UnrecognizedElementHandler {
    boolean parseUnrecognizedElement(Element element, Properties properties);
}
