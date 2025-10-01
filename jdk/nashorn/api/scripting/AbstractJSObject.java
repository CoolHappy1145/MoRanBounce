package jdk.nashorn.api.scripting;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import jdk.Exported;
import jdk.nashorn.internal.runtime.JSONListAdapter;
import jdk.nashorn.internal.runtime.JSType;

@Exported
/* loaded from: L-out.jar:jdk/nashorn/api/scripting/AbstractJSObject.class */
public abstract class AbstractJSObject implements JSObject {
    @Override // jdk.nashorn.api.scripting.JSObject
    public Object call(Object obj, Object[] objArr) {
        throw new UnsupportedOperationException("call");
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object newObject(Object[] objArr) {
        throw new UnsupportedOperationException("newObject");
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object eval(String str) {
        throw new UnsupportedOperationException("eval");
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object getMember(String str) {
        Objects.requireNonNull(str);
        return null;
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean hasMember(String str) {
        Objects.requireNonNull(str);
        return false;
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public void removeMember(String str) {
        Objects.requireNonNull(str);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public void setMember(String str, Object obj) {
        Objects.requireNonNull(str);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Set keySet() {
        return Collections.emptySet();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Collection values() {
        return Collections.emptySet();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isInstanceOf(Object obj) {
        if (obj instanceof JSObject) {
            return ((JSObject) obj).isInstance(this);
        }
        return false;
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public String getClassName() {
        return getClass().getName();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    @Deprecated
    public double toNumber() {
        return JSType.toNumber(JSType.toPrimitive((JSObject) this, Number.class));
    }

    public Object getDefaultValue(Class cls) {
        return DefaultValueImpl.getDefaultValue(this, cls);
    }

    public static Object getDefaultValue(JSObject jSObject, Class cls) {
        if (jSObject instanceof AbstractJSObject) {
            return ((AbstractJSObject) jSObject).getDefaultValue(cls);
        }
        if (jSObject instanceof JSONListAdapter) {
            return ((JSONListAdapter) jSObject).getDefaultValue(cls);
        }
        return DefaultValueImpl.getDefaultValue(jSObject, cls);
    }
}
