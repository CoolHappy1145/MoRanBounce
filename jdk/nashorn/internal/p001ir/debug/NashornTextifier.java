package jdk.nashorn.internal.p001ir.debug;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jdk.internal.org.objectweb.asm.Attribute;
import jdk.internal.org.objectweb.asm.Handle;
import jdk.internal.org.objectweb.asm.Label;
import jdk.internal.org.objectweb.asm.Type;
import jdk.internal.org.objectweb.asm.signature.SignatureReader;
import jdk.internal.org.objectweb.asm.util.Printer;
import jdk.internal.org.objectweb.asm.util.TraceSignatureVisitor;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.linker.NashornCallSiteDescriptor;
import kotlin.jvm.internal.CharCompanionObject;
import kotlin.text.Typography;
import org.apache.log4j.net.SyslogAppender;
import org.spongepowered.asm.mixin.transformer.ActivityStack;
import org.spongepowered.asm.util.Constants;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/debug/NashornTextifier.class */
public final class NashornTextifier extends Printer {
    private String currentClassName;
    private Iterator labelIter;
    private Graph graph;
    private String currentBlock;
    private boolean lastWasNop;
    private boolean lastWasEllipse;
    private static final int INTERNAL_NAME = 0;
    private static final int FIELD_DESCRIPTOR = 1;
    private static final int FIELD_SIGNATURE = 2;
    private static final int METHOD_DESCRIPTOR = 3;
    private static final int METHOD_SIGNATURE = 4;
    private static final int CLASS_SIGNATURE = 5;
    private final String tab = "  ";
    private final String tab2 = "    ";
    private final String tab3 = "      ";
    private Map labelNames;
    private boolean localVarsStarted;

    /* renamed from: cr */
    private NashornClassReader f28cr;
    private ScriptEnvironment env;
    static final boolean $assertionsDisabled;

    /* renamed from: visitMethod, reason: collision with other method in class */
    public Printer m234visitMethod(int i, String str, String str2, String str3, String[] strArr) {
        return visitMethod(i, str, str2, str3, strArr);
    }

    /* renamed from: visitField, reason: collision with other method in class */
    public Printer m235visitField(int i, String str, String str2, String str3, Object obj) {
        return visitField(i, str, str2, str3, obj);
    }

    static {
        $assertionsDisabled = !NashornTextifier.class.desiredAssertionStatus();
    }

    public NashornTextifier(ScriptEnvironment scriptEnvironment, NashornClassReader nashornClassReader) {
        this(327680);
        this.env = scriptEnvironment;
        this.f28cr = nashornClassReader;
    }

    private NashornTextifier(ScriptEnvironment scriptEnvironment, NashornClassReader nashornClassReader, Iterator it, Graph graph) {
        this(scriptEnvironment, nashornClassReader);
        this.labelIter = it;
        this.graph = graph;
    }

    protected NashornTextifier(int i) {
        super(i);
        this.lastWasNop = false;
        this.lastWasEllipse = false;
        this.tab = "  ";
        this.tab2 = "    ";
        this.tab3 = "      ";
        this.localVarsStarted = false;
    }

    public void visit(int i, int i2, String str, String str2, String str3, String[] strArr) {
        int i3 = i & CharCompanionObject.MAX_VALUE;
        this.currentClassName = str;
        StringBuilder sb = new StringBuilder();
        sb.append("// class version ").append(i3).append('.').append(i >>> 16).append(" (").append(i).append(")\n");
        if ((i2 & 131072) != 0) {
            sb.append("// DEPRECATED\n");
        }
        sb.append("// access flags 0x").append(Integer.toHexString(i2).toUpperCase()).append('\n');
        appendDescriptor(sb, 5, str2);
        if (str2 != null) {
            TraceSignatureVisitor traceSignatureVisitor = new TraceSignatureVisitor(i2);
            new SignatureReader(str2).accept(traceSignatureVisitor);
            sb.append("// declaration: ").append(str).append(traceSignatureVisitor.getDeclaration()).append('\n');
        }
        appendAccess(sb, i2 & (-33));
        if ((i2 & 8192) != 0) {
            sb.append("@interface ");
        } else if ((i2 & 512) != 0) {
            sb.append("interface ");
        } else if ((i2 & 16384) == 0) {
            sb.append("class ");
        }
        appendDescriptor(sb, 0, str);
        if (str3 != null && !Constants.OBJECT.equals(str3)) {
            sb.append(" extends ");
            appendDescriptor(sb, 0, str3);
            sb.append(' ');
        }
        if (strArr != null && strArr.length > 0) {
            sb.append(" implements ");
            for (String str4 : strArr) {
                appendDescriptor(sb, 0, str4);
                sb.append(' ');
            }
        }
        sb.append(" {\n");
        addText(sb);
    }

    public void visitSource(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        if (str != null) {
            sb.append("  ").append("// compiled from: ").append(str).append('\n');
        }
        if (str2 != null) {
            sb.append("  ").append("// debug info: ").append(str2).append('\n');
        }
        if (sb.length() > 0) {
            addText(sb);
        }
    }

    public void visitOuterClass(String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        sb.append("  ").append("outer class ");
        appendDescriptor(sb, 0, str);
        sb.append(' ');
        if (str2 != null) {
            sb.append(str2).append(' ');
        }
        appendDescriptor(sb, 3, str3);
        sb.append('\n');
        addText(sb);
    }

    public NashornTextifier visitField(int i, String str, String str2, String str3, Object obj) {
        StringBuilder sb = new StringBuilder();
        if ((i & 131072) != 0) {
            sb.append("  ").append("// DEPRECATED\n");
        }
        if (str3 != null) {
            sb.append("  ");
            appendDescriptor(sb, 2, str3);
            TraceSignatureVisitor traceSignatureVisitor = new TraceSignatureVisitor(0);
            new SignatureReader(str3).acceptType(traceSignatureVisitor);
            sb.append("  ").append("// declaration: ").append(traceSignatureVisitor.getDeclaration()).append('\n');
        }
        sb.append("  ");
        appendAccess(sb, i);
        appendDescriptor(sb, 1, str2.endsWith(";") ? str2.substring(0, str2.length() - 1) : str2);
        sb.append(' ').append(str);
        if (obj != null) {
            sb.append(" = ");
            if (obj instanceof String) {
                sb.append('\"').append(obj).append('\"');
            } else {
                sb.append(obj);
            }
        }
        sb.append(";\n");
        addText(sb);
        NashornTextifier nashornTextifierCreateNashornTextifier = createNashornTextifier();
        addText(nashornTextifierCreateNashornTextifier.getText());
        return nashornTextifierCreateNashornTextifier;
    }

    public NashornTextifier visitMethod(int i, String str, String str2, String str3, String[] strArr) {
        this.graph = new Graph(str);
        List extraLabels = this.f28cr.getExtraLabels(this.currentClassName, str, str2);
        this.labelIter = extraLabels == null ? null : extraLabels.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append('\n');
        if ((i & 131072) != 0) {
            sb.append("  ").append("// DEPRECATED\n");
        }
        sb.append("  ").append("// access flags 0x").append(Integer.toHexString(i).toUpperCase()).append('\n');
        if (str3 != null) {
            sb.append("  ");
            appendDescriptor(sb, 4, str3);
            TraceSignatureVisitor traceSignatureVisitor = new TraceSignatureVisitor(0);
            new SignatureReader(str3).accept(traceSignatureVisitor);
            String declaration = traceSignatureVisitor.getDeclaration();
            String returnType = traceSignatureVisitor.getReturnType();
            String exceptions = traceSignatureVisitor.getExceptions();
            sb.append("  ").append("// declaration: ").append(returnType).append(' ').append(str).append(declaration);
            if (exceptions != null) {
                sb.append(" throws ").append(exceptions);
            }
            sb.append('\n');
        }
        sb.append("  ");
        appendAccess(sb, i);
        if ((i & 256) != 0) {
            sb.append("native ");
        }
        if ((i & 128) != 0) {
            sb.append("varargs ");
        }
        if ((i & 64) != 0) {
            sb.append("bridge ");
        }
        sb.append(str);
        appendDescriptor(sb, 3, str2);
        if (strArr != null && strArr.length > 0) {
            sb.append(" throws ");
            for (String str4 : strArr) {
                appendDescriptor(sb, 0, str4);
                sb.append(' ');
            }
        }
        sb.append('\n');
        addText(sb);
        NashornTextifier nashornTextifierCreateNashornTextifier = createNashornTextifier();
        addText(nashornTextifierCreateNashornTextifier.getText());
        return nashornTextifierCreateNashornTextifier;
    }

    public void visitClassEnd() {
        addText("}\n");
    }

    public void visitParameter(String str, int i) {
        StringBuilder sb = new StringBuilder();
        sb.append("    ").append("// parameter ");
        appendAccess(sb, i);
        sb.append(' ').append(str == null ? "<no name>" : str).append('\n');
        addText(sb);
    }

    public void visitFrame(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        StringBuilder sb = new StringBuilder();
        sb.append("frame ");
        switch (i) {
            case -1:
            case 0:
                sb.append("full [");
                appendFrameTypes(sb, i2, objArr);
                sb.append("] [");
                appendFrameTypes(sb, i3, objArr2);
                sb.append(']');
                break;
            case 1:
                sb.append("append [");
                appendFrameTypes(sb, i2, objArr);
                sb.append(']');
                break;
            case 2:
                sb.append("chop ").append(i2);
                break;
            case 3:
                sb.append("same");
                break;
            case 4:
                sb.append("same1 ");
                appendFrameTypes(sb, 1, objArr2);
                break;
            default:
                if (!$assertionsDisabled) {
                    throw new AssertionError();
                }
                break;
        }
        sb.append('\n');
        sb.append('\n');
        addText(sb);
    }

    private StringBuilder appendOpcode(StringBuilder sb, int i) {
        Label nextLabel = getNextLabel();
        if (nextLabel instanceof NashornLabel) {
            int offset = nextLabel.getOffset();
            if (offset != -1) {
                String str = "" + offset;
                for (int i2 = 0; i2 < 5 - str.length(); i2++) {
                    sb.append(' ');
                }
                sb.append(str);
                sb.append(' ');
            } else {
                sb.append("       ");
            }
        }
        return sb.append("    ").append(OPCODES[i].toLowerCase());
    }

    private Label getNextLabel() {
        if (this.labelIter == null) {
            return null;
        }
        return (Label) this.labelIter.next();
    }

    public void visitInsn(int i) {
        if (i == 0) {
            if (this.lastWasEllipse) {
                getNextLabel();
                return;
            } else {
                if (this.lastWasNop) {
                    getNextLabel();
                    addText("          ...\n");
                    this.lastWasEllipse = true;
                    return;
                }
                this.lastWasNop = true;
            }
        } else {
            this.lastWasEllipse = false;
            this.lastWasNop = false;
        }
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, i).append('\n');
        addText(sb);
        checkNoFallThru(i, null);
    }

    public void visitIntInsn(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, i).append(' ').append(i == 188 ? TYPES[i2] : Integer.toString(i2)).append('\n');
        addText(sb);
    }

    public void visitVarInsn(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, i).append(' ').append(i2).append('\n');
        addText(sb);
    }

    public void visitTypeInsn(int i, String str) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, i).append(' ');
        appendDescriptor(sb, 0, str);
        sb.append('\n');
        addText(sb);
    }

    public void visitFieldInsn(int i, String str, String str2, String str3) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, i).append(' ');
        appendDescriptor(sb, 0, str);
        sb.append('.').append(str2).append(" : ");
        appendDescriptor(sb, 1, str3);
        sb.append('\n');
        addText(sb);
    }

    public void visitMethodInsn(int i, String str, String str2, String str3, boolean z) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, i).append(' ');
        appendDescriptor(sb, 0, str);
        sb.append('.').append(str2);
        appendDescriptor(sb, 3, str3);
        sb.append('\n');
        addText(sb);
    }

    public void visitInvokeDynamicInsn(String str, String str2, Handle handle, Object[] objArr) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, 186).append(' ');
        sb.append(str);
        appendDescriptor(sb, 3, str2);
        int length = sb.length();
        for (int i = 0; i < 80 - length; i++) {
            sb.append(' ');
        }
        sb.append(" [");
        appendHandle(sb, handle);
        if (objArr.length == 0) {
            sb.append("none");
        } else {
            for (Object obj : objArr) {
                if (obj instanceof String) {
                    appendStr(sb, (String) obj);
                } else if (obj instanceof Type) {
                    sb.append(((Type) obj).getDescriptor()).append(".class");
                } else if (obj instanceof Handle) {
                    appendHandle(sb, (Handle) obj);
                } else if (obj instanceof Integer) {
                    int iIntValue = ((Integer) obj).intValue();
                    int i2 = iIntValue >> 11;
                    if (i2 != 0) {
                        sb.append(" pp=").append(i2);
                    }
                    sb.append(NashornCallSiteDescriptor.toString(iIntValue & NashornCallSiteDescriptor.FLAGS_MASK));
                } else {
                    sb.append(obj);
                }
                sb.append(", ");
            }
            sb.setLength(sb.length() - 2);
        }
        sb.append("]\n");
        addText(sb);
    }

    private void checkNoFallThru(int i, String str) {
        boolean z;
        switch (i) {
            case Typography.section /* 167 */:
            case 172:
            case 173:
            case Typography.registered /* 174 */:
            case 175:
            case 176:
            case 191:
                z = true;
                break;
            case SyslogAppender.LOG_LOCAL5 /* 168 */:
            case Typography.copyright /* 169 */:
            case 170:
            case Typography.leftGuillemete /* 171 */:
            case Typography.plusMinus /* 177 */:
            case 178:
            case 179:
            case 180:
            case 181:
            case Typography.paragraph /* 182 */:
            case Typography.middleDot /* 183 */:
            case SyslogAppender.LOG_LOCAL7 /* 184 */:
            case 185:
            case 186:
            case Typography.rightGuillemete /* 187 */:
            case 188:
            case Typography.half /* 189 */:
            case 190:
            default:
                z = false;
                break;
        }
        if (z) {
            this.graph.setNoFallThru(this.currentBlock);
        }
        if (this.currentBlock != null && str != null) {
            this.graph.addEdge(this.currentBlock, str);
        }
    }

    public void visitJumpInsn(int i, Label label) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, i).append(' ');
        String strAppendLabel = appendLabel(sb, label);
        sb.append('\n');
        addText(sb);
        checkNoFallThru(i, strAppendLabel);
    }

    private void addText(Object obj) {
        this.text.add(obj);
        if (this.currentBlock != null) {
            this.graph.addText(this.currentBlock, obj.toString());
        }
    }

    public void visitLabel(Label label) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        String strAppendLabel = appendLabel(sb, label);
        sb.append(" [bci=");
        sb.append(label.info);
        sb.append("]");
        sb.append("\n");
        this.graph.addNode(strAppendLabel);
        if (this.currentBlock != null && !this.graph.isNoFallThru(this.currentBlock)) {
            this.graph.addEdge(this.currentBlock, strAppendLabel);
        }
        this.currentBlock = strAppendLabel;
        addText(sb);
    }

    public void visitLdcInsn(Object obj) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, 18).append(' ');
        if (obj instanceof String) {
            appendStr(sb, (String) obj);
        } else if (obj instanceof Type) {
            sb.append(((Type) obj).getDescriptor()).append(".class");
        } else {
            sb.append(obj);
        }
        sb.append('\n');
        addText(sb);
    }

    public void visitIincInsn(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, 132).append(' ');
        sb.append(i).append(' ').append(i2).append('\n');
        addText(sb);
    }

    public void visitTableSwitchInsn(int i, int i2, Label label, Label[] labelArr) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, 170).append(' ');
        for (int i3 = 0; i3 < labelArr.length; i3++) {
            sb.append("      ").append(i + i3).append(": ");
            this.graph.addEdge(this.currentBlock, appendLabel(sb, labelArr[i3]));
            sb.append('\n');
        }
        sb.append("      ").append("default: ");
        appendLabel(sb, label);
        sb.append('\n');
        addText(sb);
    }

    public void visitLookupSwitchInsn(Label label, int[] iArr, Label[] labelArr) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, Typography.leftGuillemete).append(' ');
        for (int i = 0; i < labelArr.length; i++) {
            sb.append("      ").append(iArr[i]).append(": ");
            this.graph.addEdge(this.currentBlock, appendLabel(sb, labelArr[i]));
            sb.append('\n');
        }
        sb.append("      ").append("default: ");
        this.graph.addEdge(this.currentBlock, appendLabel(sb, label));
        sb.append('\n');
        addText(sb.toString());
    }

    public void visitMultiANewArrayInsn(String str, int i) {
        StringBuilder sb = new StringBuilder();
        appendOpcode(sb, 197).append(' ');
        appendDescriptor(sb, 1, str);
        sb.append(' ').append(i).append('\n');
        addText(sb);
    }

    public void visitTryCatchBlock(Label label, Label label2, Label label3, String str) {
        StringBuilder sb = new StringBuilder();
        sb.append("    ").append("try ");
        String strAppendLabel = appendLabel(sb, label);
        sb.append(' ');
        appendLabel(sb, label2);
        sb.append(' ');
        String strAppendLabel2 = appendLabel(sb, label3);
        sb.append(' ');
        appendDescriptor(sb, 0, str);
        sb.append('\n');
        addText(sb);
        this.graph.setIsCatch(strAppendLabel2, str);
        this.graph.addTryCatch(strAppendLabel, strAppendLabel2);
    }

    public void visitLocalVariable(String str, String str2, String str3, Label label, Label label2, int i) {
        StringBuilder sb = new StringBuilder();
        if (!this.localVarsStarted) {
            this.text.add("\n");
            this.localVarsStarted = true;
            this.graph.addNode("vars");
            this.currentBlock = "vars";
        }
        sb.append("    ").append("local ").append(str).append(' ');
        int length = sb.length();
        for (int i2 = 0; i2 < 25 - length; i2++) {
            sb.append(' ');
        }
        String strAppendLabel = appendLabel(sb, label);
        for (int i3 = 0; i3 < 5 - strAppendLabel.length(); i3++) {
            sb.append(' ');
        }
        String strAppendLabel2 = appendLabel(sb, label2);
        for (int i4 = 0; i4 < 5 - strAppendLabel2.length(); i4++) {
            sb.append(' ');
        }
        sb.append(i).append("    ");
        appendDescriptor(sb, 1, str2);
        sb.append('\n');
        if (str3 != null) {
            sb.append("    ");
            appendDescriptor(sb, 2, str3);
            TraceSignatureVisitor traceSignatureVisitor = new TraceSignatureVisitor(0);
            new SignatureReader(str3).acceptType(traceSignatureVisitor);
            sb.append("    ").append("// declaration: ").append(traceSignatureVisitor.getDeclaration()).append('\n');
        }
        addText(sb.toString());
    }

    public void visitLineNumber(int i, Label label) {
        addText("<line " + i + ">\n");
    }

    public void visitMaxs(int i, int i2) {
        StringBuilder sb = new StringBuilder();
        sb.append('\n');
        sb.append("    ").append("max stack  = ").append(i);
        sb.append(", max locals = ").append(i2).append('\n');
        addText(sb.toString());
    }

    /* JADX WARN: Code restructure failed: missing block: B:35:0x00c8, code lost:
    
        r11 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00d3, code lost:
    
        throw new java.lang.RuntimeException(r11);
     */
    /* JADX WARN: Type inference failed for: r1v14, types: [java.io.PrintWriter, java.lang.Throwable] */
    /* JADX WARN: Type inference failed for: r1v16, types: [java.io.PrintWriter, java.lang.Throwable] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void printToDir(Graph graph) {
        File file;
        if (this.env._print_code_dir != null) {
            File file2 = new File(this.env._print_code_dir);
            if (!file2.exists() && !file2.mkdirs()) {
                throw new RuntimeException(file2.toString());
            }
            int i = 0;
            do {
                file = new File(file2, graph.getName() + (i == 0 ? "" : "_" + i) + ".dot");
                i++;
            } while (file.exists());
            PrintWriter printWriter = new PrintWriter(new FileOutputStream(file));
            try {
                printWriter.println(graph);
                if (printWriter != null) {
                    close();
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void visitMethodEnd() {
        if ((this.env._print_code_func == null || this.env._print_code_func.equals(this.graph.getName())) && this.env._print_code_dir != null) {
            printToDir(this.graph);
        }
    }

    protected NashornTextifier createNashornTextifier() {
        return new NashornTextifier(this.env, this.f28cr, this.labelIter, this.graph);
    }

    private static void appendDescriptor(StringBuilder sb, int i, String str) {
        if (str != null) {
            if (i == 5 || i == 2 || i == 4) {
                sb.append("// signature ").append(str).append('\n');
            } else {
                appendShortDescriptor(sb, str);
            }
        }
    }

    private String appendLabel(StringBuilder sb, Label label) {
        if (this.labelNames == null) {
            this.labelNames = new HashMap();
        }
        String str = (String) this.labelNames.get(label);
        if (str == null) {
            str = "L" + this.labelNames.size();
            this.labelNames.put(label, str);
        }
        sb.append(str);
        return str;
    }

    private static void appendHandle(StringBuilder sb, Handle handle) {
        switch (handle.getTag()) {
            case 1:
                sb.append("getfield");
                break;
            case 2:
                sb.append("getstatic");
                break;
            case 3:
                sb.append("putfield");
                break;
            case 4:
                sb.append("putstatic");
                break;
            case 5:
                sb.append("virtual");
                break;
            case 6:
                sb.append("static");
                break;
            case 7:
                sb.append("special");
                break;
            case 8:
                sb.append("new_special");
                break;
            case 9:
                sb.append("interface");
                break;
            default:
                if (!$assertionsDisabled) {
                    throw new AssertionError();
                }
                break;
        }
        sb.append(" '");
        sb.append(handle.getName());
        sb.append("'");
    }

    private static void appendAccess(StringBuilder sb, int i) {
        if ((i & 1) != 0) {
            sb.append("public ");
        }
        if ((i & 2) != 0) {
            sb.append("private ");
        }
        if ((i & 4) != 0) {
            sb.append("protected ");
        }
        if ((i & 16) != 0) {
            sb.append("final ");
        }
        if ((i & 8) != 0) {
            sb.append("static ");
        }
        if ((i & 32) != 0) {
            sb.append("synchronized ");
        }
        if ((i & 64) != 0) {
            sb.append("volatile ");
        }
        if ((i & 128) != 0) {
            sb.append("transient ");
        }
        if ((i & 1024) != 0) {
            sb.append("abstract ");
        }
        if ((i & 2048) != 0) {
            sb.append("strictfp ");
        }
        if ((i & 4096) != 0) {
            sb.append("synthetic ");
        }
        if ((i & 32768) != 0) {
            sb.append("mandated ");
        }
        if ((i & 16384) != 0) {
            sb.append("enum ");
        }
    }

    private void appendFrameTypes(StringBuilder sb, int i, Object[] objArr) {
        for (int i2 = 0; i2 < i; i2++) {
            if (i2 > 0) {
                sb.append(' ');
            }
            if (objArr[i2] instanceof String) {
                String str = (String) objArr[i2];
                if (str.startsWith("[")) {
                    appendDescriptor(sb, 1, str);
                } else {
                    appendDescriptor(sb, 0, str);
                }
            } else if (objArr[i2] instanceof Integer) {
                switch (((Integer) objArr[i2]).intValue()) {
                    case 0:
                        appendDescriptor(sb, 1, "T");
                        break;
                    case 1:
                        appendDescriptor(sb, 1, "I");
                        break;
                    case 2:
                        appendDescriptor(sb, 1, "F");
                        break;
                    case 3:
                        appendDescriptor(sb, 1, "D");
                        break;
                    case 4:
                        appendDescriptor(sb, 1, "J");
                        break;
                    case 5:
                        appendDescriptor(sb, 1, "N");
                        break;
                    case 6:
                        appendDescriptor(sb, 1, "U");
                        break;
                    default:
                        if (!$assertionsDisabled) {
                            throw new AssertionError();
                        }
                        break;
                }
            } else {
                appendLabel(sb, (Label) objArr[i2]);
            }
        }
    }

    private static void appendShortDescriptor(StringBuilder sb, String str) {
        if (str.charAt(0) == '(') {
            int i = 0;
            while (i < str.length()) {
                if (str.charAt(i) == 'L') {
                    int i2 = i;
                    while (str.charAt(i) != ';') {
                        i++;
                        if (str.charAt(i) == '/') {
                            i2 = i;
                        }
                    }
                    sb.append(str.substring(i2 + 1, i)).append(';');
                } else {
                    sb.append(str.charAt(i));
                }
                i++;
            }
            return;
        }
        int iLastIndexOf = str.lastIndexOf(47);
        int iLastIndexOf2 = str.lastIndexOf(91);
        if (iLastIndexOf2 != -1) {
            sb.append((CharSequence) str, 0, iLastIndexOf2 + 1);
        }
        sb.append(iLastIndexOf == -1 ? str : str.substring(iLastIndexOf + 1));
    }

    private static void appendStr(StringBuilder sb, String str) {
        sb.append('\"');
        for (int i = 0; i < str.length(); i++) {
            char cCharAt = str.charAt(i);
            if (cCharAt == '\n') {
                sb.append("\\n");
            } else if (cCharAt == '\r') {
                sb.append("\\r");
            } else if (cCharAt == '\\') {
                sb.append("\\\\");
            } else if (cCharAt == '\"') {
                sb.append("\\\"");
            } else if (cCharAt < ' ' || cCharAt > '\u007f') {
                sb.append("\\u");
                if (cCharAt < 16) {
                    sb.append("000");
                } else if (cCharAt < '\u0100') {
                    sb.append("00");
                } else if (cCharAt < '\u1000') {
                    sb.append('0');
                }
                sb.append(Integer.toString(cCharAt, 16));
            } else {
                sb.append(cCharAt);
            }
        }
        sb.append('\"');
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/debug/NashornTextifier$Graph.class */
    private static class Graph {
        private final String name;
        private static final String LEFT_ALIGN = "\\l";
        private static final String COLOR_CATCH = "\"#ee9999\"";
        private static final String COLOR_ORPHAN = "\"#9999bb\"";
        private static final String COLOR_DEFAULT = "\"#99bb99\"";
        private static final String COLOR_LOCALVARS = "\"#999999\"";
        static final boolean $assertionsDisabled;
        private final LinkedHashSet nodes = new LinkedHashSet();
        private final Map contents = new HashMap();
        private final Map edges = new HashMap();
        private final Set hasPreds = new HashSet();
        private final Map catches = new HashMap();
        private final Set noFallThru = new HashSet();
        private final Map exceptionMap = new HashMap();

        static {
            $assertionsDisabled = !NashornTextifier.class.desiredAssertionStatus();
        }

        Graph(String str) {
            this.name = str;
        }

        void addEdge(String str, String str2) {
            Set linkedHashSet = (Set) this.edges.get(str);
            if (linkedHashSet == null) {
                linkedHashSet = new LinkedHashSet();
                this.edges.put(str, linkedHashSet);
            }
            linkedHashSet.add(str2);
            this.hasPreds.add(str2);
        }

        void addTryCatch(String str, String str2) {
            Set hashSet = (Set) this.exceptionMap.get(str2);
            if (hashSet == null) {
                hashSet = new HashSet();
                this.exceptionMap.put(str2, hashSet);
            }
            if (!hashSet.contains(str)) {
                addEdge(str, str2);
            }
            hashSet.add(str);
        }

        void addNode(String str) {
            if (!$assertionsDisabled && this.nodes.contains(str)) {
                throw new AssertionError();
            }
            this.nodes.add(str);
        }

        void setNoFallThru(String str) {
            this.noFallThru.add(str);
        }

        boolean isNoFallThru(String str) {
            return this.noFallThru.contains(str);
        }

        void setIsCatch(String str, String str2) {
            this.catches.put(str, str2);
        }

        String getName() {
            return this.name;
        }

        void addText(String str, String str2) {
            StringBuilder sb = (StringBuilder) this.contents.get(str);
            if (sb == null) {
                sb = new StringBuilder();
            }
            for (int i = 0; i < str2.length(); i++) {
                switch (str2.charAt(i)) {
                    case '\n':
                        sb.append(LEFT_ALIGN);
                        break;
                    case '\"':
                        sb.append("'");
                        break;
                    default:
                        sb.append(str2.charAt(i));
                        break;
                }
            }
            this.contents.put(str, sb);
        }

        private static String dottyFriendly(String str) {
            return str.replace(':', '_');
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("digraph " + dottyFriendly(this.name) + " {");
            sb.append("\n");
            sb.append("\tgraph [fontname=courier]\n");
            sb.append("\tnode [style=filled,color=\"#99bb99\",fontname=courier]\n");
            sb.append("\tedge [fontname=courier]\n\n");
            Iterator it = this.nodes.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                sb.append("\t");
                sb.append(str);
                sb.append(" [");
                sb.append("id=");
                sb.append(str);
                sb.append(", label=\"");
                String string = ((StringBuilder) this.contents.get(str)).toString();
                if (string.startsWith(LEFT_ALIGN)) {
                    string = string.substring(2);
                }
                String str2 = (String) this.catches.get(str);
                if (str2 != null) {
                    sb.append("*** CATCH: ").append(str2).append(" ***\\l");
                }
                sb.append(string);
                sb.append("\"]\n");
            }
            for (String str3 : this.edges.keySet()) {
                for (String str4 : (Set) this.edges.get(str3)) {
                    sb.append("\t");
                    sb.append(str3);
                    sb.append(ActivityStack.GLUE_STRING);
                    sb.append(str4);
                    sb.append("[label=\"");
                    sb.append(str4);
                    sb.append("\"");
                    if (this.catches.get(str4) != null) {
                        sb.append(", color=red, style=dashed");
                    }
                    sb.append(']');
                    sb.append(";\n");
                }
            }
            sb.append("\n");
            Iterator it2 = this.nodes.iterator();
            while (it2.hasNext()) {
                String str5 = (String) it2.next();
                sb.append("\t");
                sb.append(str5);
                sb.append(" [shape=box");
                if (this.catches.get(str5) != null) {
                    sb.append(", color=\"#ee9999\"");
                } else if ("vars".equals(str5)) {
                    sb.append(", shape=hexagon, color=\"#999999\"");
                } else if (!this.hasPreds.contains(str5)) {
                    sb.append(", color=\"#9999bb\"");
                }
                sb.append("]\n");
            }
            sb.append("}\n");
            return sb.toString();
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/debug/NashornTextifier$NashornLabel.class */
    static class NashornLabel extends Label {
        final Label label;
        final int bci;
        final int opcode;

        NashornLabel(Label label, int i) {
            this.label = label;
            this.bci = i;
            this.opcode = -1;
        }

        NashornLabel(int i, int i2) {
            this.opcode = i;
            this.bci = i2;
            this.label = null;
        }

        Label getLabel() {
            return this.label;
        }

        public int getOffset() {
            return this.bci;
        }

        public String toString() {
            return "label " + this.bci;
        }
    }

    public Printer visitAnnotationDefault() {
        throw new AssertionError();
    }

    public void visitClassAttribute(Attribute attribute) {
        throw new AssertionError();
    }

    public Printer visitFieldAnnotation(String str, boolean z) {
        throw new AssertionError();
    }

    public void visitFieldAttribute(Attribute attribute) {
        throw new AssertionError();
    }

    public void visitMethodAttribute(Attribute attribute) {
        throw new AssertionError();
    }

    public Printer visitParameterAnnotation(int i, String str, boolean z) {
        throw new AssertionError();
    }

    public void visit(String str, Object obj) {
        throw new AssertionError();
    }

    public Printer visitAnnotation(String str, String str2) {
        throw new AssertionError();
    }

    public Printer visitArray(String str) {
        throw new AssertionError();
    }

    public void visitEnum(String str, String str2, String str3) {
        throw new AssertionError();
    }

    public void visitInnerClass(String str, String str2, String str3, int i) {
        throw new AssertionError();
    }
}
