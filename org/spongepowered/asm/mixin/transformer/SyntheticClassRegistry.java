package org.spongepowered.asm.mixin.transformer;

import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.mixin.throwables.MixinError;
import org.spongepowered.asm.service.ISyntheticClassInfo;
import org.spongepowered.asm.service.ISyntheticClassRegistry;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/SyntheticClassRegistry.class */
class SyntheticClassRegistry implements ISyntheticClassRegistry {
    private final Map classes = new HashMap();

    SyntheticClassRegistry() {
    }

    @Override // org.spongepowered.asm.service.ISyntheticClassRegistry
    public ISyntheticClassInfo findSyntheticClass(String str) {
        if (str == null) {
            return null;
        }
        return (ISyntheticClassInfo) this.classes.get(str.replace('.', '/'));
    }

    void registerSyntheticClass(ISyntheticClassInfo iSyntheticClassInfo) {
        String name = iSyntheticClassInfo.getName();
        ISyntheticClassInfo iSyntheticClassInfo2 = (ISyntheticClassInfo) this.classes.get(name);
        if (iSyntheticClassInfo2 != null) {
            if (iSyntheticClassInfo2 == iSyntheticClassInfo) {
            } else {
                throw new MixinError("Synthetic class with name " + name + " was already registered by " + iSyntheticClassInfo2.getMixin() + ". Duplicate being registered by " + iSyntheticClassInfo.getMixin());
            }
        } else {
            this.classes.put(name, iSyntheticClassInfo);
        }
    }
}
