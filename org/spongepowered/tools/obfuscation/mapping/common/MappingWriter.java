package org.spongepowered.tools.obfuscation.mapping.common;

import java.io.File;
import java.io.PrintWriter;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import org.spongepowered.tools.obfuscation.mapping.IMappingWriter;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mapping/common/MappingWriter.class */
public abstract class MappingWriter implements IMappingWriter {
    private final Messager messager;
    private final Filer filer;

    public MappingWriter(Messager messager, Filer filer) {
        this.messager = messager;
        this.filer = filer;
    }

    protected PrintWriter openFileWriter(String str, String str2) {
        if (str.matches("^.*[\\\\/:].*$")) {
            File file = new File(str);
            file.getParentFile().mkdirs();
            this.messager.printMessage(Diagnostic.Kind.NOTE, "Writing " + str2 + " to " + file.getAbsolutePath());
            return new PrintWriter(file);
        }
        FileObject fileObjectCreateResource = this.filer.createResource(StandardLocation.CLASS_OUTPUT, "", str, new Element[0]);
        this.messager.printMessage(Diagnostic.Kind.NOTE, "Writing " + str2 + " to " + new File(fileObjectCreateResource.toUri()).getAbsolutePath());
        return new PrintWriter(fileObjectCreateResource.openWriter());
    }
}
