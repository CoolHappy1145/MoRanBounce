package org.apache.log4j.xml;

import org.apache.log4j.helpers.LogLog;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXParseException;

/* loaded from: L-out.jar:org/apache/log4j/xml/SAXErrorHandler.class */
public class SAXErrorHandler implements ErrorHandler {
    @Override // org.xml.sax.ErrorHandler
    public void error(SAXParseException sAXParseException) {
        emitMessage("Continuable parsing error ", sAXParseException);
    }

    @Override // org.xml.sax.ErrorHandler
    public void fatalError(SAXParseException sAXParseException) {
        emitMessage("Fatal parsing error ", sAXParseException);
    }

    @Override // org.xml.sax.ErrorHandler
    public void warning(SAXParseException sAXParseException) {
        emitMessage("Parsing warning ", sAXParseException);
    }

    private static void emitMessage(String str, SAXParseException sAXParseException) {
        LogLog.warn(new StringBuffer().append(str).append(sAXParseException.getLineNumber()).append(" and column ").append(sAXParseException.getColumnNumber()).toString());
        LogLog.warn(sAXParseException.getMessage(), sAXParseException.getException());
    }
}
