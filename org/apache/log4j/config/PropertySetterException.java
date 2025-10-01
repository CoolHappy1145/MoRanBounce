package org.apache.log4j.config;

/* loaded from: L-out.jar:org/apache/log4j/config/PropertySetterException.class */
public class PropertySetterException extends Exception {
    private static final long serialVersionUID = -1352613734254235861L;
    protected Throwable rootCause;

    public PropertySetterException(String str) {
        super(str);
    }

    public PropertySetterException(Throwable th) {
        this.rootCause = th;
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        String message = super.getMessage();
        if (message == null && this.rootCause != null) {
            message = this.rootCause.getMessage();
        }
        return message;
    }
}
