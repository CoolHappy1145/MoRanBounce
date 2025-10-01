package jdk.nashorn.internal.runtime;

import javax.script.ScriptException;
import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.internal.codegen.CompilerConstants;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/ECMAException.class */
public final class ECMAException extends NashornException {
    public static final CompilerConstants.Call CREATE = CompilerConstants.staticCallNoLookup(ECMAException.class, "create", ECMAException.class, new Class[]{Object.class, String.class, Integer.TYPE, Integer.TYPE});
    public static final CompilerConstants.FieldAccess THROWN = CompilerConstants.virtualField(ECMAException.class, "thrown", Object.class);
    private static final String EXCEPTION_PROPERTY = "nashornException";
    public final Object thrown;

    private ECMAException(Object obj, String str, int i, int i2) {
        super(ScriptRuntime.safeToString(obj), obj instanceof Throwable ? (Throwable) obj : null, str, i, i2);
        this.thrown = obj;
        setExceptionToThrown();
    }

    public ECMAException(Object obj, Throwable th) {
        super(ScriptRuntime.safeToString(obj), th);
        this.thrown = obj;
        setExceptionToThrown();
    }

    public static ECMAException create(Object obj, String str, int i, int i2) {
        if (obj instanceof ScriptObject) {
            Object exception = getException((ScriptObject) obj);
            if (exception instanceof ECMAException) {
                ECMAException eCMAException = (ECMAException) exception;
                if (eCMAException.getThrown() == obj) {
                    eCMAException.setFileName(str);
                    eCMAException.setLineNumber(i);
                    eCMAException.setColumnNumber(i2);
                    return eCMAException;
                }
            }
        }
        return new ECMAException(obj, str, i, i2);
    }

    public Object getThrown() {
        return this.thrown;
    }

    @Override // java.lang.Throwable
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String fileName = getFileName();
        int lineNumber = getLineNumber();
        int columnNumber = getColumnNumber();
        if (fileName != null) {
            sb.append(fileName);
            if (lineNumber >= 0) {
                sb.append(':');
                sb.append(lineNumber);
            }
            if (columnNumber >= 0) {
                sb.append(':');
                sb.append(columnNumber);
            }
            sb.append(' ');
        } else {
            sb.append("ECMAScript Exception: ");
        }
        sb.append(getMessage());
        return sb.toString();
    }

    public static Object getException(ScriptObject scriptObject) {
        if (scriptObject.hasOwnProperty(EXCEPTION_PROPERTY)) {
            return scriptObject.get(EXCEPTION_PROPERTY);
        }
        return null;
    }

    public static Object printStackTrace(ScriptObject scriptObject) {
        Object exception = getException(scriptObject);
        if (exception instanceof Throwable) {
            ((Throwable) exception).printStackTrace(Context.getCurrentErr());
        } else {
            Context.err("<stack trace not available>");
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static Object getLineNumber(ScriptObject scriptObject) {
        Object exception = getException(scriptObject);
        if (exception instanceof NashornException) {
            return Integer.valueOf(((NashornException) exception).getLineNumber());
        }
        if (exception instanceof ScriptException) {
            return Integer.valueOf(((ScriptException) exception).getLineNumber());
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static Object getColumnNumber(ScriptObject scriptObject) {
        Object exception = getException(scriptObject);
        if (exception instanceof NashornException) {
            return Integer.valueOf(((NashornException) exception).getColumnNumber());
        }
        if (exception instanceof ScriptException) {
            return Integer.valueOf(((ScriptException) exception).getColumnNumber());
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static Object getFileName(ScriptObject scriptObject) {
        Object exception = getException(scriptObject);
        if (exception instanceof NashornException) {
            return ((NashornException) exception).getFileName();
        }
        if (exception instanceof ScriptException) {
            return ((ScriptException) exception).getFileName();
        }
        return ScriptRuntime.UNDEFINED;
    }

    public static String safeToString(ScriptObject scriptObject) {
        String strSafeToString;
        String strSafeToString2;
        Object obj = ScriptRuntime.UNDEFINED;
        try {
            obj = scriptObject.get("name");
        } catch (Exception unused) {
        }
        if (obj == ScriptRuntime.UNDEFINED) {
            strSafeToString = "Error";
        } else {
            strSafeToString = ScriptRuntime.safeToString(obj);
        }
        Object obj2 = ScriptRuntime.UNDEFINED;
        try {
            obj2 = scriptObject.get("message");
        } catch (Exception unused2) {
        }
        if (obj2 == ScriptRuntime.UNDEFINED) {
            strSafeToString2 = "";
        } else {
            strSafeToString2 = ScriptRuntime.safeToString(obj2);
        }
        if (strSafeToString.isEmpty()) {
            return strSafeToString2;
        }
        if (strSafeToString2.isEmpty()) {
            return strSafeToString;
        }
        return ((Object) strSafeToString) + ": " + ((Object) strSafeToString2);
    }

    private void setExceptionToThrown() {
        if (this.thrown instanceof ScriptObject) {
            ScriptObject scriptObject = (ScriptObject) this.thrown;
            if (!scriptObject.has(EXCEPTION_PROPERTY)) {
                scriptObject.addOwnProperty(EXCEPTION_PROPERTY, 2, this);
            } else {
                scriptObject.set(EXCEPTION_PROPERTY, this, 0);
            }
        }
    }
}
