package jdk.nashorn.internal.runtime.regexp.joni.exception;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/regexp/joni/exception/ValueException.class */
public class ValueException extends SyntaxException {
    private static final long serialVersionUID = -196013852479929134L;

    public ValueException(String str) {
        super(str);
    }

    public ValueException(String str, String str2) {
        super(str.replaceAll("%n", str2));
    }
}
