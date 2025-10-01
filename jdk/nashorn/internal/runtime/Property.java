package jdk.nashorn.internal.runtime;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.SwitchPoint;
import java.util.Objects;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/Property.class */
public abstract class Property implements Serializable {
    public static final int WRITABLE_ENUMERABLE_CONFIGURABLE = 0;
    public static final int NOT_WRITABLE = 1;
    public static final int NOT_ENUMERABLE = 2;
    public static final int NOT_CONFIGURABLE = 4;
    private static final int MODIFY_MASK = 7;
    public static final int IS_PARAMETER = 8;
    public static final int HAS_ARGUMENTS = 16;
    public static final int IS_FUNCTION_DECLARATION = 32;
    public static final int IS_NASGEN_PRIMITIVE = 64;
    public static final int IS_BUILTIN = 128;
    public static final int IS_BOUND = 256;
    public static final int NEEDS_DECLARATION = 512;
    public static final int IS_LEXICAL_BINDING = 1024;
    public static final int DUAL_FIELDS = 2048;
    private final String key;
    private int flags;
    private final int slot;
    private Class type;
    protected SwitchPoint builtinSwitchPoint;
    private static final long serialVersionUID = 2099814273074501176L;
    static final boolean $assertionsDisabled;

    public abstract Property copy();

    public abstract Property copy(Class cls);

    public abstract MethodHandle getGetter(Class cls);

    public abstract MethodHandle getOptimisticGetter(Class cls, int i);

    abstract void initMethodHandles(Class cls);

    public abstract int getIntValue(ScriptObject scriptObject, ScriptObject scriptObject2);

    public abstract double getDoubleValue(ScriptObject scriptObject, ScriptObject scriptObject2);

    public abstract Object getObjectValue(ScriptObject scriptObject, ScriptObject scriptObject2);

    public abstract void setValue(ScriptObject scriptObject, ScriptObject scriptObject2, int i, boolean z);

    public abstract void setValue(ScriptObject scriptObject, ScriptObject scriptObject2, double d, boolean z);

    public abstract void setValue(ScriptObject scriptObject, ScriptObject scriptObject2, Object obj, boolean z);

    public abstract MethodHandle getSetter(Class cls, PropertyMap propertyMap);

    static {
        $assertionsDisabled = !Property.class.desiredAssertionStatus();
    }

    Property(String str, int i, int i2) {
        if (!$assertionsDisabled && str == null) {
            throw new AssertionError();
        }
        this.key = str;
        this.flags = i;
        this.slot = i2;
    }

    Property(Property property, int i) {
        this.key = property.key;
        this.slot = property.slot;
        this.builtinSwitchPoint = property.builtinSwitchPoint;
        this.flags = i;
    }

    static int mergeFlags(PropertyDescriptor propertyDescriptor, PropertyDescriptor propertyDescriptor2) {
        int i = 0;
        if (!(propertyDescriptor2.has(PropertyDescriptor.CONFIGURABLE) ? propertyDescriptor2.isConfigurable() : propertyDescriptor.isConfigurable())) {
            i = 4;
        }
        if (!(propertyDescriptor2.has(PropertyDescriptor.ENUMERABLE) ? propertyDescriptor2.isEnumerable() : propertyDescriptor.isEnumerable())) {
            i |= 2;
        }
        if (!(propertyDescriptor2.has(PropertyDescriptor.WRITABLE) ? propertyDescriptor2.isWritable() : propertyDescriptor.isWritable())) {
            i |= 1;
        }
        return i;
    }

    public final void setBuiltinSwitchPoint(SwitchPoint switchPoint) {
        this.builtinSwitchPoint = switchPoint;
    }

    public final SwitchPoint getBuiltinSwitchPoint() {
        return this.builtinSwitchPoint;
    }

    public boolean isBuiltin() {
        return (this.builtinSwitchPoint == null || this.builtinSwitchPoint.hasBeenInvalidated()) ? false : true;
    }

    static int toFlags(PropertyDescriptor propertyDescriptor) {
        int i = 0;
        if (!propertyDescriptor.isConfigurable()) {
            i = 4;
        }
        if (!propertyDescriptor.isEnumerable()) {
            i |= 2;
        }
        if (!propertyDescriptor.isWritable()) {
            i |= 1;
        }
        return i;
    }

    public boolean isWritable() {
        return (this.flags & 1) == 0;
    }

    public boolean isConfigurable() {
        return (this.flags & 4) == 0;
    }

    public boolean isEnumerable() {
        return (this.flags & 2) == 0;
    }

    public boolean isParameter() {
        return (this.flags & 8) != 0;
    }

    public boolean hasArguments() {
        return (this.flags & 16) != 0;
    }

    public boolean isBound() {
        return (this.flags & 256) != 0;
    }

    public boolean needsDeclaration() {
        return (this.flags & 512) != 0;
    }

    public Property addFlags(int i) {
        if ((this.flags & i) != i) {
            Property propertyCopy = copy();
            propertyCopy.flags |= i;
            return propertyCopy;
        }
        return this;
    }

    public int getFlags() {
        return this.flags;
    }

    public Property removeFlags(int i) {
        if ((this.flags & i) != 0) {
            Property propertyCopy = copy();
            propertyCopy.flags &= i ^ (-1);
            return propertyCopy;
        }
        return this;
    }

    public Property setFlags(int i) {
        if (this.flags != i) {
            Property propertyCopy = copy();
            propertyCopy.flags &= -8;
            propertyCopy.flags |= i & 7;
            return propertyCopy;
        }
        return this;
    }

    public String getKey() {
        return this.key;
    }

    public int getSlot() {
        return this.slot;
    }

    public int hashCode() {
        Class localType = getLocalType();
        return ((Objects.hashCode(this.key) ^ this.flags) ^ getSlot()) ^ (localType == null ? 0 : localType.hashCode());
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Property property = (Property) obj;
        return equalsWithoutType(property) && getLocalType() == property.getLocalType();
    }

    boolean equalsWithoutType(Property property) {
        return getFlags() == property.getFlags() && getSlot() == property.getSlot() && getKey().equals(property.getKey());
    }

    private static String type(Class cls) {
        if (cls == null) {
            return "undef";
        }
        if (cls == Integer.TYPE) {
            return "i";
        }
        if (cls == Double.TYPE) {
            return "d";
        }
        return "o";
    }

    public final String toStringShort() {
        StringBuilder sb = new StringBuilder();
        sb.append(getKey()).append(" (").append(type(getLocalType())).append(')');
        return sb.toString();
    }

    private static String indent(String str, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        for (int i2 = 0; i2 < i - str.length(); i2++) {
            sb.append(' ');
        }
        return sb.toString();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(indent(getKey(), 20)).append(" id=").append(Debug.m11id(this)).append(" (0x").append(indent(Integer.toHexString(this.flags), 4)).append(") ").append(getClass().getSimpleName()).append(" {").append(indent(type(getLocalType()), 5)).append('}');
        if (this.slot != -1) {
            sb.append(" [").append("slot=").append(this.slot).append(']');
        }
        return sb.toString();
    }

    public final Class getType() {
        return this.type;
    }

    public final void setType(Class cls) {
        if (!$assertionsDisabled && cls == Boolean.TYPE) {
            throw new AssertionError("no boolean storage support yet - fix this");
        }
        this.type = cls == null ? null : cls.isPrimitive() ? cls : Object.class;
    }

    protected Class getLocalType() {
        return getType();
    }

    public boolean isFunctionDeclaration() {
        return (this.flags & 32) != 0;
    }

    public boolean isLexicalBinding() {
        return (this.flags & 1024) != 0;
    }

    public boolean hasDualFields() {
        return (this.flags & 2048) != 0;
    }
}
