package org.apache.log4j.spi;

import java.io.Serializable;
import org.apache.log4j.Category;
import org.apache.log4j.DefaultThrowableRenderer;

/* loaded from: L-out.jar:org/apache/log4j/spi/ThrowableInformation.class */
public class ThrowableInformation implements Serializable {
    static final long serialVersionUID = -4748765566864322735L;
    private Throwable throwable;
    private Category category;
    private String[] rep;

    public ThrowableInformation(Throwable th) {
        this.throwable = th;
    }

    public ThrowableInformation(Throwable th, Category category) {
        this.throwable = th;
        this.category = category;
    }

    public ThrowableInformation(String[] strArr) {
        if (strArr != null) {
            this.rep = (String[]) strArr.clone();
        }
    }

    public Throwable getThrowable() {
        return this.throwable;
    }

    public String[] getThrowableStrRep() {
        if (this.rep == null) {
            ThrowableRenderer throwableRenderer = null;
            if (this.category != null) {
                LoggerRepository loggerRepository = this.category.getLoggerRepository();
                if (loggerRepository instanceof ThrowableRendererSupport) {
                    throwableRenderer = ((ThrowableRendererSupport) loggerRepository).getThrowableRenderer();
                }
            }
            if (throwableRenderer == null) {
                this.rep = DefaultThrowableRenderer.render(this.throwable);
            } else {
                this.rep = throwableRenderer.doRender(this.throwable);
            }
        }
        return (String[]) this.rep.clone();
    }
}
