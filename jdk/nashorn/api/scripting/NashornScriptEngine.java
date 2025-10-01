package jdk.nashorn.api.scripting;

import java.io.IOException;
import java.io.Reader;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.Permissions;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;
import javax.script.AbstractScriptEngine;
import javax.script.Bindings;
import javax.script.Compilable;
import javax.script.CompiledScript;
import javax.script.Invocable;
import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptException;
import javax.script.SimpleBindings;
import jdk.Exported;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.linker.JavaAdapterFactory;
import jdk.nashorn.internal.runtime.options.Options;

@Exported
/* loaded from: L-out.jar:jdk/nashorn/api/scripting/NashornScriptEngine.class */
public final class NashornScriptEngine extends AbstractScriptEngine implements Compilable, Invocable {
    public static final String NASHORN_GLOBAL = "nashorn.global";
    private static final AccessControlContext CREATE_CONTEXT_ACC_CTXT;
    private static final AccessControlContext CREATE_GLOBAL_ACC_CTXT;
    private final ScriptEngineFactory factory;
    private final Context nashornContext;
    private final boolean _global_per_engine;
    private final Global global;
    private static final String MESSAGES_RESOURCE = "jdk.nashorn.api.scripting.resources.Messages";
    private static final ResourceBundle MESSAGES_BUNDLE;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !NashornScriptEngine.class.desiredAssertionStatus();
        CREATE_CONTEXT_ACC_CTXT = createPermAccCtxt(Context.NASHORN_CREATE_CONTEXT);
        CREATE_GLOBAL_ACC_CTXT = createPermAccCtxt(Context.NASHORN_CREATE_GLOBAL);
        MESSAGES_BUNDLE = ResourceBundle.getBundle(MESSAGES_RESOURCE, Locale.getDefault());
    }

    private static AccessControlContext createPermAccCtxt(String str) {
        Permissions permissions = new Permissions();
        permissions.add(new RuntimePermission(str));
        return new AccessControlContext(new ProtectionDomain[]{new ProtectionDomain(null, permissions)});
    }

    private static String getMessage(String str, String[] strArr) {
        try {
            return new MessageFormat(MESSAGES_BUNDLE.getString(str)).format(strArr);
        } catch (MissingResourceException unused) {
            throw new RuntimeException("no message resource found for message id: " + str);
        }
    }

    NashornScriptEngine(NashornScriptEngineFactory nashornScriptEngineFactory, String[] strArr, ClassLoader classLoader, ClassFilter classFilter) {
        if (!$assertionsDisabled && strArr == null) {
            throw new AssertionError("null argument array");
        }
        this.factory = nashornScriptEngineFactory;
        Options options = new Options("nashorn");
        options.process(strArr);
        this.nashornContext = (Context) AccessController.doPrivileged(new PrivilegedAction(this, options, new Context.ThrowErrorManager(), classLoader, classFilter) { // from class: jdk.nashorn.api.scripting.NashornScriptEngine.1
            final Options val$options;
            final ErrorManager val$errMgr;
            final ClassLoader val$appLoader;
            final ClassFilter val$classFilter;
            final NashornScriptEngine this$0;

            {
                this.this$0 = this;
                this.val$options = options;
                this.val$errMgr = errorManager;
                this.val$appLoader = classLoader;
                this.val$classFilter = classFilter;
            }

            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            @Override // java.security.PrivilegedAction
            public Context run() {
                try {
                    return new Context(this.val$options, this.val$errMgr, this.val$appLoader, this.val$classFilter);
                } catch (RuntimeException e) {
                    if (Context.DEBUG) {
                        e.printStackTrace();
                    }
                    throw e;
                }
            }
        }, CREATE_CONTEXT_ACC_CTXT);
        this._global_per_engine = this.nashornContext.getEnv()._global_per_engine;
        this.global = createNashornGlobal();
        this.context.setBindings(new ScriptObjectMirror(this.global, this.global), 100);
    }

    public Object eval(Reader reader, ScriptContext scriptContext) {
        return evalImpl(makeSource(reader, scriptContext), scriptContext);
    }

    public Object eval(String str, ScriptContext scriptContext) {
        return evalImpl(makeSource(str, scriptContext), scriptContext);
    }

    public ScriptEngineFactory getFactory() {
        return this.factory;
    }

    public Bindings createBindings() {
        if (this._global_per_engine) {
            return new SimpleBindings();
        }
        return createGlobalMirror();
    }

    public CompiledScript compile(Reader reader) {
        return asCompiledScript(makeSource(reader, this.context));
    }

    public CompiledScript compile(String str) {
        return asCompiledScript(makeSource(str, this.context));
    }

    public Object invokeFunction(String str, Object[] objArr) {
        return invokeImpl(null, str, objArr);
    }

    public Object invokeMethod(Object obj, String str, Object[] objArr) {
        if (obj == null) {
            throw new IllegalArgumentException(getMessage("thiz.cannot.be.null", new String[0]));
        }
        return invokeImpl(obj, str, objArr);
    }

    public Object getInterface(Class cls) {
        return getInterfaceInner(null, cls);
    }

    public Object getInterface(Object obj, Class cls) {
        if (obj == null) {
            throw new IllegalArgumentException(getMessage("thiz.cannot.be.null", new String[0]));
        }
        return getInterfaceInner(obj, cls);
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.script.ScriptException */
    private static Source makeSource(Reader reader, ScriptContext scriptContext) throws ScriptException {
        try {
            return Source.sourceFor(getScriptName(scriptContext), reader);
        } catch (IOException e) {
            throw new ScriptException(e);
        }
    }

    private static Source makeSource(String str, ScriptContext scriptContext) {
        return Source.sourceFor(getScriptName(scriptContext), str);
    }

    private static String getScriptName(ScriptContext scriptContext) {
        Object attribute = scriptContext.getAttribute("javax.script.filename");
        return attribute != null ? attribute.toString() : "<eval>";
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v15, types: [jdk.nashorn.internal.runtime.ScriptObject] */
    private Object getInterfaceInner(Object obj, Class cls) {
        if (!$assertionsDisabled && (obj instanceof ScriptObject)) {
            throw new AssertionError("raw ScriptObject not expected here");
        }
        if (cls == null || !cls.isInterface()) {
            throw new IllegalArgumentException(getMessage("interface.class.expected", new String[0]));
        }
        if (System.getSecurityManager() != null) {
            if (!Modifier.isPublic(cls.getModifiers())) {
                throw new SecurityException(getMessage("implementing.non.public.interface", new String[]{cls.getName()}));
            }
            Context.checkPackageAccess((Class<?>) cls);
        }
        Global scriptObject = null;
        Global homeGlobal = null;
        if (obj == null) {
            Global nashornGlobalFrom = getNashornGlobalFrom(this.context);
            homeGlobal = nashornGlobalFrom;
            scriptObject = nashornGlobalFrom;
        } else if (obj instanceof ScriptObjectMirror) {
            ScriptObjectMirror scriptObjectMirror = (ScriptObjectMirror) obj;
            scriptObject = scriptObjectMirror.getScriptObject();
            homeGlobal = scriptObjectMirror.getHomeGlobal();
            if (!isOfContext(homeGlobal, this.nashornContext)) {
                throw new IllegalArgumentException(getMessage("script.object.from.another.engine", new String[0]));
            }
        }
        if (scriptObject == null) {
            throw new IllegalArgumentException(getMessage("interface.on.non.script.object", new String[0]));
        }
        try {
            try {
                Global global = Context.getGlobal();
                boolean z = global != homeGlobal;
                if (z) {
                    try {
                        Context.setGlobal(homeGlobal);
                    } finally {
                        if (z) {
                            Context.setGlobal(global);
                        }
                    }
                }
                if (!isInterfaceImplemented(cls, scriptObject)) {
                }
                Object objCast = cls.cast((Object) JavaAdapterFactory.getConstructor(scriptObject.getClass(), cls, MethodHandles.publicLookup()).invoke(scriptObject));
                if (z) {
                    Context.setGlobal(global);
                }
                return objCast;
            } catch (Throwable th) {
                throw new RuntimeException(th);
            }
        } catch (Error | RuntimeException e) {
            throw e;
        }
    }

    private Global getNashornGlobalFrom(ScriptContext scriptContext) {
        Global globalGlobalFromMirror;
        Global globalGlobalFromMirror2;
        if (this._global_per_engine) {
            return this.global;
        }
        Bindings bindings = scriptContext.getBindings(100);
        if ((bindings instanceof ScriptObjectMirror) && (globalGlobalFromMirror2 = globalFromMirror((ScriptObjectMirror) bindings)) != null) {
            return globalGlobalFromMirror2;
        }
        Object obj = bindings.get(NASHORN_GLOBAL);
        if ((obj instanceof ScriptObjectMirror) && (globalGlobalFromMirror = globalFromMirror((ScriptObjectMirror) obj)) != null) {
            return globalGlobalFromMirror;
        }
        ScriptObjectMirror scriptObjectMirrorCreateGlobalMirror = createGlobalMirror();
        bindings.put(NASHORN_GLOBAL, scriptObjectMirrorCreateGlobalMirror);
        scriptObjectMirrorCreateGlobalMirror.getHomeGlobal().setInitScriptContext(scriptContext);
        return scriptObjectMirrorCreateGlobalMirror.getHomeGlobal();
    }

    private Global globalFromMirror(ScriptObjectMirror scriptObjectMirror) {
        ScriptObject scriptObject = scriptObjectMirror.getScriptObject();
        if ((scriptObject instanceof Global) && isOfContext((Global) scriptObject, this.nashornContext)) {
            return (Global) scriptObject;
        }
        return null;
    }

    private ScriptObjectMirror createGlobalMirror() {
        Global globalCreateNashornGlobal = createNashornGlobal();
        return new ScriptObjectMirror(globalCreateNashornGlobal, globalCreateNashornGlobal);
    }

    private Global createNashornGlobal() {
        Global global = (Global) AccessController.doPrivileged(new PrivilegedAction(this) { // from class: jdk.nashorn.api.scripting.NashornScriptEngine.2
            final NashornScriptEngine this$0;

            {
                this.this$0 = this;
            }

            @Override // java.security.PrivilegedAction
            public Object run() {
                return run();
            }

            @Override // java.security.PrivilegedAction
            public Global run() {
                try {
                    return this.this$0.nashornContext.newGlobal();
                } catch (RuntimeException e) {
                    if (Context.DEBUG) {
                        e.printStackTrace();
                    }
                    throw e;
                }
            }
        }, CREATE_GLOBAL_ACC_CTXT);
        this.nashornContext.initGlobal(global, this);
        return global;
    }

    private Object invokeImpl(Object obj, String str, Object[] objArr) throws NoSuchMethodException, ScriptException {
        Objects.requireNonNull(str);
        if (!$assertionsDisabled && (obj instanceof ScriptObject)) {
            throw new AssertionError("raw ScriptObject not expected here");
        }
        Global homeGlobal = null;
        ScriptObjectMirror scriptObjectMirror = null;
        if (obj instanceof ScriptObjectMirror) {
            scriptObjectMirror = (ScriptObjectMirror) obj;
            if (!isOfContext(scriptObjectMirror.getHomeGlobal(), this.nashornContext)) {
                throw new IllegalArgumentException(getMessage("script.object.from.another.engine", new String[0]));
            }
            homeGlobal = scriptObjectMirror.getHomeGlobal();
        } else if (obj == null) {
            Global nashornGlobalFrom = getNashornGlobalFrom(this.context);
            homeGlobal = nashornGlobalFrom;
            scriptObjectMirror = (ScriptObjectMirror) ScriptObjectMirror.wrap(nashornGlobalFrom, nashornGlobalFrom);
        }
        if (scriptObjectMirror != null) {
            try {
                return ScriptObjectMirror.translateUndefined(scriptObjectMirror.callMember(str, objArr));
            } catch (Exception e) {
                Throwable cause = e.getCause();
                if (cause instanceof NoSuchMethodException) {
                    throw ((NoSuchMethodException) cause);
                }
                throwAsScriptException(e, homeGlobal);
                throw new AssertionError("should not reach here");
            }
        }
        throw new IllegalArgumentException(getMessage("interface.on.non.script.object", new String[0]));
    }

    private Object evalImpl(Source source, ScriptContext scriptContext) {
        return evalImpl(compileImpl(source, scriptContext), scriptContext);
    }

    private Object evalImpl(ScriptFunction scriptFunction, ScriptContext scriptContext) {
        return evalImpl(scriptFunction, scriptContext, getNashornGlobalFrom(scriptContext));
    }

    private Object evalImpl(Context.MultiGlobalCompiledScript multiGlobalCompiledScript, ScriptContext scriptContext, Global global) throws ScriptException {
        Global global2 = Context.getGlobal();
        boolean z = global2 != global;
        if (z) {
            try {
                Context.setGlobal(global);
            } catch (Exception e) {
                throwAsScriptException(e, global);
                throw new AssertionError("should not reach here");
            } catch (Throwable th) {
                if (z) {
                    Context.setGlobal(global2);
                }
                throw th;
            }
        }
        ScriptFunction function = multiGlobalCompiledScript.getFunction(global);
        ScriptContext scriptContext2 = global.getScriptContext();
        global.setScriptContext(scriptContext);
        try {
            Object objTranslateUndefined = ScriptObjectMirror.translateUndefined(ScriptObjectMirror.wrap(ScriptRuntime.apply(function, global, new Object[0]), global));
            if (z) {
                Context.setGlobal(global2);
            }
            return objTranslateUndefined;
        } finally {
            global.setScriptContext(scriptContext2);
        }
    }

    private Object evalImpl(ScriptFunction scriptFunction, ScriptContext scriptContext, Global global) throws ScriptException {
        if (scriptFunction == null) {
            return null;
        }
        Global global2 = Context.getGlobal();
        boolean z = global2 != global;
        if (z) {
            try {
                Context.setGlobal(global);
            } catch (Exception e) {
                throwAsScriptException(e, global);
                throw new AssertionError("should not reach here");
            } catch (Throwable th) {
                if (z) {
                    Context.setGlobal(global2);
                }
                throw th;
            }
        }
        ScriptContext scriptContext2 = global.getScriptContext();
        global.setScriptContext(scriptContext);
        try {
            Object objTranslateUndefined = ScriptObjectMirror.translateUndefined(ScriptObjectMirror.wrap(ScriptRuntime.apply(scriptFunction, global, new Object[0]), global));
            if (z) {
                Context.setGlobal(global2);
            }
            return objTranslateUndefined;
        } finally {
            global.setScriptContext(scriptContext2);
        }
    }

    /* JADX INFO: Thrown type has an unknown type hierarchy: javax.script.ScriptException */
    private static void throwAsScriptException(Exception exc, Global global) throws ScriptException {
        if (exc instanceof ScriptException) {
            throw ((ScriptException) exc);
        }
        if (exc instanceof NashornException) {
            NashornException nashornException = (NashornException) exc;
            ScriptException scriptException = new ScriptException(nashornException.getMessage(), nashornException.getFileName(), nashornException.getLineNumber(), nashornException.getColumnNumber());
            nashornException.initEcmaError(global);
            scriptException.initCause(exc);
            throw scriptException;
        }
        if (exc instanceof RuntimeException) {
            throw ((RuntimeException) exc);
        }
        throw new ScriptException(exc);
    }

    private CompiledScript asCompiledScript(Source source) throws ScriptException {
        Global global = Context.getGlobal();
        Global nashornGlobalFrom = getNashornGlobalFrom(this.context);
        boolean z = global != nashornGlobalFrom;
        if (z) {
            try {
                Context.setGlobal(nashornGlobalFrom);
            } catch (Exception e) {
                throwAsScriptException(e, nashornGlobalFrom);
                throw new AssertionError("should not reach here");
            } catch (Throwable th) {
                if (z) {
                    Context.setGlobal(global);
                }
                throw th;
            }
        }
        Context.MultiGlobalCompiledScript multiGlobalCompiledScriptCompileScript = this.nashornContext.compileScript(source);
        ScriptFunction function = multiGlobalCompiledScriptCompileScript.getFunction(nashornGlobalFrom);
        if (z) {
            Context.setGlobal(global);
        }
        return new CompiledScript(this, function, multiGlobalCompiledScriptCompileScript) { // from class: jdk.nashorn.api.scripting.NashornScriptEngine.3
            final ScriptFunction val$func;
            final Context.MultiGlobalCompiledScript val$mgcs;
            final NashornScriptEngine this$0;

            {
                this.this$0 = this;
                this.val$func = function;
                this.val$mgcs = multiGlobalCompiledScriptCompileScript;
            }

            public Object eval(ScriptContext scriptContext) {
                Global nashornGlobalFrom2 = this.this$0.getNashornGlobalFrom(scriptContext);
                return this.val$func.getScope() == nashornGlobalFrom2 ? this.this$0.evalImpl(this.val$func, scriptContext, nashornGlobalFrom2) : this.this$0.evalImpl(this.val$mgcs, scriptContext, nashornGlobalFrom2);
            }

            public ScriptEngine getEngine() {
                return this.this$0;
            }
        };
    }

    private ScriptFunction compileImpl(Source source, ScriptContext scriptContext) {
        return compileImpl(source, getNashornGlobalFrom(scriptContext));
    }

    private ScriptFunction compileImpl(Source source, Global global) throws ScriptException {
        Global global2 = Context.getGlobal();
        boolean z = global2 != global;
        if (z) {
            try {
                Context.setGlobal(global);
            } catch (Exception e) {
                throwAsScriptException(e, global);
                throw new AssertionError("should not reach here");
            } catch (Throwable th) {
                if (z) {
                    Context.setGlobal(global2);
                }
                throw th;
            }
        }
        ScriptFunction scriptFunctionCompileScript = this.nashornContext.compileScript(source, global);
        if (z) {
            Context.setGlobal(global2);
        }
        return scriptFunctionCompileScript;
    }

    private static boolean isInterfaceImplemented(Class cls, ScriptObject scriptObject) throws SecurityException {
        for (Method method : cls.getMethods()) {
            if (method.getDeclaringClass() != Object.class && Modifier.isAbstract(method.getModifiers()) && !(scriptObject.get(method.getName()) instanceof ScriptFunction)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isOfContext(Global global, Context context) {
        return global.isOfContext(context);
    }
}
