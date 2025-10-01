package org.spongepowered.tools.obfuscation.mapping.fg3;

import java.io.PrintWriter;
import java.util.Iterator;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationType;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;
import org.spongepowered.tools.obfuscation.mapping.mcp.MappingWriterSrg;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mapping/fg3/MappingWriterTSrg.class */
public class MappingWriterTSrg extends MappingWriterSrg {
    private final MappingProviderTSrg provider;
    private final boolean mergeExisting;

    public MappingWriterTSrg(Messager messager, Filer filer, MappingProviderTSrg mappingProviderTSrg, boolean z) {
        super(messager, filer);
        this.provider = mappingProviderTSrg;
        this.mergeExisting = z;
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.mcp.MappingWriterSrg
    protected PrintWriter openFileWriter(String str, ObfuscationType obfuscationType) {
        return openFileWriter(str, obfuscationType + " composite mappings");
    }

    protected void writeHeader(PrintWriter printWriter) {
        if (this.mergeExisting) {
            Iterator it = this.provider.getInputMappings().iterator();
            while (it.hasNext()) {
                printWriter.println((String) it.next());
            }
        }
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.mcp.MappingWriterSrg
    protected String formatFieldMapping(IMappingConsumer.MappingSet.Pair pair) {
        return String.format("%s %s %s", ((MappingField) pair.from).getOwner(), ((MappingField) pair.from).getSimpleName(), ((MappingField) pair.f236to).getSimpleName());
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.mcp.MappingWriterSrg
    protected String formatMethodMapping(IMappingConsumer.MappingSet.Pair pair) {
        return String.format("%s %s %s %s", ((MappingMethod) pair.from).getOwner(), ((MappingMethod) pair.from).getSimpleName(), ((MappingMethod) pair.from).getDesc(), ((MappingMethod) pair.f236to).getSimpleName());
    }
}
