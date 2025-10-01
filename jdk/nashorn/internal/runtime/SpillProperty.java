package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import jdk.nashorn.internal.lookup.Lookup;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/SpillProperty.class */
public class SpillProperty extends AccessorProperty {
    private static final long serialVersionUID = 3028496245198669460L;
    private static final MethodHandles.Lookup LOOKUP = MethodHandles.lookup();
    private static final MethodHandle PARRAY_GETTER = Lookup.f31MH.asType(Lookup.f31MH.getter(LOOKUP, ScriptObject.class, "primitiveSpill", long[].class), Lookup.f31MH.type(long[].class, new Class[]{Object.class}));
    private static final MethodHandle OARRAY_GETTER = Lookup.f31MH.asType(Lookup.f31MH.getter(LOOKUP, ScriptObject.class, "objectSpill", Object[].class), Lookup.f31MH.type(Object[].class, new Class[]{Object.class}));
    private static final MethodHandle OBJECT_GETTER = Lookup.f31MH.filterArguments(Lookup.f31MH.arrayElementGetter(Object[].class), 0, new MethodHandle[]{OARRAY_GETTER});
    private static final MethodHandle PRIMITIVE_GETTER = Lookup.f31MH.filterArguments(Lookup.f31MH.arrayElementGetter(long[].class), 0, new MethodHandle[]{PARRAY_GETTER});
    private static final MethodHandle OBJECT_SETTER = Lookup.f31MH.filterArguments(Lookup.f31MH.arrayElementSetter(Object[].class), 0, new MethodHandle[]{OARRAY_GETTER});
    private static final MethodHandle PRIMITIVE_SETTER = Lookup.f31MH.filterArguments(Lookup.f31MH.arrayElementSetter(long[].class), 0, new MethodHandle[]{PARRAY_GETTER});

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/SpillProperty$Accessors.class */
    private static class Accessors {
        private MethodHandle objectGetter;
        private MethodHandle objectSetter;
        private MethodHandle primitiveGetter;
        private MethodHandle primitiveSetter;
        private final int slot;
        private final MethodHandle ensureSpillSize;
        private static Accessors[] ACCESSOR_CACHE;
        static final boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !SpillProperty.class.desiredAssertionStatus();
            ACCESSOR_CACHE = new Accessors[512];
        }

        Accessors(int i) {
            if (!$assertionsDisabled && i < 0) {
                throw new AssertionError();
            }
            this.slot = i;
            this.ensureSpillSize = Lookup.f31MH.asType(Lookup.f31MH.insertArguments(ScriptObject.ENSURE_SPILL_SIZE, 1, new Object[]{Integer.valueOf(i)}), Lookup.f31MH.type(Object.class, new Class[]{Object.class}));
        }

        private static void ensure(int i) {
            int length = ACCESSOR_CACHE.length;
            if (i >= length) {
                do {
                    length *= 2;
                } while (i >= length);
                Accessors[] accessorsArr = new Accessors[length];
                System.arraycopy(ACCESSOR_CACHE, 0, accessorsArr, 0, ACCESSOR_CACHE.length);
                ACCESSOR_CACHE = accessorsArr;
            }
        }

        static MethodHandle getCached(int i, boolean z, boolean z2) {
            ensure(i);
            Accessors accessors = ACCESSOR_CACHE[i];
            if (accessors == null) {
                accessors = new Accessors(i);
                ACCESSOR_CACHE[i] = accessors;
            }
            return accessors.getOrCreate(z, z2);
        }

        private static MethodHandle primordial(boolean z, boolean z2) {
            return z ? z2 ? SpillProperty.PRIMITIVE_GETTER : SpillProperty.PRIMITIVE_SETTER : z2 ? SpillProperty.OBJECT_GETTER : SpillProperty.OBJECT_SETTER;
        }

        MethodHandle getOrCreate(boolean z, boolean z2) {
            MethodHandle inner = getInner(z, z2);
            if (inner != null) {
                return inner;
            }
            MethodHandle methodHandleInsertArguments = Lookup.f31MH.insertArguments(primordial(z, z2), 1, new Object[]{Integer.valueOf(this.slot)});
            if (!z2) {
                methodHandleInsertArguments = Lookup.f31MH.filterArguments(methodHandleInsertArguments, 0, new MethodHandle[]{this.ensureSpillSize});
            }
            setInner(z, z2, methodHandleInsertArguments);
            return methodHandleInsertArguments;
        }

        void setInner(boolean z, boolean z2, MethodHandle methodHandle) {
            if (z) {
                if (z2) {
                    this.primitiveGetter = methodHandle;
                    return;
                } else {
                    this.primitiveSetter = methodHandle;
                    return;
                }
            }
            if (z2) {
                this.objectGetter = methodHandle;
            } else {
                this.objectSetter = methodHandle;
            }
        }

        MethodHandle getInner(boolean z, boolean z2) {
            return z ? z2 ? this.primitiveGetter : this.primitiveSetter : z2 ? this.objectGetter : this.objectSetter;
        }
    }

    private static MethodHandle primitiveGetter(int i, int i2) {
        if ((i2 & 2048) == 2048) {
            return Accessors.getCached(i, true, true);
        }
        return null;
    }

    private static MethodHandle primitiveSetter(int i, int i2) {
        if ((i2 & 2048) == 2048) {
            return Accessors.getCached(i, true, false);
        }
        return null;
    }

    private static MethodHandle objectGetter(int i) {
        return Accessors.getCached(i, false, true);
    }

    private static MethodHandle objectSetter(int i) {
        return Accessors.getCached(i, false, false);
    }

    public SpillProperty(String str, int i, int i2) {
        super(str, i, i2, primitiveGetter(i2, i), primitiveSetter(i2, i), objectGetter(i2), objectSetter(i2));
    }

    public SpillProperty(String str, int i, int i2, Class cls) {
        this(str, i, i2);
        setType(hasDualFields() ? cls : Object.class);
    }

    SpillProperty(String str, int i, int i2, ScriptObject scriptObject, Object obj) {
        this(str, i, i2);
        setInitialValue(scriptObject, obj);
    }

    protected SpillProperty(SpillProperty spillProperty) {
        super(spillProperty);
    }

    protected SpillProperty(SpillProperty spillProperty, Class cls) {
        super((AccessorProperty) spillProperty, cls);
    }

    @Override // jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    public Property copy() {
        return new SpillProperty(this);
    }

    @Override // jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    public Property copy(Class cls) {
        return new SpillProperty(this, cls);
    }

    @Override // jdk.nashorn.internal.runtime.AccessorProperty, jdk.nashorn.internal.runtime.Property
    void initMethodHandles(Class cls) {
        int slot = getSlot();
        this.primitiveGetter = primitiveGetter(slot, getFlags());
        this.primitiveSetter = primitiveSetter(slot, getFlags());
        this.objectGetter = objectGetter(slot);
        this.objectSetter = objectSetter(slot);
    }
}
