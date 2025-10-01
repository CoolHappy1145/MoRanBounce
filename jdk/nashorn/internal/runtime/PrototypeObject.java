package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.Global;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/PrototypeObject.class */
public class PrototypeObject extends ScriptObject {
    private static final PropertyMap map$;
    private Object constructor;
    private static final MethodHandle GET_CONSTRUCTOR = findOwnMH("getConstructor", Object.class, new Class[]{Object.class});
    private static final MethodHandle SET_CONSTRUCTOR = findOwnMH("setConstructor", Void.TYPE, new Class[]{Object.class, Object.class});

    static {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(AccessorProperty.create("constructor", 2, GET_CONSTRUCTOR, SET_CONSTRUCTOR));
        map$ = PropertyMap.newMap(arrayList);
    }

    private PrototypeObject(Global global, PropertyMap propertyMap) {
        super(global.getObjectPrototype(), propertyMap != map$ ? propertyMap.addAll(map$) : map$);
    }

    protected PrototypeObject() {
        this(Global.instance(), map$);
    }

    protected PrototypeObject(PropertyMap propertyMap) {
        this(Global.instance(), propertyMap);
    }

    protected PrototypeObject(ScriptFunction scriptFunction) {
        this(Global.instance(), map$);
        this.constructor = scriptFunction;
    }

    public static Object getConstructor(Object obj) {
        return obj instanceof PrototypeObject ? ((PrototypeObject) obj).getConstructor() : ScriptRuntime.UNDEFINED;
    }

    public static void setConstructor(Object obj, Object obj2) {
        if (obj instanceof PrototypeObject) {
            ((PrototypeObject) obj).setConstructor(obj2);
        }
    }

    private Object getConstructor() {
        return this.constructor;
    }

    private void setConstructor(Object obj) {
        this.constructor = obj;
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), PrototypeObject.class, str, Lookup.f31MH.type(cls, clsArr));
    }
}
