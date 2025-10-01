package jdk.nashorn.internal.codegen;

import java.lang.invoke.MethodType;
import java.util.Arrays;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.runtime.ScriptFunction;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/TypeMap.class */
public final class TypeMap {
    private final int functionNodeId;
    private final Type[] paramTypes;
    private final Type returnType;
    private final boolean needsCallee;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !TypeMap.class.desiredAssertionStatus();
    }

    public TypeMap(int i, MethodType methodType, boolean z) {
        Type[] typeArr = new Type[methodType.parameterCount()];
        int i2 = 0;
        for (Class<?> cls : methodType.parameterArray()) {
            int i3 = i2;
            i2++;
            typeArr[i3] = Type.typeFor(cls);
        }
        this.functionNodeId = i;
        this.paramTypes = typeArr;
        this.returnType = Type.typeFor(methodType.returnType());
        this.needsCallee = z;
    }

    public Type[] getParameterTypes(int i) {
        if ($assertionsDisabled || this.functionNodeId == i) {
            return (Type[]) this.paramTypes.clone();
        }
        throw new AssertionError();
    }

    MethodType getCallSiteType(FunctionNode functionNode) {
        if (!$assertionsDisabled && this.functionNodeId != functionNode.getId()) {
            throw new AssertionError();
        }
        Type[] typeArr = this.paramTypes;
        MethodType methodType = MethodType.methodType(this.returnType.getTypeClass());
        if (this.needsCallee) {
            methodType = methodType.appendParameterTypes(ScriptFunction.class);
        }
        MethodType methodTypeAppendParameterTypes = methodType.appendParameterTypes(Object.class);
        for (Type type : typeArr) {
            if (type == null) {
                return null;
            }
            methodTypeAppendParameterTypes = methodTypeAppendParameterTypes.appendParameterTypes(type.getTypeClass());
        }
        return methodTypeAppendParameterTypes;
    }

    public boolean needsCallee() {
        return this.needsCallee;
    }

    Type get(FunctionNode functionNode, int i) {
        if (!$assertionsDisabled && this.functionNodeId != functionNode.getId()) {
            throw new AssertionError();
        }
        Type[] typeArr = this.paramTypes;
        if (!$assertionsDisabled && typeArr != null && i >= typeArr.length) {
            throw new AssertionError("fn = " + functionNode.getId() + " types=" + Arrays.toString(typeArr) + " || pos=" + i + " >= length=" + typeArr.length + " in " + this);
        }
        if (typeArr != null && i < typeArr.length) {
            return typeArr[i];
        }
        return null;
    }

    public String toString() {
        return toString("");
    }

    String toString(String str) {
        StringBuilder sb = new StringBuilder();
        int i = this.functionNodeId;
        sb.append(str).append('\t');
        sb.append("function ").append(i).append('\n');
        sb.append(str).append("\t\tparamTypes=");
        sb.append(Arrays.toString(this.paramTypes));
        sb.append('\n');
        sb.append(str).append("\t\treturnType=");
        Type type = this.returnType;
        sb.append(type == null ? "N/A" : type);
        sb.append('\n');
        return sb.toString();
    }
}
