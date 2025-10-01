package jdk.nashorn.api.scripting;

import java.util.ArrayList;
import jdk.Exported;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ScriptObject;

@Exported
/* loaded from: L-out.jar:jdk/nashorn/api/scripting/NashornException.class */
public abstract class NashornException extends RuntimeException {
    private static final long serialVersionUID = 1;
    private String fileName;
    private int line;
    private boolean lineAndFileNameUnknown;
    private int column;
    private Object ecmaError;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !NashornException.class.desiredAssertionStatus();
    }

    protected NashornException(String str, String str2, int i, int i2) {
        this(str, null, str2, i, i2);
    }

    protected NashornException(String str, Throwable th, String str2, int i, int i2) {
        super(str, th == null ? null : th);
        this.fileName = str2;
        this.line = i;
        this.column = i2;
    }

    protected NashornException(String str, Throwable th) {
        super(str, th == null ? null : th);
        this.column = -1;
        this.lineAndFileNameUnknown = true;
    }

    public final String getFileName() {
        ensureLineAndFileName();
        return this.fileName;
    }

    public final void setFileName(String str) {
        this.fileName = str;
        this.lineAndFileNameUnknown = false;
    }

    public final int getLineNumber() {
        ensureLineAndFileName();
        return this.line;
    }

    public final void setLineNumber(int i) {
        this.lineAndFileNameUnknown = false;
        this.line = i;
    }

    public final int getColumnNumber() {
        return this.column;
    }

    public final void setColumnNumber(int i) {
        this.column = i;
    }

    public static StackTraceElement[] getScriptFrames(Throwable th) {
        String strStripMethodName;
        StackTraceElement[] stackTrace = th.getStackTrace();
        ArrayList arrayList = new ArrayList();
        for (StackTraceElement stackTraceElement : stackTrace) {
            if (ECMAErrors.isScriptFrame(stackTraceElement)) {
                String str = "<" + stackTraceElement.getFileName() + ">";
                String methodName = stackTraceElement.getMethodName();
                if (methodName.equals(CompilerConstants.PROGRAM.symbolName())) {
                    strStripMethodName = "<program>";
                } else {
                    strStripMethodName = stripMethodName(methodName);
                }
                arrayList.add(new StackTraceElement(str, strStripMethodName, stackTraceElement.getFileName(), stackTraceElement.getLineNumber()));
            }
        }
        return (StackTraceElement[]) arrayList.toArray(new StackTraceElement[arrayList.size()]);
    }

    private static String stripMethodName(String str) {
        String strSubstring = str;
        int iLastIndexOf = strSubstring.lastIndexOf(CompilerConstants.NESTED_FUNCTION_SEPARATOR.symbolName());
        if (iLastIndexOf >= 0) {
            strSubstring = strSubstring.substring(iLastIndexOf + 1);
        }
        int iIndexOf = strSubstring.indexOf(CompilerConstants.ID_FUNCTION_SEPARATOR.symbolName());
        if (iIndexOf >= 0) {
            strSubstring = strSubstring.substring(0, iIndexOf);
        }
        return strSubstring.contains(CompilerConstants.ANON_FUNCTION_PREFIX.symbolName()) ? "<anonymous>" : strSubstring;
    }

    public static String getScriptStackString(Throwable th) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement stackTraceElement : getScriptFrames(th)) {
            sb.append("\tat ");
            sb.append(stackTraceElement.getMethodName());
            sb.append(" (");
            sb.append(stackTraceElement.getFileName());
            sb.append(':');
            sb.append(stackTraceElement.getLineNumber());
            sb.append(")\n");
        }
        int length = sb.length();
        if (length > 0) {
            if (!$assertionsDisabled && sb.charAt(length - 1) != '\n') {
                throw new AssertionError();
            }
            sb.deleteCharAt(length - 1);
        }
        return sb.toString();
    }

    protected NashornException initEcmaError(ScriptObject scriptObject) {
        if (this.ecmaError == null) {
            Object obj = null;
            if (obj instanceof ScriptObject) {
                setEcmaError(ScriptObjectMirror.wrap(null, scriptObject));
            } else {
                setEcmaError(null);
            }
            return this;
        }
        return this;
    }

    public Object getEcmaError() {
        return this.ecmaError;
    }

    public void setEcmaError(Object obj) {
        this.ecmaError = obj;
    }

    private void ensureLineAndFileName() {
        if (this.lineAndFileNameUnknown) {
            for (StackTraceElement stackTraceElement : getStackTrace()) {
                if (ECMAErrors.isScriptFrame(stackTraceElement)) {
                    this.fileName = stackTraceElement.getFileName();
                    this.line = stackTraceElement.getLineNumber();
                    return;
                }
            }
            this.lineAndFileNameUnknown = false;
        }
    }
}
