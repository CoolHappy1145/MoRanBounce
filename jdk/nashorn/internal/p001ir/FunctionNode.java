package jdk.nashorn.internal.p001ir;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import jdk.nashorn.internal.codegen.CompileUnit;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.Namespace;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.p001ir.annotations.Ignore;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.Source;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/FunctionNode.class */
public final class FunctionNode extends LexicalContextExpression implements Flags, CompileUnitHolder {
    private static final long serialVersionUID = 1;
    public static final Type FUNCTION_TYPE = Type.typeFor((Class<?>) ScriptFunction.class);
    private final Source source;
    private final Object endParserState;

    @Ignore
    private final IdentNode ident;
    private final Block body;
    private final String name;
    private final CompileUnit compileUnit;
    private final Kind kind;
    private final List parameters;
    private final long firstToken;
    private final long lastToken;
    private final Namespace namespace;

    @Ignore
    private final int thisProperties;
    private final int flags;
    private final int lineNumber;
    private final Class rootClass;
    public static final int IS_ANONYMOUS = 1;
    public static final int IS_DECLARED = 2;
    public static final int IS_STRICT = 4;
    public static final int USES_ARGUMENTS = 8;
    public static final int IS_SPLIT = 16;
    public static final int HAS_EVAL = 32;
    public static final int HAS_NESTED_EVAL = 64;
    public static final int HAS_SCOPE_BLOCK = 128;
    public static final int DEFINES_ARGUMENTS = 256;
    public static final int USES_ANCESTOR_SCOPE = 512;
    public static final int HAS_FUNCTION_DECLARATIONS = 1024;
    public static final int IS_DEOPTIMIZABLE = 2048;
    public static final int HAS_APPLY_TO_CALL_SPECIALIZATION = 4096;
    public static final int IS_PROGRAM = 8192;
    public static final int USES_SELF_SYMBOL = 16384;
    public static final int USES_THIS = 32768;
    public static final int IN_DYNAMIC_CONTEXT = 65536;
    public static final int IS_PRINT_PARSE = 131072;
    public static final int IS_PRINT_LOWER_PARSE = 262144;
    public static final int IS_PRINT_AST = 524288;
    public static final int IS_PRINT_LOWER_AST = 1048576;
    public static final int IS_PRINT_SYMBOLS = 2097152;
    public static final int IS_PROFILE = 4194304;
    public static final int IS_TRACE_ENTEREXIT = 8388608;
    public static final int IS_TRACE_MISSES = 16777216;
    public static final int IS_TRACE_VALUES = 33554432;
    public static final int NEEDS_CALLEE = 67108864;
    public static final int IS_CACHED = 134217728;
    public static final int EXTENSION_CALLSITE_FLAGS = 66977792;
    private static final int HAS_DEEP_EVAL = 96;
    private static final int HAS_ALL_VARS_IN_SCOPE = 96;
    private static final int MAYBE_NEEDS_ARGUMENTS = 40;
    public static final int NEEDS_PARENT_SCOPE = 8800;
    private Type returnType;

    /* loaded from: L-out.jar:jdk/nashorn/internal/ir/FunctionNode$Kind.class */
    public enum Kind {
        NORMAL,
        SCRIPT,
        GETTER,
        SETTER
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextExpression, jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        return super.accept(nodeVisitor);
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public LexicalContextNode setFlags(LexicalContext lexicalContext, int i) {
        return setFlags(lexicalContext, i);
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public LexicalContextNode setFlag(LexicalContext lexicalContext, int i) {
        return setFlag(lexicalContext, i);
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public LexicalContextNode clearFlag(LexicalContext lexicalContext, int i) {
        return clearFlag(lexicalContext, i);
    }

    public FunctionNode(Source source, int i, long j, int i2, long j2, Namespace namespace, IdentNode identNode, String str, List list, Kind kind, int i3) {
        super(j, i2);
        this.returnType = Type.UNKNOWN;
        this.source = source;
        this.lineNumber = i;
        this.ident = identNode;
        this.name = str;
        this.kind = kind;
        this.parameters = list;
        this.firstToken = j2;
        this.lastToken = j;
        this.namespace = namespace;
        this.flags = i3;
        this.compileUnit = null;
        this.body = null;
        this.thisProperties = 0;
        this.rootClass = null;
        this.endParserState = null;
    }

    private FunctionNode(FunctionNode functionNode, long j, Object obj, int i, String str, Type type, CompileUnit compileUnit, Block block, List list, int i2, Class cls, Source source, Namespace namespace) {
        super(functionNode);
        this.returnType = Type.UNKNOWN;
        this.endParserState = obj;
        this.lineNumber = functionNode.lineNumber;
        this.flags = i;
        this.name = str;
        this.returnType = type;
        this.compileUnit = compileUnit;
        this.lastToken = j;
        this.body = block;
        this.parameters = list;
        this.thisProperties = i2;
        this.rootClass = cls;
        this.source = source;
        this.namespace = namespace;
        this.ident = functionNode.ident;
        this.kind = functionNode.kind;
        this.firstToken = functionNode.firstToken;
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lexicalContext, NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterFunctionNode(this)) {
            return nodeVisitor.leaveFunctionNode(setBody(lexicalContext, (Block) this.body.accept(nodeVisitor)));
        }
        return this;
    }

    public List visitParameters(NodeVisitor nodeVisitor) {
        return Node.accept(nodeVisitor, this.parameters);
    }

    public int getCallSiteFlags() {
        int i = 0;
        if (getFlag(4)) {
            i = 2;
        }
        if ((this.flags & EXTENSION_CALLSITE_FLAGS) == 0) {
            return i;
        }
        if (getFlag(4194304)) {
            i |= 64;
        }
        if (getFlag(16777216)) {
            i |= 384;
        }
        if (getFlag(33554432)) {
            i |= 1664;
        }
        if (getFlag(8388608)) {
            i |= 640;
        }
        return i;
    }

    public Source getSource() {
        return this.source;
    }

    public FunctionNode initializeDeserialized(Source source, Namespace namespace) {
        if (source == null || namespace == null) {
            throw new IllegalArgumentException();
        }
        if (this.source == source && this.namespace == namespace) {
            return this;
        }
        if (this.source != null || this.namespace != null) {
            throw new IllegalStateException();
        }
        return new FunctionNode(this, this.lastToken, this.endParserState, this.flags, this.name, this.returnType, this.compileUnit, this.body, this.parameters, this.thisProperties, this.rootClass, source, namespace);
    }

    public int getId() {
        return position();
    }

    public String getSourceName() {
        return getSourceName(this.source);
    }

    public static String getSourceName(Source source) {
        String explicitURL = source.getExplicitURL();
        return explicitURL != null ? explicitURL : source.getName();
    }

    public static int getDirectiveFlag(String str) {
        switch (str) {
            case "nashorn callsite trace enterexit":
                return 8388608;
            case "nashorn callsite trace misses":
                return 16777216;
            case "nashorn callsite trace objects":
                return 33554432;
            case "nashorn callsite profile":
                return 4194304;
            case "nashorn print parse":
                return 131072;
            case "nashorn print lower parse":
                return 262144;
            case "nashorn print ast":
                return 524288;
            case "nashorn print lower ast":
                return 1048576;
            case "nashorn print symbols":
                return 2097152;
            default:
                return 0;
        }
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public String uniqueName(String str) {
        return this.namespace.uniqueName(str);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append('[').append(this.returnType).append(']').append(' ');
        sb.append("function");
        if (this.ident != null) {
            sb.append(' ');
            this.ident.toString(sb, z);
        }
        sb.append('(');
        Iterator it = this.parameters.iterator();
        while (it.hasNext()) {
            IdentNode identNode = (IdentNode) it.next();
            if (identNode.getSymbol() != null) {
                sb.append('[').append(identNode.getType()).append(']').append(' ');
            }
            identNode.toString(sb, z);
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(')');
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public int getFlags() {
        return this.flags;
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public boolean getFlag(int i) {
        return (this.flags & i) != 0;
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public FunctionNode setFlags(LexicalContext lexicalContext, int i) {
        if (this.flags == i) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lexicalContext, this, new FunctionNode(this, this.lastToken, this.endParserState, i, this.name, this.returnType, this.compileUnit, this.body, this.parameters, this.thisProperties, this.rootClass, this.source, this.namespace));
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public FunctionNode clearFlag(LexicalContext lexicalContext, int i) {
        return setFlags(lexicalContext, this.flags & (i ^ (-1)));
    }

    @Override // jdk.nashorn.internal.p001ir.Flags
    public FunctionNode setFlag(LexicalContext lexicalContext, int i) {
        return setFlags(lexicalContext, this.flags | i);
    }

    public boolean isProgram() {
        return getFlag(8192);
    }

    public boolean canBeDeoptimized() {
        return getFlag(2048);
    }

    public boolean hasEval() {
        return getFlag(32);
    }

    public boolean hasNestedEval() {
        return getFlag(64);
    }

    public long getFirstToken() {
        return this.firstToken;
    }

    public boolean hasDeclaredFunctions() {
        return getFlag(1024);
    }

    public boolean needsCallee() {
        return needsParentScope() || usesSelfSymbol() || isSplit() || (needsArguments() && !isStrict()) || hasApplyToCallSpecialization();
    }

    public boolean usesThis() {
        return getFlag(32768);
    }

    public boolean hasApplyToCallSpecialization() {
        return getFlag(4096);
    }

    public IdentNode getIdent() {
        return this.ident;
    }

    public Block getBody() {
        return this.body;
    }

    public FunctionNode setBody(LexicalContext lexicalContext, Block block) {
        if (this.body == block) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lexicalContext, this, new FunctionNode(this, this.lastToken, this.endParserState, this.flags | (block.needsScope() ? 128 : 0), this.name, this.returnType, this.compileUnit, block, this.parameters, this.thisProperties, this.rootClass, this.source, this.namespace));
    }

    public boolean isVarArg() {
        return needsArguments() || this.parameters.size() > 250;
    }

    public boolean inDynamicContext() {
        return getFlag(65536);
    }

    public boolean needsDynamicScope() {
        return hasEval() && !isStrict();
    }

    public FunctionNode setInDynamicContext(LexicalContext lexicalContext) {
        return setFlag(lexicalContext, 65536);
    }

    public boolean needsArguments() {
        return (!getFlag(40) || getFlag(256) || isProgram()) ? false : true;
    }

    public boolean needsParentScope() {
        return getFlag(8800);
    }

    public FunctionNode setThisProperties(LexicalContext lexicalContext, int i) {
        if (this.thisProperties == i) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lexicalContext, this, new FunctionNode(this, this.lastToken, this.endParserState, this.flags, this.name, this.returnType, this.compileUnit, this.body, this.parameters, i, this.rootClass, this.source, this.namespace));
    }

    public int getThisProperties() {
        return this.thisProperties;
    }

    public boolean hasScopeBlock() {
        return getFlag(128);
    }

    public Kind getKind() {
        return this.kind;
    }

    public long getLastToken() {
        return this.lastToken;
    }

    public FunctionNode setLastToken(LexicalContext lexicalContext, long j) {
        if (this.lastToken == j) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lexicalContext, this, new FunctionNode(this, j, this.endParserState, this.flags, this.name, this.returnType, this.compileUnit, this.body, this.parameters, this.thisProperties, this.rootClass, this.source, this.namespace));
    }

    public Object getEndParserState() {
        return this.endParserState;
    }

    public FunctionNode setEndParserState(LexicalContext lexicalContext, Object obj) {
        if (this.endParserState == obj) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lexicalContext, this, new FunctionNode(this, this.lastToken, obj, this.flags, this.name, this.returnType, this.compileUnit, this.body, this.parameters, this.thisProperties, this.rootClass, this.source, this.namespace));
    }

    public String getName() {
        return this.name;
    }

    public FunctionNode setName(LexicalContext lexicalContext, String str) {
        if (this.name.equals(str)) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lexicalContext, this, new FunctionNode(this, this.lastToken, this.endParserState, this.flags, str, this.returnType, this.compileUnit, this.body, this.parameters, this.thisProperties, this.rootClass, this.source, this.namespace));
    }

    public boolean allVarsInScope() {
        return getFlag(96);
    }

    public boolean isSplit() {
        return getFlag(16);
    }

    public List getParameters() {
        return Collections.unmodifiableList(this.parameters);
    }

    public int getNumOfParams() {
        return this.parameters.size();
    }

    public IdentNode getParameter(int i) {
        return (IdentNode) this.parameters.get(i);
    }

    public FunctionNode setParameters(LexicalContext lexicalContext, List list) {
        if (this.parameters == list) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lexicalContext, this, new FunctionNode(this, this.lastToken, this.endParserState, this.flags, this.name, this.returnType, this.compileUnit, this.body, list, this.thisProperties, this.rootClass, this.source, this.namespace));
    }

    public boolean isDeclared() {
        return getFlag(2);
    }

    public boolean isAnonymous() {
        return getFlag(1);
    }

    public boolean usesSelfSymbol() {
        return getFlag(16384);
    }

    public boolean isNamedFunctionExpression() {
        return !getFlag(8195);
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getType() {
        return FUNCTION_TYPE;
    }

    @Override // jdk.nashorn.internal.p001ir.Expression
    public Type getWidestOperationType() {
        return FUNCTION_TYPE;
    }

    public Type getReturnType() {
        return this.returnType;
    }

    /*  JADX ERROR: Types fix failed
        java.lang.NullPointerException: Cannot invoke "jadx.core.dex.instructions.args.InsnArg.getType()" because "changeArg" is null
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.moveListener(TypeUpdate.java:439)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.runListeners(TypeUpdate.java:232)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.requestUpdate(TypeUpdate.java:212)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeForSsaVar(TypeUpdate.java:183)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.updateTypeChecked(TypeUpdate.java:112)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:83)
        	at jadx.core.dex.visitors.typeinference.TypeUpdate.apply(TypeUpdate.java:56)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryPossibleTypes(FixTypesVisitor.java:183)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.deduceType(FixTypesVisitor.java:242)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryDeduceTypes(FixTypesVisitor.java:221)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:91)
        */
    /* JADX WARN: Not initialized variable reg: 24, insn: 0x0001: MOVE (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r24 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) (LINE:506), block:B:2:0x0000 */
    public jdk.nashorn.internal.p001ir.FunctionNode setReturnType(jdk.nashorn.internal.p001ir.LexicalContext r21, jdk.nashorn.internal.codegen.types.Type r22) {
        /*
            r20 = this;
            r0 = r22
            r1 = r24
            boolean r1 = r1 instanceof jdk.nashorn.internal.codegen.types.ObjectType
            if (r1 == 0) goto Lf
            jdk.nashorn.internal.codegen.types.Type r1 = jdk.nashorn.internal.codegen.types.Type.OBJECT
            goto L10
        Lf:
            r1 = r22
        L10:
            r23 = r1
            r1 = r20
            jdk.nashorn.internal.codegen.types.Type r1 = r1.returnType
            r2 = r23
            if (r1 != r2) goto L1b
            r1 = r20
            return r1
        L1b:
            r1 = r21
            r2 = r20
            jdk.nashorn.internal.ir.FunctionNode r3 = new jdk.nashorn.internal.ir.FunctionNode
            r4 = r3
            r5 = r20
            r6 = r20
            long r6 = r6.lastToken
            r7 = r20
            java.lang.Object r7 = r7.endParserState
            r8 = r20
            int r8 = r8.flags
            r9 = r20
            java.lang.String r9 = r9.name
            r10 = r23
            r11 = r20
            jdk.nashorn.internal.codegen.CompileUnit r11 = r11.compileUnit
            r12 = r20
            jdk.nashorn.internal.ir.Block r12 = r12.body
            r13 = r20
            java.util.List r13 = r13.parameters
            r14 = r20
            int r14 = r14.thisProperties
            r15 = r20
            java.lang.Class r15 = r15.rootClass
            r16 = r20
            jdk.nashorn.internal.runtime.Source r16 = r16.source
            r17 = r20
            jdk.nashorn.internal.codegen.Namespace r17 = r17.namespace
            r4.<init>(r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15, r16, r17)
            jdk.nashorn.internal.ir.LexicalContextNode r1 = jdk.nashorn.internal.p001ir.Node.replaceInLexicalContext(r1, r2, r3)
            jdk.nashorn.internal.ir.FunctionNode r1 = (jdk.nashorn.internal.p001ir.FunctionNode) r1
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.p001ir.FunctionNode.setReturnType(jdk.nashorn.internal.ir.LexicalContext, jdk.nashorn.internal.codegen.types.Type):jdk.nashorn.internal.ir.FunctionNode");
    }

    public boolean isStrict() {
        return getFlag(4);
    }

    public boolean isCached() {
        return getFlag(134217728);
    }

    public FunctionNode setCached(LexicalContext lexicalContext) {
        return setFlag(lexicalContext, 134217728);
    }

    @Override // jdk.nashorn.internal.p001ir.CompileUnitHolder
    public CompileUnit getCompileUnit() {
        return this.compileUnit;
    }

    public FunctionNode setCompileUnit(LexicalContext lexicalContext, CompileUnit compileUnit) {
        if (this.compileUnit == compileUnit) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lexicalContext, this, new FunctionNode(this, this.lastToken, this.endParserState, this.flags, this.name, this.returnType, compileUnit, this.body, this.parameters, this.thisProperties, this.rootClass, this.source, this.namespace));
    }

    public Symbol compilerConstant(CompilerConstants compilerConstants) {
        return this.body.getExistingSymbol(compilerConstants.symbolName());
    }

    public Class getRootClass() {
        return this.rootClass;
    }

    public FunctionNode setRootClass(LexicalContext lexicalContext, Class cls) {
        if (this.rootClass == cls) {
            return this;
        }
        return (FunctionNode) Node.replaceInLexicalContext(lexicalContext, this, new FunctionNode(this, this.lastToken, this.endParserState, this.flags, this.name, this.returnType, this.compileUnit, this.body, this.parameters, this.thisProperties, cls, this.source, this.namespace));
    }
}
