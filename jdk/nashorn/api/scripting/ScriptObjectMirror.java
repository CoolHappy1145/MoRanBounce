package jdk.nashorn.api.scripting;

import java.nio.ByteBuffer;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import javax.script.Bindings;
import jdk.Exported;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAException;
import jdk.nashorn.internal.runtime.JSONListAdapter;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;

@Exported
/* loaded from: L-out.jar:jdk/nashorn/api/scripting/ScriptObjectMirror.class */
public final class ScriptObjectMirror extends AbstractJSObject implements Bindings {
    private static final AccessControlContext GET_CONTEXT_ACC_CTXT;
    private final ScriptObject sobj;
    private final Global global;
    private final boolean strict;
    private final boolean jsonCompatible;
    static final boolean $assertionsDisabled;

    public Object put(Object obj, Object obj2) {
        return put((String) obj, obj2);
    }

    static {
        $assertionsDisabled = !ScriptObjectMirror.class.desiredAssertionStatus();
        GET_CONTEXT_ACC_CTXT = getContextAccCtxt();
    }

    private static AccessControlContext getContextAccCtxt() {
        Permissions permissions = new Permissions();
        permissions.add(new RuntimePermission(Context.NASHORN_GET_CONTEXT));
        return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, permissions)});
    }

    public boolean equals(Object obj) {
        if (obj instanceof ScriptObjectMirror) {
            return this.sobj.equals(((ScriptObjectMirror) obj).sobj);
        }
        return false;
    }

    public int hashCode() {
        return this.sobj.hashCode();
    }

    public String toString() {
        return (String) inGlobal(new Callable(this) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.1
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public String call() {
                return ScriptRuntime.safeToString(this.this$0.sobj);
            }
        });
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public Object call(Object obj, Object[] objArr) {
        Global global = Context.getGlobal();
        boolean z = global != this.global;
        if (z) {
            try {
                try {
                    try {
                        Context.setGlobal(this.global);
                    } catch (Throwable th) {
                        throw new RuntimeException(th);
                    }
                } catch (Error | RuntimeException e) {
                    throw e;
                }
            } catch (NashornException e2) {
                throw e2.initEcmaError(this.global);
            } catch (Throwable th2) {
                if (z) {
                    Context.setGlobal(global);
                }
                throw th2;
            }
        }
        if (this.sobj instanceof ScriptFunction) {
            Object objWrapLikeMe = wrapLikeMe(ScriptRuntime.apply((ScriptFunction) this.sobj, unwrap(z ? wrapLikeMe(obj, global) : obj, this.global), unwrapArray(z ? wrapArrayLikeMe(objArr, global) : objArr, this.global)));
            if (z) {
                Context.setGlobal(global);
            }
            return objWrapLikeMe;
        }
        throw new RuntimeException("not a function: " + toString());
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public Object newObject(Object[] objArr) {
        Global global = Context.getGlobal();
        boolean z = global != this.global;
        if (z) {
            try {
                try {
                    try {
                        Context.setGlobal(this.global);
                    } catch (Throwable th) {
                        throw new RuntimeException(th);
                    }
                } catch (Error | RuntimeException e) {
                    throw e;
                }
            } catch (NashornException e2) {
                throw e2.initEcmaError(this.global);
            } catch (Throwable th2) {
                if (z) {
                    Context.setGlobal(global);
                }
                throw th2;
            }
        }
        if (this.sobj instanceof ScriptFunction) {
            Object objWrapLikeMe = wrapLikeMe(ScriptRuntime.construct((ScriptFunction) this.sobj, unwrapArray(z ? wrapArrayLikeMe(objArr, global) : objArr, this.global)));
            if (z) {
                Context.setGlobal(global);
            }
            return objWrapLikeMe;
        }
        throw new RuntimeException("not a constructor: " + toString());
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public Object eval(String str) {
        return inGlobal(new Callable(this, str) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.2
            final String val$s;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$s = str;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return this.this$0.wrapLikeMe(((Context) AccessController.doPrivileged(new PrivilegedAction(this) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.2.1
                    final CallableC00372 this$1;

                    {
                        this.this$1 = this;
                    }

                    @Override // java.security.PrivilegedAction
                    public Object run() {
                        return run();
                    }

                    @Override // java.security.PrivilegedAction
                    public Context run() {
                        return Context.getContext();
                    }
                }, ScriptObjectMirror.GET_CONTEXT_ACC_CTXT)).eval(this.this$0.global, this.val$s, this.this$0.sobj, null));
            }
        });
    }

    public Object callMember(String str, Object[] objArr) {
        Objects.requireNonNull(str);
        Global global = Context.getGlobal();
        boolean z = global != this.global;
        if (z) {
            try {
                try {
                    try {
                        Context.setGlobal(this.global);
                    } catch (Throwable th) {
                        throw new RuntimeException(th);
                    }
                } catch (Error | RuntimeException e) {
                    throw e;
                }
            } catch (NashornException e2) {
                throw e2.initEcmaError(this.global);
            } catch (Throwable th2) {
                if (z) {
                    Context.setGlobal(global);
                }
                throw th2;
            }
        }
        Object obj = this.sobj.get(str);
        if (obj instanceof ScriptFunction) {
            Object objWrapLikeMe = wrapLikeMe(ScriptRuntime.apply((ScriptFunction) obj, this.sobj, unwrapArray(z ? wrapArrayLikeMe(objArr, global) : objArr, this.global)));
            if (z) {
                Context.setGlobal(global);
            }
            return objWrapLikeMe;
        }
        if ((obj instanceof JSObject) && ((JSObject) obj).isFunction()) {
            Object objCall = ((JSObject) obj).call(this.sobj, objArr);
            if (z) {
                Context.setGlobal(global);
            }
            return objCall;
        }
        throw new NoSuchMethodException("No such function " + str);
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public Object getMember(String str) {
        Objects.requireNonNull(str);
        return inGlobal(new Callable(this, str) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.3
            final String val$name;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$name = str;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return this.this$0.wrapLikeMe(this.this$0.sobj.get(this.val$name));
            }
        });
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public Object getSlot(int i) {
        return inGlobal(new Callable(this, i) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.4
            final int val$index;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$index = i;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return this.this$0.wrapLikeMe(this.this$0.sobj.get(this.val$index));
            }
        });
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public boolean hasMember(String str) {
        Objects.requireNonNull(str);
        return ((Boolean) inGlobal(new Callable(this, str) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.5
            final String val$name;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$name = str;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(this.this$0.sobj.has(this.val$name));
            }
        })).booleanValue();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean hasSlot(int i) {
        return ((Boolean) inGlobal(new Callable(this, i) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.6
            final int val$slot;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$slot = i;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(this.this$0.sobj.has(this.val$slot));
            }
        })).booleanValue();
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public void removeMember(String str) {
        remove(Objects.requireNonNull(str));
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public void setMember(String str, Object obj) {
        put((String) Objects.requireNonNull(str), obj);
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public void setSlot(int i, Object obj) {
        inGlobal(new Callable(this, i, obj) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.7
            final int val$index;
            final Object val$value;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$index = i;
                this.val$value = obj;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Void call() {
                this.this$0.sobj.set(this.val$index, ScriptObjectMirror.unwrap(this.val$value, this.this$0.global), this.this$0.getCallSiteFlags());
                return null;
            }
        });
    }

    public void setIndexedPropertiesToExternalArrayData(ByteBuffer byteBuffer) {
        inGlobal(new Callable(this, byteBuffer) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.8
            final ByteBuffer val$buf;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$buf = byteBuffer;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Void call() {
                this.this$0.sobj.setArray(ArrayData.allocate(this.val$buf));
                return null;
            }
        });
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isInstance(Object obj) {
        if (!(obj instanceof ScriptObjectMirror)) {
            return false;
        }
        ScriptObjectMirror scriptObjectMirror = (ScriptObjectMirror) obj;
        if (this.global != scriptObjectMirror.global) {
            return false;
        }
        return ((Boolean) inGlobal(new Callable(this, scriptObjectMirror) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.9
            final ScriptObjectMirror val$mirror;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$mirror = scriptObjectMirror;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Boolean call() {
                ScriptObject unused = this.this$0.sobj;
                ScriptObject scriptObject = this.val$mirror.sobj;
                return false;
            }
        })).booleanValue();
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public String getClassName() {
        ScriptObject scriptObject = this.sobj;
        return "Object";
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isFunction() {
        return this.sobj instanceof ScriptFunction;
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isStrictFunction() {
        return isFunction() && ((ScriptFunction) this.sobj).isStrict();
    }

    @Override // jdk.nashorn.api.scripting.JSObject
    public boolean isArray() {
        return this.sobj.isArray();
    }

    public void clear() {
        inGlobal(new Callable(this) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.10
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                this.this$0.sobj.clear(this.this$0.strict);
                return null;
            }
        });
    }

    public boolean containsKey(Object obj) {
        checkKey(obj);
        return ((Boolean) inGlobal(new Callable(this, obj) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.11
            final Object val$key;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$key = obj;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(this.this$0.sobj.containsKey(this.val$key));
            }
        })).booleanValue();
    }

    public boolean containsValue(Object obj) {
        return ((Boolean) inGlobal(new Callable(this, obj) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.12
            final Object val$value;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$value = obj;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(this.this$0.sobj.containsValue(ScriptObjectMirror.unwrap(this.val$value, this.this$0.global)));
            }
        })).booleanValue();
    }

    public Set entrySet() {
        return (Set) inGlobal(new Callable(this) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.13
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Set call() {
                Iterator<String> itPropertyIterator = this.this$0.sobj.propertyIterator();
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                while (itPropertyIterator.hasNext()) {
                    String next = itPropertyIterator.next();
                    linkedHashSet.add(new AbstractMap.SimpleImmutableEntry(next, ScriptObjectMirror.translateUndefined(this.this$0.wrapLikeMe(this.this$0.sobj.get(next)))));
                }
                return Collections.unmodifiableSet(linkedHashSet);
            }
        });
    }

    public Object get(Object obj) {
        checkKey(obj);
        return inGlobal(new Callable(this, obj) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.14
            final Object val$key;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$key = obj;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return ScriptObjectMirror.translateUndefined(this.this$0.wrapLikeMe(this.this$0.sobj.get(this.val$key)));
            }
        });
    }

    public boolean isEmpty() {
        return ((Boolean) inGlobal(new Callable(this) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.15
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(this.this$0.sobj.isEmpty());
            }
        })).booleanValue();
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public Set keySet() {
        return (Set) inGlobal(new Callable(this) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.16
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Set call() {
                Iterator<String> itPropertyIterator = this.this$0.sobj.propertyIterator();
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                while (itPropertyIterator.hasNext()) {
                    linkedHashSet.add(itPropertyIterator.next());
                }
                return Collections.unmodifiableSet(linkedHashSet);
            }
        });
    }

    public Object put(String str, Object obj) {
        checkKey(str);
        Global global = Context.getGlobal();
        return inGlobal(new Callable(this, global != this.global, obj, global, str) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.17
            final boolean val$globalChanged;
            final Object val$value;
            final ScriptObject val$oldGlobal;
            final String val$key;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$globalChanged = z;
                this.val$value = obj;
                this.val$oldGlobal = global;
                this.val$key = str;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return ScriptObjectMirror.translateUndefined(this.this$0.wrapLikeMe(this.this$0.sobj.put(this.val$key, ScriptObjectMirror.unwrap(this.val$globalChanged ? this.this$0.wrapLikeMe(this.val$value, this.val$oldGlobal) : this.val$value, this.this$0.global), this.this$0.strict)));
            }
        });
    }

    public void putAll(Map map) {
        Objects.requireNonNull(map);
        Global global = Context.getGlobal();
        inGlobal(new Callable(this, map, global != this.global, global) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.18
            final Map val$map;
            final boolean val$globalChanged;
            final ScriptObject val$oldGlobal;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$map = map;
                this.val$globalChanged = z;
                this.val$oldGlobal = global;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                for (Map.Entry entry : this.val$map.entrySet()) {
                    Object value = entry.getValue();
                    Object objWrapLikeMe = this.val$globalChanged ? this.this$0.wrapLikeMe(value, this.val$oldGlobal) : value;
                    String str = (String) entry.getKey();
                    ScriptObjectMirror.checkKey(str);
                    this.this$0.sobj.set(str, ScriptObjectMirror.unwrap(objWrapLikeMe, this.this$0.global), this.this$0.getCallSiteFlags());
                }
                return null;
            }
        });
    }

    public Object remove(Object obj) {
        checkKey(obj);
        return inGlobal(new Callable(this, obj) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.19
            final Object val$key;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$key = obj;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return ScriptObjectMirror.translateUndefined(this.this$0.wrapLikeMe(this.this$0.sobj.remove(this.val$key, this.this$0.strict)));
            }
        });
    }

    public boolean delete(Object obj) {
        return ((Boolean) inGlobal(new Callable(this, obj) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.20
            final Object val$key;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$key = obj;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(this.this$0.sobj.delete(ScriptObjectMirror.unwrap(this.val$key, this.this$0.global), this.this$0.strict));
            }
        })).booleanValue();
    }

    public int size() {
        return ((Integer) inGlobal(new Callable(this) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.21
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Integer call() {
                return Integer.valueOf(this.this$0.sobj.size());
            }
        })).intValue();
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    public Collection values() {
        return (Collection) inGlobal(new Callable(this) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.22
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Collection call() {
                ArrayList arrayList = new ArrayList(this.this$0.size());
                Iterator<Object> itValueIterator = this.this$0.sobj.valueIterator();
                while (itValueIterator.hasNext()) {
                    arrayList.add(ScriptObjectMirror.translateUndefined(this.this$0.wrapLikeMe(itValueIterator.next())));
                }
                return Collections.unmodifiableList(arrayList);
            }
        });
    }

    public Object getProto() {
        return inGlobal(new Callable(this) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.23
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return this.this$0.wrapLikeMe(this.this$0.sobj.getProto());
            }
        });
    }

    public void setProto(Object obj) {
        inGlobal(new Callable(this, obj) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.24
            final Object val$proto;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$proto = obj;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Void call() {
                this.this$0.sobj.setPrototypeOf(ScriptObjectMirror.unwrap(this.val$proto, this.this$0.global));
                return null;
            }
        });
    }

    public Object getOwnPropertyDescriptor(String str) {
        return inGlobal(new Callable(this, str) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.25
            final String val$key;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$key = str;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return this.this$0.wrapLikeMe(this.this$0.sobj.getOwnPropertyDescriptor(this.val$key));
            }
        });
    }

    public String[] getOwnKeys(boolean z) {
        return (String[]) inGlobal(new Callable(this, z) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.26
            final boolean val$all;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$all = z;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public String[] call() {
                return this.this$0.sobj.getOwnKeys(this.val$all);
            }
        });
    }

    public ScriptObjectMirror preventExtensions() {
        return (ScriptObjectMirror) inGlobal(new Callable(this) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.27
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public ScriptObjectMirror call() {
                this.this$0.sobj.preventExtensions();
                return this.this$0;
            }
        });
    }

    public boolean isExtensible() {
        return ((Boolean) inGlobal(new Callable(this) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.28
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(this.this$0.sobj.isExtensible());
            }
        })).booleanValue();
    }

    public ScriptObjectMirror seal() {
        return (ScriptObjectMirror) inGlobal(new Callable(this) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.29
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public ScriptObjectMirror call() {
                this.this$0.sobj.seal();
                return this.this$0;
            }
        });
    }

    public boolean isSealed() {
        return ((Boolean) inGlobal(new Callable(this) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.30
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(this.this$0.sobj.isSealed());
            }
        })).booleanValue();
    }

    public ScriptObjectMirror freeze() {
        return (ScriptObjectMirror) inGlobal(new Callable(this) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.31
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public ScriptObjectMirror call() {
                this.this$0.sobj.freeze();
                return this.this$0;
            }
        });
    }

    public boolean isFrozen() {
        return ((Boolean) inGlobal(new Callable(this) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.32
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Boolean call() {
                return Boolean.valueOf(this.this$0.sobj.isFrozen());
            }
        })).booleanValue();
    }

    public static boolean isUndefined(Object obj) {
        return obj == ScriptRuntime.UNDEFINED;
    }

    /* renamed from: to */
    public Object m1to(Class cls) {
        return inGlobal(new Callable(this, cls) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.33
            final Class val$type;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$type = cls;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return this.val$type.cast(ScriptUtils.convert(this.this$0.sobj, this.val$type));
            }
        });
    }

    public static Object wrap(Object obj, Object obj2) {
        return wrap(obj, obj2, false);
    }

    public static Object wrapAsJSONCompatible(Object obj, Object obj2) {
        return wrap(obj, obj2, true);
    }

    private static Object wrap(Object obj, Object obj2, boolean z) {
        if (obj instanceof ScriptObject) {
            if (!(obj2 instanceof Global)) {
                return obj;
            }
            ScriptObject scriptObject = (ScriptObject) obj;
            Global global = (Global) obj2;
            ScriptObjectMirror scriptObjectMirror = new ScriptObjectMirror(scriptObject, global, z);
            if (z && scriptObject.isArray()) {
                return new JSONListAdapter(scriptObjectMirror, global);
            }
            return scriptObjectMirror;
        }
        if (obj instanceof ConsString) {
            return obj.toString();
        }
        if (z && (obj instanceof ScriptObjectMirror)) {
            return ((ScriptObjectMirror) obj).asJSONCompatible();
        }
        return obj;
    }

    private Object wrapLikeMe(Object obj, Object obj2) {
        return wrap(obj, obj2, this.jsonCompatible);
    }

    private Object wrapLikeMe(Object obj) {
        return wrapLikeMe(obj, this.global);
    }

    public static Object unwrap(Object obj, Object obj2) {
        if (obj instanceof ScriptObjectMirror) {
            ScriptObjectMirror scriptObjectMirror = (ScriptObjectMirror) obj;
            return scriptObjectMirror.global == obj2 ? scriptObjectMirror.sobj : obj;
        }
        if (obj instanceof JSONListAdapter) {
            return ((JSONListAdapter) obj).unwrap(obj2);
        }
        return obj;
    }

    public static Object[] wrapArray(Object[] objArr, Object obj) {
        return wrapArray(objArr, obj, false);
    }

    private static Object[] wrapArray(Object[] objArr, Object obj, boolean z) {
        if (objArr == null || objArr.length == 0) {
            return objArr;
        }
        Object[] objArr2 = new Object[objArr.length];
        int i = 0;
        for (Object obj2 : objArr) {
            objArr2[i] = wrap(obj2, obj, z);
            i++;
        }
        return objArr2;
    }

    private Object[] wrapArrayLikeMe(Object[] objArr, Object obj) {
        return wrapArray(objArr, obj, this.jsonCompatible);
    }

    public static Object[] unwrapArray(Object[] objArr, Object obj) {
        if (objArr == null || objArr.length == 0) {
            return objArr;
        }
        Object[] objArr2 = new Object[objArr.length];
        int i = 0;
        for (Object obj2 : objArr) {
            objArr2[i] = unwrap(obj2, obj);
            i++;
        }
        return objArr2;
    }

    public static boolean identical(Object obj, Object obj2) {
        return (obj instanceof ScriptObjectMirror ? ((ScriptObjectMirror) obj).sobj : obj) == (obj2 instanceof ScriptObjectMirror ? ((ScriptObjectMirror) obj2).sobj : obj2);
    }

    ScriptObjectMirror(ScriptObject scriptObject, Global global) {
        this(scriptObject, global, false);
    }

    private ScriptObjectMirror(ScriptObject scriptObject, Global global, boolean z) {
        if (!$assertionsDisabled && scriptObject == null) {
            throw new AssertionError("ScriptObjectMirror on null!");
        }
        if (!$assertionsDisabled && global == null) {
            throw new AssertionError("home Global is null");
        }
        this.sobj = scriptObject;
        this.global = global;
        this.strict = global.isStrictContext();
        this.jsonCompatible = z;
    }

    ScriptObject getScriptObject() {
        return this.sobj;
    }

    Global getHomeGlobal() {
        return this.global;
    }

    static Object translateUndefined(Object obj) {
        if (obj == ScriptRuntime.UNDEFINED) {
            return null;
        }
        return obj;
    }

    private int getCallSiteFlags() {
        return this.strict ? 2 : 0;
    }

    private Object inGlobal(Callable callable) {
        Global global = Context.getGlobal();
        boolean z = global != this.global;
        if (z) {
            Context.setGlobal(this.global);
        }
        try {
            Object objCall = callable.call();
            if (z) {
                Context.setGlobal(global);
            }
            return objCall;
        } catch (NashornException e) {
            throw e.initEcmaError(this.global);
        } catch (RuntimeException e2) {
            throw e2;
        } catch (Exception e3) {
            throw new AssertionError("Cannot happen", e3);
        } catch (Throwable th) {
            if (z) {
                Context.setGlobal(global);
            }
            throw th;
        }
    }

    private static void checkKey(Object obj) {
        Objects.requireNonNull(obj, "key can not be null");
        if (!(obj instanceof String)) {
            throw new ClassCastException("key should be a String. It is " + obj.getClass().getName() + " instead.");
        }
        if (((String) obj).length() == 0) {
            throw new IllegalArgumentException("key can not be empty");
        }
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject, jdk.nashorn.api.scripting.JSObject
    @Deprecated
    public double toNumber() {
        return ((Double) inGlobal(new Callable(this) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.34
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public Double call() {
                return Double.valueOf(JSType.toNumber(this.this$0.sobj));
            }
        })).doubleValue();
    }

    @Override // jdk.nashorn.api.scripting.AbstractJSObject
    public Object getDefaultValue(Class cls) {
        return inGlobal(new Callable(this, cls) { // from class: jdk.nashorn.api.scripting.ScriptObjectMirror.35
            final Class val$hint;
            final ScriptObjectMirror this$0;

            {
                this.this$0 = this;
                this.val$hint = cls;
            }

            @Override // java.util.concurrent.Callable
            public Object call() {
                try {
                    return this.this$0.sobj.getDefaultValue(this.val$hint);
                } catch (ECMAException e) {
                    throw new UnsupportedOperationException(e.getMessage(), e);
                }
            }
        });
    }

    private ScriptObjectMirror asJSONCompatible() {
        if (this.jsonCompatible) {
            return this;
        }
        return new ScriptObjectMirror(this.sobj, this.global, true);
    }
}
