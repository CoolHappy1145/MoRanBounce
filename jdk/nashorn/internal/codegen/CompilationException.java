package jdk.nashorn.internal.codegen;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CompilationException.class */
public class CompilationException extends RuntimeException {
    CompilationException(String str) {
        super(str);
    }

    CompilationException(Exception exc) {
        super(exc);
    }
}
