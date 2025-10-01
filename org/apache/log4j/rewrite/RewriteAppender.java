package org.apache.log4j.rewrite;

import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Appender;
import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.helpers.AppenderAttachableImpl;
import org.apache.log4j.spi.AppenderAttachable;
import org.apache.log4j.spi.LoggingEvent;
import org.apache.log4j.spi.OptionHandler;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.log4j.xml.UnrecognizedElementHandler;
import org.w3c.dom.Element;

/* loaded from: L-out.jar:org/apache/log4j/rewrite/RewriteAppender.class */
public class RewriteAppender extends AppenderSkeleton implements AppenderAttachable, UnrecognizedElementHandler {
    private RewritePolicy policy;
    private final AppenderAttachableImpl appenders = new AppenderAttachableImpl();
    static Class class$org$apache$log4j$rewrite$RewritePolicy;

    @Override // org.apache.log4j.AppenderSkeleton
    protected void append(LoggingEvent loggingEvent) {
        LoggingEvent loggingEventRewrite = loggingEvent;
        if (this.policy != null) {
            loggingEventRewrite = this.policy.rewrite(loggingEvent);
        }
        if (loggingEventRewrite != null) {
            synchronized (this.appenders) {
                this.appenders.appendLoopOnAppenders(loggingEventRewrite);
            }
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public void addAppender(Appender appender) {
        synchronized (this.appenders) {
            this.appenders.addAppender(appender);
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public Enumeration getAllAppenders() {
        Enumeration allAppenders;
        synchronized (this.appenders) {
            allAppenders = this.appenders.getAllAppenders();
        }
        return allAppenders;
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public Appender getAppender(String str) {
        Appender appender;
        synchronized (this.appenders) {
            appender = this.appenders.getAppender(str);
        }
        return appender;
    }

    @Override // org.apache.log4j.Appender
    public void close() {
        this.closed = true;
        synchronized (this.appenders) {
            Enumeration allAppenders = this.appenders.getAllAppenders();
            if (allAppenders != null) {
                while (allAppenders.hasMoreElements()) {
                    Object objNextElement = allAppenders.nextElement();
                    if (objNextElement instanceof Appender) {
                        ((Appender) objNextElement).close();
                    }
                }
            }
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public boolean isAttached(Appender appender) {
        boolean zIsAttached;
        synchronized (this.appenders) {
            zIsAttached = this.appenders.isAttached(appender);
        }
        return zIsAttached;
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public void removeAllAppenders() {
        synchronized (this.appenders) {
            this.appenders.removeAllAppenders();
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public void removeAppender(Appender appender) {
        synchronized (this.appenders) {
            this.appenders.removeAppender(appender);
        }
    }

    @Override // org.apache.log4j.spi.AppenderAttachable
    public void removeAppender(String str) {
        synchronized (this.appenders) {
            this.appenders.removeAppender(str);
        }
    }

    public void setRewritePolicy(RewritePolicy rewritePolicy) {
        this.policy = rewritePolicy;
    }

    @Override // org.apache.log4j.xml.UnrecognizedElementHandler
    public boolean parseUnrecognizedElement(Element element, Properties properties) throws Throwable {
        Class clsClass$;
        if ("rewritePolicy".equals(element.getNodeName())) {
            if (class$org$apache$log4j$rewrite$RewritePolicy == null) {
                clsClass$ = class$("org.apache.log4j.rewrite.RewritePolicy");
                class$org$apache$log4j$rewrite$RewritePolicy = clsClass$;
            } else {
                clsClass$ = class$org$apache$log4j$rewrite$RewritePolicy;
            }
            Object element2 = DOMConfigurator.parseElement(element, properties, clsClass$);
            if (element2 != null) {
                if (element2 instanceof OptionHandler) {
                    ((OptionHandler) element2).activateOptions();
                }
                setRewritePolicy((RewritePolicy) element2);
                return true;
            }
            return true;
        }
        return false;
    }

    static Class class$(String str) throws Throwable {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError().initCause(e);
        }
    }
}
