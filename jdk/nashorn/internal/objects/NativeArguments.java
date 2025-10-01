package jdk.nashorn.internal.objects;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.runtime.AccessorProperty;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.PropertyDescriptor;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;

/* loaded from: L-out.jar:jdk/nashorn/internal/objects/NativeArguments.class */
public final class NativeArguments extends ScriptObject {
    private static final MethodHandle G$LENGTH;
    private static final MethodHandle S$LENGTH;
    private static final MethodHandle G$CALLEE;
    private static final MethodHandle S$CALLEE;
    private static final PropertyMap map$;
    private Object length;
    private Object callee;
    private final int numMapped;
    private final int numParams;
    private ArrayData unmappedArgs;
    private BitSet deleted;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !NativeArguments.class.desiredAssertionStatus();
        G$LENGTH = findOwnMH("G$length", Object.class, new Class[]{Object.class});
        S$LENGTH = findOwnMH("S$length", Void.TYPE, new Class[]{Object.class, Object.class});
        G$CALLEE = findOwnMH("G$callee", Object.class, new Class[]{Object.class});
        S$CALLEE = findOwnMH("S$callee", Void.TYPE, new Class[]{Object.class, Object.class});
        ArrayList arrayList = new ArrayList(2);
        arrayList.add(AccessorProperty.create("length", 2, G$LENGTH, S$LENGTH));
        arrayList.add(AccessorProperty.create("callee", 2, G$CALLEE, S$CALLEE));
        map$ = PropertyMap.newMap(arrayList);
    }

    static PropertyMap getInitialMap() {
        return map$;
    }

    NativeArguments(Object[] objArr, Object obj, int i, ScriptObject scriptObject, PropertyMap propertyMap) {
        super(scriptObject, propertyMap);
        setIsArguments();
        setArray(ArrayData.allocate(objArr));
        this.length = Integer.valueOf(objArr.length);
        this.callee = obj;
        this.numMapped = Math.min(i, objArr.length);
        this.numParams = i;
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Object getArgument(int i) {
        if ($assertionsDisabled || (i >= 0 && i < this.numParams)) {
            return isMapped(i) ? getArray().getObject(i) : getUnmappedArg(i);
        }
        throw new AssertionError("invalid argument index");
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public void setArgument(int i, Object obj) {
        if (!$assertionsDisabled && (i < 0 || i >= this.numParams)) {
            throw new AssertionError("invalid argument index");
        }
        if (isMapped(i)) {
            setArray(getArray().set(i, obj, false));
        } else {
            setUnmappedArg(i, obj);
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(int i, boolean z) {
        int i2 = i >= 0 ? i : -1;
        return isMapped(i2) ? deleteMapped(i2, z) : super.delete(i, z);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(double d, boolean z) {
        int arrayIndex = ArrayIndex.getArrayIndex(d);
        return isMapped(arrayIndex) ? deleteMapped(arrayIndex, z) : super.delete(d, z);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject, jdk.nashorn.internal.runtime.PropertyAccess
    public boolean delete(Object obj, boolean z) {
        Object primitive = JSType.toPrimitive(obj, String.class);
        int arrayIndex = ArrayIndex.getArrayIndex(primitive);
        return isMapped(arrayIndex) ? deleteMapped(arrayIndex, z) : super.delete(primitive, z);
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public boolean defineOwnProperty(String str, Object obj, boolean z) {
        int arrayIndex = ArrayIndex.getArrayIndex(str);
        if (arrayIndex >= 0) {
            boolean zIsMapped = isMapped(arrayIndex);
            Object object = zIsMapped ? getArray().getObject(arrayIndex) : null;
            if (!super.defineOwnProperty(str, obj, false)) {
                if (z) {
                    throw ECMAErrors.typeError("cant.redefine.property", new String[]{str, ScriptRuntime.safeToString(this)});
                }
                return false;
            }
            if (zIsMapped) {
                PropertyDescriptor propertyDescriptor = toPropertyDescriptor(Global.instance(), obj);
                if (propertyDescriptor.type() == 2) {
                    setDeleted(arrayIndex, object);
                    return true;
                }
                if (propertyDescriptor.has(PropertyDescriptor.WRITABLE) && !propertyDescriptor.isWritable()) {
                    setDeleted(arrayIndex, propertyDescriptor.has(PropertyDescriptor.VALUE) ? propertyDescriptor.getValue() : object);
                    return true;
                }
                if (propertyDescriptor.has(PropertyDescriptor.VALUE)) {
                    setArray(getArray().set(arrayIndex, propertyDescriptor.getValue(), false));
                    return true;
                }
                return true;
            }
            return true;
        }
        return super.defineOwnProperty(str, obj, z);
    }

    private boolean isDeleted(int i) {
        return this.deleted != null && this.deleted.get(i);
    }

    private void setDeleted(int i, Object obj) {
        if (this.deleted == null) {
            this.deleted = new BitSet(this.numMapped);
        }
        this.deleted.set(i, true);
        setUnmappedArg(i, obj);
    }

    private boolean deleteMapped(int i, boolean z) {
        Object object = getArray().getObject(i);
        boolean zDelete = super.delete(i, z);
        if (zDelete) {
            setDeleted(i, object);
        }
        return zDelete;
    }

    private Object getUnmappedArg(int i) {
        if ($assertionsDisabled || (i >= 0 && i < this.numParams)) {
            return this.unmappedArgs == null ? ScriptRuntime.UNDEFINED : this.unmappedArgs.getObject(i);
        }
        throw new AssertionError();
    }

    private void setUnmappedArg(int i, Object obj) {
        if (!$assertionsDisabled && (i < 0 || i >= this.numParams)) {
            throw new AssertionError();
        }
        if (this.unmappedArgs == null) {
            Object[] objArr = new Object[this.numParams];
            System.arraycopy(getArray().asObjectArray(), 0, objArr, 0, this.numMapped);
            if (this.numMapped < this.numParams) {
                Arrays.fill(objArr, this.numMapped, this.numParams, ScriptRuntime.UNDEFINED);
            }
            this.unmappedArgs = ArrayData.allocate(objArr);
        }
        this.unmappedArgs = this.unmappedArgs.set(i, obj, false);
    }

    private boolean isMapped(int i) {
        return i >= 0 && i < this.numMapped && !isDeleted(i);
    }

    public static ScriptObject allocate(Object[] objArr, ScriptFunction scriptFunction, int i) {
        boolean z = scriptFunction == null || scriptFunction.isStrict();
        ScriptObject objectPrototype = Global.instance().getObjectPrototype();
        if (z) {
            return new NativeStrictArguments(objArr, i, objectPrototype, NativeStrictArguments.getInitialMap());
        }
        return new NativeArguments(objArr, scriptFunction, i, objectPrototype, getInitialMap());
    }

    public static Object G$length(Object obj) {
        if (obj instanceof NativeArguments) {
            return ((NativeArguments) obj).getArgumentsLength();
        }
        return 0;
    }

    public static void S$length(Object obj, Object obj2) {
        if (obj instanceof NativeArguments) {
            ((NativeArguments) obj).setArgumentsLength(obj2);
        }
    }

    public static Object G$callee(Object obj) {
        if (obj instanceof NativeArguments) {
            return ((NativeArguments) obj).getCallee();
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static void S$callee(Object obj, Object obj2) {
        if (obj instanceof NativeArguments) {
            ((NativeArguments) obj).setCallee(obj2);
        }
    }

    @Override // jdk.nashorn.internal.runtime.ScriptObject
    public Object getLength() {
        return this.length;
    }

    private Object getArgumentsLength() {
        return this.length;
    }

    private void setArgumentsLength(Object obj) {
        this.length = obj;
    }

    private Object getCallee() {
        return this.callee;
    }

    private void setCallee(Object obj) {
        this.callee = obj;
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), NativeArguments.class, str, Lookup.f31MH.type(cls, clsArr));
    }
}
