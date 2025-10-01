package jdk.nashorn.internal.codegen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import jdk.nashorn.internal.p001ir.AccessNode;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.CatchNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.ForNode;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.IndexNode;
import jdk.nashorn.internal.p001ir.LexicalContextNode;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.RuntimeNode;
import jdk.nashorn.internal.p001ir.Splittable;
import jdk.nashorn.internal.p001ir.SwitchNode;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.p001ir.TryNode;
import jdk.nashorn.internal.p001ir.UnaryNode;
import jdk.nashorn.internal.p001ir.VarNode;
import jdk.nashorn.internal.p001ir.WithNode;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.ECMAErrors;
import jdk.nashorn.internal.runtime.ErrorManager;
import jdk.nashorn.internal.runtime.JSErrorType;
import jdk.nashorn.internal.runtime.ParserException;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(name = "symbols")
/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/AssignSymbols.class */
final class AssignSymbols extends SimpleNodeVisitor implements Loggable {
    private final DebugLogger log;
    private final boolean debug;
    private final Deque thisProperties = new ArrayDeque();
    private final Map globalSymbols = new HashMap();
    private final Compiler compiler;
    private final boolean isOnDemand;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !AssignSymbols.class.desiredAssertionStatus();
    }

    private static boolean isParamOrVar(IdentNode identNode) {
        Symbol symbol = identNode.getSymbol();
        return symbol.isParam() || symbol.isVar();
    }

    private static String name(Node node) {
        String name = node.getClass().getName();
        int iLastIndexOf = name.lastIndexOf(46);
        if (iLastIndexOf == -1) {
            return name;
        }
        return name.substring(iLastIndexOf + 1);
    }

    private static FunctionNode removeUnusedSlots(FunctionNode functionNode) {
        Symbol existingSymbol;
        if (!functionNode.needsCallee()) {
            functionNode.compilerConstant(CompilerConstants.CALLEE).setNeedsSlot(false);
        }
        if (!functionNode.hasScopeBlock() && !functionNode.needsParentScope()) {
            functionNode.compilerConstant(CompilerConstants.SCOPE).setNeedsSlot(false);
        }
        if (functionNode.isNamedFunctionExpression() && !functionNode.usesSelfSymbol() && (existingSymbol = functionNode.getBody().getExistingSymbol(functionNode.getIdent().getName())) != null && existingSymbol.isFunctionSelf()) {
            existingSymbol.setNeedsSlot(false);
            existingSymbol.clearFlag(2);
        }
        return functionNode;
    }

    public AssignSymbols(Compiler compiler) {
        this.compiler = compiler;
        this.log = initLogger(compiler.getContext());
        this.debug = this.log.isEnabled();
        this.isOnDemand = compiler.isOnDemandCompilation();
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

    private void acceptDeclarations(FunctionNode functionNode, Block block) {
        block.accept(new SimpleNodeVisitor(this, block) { // from class: jdk.nashorn.internal.codegen.AssignSymbols.1
            final Block val$body;
            final AssignSymbols this$0;

            {
                this.this$0 = this;
                this.val$body = block;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveVarNode(VarNode varNode) {
                IdentNode name = varNode.getName();
                boolean zIsBlockScoped = varNode.isBlockScoped();
                if (zIsBlockScoped && this.f30lc.inUnprotectedSwitchContext()) {
                    this.this$0.throwUnprotectedSwitchError(varNode);
                }
                Symbol symbolDefineSymbol = this.this$0.defineSymbol(zIsBlockScoped ? this.f30lc.getCurrentBlock() : this.val$body, name.getName(), name, varNode.getSymbolFlags());
                if (varNode.isFunctionDeclaration()) {
                    symbolDefineSymbol.setIsFunctionDeclaration();
                }
                return varNode.setName(name.setSymbol(symbolDefineSymbol));
            }
        });
    }

    private IdentNode compilerConstantIdentifier(CompilerConstants compilerConstants) {
        return createImplicitIdentifier(compilerConstants.symbolName()).setSymbol(this.f30lc.getCurrentFunction().compilerConstant(compilerConstants));
    }

    private IdentNode createImplicitIdentifier(String str) {
        FunctionNode currentFunction = this.f30lc.getCurrentFunction();
        return new IdentNode(currentFunction.getToken(), currentFunction.getFinish(), str);
    }

    private Symbol createSymbol(String str, int i) {
        if ((i & 3) == 1) {
            Symbol symbol = (Symbol) this.globalSymbols.get(str);
            if (symbol == null) {
                symbol = new Symbol(str, i);
                this.globalSymbols.put(str, symbol);
            }
            return symbol;
        }
        return new Symbol(str, i);
    }

    private VarNode createSyntheticInitializer(IdentNode identNode, CompilerConstants compilerConstants, FunctionNode functionNode) {
        IdentNode identNodeCompilerConstantIdentifier = compilerConstantIdentifier(compilerConstants);
        if (!$assertionsDisabled && (identNodeCompilerConstantIdentifier.getSymbol() == null || !identNodeCompilerConstantIdentifier.getSymbol().isBytecodeLocal())) {
            throw new AssertionError();
        }
        VarNode varNode = new VarNode(functionNode.getLineNumber(), functionNode.getToken(), functionNode.getFinish(), identNode, identNodeCompilerConstantIdentifier);
        Symbol existingSymbol = functionNode.getBody().getExistingSymbol(identNode.getName());
        if ($assertionsDisabled || existingSymbol != null) {
            return (VarNode) varNode.setName(identNode.setSymbol(existingSymbol)).accept(this);
        }
        throw new AssertionError();
    }

    private FunctionNode createSyntheticInitializers(FunctionNode functionNode) {
        ArrayList arrayList = new ArrayList(2);
        Block body = functionNode.getBody();
        this.f30lc.push(body);
        try {
            if (functionNode.usesSelfSymbol()) {
                arrayList.add(createSyntheticInitializer(functionNode.getIdent(), CompilerConstants.CALLEE, functionNode));
            }
            if (functionNode.needsArguments()) {
                arrayList.add(createSyntheticInitializer(createImplicitIdentifier(CompilerConstants.ARGUMENTS_VAR.symbolName()), CompilerConstants.ARGUMENTS, functionNode));
            }
            if (arrayList.isEmpty()) {
                return functionNode;
            }
            ListIterator listIterator = arrayList.listIterator();
            while (listIterator.hasNext()) {
                listIterator.set((VarNode) ((VarNode) listIterator.next()).accept(this));
            }
            List statements = body.getStatements();
            ArrayList arrayList2 = new ArrayList(statements.size() + arrayList.size());
            arrayList2.addAll(arrayList);
            arrayList2.addAll(statements);
            return functionNode.setBody(this.f30lc, body.setStatements(this.f30lc, arrayList2));
        } finally {
            this.f30lc.pop(body);
        }
    }

    private Symbol defineSymbol(Block block, String str, Node node, int i) {
        Symbol symbolFindSymbol;
        FunctionNode function;
        Block functionBody;
        int i2 = i;
        boolean z = ((i2 & 16) == 0 && (i2 & 32) == 0) ? false : true;
        boolean z2 = (i2 & 3) == 1;
        if (z) {
            symbolFindSymbol = block.getExistingSymbol(str);
            function = this.f30lc.getCurrentFunction();
        } else {
            symbolFindSymbol = findSymbol(block, str);
            function = this.f30lc.getFunction(block);
        }
        if (z2) {
            i2 |= 4;
        }
        if (this.f30lc.getCurrentFunction().isProgram()) {
            i2 |= 512;
        }
        boolean z3 = (i2 & 3) == 3;
        boolean z4 = (i2 & 3) == 2;
        if (symbolFindSymbol != null) {
            if (z3) {
                if (!isLocal(function, symbolFindSymbol)) {
                    symbolFindSymbol = null;
                } else if (symbolFindSymbol.isParam()) {
                    throw new AssertionError("duplicate parameter");
                }
            } else if (z4) {
                if (z) {
                    if (symbolFindSymbol.hasBeenDeclared()) {
                        throwParserException(ECMAErrors.getMessage("syntax.error.redeclare.variable", new String[]{str}), node);
                    } else {
                        symbolFindSymbol.setHasBeenDeclared();
                        if (function.isProgram() && function.getBody() == block) {
                            symbolFindSymbol.setIsScope();
                        }
                    }
                } else if ((i2 & 64) != 0) {
                    symbolFindSymbol = null;
                } else {
                    if (symbolFindSymbol.isBlockScoped() && isLocal(this.f30lc.getCurrentFunction(), symbolFindSymbol)) {
                        throwParserException(ECMAErrors.getMessage("syntax.error.redeclare.variable", new String[]{str}), node);
                    }
                    if (!isLocal(function, symbolFindSymbol) || symbolFindSymbol.less(2)) {
                        symbolFindSymbol = null;
                    }
                }
            }
        }
        if (symbolFindSymbol == null) {
            if (z4 && ((i2 & 64) != 0 || z)) {
                functionBody = block;
            } else if (z2) {
                functionBody = this.f30lc.getOutermostFunction().getBody();
            } else {
                functionBody = this.f30lc.getFunctionBody(function);
            }
            symbolFindSymbol = createSymbol(str, i2);
            functionBody.putSymbol(symbolFindSymbol);
            if ((i2 & 4) == 0) {
                symbolFindSymbol.setNeedsSlot(true);
            }
        } else if (symbolFindSymbol.less(i2)) {
            symbolFindSymbol.setFlags(i2);
        }
        return symbolFindSymbol;
    }

    private Node end(Node node) {
        return end(node, true);
    }

    private Node end(Node node, boolean z) {
        if (this.debug) {
            StringBuilder sb = new StringBuilder();
            sb.append("[LEAVE ").append(name(node)).append("] ").append(z ? node.toString() : "").append(" in '").append(this.f30lc.getCurrentFunction().getName()).append('\'');
            if (node instanceof IdentNode) {
                Symbol symbol = ((IdentNode) node).getSymbol();
                if (symbol == null) {
                    sb.append(" <NO SYMBOL>");
                } else {
                    sb.append(" <symbol=").append(symbol).append('>');
                }
            }
            this.log.unindent();
            this.log.info(new Object[]{sb});
        }
        return node;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBlock(Block block) {
        start(block);
        if (this.f30lc.isFunctionBody()) {
            if (!$assertionsDisabled && block.hasSymbols()) {
                throw new AssertionError();
            }
            FunctionNode currentFunction = this.f30lc.getCurrentFunction();
            if (isUnparsedFunction(currentFunction)) {
                Iterator it = this.compiler.getScriptFunctionData(currentFunction.getId()).getExternalSymbolNames().iterator();
                while (it.hasNext()) {
                    nameIsUsed((String) it.next(), null);
                }
                if ($assertionsDisabled || block.getStatements().isEmpty()) {
                    return false;
                }
                throw new AssertionError();
            }
            enterFunctionBody();
            return true;
        }
        return true;
    }

    private boolean isUnparsedFunction(FunctionNode functionNode) {
        return this.isOnDemand && functionNode != this.f30lc.getOutermostFunction();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterCatchNode(CatchNode catchNode) {
        IdentNode exception = catchNode.getException();
        Block currentBlock = this.f30lc.getCurrentBlock();
        start(catchNode);
        String name = exception.getName();
        defineSymbol(currentBlock, name, catchNode, 18 | (name.startsWith(CompilerConstants.EXCEPTION_PREFIX.symbolName()) ? 64 : 0) | 8192).clearFlag(16);
        return true;
    }

    private void enterFunctionBody() {
        FunctionNode currentFunction = this.f30lc.getCurrentFunction();
        Block currentBlock = this.f30lc.getCurrentBlock();
        initFunctionWideVariables(currentFunction, currentBlock);
        acceptDeclarations(currentFunction, currentBlock);
        defineFunctionSelfSymbol(currentFunction, currentBlock);
    }

    private void defineFunctionSelfSymbol(FunctionNode functionNode, Block block) {
        if (!functionNode.isNamedFunctionExpression()) {
            return;
        }
        String name = functionNode.getIdent().getName();
        if (!$assertionsDisabled && name == null) {
            throw new AssertionError();
        }
        if (block.getExistingSymbol(name) != null) {
            return;
        }
        defineSymbol(block, name, functionNode, 8322);
        if (functionNode.allVarsInScope()) {
            this.f30lc.setFlag(functionNode, 16384);
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterFunctionNode(FunctionNode functionNode) {
        start(functionNode, false);
        this.thisProperties.push(new HashSet());
        if ($assertionsDisabled || functionNode.getBody() != null) {
            return true;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterVarNode(VarNode varNode) {
        start(varNode);
        if (varNode.isFunctionDeclaration()) {
            defineVarIdent(varNode);
            return true;
        }
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveVarNode(VarNode varNode) {
        if (!varNode.isFunctionDeclaration()) {
            defineVarIdent(varNode);
        }
        return super.leaveVarNode(varNode);
    }

    private void defineVarIdent(VarNode varNode) {
        int i;
        IdentNode name = varNode.getName();
        if (!varNode.isBlockScoped() && this.f30lc.getCurrentFunction().isProgram()) {
            i = 4;
        } else {
            i = 0;
        }
        defineSymbol(this.f30lc.getCurrentBlock(), name.getName(), name, varNode.getSymbolFlags() | i);
    }

    private Symbol exceptionSymbol() {
        return newObjectInternal(CompilerConstants.EXCEPTION_PREFIX);
    }

    private FunctionNode finalizeParameters(FunctionNode functionNode) {
        ArrayList arrayList = new ArrayList();
        boolean zIsVarArg = functionNode.isVarArg();
        Block body = functionNode.getBody();
        for (IdentNode identNode : functionNode.getParameters()) {
            Symbol existingSymbol = body.getExistingSymbol(identNode.getName());
            if (!$assertionsDisabled && existingSymbol == null) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && !existingSymbol.isParam()) {
                throw new AssertionError(existingSymbol + " " + existingSymbol.getFlags());
            }
            arrayList.add(identNode.setSymbol(existingSymbol));
            if (zIsVarArg) {
                existingSymbol.setNeedsSlot(false);
            }
        }
        return functionNode.setParameters(this.f30lc, arrayList);
    }

    private Symbol findSymbol(Block block, String str) {
        Iterator blocks = this.f30lc.getBlocks(block);
        while (blocks.hasNext()) {
            Symbol existingSymbol = ((Block) blocks.next()).getExistingSymbol(str);
            if (existingSymbol != null) {
                return existingSymbol;
            }
        }
        return null;
    }

    private void functionUsesGlobalSymbol() {
        Iterator functions = this.f30lc.getFunctions();
        while (functions.hasNext()) {
            this.f30lc.setFlag((LexicalContextNode) functions.next(), 512);
        }
    }

    private void functionUsesScopeSymbol(Symbol symbol) {
        String name = symbol.getName();
        Iterator allNodes = this.f30lc.getAllNodes();
        while (allNodes.hasNext()) {
            LexicalContextNode lexicalContextNode = (LexicalContextNode) allNodes.next();
            if (lexicalContextNode instanceof Block) {
                Block block = (Block) lexicalContextNode;
                if (block.getExistingSymbol(name) != null) {
                    if (!$assertionsDisabled && !this.f30lc.contains(block)) {
                        throw new AssertionError();
                    }
                    this.f30lc.setBlockNeedsScope(block);
                    return;
                }
            } else if (lexicalContextNode instanceof FunctionNode) {
                this.f30lc.setFlag(lexicalContextNode, 512);
            }
        }
    }

    private void functionUsesSymbol(Symbol symbol) {
        if (!$assertionsDisabled && symbol == null) {
            throw new AssertionError();
        }
        if (symbol.isScope()) {
            if (symbol.isGlobal()) {
                functionUsesGlobalSymbol();
                return;
            } else {
                functionUsesScopeSymbol(symbol);
                return;
            }
        }
        if (!$assertionsDisabled && symbol.isGlobal()) {
            throw new AssertionError();
        }
    }

    private void initCompileConstant(CompilerConstants compilerConstants, Block block, int i) {
        defineSymbol(block, compilerConstants.symbolName(), null, i).setNeedsSlot(true);
    }

    private void initFunctionWideVariables(FunctionNode functionNode, Block block) {
        initCompileConstant(CompilerConstants.CALLEE, block, 8259);
        initCompileConstant(CompilerConstants.THIS, block, 8203);
        if (functionNode.isVarArg()) {
            initCompileConstant(CompilerConstants.VARARGS, block, 8259);
            if (functionNode.needsArguments()) {
                initCompileConstant(CompilerConstants.ARGUMENTS, block, 8258);
                defineSymbol(block, CompilerConstants.ARGUMENTS_VAR.symbolName(), null, 8194);
            }
        }
        initParameters(functionNode, block);
        initCompileConstant(CompilerConstants.SCOPE, block, 8258);
        initCompileConstant(CompilerConstants.RETURN, block, 66);
    }

    private void initParameters(FunctionNode functionNode, Block block) {
        boolean zIsVarArg = functionNode.isVarArg();
        boolean z = functionNode.allVarsInScope() || zIsVarArg;
        for (IdentNode identNode : functionNode.getParameters()) {
            Symbol symbolDefineSymbol = defineSymbol(block, identNode.getName(), identNode, 3);
            if (z) {
                symbolDefineSymbol.setIsScope();
                if (!$assertionsDisabled && !symbolDefineSymbol.hasSlot()) {
                    throw new AssertionError();
                }
                if (zIsVarArg) {
                    symbolDefineSymbol.setNeedsSlot(false);
                }
            }
        }
    }

    private boolean isLocal(FunctionNode functionNode, Symbol symbol) {
        FunctionNode definingFunction = this.f30lc.getDefiningFunction(symbol);
        if ($assertionsDisabled || definingFunction != null) {
            return definingFunction == functionNode;
        }
        throw new AssertionError();
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveBinaryNode(BinaryNode binaryNode) {
        if (binaryNode.isTokenType(TokenType.ASSIGN)) {
            return leaveASSIGN(binaryNode);
        }
        return super.leaveBinaryNode(binaryNode);
    }

    private Node leaveASSIGN(BinaryNode binaryNode) {
        Expression expressionLhs = binaryNode.lhs();
        if (expressionLhs instanceof AccessNode) {
            AccessNode accessNode = (AccessNode) expressionLhs;
            Expression base = accessNode.getBase();
            if ((base instanceof IdentNode) && ((IdentNode) base).getSymbol().isThis()) {
                ((Set) this.thisProperties.peek()).add(accessNode.getProperty());
            }
        }
        return binaryNode;
    }

    /* renamed from: jdk.nashorn.internal.codegen.AssignSymbols$2 */
    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/AssignSymbols$2.class */
    static /* synthetic */ class C00642 {
        static final int[] $SwitchMap$jdk$nashorn$internal$parser$TokenType = new int[TokenType.values().length];

        static {
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.DELETE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$jdk$nashorn$internal$parser$TokenType[TokenType.TYPEOF.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveUnaryNode(UnaryNode unaryNode) {
        switch (C00642.$SwitchMap$jdk$nashorn$internal$parser$TokenType[unaryNode.tokenType().ordinal()]) {
            case 1:
                return leaveDELETE(unaryNode);
            case 2:
                return leaveTYPEOF(unaryNode);
            default:
                return super.leaveUnaryNode(unaryNode);
        }
    }

    private Node leaveDELETE(UnaryNode unaryNode) {
        boolean zIsStrict = this.f30lc.getCurrentFunction().isStrict();
        Expression expression = unaryNode.getExpression();
        Expression expression2 = (Expression) LiteralNode.newInstance(unaryNode, zIsStrict).accept(this);
        RuntimeNode.Request request = RuntimeNode.Request.DELETE;
        ArrayList arrayList = new ArrayList();
        if (expression instanceof IdentNode) {
            IdentNode identNode = (IdentNode) expression;
            String name = identNode.getName();
            Symbol symbol = identNode.getSymbol();
            if (symbol.isThis()) {
                return LiteralNode.newInstance((Node) unaryNode, true);
            }
            LiteralNode literalNodeNewInstance = LiteralNode.newInstance(unaryNode, name);
            boolean z = zIsStrict || (!symbol.isScope() && (symbol.isParam() || (symbol.isVar() && !symbol.isProgramLevel())));
            if (!z) {
                arrayList.add(compilerConstantIdentifier(CompilerConstants.SCOPE));
            }
            arrayList.add(literalNodeNewInstance);
            arrayList.add(expression2);
            if (z) {
                request = RuntimeNode.Request.FAIL_DELETE;
            } else if ((symbol.isGlobal() && !symbol.isFunctionDeclaration()) || symbol.isProgramLevel()) {
                request = RuntimeNode.Request.SLOW_DELETE;
            }
        } else if (expression instanceof AccessNode) {
            Expression base = ((AccessNode) expression).getBase();
            String property = ((AccessNode) expression).getProperty();
            arrayList.add(base);
            arrayList.add(LiteralNode.newInstance(unaryNode, property));
            arrayList.add(expression2);
        } else if (expression instanceof IndexNode) {
            IndexNode indexNode = (IndexNode) expression;
            Expression base2 = indexNode.getBase();
            Expression index = indexNode.getIndex();
            arrayList.add(base2);
            arrayList.add(index);
            arrayList.add(expression2);
        } else {
            return LiteralNode.newInstance((Node) unaryNode, true);
        }
        return new RuntimeNode(unaryNode, request, arrayList);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveForNode(ForNode forNode) {
        if (forNode.isForIn()) {
            return forNode.setIterator(this.f30lc, newObjectInternal(CompilerConstants.ITERATOR_PREFIX));
        }
        return end(forNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveFunctionNode(FunctionNode functionNode) {
        FunctionNode functionNodeMarkProgramBlock;
        if (isUnparsedFunction(functionNode)) {
            functionNodeMarkProgramBlock = functionNode;
        } else {
            functionNodeMarkProgramBlock = markProgramBlock(removeUnusedSlots(createSyntheticInitializers(finalizeParameters((FunctionNode) this.f30lc.applyTopFlags(functionNode)))).setThisProperties(this.f30lc, ((Set) this.thisProperties.pop()).size()));
        }
        return functionNodeMarkProgramBlock;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveIdentNode(IdentNode identNode) {
        if (identNode.isPropertyName()) {
            return identNode;
        }
        Symbol symbolNameIsUsed = nameIsUsed(identNode.getName(), identNode);
        if (!identNode.isInitializedHere()) {
            symbolNameIsUsed.increaseUseCount();
        }
        IdentNode symbol = identNode.setSymbol(symbolNameIsUsed);
        if (symbolNameIsUsed.isBlockScoped() && !symbolNameIsUsed.hasBeenDeclared() && !identNode.isDeclaredHere() && isLocal(this.f30lc.getCurrentFunction(), symbolNameIsUsed)) {
            symbol = symbol.markDead();
        }
        return end(symbol);
    }

    private Symbol nameIsUsed(String str, IdentNode identNode) {
        Block currentBlock = this.f30lc.getCurrentBlock();
        Symbol symbolFindSymbol = findSymbol(currentBlock, str);
        if (symbolFindSymbol != null) {
            this.log.info(new Object[]{"Existing symbol = ", symbolFindSymbol});
            if (symbolFindSymbol.isFunctionSelf()) {
                FunctionNode definingFunction = this.f30lc.getDefiningFunction(symbolFindSymbol);
                if (!$assertionsDisabled && definingFunction == null) {
                    throw new AssertionError();
                }
                if (!$assertionsDisabled && this.f30lc.getFunctionBody(definingFunction).getExistingSymbol(CompilerConstants.CALLEE.symbolName()) == null) {
                    throw new AssertionError();
                }
                this.f30lc.setFlag(definingFunction, 16384);
            }
            maybeForceScope(symbolFindSymbol);
        } else {
            this.log.info(new Object[]{"No symbol exists. Declare as global: ", str});
            symbolFindSymbol = defineSymbol(currentBlock, str, identNode, 5);
        }
        functionUsesSymbol(symbolFindSymbol);
        return symbolFindSymbol;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveSwitchNode(SwitchNode switchNode) {
        if (!switchNode.isUniqueInteger()) {
            return switchNode.setTag(this.f30lc, newObjectInternal(CompilerConstants.SWITCH_TAG_PREFIX));
        }
        return switchNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveTryNode(TryNode tryNode) {
        if (!$assertionsDisabled && tryNode.getFinallyBody() != null) {
            throw new AssertionError();
        }
        end(tryNode);
        return tryNode.setException(this.f30lc, exceptionSymbol());
    }

    private Node leaveTYPEOF(UnaryNode unaryNode) {
        Expression expression = unaryNode.getExpression();
        ArrayList arrayList = new ArrayList();
        if ((expression instanceof IdentNode) && !isParamOrVar((IdentNode) expression)) {
            arrayList.add(compilerConstantIdentifier(CompilerConstants.SCOPE));
            arrayList.add(LiteralNode.newInstance(expression, ((IdentNode) expression).getName()));
        } else {
            arrayList.add(expression);
            arrayList.add(LiteralNode.newInstance(unaryNode));
        }
        RuntimeNode runtimeNode = new RuntimeNode(unaryNode, RuntimeNode.Request.TYPEOF, arrayList);
        end(unaryNode);
        return runtimeNode;
    }

    private FunctionNode markProgramBlock(FunctionNode functionNode) {
        if (this.isOnDemand || !functionNode.isProgram()) {
            return functionNode;
        }
        return functionNode.setBody(this.f30lc, functionNode.getBody().setFlag(this.f30lc, 8));
    }

    private void maybeForceScope(Symbol symbol) {
        if (!symbol.isScope() && symbolNeedsToBeScope(symbol)) {
            Symbol.setSymbolIsScope(this.f30lc, symbol);
        }
    }

    private Symbol newInternal(CompilerConstants compilerConstants, int i) {
        return defineSymbol(this.f30lc.getCurrentBlock(), this.f30lc.getCurrentFunction().uniqueName(compilerConstants.symbolName()), null, 66 | i);
    }

    private Symbol newObjectInternal(CompilerConstants compilerConstants) {
        return newInternal(compilerConstants, 8192);
    }

    private boolean start(Node node) {
        return start(node, true);
    }

    private boolean start(Node node, boolean z) {
        if (this.debug) {
            StringBuilder sb = new StringBuilder();
            sb.append("[ENTER ").append(name(node)).append("] ").append(z ? node.toString() : "").append(" in '").append(this.f30lc.getCurrentFunction().getName()).append("'");
            this.log.info(new Object[]{sb});
            this.log.indent();
            return true;
        }
        return true;
    }

    private boolean symbolNeedsToBeScope(Symbol symbol) {
        if (symbol.isThis() || symbol.isInternal()) {
            return false;
        }
        FunctionNode currentFunction = this.f30lc.getCurrentFunction();
        if (currentFunction.allVarsInScope()) {
            return true;
        }
        if (!symbol.isBlockScoped() && currentFunction.isProgram()) {
            return true;
        }
        boolean z = false;
        Iterator allNodes = this.f30lc.getAllNodes();
        while (allNodes.hasNext()) {
            LexicalContextNode lexicalContextNode = (LexicalContextNode) allNodes.next();
            if ((lexicalContextNode instanceof FunctionNode) || isSplitLiteral(lexicalContextNode)) {
                return true;
            }
            if (lexicalContextNode instanceof WithNode) {
                if (z) {
                    return true;
                }
                z = false;
            } else if (lexicalContextNode instanceof Block) {
                if (((Block) lexicalContextNode).getExistingSymbol(symbol.getName()) == symbol) {
                    return false;
                }
                z = true;
            } else {
                z = false;
            }
        }
        throw new AssertionError();
    }

    private static boolean isSplitLiteral(LexicalContextNode lexicalContextNode) {
        return (lexicalContextNode instanceof Splittable) && ((Splittable) lexicalContextNode).getSplitRanges() != null;
    }

    private void throwUnprotectedSwitchError(VarNode varNode) {
        String[] strArr = new String[1];
        strArr[0] = varNode.isLet() ? "let" : "const";
        throwParserException(ECMAErrors.getMessage("syntax.error.unprotected.switch.declaration", strArr), varNode);
    }

    private void throwParserException(String str, Node node) {
        if (node == null) {
            throw new ParserException(str);
        }
        Source source = this.compiler.getSource();
        long token = node.getToken();
        int line = source.getLine(node.getStart());
        int column = source.getColumn(node.getStart());
        throw new ParserException(JSErrorType.SYNTAX_ERROR, ErrorManager.format(str, source, line, column, token), source, line, column, token);
    }
}
