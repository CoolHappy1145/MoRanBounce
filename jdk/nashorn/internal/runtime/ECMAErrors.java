package jdk.nashorn.internal.runtime;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.scripts.C0278JS;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/ECMAErrors.class */
public final class ECMAErrors {
    private static final String MESSAGES_RESOURCE = "jdk.nashorn.internal.runtime.resources.Messages";
    private static final ResourceBundle MESSAGES_BUNDLE;
    private static final String scriptPackage;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ECMAErrors.class.desiredAssertionStatus();
        MESSAGES_BUNDLE = ResourceBundle.getBundle(MESSAGES_RESOURCE, Locale.getDefault());
        String name = C0278JS.class.getName();
        scriptPackage = name.substring(0, name.lastIndexOf(46));
    }

    private ECMAErrors() {
    }

    private static ECMAException error(Object obj, Throwable th) {
        return new ECMAException(obj, th);
    }

    public static ECMAException asEcmaException(ParserException parserException) {
        return asEcmaException(Context.getGlobal(), parserException);
    }

    public static ECMAException asEcmaException(Global global, ParserException parserException) {
        JSErrorType errorType = parserException.getErrorType();
        if (!$assertionsDisabled && errorType == null) {
            throw new AssertionError("error type for " + parserException + " was null");
        }
        String message = parserException.getMessage();
        switch (C02241.$SwitchMap$jdk$nashorn$internal$runtime$JSErrorType[errorType.ordinal()]) {
            case 1:
                return error(global.newError(message), parserException);
            case 2:
                return error(global.newEvalError(message), parserException);
            case 3:
                return error(global.newRangeError(message), parserException);
            case 4:
                return error(global.newReferenceError(message), parserException);
            case 5:
                return error(global.newSyntaxError(message), parserException);
            case 6:
                return error(global.newTypeError(message), parserException);
            case 7:
                return error(global.newURIError(message), parserException);
            default:
                throw new AssertionError(parserException.getMessage());
        }
    }

    /* renamed from: jdk.nashorn.internal.runtime.ECMAErrors$1 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/runtime/ECMAErrors$1.class */
    static /* synthetic */ class C02241 {
        static final int[] $SwitchMap$jdk$nashorn$internal$runtime$JSErrorType = new int[JSErrorType.values().length];

        static {
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSErrorType[JSErrorType.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSErrorType[JSErrorType.EVAL_ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSErrorType[JSErrorType.RANGE_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSErrorType[JSErrorType.REFERENCE_ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSErrorType[JSErrorType.SYNTAX_ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSErrorType[JSErrorType.TYPE_ERROR.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$runtime$JSErrorType[JSErrorType.URI_ERROR.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public static ECMAException syntaxError(String str, String[] strArr) {
        return syntaxError(Context.getGlobal(), str, strArr);
    }

    public static ECMAException syntaxError(Global global, String str, String[] strArr) {
        return syntaxError(global, null, str, strArr);
    }

    public static ECMAException syntaxError(Throwable th, String str, String[] strArr) {
        return syntaxError(Context.getGlobal(), th, str, strArr);
    }

    public static ECMAException syntaxError(Global global, Throwable th, String str, String[] strArr) {
        return error(global.newSyntaxError(getMessage("syntax.error." + str, strArr)), th);
    }

    public static ECMAException typeError(String str, String[] strArr) {
        return typeError(Context.getGlobal(), str, strArr);
    }

    public static ECMAException typeError(Global global, String str, String[] strArr) {
        return typeError(global, null, str, strArr);
    }

    public static ECMAException typeError(Throwable th, String str, String[] strArr) {
        return typeError(Context.getGlobal(), th, str, strArr);
    }

    public static ECMAException typeError(Global global, Throwable th, String str, String[] strArr) {
        return error(global.newTypeError(getMessage("type.error." + str, strArr)), th);
    }

    public static ECMAException rangeError(String str, String[] strArr) {
        return rangeError(Context.getGlobal(), str, strArr);
    }

    public static ECMAException rangeError(Global global, String str, String[] strArr) {
        return rangeError(global, null, str, strArr);
    }

    public static ECMAException rangeError(Throwable th, String str, String[] strArr) {
        return rangeError(Context.getGlobal(), th, str, strArr);
    }

    public static ECMAException rangeError(Global global, Throwable th, String str, String[] strArr) {
        return error(global.newRangeError(getMessage("range.error." + str, strArr)), th);
    }

    public static ECMAException referenceError(String str, String[] strArr) {
        return referenceError(Context.getGlobal(), str, strArr);
    }

    public static ECMAException referenceError(Global global, String str, String[] strArr) {
        return referenceError(global, null, str, strArr);
    }

    public static ECMAException referenceError(Throwable th, String str, String[] strArr) {
        return referenceError(Context.getGlobal(), th, str, strArr);
    }

    public static ECMAException referenceError(Global global, Throwable th, String str, String[] strArr) {
        return error(global.newReferenceError(getMessage("reference.error." + str, strArr)), th);
    }

    public static ECMAException uriError(String str, String[] strArr) {
        return uriError(Context.getGlobal(), str, strArr);
    }

    public static ECMAException uriError(Global global, String str, String[] strArr) {
        return uriError(global, null, str, strArr);
    }

    public static ECMAException uriError(Throwable th, String str, String[] strArr) {
        return uriError(Context.getGlobal(), th, str, strArr);
    }

    public static ECMAException uriError(Global global, Throwable th, String str, String[] strArr) {
        return error(global.newURIError(getMessage("uri.error." + str, strArr)), th);
    }

    public static String getMessage(String str, String[] strArr) {
        try {
            return new MessageFormat(MESSAGES_BUNDLE.getString(str)).format(strArr);
        } catch (MissingResourceException unused) {
            throw new RuntimeException("no message resource found for message id: " + str);
        }
    }

    public static boolean isScriptFrame(StackTraceElement stackTraceElement) {
        String fileName;
        return (!stackTraceElement.getClassName().startsWith(scriptPackage) || CompilerConstants.isInternalMethodName(stackTraceElement.getMethodName()) || (fileName = stackTraceElement.getFileName()) == null || fileName.endsWith(".java")) ? false : true;
    }
}
