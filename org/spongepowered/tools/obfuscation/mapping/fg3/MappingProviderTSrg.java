package org.spongepowered.tools.obfuscation.mapping.fg3;

import com.google.common.base.Strings;
import com.google.common.collect.BiMap;
import com.google.common.io.Files;
import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.asm.obfuscation.mapping.mcp.MappingFieldSrg;
import org.spongepowered.tools.obfuscation.mapping.common.MappingProvider;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mapping/fg3/MappingProviderTSrg.class */
public class MappingProviderTSrg extends MappingProvider {
    private List inputMappings;

    public MappingProviderTSrg(Messager messager, Filer filer) {
        super(messager, filer);
        this.inputMappings = new ArrayList();
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingProvider
    public void read(File file) {
        BiMap biMap = this.packageMap;
        BiMap biMap2 = this.classMap;
        BiMap biMap3 = this.fieldMap;
        BiMap biMap4 = this.methodMap;
        String str = null;
        String str2 = null;
        this.inputMappings.addAll(Files.readLines(file, Charset.defaultCharset()));
        for (String str3 : this.inputMappings) {
            if (!Strings.isNullOrEmpty(str3) && !str3.startsWith("#")) {
                String[] strArrSplit = str3.split(" ");
                if (str3.startsWith("\t")) {
                    if (str == null) {
                        throw new IllegalStateException("Error parsing TSRG file, found member declaration with no class: " + str3);
                    }
                    strArrSplit[0] = strArrSplit[0].substring(1);
                    if (strArrSplit.length == 2) {
                        biMap3.forcePut(new MappingField(str, strArrSplit[0]), new MappingField(str2, strArrSplit[1]));
                    } else if (strArrSplit.length == 3) {
                        biMap4.forcePut(new MappingMethod(str, strArrSplit[0], strArrSplit[1]), new MappingMethodLazy(str2, strArrSplit[2], strArrSplit[1], this));
                    } else {
                        throw new IllegalStateException("Error parsing TSRG file, too many arguments: " + str3);
                    }
                } else if (strArrSplit.length > 1) {
                    String str4 = strArrSplit[0];
                    if (strArrSplit.length == 2) {
                        String str5 = strArrSplit[1];
                        if (str4.endsWith("/")) {
                            biMap.forcePut(str4.substring(0, str4.length() - 1), str5.substring(0, str5.length() - 1));
                        } else {
                            biMap2.forcePut(str4, str5);
                            str = str4;
                            str2 = str5;
                        }
                    } else if (strArrSplit.length <= 2) {
                        continue;
                    } else {
                        String str6 = (String) biMap2.get(str4);
                        if (str6 == null) {
                            throw new IllegalStateException("Error parsing TSRG file, found inline member before class mapping: " + str3);
                        }
                        if (strArrSplit.length == 3) {
                            biMap3.forcePut(new MappingField(str4, strArrSplit[1]), new MappingField(str6, strArrSplit[2]));
                        } else if (strArrSplit.length == 4) {
                            biMap4.forcePut(new MappingMethod(str4, strArrSplit[1], strArrSplit[2]), new MappingMethodLazy(str6, strArrSplit[3], strArrSplit[2], this));
                        } else {
                            throw new IllegalStateException("Error parsing TSRG file, too many arguments: " + str3);
                        }
                    }
                } else {
                    throw new IllegalStateException("Error parsing TSRG, unrecognised directive: " + str3);
                }
            }
        }
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.common.MappingProvider, org.spongepowered.tools.obfuscation.mapping.IMappingProvider
    public MappingField getFieldMapping(MappingField mappingField) {
        if (mappingField.getDesc() != null) {
            mappingField = new MappingFieldSrg(mappingField);
        }
        return (MappingField) this.fieldMap.get(mappingField);
    }

    List getInputMappings() {
        return this.inputMappings;
    }
}
