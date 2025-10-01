package jdk.nashorn.internal.runtime;

import java.io.OutputStream;
import java.io.PrintWriter;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/ErrorManager.class */
public class ErrorManager {
    private final PrintWriter writer;
    private int errors;
    private int warnings;
    private int limit;
    private boolean warningsAsErrors;

    public ErrorManager() {
        this(new PrintWriter((OutputStream) System.err, true));
    }

    public ErrorManager(PrintWriter printWriter) {
        this.writer = printWriter;
        this.limit = 100;
        this.warningsAsErrors = false;
    }

    private void checkLimit() {
        int i = this.errors;
        if (this.warningsAsErrors) {
            i += this.warnings;
        }
        if (this.limit != 0 && i > this.limit) {
            throw ECMAErrors.rangeError("too.many.errors", new String[]{Integer.toString(this.limit)});
        }
    }

    public static String format(String str, Source source, int i, int i2, long j) {
        String strLineSeparator = System.lineSeparator();
        StringBuilder sb = new StringBuilder();
        sb.append(source.getName()).append(':').append(i).append(':').append(i2).append(' ').append(str).append(strLineSeparator);
        String sourceLine = source.getSourceLine((int) (j >>> 32));
        sb.append(sourceLine).append(strLineSeparator);
        for (int i3 = 0; i3 < i2; i3++) {
            if (i3 < sourceLine.length() && sourceLine.charAt(i3) == '\t') {
                sb.append('\t');
            } else {
                sb.append(' ');
            }
        }
        sb.append('^');
        return sb.toString();
    }

    public void error(ParserException parserException) {
        error(parserException.getMessage());
    }

    public void error(String str) {
        this.writer.println(str);
        this.writer.flush();
        this.errors++;
        checkLimit();
    }

    public void warning(ParserException parserException) {
        warning(parserException.getMessage());
    }

    public void warning(String str) {
        this.writer.println(str);
        this.writer.flush();
        this.warnings++;
        checkLimit();
    }

    public boolean hasErrors() {
        return this.errors != 0;
    }

    public int getLimit() {
        return this.limit;
    }

    public void setLimit(int i) {
        this.limit = i;
    }

    public boolean isWarningsAsErrors() {
        return this.warningsAsErrors;
    }

    public void setWarningsAsErrors(boolean z) {
        this.warningsAsErrors = z;
    }

    public int getNumberOfErrors() {
        return this.errors;
    }

    public int getNumberOfWarnings() {
        return this.warnings;
    }

    void reset() {
        this.warnings = 0;
        this.errors = 0;
    }
}
