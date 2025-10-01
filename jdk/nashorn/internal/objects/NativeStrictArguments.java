package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;

/* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeStrictArguments.class */
public final class NativeStrictArguments extends ScriptObject {
    private static final MethodHandle G$LENGTH = findOwnMH("G$length", Object.class, new Class[]{Object.class});
    private static final MethodHandle S$LENGTH = findOwnMH("S$length", Void.TYPE, new Class[]{Object.class, Object.class});
    private static final PropertyMap map$;
    private Object length;
    private final Object[] namedArgs;

    static {
        ArrayList arrayList = new ArrayList(1);
        arrayList.add(AccessorProperty.create("length", 2, G$LENGTH, S$LENGTH));
        PropertyMap propertyMapNewMap = PropertyMap.newMap(arrayList);
        PropertyMap propertyMapAddPropertyNoHistory = propertyMapNewMap.addPropertyNoHistory(propertyMapNewMap.newUserAccessors("caller", 6));
        map$ = propertyMapAddPropertyNoHistory.addPropertyNoHistory(propertyMapAddPropertyNoHistory.newUserAccessors("callee", 6));
    }

    static PropertyMap getInitialMap() {
        return map$;
    }

    NativeStrictArguments(Object[] objArr, int i, ScriptObject scriptObject, PropertyMap propertyMap) {
        super(scriptObject, propertyMap);
        setIsArguments();
        ScriptFunction typeErrorThrower = Global.instance().getTypeErrorThrower();
        initUserAccessors("caller", 6, typeErrorThrower, typeErrorThrower);
        initUserAccessors("callee", 6, typeErrorThrower, typeErrorThrower);
        setArray(ArrayData.allocate(objArr));
        this.length = Integer.valueOf(objArr.length);
        this.namedArgs = new Object[i];
        if (i > objArr.length) {
            Arrays.fill(this.namedArgs, ScriptRuntime.UNDEFINED);
        }
        System.arraycopy(objArr, 0, this.namedArgs, 0, Math.min(this.namedArgs.length, objArr.length));
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Object getArgument(int i) {
        return (i < 0 || i >= this.namedArgs.length) ? ScriptRuntime.UNDEFINED : this.namedArgs[i];
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public void setArgument(int i, Object obj) {
        if (i >= 0 && i < this.namedArgs.length) {
            this.namedArgs[i] = obj;
        }
    }

    public static Object G$length(Object obj) {
        if (obj instanceof NativeStrictArguments) {
            return ((NativeStrictArguments) obj).getArgumentsLength();
        }
        return 0;
    }

    public static void S$length(Object obj, Object obj2) {
        if (obj instanceof NativeStrictArguments) {
            ((NativeStrictArguments) obj).setArgumentsLength(obj2);
        }
    }

    private Object getArgumentsLength() {
        return this.length;
    }

    private void setArgumentsLength(Object obj) {
        this.length = obj;
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), NativeStrictArguments.class, str, Lookup.f31MH.type(cls, clsArr));
    }
}
