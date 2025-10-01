package org.spongepowered.asm.service.mojang;

import net.minecraft.launchwrapper.Launch;
import org.spongepowered.asm.service.IGlobalPropertyService;
import org.spongepowered.asm.service.IPropertyKey;

/* loaded from: L-out.jar:org/spongepowered/asm/service/mojang/Blackboard.class */
public class Blackboard implements IGlobalPropertyService {

    /* loaded from: L-out.jar:org/spongepowered/asm/service/mojang/Blackboard$Key.class */
    class Key implements IPropertyKey {
        private final String key;
        final Blackboard this$0;

        Key(Blackboard blackboard, String str) {
            this.this$0 = blackboard;
            this.key = str;
        }

        public String toString() {
            return this.key;
        }
    }

    public Blackboard() {
        Launch.classLoader.hashCode();
    }

    @Override // org.spongepowered.asm.service.IGlobalPropertyService
    public IPropertyKey resolveKey(String str) {
        return new Key(this, str);
    }

    @Override // org.spongepowered.asm.service.IGlobalPropertyService
    public final Object getProperty(IPropertyKey iPropertyKey) {
        return Launch.blackboard.get(iPropertyKey.toString());
    }

    @Override // org.spongepowered.asm.service.IGlobalPropertyService
    public final void setProperty(IPropertyKey iPropertyKey, Object obj) {
        Launch.blackboard.put(iPropertyKey.toString(), obj);
    }

    @Override // org.spongepowered.asm.service.IGlobalPropertyService
    public final Object getProperty(IPropertyKey iPropertyKey, Object obj) {
        Object obj2 = Launch.blackboard.get(iPropertyKey.toString());
        return obj2 != null ? obj2 : obj;
    }

    @Override // org.spongepowered.asm.service.IGlobalPropertyService
    public final String getPropertyString(IPropertyKey iPropertyKey, String str) {
        Object obj = Launch.blackboard.get(iPropertyKey.toString());
        return obj != null ? obj.toString() : str;
    }
}
