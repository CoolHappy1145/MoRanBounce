package org.spongepowered.asm.mixin.transformer.ext.extensions;

import com.google.common.base.Charsets;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.io.Files;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.launch.MixinLaunchPlugin;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.mixin.transformer.ext.IExtension;
import org.spongepowered.asm.mixin.transformer.ext.ITargetClassContext;
import org.spongepowered.asm.util.Constants;
import org.spongepowered.asm.util.PrettyPrinter;
import org.spongepowered.asm.util.SignaturePrinter;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/transformer/ext/extensions/ExtensionCheckInterfaces.class */
public class ExtensionCheckInterfaces implements IExtension {
    private static final String AUDIT_DIR = "audit";
    private static final String IMPL_REPORT_FILENAME = "mixin_implementation_report";
    private static final String IMPL_REPORT_CSV_FILENAME = "mixin_implementation_report.csv";
    private static final String IMPL_REPORT_TXT_FILENAME = "mixin_implementation_report.txt";
    private static final Logger logger = LogManager.getLogger(MixinLaunchPlugin.NAME);
    private final File csv;
    private final File report;
    private final Multimap interfaceMethods = HashMultimap.create();
    private boolean strict;

    public ExtensionCheckInterfaces() {
        File file = new File(Constants.DEBUG_OUTPUT_DIR, AUDIT_DIR);
        file.mkdirs();
        this.csv = new File(file, IMPL_REPORT_CSV_FILENAME);
        this.report = new File(file, IMPL_REPORT_TXT_FILENAME);
        this.csv.getParentFile().mkdirs();
        try {
            Files.write("Class,Method,Signature,Interface\n", this.csv, Charsets.ISO_8859_1);
        } catch (IOException unused) {
        }
        try {
            Files.write("Mixin Implementation Report generated on " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\n", this.report, Charsets.ISO_8859_1);
        } catch (IOException unused2) {
        }
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IExtension
    public boolean checkActive(MixinEnvironment mixinEnvironment) {
        this.strict = mixinEnvironment.getOption(MixinEnvironment.Option.CHECK_IMPLEMENTS_STRICT);
        return mixinEnvironment.getOption(MixinEnvironment.Option.CHECK_IMPLEMENTS);
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IExtension
    public void preApply(ITargetClassContext iTargetClassContext) {
        ClassInfo classInfo = iTargetClassContext.getClassInfo();
        Iterator it = classInfo.getInterfaceMethods(false).iterator();
        while (it.hasNext()) {
            this.interfaceMethods.put(classInfo, (ClassInfo.Method) it.next());
        }
    }

    @Override // org.spongepowered.asm.mixin.transformer.ext.IExtension
    public void postApply(ITargetClassContext iTargetClassContext) throws IOException {
        ClassInfo classInfo = iTargetClassContext.getClassInfo();
        if (classInfo.isAbstract() && !this.strict) {
            logger.info("{} is skipping abstract target {}", new Object[]{getClass().getSimpleName(), iTargetClassContext});
            return;
        }
        String strReplace = classInfo.getName().replace('/', '.');
        int i = 0;
        PrettyPrinter prettyPrinter = new PrettyPrinter();
        prettyPrinter.add("Class: %s", new Object[]{strReplace}).m76hr();
        prettyPrinter.add("%-32s %-47s  %s", new Object[]{"Return Type", "Missing Method", "From Interface"}).m76hr();
        Set<ClassInfo.Method> interfaceMethods = classInfo.getInterfaceMethods(true);
        HashSet hashSet = new HashSet(classInfo.getSuperClass().getInterfaceMethods(true));
        hashSet.addAll(this.interfaceMethods.removeAll(classInfo));
        for (ClassInfo.Method method : interfaceMethods) {
            ClassInfo.Method methodFindMethodInHierarchy = classInfo.findMethodInHierarchy(method.getName(), method.getDesc(), ClassInfo.SearchType.ALL_CLASSES, ClassInfo.Traversal.ALL);
            if (methodFindMethodInHierarchy == null || methodFindMethodInHierarchy.isAbstract()) {
                if (!hashSet.contains(method)) {
                    if (i > 0) {
                        prettyPrinter.add();
                    }
                    SignaturePrinter modifiers = new SignaturePrinter(method.getName(), method.getDesc()).setModifiers("");
                    String strReplace2 = method.getOwner().getName().replace('/', '.');
                    i++;
                    prettyPrinter.add("%-32s%s", new Object[]{modifiers.getReturnType(), modifiers});
                    prettyPrinter.add("%-80s  %s", new Object[]{"", strReplace2});
                    appendToCSVReport(strReplace, method, strReplace2);
                }
            }
        }
        if (i > 0) {
            prettyPrinter.m76hr().add("%82s%s: %d", new Object[]{"", "Total unimplemented", Integer.valueOf(i)});
            prettyPrinter.print(System.err);
            appendToTextReport(prettyPrinter);
        }
    }

    private void appendToCSVReport(String str, ClassInfo.Method method, String str2) {
        try {
            Files.append(String.format("%s,%s,%s,%s\n", str, method.getName(), method.getDesc(), str2), this.csv, Charsets.ISO_8859_1);
        } catch (IOException unused) {
        }
    }

    private void appendToTextReport(PrettyPrinter prettyPrinter) throws IOException {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(this.report, true);
            PrintStream printStream = new PrintStream(fileOutputStream);
            printStream.print("\n");
            prettyPrinter.print(printStream);
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    logger.catching(e);
                }
            }
        } catch (Exception unused) {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e2) {
                    logger.catching(e2);
                }
            }
        } catch (Throwable th) {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e3) {
                    logger.catching(e3);
                }
            }
            throw th;
        }
    }
}
