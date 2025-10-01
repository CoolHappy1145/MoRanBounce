package jdk.nashorn.internal.codegen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.logging.DebugLogger;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/DumpBytecode.class */
public final class DumpBytecode {
    /* JADX WARN: Type inference failed for: r1v11, types: [java.io.FileOutputStream, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v18, types: [java.io.FileOutputStream, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v45, types: [java.io.PrintWriter, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v48, types: [java.io.PrintWriter, java.lang.Throwable] */
    public static void dumpBytecode(ScriptEnvironment scriptEnvironment, DebugLogger debugLogger, byte[] bArr, String str) throws IOException {
        File file;
        File file2 = null;
        try {
            if (scriptEnvironment._print_code) {
                StringBuilder sb = new StringBuilder();
                sb.append("class: " + str).append('\n').append(ClassEmitter.disassemble(bArr)).append("=====");
                if (scriptEnvironment._print_code_dir != null) {
                    String strSubstring = str;
                    int iLastIndexOf = strSubstring.lastIndexOf(36);
                    if (iLastIndexOf != -1) {
                        strSubstring = strSubstring.substring(iLastIndexOf + 1);
                    }
                    file2 = new File(scriptEnvironment._print_code_dir);
                    if (!file2.exists() && !file2.mkdirs()) {
                        throw new IOException(file2.toString());
                    }
                    int i = 0;
                    do {
                        file = new File(scriptEnvironment._print_code_dir, strSubstring + (i == 0 ? "" : "_" + i) + ".bytecode");
                        i++;
                    } while (file.exists());
                    PrintWriter printWriter = new PrintWriter(new FileOutputStream(file));
                    try {
                        printWriter.print(sb.toString());
                        printWriter.flush();
                        if (printWriter != null) {
                            close();
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                } else {
                    scriptEnvironment.getErr().println(sb);
                }
            }
            if (scriptEnvironment._dest_dir != null) {
                String str2 = str.replace('.', File.separatorChar) + ".class";
                int iLastIndexOf2 = str2.lastIndexOf(File.separatorChar);
                if (iLastIndexOf2 != -1) {
                    file2 = new File(scriptEnvironment._dest_dir, str2.substring(0, iLastIndexOf2));
                } else {
                    file2 = new File(scriptEnvironment._dest_dir);
                }
                if (!file2.exists() && !file2.mkdirs()) {
                    throw new IOException(file2.toString());
                }
                File file3 = new File(scriptEnvironment._dest_dir, str2);
                FileOutputStream fileOutputStream = new FileOutputStream(file3);
                try {
                    fileOutputStream.write(bArr);
                    if (fileOutputStream != null) {
                        close();
                    }
                    debugLogger.info("Wrote class to '" + file3.getAbsolutePath() + '\'');
                } catch (Throwable th2) {
                    if (fileOutputStream != null) {
                        close();
                    }
                    throw th2;
                }
            }
        } catch (IOException unused) {
            debugLogger.warning(new Object[]{"Skipping class dump for ", str, ": ", ECMAErrors.getMessage("io.error.cant.write", new String[]{file2.toString()})});
        }
    }
}
