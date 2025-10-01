package org.spongepowered.asm.service.modlauncher;

import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.api.TypesafeMap;
import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.service.IGlobalPropertyService;
import org.spongepowered.asm.service.IPropertyKey;

/* loaded from: L-out.jar:org/spongepowered/asm/service/modlauncher/Blackboard.class */
public class Blackboard implements IGlobalPropertyService {
    private final Map keys = new HashMap();
    private final TypesafeMap blackboard = Launcher.INSTANCE.blackboard();

    /* loaded from: L-out.jar:org/spongepowered/asm/service/modlauncher/Blackboard$Key.class */
    class Key implements IPropertyKey {
        final TypesafeMap.Key key;
        final Blackboard this$0;

        public Key(Blackboard blackboard, TypesafeMap typesafeMap, String str, Class cls) {
            this.this$0 = blackboard;
            this.key = TypesafeMap.Key.getOrCreate(typesafeMap, str, cls);
        }
    }

    @Override // org.spongepowered.asm.service.IGlobalPropertyService
    public IPropertyKey resolveKey(String str) {
        return (IPropertyKey) this.keys.computeIfAbsent(str, this::lambda$resolveKey$0);
    }

    private IPropertyKey lambda$resolveKey$0(String str) {
        return new Key(this, this.blackboard, str, Object.class);
    }

    @Override // org.spongepowered.asm.service.IGlobalPropertyService
    public Object getProperty(IPropertyKey iPropertyKey) {
        return getProperty(iPropertyKey, null);
    }

    @Override // org.spongepowered.asm.service.IGlobalPropertyService
    public void setProperty(IPropertyKey iPropertyKey, Object obj) {
        this.blackboard.computeIfAbsent(((Key) iPropertyKey).key, (v1) -> {
            return lambda$setProperty$1(r2, v1);
        });
    }

    @Override // org.spongepowered.asm.service.IGlobalPropertyService
    public String getPropertyString(IPropertyKey iPropertyKey, String str) {
        return (String) getProperty(iPropertyKey, str);
    }

    @Override // org.spongepowered.asm.service.IGlobalPropertyService
    public Object getProperty(IPropertyKey iPropertyKey, Object obj) {
        return this.blackboard.get(((Key) iPropertyKey).key).orElse(obj);
    }
}
