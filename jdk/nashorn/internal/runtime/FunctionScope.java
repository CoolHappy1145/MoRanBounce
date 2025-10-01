package jdk.nashorn.internal.runtime;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/FunctionScope.class */
public class FunctionScope extends Scope {
    public final ScriptObject arguments;

    public FunctionScope(PropertyMap propertyMap, ScriptObject scriptObject, ScriptObject scriptObject2) {
        super(scriptObject, propertyMap);
        this.arguments = scriptObject2;
    }

    public FunctionScope(PropertyMap propertyMap, ScriptObject scriptObject) {
        super(scriptObject, propertyMap);
        this.arguments = null;
    }

    public FunctionScope(PropertyMap propertyMap, long[] jArr, Object[] objArr) {
        super(propertyMap, jArr, objArr);
        this.arguments = null;
    }
}
