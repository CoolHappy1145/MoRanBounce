package jdk.nashorn.api.scripting;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import jdk.Exported;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Version;

@Exported
/* loaded from: L-out.jar:jdk/nashorn/api/scripting/NashornScriptEngineFactory.class */
public final class NashornScriptEngineFactory implements ScriptEngineFactory {
    private static final String[] DEFAULT_OPTIONS = {"-doe"};
    private static final List names = immutableList(new String[]{"nashorn", "Nashorn", "js", "JS", "JavaScript", "javascript", "ECMAScript", "ecmascript"});
    private static final List mimeTypes = immutableList(new String[]{"application/javascript", "application/ecmascript", "text/javascript", "text/ecmascript"});
    private static final List extensions = immutableList(new String[]{"js"});

    public String getEngineName() {
        return (String) getParameter("javax.script.engine");
    }

    public String getEngineVersion() {
        return (String) getParameter("javax.script.engine_version");
    }

    public List getExtensions() {
        return Collections.unmodifiableList(extensions);
    }

    public String getLanguageName() {
        return (String) getParameter("javax.script.language");
    }

    public String getLanguageVersion() {
        return (String) getParameter("javax.script.language_version");
    }

    public String getMethodCallSyntax(String str, String str2, String[] strArr) {
        StringBuilder sbAppend = new StringBuilder().append(str).append('.').append(str2).append('(');
        int length = strArr.length;
        if (length > 0) {
            sbAppend.append(strArr[0]);
        }
        for (int i = 1; i < length; i++) {
            sbAppend.append(',').append(strArr[i]);
        }
        sbAppend.append(')');
        return sbAppend.toString();
    }

    public List getMimeTypes() {
        return Collections.unmodifiableList(mimeTypes);
    }

    public List getNames() {
        return Collections.unmodifiableList(names);
    }

    public String getOutputStatement(String str) {
        return "print(" + str + ")";
    }

    public Object getParameter(String str) {
        switch (str) {
            case "javax.script.name":
                return "javascript";
            case "javax.script.engine":
                return "Oracle Nashorn";
            case "javax.script.engine_version":
                return Version.version();
            case "javax.script.language":
                return "ECMAScript";
            case "javax.script.language_version":
                return "ECMA - 262 Edition 5.1";
            case "THREADING":
                return null;
            default:
                return null;
        }
    }

    public String getProgram(String[] strArr) {
        StringBuilder sb = new StringBuilder();
        for (String str : strArr) {
            sb.append(str).append(';');
        }
        return sb.toString();
    }

    public ScriptEngine getScriptEngine() {
        try {
            return new NashornScriptEngine(this, DEFAULT_OPTIONS, getAppClassLoader(), null);
        } catch (RuntimeException e) {
            if (Context.DEBUG) {
                e.printStackTrace();
            }
            throw e;
        }
    }

    public ScriptEngine getScriptEngine(ClassLoader classLoader) {
        return newEngine(DEFAULT_OPTIONS, classLoader, null);
    }

    public ScriptEngine getScriptEngine(ClassFilter classFilter) {
        return newEngine(DEFAULT_OPTIONS, getAppClassLoader(), (ClassFilter) Objects.requireNonNull(classFilter));
    }

    public ScriptEngine getScriptEngine(String[] strArr) {
        return newEngine((String[]) Objects.requireNonNull(strArr), getAppClassLoader(), null);
    }

    public ScriptEngine getScriptEngine(String[] strArr, ClassLoader classLoader) {
        return newEngine((String[]) Objects.requireNonNull(strArr), classLoader, null);
    }

    public ScriptEngine getScriptEngine(String[] strArr, ClassLoader classLoader, ClassFilter classFilter) {
        return newEngine((String[]) Objects.requireNonNull(strArr), classLoader, (ClassFilter) Objects.requireNonNull(classFilter));
    }

    private ScriptEngine newEngine(String[] strArr, ClassLoader classLoader, ClassFilter classFilter) {
        checkConfigPermission();
        try {
            return new NashornScriptEngine(this, strArr, classLoader, classFilter);
        } catch (RuntimeException e) {
            if (Context.DEBUG) {
                e.printStackTrace();
            }
            throw e;
        }
    }

    private static void checkConfigPermission() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission(Context.NASHORN_SET_CONFIG));
        }
    }

    private static List immutableList(String[] strArr) {
        return Collections.unmodifiableList(Arrays.asList(strArr));
    }

    private static ClassLoader getAppClassLoader() {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        return contextClassLoader == null ? NashornScriptEngineFactory.class.getClassLoader() : contextClassLoader;
    }
}
