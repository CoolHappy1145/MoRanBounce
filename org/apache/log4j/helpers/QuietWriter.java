package org.apache.log4j.helpers;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import org.apache.log4j.spi.ErrorHandler;

/* loaded from: L-out.jar:org/apache/log4j/helpers/QuietWriter.class */
public class QuietWriter extends FilterWriter {
    protected ErrorHandler errorHandler;

    public QuietWriter(Writer writer, ErrorHandler errorHandler) {
        super(writer);
        setErrorHandler(errorHandler);
    }

    @Override // java.io.Writer
    public void write(String str) throws IOException {
        if (str != null) {
            try {
                this.out.write(str);
            } catch (Exception e) {
                this.errorHandler.error(new StringBuffer().append("Failed to write [").append(str).append("].").toString(), e, 1);
            }
        }
    }

    @Override // java.io.FilterWriter, java.io.Writer, java.io.Flushable
    public void flush() throws IOException {
        try {
            this.out.flush();
        } catch (Exception e) {
            this.errorHandler.error("Failed to flush writer,", e, 2);
        }
    }

    public void setErrorHandler(ErrorHandler errorHandler) {
        if (errorHandler == null) {
            throw new IllegalArgumentException("Attempted to set null ErrorHandler.");
        }
        this.errorHandler = errorHandler;
    }
}
