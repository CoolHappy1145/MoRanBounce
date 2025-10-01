package org.spongepowered.tools.obfuscation.mapping;

import com.google.common.base.Objects;
import java.util.LinkedHashSet;
import org.spongepowered.asm.obfuscation.mapping.IMapping;
import org.spongepowered.asm.obfuscation.mapping.common.MappingField;
import org.spongepowered.asm.obfuscation.mapping.common.MappingMethod;
import org.spongepowered.tools.obfuscation.ObfuscationType;

/* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mapping/IMappingConsumer.class */
public interface IMappingConsumer {
    void clear();

    void addFieldMapping(ObfuscationType obfuscationType, MappingField mappingField, MappingField mappingField2);

    void addMethodMapping(ObfuscationType obfuscationType, MappingMethod mappingMethod, MappingMethod mappingMethod2);

    MappingSet getFieldMappings(ObfuscationType obfuscationType);

    MappingSet getMethodMappings(ObfuscationType obfuscationType);

    /* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mapping/IMappingConsumer$MappingSet.class */
    public static class MappingSet extends LinkedHashSet {
        private static final long serialVersionUID = 1;

        /* loaded from: L-out.jar:org/spongepowered/tools/obfuscation/mapping/IMappingConsumer$MappingSet$Pair.class */
        public static class Pair {
            public final IMapping from;

            /* renamed from: to */
            public final IMapping f236to;

            public Pair(IMapping iMapping, IMapping iMapping2) {
                this.from = iMapping;
                this.f236to = iMapping2;
            }

            public boolean equals(Object obj) {
                if (!(obj instanceof Pair)) {
                    return false;
                }
                Pair pair = (Pair) obj;
                return Objects.equal(this.from, pair.from) && Objects.equal(this.f236to, pair.f236to);
            }

            public int hashCode() {
                return Objects.hashCode(new Object[]{this.from, this.f236to});
            }

            public String toString() {
                return String.format("%s -> %s", this.from, this.f236to);
            }
        }
    }
}
