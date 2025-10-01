package org.slf4j.helpers;

import java.io.Serializable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* loaded from: L-out.jar:org/slf4j/helpers/NamedLoggerBase.class */
abstract class NamedLoggerBase implements Logger, Serializable {
    private static final long serialVersionUID = 7535258609338176893L;
    protected String name;

    NamedLoggerBase() {
    }

    @Override // org.slf4j.Logger
    public String getName() {
        return this.name;
    }

    protected Object readResolve() {
        return LoggerFactory.getLogger(getName());
    }
}
