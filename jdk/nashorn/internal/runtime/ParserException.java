package jdk.nashorn.internal.runtime;

import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.internal.objects.Global;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/ParserException.class */
public final class ParserException extends NashornException {
    private final Source source;
    private final long token;
    private final JSErrorType errorType;

    public ParserException(String str) {
        this(JSErrorType.SYNTAX_ERROR, str, null, -1, -1, -1L);
    }

    public ParserException(JSErrorType jSErrorType, String str, Source source, int i, int i2, long j) {
        super(str, source != null ? source.getName() : null, i, i2);
        this.source = source;
        this.token = j;
        this.errorType = jSErrorType;
    }

    public Source getSource() {
        return this.source;
    }

    public long getToken() {
        return this.token;
    }

    public int getPosition() {
        return (int) (this.token >>> 32);
    }

    public JSErrorType getErrorType() {
        return this.errorType;
    }

    public void throwAsEcmaException() {
        throw ECMAErrors.asEcmaException(this);
    }

    public void throwAsEcmaException(Global global) {
        throw ECMAErrors.asEcmaException(global, this);
    }
}
