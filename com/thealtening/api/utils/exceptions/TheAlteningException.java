package com.thealtening.api.utils.exceptions;

/* loaded from: L-out.jar:com/thealtening/api/utils/exceptions/TheAlteningException.class */
public class TheAlteningException extends RuntimeException {
    public TheAlteningException(String str, String str2) {
        super(String.format("[%s]: %s", str, str2));
    }
}
