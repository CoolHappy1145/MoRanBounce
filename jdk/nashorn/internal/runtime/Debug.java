package jdk.nashorn.internal.runtime;

import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenStream;
import jdk.nashorn.internal.parser.TokenType;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/Debug.class */
public final class Debug {
    private Debug() {
    }

    public static String firstJSFrame(Throwable th) {
        for (StackTraceElement stackTraceElement : th.getStackTrace()) {
            if (ECMAErrors.isScriptFrame(stackTraceElement)) {
                return stackTraceElement.toString();
            }
        }
        return "<native code>";
    }

    public static String firstJSFrame() {
        return firstJSFrame(new Throwable());
    }

    public static String scriptStack() {
        return NashornException.getScriptStackString(new Throwable());
    }

    /* renamed from: id */
    public static String m11id(Object obj) {
        return String.format("0x%08x", Integer.valueOf(System.identityHashCode(obj)));
    }

    public static int intId(Object obj) {
        return System.identityHashCode(obj);
    }

    public static String stackTraceElementAt(int i) {
        return new Throwable().getStackTrace()[i + 1].toString();
    }

    public static String caller(int i, int i2, String[] strArr) {
        String str = "";
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int i3 = i2;
        for (int i4 = i + 1; i4 < stackTrace.length && i3 != 0; i4++) {
            StackTraceElement stackTraceElement = stackTrace[i4];
            String methodName = stackTraceElement.getMethodName();
            int length = strArr.length;
            int i5 = 0;
            while (true) {
                if (i5 < length) {
                    if (methodName.compareTo(strArr[i5]) == 0) {
                        break;
                    }
                    i5++;
                } else {
                    str = str + (methodName + CallSiteDescriptor.TOKEN_DELIMITER + stackTraceElement.getLineNumber() + "                              ").substring(0, 30);
                    i3--;
                    break;
                }
            }
        }
        return str.isEmpty() ? "<no caller>" : str;
    }

    public static void dumpTokens(Source source, Lexer lexer, TokenStream tokenStream) {
        int i = 0;
        while (true) {
            if (i > tokenStream.last()) {
                lexer.lexify();
            } else {
                long j = tokenStream.get(i);
                TokenType tokenTypeDescType = Token.descType(j);
                System.out.println("" + i + ": " + Token.toString(source, j, true));
                i++;
                if (tokenTypeDescType == TokenType.EOF) {
                    return;
                }
            }
        }
    }
}
