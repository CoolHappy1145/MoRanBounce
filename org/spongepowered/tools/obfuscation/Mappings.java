package org.spongepowered.tools.obfuscation;

import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.mapping.IMappingConsumer;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/Mappings.class */
class Mappings implements IMappingConsumer {
    private final Map fieldMappings = new HashMap();
    private final Map methodMappings = new HashMap();
    private UniqueMappings unique;

    /* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/Mappings$MappingConflictException.class */
    public static class MappingConflictException extends RuntimeException {
        private static final long serialVersionUID = 1;
        private final IMapping oldMapping;
        private final IMapping newMapping;

        public MappingConflictException(IMapping iMapping, IMapping iMapping2) {
            this.oldMapping = iMapping;
            this.newMapping = iMapping2;
        }

        public IMapping getOld() {
            return this.oldMapping;
        }

        public IMapping getNew() {
            return this.newMapping;
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/Mappings$UniqueMappings.class */
    static class UniqueMappings implements IMappingConsumer {
        private final IMappingConsumer mappings;
        private final Map fields = new HashMap();
        private final Map methods = new HashMap();

        public UniqueMappings(IMappingConsumer iMappingConsumer) {
            this.mappings = iMappingConsumer;
        }

        @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
        public void clear() {
            clearMaps();
            this.mappings.clear();
        }

        protected void clearMaps() {
            this.fields.clear();
            this.methods.clear();
        }

        @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
        public void addFieldMapping(ObfuscationType obfuscationType, MappingField mappingField, MappingField mappingField2) {
            if (!checkForExistingMapping(obfuscationType, mappingField, mappingField2, this.fields)) {
                this.mappings.addFieldMapping(obfuscationType, mappingField, mappingField2);
            }
        }

        @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
        public void addMethodMapping(ObfuscationType obfuscationType, MappingMethod mappingMethod, MappingMethod mappingMethod2) {
            if (!checkForExistingMapping(obfuscationType, mappingMethod, mappingMethod2, this.methods)) {
                this.mappings.addMethodMapping(obfuscationType, mappingMethod, mappingMethod2);
            }
        }

        private boolean checkForExistingMapping(ObfuscationType obfuscationType, IMapping iMapping, IMapping iMapping2, Map map) {
            Map map2 = (Map) map.get(obfuscationType);
            if (map2 == null) {
                map2 = new HashMap();
                map.put(obfuscationType, map2);
            }
            IMapping iMapping3 = (IMapping) map2.get(iMapping);
            if (iMapping3 != null) {
                if (iMapping3.equals(iMapping2)) {
                    return true;
                }
                throw new MappingConflictException(iMapping3, iMapping2);
            }
            map2.put(iMapping, iMapping2);
            return false;
        }

        @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
        public IMappingConsumer.MappingSet getFieldMappings(ObfuscationType obfuscationType) {
            return this.mappings.getFieldMappings(obfuscationType);
        }

        @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
        public IMappingConsumer.MappingSet getMethodMappings(ObfuscationType obfuscationType) {
            return this.mappings.getMethodMappings(obfuscationType);
        }
    }

    public Mappings() {
        init();
    }

    private void init() {
        for (ObfuscationType obfuscationType : ObfuscationType.types()) {
            this.fieldMappings.put(obfuscationType, new IMappingConsumer.MappingSet());
            this.methodMappings.put(obfuscationType, new IMappingConsumer.MappingSet());
        }
    }

    public IMappingConsumer asUnique() {
        if (this.unique == null) {
            this.unique = new UniqueMappings(this);
        }
        return this.unique;
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
    public IMappingConsumer.MappingSet getFieldMappings(ObfuscationType obfuscationType) {
        IMappingConsumer.MappingSet mappingSet = (IMappingConsumer.MappingSet) this.fieldMappings.get(obfuscationType);
        return mappingSet != null ? mappingSet : new IMappingConsumer.MappingSet();
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
    public IMappingConsumer.MappingSet getMethodMappings(ObfuscationType obfuscationType) {
        IMappingConsumer.MappingSet mappingSet = (IMappingConsumer.MappingSet) this.methodMappings.get(obfuscationType);
        return mappingSet != null ? mappingSet : new IMappingConsumer.MappingSet();
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
    public void clear() {
        this.fieldMappings.clear();
        this.methodMappings.clear();
        if (this.unique != null) {
            this.unique.clearMaps();
        }
        init();
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
    public void addFieldMapping(ObfuscationType obfuscationType, MappingField mappingField, MappingField mappingField2) {
        IMappingConsumer.MappingSet mappingSet = (IMappingConsumer.MappingSet) this.fieldMappings.get(obfuscationType);
        if (mappingSet == null) {
            mappingSet = new IMappingConsumer.MappingSet();
            this.fieldMappings.put(obfuscationType, mappingSet);
        }
        mappingSet.add(new IMappingConsumer.MappingSet.Pair(mappingField, mappingField2));
    }

    @Override // org.spongepowered.tools.obfuscation.mapping.IMappingConsumer
    public void addMethodMapping(ObfuscationType obfuscationType, MappingMethod mappingMethod, MappingMethod mappingMethod2) {
        IMappingConsumer.MappingSet mappingSet = (IMappingConsumer.MappingSet) this.methodMappings.get(obfuscationType);
        if (mappingSet == null) {
            mappingSet = new IMappingConsumer.MappingSet();
            this.methodMappings.put(obfuscationType, mappingSet);
        }
        mappingSet.add(new IMappingConsumer.MappingSet.Pair(mappingMethod, mappingMethod2));
    }
}
