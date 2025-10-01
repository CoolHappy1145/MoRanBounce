package jdk.nashorn.internal.codegen;

import java.util.List;
import jdk.nashorn.internal.codegen.CodeGenerator;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.runtime.PropertyMap;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/ObjectCreator.class */
public abstract class ObjectCreator implements CodeGenerator.SplitLiteralCreator {
    final List tuples;
    final CodeGenerator codegen;
    protected PropertyMap propertyMap;
    private final boolean isScope;
    private final boolean hasArguments;

    protected abstract void createObject(MethodEmitter methodEmitter);

    protected abstract PropertyMap makeMap();

    protected abstract Class getAllocatorClass();

    protected abstract void loadValue(Object obj, Type type);

    ObjectCreator(CodeGenerator codeGenerator, List list, boolean z, boolean z2) {
        this.codegen = codeGenerator;
        this.tuples = list;
        this.isScope = z;
        this.hasArguments = z2;
    }

    public void makeObject(MethodEmitter methodEmitter) {
        createObject(methodEmitter);
        int usedSlotsWithLiveTemporaries = methodEmitter.getUsedSlotsWithLiveTemporaries();
        Type typePeekType = methodEmitter.peekType();
        methodEmitter.storeTemp(typePeekType, usedSlotsWithLiveTemporaries);
        populateRange(methodEmitter, typePeekType, usedSlotsWithLiveTemporaries, 0, this.tuples.size());
    }

    protected MapCreator newMapCreator(Class cls) {
        return new MapCreator(cls, this.tuples);
    }

    protected void loadScope(MethodEmitter methodEmitter) {
        methodEmitter.loadCompilerConstant(CompilerConstants.SCOPE);
    }

    protected MethodEmitter loadMap(MethodEmitter methodEmitter) {
        this.codegen.loadConstant(this.propertyMap);
        return methodEmitter;
    }

    PropertyMap getMap() {
        return this.propertyMap;
    }

    protected boolean isScope() {
        return this.isScope;
    }

    protected boolean hasArguments() {
        return this.hasArguments;
    }

    MethodEmitter loadTuple(MethodEmitter methodEmitter, MapTuple mapTuple, boolean z) {
        loadValue(mapTuple.value, mapTuple.type);
        if (!this.codegen.useDualFields() || !mapTuple.isPrimitive()) {
            methodEmitter.convert(Type.OBJECT);
        } else if (z) {
            methodEmitter.pack();
        }
        return methodEmitter;
    }

    MethodEmitter loadIndex(MethodEmitter methodEmitter, long j) {
        return (((long) ((int) j)) > j ? 1 : (((long) ((int) j)) == j ? 0 : -1)) == 0 ? methodEmitter.load((int) j) : methodEmitter.load(j);
    }
}
