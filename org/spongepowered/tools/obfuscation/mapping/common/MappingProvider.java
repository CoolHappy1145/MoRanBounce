package org.spongepowered.tools.obfuscation.mapping.common;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mapping.IMappingProvider;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mapping/common/MappingProvider.class */
public abstract class MappingProvider implements IMappingProvider {
    protected final Messager messager;
    protected final Filer filer;
    protected final BiMap packageMap = HashBiMap.create();
    protected final BiMap classMap = HashBiMap.create();
    protected final BiMap fieldMap = HashBiMap.create();
    protected final BiMap methodMap = HashBiMap.create();

    public MappingProvider(Messager messager, Filer filer) {
        this.messager = messager;
        this.filer = filer;
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingProvider
    public void clear() {
        this.packageMap.clear();
        this.classMap.clear();
        this.fieldMap.clear();
        this.methodMap.clear();
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingProvider
    public boolean isEmpty() {
        return this.packageMap.isEmpty() && this.classMap.isEmpty() && this.fieldMap.isEmpty() && this.methodMap.isEmpty();
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingProvider
    public MappingMethod getMethodMapping(MappingMethod mappingMethod) {
        return (MappingMethod) this.methodMap.get(mappingMethod);
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingProvider
    public MappingField getFieldMapping(MappingField mappingField) {
        return (MappingField) this.fieldMap.get(mappingField);
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingProvider
    public String getClassMapping(String str) {
        return (String) this.classMap.get(str);
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingProvider
    public String getPackageMapping(String str) {
        return (String) this.packageMap.get(str);
    }
}
