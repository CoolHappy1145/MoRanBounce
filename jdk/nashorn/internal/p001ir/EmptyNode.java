package jdk.nashorn.internal.p001ir;

import jdk.nashorn.internal.p001ir.annotations.Immutable;
import jdk.nashorn.internal.p001ir.visitor.NodeVisitor;

@Immutable
/* loaded from: L-out.jar:jdk/nashorn/internal/ir/EmptyNode.class */
public final class EmptyNode extends Statement {
    private static final long serialVersionUID = 1;

    public EmptyNode(Statement statement) {
        super(statement);
    }

    public EmptyNode(int i, long j, int i2) {
        super(i, j, i2);
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public Node accept(NodeVisitor nodeVisitor) {
        if (nodeVisitor.enterEmptyNode(this)) {
            return nodeVisitor.leaveEmptyNode(this);
        }
        return this;
    }

    @Override // jdk.nashorn.internal.p001ir.Node
    public void toString(StringBuilder sb, boolean z) {
        sb.append(';');
    }
}
