package jdk.nashorn.internal.scripts;

import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;

/* renamed from: jdk.nashorn.internal.scripts.JD */
/* loaded from: L-out.jar:jdk/nashorn/internal/scripts/JD.class */
public class C0276JD extends ScriptObject {
    private static final PropertyMap map$ = PropertyMap.newMap((Class<? extends ScriptObject>) C0276JD.class);

    public static PropertyMap getInitialMap() {
        return map$;
    }

    public C0276JD(PropertyMap propertyMap) {
        super(propertyMap);
    }

    public C0276JD(ScriptObject scriptObject) {
        super(scriptObject, getInitialMap());
    }

    public C0276JD(PropertyMap propertyMap, long[] jArr, Object[] objArr) {
        super(propertyMap, jArr, objArr);
    }

    public static ScriptObject allocate(PropertyMap propertyMap) {
        return new C0276JD(propertyMap);
    }
}
