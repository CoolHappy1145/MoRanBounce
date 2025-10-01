package jdk.nashorn.internal.codegen;

import java.util.List;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.Property;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import jdk.nashorn.internal.scripts.C0276JD;
import jdk.nashorn.internal.scripts.C0277JO;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/SpillObjectCreator.class */
public final class SpillObjectCreator extends ObjectCreator {
    static final boolean $assertionsDisabled;

    @Override // jdk.nashorn.internal.codegen.ObjectCreator
    protected void loadValue(Object obj, Type type) {
        loadValue((Expression) obj, type);
    }

    static {
        $assertionsDisabled = !SpillObjectCreator.class.desiredAssertionStatus();
    }

    SpillObjectCreator(CodeGenerator codeGenerator, List list) {
        super(codeGenerator, list, false, false);
        makeMap();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.codegen.ObjectCreator
    public void createObject(MethodEmitter methodEmitter) {
        Object objObjectAsConstant;
        if (!$assertionsDisabled && isScope()) {
            throw new AssertionError("spill scope objects are not currently supported");
        }
        int size = this.tuples.size();
        boolean zUseDualFields = this.codegen.useDualFields();
        int iSpillAllocationLength = ScriptObject.spillAllocationLength(size);
        long[] jArr = zUseDualFields ? new long[iSpillAllocationLength] : null;
        Object[] objArr = new Object[iSpillAllocationLength];
        Class<?> allocatorClass = getAllocatorClass();
        ArrayData arrayDataAllocate = ArrayData.allocate(ScriptRuntime.EMPTY_ARRAY);
        int i = 0;
        for (MapTuple mapTuple : this.tuples) {
            String str = mapTuple.key;
            Expression expression = (Expression) mapTuple.value;
            methodEmitter.invalidateSpecialName(mapTuple.key);
            if (expression != null && (objObjectAsConstant = LiteralNode.objectAsConstant(expression)) != LiteralNode.POSTSET_MARKER) {
                Property propertyFindProperty = this.propertyMap.findProperty(str);
                if (propertyFindProperty != null) {
                    propertyFindProperty.setType(zUseDualFields ? JSType.unboxedFieldType(objObjectAsConstant) : Object.class);
                    int slot = propertyFindProperty.getSlot();
                    if (zUseDualFields && (objObjectAsConstant instanceof Number)) {
                        jArr[slot] = ObjectClassGenerator.pack((Number) objObjectAsConstant);
                    } else {
                        objArr[slot] = objObjectAsConstant;
                    }
                } else {
                    long length = arrayDataAllocate.length();
                    int arrayIndex = ArrayIndex.getArrayIndex(str);
                    long longIndex = ArrayIndex.toLongIndex(arrayIndex);
                    if (!$assertionsDisabled) {
                        if (!(arrayIndex != -1)) {
                            throw new AssertionError();
                        }
                    }
                    if (longIndex >= length) {
                        arrayDataAllocate = arrayDataAllocate.ensure(longIndex);
                    }
                    if (objObjectAsConstant instanceof Integer) {
                        arrayDataAllocate = arrayDataAllocate.set(arrayIndex, ((Integer) objObjectAsConstant).intValue(), false);
                    } else if (objObjectAsConstant instanceof Double) {
                        arrayDataAllocate = arrayDataAllocate.set(arrayIndex, ((Double) objObjectAsConstant).doubleValue(), false);
                    } else {
                        arrayDataAllocate = arrayDataAllocate.set(arrayIndex, objObjectAsConstant, false);
                    }
                    if (longIndex > length) {
                        arrayDataAllocate = arrayDataAllocate.delete(length, longIndex - 1);
                    }
                }
            }
            i++;
        }
        methodEmitter._new(allocatorClass).dup();
        this.codegen.loadConstant(this.propertyMap);
        if (zUseDualFields) {
            this.codegen.loadConstant(jArr);
        } else {
            methodEmitter.loadNull();
        }
        this.codegen.loadConstant(objArr);
        methodEmitter.invoke(CompilerConstants.constructorNoLookup(allocatorClass, new Class[]{PropertyMap.class, long[].class, Object[].class}));
        if (arrayDataAllocate.length() > 0) {
            methodEmitter.dup();
            this.codegen.loadConstant(arrayDataAllocate);
            methodEmitter.invoke(CompilerConstants.virtualCallNoLookup(ScriptObject.class, "setArray", Void.TYPE, new Class[]{ArrayData.class}));
        }
    }

    @Override // jdk.nashorn.internal.codegen.CodeGenerator.SplitLiteralCreator
    public void populateRange(MethodEmitter methodEmitter, Type type, int i, int i2, int i3) {
        int callSiteFlags = this.codegen.getCallSiteFlags();
        methodEmitter.load(type, i);
        for (int i4 = i2; i4 < i3; i4++) {
            MapTuple mapTuple = (MapTuple) this.tuples.get(i4);
            if (!LiteralNode.isConstant(mapTuple.value)) {
                Property propertyFindProperty = this.propertyMap.findProperty(mapTuple.key);
                if (propertyFindProperty == null) {
                    int arrayIndex = ArrayIndex.getArrayIndex(mapTuple.key);
                    if (!$assertionsDisabled) {
                        if (!(arrayIndex != -1)) {
                            throw new AssertionError();
                        }
                    }
                    methodEmitter.dup();
                    loadIndex(methodEmitter, ArrayIndex.toLongIndex(arrayIndex));
                    loadTuple(methodEmitter, mapTuple, false);
                    methodEmitter.dynamicSetIndex(callSiteFlags);
                } else {
                    methodEmitter.dup();
                    loadTuple(methodEmitter, mapTuple, false);
                    methodEmitter.dynamicSet(propertyFindProperty.getKey(), this.codegen.getCallSiteFlags(), false);
                }
            }
        }
    }

    @Override // jdk.nashorn.internal.codegen.ObjectCreator
    protected PropertyMap makeMap() {
        if (!$assertionsDisabled && this.propertyMap != null) {
            throw new AssertionError("property map already initialized");
        }
        this.propertyMap = new MapCreator(getAllocatorClass(), this.tuples).makeSpillMap(false, this.codegen.useDualFields());
        return this.propertyMap;
    }

    protected void loadValue(Expression expression, Type type) {
        this.codegen.loadExpressionAsType(expression, Type.generic(type));
    }

    @Override // jdk.nashorn.internal.codegen.ObjectCreator
    protected Class getAllocatorClass() {
        return this.codegen.useDualFields() ? C0276JD.class : C0277JO.class;
    }
}
