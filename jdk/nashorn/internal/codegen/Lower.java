package jdk.nashorn.internal.codegen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Pattern;
import jdk.nashorn.internal.p001ir.AccessNode;
import jdk.nashorn.internal.p001ir.BaseNode;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.BlockLexicalContext;
import jdk.nashorn.internal.p001ir.BlockStatement;
import jdk.nashorn.internal.p001ir.BreakNode;
import jdk.nashorn.internal.p001ir.CallNode;
import jdk.nashorn.internal.p001ir.CaseNode;
import jdk.nashorn.internal.p001ir.CatchNode;
import jdk.nashorn.internal.p001ir.ContinueNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.ExpressionStatement;
import jdk.nashorn.internal.p001ir.ForNode;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.IfNode;
import jdk.nashorn.internal.p001ir.IndexNode;
import jdk.nashorn.internal.p001ir.JoinPredecessorExpression;
import jdk.nashorn.internal.p001ir.JumpStatement;
import jdk.nashorn.internal.p001ir.JumpToInlinedFinally;
import jdk.nashorn.internal.p001ir.LabelNode;
import jdk.nashorn.internal.p001ir.LexicalContext;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.LoopNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.ReturnNode;
import jdk.nashorn.internal.p001ir.RuntimeNode;
import jdk.nashorn.internal.p001ir.Statement;
import jdk.nashorn.internal.p001ir.SwitchNode;
import jdk.nashorn.internal.p001ir.Symbol;
import jdk.nashorn.internal.p001ir.ThrowNode;
import jdk.nashorn.internal.p001ir.TryNode;
import jdk.nashorn.internal.p001ir.VarNode;
import jdk.nashorn.internal.p001ir.WhileNode;
import jdk.nashorn.internal.p001ir.WithNode;
import jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor;
import jdk.nashorn.internal.p001ir.visitor.SimpleNodeVisitor;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenType;
import jdk.nashorn.internal.runtime.Context;
import jdk.nashorn.internal.runtime.Source;
import jdk.nashorn.internal.runtime.logging.DebugLogger;
import jdk.nashorn.internal.runtime.logging.Loggable;
import jdk.nashorn.internal.runtime.logging.Logger;

@Logger(name = "lower")
/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/Lower.class */
final class Lower extends NodeOperatorVisitor implements Loggable {
    private final DebugLogger log;
    private static Pattern SAFE_PROPERTY_NAME;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !Lower.class.desiredAssertionStatus();
        SAFE_PROPERTY_NAME = Pattern.compile("[a-zA-Z_$][\\w$]*");
    }

    Lower(Compiler compiler) {
        super(new BlockLexicalContext() { // from class: jdk.nashorn.internal.codegen.Lower.1
            @Override // jdk.nashorn.internal.p001ir.BlockLexicalContext
            public List<Statement> popStatements() {
                List<Statement> newStatements = new ArrayList<>();
                boolean terminated = false;
                List<Statement> statements = super.popStatements();
                for (Statement statement : statements) {
                    if (!terminated) {
                        newStatements.add(statement);
                        if (statement.isTerminal() || (statement instanceof JumpStatement)) {
                            terminated = true;
                        }
                    } else {
                        FoldConstants.extractVarNodesFromDeadCode(statement, newStatements);
                    }
                }
                return newStatements;
            }

            @Override // jdk.nashorn.internal.p001ir.BlockLexicalContext
            protected Block afterSetStatements(Block block) {
                List<Statement> stmts = block.getStatements();
                ListIterator<Statement> li = stmts.listIterator(stmts.size());
                while (li.hasPrevious()) {
                    Statement stmt = li.previous();
                    if (!(stmt instanceof VarNode) || ((VarNode) stmt).getInit() != null) {
                        return block.setIsTerminal(this, stmt.isTerminal());
                    }
                }
                return block.setIsTerminal(this, false);
            }
        });
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

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterBreakNode(BreakNode breakNode) {
        addStatement(breakNode);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveCallNode(CallNode callNode) {
        return checkEval(callNode.setFunction(markerFunction(callNode.getFunction())));
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveCatchNode(CatchNode catchNode) {
        return addStatement(catchNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterContinueNode(ContinueNode continueNode) {
        addStatement(continueNode);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterJumpToInlinedFinally(JumpToInlinedFinally jumpToInlinedFinally) {
        addStatement(jumpToInlinedFinally);
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveIndexNode(IndexNode indexNode) {
        String constantPropertyName = getConstantPropertyName(indexNode.getIndex());
        if (constantPropertyName != null) {
            if ($assertionsDisabled || indexNode.isIndex()) {
                return new AccessNode(indexNode.getToken(), indexNode.getFinish(), indexNode.getBase(), constantPropertyName);
            }
            throw new AssertionError();
        }
        return super.leaveIndexNode(indexNode);
    }

    private static String getConstantPropertyName(Expression expression) {
        if (expression instanceof LiteralNode.PrimitiveLiteralNode) {
            Object value = ((LiteralNode) expression).getValue();
            if ((value instanceof String) && SAFE_PROPERTY_NAME.matcher((String) value).matches()) {
                return (String) value;
            }
            return null;
        }
        return null;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveExpressionStatement(ExpressionStatement expressionStatement) {
        Expression expression = expressionStatement.getExpression();
        ExpressionStatement expression2 = expressionStatement;
        if (((BlockLexicalContext) this.f30lc).getCurrentFunction().isProgram() && !isInternalExpression(expression) && !isEvalResultAssignment(expression)) {
            expression2 = expressionStatement.setExpression(new BinaryNode(Token.recast(expressionStatement.getToken(), TokenType.ASSIGN), compilerConstant(CompilerConstants.RETURN), expression));
        }
        return addStatement(expression2);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveBlockStatement(BlockStatement blockStatement) {
        return addStatement(blockStatement);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveForNode(ForNode forNode) {
        LoopNode test = forNode;
        JoinPredecessorExpression test2 = forNode.getTest();
        if (!forNode.isForIn() && Expression.isAlwaysTrue(test2)) {
            test = forNode.setTest(this.f30lc, (JoinPredecessorExpression) null);
        }
        ForNode forNode2 = (ForNode) checkEscape(test);
        if (forNode2.isForIn()) {
            addStatementEnclosedInBlock(forNode2);
        } else {
            addStatement(forNode2);
        }
        return forNode2;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveFunctionNode(FunctionNode functionNode) {
        this.log.info(new Object[]{"END FunctionNode: ", functionNode.getName()});
        return functionNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveIfNode(IfNode ifNode) {
        return addStatement(ifNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveIN(BinaryNode binaryNode) {
        return new RuntimeNode(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeOperatorVisitor
    public Node leaveINSTANCEOF(BinaryNode binaryNode) {
        return new RuntimeNode(binaryNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveLabelNode(LabelNode labelNode) {
        return addStatement(labelNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveReturnNode(ReturnNode returnNode) {
        addStatement(returnNode);
        return returnNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveCaseNode(CaseNode caseNode) {
        Expression test = caseNode.getTest();
        if (test instanceof LiteralNode) {
            LiteralNode literalNode = (LiteralNode) test;
            if (literalNode.isNumeric() && !(literalNode.getValue() instanceof Integer)) {
                double number = literalNode.getNumber();
                if (((double) ((int) number)) == number) {
                    return caseNode.setTest((Expression) LiteralNode.newInstance(literalNode, Integer.valueOf(literalNode.getInt32())).accept(this));
                }
            }
        }
        return caseNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveSwitchNode(SwitchNode switchNode) {
        if (!switchNode.isUniqueInteger()) {
            addStatementEnclosedInBlock(switchNode);
        } else {
            addStatement(switchNode);
        }
        return switchNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveThrowNode(ThrowNode throwNode) {
        return addStatement(throwNode);
    }

    private static Node ensureUniqueNamesIn(Node node) {
        return node.accept(new SimpleNodeVisitor() { // from class: jdk.nashorn.internal.codegen.Lower.2
            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveFunctionNode(FunctionNode functionNode) {
                return functionNode.setName(this.f30lc, this.f30lc.getCurrentFunction().uniqueName(functionNode.getName()));
            }

            /* JADX WARN: Type inference failed for: r1v1, types: [jdk.nashorn.internal.ir.LexicalContext, jdk.nashorn.internal.ir.Node] */
            public Node leaveDefault(Node node2) {
                return this.f30lc;
            }
        });
    }

    private static Block createFinallyBlock(Block block) {
        ArrayList arrayList = new ArrayList();
        for (Statement statement : block.getStatements()) {
            arrayList.add(statement);
            if (statement.hasTerminalFlags()) {
                break;
            }
        }
        return block.setStatements(null, arrayList);
    }

    private Block catchAllBlock(TryNode tryNode) {
        int lineNumber = tryNode.getLineNumber();
        long token = tryNode.getToken();
        int finish = tryNode.getFinish();
        IdentNode identNode = new IdentNode(token, finish, ((BlockLexicalContext) this.f30lc).getCurrentFunction().uniqueName(CompilerConstants.EXCEPTION_PREFIX.symbolName()));
        Block block = new Block(token, finish, new Statement[]{new ThrowNode(lineNumber, token, finish, new IdentNode(identNode), true)});
        if ($assertionsDisabled || block.isTerminal()) {
            return (Block) new Block(token, finish, new Statement[]{new CatchNode(lineNumber, token, finish, new IdentNode(identNode), null, block, true)}).accept(this);
        }
        throw new AssertionError();
    }

    private IdentNode compilerConstant(CompilerConstants compilerConstants) {
        FunctionNode currentFunction = ((BlockLexicalContext) this.f30lc).getCurrentFunction();
        return new IdentNode(currentFunction.getToken(), currentFunction.getFinish(), compilerConstants.symbolName());
    }

    private static boolean isTerminalFinally(Block block) {
        return block.getLastStatement().hasTerminalFlags();
    }

    private TryNode spliceFinally(TryNode tryNode, ThrowNode throwNode, Block block) {
        if (!$assertionsDisabled && tryNode.getFinallyBody() != null) {
            throw new AssertionError();
        }
        Block blockCreateFinallyBlock = createFinallyBlock(block);
        ArrayList arrayList = new ArrayList();
        TryNode tryNode2 = (TryNode) tryNode.accept(new SimpleNodeVisitor(this, throwNode, blockCreateFinallyBlock, ((BlockLexicalContext) this.f30lc).getCurrentFunction(), arrayList) { // from class: jdk.nashorn.internal.codegen.Lower.3
            final ThrowNode val$rethrow;
            final Block val$finallyBlock;
            final FunctionNode val$fn;
            final ArrayList val$inlinedFinallies;
            final Lower this$0;

            {
                this.this$0 = this;
                this.val$rethrow = throwNode;
                this.val$finallyBlock = blockCreateFinallyBlock;
                this.val$fn = functionNode;
                this.val$inlinedFinallies = arrayList;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveThrowNode(ThrowNode throwNode2) {
                if (this.val$rethrow == throwNode2) {
                    return new BlockStatement(Lower.prependFinally(this.val$finallyBlock, throwNode2));
                }
                return throwNode2;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveBreakNode(BreakNode breakNode) {
                return leaveJumpStatement(breakNode);
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveContinueNode(ContinueNode continueNode) {
                return leaveJumpStatement(continueNode);
            }

            private Node leaveJumpStatement(JumpStatement jumpStatement) {
                if (jumpStatement.getTarget(this.f30lc) == null) {
                    return Lower.createJumpToInlinedFinally(this.val$fn, this.val$inlinedFinallies, Lower.prependFinally(this.val$finallyBlock, jumpStatement));
                }
                return jumpStatement;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveReturnNode(ReturnNode returnNode) {
                Expression expression = returnNode.getExpression();
                if (Lower.isTerminalFinally(this.val$finallyBlock)) {
                    if (expression == null) {
                        return Lower.createJumpToInlinedFinally(this.val$fn, this.val$inlinedFinallies, (Block) Lower.ensureUniqueNamesIn(this.val$finallyBlock));
                    }
                    ArrayList arrayList2 = new ArrayList(2);
                    int lineNumber = returnNode.getLineNumber();
                    long token = returnNode.getToken();
                    arrayList2.add(new ExpressionStatement(lineNumber, token, returnNode.getFinish(), expression));
                    arrayList2.add(Lower.createJumpToInlinedFinally(this.val$fn, this.val$inlinedFinallies, (Block) Lower.ensureUniqueNamesIn(this.val$finallyBlock)));
                    return new BlockStatement(lineNumber, new Block(token, this.val$finallyBlock.getFinish(), arrayList2));
                }
                if (expression == null || (expression instanceof LiteralNode.PrimitiveLiteralNode) || ((expression instanceof IdentNode) && CompilerConstants.RETURN.symbolName().equals(((IdentNode) expression).getName()))) {
                    return Lower.createJumpToInlinedFinally(this.val$fn, this.val$inlinedFinallies, Lower.prependFinally(this.val$finallyBlock, returnNode));
                }
                ArrayList arrayList3 = new ArrayList();
                int lineNumber2 = returnNode.getLineNumber();
                long token2 = returnNode.getToken();
                int finish = returnNode.getFinish();
                IdentNode identNode = new IdentNode(expression.getToken(), expression.getFinish(), CompilerConstants.RETURN.symbolName());
                arrayList3.add(new ExpressionStatement(lineNumber2, token2, finish, new BinaryNode(Token.recast(returnNode.getToken(), TokenType.ASSIGN), identNode, expression)));
                arrayList3.add(Lower.createJumpToInlinedFinally(this.val$fn, this.val$inlinedFinallies, Lower.prependFinally(this.val$finallyBlock, returnNode.setExpression(identNode))));
                return new BlockStatement(lineNumber2, new Block(token2, finish, arrayList3));
            }
        });
        addStatement(arrayList.isEmpty() ? tryNode2 : tryNode2.setInlinedFinallies(this.f30lc, arrayList));
        addStatement(new BlockStatement(blockCreateFinallyBlock));
        return tryNode2;
    }

    private static JumpToInlinedFinally createJumpToInlinedFinally(FunctionNode functionNode, List list, Block block) {
        String strUniqueName = functionNode.uniqueName(":finally");
        long token = block.getToken();
        int finish = block.getFinish();
        list.add(new Block(token, finish, new Statement[]{new LabelNode(block.getFirstStatementLineNumber(), token, finish, strUniqueName, block)}));
        return new JumpToInlinedFinally(strUniqueName);
    }

    private static Block prependFinally(Block block, Statement statement) {
        Block block2 = (Block) ensureUniqueNamesIn(block);
        if (isTerminalFinally(block)) {
            return block2;
        }
        List statements = block2.getStatements();
        ArrayList arrayList = new ArrayList(statements.size() + 1);
        arrayList.addAll(statements);
        arrayList.add(statement);
        return new Block(block2.getToken(), statement.getFinish(), arrayList);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveTryNode(TryNode tryNode) {
        Block finallyBody = tryNode.getFinallyBody();
        TryNode finallyBody2 = tryNode.setFinallyBody(this.f30lc, null);
        if (finallyBody == null || finallyBody.getStatementCount() == 0) {
            List catches = finallyBody2.getCatches();
            if (catches == null || catches.isEmpty()) {
                return addStatement(new BlockStatement(tryNode.getBody()));
            }
            return addStatement(ensureUnconditionalCatch(finallyBody2));
        }
        Block blockCatchAllBlock = catchAllBlock(tryNode);
        ArrayList arrayList = new ArrayList(1);
        blockCatchAllBlock.accept(new SimpleNodeVisitor(this, arrayList) { // from class: jdk.nashorn.internal.codegen.Lower.4
            final List val$rethrows;
            final Lower this$0;

            {
                this.this$0 = this;
                this.val$rethrows = arrayList;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public boolean enterThrowNode(ThrowNode throwNode) {
                this.val$rethrows.add(throwNode);
                return true;
            }
        });
        if (!$assertionsDisabled && arrayList.size() != 1) {
            throw new AssertionError();
        }
        if (!tryNode.getCatchBlocks().isEmpty()) {
            finallyBody2 = finallyBody2.setBody(this.f30lc, new Block(finallyBody2.getToken(), finallyBody2.getFinish(), new Statement[]{ensureUnconditionalCatch(finallyBody2)})).setCatchBlocks(this.f30lc, null);
        }
        return (TryNode) ((BlockLexicalContext) this.f30lc).replace(tryNode, spliceFinally(finallyBody2.setCatchBlocks(this.f30lc, Arrays.asList(blockCatchAllBlock)), (ThrowNode) arrayList.get(0), finallyBody));
    }

    private TryNode ensureUnconditionalCatch(TryNode tryNode) {
        List catches = tryNode.getCatches();
        if (catches == null || catches.isEmpty() || ((CatchNode) catches.get(catches.size() - 1)).getExceptionCondition() == null) {
            return tryNode;
        }
        ArrayList arrayList = new ArrayList(tryNode.getCatchBlocks());
        arrayList.add(catchAllBlock(tryNode));
        return tryNode.setCatchBlocks(this.f30lc, arrayList);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveVarNode(VarNode varNode) {
        addStatement(varNode);
        if (varNode.getFlag(4) && ((BlockLexicalContext) this.f30lc).getCurrentFunction().isProgram()) {
            new ExpressionStatement(varNode.getLineNumber(), varNode.getToken(), varNode.getFinish(), new IdentNode(varNode.getName())).accept(this);
        }
        return varNode;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveWhileNode(WhileNode whileNode) {
        JoinPredecessorExpression test = whileNode.getTest();
        Block body = whileNode.getBody();
        if (Expression.isAlwaysTrue(test)) {
            ForNode forNode = (ForNode) new ForNode(whileNode.getLineNumber(), whileNode.getToken(), whileNode.getFinish(), body, 0).accept(this);
            ((BlockLexicalContext) this.f30lc).replace(whileNode, forNode);
            return forNode;
        }
        return addStatement(checkEscape(whileNode));
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveWithNode(WithNode withNode) {
        return addStatement(withNode);
    }

    private static Expression markerFunction(Expression expression) {
        if (expression instanceof IdentNode) {
            return ((IdentNode) expression).setIsFunction();
        }
        if (expression instanceof BaseNode) {
            return ((BaseNode) expression).setIsFunction();
        }
        return expression;
    }

    private String evalLocation(IdentNode identNode) {
        Source source = ((BlockLexicalContext) this.f30lc).getCurrentFunction().getSource();
        int iPosition = identNode.position();
        return source.getName() + '#' + source.getLine(iPosition) + ':' + source.getColumn(iPosition) + "<eval>";
    }

    private CallNode checkEval(CallNode callNode) {
        if (callNode.getFunction() instanceof IdentNode) {
            List args = callNode.getArgs();
            IdentNode identNode = (IdentNode) callNode.getFunction();
            if (args.size() >= 1 && CompilerConstants.EVAL.symbolName().equals(identNode.getName())) {
                ArrayList arrayList = new ArrayList(args.size());
                Iterator it = args.iterator();
                while (it.hasNext()) {
                    arrayList.add((Expression) ((Expression) ensureUniqueNamesIn((Expression) it.next())).accept(this));
                }
                return callNode.setEvalArgs(new CallNode.EvalArgs(arrayList, evalLocation(identNode)));
            }
        }
        return callNode;
    }

    private static boolean controlFlowEscapes(LexicalContext lexicalContext, Block block) {
        ArrayList arrayList = new ArrayList();
        block.accept(new SimpleNodeVisitor(arrayList, lexicalContext) { // from class: jdk.nashorn.internal.codegen.Lower.5
            final List val$escapes;
            final LexicalContext val$lex;

            {
                this.val$escapes = arrayList;
                this.val$lex = lexicalContext;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveBreakNode(BreakNode breakNode) {
                this.val$escapes.add(breakNode);
                return breakNode;
            }

            @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
            public Node leaveContinueNode(ContinueNode continueNode) {
                if (this.val$lex.contains(continueNode.getTarget(this.val$lex))) {
                    this.val$escapes.add(continueNode);
                }
                return continueNode;
            }
        });
        return !arrayList.isEmpty();
    }

    private LoopNode checkEscape(LoopNode loopNode) {
        boolean zControlFlowEscapes = controlFlowEscapes(this.f30lc, loopNode.getBody());
        if (zControlFlowEscapes) {
            return loopNode.setBody(this.f30lc, loopNode.getBody().setIsTerminal(this.f30lc, false)).setControlFlowEscapes(this.f30lc, zControlFlowEscapes);
        }
        return loopNode;
    }

    private Node addStatement(Statement statement) {
        ((BlockLexicalContext) this.f30lc).appendStatement(statement);
        return statement;
    }

    private void addStatementEnclosedInBlock(Statement statement) {
        BlockStatement blockStatementCreateReplacement = BlockStatement.createReplacement(statement, Collections.singletonList(statement));
        if (0 != 0) {
            blockStatementCreateReplacement = blockStatementCreateReplacement.setBlock(blockStatementCreateReplacement.getBlock().setIsTerminal(null, true));
        }
        addStatement(blockStatementCreateReplacement);
    }

    private static boolean isInternalExpression(Expression expression) {
        Symbol symbol;
        return (expression instanceof IdentNode) && (symbol = ((IdentNode) expression).getSymbol()) != null && symbol.isInternal();
    }

    private static boolean isEvalResultAssignment(Node node) {
        if (node instanceof BinaryNode) {
            Expression expressionLhs = ((BinaryNode) node).lhs();
            if (expressionLhs instanceof IdentNode) {
                return ((IdentNode) expressionLhs).getName().equals(CompilerConstants.RETURN.symbolName());
            }
            return false;
        }
        return false;
    }
}
