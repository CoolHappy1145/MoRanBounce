package jdk.nashorn.internal.p001ir;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

/* loaded from: L-out.jar:jdk/nashorn/internal/ir/LoopNode.class */
public abstract class LoopNode extends BreakableStatement {
    private static final long serialVersionUID = 1;
    protected final Label continueLabel;
    protected final JoinPredecessorExpression test;
    protected final Block body;
    protected final boolean controlFlowEscapes;

    public abstract Node ensureUniqueLabels(LexicalContext lexicalContext);

    public abstract boolean mustEnter();

    public abstract Block getBody();

    public abstract LoopNode setBody(LexicalContext lexicalContext, Block block);

    public abstract LoopNode setTest(LexicalContext lexicalContext, JoinPredecessorExpression joinPredecessorExpression);

    public abstract LoopNode setControlFlowEscapes(LexicalContext lexicalContext, boolean z);

    public abstract boolean hasPerIterationScope();

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement, jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return super.getLocalVariableConversion();
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement, jdk.nashorn.internal.p001ir.JoinPredecessor
    public JoinPredecessor setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        return super.setLocalVariableConversion(lexicalContext, localVariableConversion);
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement, jdk.nashorn.internal.p001ir.BreakableNode
    public Label getBreakLabel() {
        return super.getBreakLabel();
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableNode
    public boolean isBreakableWithoutLabel() {
        return true;
    }

    @Override // jdk.nashorn.internal.p001ir.LexicalContextStatement, jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        return super.accept(nodeVisitor);
    }

    protected LoopNode(int i, long j, int i2, Block block, boolean z) {
        super(i, j, i2, new Label("while_break"));
        this.continueLabel = new Label("while_continue");
        this.test = null;
        this.body = block;
        this.controlFlowEscapes = z;
    }

    protected LoopNode(LoopNode loopNode, JoinPredecessorExpression joinPredecessorExpression, Block block, boolean z, LocalVariableConversion localVariableConversion) {
        super(loopNode, localVariableConversion);
        this.continueLabel = new Label(loopNode.continueLabel);
        this.test = joinPredecessorExpression;
        this.body = block;
        this.controlFlowEscapes = z;
    }

    public boolean controlFlowEscapes() {
        return this.controlFlowEscapes;
    }

    @Override // jdk.nashorn.internal.p001ir.Statement, jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        if (mustEnter() && !this.controlFlowEscapes) {
            return this.body.isTerminal() || this.test == null;
        }
        return false;
    }

    public Label getContinueLabel() {
        return this.continueLabel;
    }

    @Override // jdk.nashorn.internal.p001ir.BreakableStatement, jdk.nashorn.internal.p001ir.Labels
    public List getLabels() {
        return Collections.unmodifiableList(Arrays.asList(this.breakLabel, this.continueLabel));
    }

    public final JoinPredecessorExpression getTest() {
        return this.test;
    }
}
