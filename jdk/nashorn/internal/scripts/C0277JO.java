package jdk.nashorn.internal.scripts;

import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;

/* renamed from: jdk.nashorn.internal.scripts.JO */
/* loaded from: L-out.jar:jdk/nashorn/internal/scripts/JO.class */
public class C0277JO extends ScriptObject {
    private static final PropertyMap map$ = PropertyMap.newMap((Class<? extends ScriptObject>) C0277JO.class);

    public static PropertyMap getInitialMap() {
        return map$;
    }

    public C0277JO(PropertyMap propertyMap) {
        super(propertyMap);
    }

    public C0277JO(ScriptObject scriptObject) {
        super(scriptObject, getInitialMap());
    }

    public C0277JO(PropertyMap propertyMap, long[] jArr, Object[] objArr) {
        super(propertyMap, jArr, objArr);
    }

    public static ScriptObject allocate(PropertyMap propertyMap) {
        return new C0277JO(propertyMap);
    }
}
