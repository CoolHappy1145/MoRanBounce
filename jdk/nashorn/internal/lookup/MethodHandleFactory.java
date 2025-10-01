package jdk.nashorn.internal.lookup;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.SwitchPoint;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import jdk.nashorn.internal.runtime.ConsString;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Debug;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;
import jdk.nashorn.internal.runtime.options.Options;
import org.apache.log4j.spi.Configurator;

/* loaded from: L-out.jar:jdk/nashorn/internal/lookup/MethodHandleFactory.class */
public final class MethodHandleFactory {
    private static final MethodHandles.Lookup PUBLIC_LOOKUP;
    private static final MethodHandles.Lookup LOOKUP;
    private static final Level TRACE_LEVEL;
    private static final MethodHandleFunctionality FUNC;
    private static final boolean PRINT_STACKTRACE;
    private static final MethodHandle TRACE;
    private static final MethodHandle TRACE_RETURN;
    private static final MethodHandle TRACE_RETURN_VOID;
    private static final String VOID_TAG = "[VOID]";
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !MethodHandleFactory.class.desiredAssertionStatus();
        PUBLIC_LOOKUP = MethodHandles.publicLookup();
        LOOKUP = MethodHandles.lookup();
        TRACE_LEVEL = Level.INFO;
        FUNC = new StandardMethodHandleFunctionality();
        PRINT_STACKTRACE = Options.getBooleanProperty("nashorn.methodhandles.debug.stacktrace");
        TRACE = FUNC.findStatic(LOOKUP, MethodHandleFactory.class, "traceArgs", MethodType.methodType(Void.TYPE, DebugLogger.class, String.class, Integer.TYPE, Object[].class));
        TRACE_RETURN = FUNC.findStatic(LOOKUP, MethodHandleFactory.class, "traceReturn", MethodType.methodType(Object.class, DebugLogger.class, Object.class));
        TRACE_RETURN_VOID = FUNC.findStatic(LOOKUP, MethodHandleFactory.class, "traceReturnVoid", MethodType.methodType((Class<?>) Void.TYPE, (Class<?>) DebugLogger.class));
    }

    private MethodHandleFactory() {
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/lookup/MethodHandleFactory$LookupException.class */
    public static class LookupException extends RuntimeException {
        public LookupException(Exception exc) {
            super(exc);
        }
    }

    public static String stripName(Object obj) {
        if (obj == null) {
            return Configurator.NULL;
        }
        if (obj instanceof Class) {
            return ((Class) obj).getSimpleName();
        }
        return obj.toString();
    }

    public static MethodHandleFunctionality getFunctionality() {
        return FUNC;
    }

    private static void err(String str) {
        Context.getContext().getErr().println(str);
    }

    static Object traceReturn(DebugLogger debugLogger, Object obj) {
        String str;
        StringBuilder sbAppend = new StringBuilder().append("    return");
        if (VOID_TAG.equals(obj)) {
            str = ";";
        } else {
            str = " " + stripName(obj) + "; // [type=" + (obj == null ? "null]" : stripName(obj.getClass()) + ']');
        }
        String string = sbAppend.append(str).toString();
        if (debugLogger == null) {
            err(string);
        } else if (debugLogger.isEnabled()) {
            debugLogger.log(TRACE_LEVEL, string);
        }
        return obj;
    }

    static void traceReturnVoid(DebugLogger debugLogger) {
        traceReturn(debugLogger, VOID_TAG);
    }

    static void traceArgs(DebugLogger debugLogger, String str, int i, Object[] objArr) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        for (int i2 = i; i2 < objArr.length; i2++) {
            if (i2 == i) {
                sb.append(" => args: ");
            }
            sb.append('\'').append(stripName(argString(objArr[i2]))).append('\'').append(' ').append('[').append("type=").append(objArr[i2] == null ? Configurator.NULL : stripName(objArr[i2].getClass())).append(']');
            if (i2 + 1 < objArr.length) {
                sb.append(", ");
            }
        }
        if (debugLogger == null) {
            err(sb.toString());
        } else {
            debugLogger.log(TRACE_LEVEL, new Object[]{sb});
        }
        stacktrace(debugLogger);
    }

    private static void stacktrace(DebugLogger debugLogger) {
        if (!PRINT_STACKTRACE) {
            return;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        new Throwable().printStackTrace(new PrintStream(byteArrayOutputStream));
        String string = byteArrayOutputStream.toString();
        if (debugLogger == null) {
            err(string);
        } else {
            debugLogger.log(TRACE_LEVEL, string);
        }
    }

    private static String argString(Object obj) {
        if (obj == null) {
            return Configurator.NULL;
        }
        if (obj.getClass().isArray()) {
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : (Object[]) obj) {
                arrayList.add('\'' + argString(obj2) + '\'');
            }
            return arrayList.toString();
        }
        if (obj instanceof ScriptObject) {
            return obj.toString() + " (map=" + Debug.m11id(((ScriptObject) obj).getMap()) + ')';
        }
        return obj.toString();
    }

    public static MethodHandle addDebugPrintout(MethodHandle methodHandle, Object obj) {
        return addDebugPrintout(null, Level.OFF, methodHandle, 0, true, obj);
    }

    public static MethodHandle addDebugPrintout(DebugLogger debugLogger, Level level, MethodHandle methodHandle, Object obj) {
        return addDebugPrintout(debugLogger, level, methodHandle, 0, true, obj);
    }

    public static MethodHandle addDebugPrintout(MethodHandle methodHandle, int i, boolean z, Object obj) {
        return addDebugPrintout(null, Level.OFF, methodHandle, i, z, obj);
    }

    public static MethodHandle addDebugPrintout(DebugLogger debugLogger, Level level, MethodHandle methodHandle, int i, boolean z, Object obj) {
        MethodType methodTypeType = methodHandle.type();
        if (debugLogger == null || !debugLogger.isLoggable(level)) {
            return methodHandle;
        }
        if (!$assertionsDisabled && TRACE == null) {
            throw new AssertionError();
        }
        MethodHandle methodHandleFoldArguments = MethodHandles.foldArguments(methodHandle, MethodHandles.insertArguments(TRACE, 0, debugLogger, obj, Integer.valueOf(i)).asCollector(Object[].class, methodTypeType.parameterCount()).asType(methodTypeType.changeReturnType(Void.TYPE)));
        Class<?> clsReturnType = methodTypeType.returnType();
        if (z) {
            if (clsReturnType != Void.TYPE) {
                MethodHandle methodHandleInsertArguments = MethodHandles.insertArguments(TRACE_RETURN, 0, debugLogger);
                methodHandleFoldArguments = MethodHandles.filterReturnValue(methodHandleFoldArguments, methodHandleInsertArguments.asType(methodHandleInsertArguments.type().changeParameterType(0, clsReturnType).changeReturnType(clsReturnType)));
            } else {
                methodHandleFoldArguments = MethodHandles.filterReturnValue(methodHandleFoldArguments, MethodHandles.insertArguments(TRACE_RETURN_VOID, 0, debugLogger));
            }
        }
        return methodHandleFoldArguments;
    }

    @Logger(name = "methodhandles")
    /* loaded from: L-out.jar:jdk/nashorn/internal/lookup/MethodHandleFactory$StandardMethodHandleFunctionality.class */
    private static class StandardMethodHandleFunctionality implements MethodHandleFunctionality, Loggable {
        private DebugLogger log = DebugLogger.DISABLED_LOGGER;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // jdk.nashorn.internal.runtime.logging.Loggable
        public DebugLogger initLogger(Context context) {
            DebugLogger logger = context.getLogger(getClass());
            this.log = logger;
            return logger;
        }

        @Override // jdk.nashorn.internal.runtime.logging.Loggable
        public DebugLogger getLogger() {
            return this.log;
        }

        protected static String describe(Object[] objArr) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < objArr.length; i++) {
                Object obj = objArr[i];
                if (obj == null) {
                    sb.append("<null> ");
                } else if ((obj instanceof String) || (obj instanceof ConsString)) {
                    sb.append(obj.toString());
                    sb.append(' ');
                } else if (obj.getClass().isArray()) {
                    sb.append("[ ");
                    for (Object obj2 : (Object[]) obj) {
                        sb.append(describe(new Object[]{obj2})).append(' ');
                    }
                    sb.append("] ");
                } else {
                    sb.append(obj).append('{').append(Integer.toHexString(System.identityHashCode(obj))).append('}');
                }
                if (i + 1 < objArr.length) {
                    sb.append(", ");
                }
            }
            return sb.toString();
        }

        public MethodHandle debug(MethodHandle methodHandle, String str, Object[] objArr) {
            if (this.log.isEnabled()) {
                if (MethodHandleFactory.PRINT_STACKTRACE) {
                    MethodHandleFactory.stacktrace(this.log);
                }
                return MethodHandleFactory.addDebugPrintout(this.log, Level.INFO, methodHandle, Integer.MAX_VALUE, false, str + ' ' + describe(objArr));
            }
            return methodHandle;
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle filterArguments(MethodHandle methodHandle, int i, MethodHandle[] methodHandleArr) {
            return debug(MethodHandles.filterArguments(methodHandle, i, methodHandleArr), "filterArguments", new Object[]{methodHandle, Integer.valueOf(i), methodHandleArr});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle filterReturnValue(MethodHandle methodHandle, MethodHandle methodHandle2) {
            return debug(MethodHandles.filterReturnValue(methodHandle, methodHandle2), "filterReturnValue", new Object[]{methodHandle, methodHandle2});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle guardWithTest(MethodHandle methodHandle, MethodHandle methodHandle2, MethodHandle methodHandle3) {
            return debug(MethodHandles.guardWithTest(methodHandle, methodHandle2, methodHandle3), "guardWithTest", new Object[]{methodHandle, methodHandle2, methodHandle3});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle insertArguments(MethodHandle methodHandle, int i, Object[] objArr) {
            return debug(MethodHandles.insertArguments(methodHandle, i, objArr), "insertArguments", new Object[]{methodHandle, Integer.valueOf(i), objArr});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle dropArguments(MethodHandle methodHandle, int i, Class[] clsArr) {
            return debug(MethodHandles.dropArguments(methodHandle, i, (Class<?>[]) clsArr), "dropArguments", new Object[]{methodHandle, Integer.valueOf(i), clsArr});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle dropArguments(MethodHandle methodHandle, int i, List list) {
            return debug(MethodHandles.dropArguments(methodHandle, i, (List<Class<?>>) list), "dropArguments", new Object[]{methodHandle, Integer.valueOf(i), list});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle asType(MethodHandle methodHandle, MethodType methodType) {
            return debug(methodHandle.asType(methodType), "asType", new Object[]{methodHandle, methodType});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle bindTo(MethodHandle methodHandle, Object obj) {
            return debug(methodHandle.bindTo(obj), "bindTo", new Object[]{methodHandle, obj});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle foldArguments(MethodHandle methodHandle, MethodHandle methodHandle2) {
            return debug(MethodHandles.foldArguments(methodHandle, methodHandle2), "foldArguments", new Object[]{methodHandle, methodHandle2});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle explicitCastArguments(MethodHandle methodHandle, MethodType methodType) {
            return debug(MethodHandles.explicitCastArguments(methodHandle, methodType), "explicitCastArguments", new Object[]{methodHandle, methodType});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle arrayElementGetter(Class cls) {
            return debug(MethodHandles.arrayElementGetter(cls), "arrayElementGetter", new Object[]{cls});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle arrayElementSetter(Class cls) {
            return debug(MethodHandles.arrayElementSetter(cls), "arrayElementSetter", new Object[]{cls});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle throwException(Class cls, Class cls2) {
            return debug(MethodHandles.throwException(cls, cls2), "throwException", new Object[]{cls, cls2});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle catchException(MethodHandle methodHandle, Class cls, MethodHandle methodHandle2) {
            return debug(MethodHandles.catchException(methodHandle, cls, methodHandle2), "catchException", new Object[]{cls});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle constant(Class cls, Object obj) {
            return debug(MethodHandles.constant(cls, obj), "constant", new Object[]{cls, obj});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle identity(Class cls) {
            return debug(MethodHandles.identity(cls), "identity", new Object[]{cls});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle asCollector(MethodHandle methodHandle, Class cls, int i) {
            return debug(methodHandle.asCollector(cls, i), "asCollector", new Object[]{methodHandle, cls, Integer.valueOf(i)});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle asSpreader(MethodHandle methodHandle, Class cls, int i) {
            return debug(methodHandle.asSpreader(cls, i), "asSpreader", new Object[]{methodHandle, cls, Integer.valueOf(i)});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle getter(MethodHandles.Lookup lookup, Class cls, String str, Class cls2) {
            try {
                return debug(lookup.findGetter(cls, str, cls2), "getter", new Object[]{lookup, cls, str, cls2});
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new LookupException(e);
            }
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle staticGetter(MethodHandles.Lookup lookup, Class cls, String str, Class cls2) {
            try {
                return debug(lookup.findStaticGetter(cls, str, cls2), "static getter", new Object[]{lookup, cls, str, cls2});
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new LookupException(e);
            }
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle setter(MethodHandles.Lookup lookup, Class cls, String str, Class cls2) {
            try {
                return debug(lookup.findSetter(cls, str, cls2), "setter", new Object[]{lookup, cls, str, cls2});
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new LookupException(e);
            }
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle staticSetter(MethodHandles.Lookup lookup, Class cls, String str, Class cls2) {
            try {
                return debug(lookup.findStaticSetter(cls, str, cls2), "static setter", new Object[]{lookup, cls, str, cls2});
            } catch (IllegalAccessException | NoSuchFieldException e) {
                throw new LookupException(e);
            }
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle find(Method method) {
            try {
                return debug(MethodHandleFactory.PUBLIC_LOOKUP.unreflect(method), "find", new Object[]{method});
            } catch (IllegalAccessException e) {
                throw new LookupException(e);
            }
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle findStatic(MethodHandles.Lookup lookup, Class cls, String str, MethodType methodType) {
            try {
                return debug(lookup.findStatic(cls, str, methodType), "findStatic", new Object[]{lookup, cls, str, methodType});
            } catch (IllegalAccessException | NoSuchMethodException e) {
                throw new LookupException(e);
            }
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle findSpecial(MethodHandles.Lookup lookup, Class cls, String str, MethodType methodType, Class cls2) {
            try {
                return debug(lookup.findSpecial(cls, str, methodType, cls2), "findSpecial", new Object[]{lookup, cls, str, methodType});
            } catch (IllegalAccessException | NoSuchMethodException e) {
                throw new LookupException(e);
            }
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle findVirtual(MethodHandles.Lookup lookup, Class cls, String str, MethodType methodType) {
            try {
                return debug(lookup.findVirtual(cls, str, methodType), "findVirtual", new Object[]{lookup, cls, str, methodType});
            } catch (IllegalAccessException | NoSuchMethodException e) {
                throw new LookupException(e);
            }
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public SwitchPoint createSwitchPoint() {
            SwitchPoint switchPoint = new SwitchPoint();
            this.log.log(MethodHandleFactory.TRACE_LEVEL, new Object[]{"createSwitchPoint ", switchPoint});
            return switchPoint;
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodHandle guardWithTest(SwitchPoint switchPoint, MethodHandle methodHandle, MethodHandle methodHandle2) {
            return debug(switchPoint.guardWithTest(methodHandle, methodHandle2), "guardWithTest", new Object[]{switchPoint, methodHandle, methodHandle2});
        }

        @Override // jdk.nashorn.internal.lookup.MethodHandleFunctionality
        public MethodType type(Class cls, Class[] clsArr) {
            MethodType methodType = MethodType.methodType((Class<?>) cls, (Class<?>[]) clsArr);
            this.log.log(MethodHandleFactory.TRACE_LEVEL, new Object[]{"methodType ", cls, " ", Arrays.toString(clsArr), " ", methodType});
            return methodType;
        }
    }
}
