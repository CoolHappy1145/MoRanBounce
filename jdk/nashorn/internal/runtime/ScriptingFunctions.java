package jdk.nashorn.internal.runtime;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import jdk.nashorn.internal.lookup.Lookup;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.objects.NativeArray;

/* loaded from: L-out.jar:jdk/nashorn/internal/runtime/ScriptingFunctions.class */
public final class ScriptingFunctions {
    public static final MethodHandle READLINE;
    public static final MethodHandle READFULLY;
    public static final MethodHandle EXEC;
    public static final String EXEC_NAME = "$EXEC";
    public static final String OUT_NAME = "$OUT";
    public static final String ERR_NAME = "$ERR";
    public static final String EXIT_NAME = "$EXIT";
    public static final String THROW_ON_ERROR_NAME = "throwOnError";
    public static final String ENV_NAME = "$ENV";
    public static final String PWD_NAME = "PWD";
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !ScriptingFunctions.class.desiredAssertionStatus();
        READLINE = findOwnMH("readLine", Object.class, new Class[]{Object.class, Object.class});
        READFULLY = findOwnMH("readFully", Object.class, new Class[]{Object.class, Object.class});
        EXEC = findOwnMH("exec", Object.class, new Class[]{Object.class, Object[].class});
    }

    private ScriptingFunctions() {
    }

    public static Object readLine(Object obj, Object obj2) {
        if (obj2 != ScriptRuntime.UNDEFINED) {
            System.out.print(JSType.toString(obj2));
        }
        return new BufferedReader(new InputStreamReader(System.in)).readLine();
    }

    public static Object readFully(Object obj, Object obj2) {
        File file = null;
        if (obj2 instanceof File) {
            file = (File) obj2;
        } else {
            if ((obj2 instanceof String) || (obj2 instanceof ConsString)) {
                file = new File(((CharSequence) obj2).toString());
            }
        }
        if (file == null || !file.isFile()) {
            throw ECMAErrors.typeError("not.a.file", new String[]{ScriptRuntime.safeToString(obj2)});
        }
        return new String(Source.readFully(file));
    }

    /* JADX WARN: Type inference failed for: r1v11, types: [java.io.OutputStreamWriter, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v13, types: [java.io.OutputStreamWriter, java.lang.Throwable] */
    public static Object exec(Object obj, Object[] objArr) throws InterruptedException, IOException {
        Global global = Context.getGlobal();
        Object obj2 = objArr.length > 0 ? objArr[0] : ScriptRuntime.UNDEFINED;
        Object obj3 = objArr.length > 1 ? objArr[1] : ScriptRuntime.UNDEFINED;
        Object[] objArrCopyOfRange = objArr.length > 2 ? Arrays.copyOfRange(objArr, 2, objArr.length) : ScriptRuntime.EMPTY_ARRAY;
        List list = tokenizeString(JSType.toString(obj2));
        for (Object obj4 : (objArrCopyOfRange.length == 1 && (objArrCopyOfRange[0] instanceof NativeArray)) ? ((NativeArray) objArrCopyOfRange[0]).asObjectArray() : objArrCopyOfRange) {
            list.add(JSType.toString(obj4));
        }
        ProcessBuilder processBuilder = new ProcessBuilder((List<String>) list);
        Object obj5 = global.get(ENV_NAME);
        if (obj5 instanceof ScriptObject) {
            ScriptObject scriptObject = (ScriptObject) obj5;
            Object obj6 = scriptObject.get(PWD_NAME);
            if (obj6 != ScriptRuntime.UNDEFINED) {
                File file = new File(JSType.toString(obj6));
                if (file.exists()) {
                    processBuilder.directory(file);
                }
            }
            Map<String, String> mapEnvironment = processBuilder.environment();
            mapEnvironment.clear();
            for (Map.Entry<Object, Object> entry : scriptObject.entrySet()) {
                mapEnvironment.put(JSType.toString(entry.getKey()), JSType.toString(entry.getValue()));
            }
        }
        Process processStart = processBuilder.start();
        IOException[] iOExceptionArr = new IOException[2];
        StringBuilder sb = new StringBuilder();
        Thread thread = new Thread(new Runnable(processStart, sb, iOExceptionArr) { // from class: jdk.nashorn.internal.runtime.ScriptingFunctions.1
            final Process val$process;
            final StringBuilder val$outBuffer;
            final IOException[] val$exception;

            {
                this.val$process = processStart;
                this.val$outBuffer = sb;
                this.val$exception = iOExceptionArr;
            }

            /* JADX WARN: Finally extract failed */
            /* JADX WARN: Type inference failed for: r1v2, types: [java.io.InputStreamReader, java.lang.Throwable] */
            /* JADX WARN: Type inference failed for: r1v6, types: [java.io.InputStreamReader, java.lang.Throwable] */
            @Override // java.lang.Runnable
            public void run() throws IOException {
                char[] cArr = new char[1024];
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(this.val$process.getInputStream());
                    while (true) {
                        try {
                            int i = inputStreamReader.read(cArr, 0, cArr.length);
                            if (i == -1) {
                                break;
                            } else {
                                this.val$outBuffer.append(cArr, 0, i);
                            }
                        } catch (Throwable th) {
                            if (inputStreamReader != null) {
                                close();
                            }
                            throw th;
                        }
                    }
                    if (inputStreamReader != null) {
                        close();
                    }
                } catch (IOException e) {
                    this.val$exception[0] = e;
                }
            }
        }, "$EXEC output");
        StringBuilder sb2 = new StringBuilder();
        Thread thread2 = new Thread(new Runnable(processStart, sb2, iOExceptionArr) { // from class: jdk.nashorn.internal.runtime.ScriptingFunctions.2
            final Process val$process;
            final StringBuilder val$errBuffer;
            final IOException[] val$exception;

            {
                this.val$process = processStart;
                this.val$errBuffer = sb2;
                this.val$exception = iOExceptionArr;
            }

            /* JADX WARN: Finally extract failed */
            /* JADX WARN: Type inference failed for: r1v2, types: [java.io.InputStreamReader, java.lang.Throwable] */
            /* JADX WARN: Type inference failed for: r1v6, types: [java.io.InputStreamReader, java.lang.Throwable] */
            @Override // java.lang.Runnable
            public void run() throws IOException {
                char[] cArr = new char[1024];
                try {
                    InputStreamReader inputStreamReader = new InputStreamReader(this.val$process.getErrorStream());
                    while (true) {
                        try {
                            int i = inputStreamReader.read(cArr, 0, cArr.length);
                            if (i == -1) {
                                break;
                            } else {
                                this.val$errBuffer.append(cArr, 0, i);
                            }
                        } catch (Throwable th) {
                            if (inputStreamReader != null) {
                                close();
                            }
                            throw th;
                        }
                    }
                    if (inputStreamReader != null) {
                        close();
                    }
                } catch (IOException e) {
                    this.val$exception[1] = e;
                }
            }
        }, "$EXEC error");
        thread.start();
        thread2.start();
        if (!JSType.nullOrUndefined(obj3)) {
            try {
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(processStart.getOutputStream());
                try {
                    String string = JSType.toString(obj3);
                    outputStreamWriter.write(string, 0, string.length());
                    if (outputStreamWriter != null) {
                        close();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            } catch (IOException unused) {
            }
        }
        int iWaitFor = processStart.waitFor();
        thread.join();
        thread2.join();
        String string2 = sb.toString();
        String string3 = sb2.toString();
        global.set(OUT_NAME, string2, 0);
        global.set(ERR_NAME, string3, 0);
        global.set((Object) EXIT_NAME, iWaitFor, 0);
        for (IOException iOException : iOExceptionArr) {
            if (iOException != null) {
                throw iOException;
            }
        }
        if (iWaitFor != 0) {
            Object obj7 = global.get(EXEC_NAME);
            if (!$assertionsDisabled && !(obj7 instanceof ScriptObject)) {
                throw new AssertionError("$EXEC is not a script object!");
            }
            if (JSType.toBoolean(((ScriptObject) obj7).get(THROW_ON_ERROR_NAME))) {
                throw ECMAErrors.rangeError("exec.returned.non.zero", new String[]{ScriptRuntime.safeToString(Integer.valueOf(iWaitFor))});
            }
        }
        return string2;
    }

    private static MethodHandle findOwnMH(String str, Class cls, Class[] clsArr) {
        return Lookup.f31MH.findStatic(MethodHandles.lookup(), ScriptingFunctions.class, str, Lookup.f31MH.type(cls, clsArr));
    }

    public static List tokenizeString(String str) {
        StreamTokenizer streamTokenizer = new StreamTokenizer(new StringReader(str));
        streamTokenizer.resetSyntax();
        streamTokenizer.wordChars(0, 255);
        streamTokenizer.whitespaceChars(0, 32);
        streamTokenizer.commentChar(35);
        streamTokenizer.quoteChar(34);
        streamTokenizer.quoteChar(39);
        ArrayList arrayList = new ArrayList();
        StringBuilder sb = new StringBuilder();
        while (nextToken(streamTokenizer) != -1) {
            String str2 = streamTokenizer.sval;
            if (str2.endsWith("\\")) {
                sb.append(str2.substring(0, str2.length() - 1)).append(' ');
            } else {
                arrayList.add(sb.append(str2).toString());
                sb.setLength(0);
            }
        }
        if (sb.length() != 0) {
            arrayList.add(sb.toString());
        }
        return arrayList;
    }

    private static int nextToken(StreamTokenizer streamTokenizer) {
        try {
            return streamTokenizer.nextToken();
        } catch (IOException unused) {
            return -1;
        }
    }
}
