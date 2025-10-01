package jdk.nashorn.internal.runtime;

import java.lang.invoke.MethodHandle;
import java.util.concurrent.Callable;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.parser.JSONParser;
import jdk.nashorn.internal.runtime.arrays.ArrayIndex;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/JSONFunctions.class */
public final class JSONFunctions {
    private static final Object REVIVER_INVOKER = new Object();

    private JSONFunctions() {
    }

    private static MethodHandle getREVIVER_INVOKER() {
        return Context.getGlobal().getDynamicInvoker(REVIVER_INVOKER, new Callable() { // from class: jdk.nashorn.internal.runtime.JSONFunctions.1
            @Override // java.util.concurrent.Callable
            public Object call() {
                return call();
            }

            @Override // java.util.concurrent.Callable
            public MethodHandle call() {
                return Bootstrap.createDynamicInvoker("dyn:call", Object.class, new Class[]{Object.class, Object.class, String.class, Object.class});
            }
        });
    }

    public static String quote(String str) {
        return JSONParser.quote(str);
    }

    public static Object parse(Object obj, Object obj2) {
        String string = JSType.toString(obj);
        Global global = Context.getGlobal();
        try {
            return applyReviver(global, new JSONParser(string, global, global.useDualFields()).parse(), obj2);
        } catch (ParserException e) {
            throw ECMAErrors.syntaxError(e, "invalid.json", new String[]{e.getMessage()});
        }
    }

    private static Object applyReviver(Global global, Object obj, Object obj2) {
        if (Bootstrap.isCallable(obj2)) {
            ScriptObject scriptObjectNewObject = global.newObject();
            scriptObjectNewObject.addOwnProperty("", 0, obj);
            return walk(scriptObjectNewObject, "", obj2);
        }
        return obj;
    }

    private static Object walk(ScriptObject scriptObject, Object obj, Object obj2) {
        Object obj3 = scriptObject.get(obj);
        if (obj3 instanceof ScriptObject) {
            ScriptObject scriptObject2 = (ScriptObject) obj3;
            if (scriptObject2.isArray()) {
                int integer = JSType.toInteger(scriptObject2.getLength());
                for (int i = 0; i < integer; i++) {
                    String string = Integer.toString(i);
                    Object objWalk = walk(scriptObject2, string, obj2);
                    if (objWalk == ScriptRuntime.UNDEFINED) {
                        scriptObject2.delete(i, false);
                    } else {
                        setPropertyValue(scriptObject2, string, objWalk);
                    }
                }
            } else {
                for (String str : scriptObject2.getOwnKeys(false)) {
                    Object objWalk2 = walk(scriptObject2, str, obj2);
                    if (objWalk2 == ScriptRuntime.UNDEFINED) {
                        scriptObject2.delete((Object) str, false);
                    } else {
                        setPropertyValue(scriptObject2, str, objWalk2);
                    }
                }
            }
        }
        try {
            return (Object) getREVIVER_INVOKER().invokeExact(obj2, scriptObject, JSType.toString(obj), obj3);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }

    private static void setPropertyValue(ScriptObject scriptObject, String str, Object obj) {
        int arrayIndex = ArrayIndex.getArrayIndex(str);
        if (arrayIndex != -1) {
            scriptObject.defineOwnProperty(arrayIndex, obj);
        } else if (scriptObject.getMap().findProperty(str) != null) {
            scriptObject.set(str, obj, 0);
        } else {
            scriptObject.addOwnProperty(str, 0, obj);
        }
    }
}
