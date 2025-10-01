package jdk.nashorn.internal.codegen;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import jdk.nashorn.internal.p001ir.AccessNode;
import jdk.nashorn.internal.p001ir.BinaryNode;
import jdk.nashorn.internal.p001ir.Block;
import jdk.nashorn.internal.p001ir.BlockLexicalContext;
import jdk.nashorn.internal.p001ir.BreakNode;
import jdk.nashorn.internal.p001ir.CallNode;
import jdk.nashorn.internal.p001ir.CaseNode;
import jdk.nashorn.internal.p001ir.ContinueNode;
import jdk.nashorn.internal.p001ir.Expression;
import jdk.nashorn.internal.p001ir.ExpressionStatement;
import jdk.nashorn.internal.p001ir.FunctionNode;
import jdk.nashorn.internal.p001ir.GetSplitState;
import jdk.nashorn.internal.p001ir.IdentNode;
import jdk.nashorn.internal.p001ir.IfNode;
import jdk.nashorn.internal.p001ir.JumpStatement;
import jdk.nashorn.internal.p001ir.JumpToInlinedFinally;
import jdk.nashorn.internal.p001ir.LiteralNode;
import jdk.nashorn.internal.p001ir.Node;
import jdk.nashorn.internal.p001ir.ReturnNode;
import jdk.nashorn.internal.p001ir.SetSplitState;
import jdk.nashorn.internal.p001ir.SplitNode;
import jdk.nashorn.internal.p001ir.SplitReturn;
import jdk.nashorn.internal.p001ir.Statement;
import jdk.nashorn.internal.p001ir.SwitchNode;
import jdk.nashorn.internal.p001ir.VarNode;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;
import jdk.nashorn.internal.parser.Token;
import jdk.nashorn.internal.parser.TokenType;
import org.spongepowered.asm.mixin.injection.invoke.arg.ArgsClassGenerator;

/* loaded from: L-out.jar:jdk/nashorn/internal/codegen/SplitIntoFunctions.class */
final class SplitIntoFunctions extends NodeVisitor {
    private static final int FALLTHROUGH_STATE = -1;
    private static final int RETURN_STATE = 0;
    private static final int BREAK_STATE = 1;
    private static final int FIRST_JUMP_STATE = 2;
    private static final String THIS_NAME;
    private static final String RETURN_NAME;
    private static final String RETURN_PARAM_NAME;
    private final Deque functionStates;
    private final Deque splitStates;
    private final Namespace namespace;
    private boolean artificialBlock;
    private int nextFunctionId;
    static final boolean $assertionsDisabled;

    static {
        $assertionsDisabled = !SplitIntoFunctions.class.desiredAssertionStatus();
        THIS_NAME = CompilerConstants.THIS.symbolName();
        RETURN_NAME = CompilerConstants.RETURN.symbolName();
        RETURN_PARAM_NAME = RETURN_NAME + "-in";
    }

    public SplitIntoFunctions(Compiler compiler) {
        super(new BlockLexicalContext() { // from class: jdk.nashorn.internal.codegen.SplitIntoFunctions.1
            static final boolean $assertionsDisabled;

            static {
                $assertionsDisabled = !SplitIntoFunctions.class.desiredAssertionStatus();
            }

            @Override // jdk.nashorn.internal.p001ir.BlockLexicalContext
            protected Block afterSetStatements(Block block) {
                for (Statement statement : block.getStatements()) {
                    if (!$assertionsDisabled && (statement instanceof SplitNode)) {
                        throw new AssertionError();
                    }
                }
                return block;
            }
        });
        this.functionStates = new ArrayDeque();
        this.splitStates = new ArrayDeque();
        this.artificialBlock = false;
        this.nextFunctionId = -2;
        this.namespace = new Namespace(compiler.getScriptEnvironment().getNamespace());
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterFunctionNode(FunctionNode functionNode) {
        this.functionStates.push(new FunctionState(functionNode));
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveFunctionNode(FunctionNode functionNode) {
        this.functionStates.pop();
        return functionNode;
    }

    protected Node leaveDefault(Node node) {
        if (node instanceof Statement) {
            appendStatement((Statement) node);
        }
        return node;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterSplitNode(SplitNode splitNode) {
        getCurrentFunctionState().splitDepth++;
        this.splitStates.push(new SplitState(splitNode));
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveSplitNode(SplitNode splitNode) {
        Expression binaryNode;
        Statement statementMakeIfStateEquals;
        SplitState splitState;
        FunctionState currentFunctionState = getCurrentFunctionState();
        String name = splitNode.getName();
        Block body = splitNode.getBody();
        int firstStatementLineNumber = body.getFirstStatementLineNumber();
        long token = body.getToken();
        int finish = body.getFinish();
        FunctionNode functionNode = currentFunctionState.f17fn;
        if (!$assertionsDisabled && functionNode != ((BlockLexicalContext) this.f30lc).getCurrentFunction()) {
            throw new AssertionError();
        }
        boolean zIsProgram = functionNode.isProgram();
        TokenType tokenType = TokenType.FUNCTION;
        int i = this.nextFunctionId;
        this.nextFunctionId = i - 1;
        FunctionNode compileUnit = new FunctionNode(functionNode.getSource(), body.getFirstStatementLineNumber(), Token.toDesc(tokenType, i, 0), finish, 0L, this.namespace, createIdent(name), functionNode.getName() + ArgsClassGenerator.GETTER_PREFIX + name, zIsProgram ? Collections.singletonList(createReturnParamIdent()) : Collections.emptyList(), FunctionNode.Kind.NORMAL, 529).setBody(this.f30lc, body).setCompileUnit(this.f30lc, splitNode.getCompileUnit());
        IdentNode identNodeCreateIdent = createIdent(THIS_NAME);
        Expression callNode = new CallNode(firstStatementLineNumber, token, finish, new AccessNode(0L, 0, compileUnit, "call"), zIsProgram ? Arrays.asList(identNodeCreateIdent, createReturnIdent()) : Collections.singletonList(identNodeCreateIdent), false);
        SplitState splitState2 = (SplitState) this.splitStates.pop();
        currentFunctionState.splitDepth--;
        boolean z = splitState2.hasReturn;
        if (z && currentFunctionState.splitDepth > 0 && (splitState = (SplitState) this.splitStates.peek()) != null) {
            splitState.hasReturn = true;
        }
        if (z || zIsProgram) {
            binaryNode = new BinaryNode(Token.recast(token, TokenType.ASSIGN), createReturnIdent(), callNode);
        } else {
            binaryNode = callNode;
        }
        appendStatement(new ExpressionStatement(firstStatementLineNumber, token, finish, binaryNode));
        List list = splitState2.jumpStatements;
        int size = list.size();
        if (size > 0) {
            ArrayList arrayList = new ArrayList(size + (z ? 1 : 0));
            if (z) {
                addCase(arrayList, 0, createReturnFromSplit());
            }
            int i2 = 2;
            Iterator it = list.iterator();
            while (it.hasNext()) {
                int i3 = i2;
                i2++;
                addCase(arrayList, i3, enblockAndVisit((JumpStatement) it.next()));
            }
            statementMakeIfStateEquals = new SwitchNode(-1, token, finish, GetSplitState.INSTANCE, arrayList, null);
        } else {
            statementMakeIfStateEquals = null;
        }
        if (splitState2.hasBreak) {
            statementMakeIfStateEquals = makeIfStateEquals(firstStatementLineNumber, token, finish, 1, enblockAndVisit(new BreakNode(-1, token, finish, null)), statementMakeIfStateEquals);
        }
        if (z && size == 0) {
            statementMakeIfStateEquals = makeIfStateEquals(-1, token, finish, 0, createReturnFromSplit(), statementMakeIfStateEquals);
        }
        if (statementMakeIfStateEquals != null) {
            appendStatement(statementMakeIfStateEquals);
        }
        return splitNode;
    }

    private static void addCase(List list, int i, Block block) {
        list.add(new CaseNode(0L, 0, intLiteral(i), block));
    }

    private static LiteralNode intLiteral(int i) {
        return LiteralNode.newInstance(0L, 0, Integer.valueOf(i));
    }

    private static Block createReturnFromSplit() {
        return new Block(0L, 0, new Statement[]{createReturnReturn()});
    }

    private static ReturnNode createReturnReturn() {
        return new ReturnNode(-1, 0L, 0, createReturnIdent());
    }

    private static IdentNode createReturnIdent() {
        return createIdent(RETURN_NAME);
    }

    private static IdentNode createReturnParamIdent() {
        return createIdent(RETURN_PARAM_NAME);
    }

    private static IdentNode createIdent(String str) {
        return new IdentNode(0L, 0, str);
    }

    private Block enblockAndVisit(JumpStatement jumpStatement) {
        this.artificialBlock = true;
        Block block = (Block) new Block(0L, 0, new Statement[]{jumpStatement}).accept(this);
        this.artificialBlock = false;
        return block;
    }

    private static IfNode makeIfStateEquals(int i, long j, int i2, int i3, Block block, Statement statement) {
        return new IfNode(i, j, i2, new BinaryNode(Token.recast(j, TokenType.EQ_STRICT), GetSplitState.INSTANCE, intLiteral(i3)), block, statement == null ? null : new Block(0L, 0, new Statement[]{statement}));
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public boolean enterVarNode(VarNode varNode) {
        if (!inSplitNode()) {
            return super.enterVarNode(varNode);
        }
        if (!$assertionsDisabled && varNode.isBlockScoped()) {
            throw new AssertionError();
        }
        Expression init = varNode.getInit();
        getCurrentFunctionState().varStatements.add(varNode.setInit(null));
        if (init != null) {
            long jRecast = Token.recast(varNode.getToken(), TokenType.ASSIGN);
            new ExpressionStatement(varNode.getLineNumber(), jRecast, varNode.getFinish(), new BinaryNode(jRecast, varNode.getName(), varNode.getInit())).accept(this);
            return false;
        }
        return false;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveBlock(Block block) {
        if (!this.artificialBlock) {
            if (((BlockLexicalContext) this.f30lc).isFunctionBody()) {
                ((BlockLexicalContext) this.f30lc).prependStatements(getCurrentFunctionState().varStatements);
            } else if (((BlockLexicalContext) this.f30lc).isSplitBody()) {
                appendSplitReturn(-1, -1);
                if (getCurrentFunctionState().f17fn.isProgram()) {
                    ((BlockLexicalContext) this.f30lc).prependStatement(new ExpressionStatement(-1, 0L, 0, new BinaryNode(Token.toDesc(TokenType.ASSIGN, 0, 0), createReturnIdent(), createReturnParamIdent())));
                }
            }
        }
        return block;
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveBreakNode(BreakNode breakNode) {
        return leaveJumpNode(breakNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveContinueNode(ContinueNode continueNode) {
        return leaveJumpNode(continueNode);
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveJumpToInlinedFinally(JumpToInlinedFinally jumpToInlinedFinally) {
        return leaveJumpNode(jumpToInlinedFinally);
    }

    private JumpStatement leaveJumpNode(JumpStatement jumpStatement) {
        if (inSplitNode()) {
            SplitState currentSplitState = getCurrentSplitState();
            if (((BlockLexicalContext) this.f30lc).isExternalTarget(currentSplitState.splitNode, jumpStatement.getTarget(this.f30lc))) {
                appendSplitReturn(currentSplitState.getSplitStateIndex(jumpStatement), jumpStatement.getLineNumber());
                return jumpStatement;
            }
        }
        appendStatement(jumpStatement);
        return jumpStatement;
    }

    private void appendSplitReturn(int i, int i2) {
        appendStatement(new SetSplitState(i, i2));
        if (getCurrentFunctionState().f17fn.isProgram()) {
            appendStatement(createReturnReturn());
        } else {
            appendStatement(SplitReturn.INSTANCE);
        }
    }

    @Override // jdk.nashorn.internal.p001ir.visitor.NodeVisitor
    public Node leaveReturnNode(ReturnNode returnNode) {
        if (inSplitNode()) {
            appendStatement(new SetSplitState(0, returnNode.getLineNumber()));
            getCurrentSplitState().hasReturn = true;
        }
        appendStatement(returnNode);
        return returnNode;
    }

    private void appendStatement(Statement statement) {
        ((BlockLexicalContext) this.f30lc).appendStatement(statement);
    }

    private boolean inSplitNode() {
        return getCurrentFunctionState().splitDepth > 0;
    }

    private FunctionState getCurrentFunctionState() {
        return (FunctionState) this.functionStates.peek();
    }

    private SplitState getCurrentSplitState() {
        return (SplitState) this.splitStates.peek();
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/SplitIntoFunctions$FunctionState.class */
    private static class FunctionState {

        /* renamed from: fn */
        final FunctionNode f17fn;
        final List varStatements = new ArrayList();
        int splitDepth;

        FunctionState(FunctionNode functionNode) {
            this.f17fn = functionNode;
        }
    }

    /* loaded from: L-out.jar:jdk/nashorn/internal/codegen/SplitIntoFunctions$SplitState.class */
    private static class SplitState {
        final SplitNode splitNode;
        boolean hasReturn;
        boolean hasBreak;
        final List jumpStatements = new ArrayList();

        int getSplitStateIndex(JumpStatement jumpStatement) {
            if ((jumpStatement instanceof BreakNode) && jumpStatement.getLabelName() == null) {
                this.hasBreak = true;
                return 1;
            }
            int i = 0;
            for (JumpStatement jumpStatement2 : this.jumpStatements) {
                if (jumpStatement.getClass() == jumpStatement2.getClass() && Objects.equals(jumpStatement.getLabelName(), jumpStatement2.getLabelName())) {
                    return i + 2;
                }
                i++;
            }
            this.jumpStatements.add(jumpStatement);
            return i + 2;
        }

        SplitState(SplitNode splitNode) {
            this.splitNode = splitNode;
        }
    }
}
