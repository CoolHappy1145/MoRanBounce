package jdk.nashorn.internal.p001ir;

import java.util.Objects;
import jdk.nashorn.internal.codegen.Label;
import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/JumpToInlinedFinally.class */
public final class JumpToInlinedFinally extends JumpStatement {
    private static final long serialVersionUID = 1;
    static final boolean $assertionsDisabled;

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    public LexicalContextNode getPopScopeLimit(LexicalContext lexicalContext) {
        return getPopScopeLimit(lexicalContext);
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    public BreakableNode getTarget(LexicalContext lexicalContext) {
        return getTarget(lexicalContext);
    }

    static {
        $assertionsDisabled = !JumpToInlinedFinally.class.desiredAssertionStatus();
    }

    public JumpToInlinedFinally(String str) {
        super(-1, 0L, 0, (String) Objects.requireNonNull(str));
    }

    private JumpToInlinedFinally(JumpToInlinedFinally jumpToInlinedFinally, LocalVariableConversion localVariableConversion) {
        super(jumpToInlinedFinally, localVariableConversion);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterJumpToInlinedFinally(this)) {
            return nodeVisitor.leaveJumpToInlinedFinally(this);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    JumpStatement createNewJumpStatement(LocalVariableConversion localVariableConversion) {
        return new JumpToInlinedFinally(this, localVariableConversion);
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    public Block getTarget(LexicalContext lexicalContext) {
        return lexicalContext.getInlinedFinally(getLabelName());
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    public TryNode getPopScopeLimit(LexicalContext lexicalContext) {
        return lexicalContext.getTryNodeForInlinedFinally(getLabelName());
    }

    @Override // jdk.nashorn.internal.p001ir.JumpStatement
    Label getTargetLabel(BreakableNode breakableNode) {
        if ($assertionsDisabled || breakableNode != null) {
            return ((Block) breakableNode).getEntryLabel();
        }
        throw new AssertionError();
    }
}
