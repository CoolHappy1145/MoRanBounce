package jdk.nashorn.internal.codegen;

import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Supplier;
import jdk.nashorn.internal.AssertsEnabled;
import jdk.nashorn.internal.IntDeque;
import jdk.nashorn.internal.codegen.ClassEmitter;
import jdk.nashorn.internal.codegen.CompilerConstants;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.codegen.types.ArrayType;
import jdk.nashorn.internal.codegen.types.Type;
import jdk.nashorn.internal.objects.Global;
import jdk.nashorn.internal.p001ir.AccessNode;
import jdk.nashorn.internal.p001ir.BaseNode;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.BlockStatement;
import jdk.nashorn.internal.p001ir.BreakNode;
import jdk.nashorn.internal.p001ir.CallNode;
import jdk.nashorn.internal.p001ir.CaseNode;
import jdk.nashorn.internal.p001ir.CatchNode;
import jdk.nashorn.internal.p001ir.ContinueNode;
import jdk.nashorn.internal.p001ir.EmptyNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.ExpressionStatement;
import jdk.nashorn.internal.p001ir.ForNode;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.GetSplitState;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.IfNode;
import jdk.nashorn.internal.p001ir.IndexNode;
import jdk.nashorn.internal.p001ir.JoinPredecessorExpression;
import jdk.nashorn.internal.p001ir.JumpStatement;
import jdk.nashorn.internal.p001ir.JumpToInlinedFinally;
import jdk.nashorn.internal.p001ir.LabelNode;
import jdk.nashorn.internal.p001ir.LexicalContext;
import jdk.nashorn.internal.p001ir.LexicalContextNode;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.LocalVariableConversion;
import jdk.nashorn.internal.p001ir.LoopNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.ObjectNode;
import jdk.nashorn.internal.p001ir.Optimistic;
import jdk.nashorn.internal.p001ir.PropertyNode;
import jdk.nashorn.internal.p001ir.ReturnNode;
import jdk.nashorn.internal.p001ir.RuntimeNode;
import jdk.nashorn.internal.p001ir.SetSplitState;
import jdk.nashorn.internal.p001ir.SplitReturn;
import jdk.nashorn.internal.p001ir.Splittable;
import jdk.nashorn.internal.p001ir.Statement;
import jdk.nashorn.internal.p001ir.SwitchNode;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.p001ir.TernaryNode;
import jdk.nashorn.internal.p001ir.ThrowNode;
import jdk.nashorn.internal.p001ir.TryNode;
import jdk.nashorn.internal.p001ir.UnaryNode;
import jdk.nashorn.internal.p001ir.VarNode;
import jdk.nashorn.internal.p001ir.WhileNode;
import jdk.nashorn.internal.p001ir.WithNode;
import jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.parser.Lexer;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Debug;
import jdk.nashorn.internal.runtime.ECMAException;
import jdk.nashorn.internal.runtime.JSType;
import jdk.nashorn.internal.runtime.OptimisticReturnFilters;
import jdk.nashorn.internal.runtime.PropertyMap;
import jdk.nashorn.internal.runtime.RecompilableScriptFunctionData;
import jdk.nashorn.internal.runtime.RewriteException;
import jdk.nashorn.internal.runtime.Scope;
import jdk.nashorn.internal.runtime.ScriptEnvironment;
import jdk.nashorn.internal.runtime.ScriptFunction;
import jdk.nashorn.internal.runtime.ScriptObject;
import jdk.nashorn.internal.runtime.ScriptRuntime;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.Undefined;
import jdk.nashorn.internal.runtime.UnwarrantedOptimismException;
import jdk.nashorn.internal.runtime.arrays.ArrayData;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;
import jdk.nashorn.internal.runtime.options.Options;
import jdk.nashorn.internal.runtime.regexp.joni.constants.AsmConstants;

@Logger(name = "codegen")
/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator.class */
final class CodeGenerator extends NodeOperatorVisitor<CodeGeneratorLexicalContext> implements Loggable {
    private static final Type SCOPE_TYPE;
    private static final String GLOBAL_OBJECT;
    private static final CompilerConstants.Call CREATE_REWRITE_EXCEPTION;
    private static final CompilerConstants.Call CREATE_REWRITE_EXCEPTION_REST_OF;
    private static final CompilerConstants.Call ENSURE_INT;
    private static final CompilerConstants.Call ENSURE_NUMBER;
    private static final CompilerConstants.Call CREATE_FUNCTION_OBJECT;
    private static final CompilerConstants.Call CREATE_FUNCTION_OBJECT_NO_SCOPE;
    private static final CompilerConstants.Call TO_NUMBER_FOR_EQ;
    private static final CompilerConstants.Call TO_NUMBER_FOR_STRICT_EQ;
    private static final Class<?> ITERATOR_CLASS;
    private static final Type ITERATOR_TYPE;
    private static final Type EXCEPTION_TYPE;
    private static final Integer INT_ZERO;
    private final Compiler compiler;
    private final boolean evalCode;
    private final int callSiteFlags;
    private int regexFieldCount;
    private int lastLineNumber;
    private static final int MAX_REGEX_FIELDS = 2048;
    private MethodEmitter method;
    private CompileUnit unit;
    private final DebugLogger log;
    static final int OBJECT_SPILL_THRESHOLD;
    private final Set<String> emittedMethods;
    private ContinuationInfo continuationInfo;
    private final Deque<Label> scopeEntryLabels;
    private static final Label METHOD_BOUNDARY;
    private final Deque<Label> catchLabels;
    private final IntDeque labeledBlockBreakLiveLocals;
    private final int[] continuationEntryPoints;
    static final /* synthetic */ boolean $assertionsDisabled;

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$SplitLiteralCreator.class */
    interface SplitLiteralCreator {
        void populateRange(MethodEmitter methodEmitter, Type type, int i, int i2, int i3);
    }

    static {
        $assertionsDisabled = !CodeGenerator.class.desiredAssertionStatus();
        SCOPE_TYPE = Type.typeFor((Class<?>) ScriptObject.class);
        GLOBAL_OBJECT = Type.getInternalName(Global.class);
        CREATE_REWRITE_EXCEPTION = CompilerConstants.staticCallNoLookup(RewriteException.class, "create", RewriteException.class, new Class[]{UnwarrantedOptimismException.class, Object[].class, String[].class});
        CREATE_REWRITE_EXCEPTION_REST_OF = CompilerConstants.staticCallNoLookup(RewriteException.class, "create", RewriteException.class, new Class[]{UnwarrantedOptimismException.class, Object[].class, String[].class, int[].class});
        ENSURE_INT = CompilerConstants.staticCallNoLookup(OptimisticReturnFilters.class, "ensureInt", Integer.TYPE, new Class[]{Object.class, Integer.TYPE});
        ENSURE_NUMBER = CompilerConstants.staticCallNoLookup(OptimisticReturnFilters.class, "ensureNumber", Double.TYPE, new Class[]{Object.class, Integer.TYPE});
        CREATE_FUNCTION_OBJECT = CompilerConstants.staticCallNoLookup(ScriptFunction.class, "create", ScriptFunction.class, new Class[]{Object[].class, Integer.TYPE, ScriptObject.class});
        CREATE_FUNCTION_OBJECT_NO_SCOPE = CompilerConstants.staticCallNoLookup(ScriptFunction.class, "create", ScriptFunction.class, new Class[]{Object[].class, Integer.TYPE});
        TO_NUMBER_FOR_EQ = CompilerConstants.staticCallNoLookup(JSType.class, "toNumberForEq", Double.TYPE, new Class[]{Object.class});
        TO_NUMBER_FOR_STRICT_EQ = CompilerConstants.staticCallNoLookup(JSType.class, "toNumberForStrictEq", Double.TYPE, new Class[]{Object.class});
        ITERATOR_CLASS = Iterator.class;
        if (!$assertionsDisabled && ITERATOR_CLASS != CompilerConstants.ITERATOR_PREFIX.type()) {
            throw new AssertionError();
        }
        ITERATOR_TYPE = Type.typeFor(ITERATOR_CLASS);
        EXCEPTION_TYPE = Type.typeFor((Class<?>) CompilerConstants.EXCEPTION_PREFIX.type());
        INT_ZERO = 0;
        OBJECT_SPILL_THRESHOLD = Options.getIntProperty("nashorn.spill.threshold", 256);
        METHOD_BOUNDARY = new Label("");
    }

    static /* synthetic */ MethodEmitter access$000(CodeGenerator x0) {
        return x0.method;
    }

    static /* synthetic */ MethodEmitter access$600(CodeGenerator x0, Expression x1) {
        return x0.loadExpressionAsObject(x1);
    }

    static /* synthetic */ MethodEmitter access$700(CodeGenerator x0, Expression x1) {
        return x0.loadExpressionUnbounded(x1);
    }

    static /* synthetic */ int access$5100(CodeGenerator x0, List x1) {
        return x0.loadArgs(x1);
    }

    CodeGenerator(Compiler compiler, int[] continuationEntryPoints) {
        super(new CodeGeneratorLexicalContext());
        this.lastLineNumber = -1;
        this.emittedMethods = new HashSet();
        this.scopeEntryLabels = new ArrayDeque();
        this.catchLabels = new ArrayDeque();
        this.labeledBlockBreakLiveLocals = new IntDeque();
        this.compiler = compiler;
        this.evalCode = compiler.getSource().isEvalCode();
        this.continuationEntryPoints = continuationEntryPoints;
        this.callSiteFlags = compiler.getScriptEnvironment()._callsite_flags;
        this.log = initLogger(compiler.getContext());
    }

    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger getLogger() {
        return this.log;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // jdk.nashorn.internal.runtime.logging.Loggable
    public DebugLogger initLogger(Context context) {
        return context.getLogger(getClass());
    }

    int getCallSiteFlags() {
        return ((CodeGeneratorLexicalContext) this.f30lc).getCurrentFunction().getCallSiteFlags() | this.callSiteFlags;
    }

    private int getScopeCallSiteFlags(Symbol symbol) {
        if (!$assertionsDisabled && !symbol.isScope()) {
            throw new AssertionError();
        }
        int flags = getCallSiteFlags() | 1;
        if (isEvalCode() && symbol.isGlobal()) {
            return flags;
        }
        return isFastScope(symbol) ? flags | 4 : flags;
    }

    boolean isEvalCode() {
        return this.evalCode;
    }

    boolean useDualFields() {
        return this.compiler.getContext().useDualFields();
    }

    private MethodEmitter loadIdent(IdentNode identNode, TypeBounds resultBounds) {
        checkTemporalDeadZone(identNode);
        Symbol symbol = identNode.getSymbol();
        if (!symbol.isScope()) {
            Type type = identNode.getType();
            if (type == Type.UNDEFINED) {
                return this.method.loadUndefined(resultBounds.widest);
            }
            if ($assertionsDisabled || symbol.hasSlot() || symbol.isParam()) {
                return this.method.load(identNode);
            }
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !identNode.getSymbol().isScope()) {
            throw new AssertionError(identNode + " is not in scope!");
        }
        int flags = getScopeCallSiteFlags(symbol);
        if (isFastScope(symbol)) {
            if (symbol.getUseCount() > 200 && !identNode.isOptimistic()) {
                new OptimisticOperation(this, identNode, TypeBounds.OBJECT, resultBounds, symbol, flags) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.1
                    final TypeBounds val$resultBounds;
                    final Symbol val$symbol;
                    final int val$flags;
                    final CodeGenerator this$0;

                    {
                        this.this$0 = this;
                        this.val$resultBounds = resultBounds;
                        this.val$symbol = symbol;
                        this.val$flags = flags;
                    }

                    @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                    void loadStack() {
                        this.this$0.method.loadCompilerConstant(CompilerConstants.SCOPE);
                    }

                    @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                    void consumeStack() {
                        this.this$0.loadSharedScopeVar(this.val$resultBounds.widest, this.val$symbol, this.val$flags);
                    }
                }.emit();
            } else {
                new LoadFastScopeVar(this, identNode, resultBounds, flags).emit();
            }
        } else {
            new LoadScopeVar(this, identNode, resultBounds, flags).emit();
        }
        return this.method;
    }

    private void checkTemporalDeadZone(IdentNode identNode) {
        if (identNode.isDead()) {
            this.method.load(identNode.getSymbol().getName()).invoke(ScriptRuntime.THROW_REFERENCE_ERROR);
        }
    }

    private void checkAssignTarget(Expression expression) {
        if ((expression instanceof IdentNode) && ((IdentNode) expression).getSymbol().isConst()) {
            this.method.load(((IdentNode) expression).getSymbol().getName()).invoke(ScriptRuntime.THROW_CONST_TYPE_ERROR);
        }
    }

    private boolean isRestOf() {
        return this.continuationEntryPoints != null;
    }

    private boolean isCurrentContinuationEntryPoint(int programPoint) {
        return isRestOf() && getCurrentContinuationEntryPoint() == programPoint;
    }

    private int[] getContinuationEntryPoints() {
        if (isRestOf()) {
            return this.continuationEntryPoints;
        }
        return null;
    }

    private int getCurrentContinuationEntryPoint() {
        if (isRestOf()) {
            return this.continuationEntryPoints[0];
        }
        return -1;
    }

    private boolean isContinuationEntryPoint(int programPoint) {
        if (isRestOf()) {
            if (!$assertionsDisabled && this.continuationEntryPoints == null) {
                throw new AssertionError();
            }
            for (int cep : this.continuationEntryPoints) {
                if (cep == programPoint) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private boolean isFastScope(Symbol symbol) {
        if (!symbol.isScope()) {
            return false;
        }
        if (!((CodeGeneratorLexicalContext) this.f30lc).inDynamicScope()) {
            if ($assertionsDisabled || symbol.isGlobal() || ((CodeGeneratorLexicalContext) this.f30lc).getDefiningBlock(symbol).needsScope()) {
                return true;
            }
            throw new AssertionError(symbol.getName());
        }
        if (symbol.isGlobal()) {
            return false;
        }
        String name = symbol.getName();
        boolean previousWasBlock = false;
        Iterator<LexicalContextNode> it = ((CodeGeneratorLexicalContext) this.f30lc).getAllNodes();
        while (it.hasNext()) {
            LexicalContextNode node = it.next();
            if (node instanceof Block) {
                Block block = (Block) node;
                if (block.getExistingSymbol(name) == symbol) {
                    if ($assertionsDisabled || block.needsScope()) {
                        return true;
                    }
                    throw new AssertionError();
                }
                previousWasBlock = true;
            } else {
                if ((node instanceof WithNode) && previousWasBlock) {
                    return false;
                }
                if ((node instanceof FunctionNode) && ((FunctionNode) node).needsDynamicScope()) {
                    return false;
                }
                previousWasBlock = false;
            }
        }
        throw new AssertionError();
    }

    private MethodEmitter loadSharedScopeVar(Type valueType, Symbol symbol, int flags) {
        if (!$assertionsDisabled && !isFastScope(symbol)) {
            throw new AssertionError();
        }
        this.method.load(getScopeProtoDepth(((CodeGeneratorLexicalContext) this.f30lc).getCurrentBlock(), symbol));
        return ((CodeGeneratorLexicalContext) this.f30lc).getScopeGet(this.unit, symbol, valueType, flags).generateInvoke(this.method);
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$LoadScopeVar.class */
    private class LoadScopeVar extends OptimisticOperation {
        final IdentNode identNode;
        private final int flags;
        final CodeGenerator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        LoadScopeVar(CodeGenerator codeGenerator, IdentNode identNode, TypeBounds typeBounds, int i) {
            super(identNode, typeBounds);
            this.this$0 = codeGenerator;
            this.identNode = identNode;
            this.flags = i;
        }

        @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
        void loadStack() {
            this.this$0.method.loadCompilerConstant(CompilerConstants.SCOPE);
        }

        @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
        void consumeStack() {
            if (this.identNode.isCompileTimePropertyName()) {
                this.this$0.method.dynamicGet(Type.OBJECT, this.identNode.getSymbol().getName(), this.flags, this.identNode.isFunction(), false);
                replaceCompileTimeProperty();
            } else {
                dynamicGet(this.identNode.getSymbol().getName(), this.flags, this.identNode.isFunction(), false);
            }
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$LoadFastScopeVar.class */
    private class LoadFastScopeVar extends LoadScopeVar {
        final CodeGenerator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        LoadFastScopeVar(CodeGenerator codeGenerator, IdentNode identNode, TypeBounds typeBounds, int i) {
            super(codeGenerator, identNode, typeBounds, i);
            this.this$0 = codeGenerator;
        }

        void getProto() {
            this.this$0.loadFastScopeProto(this.identNode.getSymbol(), false);
        }
    }

    private MethodEmitter storeFastScopeVar(Symbol symbol, int flags) {
        loadFastScopeProto(symbol, true);
        this.method.dynamicSet(symbol.getName(), flags, false);
        return this.method;
    }

    private int getScopeProtoDepth(Block startingBlock, Symbol symbol) {
        int depth;
        FunctionNode fn = ((CodeGeneratorLexicalContext) this.f30lc).getCurrentFunction();
        int externalDepth = this.compiler.getScriptFunctionData(fn.getId()).getExternalSymbolDepth(symbol.getName());
        int internalDepth = FindScopeDepths.findInternalDepth(this.f30lc, fn, startingBlock, symbol);
        int scopesToStart = FindScopeDepths.findScopesToStart(this.f30lc, fn, startingBlock);
        if (internalDepth == -1) {
            depth = scopesToStart + externalDepth;
        } else {
            if (!$assertionsDisabled && internalDepth > scopesToStart) {
                throw new AssertionError();
            }
            depth = internalDepth;
        }
        return depth;
    }

    private void loadFastScopeProto(Symbol symbol, boolean swap) {
        int depth = getScopeProtoDepth(((CodeGeneratorLexicalContext) this.f30lc).getCurrentBlock(), symbol);
        if (!$assertionsDisabled && depth == -1) {
            throw new AssertionError("Couldn't find scope depth for symbol " + symbol.getName() + " in " + ((CodeGeneratorLexicalContext) this.f30lc).getCurrentFunction());
        }
        if (depth > 0) {
            if (swap) {
                this.method.swap();
            }
            for (int i = 0; i < depth; i++) {
                this.method.invoke(ScriptObject.GET_PROTO);
            }
            if (swap) {
                this.method.swap();
            }
        }
    }

    private MethodEmitter loadExpressionUnbounded(Expression expr) {
        return loadExpression(expr, TypeBounds.UNBOUNDED);
    }

    private MethodEmitter loadExpressionAsObject(Expression expr) {
        return loadExpression(expr, TypeBounds.OBJECT);
    }

    MethodEmitter loadExpressionAsBoolean(Expression expr) {
        return loadExpression(expr, TypeBounds.BOOLEAN);
    }

    private static boolean noToPrimitiveConversion(Type source, Type target) {
        return source.isJSPrimitive() || !target.isJSPrimitive() || target.isBoolean();
    }

    MethodEmitter loadBinaryOperands(BinaryNode binaryNode) {
        return loadBinaryOperands(binaryNode.lhs(), binaryNode.rhs(), TypeBounds.UNBOUNDED.notWiderThan(binaryNode.getWidestOperandType()), false, false);
    }

    private MethodEmitter loadBinaryOperands(Expression lhs, Expression rhs, TypeBounds explicitOperandBounds, boolean baseAlreadyOnStack, boolean forceConversionSeparation) {
        Type lhsType = undefinedToNumber(lhs.getType());
        Type rhsType = undefinedToNumber(rhs.getType());
        Type narrowestOperandType = Type.narrowest(Type.widest(lhsType, rhsType), explicitOperandBounds.widest);
        TypeBounds operandBounds = explicitOperandBounds.notNarrowerThan(narrowestOperandType);
        if (noToPrimitiveConversion(lhsType, explicitOperandBounds.widest) || rhs.isLocal()) {
            if (forceConversionSeparation) {
                TypeBounds safeConvertBounds = TypeBounds.UNBOUNDED.notNarrowerThan(narrowestOperandType);
                loadExpression(lhs, safeConvertBounds, baseAlreadyOnStack);
                this.method.convert(operandBounds.within(this.method.peekType()));
                loadExpression(rhs, safeConvertBounds, false);
                this.method.convert(operandBounds.within(this.method.peekType()));
            } else {
                loadExpression(lhs, operandBounds, baseAlreadyOnStack);
                loadExpression(rhs, operandBounds, false);
            }
        } else {
            TypeBounds safeConvertBounds2 = TypeBounds.UNBOUNDED.notNarrowerThan(narrowestOperandType);
            loadExpression(lhs, safeConvertBounds2, baseAlreadyOnStack);
            Type lhsLoadedType = this.method.peekType();
            loadExpression(rhs, safeConvertBounds2, false);
            Type convertedLhsType = operandBounds.within(this.method.peekType());
            if (convertedLhsType != lhsLoadedType) {
                this.method.swap().convert(convertedLhsType).swap();
            }
            this.method.convert(operandBounds.within(this.method.peekType()));
        }
        if (!$assertionsDisabled && Type.generic(this.method.peekType()) != operandBounds.narrowest) {
            throw new AssertionError();
        }
        if ($assertionsDisabled || Type.generic(this.method.peekType(1)) == operandBounds.narrowest) {
            return this.method;
        }
        throw new AssertionError();
    }

    MethodEmitter loadComparisonOperands(BinaryNode cmp) {
        Expression lhs = cmp.lhs();
        Expression rhs = cmp.rhs();
        Type lhsType = lhs.getType();
        Type rhsType = rhs.getType();
        if (!$assertionsDisabled && lhsType.isObject() && rhsType.isObject()) {
            throw new AssertionError();
        }
        if (lhsType.isObject() || rhsType.isObject()) {
            boolean canReorder = lhsType.isPrimitive() || rhs.isLocal();
            boolean canCombineLoadAndConvert = canReorder && cmp.isRelational();
            loadExpression(lhs, (!canCombineLoadAndConvert || lhs.isOptimistic()) ? TypeBounds.UNBOUNDED : TypeBounds.NUMBER);
            Type lhsLoadedType = this.method.peekType();
            TokenType tt = cmp.tokenType();
            if (canReorder) {
                emitObjectToNumberComparisonConversion(this.method, tt);
                loadExpression(rhs, (!canCombineLoadAndConvert || rhs.isOptimistic()) ? TypeBounds.UNBOUNDED : TypeBounds.NUMBER);
            } else {
                loadExpression(rhs, TypeBounds.UNBOUNDED);
                if (lhsLoadedType != Type.NUMBER) {
                    this.method.swap();
                    emitObjectToNumberComparisonConversion(this.method, tt);
                    this.method.swap();
                }
            }
            emitObjectToNumberComparisonConversion(this.method, tt);
            return this.method;
        }
        return loadBinaryOperands(cmp);
    }

    /* renamed from: jdk.nashorn.internal.codegen.CodeGenerator$33 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$33.class */
    static /* synthetic */ class C009433 {
        static final int[] $SwitchMap$jdk$nashorn$internal$parser$TokenType = new int[TokenType.values().length];

        static {
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f42EQ.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.f39NE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.EQ_STRICT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.NE_STRICT.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private static void emitObjectToNumberComparisonConversion(MethodEmitter method, TokenType tt) {
        switch (C009433.$SwitchMap$jdk$nashorn$internal$parser$TokenType[tt.ordinal()]) {
            case 1:
            case 2:
                if (method.peekType().isObject()) {
                    TO_NUMBER_FOR_EQ.invoke(method);
                    return;
                }
                break;
            case 3:
            case 4:
                if (method.peekType().isObject()) {
                    TO_NUMBER_FOR_STRICT_EQ.invoke(method);
                    return;
                }
                break;
        }
        method.convert(Type.NUMBER);
    }

    private static final Type undefinedToNumber(Type type) {
        return type == Type.UNDEFINED ? Type.NUMBER : type;
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$TypeBounds.class */
    private static final class TypeBounds {
        final Type narrowest;
        final Type widest;
        static final TypeBounds UNBOUNDED;
        static final TypeBounds INT;
        static final TypeBounds NUMBER;
        static final TypeBounds OBJECT;
        static final TypeBounds BOOLEAN;
        static final boolean $assertionsDisabled;

        static {
            $assertionsDisabled = !CodeGenerator.class.desiredAssertionStatus();
            UNBOUNDED = new TypeBounds(Type.UNKNOWN, Type.OBJECT);
            INT = exact(Type.INT);
            NUMBER = exact(Type.NUMBER);
            OBJECT = exact(Type.OBJECT);
            BOOLEAN = exact(Type.BOOLEAN);
        }

        static TypeBounds exact(Type type) {
            return new TypeBounds(type, type);
        }

        TypeBounds(Type type, Type type2) {
            if (!$assertionsDisabled && (type2 == null || type2 == Type.UNDEFINED || type2 == Type.UNKNOWN)) {
                throw new AssertionError(type2);
            }
            if (!$assertionsDisabled && (type == null || type == Type.UNDEFINED)) {
                throw new AssertionError(type);
            }
            if (!$assertionsDisabled && type.widerThan(type2)) {
                throw new AssertionError(type + " wider than " + type2);
            }
            if (!$assertionsDisabled && type2.narrowerThan(type)) {
                throw new AssertionError();
            }
            this.narrowest = Type.generic(type);
            this.widest = Type.generic(type2);
        }

        TypeBounds notNarrowerThan(Type type) {
            return maybeNew(Type.narrowest(Type.widest(this.narrowest, type), this.widest), this.widest);
        }

        TypeBounds notWiderThan(Type type) {
            return maybeNew(Type.narrowest(this.narrowest, type), Type.narrowest(this.widest, type));
        }

        boolean canBeNarrowerThan(Type type) {
            return this.narrowest.narrowerThan(type);
        }

        TypeBounds maybeNew(Type type, Type type2) {
            if (type == this.narrowest && type2 == this.widest) {
                return this;
            }
            return new TypeBounds(type, type2);
        }

        TypeBounds booleanToInt() {
            return maybeNew(CodeGenerator.booleanToInt(this.narrowest), CodeGenerator.booleanToInt(this.widest));
        }

        TypeBounds objectToNumber() {
            return maybeNew(CodeGenerator.objectToNumber(this.narrowest), CodeGenerator.objectToNumber(this.widest));
        }

        Type within(Type type) {
            if (type.narrowerThan(this.narrowest)) {
                return this.narrowest;
            }
            if (type.widerThan(this.widest)) {
                return this.widest;
            }
            return type;
        }

        public String toString() {
            return "[" + this.narrowest + ", " + this.widest + "]";
        }
    }

    private static Type booleanToInt(Type t) {
        return t == Type.BOOLEAN ? Type.INT : t;
    }

    private static Type objectToNumber(Type t) {
        return t.isObject() ? Type.NUMBER : t;
    }

    MethodEmitter loadExpressionAsType(Expression expr, Type type) {
        if (type == Type.BOOLEAN) {
            return loadExpressionAsBoolean(expr);
        }
        if (type == Type.UNDEFINED) {
            if ($assertionsDisabled || expr.getType() == Type.UNDEFINED) {
                return loadExpressionAsObject(expr);
            }
            throw new AssertionError();
        }
        return loadExpression(expr, TypeBounds.UNBOUNDED.notNarrowerThan(type)).convert(type);
    }

    private MethodEmitter loadExpression(Expression expr, TypeBounds resultBounds) {
        return loadExpression(expr, resultBounds, false);
    }

    private MethodEmitter loadExpression(Expression expr, TypeBounds resultBounds, boolean baseAlreadyOnStack) {
        boolean isCurrentDiscard = ((CodeGeneratorLexicalContext) this.f30lc).isCurrentDiscard(expr);
        expr.accept(new C00792(this, new LexicalContext(), resultBounds, baseAlreadyOnStack, this));
        if (!isCurrentDiscard) {
            coerceStackTop(resultBounds);
        }
        return this.method;
    }

    /* renamed from: jdk.nashorn.internal.codegen.CodeGenerator$2 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$2.class */
    class C00792 extends NodeOperatorVisitor {
        final TypeBounds val$resultBounds;
        final boolean val$baseAlreadyOnStack;
        final CodeGenerator val$codegen;
        final CodeGenerator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00792(CodeGenerator codeGenerator, LexicalContext lexicalContext, TypeBounds typeBounds, boolean z, CodeGenerator codeGenerator2) {
            super(lexicalContext);
            this.this$0 = codeGenerator;
            this.val$resultBounds = typeBounds;
            this.val$baseAlreadyOnStack = z;
            this.val$codegen = codeGenerator2;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
        public boolean enterIdentNode(IdentNode identNode) {
            this.this$0.loadIdent(identNode, this.val$resultBounds);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
        public boolean enterAccessNode(final AccessNode accessNode) {
            new OptimisticOperation(accessNode, this.val$resultBounds) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.2.1
                static final /* synthetic */ boolean $assertionsDisabled;

                {
                    CodeGenerator codeGenerator = C00792.this.this$0;
                }

                static {
                    $assertionsDisabled = !CodeGenerator.class.desiredAssertionStatus();
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void loadStack() {
                    if (!C00792.this.val$baseAlreadyOnStack) {
                        C00792.this.this$0.loadExpressionAsObject(accessNode.getBase());
                    }
                    if (!$assertionsDisabled && !C00792.this.this$0.method.peekType().isObject()) {
                        throw new AssertionError();
                    }
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void consumeStack() {
                    int flags = C00792.this.this$0.getCallSiteFlags();
                    dynamicGet(accessNode.getProperty(), flags, accessNode.isFunction(), accessNode.isIndex());
                }
            }.emit(this.val$baseAlreadyOnStack ? 1 : 0);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
        public boolean enterIndexNode(IndexNode indexNode) {
            new OptimisticOperation(this, indexNode, this.val$resultBounds, indexNode) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.2.2
                final IndexNode val$indexNode;
                final C00792 this$1;

                {
                    this.this$1 = this;
                    this.val$indexNode = indexNode;
                    CodeGenerator codeGenerator = this.this$0;
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void loadStack() {
                    if (!this.this$1.val$baseAlreadyOnStack) {
                        this.this$1.this$0.loadExpressionAsObject(this.val$indexNode.getBase());
                        this.this$1.this$0.loadExpressionUnbounded(this.val$indexNode.getIndex());
                    }
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void consumeStack() {
                    dynamicGetIndex(this.this$1.this$0.getCallSiteFlags(), this.val$indexNode.isFunction());
                }
            }.emit(this.val$baseAlreadyOnStack ? 2 : 0);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
        public boolean enterFunctionNode(FunctionNode functionNode) {
            this.f30lc.pop(functionNode);
            functionNode.accept(this.val$codegen);
            this.f30lc.push(functionNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterASSIGN(BinaryNode binaryNode) {
            this.this$0.checkAssignTarget(binaryNode.lhs());
            this.this$0.loadASSIGN(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterASSIGN_ADD(BinaryNode binaryNode) {
            this.this$0.checkAssignTarget(binaryNode.lhs());
            this.this$0.loadASSIGN_ADD(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterASSIGN_BIT_AND(BinaryNode binaryNode) {
            this.this$0.checkAssignTarget(binaryNode.lhs());
            this.this$0.loadASSIGN_BIT_AND(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterASSIGN_BIT_OR(BinaryNode binaryNode) {
            this.this$0.checkAssignTarget(binaryNode.lhs());
            this.this$0.loadASSIGN_BIT_OR(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterASSIGN_BIT_XOR(BinaryNode binaryNode) {
            this.this$0.checkAssignTarget(binaryNode.lhs());
            this.this$0.loadASSIGN_BIT_XOR(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterASSIGN_DIV(BinaryNode binaryNode) {
            this.this$0.checkAssignTarget(binaryNode.lhs());
            this.this$0.loadASSIGN_DIV(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterASSIGN_MOD(BinaryNode binaryNode) {
            this.this$0.checkAssignTarget(binaryNode.lhs());
            this.this$0.loadASSIGN_MOD(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterASSIGN_MUL(BinaryNode binaryNode) {
            this.this$0.checkAssignTarget(binaryNode.lhs());
            this.this$0.loadASSIGN_MUL(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterASSIGN_SAR(BinaryNode binaryNode) {
            this.this$0.checkAssignTarget(binaryNode.lhs());
            this.this$0.loadASSIGN_SAR(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterASSIGN_SHL(BinaryNode binaryNode) {
            this.this$0.checkAssignTarget(binaryNode.lhs());
            this.this$0.loadASSIGN_SHL(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterASSIGN_SHR(BinaryNode binaryNode) {
            this.this$0.checkAssignTarget(binaryNode.lhs());
            this.this$0.loadASSIGN_SHR(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterASSIGN_SUB(BinaryNode binaryNode) {
            this.this$0.checkAssignTarget(binaryNode.lhs());
            this.this$0.loadASSIGN_SUB(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
        public boolean enterCallNode(CallNode callNode) {
            return this.this$0.loadCallNode(callNode, this.val$resultBounds);
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
        public boolean enterLiteralNode(LiteralNode literalNode) {
            this.this$0.loadLiteral(literalNode, this.val$resultBounds);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
        public boolean enterTernaryNode(TernaryNode ternaryNode) {
            this.this$0.loadTernaryNode(ternaryNode, this.val$resultBounds);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterADD(BinaryNode binaryNode) {
            this.this$0.loadADD(binaryNode, this.val$resultBounds);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterSUB(UnaryNode unaryNode) {
            this.this$0.loadSUB(unaryNode, this.val$resultBounds);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterSUB(BinaryNode binaryNode) {
            this.this$0.loadSUB(binaryNode, this.val$resultBounds);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterMUL(BinaryNode binaryNode) {
            this.this$0.loadMUL(binaryNode, this.val$resultBounds);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterDIV(BinaryNode binaryNode) {
            this.this$0.loadDIV(binaryNode, this.val$resultBounds);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterMOD(BinaryNode binaryNode) {
            this.this$0.loadMOD(binaryNode, this.val$resultBounds);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterSAR(BinaryNode binaryNode) {
            this.this$0.loadSAR(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterSHL(BinaryNode binaryNode) {
            this.this$0.loadSHL(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterSHR(BinaryNode binaryNode) {
            this.this$0.loadSHR(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterCOMMALEFT(BinaryNode binaryNode) {
            this.this$0.loadCOMMALEFT(binaryNode, this.val$resultBounds);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterCOMMARIGHT(BinaryNode binaryNode) {
            this.this$0.loadCOMMARIGHT(binaryNode, this.val$resultBounds);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterAND(BinaryNode binaryNode) {
            this.this$0.loadAND_OR(binaryNode, this.val$resultBounds, true);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterOR(BinaryNode binaryNode) {
            this.this$0.loadAND_OR(binaryNode, this.val$resultBounds, false);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterNOT(UnaryNode unaryNode) {
            this.this$0.loadNOT(unaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterADD(UnaryNode unaryNode) {
            this.this$0.loadADD(unaryNode, this.val$resultBounds);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterBIT_NOT(UnaryNode unaryNode) {
            this.this$0.loadBIT_NOT(unaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterBIT_AND(BinaryNode binaryNode) {
            this.this$0.loadBIT_AND(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterBIT_OR(BinaryNode binaryNode) {
            this.this$0.loadBIT_OR(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterBIT_XOR(BinaryNode binaryNode) {
            this.this$0.loadBIT_XOR(binaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterVOID(UnaryNode unaryNode) {
            this.this$0.loadVOID(unaryNode, this.val$resultBounds);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterEQ(BinaryNode binaryNode) {
            this.this$0.loadCmp(binaryNode, Condition.EQ);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterEQ_STRICT(BinaryNode binaryNode) {
            this.this$0.loadCmp(binaryNode, Condition.EQ);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterGE(BinaryNode binaryNode) {
            this.this$0.loadCmp(binaryNode, Condition.GE);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterGT(BinaryNode binaryNode) {
            this.this$0.loadCmp(binaryNode, Condition.GT);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterLE(BinaryNode binaryNode) {
            this.this$0.loadCmp(binaryNode, Condition.LE);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterLT(BinaryNode binaryNode) {
            this.this$0.loadCmp(binaryNode, Condition.LT);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterNE(BinaryNode binaryNode) {
            this.this$0.loadCmp(binaryNode, Condition.NE);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterNE_STRICT(BinaryNode binaryNode) {
            this.this$0.loadCmp(binaryNode, Condition.NE);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
        public boolean enterObjectNode(ObjectNode objectNode) {
            this.this$0.loadObjectNode(objectNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
        public boolean enterRuntimeNode(RuntimeNode runtimeNode) {
            this.this$0.loadRuntimeNode(runtimeNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterNEW(UnaryNode unaryNode) {
            this.this$0.loadNEW(unaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
        public boolean enterDECINC(UnaryNode unaryNode) {
            this.this$0.checkAssignTarget(unaryNode.getExpression());
            this.this$0.loadDECINC(unaryNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
        public boolean enterJoinPredecessorExpression(JoinPredecessorExpression joinPredecessorExpression) {
            this.this$0.loadMaybeDiscard(joinPredecessorExpression, joinPredecessorExpression.getExpression(), this.val$resultBounds);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
        public boolean enterGetSplitState(GetSplitState getSplitState) {
            this.this$0.method.loadScope();
            this.this$0.method.invoke(Scope.GET_SPLIT_STATE);
            return false;
        }

        public boolean enterDefault(Node node) {
            throw new AssertionError(node.getClass().getName());
        }
    }

    private MethodEmitter coerceStackTop(TypeBounds typeBounds) {
        return this.method.convert(typeBounds.within(this.method.peekType()));
    }

    private void closeBlockVariables(Block block) {
        for (Symbol symbol : block.getSymbols()) {
            if (symbol.isBytecodeLocal()) {
                this.method.closeLocalVariable(symbol, block.getBreakLabel());
            }
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBlock(Block block) {
        Label entryLabel = block.getEntryLabel();
        if (entryLabel.isBreakTarget()) {
            if (!$assertionsDisabled && this.method.isReachable()) {
                throw new AssertionError();
            }
            this.method.breakLabel(entryLabel, ((CodeGeneratorLexicalContext) this.f30lc).getUsedSlotCount());
        } else {
            this.method.label(entryLabel);
        }
        if (!this.method.isReachable()) {
            return false;
        }
        if (((CodeGeneratorLexicalContext) this.f30lc).isFunctionBody() && this.emittedMethods.contains(((CodeGeneratorLexicalContext) this.f30lc).getCurrentFunction().getName())) {
            return false;
        }
        initLocals(block);
        if ($assertionsDisabled || ((CodeGeneratorLexicalContext) this.f30lc).getUsedSlotCount() == this.method.getFirstTemp()) {
            return true;
        }
        throw new AssertionError();
    }

    boolean useOptimisticTypes() {
        return !((CodeGeneratorLexicalContext) this.f30lc).inSplitNode() && this.compiler.useOptimisticTypes();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveBlock(Block block) {
        popBlockScope(block);
        this.method.beforeJoinPoint(block);
        closeBlockVariables(block);
        ((CodeGeneratorLexicalContext) this.f30lc).releaseSlots();
        if (!$assertionsDisabled && this.method.isReachable()) {
            if ((((CodeGeneratorLexicalContext) this.f30lc).isFunctionBody() ? 0 : ((CodeGeneratorLexicalContext) this.f30lc).getUsedSlotCount()) != this.method.getFirstTemp()) {
                throw new AssertionError("reachable=" + this.method.isReachable() + " isFunctionBody=" + ((CodeGeneratorLexicalContext) this.f30lc).isFunctionBody() + " usedSlotCount=" + ((CodeGeneratorLexicalContext) this.f30lc).getUsedSlotCount() + " firstTemp=" + this.method.getFirstTemp());
            }
        }
        return block;
    }

    private void popBlockScope(Block block) {
        Label breakLabel = block.getBreakLabel();
        if (!block.needsScope() || ((CodeGeneratorLexicalContext) this.f30lc).isFunctionBody()) {
            emitBlockBreakLabel(breakLabel);
            return;
        }
        Label beginTryLabel = this.scopeEntryLabels.pop();
        Label recoveryLabel = new Label("block_popscope_catch");
        emitBlockBreakLabel(breakLabel);
        boolean bodyCanThrow = breakLabel.isAfter(beginTryLabel);
        if (bodyCanThrow) {
            this.method._try(beginTryLabel, breakLabel, recoveryLabel);
        }
        Label afterCatchLabel = null;
        if (this.method.isReachable()) {
            popScope();
            if (bodyCanThrow) {
                afterCatchLabel = new Label("block_after_catch");
                this.method._goto(afterCatchLabel);
            }
        }
        if (bodyCanThrow) {
            if (!$assertionsDisabled && this.method.isReachable()) {
                throw new AssertionError();
            }
            this.method._catch(recoveryLabel);
            popScopeException();
            this.method.athrow();
        }
        if (afterCatchLabel != null) {
            this.method.label(afterCatchLabel);
        }
    }

    private void emitBlockBreakLabel(Label breakLabel) {
        LabelNode labelNode = ((CodeGeneratorLexicalContext) this.f30lc).getCurrentBlockLabelNode();
        if (labelNode != null) {
            if (!$assertionsDisabled && labelNode.getLocalVariableConversion() != null && !this.method.isReachable()) {
                throw new AssertionError();
            }
            this.method.beforeJoinPoint(labelNode);
            this.method.breakLabel(breakLabel, this.labeledBlockBreakLiveLocals.pop());
            return;
        }
        this.method.label(breakLabel);
    }

    private void popScope() {
        popScopes(1);
    }

    private void popScopeException() {
        Label catchLabel;
        popScope();
        ContinuationInfo ci = getContinuationInfo();
        if (ci != null && (catchLabel = ci.catchLabel) != METHOD_BOUNDARY && catchLabel == this.catchLabels.peek()) {
            ContinuationInfo.access$4804(ci);
        }
    }

    private void popScopesUntil(LexicalContextNode until) {
        popScopes(((CodeGeneratorLexicalContext) this.f30lc).getScopeNestingLevelTo(until));
    }

    private void popScopes(int count) {
        if (count == 0) {
            return;
        }
        if (!$assertionsDisabled && count <= 0) {
            throw new AssertionError();
        }
        if (!this.method.hasScope()) {
            return;
        }
        this.method.loadCompilerConstant(CompilerConstants.SCOPE);
        for (int i = 0; i < count; i++) {
            this.method.invoke(ScriptObject.GET_PROTO);
        }
        this.method.storeCompilerConstant(CompilerConstants.SCOPE);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBreakNode(BreakNode breakNode) {
        return enterJumpStatement(breakNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterJumpToInlinedFinally(JumpToInlinedFinally jumpToInlinedFinally) {
        return enterJumpStatement(jumpToInlinedFinally);
    }

    private boolean enterJumpStatement(JumpStatement jump) {
        if (!this.method.isReachable()) {
            return false;
        }
        enterStatement(jump);
        this.method.beforeJoinPoint(jump);
        popScopesUntil(jump.getPopScopeLimit(this.f30lc));
        Label targetLabel = jump.getTargetLabel(this.f30lc);
        targetLabel.markAsBreakTarget();
        this.method._goto(targetLabel);
        return false;
    }

    private int loadArgs(List<Expression> args) {
        int argCount = args.size();
        if (argCount > 250) {
            loadArgsArray(args);
            return 1;
        }
        for (Expression arg : args) {
            if (!$assertionsDisabled && arg == null) {
                throw new AssertionError();
            }
            loadExpressionUnbounded(arg);
        }
        return argCount;
    }

    private boolean loadCallNode(CallNode callNode, TypeBounds resultBounds) {
        lineNumber(callNode.getLineNumber());
        List<Expression> args = callNode.getArgs();
        Expression function = callNode.getFunction();
        Block currentBlock = ((CodeGeneratorLexicalContext) this.f30lc).getCurrentBlock();
        CodeGeneratorLexicalContext codegenLexicalContext = (CodeGeneratorLexicalContext) this.f30lc;
        function.accept(new C00903(this, callNode, resultBounds, currentBlock, args, codegenLexicalContext, function));
        return false;
    }

    /* renamed from: jdk.nashorn.internal.codegen.CodeGenerator$3 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$3.class */
    class C00903 extends SimpleNodeVisitor {
        static final boolean $assertionsDisabled;
        final CallNode val$callNode;
        final TypeBounds val$resultBounds;
        final Block val$currentBlock;
        final List val$args;
        final CodeGeneratorLexicalContext val$codegenLexicalContext;
        final Expression val$function;
        final CodeGenerator this$0;

        C00903(CodeGenerator codeGenerator, CallNode callNode, TypeBounds typeBounds, Block block, List list, CodeGeneratorLexicalContext codeGeneratorLexicalContext, Expression expression) {
            this.this$0 = codeGenerator;
            this.val$callNode = callNode;
            this.val$resultBounds = typeBounds;
            this.val$currentBlock = block;
            this.val$args = list;
            this.val$codegenLexicalContext = codeGeneratorLexicalContext;
            this.val$function = expression;
        }

        static {
            $assertionsDisabled = !CodeGenerator.class.desiredAssertionStatus();
        }

        private MethodEmitter sharedScopeCall(IdentNode identNode, int i) {
            Symbol symbol = identNode.getSymbol();
            new OptimisticOperation(this, this.val$callNode, this.val$resultBounds, this.this$0.isFastScope(symbol), symbol, identNode, i) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.3.1
                final boolean val$isFastScope;
                final Symbol val$symbol;
                final IdentNode val$identNode;
                final int val$flags;
                final C00903 this$1;

                {
                    this.this$1 = this;
                    this.val$isFastScope = z;
                    this.val$symbol = symbol;
                    this.val$identNode = identNode;
                    this.val$flags = i;
                    CodeGenerator codeGenerator = this.this$0;
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void loadStack() {
                    this.this$1.this$0.method.loadCompilerConstant(CompilerConstants.SCOPE);
                    if (this.val$isFastScope) {
                        this.this$1.this$0.method.load(this.this$1.this$0.getScopeProtoDepth(this.this$1.val$currentBlock, this.val$symbol));
                    } else {
                        this.this$1.this$0.method.load(-1);
                    }
                    this.this$1.this$0.loadArgs(this.this$1.val$args);
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void consumeStack() {
                    Type[] typesFromStack = this.this$1.this$0.method.getTypesFromStack(this.this$1.val$args.size());
                    for (int i2 = 0; i2 < typesFromStack.length; i2++) {
                        typesFromStack[i2] = Type.generic(typesFromStack[i2]);
                    }
                    this.this$1.val$codegenLexicalContext.getScopeCall(this.this$1.this$0.unit, this.val$symbol, this.val$identNode.getType(), this.this$1.val$resultBounds.widest, typesFromStack, this.val$flags).generateInvoke(this.this$1.this$0.method);
                }
            }.emit();
            return this.this$0.method;
        }

        private void scopeCall(IdentNode identNode, int i) {
            new OptimisticOperation(this, this.val$callNode, this.val$resultBounds, identNode, i) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.3.2
                int argsCount;
                final IdentNode val$ident;
                final int val$flags;
                final C00903 this$1;

                {
                    this.this$1 = this;
                    this.val$ident = identNode;
                    this.val$flags = i;
                    CodeGenerator codeGenerator = this.this$0;
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void loadStack() {
                    this.this$1.this$0.loadExpressionAsObject(this.val$ident);
                    this.this$1.this$0.method.loadUndefined(Type.OBJECT);
                    this.argsCount = this.this$1.this$0.loadArgs(this.this$1.val$args);
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void consumeStack() {
                    dynamicCall(2 + this.argsCount, this.val$flags, this.val$ident.getName());
                }
            }.emit();
        }

        private void evalCall(IdentNode identNode, int i) {
            Label label = new Label("invoke_direct_eval");
            Label label2 = new Label("is_not_eval");
            Label label3 = new Label("eval_done");
            new OptimisticOperation(this, this.val$callNode, this.val$resultBounds, identNode, label2, label, i, label3) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.3.3
                int argsCount;
                final IdentNode val$ident;
                final Label val$is_not_eval;
                final Label val$invoke_direct_eval;
                final int val$flags;
                final Label val$eval_done;
                final C00903 this$1;

                {
                    this.this$1 = this;
                    this.val$ident = identNode;
                    this.val$is_not_eval = label2;
                    this.val$invoke_direct_eval = label;
                    this.val$flags = i;
                    this.val$eval_done = label3;
                    CodeGenerator codeGenerator = this.this$0;
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void loadStack() {
                    this.this$1.this$0.loadExpressionAsObject(this.val$ident.setIsNotFunction());
                    this.this$1.this$0.globalIsEval();
                    this.this$1.this$0.method.ifeq(this.val$is_not_eval);
                    this.this$1.this$0.method.loadCompilerConstant(CompilerConstants.SCOPE);
                    List args = this.this$1.val$callNode.getEvalArgs().getArgs();
                    this.this$1.this$0.loadExpressionAsObject((Expression) args.get(0));
                    int size = args.size();
                    for (int i2 = 1; i2 < size; i2++) {
                        this.this$1.this$0.loadAndDiscard((Expression) args.get(i2));
                    }
                    this.this$1.this$0.method._goto(this.val$invoke_direct_eval);
                    this.this$1.this$0.method.label(this.val$is_not_eval);
                    this.this$1.this$0.loadExpressionAsObject(this.val$ident);
                    this.this$1.this$0.method.loadNull();
                    this.argsCount = this.this$1.this$0.loadArgs(this.this$1.val$callNode.getArgs());
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void consumeStack() {
                    dynamicCall(2 + this.argsCount, this.val$flags, "eval");
                    this.this$1.this$0.method._goto(this.val$eval_done);
                    this.this$1.this$0.method.label(this.val$invoke_direct_eval);
                    this.this$1.this$0.method.loadCompilerConstant(CompilerConstants.THIS);
                    this.this$1.this$0.method.load(this.this$1.val$callNode.getEvalArgs().getLocation());
                    this.this$1.this$0.method.load(((CodeGeneratorLexicalContext) this.this$1.this$0.f30lc).getCurrentFunction().isStrict());
                    this.this$1.this$0.globalDirectEval();
                    convertOptimisticReturnValue();
                    this.this$1.this$0.coerceStackTop(this.this$1.val$resultBounds);
                }
            }.emit();
            this.this$0.method.label(label3);
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
        public boolean enterIdentNode(IdentNode identNode) {
            Symbol symbol = identNode.getSymbol();
            if (symbol.isScope()) {
                int scopeCallSiteFlags = this.this$0.getScopeCallSiteFlags(symbol);
                int useCount = symbol.getUseCount();
                if (this.val$callNode.isEval()) {
                    evalCall(identNode, scopeCallSiteFlags);
                } else if (useCount <= 4 || ((!this.this$0.isFastScope(symbol) && useCount <= 500) || ((CodeGeneratorLexicalContext) this.this$0.f30lc).inDynamicScope() || this.val$callNode.isOptimistic())) {
                    scopeCall(identNode, scopeCallSiteFlags);
                } else {
                    sharedScopeCall(identNode, scopeCallSiteFlags);
                }
                if (!$assertionsDisabled && !this.this$0.method.peekType().equals(this.val$resultBounds.within(this.val$callNode.getType()))) {
                    throw new AssertionError(this.this$0.method.peekType() + " != " + this.val$resultBounds + "(" + this.val$callNode.getType() + ")");
                }
                return false;
            }
            enterDefault(identNode);
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
        public boolean enterAccessNode(AccessNode accessNode) {
            new OptimisticOperation(this, this.val$callNode, this.val$resultBounds, accessNode, this.this$0.getCallSiteFlags() | (this.val$callNode.isApplyToCall() ? 16 : 0)) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.3.4
                int argCount;
                static final boolean $assertionsDisabled;
                final AccessNode val$node;
                final int val$flags;
                final C00903 this$1;

                {
                    this.this$1 = this;
                    this.val$node = accessNode;
                    this.val$flags = i;
                    CodeGenerator codeGenerator = this.this$0;
                }

                static {
                    $assertionsDisabled = !CodeGenerator.class.desiredAssertionStatus();
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void loadStack() {
                    this.this$1.this$0.loadExpressionAsObject(this.val$node.getBase());
                    this.this$1.this$0.method.dup();
                    if (!$assertionsDisabled && this.val$node.isOptimistic()) {
                        throw new AssertionError();
                    }
                    this.this$1.this$0.method.dynamicGet(this.val$node.getType(), this.val$node.getProperty(), this.val$flags, true, this.val$node.isIndex());
                    this.this$1.this$0.method.swap();
                    this.argCount = this.this$1.this$0.loadArgs(this.this$1.val$args);
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void consumeStack() {
                    dynamicCall(2 + this.argCount, this.val$flags, this.val$node.toString(false));
                }
            }.emit();
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
        public boolean enterFunctionNode(FunctionNode functionNode) {
            new OptimisticOperation(this, this.val$callNode, this.val$resultBounds, functionNode) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.3.5
                FunctionNode callee;
                int argsCount;
                final FunctionNode val$origCallee;
                final C00903 this$1;

                {
                    this.this$1 = this;
                    this.val$origCallee = functionNode;
                    CodeGenerator codeGenerator = this.this$0;
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void loadStack() {
                    this.callee = (FunctionNode) this.val$origCallee.accept(this.this$1.this$0);
                    if (this.callee.isStrict()) {
                        this.this$1.this$0.method.loadUndefined(Type.OBJECT);
                    } else {
                        this.this$1.this$0.globalInstance();
                    }
                    this.argsCount = this.this$1.this$0.loadArgs(this.this$1.val$args);
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void consumeStack() {
                    dynamicCall(2 + this.argsCount, this.this$1.this$0.getCallSiteFlags(), null);
                }
            }.emit();
            return false;
        }

        @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
        public boolean enterIndexNode(IndexNode indexNode) {
            new OptimisticOperation(this, this.val$callNode, this.val$resultBounds, indexNode) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.3.6
                int argsCount;
                static final boolean $assertionsDisabled;
                final IndexNode val$node;
                final C00903 this$1;

                {
                    this.this$1 = this;
                    this.val$node = indexNode;
                    CodeGenerator codeGenerator = this.this$0;
                }

                static {
                    $assertionsDisabled = !CodeGenerator.class.desiredAssertionStatus();
                }

                /*  JADX ERROR: Types fix failed
                    java.lang.NullPointerException
                    */
                /* JADX WARN: Not initialized variable reg: 8, insn: 0x002c: MOVE (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r8 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) (LINE:506), block:B:2:0x0000 */
                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void loadStack() {
                    /*
                        r6 = this;
                        r0 = r6
                        jdk.nashorn.internal.codegen.CodeGenerator$3 r0 = r0.this$1
                        jdk.nashorn.internal.codegen.CodeGenerator r0 = r0.this$0
                        r1 = r6
                        jdk.nashorn.internal.ir.IndexNode r1 = r1.val$node
                        jdk.nashorn.internal.ir.Expression r1 = r1.getBase()
                        jdk.nashorn.internal.codegen.MethodEmitter r0 = jdk.nashorn.internal.codegen.CodeGenerator.access$600(r0, r1)
                        r0 = r6
                        jdk.nashorn.internal.codegen.CodeGenerator$3 r0 = r0.this$1
                        jdk.nashorn.internal.codegen.CodeGenerator r0 = r0.this$0
                        jdk.nashorn.internal.codegen.MethodEmitter r0 = jdk.nashorn.internal.codegen.CodeGenerator.access$000(r0)
                        jdk.nashorn.internal.codegen.MethodEmitter r0 = r0.dup()
                        r0 = r6
                        jdk.nashorn.internal.ir.IndexNode r0 = r0.val$node
                        jdk.nashorn.internal.ir.Expression r0 = r0.getIndex()
                        jdk.nashorn.internal.codegen.types.Type r0 = r0.getType()
                        r7 = r0
                        r0 = r7
                        r1 = r8
                        boolean r1 = r1 instanceof jdk.nashorn.internal.codegen.types.ObjectType
                        if (r1 != 0) goto L3a
                        r1 = r7
                        boolean r1 = r1.isBoolean()
                        if (r1 == 0) goto L4f
                    L3a:
                        r1 = r6
                        jdk.nashorn.internal.codegen.CodeGenerator$3 r1 = r1.this$1
                        jdk.nashorn.internal.codegen.CodeGenerator r1 = r1.this$0
                        r2 = r6
                        jdk.nashorn.internal.ir.IndexNode r2 = r2.val$node
                        jdk.nashorn.internal.ir.Expression r2 = r2.getIndex()
                        jdk.nashorn.internal.codegen.MethodEmitter r1 = jdk.nashorn.internal.codegen.CodeGenerator.access$600(r1, r2)
                        goto L61
                    L4f:
                        r1 = r6
                        jdk.nashorn.internal.codegen.CodeGenerator$3 r1 = r1.this$1
                        jdk.nashorn.internal.codegen.CodeGenerator r1 = r1.this$0
                        r2 = r6
                        jdk.nashorn.internal.ir.IndexNode r2 = r2.val$node
                        jdk.nashorn.internal.ir.Expression r2 = r2.getIndex()
                        jdk.nashorn.internal.codegen.MethodEmitter r1 = jdk.nashorn.internal.codegen.CodeGenerator.access$700(r1, r2)
                    L61:
                        boolean r1 = jdk.nashorn.internal.codegen.CodeGenerator.C00903.AnonymousClass6.$assertionsDisabled
                        if (r1 != 0) goto L79
                        r1 = r6
                        jdk.nashorn.internal.ir.IndexNode r1 = r1.val$node
                        boolean r1 = r1.isOptimistic()
                        if (r1 == 0) goto L79
                        java.lang.AssertionError r1 = new java.lang.AssertionError
                        r2 = r1
                        r2.<init>()
                        throw r1
                    L79:
                        r1 = r6
                        jdk.nashorn.internal.codegen.CodeGenerator$3 r1 = r1.this$1
                        jdk.nashorn.internal.codegen.CodeGenerator r1 = r1.this$0
                        jdk.nashorn.internal.codegen.MethodEmitter r1 = jdk.nashorn.internal.codegen.CodeGenerator.access$000(r1)
                        r2 = r6
                        jdk.nashorn.internal.ir.IndexNode r2 = r2.val$node
                        jdk.nashorn.internal.codegen.types.Type r2 = r2.getType()
                        r3 = r6
                        jdk.nashorn.internal.codegen.CodeGenerator$3 r3 = r3.this$1
                        jdk.nashorn.internal.codegen.CodeGenerator r3 = r3.this$0
                        int r3 = r3.getCallSiteFlags()
                        r4 = 1
                        jdk.nashorn.internal.codegen.MethodEmitter r1 = r1.dynamicGetIndex(r2, r3, r4)
                        r1 = r6
                        jdk.nashorn.internal.codegen.CodeGenerator$3 r1 = r1.this$1
                        jdk.nashorn.internal.codegen.CodeGenerator r1 = r1.this$0
                        jdk.nashorn.internal.codegen.MethodEmitter r1 = jdk.nashorn.internal.codegen.CodeGenerator.access$000(r1)
                        jdk.nashorn.internal.codegen.MethodEmitter r1 = r1.swap()
                        r1 = r6
                        r2 = r6
                        jdk.nashorn.internal.codegen.CodeGenerator$3 r2 = r2.this$1
                        jdk.nashorn.internal.codegen.CodeGenerator r2 = r2.this$0
                        r3 = r6
                        jdk.nashorn.internal.codegen.CodeGenerator$3 r3 = r3.this$1
                        java.util.List r3 = r3.val$args
                        int r2 = jdk.nashorn.internal.codegen.CodeGenerator.access$5100(r2, r3)
                        r1.argsCount = r2
                        return
                    */
                    throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.codegen.CodeGenerator.C00903.AnonymousClass6.loadStack():void");
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void consumeStack() {
                    dynamicCall(2 + this.argsCount, this.this$1.this$0.getCallSiteFlags(), this.val$node.toString(false));
                }
            }.emit();
            return false;
        }

        protected boolean enterDefault(Node node) {
            new OptimisticOperation(this, this.val$callNode, this.val$resultBounds, node) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.3.7
                int argsCount;
                final Node val$node;
                final C00903 this$1;

                {
                    this.this$1 = this;
                    this.val$node = node;
                    CodeGenerator codeGenerator = this.this$0;
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void loadStack() {
                    this.this$1.this$0.loadExpressionAsObject(this.this$1.val$function);
                    this.this$1.this$0.method.loadUndefined(Type.OBJECT);
                    this.argsCount = this.this$1.this$0.loadArgs(this.this$1.val$args);
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void consumeStack() {
                    dynamicCall(2 + this.argsCount, this.this$1.this$0.getCallSiteFlags() | 1, this.val$node.toString(false));
                }
            }.emit();
            return false;
        }
    }

    static int nonOptimisticFlags(int flags) {
        return flags & 2039;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterContinueNode(ContinueNode continueNode) {
        return enterJumpStatement(continueNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterEmptyNode(EmptyNode emptyNode) {
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterExpressionStatement(ExpressionStatement expressionStatement) {
        if (!this.method.isReachable()) {
            return false;
        }
        enterStatement(expressionStatement);
        loadAndDiscard(expressionStatement.getExpression());
        if ($assertionsDisabled || this.method.getStackSize() == 0) {
            return false;
        }
        throw new AssertionError("stack not empty in " + expressionStatement);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBlockStatement(BlockStatement blockStatement) {
        if (!this.method.isReachable()) {
            return false;
        }
        enterStatement(blockStatement);
        blockStatement.getBlock().accept(this);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterForNode(ForNode forNode) {
        if (!this.method.isReachable()) {
            return false;
        }
        enterStatement(forNode);
        if (forNode.isForIn()) {
            enterForIn(forNode);
            return false;
        }
        Expression init = forNode.getInit();
        if (init != null) {
            loadAndDiscard(init);
        }
        enterForOrWhile(forNode, forNode.getModify());
        return false;
    }

    private void enterForIn(ForNode forNode) {
        loadExpression(forNode.getModify(), TypeBounds.OBJECT);
        this.method.invoke(forNode.isForEach() ? ScriptRuntime.TO_VALUE_ITERATOR : ScriptRuntime.TO_PROPERTY_ITERATOR);
        Symbol iterSymbol = forNode.getIterator();
        int iterSlot = iterSymbol.getSlot(Type.OBJECT);
        this.method.store(iterSymbol, ITERATOR_TYPE);
        this.method.beforeJoinPoint(forNode);
        Label continueLabel = forNode.getContinueLabel();
        Label breakLabel = forNode.getBreakLabel();
        this.method.label(continueLabel);
        this.method.load(ITERATOR_TYPE, iterSlot);
        this.method.invoke(CompilerConstants.interfaceCallNoLookup(ITERATOR_CLASS, "hasNext", Boolean.TYPE, new Class[0]));
        JoinPredecessorExpression test = forNode.getTest();
        Block body = forNode.getBody();
        if (LocalVariableConversion.hasLiveConversion(test)) {
            Label afterConversion = new Label("for_in_after_test_conv");
            this.method.ifne(afterConversion);
            this.method.beforeJoinPoint(test);
            this.method._goto(breakLabel);
            this.method.label(afterConversion);
        } else {
            this.method.ifeq(breakLabel);
        }
        new C00954(this, forNode.getInit(), forNode, iterSlot).store();
        body.accept(this);
        if (this.method.isReachable()) {
            this.method._goto(continueLabel);
        }
        this.method.label(breakLabel);
    }

    /* renamed from: jdk.nashorn.internal.codegen.CodeGenerator$4 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$4.class */
    class C00954 extends Store {
        final ForNode val$forNode;
        final int val$iterSlot;
        final CodeGenerator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C00954(CodeGenerator codeGenerator, Expression expression, ForNode forNode, int i) {
            super(codeGenerator, expression);
            this.this$0 = codeGenerator;
            this.val$forNode = forNode;
            this.val$iterSlot = i;
        }

        @Override // jdk.nashorn.internal.codegen.CodeGenerator.Store
        protected void evaluate() {
            new OptimisticOperation(this, (Optimistic) this.val$forNode.getInit(), TypeBounds.UNBOUNDED) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.4.1
                final C00954 this$1;

                {
                    this.this$1 = this;
                    CodeGenerator codeGenerator = this.this$0;
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void loadStack() {
                    this.this$1.this$0.method.load(CodeGenerator.ITERATOR_TYPE, this.this$1.val$iterSlot);
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void consumeStack() {
                    this.this$1.this$0.method.invoke(CompilerConstants.interfaceCallNoLookup(Iterator.class, "next", Object.class, new Class[0]));
                    convertOptimisticReturnValue();
                }
            }.emit();
        }
    }

    private void initLocals(Block block) {
        Symbol paramSymbol;
        IdentNode nextParam;
        Type type;
        ((CodeGeneratorLexicalContext) this.f30lc).onEnterBlock(block);
        boolean isFunctionBody = ((CodeGeneratorLexicalContext) this.f30lc).isFunctionBody();
        FunctionNode function = ((CodeGeneratorLexicalContext) this.f30lc).getCurrentFunction();
        if (isFunctionBody) {
            initializeMethodParameters(function);
            if (!function.isVarArg()) {
                expandParameterSlots(function);
            }
            if (this.method.hasScope()) {
                if (function.needsParentScope()) {
                    this.method.loadCompilerConstant(CompilerConstants.CALLEE);
                    this.method.invoke(ScriptFunction.GET_SCOPE);
                } else {
                    if (!$assertionsDisabled && !function.hasScopeBlock()) {
                        throw new AssertionError();
                    }
                    this.method.loadNull();
                }
                this.method.storeCompilerConstant(CompilerConstants.SCOPE);
            }
            if (function.needsArguments()) {
                initArguments(function);
            }
        }
        if (block.needsScope()) {
            boolean varsInScope = function.allVarsInScope();
            boolean hasArguments = function.needsArguments();
            List<MapTuple<Symbol>> tuples = new ArrayList<>();
            Iterator<IdentNode> paramIter = function.getParameters().iterator();
            for (Symbol symbol : block.getSymbols()) {
                if (!symbol.isInternal() && !symbol.isThis()) {
                    if (symbol.isVar()) {
                        if (!$assertionsDisabled && varsInScope && !symbol.isScope()) {
                            throw new AssertionError();
                        }
                        if (varsInScope || symbol.isScope()) {
                            if (!$assertionsDisabled && !symbol.isScope()) {
                                throw new AssertionError("scope for " + symbol + " should have been set in Lower already " + function.getName());
                            }
                            if (!$assertionsDisabled && symbol.hasSlot()) {
                                throw new AssertionError("slot for " + symbol + " should have been removed in Lower already" + function.getName());
                            }
                            tuples.add(new MapTuple<>(symbol.getName(), symbol, null));
                        } else if (!$assertionsDisabled && !symbol.hasSlot() && symbol.slotCount() != 0) {
                            throw new AssertionError(symbol + " should have a slot only, no scope");
                        }
                    } else if (symbol.isParam() && (varsInScope || hasArguments || symbol.isScope())) {
                        if (!$assertionsDisabled && !symbol.isScope()) {
                            throw new AssertionError("scope for " + symbol + " should have been set in AssignSymbols already " + function.getName() + " varsInScope=" + varsInScope + " hasArguments=" + hasArguments + " symbol.isScope()=" + symbol.isScope());
                        }
                        if (!$assertionsDisabled && hasArguments && symbol.hasSlot()) {
                            throw new AssertionError("slot for " + symbol + " should have been removed in Lower already " + function.getName());
                        }
                        if (hasArguments) {
                            if (!$assertionsDisabled && symbol.hasSlot()) {
                                throw new AssertionError("slot for " + symbol + " should have been removed in Lower already ");
                            }
                            paramSymbol = null;
                            type = null;
                        } else {
                            paramSymbol = symbol;
                            do {
                                nextParam = paramIter.next();
                            } while (!nextParam.getName().equals(symbol.getName()));
                            type = nextParam.getType();
                        }
                        Type paramType = type;
                        tuples.add(new MapTuple(this, symbol.getName(), symbol, paramType, paramSymbol, paramType) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.5
                            final Type val$paramType;
                            final CodeGenerator this$0;

                            {
                                this.this$0 = this;
                                this.val$paramType = paramType;
                            }

                            @Override // jdk.nashorn.internal.codegen.MapTuple
                            public Class getValueType() {
                                if (!this.this$0.useDualFields() || this.value == 0 || this.val$paramType == null || this.val$paramType.isBoolean()) {
                                    return Object.class;
                                }
                                return this.val$paramType.getTypeClass();
                            }
                        });
                    }
                }
            }
            new FieldObjectCreator(this, this, tuples, true, hasArguments) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.6
                final CodeGenerator this$0;

                {
                    this.this$0 = this;
                }

                @Override // jdk.nashorn.internal.codegen.ObjectCreator
                protected void loadValue(Object obj, Type type2) {
                    loadValue((Symbol) obj, type2);
                }

                protected void loadValue(Symbol symbol2, Type type2) {
                    this.this$0.method.load(symbol2, type2);
                }
            }.makeObject(this.method);
            if (isFunctionBody && function.isProgram()) {
                this.method.invoke(ScriptRuntime.MERGE_SCOPE);
            }
            this.method.storeCompilerConstant(CompilerConstants.SCOPE);
            if (!isFunctionBody) {
                Label scopeEntryLabel = new Label("scope_entry");
                this.scopeEntryLabels.push(scopeEntryLabel);
                this.method.label(scopeEntryLabel);
            }
        } else if (isFunctionBody && function.isVarArg()) {
            int nextParam2 = 0;
            for (IdentNode param : function.getParameters()) {
                int i = nextParam2;
                nextParam2++;
                param.getSymbol().setFieldIndex(i);
            }
        }
        printSymbols(block, function, (isFunctionBody ? "Function " : "Block in ") + (function.getIdent() == null ? "<anonymous>" : function.getIdent().getName()));
    }

    private void initializeMethodParameters(FunctionNode function) {
        Label functionStart = new Label("fn_start");
        this.method.label(functionStart);
        int nextSlot = 0;
        if (function.needsCallee()) {
            nextSlot = 0 + 1;
            initializeInternalFunctionParameter(CompilerConstants.CALLEE, function, functionStart, 0);
        }
        int i = nextSlot;
        int nextSlot2 = nextSlot + 1;
        initializeInternalFunctionParameter(CompilerConstants.THIS, function, functionStart, i);
        if (function.isVarArg()) {
            int i2 = nextSlot2 + 1;
            initializeInternalFunctionParameter(CompilerConstants.VARARGS, function, functionStart, nextSlot2);
            return;
        }
        for (IdentNode param : function.getParameters()) {
            Symbol symbol = param.getSymbol();
            if (symbol.isBytecodeLocal()) {
                this.method.initializeMethodParameter(symbol, param.getType(), functionStart);
            }
        }
    }

    private void initializeInternalFunctionParameter(CompilerConstants cc, FunctionNode fn, Label functionStart, int slot) {
        Symbol symbol = initializeInternalFunctionOrSplitParameter(cc, fn, functionStart, slot);
        if (!$assertionsDisabled && symbol.getFirstSlot() != slot) {
            throw new AssertionError();
        }
    }

    private Symbol initializeInternalFunctionOrSplitParameter(CompilerConstants cc, FunctionNode fn, Label functionStart, int slot) {
        Symbol symbol = fn.getBody().getExistingSymbol(cc.symbolName());
        Type type = Type.typeFor((Class<?>) cc.type());
        this.method.initializeMethodParameter(symbol, type, functionStart);
        this.method.onLocalStore(type, slot);
        return symbol;
    }

    private void expandParameterSlots(FunctionNode function) {
        List<IdentNode> parameters = function.getParameters();
        int currentIncomingSlot = function.needsCallee() ? 2 : 1;
        Iterator<IdentNode> it = parameters.iterator();
        while (it.hasNext()) {
            currentIncomingSlot += it.next().getType().getSlots();
        }
        int i = parameters.size();
        while (true) {
            int i2 = i;
            i--;
            if (i2 > 0) {
                IdentNode parameter = parameters.get(i);
                Type parameterType = parameter.getType();
                int typeWidth = parameterType.getSlots();
                currentIncomingSlot -= typeWidth;
                Symbol symbol = parameter.getSymbol();
                int slotCount = symbol.slotCount();
                if (!$assertionsDisabled && slotCount <= 0) {
                    throw new AssertionError();
                }
                if (!$assertionsDisabled && !symbol.isBytecodeLocal() && slotCount != typeWidth) {
                    throw new AssertionError();
                }
                this.method.onLocalStore(parameterType, currentIncomingSlot);
                if (currentIncomingSlot != symbol.getSlot(parameterType)) {
                    this.method.load(parameterType, currentIncomingSlot);
                    this.method.store(symbol, parameterType);
                }
            } else {
                return;
            }
        }
    }

    private void initArguments(FunctionNode function) {
        this.method.loadCompilerConstant(CompilerConstants.VARARGS);
        if (function.needsCallee()) {
            this.method.loadCompilerConstant(CompilerConstants.CALLEE);
        } else {
            if (!$assertionsDisabled && !function.isStrict()) {
                throw new AssertionError();
            }
            this.method.loadNull();
        }
        this.method.load(function.getParameters().size());
        globalAllocateArguments();
        this.method.storeCompilerConstant(CompilerConstants.ARGUMENTS);
    }

    private boolean skipFunction(FunctionNode functionNode) {
        ScriptEnvironment env = this.compiler.getScriptEnvironment();
        boolean lazy = env._lazy_compilation;
        boolean onDemand = this.compiler.isOnDemandCompilation();
        if ((onDemand || lazy) && ((CodeGeneratorLexicalContext) this.f30lc).getOutermostFunction() != functionNode) {
            return true;
        }
        return !onDemand && lazy && env._optimistic_types && functionNode.isProgram();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterFunctionNode(FunctionNode functionNode) {
        if (skipFunction(functionNode)) {
            newFunctionObject(functionNode, false);
            return false;
        }
        String fnName = functionNode.getName();
        if (!this.emittedMethods.contains(fnName)) {
            this.log.info(new Object[]{"=== BEGIN ", fnName});
            if (!$assertionsDisabled && functionNode.getCompileUnit() == null) {
                throw new AssertionError("no compile unit for " + fnName + " " + Debug.m11id(functionNode));
            }
            this.unit = ((CodeGeneratorLexicalContext) this.f30lc).pushCompileUnit(functionNode.getCompileUnit());
            if (!$assertionsDisabled && !((CodeGeneratorLexicalContext) this.f30lc).hasCompileUnits()) {
                throw new AssertionError();
            }
            ClassEmitter classEmitter = this.unit.getClassEmitter();
            pushMethodEmitter(isRestOf() ? classEmitter.restOfMethod(functionNode) : classEmitter.method(functionNode));
            this.method.setPreventUndefinedLoad();
            if (useOptimisticTypes()) {
                ((CodeGeneratorLexicalContext) this.f30lc).pushUnwarrantedOptimismHandlers();
            }
            this.lastLineNumber = -1;
            this.method.begin();
            if (isRestOf()) {
                if (!$assertionsDisabled && this.continuationInfo != null) {
                    throw new AssertionError();
                }
                this.continuationInfo = new ContinuationInfo();
                this.method.gotoLoopStart(this.continuationInfo.getHandlerLabel());
                return true;
            }
            return true;
        }
        return true;
    }

    private void pushMethodEmitter(MethodEmitter newMethod) {
        this.method = ((CodeGeneratorLexicalContext) this.f30lc).pushMethodEmitter(newMethod);
        this.catchLabels.push(METHOD_BOUNDARY);
    }

    private void popMethodEmitter() {
        this.method = ((CodeGeneratorLexicalContext) this.f30lc).popMethodEmitter(this.method);
        if (!$assertionsDisabled && this.catchLabels.peek() != METHOD_BOUNDARY) {
            throw new AssertionError();
        }
        this.catchLabels.pop();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveFunctionNode(FunctionNode functionNode) {
        boolean markOptimistic;
        try {
            if (this.emittedMethods.add(functionNode.getName())) {
                markOptimistic = generateUnwarrantedOptimismExceptionHandlers(functionNode);
                generateContinuationHandler();
                this.method.end();
                this.unit = ((CodeGeneratorLexicalContext) this.f30lc).popCompileUnit(functionNode.getCompileUnit());
                popMethodEmitter();
                this.log.info(new Object[]{"=== END ", functionNode.getName()});
            } else {
                markOptimistic = false;
            }
            FunctionNode newFunctionNode = functionNode;
            if (markOptimistic) {
                newFunctionNode = newFunctionNode.setFlag(this.f30lc, 2048);
            }
            newFunctionObject(newFunctionNode, true);
            return newFunctionNode;
        } catch (Throwable t) {
            Context.printStackTrace(t);
            VerifyError e = new VerifyError("Code generation bug in \"" + functionNode.getName() + "\": likely stack misaligned: " + t + " " + functionNode.getSource().getName());
            e.initCause(t);
            throw e;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterIfNode(IfNode ifNode) {
        if (!this.method.isReachable()) {
            return false;
        }
        enterStatement(ifNode);
        Expression test = ifNode.getTest();
        Block pass = ifNode.getPass();
        Block fail = ifNode.getFail();
        if (Expression.isAlwaysTrue(test)) {
            loadAndDiscard(test);
            pass.accept(this);
            return false;
        }
        if (Expression.isAlwaysFalse(test)) {
            loadAndDiscard(test);
            if (fail != null) {
                fail.accept(this);
                return false;
            }
            return false;
        }
        boolean hasFailConversion = LocalVariableConversion.hasLiveConversion(ifNode);
        Label failLabel = new Label("if_fail");
        Label afterLabel = (fail != null || hasFailConversion) ? new Label("if_done") : null;
        emitBranch(test, failLabel, false);
        pass.accept(this);
        if (this.method.isReachable() && afterLabel != null) {
            this.method._goto(afterLabel);
        }
        this.method.label(failLabel);
        if (fail != null) {
            fail.accept(this);
        } else if (hasFailConversion) {
            this.method.beforeJoinPoint(ifNode);
        }
        if (afterLabel != null && afterLabel.isReachable()) {
            this.method.label(afterLabel);
            return false;
        }
        return false;
    }

    private void emitBranch(Expression test, Label label, boolean jumpWhenTrue) {
        new BranchOptimizer(this, this.method).execute(test, label, jumpWhenTrue);
    }

    private void enterStatement(Statement statement) {
        lineNumber(statement);
    }

    private void lineNumber(Statement statement) {
        lineNumber(statement.getLineNumber());
    }

    private void lineNumber(int lineNumber) {
        if (lineNumber != this.lastLineNumber && lineNumber != -1) {
            this.method.lineNumber(lineNumber);
            this.lastLineNumber = lineNumber;
        }
    }

    int getLastLineNumber() {
        return this.lastLineNumber;
    }

    private void loadArray(LiteralNode.ArrayLiteralNode arrayLiteralNode, ArrayType arrayType) {
        if (!$assertionsDisabled && arrayType != Type.INT_ARRAY && arrayType != Type.NUMBER_ARRAY && arrayType != Type.OBJECT_ARRAY) {
            throw new AssertionError();
        }
        Expression[] nodes = (Expression[]) arrayLiteralNode.getValue();
        Object presets = arrayLiteralNode.getPresets();
        int[] postsets = arrayLiteralNode.getPostsets();
        List<Splittable.SplitRange> ranges = arrayLiteralNode.getSplitRanges();
        loadConstant(presets);
        Type elementType = arrayType.getElementType();
        if (ranges != null) {
            loadSplitLiteral(new SplitLiteralCreator(this, nodes, elementType, postsets) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.7
                final Expression[] val$nodes;
                final Type val$elementType;
                final int[] val$postsets;
                final CodeGenerator this$0;

                {
                    this.this$0 = this;
                    this.val$nodes = nodes;
                    this.val$elementType = elementType;
                    this.val$postsets = postsets;
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.SplitLiteralCreator
                public void populateRange(MethodEmitter methodEmitter, Type type, int i, int i2, int i3) {
                    for (int i4 = i2; i4 < i3; i4++) {
                        methodEmitter.load(type, i);
                        this.this$0.storeElement(this.val$nodes, this.val$elementType, this.val$postsets[i4]);
                    }
                    methodEmitter.load(type, i);
                }
            }, ranges, arrayType);
            return;
        }
        if (postsets.length > 0) {
            int arraySlot = this.method.getUsedSlotsWithLiveTemporaries();
            this.method.storeTemp(arrayType, arraySlot);
            for (int postset : postsets) {
                this.method.load(arrayType, arraySlot);
                storeElement(nodes, elementType, postset);
            }
            this.method.load(arrayType, arraySlot);
        }
    }

    private void storeElement(Expression[] nodes, Type elementType, int index) {
        this.method.load(index);
        Expression element = nodes[index];
        if (element == null) {
            this.method.loadEmpty(elementType);
        } else {
            loadExpressionAsType(element, elementType);
        }
        this.method.arraystore();
    }

    private MethodEmitter loadArgsArray(List<Expression> args) {
        Object[] array = new Object[args.size()];
        loadConstant(array);
        for (int i = 0; i < args.size(); i++) {
            this.method.dup();
            this.method.load(i);
            loadExpression(args.get(i), TypeBounds.OBJECT);
            this.method.arraystore();
        }
        return this.method;
    }

    void loadConstant(String string) {
        String unitClassName = this.unit.getUnitClassName();
        ClassEmitter classEmitter = this.unit.getClassEmitter();
        int index = this.compiler.getConstantData().add(string);
        this.method.load(index);
        this.method.invokestatic(unitClassName, CompilerConstants.GET_STRING.symbolName(), CompilerConstants.methodDescriptor(String.class, new Class[]{Integer.TYPE}));
        classEmitter.needGetConstantMethod(String.class);
    }

    void loadConstant(Object object) {
        loadConstant(object, this.unit, this.method);
    }

    private void loadConstant(Object object, CompileUnit compileUnit, MethodEmitter methodEmitter) {
        String unitClassName = compileUnit.getUnitClassName();
        ClassEmitter classEmitter = compileUnit.getClassEmitter();
        int index = this.compiler.getConstantData().add(object);
        Class<?> cls = object.getClass();
        if (cls == PropertyMap.class) {
            methodEmitter.load(index);
            methodEmitter.invokestatic(unitClassName, CompilerConstants.GET_MAP.symbolName(), CompilerConstants.methodDescriptor(PropertyMap.class, new Class[]{Integer.TYPE}));
            classEmitter.needGetConstantMethod(PropertyMap.class);
        } else {
            if (cls.isArray()) {
                methodEmitter.load(index);
                String methodName = ClassEmitter.getArrayMethodName(cls);
                methodEmitter.invokestatic(unitClassName, methodName, CompilerConstants.methodDescriptor(cls, new Class[]{Integer.TYPE}));
                classEmitter.needGetConstantMethod(cls);
                return;
            }
            methodEmitter.loadConstants().load(index).arrayload();
            if (object instanceof ArrayData) {
                methodEmitter.checkcast(ArrayData.class);
                methodEmitter.invoke(CompilerConstants.virtualCallNoLookup(ArrayData.class, "copy", ArrayData.class, new Class[0]));
            } else if (cls != Object.class) {
                methodEmitter.checkcast(cls);
            }
        }
    }

    private void loadConstantsAndIndex(Object object, MethodEmitter methodEmitter) {
        methodEmitter.loadConstants().load(this.compiler.getConstantData().add(object));
    }

    private void loadLiteral(LiteralNode<?> node, TypeBounds resultBounds) {
        Object value = node.getValue();
        if (value == null) {
            this.method.loadNull();
            return;
        }
        if (value instanceof Undefined) {
            this.method.loadUndefined(resultBounds.within(Type.OBJECT));
            return;
        }
        if (value instanceof String) {
            String string = (String) value;
            if (string.length() > 10922) {
                loadConstant(string);
                return;
            } else {
                this.method.load(string);
                return;
            }
        }
        if (value instanceof Lexer.RegexToken) {
            loadRegex((Lexer.RegexToken) value);
            return;
        }
        if (value instanceof Boolean) {
            this.method.load(((Boolean) value).booleanValue());
            return;
        }
        if (value instanceof Integer) {
            if (!resultBounds.canBeNarrowerThan(Type.OBJECT)) {
                this.method.load(((Integer) value).intValue());
                this.method.convert(Type.OBJECT);
                return;
            } else if (!resultBounds.canBeNarrowerThan(Type.NUMBER)) {
                this.method.load(((Integer) value).doubleValue());
                return;
            } else {
                this.method.load(((Integer) value).intValue());
                return;
            }
        }
        if (value instanceof Double) {
            if (!resultBounds.canBeNarrowerThan(Type.OBJECT)) {
                this.method.load(((Double) value).doubleValue());
                this.method.convert(Type.OBJECT);
                return;
            } else {
                this.method.load(((Double) value).doubleValue());
                return;
            }
        }
        if (node instanceof LiteralNode.ArrayLiteralNode) {
            LiteralNode.ArrayLiteralNode arrayLiteral = (LiteralNode.ArrayLiteralNode) node;
            ArrayType atype = arrayLiteral.getArrayType();
            loadArray(arrayLiteral, atype);
            globalAllocateArray(atype);
            return;
        }
        throw new UnsupportedOperationException("Unknown literal for " + node.getClass() + " " + value.getClass() + " " + value);
    }

    private MethodEmitter loadRegexToken(Lexer.RegexToken value) {
        this.method.load(value.getExpression());
        this.method.load(value.getOptions());
        return globalNewRegExp();
    }

    private MethodEmitter loadRegex(Lexer.RegexToken regexToken) {
        if (this.regexFieldCount > 2048) {
            return loadRegexToken(regexToken);
        }
        String regexName = ((CodeGeneratorLexicalContext) this.f30lc).getCurrentFunction().uniqueName(CompilerConstants.REGEX_PREFIX.symbolName());
        ClassEmitter classEmitter = this.unit.getClassEmitter();
        classEmitter.field(EnumSet.of(ClassEmitter.Flag.PRIVATE, ClassEmitter.Flag.STATIC), regexName, Object.class);
        this.regexFieldCount++;
        this.method.getStatic(this.unit.getUnitClassName(), regexName, CompilerConstants.typeDescriptor(Object.class));
        this.method.dup();
        Label cachedLabel = new Label("cached");
        this.method.ifnonnull(cachedLabel);
        this.method.pop();
        loadRegexToken(regexToken);
        this.method.dup();
        this.method.putStatic(this.unit.getUnitClassName(), regexName, CompilerConstants.typeDescriptor(Object.class));
        this.method.label(cachedLabel);
        globalRegExpCopy();
        return this.method;
    }

    /* renamed from: jdk.nashorn.internal.codegen.CodeGenerator$8 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$8.class */
    static class C00998 implements Supplier {
        boolean contains;
        final Expression val$value;
        final int val$pp;

        C00998(Expression expression, int i) {
            this.val$value = expression;
            this.val$pp = i;
        }

        @Override // java.util.function.Supplier
        public Object get() {
            return get();
        }

        @Override // java.util.function.Supplier
        public Boolean get() {
            this.val$value.accept(new SimpleNodeVisitor(this) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.8.1
                final C00998 this$0;

                {
                    this.this$0 = this;
                }

                /* JADX WARN: Multi-variable type inference failed */
                public boolean enterDefault(Node node) {
                    if (this.this$0.contains) {
                        return false;
                    }
                    if ((node instanceof Optimistic) && ((Optimistic) node).getProgramPoint() == this.this$0.val$pp) {
                        this.this$0.contains = true;
                        return false;
                    }
                    return true;
                }
            });
            return Boolean.valueOf(this.contains);
        }
    }

    private static boolean propertyValueContains(Expression value, int pp) {
        return new C00998(value, pp).get().booleanValue();
    }

    private void loadObjectNode(ObjectNode objectNode) {
        ObjectCreator<?> oc;
        List<PropertyNode> elements = objectNode.getElements();
        List<MapTuple<Expression>> tuples = new ArrayList<>();
        List<PropertyNode> gettersSetters = new ArrayList<>();
        int ccp = getCurrentContinuationEntryPoint();
        List<Splittable.SplitRange> ranges = objectNode.getSplitRanges();
        Expression protoNode = null;
        boolean restOfProperty = false;
        for (PropertyNode propertyNode : elements) {
            Expression value = propertyNode.getValue();
            String key = propertyNode.getKeyName();
            Symbol symbol = value == null ? null : new Symbol(key, 0);
            if (value == null) {
                gettersSetters.add(propertyNode);
            } else if ((propertyNode.getKey() instanceof IdentNode) && key.equals(ScriptObject.PROTO_PROPERTY_NAME)) {
                protoNode = value;
            }
            restOfProperty |= value != null && UnwarrantedOptimismException.isValid(ccp) && propertyValueContains(value, ccp);
            Class<?> valueType = (!useDualFields() || value == null || value.getType().isBoolean()) ? Object.class : value.getType().getTypeClass();
            tuples.add(new MapTuple(this, key, symbol, Type.typeFor(valueType), value) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.9
                final CodeGenerator this$0;

                {
                    this.this$0 = this;
                }

                @Override // jdk.nashorn.internal.codegen.MapTuple
                public Class getValueType() {
                    return this.type.getTypeClass();
                }
            });
        }
        if (elements.size() > OBJECT_SPILL_THRESHOLD) {
            oc = new SpillObjectCreator(this, tuples);
        } else {
            oc = new FieldObjectCreator(this, this, tuples) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.10
                final CodeGenerator this$0;

                {
                    this.this$0 = this;
                }

                @Override // jdk.nashorn.internal.codegen.ObjectCreator
                protected void loadValue(Object obj, Type type) {
                    loadValue((Expression) obj, type);
                }

                protected void loadValue(Expression expression, Type type) {
                    this.this$0.loadExpressionAsType(expression, Type.generic(type));
                }
            };
        }
        if (ranges != null) {
            oc.createObject(this.method);
            loadSplitLiteral(oc, ranges, Type.typeFor((Class<?>) oc.getAllocatorClass()));
        } else {
            oc.makeObject(this.method);
        }
        if (restOfProperty) {
            ContinuationInfo ci = getContinuationInfo();
            ci.setObjectLiteralMap(this.method.getStackSize(), oc.getMap());
        }
        this.method.dup();
        if (protoNode != null) {
            loadExpressionAsObject(protoNode);
            this.method.convert(Type.OBJECT);
            this.method.invoke(ScriptObject.SET_PROTO_FROM_LITERAL);
        } else {
            this.method.invoke(ScriptObject.SET_GLOBAL_OBJECT_PROTO);
        }
        for (PropertyNode propertyNode2 : gettersSetters) {
            FunctionNode getter = propertyNode2.getGetter();
            FunctionNode setter = propertyNode2.getSetter();
            if (!$assertionsDisabled && getter == null && setter == null) {
                throw new AssertionError();
            }
            this.method.dup().loadKey(propertyNode2.getKey());
            if (getter == null) {
                this.method.loadNull();
            } else {
                getter.accept(this);
            }
            if (setter == null) {
                this.method.loadNull();
            } else {
                setter.accept(this);
            }
            this.method.invoke(ScriptObject.SET_USER_ACCESSORS);
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterReturnNode(ReturnNode returnNode) {
        if (!this.method.isReachable()) {
            return false;
        }
        enterStatement(returnNode);
        Type returnType = ((CodeGeneratorLexicalContext) this.f30lc).getCurrentFunction().getReturnType();
        Expression expression = returnNode.getExpression();
        if (expression != null) {
            loadExpressionUnbounded(expression);
        } else {
            this.method.loadUndefined(returnType);
        }
        this.method._return(returnType);
        return false;
    }

    private boolean undefinedCheck(RuntimeNode runtimeNode, List<Expression> args) {
        Symbol undefinedSymbol;
        RuntimeNode.Request request = runtimeNode.getRequest();
        if (!RuntimeNode.Request.isUndefinedCheck(request)) {
            return false;
        }
        Expression lhs = args.get(0);
        Expression rhs = args.get(1);
        Symbol lhsSymbol = lhs instanceof IdentNode ? ((IdentNode) lhs).getSymbol() : null;
        Symbol rhsSymbol = rhs instanceof IdentNode ? ((IdentNode) rhs).getSymbol() : null;
        if (!$assertionsDisabled && lhsSymbol == null && rhsSymbol == null) {
            throw new AssertionError();
        }
        if (isUndefinedSymbol(lhsSymbol)) {
            undefinedSymbol = lhsSymbol;
        } else {
            if (!$assertionsDisabled && !isUndefinedSymbol(rhsSymbol)) {
                throw new AssertionError();
            }
            undefinedSymbol = rhsSymbol;
        }
        if (!$assertionsDisabled && undefinedSymbol == null) {
            throw new AssertionError();
        }
        if (!undefinedSymbol.isScope()) {
            return false;
        }
        if ((lhsSymbol == undefinedSymbol && lhs.getType().isPrimitive()) || isDeoptimizedExpression(lhs) || !this.compiler.isGlobalSymbol(((CodeGeneratorLexicalContext) this.f30lc).getCurrentFunction(), "undefined")) {
            return false;
        }
        boolean isUndefinedCheck = request == RuntimeNode.Request.IS_UNDEFINED;
        Expression expr = undefinedSymbol == lhsSymbol ? rhs : lhs;
        if (expr.getType().isPrimitive()) {
            loadAndDiscard(expr);
            this.method.load(!isUndefinedCheck);
            return true;
        }
        Label checkTrue = new Label("ud_check_true");
        Label end = new Label(AsmConstants.END);
        loadExpressionAsObject(expr);
        this.method.loadUndefined(Type.OBJECT);
        this.method.if_acmpeq(checkTrue);
        this.method.load(!isUndefinedCheck);
        this.method._goto(end);
        this.method.label(checkTrue);
        this.method.load(isUndefinedCheck);
        this.method.label(end);
        return true;
    }

    private static boolean isUndefinedSymbol(Symbol symbol) {
        return symbol != null && "undefined".equals(symbol.getName());
    }

    private static boolean isNullLiteral(Node node) {
        return (node instanceof LiteralNode) && ((LiteralNode) node).isNull();
    }

    private boolean nullCheck(RuntimeNode runtimeNode, List<Expression> args) {
        Label popLabel;
        RuntimeNode.Request request = runtimeNode.getRequest();
        if (!RuntimeNode.Request.isEQ(request) && !RuntimeNode.Request.isNE(request)) {
            return false;
        }
        if (!$assertionsDisabled && args.size() != 2) {
            throw new AssertionError("EQ or NE or TYPEOF need two args");
        }
        Expression lhs = args.get(0);
        Expression rhs = args.get(1);
        if (isNullLiteral(lhs)) {
            lhs = rhs;
            rhs = lhs;
        }
        if (!isNullLiteral(rhs) || !lhs.getType().isObject() || isDeoptimizedExpression(lhs)) {
            return false;
        }
        Label trueLabel = new Label("trueLabel");
        Label falseLabel = new Label("falseLabel");
        Label endLabel = new Label(AsmConstants.END);
        loadExpressionUnbounded(lhs);
        if (!RuntimeNode.Request.isStrict(request)) {
            this.method.dup();
            popLabel = new Label("pop");
        } else {
            popLabel = null;
        }
        if (RuntimeNode.Request.isEQ(request)) {
            this.method.ifnull(!RuntimeNode.Request.isStrict(request) ? popLabel : trueLabel);
            if (!RuntimeNode.Request.isStrict(request)) {
                this.method.loadUndefined(Type.OBJECT);
                this.method.if_acmpeq(trueLabel);
            }
            this.method.label(falseLabel);
            this.method.load(false);
            this.method._goto(endLabel);
            if (!RuntimeNode.Request.isStrict(request)) {
                this.method.label(popLabel);
                this.method.pop();
            }
            this.method.label(trueLabel);
            this.method.load(true);
            this.method.label(endLabel);
        } else if (RuntimeNode.Request.isNE(request)) {
            this.method.ifnull(!RuntimeNode.Request.isStrict(request) ? popLabel : falseLabel);
            if (!RuntimeNode.Request.isStrict(request)) {
                this.method.loadUndefined(Type.OBJECT);
                this.method.if_acmpeq(falseLabel);
            }
            this.method.label(trueLabel);
            this.method.load(true);
            this.method._goto(endLabel);
            if (!RuntimeNode.Request.isStrict(request)) {
                this.method.label(popLabel);
                this.method.pop();
            }
            this.method.label(falseLabel);
            this.method.load(false);
            this.method.label(endLabel);
        }
        if (!$assertionsDisabled && !runtimeNode.getType().isBoolean()) {
            throw new AssertionError();
        }
        this.method.convert(runtimeNode.getType());
        return true;
    }

    private boolean isDeoptimizedExpression(Expression rootExpr) {
        if (!isRestOf()) {
            return false;
        }
        return new C007011(this, rootExpr).get().booleanValue();
    }

    /* renamed from: jdk.nashorn.internal.codegen.CodeGenerator$11 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$11.class */
    class C007011 implements Supplier {
        boolean contains;
        final Expression val$rootExpr;
        final CodeGenerator this$0;

        C007011(CodeGenerator codeGenerator, Expression expression) {
            this.this$0 = codeGenerator;
            this.val$rootExpr = expression;
        }

        @Override // java.util.function.Supplier
        public Object get() {
            return get();
        }

        @Override // java.util.function.Supplier
        public Boolean get() {
            this.val$rootExpr.accept(new SimpleNodeVisitor(this) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.11.1
                final C007011 this$1;

                {
                    this.this$1 = this;
                }

                /* JADX WARN: Multi-variable type inference failed */
                public boolean enterDefault(Node node) {
                    if (!this.this$1.contains && (node instanceof Optimistic)) {
                        int programPoint = ((Optimistic) node).getProgramPoint();
                        this.this$1.contains = UnwarrantedOptimismException.isValid(programPoint) && this.this$1.this$0.isContinuationEntryPoint(programPoint);
                    }
                    return !this.this$1.contains;
                }
            });
            return Boolean.valueOf(this.contains);
        }
    }

    private void loadRuntimeNode(RuntimeNode runtimeNode) {
        RuntimeNode newRuntimeNode;
        List<Expression> args = new ArrayList<>(runtimeNode.getArgs());
        if (nullCheck(runtimeNode, args) || undefinedCheck(runtimeNode, args)) {
            return;
        }
        RuntimeNode.Request request = runtimeNode.getRequest();
        if (RuntimeNode.Request.isUndefinedCheck(request)) {
            newRuntimeNode = runtimeNode.setRequest(request == RuntimeNode.Request.IS_UNDEFINED ? RuntimeNode.Request.EQ_STRICT : RuntimeNode.Request.NE_STRICT);
        } else {
            newRuntimeNode = runtimeNode;
        }
        for (Expression arg : args) {
            loadExpression(arg, TypeBounds.OBJECT);
        }
        this.method.invokestatic(CompilerConstants.className(ScriptRuntime.class), newRuntimeNode.getRequest().toString(), new FunctionSignature(false, false, newRuntimeNode.getType(), args.size()).toString());
        this.method.convert(newRuntimeNode.getType());
    }

    private void defineCommonSplitMethodParameters() {
        defineSplitMethodParameter(0, CompilerConstants.CALLEE);
        defineSplitMethodParameter(1, CompilerConstants.THIS);
        defineSplitMethodParameter(2, CompilerConstants.SCOPE);
    }

    private void defineSplitMethodParameter(int slot, CompilerConstants cc) {
        defineSplitMethodParameter(slot, Type.typeFor((Class<?>) cc.type()));
    }

    private void defineSplitMethodParameter(int slot, Type type) {
        this.method.defineBlockLocalVariable(slot, slot + type.getSlots());
        this.method.onLocalStore(type, slot);
    }

    private void loadSplitLiteral(SplitLiteralCreator creator, List<Splittable.SplitRange> ranges, Type literalType) {
        if (!$assertionsDisabled && ranges == null) {
            throw new AssertionError();
        }
        MethodEmitter savedMethod = this.method;
        FunctionNode currentFunction = ((CodeGeneratorLexicalContext) this.f30lc).getCurrentFunction();
        for (Splittable.SplitRange splitRange : ranges) {
            this.unit = ((CodeGeneratorLexicalContext) this.f30lc).pushCompileUnit(splitRange.getCompileUnit());
            if (!$assertionsDisabled && this.unit == null) {
                throw new AssertionError();
            }
            String className = this.unit.getUnitClassName();
            String name = currentFunction.uniqueName(CompilerConstants.SPLIT_PREFIX.symbolName());
            Class<?> clazz = literalType.getTypeClass();
            String signature = CompilerConstants.methodDescriptor(clazz, new Class[]{ScriptFunction.class, Object.class, ScriptObject.class, clazz});
            pushMethodEmitter(this.unit.getClassEmitter().method(EnumSet.of(ClassEmitter.Flag.PUBLIC, ClassEmitter.Flag.STATIC), name, signature));
            this.method.setFunctionNode(currentFunction);
            this.method.begin();
            defineCommonSplitMethodParameters();
            defineSplitMethodParameter(CompilerConstants.SPLIT_ARRAY_ARG.slot(), literalType);
            int literalSlot = fixScopeSlot(currentFunction, 3);
            ((CodeGeneratorLexicalContext) this.f30lc).enterSplitNode();
            creator.populateRange(this.method, literalType, literalSlot, splitRange.getLow(), splitRange.getHigh());
            this.method._return();
            ((CodeGeneratorLexicalContext) this.f30lc).exitSplitNode();
            this.method.end();
            ((CodeGeneratorLexicalContext) this.f30lc).releaseSlots();
            popMethodEmitter();
            if (!$assertionsDisabled && this.method != savedMethod) {
                throw new AssertionError();
            }
            this.method.loadCompilerConstant(CompilerConstants.CALLEE).swap();
            this.method.loadCompilerConstant(CompilerConstants.THIS).swap();
            this.method.loadCompilerConstant(CompilerConstants.SCOPE).swap();
            this.method.invokestatic(className, name, signature);
            this.unit = ((CodeGeneratorLexicalContext) this.f30lc).popCompileUnit(this.unit);
        }
    }

    private int fixScopeSlot(FunctionNode functionNode, int extraSlot) {
        int actualScopeSlot = functionNode.compilerConstant(CompilerConstants.SCOPE).getSlot(SCOPE_TYPE);
        int defaultScopeSlot = CompilerConstants.SCOPE.slot();
        int newExtraSlot = extraSlot;
        if (actualScopeSlot != defaultScopeSlot) {
            if (actualScopeSlot == extraSlot) {
                newExtraSlot = extraSlot + 1;
                this.method.defineBlockLocalVariable(newExtraSlot, newExtraSlot + 1);
                this.method.load(Type.OBJECT, extraSlot);
                this.method.storeHidden(Type.OBJECT, newExtraSlot);
            } else {
                this.method.defineBlockLocalVariable(actualScopeSlot, actualScopeSlot + 1);
            }
            this.method.load(SCOPE_TYPE, defaultScopeSlot);
            this.method.storeCompilerConstant(CompilerConstants.SCOPE);
        }
        return newExtraSlot;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterSplitReturn(SplitReturn splitReturn) {
        if (this.method.isReachable()) {
            this.method.loadUndefined(((CodeGeneratorLexicalContext) this.f30lc).getCurrentFunction().getReturnType())._return();
            return false;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterSetSplitState(SetSplitState setSplitState) {
        if (this.method.isReachable()) {
            this.method.setSplitState(setSplitState.getState());
            return false;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterSwitchNode(SwitchNode switchNode) {
        Label fallThroughLabel;
        if (!this.method.isReachable()) {
            return false;
        }
        enterStatement(switchNode);
        Expression expression = switchNode.getExpression();
        List<CaseNode> cases = switchNode.getCases();
        if (cases.isEmpty()) {
            loadAndDiscard(expression);
            return false;
        }
        CaseNode defaultCase = switchNode.getDefaultCase();
        Label breakLabel = switchNode.getBreakLabel();
        int liveLocalsOnBreak = this.method.getUsedSlotsWithLiveTemporaries();
        if (defaultCase != null && cases.size() == 1) {
            if (!$assertionsDisabled && cases.get(0) != defaultCase) {
                throw new AssertionError();
            }
            loadAndDiscard(expression);
            defaultCase.getBody().accept(this);
            this.method.breakLabel(breakLabel, liveLocalsOnBreak);
            return false;
        }
        Label defaultLabel = defaultCase != null ? defaultCase.getEntry() : breakLabel;
        boolean hasSkipConversion = LocalVariableConversion.hasLiveConversion(switchNode);
        if (switchNode.isUniqueInteger()) {
            TreeMap<Integer, Label> tree = new TreeMap<>();
            for (CaseNode caseNode : cases) {
                Node test = caseNode.getTest();
                if (test != null) {
                    Integer value = (Integer) ((LiteralNode) test).getValue();
                    Label entry = caseNode.getEntry();
                    if (!tree.containsKey(value)) {
                        tree.put(value, entry);
                    }
                }
            }
            int size = tree.size();
            Integer[] values = (Integer[]) tree.keySet().toArray(new Integer[size]);
            Label[] labels = (Label[]) tree.values().toArray(new Label[size]);
            int lo = values[0].intValue();
            int hi = values[size - 1].intValue();
            long range = (hi - lo) + 1;
            int deflt = Integer.MIN_VALUE;
            for (Integer num : values) {
                int value2 = num.intValue();
                if (deflt == value2) {
                    deflt++;
                } else if (deflt < value2) {
                    break;
                }
            }
            loadExpressionUnbounded(expression);
            Type type = expression.getType();
            if (!type.isInteger()) {
                this.method.load(deflt);
                Class<?> exprClass = type.getTypeClass();
                MethodEmitter methodEmitter = this.method;
                Class cls = Integer.TYPE;
                Class[] clsArr = new Class[2];
                clsArr[0] = exprClass.isPrimitive() ? exprClass : Object.class;
                clsArr[1] = Integer.TYPE;
                methodEmitter.invoke(CompilerConstants.staticCallNoLookup(ScriptRuntime.class, "switchTagAsInt", cls, clsArr));
            }
            if (hasSkipConversion) {
                if (!$assertionsDisabled && defaultLabel != breakLabel) {
                    throw new AssertionError();
                }
                defaultLabel = new Label("switch_skip");
            }
            if (range + 1 <= size * 2 && range <= 2147483647L) {
                Label[] table = new Label[(int) range];
                Arrays.fill(table, defaultLabel);
                for (int i = 0; i < size; i++) {
                    table[values[i].intValue() - lo] = labels[i];
                }
                this.method.tableswitch(lo, hi, defaultLabel, table);
            } else {
                int[] ints = new int[size];
                for (int i2 = 0; i2 < size; i2++) {
                    ints[i2] = values[i2].intValue();
                }
                this.method.lookupswitch(defaultLabel, ints, labels);
            }
            if (hasSkipConversion) {
                this.method.label(defaultLabel);
                this.method.beforeJoinPoint(switchNode);
                this.method._goto(breakLabel);
            }
        } else {
            Symbol tagSymbol = switchNode.getTag();
            int tagSlot = tagSymbol.getSlot(Type.OBJECT);
            loadExpressionAsObject(expression);
            this.method.store(tagSymbol, Type.OBJECT);
            for (CaseNode caseNode2 : cases) {
                Expression test2 = caseNode2.getTest();
                if (test2 != null) {
                    this.method.load(Type.OBJECT, tagSlot);
                    loadExpressionAsObject(test2);
                    this.method.invoke(ScriptRuntime.EQ_STRICT);
                    this.method.ifne(caseNode2.getEntry());
                }
            }
            if (defaultCase != null) {
                this.method._goto(defaultLabel);
            } else {
                this.method.beforeJoinPoint(switchNode);
                this.method._goto(breakLabel);
            }
        }
        if (!$assertionsDisabled && this.method.isReachable()) {
            throw new AssertionError();
        }
        for (CaseNode caseNode3 : cases) {
            if (caseNode3.getLocalVariableConversion() != null && this.method.isReachable()) {
                fallThroughLabel = new Label("fallthrough");
                this.method._goto(fallThroughLabel);
            } else {
                fallThroughLabel = null;
            }
            this.method.label(caseNode3.getEntry());
            this.method.beforeJoinPoint(caseNode3);
            if (fallThroughLabel != null) {
                this.method.label(fallThroughLabel);
            }
            caseNode3.getBody().accept(this);
        }
        this.method.breakLabel(breakLabel, liveLocalsOnBreak);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterThrowNode(ThrowNode throwNode) {
        if (!this.method.isReachable()) {
            return false;
        }
        enterStatement(throwNode);
        if (throwNode.isSyntheticRethrow()) {
            this.method.beforeJoinPoint(throwNode);
            IdentNode exceptionExpr = (IdentNode) throwNode.getExpression();
            Symbol exceptionSymbol = exceptionExpr.getSymbol();
            this.method.load(exceptionSymbol, EXCEPTION_TYPE);
            this.method.checkcast(EXCEPTION_TYPE.getTypeClass());
            this.method.athrow();
            return false;
        }
        Source source = getCurrentSource();
        Expression expression = throwNode.getExpression();
        int position = throwNode.position();
        int line = throwNode.getLineNumber();
        int column = source.getColumn(position);
        loadExpressionAsObject(expression);
        this.method.load(source.getName());
        this.method.load(line);
        this.method.load(column);
        this.method.invoke(ECMAException.CREATE);
        this.method.beforeJoinPoint(throwNode);
        this.method.athrow();
        return false;
    }

    private Source getCurrentSource() {
        return ((CodeGeneratorLexicalContext) this.f30lc).getCurrentFunction().getSource();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterTryNode(TryNode tryNode) {
        Label nextCatch;
        if (!this.method.isReachable()) {
            return false;
        }
        enterStatement(tryNode);
        Block body = tryNode.getBody();
        List<Block> catchBlocks = tryNode.getCatchBlocks();
        Symbol vmException = tryNode.getException();
        Label entry = new Label("try");
        Label recovery = new Label("catch");
        Label exit = new Label("end_try");
        Label skip = new Label("skip");
        this.method.canThrow(recovery);
        this.method.beforeTry(tryNode, recovery);
        this.method.label(entry);
        this.catchLabels.push(recovery);
        try {
            body.accept(this);
            if (!$assertionsDisabled && this.catchLabels.peek() != recovery) {
                throw new AssertionError();
            }
            this.catchLabels.pop();
            this.method.label(exit);
            boolean bodyCanThrow = exit.isAfter(entry);
            if (!bodyCanThrow) {
                return false;
            }
            this.method._try(entry, exit, recovery, Throwable.class);
            if (this.method.isReachable()) {
                this.method._goto(skip);
            }
            for (Block inlinedFinally : tryNode.getInlinedFinallies()) {
                TryNode.getLabelledInlinedFinallyBlock(inlinedFinally).accept(this);
                if (!$assertionsDisabled && this.method.isReachable()) {
                    throw new AssertionError();
                }
            }
            this.method._catch(recovery);
            this.method.store(vmException, EXCEPTION_TYPE);
            int catchBlockCount = catchBlocks.size();
            Label afterCatch = new Label("after_catch");
            for (int i = 0; i < catchBlockCount; i++) {
                if (!$assertionsDisabled && !this.method.isReachable()) {
                    throw new AssertionError();
                }
                Block catchBlock = catchBlocks.get(i);
                ((CodeGeneratorLexicalContext) this.f30lc).push(catchBlock);
                enterBlock(catchBlock);
                CatchNode catchNode = (CatchNode) catchBlocks.get(i).getStatements().get(0);
                IdentNode exception = catchNode.getException();
                Expression exceptionCondition = catchNode.getExceptionCondition();
                Block catchBody = catchNode.getBody();
                new Store(this, exception, catchNode, vmException) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.12
                    final CatchNode val$catchNode;
                    final Symbol val$vmException;
                    final CodeGenerator this$0;

                    {
                        this.this$0 = this;
                        this.val$catchNode = catchNode;
                        this.val$vmException = vmException;
                    }

                    @Override // jdk.nashorn.internal.codegen.CodeGenerator.Store
                    protected void evaluate() {
                        if (this.val$catchNode.isSyntheticRethrow()) {
                            this.this$0.method.load(this.val$vmException, CodeGenerator.EXCEPTION_TYPE);
                            return;
                        }
                        Label label = new Label("no_ecma_exception");
                        this.this$0.method.load(this.val$vmException, CodeGenerator.EXCEPTION_TYPE).dup()._instanceof(ECMAException.class).ifeq(label);
                        this.this$0.method.checkcast(ECMAException.class);
                        this.this$0.method.getField(ECMAException.THROWN);
                        this.this$0.method.label(label);
                    }
                }.store();
                boolean isConditionalCatch = exceptionCondition != null;
                if (isConditionalCatch) {
                    loadExpressionAsBoolean(exceptionCondition);
                    nextCatch = new Label("next_catch");
                    nextCatch.markAsBreakTarget();
                    this.method.ifeq(nextCatch);
                } else {
                    nextCatch = null;
                }
                catchBody.accept(this);
                leaveBlock(catchBlock);
                ((CodeGeneratorLexicalContext) this.f30lc).pop(catchBlock);
                if (nextCatch != null) {
                    if (this.method.isReachable()) {
                        this.method._goto(afterCatch);
                    }
                    this.method.breakLabel(nextCatch, ((CodeGeneratorLexicalContext) this.f30lc).getUsedSlotCount());
                }
            }
            this.method.label(afterCatch);
            if (this.method.isReachable()) {
                this.method.markDeadLocalVariable(vmException);
            }
            this.method.label(skip);
            if ($assertionsDisabled || tryNode.getFinallyBody() == null) {
                return false;
            }
            throw new AssertionError();
        } catch (Throwable th) {
            if (!$assertionsDisabled && this.catchLabels.peek() != recovery) {
                throw new AssertionError();
            }
            this.catchLabels.pop();
            throw th;
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterVarNode(VarNode varNode) {
        if (!this.method.isReachable()) {
            return false;
        }
        Expression init = varNode.getInit();
        IdentNode identNode = varNode.getName();
        Symbol identSymbol = identNode.getSymbol();
        if (!$assertionsDisabled && identSymbol == null) {
            throw new AssertionError("variable node " + varNode + " requires a name with a symbol");
        }
        boolean needsScope = identSymbol.isScope();
        if (init == null) {
            if (needsScope && varNode.isBlockScoped()) {
                this.method.loadCompilerConstant(CompilerConstants.SCOPE);
                this.method.loadUndefined(Type.OBJECT);
                int flags = getScopeCallSiteFlags(identSymbol) | (varNode.isBlockScoped() ? 32 : 0);
                if (!$assertionsDisabled && !isFastScope(identSymbol)) {
                    throw new AssertionError();
                }
                storeFastScopeVar(identSymbol, flags);
                return false;
            }
            return false;
        }
        enterStatement(varNode);
        if (!$assertionsDisabled && this.method == null) {
            throw new AssertionError();
        }
        if (needsScope) {
            this.method.loadCompilerConstant(CompilerConstants.SCOPE);
        }
        if (needsScope) {
            loadExpressionUnbounded(init);
            int flags2 = getScopeCallSiteFlags(identSymbol) | (varNode.isBlockScoped() ? 32 : 0);
            if (isFastScope(identSymbol)) {
                storeFastScopeVar(identSymbol, flags2);
                return false;
            }
            this.method.dynamicSet(identNode.getName(), flags2, false);
            return false;
        }
        Type identType = identNode.getType();
        if (identType == Type.UNDEFINED) {
            if (!$assertionsDisabled && init.getType() != Type.UNDEFINED && identNode.getSymbol().slotCount() != 0) {
                throw new AssertionError();
            }
            loadAndDiscard(init);
            return false;
        }
        loadExpressionAsType(init, identType);
        storeIdentWithCatchConversion(identNode, identType);
        return false;
    }

    private void storeIdentWithCatchConversion(IdentNode identNode, Type type) {
        LocalVariableConversion conversion = identNode.getLocalVariableConversion();
        Symbol symbol = identNode.getSymbol();
        if (conversion != null && conversion.isLive()) {
            if (!$assertionsDisabled && symbol != conversion.getSymbol()) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && !symbol.isBytecodeLocal()) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && conversion.getNext() != null) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && conversion.getFrom() != type) {
                throw new AssertionError();
            }
            Label catchLabel = this.catchLabels.peek();
            if (!$assertionsDisabled && catchLabel == METHOD_BOUNDARY) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && !catchLabel.isReachable()) {
                throw new AssertionError();
            }
            Type joinType = conversion.getTo();
            Label.Stack catchStack = catchLabel.getStack();
            int joinSlot = symbol.getSlot(joinType);
            if (catchStack.getUsedSlotsWithLiveTemporaries() > joinSlot) {
                this.method.dup();
                this.method.convert(joinType);
                this.method.store(symbol, joinType);
                catchLabel.getStack().onLocalStore(joinType, joinSlot, true);
                this.method.canThrow(catchLabel);
                this.method.store(symbol, type, false);
                return;
            }
        }
        this.method.store(symbol, type, true);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterWhileNode(WhileNode whileNode) {
        if (!this.method.isReachable()) {
            return false;
        }
        if (whileNode.isDoWhile()) {
            enterDoWhile(whileNode);
            return false;
        }
        enterStatement(whileNode);
        enterForOrWhile(whileNode, null);
        return false;
    }

    private void enterForOrWhile(LoopNode loopNode, JoinPredecessorExpression modify) {
        int liveLocalsOnBreak = this.method.getUsedSlotsWithLiveTemporaries();
        JoinPredecessorExpression test = loopNode.getTest();
        if (Expression.isAlwaysFalse(test)) {
            loadAndDiscard(test);
            return;
        }
        this.method.beforeJoinPoint(loopNode);
        Label continueLabel = loopNode.getContinueLabel();
        Label repeatLabel = modify != null ? new Label("for_repeat") : continueLabel;
        this.method.label(repeatLabel);
        int liveLocalsOnContinue = this.method.getUsedSlotsWithLiveTemporaries();
        Block body = loopNode.getBody();
        Label breakLabel = loopNode.getBreakLabel();
        boolean testHasLiveConversion = test != null && LocalVariableConversion.hasLiveConversion(test);
        if (Expression.isAlwaysTrue(test)) {
            if (test != null) {
                loadAndDiscard(test);
                if (testHasLiveConversion) {
                    this.method.beforeJoinPoint(test);
                }
            }
        } else if (test != null) {
            if (testHasLiveConversion) {
                emitBranch(test.getExpression(), body.getEntryLabel(), true);
                this.method.beforeJoinPoint(test);
                this.method._goto(breakLabel);
            } else {
                emitBranch(test.getExpression(), breakLabel, false);
            }
        }
        body.accept(this);
        if (repeatLabel != continueLabel) {
            emitContinueLabel(continueLabel, liveLocalsOnContinue);
        }
        if (loopNode.hasPerIterationScope() && ((CodeGeneratorLexicalContext) this.f30lc).getCurrentBlock().needsScope()) {
            this.method.loadCompilerConstant(CompilerConstants.SCOPE);
            this.method.invoke(CompilerConstants.virtualCallNoLookup(ScriptObject.class, "copy", ScriptObject.class, new Class[0]));
            this.method.storeCompilerConstant(CompilerConstants.SCOPE);
        }
        if (this.method.isReachable()) {
            if (modify != null) {
                lineNumber(loopNode);
                loadAndDiscard(modify);
                this.method.beforeJoinPoint(modify);
            }
            this.method._goto(repeatLabel);
        }
        this.method.breakLabel(breakLabel, liveLocalsOnBreak);
    }

    private void emitContinueLabel(Label continueLabel, int liveLocals) {
        boolean reachable = this.method.isReachable();
        this.method.breakLabel(continueLabel, liveLocals);
        if (!reachable) {
            this.method.undefineLocalVariables(((CodeGeneratorLexicalContext) this.f30lc).getUsedSlotCount(), false);
        }
    }

    private void enterDoWhile(WhileNode whileNode) {
        int liveLocalsOnContinueOrBreak = this.method.getUsedSlotsWithLiveTemporaries();
        this.method.beforeJoinPoint(whileNode);
        Block body = whileNode.getBody();
        body.accept(this);
        emitContinueLabel(whileNode.getContinueLabel(), liveLocalsOnContinueOrBreak);
        if (this.method.isReachable()) {
            lineNumber(whileNode);
            JoinPredecessorExpression test = whileNode.getTest();
            Label bodyEntryLabel = body.getEntryLabel();
            boolean testHasLiveConversion = LocalVariableConversion.hasLiveConversion(test);
            if (Expression.isAlwaysFalse(test)) {
                loadAndDiscard(test);
                if (testHasLiveConversion) {
                    this.method.beforeJoinPoint(test);
                }
            } else if (testHasLiveConversion) {
                Label beforeExit = new Label("do_while_preexit");
                emitBranch(test.getExpression(), beforeExit, false);
                this.method.beforeJoinPoint(test);
                this.method._goto(bodyEntryLabel);
                this.method.label(beforeExit);
                this.method.beforeJoinPoint(test);
            } else {
                emitBranch(test.getExpression(), bodyEntryLabel, true);
            }
        }
        this.method.breakLabel(whileNode.getBreakLabel(), liveLocalsOnContinueOrBreak);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterWithNode(WithNode withNode) {
        Label tryLabel;
        if (!this.method.isReachable()) {
            return false;
        }
        enterStatement(withNode);
        Expression expression = withNode.getExpression();
        Block body = withNode.getBody();
        boolean hasScope = this.method.hasScope();
        if (hasScope) {
            this.method.loadCompilerConstant(CompilerConstants.SCOPE);
        }
        loadExpressionAsObject(expression);
        if (hasScope) {
            this.method.invoke(ScriptRuntime.OPEN_WITH);
            this.method.storeCompilerConstant(CompilerConstants.SCOPE);
            tryLabel = new Label("with_try");
            this.method.label(tryLabel);
        } else {
            globalCheckObjectCoercible();
            tryLabel = null;
        }
        body.accept(this);
        if (hasScope) {
            Label endLabel = new Label("with_end");
            Label catchLabel = new Label("with_catch");
            Label exitLabel = new Label("with_exit");
            this.method.label(endLabel);
            boolean bodyCanThrow = endLabel.isAfter(tryLabel);
            if (bodyCanThrow) {
                this.method._try(tryLabel, endLabel, catchLabel);
            }
            boolean reachable = this.method.isReachable();
            if (reachable) {
                popScope();
                if (bodyCanThrow) {
                    this.method._goto(exitLabel);
                }
            }
            if (bodyCanThrow) {
                this.method._catch(catchLabel);
                popScopeException();
                this.method.athrow();
                if (reachable) {
                    this.method.label(exitLabel);
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    private void loadADD(UnaryNode unaryNode, TypeBounds resultBounds) {
        loadExpression(unaryNode.getExpression(), resultBounds.booleanToInt().notWiderThan(Type.NUMBER));
        if (this.method.peekType() == Type.BOOLEAN) {
            this.method.convert(Type.INT);
        }
    }

    private void loadBIT_NOT(UnaryNode unaryNode) {
        loadExpression(unaryNode.getExpression(), TypeBounds.INT).load(-1).xor();
    }

    private void loadDECINC(UnaryNode unaryNode) {
        Expression operand = unaryNode.getExpression();
        Type type = unaryNode.getType();
        TypeBounds typeBounds = new TypeBounds(type, Type.NUMBER);
        TokenType tokenType = unaryNode.tokenType();
        boolean isPostfix = tokenType == TokenType.DECPOSTFIX || tokenType == TokenType.INCPOSTFIX;
        boolean isIncrement = tokenType == TokenType.INCPREFIX || tokenType == TokenType.INCPOSTFIX;
        if (!$assertionsDisabled && type.isObject()) {
            throw new AssertionError();
        }
        new C007213(this, unaryNode, operand, operand, typeBounds, isPostfix, unaryNode, type, isIncrement).store();
    }

    /* renamed from: jdk.nashorn.internal.codegen.CodeGenerator$13 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$13.class */
    class C007213 extends SelfModifyingStore {
        final Expression val$operand;
        final TypeBounds val$typeBounds;
        final boolean val$isPostfix;
        final UnaryNode val$unaryNode;
        final Type val$type;
        final boolean val$isIncrement;
        final CodeGenerator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C007213(CodeGenerator codeGenerator, UnaryNode unaryNode, Expression expression, Expression expression2, TypeBounds typeBounds, boolean z, UnaryNode unaryNode2, Type type, boolean z2) {
            super(codeGenerator, unaryNode, expression);
            this.this$0 = codeGenerator;
            this.val$operand = expression2;
            this.val$typeBounds = typeBounds;
            this.val$isPostfix = z;
            this.val$unaryNode = unaryNode2;
            this.val$type = type;
            this.val$isIncrement = z2;
        }

        private void loadRhs() {
            this.this$0.loadExpression(this.val$operand, this.val$typeBounds, true);
        }

        @Override // jdk.nashorn.internal.codegen.CodeGenerator.Store
        protected void evaluate() {
            if (!this.val$isPostfix) {
                new OptimisticOperation(this, this.val$unaryNode, this.val$typeBounds) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.13.1
                    final C007213 this$1;

                    {
                        this.this$1 = this;
                        CodeGenerator codeGenerator = this.this$0;
                    }

                    @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                    void loadStack() {
                        this.this$1.loadRhs();
                        this.this$1.loadMinusOne();
                    }

                    @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                    void consumeStack() {
                        this.this$1.doDecInc(getProgramPoint());
                    }
                }.emit(CodeGenerator.getOptimisticIgnoreCountForSelfModifyingExpression(this.val$operand));
            } else {
                loadRhs();
            }
        }

        @Override // jdk.nashorn.internal.codegen.CodeGenerator.Store
        protected void storeNonDiscard() {
            super.storeNonDiscard();
            if (this.val$isPostfix) {
                new OptimisticOperation(this, this.val$unaryNode, this.val$typeBounds) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.13.2
                    final C007213 this$1;

                    {
                        this.this$1 = this;
                        CodeGenerator codeGenerator = this.this$0;
                    }

                    @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                    void loadStack() {
                        this.this$1.loadMinusOne();
                    }

                    @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                    void consumeStack() {
                        this.this$1.doDecInc(getProgramPoint());
                    }
                }.emit(1);
            }
        }

        private void loadMinusOne() {
            if (this.val$type.isInteger()) {
                this.this$0.method.load(this.val$isIncrement ? 1 : -1);
            } else {
                this.this$0.method.load(this.val$isIncrement ? 1.0d : -1.0d);
            }
        }

        private void doDecInc(int i) {
            this.this$0.method.add(i);
        }
    }

    private static int getOptimisticIgnoreCountForSelfModifyingExpression(Expression target) {
        if (target instanceof AccessNode) {
            return 1;
        }
        return target instanceof IndexNode ? 2 : 0;
    }

    private void loadAndDiscard(Expression expr) {
        if ((expr instanceof LiteralNode.PrimitiveLiteralNode) | isLocalVariable(expr)) {
            if (!$assertionsDisabled && ((CodeGeneratorLexicalContext) this.f30lc).isCurrentDiscard(expr)) {
                throw new AssertionError();
            }
            return;
        }
        ((CodeGeneratorLexicalContext) this.f30lc).pushDiscard(expr);
        loadExpression(expr, TypeBounds.UNBOUNDED);
        if (((CodeGeneratorLexicalContext) this.f30lc).popDiscardIfCurrent(expr)) {
            if (!$assertionsDisabled && expr.isAssignment()) {
                throw new AssertionError();
            }
            this.method.pop();
        }
    }

    private void loadMaybeDiscard(Expression parent, Expression expr, TypeBounds resultBounds) {
        loadMaybeDiscard(((CodeGeneratorLexicalContext) this.f30lc).popDiscardIfCurrent(parent), expr, resultBounds);
    }

    private void loadMaybeDiscard(boolean discard, Expression expr, TypeBounds resultBounds) {
        if (discard) {
            loadAndDiscard(expr);
        } else {
            loadExpression(expr, resultBounds);
        }
    }

    private void loadNEW(UnaryNode unaryNode) {
        CallNode callNode = (CallNode) unaryNode.getExpression();
        List<Expression> args = callNode.getArgs();
        Expression func = callNode.getFunction();
        loadExpressionAsObject(func);
        this.method.dynamicNew(1 + loadArgs(args), getCallSiteFlags(), func.toString(false));
    }

    private void loadNOT(UnaryNode unaryNode) {
        Expression expr = unaryNode.getExpression();
        if ((expr instanceof UnaryNode) && expr.isTokenType(TokenType.NOT)) {
            loadExpressionAsBoolean(((UnaryNode) expr).getExpression());
            return;
        }
        Label trueLabel = new Label("true");
        Label afterLabel = new Label("after");
        emitBranch(expr, trueLabel, true);
        this.method.load(true);
        this.method._goto(afterLabel);
        this.method.label(trueLabel);
        this.method.load(false);
        this.method.label(afterLabel);
    }

    private void loadSUB(UnaryNode unaryNode, TypeBounds resultBounds) {
        Type type = unaryNode.getType();
        if (!$assertionsDisabled && !type.isNumeric()) {
            throw new AssertionError();
        }
        TypeBounds numericBounds = resultBounds.booleanToInt();
        new OptimisticOperation(this, unaryNode, numericBounds, unaryNode, numericBounds, type) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.14
            final UnaryNode val$unaryNode;
            final TypeBounds val$numericBounds;
            final Type val$type;
            final CodeGenerator this$0;

            {
                this.this$0 = this;
                this.val$unaryNode = unaryNode;
                this.val$numericBounds = numericBounds;
                this.val$type = type;
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
            void loadStack() {
                this.this$0.loadExpression(this.val$unaryNode.getExpression(), this.val$numericBounds.notWiderThan(Type.NUMBER));
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
            void consumeStack() {
                if (this.val$type.isNumber()) {
                    this.this$0.method.convert(this.val$type);
                }
                this.this$0.method.neg(getProgramPoint());
            }
        }.emit();
    }

    public void loadVOID(UnaryNode unaryNode, TypeBounds resultBounds) {
        loadAndDiscard(unaryNode.getExpression());
        if (!((CodeGeneratorLexicalContext) this.f30lc).popDiscardIfCurrent(unaryNode)) {
            this.method.loadUndefined(resultBounds.widest);
        }
    }

    public void loadADD(BinaryNode binaryNode, TypeBounds resultBounds) {
        new OptimisticOperation(this, binaryNode, resultBounds, binaryNode, resultBounds) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.15
            final BinaryNode val$binaryNode;
            final TypeBounds val$resultBounds;
            final CodeGenerator this$0;

            {
                this.this$0 = this;
                this.val$binaryNode = binaryNode;
                this.val$resultBounds = resultBounds;
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
            void loadStack() {
                TypeBounds typeBounds;
                boolean zNarrowerThan = false;
                if (UnwarrantedOptimismException.isValid(getProgramPoint())) {
                    typeBounds = new TypeBounds(this.val$binaryNode.getType(), Type.OBJECT);
                } else {
                    Type widestOperationType = this.val$binaryNode.getWidestOperationType();
                    typeBounds = new TypeBounds(Type.narrowest(this.val$binaryNode.getWidestOperandType(), this.val$resultBounds.widest), widestOperationType);
                    zNarrowerThan = widestOperationType.narrowerThan(this.val$resultBounds.widest);
                }
                this.this$0.loadBinaryOperands(this.val$binaryNode.lhs(), this.val$binaryNode.rhs(), typeBounds, false, zNarrowerThan);
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
            void consumeStack() {
                this.this$0.method.add(getProgramPoint());
            }
        }.emit();
    }

    private void loadAND_OR(BinaryNode binaryNode, TypeBounds resultBounds, boolean isAnd) {
        Type narrowestOperandType = Type.widestReturnType(binaryNode.lhs().getType(), binaryNode.rhs().getType());
        boolean isCurrentDiscard = ((CodeGeneratorLexicalContext) this.f30lc).popDiscardIfCurrent(binaryNode);
        Label skip = new Label("skip");
        if (narrowestOperandType == Type.BOOLEAN) {
            Label onTrue = new Label("andor_true");
            emitBranch(binaryNode, onTrue, true);
            if (isCurrentDiscard) {
                this.method.label(onTrue);
                return;
            }
            this.method.load(false);
            this.method._goto(skip);
            this.method.label(onTrue);
            this.method.load(true);
            this.method.label(skip);
            return;
        }
        TypeBounds outBounds = resultBounds.notNarrowerThan(narrowestOperandType);
        JoinPredecessorExpression lhs = (JoinPredecessorExpression) binaryNode.lhs();
        boolean lhsConvert = LocalVariableConversion.hasLiveConversion(lhs);
        Label evalRhs = lhsConvert ? new Label("eval_rhs") : null;
        loadExpression(lhs, outBounds);
        if (!isCurrentDiscard) {
            this.method.dup();
        }
        this.method.convert(Type.BOOLEAN);
        if (isAnd) {
            if (lhsConvert) {
                this.method.ifne(evalRhs);
            } else {
                this.method.ifeq(skip);
            }
        } else if (lhsConvert) {
            this.method.ifeq(evalRhs);
        } else {
            this.method.ifne(skip);
        }
        if (lhsConvert) {
            this.method.beforeJoinPoint(lhs);
            this.method._goto(skip);
            this.method.label(evalRhs);
        }
        if (!isCurrentDiscard) {
            this.method.pop();
        }
        JoinPredecessorExpression rhs = (JoinPredecessorExpression) binaryNode.rhs();
        loadMaybeDiscard(isCurrentDiscard, rhs, outBounds);
        this.method.beforeJoinPoint(rhs);
        this.method.label(skip);
    }

    private static boolean isLocalVariable(Expression lhs) {
        return (lhs instanceof IdentNode) && isLocalVariable((IdentNode) lhs);
    }

    private static boolean isLocalVariable(IdentNode lhs) {
        return lhs.getSymbol().isBytecodeLocal();
    }

    private void loadASSIGN(BinaryNode binaryNode) {
        Expression lhs = binaryNode.lhs();
        Expression rhs = binaryNode.rhs();
        Type rhsType = rhs.getType();
        if (lhs instanceof IdentNode) {
            Symbol symbol = ((IdentNode) lhs).getSymbol();
            if (!symbol.isScope() && !symbol.hasSlotFor(rhsType) && ((CodeGeneratorLexicalContext) this.f30lc).popDiscardIfCurrent(binaryNode)) {
                loadAndDiscard(rhs);
                this.method.markDeadLocalVariable(symbol);
                return;
            }
        }
        new Store(this, binaryNode, lhs, rhs, rhsType) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.16
            final Expression val$rhs;
            final Type val$rhsType;
            final CodeGenerator this$0;

            {
                this.this$0 = this;
                this.val$rhs = rhs;
                this.val$rhsType = rhsType;
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.Store
            protected void evaluate() {
                this.this$0.loadExpressionAsType(this.val$rhs, this.val$rhsType);
            }
        }.store();
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$BinaryOptimisticSelfAssignment.class */
    private abstract class BinaryOptimisticSelfAssignment extends SelfModifyingStore {
        final CodeGenerator this$0;

        /* renamed from: op */
        protected abstract void mo2op(OptimisticOperation optimisticOperation);

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        BinaryOptimisticSelfAssignment(CodeGenerator codeGenerator, BinaryNode binaryNode) {
            super(codeGenerator, binaryNode, binaryNode.lhs());
            this.this$0 = codeGenerator;
        }

        @Override // jdk.nashorn.internal.codegen.CodeGenerator.Store
        protected void evaluate() {
            Expression expressionLhs = ((BinaryNode) this.assignNode).lhs();
            Expression expressionRhs = ((BinaryNode) this.assignNode).rhs();
            Type widestOperationType = ((BinaryNode) this.assignNode).getWidestOperationType();
            TypeBounds typeBounds = new TypeBounds(((BinaryNode) this.assignNode).getType(), widestOperationType);
            new OptimisticOperation(this, (Optimistic) this.assignNode, typeBounds, widestOperationType, expressionLhs, expressionRhs, typeBounds) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.BinaryOptimisticSelfAssignment.1
                final Type val$widestOperationType;
                final Expression val$lhs;
                final Expression val$rhs;
                final TypeBounds val$bounds;
                final BinaryOptimisticSelfAssignment this$1;

                {
                    this.this$1 = this;
                    this.val$widestOperationType = widestOperationType;
                    this.val$lhs = expressionLhs;
                    this.val$rhs = expressionRhs;
                    this.val$bounds = typeBounds;
                    CodeGenerator codeGenerator = this.this$0;
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void loadStack() {
                    boolean zNarrowerThan;
                    if (UnwarrantedOptimismException.isValid(getProgramPoint()) || this.val$widestOperationType == Type.NUMBER) {
                        zNarrowerThan = false;
                    } else {
                        zNarrowerThan = Type.widest(CodeGenerator.booleanToInt(CodeGenerator.objectToNumber(this.val$lhs.getType())), CodeGenerator.booleanToInt(CodeGenerator.objectToNumber(this.val$rhs.getType()))).narrowerThan(this.val$widestOperationType);
                    }
                    this.this$1.this$0.loadBinaryOperands(this.val$lhs, this.val$rhs, this.val$bounds, true, zNarrowerThan);
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void consumeStack() {
                    this.this$1.mo2op(this);
                }
            }.emit(CodeGenerator.getOptimisticIgnoreCountForSelfModifyingExpression(expressionLhs));
            this.this$0.method.convert(((BinaryNode) this.assignNode).getType());
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$BinarySelfAssignment.class */
    private abstract class BinarySelfAssignment extends SelfModifyingStore {
        final CodeGenerator this$0;

        /* renamed from: op */
        protected abstract void mo3op();

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        BinarySelfAssignment(CodeGenerator codeGenerator, BinaryNode binaryNode) {
            super(codeGenerator, binaryNode, binaryNode.lhs());
            this.this$0 = codeGenerator;
        }

        @Override // jdk.nashorn.internal.codegen.CodeGenerator.Store
        protected void evaluate() {
            this.this$0.loadBinaryOperands(((BinaryNode) this.assignNode).lhs(), ((BinaryNode) this.assignNode).rhs(), TypeBounds.UNBOUNDED.notWiderThan(((BinaryNode) this.assignNode).getWidestOperandType()), true, false);
            mo3op();
        }
    }

    private void loadASSIGN_ADD(final BinaryNode binaryNode) {
        new BinaryOptimisticSelfAssignment(binaryNode) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.17
            static final /* synthetic */ boolean $assertionsDisabled;

            static {
                $assertionsDisabled = !CodeGenerator.class.desiredAssertionStatus();
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.BinaryOptimisticSelfAssignment
            /* renamed from: op */
            protected void mo2op(OptimisticOperation oo) {
                if (!$assertionsDisabled && binaryNode.getType().isObject() && oo.isOptimistic) {
                    throw new AssertionError();
                }
                CodeGenerator.this.method.add(oo.getProgramPoint());
            }
        }.store();
    }

    private void loadASSIGN_BIT_AND(BinaryNode binaryNode) {
        new BinarySelfAssignment(this, binaryNode) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.18
            final CodeGenerator this$0;

            {
                this.this$0 = this;
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.BinarySelfAssignment
            /* renamed from: op */
            protected void mo3op() {
                this.this$0.method.and();
            }
        }.store();
    }

    private void loadASSIGN_BIT_OR(BinaryNode binaryNode) {
        new BinarySelfAssignment(this, binaryNode) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.19
            final CodeGenerator this$0;

            {
                this.this$0 = this;
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.BinarySelfAssignment
            /* renamed from: op */
            protected void mo3op() {
                this.this$0.method.m5or();
            }
        }.store();
    }

    private void loadASSIGN_BIT_XOR(BinaryNode binaryNode) {
        new BinarySelfAssignment(this, binaryNode) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.20
            final CodeGenerator this$0;

            {
                this.this$0 = this;
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.BinarySelfAssignment
            /* renamed from: op */
            protected void mo3op() {
                this.this$0.method.xor();
            }
        }.store();
    }

    private void loadASSIGN_DIV(BinaryNode binaryNode) {
        new BinaryOptimisticSelfAssignment(this, binaryNode) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.21
            final CodeGenerator this$0;

            {
                this.this$0 = this;
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.BinaryOptimisticSelfAssignment
            /* renamed from: op */
            protected void mo2op(OptimisticOperation optimisticOperation) {
                this.this$0.method.div(optimisticOperation.getProgramPoint());
            }
        }.store();
    }

    private void loadASSIGN_MOD(BinaryNode binaryNode) {
        new BinaryOptimisticSelfAssignment(this, binaryNode) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.22
            final CodeGenerator this$0;

            {
                this.this$0 = this;
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.BinaryOptimisticSelfAssignment
            /* renamed from: op */
            protected void mo2op(OptimisticOperation optimisticOperation) {
                this.this$0.method.rem(optimisticOperation.getProgramPoint());
            }
        }.store();
    }

    private void loadASSIGN_MUL(BinaryNode binaryNode) {
        new BinaryOptimisticSelfAssignment(this, binaryNode) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.23
            final CodeGenerator this$0;

            {
                this.this$0 = this;
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.BinaryOptimisticSelfAssignment
            /* renamed from: op */
            protected void mo2op(OptimisticOperation optimisticOperation) {
                this.this$0.method.mul(optimisticOperation.getProgramPoint());
            }
        }.store();
    }

    private void loadASSIGN_SAR(BinaryNode binaryNode) {
        new BinarySelfAssignment(this, binaryNode) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.24
            final CodeGenerator this$0;

            {
                this.this$0 = this;
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.BinarySelfAssignment
            /* renamed from: op */
            protected void mo3op() {
                this.this$0.method.sar();
            }
        }.store();
    }

    private void loadASSIGN_SHL(BinaryNode binaryNode) {
        new BinarySelfAssignment(this, binaryNode) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.25
            final CodeGenerator this$0;

            {
                this.this$0 = this;
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.BinarySelfAssignment
            /* renamed from: op */
            protected void mo3op() {
                this.this$0.method.shl();
            }
        }.store();
    }

    /* renamed from: jdk.nashorn.internal.codegen.CodeGenerator$26 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$26.class */
    class C008626 extends SelfModifyingStore {
        final BinaryNode val$binaryNode;
        final CodeGenerator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        C008626(CodeGenerator codeGenerator, BinaryNode binaryNode, Expression expression, BinaryNode binaryNode2) {
            super(codeGenerator, binaryNode, expression);
            this.this$0 = codeGenerator;
            this.val$binaryNode = binaryNode2;
        }

        @Override // jdk.nashorn.internal.codegen.CodeGenerator.Store
        protected void evaluate() {
            new OptimisticOperation(this, (Optimistic) this.assignNode, new TypeBounds(Type.INT, Type.NUMBER)) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.26.1
                static final boolean $assertionsDisabled;
                final C008626 this$1;

                {
                    this.this$1 = this;
                    CodeGenerator codeGenerator = this.this$0;
                }

                static {
                    $assertionsDisabled = !CodeGenerator.class.desiredAssertionStatus();
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void loadStack() {
                    if (!$assertionsDisabled && ((BinaryNode) this.this$1.assignNode).getWidestOperandType() != Type.INT) {
                        throw new AssertionError();
                    }
                    if (CodeGenerator.isRhsZero(this.this$1.val$binaryNode)) {
                        this.this$1.this$0.loadExpression(this.this$1.val$binaryNode.lhs(), TypeBounds.INT, true);
                    } else {
                        this.this$1.this$0.loadBinaryOperands(this.this$1.val$binaryNode.lhs(), this.this$1.val$binaryNode.rhs(), TypeBounds.INT, true, false);
                        this.this$1.this$0.method.shr();
                    }
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void consumeStack() {
                    if (CodeGenerator.isOptimistic(this.this$1.val$binaryNode)) {
                        this.this$1.this$0.toUint32Optimistic(this.this$1.val$binaryNode.getProgramPoint());
                    } else {
                        this.this$1.this$0.toUint32Double();
                    }
                }
            }.emit(CodeGenerator.getOptimisticIgnoreCountForSelfModifyingExpression(this.val$binaryNode.lhs()));
            this.this$0.method.convert(((BinaryNode) this.assignNode).getType());
        }
    }

    private void loadASSIGN_SHR(BinaryNode binaryNode) {
        new C008626(this, binaryNode, binaryNode.lhs(), binaryNode).store();
    }

    private void doSHR(BinaryNode binaryNode) {
        new OptimisticOperation(this, binaryNode, new TypeBounds(Type.INT, Type.NUMBER), binaryNode) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.27
            final BinaryNode val$binaryNode;
            final CodeGenerator this$0;

            {
                this.this$0 = this;
                this.val$binaryNode = binaryNode;
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
            void loadStack() {
                if (CodeGenerator.isRhsZero(this.val$binaryNode)) {
                    this.this$0.loadExpressionAsType(this.val$binaryNode.lhs(), Type.INT);
                } else {
                    this.this$0.loadBinaryOperands(this.val$binaryNode);
                    this.this$0.method.shr();
                }
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
            void consumeStack() {
                if (CodeGenerator.isOptimistic(this.val$binaryNode)) {
                    this.this$0.toUint32Optimistic(this.val$binaryNode.getProgramPoint());
                } else {
                    this.this$0.toUint32Double();
                }
            }
        }.emit();
    }

    private void toUint32Optimistic(int programPoint) {
        this.method.load(programPoint);
        JSType.TO_UINT32_OPTIMISTIC.invoke(this.method);
    }

    private void toUint32Double() {
        JSType.TO_UINT32_DOUBLE.invoke(this.method);
    }

    private void loadASSIGN_SUB(BinaryNode binaryNode) {
        new BinaryOptimisticSelfAssignment(this, binaryNode) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.28
            final CodeGenerator this$0;

            {
                this.this$0 = this;
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.BinaryOptimisticSelfAssignment
            /* renamed from: op */
            protected void mo2op(OptimisticOperation optimisticOperation) {
                this.this$0.method.sub(optimisticOperation.getProgramPoint());
            }
        }.store();
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$BinaryArith.class */
    private abstract class BinaryArith {
        final CodeGenerator this$0;

        /* renamed from: op */
        protected abstract void mo4op(int i);

        private BinaryArith(CodeGenerator codeGenerator) {
            this.this$0 = codeGenerator;
        }

        BinaryArith(CodeGenerator codeGenerator, C00681 c00681) {
            this(codeGenerator);
        }

        protected void evaluate(BinaryNode binaryNode, TypeBounds typeBounds) {
            TypeBounds typeBoundsObjectToNumber = typeBounds.booleanToInt().objectToNumber();
            new OptimisticOperation(this, binaryNode, typeBoundsObjectToNumber, typeBoundsObjectToNumber, binaryNode) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.BinaryArith.1
                static final boolean $assertionsDisabled;
                final TypeBounds val$numericBounds;
                final BinaryNode val$node;
                final BinaryArith this$1;

                {
                    this.this$1 = this;
                    this.val$numericBounds = typeBoundsObjectToNumber;
                    this.val$node = binaryNode;
                    CodeGenerator codeGenerator = this.this$0;
                }

                static {
                    $assertionsDisabled = !CodeGenerator.class.desiredAssertionStatus();
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void loadStack() {
                    TypeBounds typeBounds2;
                    boolean z = false;
                    if (this.val$numericBounds.narrowest == Type.NUMBER) {
                        if (!$assertionsDisabled && this.val$numericBounds.widest != Type.NUMBER) {
                            throw new AssertionError();
                        }
                        typeBounds2 = this.val$numericBounds;
                    } else if (UnwarrantedOptimismException.isValid(getProgramPoint()) || this.val$node.isTokenType(TokenType.DIV) || this.val$node.isTokenType(TokenType.MOD)) {
                        typeBounds2 = new TypeBounds(this.val$node.getType(), Type.NUMBER);
                    } else {
                        typeBounds2 = new TypeBounds(Type.narrowest(this.val$node.getWidestOperandType(), this.val$numericBounds.widest), Type.NUMBER);
                        z = true;
                    }
                    this.this$1.this$0.loadBinaryOperands(this.val$node.lhs(), this.val$node.rhs(), typeBounds2, false, z);
                }

                @Override // jdk.nashorn.internal.codegen.CodeGenerator.OptimisticOperation
                void consumeStack() {
                    this.this$1.mo4op(getProgramPoint());
                }
            }.emit();
        }
    }

    private void loadBIT_AND(BinaryNode binaryNode) {
        loadBinaryOperands(binaryNode);
        this.method.and();
    }

    private void loadBIT_OR(BinaryNode binaryNode) {
        if (isRhsZero(binaryNode)) {
            loadExpressionAsType(binaryNode.lhs(), Type.INT);
        } else {
            loadBinaryOperands(binaryNode);
            this.method.m5or();
        }
    }

    private static boolean isRhsZero(BinaryNode binaryNode) {
        Expression rhs = binaryNode.rhs();
        return (rhs instanceof LiteralNode) && INT_ZERO.equals(((LiteralNode) rhs).getValue());
    }

    private void loadBIT_XOR(BinaryNode binaryNode) {
        loadBinaryOperands(binaryNode);
        this.method.xor();
    }

    private void loadCOMMARIGHT(BinaryNode binaryNode, TypeBounds resultBounds) {
        loadAndDiscard(binaryNode.lhs());
        loadMaybeDiscard(binaryNode, binaryNode.rhs(), resultBounds);
    }

    private void loadCOMMALEFT(BinaryNode binaryNode, TypeBounds resultBounds) {
        loadMaybeDiscard(binaryNode, binaryNode.lhs(), resultBounds);
        loadAndDiscard(binaryNode.rhs());
    }

    private void loadDIV(BinaryNode binaryNode, TypeBounds resultBounds) {
        new BinaryArith(this) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.29
            final CodeGenerator this$0;

            {
                this.this$0 = this;
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.BinaryArith
            /* renamed from: op */
            protected void mo4op(int i) {
                this.this$0.method.div(i);
            }
        }.evaluate(binaryNode, resultBounds);
    }

    private void loadCmp(BinaryNode binaryNode, Condition cond) {
        loadComparisonOperands(binaryNode);
        Label trueLabel = new Label("trueLabel");
        Label afterLabel = new Label("skip");
        this.method.conditionalJump(cond, trueLabel);
        this.method.load(Boolean.FALSE.booleanValue());
        this.method._goto(afterLabel);
        this.method.label(trueLabel);
        this.method.load(Boolean.TRUE.booleanValue());
        this.method.label(afterLabel);
    }

    private void loadMOD(BinaryNode binaryNode, TypeBounds resultBounds) {
        new BinaryArith(this) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.30
            final CodeGenerator this$0;

            {
                this.this$0 = this;
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.BinaryArith
            /* renamed from: op */
            protected void mo4op(int i) {
                this.this$0.method.rem(i);
            }
        }.evaluate(binaryNode, resultBounds);
    }

    private void loadMUL(BinaryNode binaryNode, TypeBounds resultBounds) {
        new BinaryArith(this) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.31
            final CodeGenerator this$0;

            {
                this.this$0 = this;
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.BinaryArith
            /* renamed from: op */
            protected void mo4op(int i) {
                this.this$0.method.mul(i);
            }
        }.evaluate(binaryNode, resultBounds);
    }

    private void loadSAR(BinaryNode binaryNode) {
        loadBinaryOperands(binaryNode);
        this.method.sar();
    }

    private void loadSHL(BinaryNode binaryNode) {
        loadBinaryOperands(binaryNode);
        this.method.shl();
    }

    private void loadSHR(BinaryNode binaryNode) {
        doSHR(binaryNode);
    }

    private void loadSUB(BinaryNode binaryNode, TypeBounds resultBounds) {
        new BinaryArith(this) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.32
            final CodeGenerator this$0;

            {
                this.this$0 = this;
            }

            @Override // jdk.nashorn.internal.codegen.CodeGenerator.BinaryArith
            /* renamed from: op */
            protected void mo4op(int i) {
                this.this$0.method.sub(i);
            }
        }.evaluate(binaryNode, resultBounds);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterLabelNode(LabelNode labelNode) {
        this.labeledBlockBreakLiveLocals.push(((CodeGeneratorLexicalContext) this.f30lc).getUsedSlotCount());
        return true;
    }

    protected boolean enterDefault(Node node) {
        throw new AssertionError("Code generator entered node of type " + node.getClass().getName());
    }

    private void loadTernaryNode(TernaryNode ternaryNode, TypeBounds resultBounds) {
        Expression test = ternaryNode.getTest();
        JoinPredecessorExpression trueExpr = ternaryNode.getTrueExpression();
        JoinPredecessorExpression falseExpr = ternaryNode.getFalseExpression();
        Label falseLabel = new Label("ternary_false");
        Label exitLabel = new Label("ternary_exit");
        Type outNarrowest = Type.narrowest(resultBounds.widest, Type.generic(Type.widestReturnType(trueExpr.getType(), falseExpr.getType())));
        TypeBounds outBounds = resultBounds.notNarrowerThan(outNarrowest);
        emitBranch(test, falseLabel, false);
        boolean isCurrentDiscard = ((CodeGeneratorLexicalContext) this.f30lc).popDiscardIfCurrent(ternaryNode);
        loadMaybeDiscard(isCurrentDiscard, trueExpr.getExpression(), outBounds);
        if (!$assertionsDisabled && !isCurrentDiscard && Type.generic(this.method.peekType()) != outBounds.narrowest) {
            throw new AssertionError();
        }
        this.method.beforeJoinPoint(trueExpr);
        this.method._goto(exitLabel);
        this.method.label(falseLabel);
        loadMaybeDiscard(isCurrentDiscard, falseExpr.getExpression(), outBounds);
        if (!$assertionsDisabled && !isCurrentDiscard && Type.generic(this.method.peekType()) != outBounds.narrowest) {
            throw new AssertionError();
        }
        this.method.beforeJoinPoint(falseExpr);
        this.method.label(exitLabel);
    }

    void generateScopeCalls() {
        for (SharedScopeCall scopeAccess : ((CodeGeneratorLexicalContext) this.f30lc).getScopeCalls()) {
            scopeAccess.generateScopeCall();
        }
    }

    private void printSymbols(Block block, FunctionNode function, String ident) {
        if (this.compiler.getScriptEnvironment()._print_symbols || function.getFlag(2097152)) {
            PrintWriter out = this.compiler.getScriptEnvironment().getErr();
            out.println("[BLOCK in '" + ident + "']");
            if (!block.printSymbols(out)) {
                out.println("<no symbols>");
            }
            out.println();
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$SelfModifyingStore.class */
    private abstract class SelfModifyingStore extends Store {
        final CodeGenerator this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        protected SelfModifyingStore(CodeGenerator codeGenerator, Expression expression, Expression expression2) {
            super(codeGenerator, expression, expression2);
            this.this$0 = codeGenerator;
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$Store.class */
    private abstract class Store {
        protected final Expression assignNode;
        private final Expression target;
        private int depth;
        private IdentNode quick;
        static final boolean $assertionsDisabled;
        final CodeGenerator this$0;

        protected abstract void evaluate();

        static int access$7900(Store store) {
            return store.depth;
        }

        static int access$7902(Store store, int i) {
            store.depth = i;
            return i;
        }

        static {
            $assertionsDisabled = !CodeGenerator.class.desiredAssertionStatus();
        }

        protected Store(CodeGenerator codeGenerator, Expression expression, Expression expression2) {
            this.this$0 = codeGenerator;
            this.assignNode = expression;
            this.target = expression2;
        }

        protected Store(CodeGenerator codeGenerator, Expression expression) {
            this(codeGenerator, expression, expression);
        }

        private void prologue() {
            this.target.accept(new SimpleNodeVisitor(this) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.Store.1
                static final boolean $assertionsDisabled;
                final Store this$1;

                {
                    this.this$1 = this;
                }

                static {
                    $assertionsDisabled = !CodeGenerator.class.desiredAssertionStatus();
                }

                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public boolean enterIdentNode(IdentNode identNode) {
                    if (identNode.getSymbol().isScope()) {
                        this.this$1.this$0.method.loadCompilerConstant(CompilerConstants.SCOPE);
                        this.this$1.depth += Type.SCOPE.getSlots();
                        if ($assertionsDisabled || this.this$1.depth == 1) {
                            return false;
                        }
                        throw new AssertionError();
                    }
                    return false;
                }

                private void enterBaseNode() {
                    if (!$assertionsDisabled && !(this.this$1.target instanceof BaseNode)) {
                        throw new AssertionError("error - base node " + this.this$1.target + " must be instanceof BaseNode");
                    }
                    this.this$1.this$0.loadExpressionAsObject(((BaseNode) this.this$1.target).getBase());
                    this.this$1.depth += Type.OBJECT.getSlots();
                    if (!$assertionsDisabled && this.this$1.depth != 1) {
                        throw new AssertionError();
                    }
                    Store store = this.this$1;
                    if (0 != 0) {
                        this.this$1.this$0.method.dup();
                    }
                }

                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public boolean enterAccessNode(AccessNode accessNode) {
                    enterBaseNode();
                    return false;
                }

                /*  JADX ERROR: Types fix failed
                    java.lang.NullPointerException
                    */
                /* JADX WARN: Not initialized variable reg: 8, insn: 0x000d: MOVE (r1 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r8 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) (LINE:477), block:B:2:0x0000 */
                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public boolean enterIndexNode(jdk.nashorn.internal.p001ir.IndexNode r6) {
                    /*
                        r5 = this;
                        r0 = r5
                        r0.enterBaseNode()
                        r0 = r6
                        jdk.nashorn.internal.ir.Expression r0 = r0.getIndex()
                        r7 = r0
                        r0 = r7
                        jdk.nashorn.internal.codegen.types.Type r0 = r0.getType()
                        r1 = r8
                        boolean r1 = r1 instanceof jdk.nashorn.internal.codegen.types.NumericType
                        if (r1 != 0) goto L23
                        r1 = r5
                        jdk.nashorn.internal.codegen.CodeGenerator$Store r1 = r1.this$1
                        jdk.nashorn.internal.codegen.CodeGenerator r1 = r1.this$0
                        r2 = r7
                        jdk.nashorn.internal.codegen.MethodEmitter r1 = jdk.nashorn.internal.codegen.CodeGenerator.access$600(r1, r2)
                        goto L2f
                    L23:
                        r1 = r5
                        jdk.nashorn.internal.codegen.CodeGenerator$Store r1 = r1.this$1
                        jdk.nashorn.internal.codegen.CodeGenerator r1 = r1.this$0
                        r2 = r7
                        jdk.nashorn.internal.codegen.MethodEmitter r1 = jdk.nashorn.internal.codegen.CodeGenerator.access$700(r1, r2)
                    L2f:
                        r1 = r5
                        jdk.nashorn.internal.codegen.CodeGenerator$Store r1 = r1.this$1
                        r2 = r5
                        jdk.nashorn.internal.codegen.CodeGenerator$Store r2 = r2.this$1
                        int r2 = jdk.nashorn.internal.codegen.CodeGenerator.Store.access$7900(r2)
                        r3 = r7
                        jdk.nashorn.internal.codegen.types.Type r3 = r3.getType()
                        int r3 = r3.getSlots()
                        int r2 = r2 + r3
                        int r1 = jdk.nashorn.internal.codegen.CodeGenerator.Store.access$7902(r1, r2)
                        r1 = r5
                        jdk.nashorn.internal.codegen.CodeGenerator$Store r1 = r1.this$1
                        r2 = 0
                        if (r2 == 0) goto L5d
                        r2 = r5
                        jdk.nashorn.internal.codegen.CodeGenerator$Store r2 = r2.this$1
                        jdk.nashorn.internal.codegen.CodeGenerator r2 = r2.this$0
                        jdk.nashorn.internal.codegen.MethodEmitter r2 = jdk.nashorn.internal.codegen.CodeGenerator.access$000(r2)
                        r3 = 1
                        jdk.nashorn.internal.codegen.MethodEmitter r2 = r2.dup(r3)
                    L5d:
                        r2 = 0
                        return r2
                    */
                    throw new UnsupportedOperationException("Method not decompiled: jdk.nashorn.internal.codegen.CodeGenerator.Store.C01031.enterIndexNode(jdk.nashorn.internal.ir.IndexNode):boolean");
                }
            });
        }

        private IdentNode quickLocalVariable(Type type) {
            Symbol symbol = new Symbol(((CodeGeneratorLexicalContext) this.this$0.f30lc).getCurrentFunction().uniqueName(CompilerConstants.QUICK_PREFIX.symbolName()), 1088);
            symbol.setHasSlotFor(type);
            symbol.setFirstSlot(((CodeGeneratorLexicalContext) this.this$0.f30lc).quickSlot(type));
            return IdentNode.createInternalIdentifier(symbol).setType(type);
        }

        protected void storeNonDiscard() {
            if (!((CodeGeneratorLexicalContext) this.this$0.f30lc).popDiscardIfCurrent(this.assignNode)) {
                if (this.this$0.method.dup(this.depth) == null) {
                    this.this$0.method.dup();
                    Type typePeekType = this.this$0.method.peekType();
                    this.quick = quickLocalVariable(typePeekType);
                    this.this$0.method.storeTemp(typePeekType, this.quick.getSymbol().getFirstSlot());
                    return;
                }
                return;
            }
            if (!$assertionsDisabled && !this.assignNode.isAssignment()) {
                throw new AssertionError();
            }
        }

        private void epilogue() {
            this.target.accept(new SimpleNodeVisitor(this) { // from class: jdk.nashorn.internal.codegen.CodeGenerator.Store.2
                static final boolean $assertionsDisabled;
                final Store this$1;

                {
                    this.this$1 = this;
                }

                static {
                    $assertionsDisabled = !CodeGenerator.class.desiredAssertionStatus();
                }

                protected boolean enterDefault(Node node) {
                    throw new AssertionError("Unexpected node " + node + " in store epilogue");
                }

                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public boolean enterIdentNode(IdentNode identNode) {
                    Symbol symbol = identNode.getSymbol();
                    if (!$assertionsDisabled && symbol == null) {
                        throw new AssertionError();
                    }
                    if (symbol.isScope()) {
                        int scopeCallSiteFlags = this.this$1.this$0.getScopeCallSiteFlags(symbol);
                        if (this.this$1.this$0.isFastScope(symbol)) {
                            this.this$1.this$0.storeFastScopeVar(symbol, scopeCallSiteFlags);
                            return false;
                        }
                        this.this$1.this$0.method.dynamicSet(identNode.getName(), scopeCallSiteFlags, false);
                        return false;
                    }
                    Type type = this.this$1.assignNode.getType();
                    if (!$assertionsDisabled && type == Type.LONG) {
                        throw new AssertionError();
                    }
                    if (symbol.hasSlotFor(type)) {
                        this.this$1.this$0.method.convert(type);
                    }
                    this.this$1.this$0.storeIdentWithCatchConversion(identNode, type);
                    return false;
                }

                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public boolean enterAccessNode(AccessNode accessNode) {
                    this.this$1.this$0.method.dynamicSet(accessNode.getProperty(), this.this$1.this$0.getCallSiteFlags(), accessNode.isIndex());
                    return false;
                }

                @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
                public boolean enterIndexNode(IndexNode indexNode) {
                    this.this$1.this$0.method.dynamicSetIndex(this.this$1.this$0.getCallSiteFlags());
                    return false;
                }
            });
        }

        void store() {
            if (this.target instanceof IdentNode) {
                this.this$0.checkTemporalDeadZone((IdentNode) this.target);
            }
            prologue();
            evaluate();
            storeNonDiscard();
            epilogue();
            if (this.quick != null) {
                this.this$0.method.load(this.quick);
            }
        }
    }

    private void newFunctionObject(FunctionNode functionNode, boolean addInitializer) {
        if (!$assertionsDisabled && ((CodeGeneratorLexicalContext) this.f30lc).peek() != functionNode) {
            throw new AssertionError();
        }
        RecompilableScriptFunctionData data = this.compiler.getScriptFunctionData(functionNode.getId());
        if (functionNode.isProgram() && !this.compiler.isOnDemandCompilation()) {
            MethodEmitter createFunction = functionNode.getCompileUnit().getClassEmitter().method(EnumSet.of(ClassEmitter.Flag.PUBLIC, ClassEmitter.Flag.STATIC), CompilerConstants.CREATE_PROGRAM_FUNCTION.symbolName(), ScriptFunction.class, new Class[]{ScriptObject.class});
            createFunction.begin();
            loadConstantsAndIndex(data, createFunction);
            createFunction.load(SCOPE_TYPE, 0);
            createFunction.invoke(CREATE_FUNCTION_OBJECT);
            createFunction._return();
            createFunction.end();
        }
        if (addInitializer && !this.compiler.isOnDemandCompilation()) {
            functionNode.getCompileUnit().addFunctionInitializer(data, functionNode);
        }
        if (((CodeGeneratorLexicalContext) this.f30lc).getOutermostFunction() == functionNode) {
            return;
        }
        loadConstantsAndIndex(data, this.method);
        if (functionNode.needsParentScope()) {
            this.method.loadCompilerConstant(CompilerConstants.SCOPE);
            this.method.invoke(CREATE_FUNCTION_OBJECT);
        } else {
            this.method.invoke(CREATE_FUNCTION_OBJECT_NO_SCOPE);
        }
    }

    private MethodEmitter globalInstance() {
        return this.method.invokestatic(GLOBAL_OBJECT, "instance", "()L" + GLOBAL_OBJECT + ';');
    }

    private MethodEmitter globalAllocateArguments() {
        return this.method.invokestatic(GLOBAL_OBJECT, "allocateArguments", CompilerConstants.methodDescriptor(ScriptObject.class, new Class[]{Object[].class, Object.class, Integer.TYPE}));
    }

    private MethodEmitter globalNewRegExp() {
        return this.method.invokestatic(GLOBAL_OBJECT, "newRegExp", CompilerConstants.methodDescriptor(Object.class, new Class[]{String.class, String.class}));
    }

    private MethodEmitter globalRegExpCopy() {
        return this.method.invokestatic(GLOBAL_OBJECT, "regExpCopy", CompilerConstants.methodDescriptor(Object.class, new Class[]{Object.class}));
    }

    private MethodEmitter globalAllocateArray(ArrayType type) {
        return this.method.invokestatic(GLOBAL_OBJECT, "allocate", "(" + type.getDescriptor() + ")Ljdk/nashorn/internal/objects/NativeArray;");
    }

    private MethodEmitter globalIsEval() {
        return this.method.invokestatic(GLOBAL_OBJECT, "isEval", CompilerConstants.methodDescriptor(Boolean.TYPE, new Class[]{Object.class}));
    }

    private MethodEmitter globalReplaceLocationPropertyPlaceholder() {
        return this.method.invokestatic(GLOBAL_OBJECT, "replaceLocationPropertyPlaceholder", CompilerConstants.methodDescriptor(Object.class, new Class[]{Object.class, Object.class}));
    }

    private MethodEmitter globalCheckObjectCoercible() {
        return this.method.invokestatic(GLOBAL_OBJECT, "checkObjectCoercible", CompilerConstants.methodDescriptor(Void.TYPE, new Class[]{Object.class}));
    }

    private MethodEmitter globalDirectEval() {
        return this.method.invokestatic(GLOBAL_OBJECT, "directEval", CompilerConstants.methodDescriptor(Object.class, new Class[]{Object.class, Object.class, Object.class, Object.class, Boolean.TYPE}));
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$OptimisticOperation.class */
    private abstract class OptimisticOperation {
        private final boolean isOptimistic;
        private final Expression expression;
        private final Optimistic optimistic;
        private final TypeBounds resultBounds;
        static final /* synthetic */ boolean $assertionsDisabled;

        abstract void loadStack();

        abstract void consumeStack();

        static {
            $assertionsDisabled = !CodeGenerator.class.desiredAssertionStatus();
        }

        /* JADX WARN: Multi-variable type inference failed */
        OptimisticOperation(Optimistic optimistic, TypeBounds resultBounds) {
            this.optimistic = optimistic;
            this.expression = (Expression) optimistic;
            this.resultBounds = resultBounds;
            this.isOptimistic = CodeGenerator.isOptimistic(optimistic) && CodeGenerator.this.useOptimisticTypes() && resultBounds.within(Type.generic(((Expression) optimistic).getType())).narrowerThan(resultBounds.widest);
        }

        MethodEmitter emit() {
            return emit(0);
        }

        MethodEmitter emit(int ignoredArgCount) {
            Label catchLabel;
            Label beginTry;
            int programPoint = this.optimistic.getProgramPoint();
            boolean optimisticOrContinuation = this.isOptimistic || CodeGenerator.this.isContinuationEntryPoint(programPoint);
            boolean currentContinuationEntryPoint = CodeGenerator.this.isCurrentContinuationEntryPoint(programPoint);
            int stackSizeOnEntry = CodeGenerator.this.method.getStackSize() - ignoredArgCount;
            storeStack(ignoredArgCount, optimisticOrContinuation);
            loadStack();
            int liveLocalsCount = storeStack(CodeGenerator.this.method.getStackSize() - stackSizeOnEntry, optimisticOrContinuation);
            if (!$assertionsDisabled) {
                if (optimisticOrContinuation != (liveLocalsCount != -1)) {
                    throw new AssertionError();
                }
            }
            Label afterConsumeStack = (this.isOptimistic || currentContinuationEntryPoint) ? new Label("after_consume_stack") : null;
            if (this.isOptimistic) {
                beginTry = new Label("try_optimistic");
                String catchLabelName = (afterConsumeStack == null ? "" : afterConsumeStack.toString()) + "_handler";
                catchLabel = new Label(catchLabelName);
                CodeGenerator.this.method.label(beginTry);
            } else {
                catchLabel = null;
                beginTry = null;
            }
            consumeStack();
            if (this.isOptimistic) {
                CodeGenerator.this.method._try(beginTry, afterConsumeStack, catchLabel, UnwarrantedOptimismException.class);
            }
            if (this.isOptimistic || currentContinuationEntryPoint) {
                CodeGenerator.this.method.label(afterConsumeStack);
                int[] localLoads = CodeGenerator.this.method.getLocalLoadsOnStack(0, stackSizeOnEntry);
                if (!$assertionsDisabled && !CodeGenerator.everyStackValueIsLocalLoad(localLoads)) {
                    throw new AssertionError(Arrays.toString(localLoads) + ", " + stackSizeOnEntry + ", " + ignoredArgCount);
                }
                List<Type> localTypesList = CodeGenerator.this.method.getLocalVariableTypes();
                int usedLocals = CodeGenerator.this.method.getUsedSlotsWithLiveTemporaries();
                List<Type> localTypes = CodeGenerator.this.method.getWidestLiveLocals(localTypesList.subList(0, usedLocals));
                if (!$assertionsDisabled && !CodeGenerator.everyLocalLoadIsValid(localLoads, usedLocals)) {
                    throw new AssertionError(Arrays.toString(localLoads) + " ~ " + localTypes);
                }
                if (this.isOptimistic) {
                    addUnwarrantedOptimismHandlerLabel(localTypes, catchLabel);
                }
                if (currentContinuationEntryPoint) {
                    ContinuationInfo ci = CodeGenerator.this.getContinuationInfo();
                    if (!$assertionsDisabled && ci == null) {
                        throw new AssertionError("no continuation info found for " + ((CodeGeneratorLexicalContext) CodeGenerator.this.f30lc).getCurrentFunction());
                    }
                    if (!$assertionsDisabled && ci.hasTargetLabel()) {
                        throw new AssertionError();
                    }
                    ci.setTargetLabel(afterConsumeStack);
                    ci.getHandlerLabel().markAsOptimisticContinuationHandlerFor(afterConsumeStack);
                    ci.lvarCount = localTypes.size();
                    ci.setStackStoreSpec(localLoads);
                    ci.setStackTypes((Type[]) Arrays.copyOf(CodeGenerator.this.method.getTypesFromStack(CodeGenerator.this.method.getStackSize()), stackSizeOnEntry));
                    if (!$assertionsDisabled && ci.getStackStoreSpec().length != ci.getStackTypes().length) {
                        throw new AssertionError();
                    }
                    ci.setReturnValueType(CodeGenerator.this.method.peekType());
                    ci.lineNumber = CodeGenerator.this.getLastLineNumber();
                    ci.catchLabel = (Label) CodeGenerator.this.catchLabels.peek();
                }
            }
            return CodeGenerator.this.method;
        }

        private int storeStack(int ignoreArgCount, boolean optimisticOrContinuation) {
            if (optimisticOrContinuation) {
                int stackSize = CodeGenerator.this.method.getStackSize();
                Type[] stackTypes = CodeGenerator.this.method.getTypesFromStack(stackSize);
                int[] localLoadsOnStack = CodeGenerator.this.method.getLocalLoadsOnStack(0, stackSize);
                int usedSlots = CodeGenerator.this.method.getUsedSlotsWithLiveTemporaries();
                int firstIgnored = stackSize - ignoreArgCount;
                int firstNonLoad = 0;
                while (firstNonLoad < firstIgnored && localLoadsOnStack[firstNonLoad] != -1) {
                    firstNonLoad++;
                }
                if (firstNonLoad >= firstIgnored) {
                    return usedSlots;
                }
                int tempSlotsNeeded = 0;
                for (int i = firstNonLoad; i < stackSize; i++) {
                    if (localLoadsOnStack[i] == -1) {
                        tempSlotsNeeded += stackTypes[i].getSlots();
                    }
                }
                int lastTempSlot = usedSlots + tempSlotsNeeded;
                int ignoreSlotCount = 0;
                int i2 = stackSize;
                while (true) {
                    int i3 = i2;
                    i2--;
                    if (i3 <= firstNonLoad) {
                        break;
                    }
                    if (localLoadsOnStack[i2] != -1) {
                        CodeGenerator.this.method.pop();
                    } else {
                        Type type = stackTypes[i2];
                        int slots = type.getSlots();
                        lastTempSlot -= slots;
                        if (i2 >= firstIgnored) {
                            ignoreSlotCount += slots;
                        }
                        CodeGenerator.this.method.storeTemp(type, lastTempSlot);
                    }
                }
                if (!$assertionsDisabled && lastTempSlot != usedSlots) {
                    throw new AssertionError();
                }
                List<Type> localTypesList = CodeGenerator.this.method.getLocalVariableTypes();
                for (int i4 = firstNonLoad; i4 < stackSize; i4++) {
                    int loadSlot = localLoadsOnStack[i4];
                    Type stackType = stackTypes[i4];
                    boolean isLoad = loadSlot != -1;
                    int lvarSlot = isLoad ? loadSlot : lastTempSlot;
                    Type lvarType = localTypesList.get(lvarSlot);
                    CodeGenerator.this.method.load(lvarType, lvarSlot);
                    if (isLoad) {
                        CodeGenerator.this.method.convert(stackType);
                    } else {
                        if (!$assertionsDisabled && lvarType != stackType) {
                            throw new AssertionError();
                        }
                        lastTempSlot += lvarType.getSlots();
                    }
                }
                if ($assertionsDisabled || lastTempSlot == usedSlots + tempSlotsNeeded) {
                    return lastTempSlot - ignoreSlotCount;
                }
                throw new AssertionError();
            }
            return -1;
        }

        private void addUnwarrantedOptimismHandlerLabel(List<Type> localTypes, Label label) {
            String lvarTypesDescriptor = CodeGenerator.this.getLvarTypesDescriptor(localTypes);
            Map<String, Collection<Label>> unwarrantedOptimismHandlers = ((CodeGeneratorLexicalContext) CodeGenerator.this.f30lc).getUnwarrantedOptimismHandlers();
            Collection<Label> labels = unwarrantedOptimismHandlers.get(lvarTypesDescriptor);
            if (labels == null) {
                labels = new LinkedList<>();
                unwarrantedOptimismHandlers.put(lvarTypesDescriptor, labels);
            }
            CodeGenerator.this.method.markLabelAsOptimisticCatchHandler(label, localTypes.size());
            labels.add(label);
        }

        MethodEmitter dynamicGet(String name, int flags, boolean isMethod, boolean isIndex) {
            return this.isOptimistic ? CodeGenerator.this.method.dynamicGet(getOptimisticCoercedType(), name, getOptimisticFlags(flags), isMethod, isIndex) : CodeGenerator.this.method.dynamicGet(this.resultBounds.within(this.expression.getType()), name, CodeGenerator.nonOptimisticFlags(flags), isMethod, isIndex);
        }

        MethodEmitter dynamicGetIndex(int flags, boolean isMethod) {
            return this.isOptimistic ? CodeGenerator.this.method.dynamicGetIndex(getOptimisticCoercedType(), getOptimisticFlags(flags), isMethod) : CodeGenerator.this.method.dynamicGetIndex(this.resultBounds.within(this.expression.getType()), CodeGenerator.nonOptimisticFlags(flags), isMethod);
        }

        MethodEmitter dynamicCall(int argCount, int flags, String msg) {
            return this.isOptimistic ? CodeGenerator.this.method.dynamicCall(getOptimisticCoercedType(), argCount, getOptimisticFlags(flags), msg) : CodeGenerator.this.method.dynamicCall(this.resultBounds.within(this.expression.getType()), argCount, CodeGenerator.nonOptimisticFlags(flags), msg);
        }

        int getOptimisticFlags(int flags) {
            return flags | 8 | (this.optimistic.getProgramPoint() << 11);
        }

        int getProgramPoint() {
            if (this.isOptimistic) {
                return this.optimistic.getProgramPoint();
            }
            return -1;
        }

        void convertOptimisticReturnValue() {
            if (this.isOptimistic) {
                Type optimisticType = getOptimisticCoercedType();
                if (!optimisticType.isObject()) {
                    CodeGenerator.this.method.load(this.optimistic.getProgramPoint());
                    if (optimisticType.isInteger()) {
                        CodeGenerator.this.method.invoke(CodeGenerator.ENSURE_INT);
                    } else {
                        if (optimisticType.isNumber()) {
                            CodeGenerator.this.method.invoke(CodeGenerator.ENSURE_NUMBER);
                            return;
                        }
                        throw new AssertionError(optimisticType);
                    }
                }
            }
        }

        void replaceCompileTimeProperty() {
            IdentNode identNode = (IdentNode) this.expression;
            String name = identNode.getSymbol().getName();
            if (CompilerConstants.__FILE__.name().equals(name)) {
                replaceCompileTimeProperty(CodeGenerator.this.getCurrentSource().getName());
            } else if (CompilerConstants.__DIR__.name().equals(name)) {
                replaceCompileTimeProperty(CodeGenerator.this.getCurrentSource().getBase());
            } else if (CompilerConstants.__LINE__.name().equals(name)) {
                replaceCompileTimeProperty(Integer.valueOf(CodeGenerator.this.getCurrentSource().getLine(identNode.position())));
            }
        }

        private void replaceCompileTimeProperty(Object propertyValue) {
            if (!$assertionsDisabled && !CodeGenerator.this.method.peekType().isObject()) {
                throw new AssertionError();
            }
            if ((propertyValue instanceof String) || propertyValue == null) {
                CodeGenerator.this.method.load((String) propertyValue);
            } else if (propertyValue instanceof Integer) {
                CodeGenerator.this.method.load(((Integer) propertyValue).intValue());
                CodeGenerator.this.method.convert(Type.OBJECT);
            } else {
                throw new AssertionError();
            }
            CodeGenerator.this.globalReplaceLocationPropertyPlaceholder();
            convertOptimisticReturnValue();
        }

        private Type getOptimisticCoercedType() {
            Type optimisticType = this.expression.getType();
            if (!$assertionsDisabled && !this.resultBounds.widest.widerThan(optimisticType)) {
                throw new AssertionError();
            }
            Type narrowest = this.resultBounds.narrowest;
            if (narrowest.isBoolean() || narrowest.narrowerThan(optimisticType)) {
                if ($assertionsDisabled || !optimisticType.isObject()) {
                    return optimisticType;
                }
                throw new AssertionError();
            }
            if ($assertionsDisabled || !narrowest.isObject()) {
                return narrowest;
            }
            throw new AssertionError();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static boolean isOptimistic(Optimistic optimistic) {
        if (!optimistic.canBeOptimistic()) {
            return false;
        }
        Expression expr = (Expression) optimistic;
        return expr.getType().narrowerThan(expr.getWidestOperationType());
    }

    private static boolean everyLocalLoadIsValid(int[] loads, int localCount) {
        for (int load : loads) {
            if (load < 0 || load >= localCount) {
                return false;
            }
        }
        return true;
    }

    private static boolean everyStackValueIsLocalLoad(int[] loads) {
        for (int load : loads) {
            if (load == -1) {
                return false;
            }
        }
        return true;
    }

    private String getLvarTypesDescriptor(List<Type> localVarTypes) {
        int count = localVarTypes.size();
        StringBuilder desc = new StringBuilder(count);
        int iAppendType = 0;
        while (true) {
            int i = iAppendType;
            if (i < count) {
                iAppendType = i + appendType(desc, localVarTypes.get(i));
            } else {
                return this.method.markSymbolBoundariesInLvarTypesDescriptor(desc.toString());
            }
        }
    }

    private static int appendType(StringBuilder b, Type t) {
        b.append(t.getBytecodeStackType());
        return t.getSlots();
    }

    private static int countSymbolsInLvarTypeDescriptor(String lvarTypeDescriptor) {
        int count = 0;
        for (int i = 0; i < lvarTypeDescriptor.length(); i++) {
            if (Character.isUpperCase(lvarTypeDescriptor.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    private boolean generateUnwarrantedOptimismExceptionHandlers(FunctionNode fn) {
        String commonLvarSpec;
        int firstArrayIndex;
        int lvarIndex;
        int firstLvarIndex;
        Label delegationLabel;
        if (!useOptimisticTypes()) {
            return false;
        }
        Map<String, Collection<Label>> unwarrantedOptimismHandlers = ((CodeGeneratorLexicalContext) this.f30lc).popUnwarrantedOptimismHandlers();
        if (unwarrantedOptimismHandlers.isEmpty()) {
            return false;
        }
        this.method.lineNumber(0);
        List<OptimismExceptionHandlerSpec> handlerSpecs = new ArrayList<>((unwarrantedOptimismHandlers.size() * 4) / 3);
        Iterator<String> it = unwarrantedOptimismHandlers.keySet().iterator();
        while (it.hasNext()) {
            handlerSpecs.add(new OptimismExceptionHandlerSpec(it.next(), true));
        }
        Collections.sort(handlerSpecs, Collections.reverseOrder());
        Map<String, Label> delegationLabels = new HashMap<>();
        int handlerIndex = 0;
        while (handlerIndex < handlerSpecs.size()) {
            OptimismExceptionHandlerSpec spec = handlerSpecs.get(handlerIndex);
            String lvarSpec = spec.lvarSpec;
            if (spec.catchTarget) {
                if (!$assertionsDisabled && this.method.isReachable()) {
                    throw new AssertionError();
                }
                this.method._catch(unwarrantedOptimismHandlers.get(lvarSpec));
                this.method.load(countSymbolsInLvarTypeDescriptor(lvarSpec));
                this.method.newarray(Type.OBJECT_ARRAY);
            }
            if (spec.delegationTarget) {
                this.method.label(delegationLabels.get(lvarSpec));
            }
            boolean lastHandler = handlerIndex == handlerSpecs.size() - 1;
            if (lastHandler) {
                lvarIndex = 0;
                firstLvarIndex = 0;
                firstArrayIndex = 0;
                delegationLabel = null;
                commonLvarSpec = null;
            } else {
                int nextHandlerIndex = handlerIndex + 1;
                String nextLvarSpec = handlerSpecs.get(nextHandlerIndex).lvarSpec;
                commonLvarSpec = commonPrefix(lvarSpec, nextLvarSpec);
                if (!$assertionsDisabled && !Character.isUpperCase(commonLvarSpec.charAt(commonLvarSpec.length() - 1))) {
                    throw new AssertionError();
                }
                boolean addNewHandler = true;
                int commonHandlerIndex = nextHandlerIndex;
                while (true) {
                    if (commonHandlerIndex >= handlerSpecs.size()) {
                        break;
                    }
                    OptimismExceptionHandlerSpec forwardHandlerSpec = handlerSpecs.get(commonHandlerIndex);
                    String forwardLvarSpec = forwardHandlerSpec.lvarSpec;
                    if (forwardLvarSpec.equals(commonLvarSpec)) {
                        addNewHandler = false;
                        forwardHandlerSpec.delegationTarget = true;
                        break;
                    }
                    if (!forwardLvarSpec.startsWith(commonLvarSpec)) {
                        break;
                    }
                    commonHandlerIndex++;
                }
                if (addNewHandler) {
                    handlerSpecs.add(commonHandlerIndex, new OptimismExceptionHandlerSpec(commonLvarSpec, false));
                }
                firstArrayIndex = countSymbolsInLvarTypeDescriptor(commonLvarSpec);
                lvarIndex = 0;
                for (int j = 0; j < commonLvarSpec.length(); j++) {
                    lvarIndex += CodeGeneratorLexicalContext.getTypeForSlotDescriptor(commonLvarSpec.charAt(j)).getSlots();
                }
                firstLvarIndex = lvarIndex;
                delegationLabel = delegationLabels.get(commonLvarSpec);
                if (delegationLabel == null) {
                    delegationLabel = new Label("uo_pa_" + commonLvarSpec);
                    delegationLabels.put(commonLvarSpec, delegationLabel);
                }
            }
            int args = 0;
            boolean symbolHadValue = false;
            for (int typeIndex = commonLvarSpec == null ? 0 : commonLvarSpec.length(); typeIndex < lvarSpec.length(); typeIndex++) {
                char typeDesc = lvarSpec.charAt(typeIndex);
                Type lvarType = CodeGeneratorLexicalContext.getTypeForSlotDescriptor(typeDesc);
                if (!lvarType.isUnknown()) {
                    this.method.load(lvarType, lvarIndex);
                    symbolHadValue = true;
                    args++;
                } else if (typeDesc == 'U' && !symbolHadValue) {
                    if (this.method.peekType() == Type.UNDEFINED) {
                        this.method.dup();
                    } else {
                        this.method.loadUndefined(Type.OBJECT);
                    }
                    args++;
                }
                if (Character.isUpperCase(typeDesc)) {
                    symbolHadValue = false;
                }
                lvarIndex += lvarType.getSlots();
            }
            if (!$assertionsDisabled && args <= 0) {
                throw new AssertionError();
            }
            this.method.dynamicArrayPopulatorCall(args + 1, firstArrayIndex);
            if (delegationLabel != null) {
                if (!$assertionsDisabled && lastHandler) {
                    throw new AssertionError();
                }
                if (!$assertionsDisabled && commonLvarSpec == null) {
                    throw new AssertionError();
                }
                this.method.undefineLocalVariables(firstLvarIndex, true);
                OptimismExceptionHandlerSpec nextSpec = handlerSpecs.get(handlerIndex + 1);
                if (!nextSpec.lvarSpec.equals(commonLvarSpec) || nextSpec.catchTarget) {
                    this.method._goto(delegationLabel);
                }
            } else {
                if (!$assertionsDisabled && !lastHandler) {
                    throw new AssertionError();
                }
                loadConstant(getByteCodeSymbolNames(fn));
                if (isRestOf()) {
                    loadConstant(getContinuationEntryPoints());
                    this.method.invoke(CREATE_REWRITE_EXCEPTION_REST_OF);
                } else {
                    this.method.invoke(CREATE_REWRITE_EXCEPTION);
                }
                this.method.athrow();
            }
            handlerIndex++;
        }
        return true;
    }

    private static String[] getByteCodeSymbolNames(FunctionNode fn) {
        List<String> names = new ArrayList<>();
        for (Symbol symbol : fn.getBody().getSymbols()) {
            if (symbol.hasSlot()) {
                if (symbol.isScope()) {
                    if (!$assertionsDisabled && !symbol.isParam()) {
                        throw new AssertionError();
                    }
                    names.add(null);
                } else {
                    names.add(symbol.getName());
                }
            }
        }
        return (String[]) names.toArray(new String[names.size()]);
    }

    private static String commonPrefix(String s1, String s2) {
        int l1 = s1.length();
        int l = Math.min(l1, s2.length());
        int lms = -1;
        for (int i = 0; i < l; i++) {
            char c1 = s1.charAt(i);
            if (c1 != s2.charAt(i)) {
                return s1.substring(0, lms + 1);
            }
            if (Character.isUpperCase(c1)) {
                lms = i;
            }
        }
        return l == l1 ? s1 : s2;
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$OptimismExceptionHandlerSpec.class */
    private static class OptimismExceptionHandlerSpec implements Comparable {
        private final String lvarSpec;
        private final boolean catchTarget;
        private boolean delegationTarget;

        @Override // java.lang.Comparable
        public int compareTo(Object obj) {
            return compareTo((OptimismExceptionHandlerSpec) obj);
        }

        OptimismExceptionHandlerSpec(String str, boolean z) {
            this.lvarSpec = str;
            this.catchTarget = z;
            if (!z) {
                this.delegationTarget = true;
            }
        }

        public int compareTo(OptimismExceptionHandlerSpec optimismExceptionHandlerSpec) {
            return this.lvarSpec.compareTo(optimismExceptionHandlerSpec.lvarSpec);
        }

        public String toString() {
            StringBuilder sbAppend = new StringBuilder(64).append("[HandlerSpec ").append(this.lvarSpec);
            if (this.catchTarget) {
                sbAppend.append(", catchTarget");
            }
            if (this.delegationTarget) {
                sbAppend.append(", delegationTarget");
            }
            return sbAppend.append("]").toString();
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/CodeGenerator$ContinuationInfo.class */
    private static class ContinuationInfo {
        private final Label handlerLabel = new Label("continuation_handler");
        private Label targetLabel;
        int lvarCount;
        private int[] stackStoreSpec;
        private Type[] stackTypes;
        private Type returnValueType;
        private Map objectLiteralMaps;
        private int lineNumber;
        private Label catchLabel;
        private int exceptionScopePops;

        static int access$4804(ContinuationInfo continuationInfo) {
            int i = continuationInfo.exceptionScopePops + 1;
            continuationInfo.exceptionScopePops = i;
            return i;
        }

        ContinuationInfo() {
        }

        Label getHandlerLabel() {
            return this.handlerLabel;
        }

        boolean hasTargetLabel() {
            return this.targetLabel != null;
        }

        Label getTargetLabel() {
            return this.targetLabel;
        }

        void setTargetLabel(Label label) {
            this.targetLabel = label;
        }

        int[] getStackStoreSpec() {
            return (int[]) this.stackStoreSpec.clone();
        }

        void setStackStoreSpec(int[] iArr) {
            this.stackStoreSpec = iArr;
        }

        Type[] getStackTypes() {
            return (Type[]) this.stackTypes.clone();
        }

        void setStackTypes(Type[] typeArr) {
            this.stackTypes = typeArr;
        }

        Type getReturnValueType() {
            return this.returnValueType;
        }

        void setReturnValueType(Type type) {
            this.returnValueType = type;
        }

        void setObjectLiteralMap(int i, PropertyMap propertyMap) {
            if (this.objectLiteralMaps == null) {
                this.objectLiteralMaps = new HashMap();
            }
            this.objectLiteralMaps.put(Integer.valueOf(i), propertyMap);
        }

        PropertyMap getObjectLiteralMap(int i) {
            if (this.objectLiteralMaps == null) {
                return null;
            }
            return (PropertyMap) this.objectLiteralMaps.get(Integer.valueOf(i));
        }

        public String toString() {
            return "[localVariableTypes=" + this.targetLabel.getStack().getLocalVariableTypesCopy() + ", stackStoreSpec=" + Arrays.toString(this.stackStoreSpec) + ", returnValueType=" + this.returnValueType + "]";
        }
    }

    private ContinuationInfo getContinuationInfo() {
        return this.continuationInfo;
    }

    private void generateContinuationHandler() {
        if (!isRestOf()) {
            return;
        }
        ContinuationInfo ci = getContinuationInfo();
        this.method.label(ci.getHandlerLabel());
        this.method.lineNumber(0);
        Label.Stack stack = ci.getTargetLabel().getStack();
        List<Type> lvarTypes = stack.getLocalVariableTypesCopy();
        BitSet symbolBoundary = stack.getSymbolBoundaryCopy();
        int lvarCount = ci.lvarCount;
        Type rewriteExceptionType = Type.typeFor((Class<?>) RewriteException.class);
        this.method.load(rewriteExceptionType, 0);
        this.method.storeTemp(rewriteExceptionType, lvarCount);
        this.method.load(rewriteExceptionType, 0);
        this.method.invoke(RewriteException.GET_BYTECODE_SLOTS);
        int arrayIndex = 0;
        int i = 0;
        while (true) {
            int lvarIndex = i;
            if (lvarIndex >= lvarCount) {
                break;
            }
            Type lvarType = lvarTypes.get(lvarIndex);
            if (!lvarType.isUnknown()) {
                this.method.dup();
                this.method.load(arrayIndex).arrayload();
                Class<?> typeClass = lvarType.getTypeClass();
                if (typeClass == long[].class) {
                    this.method.load(rewriteExceptionType, lvarCount);
                    this.method.invoke(RewriteException.TO_LONG_ARRAY);
                } else if (typeClass == double[].class) {
                    this.method.load(rewriteExceptionType, lvarCount);
                    this.method.invoke(RewriteException.TO_DOUBLE_ARRAY);
                } else if (typeClass == Object[].class) {
                    this.method.load(rewriteExceptionType, lvarCount);
                    this.method.invoke(RewriteException.TO_OBJECT_ARRAY);
                } else {
                    if (!typeClass.isPrimitive() && typeClass != Object.class) {
                        this.method.loadType(Type.getInternalName(typeClass));
                        this.method.invoke(RewriteException.INSTANCE_OR_NULL);
                    }
                    this.method.convert(lvarType);
                }
                this.method.storeHidden(lvarType, lvarIndex, false);
            }
            int nextLvarIndex = lvarIndex + lvarType.getSlots();
            if (symbolBoundary.get(nextLvarIndex - 1)) {
                arrayIndex++;
            }
            i = nextLvarIndex;
        }
        if (AssertsEnabled.assertsEnabled()) {
            this.method.load(arrayIndex);
            this.method.invoke(RewriteException.ASSERT_ARRAY_LENGTH);
        } else {
            this.method.pop();
        }
        int[] stackStoreSpec = ci.getStackStoreSpec();
        Type[] stackTypes = ci.getStackTypes();
        boolean isStackEmpty = stackStoreSpec.length == 0;
        int replacedObjectLiteralMaps = 0;
        if (!isStackEmpty) {
            for (int i2 = 0; i2 < stackStoreSpec.length; i2++) {
                int slot = stackStoreSpec[i2];
                this.method.load(lvarTypes.get(slot), slot);
                this.method.convert(stackTypes[i2]);
                PropertyMap map = ci.getObjectLiteralMap(i2);
                if (map != null) {
                    this.method.dup();
                    if (!$assertionsDisabled && !ScriptObject.class.isAssignableFrom(this.method.peekType().getTypeClass())) {
                        throw new AssertionError(this.method.peekType().getTypeClass() + " is not a script object");
                    }
                    loadConstant(map);
                    this.method.invoke(ScriptObject.SET_MAP);
                    replacedObjectLiteralMaps++;
                }
            }
        }
        if (!$assertionsDisabled && ci.objectLiteralMaps != null && ci.objectLiteralMaps.size() != replacedObjectLiteralMaps) {
            throw new AssertionError();
        }
        this.method.load(rewriteExceptionType, lvarCount);
        this.method.loadNull();
        this.method.storeHidden(Type.OBJECT, lvarCount);
        this.method.markDeadSlots(lvarCount, Type.OBJECT.getSlots());
        this.method.invoke(RewriteException.GET_RETURN_VALUE);
        Type returnValueType = ci.getReturnValueType();
        boolean needsCatch = false;
        Label targetCatchLabel = ci.catchLabel;
        Label _try = null;
        if (returnValueType.isPrimitive()) {
            this.method.lineNumber(ci.lineNumber);
            if (targetCatchLabel != METHOD_BOUNDARY) {
                _try = new Label("");
                this.method.label(_try);
                needsCatch = true;
            }
        }
        this.method.convert(returnValueType);
        int scopePopCount = needsCatch ? ci.exceptionScopePops : 0;
        Label catchLabel = scopePopCount > 0 ? new Label("") : targetCatchLabel;
        if (needsCatch) {
            Label _end_try = new Label("");
            this.method.label(_end_try);
            this.method._try(_try, _end_try, catchLabel);
        }
        this.method._goto(ci.getTargetLabel());
        if (catchLabel != targetCatchLabel) {
            this.method.lineNumber(0);
            if (!$assertionsDisabled && scopePopCount <= 0) {
                throw new AssertionError();
            }
            this.method._catch(catchLabel);
            popScopes(scopePopCount);
            this.method.uncheckedGoto(targetCatchLabel);
        }
    }
}
