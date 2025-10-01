package jdk.nashorn.internal.runtime;

import jdk.nashorn.internal.codegen.CompilerConstants;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/ArgumentSetter.class */
public final class ArgumentSetter {
    public static final CompilerConstants.Call SET_ARGUMENT = CompilerConstants.staticCallNoLookup(ArgumentSetter.class, "setArgument", Void.TYPE, new Class[]{Object.class, ScriptObject.class, Integer.TYPE});
    public static final CompilerConstants.Call SET_ARRAY_ELEMENT = CompilerConstants.staticCallNoLookup(ArgumentSetter.class, "setArrayElement", Void.TYPE, new Class[]{Object.class, Object[].class, Integer.TYPE});

    private ArgumentSetter() {
    }

    public static void setArgument(Object obj, ScriptObject scriptObject, int i) {
        scriptObject.setArgument(i, obj);
    }
}
