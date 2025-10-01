package jdk.nashorn.api.scripting;

import java.lang.invoke.MethodHandle;
import jdk.Exported;
import jdk.internal.dynalink.beans.StaticClass;
import jdk.internal.dynalink.linker.LinkerServices;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.linker.Bootstrap;

@Exported
/* loaded from: L-out.jar:jdk/nashorn/api/scripting/ScriptUtils.class */
public final class ScriptUtils {
    private ScriptUtils() {
    }

    public static String parse(String str, String str2, boolean z) {
        return ScriptRuntime.parse(str, str2, z);
    }

    public static String format(String str, Object[] objArr) {
        return Formatter.format(str, objArr);
    }

    public static Object makeSynchronizedFunction(Object obj, Object obj2) {
        Object objUnwrap = unwrap(obj);
        if (objUnwrap instanceof ScriptFunction) {
            return ((ScriptFunction) objUnwrap).createSynchronized(unwrap(obj2));
        }
        throw new IllegalArgumentException();
    }

    public static ScriptObjectMirror wrap(Object obj) {
        if (obj instanceof ScriptObjectMirror) {
            return (ScriptObjectMirror) obj;
        }
        if (obj instanceof ScriptObject) {
            return (ScriptObjectMirror) ScriptObjectMirror.wrap((ScriptObject) obj, Context.getGlobal());
        }
        throw new IllegalArgumentException();
    }

    public static Object unwrap(Object obj) {
        if (obj instanceof ScriptObjectMirror) {
            return ScriptObjectMirror.unwrap(obj, Context.getGlobal());
        }
        return obj;
    }

    public static Object[] wrapArray(Object[] objArr) {
        if (objArr == null || objArr.length == 0) {
            return objArr;
        }
        return ScriptObjectMirror.wrapArray(objArr, Context.getGlobal());
    }

    public static Object[] unwrapArray(Object[] objArr) {
        if (objArr == null || objArr.length == 0) {
            return objArr;
        }
        return ScriptObjectMirror.unwrapArray(objArr, Context.getGlobal());
    }

    public static Object convert(Object obj, Object obj2) {
        Class representedClass;
        if (obj == null) {
            return null;
        }
        if (obj2 instanceof Class) {
            representedClass = (Class) obj2;
        } else if (obj2 instanceof StaticClass) {
            representedClass = ((StaticClass) obj2).getRepresentedClass();
        } else {
            throw new IllegalArgumentException("type expected");
        }
        LinkerServices linkerServices = Bootstrap.getLinkerServices();
        Object objUnwrap = unwrap(obj);
        MethodHandle typeConverter = linkerServices.getTypeConverter(objUnwrap.getClass(), representedClass);
        if (typeConverter == null) {
            throw new UnsupportedOperationException("conversion not supported");
        }
        try {
            return (Object) typeConverter.invoke(objUnwrap);
        } catch (Error | RuntimeException e) {
            throw e;
        } catch (Throwable th) {
            throw new RuntimeException(th);
        }
    }
}
