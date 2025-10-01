package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/ContinueNode.class */
public class ContinueNode extends JumpStatement {
    private static final long serialVersionUID = 1;

    public ContinueNode(int i, long j, int i2, String str) {
        super(i, j, i2, str);
    }

    private ContinueNode(ContinueNode continueNode, LocalVariableConversion localVariableConversion) {
        super(continueNode, localVariableConversion);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterContinueNode(this)) {
            return nodeVisitor.leaveContinueNode(this);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    JumpStatement createNewJumpStatement(LocalVariableConversion localVariableConversion) {
        return new ContinueNode(this, localVariableConversion);
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    public BreakableNode getTarget(LexicalContext lexicalContext) {
        return lexicalContext.getContinueTo(getLabelName());
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    Label getTargetLabel(BreakableNode breakableNode) {
        return ((LoopNode) breakableNode).getContinueLabel();
    }
}
