package jdk.nashorn.internal.codegen;

import jdk.nashorn.internal.codegen.types.Type;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/LocalStateRestorationInfo.class */
public class LocalStateRestorationInfo {
    private final Type[] localVariableTypes;
    private final int[] stackLoads;

    LocalStateRestorationInfo(Type[] typeArr, int[] iArr) {
        this.localVariableTypes = typeArr;
        this.stackLoads = iArr;
    }

    public Type[] getLocalVariableTypes() {
        return (Type[]) this.localVariableTypes.clone();
    }

    public int[] getStackLoads() {
        return (int[]) this.stackLoads.clone();
    }
}
