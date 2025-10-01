package jdk.nashorn.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import jdk.nashorn.api.scripting.NashornException;
import jdk.nashorn.internal.codegen.Compiler;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.debug.ASTWriter;
import jdk.nashorn.internal.p001ir.debug.PrintVisitor;
import jdk.nashorn.internal.parser.Parser;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.ScriptingFunctions;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.options.Options;

/* loaded from: L-out.jar:jdk/nashorn/tools/Shell.class */
public class Shell {
    private static final String MESSAGE_RESOURCE = "jdk.nashorn.tools.resources.Shell";
    private static final ResourceBundle bundle = ResourceBundle.getBundle(MESSAGE_RESOURCE, Locale.getDefault());
    public static final int SUCCESS = 0;
    public static final int COMMANDLINE_ERROR = 100;
    public static final int COMPILATION_ERROR = 101;
    public static final int RUNTIME_ERROR = 102;
    public static final int IO_ERROR = 103;
    public static final int INTERNAL_ERROR = 104;

    protected Shell() {
    }

    public static void main(String[] strArr) {
        try {
            int iMain = main(System.in, System.out, System.err, strArr);
            if (iMain != 0) {
                System.exit(iMain);
            }
        } catch (IOException e) {
            System.err.println(e);
            System.exit(IO_ERROR);
        }
    }

    public static int main(InputStream inputStream, OutputStream outputStream, OutputStream outputStream2, String[] strArr) {
        return new Shell().run(inputStream, outputStream, outputStream2, strArr);
    }

    protected final int run(InputStream inputStream, OutputStream outputStream, OutputStream outputStream2, String[] strArr) {
        Context contextMakeContext = makeContext(inputStream, outputStream, outputStream2, strArr);
        if (contextMakeContext == null) {
            return 100;
        }
        Global globalCreateGlobal = contextMakeContext.createGlobal();
        ScriptEnvironment env = contextMakeContext.getEnv();
        List files = env.getFiles();
        if (files.isEmpty()) {
            return readEvalPrint(contextMakeContext, globalCreateGlobal);
        }
        if (env._compile_only) {
            return compileScripts(contextMakeContext, globalCreateGlobal, files);
        }
        if (env._fx) {
            return runFXScripts(contextMakeContext, globalCreateGlobal, files);
        }
        return runScripts(contextMakeContext, globalCreateGlobal, files);
    }

    /* JADX WARN: Type inference failed for: r1v14, types: [java.io.FileReader, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v16, types: [java.io.FileReader, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v18, types: [java.io.FileReader, java.lang.Throwable] */
    private static Context makeContext(InputStream inputStream, OutputStream outputStream, OutputStream outputStream2, String[] strArr) {
        PrintStream printStream = outputStream instanceof PrintStream ? (PrintStream) outputStream : new PrintStream(outputStream);
        PrintStream printStream2 = outputStream2 instanceof PrintStream ? (PrintStream) outputStream2 : new PrintStream(outputStream2);
        PrintWriter printWriter = new PrintWriter((OutputStream) printStream, true);
        PrintWriter printWriter2 = new PrintWriter((OutputStream) printStream2, true);
        ErrorManager errorManager = new ErrorManager(printWriter2);
        Options options = new Options("nashorn", printWriter2);
        if (strArr != null) {
            try {
                options.process(preprocessArgs(strArr));
            } catch (IllegalArgumentException e) {
                printWriter2.println(bundle.getString("shell.usage"));
                options.displayHelp(e);
                return null;
            }
        }
        if (!options.getBoolean("scripting")) {
            Iterator it = options.getFiles().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                File file = new File((String) it.next());
                if (file.isFile()) {
                    try {
                        FileReader fileReader = new FileReader(file);
                        try {
                            if (fileReader.read() == 35) {
                                options.set("scripting", true);
                                if (fileReader != null) {
                                    close();
                                }
                            } else if (fileReader != null) {
                                close();
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    } catch (IOException unused) {
                    }
                }
            }
        }
        return new Context(options, errorManager, printWriter, printWriter2, Thread.currentThread().getContextClassLoader());
    }

    /* JADX WARN: Type inference failed for: r1v17, types: [java.io.BufferedReader, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v18, types: [java.io.BufferedReader, java.lang.Throwable] */
    private static String[] preprocessArgs(String[] strArr) throws IOException {
        if (strArr.length == 0) {
            return strArr;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(Arrays.asList(strArr));
        if (strArr[0].startsWith("-") && !System.getProperty("os.name", "generic").startsWith("Mac OS X")) {
            arrayList.addAll(0, ScriptingFunctions.tokenizeString((String) arrayList.remove(0)));
        }
        int i = -1;
        int i2 = 0;
        while (true) {
            if (i2 >= arrayList.size()) {
                break;
            }
            String str = (String) arrayList.get(i2);
            if (str.startsWith("-")) {
                i2++;
            } else {
                String line = "";
                try {
                    BufferedReader bufferedReaderNewBufferedReader = Files.newBufferedReader(Paths.get(str, new String[0]));
                    try {
                        line = bufferedReaderNewBufferedReader.readLine();
                        if (bufferedReaderNewBufferedReader != null) {
                            close();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                } catch (IOException unused) {
                }
                if (line.startsWith("#!")) {
                    i = i2;
                }
            }
        }
        if (i != -1) {
            arrayList.add(i + 1, "--");
        }
        return (String[]) arrayList.toArray(new String[0]);
    }

    private static int compileScripts(Context context, Global global, List list) {
        Global global2 = Context.getGlobal();
        boolean z = global2 != global;
        ScriptEnvironment env = context.getEnv();
        if (z) {
            try {
                Context.setGlobal(global);
            } finally {
                env.getOut().flush();
                env.getErr().flush();
                if (z) {
                    Context.setGlobal(global2);
                }
            }
        }
        ErrorManager errorManager = context.getErrorManager();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            FunctionNode functionNode = new Parser(env, Source.sourceFor(str, new File(str)), errorManager, env._strict, 0, context.getLogger(Parser.class)).parse();
            if (errorManager.getNumberOfErrors() != 0) {
                env.getOut().flush();
                env.getErr().flush();
                if (!z) {
                    return COMPILATION_ERROR;
                }
                Context.setGlobal(global2);
                return COMPILATION_ERROR;
            }
            Compiler.forNoInstallerCompilation(context, functionNode.getSource(), env._strict | functionNode.isStrict()).compile(functionNode, Compiler.CompilationPhases.COMPILE_ALL_NO_INSTALL);
            if (env._print_ast) {
                context.getErr().println(new ASTWriter(functionNode));
            }
            if (env._print_parse) {
                context.getErr().println(new PrintVisitor(functionNode));
            }
            if (errorManager.getNumberOfErrors() != 0) {
            }
        }
        env.getOut().flush();
        env.getErr().flush();
        if (!z) {
            return 0;
        }
        Context.setGlobal(global2);
        return 0;
    }

    private int runScripts(Context context, Global global, List list) {
        Global global2 = Context.getGlobal();
        boolean z = global2 != global;
        if (z) {
            try {
                Context.setGlobal(global);
            } catch (Throwable th) {
                context.getOut().flush();
                context.getErr().flush();
                if (z) {
                    Context.setGlobal(global2);
                }
                throw th;
            }
        }
        ErrorManager errorManager = context.getErrorManager();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            if ("-".equals(str)) {
                int evalPrint = readEvalPrint(context, global);
                if (evalPrint != 0) {
                    context.getOut().flush();
                    context.getErr().flush();
                    if (z) {
                        Context.setGlobal(global2);
                    }
                    return evalPrint;
                }
            } else {
                ScriptFunction scriptFunctionCompileScript = context.compileScript(Source.sourceFor(str, new File(str)), global);
                if (scriptFunctionCompileScript == null || errorManager.getNumberOfErrors() != 0) {
                    context.getOut().flush();
                    context.getErr().flush();
                    if (!z) {
                        return COMPILATION_ERROR;
                    }
                    Context.setGlobal(global2);
                    return COMPILATION_ERROR;
                }
                try {
                    apply(scriptFunctionCompileScript, global);
                } catch (NashornException e) {
                    errorManager.error(e.toString());
                    if (context.getEnv()._dump_on_error) {
                        e.printStackTrace(context.getErr());
                    }
                    context.getOut().flush();
                    context.getErr().flush();
                    if (!z) {
                        return RUNTIME_ERROR;
                    }
                    Context.setGlobal(global2);
                    return RUNTIME_ERROR;
                }
            }
        }
        context.getOut().flush();
        context.getErr().flush();
        if (!z) {
            return 0;
        }
        Context.setGlobal(global2);
        return 0;
    }

    private static int runFXScripts(Context context, Global global, List list) {
        Global global2 = Context.getGlobal();
        boolean z = global2 != global;
        try {
            if (z) {
                try {
                    Context.setGlobal(global);
                } catch (NashornException e) {
                    context.getErrorManager().error(e.toString());
                    if (context.getEnv()._dump_on_error) {
                        e.printStackTrace(context.getErr());
                    }
                    context.getOut().flush();
                    context.getErr().flush();
                    if (!z) {
                        return RUNTIME_ERROR;
                    }
                    Context.setGlobal(global2);
                    return RUNTIME_ERROR;
                }
            }
            global.addOwnProperty("$GLOBAL", 2, global);
            global.addOwnProperty("$SCRIPTS", 2, list);
            context.load(global, "fx:bootstrap.js");
            context.getOut().flush();
            context.getErr().flush();
            if (z) {
                Context.setGlobal(global2);
                return 0;
            }
            return 0;
        } catch (Throwable th) {
            context.getOut().flush();
            context.getErr().flush();
            if (z) {
                Context.setGlobal(global2);
            }
            throw th;
        }
    }

    protected Object apply(ScriptFunction scriptFunction, Object obj) {
        return ScriptRuntime.apply(scriptFunction, obj, new Object[0]);
    }

    private static int readEvalPrint(Context context, Global global) {
        String string = bundle.getString("shell.prompt");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter err = context.getErr();
        Global global2 = Context.getGlobal();
        boolean z = global2 != global;
        ScriptEnvironment env = context.getEnv();
        if (z) {
            try {
                Context.setGlobal(global);
            } finally {
                if (z) {
                    Context.setGlobal(global2);
                }
            }
        }
        global.addShellBuiltins();
        while (true) {
            err.print(string);
            err.flush();
            String line = "";
            try {
                line = bufferedReader.readLine();
            } catch (IOException e) {
                err.println(e.toString());
            }
            if (line == null) {
                break;
            }
            if (!line.isEmpty()) {
                try {
                    Object objEval = context.eval(global, line, global, "<shell>");
                    if (objEval != ScriptRuntime.UNDEFINED) {
                        err.println(JSType.toString(objEval));
                    }
                } catch (Exception e2) {
                    err.println(e2);
                    if (env._dump_on_error) {
                        e2.printStackTrace(err);
                    }
                }
            }
        }
    }
}
