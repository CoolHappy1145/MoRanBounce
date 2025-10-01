package jdk.internal.dynalink.support;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: L-out.jar:jdk/internal/dynalink/support/ClassMap.class */
public abstract class ClassMap {
    private final ConcurrentMap map = new ConcurrentHashMap();
    private final Map weakMap = new WeakHashMap();
    private final ClassLoader classLoader;
    static final boolean $assertionsDisabled;

    protected abstract Object computeValue(Class cls);

    static {
        $assertionsDisabled = !ClassMap.class.desiredAssertionStatus();
    }

    protected ClassMap(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public Object get(Class cls) {
        Reference reference;
        Object obj;
        Object obj2;
        Object obj3 = this.map.get(cls);
        if (obj3 != null) {
            return obj3;
        }
        synchronized (this.weakMap) {
            reference = (Reference) this.weakMap.get(cls);
        }
        if (reference != null && (obj2 = reference.get()) != null) {
            return obj2;
        }
        Object objComputeValue = computeValue(cls);
        if (!$assertionsDisabled && objComputeValue == null) {
            throw new AssertionError();
        }
        if (Guards.canReferenceDirectly(this.classLoader, (ClassLoader) AccessController.doPrivileged(new PrivilegedAction(this, cls) { // from class: jdk.internal.dynalink.support.ClassMap.1
            final Class val$clazz;
            final ClassMap this$0;

            {
                this.this$0 = this;
                this.val$clazz = cls;
            }

            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            @Override // java.security.PrivilegedAction
            public ClassLoader run() {
                return this.val$clazz.getClassLoader();
            }
        }, ClassLoaderGetterContextProvider.GET_CLASS_LOADER_CONTEXT))) {
            Object objPutIfAbsent = this.map.putIfAbsent(cls, objComputeValue);
            return objPutIfAbsent != null ? objPutIfAbsent : objComputeValue;
        }
        synchronized (this.weakMap) {
            Reference reference2 = (Reference) this.weakMap.get(cls);
            if (reference2 != null && (obj = reference2.get()) != null) {
                return obj;
            }
            this.weakMap.put(cls, new SoftReference(objComputeValue));
            return objComputeValue;
        }
    }
}
