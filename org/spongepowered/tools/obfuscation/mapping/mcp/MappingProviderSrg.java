package org.spongepowered.tools.obfuscation.mapping.mcp;

import com.google.common.base.Strings;
import com.google.common.collect.BiMap;
import com.google.common.io.Files;
import com.google.common.io.LineProcessor;
import java.io.File;
import java.nio.charset.Charset;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.mixin.throwables.MixinException;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.obfuscation.mapping.mcp.MappingFieldSrg;
import org.spongepowered.tools.obfuscation.mapping.common.MappingProvider;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mapping/mcp/MappingProviderSrg.class */
public class MappingProviderSrg extends MappingProvider {
    public MappingProviderSrg(Messager messager, Filer filer) {
        super(messager, filer);
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingProvider
    public void read(File file) {
        Files.readLines(file, Charset.defaultCharset(), new LineProcessor(this, this.packageMap, this.classMap, this.fieldMap, this.methodMap, file) { // from class: org.spongepowered.tools.obfuscation.mapping.mcp.MappingProviderSrg.1
            final BiMap val$packageMap;
            final BiMap val$classMap;
            final BiMap val$fieldMap;
            final BiMap val$methodMap;
            final File val$input;
            final MappingProviderSrg this$0;

            {
                this.this$0 = this;
                this.val$packageMap = biMap;
                this.val$classMap = biMap;
                this.val$fieldMap = biMap;
                this.val$methodMap = biMap;
                this.val$input = file;
            }

            public Object getResult() {
                return null;
            }

            public boolean processLine(String str) {
                if (Strings.isNullOrEmpty(str) || str.startsWith("#")) {
                    return true;
                }
                String strSubstring = str.substring(0, 2);
                String[] strArrSplit = str.substring(4).split(" ");
                if (strSubstring.equals("PK")) {
                    this.val$packageMap.forcePut(strArrSplit[0], strArrSplit[1]);
                    return true;
                }
                if (strSubstring.equals("CL")) {
                    this.val$classMap.forcePut(strArrSplit[0], strArrSplit[1]);
                    return true;
                }
                if (strSubstring.equals("FD")) {
                    this.val$fieldMap.forcePut(new MappingFieldSrg(strArrSplit[0]).copy(), new MappingFieldSrg(strArrSplit[1]).copy());
                    return true;
                }
                if (strSubstring.equals("MD")) {
                    this.val$methodMap.forcePut(new MappingMethod(strArrSplit[0], strArrSplit[1]), new MappingMethod(strArrSplit[2], strArrSplit[3]));
                    return true;
                }
                throw new MixinException("Invalid SRG file: " + this.val$input);
            }
        });
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.common.MappingProvider, org.spongepowered.tools.obfuscation.mapping.IMappingProvider
    public MappingField getFieldMapping(MappingField mappingField) {
        if (mappingField.getDesc() != null) {
            mappingField = new MappingFieldSrg(mappingField);
        }
        return (MappingField) this.fieldMap.get(mappingField);
    }
}
