package org.spongepowered.asm.service;

/* loaded from: L-out.jar:org/spongepowered/asm/service/ServiceInitialisationException.class */
public class ServiceInitialisationException extends RuntimeException {
    private static final long serialVersionUID = 1;

    public ServiceInitialisationException() {
    }

    public ServiceInitialisationException(String str) {
        super(str);
    }

    public ServiceInitialisationException(Throwable th) {
        super(th);
    }

    public ServiceInitialisationException(String str, Throwable th) {
        super(str, th);
    }
}
