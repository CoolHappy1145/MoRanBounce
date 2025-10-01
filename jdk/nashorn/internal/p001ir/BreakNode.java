package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/BreakNode.class */
public final class BreakNode extends JumpStatement {
    private static final long serialVersionUID = 1;

    public BreakNode(int i, long j, int i2, String str) {
        super(i, j, i2, str);
    }

    private BreakNode(BreakNode breakNode, LocalVariableConversion localVariableConversion) {
        super(breakNode, localVariableConversion);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterBreakNode(this)) {
            return nodeVisitor.leaveBreakNode(this);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    JumpStatement createNewJumpStatement(LocalVariableConversion localVariableConversion) {
        return new BreakNode(this, localVariableConversion);
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    public BreakableNode getTarget(LexicalContext lexicalContext) {
        return lexicalContext.getBreakable(getLabelName());
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    Label getTargetLabel(BreakableNode breakableNode) {
        return breakableNode.getBreakLabel();
    }
}
