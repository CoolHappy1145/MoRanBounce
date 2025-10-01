package jdk.nashorn.internal.runtime;

import java.io.Serializable;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.ref.WeakReference;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.ObjectClassGenerator;
import jdk.nashorn.internal.lookup.Lookup;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/AllocationStrategy.class */
public final class AllocationStrategy implements Serializable {
    private static final long serialVersionUID = 1;
    private static final MethodHandles.Lookup LOOKUP;
    private final int fieldCount;
    private final boolean dualFields;
    private String allocatorClassName;
    private MethodHandle allocator;
    private AllocatorMap lastMap;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !AllocationStrategy.class.desiredAssertionStatus();
        LOOKUP = MethodHandles.lookup();
    }

    public AllocationStrategy(int i, boolean z) {
        this.fieldCount = i;
        this.dualFields = z;
    }

    private String getAllocatorClassName() {
        if (this.allocatorClassName == null) {
            this.allocatorClassName = Compiler.binaryName(ObjectClassGenerator.getClassName(this.fieldCount, this.dualFields)).intern();
        }
        return this.allocatorClassName;
    }

    PropertyMap getAllocatorMap(ScriptObject scriptObject) {
        if (!$assertionsDisabled && scriptObject == null) {
            throw new AssertionError();
        }
        PropertyMap map = scriptObject.getMap();
        if (this.lastMap != null) {
            if (!this.lastMap.hasSharedProtoMap()) {
                if (!this.lastMap.hasSamePrototype(scriptObject)) {
                    if (this.lastMap.hasSameProtoMap(map) && this.lastMap.hasUnchangedProtoMap()) {
                        PropertyMap propertyMapNewMap = PropertyMap.newMap(null, getAllocatorClassName(), 0, this.fieldCount, 0);
                        SharedPropertyMap sharedPropertyMap = new SharedPropertyMap(map);
                        propertyMapNewMap.setSharedProtoMap(sharedPropertyMap);
                        scriptObject.setMap(sharedPropertyMap);
                        this.lastMap = new AllocatorMap(scriptObject, map, propertyMapNewMap);
                        return propertyMapNewMap;
                    }
                } else {
                    return this.lastMap.allocatorMap;
                }
            }
            if (this.lastMap.hasValidSharedProtoMap() && this.lastMap.hasSameProtoMap(map)) {
                scriptObject.setMap(this.lastMap.getSharedProtoMap());
                return this.lastMap.allocatorMap;
            }
        }
        PropertyMap propertyMapNewMap2 = PropertyMap.newMap(null, getAllocatorClassName(), 0, this.fieldCount, 0);
        this.lastMap = new AllocatorMap(scriptObject, map, propertyMapNewMap2);
        return propertyMapNewMap2;
    }

    ScriptObject allocate(PropertyMap propertyMap) {
        try {
            if (this.allocator == null) {
                this.allocator = Lookup.f31MH.findStatic(LOOKUP, Context.forStructureClass(getAllocatorClassName()), CompilerConstants.ALLOCATE.symbolName(), Lookup.f31MH.type(ScriptObject.class, new Class[]{PropertyMap.class}));
            }
            return (ScriptObject) this.allocator.invokeExact(propertyMap);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    public String toString() {
        return "AllocationStrategy[fieldCount=" + this.fieldCount + "]";
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/AllocationStrategy$AllocatorMap.class */
    static class AllocatorMap {
        private final WeakReference<ScriptObject> prototype;
        private final WeakReference<PropertyMap> prototypeMap;
        private PropertyMap allocatorMap;

        AllocatorMap(ScriptObject prototype, PropertyMap protoMap, PropertyMap allocMap) {
            this.prototype = new WeakReference<>(prototype);
            this.prototypeMap = new WeakReference<>(protoMap);
            this.allocatorMap = allocMap;
        }

        boolean hasSamePrototype(ScriptObject proto) {
            return this.prototype.get() == proto;
        }

        boolean hasSameProtoMap(PropertyMap protoMap) {
            return this.prototypeMap.get() == protoMap || this.allocatorMap.getSharedProtoMap() == protoMap;
        }

        boolean hasUnchangedProtoMap() {
            ScriptObject proto = this.prototype.get();
            return proto != null && proto.getMap() == this.prototypeMap.get();
        }

        boolean hasSharedProtoMap() {
            return getSharedProtoMap() != null;
        }

        boolean hasValidSharedProtoMap() {
            return hasSharedProtoMap() && getSharedProtoMap().isValidSharedProtoMap();
        }

        PropertyMap getSharedProtoMap() {
            return this.allocatorMap.getSharedProtoMap();
        }
    }
}
