package jdk.nashorn.internal.runtime;

import java.util.Collection;
import java.util.Set;
import jdk.nashorn.api.scripting.AbstractJSObject;
import jdk.nashorn.api.scripting.JSObject;
import jdk.nashorn.api.scripting.ScriptObjectMirror;
import jdk.nashorn.internal.objects.Global;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/JSONListAdapter.class */
public final class JSONListAdapter extends ListAdapter implements JSObject {
    public JSONListAdapter(JSObject jSObject, Global global) {
        super(jSObject, global);
    }

    public Object unwrap(Object obj) {
        Object objUnwrap = ScriptObjectMirror.unwrap(this.obj, obj);
        return objUnwrap != this.obj ? objUnwrap : this;
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object call(Object obj, Object[] objArr) {
        return this.obj.call(obj, objArr);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object newObject(Object[] objArr) {
        return this.obj.newObject(objArr);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object eval(String str) {
        return this.obj.eval(str);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object getMember(String str) {
        return this.obj.getMember(str);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object getSlot(int i) {
        return this.obj.getSlot(i);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean hasMember(String str) {
        return this.obj.hasMember(str);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean hasSlot(int i) {
        return this.obj.hasSlot(i);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public void removeMember(String str) {
        this.obj.removeMember(str);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public void setMember(String str, Object obj) {
        this.obj.setMember(str, obj);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public void setSlot(int i, Object obj) {
        this.obj.setSlot(i, obj);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Set keySet() {
        return this.obj.keySet();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Collection values() {
        return this.obj.values();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isInstance(Object obj) {
        return this.obj.isInstance(obj);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isInstanceOf(Object obj) {
        return this.obj.isInstanceOf(obj);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public String getClassName() {
        return this.obj.getClassName();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isFunction() {
        return this.obj.isFunction();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isStrictFunction() {
        return this.obj.isStrictFunction();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isArray() {
        return this.obj.isArray();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    @Deprecated
    public double toNumber() {
        return this.obj.toNumber();
    }

    public Object getDefaultValue(Class cls) {
        return AbstractJSObject.getDefaultValue(this.obj, cls);
    }
}
