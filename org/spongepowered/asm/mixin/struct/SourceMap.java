package org.spongepowered.asm.mixin.struct;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.ListIterator;
import java.util.Map;
import jdk.internal.dynalink.CallSiteDescriptor;
import jdk.nashorn.internal.codegen.SharedScopeCall;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LineNumberNode;
import org.objectweb.asm.tree.MethodNode;
import org.spongepowered.asm.util.Bytecode;

/* loaded from: L-out.jar:org/spongepowered/asm/mixin/struct/SourceMap.class */
public class SourceMap {
    private static final String DEFAULT_STRATUM = "Mixin";
    private static final String NEWLINE = "\n";
    private final String sourceFile;
    private final Map strata = new LinkedHashMap();
    private int nextLineOffset = 1;
    private String defaultStratum = DEFAULT_STRATUM;

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/struct/SourceMap$File.class */
    public static class File {

        /* renamed from: id */
        public final int f222id;
        public final int lineOffset;
        public final int size;
        public final String sourceFileName;
        public final String sourceFilePath;

        public File(int i, int i2, int i3, String str) {
            this(i, i2, i3, str, null);
        }

        public File(int i, int i2, int i3, String str, String str2) {
            this.f222id = i;
            this.lineOffset = i2;
            this.size = i3;
            this.sourceFileName = str;
            this.sourceFilePath = str2;
        }

        public void applyOffset(ClassNode classNode) {
            Iterator it = classNode.methods.iterator();
            while (it.hasNext()) {
                applyOffset((MethodNode) it.next());
            }
        }

        public void applyOffset(MethodNode methodNode) {
            ListIterator it = methodNode.instructions.iterator();
            while (it.hasNext()) {
                LineNumberNode lineNumberNode = (AbstractInsnNode) it.next();
                if (lineNumberNode instanceof LineNumberNode) {
                    lineNumberNode.line += this.lineOffset - 1;
                }
            }
        }

        void appendFile(StringBuilder sb) {
            if (this.sourceFilePath != null) {
                sb.append("+ ").append(this.f222id).append(" ").append(this.sourceFileName).append(SourceMap.NEWLINE);
                sb.append(this.sourceFilePath).append(SourceMap.NEWLINE);
            } else {
                sb.append(this.f222id).append(" ").append(this.sourceFileName).append(SourceMap.NEWLINE);
            }
        }

        public void appendLines(StringBuilder sb) {
            sb.append("1#").append(this.f222id).append(",").append(this.size).append(CallSiteDescriptor.TOKEN_DELIMITER).append(this.lineOffset).append(SourceMap.NEWLINE);
        }
    }

    /* loaded from: L-out.jar:org/spongepowered/asm/mixin/struct/SourceMap$Stratum.class */
    static class Stratum {
        private static final String STRATUM_MARK = "*S";
        private static final String FILE_MARK = "*F";
        private static final String LINES_MARK = "*L";
        public final String name;
        private final Map files = new LinkedHashMap();

        public Stratum(String str) {
            this.name = str;
        }

        public File addFile(int i, int i2, String str, String str2) {
            File file = (File) this.files.get(str2);
            if (file == null) {
                file = new File(this.files.size() + 1, i, i2, str, str2);
                this.files.put(str2, file);
            }
            return file;
        }

        void appendTo(StringBuilder sb) {
            sb.append(STRATUM_MARK).append(" ").append(this.name).append(SourceMap.NEWLINE);
            sb.append(FILE_MARK).append(SourceMap.NEWLINE);
            Iterator it = this.files.values().iterator();
            while (it.hasNext()) {
                ((File) it.next()).appendFile(sb);
            }
            sb.append(LINES_MARK).append(SourceMap.NEWLINE);
            Iterator it2 = this.files.values().iterator();
            while (it2.hasNext()) {
                ((File) it2.next()).appendLines(sb);
            }
        }
    }

    public SourceMap(String str) {
        this.sourceFile = str;
    }

    public String getSourceFile() {
        return this.sourceFile;
    }

    public String getPseudoGeneratedSourceFile() {
        return this.sourceFile.replace(".java", "$mixin.java");
    }

    public File addFile(ClassNode classNode) {
        return addFile(this.defaultStratum, classNode);
    }

    public File addFile(String str, ClassNode classNode) {
        return addFile(str, classNode.sourceFile, classNode.name + ".java", Bytecode.getMaxLineNumber(classNode, SharedScopeCall.SLOW_SCOPE_CALL_THRESHOLD, 50));
    }

    public File addFile(String str, String str2, int i) {
        return addFile(this.defaultStratum, str, str2, i);
    }

    public File addFile(String str, String str2, String str3, int i) {
        Stratum stratum = (Stratum) this.strata.get(str);
        if (stratum == null) {
            stratum = new Stratum(str);
            this.strata.put(str, stratum);
        }
        File fileAddFile = stratum.addFile(this.nextLineOffset, i, str2, str3);
        this.nextLineOffset += i;
        return fileAddFile;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        appendTo(sb);
        return sb.toString();
    }

    private void appendTo(StringBuilder sb) {
        sb.append("SMAP").append(NEWLINE);
        sb.append(getSourceFile()).append(NEWLINE);
        sb.append(this.defaultStratum).append(NEWLINE);
        Iterator it = this.strata.values().iterator();
        while (it.hasNext()) {
            ((Stratum) it.next()).appendTo(sb);
        }
        sb.append("*E").append(NEWLINE);
    }
}
