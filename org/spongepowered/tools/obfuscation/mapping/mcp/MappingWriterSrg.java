package org.spongepowered.tools.obfuscation.mapping.mcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mapping.common.MappingWriter;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mapping/mcp/MappingWriterSrg.class */
public class MappingWriterSrg extends MappingWriter {
    public MappingWriterSrg(Messager messager, Filer filer) {
        super(messager, filer);
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingWriter
    public void write(String str, ObfuscationType obfuscationType, IMappingConsumer.MappingSet mappingSet, IMappingConsumer.MappingSet mappingSet2) {
        if (str == null) {
            return;
        }
        PrintWriter printWriterOpenFileWriter = null;
        try {
            try {
                printWriterOpenFileWriter = openFileWriter(str, obfuscationType);
                writeFieldMappings(printWriterOpenFileWriter, mappingSet);
                writeMethodMappings(printWriterOpenFileWriter, mappingSet2);
                if (printWriterOpenFileWriter != null) {
                    try {
                        printWriterOpenFileWriter.close();
                    } catch (Exception unused) {
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                if (printWriterOpenFileWriter != null) {
                    try {
                        printWriterOpenFileWriter.close();
                    } catch (Exception unused2) {
                    }
                }
            }
        } catch (Throwable th) {
            if (printWriterOpenFileWriter != null) {
                try {
                    printWriterOpenFileWriter.close();
                } catch (Exception unused3) {
                }
            }
            throw th;
        }
    }

    protected PrintWriter openFileWriter(String str, ObfuscationType obfuscationType) {
        return openFileWriter(str, obfuscationType + " output SRGs");
    }

    protected void writeFieldMappings(PrintWriter printWriter, IMappingConsumer.MappingSet mappingSet) {
        Iterator it = mappingSet.iterator();
        while (it.hasNext()) {
            printWriter.println(formatFieldMapping((IMappingConsumer.MappingSet.Pair) it.next()));
        }
    }

    protected void writeMethodMappings(PrintWriter printWriter, IMappingConsumer.MappingSet mappingSet) {
        Iterator it = mappingSet.iterator();
        while (it.hasNext()) {
            printWriter.println(formatMethodMapping((IMappingConsumer.MappingSet.Pair) it.next()));
        }
    }

    protected String formatFieldMapping(IMappingConsumer.MappingSet.Pair pair) {
        return String.format("FD: %s/%s %s/%s", ((MappingField) pair.from).getOwner(), ((MappingField) pair.from).getName(), ((MappingField) pair.f236to).getOwner(), ((MappingField) pair.f236to).getName());
    }

    protected String formatMethodMapping(IMappingConsumer.MappingSet.Pair pair) {
        return String.format("MD: %s %s %s %s", ((MappingMethod) pair.from).getName(), ((MappingMethod) pair.from).getDesc(), ((MappingMethod) pair.f236to).getName(), ((MappingMethod) pair.f236to).getDesc());
    }
}
