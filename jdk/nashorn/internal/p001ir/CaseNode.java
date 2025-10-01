package jdk.nashorn.internal.p001ir;

import java.util.Collections;
import java.util.List;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/CaseNode.class */
public final class CaseNode extends Node implements JoinPredecessor, Labels, Terminal {
    private static final long serialVersionUID = 1;
    private final Expression test;
    private final Block body;
    private final Label entry;
    private final LocalVariableConversion conversion;

    public CaseNode(long j, int i, Expression expression, Block block) {
        super(j, i);
        this.test = expression;
        this.body = block;
        this.entry = new Label("entry");
        this.conversion = null;
    }

    CaseNode(CaseNode caseNode, Expression expression, Block block, LocalVariableConversion localVariableConversion) {
        super(caseNode);
        this.test = expression;
        this.body = block;
        this.entry = new Label(caseNode.entry);
        this.conversion = localVariableConversion;
    }

    @Override // jdk.nashorn.internal.p001ir.Terminal
    public boolean isTerminal() {
        return this.body.isTerminal();
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterCaseNode(this)) {
            Expression expression = this.test == null ? null : (Expression) this.test.accept(nodeVisitor);
            return nodeVisitor.leaveCaseNode(setTest(expression).setBody(this.body == null ? null : (Block) this.body.accept(nodeVisitor)));
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        if (this.test != null) {
            sb.append("case ");
            this.test.toString(sb, z);
            sb.append(':');
            return;
        }
        sb.append("default:");
    }

    public Block getBody() {
        return this.body;
    }

    public Label getEntry() {
        return this.entry;
    }

    public Expression getTest() {
        return this.test;
    }

    public CaseNode setTest(Expression expression) {
        if (this.test == expression) {
            return this;
        }
        return new CaseNode(this, expression, this.body, this.conversion);
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public JoinPredecessor setLocalVariableConversion(LexicalContext lexicalContext, LocalVariableConversion localVariableConversion) {
        if (this.conversion == localVariableConversion) {
            return this;
        }
        return new CaseNode(this, this.test, this.body, localVariableConversion);
    }

    @Override // jdk.nashorn.internal.p001ir.JoinPredecessor
    public LocalVariableConversion getLocalVariableConversion() {
        return this.conversion;
    }

    private CaseNode setBody(Block block) {
        if (this.body == block) {
            return this;
        }
        return new CaseNode(this, this.test, block, this.conversion);
    }

    @Override // jdk.nashorn.internal.p001ir.Labels
    public List getLabels() {
        return Collections.unmodifiableList(Collections.singletonList(this.entry));
    }
}
