package org.apache.log4j.xml;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import org.apache.log4j.helpers.LogLog;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;

/* loaded from: L-out.jar:org/apache/log4j/xml/Log4jEntityResolver.class */
public class Log4jEntityResolver implements EntityResolver {
    private static final String PUBLIC_ID = "-//APACHE//DTD LOG4J 1.2//EN";

    @Override // org.xml.sax.EntityResolver
    public InputSource resolveEntity(String str, String str2) {
        if (str2.endsWith("log4j.dtd") || PUBLIC_ID.equals(str)) {
            Class<?> cls = getClass();
            InputStream resourceAsStream = cls.getResourceAsStream("/org/apache/log4j/xml/log4j.dtd");
            if (resourceAsStream == null) {
                LogLog.warn(new StringBuffer().append("Could not find [log4j.dtd] using [").append(cls.getClassLoader()).append("] class loader, parsed without DTD.").toString());
                resourceAsStream = new ByteArrayInputStream(new byte[0]);
            }
            return new InputSource(resourceAsStream);
        }
        return null;
    }
}
