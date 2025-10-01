package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/ForNode.class */
public final class ForNode extends LoopNode {
    private static final long serialVersionUID = 1;
    private final Expression init;
    private final JoinPredecessorExpression modify;
    private final Symbol iterator;
    public static final int IS_FOR_IN = 1;
    public static final int IS_FOR_EACH = 2;
    public static final int PER_ITERATION_SCOPE = 4;
    private final int flags;

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public LoopNode setControlFlowEscapes(LexicalContext lexicalContext, boolean z) {
        return setControlFlowEscapes(lexicalContext, z);
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public LoopNode setTest(LexicalContext lexicalContext, JoinPredecessorExpression joinPredecessorExpression) {
        return setTest(lexicalContext, joinPredecessorExpression);
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public LoopNode setBody(LexicalContext lexicalContext, Block block) {
        return setBody(lexicalContext, block);
    }

    public ForNode(int i, long j, int i2, Block block, int i3) {
        super(i, j, i2, block, false);
        this.flags = i3;
        this.init = null;
        this.modify = null;
        this.iterator = null;
    }

    private ForNode(ForNode forNode, Expression expression, JoinPredecessorExpression joinPredecessorExpression, Block block, JoinPredecessorExpression joinPredecessorExpression2, int i, boolean z, LocalVariableConversion localVariableConversion, Symbol symbol) {
        super(forNode, joinPredecessorExpression, block, z, localVariableConversion);
        this.init = expression;
        this.modify = joinPredecessorExpression2;
        this.flags = i;
        this.iterator = symbol;
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode, jdk.nashorn.internal.p001ir.BreakableNode
    public Node ensureUniqueLabels(LexicalContext lexicalContext) {
        return (Node) Node.replaceInLexicalContext(lexicalContext, this, new ForNode(this, this.init, this.test, this.body, this.modify, this.flags, this.controlFlowEscapes, this.conversion, this.iterator));
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextNode
    public Node accept(LexicalContext lexicalContext, NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterForNode(this)) {
            return nodeVisitor.leaveForNode(setInit(lexicalContext, this.init == null ? null : (Expression) this.init.accept(nodeVisitor)).setTest(lexicalContext, this.test == null ? null : (JoinPredecessorExpression) this.test.accept(nodeVisitor)).setModify(lexicalContext, this.modify == null ? null : (JoinPredecessorExpression) this.modify.accept(nodeVisitor)).setBody(lexicalContext, (Block) this.body.accept(nodeVisitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append("for");
        LocalVariableConversion.toString(this.conversion, sb).append(' ');
        if (isForIn()) {
            this.init.toString(sb, z);
            sb.append(" in ");
            this.modify.toString(sb, z);
        } else {
            if (this.init != null) {
                this.init.toString(sb, z);
            }
            sb.append("; ");
            if (this.test != null) {
                this.test.toString(sb, z);
            }
            sb.append("; ");
            if (this.modify != null) {
                this.modify.toString(sb, z);
            }
        }
        sb.append(')');
    }

    @Override // jdk.nashorn.internal.p001ir.Statement
    public boolean hasGoto() {
        return !isForIn() && this.test == null;
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public boolean mustEnter() {
        return !isForIn() && this.test == null;
    }

    public Expression getInit() {
        return this.init;
    }

    public ForNode setInit(LexicalContext lexicalContext, Expression expression) {
        if (this.init == expression) {
            return this;
        }
        return (ForNode) Node.replaceInLexicalContext(lexicalContext, this, new ForNode(this, expression, this.test, this.body, this.modify, this.flags, this.controlFlowEscapes, this.conversion, this.iterator));
    }

    public boolean isForIn() {
        return (this.flags & 1) != 0;
    }

    public ForNode setIsForIn(LexicalContext lexicalContext) {
        return setFlags(lexicalContext, this.flags | 1);
    }

    public boolean isForEach() {
        return (this.flags & 2) != 0;
    }

    public ForNode setIsForEach(LexicalContext lexicalContext) {
        return setFlags(lexicalContext, this.flags | 2);
    }

    public Symbol getIterator() {
        return this.iterator;
    }

    public ForNode setIterator(LexicalContext lexicalContext, Symbol symbol) {
        if (this.iterator == symbol) {
            return this;
        }
        return (ForNode) Node.replaceInLexicalContext(lexicalContext, this, new ForNode(this, this.init, this.test, this.body, this.modify, this.flags, this.controlFlowEscapes, this.conversion, symbol));
    }

    public JoinPredecessorExpression getModify() {
        return this.modify;
    }

    public ForNode setModify(LexicalContext lexicalContext, JoinPredecessorExpression joinPredecessorExpression) {
        if (this.modify == joinPredecessorExpression) {
            return this;
        }
        return (ForNode) Node.replaceInLexicalContext(lexicalContext, this, new ForNode(this, this.init, this.test, this.body, joinPredecessorExpression, this.flags, this.controlFlowEscapes, this.conversion, this.iterator));
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public ForNode setTest(LexicalContext lexicalContext, JoinPredecessorExpression joinPredecessorExpression) {
        if (this.test == joinPredecessorExpression) {
            return this;
        }
        return (ForNode) Node.replaceInLexicalContext(lexicalContext, this, new ForNode(this, this.init, joinPredecessorExpression, this.body, this.modify, this.flags, this.controlFlowEscapes, this.conversion, this.iterator));
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public Block getBody() {
        return this.body;
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public ForNode setBody(LexicalContext lexicalContext, Block block) {
        if (this.body == block) {
            return this;
        }
        return (ForNode) Node.replaceInLexicalContext(lexicalContext, this, new ForNode(this, this.init, this.test, block, this.modify, this.flags, this.controlFlowEscapes, this.conversion, this.iterator));
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public ForNode setControlFlowEscapes(LexicalContext lexicalContext, boolean z) {
        if (this.controlFlowEscapes == z) {
            return this;
        }
        return (ForNode) Node.replaceInLexicalContext(lexicalContext, this, new ForNode(this, this.init, this.test, this.body, this.modify, this.flags, z, this.conversion, this.iterator));
    }

    private ForNode setFlags(LexicalContext lexicalContext, int i) {
        if (this.flags == i) {
            return this;
        }
        return (ForNode) Node.replaceInLexicalContext(lexicalContext, this, new ForNode(this, this.init, this.test, this.body, this.modify, i, this.controlFlowEscapes, this.conversion, this.iterator));
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement
    JoinPredecessor setLocalVariableConversionChanged(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        return (JoinPredecessor) Node.replaceInLexicalContext(lexicalContext, this, new ForNode(this, this.init, this.test, this.body, this.modify, this.flags, this.controlFlowEscapes, localVariableConversion, this.iterator));
    }

    @Override // jdk.nashorn.internal.p001ir.LoopNode
    public boolean hasPerIterationScope() {
        return (this.flags & 4) != 0;
    }

    public ForNode setPerIterationScope(LexicalContext lexicalContext) {
        return setFlags(lexicalContext, this.flags | 4);
    }
}
